/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DocumentoAprobar;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaAprobar;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.aprobacion.AprobarVigilanciaDelegateService;
import mx.gob.sat.siat.cob.seguimiento.service.aprobacion.AprobarVigilanciasService;
import mx.gob.sat.siat.cob.seguimiento.service.aprobacion.RechazarVigilanciasService;
import mx.gob.sat.siat.cob.seguimiento.service.aprobacion.impl.ValidacionCumplimientoThread;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import mx.gob.sat.siat.cob.seguimiento.utils.AbstractBaseMB;
import mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.helper.AprobarDocumentosHelper;
import mx.gob.sat.siat.cob.seguimiento.web.util.MessageFasces;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author root
 */
@Controller("aprobarDocumentosMB")
@Scope("view")
public class AprobarDocumentosManagedBean extends AbstractBaseMB {

    @Autowired
    private transient AprobarVigilanciasService aprobarVigilanciasService;
    @Autowired
    private transient RechazarVigilanciasService rechazarVigilanciasService;
    @Autowired
    private transient ApplicationContext applicationContext;
    @Autowired
    private AprobarVigilanciaDelegateService aprobarVigilanciaDelegateServiceImpl;
    private AprobarDocumentosHelper aprobarDocumentosHelper;
    private transient ValidacionCumplimientoThread validacionCumplimientoThread;
    private static final String ERROR = "ERROR";
    private static final String INFORMACION = "Información";
    private static final int TAMANIO_PAGINAS = 20;
    @SuppressWarnings("compatibility:-1737564122481946159")
    private static final long serialVersionUID = 301L;

    public AprobarDocumentosManagedBean() {
        aprobarDocumentosHelper = new AprobarDocumentosHelper();
    }

    @PostConstruct
    public void init() {
        obtenerAccesoUsrEmpleados();
        try {
//            autorizar(ConstantsCatalogos.IDENTIFICADOR_VIGILANCIA);
            HttpSession session = getSession();
            VigilanciaAprobar v = (VigilanciaAprobar) session.getAttribute("vigilancia");
            MessageFasces mf = (MessageFasces) session.getAttribute("messageFasces");
            session.removeAttribute("vigilancia");
            aprobarDocumentosHelper.setVigilanciaAprobar(v);
            aprobarDocumentosHelper.setPaginador(
                    aprobarVigilanciasService.crearPaginador(v.getNumeroCarga(),
                            getAccesoUsr().getNumeroEmp(), 20));
            aprobarDocumentosHelper.setDocumentos(
                    aprobarVigilanciasService.
                    listarDocumentosPorAprobar(
                            v.getNumeroCarga(),
                            aprobarDocumentosHelper.getPaginador(), getAccesoUsr().getNumeroEmp(),
                            aprobarDocumentosHelper.getFiltroRFC()));
            aprobarDocumentosHelper.setPagina(
                    aprobarDocumentosHelper.getPaginador().getNumeroPagina());
            if (mf != null) {
                session.removeAttribute("messageFasces");
                setMessageFasces(mf);
            }
        } catch (SGTServiceException ex) {
            addErrorMessage(ERROR, ex.getMessage());
        }
    }

    public void cargarTodos() {
        try {
            aprobarDocumentosHelper.setFiltroRFC(null);
            aprobarDocumentosHelper.setPaginador(
                    aprobarVigilanciasService.crearPaginador(aprobarDocumentosHelper.getVigilanciaAprobar().getNumeroCarga(),
                            getAccesoUsr().getNumeroEmp(), 20));
            aprobarDocumentosHelper.setDocumentos(
                    aprobarVigilanciasService.
                    listarDocumentosPorAprobar(
                            aprobarDocumentosHelper.getVigilanciaAprobar().getNumeroCarga(),
                            aprobarDocumentosHelper.getPaginador(), getAccesoUsr().getNumeroEmp(), aprobarDocumentosHelper.getFiltroRFC()));
            aprobarDocumentosHelper.setPagina(
                    aprobarDocumentosHelper.getPaginador().getNumeroPagina());
        } catch (SGTServiceException ex) {
            addErrorMessage(ERROR, ex.getMessage());
        }
    }

