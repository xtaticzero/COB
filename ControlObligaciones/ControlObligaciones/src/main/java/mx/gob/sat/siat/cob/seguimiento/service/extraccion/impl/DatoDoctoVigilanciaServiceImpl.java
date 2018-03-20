package mx.gob.sat.siat.cob.seguimiento.service.extraccion.impl;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DatoDoctoVigilanciaDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DatoDoctoVigilancia;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.extraccion.DatoDoctoVigilanciaService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatoDoctoVigilanciaServiceImpl implements DatoDoctoVigilanciaService {

    @Autowired
    private DatoDoctoVigilanciaDAO datoDoctoVig;
    private final Logger log = Logger.getLogger(DatoDoctoVigilanciaServiceImpl.class);

    @Override
    public List<DatoDoctoVigilancia> consultaDatosDoctosVigilancia() throws SGTServiceException {
        List<DatoDoctoVigilancia> datosDoctoVigs;
        try {
            datosDoctoVigs = datoDoctoVig.consultaDatosDoctosVigilancia();
        } catch (SeguimientoDAOException e) {
            log.error(e);
            throw new SGTServiceException("No se pudo consultar los datos de los documentos de la vigilancia",e);
        }
        return datosDoctoVigs;
    }
}
