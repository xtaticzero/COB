package mx.gob.sat.siat.cob.seguimiento.util.exportador.pdf;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.UsuarioEF;

public class UsuarioEFPdf extends GeneraPdf{
    
    public UsuarioEFPdf() {
        super();
    }
    
    /**
     *
     * @param datos
     */
    @Override
    public void colocarDatos(List<Object> datos) {
        for(Object obj : datos){
            UsuarioEF usuarioEF = (UsuarioEF)obj;
            super.getTb().addCell(usuarioEF.getRfcCorto());
            super.getTb().addCell(usuarioEF.getEntidadDesc());
            super.getTb().addCell(usuarioEF.getNombreUsuario());
            super.getTb().addCell(usuarioEF.getCorreoElectronico());
            super.getTb().addCell(usuarioEF.getSituacion());
            
        }
    }
}
