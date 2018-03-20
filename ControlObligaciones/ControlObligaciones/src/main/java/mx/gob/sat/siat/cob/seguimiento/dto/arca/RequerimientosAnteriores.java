package mx.gob.sat.siat.cob.seguimiento.dto.arca;

import java.io.Serializable;

/**
 * class RequerimientosAnteriores
 *
 * Clase que almacena los requerimiento anteriores, donde esta el padre del
 * requerimiento.
 *
 * @author Agustin Romero Mata - Softtek
 */
public class RequerimientosAnteriores implements Serializable{
    private static final long serialVersionUID = -5197691180506547942L;
    private String idDocumento;
    private String idDocumentoPadre;
    private String descrTipoRequ;
    private String fechaNotificacion;
    private String descrObligacion;
    private String descrPeriodo;
    private int ejercicio;
    private String fechaPresentacionObligacion;
    private String fechaVencimientoReq;
    private String edoObligacionVencimiento;
    private String tipoDocumento;

    /**
     * Constructor default.
     */
    public RequerimientosAnteriores() {
        super();
    }

    public String getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(String idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getIdDocumentoPadre() {
        return idDocumentoPadre;
    }

    public void setIdDocumentoPadre(String idDocumentoPadre) {
        this.idDocumentoPadre = idDocumentoPadre;
    }

    public String getDescrTipoRequ() {
        return descrTipoRequ;
    }

    public void setDescrTipoRequ(String descrTipoRequ) {
        this.descrTipoRequ = descrTipoRequ;
    }

    public String getFechaNotificacion() {
        return fechaNotificacion;
    }

    public void setFechaNotificacion(String fechaNotificacion) {
        this.fechaNotificacion = fechaNotificacion;
    }

    public String getDescrObligacion() {
        return descrObligacion;
    }

    public void setDescrObligacion(String descrObligacion) {
        this.descrObligacion = descrObligacion;
    }

    public String getDescrPeriodo() {
        return descrPeriodo;
    }

    public void setDescrPeriodo(String descrPeriodo) {
        this.descrPeriodo = descrPeriodo;
    }

    public int getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(int ejercicio) {
        this.ejercicio = ejercicio;
    }

    public String getFechaPresentacionObligacion() {
        return fechaPresentacionObligacion;
    }

    public void setFechaPresentacionObligacion(String fechaPresentacionObligacion) {
        this.fechaPresentacionObligacion = fechaPresentacionObligacion;
    }

    public String getFechaVencimientoReq() {
        return fechaVencimientoReq;
    }

    public void setFechaVencimientoReq(String fechaVencimientoReq) {
        this.fechaVencimientoReq = fechaVencimientoReq;
    }

    public String getEdoObligacionVencimiento() {
        return edoObligacionVencimiento;
    }

    public void setEdoObligacionVencimiento(String edoObligacionVencimiento) {
        this.edoObligacionVencimiento = edoObligacionVencimiento;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
}
