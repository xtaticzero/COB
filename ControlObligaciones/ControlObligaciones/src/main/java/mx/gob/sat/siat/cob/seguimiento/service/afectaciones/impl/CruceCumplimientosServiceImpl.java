/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.afectaciones.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import mx.gob.sat.siat.cob.seguimiento.dao.dbd2.HistoricoCumplimientoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.CumplimientoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.MultaComplementariaDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.db2.HistoricoCumplimiento;
import mx.gob.sat.siat.cob.seguimiento.dto.db2.Paginador;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DocumentoAprobar;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.GrupoAfectacionCumpDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.CruceCumplimientosService;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.CumplimientoService;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.MultaComplementariaService;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.ProcesoAfectacionCumplimientoService;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author root
 */
@Service
public class CruceCumplimientosServiceImpl implements CruceCumplimientosService {

    @Autowired
    private CumplimientoDAO cumplimientoDAO;
    @Autowired
    private HistoricoCumplimientoDAO historicoCumplimientoDAO;
    @Autowired
    private MultaComplementariaDAO multaComplementariaDAO;
    @Autowired
    private DocumentoDAO documentoDAO;
    @Autowired
    private ProcesoAfectacionCumplimientoService procesoAfectacionCumplimientoService;
    @Autowired
    private CumplimientoService cumplimientoService;
    @Autowired
    private MultaComplementariaService multaComplementariaService;
    private final Logger log = Logger.getLogger(CruceCumplimientosServiceImpl.class);
    private boolean cruceComplementaria;
    
    private static final int TAMANIO_PAGINA = 1500;
    private static final int MESES_MIN_COMPLEMENTARIA = 6;

    @Override
    public void cruceAfectacionCumplimientos(GrupoAfectacionCumpDTO grupoAfectacionCumpDTO, List<EstadoDocumentoEnum> estados) throws SGTServiceException {
        cruceComplementaria = false;
        List<Documento> documentosAfectar = new ArrayList<Documento>();
        Paginador paginador = new Paginador(grupoAfectacionCumpDTO.getConteo(), TAMANIO_PAGINA);
        do {
            log.info("Trayendo de Estructura de Cumplimiento los cumplimientos de los detalles de  la vigilancia " + grupoAfectacionCumpDTO.getVigilancia() + " con la local " + grupoAfectacionCumpDTO.getIdAdmonLocal()
                    + " Pagina " + paginador.getNumeroPagina() + " de " + paginador.getNumeroPaginas());

            List<DocumentoAprobar> documentosAprobar = cumplimientoDAO.listarDocumentosIcep(grupoAfectacionCumpDTO, paginador);

            if (documentosAprobar == null) {
                throw new SGTServiceException("Error al generar los documentos por aprobar");
            }

            if (documentosAprobar.size() > 0) {
                //Obtener cumplimientos

                List<HistoricoCumplimiento> cumplimientos = historicoCumplimientoDAO.buscarCumplimientos(documentosAprobar);
                if (cumplimientos == null) {
                    throw new SGTServiceException("Error al traer los cumplimientos de Estructura de cumplimiento");
                }

                List<Documento> documentos = generarDocumentos(cumplimientos);

                for (Documento documento : documentos) {
                    //Le asigna el Numero de Control al objeto documento en base a la lista inicial

                    List<Documento> listaDoc = buscarDocumento(documento, documentosAprobar);
                    documentosAfectar.addAll(listaDoc);
                }
            }
        } while (paginador.next());
        for (Documento doc : documentosAfectar) {
                cumplimientoService.actualizarDetalleConCumplimiento(doc, contieneEstadoDocumentoEnum(estados, doc.getUltimoEstado()) ? 0 : 1);
        }
        List<Documento> documentos;
        //Obtener documentos a procesar
        log.info("obteniendo documentos a procesar");
        documentos = documentoDAO.documentosAprocesar(ProcesoAfectacionCumplimientoService.ESTADOS_CUMPLIMIENTO, grupoAfectacionCumpDTO.getVigilancia(), grupoAfectacionCumpDTO.getIdAdmonLocal());        
        if (documentos == null) {
                throw new SGTServiceException("Error al traer los documentos a procesar");
        }

        log.info("procesando documentos cumplimiento");
        procesoAfectacionCumplimientoService.procesaDocumentosCumplimiento(documentos);
    }

