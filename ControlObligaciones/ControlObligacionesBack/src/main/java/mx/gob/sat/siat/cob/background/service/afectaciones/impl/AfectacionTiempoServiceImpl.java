package mx.gob.sat.siat.cob.background.service.afectaciones.impl;

import java.util.List;

import mx.gob.sat.siat.cob.background.service.afectaciones.AfectacionTiempoService;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.EjecucionSeguimientoDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Vigilancia;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EjecucionSeguimientoEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EtapaVigilanciaEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.IcepRenuenteEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.ProcesoAfectacionTiempoService;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author root
 */
@Service("afectacionTiempoService")
public class AfectacionTiempoServiceImpl implements AfectacionTiempoService {

    private final Logger log = Logger.getLogger(AfectacionTiempoServiceImpl.class);
    private static final int LOTE_DOCS = 10000;
    private static final int DOC_GENERADO = 1;
    @Autowired
    private ProcesoAfectacionTiempoService procesoAfectacionTiempoService;
    @Autowired
    private EjecucionSeguimientoDAO ejecucionSeguimientoDAO;

    /**
     *
     * @throws SGTServiceException
     */
    @Override
    public void afectacionPorTiempo() throws SGTServiceException {
        log.debug("Inicia proceso AfectacionTiempoServiceImpl.afectacionPorTiempo");
        EjecucionSeguimientoEnum ejecucionSeguimientoEnum = null;
        try {
            ejecucionSeguimientoEnum = ejecucionSeguimientoDAO.enEjecucion();
        } catch (SeguimientoDAOException e) {
            log.error(e.toString());
        }
        if (!ejecucionSeguimientoEnum.equals(EjecucionSeguimientoEnum.PROCESANDO)) {
            log.error("<--AfectacionPorTiempo La bandera de procesamiento se encuentra inactiva");
            return;
        }
        EstadoDocumentoEnum[] estadosDocAfectacion = {EstadoDocumentoEnum.NOTIFICADO,
            EstadoDocumentoEnum.SOLVENTADO_PARCIAL, EstadoDocumentoEnum.VENCIDO,
            EstadoDocumentoEnum.VENCIDO_PARCIAL};
        TipoDocumentoEnum[] tipoDocs = {TipoDocumentoEnum.REQUERIMIENTO_NORMAL,
            TipoDocumentoEnum.REQUERIMIENTO_MULTA, TipoDocumentoEnum.REQUERIMIENTO_ACUMULADO,
            TipoDocumentoEnum.REQUERIMIENTO_DIOT};

        List<Vigilancia> vigilancias = null;
        for (EstadoDocumentoEnum estadoDoc : estadosDocAfectacion) {
            for (EtapaVigilanciaEnum etapa : EtapaVigilanciaEnum.values()) {
                if (etapa != EtapaVigilanciaEnum.ETAPA_1 && (estadoDoc == EstadoDocumentoEnum.VENCIDO
                        || estadoDoc == EstadoDocumentoEnum.VENCIDO_PARCIAL)) {
                    break;
                }
                try {
                    vigilancias = procesoAfectacionTiempoService.consultarDocsVencidosVigilanciaPorEstado(estadoDoc, etapa, tipoDocs);
                } catch (SGTServiceException ex) {
                    log.debug(ex);
                    log.error("<--AfectacionPorTiempo SGTServiceException");
                }
                if (vigilancias != null && !vigilancias.isEmpty()) {
                    this.procesarVigilancias(vigilancias, estadoDoc, etapa);
                }
            }
        }
        log.debug("Finaliza proceso AfectacionTiempoServiceImpl.afectacionPorTiempo con exito");
    }

    private void procesarVigilancias(List<Vigilancia> vigilancias, EstadoDocumentoEnum estadoDoc,
            EtapaVigilanciaEnum etapa) {
        int totalDocs = 0;
        Documento tipoDocumento = new Documento();
        for (Vigilancia vigilancia : vigilancias) {
            log.debug("### Inicia afectacion tiempo con un total de " + vigilancia.getIdMovimiento()
                    + " documentos en estado " + estadoDoc + ", " + etapa + " y vigilancia " + vigilancia.getIdVigilancia());
            int limMenor = 1;
            int limMayor = LOTE_DOCS;
            int numeroConsultas = 1;
            totalDocs = vigilancia.getIdMovimiento().intValue();
            tipoDocumento.setVigilancia(vigilancia);
            tipoDocumento.getVigilancia().setIdTipoDocumento(vigilancia.getIdTipoDocumento());
            tipoDocumento.setUltimoEstado(estadoDoc.getValor());
            tipoDocumento.setIdEtapaVigilancia(etapa.getValor());
            tipoDocumento.setEsUltimoGenerado(DOC_GENERADO);
            tipoDocumento.setConsideraRenuencia(etapa == EtapaVigilanciaEnum.ETAPA_1 ? IcepRenuenteEnum.NO.getValor()
                    : IcepRenuenteEnum.SI.getValor());
            if (totalDocs > LOTE_DOCS) {
                numeroConsultas = (totalDocs / LOTE_DOCS);
                if (totalDocs % LOTE_DOCS > 0) {
                    numeroConsultas += 1;
                }
            }
            for (int i = 1; i <= numeroConsultas; i++) {
                try {
                    switch (estadoDoc) {
                        case NOTIFICADO:
                            procesoAfectacionTiempoService.procesarDocumentosVencidosTiempo(limMenor, limMayor,
                                    tipoDocumento,
                                    EstadoDocumentoEnum.VENCIDO);
                            break;
                        case SOLVENTADO_PARCIAL:
                            procesoAfectacionTiempoService.procesarDocumentosVencidosTiempo(limMenor, limMayor,
                                    tipoDocumento,
                                    EstadoDocumentoEnum.VENCIDO_PARCIAL);
                            break;
                        case VENCIDO:
                        case VENCIDO_PARCIAL:
                            switch (vigilancia.getIdTipoDocumentoEnum()) {
                                case REQUERIMIENTO_NORMAL:
                                case REQUERIMIENTO_MULTA:
                                    tipoDocumento.setConsideraRenuencia(IcepRenuenteEnum.SI.getValor());
                                    procesoAfectacionTiempoService.procesarDocumentosVencidosRenuentes(limMenor, limMayor,
                                            tipoDocumento);
                                    break;
                                default:
                                    break;
                            }
                            break;
                    }
                } catch (Exception e) {
                    log.error(e + " no se pudo procesar lote de documentos" + e.getMessage());
                    limMenor = limMenor + LOTE_DOCS;
                    limMayor = limMayor + LOTE_DOCS;
                }
            }
        }
    }
}
