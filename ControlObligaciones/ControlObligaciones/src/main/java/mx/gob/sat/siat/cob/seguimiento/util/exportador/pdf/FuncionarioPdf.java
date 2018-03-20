package mx.gob.sat.siat.cob.seguimiento.util.exportador.pdf;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Funcionario;

public class FuncionarioPdf extends GeneraPdf{
    
    public FuncionarioPdf() {
        super();
    }
    
    /**
     *
     * @param datos
     */
    @Override
    public void colocarDatos(List<Object> datos) {
        for(Object obj : datos){
            Funcionario funcionario = (Funcionario)obj;
            super.getTb().addCell(funcionario.getNumeroEmpleado());
            super.getTb().addCell(funcionario.getNombreFuncionario());
            super.getTb().addCell(funcionario.getDescripcionCargo());
            super.getTb().addCell(funcionario.getDescAreaAdscripcion());
            super.getTb().addCell(funcionario.getCorreoElectronicoFuncionario());
            super.getTb().addCell(funcionario.getCorreoElectronicoAlterno());
            super.getTb().addCell(funcionario.getSituacion());
        }
    }
}

