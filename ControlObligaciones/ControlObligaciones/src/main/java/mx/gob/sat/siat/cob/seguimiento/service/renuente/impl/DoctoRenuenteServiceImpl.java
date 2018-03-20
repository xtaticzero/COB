package mx.gob.sat.siat.cob.seguimiento.service.renuente.impl;

import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.EstadoDocumento;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.renuente.DoctoRenuenteService;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DoctoRenuenteServiceImpl implements DoctoRenuenteService {

    @Autowired
    private DocumentoDAO documentoDAO;

    /**
     *
     */
    public DoctoRenuenteServiceImpl() {
        super();
    }

    /**
     * @param numControl
     * @return
     */
    @Override
    @Propagable(exceptionClass = SGTServiceException.class)
    public Documento consultaXNumControl(String numControl)  {
        return  documentoDAO.consultaXNumControl(numControl);
    }

    /**
     * @param numControl
     * @return
     */
    @Override
    @Propagable(exceptionClass = SGTServiceException.class)
    public Boolean updateConsideraRenuenciaDocto(String numControl)  {
            return documentoDAO.updateConsideraRenuenciaDocto(numControl)!=null;
    }
    
    @Override
    @Propagable(exceptionClass = SGTServiceException.class)
    public EstadoDocumento consultaEstadoDoctoXNumControl(String numControl) {
        return documentoDAO.consultaEstadoDoctoXNumControl(numControl);
    }
}

