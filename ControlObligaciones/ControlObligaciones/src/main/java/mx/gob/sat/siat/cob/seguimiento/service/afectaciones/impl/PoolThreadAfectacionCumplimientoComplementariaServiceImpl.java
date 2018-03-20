package mx.gob.sat.siat.cob.seguimiento.service.afectaciones.impl;

import java.util.List;
import java.util.Map;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.GrupoAfectacionCumpDTO;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.util.PoolThread;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.CruceCumplimientosService;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.PoolThreadAfectacionCumplimientoComplementariaService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author Juan
 */
@Service(value = "PoolThreadAfectacionCumplimientoComplementariaService")
public class PoolThreadAfectacionCumplimientoComplementariaServiceImpl extends PoolThread
        implements PoolThreadAfectacionCumplimientoComplementariaService, Runnable {

    private static Integer erroresHilos = 0;
    private Logger log = Logger.getLogger(PoolThreadAfectacionCumplimientoComplementariaServiceImpl.class);
    private CruceCumplimientosService cruceCumplimiento;
    private GrupoAfectacionCumpDTO grupoAfectacionCumpDTO;
    private Map condiciones;
    private List<Integer> tiposDeclaracion;

    @Override
    public void ejecutaCruceAfectacionCumplimientosComplementaria(GrupoAfectacionCumpDTO grupoAfectacionCumpDTO, Map condiciones, List<Integer> tiposDeclaracion) throws SGTServiceException {
        try {
            cruceCumplimiento.cruceAfectacionCumplimientosComplementaria(grupoAfectacionCumpDTO, condiciones, tiposDeclaracion);
        } catch (Exception ex) {
            log.error("Error al traer los cumplimientos complementarios para la vigilancia  "
                    + grupoAfectacionCumpDTO.getVigilancia() + " y la local " + grupoAfectacionCumpDTO.getIdAdmonLocal() + "\n" + ex);
            aumentaErrores();
        } finally {
            setEstado(FINISHED);
        }
    }

    @Override
    public void run() {
        try {
            ejecutaCruceAfectacionCumplimientosComplementaria(grupoAfectacionCumpDTO, condiciones, tiposDeclaracion);
        } catch (SGTServiceException ex) {
            log.error(ex);
        }
    }

    public void setCruceCumplimiento(CruceCumplimientosService cruceCumplimiento) {
        this.cruceCumplimiento = cruceCumplimiento;
    }

    public void setGrupoAfectacionCumpDTO(GrupoAfectacionCumpDTO grupoAfectacionCumpDTO) {
        this.grupoAfectacionCumpDTO = grupoAfectacionCumpDTO;
    }

    public void setCondiciones(Map condiciones) {
        this.condiciones = condiciones;
    }

    public void setTiposDeclaracion(List<Integer> tiposDeclaracion) {
        this.tiposDeclaracion = tiposDeclaracion;
    }

    public static Integer getErroresHilos() {
        return erroresHilos;
    }

    public synchronized static void aumentaErrores() {
        erroresHilos= erroresHilos +1;
    }
    public static void reiniciaErroresHilos() {
       PoolThreadAfectacionCumplimientoComplementariaServiceImpl.erroresHilos=0; 
    }
}
