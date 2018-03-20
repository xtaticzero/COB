package mx.gob.sat.siat.cob.seguimiento.util.exportador.excel;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.EmailReporteProceso;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

public class EmailReporteProcesoXLS extends GeneraExcel{
    
    /**
     *
     * @param datos
     */
    @Override
    public void colocarContenido(List<Object> datos) {
        int cont= 2;
        for(Object obj : datos){
            EmailReporteProceso emailReporteProceso = (EmailReporteProceso)obj;
            HSSFRow row;
            row = super.getWorksheet().createRow((short)cont);

            HSSFCell cellNombre = row.createCell(0);
            cellNombre.setCellValue(emailReporteProceso.getNombreCompleto());

            HSSFCell cellCorreo = row.createCell(1);
            cellCorreo.setCellValue(emailReporteProceso.getCorreoElectronico());
            
            HSSFCell cellCorreoAlterno = row.createCell(2);
            cellCorreoAlterno.setCellValue(emailReporteProceso.getCorreoElectronicoAlterno());

            cont ++;
        }
    }
}