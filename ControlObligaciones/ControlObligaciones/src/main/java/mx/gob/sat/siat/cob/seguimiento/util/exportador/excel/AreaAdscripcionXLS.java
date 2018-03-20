package mx.gob.sat.siat.cob.seguimiento.util.exportador.excel;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.AreaAdscripcion;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

public class AreaAdscripcionXLS extends GeneraExcel {
    
    public AreaAdscripcionXLS() {
        super();
    }
    
    /**
     *
     * @param datos
     */
    @Override
    public void colocarContenido(List<Object> datos) {
        int cont = 2;
        for (Object obj : datos) {
            AreaAdscripcion areaAdscripcion = (AreaAdscripcion) obj;
            HSSFRow row;
            row = super.getWorksheet().createRow((short) cont);

            HSSFCell cellNombre = row.createCell(0);
            cellNombre.setCellValue(areaAdscripcion.getNombre());


            HSSFCell cellDescripcion = row.createCell(1);
            cellDescripcion.setCellValue(areaAdscripcion.getDescripcion());

            cont++;
        }
    }
}
