package mx.gob.sat.siat.cob.seguimiento.exception;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.ErrorCampo;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.Notificacion;


public class BusinessException extends Exception {

    @SuppressWarnings("compatibility:7563042107734891314")
    private static final long serialVersionUID = -5030203108332244848L;
    
    private Notificacion notificacion; 
    private List<ErrorCampo> errores;
    
    public BusinessException(Throwable throwable) {
        super(throwable);
    }

    public BusinessException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public BusinessException(String string) {
        super(string);
    }

    public BusinessException() {
        super();
    }

    public BusinessException(Notificacion notificacion, List<ErrorCampo> errores) {
        super();
        this.notificacion = notificacion;
        this.errores = errores;
    }

    public void setNotificacion(Notificacion notificacion) {
        this.notificacion = notificacion;
    }

    public Notificacion getNotificacion() {
        return notificacion;
    }

    public void setErrores(List<ErrorCampo> errores) {
        this.errores = errores;
    }

    public List<ErrorCampo> getErrores() {
        return errores;
    }
}
