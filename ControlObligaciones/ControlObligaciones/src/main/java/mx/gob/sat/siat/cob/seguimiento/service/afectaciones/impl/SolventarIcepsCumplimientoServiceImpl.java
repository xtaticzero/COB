/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.afectaciones.impl;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DetalleDocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.SituacionIcepEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.SolventarIcepsCumplimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author root
 */
@Service
public class SolventarIcepsCumplimientoServiceImpl implements SolventarIcepsCumplimientoService {
    @Autowired
    private DetalleDocumentoDAO detalleDocumentoDAO;
    
    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void solventarICEPS(List<DetalleDocumento> detalles) throws SGTServiceException {
        try {
            for (DetalleDocumento detalleDocumento : detalles) {
                detalleDocumentoDAO.cambiarEstatusICEP(detalleDocumento, SituacionIcepEnum.CUMPLIDO);
            }
        } catch (SeguimientoDAOException e) {
            throw new SGTServiceException(e.getMessage(), e);
        }
    }
    
}
