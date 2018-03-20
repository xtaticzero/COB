package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;

import java.sql.SQLException;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.FuncionarioSat;

import org.springframework.jdbc.core.RowMapper;

public class AdministracionFuncionariosMapper implements RowMapper<FuncionarioSat>{
    public AdministracionFuncionariosMapper() {
        super();
    }

    @Override
    public FuncionarioSat mapRow(ResultSet rs, int i) throws SQLException {
        FuncionarioSat fn=new FuncionarioSat();
        
        
      
        fn.setFechaFin(rs.getDate("FECHAFIN"));
        fn.setIdRegionAlr(rs.getString("IDADMONLOCAL"));
        fn.setIdCargoAdministrativo(Integer.valueOf(rs.getString("IDCARGOADMTVO")));
        fn.setDescCargoAdministrativo(rs.getString("cadesc"));
        fn.setIdEventoCarga(Integer.valueOf(rs.getString("IDEVENTOCARGA")));
        fn.setDescEventoCarga(rs.getString("evdesc"));
        fn.setNivelEmision(Integer.valueOf(rs.getString("IDNIVELEMISION")));
        fn.setDescNivelEmision(rs.getString("emdesc"));
        fn.setNumeroEmpleado(rs.getString("NUMEROEMPLEADO"));
        fn.setNumeroTabla(rs.getString("NUMEROEMPLEADO").replaceFirst("^0+(?!$)", ""));
        fn.setNombreEmpleado(rs.getString("NOMBRE"));
        fn.setNombreAlsc(rs.getString("descripcionalsc"));
     
        
        
        return fn;
    }
}
