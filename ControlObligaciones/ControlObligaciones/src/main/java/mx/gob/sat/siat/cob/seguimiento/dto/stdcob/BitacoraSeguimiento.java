package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.util.Date;

/**
 *
 * @author root
 */
public class BitacoraSeguimiento {
    /**
     *
     */
    public BitacoraSeguimiento() {
        super();
    }
    private String numeroControl;
    private Integer idEstadoDocumento;
    private Date fechaMovimiento;

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
    public String getNumeroControl() {
        return numeroControl;
    }

    /**
     *
     * @param idEstadoDocumento
     */
    public void setIdEstadoDocumento(Integer idEstadoDocumento) {
        this.idEstadoDocumento = idEstadoDocumento;
    }

    /**
     *
     * @return
     */
    public Integer getIdEstadoDocumento() {
        return idEstadoDocumento;
    }

    /**
     *
     * @param fechaMovimiento
     */
    public void setFechaMovimiento(Date fechaMovimiento) {
        if(fechaMovimiento!=null){
            this.fechaMovimiento = (Date)fechaMovimiento.clone();
        }
    }

    /**
     *
     * @return
     */
    public Date getFechaMovimiento() {
        if(fechaMovimiento!=null){
            return (Date) fechaMovimiento.clone();
        }else{
            return null;
        }
    }
}
