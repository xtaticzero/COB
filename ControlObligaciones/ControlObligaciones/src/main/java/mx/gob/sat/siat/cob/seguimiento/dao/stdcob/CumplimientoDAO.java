package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.db2.Paginador;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Cumplimiento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DocumentoAprobar;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.GrupoAfectacionCumpDTO;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;

public interface CumplimientoDAO {

    Integer insert(Cumplimiento cumplimiento);

    Integer afectarDetallesConCumplimiento(Integer[] estados) throws SeguimientoDAOException;

    BigInteger obtenerMaximoIdentificadorCumplimiento();

    Integer eliminarCumplimientosProcesados();

    Integer eliminarCumplimientosPorFechaMto(Date fechaMantenimiento);

    List<DocumentoAprobar> listarDocumentosIcep(GrupoAfectacionCumpDTO grupoAfectacionCumpDTO, Paginador paginador);

    Integer actualizarDetalleConCumplimiento(Documento documento, Integer idSituacionIcep);

    List<GrupoAfectacionCumpDTO> obtenerDetallesOmisos();
}
