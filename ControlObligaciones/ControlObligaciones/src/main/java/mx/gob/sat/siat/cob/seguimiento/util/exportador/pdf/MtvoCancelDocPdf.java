package mx.gob.sat.siat.cob.seguimiento.util.exportador.pdf;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MtvoCancelDoc;

public class MtvoCancelDocPdf extends GeneraPdf {
    /**
     *
     */
    public MtvoCancelDocPdf() {
        super();
    }
    
    /**
     *
     * @param datos
     */
    @Override
    public void colocarDatos(List<Object> datos) {
        for(Object obj : datos){
            MtvoCancelDoc mtvoCancelDoc = (MtvoCancelDoc)obj;
            super.getTb().addCell(mtvoCancelDoc.getNombre());
            super.getTb().addCell(mtvoCancelDoc.getDescripcion());
            
        }
    }
}
