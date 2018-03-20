package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Funcionario;
import org.springframework.jdbc.core.RowMapper;

public class FuncionarioAprobarMapper implements RowMapper<Funcionario> {

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
        funcionario.setNumeroEmpleado(rs.getString("NUMEROEMPLEADO"));
        funcionario.setNombreFuncionario(rs.getString("NOMBREFUNCIONARIO"));
        funcionario.setCorreoElectronicoFuncionario(rs.getString("CORREOELECTRONICO"));
        funcionario.setCorreoElectronicoAlterno(rs.getString("CORREOELECTRONICOALTERNO"));
        funcionario.setFechaInicio(rs.getDate("FECHAINICIO"));
        funcionario.setFechaFin(rs.getDate("FECHAFIN"));
        funcionario.setAreaDeAdscripcion(rs.getLong("IDAREAADSCRIPCION"));
        funcionario.setDescAreaAdscripcion(rs.getString("NOMBRE"));
        return funcionario;
    }

}
