package mx.gob.sat.siat.cob.seguimiento.util.exportador.excel;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MotRechazoVig;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

public class MotRechazoVigXLS extends GeneraExcel{
    
    public MotRechazoVigXLS() {
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
            MotRechazoVig motRechazoVig = (MotRechazoVig)obj;
            HSSFRow row;
            row = super.getWorksheet().createRow((short)cont);

            HSSFCell cellNombre = row.createCell(0);
            cellNombre.setCellValue(motRechazoVig.getNombre());


            HSSFCell cellDescripcion = row.createCell(1);
            cellDescripcion.setCellValue(motRechazoVig.getDescripcion());

            cont ++;
        }
    }
}
