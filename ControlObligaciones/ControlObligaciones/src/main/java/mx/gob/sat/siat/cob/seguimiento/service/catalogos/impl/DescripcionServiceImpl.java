package mx.gob.sat.siat.cob.seguimiento.service.catalogos.impl;

import java.io.ByteArrayOutputStream;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.DescripcionDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Descripcion;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.catalogos.DescripcionService;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.exportador.excel.DescripcionXLS;
import mx.gob.sat.siat.cob.seguimiento.util.exportador.pdf.DescripcionPdf;
import mx.gob.sat.siat.cob.seguimiento.util.exportador.pdf.ModeloPDFExcel;
import mx.gob.sat.siat.exception.DaoException;
import mx.gob.sat.siat.modelo.dao.SegbMovimientosDAO;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class DescripcionServiceImpl implements DescripcionService{
    
    @Autowired
    private DescripcionDAO descripcionDAO;
    
    @Autowired
    @Qualifier("segbMovimientosDAOImpl")
    private SegbMovimientosDAO segbMovimientosDAO;
    
    
    @Propagable(catched = true ,exceptionClass = SGTServiceException.class)
    @Transactional(readOnly = true)
    @Override
    public List<Descripcion> todasLasDescripciones() {
            return  descripcionDAO.todasLasDescripciones();
    }
    
    @Transactional( readOnly = false)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public void agregaDescripcion(Descripcion descripcion, SegbMovimientoDTO segMovDto) throws SeguimientoDAOException, DaoException {
         descripcionDAO.agregaDescripcion(descripcion);
         segbMovimientosDAO.insert(segMovDto);
        
    }

    @Transactional( readOnly = true)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public void editaDescripcion(Descripcion descripcion, SegbMovimientoDTO segMovDto) throws SeguimientoDAOException, DaoException {
        descripcionDAO.editaDescripcion(descripcion);
        segbMovimientosDAO.insert(segMovDto);
        
    }
    
    @Transactional( readOnly = true)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public void eliminaDescripcion(Descripcion descripcion, SegbMovimientoDTO segMovDto) throws SeguimientoDAOException, DaoException {
          descripcionDAO.eliminaDescripcion(descripcion);
          segbMovimientosDAO.insert(segMovDto);
               
    }
    
    @Override
    @Propagable(catched = true ,exceptionClass = SGTServiceException.class)
    public Integer buscaDescripcionPorIdYDes(Descripcion descripcion) {
        return descripcionDAO.buscarDescripcionPorIdYDes(descripcion);
    }

    
    @Override
    @Propagable(catched = true ,exceptionClass = SGTServiceException.class)
    public ByteArrayOutputStream generaExcel(List<Descripcion> list) {
        DescripcionXLS descripcionXLS = new DescripcionXLS();
        return descripcionXLS.generarExcel(generaModelo(list));
    }

    public ModeloPDFExcel generaModelo(List<Descripcion> list){
        ModeloPDFExcel modeloPDFExcel  = new ModeloPDFExcel();
        modeloPDFExcel.setTitulo("Catalogo Descripcion de Vigilancia");
        modeloPDFExcel.setDatos(list);
        modeloPDFExcel.getNombreColumnas().add("Descripcion de Vigilancia");
                
        return modeloPDFExcel;
    }
    
    @Override
    @Propagable(catched = true ,exceptionClass = SGTServiceException.class)
    public ByteArrayOutputStream generaPDF(List<Descripcion> list) {
        DescripcionPdf descripcionPDF = new DescripcionPdf();
        return descripcionPDF.generarPdf(generaModelo(list));
    }

}
