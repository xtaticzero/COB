/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.afectaciones.impl;

import java.math.BigInteger;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.AfectacionXAutoridadDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.RequerimientosAnteriores;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.ComboStr;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MtvoCancelDoc;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AfectacionXAutoridad;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ConsultasRenuentes;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaDocumentoAfectaciones;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ReporteAfectacionXAutoridadDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaAdministracionLocal;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.AfectacionEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.SituacionIcepEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.SituacionVigilanciaEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.AfectacionService;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.MultaService;
import mx.gob.sat.siat.cob.seguimiento.service.arca.CancelaDoctoService;
import mx.gob.sat.siat.cob.seguimiento.service.extraccion.DocumentoService;
import mx.gob.sat.siat.cob.seguimiento.service.extraccion.VigilanciaAdministracionLocalService;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import mx.gob.sat.siat.exception.DaoException;
import mx.gob.sat.siat.modelo.dao.SegbMovimientosDAO;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author root
 */
@Service
public class AfectacionServiceImpl implements AfectacionService {

    @Autowired
    private DocumentoService documentoService;
    @Autowired
    private CancelaDoctoService cancelaDoctoService;
    @Autowired
    private DocumentoDAO documentoDAO;
    @Autowired
    private AfectacionXAutoridadDAO afectacionDao;
    @Autowired
    @Qualifier("segbMovimientosDAOImpl")
    private SegbMovimientosDAO segbMovimientosDAO;
    @Autowired
    private MultaService multaService;
    @Autowired
    private VigilanciaAdministracionLocalService vigilanciaAdministracionLocalService;
    private final Logger log = Logger.getLogger(AfectacionServiceImpl.class);

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void generaDocumentosAfectacion(Documento documento, AfectacionEnum afectacionEnum) throws SGTServiceException {
        //el documento padre debe ser el ultimo generado y no debe estar cancelado
        VigilanciaAdministracionLocal vigilanciaAdministracionLocal;
        if (documento.getEsUltimoGenerado() != 0
                && documento.getUltimoEstado() != EstadoDocumentoEnum.CANCELADO.getValor()) {

            Documento documentoInsertar = new Documento();

            documentoInsertar.setNumeroControlPadre(documento.getNumeroControlPadre());
            documentoInsertar.setRfc(documento.getRfc());
            documentoInsertar.setBoid(documento.getBoid());
            documentoInsertar.setIdEtapaVigilancia(documento.getIdEtapaVigilancia());
            documentoInsertar.setVigilancia(documento.getVigilancia());
            documentoInsertar.setEsUltimoGenerado(1);
            documentoInsertar.setUltimoEstado(EstadoDocumentoEnum.NO_GENERADO.getValor());
            documentoInsertar.setConsideraRenuencia(documento.getConsideraRenuencia());
            documento.setEsUltimoGenerado(0);
            documentoService.actualizaSgtDocumento(documento);
            List<DetalleDocumento> icepsOmisos;
            //cuando es afectación por cumplimiento vamos por los iceps omisos,
            //en otro caso ya los trae el documento padre
            icepsOmisos = consultarICEPsOmisos(documento);

            documentoInsertar.setDetalles(new ArrayList<DetalleDocumento>());
            for (DetalleDocumento detalleDocumento : icepsOmisos) {
                detalleDocumento.setNumeroControl(null);
                detalleDocumento.setFechaCumplimiento(null);
                detalleDocumento.setIdSituacionIcep(SituacionIcepEnum.INCUMPLIDO.getValor());
                detalleDocumento.setImporteCargo(null);
                detalleDocumento.setIdTipoDeclaracion(null);
                detalleDocumento.setTieneMultaExtemporaneidad(0);
                detalleDocumento.setTieneMultaComplementaria(0);
                documentoInsertar.getDetalles().add(detalleDocumento);
            }
            //insertamos el nuevo documento
            documentoService.guardaDocumento(documentoInsertar);
            //el nuevo documento sigue con la misma administración local
            documentoInsertar.setIdAdmonLocal(documento.getIdAdmonLocal());
            //si la vigilancia a la que pertenece el documento ya fue enviada a ARCA volvemos la situación a erronea para que
            //la vigilancia sea reprocesada
            vigilanciaAdministracionLocal = vigilanciaAdministracionLocalService.obtenerVigilancia(documentoInsertar.getVigilancia().getIdVigilancia(),
                    documentoInsertar.getIdAdmonLocal());
            if (vigilanciaAdministracionLocal.getSituacionVigilanciaEnum() == SituacionVigilanciaEnum.ENVIADA_ARCA) {
                List<VigilanciaAdministracionLocal> listaVigilanciaAdministracionLocal = new ArrayList<VigilanciaAdministracionLocal>();
                listaVigilanciaAdministracionLocal.add(vigilanciaAdministracionLocal);
                vigilanciaAdministracionLocalService.updateSituacionVigilancia(listaVigilanciaAdministracionLocal, SituacionVigilanciaEnum.ERRONEA);
            }

            log.info("el n\u00FAmero de control del documento generado es " + documentoInsertar.getNumeroControl() + " generado a partir del documento " + documento.getNumeroControl());
        } else {
            throw new SGTServiceException("No se puede generar un documento a partir de un documento cancelado o no sea el \u00FAltimo generado");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void cancelarDocumento(Documento doc) throws SGTServiceException {
        if(!documentoService.actualizaEstadoBitacoraDocumento(doc, EstadoDocumentoEnum.CANCELADO)){
            throw new SGTServiceException("Error al cambiar el estado del documento");
        }

        cancelarDocumentoARCA(doc);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    @Propagable(exceptionClass = SGTServiceException.class)
    public boolean cancelarDocumentoXAutoridad(List<Documento> docs, MtvoCancelDoc motivoCancelacion) throws SGTServiceException{
        boolean resp =false;
        //SE ACTUALIZA EL ESTADO DEL DOCUMENTO A CANCELADO POR AUTORIDAD
        try {
        
            for (Documento doc : docs) {
                //SE BUSCAN LAS MULTAS DE CADA DOCUMENTO
                List<MultaDocumento> multas = multaService.buscarMultasPorNumeroControl(doc.getNumeroControl());

                //SE VALIDA SI EXISTEN O NO MULTAS PARA CANCELAR EL DOCUMENTO EN ARCA
                if (multas != null && !multas.isEmpty()) {
                    //SE CANCELAN LAS MULTAS DE CADA DOCUMENTO
                    //DENTRO DEL METODO CANCELARMULTA, SE CANCELA EL DOCUMENTO EN ARCA
                    for (MultaDocumento multa : multas) {
                        multaService.cancelarMulta(multa);
                    }
                }
                cancelaDoctoService.cancelaDoctoArca(doc.getNumeroControl());
            }
            resp = documentoService.cancelaDocumentoXAutoridad(docs, motivoCancelacion, EstadoDocumentoEnum.CANCELADO_POR_AUTORIDAD);
            
        } catch (SGTServiceException e) {
            log.error(e.getMessage());
            throw new SGTServiceException(ConstantsCatalogos.MSG_ERROR_AL_CANCELAR, e);
        }
        return resp;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void cancelarDocumentoARCA(Documento doc) throws SGTServiceException {
        cancelaDoctoService.cancelaDoctoArca(doc.getNumeroControl());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleDocumento> consultarICEPsOmisos(Documento doc) {
        return documentoDAO.consultarICEPsOmisos(doc);
    }

    /**
     * Metodo para la busqueda de ICEP's susceptibles de afectacion por
     * autoridad
     *
     * @param numeroControl
     * @return
     * @throws SGTServiceException
     */
    @Override
    @Propagable(exceptionClass = SGTServiceException.class)
    public List<AfectacionXAutoridad> searchDocsAfectacionByNumeroControl(String numeroControl) throws SGTServiceException {

        List<AfectacionXAutoridad> afectaciones = afectacionDao.searchDocsAfectacionByNumeroControl(numeroControl);
        if (afectaciones == null) {
            throw new SGTServiceException(ConstantsCatalogos.MSG_SIN_RESULTADOS_NUM_CONTROL);
        }
        return afectaciones;
    }

    /**
     * Metodo para la busqueda de documentos asociados a un numero de control
     *
     * @param numeroControl
     * @return
     * @throws SGTServiceException
     */
    @Override
    @Propagable(exceptionClass = SGTServiceException.class)
    public List<AfectacionXAutoridad> searchAfectacionXAutoridadByNumeroControl(String numeroControl) throws SGTServiceException {

        List<AfectacionXAutoridad> afectaciones = afectacionDao.searchAfectacionXAutoridadByNumeroControl(numeroControl);
        if (afectaciones == null) {
            throw new SGTServiceException(ConstantsCatalogos.MSG_NO_SE_ENCONTRARON_DOCS_ASOCIADOS_NUM_CONT);
        }
        return afectaciones;
    }

    /**
     * Metodo para la busqueda de documentos asociados a un RFC
     *
     * @param rfc
     * @return
     * @throws SGTServiceException
     */
    @Override
    @Propagable(exceptionClass = SGTServiceException.class)
    public List<AfectacionXAutoridad> searchAfectacionXAutoridadByRFC(String rfc) throws SGTServiceException {

        List<AfectacionXAutoridad> afectaciones = afectacionDao.searchAfectacionXAutoridadByRFC(rfc);
        if (afectaciones == null) {
            throw new SGTServiceException(ConstantsCatalogos.MSG_NO_SE_ENCONTRARON_DOCS_ASOCIADOS);
        }
        return afectaciones;
    }

    /**
     * Metodo para el registro del movimiento en bitacora
     *
     * @param segMovDto
     * @return
     * @throws SGTServiceException
     */
    @Override
    @Propagable(exceptionClass = SGTServiceException.class)
    public BigInteger registraBitacora(SegbMovimientoDTO segMovDto) throws SGTServiceException {
        try {
            return segbMovimientosDAO.insert(segMovDto);
        } catch (DaoException ex) {
            log.error(ex);
            throw new SGTServiceException(ConstantsCatalogos.MSG_ERROR_BITACORA + "\n" + ex);
        }
    }

    @Override
    @Propagable(exceptionClass = SGTServiceException.class)
    public List<ConsultasRenuentes> searchAfectacionXAutoridadByBoId(String boId, String tipoPersona) throws SGTServiceException {

        List<ConsultasRenuentes> afectaciones = afectacionDao.searchAfectacionXAutoridadByBoId(boId, tipoPersona);
        if (afectaciones == null) {
            throw new SGTServiceException(ConstantsCatalogos.MSG_NO_SE_ENCONTRARON_DOCS_ASOCIADOS);
        }
        return afectaciones;
    }

    @Override
    @Propagable(exceptionClass = SGTServiceException.class)
    public List<ComboStr> obtenerBoId(String rfc, String tipoPersona) throws SGTServiceException {

        List<ComboStr> boIds = afectacionDao.obtenerBoId(rfc, tipoPersona);
        if (boIds == null) {
            throw new SGTServiceException(ConstantsCatalogos.MSG_NO_SE_ENCONTRARON_DOCS_ASOCIADOS);
        }
        return boIds;
    }

    @Override
    @Propagable(exceptionClass = SGTServiceException.class)
    public List<AfectacionXAutoridad> searchDocsAfectacionByNumeroControl(String numeroControl, String tipoPersona) throws SGTServiceException {

        List<AfectacionXAutoridad> afectaciones = afectacionDao.searchDocsAfectacionByNumeroControl(numeroControl, tipoPersona);
        if (afectaciones == null) {
            throw new SGTServiceException(ConstantsCatalogos.MSG_SIN_RESULTADOS_NUM_CONTROL);
        }
        return afectaciones;
    }

    @Override
    @Propagable(exceptionClass = SGTServiceException.class)
    public List<AfectacionXAutoridad> searchDocsAfectacionNumControl(String numeroControl) throws SGTServiceException {

        List<AfectacionXAutoridad> afectaciones = afectacionDao.searchDocsAfectacionNumControl(numeroControl);
        if (afectaciones == null) {
            throw new SGTServiceException(ConstantsCatalogos.MSG_SIN_RESULTADOS_NUM_CONTROL);
        }
        return afectaciones;
    }

    @Override
    @Propagable(exceptionClass = SGTServiceException.class)
    public List<AfectacionXAutoridad> searchDocsAfectacionByNumeroControlCancelacion(String numeroControl) throws SGTServiceException {

        List<AfectacionXAutoridad> afectaciones = afectacionDao.searchDocsAfectacionByNumeroControlCancelacion(numeroControl);
        if (afectaciones == null) {
            throw new SGTServiceException(ConstantsCatalogos.MSG_SIN_RESULTADOS_NUM_CONTROL);
        }
        return afectaciones;
    }

    @Override
    @Propagable(exceptionClass = SGTServiceException.class)
    public List<MultaDocumentoAfectaciones> obtenerMultasPorNumControl(String numControl) throws SGTServiceException {

        List<MultaDocumentoAfectaciones> multas = afectacionDao.obtenerMultasPorNumControl(numControl);

        return multas;
    }

    @Override
    @Propagable(exceptionClass = SGTServiceException.class)
    public List<MultaDocumentoAfectaciones> obtenerMultasPorNumControlTipoMulta(String numRes, String tipoMulta) throws SGTServiceException {

        List<MultaDocumentoAfectaciones> multas = afectacionDao.obtenerMultasPorNumControlTipoMulta(numRes, tipoMulta);

        return multas;
    }

    @Propagable(exceptionClass = SGTServiceException.class)
    @Override
    public List<RequerimientosAnteriores> origenMultaArcaPosteriores(String numControl) throws SGTServiceException {
        List<RequerimientosAnteriores> reqAnteriores = afectacionDao.origenMultaArcaPosteriores(numControl);

        return reqAnteriores;
    }

    @Propagable(exceptionClass = SGTServiceException.class)
    @Override
    public List<RequerimientosAnteriores> origenMultaArcaAnteriores(String numControl) throws SGTServiceException {
        List<RequerimientosAnteriores> reqAnteriores = afectacionDao.origenMultaArcaAnteriores(numControl);

        return reqAnteriores;
    }

    @Override
    @Propagable(exceptionClass = SGTServiceException.class)
    public List<ReporteAfectacionXAutoridadDTO> obtenerDatosReporte(String numControl)
            throws SGTServiceException {

        List<ReporteAfectacionXAutoridadDTO> datos = afectacionDao.obtenerReporte(numControl);

        return datos;
    }
}