    public void filtroRFC() {

        try {

            if (aprobarDocumentosHelper.getFiltroRFC() == null || aprobarDocumentosHelper.getFiltroRFC().equals("")) {
                aprobarDocumentosHelper.setPaginador(
                        aprobarVigilanciasService.crearPaginador(aprobarDocumentosHelper.getVigilanciaAprobar().getNumeroCarga(),
                                getAccesoUsr().getNumeroEmp(), 20));
                aprobarDocumentosHelper.setDocumentos(
                        aprobarVigilanciasService.
                        listarDocumentosPorAprobar(
                                aprobarDocumentosHelper.getVigilanciaAprobar().getNumeroCarga(),
                                aprobarDocumentosHelper.getPaginador(), getAccesoUsr().getNumeroEmp(), aprobarDocumentosHelper.getFiltroRFC()));
            } else {
                aprobarDocumentosHelper.setPaginador(
                        aprobarVigilanciasService.crearPaginador(aprobarDocumentosHelper.getVigilanciaAprobar().getNumeroCarga(),
                                getAccesoUsr().getNumeroEmp(), TAMANIO_PAGINAS, aprobarDocumentosHelper.getFiltroRFC()));
                aprobarDocumentosHelper.setDocumentos(
                        aprobarVigilanciasService.
                        listarDocumentosPorAprobar(
                                aprobarDocumentosHelper.getVigilanciaAprobar().getNumeroCarga(),
                                aprobarDocumentosHelper.getPaginador(), getAccesoUsr().getNumeroEmp(), aprobarDocumentosHelper.getFiltroRFC()));
            }

            aprobarDocumentosHelper.setPagina(
                    aprobarDocumentosHelper.getPaginador().getNumeroPagina());
        } catch (SGTServiceException ex) {
            addErrorMessage(ERROR, ex.getMessage());
        }
    }

