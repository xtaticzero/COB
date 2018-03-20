package mx.gob.sat.siat.cob.seguimiento.service.catalogos.impl;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.FundamentoLegalDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Combo;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.FundamentoLegal;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;
import mx.gob.sat.siat.cob.seguimiento.exception.AgrupamientoException;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.carga.Agrupamiento;
import mx.gob.sat.siat.cob.seguimiento.service.catalogos.FundamentoLegalService;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.exportador.excel.FundamentoLegalXLS;
import mx.gob.sat.siat.cob.seguimiento.util.exportador.pdf.FundamentoLegalPdf;
import mx.gob.sat.siat.cob.seguimiento.util.exportador.pdf.ModeloPDFExcel;
import mx.gob.sat.siat.exception.DaoException;
import mx.gob.sat.siat.modelo.dao.SegbMovimientosDAO;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author root
 */
@Service
public class FundamentoLegalServiceImpl implements FundamentoLegalService {
    
    private final Logger log = Logger.getLogger(FundamentoLegalServiceImpl.class);
    @Autowired
    private FundamentoLegalDAO fundamentoLegalDAO;
    
    @Autowired
    @Qualifier("segbMovimientosDAOImpl")
    private SegbMovimientosDAO segbMovimientosDAO;
    

    /**
     *
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    @Propagable(catched = true ,exceptionClass = SGTServiceException.class)
    public List<FundamentoLegal> todosLosFundamentos() {
        return fundamentoLegalDAO.todosLosFundamentos();
    }
    
    /**
     *
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    @Propagable(catched = true ,exceptionClass = SGTServiceException.class)
    public List<FundamentoLegal> todosLosFundamentosPorEjercicioFiscal(Long idEjercicio) {
        return fundamentoLegalDAO.todosLosFundamentosPorEjercicioFiscal(idEjercicio);
    }

    /**
     *
     * @param fundamentoLegal
     * @param segMovDto
     * @throws SeguimientoDAOException
     * @throws DaoException
     */
    @Transactional( readOnly = false)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public void agregaFundamentoLegal(FundamentoLegal fundamentoLegal, SegbMovimientoDTO segMovDto) throws SeguimientoDAOException, DaoException {
                  
         fundamentoLegalDAO.agregarFundamentoLegal(fundamentoLegal);
          segbMovimientosDAO.insert(segMovDto);
         
    }

    /**
     *
     * @param fundamentoLegal
     * @param segMovDto
     * @throws SeguimientoDAOException
     * @throws DaoException
     */
    @Transactional( readOnly = false)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public void editaFundamentoLegal(FundamentoLegal fundamentoLegal, SegbMovimientoDTO segMovDto) throws SeguimientoDAOException, DaoException{
        
        fundamentoLegalDAO.editaFundamentoLegal(fundamentoLegal);
         segbMovimientosDAO.insert(segMovDto);
       
    }

    /**
     *
     * @param fundamentoLegal
     * @param segMovDto
     * @throws SeguimientoDAOException
     * @throws DaoException
     */
    @Transactional( readOnly = false)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public void eliminaFundamentoLegal(FundamentoLegal fundamentoLegal, SegbMovimientoDTO segMovDto) throws SeguimientoDAOException, DaoException{
       
           fundamentoLegalDAO.eliminaFundamentoLegal(fundamentoLegal);
           segbMovimientosDAO.insert(segMovDto);
       
    }

    /**
     *
     * @param fundamentoLegal
     * @return
     */
    @Transactional( readOnly = true)
    @Override
    @Propagable(catched = true ,exceptionClass = SGTServiceException.class)
    public Integer buscarFundamentoLegalPorIdObligYIdReg(FundamentoLegal fundamentoLegal) {
        return fundamentoLegalDAO.buscarFundamentoLegalPorIdObligYIdReg(fundamentoLegal);
    }
    
    /**
     *
     * @return
     */
    @Transactional( readOnly = true)
    @Override
    @Propagable(catched = true ,exceptionClass = SGTServiceException.class)
    public List<Combo> obtenerComboObligacion() {
       return fundamentoLegalDAO.obtenerComboObligacion(); 
    }
    
    /**
     *
     * @return
     */
    @Transactional( readOnly = true)
    @Override
    public List<Combo> obtenerComboRegimenPorIdObligacion() {
        List<Combo> valores = null;
        try{
           valores = fundamentoLegalDAO.obtenerListRegimenPorIdObligacion(); 
        }catch(SeguimientoDAOException e){
            log.debug(e);
        }
        return valores;
    }
    /**
     *
     * @return
     */
    @Transactional( readOnly = true)
    @Override
    @Propagable(catched = true ,exceptionClass = SGTServiceException.class)
    public List<Combo> obtenerComboEjercicioFiscal() {
           return fundamentoLegalDAO.obtenerComboEjercicioFiscal(); 
    }
    /**
     *
     * @return
     */
    @Transactional( readOnly = true)
    @Override
    @Propagable(catched = true ,exceptionClass = SGTServiceException.class)
    public List<Combo> obtenerComboPeriodicidad() {
        return fundamentoLegalDAO.obtenerComboPeriodicidad(); 
    }
   
