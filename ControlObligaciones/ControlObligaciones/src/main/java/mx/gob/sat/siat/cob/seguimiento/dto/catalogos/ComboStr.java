package mx.gob.sat.siat.cob.seguimiento.dto.catalogos;

import java.io.Serializable;

public class ComboStr implements Serializable {
    private static final long serialVersionUID = -5197691180506547942L;
    private String descripcion;
    private String   idCombo;

    public ComboStr() {
        super();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setIdCombo(String idCombo) {
        this.idCombo = idCombo;
    }

    public String getIdCombo() {
        return idCombo;
    }
}
