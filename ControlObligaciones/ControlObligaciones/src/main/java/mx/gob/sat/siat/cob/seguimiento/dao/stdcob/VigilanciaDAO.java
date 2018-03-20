package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.CargaVigilancia;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ConsultaVigilancia;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleCarga;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.EmisionVigilancia;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Vigilancia;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EtapaVigilanciaEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;

/**
 *
 * @author root
 */
public interface VigilanciaDAO {

    /**
     *
     * @return @throws SeguimientoDAOException
     */
    List<ConsultaVigilancia> consultaVigilancia() throws SeguimientoDAOException;

    /**
     *
     * @return @throws SeguimientoDAOException
     */
    List<ConsultaVigilancia> consultaVigilanciaEF() throws SeguimientoDAOException;

    /**
     *
     * @param estadoDoc
     * @param etapa
     * @param tipoDocs
     * @return
     */
    List<Vigilancia> consultarDocsVencidosVigilanciaPorEstado(EstadoDocumentoEnum estadoDoc, EtapaVigilanciaEnum etapa, TipoDocumentoEnum[] tipoDocs);

    /**
     *
     * @param idVigilancia
     * @return
     * @throws SeguimientoDAOException
     */
    int actualizaVigilancia(int idVigilancia) throws SeguimientoDAOException;

    /**
     *
     * @param vigilancia
     * @throws SeguimientoDAOException
     */
    void guarda(CargaVigilancia vigilancia) throws SeguimientoDAOException;

    /**
     *
     * @param detalle
     * @throws SeguimientoDAOException
     */
    void guardaDetalle(DetalleCarga detalle) throws SeguimientoDAOException;

    /**
     *
     * @return
     */
    String consultaUltimoIdVigilancia();

    /**
     * @return @throws SeguimientoDAOException
     * @throws SeguimientoDAOException
     */
    List<EmisionVigilancia> conusltaVigilanciaXEtapa() throws SeguimientoDAOException;

    /**
     *
     * @param idVigilancia
     * @param archivoLocal
     * @param nombreArchivo
     */
    void guardarBitacoraErrores(int idVigilancia, String archivoLocal, String nombreArchivo);

    /**
     *
     * @param idVigilancia
     * @return
     * @throws SeguimientoDAOException
     */
    Integer consultaVigilanciasExistente(int idVigilancia) throws SeguimientoDAOException;

    /**
     *
     * @param idVigilancia
     * @return
     * @throws SeguimientoDAOException
     */
    Integer consultaVigilanciasExistenteEF(int idVigilancia) throws SeguimientoDAOException;

    /**
     *
     * @param idvigilancia
     * @return
     * @throws SeguimientoDAOException
     */
    Integer consultaIdTipoFirma(Long idvigilancia) throws SeguimientoDAOException;
}
