package mx.gob.sat.siat.cob.seguimiento.service.catalogos;

import java.io.ByteArrayOutputStream;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Combo;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.UsuarioEF;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.exception.DaoException;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;

public interface UsuarioEFService {

    List<UsuarioEF> obtenerUsuarioPorRfcCorto(String rfc) throws SGTServiceException;

    List<UsuarioEF> todosLosUsuariosEF();

    void agregaUsuarioEF(UsuarioEF usuarioEF, SegbMovimientoDTO segMovDto) throws SeguimientoDAOException, DaoException;

    void editaUsuarioEF(UsuarioEF usuarioEF, SegbMovimientoDTO segMovDto) throws SeguimientoDAOException, DaoException;

    void reactivaUsuarioEF(UsuarioEF usuarioEF) throws SeguimientoDAOException;

    void eliminaUsuarioEF(UsuarioEF usuarioEF, SegbMovimientoDTO segMovDto) throws SeguimientoDAOException, DaoException;

    Integer buscaUsuarioEFPorIdYNom(UsuarioEF usuarioEF);

    ByteArrayOutputStream generaExcel(List<UsuarioEF> list);

    ByteArrayOutputStream generaPDF(List<UsuarioEF> list);

    List<Combo> obtenerComboEntidad();

    List<UsuarioEF> obtenerUsuariosPorEntidad(Long entidad) throws SGTServiceException;
}
