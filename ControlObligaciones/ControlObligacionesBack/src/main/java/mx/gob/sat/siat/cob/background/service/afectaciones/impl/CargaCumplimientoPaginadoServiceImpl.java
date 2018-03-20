/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.service.afectaciones.impl;

import java.util.Date;
import mx.gob.sat.siat.cob.background.service.afectaciones.CargaCumplimientoService;
import mx.gob.sat.siat.cob.seguimiento.dao.dbd2.HistoricoCumplimientoDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ParametrosSgtDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.ParametroSgtEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.otros.SGTService;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import org.apache.log4j.Logger;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author root
 */
@Service("cargaCumplimientoPaginadoService")
public class CargaCumplimientoPaginadoServiceImpl implements  CargaCumplimientoService{
    private Logger log = Logger.getLogger(CargaCumplimientoPaginadoServiceImpl.class);
    @Autowired
    @Qualifier("jobLauncher")
    private JobLauncher jobLauncher;
    @Autowired
    @Qualifier("cargaCumplimientoPaginadoJob")
    private Job job;
    @Autowired
    @Qualifier("jobRepository")
    private MapJobRepositoryFactoryBean mapJobRepositoryFactoryBean;
    @Autowired
    private HistoricoCumplimientoDAO historicoCumplimientoDAO;
    @Autowired
    private SGTService sgtService;

    
    @Override
    public void cargarCumplimientos(Date fechaExtraccion) throws SGTServiceException {
        Long totalRegistrosAyer;
        Long paginadoRegistros;
        Long registroInicial=1L;
        Long registroFinal;
        int iteraciones=0;
        
        ParametrosSgtDTO parametro = sgtService.obtenerParametroSgtPorIdParametro(ParametroSgtEnum.TAMANO_BLOQUE_EXTRACCION_CUMPLIMIENTO_PAGINADO.getValor());
        
        log.info("La paginacion es de " + parametro.getValor());
        
        paginadoRegistros= Long.valueOf(parametro.getValor());
        
        registroFinal= paginadoRegistros;
        
        JobParameters param;
        JobExecution execution;
        
            
        totalRegistrosAyer= historicoCumplimientoDAO.consultarTamanio(fechaExtraccion);
        
        log.info("La fecha de extracción es " + fechaExtraccion + " y el total de registros a insertar es " + totalRegistrosAyer);
        try {            
            log.info("Inicia el proceso carga cumplimientos...");           
            do{                
                log.info("Realizando iteracion " + iteraciones);
                param = new JobParametersBuilder()
                        .addString("fecha", Utilerias.
                            formatearFechaAAAAMMDD(fechaExtraccion))
                        .addLong("filaInicio", registroInicial)
                        .addLong("filaFin", registroFinal)
                        .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
                execution = jobLauncher.run(job, param);
                 if(execution!=null && execution.getStatus().equals(BatchStatus.FAILED)) {
                        throw new SGTServiceException("Error al realizar la carga de cumplimientos");
                }
                iteraciones++;
                param=null;
                execution=null;
                mapJobRepositoryFactoryBean.clear();
                System.gc();
                registroInicial=(iteraciones * paginadoRegistros) +1;
                registroFinal+=paginadoRegistros;
            }while(registroInicial<=totalRegistrosAyer);    
            log.info("Termina proceso carga cumplimientos");
        } catch (JobExecutionException e) {
            throw new SGTServiceException("No se pudo realizar la carga de cumplimientos\n" +e);
        } 
    }
}
