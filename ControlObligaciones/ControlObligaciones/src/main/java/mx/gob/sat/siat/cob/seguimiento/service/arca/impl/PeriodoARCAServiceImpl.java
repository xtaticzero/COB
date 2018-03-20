/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.arca.impl;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.PeriodoArcaDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.Periodo;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.arca.PeriodoARCAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Juan
 */
@Service("periodoARCAService")
public class PeriodoARCAServiceImpl implements PeriodoARCAService {

    @Autowired
    private PeriodoArcaDAO periodoDAO;

    @Override
    public void insertaPeriodo(List<Periodo> periodo) throws SGTServiceException {
        if (periodoDAO.insert(periodo) == null) {
            throw new SGTServiceException("Ocurrio un problema al insertar en Periodo");
        }
    }
}
