/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.afectaciones.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.BaseDocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.BitacoraMultaDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DetalleDocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.MultaComplementariaDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.MultaDocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.MultaIcepDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoMultaEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.SituacionIcepEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TieneMultaEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoDeclaracionEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoMedioEnvioEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoMultaEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.MultaService;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.ProcesoMultaComplementariaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author root
 */
@Service
public class ProcesoMultaComplementariaServiceImpl implements ProcesoMultaComplementariaService{
    @Autowired
    private MultaComplementariaDAO multaComplementariaDAO;    
    @Autowired
    private DetalleDocumentoDAO detalleDocumentoDAO;    
    @Autowired
    private MultaService multaService;    
    @Autowired
    private BaseDocumentoDAO baseDocumentoDAO;    
    @Autowired
    private MultaDocumentoDAO multaDocumentoDAO;
    @Autowired
    private MultaIcepDAO multaIcepDAO;
    @Autowired
    private BitacoraMultaDAO bitacoraMultaDAO;

    @Override
    public void afectarDetallesConComplementaria() throws SGTServiceException {
        Map condiciones = new HashMap<String, Object>();
         condiciones.put("tipoDeclaracionPrevia", TipoDeclaracionEnum.NORMAL.getValor());
                condiciones.put("anuladoPadron", SituacionIcepEnum.CANCELADO_POR_MOVIMIENTOS_PADRON.getValor());
                condiciones.put("multacomplementaria", TieneMultaEnum.NO_TIENE.ordinal());
                condiciones.put("multaExtemporaneidad", TieneMultaEnum.TIENE.ordinal());
                condiciones.put("canceladoAutoridad", EstadoDocumentoEnum.CANCELADO_POR_AUTORIDAD.getValor());
                condiciones.put("tipoDeclaracionCumplimiento",TipoDeclaracionEnum.COMPLEMENTARIA.getValor());
                condiciones.put("mesesMaximo", 6);
        if(multaComplementariaDAO.afectarDetallesConComplementaria(condiciones)==null){
            throw new SGTServiceException("No se pudieron afectar los detalles que tienen declaración complementaria.");
        }
    }

    @Override
    public List<Documento> documentosMultaComplementaria() throws SGTServiceException {
        return multaComplementariaDAO.documentosMultaComplementaria(EstadoDocumentoEnum.CANCELADO_POR_AUTORIDAD.getValor(), TipoMedioEnvioEnum.ENTIDAD_FEDERATIVA);
    }
    @Override
    public List<Documento> documentosMultaComplementaria(String idVigilancia, String idLocal) throws SGTServiceException {
        return multaComplementariaDAO.documentosMultaComplementaria(EstadoDocumentoEnum.CANCELADO_POR_AUTORIDAD.getValor(), idVigilancia, idLocal);
    }

    @Override
    public void procesarDocumentoMultaComplementaria(Documento documento) throws SGTServiceException{
        List<DetalleDocumento> detallesDocumento;
        detallesDocumento = detalleDocumentoDAO.consultaXNumControl(documento.getNumeroControl());
        for(DetalleDocumento detalleDocumento : detallesDocumento){
            if(detalleDocumento.getIdCumplimientoCompl()!= BigInteger.valueOf(0) && detalleDocumento.getTieneMultaComplementaria()==0){
                documento.getDetalles().add(detalleDocumento);
            }
        }
        multaService.generarMulta(documento, TipoMultaEnum.COMPLEMENTARIA);
    }
    @Override
    @Transactional
    public void generarMultasComplementarias(List<Documento> documentos) throws SGTServiceException {
        baseDocumentoDAO.borrarTablaNumeroResolucion();
        
        //registramos en bitàcora pero separamos el tipo de multa que vamos a generar
        List<Documento> documentosEntidadFederativa = new ArrayList<Documento>();
        List<Documento> documentosSinEntidadFederativa = new ArrayList<Documento>();
        for(Documento documento: documentos){
            if(documento.getVigilancia().getIdTipoMedioEnum() == TipoMedioEnvioEnum.ENTIDAD_FEDERATIVA){
                documentosEntidadFederativa.add(documento);
            }                
            else{
                documentosSinEntidadFederativa.add(documento);
            }                
        }

        if (baseDocumentoDAO.generaNumeroResoluciones(documentos, TipoMultaEnum.COMPLEMENTARIA.toString(), 0) == 0) {
            throw new SGTServiceException("No se pudo obtener numeros resolucion");
        }
        if(!documentosEntidadFederativa.isEmpty()){
            generaMultaTipoMedio(documentosEntidadFederativa, true);
        }
        if(!documentosSinEntidadFederativa.isEmpty()){
            generaMultaTipoMedio(documentosSinEntidadFederativa, false);
        }
        
        if (multaComplementariaDAO.marcarIcepsMultaComplementaria(documentos) == 0) {
            throw new SGTServiceException("No se pudieron marcar los icep con multa complementaria");
        }
        
    }
    private void generaMultaTipoMedio(List<Documento> documentos, boolean conEntidad) throws SGTServiceException{
        TipoMedioEnvioEnum tipoMedioEnvioEnum;
        EstadoMultaEnum estadoMultaEnum;
        String con;
        if(conEntidad){
            tipoMedioEnvioEnum = TipoMedioEnvioEnum.ENTIDAD_FEDERATIVA;
            estadoMultaEnum = EstadoMultaEnum.PENDIENTE_DE_PROCESAR_ENTIDAD;
            con="con";
        }else{
            tipoMedioEnvioEnum = null;
            estadoMultaEnum = EstadoMultaEnum.PENDIENTE_DE_PROCESAR;
            con="sin";
        }
        //Se genera multa en COB
        if( multaDocumentoDAO.generarMultaMasivaSeguimiento(documentos, TipoMultaEnum.COMPLEMENTARIA, tipoMedioEnvioEnum) == 0){
            throw new SGTServiceException("No se pudieron generar la multas para documentos " + con + " medio de envio de entidad federativa");
        }
        //Guardar iceps de multas
        if (multaIcepDAO.insertarIcepsPorMultasComplementarias(documentos) == null) {
            throw new SGTServiceException("No se pudieron registrar los ICEP asociados a la multa " + con + " entidad federativa");
        }
        
        if(bitacoraMultaDAO.registraBitacoraMultaMasivo(documentos, estadoMultaEnum) == null){
            throw new SGTServiceException("No se pudo generar la bitácora para documentos " + con + " medio de envio de entidad federativa");
        }
        
        
    }
}
