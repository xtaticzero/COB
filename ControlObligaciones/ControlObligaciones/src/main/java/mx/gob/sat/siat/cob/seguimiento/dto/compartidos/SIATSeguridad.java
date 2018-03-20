package mx.gob.sat.siat.cob.seguimiento.dto.compartidos;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.api.Usuario;

public interface SIATSeguridad {
    void aplica() ;
    Usuario getUsuario();
    long getIdMovimiento();
}
