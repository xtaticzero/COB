package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Funcionario;
import org.springframework.jdbc.core.RowMapper;

public class FuncionarioMapper implements RowMapper<Funcionario>{
    public FuncionarioMapper() {
        super();
    }
    
    /**
     *
     * @param rs
     * @param rowNum
     * @return
     * @throws SQLException
     */
    @Override
    public Funcionario mapRow(ResultSet rs, int rowNum) throws SQLException {
        Funcionario funcionario = new Funcionario();
        
       
        String sqlNumber=rs.getString("NUMEROEMPLEADO");
        String noCeros=sqlNumber.replaceFirst("^0+(?!$)", "");
        funcionario.setNumeroEmpleado(noCeros);
        funcionario.setNombreFuncionario(rs.getString("NOMBREFUNCIONARIO"));
        funcionario.setCorreoElectronicoFuncionario(rs.getString("CORREOELECTRONICO"));
        funcionario.setCorreoElectronicoAlterno(rs.getString("CORREOELECTRONICOALTERNO"));
        funcionario.setFechaInicio(rs.getDate("FECHAINICIO"));
        funcionario.setFechaFin(rs.getDate("FECHAFIN"));
        funcionario.setAreaDeAdscripcion(rs.getLong("IDAREAADSCRIPCION"));
        funcionario.setDescAreaAdscripcion(rs.getString("NOMBRE"));
        funcionario.setDescripcionCargo(rs.getString("DESCRIPCIONCARGO"));
        if (rs.getDate("FECHAFIN")==null){
            funcionario.setFechaFinStr("1");
            funcionario.setSituacion("Activo");
        }else {
            funcionario.setFechaFinStr("0");
            funcionario.setSituacion("Inactivo");
        }
                
        return funcionario;
    }

}
