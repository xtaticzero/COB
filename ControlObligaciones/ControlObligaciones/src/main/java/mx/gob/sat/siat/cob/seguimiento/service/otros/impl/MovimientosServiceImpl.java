/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.otros.impl;

import mx.gob.sat.siat.cob.seguimiento.service.otros.MovimientosService;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
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
public class MovimientosServiceImpl implements MovimientosService {

    @Autowired
    @Qualifier("segbMovimientosDAOImpl")
    private SegbMovimientosDAO segbMovimientosDAO;
    private final Logger log = Logger.getLogger(MovimientosServiceImpl.class);

    @Override
    @Propagable(catched = true)
    @Transactional
    public void registrarMovimiento(SegbMovimientoDTO movimiento) {
        try {
            segbMovimientosDAO.insert(movimiento);
        } catch (DaoException e) {
            log.error("No se pudo registrar el movimiento", e);
        }
    }

}
