package mx.gob.sat.siat.cob.seguimiento.service.afectaciones.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import mx.gob.sat.siat.cob.seguimiento.dao.dbd2.HistoricoPagosIcepDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.BaseDocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.BitacoraMultaDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DetalleDocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.MultaDocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.MultaIcepDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.VigilanciaDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.db2.PagoIcepOmiso;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.NumeroControlDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Vigilancia;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaAdministracionLocal;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.DiasPlantillaEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoMultaEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EtapaVigilanciaEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.IcepRenuenteEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.ParametroSgtEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.SituacionIcepEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.SituacionVigilanciaEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoMedioEnvioEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoMultaEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.UltimoDocGeneradoEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.ProcesoAfectacionTiempoService;
import mx.gob.sat.siat.cob.seguimiento.service.extraccion.VigilanciaAdministracionLocalService;
import mx.gob.sat.siat.cob.seguimiento.service.otros.CicloDocEtapaService;
import mx.gob.sat.siat.cob.seguimiento.service.otros.CicloVidaSgtService;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author root
 */
@Service
public class ProcesoAfectacionTiempoServiceImpl implements ProcesoAfectacionTiempoService {

    private final Logger log = Logger.getLogger(ProcesoAfectacionTiempoServiceImpl.class);
    @Autowired
    private VigilanciaDAO vigilanciaDAO;
    @Autowired
    private DocumentoDAO documentoDAO;
    @Autowired
    private BaseDocumentoDAO baseDocumentoDAO;
    @Autowired
    private CicloDocEtapaService cicloDocEtapaService;
    @Autowired
    private CicloVidaSgtService cicloVidaSgtService;
    @Autowired(required = false)
    private HistoricoPagosIcepDAO historicoPagosIcepDAO;
    @Autowired
    private MultaDocumentoDAO multaDocumentoDAO;
    @Autowired
    private MultaIcepDAO multaIcepDAO;
    @Autowired
    private BitacoraMultaDAO bitacoraMultaDAO;
    @Autowired
    private DetalleDocumentoDAO detalleDocumentoDAO;
    @Autowired
    private VigilanciaAdministracionLocalService vigilanciaAdministracionLocalService;

    /**
     *
     * @param estado
     * @param etapa
     * @param tipoDocs
     * @return Devuelve una lista de las Vigilancias, en cada una el numero de
     * documentos que existen
     * @throws SGTServiceException Metodo que consulta los documentos de cada
     * vigilancia segun el tipo de documento, la etapa y si aplica renuentes
     */
    @Override
    @Transactional(rollbackFor = SGTServiceException.class, readOnly = false, timeout = 5000,
            isolation = Isolation.READ_COMMITTED)
    public List<Vigilancia> consultarDocsVencidosVigilanciaPorEstado(EstadoDocumentoEnum estado,
            EtapaVigilanciaEnum etapa,
            TipoDocumentoEnum[] tipoDocs) throws SGTServiceException {
        List<Vigilancia> vigilancias;

        vigilancias = vigilanciaDAO.consultarDocsVencidosVigilanciaPorEstado(estado, etapa, tipoDocs);

        if (vigilancias == null) {
            throw new SGTServiceException("No se pudo consultar documentos vencidos por vigilancias");
        }
        return vigilancias;
    }

    @Transactional(readOnly = false,
            isolation = Isolation.READ_COMMITTED,
            propagation = Propagation.REQUIRED,
            rollbackFor = {SGTServiceException.class})
    @Override
    public void procesarDocumentosVencidosRenuentes(int limMenor, int limMayor,
            Documento tipoDoc) throws SGTServiceException {
        List<Documento> documentosVencidos;
        List<DetalleDocumento> icepsOmisos;
        documentosVencidos = documentoDAO.consultarDocumentosActualizadosPorLote(limMenor, limMayor, tipoDoc);
        if (documentosVencidos == null || documentosVencidos.isEmpty()) {
            throw new SGTServiceException("No se pudieron obtener los documentos actualizados por lote");
        }
        icepsOmisos = documentoDAO.consultarICEPsOmisosPorLote(limMenor, limMayor, tipoDoc);

        if (icepsOmisos == null || icepsOmisos.isEmpty()) {
            throw new SGTServiceException("No se pudieron obtener los ICEP omisos");
        }
        documentosVencidos = this.agregarICEPSaDocumentos(icepsOmisos, documentosVencidos);

        generaDocumentoRequerimientoRenuente(tipoDoc.getEtapaVigilancia(), tipoDoc.getTipoDocumento(), documentosVencidos);
    }

