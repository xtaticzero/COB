package mx.gob.sat.siat.cob.seguimiento.util.exportador.excel;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Funcionario;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;


public class FuncionarioXLS extends GeneraExcel{
    
    public FuncionarioXLS() {
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
            Funcionario funcionario = (Funcionario)obj;
            HSSFRow row;
            row = super.getWorksheet().createRow((short)cont);

            HSSFCell cellNumeroEmpleado = row.createCell(0);
            cellNumeroEmpleado.setCellValue(funcionario.getNumeroEmpleado());

            HSSFCell cellNombreFuncionario = row.createCell(1);
            cellNombreFuncionario.setCellValue(funcionario.getNombreFuncionario());
            
            HSSFCell cellCargo = row.createCell(2);
            cellCargo.setCellValue(funcionario.getDescripcionCargo());
            
            HSSFCell cellArea= row.createCell(ConstantsCatalogos.TRES);
            cellArea.setCellValue(funcionario.getDescAreaAdscripcion());
            
            HSSFCell cellCorreoElectronico = row.createCell(ConstantsCatalogos.CUATRO);
            cellCorreoElectronico.setCellValue(funcionario.getCorreoElectronicoFuncionario());
            
            HSSFCell cellCorreoElectronicoAlterno = row.createCell(ConstantsCatalogos.CINCO);
            cellCorreoElectronicoAlterno.setCellValue(funcionario.getCorreoElectronicoAlterno());
                      
            HSSFCell cellSituacion = row.createCell(6);
            cellSituacion.setCellValue(funcionario.getSituacion());
            
            cont ++;
            
        }
    }
    }
