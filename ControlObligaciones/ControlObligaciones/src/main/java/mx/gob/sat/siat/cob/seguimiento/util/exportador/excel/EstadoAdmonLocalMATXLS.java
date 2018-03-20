package mx.gob.sat.siat.cob.seguimiento.util.exportador.excel;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.EstadoAdmonLocalMAT;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

public class EstadoAdmonLocalMATXLS extends GeneraExcel {

    public EstadoAdmonLocalMATXLS() {
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
            EstadoAdmonLocalMAT estadoAdmonLocalMAT = (EstadoAdmonLocalMAT) obj;
            HSSFRow row;
            row = super.getWorksheet().createRow((short) cont);

            HSSFCell cellIdAdmonLocal = row.createCell(ConstantsCatalogos.CERO);
            cellIdAdmonLocal.setCellValue(estadoAdmonLocalMAT.getIdAdmonLocal());

            HSSFCell cellNombre = row.createCell(ConstantsCatalogos.UNO);
            cellNombre.setCellValue(estadoAdmonLocalMAT.getEntidadDesc());

            HSSFCell cellEsOperacionMAT = row.createCell(ConstantsCatalogos.DOS);
            cellEsOperacionMAT.setCellValue(estadoAdmonLocalMAT.getEsOperacionMATDesc());

            cont++;
        }
    }
}
