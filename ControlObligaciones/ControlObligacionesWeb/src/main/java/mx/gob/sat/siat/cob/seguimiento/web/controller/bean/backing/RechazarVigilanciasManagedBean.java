/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing;

import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MotRechazoVig;
import mx.gob.sat.siat.cob.seguimiento.dto.db2.Paginador;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaAdministracionLocal;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.aprobacion.AprobarVigilanciasService;
import mx.gob.sat.siat.cob.seguimiento.service.aprobacion.RechazarVigilanciasService;
import mx.gob.sat.siat.cob.seguimiento.service.carga.CargaOmisosEntidadFederativaService;
import mx.gob.sat.siat.cob.seguimiento.service.catalogos.MotRechazoVigService;
import mx.gob.sat.siat.cob.seguimiento.service.otros.MovimientosService;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import mx.gob.sat.siat.cob.seguimiento.utils.AbstractBaseMB;
import mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.helper.RechazarVigilanciasHelper;
import mx.gob.sat.siat.exception.AccesoDenegadoException;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;
import mx.gob.sat.siat.util.MovimientoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author usuario
 */
@Controller
@Scope("view")
public class RechazarVigilanciasManagedBean extends AbstractBaseMB {

    @Autowired
    private CargaOmisosEntidadFederativaService cargaOmisosEntidadFederativaService;
    @Autowired
    private AprobarVigilanciasService aprobarVigilanciasService;
    @Autowired
    private MotRechazoVigService motRechazoVigService;
    @Autowired
    private RechazarVigilanciasService rechazarVigilanciasService;
    @Autowired
    private MovimientosService movimientosService;
    private RechazarVigilanciasHelper rechazarVigilanciasHelper;
    private List<MotRechazoVig> motivosRechazo;
    private static final String EXITO = "Exito";
    private static final int TAMANIO_PAGINAS = 10;

    public RechazarVigilanciasManagedBean() {
        rechazarVigilanciasHelper = new RechazarVigilanciasHelper();
    }

    @PostConstruct
    public void init() throws SGTServiceException {
        obtenerAccesoUsrEmpleados();
        motivosRechazo = motRechazoVigService.todosLosMotivos();
        paginadoVigilanciaEntidadFederativa();
        paginadoVigilanciaAdmonLocal();
        autorizar(ConstantsCatalogos.IDENTIFICADOR_RECHAZO_VIGILANCIA);
    }

    public void paginadoVigilanciaEntidadFederativa() {
        try {
            rechazarVigilanciasHelper.setPaginadorEntidadFederativa(
                    new Paginador(cargaOmisosEntidadFederativaService.obtenerCantidadVigilancias(), TAMANIO_PAGINAS));
            cargarPagina();
            rechazarVigilanciasHelper.setPaginaEntidadFederativa(
                    rechazarVigilanciasHelper.getPaginadorEntidadFederativa().getNumeroPagina());
        } catch (SGTServiceException e) {
            getLogger().error(e.getMessage());
            super.addErrorMessage(e.getMessage(), "");
        }
    }

    public void paginadoVigilanciaAdmonLocal() {
        try {
            rechazarVigilanciasHelper.setPaginadorVigilanciasAdmonLocal(new Paginador(
                    aprobarVigilanciasService.obtenerCantidadVigilanciasAL(), TAMANIO_PAGINAS));
            cargarPaginaAdmonLocal();
            rechazarVigilanciasHelper.setPaginaAdmonLocal(rechazarVigilanciasHelper.getPaginadorVigilanciasAdmonLocal().getNumeroPagina());
        } catch (SGTServiceException e) {
            getLogger().error(e.getMessage());
            super.addErrorMessage(e.getMessage(), "");
        }
    }

