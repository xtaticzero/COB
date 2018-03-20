package mx.gob.sat.siat.cob.seguimiento.dto.stdcob.api;

import java.sql.SQLException;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.EjecucionDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.IntentoDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Proceso;

import org.springframework.jdbc.core.PreparedStatementCreator;


/**
 *
 * @author root
 */
public final class PreparedStatementCreatorFactory {
    /**
     *
     */
    public static final int 
        EJECUCION_JOB = 1,
        /**
         *
         */
        INTENTO_JOB = 2,
        /**
         *
         */
        PROCESO = 3;
        
    /**
     *
     * @param type
     * @param param
     * @return
     * @throws SQLException
     */
    public static PreparedStatementCreator getStatementCreator(int type,Object param)throws SQLException{
        PreparedStatementCreator result = null;
        switch(type){
            case EJECUCION_JOB:
                result = new EjecucionJobPreparedStatementCreator((EjecucionDTO) param);
            break;
            case INTENTO_JOB:
                result = new IntentoJobPreparedStatementCreator((IntentoDTO) param);
            break;
            case PROCESO:
                result = new ProcesoPreparedStatementCreator((Proceso)param);
            break;
            default: result = null;
        }
        
        return result;
    }
    
    private PreparedStatementCreatorFactory(){
    }
}
