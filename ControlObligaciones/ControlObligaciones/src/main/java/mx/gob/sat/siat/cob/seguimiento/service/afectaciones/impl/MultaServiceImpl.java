/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.afectaciones.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.BaseDocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DetalleDocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.MultaDocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.MultaIcepDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaIcep;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.NumeroControlDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoMultaEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoMedioEnvioEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoMultaEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.BitacoraMultaService;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.MultaService;
import mx.gob.sat.siat.cob.seguimiento.service.arca.CancelaDoctoService;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author root
 */
@Service
public class MultaServiceImpl implements MultaService {

    private final Logger log = Logger.getLogger(MultaServiceImpl.class);
    @Autowired
    private MultaDocumentoDAO multaDocumentoDAO;
    @Autowired
    private DetalleDocumentoDAO detalleDocumentoDAO;
    @Autowired
    private MultaIcepDAO multaIcepDAO;
    @Autowired
    private BitacoraMultaService bitacoraMultaService;
    @Autowired
    private BaseDocumentoDAO baseDocumentoDAO;
    @Autowired
    private CancelaDoctoService cancelaDoctoService;

    /**
     *
     * @param documento
     * @param tipo
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException
     */
    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void generarMulta(Documento documento, TipoMultaEnum tipo) throws SGTServiceException {
        NumeroControlDTO numeroControlDTO;
        String fechaActual = Utilerias.formatearFechaDDMMYYYY(new Date());
        MultaDocumento multaDocumento = new MultaDocumento();
        multaDocumento.setNumeroControl(documento.getNumeroControl());
        numeroControlDTO=baseDocumentoDAO.getNumeroResolucion(documento.getBoid(),
                tipo.toString());
        multaDocumento.setNumeroResolucion(numeroControlDTO.getNumeroControl());
        //establecemos el rfc actual
        multaDocumento.setRfc(numeroControlDTO.getRfc());
        multaDocumento.setUltimoEstado(EstadoMultaEnum.PENDIENTE_DE_PROCESAR.getValor());
        multaDocumento.setConstanteResolucionMotivo(tipo.toString());
        try {
            multaDocumento.setFechaRegistro(Utilerias.formatearFechaDDMMAAAAHHMM(fechaActual));
        } catch (ParseException ex) {
            throw new SGTServiceException("Error al obtener fecha para generacion de multa " + ex);
        }
        if (documento.getVigilancia().getIdTipoMedioEnum() == TipoMedioEnvioEnum.ENTIDAD_FEDERATIVA) {
            multaDocumento.setUltimoEstado(EstadoMultaEnum.PENDIENTE_DE_PROCESAR_ENTIDAD.getValor());
        }
        //Se genera multa en COB
        if (multaDocumentoDAO.insert(multaDocumento) == null) {
            throw new SGTServiceException("No se pudo registrar la multa del documento " + documento.getNumeroControl());
        }
        for (DetalleDocumento dd : documento.getDetalles()) {
            MultaIcep multaIcep = new MultaIcep();
            multaIcep.setClaveIcep(dd.getClaveIcep());
            multaIcep.setNumeroControl(documento.getNumeroControl());
            multaIcep.setNumeroResolucion(multaDocumento.getNumeroResolucion());
            //Agrega iceps a la multa
            if (multaIcepDAO.insert(multaIcep) == null) {
                throw new SGTServiceException("No se pudieron registrar los ICEP asociados a la multa con numero de control " + documento.getNumeroControl() );
            }
            //Marca los iceps del documento como multados
            try {
                detalleDocumentoDAO.marcarConMulta(dd, tipo);
            } catch (SeguimientoDAOException e) {
                log.error(e);
                throw new SGTServiceException(
                        "No se pudo marcar los ICEP del documento " + documento.getNumeroControl() + " con su multa", e);
            }
        }
        //Registro en bitacora
        bitacoraMultaService.registraBitacoraMulta(
                multaDocumento.getNumeroResolucion(),
                multaDocumento.getUltimoEstado());

    }

    /**
     *
     * @param multaDocumento Debe tener el valor de numeroResolucion
     * @param cancelaEnCobranza true si se desea cancelar la multa en cobranza ,
     * false si no existe multa en cobranza
     * @throws SGTServiceException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelarMulta(MultaDocumento multaDocumento) throws SGTServiceException {

       

        //Se cancela en arca
        if (multaDocumento.getUltimoEstado() == EstadoMultaEnum.ENVIADA_A_ARCA.getValor()) {
            Documento d = new Documento();
            d.setNumeroControl(multaDocumento.getNumeroResolucion());
            cancelaDoctoService.cancelaDoctoArca(d.getNumeroControl());
        }

        if (multaDocumento.getUltimoEstado() == EstadoMultaEnum.ENVIADA_A_ARCA.getValor()
                && multaDocumento.getIdResolucion() == null) {
            // cancelar en SIR - pasar el fiIdEstatus = 33 (T_DocumentoResolucion)
            cancelaDoctoService.cancelarMultaSIR(multaDocumento.getNumeroResolucion());
            
        } else if (multaDocumento.getUltimoEstado() == EstadoMultaEnum.ENVIADA_A_ARCA.getValor()
                && multaDocumento.getIdResolucion() != null
                && multaDocumento.getUltimoEstado() != EstadoMultaEnum.PENDIENTE_DE_PROCESAR_ENTIDAD.getValor()) {
            //BajaResolucionImprocedencia
        }
        bitacoraMultaService.actualizaEstadoBitacoraMulta(multaDocumento, EstadoMultaEnum.CANCELADA);
    }

    /**
     *
     * @param multaDocumento
     * @return
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException
     */
    @Override
    public List<MultaDocumento> buscarMultasPorTipoYNumeroControl(MultaDocumento multaDocumento) throws SGTServiceException {
        List<MultaDocumento> multas = multaDocumentoDAO.buscarMultasPorNumControlYTipo(multaDocumento);
        if (multas != null) {
             for (MultaDocumento multa : multas) {
                agregarIcepsAMulta(multa);
            }
        }
        return multas;
    }

    /**
     *
     * @param numControl
     * @return
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException
     */
    @Override
    public List<MultaDocumento> buscarMultasPorNumeroControl(String numControl) throws SGTServiceException {
        
        List<MultaDocumento> multas = multaDocumentoDAO.buscarMultasPorNumControl(numControl);
        if (multas != null) {
            for (MultaDocumento multa : multas) {
                agregarIcepsAMulta(multa);
            }
        }else{
            throw new SGTServiceException();
        }    
        return multas;
    }

    private void agregarIcepsAMulta(MultaDocumento multa) throws SGTServiceException {
        List<MultaIcep> iceps = multaIcepDAO.selectPorNumeroResolucion(
                multa.getNumeroResolucion());
        if (iceps != null) {
            multa.setIceps(iceps);
        } 
    }

    @Override
    public void actualizarUltimoEstadoMulta(String numeroResol, EstadoMultaEnum estado) throws SGTServiceException {

        multaDocumentoDAO.actualizarUltimoEstadoMulta(numeroResol, estado);

    }
}
