package mx.gob.sat.siat.cob.background.service.afectaciones.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.cob.background.service.afectaciones.AfectacionCumplimientoService;
import mx.gob.sat.siat.cob.seguimiento.dao.dbd2.impl.HistoricoCumplimientoDAOImpl;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.EjecucionSeguimientoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.ParametroSgtDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.ProcesoDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ConsultarProcesosFiltroDto;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ParametrosSgtDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Proceso;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EjecucionSeguimientoEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.ParametroSgtEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.ProcesoEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.CumplimientoService;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.ProcesoAfectacionCumplimientoService;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.ProcesoMultaComplementariaService;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;

@Service("afectacionCumplimientoService")
public class AfectacionCumplimientoServiceImpl implements AfectacionCumplimientoService {

    @Autowired
    private DocumentoDAO documentoDAO;
    @Autowired
    private ProcesoDAO procesoDAO;
    @Autowired
    private ProcesoAfectacionCumplimientoService procesoAfectacionCumplimientoService;
    @Autowired
    private ProcesoMultaComplementariaService procesoMultaComplementariaService;
    @Autowired
    private EjecucionSeguimientoDAO ejecucionSeguimientoDAO;
    @Autowired
    private CumplimientoService cumplimientoService;
    @Autowired
    private ParametroSgtDAO parametroSgtDAO;
    private ParametrosSgtDTO parametrosSgtDTO;
    private Logger log = Logger.getLogger(AfectacionCumplimientoServiceImpl.class);
    private static final List<EstadoDocumentoEnum> ESTADOS_AFECTACION = new ArrayList<EstadoDocumentoEnum>();

    @Override
    public void afectarPorCumplimientos() throws SGTServiceException {

        boolean procesoEnActivo;
        EjecucionSeguimientoEnum ejecucionSeguimientoEnum;

        ESTADOS_AFECTACION.add(EstadoDocumentoEnum.PENDIENTE_DE_IMPRIMIR);
        ESTADOS_AFECTACION.add(EstadoDocumentoEnum.PENDIENTE_DE_NOTIFICAR);
        ESTADOS_AFECTACION.add(EstadoDocumentoEnum.NOTIFICADO);
        ESTADOS_AFECTACION.add(EstadoDocumentoEnum.VENCIDO);
        ESTADOS_AFECTACION.add(EstadoDocumentoEnum.SOLVENTADO_PARCIAL);
        ESTADOS_AFECTACION.add(EstadoDocumentoEnum.VENCIDO_PARCIAL);
        ESTADOS_AFECTACION.add(EstadoDocumentoEnum.EMITIDO);
        ESTADOS_AFECTACION.add(EstadoDocumentoEnum.ATENDIDO_PARCIAL);
        ESTADOS_AFECTACION.add(EstadoDocumentoEnum.NO_GENERADO);
        ESTADOS_AFECTACION.add(EstadoDocumentoEnum.PENDIENTE_DE_PROCESAR);


        try {
            ejecucionSeguimientoEnum = ejecucionSeguimientoDAO.enEjecucion();
            if (ejecucionSeguimientoEnum == null || !ejecucionSeguimientoEnum.equals(EjecucionSeguimientoEnum.PROCESANDO)) {
                log.error("<--afectarPorCumplimiento La bandera de procesamiento se encuentra inactiva");
                return;
            }
            procesoEnActivo = procesoActivo();
            log.info("Comienza el proceso de afectacion por cumplimiento");

            if (!procesoEnActivo) {
                determinaImpresionConsultasExtraccion();
            }
            afectarCumplimientos(procesoEnActivo);
            procesoCumplimiento();


            afectarComplementaria(procesoEnActivo);
            procesoCumplementaria();

            if (procesoEnActivo) {
                log.info("Comenzando eliminación de de cumplimientos");
                cumplimientoService.eliminarCumplimientosProcesados();
                log.info("Termina eliminacion de cumplimientos");
            }

        } catch (SeguimientoDAOException e) {
            log.error(e);
            Utilerias.getDetalleExcepcion(e);
        } catch (Exception e) {
            log.error("Error al afectar los cumplimientos ", e);
            Utilerias.getDetalleExcepcion(e);
        }
    }

