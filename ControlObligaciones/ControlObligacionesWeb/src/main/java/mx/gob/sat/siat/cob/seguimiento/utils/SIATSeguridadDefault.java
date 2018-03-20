package mx.gob.sat.siat.cob.seguimiento.utils;

import java.io.Serializable;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.SIATSeguridad;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.UsuarioDefault;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.api.Usuario;

/**
 * deprecated, not used
 * @author root
 */
public class SIATSeguridadDefault implements Serializable, SIATSeguridad {
    /**
     *
     */
    public SIATSeguridadDefault() {
        super();
    }

    /**
     *
     */
    @Override
    public void aplica() {
    }

    /**
     *
     * @return
     */
    @Override
    public Usuario getUsuario() {
        return new UsuarioDefault();
    }

    /**
     *
     * @return
     */
    @Override
    public long getIdMovimiento() {
        return 111111;
    }
}
