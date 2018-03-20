package mx.gob.sat.siat.cob.seguimiento.util.exportador.pdf;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.AreaAdscripcion;

public class AreaAdscripcionPdf extends GeneraPdf {
    
    public AreaAdscripcionPdf() {
        super();
    }
    
    /**
     *
     * @param datos
     */
    @Override
    public void colocarDatos(List<Object> datos) {
        for (Object obj : datos) {
            AreaAdscripcion areaAdscripcion = (AreaAdscripcion) obj;
            super.getTb().addCell(areaAdscripcion.getNombre());
            super.getTb().addCell(areaAdscripcion.getDescripcion());

        }
    }
}