    public void refresh() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("aprobarDocumentos.jsf");
    }

    public void validarCumplimientosThread() {
        try {
            validacionCumplimientoThread = applicationContext.getBean(ValidacionCumplimientoThread.class);
            validacionCumplimientoThread.setParametros(getAccesoUsr().getNumeroEmp(),
                    aprobarDocumentosHelper.getVigilanciaAprobar().getNumeroCarga(),
                    aprobarDocumentosHelper.getProgressValidacion(),
                    aprobarDocumentosHelper.getVigilanciaAprobar().getDescripcionVigilancia());
            validacionCumplimientoThread.start();
        } catch (SGTServiceException e) {
            HttpSession session = getSession();
            MessageFasces mf = new MessageFasces(MessageFasces.TypeMessage.ERROR, e.getMessage());
            session.setAttribute("messageFasces", mf);
            getLogger().error(e.getMessage(), e);
        }
    }

    public void actualizaDetalleDocumento() {
        try {
            for (VigilanciaAprobar vpa : aprobarVigilanciasService.listarVigilanciasPorAprobar(getAccesoUsr().getNumeroEmp())) {
                if (aprobarDocumentosHelper.getVigilanciaAprobar().getNumeroCarga().equals(vpa.getNumeroCarga())) {
                    if (aprobarDocumentosHelper.getVigilanciaAprobar().getFechaCarga().equals(vpa.getFechaCarga())) {
                        aprobarDocumentosHelper.getVigilanciaAprobar().setFechaValidacion(vpa.getFechaValidacion());
                        break;
                    }
                }
            }
        } catch (SGTServiceException ex) {
            getLogger().info(ex.getMessage(), ex);
        }
    }

    public void cargarVigilancia() {
        HttpSession session = getSession();
        session.setAttribute("vigilancia", aprobarDocumentosHelper.getVigilanciaAprobar());
    }

    public void afterValidacionCumplimiento() {
        try {
            HttpSession session = getSession();
            if (validacionCumplimientoThread.getException() == null) {
                aprobarDocumentosHelper.setDocumentos(
                        aprobarVigilanciasService.
                        listarDocumentosPorAprobar(
                                aprobarDocumentosHelper.getVigilanciaAprobar().getNumeroCarga(),
                                aprobarDocumentosHelper.getPaginador(), getAccesoUsr().getNumeroEmp(), aprobarDocumentosHelper.getFiltroRFC()));
                aprobarDocumentosHelper.setPagina(
                        aprobarDocumentosHelper.getPaginador().getNumeroPagina());
                MessageFasces mf = new MessageFasces(MessageFasces.TypeMessage.INFO, "Se validaron los cumplimientos");
                session.setAttribute("messageFasces", mf);
                aprobarDocumentosHelper.setCumplimientosEjecutados(true);
                actualizaDetalleDocumento();
                cargarVigilancia();
                refresh();
            } else {
                cargarVigilancia();
                throw validacionCumplimientoThread.getException();
            }
        } catch (Exception e) {
            MessageFasces mf;
            HttpSession session = getSession();
            if (e.getMessage().equals("Todos los ICEP ya han sido cumplidos o cancelados")) {
                mf = new MessageFasces(MessageFasces.TypeMessage.WARN, e.getMessage());
            } else {
                mf = new MessageFasces(MessageFasces.TypeMessage.ERROR, e.getMessage());
            }
            try {
                session.setAttribute("messageFasces", mf);
                refresh();
            } catch (IOException ex) {
                Logger.getLogger(AprobarDocumentosManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void actualizarExcluidos() {
        for (DocumentoAprobar documento : aprobarDocumentosHelper.getDocumentos()) {
            aprobarDocumentosHelper.getDocumentosSeleccionados().
                    remove(documento);
        }
        for (DocumentoAprobar documento : aprobarDocumentosHelper.getDocumentos()) {
            if (documento.isExcluir() && !documento.isEstadoValido()) {
                aprobarDocumentosHelper.getDocumentosSeleccionados().add(documento);
            }
        }
    }

    public String rechazarDocumentosExcluidos() {
        try {
            rechazarVigilanciasService.
                    rechazarPorNumeroDocumento(
                            aprobarDocumentosHelper.getDocumentosSeleccionados());
        } catch (SGTServiceException e) {
            getLogger().error(e.getMessage());
            addErrorMessage(ERROR, e.getMessage());
        }
        return "aprobarVigilancias";
    }

    public void siguiente() {
        try {
            if (aprobarDocumentosHelper.getPaginador().next()) {
                cargarPagina();
            }
        } catch (SGTServiceException e) {
            aprobarDocumentosHelper.getPaginador().back();
            addErrorMessage(ERROR, e.getMessage());
        }
        aprobarDocumentosHelper.setPagina(aprobarDocumentosHelper.getPaginador().getNumeroPagina());
    }

    public void anterior() {
        try {
            if (aprobarDocumentosHelper.getPaginador().back()) {
                cargarPagina();
            }
        } catch (SGTServiceException e) {
            aprobarDocumentosHelper.getPaginador().next();
            addErrorMessage(ERROR, e.getMessage());
        }
        aprobarDocumentosHelper.setPagina(aprobarDocumentosHelper.getPaginador().getNumeroPagina());
    }

    public void irA() {
        long buffer = aprobarDocumentosHelper.getPaginador().getNumeroPagina();
        try {

            if (aprobarDocumentosHelper.getPaginador().goTo(aprobarDocumentosHelper.getPagina())) {
                cargarPagina();
            }
        } catch (SGTServiceException e) {
            aprobarDocumentosHelper.getPaginador().goTo(buffer);
            addErrorMessage(ERROR, e.getMessage());
        }
        aprobarDocumentosHelper.setPagina(aprobarDocumentosHelper.getPaginador().getNumeroPagina());
    }

    private void cargarPagina() throws SGTServiceException {
        aprobarDocumentosHelper.setDocumentos(
                aprobarVigilanciasService.
                listarDocumentosPorAprobar(
                        aprobarDocumentosHelper.getVigilanciaAprobar().getNumeroCarga(),
                        aprobarDocumentosHelper.getPaginador(), getAccesoUsr().getNumeroEmp(), aprobarDocumentosHelper.getFiltroRFC()));
        cargarExclusiones();
    }

    private void cargarExclusiones() {
        for (DocumentoAprobar documento : aprobarDocumentosHelper.getDocumentos()) {
            if (aprobarDocumentosHelper.getDocumentosSeleccionados().contains(documento)) {
                documento.setExcluir(true);
            }
        }
    }

    public void mostrarDetalle() {
        try {
            aprobarDocumentosHelper.setPaginadorIceps(
                    aprobarVigilanciaDelegateServiceImpl.crearPaginadorIceps(
                            aprobarDocumentosHelper.getVigilanciaAprobar().getNumeroCarga(),
                            getAccesoUsr().getNumeroEmp(), TAMANIO_PAGINAS));
            aprobarDocumentosHelper.setPaginaIceps(
                    aprobarDocumentosHelper.getPaginadorIceps().getNumeroPagina());
            aprobarDocumentosHelper.setIcepsAprobar(
                    aprobarVigilanciasService.listarIcepsPagina(
                            aprobarDocumentosHelper.getVigilanciaAprobar().getNumeroCarga(),
                            aprobarDocumentosHelper.getPaginadorIceps(),
                            getAccesoUsr().getNumeroEmp()));
            if (aprobarDocumentosHelper.getIcepsAprobar().isEmpty()) {
                addWarningMessage(INFORMACION, "Todos los ICEP ya han sido cumplidos o cancelados");
            }
        } catch (SGTServiceException e) {
            addErrorMessage(ERROR, e.getMessage());
        }
    }

    public StreamedContent getCsv() {
        StreamedContent sc = null;
        try {
            sc = new DefaultStreamedContent(
                    aprobarVigilanciasService.generarArchivoIceps(
                            aprobarDocumentosHelper.getVigilanciaAprobar().getNumeroCarga(),
                            getAccesoUsr().getNumeroEmp(),
                            aprobarDocumentosHelper.getProgress()),
                    "text/plain",
                    "vigilancia.txt", "UTF-8");
        } catch (SGTServiceException e) {
            addErrorMessage(ERROR, e.getMessage());
        }
        return sc;
    }

    public void siguienteIceps() {
        try {
            if (aprobarDocumentosHelper.getPaginadorIceps().next()) {
                cargarPaginaIceps();
            }
        } catch (SGTServiceException e) {
            aprobarDocumentosHelper.getPaginadorIceps().back();
            addErrorMessage(ERROR, e.getMessage());
        }
        aprobarDocumentosHelper.setPaginaIceps(aprobarDocumentosHelper.getPaginadorIceps().getNumeroPagina());
    }

    public void anteriorIceps() {
        try {
            if (aprobarDocumentosHelper.getPaginadorIceps().back()) {
                cargarPaginaIceps();
            }
        } catch (SGTServiceException e) {
            aprobarDocumentosHelper.getPaginadorIceps().next();
            addErrorMessage(ERROR, e.getMessage());
        }
        aprobarDocumentosHelper.setPaginaIceps(aprobarDocumentosHelper.getPaginadorIceps().getNumeroPagina());
    }

    public void irAIceps() {
        long buffer = aprobarDocumentosHelper.getPaginadorIceps().getNumeroPagina();
        try {

            if (aprobarDocumentosHelper.getPaginadorIceps().goTo(aprobarDocumentosHelper.getPaginaIceps())) {
                cargarPaginaIceps();
            }
        } catch (SGTServiceException e) {
            aprobarDocumentosHelper.getPaginadorIceps().goTo(buffer);
            addErrorMessage(ERROR, e.getMessage());
        }
        aprobarDocumentosHelper.setPaginaIceps(aprobarDocumentosHelper.getPaginadorIceps().getNumeroPagina());
    }

    private void cargarPaginaIceps() throws SGTServiceException {
        aprobarDocumentosHelper.setIcepsAprobar(
                aprobarVigilanciasService.listarIcepsPagina(
                        aprobarDocumentosHelper.getVigilanciaAprobar().getNumeroCarga(),
                        aprobarDocumentosHelper.getPaginadorIceps(),
                        getAccesoUsr().getNumeroEmp()));
    }

    public void regresarDocumentos() {
        aprobarDocumentosHelper.getIcepsAprobar().clear();
    }

    public AprobarDocumentosHelper getAprobarDocumentosHelper() {
        return aprobarDocumentosHelper;
    }

    public void setAprobarDocumentosHelper(AprobarDocumentosHelper aprobarDocumentosHelper) {
        this.aprobarDocumentosHelper = aprobarDocumentosHelper;
    }
}
