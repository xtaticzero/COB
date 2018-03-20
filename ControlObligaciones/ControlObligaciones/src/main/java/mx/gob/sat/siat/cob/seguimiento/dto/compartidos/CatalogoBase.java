package mx.gob.sat.siat.cob.seguimiento.dto.compartidos;

import java.io.Serializable;

import java.util.Comparator;

public class CatalogoBase implements Serializable, Comparable<CatalogoBase>, Comparator<CatalogoBase> {

    @SuppressWarnings("compatibility:2267906965603925450")
    private static final long serialVersionUID = 1L;
    private int id;
    private String idString;
    private String nombre;
    private String idAux;

    /**
     *
     */
    public CatalogoBase() {
        super();
    }

    public CatalogoBase(int pId) {
        super();
        this.id=pId;
    }

    @Override
    public int compareTo(CatalogoBase o) {
        if( this.getId()<o.getId() ){
            return -1;
        }else if( this.getId()>o.getId() ){
            return 1;
        }else{
            return 0;
        }
    }

    @Override
    public int compare(CatalogoBase o1, CatalogoBase o2) {
        if( o1.getId()<o2.getId() ){
            return -1;
        }else if( o1.getId()>o2.getId() ){
            return 1;
        }else{
            return 0;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null){
            return false;
        }else{
            if(!(obj instanceof CatalogoBase)){
                return false;
            }else{
                return compareTo((CatalogoBase) obj) == 0;
            }
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + this.id;
        hash = 29 * hash + (this.idString != null ? this.idString.hashCode() : 0);
        hash = 29 * hash + (this.nombre != null ? this.nombre.hashCode() : 0);
        hash = 29 * hash + (this.idAux != null ? this.idAux.hashCode() : 0);
        return hash;
    }
    
    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @param idString
     */
    public void setIdString(String idString) {
        this.idString = idString;
    }

    /**
     *
     * @return
     */
    public String getIdString() {
        return idString;
    }

    /**
     *
     * @param idAux
     */
    public void setIdAux(String idAux) {
        this.idAux = idAux;
    }

    /**
     *
     * @return
     */
    public String getIdAux() {
        return idAux;
    }

}

