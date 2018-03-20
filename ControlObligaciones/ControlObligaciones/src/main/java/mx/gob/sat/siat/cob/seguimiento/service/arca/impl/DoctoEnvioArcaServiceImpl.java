package mx.gob.sat.siat.cob.seguimiento.service.arca.impl;

import java.util.List;
import java.util.Set;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.DoctoEnvioArcaDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.DocumentoARCA;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.ObligacionPeriodo;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.RequerimientosAnteriores;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.arca.DoctoEnvioArcaService;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Juan
 */
@Service("doctoEnvioArcaService")
public class DoctoEnvioArcaServiceImpl implements DoctoEnvioArcaService {

    @Autowired
    private DoctoEnvioArcaDAO doctoEnvioArcaDAO;

    /**
     *
     */
    public DoctoEnvioArcaServiceImpl() {
        super();
    }

    /**
     * @param numControl
     * @return
     * @throws SGTServiceException
     */
    @Propagable(exceptionClass = SGTServiceException.class)
    @Override
    public List<ObligacionPeriodo> obtenerObligacionPeriodo(String numControl)
            throws SGTServiceException {
        return doctoEnvioArcaDAO.obtenerObligacionPeriodo(numControl);
    }

    /**
     * @param numControl
     * @return
     * @throws SGTServiceException
     */
    @Propagable(exceptionClass = SGTServiceException.class)
    @Override
    public List<RequerimientosAnteriores> obtenerReqsAnteriores(Set<String> numControl)
            throws SGTServiceException {
        List<RequerimientosAnteriores> reqsAnteriores = doctoEnvioArcaDAO.obtenerReqAnteriores(numControl);
        if (reqsAnteriores == null) {
            throw new SGTServiceException("Ocurrio un error en la base de datos a la hora de obtener Requerimientos Anteriores");
        }
        return reqsAnteriores;
    }

    /**
     *
     * @param numControl
     * @param idResolucion
     * @return
     * @throws SGTServiceException
     */
    @Propagable(exceptionClass = SGTServiceException.class)
    @Override
    public DocumentoARCA multaDoctoArca(String numControl, int idResolucion) throws SGTServiceException {
        DocumentoARCA docto;
        docto = doctoEnvioArcaDAO.multaDoctoArca(numControl, idResolucion);
        if (docto == null) {
            throw new SGTServiceException("Ocurrio un error en la base de datos en multa documento ARCA");
        }
        return docto;
    }

    /**
     *
     * @param numControl
     * @return
     * @throws SGTServiceException
     */
    @Propagable(exceptionClass = SGTServiceException.class)
    @Override
    public List<RequerimientosAnteriores> origenMultaArcaAntecesores(String numControl) throws SGTServiceException {
        List<RequerimientosAnteriores> reqAnteriores = doctoEnvioArcaDAO.origenMultaArcaAntecesores(numControl);
        if (reqAnteriores == null) {
            throw new SGTServiceException("Ocurrio un error en la base de datos");
        }
        return reqAnteriores;
    }

    /**
     *
     * @param numControl
     * @return
     * @throws SGTServiceException
     */
    @Propagable(exceptionClass = SGTServiceException.class)
    @Override
    public List<RequerimientosAnteriores> origenMultaArcaPosteriores(String numControl) throws SGTServiceException {
        List<RequerimientosAnteriores> reqAnteriores = doctoEnvioArcaDAO.origenMultaArcaPosteriores(numControl);
        if (reqAnteriores == null) {
            throw new SGTServiceException("Ocurrio un error en la base de datos");
        }
        return reqAnteriores;
    }
}
