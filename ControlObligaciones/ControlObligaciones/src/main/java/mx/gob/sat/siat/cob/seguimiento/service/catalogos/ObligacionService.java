package mx.gob.sat.siat.cob.seguimiento.service.catalogos;

import java.io.ByteArrayOutputStream;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Combo;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Obligacion;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.ValorBooleano;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.exception.DaoException;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;


public interface ObligacionService {

    List<ValorBooleano> obtenerTodosLosValoresBool();

    List<Obligacion> todasLasObligaciones();

    void agregaObligacion(Obligacion obligacion, SegbMovimientoDTO segMovDto) throws SeguimientoDAOException, DaoException;

    void editaObligacion(Obligacion obligacion, SegbMovimientoDTO segMovDto) throws SeguimientoDAOException, DaoException;
    
    void reactivaObligacion(Obligacion obligacion) throws SeguimientoDAOException;

    void eliminaObligacion(Obligacion obligacion, SegbMovimientoDTO segMovDto) throws SeguimientoDAOException, DaoException;

    Integer buscaObligacionPorConcYDesc(Obligacion obligacion);

    ByteArrayOutputStream generaExcel(List<Obligacion> list);

    ByteArrayOutputStream generaPDF(List<Obligacion> list);

    List<Combo> obtenerListaComboObligancion();

    List<Combo> obtenerListaComboRegimen();

    List<Obligacion> getDistinctObligacion();
    
    String obtenerConceptoImpuesto(Long idObli);
}
