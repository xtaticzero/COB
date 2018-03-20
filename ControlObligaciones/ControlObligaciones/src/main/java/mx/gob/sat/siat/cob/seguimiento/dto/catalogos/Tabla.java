package mx.gob.sat.siat.cob.seguimiento.dto.catalogos;


import java.io.Serializable;
import java.util.Date;


public class Tabla implements Serializable{

    @SuppressWarnings("compatibility:1793341050705063049")
    private static final long serialVersionUID = -7623053235630865108L;
    /**
     *
     */
    public static final String NOMBRE_TABLA="CBZC_TABLA";
    private Long idTabla;
    private String idObligacion;
    private int idRegimen;
    private String concepto;
    private String decripcion;
    private String fundamentoLegal;
    private Date   fechaInicio;
    private Date   fechaFin;
    private Long    orden;
    
    /**
     *
     */
    public Tabla() {
        super();
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
     * @param fechaInicio
     */
    public void setFechaInicio(Date fechaInicio) {
        if (fechaInicio != null) {
            this.fechaInicio = (Date) fechaInicio.clone();
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
     * @param fechaFin
     */
    public void setFechaFin(Date fechaFin) {
        if (fechaFin != null) {
            this.fechaFin = (Date) fechaFin.clone();
        }
    }

    /**
     *
     * @param idObligacion
     */
    public void setIdObligacion(String idObligacion) {
        this.idObligacion = idObligacion;
    }

    /**
     *
     * @return
     */
    public String getIdObligacion() {
        return idObligacion;
    }

    /**
     *
     * @param idRegimen
     */
    public void setIdRegimen(int idRegimen) {
        this.idRegimen = idRegimen;
    }

    /**
     *
     * @return
     */
    public int getIdRegimen() {
        return idRegimen;
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
     * @param decripcion
     */
    public void setDecripcion(String decripcion) {
        this.decripcion = decripcion;
    }

    /**
     *
     * @return
     */
    public String getDecripcion() {
        return decripcion;
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
     * @param idTabla
     */
    public void setIdTabla(Long idTabla) {
        this.idTabla = idTabla;
    }

    /**
     *
     * @return
     */
    public Long getIdTabla() {
        return idTabla;
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
}

