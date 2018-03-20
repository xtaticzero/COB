package mx.gob.sat.siat.cob.seguimiento.service.catalogos.impl;

import java.io.ByteArrayOutputStream;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.ObligacionDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.rfcampliado.RfccObligacionDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.rfcampliado.RfccRegimenDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Combo;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Obligacion;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.ValorBooleano;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.catalogos.ObligacionService;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.exportador.excel.ObligacionXLS;
import mx.gob.sat.siat.cob.seguimiento.util.exportador.pdf.ModeloPDFExcel;
import mx.gob.sat.siat.cob.seguimiento.util.exportador.pdf.ObligacionPdf;
import mx.gob.sat.siat.exception.DaoException;
import mx.gob.sat.siat.modelo.dao.SegbMovimientosDAO;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ObligacionServiceImpl implements ObligacionService {

    private final Logger log = Logger.getLogger(ObligacionServiceImpl.class);

    @Autowired
    private ObligacionDAO obligacionDAO;

    @Autowired
    @Qualifier("segbMovimientosDAOImpl")
    private SegbMovimientosDAO segbMovimientosDAO;
    
    @Autowired
    private RfccObligacionDAO rfccObligacionDAO;
    
    @Autowired
    private RfccRegimenDAO rfccRegimenDAO;

    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    @Override
    public List<Obligacion> todasLasObligaciones() {
        return obligacionDAO.todosLasObligaciones();
    }

    @Transactional( readOnly = false)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public void agregaObligacion(Obligacion obligacion, SegbMovimientoDTO segMovDto) throws SeguimientoDAOException, DaoException {

        obligacionDAO.agregaObligacion(obligacion);
        segbMovimientosDAO.insert(segMovDto);

    }

    @Transactional( readOnly = true)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public void editaObligacion(Obligacion obligacion, SegbMovimientoDTO segMovDto) throws SeguimientoDAOException, DaoException {

        obligacionDAO.editaObligacion(obligacion);
        segbMovimientosDAO.insert(segMovDto);

    }
    
    @Transactional( readOnly = true)
    @Override
    public void reactivaObligacion(Obligacion obligacion) throws SeguimientoDAOException {
         obligacionDAO.reactivaObligacion(obligacion);
           
    }

    @Transactional( readOnly = true)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public void eliminaObligacion(Obligacion obligacion, SegbMovimientoDTO segMovDto) throws SeguimientoDAOException, DaoException {

        obligacionDAO.eliminaObligacion(obligacion);
       segbMovimientosDAO.insert(segMovDto);

    }

    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public Integer buscaObligacionPorConcYDesc(Obligacion obligacion) {
        return obligacionDAO.buscarObligacionPorConcYDesc(obligacion);
    }
    
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public String obtenerConceptoImpuesto(Long idObli){
        return obligacionDAO.obtenerConceptoImpuesto(idObli);
    }

    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public List<Combo> obtenerListaComboObligancion() {
        return rfccObligacionDAO.obtenerComboObligacion();
    }

    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public List<Combo> obtenerListaComboRegimen() {
        return rfccRegimenDAO.obtenerComboRegimen();
    }

    /**
     * Metodo que regresa las obligaciones sin importar de que regimen este
     * compuesto.
     *
     * @return lista de obligaciones
     */
    @Transactional(readOnly = true)
    @Override
    public List<Obligacion> getDistinctObligacion() {
        List<Obligacion> obligaciones = null;
        try {
            obligaciones = obligacionDAO.getDistinctObligacion();
        } catch (SeguimientoDAOException e) {
            log.error(e);
        }
        return obligaciones;
    }

    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public ByteArrayOutputStream generaExcel(List<Obligacion> list) {
        ObligacionXLS obligacionXLS = new ObligacionXLS();
        return obligacionXLS.generarExcel(generaModelo(list));
    }

    public ModeloPDFExcel generaModelo(List<Obligacion> list) {
        ModeloPDFExcel modeloPDFExcel = new ModeloPDFExcel();
        modeloPDFExcel.setTitulo("Catalogo de Obligaciones");
        modeloPDFExcel.setDatos(list);
        modeloPDFExcel.getNombreColumnas().add("Obligacion");
        modeloPDFExcel.getNombreColumnas().add("Concepto/Impuesto");
        modeloPDFExcel.getNombreColumnas().add("Descripcion");
        modeloPDFExcel.getNombreColumnas().add("Aplica Renuentes");
        return modeloPDFExcel;
    }

    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public ByteArrayOutputStream generaPDF(List<Obligacion> list) {
            ObligacionPdf obligacionPDF = new ObligacionPdf();
            return obligacionPDF.generarPdf(generaModelo(list));
    }

    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public List<ValorBooleano> obtenerTodosLosValoresBool() {
        return  obligacionDAO.obtenerTodosLosValores();
    }

    public void setObligacionDAO(ObligacionDAO obligacionDAO) {
        this.obligacionDAO = obligacionDAO;
    }

    public ObligacionDAO getObligacionDAO() {
        return obligacionDAO;
    }

    public void setSegbMovimientosDAO(SegbMovimientosDAO segbMovimientosDAO) {
        this.segbMovimientosDAO = segbMovimientosDAO;
    }

    public SegbMovimientosDAO getSegbMovimientosDAO() {
        return segbMovimientosDAO;
    }

    public void setRfccObligacionDAO(RfccObligacionDAO rfccObligacionDAO) {
        this.rfccObligacionDAO = rfccObligacionDAO;
    }

    public RfccObligacionDAO getRfccObligacionDAO() {
        return rfccObligacionDAO;
    }

    public void setRfccRegimenDAO(RfccRegimenDAO rfccRegimenDAO) {
        this.rfccRegimenDAO = rfccRegimenDAO;
    }

    public RfccRegimenDAO getRfccRegimenDAO() {
        return rfccRegimenDAO;
    }
}
