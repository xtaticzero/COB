package mx.gob.sat.siat.cob.seguimiento.service.renuente.impl;

import java.util.ArrayList;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.PeriodoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.PlantillaDAOImpl;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.renuente.PeriodoXPeriodicidadService;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author root
 */
@Service(value = "periodoXPeriodicidadService")
@Transactional(readOnly = true)
public class PeriodoXPeriodicidadServiceImpl implements PeriodoXPeriodicidadService{
    
    @Autowired
    private PeriodoDAO periodoDAO;
    
    private Logger logger = Logger.getLogger(PlantillaDAOImpl.class.getName());
    
    /**
     *
     * @param periodicidad
     * @return
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException
     */
    @Override
    public List<CatalogoBase> catalogoPeriodoXPeriodicidad(String periodicidad) throws SGTServiceException {
        List<CatalogoBase>listPeriodos;
        try{
            listPeriodos = periodoDAO.periodoXPeriodicidad(periodicidad);
        }
        catch(SeguimientoDAOException e){
            logger.error(e.getMessage());
            throw new SGTServiceException(e);
        }
        
        return listPeriodos;
    }
    
    /**
     *
     * @return
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException
     */
    @Override
    public List<CatalogoBase> catalogoTodosLosPeriodos() throws SGTServiceException{
        List<CatalogoBase> todosLosPeriodos=new ArrayList<CatalogoBase>();
        try{
        
        List<CatalogoBase> listaMensual = periodoDAO.periodoXPeriodicidad("M");
        List<CatalogoBase> listaBimestral = periodoDAO.periodoXPeriodicidad("B");
        List<CatalogoBase> listaTrimestral = periodoDAO.periodoXPeriodicidad("T");
        List<CatalogoBase> listaCuatrimestral = periodoDAO.periodoXPeriodicidad("Q");
        List<CatalogoBase> listaSemestral = periodoDAO.periodoXPeriodicidad("S");
        List<CatalogoBase> listaAnual = periodoDAO.periodoXPeriodicidad("Y");
        
        
        todosLosPeriodos.addAll(listaMensual);
        todosLosPeriodos.addAll(listaBimestral);
        todosLosPeriodos.addAll(listaTrimestral);
        todosLosPeriodos.addAll(listaCuatrimestral);
        todosLosPeriodos.addAll(listaSemestral);
        todosLosPeriodos.addAll(listaAnual);
        
        for(CatalogoBase cat:todosLosPeriodos){
            cat.setIdString(cat.getId()+"-"+cat.getIdString());
            cat.setIdAux(cat.getIdString()+"|"+cat.getNombre().trim());
        }
            
        }catch(SeguimientoDAOException e){
            logger.error(e.getMessage());
            throw new SGTServiceException(e);
        }
        return todosLosPeriodos;
        
    }
    
    
    
}
