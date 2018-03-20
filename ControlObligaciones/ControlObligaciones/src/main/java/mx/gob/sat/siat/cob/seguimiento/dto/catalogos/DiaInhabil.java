package mx.gob.sat.siat.cob.seguimiento.dto.catalogos;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author root
 */
public class DiaInhabil implements Serializable{
    @SuppressWarnings("compatibility:5281467110081089880")
    private static final long serialVersionUID = 3741445825710903640L;
    
    /**
     *
     */
    public static final String NOMBRE_TABLA="ADMC_DIAINHABIL";
    private String motivo;
    private Date    fecha;
    
        
    /**
     *
     */
    public DiaInhabil() {
        super();
    }

    /**
     *
     * @param motivo
     */
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    /**
     *
     * @return
     */
    public String getMotivo() {
        return motivo;
    }

    /**
     *
     * @param fecha
     */
    public void setFecha(Date fecha) {
        if (fecha != null) {
            this.fecha = (Date) fecha.clone();
        }
    }

    /**
     *
     * @return
     */
    public Date getFecha() {
        if(fecha!=null){
        return (Date) fecha.clone();
        }
        return null;
    }

}
