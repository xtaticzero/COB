package mx.gob.sat.siat.cob.seguimiento.util.exportador.excel;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MultaMonto;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;


public class MultaMontoXLS extends GeneraExcel{
    /**
     *
     */
    public MultaMontoXLS() {
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
                MultaMonto oblisancion = (MultaMonto)obj;
                HSSFRow row;
                row = super.getWorksheet().createRow((short)cont);

                HSSFCell cellIdObligacion = row.createCell(0);
                cellIdObligacion.setCellValue(oblisancion.getDescripcionLarga());
                
                HSSFCell cellMotivoDescripcion = row.createCell(1);
                cellMotivoDescripcion.setCellValue(oblisancion.getResolMotivoDescr());    

                HSSFCell cellIdOblisancion = row.createCell(2);
                cellIdOblisancion.setCellValue(oblisancion.getSancion());
                
                HSSFCell cellIdregimen = row.createCell(ConstantsCatalogos.TRES);
                cellIdregimen.setCellValue(oblisancion.getInfraccion());
                
                HSSFCell cellMotivacion = row.createCell(ConstantsCatalogos.CUATRO);
                cellMotivacion.setCellValue(oblisancion.getMotivacion());
                
                HSSFCell cellMonto = row.createCell(ConstantsCatalogos.CINCO);
                cellMonto.setCellValue(oblisancion.getMonto());
                
                HSSFCell cellDescripcionMonto = row.createCell(ConstantsCatalogos.SEIS);
                cellDescripcionMonto.setCellValue(oblisancion.getDescripcionMonto());
                
                HSSFCell cellFechaInicio = row.createCell(ConstantsCatalogos.SIETE);
                cellFechaInicio.setCellValue(oblisancion.getFechaInicioStr());
                
                HSSFCell cellFechaFin = row.createCell(ConstantsCatalogos.OCHO);
                cellFechaFin.setCellValue(oblisancion.getFechaFinStr());

                cont ++;
            }
        }
   
}
