package mx.gob.sat.siat.cob.seguimiento.service.catalogos.impl;

import java.io.ByteArrayOutputStream;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.EjercicioFiscalDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.EjercicioFiscal;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.catalogos.EjercicioFiscalService;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.exportador.excel.EjercicioFiscalXLS;
import mx.gob.sat.siat.cob.seguimiento.util.exportador.pdf.EjercicioFiscalPdf;
import mx.gob.sat.siat.cob.seguimiento.util.exportador.pdf.ModeloPDFExcel;
import mx.gob.sat.siat.exception.DaoException;
import mx.gob.sat.siat.modelo.dao.SegbMovimientosDAO;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class EjercicioFiscalServiceImpl implements EjercicioFiscalService {
    
    @Autowired
    private EjercicioFiscalDAO ejercicioFiscalDAO;
    
    @Autowired
    @Qualifier("segbMovimientosDAOImpl")
    private SegbMovimientosDAO segbMovimientosDAO;
    
    public EjercicioFiscalServiceImpl() {
        super();
    }
    
    @Transactional(readOnly = true)
    @Override
    @Propagable(catched = true ,exceptionClass = SGTServiceException.class)
    public List<EjercicioFiscal> todosLosEjercicios() {
       return ejercicioFiscalDAO.todosLosEjercicios();
    }
    
    @Transactional( readOnly = false)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public void agregaEjercicio(EjercicioFiscal ejercicioFiscal, SegbMovimientoDTO segMovDto)throws SeguimientoDAOException, DaoException {
        
            ejercicioFiscalDAO.agregaEjercicio(ejercicioFiscal);
            segbMovimientosDAO.insert(segMovDto);
       
    }

    @Transactional( readOnly = true)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public void editaEjercicio(EjercicioFiscal ejercicioFiscal, SegbMovimientoDTO segMovDto)throws SeguimientoDAOException, DaoException {
       
            ejercicioFiscalDAO.editaEjercicio(ejercicioFiscal);
            segbMovimientosDAO.insert(segMovDto);
        
    }
    
    @Transactional( readOnly = true)
    @Override
    public void reactivaEjercicio(EjercicioFiscal ejercicioFiscal) throws SeguimientoDAOException {
         ejercicioFiscalDAO.reactivaEjercicio(ejercicioFiscal);
           
    }

    @Transactional( readOnly = true)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public void eliminaEjercicio(EjercicioFiscal ejercicioFiscal, SegbMovimientoDTO segMovDto) throws SeguimientoDAOException, DaoException {
        ejercicioFiscalDAO.eliminaEjercicio(ejercicioFiscal);
        segbMovimientosDAO.insert(segMovDto);
    }  
    
    @Override
    @Propagable(catched = true ,exceptionClass = SGTServiceException.class)
    public Integer buscaEjercicioPorNomYDesc(EjercicioFiscal ejercicioFiscal) {
        return ejercicioFiscalDAO.buscarEjercicioPorNomYDesc(ejercicioFiscal);

    }

    
    @Override
    @Propagable(catched = true ,exceptionClass = SGTServiceException.class)
    public ByteArrayOutputStream generaExcel(List<EjercicioFiscal> list) {
        EjercicioFiscalXLS ejercicioFiscalXLS = new EjercicioFiscalXLS();
        return ejercicioFiscalXLS.generarExcel(generaModelo(list));
    }

    public ModeloPDFExcel generaModelo(List<EjercicioFiscal> list){
        ModeloPDFExcel modeloPDFExcel  = new ModeloPDFExcel();
        modeloPDFExcel.setTitulo("Catalogo de Ejercicio Fiscal");
        modeloPDFExcel.setDatos(list);
        modeloPDFExcel.getNombreColumnas().add("Ejercicio Fiscal");
        modeloPDFExcel.getNombreColumnas().add("Nombre");
        modeloPDFExcel.getNombreColumnas().add("Descripcion");
        return modeloPDFExcel;
    }
    
    @Override
    @Propagable(catched = true ,exceptionClass = SGTServiceException.class)
    public ByteArrayOutputStream generaPDF(List<EjercicioFiscal> list) {
        EjercicioFiscalPdf ejercicioFiscalPDF = new EjercicioFiscalPdf();
        return  ejercicioFiscalPDF.generarPdf(generaModelo(list));
            
    }
    

}
