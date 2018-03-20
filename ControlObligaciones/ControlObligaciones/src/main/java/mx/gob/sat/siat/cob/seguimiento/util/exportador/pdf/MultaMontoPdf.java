package mx.gob.sat.siat.cob.seguimiento.util.exportador.pdf;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MultaMonto;


public class MultaMontoPdf extends GeneraPdf {

    /**
     *
     */
    public MultaMontoPdf() {
        super();
    }

    /**
     *
     * @param datos
     */
    @Override
    public void colocarDatos(List<Object> datos) {
        for (Object obj : datos) {
            MultaMonto oblisancion = (MultaMonto) obj;
           super.getTb().addCell(oblisancion.getDescripcionLarga());
           super.getTb().addCell(oblisancion.getResolMotivoDescr());
           super.getTb().addCell(oblisancion.getSancion());
           super.getTb().addCell(oblisancion.getInfraccion());
           super.getTb().addCell(oblisancion.getMotivacion());
           super.getTb().addCell(oblisancion.getMonto().toString());
           super.getTb().addCell(oblisancion.getDescripcionMonto()); 
           super.getTb().addCell(oblisancion.getFechaInicioStr()); 
           super.getTb().addCell(oblisancion.getFechaFinStr()); 

        }
    }
}
