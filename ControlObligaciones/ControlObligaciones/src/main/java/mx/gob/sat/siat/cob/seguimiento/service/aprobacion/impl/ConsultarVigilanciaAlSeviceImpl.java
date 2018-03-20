package mx.gob.sat.siat.cob.seguimiento.service.aprobacion.impl;

import java.sql.SQLException;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.VigilanciaAlDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AlscDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ReporteVigilancia;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaAl;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoMedioEnvioEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.aprobacion.ConsultarVigilanciaAlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author root
 */
@Service
public class ConsultarVigilanciaAlSeviceImpl implements ConsultarVigilanciaAlService {

    @Autowired
    private VigilanciaAlDAO vigilanciaAlDAO;

    /**
     *
     * @param vigilancia
     * @return
     * @throws SGTServiceException
     */
    @Override
    @Transactional(readOnly = true)
    public VigilanciaAl consultarVigilancia(VigilanciaAl vigilancia) throws SGTServiceException {
        String numeroCarga = vigilancia.getNumeroCarga();
        VigilanciaAl v = vigilanciaAlDAO.buscarVigilancia(numeroCarga);
        if (v == null) {
            throw new SGTServiceException("No se encontraron resultados para la vigilancia " + numeroCarga);
        }
        v.setDetalle(vigilanciaAlDAO.buscarDetalleVigilancia(numeroCarga));
        if (v.getDetalle() == null) {
            throw new SGTServiceException("No se pudo consultar el detalle de la vigilancia, por favor intente mas tarde");
        }
        if (v.getTipoMedioEnvio() == TipoMedioEnvioEnum.ENTIDAD_FEDERATIVA) {
            v.setDetalleEF(vigilanciaAlDAO.buscarDetalleVigilanciaEF(numeroCarga));
            if (v.getDetalleEF() == null) {
                throw new SGTServiceException("No se pudo consultar el detalle de entidad federativa de la vigilancia, por favor intente mas tarde");
            }
        }
        v.setNumeroCarga(numeroCarga);
        return v;
    }
    
    /**
     *
     * @param idVigilancia
     * @return
     * @throws SGTServiceException
     */
    @Override
    @Transactional(readOnly = true)
    public ReporteVigilancia consultaReporteVigilancia(String idVigilancia) throws SGTServiceException {
        ReporteVigilancia reporte = vigilanciaAlDAO.consultaReporteVigilancia(idVigilancia);
        if (reporte == null) {
            throw new SGTServiceException("No se encontraron resultados para la vigilancia " + idVigilancia);
        }
        return reporte;
    }    

    /**
     *
     * @param idVigilancia
     * @return
     * @throws SGTServiceException
     */
    @Override
    public List<AlscDTO> consultaAlscXVigilancia(Integer idVigilancia) throws SGTServiceException {
        List<AlscDTO> lstAlsc;
        lstAlsc = null;
        try{
            lstAlsc = vigilanciaAlDAO.buscarALSCVigilancia(idVigilancia);
            return lstAlsc;
        }catch(SQLException sQLException){            
            throw new SGTServiceException(sQLException);            
        }
    }
    
    /**
     *
     * @param idVigilancia
     * @return
     * @throws SGTServiceException
     */
    @Override
    public List<AlscDTO> consultaEFXVigilancia(Integer idVigilancia) throws SGTServiceException {
        List<AlscDTO> lstEF;
        lstEF = null;
        try{
            lstEF = vigilanciaAlDAO.buscarEFVigilancia(idVigilancia);
            return lstEF;
        }catch(SQLException sQLException){            
            throw new SGTServiceException(sQLException);            
        }
    }
}
