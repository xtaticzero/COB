package mx.gob.sat.siat.cob.seguimiento.service.catalogos.impl;

import java.io.ByteArrayOutputStream;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.RegimenDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Regimen;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.catalogos.RegimenService;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.exportador.excel.RegimenXLS;
import mx.gob.sat.siat.cob.seguimiento.util.exportador.pdf.ModeloPDFExcel;
import mx.gob.sat.siat.cob.seguimiento.util.exportador.pdf.RegimenPdf;
import mx.gob.sat.siat.exception.DaoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class RegimenServiceImpl implements RegimenService{
    
    @Autowired
    private RegimenDAO regimenDAO;
    
    public RegimenServiceImpl() {
        super();
    }
    
    @Transactional(readOnly = true)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public List<Regimen> todosLosRegimen() {
        return regimenDAO.todosLosRegimen();
    }
    
    @Transactional( readOnly = false)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public void agregaRegimen(Regimen regimen) throws SeguimientoDAOException, DaoException{
 
        regimenDAO.agregaRegimen(regimen);
      
    }

    @Transactional( readOnly = true)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public void editaRegimen(Regimen regimen) throws SeguimientoDAOException, DaoException {
      
        regimenDAO.editaRegimen(regimen);
      
    }
    
    @Transactional( readOnly = true)
    @Override
    public void reactivaRegimen(Regimen regimen) throws SGTServiceException, SeguimientoDAOException {
        
        regimenDAO.reactivaRegimen(regimen);
        
    }

    @Transactional( readOnly = true)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public void eliminaRegimen(Regimen regimen)throws SeguimientoDAOException, DaoException {
       
          regimenDAO.eliminaRegimen(regimen);
       
    }
    
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public Integer buscaRegimenPorIdYNom(Regimen regimen) {
        return regimenDAO.buscarRegimenPorIdYNom(regimen);
    }

    
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public ByteArrayOutputStream generaExcel(List<Regimen> list) {
        RegimenXLS regimenXLS = new RegimenXLS();
        return regimenXLS.generarExcel(generaModelo(list));
    }

    public ModeloPDFExcel generaModelo(List<Regimen> list){
        ModeloPDFExcel modeloPDFExcel = new ModeloPDFExcel();
        modeloPDFExcel.setTitulo("Catalogo de Regimen");
        modeloPDFExcel.setDatos(list);
        modeloPDFExcel.getNombreColumnas().add("Id Regimen");
        modeloPDFExcel.getNombreColumnas().add("Nombre");
        modeloPDFExcel.getNombreColumnas().add("Descripcion");
        return modeloPDFExcel;
    }
    
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public ByteArrayOutputStream generaPDF(List<Regimen> list) {
        RegimenPdf regimenPDF = new RegimenPdf();
        return regimenPDF.generarPdf(generaModelo(list));
    }
    
    
}
