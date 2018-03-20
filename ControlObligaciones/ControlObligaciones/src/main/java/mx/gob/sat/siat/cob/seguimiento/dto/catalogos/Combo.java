package mx.gob.sat.siat.cob.seguimiento.dto.catalogos;

import java.io.Serializable;

public class Combo implements Serializable{
    private static final long serialVersionUID = 3995462063655855867L;
    private String descripcion;
    private Long   idCombo;
    private String idAux;


    public Combo() {
        super();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setIdCombo(Long idCombo) {
        this.idCombo = idCombo;
    }

    public Long getIdCombo() {
        return idCombo;
    }

    public void setIdAux(String idAux) {
        this.idAux = idAux;
    }

    public String getIdAux() {
        return idAux;
    }
}
