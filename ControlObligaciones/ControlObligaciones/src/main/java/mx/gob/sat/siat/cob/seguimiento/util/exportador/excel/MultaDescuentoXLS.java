package mx.gob.sat.siat.cob.seguimiento.util.exportador.excel;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MultaDescuento;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;


public class MultaDescuentoXLS extends GeneraExcel {
    /**
     *
     */
    public MultaDescuentoXLS() {
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
            MultaDescuento multa = (MultaDescuento)obj;
            HSSFRow row;
            row = super.getWorksheet().createRow((short)cont);

            HSSFCell cellResolMotivo = row.createCell(0);
            cellResolMotivo.setCellValue(multa.getResolMotivoDescr());

            HSSFCell cellMultaDescuento = row.createCell(1);
            cellMultaDescuento.setCellValue(multa.getMultaDescuentoDescr());
            
                    
                        
            cont ++;
        }
    }
}
