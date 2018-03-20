package mx.gob.sat.siat.cob.seguimiento.utils;

/*
 * Clase que se emplea para abstraer la capa de exposicion web.
 * Por el momento solo obtiene el logger, pero se pueden adicionar metodos 
 * o atributos comunes, por ejemplo sesion.
 * 
 */
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import mx.gob.sat.siat.cob.seguimiento.web.util.MessageFasces;
import mx.gob.sat.siat.controlador.AccesoProceso;
import mx.gob.sat.siat.exception.AccesoDenegadoException;
import mx.gob.sat.siat.exception.AccesoDenegadoFielException;
import mx.gob.sat.siat.exception.SessionRolNullException;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;
import mx.gob.sat.siat.util.MovimientoUtil;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Value;

public abstract class AbstractBaseMB implements Serializable {

    private final Logger logger;
    private final ResourceBundle bundle;
    private AccesoUsr accesoUsr;
    private HttpSession session;
    @Value("#{properties['ruta.carpeta.temporal']}")
    private String rutaCarpetaTemporal;

    public AbstractBaseMB() {
        logger = Logger.getLogger(getClass());
        bundle = FacesContext.getCurrentInstance().
                getApplication().getResourceBundle(FacesContext.getCurrentInstance(), "mensajes");
        accesoUsr = new AccesoUsr();
    }

    public HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    public void autorizar(String identificador) {
        try {
            if (AccesoProceso.validaAccesoProceso(getSession(),
                    identificador,
                    ConstantsCatalogos.PARAMETRO_FIEL)) {
                logger.info("Autorizado para este contenido!");
            } else {
                redirecionarPaginaError(new AccesoDenegadoException("No se pudo autorizar"));
            }
        } catch (SessionRolNullException ex) {
            redirecionarPaginaError(ex);
        } catch (AccesoDenegadoException ex) {
            redirecionarPaginaError(ex);
        } catch (AccesoDenegadoFielException ex) {
            redirecionarPaginaError(ex);
        }
    }

    public SegbMovimientoDTO generarMovimiento(String identificador,
            int tipoMovimiento,
            String descripcionMovimiento) {
        try {
            return MovimientoUtil.addMovimiento(getSession(),
                    ConstantsCatalogos.CVE_SISTEMA,
                    identificador,
                    new Date(),
                    new Date(),
                    tipoMovimiento,
                    descripcionMovimiento);
        } catch (AccesoDenegadoException ex) {
            redirecionarPaginaError(ex);
        }
        return null;
    }

    public AccesoUsr obtenerAccesoUsrEmpleados() {
        session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        accesoUsr = (AccesoUsr) session.getAttribute(ConstantsCatalogos.SESSION_EP);
        if (accesoUsr == null) {
            accesoUsr = Utilerias.obtenerAccesoUsrEmpleados();
        }
        return accesoUsr;
    }

    public void redirigir(String url) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().
                    redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+url);
        } catch (IOException ex) {
            logger.error(ex);
        }
    }

    public void redirecionarPaginaError(Exception e) {
        getLogger().error(e);
        getSession().setAttribute("mensaje", e.getMessage());
        redirigir(ConstantsCatalogos.URL_EN_CONSTRUCCION);
    }

    public void redirectErrorPage(Exception e) {
        getLogger().error(e.getMessage());
        getSession().setAttribute("mensaje", e.getMessage());
        try {
            FacesContext.getCurrentInstance().getExternalContext().
                    redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/petnoval500.jsf");
        } catch (IOException ex) {
            logger.error(ex);
        }
    }

    public void despachaArchivo(byte[] archivo, String contentType, String nombreArchivo, String error) {
        try {
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().
                    getExternalContext().getResponse();
            response.setContentType(contentType);
            response.setContentLength(archivo.length);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + nombreArchivo + "\"");

            ServletOutputStream out;
            out = response.getOutputStream();
            out.write(archivo);
            FacesContext.getCurrentInstance().responseComplete();

        } catch (IOException e) {
            getLogger().error(e.getMessage());
            AbstractBaseMB.msgFatal(error);
        }

    }

    public void createMessage(String summary, String detail,
            FacesMessage.Severity severity) {
        FacesMessage message = new FacesMessage(severity, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void addMessage(String titulo, String mensaje) {
        createMessage(titulo, mensaje, FacesMessage.SEVERITY_INFO);
    }

    public void addErrorMessage(String titulo, String mensaje) {
        createMessage(titulo, mensaje, FacesMessage.SEVERITY_ERROR);
    }

    public void addFatalMessage(String titulo, String mensaje) {
        createMessage(titulo, mensaje, FacesMessage.SEVERITY_FATAL);
    }

    public void addWarningMessage(String titulo, String mensaje) {
        createMessage(titulo, mensaje, FacesMessage.SEVERITY_WARN);
    }

    public static void msgError(String msg) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", msg));
    }

    public static void msgInfo(String msg) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", msg));
    }

    public static void msgFatal(String msg) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "", msg));
    }

    public static void msgWarn(String msg) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", msg));
    }

    public String getFileName(String nombreArchivo) {
        StringBuilder name = new StringBuilder();

        name.append(rutaCarpetaTemporal);
        name.append(nombreArchivo);
        name.append(getAccesoUsr().getUsuario());
        name.append(Utilerias.formatearFechaAAAAMMDDHHMMSS(new Date()));
        name.append(".txt");

        return name.toString();
    }
    
    public String getFileNameRFC(String nombreArchivo) {
        StringBuilder name = new StringBuilder();

        name.append(rutaCarpetaTemporal);
        name.append(nombreArchivo);
        name.append(Utilerias.formatearFechaAAAAMMDDHHMMSS(new Date()));
        name.append(getAccesoUsr().getUsuario());
        name.append(".txt");

        return name.toString();
    }
    
    public void setMessageFasces(MessageFasces messageFasces) {
        switch (messageFasces.getTypeMessage()) {
            case INFO:
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", messageFasces.getMessage()));
                break;
            case WARN:
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta!", messageFasces.getMessage()));
                break;
            case ERROR:
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", messageFasces.getMessage()));
                break;
            case FATAL:
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", messageFasces.getMessage()));
                break;
        }
    }
    
    public void setLstMessageFasces(List<MessageFasces> lstMessageFasces){
        for(MessageFasces message:lstMessageFasces){
            setMessageFasces(message);
        }
    }

    public Logger getLogger() {
        return logger;
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    public AccesoUsr getAccesoUsr() {
        return accesoUsr;
    }

    public HttpSession getSession() {
        return session;
    }
}
