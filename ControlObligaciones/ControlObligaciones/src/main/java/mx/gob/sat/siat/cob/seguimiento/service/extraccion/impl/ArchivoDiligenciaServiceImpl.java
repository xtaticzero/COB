/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.extraccion.impl;

import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.ArchivoDiligenciaDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ArchivoDiligencia;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.extraccion.ArchivoDiligenciaService;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author root
 */
@Service
public class ArchivoDiligenciaServiceImpl implements ArchivoDiligenciaService {

    @Autowired
    private ArchivoDiligenciaDAO archivoDiligenciaDAO;

    @Transactional(readOnly = true)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public ArchivoDiligencia obtenerArchivoDiligencia(Long idEntidadFederativa) {

        return archivoDiligenciaDAO.obtenerArchivoDiligencia(idEntidadFederativa);

    }

    @Transactional(readOnly = true)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public void agregaArchivoDiligencia(ArchivoDiligencia archivoDiligencia) throws SGTServiceException {
        try {
            archivoDiligenciaDAO.agregaArchivoDiligencia(archivoDiligencia);
        } catch (SeguimientoDAOException ex) {
            throw new SGTServiceException(ex);
        }
    }

    @Transactional(readOnly = true)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public void agregaRutaArchivoDiligencia(ArchivoDiligencia archivoDiligencia) throws SGTServiceException {
        try {
            archivoDiligenciaDAO.agregaRutaArchivoDiligencia(archivoDiligencia);
        } catch (SeguimientoDAOException ex) {
            throw new SGTServiceException(ex);
        }
    }
}
