package mx.gob.sat.siat.cob.seguimiento.util.exportador.pdf;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.AdmonLocal;

public class AdmonLocalPdf extends GeneraPdf {
    
    public AdmonLocalPdf() {
        super();
    }
    
    /**
     *
     * @param datos
     */
    @Override
    public void colocarDatos(List<Object> datos) {
        for (Object obj : datos) {
            AdmonLocal admonLocal = (AdmonLocal) obj;
            super.getTb().addCell(admonLocal.getIdAdmonLocal());
            super.getTb().addCell(admonLocal.getNombre());
            super.getTb().addCell(admonLocal.getDescripcion());

        }
    }
}
