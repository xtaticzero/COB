package mx.gob.sat.siat.cob.seguimiento.util.exportador.excel;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Regimen;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

public class RegimenXLS extends GeneraExcel{
    /**
     *
     */
    public RegimenXLS() {
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
            Regimen regimen = (Regimen)obj;
            HSSFRow row;
            row = super.getWorksheet().createRow((short)cont);

            HSSFCell cellIdRegimen = row.createCell(0);
            cellIdRegimen.setCellValue(regimen.getIdRegimen());

            HSSFCell cellNombre = row.createCell(1);
            cellNombre.setCellValue(regimen.getNombre());


            HSSFCell cellDescripcion = row.createCell(2);
            cellDescripcion.setCellValue(regimen.getDescripcion());

            cont ++;
        }
    }
}
