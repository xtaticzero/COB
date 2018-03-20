package mx.gob.sat.siat.cob.seguimiento.dto.catalogos;

import java.io.Serializable;

import java.util.Date;


public class Obligacion implements Serializable{
    private static final long serialVersionUID = -5197691180506547942L;
    
    private Long idObligacion;
    private String concepto;
    private String descripcion;
    private String fundamentoLegal;
    private Date   fechaInicio;
    private Date   fechaFin;
    private Long   orden;
    private ValorBooleano valorBooleano;
    private String descrObliIdc;
    private String fechaFinStr;
    private String descrObliCompleta;
       
    
    /**
     *
     */
    public Obligacion() {
        super();
    }

    /**
     *
     * @param concepto
     */
    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    /**
     *
     * @return
     */
    public String getConcepto() {
        return concepto;
    }

   
    /**
     *
     * @param fundamentoLegal
     */
    public void setFundamentoLegal(String fundamentoLegal) {
        this.fundamentoLegal = fundamentoLegal;
    }

    /**
     *
     * @return
     */
    public String getFundamentoLegal() {
        return fundamentoLegal;
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
     * @param descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     *
     * @return
     */
    public String getDescripcion() {
        return descripcion;
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
     * @param valorBooleano
     */
    public void setValorBooleano(ValorBooleano valorBooleano) {
        this.valorBooleano = valorBooleano;
    }

    /**
     *
     * @return
     */
    public ValorBooleano getValorBooleano() {
        return valorBooleano;
    }


    /**
     *
     * @param descrObliIdc
     */
    public void setDescrObliIdc(String descrObliIdc) {
        this.descrObliIdc = descrObliIdc;
    }

    /**
     *
     * @return
     */
    public String getDescrObliIdc() {
        return descrObliIdc;
    }


    public void setFechaFinStr(String fechaFinStr) {
        this.fechaFinStr = fechaFinStr;
    }

    public String getFechaFinStr() {
        return fechaFinStr;
    }

    public void setDescrObliCompleta(String descrObliCompleta) {
        this.descrObliCompleta = descrObliCompleta;
    }

    public String getDescrObliCompleta() {
        return descrObliCompleta;
    }
}

