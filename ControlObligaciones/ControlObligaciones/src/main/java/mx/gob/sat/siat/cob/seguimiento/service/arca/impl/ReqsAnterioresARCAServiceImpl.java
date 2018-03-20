/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.arca.impl;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.ReqsAnterioresDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.RequerimientosAnteriores;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.arca.ReqsAnterioresARCAService;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Juan
 */
@Service("reqsAnterioresARCAService")
public class ReqsAnterioresARCAServiceImpl implements ReqsAnterioresARCAService {

    @Autowired
    private ReqsAnterioresDAO rqsAnterioresDAO;

    @Propagable(exceptionClass = SGTServiceException.class)
    @Override
    public Integer guardaReqsAnteriores(List<RequerimientosAnteriores> reqsAnteriores) {
        return rqsAnterioresDAO.guardaReqsAnteriores(reqsAnteriores);
    }
}
