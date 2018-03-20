package mx.gob.sat.siat.cob.seguimiento.service.aprobacion.impl;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

import java.util.LinkedList;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DocumentoAprobarDAO;

import mx.gob.sat.siat.cob.seguimiento.dto.db2.HistoricoCumplimiento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DocumentoAprobar;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.IndicadorCumplimientoEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.SituacionIcepEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.aprobacion.ProcesoValidacionCumplimientoService;
import mx.gob.sat.siat.cob.seguimiento.service.aprobacion.ValidacionCumplimientoService;
import mx.gob.sat.siat.cob.seguimiento.service.otros.ValidacionCumplimientoFailWriter;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author root
 */
@Service
public class ValidacionCumplimientoServiceImpl implements ValidacionCumplimientoService {

    private final Logger logger = Logger.getLogger(ValidacionCumplimientoServiceImpl.class);
    @Autowired
    private ProcesoValidacionCumplimientoService procesoValidacionCumplimientoService;
    @Autowired
    private DocumentoAprobarDAO documentoAprobarDAO;
    @Autowired
    @Qualifier("validacionCumplimientoFailWriter")
    private ValidacionCumplimientoFailWriter validacionCumplimientoFailWriter;

    /**
     *
     * @param cumplimientos
     * @param documentosIniciales
     * @throws SGTServiceException
     */
    @Override
    @Transactional(rollbackFor = SGTServiceException.class, propagation = Propagation.REQUIRED)
    public void afectarCumplidos(List<HistoricoCumplimiento> cumplimientos, List<DocumentoAprobar> documentosIniciales) throws SGTServiceException {
        List<Documento> documentos = generarDocumentos(cumplimientos);
        for (Documento documento : documentos) {
            //Le asigna el Numero de Control al objeto documento en base a la lista inicial
            List<Documento> listaDoc = buscarDocumento(documento, documentosIniciales);
            for (Documento doc : listaDoc) {
                if (doc.getNumeroControl().isEmpty() || doc.getNumeroControl() == null) {
                    logger.error("El BOID y claves ICEP no se encuentra registrado como documento: " + doc.getBoid());
                } else {
                    procesoValidacionCumplimientoService.validarVigilables(doc);
                }
            }
        }
    }

    @Override
    public void validarHistoricosCumplimiento(String vigilancia, List<HistoricoCumplimiento> cumplimientos) {
        List<HistoricoCumplimiento> incidencias = new LinkedList<HistoricoCumplimiento>();
        String processId = obtenerProcessId();
        for (HistoricoCumplimiento historicoCumplimiento : cumplimientos) {
            if (isVigilable(historicoCumplimiento) && !isValido(historicoCumplimiento)) {
                incidencias.add(historicoCumplimiento);
            }
        }
        if (incidencias.size() > 0) {
            try {
                validacionCumplimientoFailWriter.reportarIncidencias(processId, vigilancia, incidencias);
            } catch (IOException ioe) {
                logger.error(ioe.toString());
            }
        }
    }

    @Override
    @Transactional(rollbackFor = SGTServiceException.class, propagation = Propagation.REQUIRED)
    public Integer actualizarEstatusDocumento(String numeroControl, EstadoDocumentoEnum estadoNuevo) throws SGTServiceException {
        Integer resultado = null;
        resultado = documentoAprobarDAO.actualizarEstatusDocumento(numeroControl, estadoNuevo);
        if (resultado == null) {
            throw new SGTServiceException("No se pudo realizar el "
                    + "cambio de estatus del documento-->" + numeroControl);
        }
        return resultado;
    }

    private List<Documento> generarDocumentos(List<HistoricoCumplimiento> cumplimientos) {
        List<Documento> documentos = new LinkedList<Documento>();
        Documento documento = new Documento();
        documento.setBoid("0");
        for (HistoricoCumplimiento historicoCumplimiento : cumplimientos) {
            if (documento.getBoid().equals("0")
                    || !historicoCumplimiento.getbOID().equals(documento.getBoid())) {
                //Si ninguno de los iceps fue apto para cambiar de estado se remueve el documento
                if (documento.getDetalles().isEmpty()) {
                    documentos.remove(documento);
                }
                documento = new Documento();
                documento.setBoid(historicoCumplimiento.getbOID());
                documentos.add(documento);
            }
            DetalleDocumento d = new DetalleDocumento();
            d.setClaveIcep(Long.parseLong(historicoCumplimiento.getClaveICEP()));
            d.setIdSituacionIcep(SituacionIcepEnum.CANCELADO_POR_MOVIMIENTOS_PADRON.getValor());
            llenarPropiedadesVigilables(historicoCumplimiento, d, documento);
            if (!historicoCumplimiento.getEstadoVigilable().equals(1)) {
                documento.getDetalles().add(d);
            }
        }
        return documentos;
    }

