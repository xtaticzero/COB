package mx.gob.sat.siat.cob.seguimiento.service.catalogos.impl;

import java.io.ByteArrayOutputStream;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.AreaAdscripcionDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.AreaAdscripcion;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.catalogos.AreaAdscripcionService;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.exportador.excel.AreaAdscripcionXLS;
import mx.gob.sat.siat.cob.seguimiento.util.exportador.pdf.AreaAdscripcionPdf;
import mx.gob.sat.siat.cob.seguimiento.util.exportador.pdf.ModeloPDFExcel;
import mx.gob.sat.siat.exception.DaoException;
import mx.gob.sat.siat.modelo.dao.SegbMovimientosDAO;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AreaAdscripcionServiceImpl implements AreaAdscripcionService{
    
    @Autowired
    private AreaAdscripcionDAO areaAdscripcionDAO;
    
    @Autowired
    @Qualifier("segbMovimientosDAOImpl")
    private SegbMovimientosDAO segbMovimientosDAO;
    
    public AreaAdscripcionServiceImpl() {
        super();
    }
    
    @Transactional(readOnly = true)
    @Override
    @Propagable(catched = true ,exceptionClass = SGTServiceException.class)
    public List<AreaAdscripcion> todasLasAreas() {
       return areaAdscripcionDAO.todasLasAreas();
    }
    
    @Transactional( readOnly = false)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public void agregaArea(AreaAdscripcion areaAdscripcion, SegbMovimientoDTO segMovDto)throws SeguimientoDAOException, DaoException {
        
            areaAdscripcionDAO.agregaArea(areaAdscripcion);
            segbMovimientosDAO.insert(segMovDto);
       
    }

    @Transactional( readOnly = true)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public void editaArea(AreaAdscripcion areaAdscripcion, SegbMovimientoDTO segMovDto)throws SeguimientoDAOException, DaoException {
       
            areaAdscripcionDAO.editaArea(areaAdscripcion);
            segbMovimientosDAO.insert(segMovDto);
        
    }
    
    @Transactional( readOnly = true)
    @Override
    public void reactivaArea(AreaAdscripcion areaAdscripcion) throws SeguimientoDAOException {
         areaAdscripcionDAO.reactivaArea(areaAdscripcion);
           
    }

    @Transactional( readOnly = true)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public void eliminaArea(AreaAdscripcion areaAdscripcion, SegbMovimientoDTO segMovDto) throws SeguimientoDAOException, DaoException {
        areaAdscripcionDAO.eliminaArea(areaAdscripcion);
        segbMovimientosDAO.insert(segMovDto);
    }  
    
    @Override
    @Propagable(catched = true ,exceptionClass = SGTServiceException.class)
    public Integer buscaAreaPorId(AreaAdscripcion areaAdscripcion) {
        return areaAdscripcionDAO.buscarAreaPorId(areaAdscripcion);

    }
    
    @Override
    @Propagable(catched = true ,exceptionClass = SGTServiceException.class)
    public ByteArrayOutputStream generaExcel(List<AreaAdscripcion> list) {
        AreaAdscripcionXLS areaAdscripcionXLS = new AreaAdscripcionXLS();
        return areaAdscripcionXLS.generarExcel(generaModelo(list));
    }
    
    @Override
    @Propagable(catched = true ,exceptionClass = SGTServiceException.class)
    public ByteArrayOutputStream generaPDF(List<AreaAdscripcion> list) {
        AreaAdscripcionPdf areaAdscripcionPdf = new AreaAdscripcionPdf();
        return  areaAdscripcionPdf.generarPdf(generaModelo(list));
            
    }

    public ModeloPDFExcel generaModelo(List<AreaAdscripcion> list){
        ModeloPDFExcel modeloPDFExcel  = new ModeloPDFExcel();
        modeloPDFExcel.setTitulo("Catalogo \u00c1rea de Adscripci\u00f3n");
        modeloPDFExcel.setDatos(list);
        modeloPDFExcel.getNombreColumnas().add("Nombre");
        modeloPDFExcel.getNombreColumnas().add("Descripci\u00f3n");
        return modeloPDFExcel;
    }         

}
