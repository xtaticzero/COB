/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.afectaciones.impl;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.GrupoAfectacionCumpDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.util.PoolThread;
import static mx.gob.sat.siat.cob.seguimiento.util.PoolThread.FINISHED;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.CruceCumplimientosService;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.PoolThreadAfectacionCumplimientoService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author Juan
 */
@Service(value = "PoolThreadAfectacionCumplimientoService")
public class PoolThreadAfectacionCumplimientoServiceImpl extends PoolThread
        implements PoolThreadAfectacionCumplimientoService, Runnable {

    private static Integer erroresHilos = 0;
    private static Logger log = Logger.getLogger(PoolThreadAfectacionCumplimientoServiceImpl.class);
    private CruceCumplimientosService cruceCumplimiento;
    private GrupoAfectacionCumpDTO grupoAfectacionCumpDTO;
    private List<EstadoDocumentoEnum> estados;

    @Override
    public void ejecutaCruceAfectacionCumplimientos(GrupoAfectacionCumpDTO grupoAfectacionCumpDTO, List<EstadoDocumentoEnum> estados) throws SGTServiceException {
        try {
            cruceCumplimiento.cruceAfectacionCumplimientos(grupoAfectacionCumpDTO, estados);
        } catch (Exception ex) {
            log.error("Error al traer los cumplimientos para la vigilancia  "
                    + grupoAfectacionCumpDTO.getVigilancia() + " y la local " + grupoAfectacionCumpDTO.getIdAdmonLocal() + "\n" + ex);
            aumentaErrores();
        } finally {
            setEstado(FINISHED);
        }
    }

    @Override
    public void run() {
        try {
            ejecutaCruceAfectacionCumplimientos(grupoAfectacionCumpDTO, estados);
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

    public void setEstados(List<EstadoDocumentoEnum> estados) {
        this.estados = estados;
    }

    public static Integer getErroresHilos() {
        return erroresHilos;
    }

    private static synchronized void aumentaErrores() {
        erroresHilos= erroresHilos +1;
    }

    public static void reiniciaErroresHilos() {
        PoolThreadAfectacionCumplimientoServiceImpl.erroresHilos = 0;
    }
}