    /**
     *
     * @param fundamento
     * @return
     * @throws SGTServiceException
     */
    @Transactional( readOnly = false)
    @Override
    public FundamentoLegal buscarFundamentoLegalParaExportacion(FundamentoLegal fundamento)throws SGTServiceException{
        FundamentoLegal fnd=fundamentoLegalDAO.buscarFundamentoLegalParaExportacion(fundamento);
        if(fnd==null){
           throw new SGTServiceException("No se encontraron resultados");
        } 
        return fnd;
    }
   
    
    /**
     *
     * @param list
     * @return
     */
    public ModeloPDFExcel generaModelo(List<FundamentoLegal> list){
        ModeloPDFExcel modeloPDFExcel = new ModeloPDFExcel();
        modeloPDFExcel.setTitulo("Catalogo de Fundamento Legal");
        modeloPDFExcel.setDatos(list);
        modeloPDFExcel.getNombreColumnas().add("Obligacion");
        modeloPDFExcel.getNombreColumnas().add("Regimen");
        modeloPDFExcel.getNombreColumnas().add("EjercicioFiscal");
        modeloPDFExcel.getNombreColumnas().add("Periodo");
        modeloPDFExcel.getNombreColumnas().add("Fecha Vencimiento");
        modeloPDFExcel.getNombreColumnas().add("Fundamento");
       
        return modeloPDFExcel;
    }
    
    /**
     *
     * @param list
     * @return
     */
    @Override
    @Propagable(catched = true ,exceptionClass = SGTServiceException.class)
    public ByteArrayOutputStream generaExcel(List<FundamentoLegal> list) {
        FundamentoLegalXLS fundamentoLegalXLS = new FundamentoLegalXLS();
        return fundamentoLegalXLS.generarExcel(generaModelo(list));
    }

    
    /**
     *
     * @param list
     * @return
     */
    @Override
    @Propagable(catched = true ,exceptionClass = SGTServiceException.class)
    public ByteArrayOutputStream generaPDF(List<FundamentoLegal> list) {
        FundamentoLegalPdf fundamentoLegalPdf = new FundamentoLegalPdf();
        return fundamentoLegalPdf.generarPdf(generaModelo(list));
    }
    
    /**
     * 
     * @param rutaEnRepositorio
     * @param nombreOriginalArchivo
     * @return
     * @throws SeguimientoDAOException 
     */
    @Transactional
    @Override
    public Map<String,String> guardaFundamentoLegalBatch(String rutaEnRepositorio,String nombreOriginalArchivo) 
            throws SeguimientoDAOException {
        Map<String,String> resultados = new HashMap<String,String>();
        FundamentoLegalArchivo leerDatos=new FundamentoLegalArchivo();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        
        Agrupamiento.nuevoAgrupamiento();
        try {
            leerDatos.agrupa(rutaEnRepositorio, nombreOriginalArchivo);
        } catch (AgrupamientoException ex) {
            log.error(ex);
        }
        List<String> datosFundamentoLegal=Agrupamiento.getMapaAgr().get(rutaEnRepositorio);

        if(datosFundamentoLegal.isEmpty()){
            log.debug(">>>no hay dato del archivo:"+rutaEnRepositorio+", "+nombreOriginalArchivo);
            resultados.put("estado", "sin datos");
        }else{
            log.debug(">>>total de datos:"+datosFundamentoLegal.size());
            List<FundamentoLegal> detalles = new ArrayList<FundamentoLegal>();
            for(String fundamentoL:datosFundamentoLegal){
                String[] arryFundamento=fundamentoL.split("\\|");
                FundamentoLegal detalle=new FundamentoLegal();
                detalle.setIdObligacion(Long.valueOf(arryFundamento[0]));
                detalle.setIdRegimen(Long.valueOf(arryFundamento[1]));
                detalle.setIdEjercicioFiscal(Long.valueOf(arryFundamento[2]));
                detalle.setIdPeriodicidad(arryFundamento[3]);
                detalle.setIdPeriodo(arryFundamento[4]);
                try {
                    detalle.setFechaVencimiento(format.parse(arryFundamento[5]));
                } catch (ParseException ex) {
                    log.error(ex);
                }
                detalle.setFundamentoLegal(arryFundamento[6]);
                detalles.add(detalle);
            }
            if(!detalles.isEmpty()){
                resultados=fundamentoLegalDAO.guardaFundamentoLegalBatch(detalles);
            }
        }
        log.debug(">>>"+resultados);
        return resultados;
    }
    
    /**
     * 
     * @return 
     */
    @Transactional( readOnly = true)
    @Override
    @Propagable(catched = true ,exceptionClass = SGTServiceException.class)
    public List<CatalogoBase> getFundamentosExistentes() {
        return fundamentoLegalDAO.getFundamentosExistentes();
    }

}