/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.service.renuente.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import mx.gob.sat.siat.cob.background.service.renuente.GeneraArchivoRenuentes;
import mx.gob.sat.siat.cob.background.util.UtileriasBackground;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.BuscaRenuentesDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.PeriodoBusquedaDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.BuscaRenuentes;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.PeriodicidadHelper;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoServiciosConcurrentesEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.otros.ConcurrenceService;
import mx.gob.sat.siat.cob.seguimiento.service.renuente.BuscaRenuentesService;
import mx.gob.sat.siat.cob.seguimiento.util.PoolThread;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import org.apache.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author juan
 */
@Service("generaArchivoRenuentes")
public class GeneraArchivoRenuentesImpl implements GeneraArchivoRenuentes {

    private Logger log = Logger.getLogger(GeneraArchivoRenuentesImpl.class);
    @Autowired
    @Qualifier("jobLauncher")
    private JobLauncher launcher;
    @Autowired(required = false)
    @Qualifier("archivoRenuenteJob")
    private Job archivoRenuenteJob;
    @Autowired
    private ConcurrenceService concurrenceService;
    @Autowired
    private BuscaRenuentesDAO buscaRenuentesDAO;
    @Autowired
    private PeriodoBusquedaDAO periodoBusquedaDAO;
    @Autowired
    private BuscaRenuentesService buscaRenuentesService;

    private final static int CIEN = 100;

    /**
     * Metodo encargado de generar archivos en el server dependiendo los datos
     * de busqueda.
     *
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException
     */
    @Override
    public void generarArchivo() throws SGTServiceException {
        try {
            List<BuscaRenuentes> renuentes = buscaRenuentesDAO.registroSinFinalizar();
            ejecutaJobGeneraArchivoRenuente(renuentes);
            for (BuscaRenuentes renuente : renuentes) {
                buscaRenuentesService.enviaCorreo(renuente);
            }
            if (isProcesoBloqueado()) {
                desbloquearProceso();
            }
        } catch (SeguimientoDAOException sde) {
            log.error(sde);
            throw new SGTServiceException(sde);
        }
    }

    /**
     * Manda a ejecutar el job, con los parametros; esta ejecucion esta
     * integradas con PoolThread.
     */
    private void ejecutaJobGeneraArchivoRenuente(List<BuscaRenuentes> renuentes) throws SeguimientoDAOException {
        try {
            Properties propertiesHilos = UtileriasBackground.obtenProperties(ConstantsCatalogos.RUTA_THREADS);
            ExecutorService executor = Executors.newFixedThreadPool(
                    Integer.parseInt(propertiesHilos.getProperty("thread.cantidad")));

            List<PoolThread> threads = new ArrayList<PoolThread>();
            for (BuscaRenuentes renuente : renuentes) {
                List<PeriodicidadHelper> periodicidades
                        = periodoBusquedaDAO.selectPorIdBusqueda(
                                renuente.getIdBusquedaRenuente());
                Map<String, String> referencias = parametrosSQL(renuente, periodicidades);
                referencias.put("idsBusqRen", renuente.getIdBusquedaRenuente().toString());
                ArchivoRenuenteThreadServiceImpl renuenteThread = new ArchivoRenuenteThreadServiceImpl();

                renuenteThread.setArchivoRenuenteJob(archivoRenuenteJob);
                renuenteThread.setLauncher(launcher);
                renuenteThread.setReferencias(referencias);

                executor.execute(renuenteThread);
                threads.add(renuenteThread);
            }

            Utilerias.terminaProceso(executor, threads);
            Utilerias.interrumpirProceso(executor);
            log.info("Fin Job Genera Archivo Renuente.");
        } catch (InterruptedException ex) {
            log.error(ex);
        }
    }

    /**
     * Retorna los valores que se le enviaran al job para su ejecucion.
     */
    private Map<String, String> parametrosSQL(BuscaRenuentes renuente, List<PeriodicidadHelper> periodicidades) {
        Map<String, String> parametros = new HashMap<String, String>();

        parametros.put("idBusqRenuente", renuente.getIdBusquedaRenuente().toString());
        parametros.put("rutaArchivo", renuente.getRutaArchivo());
        parametros.put("idTipoDocumento", Utilerias.separadoPorComas(renuente.getSelectedTipoDocumento().toString()));
        parametros.put("ultimoEstado", renuente.getEstadoDocumento().toString());
        parametros.put("idObligaciones", Utilerias.separadoPorComas(renuente.getSelectedObligaciones().toString()));
        parametros.put("wherePB", obtenerFechaPeriodo(periodicidades));

        return parametros;
    }

    /**
     * Construye las condiciones necesarias por periodicidades.
     */
    private String obtenerFechaPeriodo(List<PeriodicidadHelper> periodicidades) {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator itr = periodicidades.iterator();
        while (itr.hasNext()) {
            PeriodicidadHelper periodo = (PeriodicidadHelper) itr.next();

            String mesInicial = periodo.getPeriodoInicialSelected().trim().split("\\|")[0];
            String mesFinal = periodo.getPeriodoFinalSelected().trim().split("\\|")[0];
            String anioInicial = periodo.getEjercicioInicialSelected();
            String anioFinal = periodo.getEjercicioFinalSelected();

            Integer valorInicial = Integer.valueOf(anioInicial) * CIEN + Integer.valueOf(mesInicial);
            Integer valorFinal = Integer.valueOf(anioFinal) * CIEN + Integer.valueOf(mesFinal);

            stringBuilder.append("(detalle.IdPeriodicidad = '").
                    append(periodo.getPeriodicidad().getIdString()).
                    append("' AND(detalle.ejercicio*100+detalle.idPeriodo>=(").
                    append(valorInicial).
                    append(") AND (detalle.ejercicio*100+detalle.idPeriodo<=").
                    append(valorFinal).
                    append(")))");
            if (itr.hasNext()) {
                stringBuilder.append("OR");
            } else {
                stringBuilder.append(")");
            }
        }
        return stringBuilder.toString();
    }

    private boolean isProcesoBloqueado() {
        String proceso = "buscaRenuentes";
        return concurrenceService.isLockedServices(
                TipoServiciosConcurrentesEnum.IDENTIFICADOR_BUSCARENUENTES, proceso);
    }

    private void desbloquearProceso() throws SGTServiceException {
        log.info("Desbloqueando el proceso datosProceso");
        if (!concurrenceService.unlockServices(
                TipoServiciosConcurrentesEnum.IDENTIFICADOR_BUSCARENUENTES, "buscaRenuentes")) {
            throw new SGTServiceException("No se pudo hacer el desbloqueo de este proceso.");
        }
    }
}
