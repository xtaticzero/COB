package mx.gob.sat.siat.cob.seguimiento.util.exportador.excel;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.FundamentoLegal;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;


public class FundamentoLegalXLS extends GeneraExcel{
    /**
     *
     */
    public FundamentoLegalXLS() {
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
             FundamentoLegal  fundamentoLegal = ( FundamentoLegal)obj;
            HSSFRow row;
            row = super.getWorksheet().createRow((short)cont);
            
            HSSFCell cellObligacion = row.createCell(0);
            cellObligacion.setCellValue(fundamentoLegal.getDescripcionLarga());


            HSSFCell cellRegimen = row.createCell(1);
            cellRegimen.setCellValue(fundamentoLegal.getIdRegimenDescr());


            HSSFCell cellEjercicioFiscal = row.createCell(2);
            cellEjercicioFiscal.setCellValue(fundamentoLegal.getIdEjercicioFiscalDescr());
            
            
            HSSFCell cellPeriodo = row.createCell(ConstantsCatalogos.TRES);
            cellPeriodo.setCellValue(fundamentoLegal.getIdPeriodoDescr());
            
            HSSFCell cellFechaVencimiento = row.createCell(ConstantsCatalogos.CUATRO);
            cellFechaVencimiento.setCellValue(fundamentoLegal.getFechaVencimientoStr()==null?"":fundamentoLegal.getFechaVencimientoStr().toString());            
            
            HSSFCell cellFundamento = row.createCell(ConstantsCatalogos.CINCO);
            cellFundamento.setCellValue(fundamentoLegal.getFundamentoLegal());
            
            cont ++;
        }
    }
}