    private List<Documento> buscarDocumento(Documento documento, List<DocumentoAprobar> documentosIniciales) {
        List<Documento> listaDoc = new ArrayList<Documento>();
        for (DocumentoAprobar documentoAprobar : documentosIniciales) {
            if (documentoAprobar.getBoid().equals(documento.getBoid())) {
                if (validarIceps(documento, documentoAprobar)) {
                    documento.setNumeroControl(documentoAprobar.getNumeroControl());
                    documento.getVigilancia().setIdTipoDocumento(
                            Integer.parseInt(documentoAprobar.getIdTipoDocumento()));
                    if (documento.getDetalles().size() == documentoAprobar.getDetalles().size()) {
                        documento.setIndicadorCumplimiento(IndicadorCumplimientoEnum.TOTAL);
                    } else {
                        documento.setIndicadorCumplimiento(IndicadorCumplimientoEnum.PARCIAL);
                    }
                    try {
                        listaDoc.add(documento.clone());
                    } catch (CloneNotSupportedException ex) {
                        logger.error(ex);
                    }
                }
            }
        }
        return listaDoc;
    }

    private boolean validarIceps(Documento documento, DocumentoAprobar documentoAprobar) {
        int encontrado = 0;
        for (DetalleDocumento detAprobar : documentoAprobar.getDetalles()) {
            for (DetalleDocumento det : documento.getDetalles()) {
                if (detAprobar.getClaveIcep().equals(det.getClaveIcep())) {
                    encontrado++;
                    if (encontrado == documento.getDetalles().size()) {
                        return true;
                    }
                    break;
                }
            }
        }
        if (encontrado == 0) {
            return false;
        }
        return true;
    }

    private void llenarPropiedadesVigilables(HistoricoCumplimiento historicoCumplimiento,
            DetalleDocumento d, Documento documento) {
        if (isVigilable(historicoCumplimiento)) {

            d.setFechaCumplimiento(historicoCumplimiento.getFechaPresentacion());
            d.setEstadoIcepEC(historicoCumplimiento.getEstadoIcep());
            d.setIdCumplimiento(getBigIntegerValueOrNull(historicoCumplimiento.getIdentificadorCumplimiento()));
            d.setImporteCargo(historicoCumplimiento.getImportePagar());
            d.setIdTipoDeclaracion(getIntegerValueOrNull(historicoCumplimiento.getTipoDeclaracion()));
            d.setIdSituacionIcep(SituacionIcepEnum.CUMPLIDO.getValor());
            documento.getDetalles().add(d);

        }
    }

    private boolean isVigilable(HistoricoCumplimiento historicoCumplimiento) {
        return historicoCumplimiento.getEstadoVigilable().equals(1)
                && (historicoCumplimiento.getEstadoIcep().equals(16)
                || historicoCumplimiento.getEstadoIcep() > 1
                && historicoCumplimiento.getEstadoIcep() < 11);
    }

    private BigInteger getBigIntegerValueOrNull(String value) {
        BigInteger resultado = null;
        if (value != null) {
            try {
                resultado = new BigInteger(value);
            } catch (NumberFormatException nfe) {
                resultado = null;
            }
        }
        return resultado;
    }

    private Integer getIntegerValueOrNull(String value) {
        Integer resultado = null;
        if (value != null) {
            try {
                resultado = Integer.parseInt(value);
            } catch (NumberFormatException nfe) {
                resultado = null;
            }
        }
        return resultado;
    }

    private boolean isValido(HistoricoCumplimiento historicoCumplimiento) {
        return (historicoCumplimiento.getIdentificadorCumplimiento() != null)
                && (historicoCumplimiento.getTipoDeclaracion() != null)
                && (historicoCumplimiento.getFechaPresentacion() != null);
    }

    private String obtenerProcessId() {
        return new Double(Math.random()).toString().substring(0, 10);
    }

}
