package mx.gob.sat.siat.cob.seguimiento.util.exportador.excel;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.EjercicioFiscal;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

public class EjercicioFiscalXLS extends GeneraExcel {

    /**
     *
     */
    public EjercicioFiscalXLS() {
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
            EjercicioFiscal ejercicioFiscal = (EjercicioFiscal) obj;
            HSSFRow row;
            row = super.getWorksheet().createRow((short) cont);

            HSSFCell cellIdEjercicioFiscal = row.createCell(0);
            cellIdEjercicioFiscal.setCellValue(ejercicioFiscal.getIdEjercicioFiscal());

            HSSFCell cellNombre = row.createCell(1);
            cellNombre.setCellValue(ejercicioFiscal.getNombre());


            HSSFCell cellDescripcion = row.createCell(2);
            cellDescripcion.setCellValue(ejercicioFiscal.getDescripcion());

            cont++;
        }
    }
}
