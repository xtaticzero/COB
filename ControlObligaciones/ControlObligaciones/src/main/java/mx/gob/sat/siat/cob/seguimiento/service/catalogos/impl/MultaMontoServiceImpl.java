package mx.gob.sat.siat.cob.seguimiento.service.catalogos.impl;

import java.io.ByteArrayOutputStream;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.MultaMontoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.MultaCobDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Combo;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.ComboStr;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MultaMonto;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.catalogos.MultaMontoService;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.exportador.excel.MultaMontoXLS;
import mx.gob.sat.siat.cob.seguimiento.util.exportador.pdf.ModeloPDFExcel;
import mx.gob.sat.siat.cob.seguimiento.util.exportador.pdf.MultaMontoPdf;
import mx.gob.sat.siat.exception.DaoException;
import mx.gob.sat.siat.modelo.dao.SegbMovimientosDAO;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author root
 */
@Service
public class MultaMontoServiceImpl implements MultaMontoService  {

    @Autowired
    private MultaMontoDAO oblisancionDAO;
    
    @Autowired
    @Qualifier("segbMovimientosDAOImpl")
    private SegbMovimientosDAO segbMovimientosDAO;
    
    @Autowired
    private MultaCobDAO multaCobDAO;
    
        
    /**
     *
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public List<MultaMonto> todasLasOblisanciones(){
       return oblisancionDAO.todosLasOblisanciones();
    }

    /**
     *
     * @param oblisancion
     * @param segMovDto
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException
     * @throws mx.gob.sat.siat.exception.DaoException
     */
    @Transactional( readOnly = false)
    @Override
    public void agregaOblisancion(MultaMonto oblisancion, SegbMovimientoDTO segMovDto, boolean bol) throws SeguimientoDAOException, DaoException{
     
        oblisancionDAO.agregarOblisancion(oblisancion, bol);
        segbMovimientosDAO.insert(segMovDto);
      
    }

    /**
     *
     * @param oblisancion
     * @param segMovDto
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException
     * @throws mx.gob.sat.siat.exception.DaoException
     */
    @Transactional( readOnly = true)
    @Override
    public void editaOblisancion(MultaMonto oblisancion, SegbMovimientoDTO segMovDto,  boolean bol)throws SeguimientoDAOException, DaoException{
     
        oblisancionDAO.editaOblisancion(oblisancion, bol);
        segbMovimientosDAO.insert(segMovDto);
     
    }

    /**
     *
     * @param oblisancion
     * @param segMovDto
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException
     * @throws mx.gob.sat.siat.exception.DaoException
     */
    @Transactional( readOnly = true)
    @Override
    public void eliminaOblisancion(MultaMonto oblisancion, SegbMovimientoDTO segMovDto)throws SeguimientoDAOException, DaoException{
   
          oblisancionDAO.eliminaOblisancion(oblisancion);
          segbMovimientosDAO.insert(segMovDto);
       
    }
   
    /**
     *
     * @return
     */
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public List<Combo> obtenerListaComboObligacion() {
       return  oblisancionDAO.obtenerComboObligacion(); 
    }
    
    /**
     *
     * @param list
     * @return
     */
    public ModeloPDFExcel generaModelo(List<MultaMonto> list){
        ModeloPDFExcel modeloPDFExcel  = new ModeloPDFExcel();
        modeloPDFExcel.setTitulo("Catalogo de Multa monto");
        modeloPDFExcel.setDatos(list);
        modeloPDFExcel.getNombreColumnas().add("Obligacion");
        modeloPDFExcel.getNombreColumnas().add("Tipo de multa");
        modeloPDFExcel.getNombreColumnas().add("Sancion");
        modeloPDFExcel.getNombreColumnas().add("Infraccion");
        modeloPDFExcel.getNombreColumnas().add("Motivacion");
        modeloPDFExcel.getNombreColumnas().add("Monto");
        modeloPDFExcel.getNombreColumnas().add("Descripcion Monto");
        modeloPDFExcel.getNombreColumnas().add("Fecha Inicio");
        modeloPDFExcel.getNombreColumnas().add("Fecha Fin");
        
        return modeloPDFExcel;
    }
   
    /**
     *
     * @param list
     * @return
     */
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public ByteArrayOutputStream generaExcel(List<MultaMonto> list) {
        MultaMontoXLS oblisancionXLS = new MultaMontoXLS();
        return oblisancionXLS.generarExcel(generaModelo(list));
        
    }

   
    /**
     *
     * @param list
     * @return
     */
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public ByteArrayOutputStream generaPDF(List<MultaMonto> list) {
        MultaMontoPdf oblisancionPDF = new MultaMontoPdf();
        return oblisancionPDF.generarPdf(generaModelo(list));
    }
    

    @Override
    @Propagable(catched = true ,exceptionClass = SGTServiceException.class)
    public List<ComboStr> obtenerListaComboTipoMulta() {
        return multaCobDAO.buscarTiposMulta();
    }

    /**
     * 
     * @return 
     */
    @Transactional(readOnly = true)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public List<CatalogoBase> getOblisancionMotivaciones() {
        return oblisancionDAO.getOblisancionMotivaciones();
    }

    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public Integer buscarMultaMontoRepetida(Long idObligacionSan, String idTipoMultaSel) {
       return oblisancionDAO.buscarMultaMontoRepetida(idObligacionSan, idTipoMultaSel);
    }

   
}