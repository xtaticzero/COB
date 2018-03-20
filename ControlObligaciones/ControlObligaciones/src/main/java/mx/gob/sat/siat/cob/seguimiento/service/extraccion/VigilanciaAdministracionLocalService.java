/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.extraccion;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.DocumentoARCA;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaAdministracionLocal;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.SituacionVigilanciaEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;

/**
 *
 * @author Juan
 */
public interface VigilanciaAdministracionLocalService {

    /**
     *
     * @return @throws SGTServiceException
     */
    List<VigilanciaAdministracionLocal> obtenerAdministracionLocalXIdVigilanica(Long idVigilancia) throws SGTServiceException;

    /**
     *
     * @param idVigilancia
     * @return
     * @throws SGTServiceException
     */
    List<VigilanciaAdministracionLocal> obtenerAdministracionLocalMultaXIdVigilanica(Long idVigilancia) throws SGTServiceException;

    /**
     *
     * @param vigilanciaAdministracionLocal
     * @throws SGTServiceException
     */
    void actualizaIdSituacionVigilancia(VigilanciaAdministracionLocal vigilanciaAdministracionLocal, int idSituacionVigilancia) throws SGTServiceException;

    /**
     *
     * @param idVigilancia
     * * @param idAdministracionLocal
     * @throws SGTServiceException
     */
    void actualizaFechaVigilancia(Long idVigilancia, String idAdministracionLocal) throws SGTServiceException;

    /**
     *
     * @param doctos
     * @return lista de Administracion Local
     * @throws SGTServiceException
     */
    List<VigilanciaAdministracionLocal> obtenerIdAdministracionLocal(List<DocumentoARCA> doctos) throws SGTServiceException;

    /**
     *
     * @param vigilancia
     * @param joinNivelEmision
     * @return
     * @throws SGTServiceException
     */
    boolean hayRegistrosPorVigilancia(VigilanciaAdministracionLocal vigilancia, String joinNivelEmision) throws SGTServiceException;

    /**
     *
     * @param valValida
     * @param situacionEnum
     * @throws SGTServiceException
     */
    void updateSituacionVigilancia(List<VigilanciaAdministracionLocal> valValida, SituacionVigilanciaEnum situacionEnum) throws SGTServiceException;

    /**
     * Devuelve las vigilancias que ya estan con estado 5 (enviadas a ARCA),
     * pero aun hay documentos pendientes con estado 1(pendiente de envio).
     *
     * @throws SGTServiceException
     * @return
     */
    List<VigilanciaAdministracionLocal> obtenerVigilanciasConDocumentos(VigilanciaAdministracionLocal vigilanciaAdminLocal) throws SGTServiceException;

    /**
     *
     * @return @throws SGTServiceException
     */
    VigilanciaAdministracionLocal obtenerVigilanciaMultaBatch() throws SGTServiceException;

    /**
     *
     * @param idVigilancia
     * @param idAdmonLocal
     * @return
     * @throws SGTServiceException
     */
    VigilanciaAdministracionLocal obtenerVigilancia(Long idVigilancia, String idAdmonLocal) throws SGTServiceException;

    /**
     *
     * @return @throws SGTServiceException
     */
    Long obtenetNumeroVigilancia() throws SGTServiceException;

    /**
     * @param idVigilancia
     * @param idAdmonLocal
     * @return
     * @throws SGTServiceException
     */
    Integer guardaVigAdminLocal(Long idVigilancia, String idAdmonLocal) throws SGTServiceException;
}
