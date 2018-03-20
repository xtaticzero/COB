package mx.gob.sat.siat.cob.seguimiento.dto.catalogos;

import java.io.Serializable;

import java.util.Date;


public class MultaMonto implements Serializable{
    private static final long serialVersionUID = -5197691180506547942L;
   
    private String sancion;
    private String infraccion;
    private Date   fechaInicio;
    private Date   fechaFin;
    private Long idObligacion;
    private Long   orden;
    private Long   idOblisancion;    
    private String  idObligacionDescr;
    private String  resolMotivo;
    private String  resolMotivoDescr;
    private String  descripcionCorta;
    private String  descripcionLarga;
    private String motivacion;
    private Long monto;
    private String descripcionMonto;
    private String fechaInicioStr;
    private String fechaFinStr;

        
    /**
     *
     */
    public MultaMonto() {
        super();
    }

    /**
     *
     * @param sancion
     */
    public void setSancion(String sancion) {
        this.sancion = sancion;
    }

    /**
     *
     * @return
     */
    public String getSancion() {
        return sancion;
    }

    /**
     *
     * @param infraccion
     */
    public void setInfraccion(String infraccion) {
        this.infraccion = infraccion;
    }

    /**
     *
     * @return
     */
    public String getInfraccion() {
        return infraccion;
    }

    /**
     *
     * @param fechaInicio
     */
    public void setFechaInicio(Date fechaInicio) {
        if (fechaInicio != null) {
            this.fechaInicio = (Date) fechaInicio.clone();
        }else{
            this.fechaInicio = null;
        }
    }

    /**
     *
     * @return
     */
    public Date getFechaInicio() {
        if(fechaInicio!=null){
        return (Date) fechaInicio.clone();
        }
        return null;
    }

    /**
     *
     * @param fechaFin
     */
    public void setFechaFin(Date fechaFin) {
        if (fechaFin != null) {
            this.fechaFin = (Date) fechaFin.clone();
        }else{
            this.fechaInicio = null;
        }
    }

    /**
     *
     * @return
     */
    public Date getFechaFin() {
        if(fechaFin!=null){
        return (Date) fechaFin.clone();
        }
        return null;
    }

    /**
     *
     * @param idObligacion
     */
    public void setIdObligacion(Long idObligacion) {
        this.idObligacion = idObligacion;
    }

    /**
     *
     * @return
     */
    public Long getIdObligacion() {
        return idObligacion;
    }
  

    /**
     *
     * @param orden
     */
    public void setOrden(Long orden) {
        this.orden = orden;
    }

    /**
     *
     * @return
     */
    public Long getOrden() {
        return orden;
    }


    /**
     *
     * @param idOblisancion
     */
    public void setIdOblisancion(Long idOblisancion) {
        this.idOblisancion = idOblisancion;
    }

    /**
     *
     * @return
     */
    public Long getIdOblisancion() {
        return idOblisancion;
    }

    /**
     *
     * @param idObligacionDescr
     */
    public void setIdObligacionDescr(String idObligacionDescr) {
        this.idObligacionDescr = idObligacionDescr;
    }

    /**
     *
     * @return
     */
    public String getIdObligacionDescr() {
        return idObligacionDescr;
    }


    public void setResolMotivo(String resolMotivo) {
        this.resolMotivo = resolMotivo;
    }

    public String getResolMotivo() {
        return resolMotivo;
    }

    public void setResolMotivoDescr(String resolMotivoDescr) {
        this.resolMotivoDescr = resolMotivoDescr;
    }

    public String getResolMotivoDescr() {
        return resolMotivoDescr;
    }

    public void setDescripcionCorta(String descripcionCorta) {
        this.descripcionCorta = descripcionCorta;
    }

    public String getDescripcionCorta() {
        return descripcionCorta;
    }

    public void setDescripcionLarga(String descripcionLarga) {
        this.descripcionLarga = descripcionLarga;
    }

    public String getDescripcionLarga() {
        return descripcionLarga;
    }

    public void setMotivacion(String motivacion) {
        this.motivacion = motivacion;
    }

    public String getMotivacion() {
        return motivacion;
    }

    public void setMonto(Long monto) {
        this.monto = monto;
    }

    public Long getMonto() {
        return monto;
    }

    public void setDescripcionMonto(String descripcionMonto) {
        this.descripcionMonto = descripcionMonto;
    }

    public String getDescripcionMonto() {
        return descripcionMonto;
    }

    public String getFechaInicioStr() {
        return fechaInicioStr;
    }

    public void setFechaInicioStr(String fechaInicioStr) {
        this.fechaInicioStr = fechaInicioStr;
    }

    public String getFechaFinStr() {
        return fechaFinStr;
    }

    public void setFechaFinStr(String fechaFinStr) {
        this.fechaFinStr = fechaFinStr;
    }
    
}
