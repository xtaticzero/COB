/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.afectaciones.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.CumplimientoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DetalleDocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.MultaComplementariaDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.TipoDeclaracionDAO;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.GrupoAfectacionCumpDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.IndicadorCumplimientoEnum;
import static mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.IndicadorCumplimientoEnum.PARCIAL;
import static mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.IndicadorCumplimientoEnum.TOTAL;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.SituacionIcepEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TieneMultaEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoDeclaracionEnum;
import static mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoDocumentoEnum.CARTA_EXHORTO;
import static mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoDocumentoEnum.REQUERIMIENTO_ACUMULADO;
import static mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoDocumentoEnum.REQUERIMIENTO_DIOT;
import static mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoDocumentoEnum.REQUERIMIENTO_MULTA;
import static mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoDocumentoEnum.REQUERIMIENTO_NORMAL;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.util.PoolThread;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.CambiaEstadosCumplimientoService;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.CruceCumplimientosService;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.ProcesoAfectacionCumplimientoService;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author root
 */
@Service
public class ProcesoAfectacionCumplimientoServiceImpl implements ProcesoAfectacionCumplimientoService {

    
    @Autowired
    private DetalleDocumentoDAO detalleDocumentoDAO;
    @Autowired
    private CruceCumplimientosService cruceCumplimientosService;
    @Autowired
    private CumplimientoDAO cumplimientoDAO;
    @Autowired
    private MultaComplementariaDAO multaComplementariaDAO;
    @Autowired
    private TipoDeclaracionDAO tipoDeclaracionDAO;
    @Autowired
    private CambiaEstadosCumplimientoService cambiaEstadosCumplimientoService;
    private final Logger log = Logger.getLogger(ProcesoAfectacionCumplimientoServiceImpl.class);

    /**
     *
     * @param documento
     * @throws SGTServiceException
     */
    @Override
    public void procesarCumplimiento(Documento documento) throws SGTServiceException {

        log.info("procesando el documento " + documento.getNumeroControl());
        if (!verificaIntegridadDocumento(documento)){
            return;
        }

        //Obtener los detalles del documento
        documento.setDetalles(detalleDocumentoDAO.
                consultaXNumControl(documento.getNumeroControl()));
        switch (documento.getVigilancia().getIdTipoDocumentoEnum()) {
            case CARTA_EXHORTO:
                procesarCartaExhorto(documento);
                break;
            case REQUERIMIENTO_MULTA:
            case REQUERIMIENTO_NORMAL:
            case REQUERIMIENTO_DIOT:
            case REQUERIMIENTO_ACUMULADO:
                procesarRequerimiento(documento);
                break;
            default:
                log.info("Se recibe un tipo de documento " + documento.getVigilancia().getIdTipoDocumentoEnum());
                break;
        }


    }
   

    /**
     *
     * @param documento Documento del que se desea obtener el indicador de
     * cumplimiento
     *
     * Indica si el documento se cumplio parcialmente o totalmente en el
     * atributo indicador cumplimiento del objeto documento
     */
    private void determinarIndicadorCumplimiento(Documento documento) {
        documento.setIndicadorCumplimiento(IndicadorCumplimientoEnum.TOTAL);
        for (DetalleDocumento dd : documento.getDetalles()) {
            if (dd.getFechaCumplimiento() == null
                    && !(dd.getSituacionIcep() == SituacionIcepEnum.CANCELADO_POR_MOVIMIENTOS_PADRON)) {
                documento.setIndicadorCumplimiento(IndicadorCumplimientoEnum.PARCIAL);
                return;
            }
        }
    }
    private void procesarCartaExhorto(Documento documento) throws SGTServiceException {
        //determinamos el indicador de cumplimiento
        determinarIndicadorCumplimiento(documento);
        documento.setDetalles(icepsCumplidosSesion(documento));
        switch (documento.getIndicadorCumplimiento()) {
            case TOTAL:
                cambiaEstadosCumplimientoService.procesaCartaExhortoTotal(documento);
                break;
            case PARCIAL:
                cambiaEstadosCumplimientoService.procesaCartaExhortoParcial(documento);
                break;
        }
    }

    private void procesarRequerimiento(Documento documento) throws SGTServiceException {
        if (documento.getFechaNotificacion() == null) {
            //determinamos el indicador de cumplimiento
            determinarIndicadorCumplimiento(documento);
            documento.setDetalles(icepsCumplidosSesion(documento));
            procesarCumplidoSinNotifiacion(documento);
        } else {
            clasificarICEPSPorFechaCumplimiento(documento);
        }
    }

    

