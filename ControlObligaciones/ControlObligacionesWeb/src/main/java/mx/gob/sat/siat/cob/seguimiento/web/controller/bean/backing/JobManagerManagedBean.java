package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.BitacoraEjecucionDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ConsultarBitacoraFiltroDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.JobDTO;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.jobmanager.MonitorService;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import static mx.gob.sat.siat.cob.seguimiento.util.constante.FilesPath.RUTA_ARCHIVO_TMP_TXT;
import mx.gob.sat.siat.cob.seguimiento.utils.AbstractBaseMB;
import mx.gob.sat.siat.controlador.AccesoProceso;
import mx.gob.sat.siat.exception.AccesoDenegadoException;
import mx.gob.sat.siat.exception.AccesoDenegadoFielException;
import mx.gob.sat.siat.exception.SessionRolNullException;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("jobMB")
@Scope("view")
public class JobManagerManagedBean extends AbstractBaseMB {

    @Autowired
    private MonitorService monitorService;
    private List<JobDTO> job;
    private List<BitacoraEjecucionDTO> bitacora;
    private JobDTO elementoSeleccionado;
    private boolean mostrarDetalleTabla;
    private boolean mostrarTabla;
    private Date fechaDe;
    private Date fechaA;
    private ConsultarBitacoraFiltroDTO consultaBitacora = new ConsultarBitacoraFiltroDTO();
    private JobManagerAdministratorManagedBean jobManagerAdministrador;

    @PostConstruct
    public void init() {

        try {
            super.obtenerAccesoUsrEmpleados();
            autorizar(ConstantsCatalogos.IDENTIFICADOR_MONITOREO);
            job = monitorService.consultarMonitor();
            job = monitorService.obtenerFechaYHora(job);
            setMostrarTabla(true);
            setMostrarDetalleTabla(false);
            fechaDe = getfechaActual();
            fechaA = getfechaActual();

        } catch (SeguimientoDAOException e) {
            redirigir(ConstantsCatalogos.URL_EN_CONSTRUCCION);
            getLogger().error(e.getMessage());
        } catch (ParseException p) {
            redirigir(ConstantsCatalogos.URL_EN_CONSTRUCCION);
            getLogger().error(p.getMessage());
        }

    }

    @Override
    public void autorizar(String identificador) {
        try {
            if (AccesoProceso.validaAccesoProceso(getSession(),
                    identificador,
                    ConstantsCatalogos.PARAMETRO_FIEL)) {
                getLogger().info("Autorizado para este contenido!");
            } else {
                redirecionarPaginaError(new AccesoDenegadoException("No se pudo autorizar"));
            }
        } catch (SessionRolNullException ex) {
            getSession().setAttribute("mensaje", ex.getMessage());
            redirigir(ConstantsCatalogos.URL_EN_CONSTRUCCION);
            getLogger().error(ex);
        } catch (AccesoDenegadoException ex) {
            getSession().setAttribute("mensaje", ex.getMessage());
            redirigir(ConstantsCatalogos.URL_EN_CONSTRUCCION);
            getLogger().error(ex);
        } catch (AccesoDenegadoFielException ex) {
            getSession().setAttribute("mensaje", ex.getMessage());
            redirigir(ConstantsCatalogos.URL_EN_CONSTRUCCION);
            getLogger().error(ex);
        }
    }

    public void consultaBitacora() {
        try {
            ConsultarBitacoraFiltroDTO dto = new ConsultarBitacoraFiltroDTO();
            dto.setIdProceso(elementoSeleccionado.getId());
            setFechaDe(getfechaActual());
            setFechaA(getfechaActual());
            dto.setFechaInicioA(getfechaActual());
            dto.setFechaInicioDe(getfechaActual());
            bitacora = monitorService.consultarBitacora(dto);
            bitacora = monitorService.obtenerFechaYHoraDetalle(bitacora);
            setMostrarTabla(false);
            setMostrarDetalleTabla(true);

        } catch (Exception e) {
            AbstractBaseMB.msgError(ConstantsCatalogos.ERROR_CONSULTA_DB);
            getLogger().error(e.getMessage());
        }
    }

