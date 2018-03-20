package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.SituacionIcepEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoMultaEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;

public interface DetalleDocumentoDAO {

    /**
     * @param detalle
     * @return int
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException
     */
    int guardaDetalleDocumento(DetalleDocumento detalle) throws SeguimientoDAOException;

    Map<String, String> guardaDetalleDocumentoBatch(List<DetalleDocumento> detalles) throws SeguimientoDAOException;

    List<DetalleDocumento> consultaXNumControl(String numControl);

    List<DetalleDocumento> consultaXNumControl(String numControl, SituacionIcepEnum situacion);

    void cambiarEstatusICEP(DetalleDocumento detalleDocumento, SituacionIcepEnum situacion) throws SeguimientoDAOException;

    void cambiarEstadoICEPsPorNumControl(Documento documento, SituacionIcepEnum valorActual, SituacionIcepEnum valorNuevo) throws SeguimientoDAOException;

    void marcarConMulta(DetalleDocumento detalleDocumento, TipoMultaEnum tipoMulta) throws SeguimientoDAOException;

    Integer solventarPorMovimientoAlPadron(String numeroControl);

    Integer solventarPorMovimientoAlPadron(Documento documento);

    Integer getNumeroVigilables(String numeroControl);
}