    private void clasificarICEPSPorFechaCumplimiento(Documento documento) throws SGTServiceException {
        List<DetalleDocumento> cumplidosAntesDeNotificacion = new ArrayList<DetalleDocumento>();
        List<DetalleDocumento> cumplidosDentroDelPlazoDeNotificacion = new ArrayList<DetalleDocumento>();
        List<DetalleDocumento> cumplidosFueraDelPlazoDeNotificacion = new ArrayList<DetalleDocumento>();
        for (DetalleDocumento dd : documento.getDetalles()) {
            if (dd.getSituacionIcep() == SituacionIcepEnum.INCUMPLIDO && dd.getFechaCumplimiento() != null) {
                //Cumplidos antes de notificacion
                int comparacion = documento.getFechaNotificacion().compareTo(dd.getFechaCumplimiento());
                //Cumplidos Dentro del Plazo de Notificacion
                int cumpNot = dd.getFechaCumplimiento().compareTo(documento.getFechaNotificacion());
                int cumpVenc = dd.getFechaCumplimiento().compareTo(documento.getFechaVencimiento());
                //FueraDelPlazoDeNotificacion
                int cumpVencimiento = dd.getFechaCumplimiento().compareTo(documento.getFechaVencimiento());
                //Cumplidos antes de notificacion
                if (comparacion >= 0) {
                    cumplidosAntesDeNotificacion.add(dd);
                }//Cumplidos Dentro del Plazo de Notificacion 
                else if (cumpNot > 0 && cumpVenc <= 0) {
                    cumplidosDentroDelPlazoDeNotificacion.add(dd);
                }//FueraDelPlazoDeNotificacion
                else if (cumpVencimiento > 0) {
                    cumplidosFueraDelPlazoDeNotificacion.add(dd);
                }
            }
        }

        procesarCumplidos(documento,
                cumplidosAntesDeNotificacion,
                cumplidosDentroDelPlazoDeNotificacion,
                cumplidosFueraDelPlazoDeNotificacion);

    }
    
    
    private void procesarCumplidos(Documento documento,
            List<DetalleDocumento> cumplidosAntesDeNotificacion,
            List<DetalleDocumento> cumplidosDentroDelPlazoDeNotificacion,
            List<DetalleDocumento> cumplidosFueraDelPlazoDeNotificacion) throws SGTServiceException {
        //con el propósito de generar una sola multa por extemporaneidad, pasamos los iceps cumplidos dentro de plazo a la coleccion de los iceps
        //cumplidos fuera de plazo en caso de que el documento tenga estado de vencido o vencido parcial.
        //si el documento no tiene alguno de estos dos estados entonces las multas se generarán por separado (de los iceps que estan dentro y fuera de plazo)
        if ((documento.getEstadoDocumento() == EstadoDocumentoEnum.VENCIDO || documento.getEstadoDocumento() == EstadoDocumentoEnum.VENCIDO_PARCIAL)
                && !cumplidosFueraDelPlazoDeNotificacion.isEmpty()) {
            cumplidosFueraDelPlazoDeNotificacion.addAll(cumplidosDentroDelPlazoDeNotificacion);
            cumplidosDentroDelPlazoDeNotificacion.clear();
        }
        if (!cumplidosAntesDeNotificacion.isEmpty()) {
            //si existen más cumplimientos que procesar  que caen en 
            // otros rangos de fecha mas adelante, entonces el documento lo procesamos
            //como parcial
            if (!cumplidosDentroDelPlazoDeNotificacion.isEmpty()
                    || !cumplidosFueraDelPlazoDeNotificacion.isEmpty()) {
                documento.setIndicadorCumplimiento(IndicadorCumplimientoEnum.PARCIAL);
            } else {
                determinarIndicadorCumplimiento(documento);
            }
            procesarCumplidoAntesDeNotificacion(documento, cumplidosAntesDeNotificacion);
        }

        if (!cumplidosDentroDelPlazoDeNotificacion.isEmpty()) {
            //si existen más cumplimientos que procesar  que caen en 
            // otros rangos de fecha mas adelante, entonces el documento lo procesamos
            //como parcial
            if (!cumplidosFueraDelPlazoDeNotificacion.isEmpty()) {
                documento.setIndicadorCumplimiento(IndicadorCumplimientoEnum.PARCIAL);
            } else {
                determinarIndicadorCumplimiento(documento);
            }
            procesarCumplidoDentroDelPlazoDeNotificacion(documento, cumplidosDentroDelPlazoDeNotificacion);
        }

        if (!cumplidosFueraDelPlazoDeNotificacion.isEmpty()) {
            determinarIndicadorCumplimiento(documento);
            procesarCumplidoFueraDelPlazoDeNotificacion(documento, cumplidosFueraDelPlazoDeNotificacion);
        }

    }

   

    private void procesarCumplidoSinNotifiacion(Documento documento) throws SGTServiceException {
        switch (documento.getIndicadorCumplimiento()) {
            case TOTAL:
                cambiaEstadosCumplimientoService.procesarCumplidoSinNotificacionTotal(documento);
                break;
            case PARCIAL:
                cambiaEstadosCumplimientoService.procesarCumplidoSinNotificacionParcial(documento);
                break;
        }
    }

