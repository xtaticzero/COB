package mx.gob.sat.siat.cob.seguimiento.service.arca.impl;

import mx.gob.sat.siat.cob.seguimiento.dao.arca.DocumentoARCADAO;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.enums.EstadoDocArcaEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.arca.CancelaDoctoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CancelaDoctoServiceImpl implements CancelaDoctoService {

    /**
     *
     */
    private Logger log = Logger.getLogger(CancelaDoctoServiceImpl.class);
    @Autowired
    private DocumentoARCADAO documentoArcaDAO;

    /**
     *
     */
    public CancelaDoctoServiceImpl() {
        super();
    }

    /**
     *
     * @param id
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException
     */
    @Override
    @Transactional
    public void cancelaDoctoArca(String id) throws SGTServiceException {
        Integer resp;
        Integer estadoDocumento;

        resp = null;
        estadoDocumento = documentoArcaDAO.getEstadoDocumento(id);
        if (estadoDocumento != null) {
            if (estadoDocumento == EstadoDocArcaEnum.ARCA_PENDIENTE.getIdEdoDoc() || estadoDocumento == EstadoDocArcaEnum.ARCA_IMPRESO.getIdEdoDoc()) {
                resp = documentoArcaDAO.cancelaDocto(id);
                if (resp == null || resp == -1) {
                    log.info(resp);
                    throw new SGTServiceException("No se pudo cancelar el documento en ARCA.");
                }
            } else if (estadoDocumento == -1) {
                log.error("DATOS INCONSISTENTES: El número de control " + id + " no existe en ARCA.");
            }

        } else {
            throw new SGTServiceException("Error al obtener el estado de documento en ARCA.");
        }
    }

    @Override
    @Transactional
    public void cancelarMultaSIR(String numeroResolucion) throws SGTServiceException {

        if (documentoArcaDAO.cancelarMultaSIR(numeroResolucion) == null) {
            throw new SGTServiceException("Ocurrio un problema al cancelar la multa en SIR");
        }
    }
}
