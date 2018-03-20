package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;


import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Proceso;

import org.springframework.jdbc.core.RowMapper;


public class ProcesoMapper implements RowMapper<Proceso>{
    /**
     *
     * @param resultSet
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public Proceso mapRow(ResultSet resultSet, int i) throws SQLException{
        
        
            Proceso proceso = new Proceso();
            
            proceso.setIdProceso(resultSet.getInt("IDPROCESO"));
            proceso.setNombre(resultSet.getString("NOMBRE"));
            proceso.setDescripcion(resultSet.getString("DESCRIPCION"));
            proceso.setLanzador(resultSet.getString("LANZADOR"));
            proceso.setProgramacion(resultSet.getString("PROGRAMACION"));
            proceso.setExcluir(resultSet.getString("EXCLUIR"));
            proceso.setPrioridad(resultSet.getInt("PRIORIDAD"));
            proceso.setEstado(resultSet.getInt("ESTADO"));
            proceso.setIdManager(resultSet.getString("IDMANAGER"));
            proceso.setIntentos(resultSet.getInt("INTENTOS"));
            proceso.setIntentosMax(resultSet.getInt("INTENTOSMAX"));
            proceso.setFechaInicioEjecucion(resultSet.getTimestamp("INICIOEJECUCION"));
            proceso.setFechaFinEjecucion(resultSet.getTimestamp("FINEJECUCION"));
            proceso.setTipoCadena(resultSet.getInt("TIPOPROCESAMIENTO"));
        
        return proceso;
    }
}
