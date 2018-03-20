package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import mx.gob.sat.siat.cob.seguimiento.dto.report.enums.FacturaVigilanciaAprobadaEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ParametrosValidacionCumplimiento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaAdministracionLocal;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaAprobar;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciasLogDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoServiciosConcurrentesEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.aprobacion.AprobarVigilanciasService;
import mx.gob.sat.siat.cob.seguimiento.service.aprobacion.impl.AprobarVigilanciasThread;
import mx.gob.sat.siat.cob.seguimiento.service.otros.ConcurrenceService;
import mx.gob.sat.siat.cob.seguimiento.service.otros.MovimientosService;
import static mx.gob.sat.siat.cob.seguimiento.util.MensajesError.ERR_APROBAR_VIGILANCIA;
import static mx.gob.sat.siat.cob.seguimiento.util.MensajesError.ERR_ENVIO_CORREO;
import static mx.gob.sat.siat.cob.seguimiento.util.MensajesError.ERR_GENERAR_FACTURA;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import mx.gob.sat.siat.cob.seguimiento.utils.AbstractBaseMB;
import mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.helper.AprobarVigilanciasHelper;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;
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
@Controller("aprobarVigilancias")
@Scope("view")
public class AprobarVigilanciasManagedBean extends AbstractBaseMB {

    @Autowired
    private AprobarVigilanciasService aprobarVigilanciasService;
    @Autowired
    private MovimientosService movimientosService;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private ConcurrenceService concurrenceService;
    private AprobarVigilanciasHelper aprobarVigilanciasHelper;
    private String identificador;
    private boolean isInfoSystem;
    private static final String ERROR = "ERROR";
    private static final String EXITO = "Exito";

    private String numEmp;
    private String rfcEmp;

    /**
     *
     */
    public AprobarVigilanciasManagedBean() {
        aprobarVigilanciasHelper = new AprobarVigilanciasHelper();
    }

    /**
     *
     */
    @PostConstruct
    public void init() {
        obtenerAccesoUsrEmpleados();
        identificador = ConstantsCatalogos.IDENTIFICADOR_VIGILANCIA;
        autorizar(identificador);
    }

    private void desbloqueaFirma() throws SGTServiceException {
        String firma = getSession().getAttribute("datosFirma").toString();
        getLogger().info("Desbloqueando la firma " + firma);
        if (!concurrenceService.unlockServices(TipoServiciosConcurrentesEnum.IDENTIFICADOR_VIGILANCIA_DOCUMENTOS, firma)) {
            throw new SGTServiceException("No se pudo hacer el desbloqueo de este proceso.");
        }
    }

    /**
     *
     * @param vigilanciaAprobar
     */
    public void setVigilanciaAprobar(VigilanciaAprobar vigilanciaAprobar) {
        getSession().setAttribute("vigilancia", vigilanciaAprobar);
    }

    /**
     *
     */
    public void cargarVigilanciasAprobar() {
        try {
            cargarVigilanciasConProceso();
            cargaLstErrores();
        } catch (SGTServiceException ex) {
            addErrorMessage(ERROR, ex.getMessage());
        }
    }

    private void cargarVigilanciasConProceso() throws SGTServiceException {
        setNumEmp(getAccesoUsr().getNumeroEmp());
        setRfcEmp(getAccesoUsr().getUsuario());
        aprobarVigilanciasHelper.setVigilancias(
                aprobarVigilanciasService.
                listarVigilanciasPorAprobar(getAccesoUsr().getNumeroEmp()));
        List<VigilanciaAprobar> vigilanciasEnProceso = new CopyOnWriteArrayList<VigilanciaAprobar>();

        for (VigilanciaAprobar v : aprobarVigilanciasHelper.getVigilancias()) {
            ParametrosValidacionCumplimiento p
                    = new ParametrosValidacionCumplimiento(getAccesoUsr().getNumeroEmp(), v.getNumeroCarga());
            if (aprobarVigilanciasService.getParametrosProcesoAprobacion().contains(p)) {
                vigilanciasEnProceso.add(v);
            }
        }
        aprobarVigilanciasHelper.getVigilancias().removeAll(vigilanciasEnProceso);
    }


