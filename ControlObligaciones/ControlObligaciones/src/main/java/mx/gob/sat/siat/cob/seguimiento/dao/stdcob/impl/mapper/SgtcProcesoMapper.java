/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.SgtcProcesoDTO;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author emmanuel
 */
public class SgtcProcesoMapper implements RowMapper<SgtcProcesoDTO>{

    @Override
    public SgtcProcesoDTO mapRow(ResultSet rs, int i) throws SQLException {
        SgtcProcesoDTO sgtcProcesoDTO = new SgtcProcesoDTO();
                
        sgtcProcesoDTO.setIdProceso(rs.getInt("IDPROCESO"));
        sgtcProcesoDTO.setNombre(rs.getString("NOMBRE"));
        sgtcProcesoDTO.setDescripcion(rs.getString("DESCRIPCION"));
        sgtcProcesoDTO.setLanzador(rs.getString("LANZADOR"));        
        sgtcProcesoDTO.setProgramacion(rs.getString("PROGRAMACION"));
        sgtcProcesoDTO.setExcluir(rs.getString("EXCLUIR"));
        sgtcProcesoDTO.setPrioridad(rs.getInt("PRIORIDAD"));
        sgtcProcesoDTO.setEstado(rs.getInt("ESTADO"));
        sgtcProcesoDTO.setIntentos(rs.getInt("INTENTOS"));
        sgtcProcesoDTO.setIntentosMax(rs.getInt("INTENTOSMAX"));        
        sgtcProcesoDTO.setIdManager(rs.getString("IDMANAGER"));
        sgtcProcesoDTO.setIntentoEjecucion(rs.getDate("INICIOEJECUCION"));
        sgtcProcesoDTO.setFinEjecucion(rs.getDate("FINEJECUCION"));
        sgtcProcesoDTO.setTipoProcesamiento(rs.getInt("TIPOPROCESAMIENTO"));
        
        return sgtcProcesoDTO;
    }
    
}
