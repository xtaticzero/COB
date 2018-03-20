package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;
import java.util.Comparator;

/**
 *
 * @author christian.ventura
 */
public class RfcNombreDTO implements Serializable, Comparator<RfcNombreDTO>, Comparable<RfcNombreDTO> {

    private int idFederativa;
    private String boid;
    private String numeroControl;
    private String fecha;
    private String rfc;
    private String nombre;
    private String obligaciones;
    private String idAdmonLocal;
    
    /**
     * 
     * @param o1
     * @param o2
     * @return 
     */
    @Override
    public int compare(RfcNombreDTO o1, RfcNombreDTO o2) {
        return o1.getRfc().compareTo(o2.getRfc());
    }
    
    /**
     * 
     * @param o
     * @return 
     */
    @Override
    public int compareTo(RfcNombreDTO o) {
        return this.getRfc().compareTo(o.getRfc());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj ==null){
            return false;
        }
        if(!(obj instanceof RfcNombreDTO)){ 
            return false;
        }
        return compareTo((RfcNombreDTO)obj)==0;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.idFederativa;
        hash = 67 * hash + (this.boid != null ? this.boid.hashCode() : 0);
        hash = 67 * hash + (this.numeroControl != null ? this.numeroControl.hashCode() : 0);
        hash = 67 * hash + (this.fecha != null ? this.fecha.hashCode() : 0);
        hash = 67 * hash + (this.rfc != null ? this.rfc.hashCode() : 0);
        hash = 67 * hash + (this.nombre != null ? this.nombre.hashCode() : 0);
        hash = 67 * hash + (this.obligaciones != null ? this.obligaciones.hashCode() : 0);
        hash = 67 * hash + (this.idAdmonLocal != null ? this.idAdmonLocal.hashCode() : 0);
        return hash;
    }

    /**
     * @return the idFederativa
     */
    public int getIdFederativa() {
        return idFederativa;
    }

    /**
     * @param idFederativa the idFederativa to set
     */
    public void setIdFederativa(int idFederativa) {
        this.idFederativa = idFederativa;
    }

    /**
     * @return the boid
     */
    public String getBoid() {
        return boid;
    }

    /**
     * @param boid the boid to set
     */
    public void setBoid(String boid) {
        this.boid = boid;
    }

    /**
     * @return the fecha
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the rfc
     */
    public String getRfc() {
        return rfc;
    }

    /**
     * @param rfc the rfc to set
     */
    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the numeroControl
     */
    public String getNumeroControl() {
        return numeroControl;
    }

    /**
     * @param numeroControl the numeroControl to set
     */
    public void setNumeroControl(String numeroControl) {
        this.numeroControl = numeroControl;
    }

    /**
     * @return the obligaciones
     */
    public String getObligaciones() {
        return obligaciones;
    }

    /**
     * @param obligaciones the obligaciones to set
     */
    public void setObligaciones(String obligaciones) {
        this.obligaciones = obligaciones;
    }

    /**
     * @return the idAdmonLocal
     */
    public String getIdAdmonLocal() {
        return idAdmonLocal;
    }

    /**
     * @param idAdmonLocal the idAdmonLocal to set
     */
    public void setIdAdmonLocal(String idAdmonLocal) {
        this.idAdmonLocal = idAdmonLocal;
    }

    
}
