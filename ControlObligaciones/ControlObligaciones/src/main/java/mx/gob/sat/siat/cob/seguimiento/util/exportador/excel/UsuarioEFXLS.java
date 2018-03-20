package mx.gob.sat.siat.cob.seguimiento.util.exportador.excel;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.UsuarioEF;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;


public class UsuarioEFXLS extends GeneraExcel{
    
    public UsuarioEFXLS() {
        super();
    }
    
    /**
     *
     * @param datos
     */
    @Override
    public void colocarContenido(List<Object> datos) {
        int cont= 2;
        for(Object obj : datos){
            UsuarioEF usuarioEF = (UsuarioEF)obj;
            HSSFRow row;
            row = super.getWorksheet().createRow((short)cont);

            HSSFCell cellRfcCorto = row.createCell(0);
            cellRfcCorto.setCellValue(usuarioEF.getRfcCorto());

            HSSFCell cellEntidadFederativa = row.createCell(1);
            cellEntidadFederativa.setCellValue(usuarioEF.getEntidadDesc());
            
            HSSFCell cellNombreUsuario = row.createCell(2);
            cellNombreUsuario.setCellValue(usuarioEF.getNombreUsuario());
            
            HSSFCell cellCorreoElectronico = row.createCell(ConstantsCatalogos.TRES);
            cellCorreoElectronico.setCellValue(usuarioEF.getCorreoElectronico());
            
            HSSFCell cellSituacion = row.createCell(ConstantsCatalogos.CUATRO);
            cellSituacion.setCellValue(usuarioEF.getSituacion());

            cont ++;
        }
    }
}
