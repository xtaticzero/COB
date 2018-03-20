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
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaAprobarGrupo;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EventoCargaEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.NivelEmisionEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoServiciosConcurrentesEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.aprobacion.AprobarMultasService;
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
 * @author christian.ventura
 */
@Controller
@RequestMapping("/firmaMultas/")
public class FirmaMultasController {

    private final Logger logger = Logger.getLogger(FirmaMultasController.class);
    @Autowired
    private AprobarMultasService aprobarMultasService;
    @Autowired
    private ConcurrenceService concurrenceServiceImpl;
    @Autowired
    @Qualifier("firmaMultas")
    FirmaDocumentoJob firmaDocumentos;
    @Autowired
    private AdministracionFuncionariosService administracionFuncionariosService;
    private MultaAprobarGrupo multaAprobarGrupo;
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
            documentosFirmar = aprobarMultasService.listarCadenasOriginales(multaAprobarGrupo, administrativo, rowInicial, rowFinal);
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
    public Map<String, Object> guardarDatos(HttpServletRequest request, HttpSession sesion) {
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
                valoresSesion(request, sesion);

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
        Map<String, Object> retorno = new HashMap<String, Object>();
        Integer totalMultasFirmar = null;
        try {
            valoresSesion(request, sesion);
            if (multaAprobarGrupo.getConteoElementos() != null) {
                totalMultasFirmar = multaAprobarGrupo.getConteoElementos();
            } else {
                totalMultasFirmar = aprobarMultasService.contarResolucionesAprobar(multaAprobarGrupo, administrativo);
            }

        } catch (Exception ex) {
            logger.error(ex);
        }

        retorno.put("numeroRegistros", totalMultasFirmar);
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
            logger.info("Verificando si se puede generar el firmado de multas");

            valoresSesion(request, sesion);

            String firma = aprobarMultasService.generaFirma(multaAprobarGrupo, administrativo);
            if (!concurrenceServiceImpl.isLockedServices(TipoServiciosConcurrentesEnum.IDENTIFICADOR_APROBAR_MULTAS, firma)) {
                logger.info("Si se puede hacer el firmado de multas");
                //eliminamos las firmas de multas que no se hayan podido aprobar en ocasiones anteriores
                aprobarMultasService.eliminaFirmasSinAprobar(multaAprobarGrupo, administrativo);
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
        if (sesion.getAttribute("multaAprobarGrupo") != null) {
            multaAprobarGrupo = (MultaAprobarGrupo) sesion.getAttribute("multaAprobarGrupo");
        } else {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("multaGrupo")) {
                    Gson converter = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                    multaAprobarGrupo = converter.fromJson(cookie.getValue(), MultaAprobarGrupo.class);
                    sesion.setAttribute("multaAprobarGrupo", multaAprobarGrupo);
                }
            }
        }
    }
}