    /**
     *
     * @param limMenor
     * @param limMayor
     * @param tipoDoc
     * @param estadoDestino
     * @throws SGTServiceException
     */
    @Transactional(readOnly = false,
            isolation = Isolation.READ_COMMITTED,
            propagation = Propagation.NESTED,
            rollbackFor = {SGTServiceException.class})
    @Override
    public void procesarDocumentosVencidosTiempo(int limMenor, int limMayor, Documento tipoDoc,
            EstadoDocumentoEnum estadoDestino) throws SGTServiceException {
        List<DetalleDocumento> icepsOmisos = null;
        List<Documento> documentosVencidos = null;

        baseDocumentoDAO.borrarTablaNumeroResolucion();
        icepsOmisos = this.consultarICEPsOmisos(limMenor, limMayor, tipoDoc);
        documentosVencidos = this.actualizarDocsVencidosTiempo(limMenor, limMayor, tipoDoc, estadoDestino);

        if (tipoDoc.getVigilancia().getIdTipoMedio() != TipoMedioEnvioEnum.ENTIDAD_FEDERATIVA.getValor() && tipoDoc.getEtapaVigilancia() != EtapaVigilanciaEnum.ETAPA_4
                && icepsOmisos != null && !icepsOmisos.isEmpty()) {

            documentosVencidos = this.agregarICEPSaDocumentos(icepsOmisos, documentosVencidos);

            this.generarMultasIncumplimiento(documentosVencidos, tipoDoc.getConsideraRenuencia(),
                    tipoDoc.getVigilancia().getIdTipoMedioEnum());

            switch (tipoDoc.getEtapaVigilancia()) {
                case ETAPA_2:
                    this.generaDocumentoRequerimientoRenuente(tipoDoc.getEtapaVigilancia(), tipoDoc.getTipoDocumento(), documentosVencidos);
                    break;
                case ETAPA_3:
                    this.generaDocumentoLiquidacion(tipoDoc.getEtapaVigilancia(), tipoDoc.getTipoDocumento(), documentosVencidos);
                    break;
                default:
                    break;
            }
        }
    }

    private List<DetalleDocumento> consultarICEPsOmisos(int limMenor, int limMayor,
            Documento tipoDoc) throws SGTServiceException {
        List<DetalleDocumento> icepsOmisos = null;
        if (tipoDoc.getEtapaVigilancia() != EtapaVigilanciaEnum.ETAPA_4) {
            icepsOmisos = documentoDAO.consultarICEPsOmisosPorLote(limMenor, limMayor, tipoDoc);
            if (icepsOmisos == null) {
                throw new SGTServiceException("No se pudieron consultar los ICEPs Omisos");
            }
        }
        return icepsOmisos;
    }

