/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.afectaciones.impl;

import java.math.BigInteger;
import java.util.Date;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.CumplimientoDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.CumplimientoService;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author root
 */
@Service
public class CumplimientoServiceImpl implements CumplimientoService {
    
    @Autowired
    private CumplimientoDAO cumplimientoDAO;

    @Override
    @Propagable(exceptionClass = SGTServiceException.class)
    public BigInteger obtenerMaximoIdentificadorCumplimiento() throws SGTServiceException {
        BigInteger maximoIdentificador;
        maximoIdentificador = cumplimientoDAO.obtenerMaximoIdentificadorCumplimiento();
        if(maximoIdentificador == null){
            throw new SGTServiceException("No se pudo obtener el máximo identificador de cumplimiento.");
        }
        return maximoIdentificador;
    }

    @Override
    @Propagable(exceptionClass = SGTServiceException.class)
    public void eliminarCumplimientosProcesados() throws SGTServiceException  {
        if (cumplimientoDAO.eliminarCumplimientosProcesados()==null){
            throw new SGTServiceException("No se pudieron eliminar los cumplimientos procesados.");
        }
    }

    @Override
    @Propagable(exceptionClass = SGTServiceException.class)
    public void eliminarCumplimientosPorFechaMto(Date fechaMantenimiento) throws SGTServiceException {
        if (cumplimientoDAO.eliminarCumplimientosPorFechaMto(fechaMantenimiento)==null){
            throw new SGTServiceException("No se pudieron eliminar los cumplimientos de la fecha.");
        }
    }

    @Override
    @Transactional
    public Integer actualizarDetalleConCumplimiento(Documento documento, Integer idSituacionIcep) throws SGTServiceException {
        Integer actualizados=cumplimientoDAO.actualizarDetalleConCumplimiento(documento, idSituacionIcep);
        if(actualizados==null || actualizados!=documento.getDetalles().size()){
            throw new SGTServiceException("No se pudieron actualizar los detalles del documento " + documento.getNumeroControl());
        }
        return actualizados;
    }
    
}
