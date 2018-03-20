package mx.gob.sat.siat.cob.background.listener.impl;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.DocumentoARCADAO;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.Periodo;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author root
 */
public class PeriodoARCAWriterListenerImpl implements ItemWriteListener<Periodo> {

    @Autowired
    private DocumentoARCADAO documentoARCADAO;
    
    /**
     *
     * @param list
     */
    @Override
    public void beforeWrite(List<? extends Periodo> list) {
    }

    /**
     *
     * @param list
     */
    @Override
    public void afterWrite(List<? extends Periodo> list) {
    }

    /**
     *
     * @param excptn
     * @param periodos
     */
    @Override
    public void onWriteError(Exception excptn, List<? extends Periodo> periodos) {
        
        documentoARCADAO.eliminarDocumentoEnCascada(null);
    }
}
