package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.api.Usuario;

public class UsuarioDefault implements Usuario, Serializable {

    /**
     *
     */
    public UsuarioDefault() {
        super();
    }

    /**
     *
     * @return
     */
    @Override
    public String getRFC() {
        return "RFC000000001";
    }

    /**
     *
     * @return
     */
    @Override
    public String getIP() {
        return "127.0.0.1";
    }

    /**
     *
     * @return
     */
    @Override
    public String getIDAdmonLocal() {
        return "ADM_LOCAL";
    }

    /**
     *
     * @return
     */
    @Override
    public String getIDAdmonGral() {
        return "ADM_GRAL";
    }

    /**
     *
     * @return
     */
    @Override
    public String getNombre() {
        return "NOMBRE";
    }

    /**
     *
     * @return
     */
    @Override
    public String getApaterno() {
        return "PATERNO";
    }

    /**
     *
     * @return
     */
    @Override
    public String getAmaterno() {
        return "MATERNO";
    }

    /**
     *
     * @return
     */
    @Override
    public String getIDEmpleado() {
        return "IDEMP";
    }
}
