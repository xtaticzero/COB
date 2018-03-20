package mx.gob.sat.siat.cob.seguimiento.util.exportador.excel;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Obligacion;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;


public class ObligacionXLS extends GeneraExcel {

    /**
     *
     */
    public ObligacionXLS() {
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
            Obligacion obligacion = (Obligacion) obj;
            HSSFRow row;
            row = super.getWorksheet().createRow((short) cont);

            HSSFCell cellIdObligacion = row.createCell(0);
            cellIdObligacion.setCellValue(obligacion.getDescrObliCompleta());

            HSSFCell cellConcepto = row.createCell(1);
            cellConcepto.setCellValue(obligacion.getConcepto());

            HSSFCell cellDescripcion = row.createCell(2);
            cellDescripcion.setCellValue(obligacion.getDescripcion());

            HSSFCell cellAplicaRenuentes = row.createCell(ConstantsCatalogos.TRES);
            cellAplicaRenuentes.setCellValue(obligacion.getValorBooleano().getNombre());

            cont++;
        }
    }
}