    private void procesarCumplidoAntesDeNotificacion(Documento documento, List<DetalleDocumento> detalles) throws SGTServiceException {
        Documento documentoClon = clonarYAgregarDetalles(documento, detalles);
        switch (documento.getIndicadorCumplimiento()) {
            case TOTAL:
                cambiaEstadosCumplimientoService.procesarCumplidoAntesDeNotificacionTotal(documentoClon, documento);
                break;
            case PARCIAL:
                cambiaEstadosCumplimientoService.procesarCumplidoAntesDeNotificacionParcial(documentoClon, documento);
                break;
        }
    }

    private void procesarCumplidoDentroDelPlazoDeNotificacion(Documento documento, List<DetalleDocumento> detalles) throws SGTServiceException {
        Documento documentoClon = clonarYAgregarDetalles(documento, detalles);
        switch (documento.getIndicadorCumplimiento()) {
            case TOTAL:
                cambiaEstadosCumplimientoService.procesarCumplidoDentroDelPlazoDeNotificacionTotal(documentoClon, documento);
                break;
            case PARCIAL:
                cambiaEstadosCumplimientoService.procesarCumplidoDentroDelPlazoDeNotificacionParcial(documentoClon, documento);
                break;
        }
    }

    private void procesarCumplidoFueraDelPlazoDeNotificacion(Documento documento, List<DetalleDocumento> detalles) throws SGTServiceException {
        Documento documentoClon = clonarYAgregarDetalles(documento, detalles);
        switch (documento.getIndicadorCumplimiento()) {
            case TOTAL:
                cambiaEstadosCumplimientoService.procesarCumplidoFueraDelPlazoDeNotificacionTotal(documentoClon);
                break;
            case PARCIAL:
                cambiaEstadosCumplimientoService.procesarCumplidoFueraDelPlazoDeNotificacionParcial(documentoClon);
                break;
        }
    }
     private Documento clonarYAgregarDetalles(Documento documento, List<DetalleDocumento> detalles) throws SGTServiceException {
        try {
            Documento clon = documento.clone();
            clon.setDetalles(detalles);
            return clon;
        } catch (CloneNotSupportedException e) {
            throw new SGTServiceException("Ocurrio un error al clonar el documento " + e.getMessage(), e);
        }
    }

    /**
     * Devuelve los iceps cumplidos de la sesión actual, éstos aun no tienen la
     * marca de solventado
     *
     * @param documento Documento del que se desea obtener los iceps cumplidos
     * en la sesion
     */
    private List<DetalleDocumento> icepsCumplidosSesion(Documento documento) throws SGTServiceException {

        List<DetalleDocumento> cumplidos = new ArrayList<DetalleDocumento>();
        for (DetalleDocumento dd : documento.getDetalles()) {
            if (dd.getSituacionIcep() == SituacionIcepEnum.INCUMPLIDO && dd.getFechaCumplimiento() != null) {
                cumplidos.add(dd);
            }
        }
        return cumplidos;
    }




   

    @Override
    public void afectarDetallesConCumplimiento(List<EstadoDocumentoEnum> estados) throws SGTServiceException {
        InputStream fileInput = null;
        try {
            List<GrupoAfectacionCumpDTO> listaDetallesAgrupados;
            listaDetallesAgrupados = cumplimientoDAO.obtenerDetallesOmisos();
            log.info("La lista de grupos para actualizar los detalles de cumplimiento son \n"
                    + listaDetallesAgrupados);
            fileInput = new FileInputStream(ConstantsCatalogos.RUTA_THREADS);
            Properties prop = new Properties();
            prop.load(fileInput);
            ExecutorService executor = Executors.newFixedThreadPool(
                    Integer.parseInt(prop.getProperty("thread.afectacion.cumplimiento")));

            List<PoolThread> threads = new ArrayList<PoolThread>();
            for (GrupoAfectacionCumpDTO grupoAfectacionCumpDTO : listaDetallesAgrupados) {

                PoolThreadAfectacionCumplimientoServiceImpl pool = new PoolThreadAfectacionCumplimientoServiceImpl();
                pool.setCruceCumplimiento(cruceCumplimientosService);
                pool.setEstados(estados);
                pool.setGrupoAfectacionCumpDTO(grupoAfectacionCumpDTO);

                executor.execute(pool);
                threads.add(pool);
            }
            Utilerias.terminaProceso(executor, threads);
            Utilerias.interrumpirProceso(executor);
            if(PoolThreadAfectacionCumplimientoServiceImpl.getErroresHilos()>0){
                PoolThreadAfectacionCumplimientoServiceImpl.reiniciaErroresHilos();
                throw new SGTServiceException("Ocurrió un error al afectar los cumplimientos, por favor verifique la bitácora");
            }
        } catch (InterruptedException ex) {
            log.error(ex);
        } catch (FileNotFoundException ex) {
            log.error(ex);
        } catch (IOException ex) {
            log.error("No se encontro el archivo " + ex);
        } finally {
            if (fileInput != null) {
                try {
                    fileInput.close();
                } catch (IOException ex) {
                    log.error("Fallo al cerrar el archivo " + ex);
                }
            }
        }
    }