    private List<Documento> actualizarDocsVencidosTiempo(int limMenor, int limMayor, Documento tipoDoc,
            EstadoDocumentoEnum estadoDestino) throws SGTServiceException {
        List<Documento> documentosVencidos;
        if (cicloDocEtapaService.validaCambioEstado(tipoDoc.getEstadoDocumento(), tipoDoc.getTipoDocumento(),
                tipoDoc.getEtapaVigilancia(), estadoDestino)) {

            documentosVencidos = documentoDAO.consultarDocumentosActualizadosPorLote(limMenor, limMayor, tipoDoc);
            if (documentosVencidos == null) {
                throw new SGTServiceException("No se pudieron consultar los documentos vencidos");
            }
            documentoDAO.insertarEdoDocumentoBitacoraPorLote(limMenor, limMayor, tipoDoc, estadoDestino);

            if (documentoDAO.actualizarEstadoDocumentoPorLote(limMenor, limMayor, tipoDoc, estadoDestino) == null) {
                throw new SGTServiceException("No se pudo actualizar el estado de los documentos");
            }
        } else {
            throw new SGTServiceException("\n No se puede hacer la transicion de estado " + tipoDoc.getEstadoDocumento() + " a "
                    + estadoDestino + "\n"
                    + "tipo documento " + tipoDoc.getTipoDocumento() + "\n"
                    + "etapa vigilancia " + tipoDoc.getEtapaVigilancia() + "\n"
                    + " de lote de documentos " + "\n"
                    + "vigilancia " + tipoDoc.getVigilancia().getIdVigilancia() + "\n"
                    + "tipo documento " + tipoDoc.getVigilancia().getIdTipoDocumento() + "\n"
                    + "estado " + tipoDoc.getUltimoEstado() + "\n"
                    + "es ultimo generado " + tipoDoc.getEsUltimoGenerado() + "\n"
                    + "etapa vigilancia " + tipoDoc.getIdEtapaVigilancia() + "\n"
                    + "considera renuencia " + tipoDoc.getConsideraRenuencia());
        }
        return documentosVencidos;
    }

    private void generarMultasIncumplimiento(List<Documento> documentosVencidos, int consideraRenuencia,
            TipoMedioEnvioEnum idTipoMedio) throws SGTServiceException {

        if (baseDocumentoDAO.generaNumeroResoluciones(documentosVencidos, TipoMultaEnum.INCUMPLIMIENTO.toString(),
                0) == 0) {
            throw new SGTServiceException("No se pudo obtener numeros resolucion");
        }
        Integer multasGeneradas = multaDocumentoDAO.generarMultaMasivaSeguimiento(documentosVencidos, TipoMultaEnum.INCUMPLIMIENTO,
                idTipoMedio);
        if (multasGeneradas == null || multasGeneradas.equals(0)) {
            throw new SGTServiceException("Ocurrio un error al generar las multas en seguimiento ");
        }
        if (multaIcepDAO.insertarIcepsPorMultasIncumplimiento(documentosVencidos, consideraRenuencia) == null) {
            throw new SGTServiceException("No se pudieron registrar los ICEP asociados a la multa");
        }
        if (bitacoraMultaDAO.registraBitacoraMultaMasivo(documentosVencidos,
                (idTipoMedio == TipoMedioEnvioEnum.ENTIDAD_FEDERATIVA
                ? EstadoMultaEnum.PENDIENTE_DE_PROCESAR_ENTIDAD
                : EstadoMultaEnum.PENDIENTE_DE_PROCESAR)) == null) {
            throw new SGTServiceException("No se pudo generar la bitácora de la multa para los documentos ");
        }
    }

    private List<Documento> agregarICEPSaDocumentos(List<DetalleDocumento> icepsOmisos,
            List<Documento> documentosVencidos) {
        for (Documento docVencido : documentosVencidos) {
            List<DetalleDocumento> icepsOmisosDoc;
            Iterator iceps = icepsOmisos.iterator();
            icepsOmisosDoc = new ArrayList<DetalleDocumento>();
            while (iceps.hasNext()) {
                DetalleDocumento icep = (DetalleDocumento) iceps.next();
                if (icep.getNumeroControl().equals(docVencido.getNumeroControl())) {
                    icepsOmisosDoc.add(icep);
                    iceps.remove();
                } else {
                    break;
                }
            }
            docVencido.setDetalles(icepsOmisosDoc);
        }
        return documentosVencidos;
    }

