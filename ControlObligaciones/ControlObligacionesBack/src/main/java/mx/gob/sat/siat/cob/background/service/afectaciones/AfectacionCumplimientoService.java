package mx.gob.sat.siat.cob.background.service.afectaciones;

import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;

public interface AfectacionCumplimientoService {   
    
    void afectarPorCumplimientos() throws SGTServiceException;
}
