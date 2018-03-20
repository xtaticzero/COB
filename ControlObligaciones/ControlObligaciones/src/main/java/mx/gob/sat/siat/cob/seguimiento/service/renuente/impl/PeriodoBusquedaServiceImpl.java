/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.renuente.impl;

import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.PeriodoBusquedaDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.PeriodicidadHelper;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.renuente.PeriodoBusquedaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author juan
 */
@Service
public class PeriodoBusquedaServiceImpl implements PeriodoBusquedaService {

    @Autowired
    private PeriodoBusquedaDAO periodoBusquedaDAO;

    @Override
    public Integer insertPeriodoBusqueda(PeriodicidadHelper periodoBusqueda) throws SGTServiceException {
        Integer resultado = null;
        resultado = periodoBusquedaDAO.insertPeriodoBusqueda(periodoBusqueda);
        if (resultado == null) {
            throw new SGTServiceException("No se concluyo la inserccion satisfactoriamnete");
        }
        return resultado;
    }
}
