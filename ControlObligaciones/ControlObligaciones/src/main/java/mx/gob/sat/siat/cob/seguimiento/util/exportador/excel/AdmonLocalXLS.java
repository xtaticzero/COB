package mx.gob.sat.siat.cob.seguimiento.util.exportador.excel;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.AdmonLocal;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

public class AdmonLocalXLS extends GeneraExcel {
    
    public AdmonLocalXLS() {
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
            AdmonLocal admonLocal = (AdmonLocal) obj;
            HSSFRow row;
            row = super.getWorksheet().createRow((short) cont);

            HSSFCell cellIdAdmonLocal = row.createCell(0);
            cellIdAdmonLocal.setCellValue(admonLocal.getIdAdmonLocal());

            HSSFCell cellNombre = row.createCell(1);
            cellNombre.setCellValue(admonLocal.getNombre());


            HSSFCell cellDescripcion = row.createCell(2);
            cellDescripcion.setCellValue(admonLocal.getDescripcion());

            cont++;
        }
    }
}
