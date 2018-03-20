package mx.gob.sat.siat.cob.seguimiento.util.exportador.pdf;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MotRechazoVig;

public class MotRechazoVigPdf extends GeneraPdf {
    
    public MotRechazoVigPdf() {
        super();
    }
    
    /**
     *
     * @param datos
     */
    @Override
    public void colocarDatos(List<Object> datos) {
        for(Object obj : datos){
            MotRechazoVig motRechazoVig = (MotRechazoVig)obj;
            super.getTb().addCell(motRechazoVig.getNombre());
            super.getTb().addCell(motRechazoVig.getDescripcion());
            
        }
    }
}