    public void redirigirEditar() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("jobManagerAdministrator.jsf?idProceso=" + elementoSeleccionado.getId().toString());
    }

    public void redirigirAgregar() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("jobManagerAdministrator.jsf");
    }

    public void consultaBitacoraPorFecha() {
        try {
            consultaBitacora.setIdProceso(elementoSeleccionado.getId());
            consultaBitacora.setFechaInicioA(fechaA);
            consultaBitacora.setFechaInicioDe(fechaDe);
            bitacora = monitorService.consultarBitacora(consultaBitacora);
            bitacora = monitorService.obtenerFechaYHoraDetalle(bitacora);
        } catch (Exception e) {
            getLogger().error(e.getMessage());
        }
    }

    public StreamedContent getManual() {
        InputStream is = null;
        try {
            is = this.getClass().getClassLoader().getResourceAsStream(("documentation/MAU_JOBMANAGER.pdf"));
            super.msgInfo("Transacción completada correctamente");
        } catch (Exception ex) {
            super.msgError("No se localizar el archivo");
        }
        return new DefaultStreamedContent(is, "application/pdf", "MAU_JOBMANAGER.pdf");
    }

    public StreamedContent getTxt() {
        InputStream is = null;
        try {
            String nameFile = monitorService.exportFileTxt();
            is = new FileInputStream(RUTA_ARCHIVO_TMP_TXT + nameFile);
            super.msgInfo("Transacción completada correctamente");
        } catch (Exception ex) {
            super.msgError("No se localizar el archivo");
            return null;
        }
        return new DefaultStreamedContent(is, "text/plain", "jobManagerExport.txt");
    }

    public void regresar() {
        setMostrarTabla(true);
        setMostrarDetalleTabla(false);
        actualizar();
    }

    public void actualizar() {
        try {
            job = monitorService.consultarMonitor();
            job = monitorService.obtenerFechaYHora(job);
            setMostrarTabla(true);
            setMostrarDetalleTabla(false);

        } catch (Exception e) {

            getLogger().error(e.getMessage());
        }
    }

    public static Date getPrimerDiaDelMes() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    public static Date getfechaActual() {
        Calendar cal = Calendar.getInstance();

        return cal.getTime();
    }

    public void setMonitorService(MonitorService monitorService) {
        this.monitorService = monitorService;
    }

    public MonitorService getMonitorService() {
        return monitorService;
    }

    public void setJob(List<JobDTO> job) {
        this.job = job;
    }

    public List<JobDTO> getJob() {
        return job;
    }

    public void setBitacora(List<BitacoraEjecucionDTO> bitacora) {
        this.bitacora = bitacora;
    }

    public List<BitacoraEjecucionDTO> getBitacora() {
        return bitacora;
    }

    public void setElementoSeleccionado(JobDTO elementoSeleccionado) {
        this.elementoSeleccionado = elementoSeleccionado;
    }

    public JobDTO getElementoSeleccionado() {
        return elementoSeleccionado;
    }

    public void setMostrarDetalleTabla(boolean mostrarDetalleTabla) {
        this.mostrarDetalleTabla = mostrarDetalleTabla;
    }

    public boolean isMostrarDetalleTabla() {
        return mostrarDetalleTabla;
    }

    public void setMostrarTabla(boolean mostrarTabla) {
        this.mostrarTabla = mostrarTabla;
    }

    public boolean isMostrarTabla() {
        return mostrarTabla;
    }

    public void setFechaDe(Date fechaDe) {
        if (fechaDe != null) {
            this.fechaDe = (Date) fechaDe.clone();
        }
    }

    public Date getFechaDe() {
        if (fechaDe != null) {
            return (Date) fechaDe.clone();
        }
        return null;
    }

    public void setFechaA(Date fechaA) {
        if (fechaA != null) {
            this.fechaA = (Date) fechaA.clone();
        }
    }

    public Date getFechaA() {
        if (fechaA != null) {
            return (Date) fechaA.clone();
        }
        return null;
    }

    public void setConsultaBitacora(ConsultarBitacoraFiltroDTO consultaBitacora) {
        this.consultaBitacora = consultaBitacora;
    }

    public ConsultarBitacoraFiltroDTO getConsultaBitacora() {
        return consultaBitacora;
    }

    public void setJobManagerAdministrador(JobManagerAdministratorManagedBean jobManagerAdministrador) {
        this.jobManagerAdministrador = jobManagerAdministrador;
    }

    public JobManagerAdministratorManagedBean getJobManagerAdministrador() {
        return jobManagerAdministrador;
    }
}
