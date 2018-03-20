/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.afectaciones.impl;

import java.util.Date;

import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.BitacoraMultaDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.MultaDocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.BitacoraMulta;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoMultaEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.BitacoraMultaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author root
 */
@Service
public class BitacoraMultaServiceImpl implements BitacoraMultaService {

    @Autowired
    private BitacoraMultaDAO bitacoraMultaDAO;
    @Autowired
    private MultaDocumentoDAO multaDocumentoDAO;

    @Override
    @Transactional
    public void registraBitacoraMulta(String numeroResolucion, int estatus) throws SGTServiceException {
        BitacoraMulta bitacora = new BitacoraMulta();
        bitacora.setFechaMovimiento(new Date());
        bitacora.setIdEstadoResolucion(estatus);
        bitacora.setNumeroResolucion(numeroResolucion);
        bitacoraMultaDAO.insert(bitacora);
    }

    @Override
    @Transactional
    public void actualizaEstadoBitacoraMulta(MultaDocumento multaDocumento, EstadoMultaEnum estadoMultaEnum) throws SGTServiceException {
        Integer modificado=null;
        BitacoraMulta bitacora = new BitacoraMulta();
        bitacora.setFechaMovimiento(new Date());
        bitacora.setIdEstadoResolucion(estadoMultaEnum.getValor());
        bitacora.setNumeroResolucion(multaDocumento.getNumeroResolucion());
        modificado = bitacoraMultaDAO.insert(bitacora);
        if(modificado==null || modificado<=0){
            throw new SGTServiceException("No se pudo registrar en bitácora el cambio de estado de multa");
        }
        multaDocumento.setUltimoEstado(estadoMultaEnum.getValor());
        modificado =multaDocumentoDAO.update(multaDocumento);
        if (modificado==null || modificado<=0) {
            throw new SGTServiceException("No se pudo modificar el estado de la multa a cancelada");
        }
    }
}
