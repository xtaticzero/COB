package mx.gob.sat.siat.cob.seguimiento.dao.psatgj.sql;

/**
 *
 * @author root
 */
public interface CorreoSQL {

    String OBTENER_CORREO = 
            "select correoelectronico || ',' || trim(CORREOELECTRONICOALTERNO) as correos "
            + " from SGTP_FUNCIONARIO where numeroempleado = ?";

}
