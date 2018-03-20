/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing;

import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mx.gob.sat.siat.cob.seguimiento.dto.db2.Paginador;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AdministrativoNivelCargo;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaAprobarGrupo;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EventoCargaEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoFirmaEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.aprobacion.AprobarMultasService;
import mx.gob.sat.siat.cob.seguimiento.service.aprobacion.impl.AprobarMultasThread;
import mx.gob.sat.siat.cob.seguimiento.service.otros.AdministracionFuncionariosService;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import mx.gob.sat.siat.cob.seguimiento.utils.AbstractBaseMB;
import mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.helper.AprobarMultasManagedBeanHelper;
import mx.gob.sat.siat.exception.AccesoDenegadoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;
import mx.gob.sat.siat.util.MovimientoUtil;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author root
 */
@Controller("aprobarMultaMB")
@Scope(value = "view")
public class AprobarMultasManagedBean extends AbstractBaseMB {

    private static final long serialVersionUID = -51976912L;
    private AprobarMultasManagedBeanHelper aprobarMultasManagedBeanHelper;
    @Autowired
    private AprobarMultasService aprobarMultasService;
    @Autowired
    private AdministracionFuncionariosService administracionFuncionariosService;
    @Autowired
    private ApplicationContext applicationContext;
    private AdministrativoNivelCargo administrativo;
    private Integer progress;
    private transient AprobarMultasThread aprobarMultasThread;
    private static final int TAMANIO_PAGINAS = 20;

    public AprobarMultasManagedBean() {
        aprobarMultasManagedBeanHelper = new AprobarMultasManagedBeanHelper();
        aprobarMultasManagedBeanHelper.setMultaAprobarGrupo((MultaAprobarGrupo) ((HttpSession) FacesContext.
                getCurrentInstance().
                getExternalContext().
                getSession(true)).
                getAttribute("multaAprobarGrupo"));
        ((HttpSession) FacesContext.
                getCurrentInstance().
                getExternalContext().
                getSession(true)).removeAttribute("multaAprobarGrupo");
    }

    @PostConstruct
    public void init() {
        try {
            obtenerAccesoUsrEmpleados();
            autorizar(ConstantsCatalogos.IDENTIFICADOR_EMISION_MULTAS);
            administrativo = administracionFuncionariosService.buscarCargoAdministrativo(this.getAccesoUsr().getNumeroEmp(), EventoCargaEnum.CARGA_OMISOS);

            generaCookies();

            aprobarMultasManagedBeanHelper.setPaginador(new Paginador(aprobarMultasManagedBeanHelper.getMultaAprobarGrupo().getConteoElementos(), TAMANIO_PAGINAS));
            aprobarMultasManagedBeanHelper.getMultaAprobarGrupo().setCantidadPosiblesEmitir(aprobarMultasManagedBeanHelper.getPaginador().getTamanioTotal());
            cargarPagina();
            aprobarMultasManagedBeanHelper.setPagina(aprobarMultasManagedBeanHelper.getPaginador().getNumeroPagina());
            aprobarMultasManagedBeanHelper.setListaMultaNoAprobar(aprobarMultasService.listarMultasNoAprobar(aprobarMultasManagedBeanHelper.getMultaAprobarGrupo(),
                    administrativo));
            progress = 0;
        } catch (SGTServiceException ex) {
            mensajeError(ex.getMessage());
        }
    }

    private void cargarPagina() throws SGTServiceException {
        this.getLogger().info("comenzando a traer las multas");
        aprobarMultasManagedBeanHelper.setListaMultaAprobar(
                aprobarMultasService.listarMultasPorAprobar(aprobarMultasManagedBeanHelper.getMultaAprobarGrupo(),
                aprobarMultasManagedBeanHelper.getPaginador(), administrativo));
    }

    public AprobarMultasManagedBeanHelper getAprobarMultasManagedBeanHelper() {
        return aprobarMultasManagedBeanHelper;
    }

    public void setAprobarMultasManagedBeanHelper(AprobarMultasManagedBeanHelper aprobarMultasManagedBeanHelper) {
        this.aprobarMultasManagedBeanHelper = aprobarMultasManagedBeanHelper;
    }

    public void siguiente() {
        try {
            if (aprobarMultasManagedBeanHelper.getPaginador().next()) {
                cargarPagina();
            }
        } catch (SGTServiceException e) {
            aprobarMultasManagedBeanHelper.getPaginador().back();
            mensajeError(e.getMessage());
        }
        aprobarMultasManagedBeanHelper.setPagina(aprobarMultasManagedBeanHelper.getPaginador().getNumeroPagina());
    }

