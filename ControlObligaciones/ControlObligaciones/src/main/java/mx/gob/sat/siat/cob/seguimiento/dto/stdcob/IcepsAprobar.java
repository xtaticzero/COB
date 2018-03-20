package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author root
 */
public class IcepsAprobar implements Serializable {

    private Date fechaCorte;
    private String rfc;
    private String numeroControl;
    private String claveIcep;
    private String descripcionObligacion;
    private String estadoObligacion;
    private String ejercicio;
    private String periodo;
    /**
     *
     */
    public IcepsAprobar() {
    }

    /**
     *
     * @return
     */
    public Date getFechaCorte() {
        if(fechaCorte!=null){
            return (Date) fechaCorte.clone();
        }else{
            return null;
        }
    }

    /**
     *
     * @param fechaCorte
     */
    public void setFechaCorte(Date fechaCorte) {
        if(fechaCorte!=null){
            this.fechaCorte = (Date)fechaCorte.clone();
        }
    }

    /**
     *
     * @return
     */
    public String getRfc() {
        return rfc;
    }

    /**
     *
     * @param rfc
     */
    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    /**
     *
     * @return
     */
    public String getNumeroControl() {
        return numeroControl;
    }

    /**
     *
     * @param numeroControl
     */
    public void setNumeroControl(String numeroControl) {
        this.numeroControl = numeroControl;
    }

    /**
     *
     * @return
     */
    public String getClaveIcep() {
        return claveIcep;
    }

    /**
     *
     * @param claveIcep
     */
    public void setClaveIcep(String claveIcep) {
        this.claveIcep = claveIcep;
    }

    /**
     *
     * @return
     */
    public String getDescripcionObligacion() {
        return descripcionObligacion;
    }

    /**
     *
     * @param descripcionObligacion
     */
    public void setDescripcionObligacion(String descripcionObligacion) {
        this.descripcionObligacion = descripcionObligacion;
    }

    /**
     * 
     * @return
     */
    public String getEstadoObligacion() {
        return estadoObligacion;
    }

    /**
     *
     * @param estadoObligacion
     */
    public void setEstadoObligacion(String estadoObligacion) {
        this.estadoObligacion = estadoObligacion;
    }

    public String getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(String ejercicio) {
        this.ejercicio = ejercicio;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
    
    

}
