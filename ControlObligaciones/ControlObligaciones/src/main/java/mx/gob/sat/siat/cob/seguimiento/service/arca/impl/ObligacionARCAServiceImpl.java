/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.arca.impl;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.ObligacionArcaDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.Obligacion;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.arca.ObligacionARCAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Juan
 */
@Service("obligacionARCAService")
public class ObligacionARCAServiceImpl implements ObligacionARCAService {

    @Autowired
    private ObligacionArcaDAO obligacionDAO;

    @Override
    public void insertaObligacion(List<Obligacion> obligaciones) throws SGTServiceException {
        if (obligacionDAO.insert(obligaciones) == null) {
            throw new SGTServiceException("Fallo al insertar en Obligacion");
        }
    }
}
