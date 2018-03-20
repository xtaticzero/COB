/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.aprobacion.impl;

import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DetalleDocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.IcepsAprobarDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.EstadoDocumentoDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.aprobacion.AprobarVigilanciasService;
import mx.gob.sat.siat.cob.seguimiento.service.aprobacion.ProcesoValidacionCumplimientoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author root
 */
@Service
public class ProcesoValidacionCumplimientoServiceImpl implements ProcesoValidacionCumplimientoService {

    @Autowired
    private IcepsAprobarDAO icepsAprobarDAO;
    @Autowired
    private DetalleDocumentoDAO detalleDocumentoDAO;
    @Autowired
    private AprobarVigilanciasService aprobarVigilanciasService;
    private final Logger log = Logger.getLogger(ProcesoValidacionCumplimientoServiceImpl.class);

    @Override
    @Transactional(rollbackFor = SGTServiceException.class, propagation = Propagation.REQUIRES_NEW)
    public void validarVigilables(Documento documento) throws SGTServiceException {
        if (icepsAprobarDAO.realizarCumplimientoIcep(documento) == null) {
            log.debug("No se pudo realizar el cambio "
                    + "de situacion de los iceps del documento-->"
                    + documento.getNumeroControl());
            throw new SGTServiceException("No se pudo realizar el cambio "
                    + "de situacion de los iceps del documento-->"
                    + documento.getNumeroControl());
        }
        if (detalleDocumentoDAO.getNumeroVigilables(documento.getNumeroControl()) == 0) {
            EstadoDocumentoEnum estado = null;

            if (documento.tieneICEPScancelados()) {
                if (documento.getVigilancia().getIdTipoDocumentoEnum() == TipoDocumentoEnum.CARTA_EXHORTO) {
                    estado = EstadoDocumentoEnum.ANULADO;
                } else if (documento.getVigilancia().getIdTipoDocumentoEnum() != TipoDocumentoEnum.MULTA) {
                    estado = EstadoDocumentoEnum.CANCELADO;
                }
            } else {
                if (documento.getVigilancia().getIdTipoDocumentoEnum() == TipoDocumentoEnum.CARTA_EXHORTO) {
                    estado = EstadoDocumentoEnum.CUMPLIDO;
                } else if (documento.getVigilancia().getIdTipoDocumentoEnum() != TipoDocumentoEnum.MULTA) {
                    estado = EstadoDocumentoEnum.CUMPLIDO_SIN_NOTIFICAR;
                }
            }
            EstadoDocumentoDTO datos = new EstadoDocumentoDTO();
            datos.setNumeroControl(documento.getNumeroControl());
            datos.setEstado(estado);
            aprobarVigilanciasService.getListaActEstado().add(datos);
            //aqui antes se actualizaba con documentoAprobarDAO.actualizarEstatusDocumento
        }
    }

}