    public void rechazarVigilanciasAL() {
        try {
            buscarDescripcionMotivoRechazo();
            rechazarVigilanciasService.rechazar(
                    rechazarVigilanciasHelper.getVigilanciaLocal(),
                    getAccesoUsr().getNumeroEmp());
            cargarPaginaAdmonLocal();
            addMessage(EXITO, "Se rechazo la vigilancia "
                    + rechazarVigilanciasHelper.getVigilanciaLocal().getNumeroCarga()
                    + " de la ALSC " + rechazarVigilanciasHelper.getVigilanciaLocal().getAdministracionLocal());
            rechazarVigilanciasHelper.setVigilanciaLocal(new VigilanciaAdministracionLocal());
            /*Se cargan de nuevo las vigilancias*/
            SegbMovimientoDTO movimiento = generarMovimiento(ConstantsCatalogos.IDENTIFICADOR_RECHAZO_VIGILANCIA,
                    ConstantsCatalogos.MODIFICACION_RECHAZO_OMISOS,
                    ConstantsCatalogos.MODIFICACION_RECHAZO_OMISOS_DESC);
            movimientosService.registrarMovimiento(movimiento);
        } catch (SGTServiceException e) {
            addErrorMessage(e.getMessage(), e.getMessage());
        }
    }

    private void buscarDescripcionMotivoRechazo() {
        for (MotRechazoVig r : getMotivosRechazo()) {
            if (r.getIdMotivoRechazoVig().equals(
                    rechazarVigilanciasHelper.
                    getVigilanciaLocal().
                    getMotivoRechazoVigilancia().
                    getIdMotivoRechazoVig())) {
                rechazarVigilanciasHelper.
                        getVigilanciaLocal().
                        getMotivoRechazoVigilancia().setNombre(r.getNombre());
            }
        }
    }

    public void rechazarVigilanciasEntidadFederativa() {
        SegbMovimientoDTO dto = null;
        try {
            dto = MovimientoUtil.addMovimiento(getSession(),
                    ConstantsCatalogos.CVE_SISTEMA,
                    ConstantsCatalogos.IDENTIFICADOR_CARGA_OMISOS_EF,
                    new Date(), new Date(),
                    ConstantsCatalogos.RECHAZO_OMISOS_EF,
                    ConstantsCatalogos.RECHAZO_VIGILANCIA_OMISOS_EF);
        } catch (AccesoDenegadoException e) {
            getLogger().error(e.getMessage(), e);
        }
        try {
            cargaOmisosEntidadFederativaService.guardarRechazo(
                    rechazarVigilanciasHelper.getVigilanciaRechazada(), dto, getAccesoUsr().getNumeroEmp());
            cargarPagina();
            addMessage(EXITO, "Se rechazo la vigilancia: "
                    + rechazarVigilanciasHelper.getVigilanciaRechazada().getIdVigilancia()
                    + " de la Entidad Federativa: "
                    + rechazarVigilanciasHelper.getVigilanciaRechazada().getNombreEntidadFederativa());
        } catch (SGTServiceException e) {
            addErrorMessage(e.getMessage(), "");
        }
    }

    private void cargarPagina() throws SGTServiceException {
        this.getLogger().info("comenzando a traer las entidades");
        rechazarVigilanciasHelper.setVigilanciasEntidadFederativa(
                cargaOmisosEntidadFederativaService.obtenerVigilanciasPaginadas(rechazarVigilanciasHelper.getPaginadorEntidadFederativa()));
    }

    private void mensajeError(String mensaje) {
        addErrorMessage("Error", mensaje);
    }

    public void siguiente() {
        try {
            if (rechazarVigilanciasHelper.getPaginadorEntidadFederativa().next()) {
                cargarPagina();
            }
        } catch (SGTServiceException e) {
            rechazarVigilanciasHelper.getPaginadorEntidadFederativa().back();
            mensajeError(e.getMessage());
        }
        rechazarVigilanciasHelper.setPaginaEntidadFederativa(rechazarVigilanciasHelper.getPaginadorEntidadFederativa().getNumeroPagina());
    }

