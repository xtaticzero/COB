package mx.gob.sat.siat.cob.seguimiento.util.exportador.excel;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MtvoCancelDoc;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

public class MtvoCancelDocXLS extends GeneraExcel{
    /**
     *
     */
    public MtvoCancelDocXLS() {
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
            MtvoCancelDoc mtvoCancelDoc = (MtvoCancelDoc)obj;
            HSSFRow row;
            row = super.getWorksheet().createRow((short)cont);

            HSSFCell cellNombre = row.createCell(0);
            cellNombre.setCellValue(mtvoCancelDoc.getNombre());


            HSSFCell cellDescripcion = row.createCell(1);
            cellDescripcion.setCellValue(mtvoCancelDoc.getDescripcion());

            cont ++;
        }
    }
}
