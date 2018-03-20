package mx.gob.sat.siat.cob.seguimiento.service.extraccion.impl;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DatoDocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DatoDocumento;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.extraccion.DatoDocumentoService;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatoDocumentoServiceImpl implements DatoDocumentoService {

    @Autowired
    private DatoDocumentoDAO datoDocumentoDAO;
    private final Logger log = Logger.getLogger(DatoDocumentoServiceImpl.class);

    /**
     * @param datoDocumento
     * @throws SGTServiceException
     */
    @Override
    public void insert(DatoDocumento datoDocumento) throws SGTServiceException {
        try {
            datoDocumentoDAO.insert(datoDocumento);
        } catch (SeguimientoDAOException e) {
            log.error(e);
            throw new SGTServiceException("No se pudo registrar dato documento: " + e);
        }
    }

    /**
     * @return @throws SGTServiceException
     */
    @Override
    public List<DatoDocumento> consultaDatoDocumento() throws SGTServiceException {
        List<DatoDocumento> datoDocumentos;
        try {
            datoDocumentos = datoDocumentoDAO.consultaDatoDocumento();
        } catch (SeguimientoDAOException e) {
            log.error(e);
            throw new SGTServiceException("No se pudo consultar dato documento: " + e);
        }
        return datoDocumentos;
    }

    /**
     * @param idVigilancia
     * @param idEtapaVigilancia
     * @return
     * @throws SGTServiceException
     */
    @Override
    public List<DatoDocumento> consultaDatoDocumento(int idVigilancia, int idEtapaVigilancia) throws SGTServiceException {
        List<DatoDocumento> datoDocumentos;
        try {
            datoDocumentos = datoDocumentoDAO.consultaDatoDocumento(idVigilancia, idEtapaVigilancia);
            log.info(datoDocumentos);
        } catch (SeguimientoDAOException e) {
            log.error(e);
            throw new SGTServiceException("No se pudo registrar dato documento por vigilancia y por etapa: " + e);
        }
        return datoDocumentos;
    }

    /**
     * @param idEstadoGeneracion
     * @param numeroControl
     * @return
     * @throws SGTServiceException
     */
    @Override
    public int actualizaEstadoDocumento(int idEstadoGeneracion, long numeroControl) throws SGTServiceException {
        int respuesta = 0;
        try {
            respuesta = datoDocumentoDAO.actualizaEstadoDocumento(idEstadoGeneracion, numeroControl);
        } catch (SeguimientoDAOException e) {
            log.error(e);
            throw new SGTServiceException("No se pudo actualizar dato documento: " + e);
        }
        return respuesta;
    }
}