    private List<Documento> generaDocumentoRequerimientoRenuente(EtapaVigilanciaEnum etapaVigilancia, TipoDocumentoEnum tipoDocumento, List<Documento> documentosVencidos)
            throws SGTServiceException {

        List<Documento> docsRenuentes = new ArrayList<Documento>();
        List<DetalleDocumento> nuevosIcepsOmisos = new ArrayList<DetalleDocumento>();
        int etapaDocumento = etapaVigilancia.getValor() + 1;
        VigilanciaAdministracionLocal vigilanciaAdministracionLocal;
        List<VigilanciaAdministracionLocal> listaVigilanciaAdministracionLocal = new ArrayList<VigilanciaAdministracionLocal>();

        if (!cicloVidaSgtService.validaCambioEtapa(etapaVigilancia.getValor(),
                tipoDocumento.getValor(), etapaDocumento)) {
            throw new SGTServiceException("No se pudo cambiar de etapa");
        }
        for (Documento documento : documentosVencidos) {

            Documento nuevoDocRenuente = obtenerDocumentoNuevo(documento, etapaDocumento);
            docsRenuentes.add(nuevoDocRenuente);

            for (DetalleDocumento detalleDocumento : documento.getDetalles()) {
                detalleDocumento.setNumeroControl(nuevoDocRenuente.getNumeroControl());
                detalleDocumento.setFechaCumplimiento(null);
                detalleDocumento.setIdSituacionIcep(SituacionIcepEnum.INCUMPLIDO.getValor());
                detalleDocumento.setImporteCargo(null);
                detalleDocumento.setIdTipoDeclaracion(null);
                detalleDocumento.setTieneMultaExtemporaneidad(0);
                detalleDocumento.setTieneMultaComplementaria(0);
                nuevosIcepsOmisos.add(detalleDocumento);
            }
            vigilanciaAdministracionLocal = vigilanciaAdministracionLocalService.obtenerVigilancia(nuevoDocRenuente.getVigilancia().getIdVigilancia(),
                    nuevoDocRenuente.getIdAdmonLocal());

            if (vigilanciaAdministracionLocal.getSituacionVigilanciaEnum() == SituacionVigilanciaEnum.ENVIADA_ARCA) {
                listaVigilanciaAdministracionLocal.add(vigilanciaAdministracionLocal);
            }
        }
        if (documentoDAO.actualizarDocsUltimoGenerado(documentosVencidos, UltimoDocGeneradoEnum.NO.getValor()) == null) {
            throw new SGTServiceException("No se pudo actualizar el estado de los documentos");
        }

        if (documentoDAO.guardarDocumentos(docsRenuentes) == null) {
            throw new SGTServiceException("no se pudo guardar documentos ");
        }

        try {
            detalleDocumentoDAO.guardaDetalleDocumentoBatch(nuevosIcepsOmisos);
        } catch (SeguimientoDAOException e) {
            throw new SGTServiceException("No se pudo guardar los nuevos iceps " + e);
        }
        if (!listaVigilanciaAdministracionLocal.isEmpty()) {
            vigilanciaAdministracionLocalService.updateSituacionVigilancia(listaVigilanciaAdministracionLocal, SituacionVigilanciaEnum.ERRONEA);
        }
        return docsRenuentes;
    }

