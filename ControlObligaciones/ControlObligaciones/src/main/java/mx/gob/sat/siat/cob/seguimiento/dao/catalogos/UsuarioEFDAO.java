package mx.gob.sat.siat.cob.seguimiento.dao.catalogos;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.UsuarioEF;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;

public interface UsuarioEFDAO {
    
    List<UsuarioEF> todosLosUsuariosEFXVigilancia(int idVigilancia);
    List<UsuarioEF> todosLosUsuariosEF();
    List<UsuarioEF> obtenerUsuarioPorRfcCorto(String rfc);
    void agregaUsuarioEF(UsuarioEF usuarioEF) throws SeguimientoDAOException;
    void editaUsuarioEF(UsuarioEF usuarioEF) throws SeguimientoDAOException;
    void reactivaUsuarioEF(UsuarioEF usuarioEF) throws SeguimientoDAOException;
    Integer eliminaUsuarioEF(UsuarioEF usuarioEF) throws SeguimientoDAOException;
    UsuarioEF buscaUsuarioEFPorID(UsuarioEF usuarioEF);
    Integer buscarUsuarioEFPorIdYNom(UsuarioEF usuarioEF);
    List<UsuarioEF> obtenerUsuariosPorEntidad(Long entidad);
}
