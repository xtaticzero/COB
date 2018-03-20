package mx.gob.sat.siat.cob.seguimiento.service.otros.impl;

import java.util.List;
import java.util.Map;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.OmisosEFDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.VigilanciaDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ConsultaVigilancia;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleReporteFacturaBean;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.RfcNombreDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.UbicacionEFDTO;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.otros.OmisosEFService;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author christian.ventura
 */
@Service
public class OmisosEFServiceImpl implements OmisosEFService {

    @Autowired
    private VigilanciaDAO vigilanciaDAO;

    @Autowired
    private OmisosEFDAO omisosEFDAO;

    /**
     *
     * @return
     * @throws SeguimientoDAOException
     */
    @Transactional(readOnly = true,
                   rollbackFor = { SeguimientoDAOException.class })
    @Override
    public List<ConsultaVigilancia> consultaVigilancias() throws SeguimientoDAOException{
        return vigilanciaDAO.consultaVigilancia();
    }
    /**
     *
     * @return
     * @throws SeguimientoDAOException
     */
    @Transactional(readOnly = true,
                   rollbackFor = { SeguimientoDAOException.class })
    @Override
    public List<ConsultaVigilancia> consultaVigilanciasEF() throws SeguimientoDAOException{
        return vigilanciaDAO.consultaVigilanciaEF();
    }
    /**
     *
     * @param idVigilancia
     * @param identidadFederativa 
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<RfcNombreDTO> consultaBoidDatosGenerales(int idVigilancia, int identidadFederativa){
        return omisosEFDAO.consultarRfcNombre(idVigilancia, identidadFederativa);
    }
    /**
     *
     * @param pBoid
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public List<UbicacionEFDTO> consultarDatosUbicacion(String pBoid){
        return omisosEFDAO.consultarDatosUbicacion(pBoid);
    }
    /**
     * 
     * @param idVigilancia
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public List<String> consultarFundamentoLegal(int idVigilancia, Integer identidadFederativa) {
        return omisosEFDAO.consultarFundamentoLegal(idVigilancia, identidadFederativa);
    }
    /**
     * 
     * @param idVigilancia
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public List<Integer> consultarListaEntidadesFed(int idVigilancia) {
        return omisosEFDAO.consultarListaEntidadesFed(idVigilancia);
    }
    /**
     *
     * @param idVigilancia
     * @param identidadFederativa 
     * @param archOmisos
     * @param archFundLegal
     * @param archFactura
     * @return
     */
    @Transactional(readOnly = false,
                   rollbackFor = { SeguimientoDAOException.class })
    @Override
    public int insertaVigilanciaEF(int idVigilancia, int identidadFederativa,
            String archOmisos, String archFundLegal,String archFactura){
        return omisosEFDAO.insertaVigilanciaEF(idVigilancia, identidadFederativa,
                archOmisos, archFundLegal,archFactura);
    }

    /**
     * actualiza vigilancia
     * @param idVigilancia
     * @throws CargaInformacionSGT_DAOException
     */
    @Transactional(readOnly = false,
                   rollbackFor = { SeguimientoDAOException.class })
    @Propagable(catched = true)
    @Override
    public void actualizarVigilancia(int idVigilancia) throws SeguimientoDAOException{
        vigilanciaDAO.actualizaVigilancia(idVigilancia);
    }
    /**
     *
     * @param idVigilancia
     * @param identidadFederativa
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public Map<String, Object> consultarEncabezadoFactura(Integer idVigilancia, Integer identidadFederativa) {
        return omisosEFDAO.consultarEncabezadoFactura(idVigilancia, identidadFederativa);
    }
    
    /**
     *
     * @param idVigilancia
     * @param identidadFederativa
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public List<DetalleReporteFacturaBean> consultarDetalleFactura(Integer idVigilancia, Integer identidadFederativa) {
        return omisosEFDAO.consultarDetalleFactura(idVigilancia, identidadFederativa);
    }

}
