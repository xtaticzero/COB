package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.util.List;

import javax.annotation.PostConstruct;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.FundamentoLegal;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.catalogos.FundamentoLegalService;
import mx.gob.sat.siat.cob.seguimiento.service.otros.ServiceCatalogos;
import mx.gob.sat.siat.cob.seguimiento.service.renuente.PeriodoXPeriodicidadService;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import mx.gob.sat.siat.cob.seguimiento.utils.AbstractBaseMB;
import mx.gob.sat.siat.cob.seguimiento.web.util.CreaArchivoSistema;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


@Controller("buscarFundamentosLegales")
@Scope(value = "view")
public class BuscarFundamentosLegalesManagedBean extends AbstractBaseMB {
    
    @Value("#{properties['filename.archivo.fundamentos']}")
    private String nombreArchivo;
  
    @Autowired
    private ServiceCatalogos catalogoService;
    @Autowired
    private PeriodoXPeriodicidadService periodoService;
    @Autowired
    private FundamentoLegalService fundamentoLegalService;
    
    private List<CatalogoBase> catalogoObligaciones;
    private List<CatalogoBase> lstregimen;
    private List<CatalogoBase> catalogoEjercicioFiscal;
    private List<CatalogoBase> catalogoPeriodos;
    
    private List<CatalogoBase> catalogoObligacionHelper;
    private List<String> selectedObligaciones;
    
    private FundamentoLegal fundamentoLegal=new FundamentoLegal();
   
    @PostConstruct
     public void init() {
         try {
                obtenerAccesoUsrEmpleados();
                autorizar(ConstantsCatalogos.IDENTIFICADOR_FUNDAMENTOLEGAL_COB);
                catalogoEjercicioFiscal = this.parseEjercicioFiscal(catalogoService.consultar("ejercicio fiscal"));
                catalogoPeriodos=periodoService.catalogoTodosLosPeriodos();
                    
            } catch (SGTServiceException ex) {
                 getLogger().debug("Error de Servicio:"+ex.getMessage());
                 manejaException(ex);
            }  
     }
    
    private void manejaException(Exception e) {
                getSession().setAttribute("mensaje", e.getMessage());
                redirigir(ConstantsCatalogos.URL_EN_CONSTRUCCION);
    }
    
    public StreamedContent getArchivo() throws IOException{
        FundamentoLegal fndHelper=getFundamentoLegal();
        FundamentoLegal fundamento;
        try {
            fundamento = fundamentoLegalService.buscarFundamentoLegalParaExportacion(fndHelper);
            CreaArchivoSistema.generateCsvFile(fundamento,getFileName(nombreArchivo));
            File arc = new File(getFileName(nombreArchivo));
            InputStream is = new FileInputStream(arc);
            return new DefaultStreamedContent(is,"text/plain",getFileName(nombreArchivo));
        } catch (SGTServiceException e) {
            super.addErrorMessage("", e.getMessage());
            getLogger().debug("Error Metodo: getArchivo(), Causa:" + e + " Mensaje: " + e.getMessage() + " Clase error:" + e.getClass());
        } catch (FileNotFoundException e) {
            super.addErrorMessage("", "No existen registros con los datos de búsqueda");
            getLogger().debug("Error Metodo: getArchivo(), Causa:" + e+ " Mensaje: " + e.getMessage() + " Clase error:" + e.getClass());
        }
        return null;
    }
    
    private List<CatalogoBase> parseEjercicioFiscal(List<CatalogoBase> ej){
        
        for(CatalogoBase cat:ej){
            cat.setIdAux(cat.getId()+""+"-"+cat.getNombre());
        }
        return ej;
    }
    
    public void setObligaciones(List<CatalogoBase> catalogoObligaciones) {
        this.catalogoObligaciones = catalogoObligaciones;
    }


    public List<CatalogoBase> getCatalogoObligaciones() {
        return catalogoObligaciones;
    }

    public void setCatalogoEjercicioFiscal(List<CatalogoBase> catalogoEjercicioFiscal) {
        this.catalogoEjercicioFiscal = catalogoEjercicioFiscal;
    }

    public List<CatalogoBase> getCatalogoEjercicioFiscal() {
        return catalogoEjercicioFiscal;
    }

    public void setCatalogoPeriodos(List<CatalogoBase> catalogoPeriodos) {
        this.catalogoPeriodos = catalogoPeriodos;
    }

    public List<CatalogoBase> getCatalogoPeriodos() {
        return catalogoPeriodos;
    }

    public void setLstregimen(List<CatalogoBase> lstregimen) {
        this.lstregimen = lstregimen;
    }

    public List<CatalogoBase> getLstregimen() {
        return lstregimen;
    }

    public void setCatalogoObligacionHelper(List<CatalogoBase> catalogoObligacionHelper) {
        this.catalogoObligacionHelper = catalogoObligacionHelper;
    }

    public List<CatalogoBase> getCatalogoObligacionHelper() {
        return catalogoObligacionHelper;
    }

    public void setSelectedObligaciones(List<String> selectedObligaciones) {
        this.selectedObligaciones = selectedObligaciones;
    }

   
    public List<String> getSelectedObligaciones() {
        return selectedObligaciones;
    }

    public void setFundamentoLegal(FundamentoLegal fundamentoLegal) {
        this.fundamentoLegal = fundamentoLegal;
    }

    public FundamentoLegal getFundamentoLegal() {
        return fundamentoLegal;
    }

}