    private Documento obtenerDocumentoNuevo(Documento documento, int etapaDocumento) throws SGTServiceException {
        Documento nuevoDocRenuente = new Documento();
        nuevoDocRenuente.setNumeroControlPadre(documento.getNumeroControl());
        nuevoDocRenuente.setRfc(documento.getRfc());
        nuevoDocRenuente.setBoid(documento.getBoid());
        nuevoDocRenuente.setIdEtapaVigilancia(etapaDocumento);
        nuevoDocRenuente.setVigilancia(documento.getVigilancia());
        nuevoDocRenuente.setEsUltimoGenerado(UltimoDocGeneradoEnum.SI.getValor());
        nuevoDocRenuente.setUltimoEstado(EstadoDocumentoEnum.PENDIENTE_DE_PROCESAR.getValor());
        nuevoDocRenuente.setConsideraRenuencia(documento.getConsideraRenuencia());
        nuevoDocRenuente.setFechaRegistro(new Date());

        NumeroControlDTO datos = baseDocumentoDAO.getNumeroControl(documento.getBoid(),
                documento.getTipoDocumento().getValor(), etapaDocumento);
        if (datos == null) {
            throw new SGTServiceException("No se pudo obtener el n\u00famero de control");
        }
        nuevoDocRenuente.setNumeroControl(datos.getNumeroControl());
        if (datos.getUbicacion() != null) {
            nuevoDocRenuente.setIdAdmonLocal(datos.getUbicacion().getClvALR());
            nuevoDocRenuente.setCodigoPostal(datos.getUbicacion().getCodPostal());

            if (datos.getUbicacion().getClvCRH() != null) {
                nuevoDocRenuente.setIdcrh(Integer.parseInt(datos.getUbicacion().getClvCRH()));
            }
            if (datos.getUbicacion().getClvEntidadFederativa() != null) {
                nuevoDocRenuente.setIdentidadFederativa(Integer.parseInt(datos.getUbicacion().getClvEntidadFederativa()));
            }
            if (datos.getUbicacion().getReferenciaAdicional() != null) {
                nuevoDocRenuente.setIdTipoPersona(datos.getUbicacion().getReferenciaAdicional());
            } else {
                nuevoDocRenuente.setIdTipoPersona("F");
                log.error("No se pudo obtener el idTipoPersona de RFC Ampliado con boid " + documento.getBoid() + "por defecto se dejo tipoPersona = F");
            }
        }
        return nuevoDocRenuente;
    }

    /**
     *
     * @param documentosVencidos
     */
    @Override
    @Transactional
    public void generaDocumentoLiquidacion(EtapaVigilanciaEnum etapaVigilancia, TipoDocumentoEnum tipoDocumento, List<Documento> documentosVencidos) throws SGTServiceException {

        List<Documento> documentos15 = new ArrayList<Documento>();
        List<Documento> documentos45 = new ArrayList<Documento>();

        for (Documento documento : documentosVencidos) {

            DiasPlantillaEnum resultado = validarLiquidacion(documento);
            if (resultado == null) {
                log.info("el documento " + documento.getNumeroControl() + " no procede para generar documento liquidacion");
            } else {
                switch (resultado) {
                    case DIAS_15:
                        documentos15.add(documento);
                        break;
                    case DIAS_45:
                        documentos45.add(documento);
                        break;
                    default:
                        log.info("el documento " + documento.getNumeroControl() + " no procede para generar documento liquidacion");
                }
            }
        }

        if (!documentos15.isEmpty()) {
            this.generarLiquidacion(etapaVigilancia, tipoDocumento, DiasPlantillaEnum.DIAS_15, documentos15);
        }

        if (!documentos45.isEmpty()) {
            this.generarLiquidacion(etapaVigilancia, tipoDocumento, DiasPlantillaEnum.DIAS_45, documentos45);
        }
    }

    private DiasPlantillaEnum validarLiquidacion(Documento documento) throws SGTServiceException {
        final short esCantidadMayor = 1;
        double sumaImportes = 0;
        Map<String, List<PagoIcepOmiso>> obligacionDeclaraciones = this.obtenerUltimosSeisPagosPorObligacion(documento.getBoid(), documento.getDetalles());
        if (obligacionDeclaraciones.isEmpty()) {
            return null;
        }

        obligacionDeclaraciones = validarMontoMayorObligaciones(obligacionDeclaraciones, documento);

        if (obligacionDeclaraciones.isEmpty()) {
            return null;
        }

        guardarHistoricoPagosLiquidacion(obligacionDeclaraciones);

        for (Map.Entry<String, List<PagoIcepOmiso>> entry : obligacionDeclaraciones.entrySet()) {
            for (PagoIcepOmiso icep : entry.getValue()) {
                if (icep.getEsCantidadDetMayor() == esCantidadMayor) {
                    sumaImportes += icep.getImporteaCargo();
                }
            }
        }

        if (sumaImportes > multaDocumentoDAO.obtenerMontoLiquidacion(ParametroSgtEnum.MONTO_PLANTILLA)) {
            return DiasPlantillaEnum.DIAS_45;
        } else {
            return DiasPlantillaEnum.DIAS_15;
        }
    }