    public void anterior() {
        try {
            if (rechazarVigilanciasHelper.getPaginadorEntidadFederativa().back()) {
                cargarPagina();
            }
        } catch (SGTServiceException e) {
            rechazarVigilanciasHelper.getPaginadorEntidadFederativa().next();
            mensajeError(e.getMessage());
        }
        rechazarVigilanciasHelper.setPaginaEntidadFederativa(rechazarVigilanciasHelper.getPaginadorEntidadFederativa().getNumeroPagina());
    }

    public void irA() {
        long buffer = rechazarVigilanciasHelper.getPaginadorEntidadFederativa().getNumeroPagina();
        try {
            if (rechazarVigilanciasHelper.getPaginadorEntidadFederativa().goTo(rechazarVigilanciasHelper.getPaginaEntidadFederativa())) {
                cargarPagina();
            }
        } catch (SGTServiceException e) {
            rechazarVigilanciasHelper.getPaginadorEntidadFederativa().goTo(buffer);
            mensajeError(e.getMessage());
        }
        rechazarVigilanciasHelper.setPaginaEntidadFederativa(rechazarVigilanciasHelper.getPaginadorEntidadFederativa().getNumeroPagina());
    }

    private void cargarPaginaAdmonLocal() throws SGTServiceException {
        this.getLogger().info("comenzando a traer las entidades");
        rechazarVigilanciasHelper.setVigilanciasAdmonLocal(
                aprobarVigilanciasService.obtenerVigilanciasPaginadas(rechazarVigilanciasHelper.getPaginadorVigilanciasAdmonLocal()));
    }

    private void mensajeErrorAdmonLocal(String mensaje) {
        addErrorMessage("Error", mensaje);
    }

    public void siguienteAdmonLocal() {
        try {
            if (rechazarVigilanciasHelper.getPaginadorVigilanciasAdmonLocal().next()) {
                cargarPaginaAdmonLocal();
            }
        } catch (SGTServiceException e) {
            rechazarVigilanciasHelper.getPaginadorVigilanciasAdmonLocal().back();
            mensajeErrorAdmonLocal(e.getMessage());
        }
        rechazarVigilanciasHelper.setPaginaAdmonLocal(rechazarVigilanciasHelper.getPaginadorVigilanciasAdmonLocal().getNumeroPagina());
    }

    public void anteriorAdmonLocal() {
        try {
            if (rechazarVigilanciasHelper.getPaginadorVigilanciasAdmonLocal().back()) {
                cargarPaginaAdmonLocal();
            }
        } catch (SGTServiceException e) {
            rechazarVigilanciasHelper.getPaginadorVigilanciasAdmonLocal().next();
            mensajeErrorAdmonLocal(e.getMessage());
        }
        rechazarVigilanciasHelper.setPaginaAdmonLocal(rechazarVigilanciasHelper.getPaginadorVigilanciasAdmonLocal().getNumeroPagina());
    }

    public void irAAdmonLocal() {
        long buffer = rechazarVigilanciasHelper.getPaginadorVigilanciasAdmonLocal().getNumeroPagina();
        try {
            if (rechazarVigilanciasHelper.getPaginadorVigilanciasAdmonLocal().goTo(rechazarVigilanciasHelper.getPaginaAdmonLocal())) {
                cargarPaginaAdmonLocal();
            }
        } catch (SGTServiceException e) {
            rechazarVigilanciasHelper.getPaginadorVigilanciasAdmonLocal().goTo(buffer);
            mensajeErrorAdmonLocal(e.getMessage());
        }
        rechazarVigilanciasHelper.setPaginaAdmonLocal(rechazarVigilanciasHelper.getPaginadorVigilanciasAdmonLocal().getNumeroPagina());
    }

    public RechazarVigilanciasHelper getRechazarVigilanciasHelper() {
        return rechazarVigilanciasHelper;
    }

    public List<MotRechazoVig> getMotivosRechazo() {
        return motivosRechazo;
    }
}
