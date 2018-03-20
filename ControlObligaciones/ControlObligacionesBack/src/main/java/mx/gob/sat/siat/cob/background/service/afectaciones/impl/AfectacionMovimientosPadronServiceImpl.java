/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.service.afectaciones.impl;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import mx.gob.sat.siat.cob.background.service.afectaciones.AfectacionMovimientosPadronService;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DetalleDocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.EjecucionSeguimientoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.ParametroSgtDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ParametrosSgtDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EjecucionSeguimientoEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.ParametroSgtEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.ProcesoAfectacionMovimientosPadronService;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;

import org.apache.log4j.Logger;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author root
 */
@Service("afectacionMovimientosPadronService")
public class AfectacionMovimientosPadronServiceImpl implements AfectacionMovimientosPadronService {

    @Autowired
    @Qualifier("jobLauncher")
    private JobLauncher jobLauncher;
    @Autowired
    @Qualifier("afectacionMovimientosPadronJob")
    private Job job;
    @Autowired
    private DocumentoDAO documentoDAO;
    @Autowired
    private ProcesoAfectacionMovimientosPadronService procesoAfectacionMovimientosPadronService;
    @Autowired
    private DetalleDocumentoDAO detalleDocumentoDAO;
    @Autowired
    private ParametroSgtDAO parametroSgtDAO;
    @Autowired
    private EjecucionSeguimientoDAO ejecucionSeguimientoDAO;
    private final Logger log = Logger.getLogger(AfectacionMovimientosPadronServiceImpl.class);

    @Override
    public void cargarMovimientoPadron() throws SGTServiceException {
        EjecucionSeguimientoEnum ejecucionSeguimientoEnum = null;
        try {
            ejecucionSeguimientoEnum = ejecucionSeguimientoDAO.enEjecucion();
        } catch (SeguimientoDAOException e) {
            log.error(e);
            Utilerias.getDetalleExcepcion(e);
        }
        if (!ejecucionSeguimientoEnum.equals(EjecucionSeguimientoEnum.PROCESANDO)) {
            log.error("<--cargarMovimientoPadron La bandera de procesamiento se encuentra inactiva");
            return;
        }

        StringBuilder sb = new StringBuilder("");
        try {
            log.info("Inicia el proceso...");
            Date fechaProcesamiento = null;
            ParametrosSgtDTO parametrosSgtDTO = parametroSgtDAO.obtenerParametroSgtPorIdParametro(ParametroSgtEnum.FECHA_ULTIMA_EJECUCION_BAJA_POR_PADRON.getValor());
            if (parametrosSgtDTO.getValor() == null) {
                fechaProcesamiento = Utilerias.getYesterday();
            } else {
                Calendar calendar = Calendar.getInstance();
                fechaProcesamiento = Utilerias.formatearFechaDDMMAAAAHHMM(parametrosSgtDTO.getValor());
                if (fechaProcesamiento.compareTo(Utilerias.getYesterday()) >= 0) {
                    fechaProcesamiento = null;
                } else {
                    calendar.setTime(fechaProcesamiento);
                    calendar.add(Calendar.DAY_OF_YEAR, 1);
                    fechaProcesamiento = calendar.getTime();
                }
            }//21-04-2014
            if (fechaProcesamiento != null) {
                JobParameters param = new JobParametersBuilder().
                        addString("fecha",
                        //TODO: Para produccion Utilerias.formatearFechaAAAAMMDD(Utilerias.getYesterday())).
                        Utilerias.formatearFechaAAAAMMDD(fechaProcesamiento)).
                        toJobParameters();
                JobExecution jobExe = jobLauncher.run(job, param);
                for (Throwable th : jobExe.getAllFailureExceptions()) {
                    throw new SGTServiceException(th.toString());
                }
                parametrosSgtDTO.setValor(Utilerias.formatearFechaDDMMYYYY(fechaProcesamiento));
                parametroSgtDAO.actualizarParametroSgt(parametrosSgtDTO);
            } else {
                sb.append("La ultima fecha de extraccion es:");
                sb.append(parametrosSgtDTO.getValor());
                log.error(sb.toString());
            }
        } catch (JobParametersInvalidException e) {
            log.error(e);
            Utilerias.getDetalleExcepcion(e);
        } catch (JobExecutionAlreadyRunningException e) {
            log.error(e);
            Utilerias.getDetalleExcepcion(e);
        } catch (JobInstanceAlreadyCompleteException e) {
            log.error(e);
            Utilerias.getDetalleExcepcion(e);
        } catch (JobRestartException e) {
            log.error(e);
            Utilerias.getDetalleExcepcion(e);
        } catch (ParseException e) {
            log.error(e);
            Utilerias.getDetalleExcepcion(e);
        } catch (SeguimientoDAOException e) {
            log.error(e);
            Utilerias.getDetalleExcepcion(e);
        }
    }

