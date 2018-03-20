package mx.gob.sat.siat.cob.background.service.job;

/**
 *
 * @author christian.ventura
 */
public interface RespaldosRegistroJobService {
    
    /**
     * para guardar registros en las tablas de historicos de ejecucion e intento
     */
    void ejecutarRespaldo();
    
}