    public void anterior() {
        try {
            if (aprobarMultasManagedBeanHelper.getPaginador().back()) {
                cargarPagina();
            }
        } catch (SGTServiceException e) {
            aprobarMultasManagedBeanHelper.getPaginador().next();
            mensajeError(e.getMessage());
        }
        aprobarMultasManagedBeanHelper.setPagina(aprobarMultasManagedBeanHelper.getPaginador().getNumeroPagina());
    }

    public void irA() {
        long buffer = aprobarMultasManagedBeanHelper.getPaginador().getNumeroPagina();
        try {

            if (aprobarMultasManagedBeanHelper.getPaginador().goTo(aprobarMultasManagedBeanHelper.getPagina())) {
                cargarPagina();
            }
        } catch (SGTServiceException e) {
            aprobarMultasManagedBeanHelper.getPaginador().goTo(buffer);
            mensajeError(e.getMessage());
        }
        aprobarMultasManagedBeanHelper.setPagina(aprobarMultasManagedBeanHelper.getPaginador().getNumeroPagina());
    }

    public void obtieneDetalles() {
        try {
            aprobarMultasManagedBeanHelper.setListaDetalle(aprobarMultasService.listarDetallesMulta(aprobarMultasManagedBeanHelper.getMultaSeleccionada()));
        } catch (SGTServiceException ex) {
            mensajeError(ex.getMessage());
        }
    }

    public boolean isTieneDocumentosAprobar() {
        return aprobarMultasManagedBeanHelper.getPaginador().getTamanioTotal() > 0;
    }

    public String getRfc() {
        return getAccesoUsr().getUsuario();
    }

    public void emision() {
        try {
            SegbMovimientoDTO dto = MovimientoUtil.addMovimiento(getSession(), ConstantsCatalogos.CVE_SISTEMA, ConstantsCatalogos.IDENTIFICADOR_EMISION_MULTAS, new Date(), new Date(), ConstantsCatalogos.MODIFICACION_MOV_MULTAOBLIGACION, ConstantsCatalogos.EMISION_MULTAS_OBS);
            aprobarMultasThread = applicationContext.getBean(AprobarMultasThread.class);
            try {
                aprobarMultasThread.establecerValoresEjecucion(aprobarMultasManagedBeanHelper.getMultaAprobarGrupo(), dto,
                        administrativo, aprobarMultasManagedBeanHelper.getProgress());
            } catch (SGTServiceException ex) {
                aprobarMultasManagedBeanHelper.getProgress().setValor(AprobarMultasService.PORCENTAJE_TOTAL);
                mensajeError(ex.getMessage());                
                return;
            }
            aprobarMultasThread.start();
        } catch (AccesoDenegadoException e) {
            aprobarMultasManagedBeanHelper.getProgress().setValor(AprobarMultasService.PORCENTAJE_TOTAL);
            getLogger().error(e.getMessage());
            mensajeError(e.getMessage());
        }
    }

    private void mensajeError(String mensaje) {
        addErrorMessage("Error", mensaje);
    }

    public void increment() {
        progress++;
        getLogger().info("\nel número de llamadas del poll va en " + progress);
    }

    public void redirigirMultasPrincipal() {
        getLogger().info("\nelarchivo de resultados eses " + aprobarMultasThread.getArchivoResultados());

        if (aprobarMultasThread.getArchivoResultados() != null) {
            ((HttpSession) FacesContext.
                    getCurrentInstance().
                    getExternalContext().
                    getSession(true)).setAttribute("archivoResultadosEmisionMulta", aprobarMultasThread.getArchivoResultados());
            redirigir("/pages/cob/aprobarVigilancias/aprobarMultaGrupo.jsf");
        } else {
            mensajeError("Error al emitir las multas " + aprobarMultasThread.getException().getMessage());
        }

    }

    public boolean isFirmaDigital() {
        return aprobarMultasManagedBeanHelper.getMultaAprobarGrupo().getIdTipoFirma().equals(TipoFirmaEnum.ELECTRONICA.getValor());
    }

    private void generaCookies() {
        ExternalContext externalContext;
        HttpServletResponse response;
        Cookie cookieAdministrativo;
        Cookie cookieMultaGrupo;

        externalContext = FacesContext.getCurrentInstance().getExternalContext();
        response = (HttpServletResponse) externalContext.getResponse();

        cookieAdministrativo = new Cookie("administrativo", administrativo.getNumeroEmpleado());
        cookieMultaGrupo = new Cookie("multaGrupo", aprobarMultasManagedBeanHelper.getMultaAprobarGrupo().toString());

        cookieAdministrativo.setMaxAge(-1);
        cookieAdministrativo.setPath("/app/PE/cob/restServices");

        cookieMultaGrupo.setMaxAge(-1);
        cookieMultaGrupo.setPath("/app/PE/cob/restServices");


        response.addCookie(cookieAdministrativo);
        response.addCookie(cookieMultaGrupo);

    }
}
