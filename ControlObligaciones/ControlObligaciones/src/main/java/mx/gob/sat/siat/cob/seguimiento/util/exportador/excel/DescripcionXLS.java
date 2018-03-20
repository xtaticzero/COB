package mx.gob.sat.siat.cob.seguimiento.util.exportador.excel;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Descripcion;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

public class DescripcionXLS extends GeneraExcel{
    
    public DescripcionXLS() {
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
            Descripcion descripcion = (Descripcion)obj;
            HSSFRow row;
            row = super.getWorksheet().createRow((short)cont);

            HSSFCell cellDescripcion = row.createCell(0);
            cellDescripcion.setCellValue(descripcion.getDescripcion());

            cont ++;
        }
    }
}