    @Override
    public void cruceAfectacionCumplimientosComplementaria(GrupoAfectacionCumpDTO grupoAfectacionCumpDTO, Map condiciones, List<Integer> tiposDeclaracion) throws SGTServiceException {
        cruceComplementaria = true;
        List<Documento> documentosAfectar = new ArrayList<Documento>();
        Paginador paginador = new Paginador(grupoAfectacionCumpDTO.getConteo(), TAMANIO_PAGINA);
        do {
            log.info("Actualizando los cumplimientos de complementaria a los detalles de  la vigilancia " + grupoAfectacionCumpDTO.getVigilancia() + " con la local " + grupoAfectacionCumpDTO.getIdAdmonLocal()
                    + " Pagina " + paginador.getNumeroPagina() + " de " + paginador.getNumeroPaginas());
            List<DocumentoAprobar> documentosAprobar = multaComplementariaDAO.listarDocumentosIcep(grupoAfectacionCumpDTO, paginador, condiciones);
            if (documentosAprobar == null) {
                throw new SGTServiceException("Error al generar los documentos por aprobar");
            }
            if (documentosAprobar.size() > 0) {
                //Obtener cumplimientos
                List<HistoricoCumplimiento> cumplimientos = historicoCumplimientoDAO.buscarCumplimientos(documentosAprobar, tiposDeclaracion);
                if (cumplimientos == null) {
                    throw new SGTServiceException("Error al traer los cumplimientos de Estructura de cumplimiento");
                }
                List<Documento> documentos = generarDocumentos(cumplimientos);

                for (Documento documento : documentos) {
                    //Le asigna el Numero de Control al objeto documento en base a la lista inicial
                    List<Documento> listaDoc = buscarDocumento(documento, documentosAprobar);
                    documentosAfectar.addAll(listaDoc);

                }
            }
        } while (paginador.next());

        for (Documento doc : documentosAfectar) {
             multaComplementariaService.actualizarDetalleConComplementaria(doc);
        }
        log.info("Termina actualizacion de detalles de complementaria");
    }

    private boolean contieneEstadoDocumentoEnum(List<EstadoDocumentoEnum> estados, Integer estado) {
        for (EstadoDocumentoEnum estadoDocumentoEnum : estados) {
            if (estadoDocumentoEnum.getValor() == estado) {
                return true;
            }
        }
        return false;
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

            if (this.isVigilable(historicoCumplimiento)) {
                llenarPropiedadesVigilables(historicoCumplimiento, d);
                documento.getDetalles().add(d);
            }
        }
        return documentos;
    }

    private List<Documento> buscarDocumento(Documento documento, List<DocumentoAprobar> documentosIniciales) {
        List<Documento> listaDoc = new ArrayList<Documento>();
        List<DetalleDocumento> detallesValidos;
        for (DocumentoAprobar documentoAprobar : documentosIniciales) {
            if (documentoAprobar.getBoid().equals(documento.getBoid())) {
                detallesValidos = validarIceps(documento, documentoAprobar);
                if (detallesValidos != null && !detallesValidos.isEmpty()) {
                    documento.setNumeroControl(documentoAprobar.getNumeroControl());
                    try {
                        Documento doc = documento.clone();
                        doc.setUltimoEstado(documentoAprobar.getUltimoEstado());
                        doc.setDetalles(detallesValidos);
                        listaDoc.add(doc);
                    } catch (CloneNotSupportedException ex) {
                        log.error(ex);
                    }
                }
            }
        }
        return listaDoc;
    }

    private List<DetalleDocumento> validarIceps(Documento documento, DocumentoAprobar documentoAprobar) {
        List<DetalleDocumento> detallesValidos = new ArrayList<DetalleDocumento>();
        int encontrado = 0;
        for (DetalleDocumento detAprobar : documentoAprobar.getDetalles()) {
            for (DetalleDocumento det : documento.getDetalles()) {
                if (detAprobar.getClaveIcep().equals(det.getClaveIcep())
                        && (!cruceComplementaria || validarIcepsComplementaria(det, detAprobar))) {
                    encontrado++;
                    detallesValidos.add(det);
                    break;
                }
            }
        }
        if (encontrado == 0) {
            return null;
        }
        return detallesValidos;
    }

    private boolean validarIcepsComplementaria(DetalleDocumento detHistorico, DetalleDocumento detAprobar) {
        return (detHistorico.getImporteCargo() != null
                && detAprobar.getImporteCargo() != null
                && detHistorico.getImporteCargo() > detAprobar.getImporteCargo()
                && Utilerias.obtenerDiferenciaMeses(detAprobar.getFechaCumplimiento(), detHistorico.getFechaCumplimiento()) <= MESES_MIN_COMPLEMENTARIA);
    }

    private void llenarPropiedadesVigilables(HistoricoCumplimiento historicoCumplimiento,
            DetalleDocumento d) {

        d.setFechaCumplimiento(historicoCumplimiento.getFechaPresentacion());
        d.setEstadoIcepEC(historicoCumplimiento.getEstadoIcep());
        d.setIdCumplimiento(getBigIntegerValueOrNull(historicoCumplimiento.getIdentificadorCumplimiento()));
        d.setImporteCargo(historicoCumplimiento.getImportePagar());
        d.setIdTipoDeclaracion(getIntegerValueOrNull(historicoCumplimiento.getTipoDeclaracion()));
    }

    private boolean isVigilable(HistoricoCumplimiento historicoCumplimiento) {
        return ((historicoCumplimiento.getEstadoIcep() == 16
                || (historicoCumplimiento.getEstadoIcep() > 1
                && historicoCumplimiento.getEstadoIcep() < (cruceComplementaria ? 9 : 11)))
                && historicoCumplimiento.getFechaPresentacion() != null);
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
}
