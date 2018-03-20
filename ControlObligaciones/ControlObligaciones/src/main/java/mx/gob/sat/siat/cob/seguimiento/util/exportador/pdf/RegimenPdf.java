package mx.gob.sat.siat.cob.seguimiento.util.exportador.pdf;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Regimen;

public class RegimenPdf extends GeneraPdf {
    /**
     *
     */
    public RegimenPdf() {
        super();
    }
    
    /**
     *
     * @param datos
     */
    @Override
    public void colocarDatos(List<Object> datos) {
        for(Object obj : datos){
            Regimen regimen = (Regimen)obj;
            super.getTb().addCell(regimen.getIdRegimen().toString());
            super.getTb().addCell(regimen.getNombre());
            super.getTb().addCell(regimen.getDescripcion());
            
        }
    }
}