    /**
     *
     */
    public void factura() {
        InputStream is = null;
        byte[] bytes = null;
        AprobarVigilanciasThread thread = null;
        try {
            thread = applicationContext.getBean(AprobarVigilanciasThread.class);
            thread.establecerValoresEjecucion(aprobarVigilanciasHelper.getVigilancia(),
                    getAccesoUsr().getNumeroEmp(),
                    aprobarVigilanciasHelper.getProgress());
            thread.start();
            synchronized (aprobarVigilanciasHelper.getProgress()) {
                try {
                    while (aprobarVigilanciasHelper.getProgress().getValor() < AprobarVigilanciasService.PORCENTAJE_TOTAL) {
                        aprobarVigilanciasHelper.getProgress().wait();
                    }
                } catch (InterruptedException e) {
                    getLogger().error(e);
                }
            }
            if (thread.getException() != null) {
                throw thread.getException();
            }
           SegbMovimientoDTO movimiento = generarMovimiento(identificador,
                   ConstantsCatalogos.MODIFICACION_ACEPTACION_OMISOS,
                   ConstantsCatalogos.MODIFICACION_APROBAR_OMISOS_DESC);
           movimientosService.registrarMovimiento(movimiento);
            thread=null;
            is = new FileInputStream(AprobarVigilanciasService.PATH
                    + FacturaVigilanciaAprobadaEnum.NOMBRE_ARCHIVO
                    + aprobarVigilanciasHelper.getVigilancia().getNumeroCarga()
                    + getAccesoUsr().getNumeroEmp()
                    + FacturaVigilanciaAprobadaEnum.EXTENSION);
            String numCarga=aprobarVigilanciasHelper.getVigilancia().getNumeroCarga();
            addMessage(EXITO, "La vigilancia "
                    + aprobarVigilanciasHelper.getVigilancia().getNumeroCarga()
                    + " fue aprobada");
            aprobarVigilanciasHelper.setVigilancia(new VigilanciaAdministracionLocal());
            /*Se cargan de nuevo las vigilancias*/
            if (is != null) {
                getSession().setAttribute("nameFactura", (AprobarVigilanciasService.PATH
                    + FacturaVigilanciaAprobadaEnum.NOMBRE_ARCHIVO
                    + numCarga
                    + getAccesoUsr().getNumeroEmp()
                    + FacturaVigilanciaAprobadaEnum.EXTENSION));
                FacesContext.getCurrentInstance().getExternalContext().redirect("downloadFacturaMultimedia.jsf");
            }
            cargarVigilanciasConProceso();
        } catch (SGTServiceException e) {
            try{
                if(e.getCause() instanceof MessagingException ){
                    aprobarVigilanciasService.guardaErrorAprobacion(getAccesoUsr().getNumeroEmp(),aprobarVigilanciasHelper.getVigilancia(),ERR_ENVIO_CORREO);
                    getLogger().error(ERR_ENVIO_CORREO,e);
                }else{
                    aprobarVigilanciasService.guardaErrorAprobacion(getAccesoUsr().getNumeroEmp(),aprobarVigilanciasHelper.getVigilancia(),ERR_ENVIO_CORREO);
                    getLogger().error(e);
                }
                reloadPage();
                aprobarVigilanciasService.guardaErrorAprobacion(getAccesoUsr().getNumeroEmp(),aprobarVigilanciasHelper.getVigilancia(),ERR_APROBAR_VIGILANCIA);
                addErrorMessage(ERROR, ERR_APROBAR_VIGILANCIA);
            }catch(IOException ex){
                getLogger().error("No se pudo cargar la pagina correctamente",ex);
            }
        } catch (FileNotFoundException ex) {
            aprobarVigilanciasService.guardaErrorAprobacion(getAccesoUsr().getNumeroEmp(),aprobarVigilanciasHelper.getVigilancia(),ERR_GENERAR_FACTURA);
            addErrorMessage(ERROR, ERR_GENERAR_FACTURA);
            try{
            reloadPage();
            }catch(IOException e){
                getLogger().error("No se pudo cargar la pagina correctamente",ex);
            }
        } catch (IOException ex) {
            getLogger().error("No se pudo leer archivo",ex);
        } finally{
            try {
                if(is!=null){
                    is.close();
                }
                desbloqueaFirma();
            } catch (SGTServiceException ex) {
                getLogger().error("Error al desbloquear registro de firmado",ex);
            } catch (IOException e) {
                getLogger().error("Elemento imposible de cerrar InputStream",e);
            }
        }
    }

