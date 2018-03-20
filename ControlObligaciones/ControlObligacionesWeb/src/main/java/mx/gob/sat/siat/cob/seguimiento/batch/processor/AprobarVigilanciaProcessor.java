package mx.gob.sat.siat.cob.seguimiento.batch.processor;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.FirmaDigital;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.FirmaDigitalDTO;
import org.springframework.batch.item.ItemProcessor;

/**
 *
 * @author christian.ventura
 */
public interface AprobarVigilanciaProcessor extends ItemProcessor<FirmaDigitalDTO, FirmaDigital> {
    
}
