/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.afectaciones.impl;

import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.MultaComplementariaService;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.MultaComplementariaDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author root
 */
@Service("multaComplementariaService")
public class MultaComplementariaServiceImpl implements MultaComplementariaService {
 @Autowired
 private MultaComplementariaDAO multaComplementariaDAO;
    @Override
    @Transactional
    public Integer actualizarDetalleConComplementaria(Documento documento) throws SGTServiceException {
        Integer actualizados=multaComplementariaDAO.actualizarDetalleConComplementaria(documento);
        if(actualizados==null || actualizados!=documento.getDetalles().size()){
            throw new SGTServiceException("No se pudieron actualizar los detalles de complementaria para el documento " + documento.getNumeroControl());
        }
        return actualizados;
    }
    
}
