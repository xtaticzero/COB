/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.renuente.impl;

import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.ArchivoRenuentesDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ArchivoRenuente;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.renuente.ArchivoRenuentesService;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Alex
 */
@Service
public class ArchivoRenuentesServiceImpl implements ArchivoRenuentesService {

    @Autowired
    private ArchivoRenuentesDAO archivoRenuentesDAO;

    @Override
    public Long obtenerIdCargaRenuents() throws SGTServiceException {
        try {
            return archivoRenuentesDAO.obtenerIdCargaRenuents();
        } catch (SeguimientoDAOException ex) {
            throw new SGTServiceException(ex);
        }
    }

    @Override
    @Transactional
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public void agregaArchivoRenuente(ArchivoRenuente archivoRenuente) {
        archivoRenuentesDAO.agregaArchivoRenuente(archivoRenuente);
    }

    @Transactional(readOnly = true)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public ArchivoRenuente obtenerArchivoRenuente() {
        return archivoRenuentesDAO.obtenerArchivoRenuente();
    }
}