    @Override
    @Transactional
    public void afectarDetallesConCumplimiento(Integer[] estados) throws SGTServiceException {
        try {
            cumplimientoDAO.afectarDetallesConCumplimiento(estados);
        } catch (SeguimientoDAOException ex) {
            log.error(ex);
            Utilerias.getDetalleExcepcion(ex);
        }
    }

    @Override
    public void afectarDetallesComplementaria() throws SGTServiceException {
        List<GrupoAfectacionCumpDTO> listaDetallesAgrupados;
        List<Integer> tiposDeclaracion = tipoDeclaracionDAO.gettipoDeclaracion(TipoDeclaracionEnum.COMPLEMENTARIA);
        Map condiciones = new HashMap<String, Object>();
        condiciones.put("anuladoPadron", SituacionIcepEnum.CANCELADO_POR_MOVIMIENTOS_PADRON.getValor());
        condiciones.put("multacomplementaria", TieneMultaEnum.NO_TIENE.ordinal());
        condiciones.put("multaExtemporaneidad", TieneMultaEnum.TIENE.ordinal());
        condiciones.put("canceladoAutoridad", EstadoDocumentoEnum.CANCELADO_POR_AUTORIDAD.getValor());
        condiciones.put("tipoDeclaracion", TipoDeclaracionEnum.NORMAL.getValor());
        listaDetallesAgrupados = multaComplementariaDAO.obtenerDetallesComplementariaGrupo(condiciones);
        log.info("La lista de grups para actualizar los detalles de complementaria son "
                + listaDetallesAgrupados + " y son los siguientes \n" + listaDetallesAgrupados);

        InputStream fileInput = null;
        try {
            fileInput = new FileInputStream(ConstantsCatalogos.RUTA_THREADS);
            Properties prop = new Properties();
            prop.load(fileInput);
            ExecutorService executor = Executors.newFixedThreadPool(
                    Integer.parseInt(prop.getProperty("thread.afectacion.cumplimiento")));

            List<PoolThread> threads = new ArrayList<PoolThread>();
            for (GrupoAfectacionCumpDTO grupoAfectacionCumpDTO : listaDetallesAgrupados) {

                PoolThreadAfectacionCumplimientoComplementariaServiceImpl pool = new PoolThreadAfectacionCumplimientoComplementariaServiceImpl();
                pool.setCondiciones(condiciones);
                pool.setCruceCumplimiento(cruceCumplimientosService);
                pool.setGrupoAfectacionCumpDTO(grupoAfectacionCumpDTO);
                pool.setTiposDeclaracion(tiposDeclaracion);

                executor.execute(pool);
                threads.add(pool);
            }

            Utilerias.terminaProceso(executor, threads);
            Utilerias.interrumpirProceso(executor);
            if(PoolThreadAfectacionCumplimientoComplementariaServiceImpl.getErroresHilos()>0){
                PoolThreadAfectacionCumplimientoComplementariaServiceImpl.reiniciaErroresHilos();
                throw new SGTServiceException("Ocurrió un error al afectar los cumplimientos complementarios, por favor verifique la bitácora");                
            }
        } catch (InterruptedException ex) {
            log.error(ex);
        } catch (FileNotFoundException ex) {
            log.error(ex);
        } catch (IOException ex) {
            log.error("No se encontro el archivo " + ex);
        } finally {
            if (fileInput != null) {
                try {
                    fileInput.close();
                } catch (IOException ex) {
                    log.error("Fallo al cerrar el archivo " + ex);
                }
            }
        }

    }

    @Override
    public void procesaDocumentosCumplimiento(List<Documento> documentos) throws SGTServiceException {
        for (Documento documento : documentos) {
                log.info("procesando el documento " + documento.getNumeroControl());
                procesarCumplimiento(documento);
        }
    }

    private boolean verificaIntegridadDocumento(Documento documento) {
        //verificamos que haya consistencia en las fechas de
        if ((documento.getFechaNotificacion() != null && documento.getFechaVencimiento() == null)
                || (documento.getFechaNotificacion() == null && documento.getFechaVencimiento() != null)) {
            log.error("No hay consistencia entre la fecha de notificacion y de vencimiento del documento " + documento.getNumeroControl());
            return false;
        }

        return true;
    }
}