    private Map<String, List<PagoIcepOmiso>> obtenerUltimosSeisPagosPorObligacion(String boid,
            List<DetalleDocumento> icepsOmisosDoc) {
        List<PagoIcepOmiso> icepsPagados;
        final int numeroIcepsFinales = 6;
        Map<String, List<PagoIcepOmiso>> obligacionDeclaraciones = new HashMap<String, List<PagoIcepOmiso>>();
        for (DetalleDocumento icepOmiso : icepsOmisosDoc) {
            icepsPagados = historicoPagosIcepDAO.consultarHistoricoPagosICEPsOmisos(new BigDecimal(boid), Integer.parseInt(icepOmiso.getIdObligacion().toString()));
            if (icepsPagados != null && icepsPagados.size() >= numeroIcepsFinales) {
                obligacionDeclaraciones.put(String.valueOf(icepOmiso.getClaveIcep()), icepsPagados);
            }
        }
        return obligacionDeclaraciones;
    }

    private Map<String, List<PagoIcepOmiso>> validarMontoMayorObligaciones(Map<String, List<PagoIcepOmiso>> declaraciones, Documento documento) {
        HashMap<String, List<PagoIcepOmiso>> declaracionesMayores;
        declaracionesMayores = new HashMap<String, List<PagoIcepOmiso>>();
        PagoIcepOmiso icepMayor = new PagoIcepOmiso();

        for (Map.Entry<String, List<PagoIcepOmiso>> entry : declaraciones.entrySet()) {
            Long montoMayor = 0L;
            for (PagoIcepOmiso icepPagado : entry.getValue()) {
                icepPagado.setNumeroControl(documento.getNumeroControl());
                icepPagado.setClaveIcep(new Long(entry.getKey()));
                if (icepPagado.getImporteaCargo() > montoMayor) {
                    montoMayor = icepPagado.getImporteaCargo();
                    icepMayor = icepPagado;
                }
            }
            if (montoMayor > multaDocumentoDAO.obtenerMontoLiquidacion(ParametroSgtEnum.MONTO_MINIMO)) {
                declaraciones.get(entry.getKey()).remove(icepMayor);
                icepMayor.setEsCantidadDetMayor(IcepRenuenteEnum.SI.getValor());
                declaraciones.get(entry.getKey()).add(icepMayor);
                declaracionesMayores.put(entry.getKey(), declaraciones.get(entry.getKey()));
            }
        }
        return declaracionesMayores;
    }

    private void guardarHistoricoPagosLiquidacion(Map<String, List<PagoIcepOmiso>> pagosLiquidacion) throws SGTServiceException {

        List<PagoIcepOmiso> totalPagos = new ArrayList<PagoIcepOmiso>();

        for (Map.Entry<String, List<PagoIcepOmiso>> entry : pagosLiquidacion.entrySet()) {

            totalPagos.addAll(entry.getValue());
        }
        try {
            historicoPagosIcepDAO.guardarHistoricoPagosLiquidacion(totalPagos);
        } catch (SeguimientoDAOException ex) {
            throw new SGTServiceException("no se pudo guardar el historico de pagos " + ex);
        }
    }

    private void generarLiquidacion(EtapaVigilanciaEnum etapaVigilancia, TipoDocumentoEnum tipoDocumento, DiasPlantillaEnum dias, List<Documento> documentos) throws SGTServiceException {

        List<Documento> documentosRenuentes = this.generaDocumentoRequerimientoRenuente(etapaVigilancia, tipoDocumento, documentos);

        if (multaDocumentoDAO.generarResolucionLiquidacion(documentosRenuentes, TipoMultaEnum.LIQUIDACION,
                null, dias.getValor()) == null) {
            throw new SGTServiceException("Ocurrio un error al generar las resoluciones de liquidacion");
        }

        if (bitacoraMultaDAO.registraBitacoraMultaMasivo(documentos, EstadoMultaEnum.PENDIENTE_DE_PROCESAR) == 0) {
            throw new SGTServiceException("No se pudo generar la bitácora de la resolucion de documentos liquidacion");
        }
    }
}