    /**
     *
     * @return
     */
    public StreamedContent getPlantilla() {
        byte[] archivo = new byte[1];
        try {
            archivo = aprobarVigilanciasService.obtenerPlantilla(aprobarVigilanciasHelper.getVigilancia().getIdPlantilla());
        } catch (SGTServiceException e) {
            addErrorMessage(ERROR, e.getMessage());
        }
        return new DefaultStreamedContent(new ByteArrayInputStream(archivo),
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                "Plantilla.docx", "UTF-8");
    }

    /**
     *
     * @return
     */
    public AprobarVigilanciasHelper getAprobarVigilanciasHelper() {
        return aprobarVigilanciasHelper;
    }

    /**
     *
     * @param aprobarVigilanciasHelper
     */
    public void setAprobarVigilanciasHelper(AprobarVigilanciasHelper aprobarVigilanciasHelper) {
        this.aprobarVigilanciasHelper = aprobarVigilanciasHelper;
    }

    /**
     *
     * @param identificador
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     *
     * @throws IOException
     */
    public void reloadPage() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("aprobarVigilancias.jsf");
    }

    /**
     *
     * @param lstErrores
     */
    public void showMessageError(List<VigilanciasLogDTO> lstErrores) {
        FacesContext context = FacesContext.getCurrentInstance();
        if(lstErrores.size()>0){
            isInfoSystem=true;
        }else{
            isInfoSystem=false;
        }
        for(VigilanciasLogDTO item:lstErrores){
            context.addMessage(ERROR, new FacesMessage("Ocurrió un error al procesar la vigilancia: "+item.getIdVigilancia(),"Causa: "+item.getDescripcion()+"\n"+"Fecha: "+item.getFechaRegistro()));
        }
    }

    /**
     *
     */
    public void cargaLstErrores(){

        List<VigilanciaAdministracionLocal> lstAdmLocal;
        lstAdmLocal = new ArrayList<VigilanciaAdministracionLocal>();
        for (VigilanciaAprobar item : aprobarVigilanciasHelper.getVigilancias()) {
            VigilanciaAdministracionLocal obj = new VigilanciaAdministracionLocal();
            obj.setNumeroCarga(item.getNumeroCarga());
            obj.setIdAdministracionLocal(item.getIdAdministracionLocal());
            obj.setDescripcionVigilancia(item.getDescripcionVigilancia());
            lstAdmLocal.add(obj);
        }

        if((aprobarVigilanciasService.getVigilanciaConError((getAccesoUsr().getNumeroEmp()),lstAdmLocal))!=null && (aprobarVigilanciasService.getVigilanciaConError((getAccesoUsr().getNumeroEmp()),lstAdmLocal)).size()>0){
            showMessageError(aprobarVigilanciasService.getVigilanciaConError((getAccesoUsr().getNumeroEmp()),lstAdmLocal));
        }

    }

    /**
     *
     * @return
     */
    public boolean isIsInfoSystem() {
        return isInfoSystem;
    }

    /**
     *
     * @param isInfoSystem
     */
    public void setIsInfoSystem(boolean isInfoSystem) {
        this.isInfoSystem = isInfoSystem;
    }

    /**
     *
     * @return
     */
    public String getNumEmp() {
        return numEmp;
    }

    /**
     *
     * @param numEmp
     */
    public void setNumEmp(String numEmp) {
        this.numEmp = numEmp;
    }

    /**
     *
     * @return
     */
    public String getRfcEmp() {
        return rfcEmp;
    }

    /**
     *
     * @param rfcEmp
     */
    public void setRfcEmp(String rfcEmp) {
        this.rfcEmp = rfcEmp;
    }

}
