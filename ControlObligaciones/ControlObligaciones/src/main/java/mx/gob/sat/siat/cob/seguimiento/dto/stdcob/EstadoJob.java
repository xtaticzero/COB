package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;

public class EstadoJob implements Serializable{
    
    
    private String strEstado;
    private Integer   estado;

    public EstadoJob() {
        super();
    }

    public void setStrEstado(String strEstado) {
        this.strEstado = strEstado;
    }

    public String getStrEstado() {
        return strEstado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Integer getEstado() {
        return estado;
    }
}
