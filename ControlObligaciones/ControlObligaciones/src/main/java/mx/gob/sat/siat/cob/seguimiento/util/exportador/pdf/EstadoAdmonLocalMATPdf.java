package mx.gob.sat.siat.cob.seguimiento.util.exportador.pdf;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.EstadoAdmonLocalMAT;

public class EstadoAdmonLocalMATPdf extends GeneraPdf{
    
    public EstadoAdmonLocalMATPdf() {
        super();
    }
    
    /**
     *
     * @param datos
     */
    @Override
    public void colocarDatos(List<Object> datos) {
        for(Object obj : datos){
            EstadoAdmonLocalMAT estadoAdmonLocalMAT = (EstadoAdmonLocalMAT)obj;
            super.getTb().addCell(estadoAdmonLocalMAT.getIdAdmonLocal());
            super.getTb().addCell(estadoAdmonLocalMAT.getEntidadDesc());
            super.getTb().addCell(estadoAdmonLocalMAT.getEsOperacionMATDesc());
            
        }
    }
}
