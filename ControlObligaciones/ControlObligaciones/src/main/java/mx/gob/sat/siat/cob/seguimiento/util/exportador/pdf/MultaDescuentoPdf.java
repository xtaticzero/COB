package mx.gob.sat.siat.cob.seguimiento.util.exportador.pdf;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MultaDescuento;


public class MultaDescuentoPdf extends GeneraPdf {
    /**
     *
     */
    public MultaDescuentoPdf() {
        super();
    }

    /**
     *
     * @param datos
     */
    @Override
    public void colocarDatos(List<Object> datos) {
        for(Object obj : datos){
            MultaDescuento multa = (MultaDescuento)obj;
            super.getTb().addCell(multa.getResolMotivoDescr());
            super.getTb().addCell(multa.getMultaDescuentoDescr());
                    
          }
    }

}
