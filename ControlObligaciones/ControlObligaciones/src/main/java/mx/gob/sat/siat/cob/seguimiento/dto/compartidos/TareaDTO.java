package mx.gob.sat.siat.cob.seguimiento.dto.compartidos;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TareaDTO implements Serializable {

    private String idUsuario;

    /**
     *
     * @param idUsuario
     */
    public TareaDTO(String idUsuario) {
        super();
        this.idUsuario = idUsuario;
    }

    @Override
    public String toString() {
        String fechaHoraEjecucionInicial = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss_SSS").format(new Date());
        return this.idUsuario + "_" + fechaHoraEjecucionInicial;
    }
}