    @Override
    public void afectarPorMovimientoEnPadron() throws SGTServiceException {
        //Obtener documentos que tienen algun movimiento en el padron
        EjecucionSeguimientoEnum ejecucionSeguimientoEnum = null;
        try {
            ejecucionSeguimientoEnum = ejecucionSeguimientoDAO.enEjecucion();

        } catch (SeguimientoDAOException e) {
            log.error(e.toString());
        }

        if (!ejecucionSeguimientoEnum.equals(EjecucionSeguimientoEnum.PROCESANDO)) {
            log.error("<--afectarPorMovimientoEnPadron La bandera de procesamiento se encuentra inactiva");
            return;
        }

        log.info("-->afectarPorMovimientoEnPadron");
        log.info("Retrieving documentos...");
        Date t1 = new Date();
        List<Documento> documentos = documentoDAO.listarDocumentosConBajaEnPadron();
        Date t2 = new Date();
        log.info("Retrieving documentos Done! Time elapsed:" + Utilerias.getDateDiff(t1, t2, TimeUnit.MINUTES) + " mins.");
        if (documentos == null) {
            log.info("No existen movimientos en el padron actualmente, revise que el proceso de carga este funcionando correctamente");
        } else {
            List<Documento> documentosNoProcesados = new LinkedList<Documento>();
            for (Documento documento : documentos) {
                try {
                    log.info("Procesando documento " + documento.getBoid() + "...");
                    t1 = new Date();
                    procesarDocumento(documento);
                    t2 = new Date();
                    log.info("Procesando documento " + documento.getBoid() + " Done! Time elapsed:" + Utilerias.getDateDiff(t1, t2, TimeUnit.MINUTES) + " mins.");
                } catch (SGTServiceException e) {
                    log.error(e);
                    documentosNoProcesados.add(documento);
                }
            }
            log.info("Borrando datos baja Icep ...");
            t1 = new Date();
            procesoAfectacionMovimientosPadronService.borrarDatosBajaIcep(documentosNoProcesados);
            t2 = new Date();
            log.info("Borrando datos baja Icep Done!Time elapsed:" + Utilerias.getDateDiff(t1, t2, TimeUnit.MINUTES) + " mins.");
        }
        log.info("<--afectarPorMovimientoEnPadron");
    }

    @Override
    public void procesarDocumento(Documento documento) throws SGTServiceException {
        List<DetalleDocumento> detalles = detalleDocumentoDAO.consultaXNumControl(
                documento.getNumeroControl());
        if (detalles == null) {
            throw new SGTServiceException("No se pudo obtener los detalles del documento");
        }

        if (isEstadoValido(documento)) {
            if (detalles.size() == documento.getDetalles().size()) {
                //Total
                procesoAfectacionMovimientosPadronService.solventarPorMovimientoAlPadronTotal(documento);
            } else {
                //Parcial
                procesoAfectacionMovimientosPadronService.solventarPorMovimientoAlPadronParcial(documento);
            }
        } else {
            // No hace nada
            log.debug("Estado no válido para afectación por movimientos al padrón:" + documento.getEstadoDocumento().getValor());
        }

    }

    private boolean isEstadoValido(Documento documento) {
        List<EstadoDocumentoEnum> estadosNoValidos = new ArrayList<EstadoDocumentoEnum>();
        estadosNoValidos.add(EstadoDocumentoEnum.CANCELADO_POR_AUTORIDAD);
        estadosNoValidos.add(EstadoDocumentoEnum.NO_PROCESADO);
        estadosNoValidos.add(EstadoDocumentoEnum.ANULADO);
        estadosNoValidos.add(EstadoDocumentoEnum.NO_LOCALIZADO);
        estadosNoValidos.add(EstadoDocumentoEnum.NO_TRABAJADO);
        for (EstadoDocumentoEnum estadoDocumentoEnum : estadosNoValidos) {
            if (documento.getEstadoDocumento().equals(estadoDocumentoEnum)) {
                return false;
            }
        }
        return true;
    }
}
