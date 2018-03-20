package mx.gob.sat.siat.cob.seguimiento.service.catalogos.impl;

import java.io.ByteArrayOutputStream;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.AdmonLocalDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.AdmonLocal;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.catalogos.AdmonLocalService;

import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.exportador.excel.AdmonLocalXLS;
import mx.gob.sat.siat.cob.seguimiento.util.exportador.pdf.AdmonLocalPdf;
import mx.gob.sat.siat.cob.seguimiento.util.exportador.pdf.ModeloPDFExcel;
import mx.gob.sat.siat.exception.DaoException;
import mx.gob.sat.siat.modelo.dao.SegbMovimientosDAO;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdmonLocalServiceImpl implements AdmonLocalService{
    
    @Autowired
    private AdmonLocalDAO admonLocalDAO;
    
    @Autowired
    @Qualifier("segbMovimientosDAOImpl")
    private SegbMovimientosDAO segbMovimientosDAO;
    
    public AdmonLocalServiceImpl() {
        super();
    }
    
    @Transactional(readOnly = true)
    @Override
    @Propagable(catched = true ,exceptionClass = SGTServiceException.class)
    public List<AdmonLocal> todasLasAdmon() {
       return admonLocalDAO.todasLasAdmon();
    }
    
    @Transactional( readOnly = false)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public void agregaAdmon(AdmonLocal admonLocal, SegbMovimientoDTO segMovDto)throws SeguimientoDAOException, DaoException {
        
            admonLocalDAO.agregaAdmon(admonLocal);
            segbMovimientosDAO.insert(segMovDto);
       
    }

    @Transactional( readOnly = true)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public void editaAdmon(AdmonLocal admonLocal, SegbMovimientoDTO segMovDto)throws SeguimientoDAOException, DaoException {
       
            admonLocalDAO.editaAdmon(admonLocal);
            segbMovimientosDAO.insert(segMovDto);
        
    }
    
    @Transactional( readOnly = true)
    @Override
    public void reactivaAdmon(AdmonLocal admonLocal) throws SeguimientoDAOException {
         admonLocalDAO.reactivaAdmon(admonLocal);
           
    }

    @Transactional( readOnly = true)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public void eliminaAdmon(AdmonLocal admonLocal, SegbMovimientoDTO segMovDto) throws SeguimientoDAOException, DaoException {
        admonLocalDAO.eliminaAdmon(admonLocal);
        segbMovimientosDAO.insert(segMovDto);
    }  
    
    @Override
    @Propagable(catched = true ,exceptionClass = SGTServiceException.class)
    public Integer buscaAdmonPorId(AdmonLocal admonLocal) {
        return admonLocalDAO.buscarAdmonPorId(admonLocal);

    }
    
    @Override
    @Propagable(catched = true ,exceptionClass = SGTServiceException.class)
    public ByteArrayOutputStream generaExcel(List<AdmonLocal> list) {
        AdmonLocalXLS admonLocalXLS = new AdmonLocalXLS();
        return admonLocalXLS.generarExcel(generaModelo(list));
    }
    
    @Override
    @Propagable(catched = true ,exceptionClass = SGTServiceException.class)
    public ByteArrayOutputStream generaPDF(List<AdmonLocal> list) {
        AdmonLocalPdf admonLocalPdf = new AdmonLocalPdf();
        return  admonLocalPdf.generarPdf(generaModelo(list));
            
    }

    public ModeloPDFExcel generaModelo(List<AdmonLocal> list){
        ModeloPDFExcel modeloPDFExcel  = new ModeloPDFExcel();
        modeloPDFExcel.setTitulo("Catalogo de Administraci\u00f3n Local de Servicios al Contribuyente");
        modeloPDFExcel.setDatos(list);
        modeloPDFExcel.getNombreColumnas().add("Id Administraci\u00f3n Local");
        modeloPDFExcel.getNombreColumnas().add("Nombre");
        modeloPDFExcel.getNombreColumnas().add("Descripci\u00f3n");
        return modeloPDFExcel;
    }         

}
