package mx.gob.sat.siat.cob.seguimiento.util.exportador.pdf;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Descripcion;

public class DescripcionPdf extends GeneraPdf{
    
    public DescripcionPdf() {
        super();
    }
    
    /**
     *
     * @param datos
     */
    @Override
    public void colocarDatos(List<Object> datos) {
        for(Object obj : datos){
            Descripcion descripcion = (Descripcion)obj;
            super.getTb().addCell(descripcion.getDescripcion());
           
        }
    }

}