    private void afectarCumplimientos(boolean procesoActivo) throws SGTServiceException {
        //Actualizacion masiva de cumplimientos en documentos
        log.info("Comienza actualizacion masiva afectacion por cumplimiento.");
        if (procesoActivo) {
            log.info("Afectacion de detalles por cumplimientos extraidos previamente.");
            procesoAfectacionCumplimientoService.afectarDetallesConCumplimiento(ProcesoAfectacionCumplimientoService.ESTADOS_CUMPLIMIENTO);
        } else {
            log.info("Afectacion de detalles extrayendo cumplimientos directamente de estructura de cumplimiento.");
            procesoAfectacionCumplimientoService.afectarDetallesConCumplimiento(ESTADOS_AFECTACION);
        }
        log.info("Termina actualizacion masiva afectacion por cumplimiento.");
    }

    private void afectarComplementaria(boolean procesoActivo) throws SGTServiceException {
        log.info("Comienza afectacion de detalles con complementaria.");
        if (procesoActivo) {
            log.info("Afectacion de detalles por cumplimientos extraidos previamente.");
            procesoMultaComplementariaService.afectarDetallesConComplementaria();
        } else {
            log.info("Afectacion de detalles extrayendo cumplimientos directamente de estructura de cumplimiento.");
            procesoAfectacionCumplimientoService.afectarDetallesComplementaria();
        }
        log.info("Termina afectacion de detalles con complementaria.");

    }

    private void procesoCumplimiento() throws SGTServiceException {
        List<Documento> documentos;
        //Obtener documentos a procesar
        log.info("obteniendo documentos a procesar");
        documentos = documentoDAO.documentosAprocesar(ProcesoAfectacionCumplimientoService.ESTADOS_CUMPLIMIENTO);
        if (documentos == null) {
            throw new SGTServiceException("Error al traer los documentos a procesar");
        }
        log.info("procesando documentos cumplimiento");
        procesoAfectacionCumplimientoService.procesaDocumentosCumplimiento(documentos);
        log.info("finaliza procesando documentos cumplimiento");

    }

    private void procesoCumplementaria() {
        List<Documento> documentos;
        log.info("comienza documentos multa complementaria");
        try {
            documentos = procesoMultaComplementariaService.documentosMultaComplementaria();
            if (!documentos.isEmpty()) {
                procesoMultaComplementariaService.generarMultasComplementarias(documentos);
            }
            log.info("termina documentos multa complementaria");
        } catch (SGTServiceException ex) {
            log.error("No se pudieron procesar los cumplimientos complementarios " + "\n" + ex);
        }
    }

    private boolean procesoActivo() throws SeguimientoDAOException, SGTServiceException {
        ConsultarProcesosFiltroDto consultarProcesosFiltroDto = new ConsultarProcesosFiltroDto();
        consultarProcesosFiltroDto.setIdProceso(ProcesoEnum.EXTRACCION_CUMPLIMIENTO.getValor());
        List<Proceso> procesos = procesoDAO.consultarProcesos(consultarProcesosFiltroDto);
        if (procesos == null || procesos.isEmpty()) {
            throw new SGTServiceException("No se pudo determinar si el proceso está activo");
        } else {
            return !procesos.get(0).getEstado().equals(0);
        }
    }

    private void determinaImpresionConsultasExtraccion() {
        parametrosSgtDTO = parametroSgtDAO.obtenerParametroSgtPorIdParametro(ParametroSgtEnum.IMPRESION_CONSULTAS_BUSQUEDA_CUMPLIMIENTOS_BKG.getValor());
        HistoricoCumplimientoDAOImpl.setFlgImprimeConsultas( parametrosSgtDTO.getValor() != null && parametrosSgtDTO.getValor().equals("1") );
    }
}
