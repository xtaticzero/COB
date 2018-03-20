package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;

public class ConsultarIntentosFiltroDTO implements Serializable {

    @SuppressWarnings("compatibility:5863067301093614396")
    private static final long serialVersionUID = 7574538027422785603L;
    private Integer id;
    private Integer idEjecucion;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setIdEjecucion(Integer idEjecucion) {
        this.idEjecucion = idEjecucion;
    }

    public Integer getIdEjecucion() {
        return idEjecucion;
    }
}
