/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import mx.gob.sat.siat.cob.seguimiento.batch.job.FirmaDocumentoJob;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AdministrativoNivelCargo;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.CadenaOriginal;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.FirmaDigital;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VisualizaVigilanciaRenuentes;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EventoCargaEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoServiciosConcurrentesEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.aprobacion.AprobarRenuentesService;
import mx.gob.sat.siat.cob.seguimiento.service.otros.AdministracionFuncionariosService;
import mx.gob.sat.siat.cob.seguimiento.service.otros.ConcurrenceService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author root
 */
@Controller
@RequestMapping("/firmaRenuentes/")
public class FirmaRenuentesController {

    private final Logger logger = Logger.getLogger(FirmaMultasController.class);
    @Autowired
    private AprobarRenuentesService aprobarRenuentesService;
    @Autowired
    @Qualifier("firmaDocumentosRenuentes")
    FirmaDocumentoJob firmaDocumentos;
    @Autowired
    private AdministracionFuncionariosService administracionFuncionariosService;
    @Autowired
    private ConcurrenceService concurrenceServiceImpl;
    private VisualizaVigilanciaRenuentes visualizaVigilanciaRenuentes;
    private AdministrativoNivelCargo administrativo;

    @RequestMapping(value = "/getDatos", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getDatos(HttpServletRequest request, HttpSession sesion) {
        logger.debug("obteniendo cadenas originales");
        Integer rowInicial;
        Integer rowFinal;
        Map<String, Object> retorno = new HashMap<String, Object>();

        rowInicial = Integer.parseInt(request.getParameter("rowInicial"));
        rowFinal = Integer.parseInt(request.getParameter("rowFinal"));
        List<CadenaOriginal> documentosFirmar = null;
        try {
            valoresSesion(request, sesion);
            documentosFirmar = aprobarRenuentesService.listarCadenasOriginales(visualizaVigilanciaRenuentes, administrativo, rowInicial, rowFinal);
            retorno.put("cadenasOriginales", documentosFirmar);
            retorno.put("estatus", 1);
        } catch (Exception ex) {
            logger.error(ex);
            retorno.put("estatus", 0);
        }



        return retorno;
    }

    @RequestMapping(value = "/guardarDatos", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> guardarDatos(HttpServletRequest request) {
        logger.info("metodo de guardado de firmas");
        Gson converter = new Gson();
        List<FirmaDigital> firmas = null;
        logger.debug("guardar datos");
        Map<String, Object> retorno = new HashMap<String, Object>();

        try {
            firmas = converter.fromJson(request.getParameter("firmas"), tipoListaDatoFirma());
        } catch (Exception ex) {
            logger.error("error al convertir \n" + request.getParameter("firmas"));
        }
        if (firmas != null) {
            try {
                logger.info("guardando las firmas");
                firmaDocumentos.firmar(firmas, administrativo.getNumeroEmpleado());
                retorno.put("mensaje", "guardado");
                retorno.put("estatus", 1);
            } catch (SGTServiceException ex) {
                logger.error("No se pudo realizar el guardado de firmas\n" + ex);
                retorno.put("estatus", 0);
                retorno.put("mensaje", "No se pudo realizar el guardado de firmas");
            }
        }

        return retorno;
    }

    @RequestMapping(value = "/getTotalRegistros", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getTotalRegistros(HttpServletRequest request, HttpSession sesion) {
        logger.debug("get total de registros");
        Map<String, Object> retorno = new HashMap<String, Object>();

        Integer totalDocsFirmar = null;
        try {
            valoresSesion(request, sesion);
            totalDocsFirmar = aprobarRenuentesService.contarDocumentosAprobar(visualizaVigilanciaRenuentes, administrativo);
        } catch (Exception ex) {
            logger.error(ex);
        }

        retorno.put("numeroRegistros", totalDocsFirmar);
        retorno.put("estatus", 1);

        return retorno;
    }

    @RequestMapping(value = "/procesoTomado", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getProcesoTomado(HttpServletRequest request, HttpSession sesion) {
        Map<String, Object> retorno = new HashMap<String, Object>();
        retorno.put("estatus", 1);
        retorno.put("bloqueado", 1);
        try {
            valoresSesion(request, sesion);
            String firma = aprobarRenuentesService.generaFirma(visualizaVigilanciaRenuentes, administrativo);
            if (!concurrenceServiceImpl.isLockedServices(TipoServiciosConcurrentesEnum.IDENTIFICADOR_DOCUMENTOS_RENUENTES, firma)) {
                //eliminamos las firmas que no se hayan podido aprobar en ocasiones anteriores
                aprobarRenuentesService.eliminaFirmasSinAprobar(visualizaVigilanciaRenuentes, administrativo);
                retorno.put("bloqueado", 0);
            }

        } catch (SGTServiceException ex) {
            retorno.put("estatus", 0);
            logger.error("Error al eliminar las firmas sin aprobar\n" + ex);
        } catch (Exception ex) {
            retorno.put("estatus", 0);
            retorno.put("error", ex.getMessage());
            logger.error("Error en verificar si el proceso esta tomado \n" + ex);
        }


        return retorno;
    }

    private Type tipoListaDatoFirma() {
        return new TypeToken<List<FirmaDigital>>() {
        }.getType();
    }

    private void valoresSesion(HttpServletRequest request, HttpSession sesion) throws SGTServiceException {
        Cookie cookies[] = request.getCookies();
        if (sesion.getAttribute("administrativo") != null) {
            administrativo = (AdministrativoNivelCargo) sesion.getAttribute("administrativo");
        } else {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("administrativo")) {
                    administrativo = administracionFuncionariosService.buscarCargoAdministrativo(cookie.getValue(), EventoCargaEnum.CARGA_OMISOS);
                    sesion.setAttribute("administrativo", administrativo);
                }
            }
        }
        if (sesion.getAttribute("vigilanciaGrupoDocumentos") != null) {
            visualizaVigilanciaRenuentes = (VisualizaVigilanciaRenuentes) sesion.getAttribute("vigilanciaGrupoDocumentos");
        } else {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("renuenteGrupo")) {
                    Gson converter = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                    visualizaVigilanciaRenuentes = converter.fromJson(cookie.getValue(), VisualizaVigilanciaRenuentes.class);
                    sesion.setAttribute("vigilanciaGrupoDocumentos", visualizaVigilanciaRenuentes);
                }
            }
        }
    }
}
