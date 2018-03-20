package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.api.Usuario;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

public class UsuarioNovell extends AccesoUsr implements Usuario, Serializable {

    /**
     *
     */
    public UsuarioNovell() {
        super();
    }

    /**
     *
     * @return
     */
    @Override
    public String getRFC() {
        return super.getRfcCorto();
    }

    /**
     *
     * @return
     */
    @Override
    public String getIP() {
        return super.getIp();
    }

    /**
     *
     * @return
     */
    @Override
    public String getIDAdmonLocal() {
        return super.getLocalidad();
    }

    /**
     *
     * @return
     */
    @Override
    public String getIDAdmonGral() {
        return super.getClaveAdminGral();
    }

    /**
     *
     * @return
     */
    @Override
    public String getNombre() {
        return super.getNombres();
    }

    /**
     *
     * @return
     */
    @Override
    public String getApaterno() {
        return super.getPrimerApellido();
    }

    /**
     *
     * @return
     */
    @Override
    public String getAmaterno() {
        return super.getSegundoApellido();
    }

    /**
     *
     * @return
     */
    @Override
    public String getIDEmpleado() {
        return super.getNumeroEmp();
    }
}
