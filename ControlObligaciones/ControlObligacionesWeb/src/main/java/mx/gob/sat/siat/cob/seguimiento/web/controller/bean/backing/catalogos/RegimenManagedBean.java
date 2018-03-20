package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.catalogos;

import java.util.Calendar;

import javax.annotation.PostConstruct;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Regimen;
import mx.gob.sat.siat.cob.seguimiento.service.catalogos.RegimenService;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import mx.gob.sat.siat.cob.seguimiento.utils.AbstractBaseMB;
import mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.catalogos.model.RegimenModel;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


@Controller("catalogoRegimenMB")
@Scope("view")
public class RegimenManagedBean extends AbstractBaseMB{
    
   
    
    @Autowired
    private RegimenService regimenService;

    private RegimenModel regimenModel = new RegimenModel();
    private Logger log = Logger.getLogger(RegimenManagedBean.class.getName());
    /**
     *
     */
    public RegimenManagedBean() {
        super();
    }
    
    /**
     *
     */
    @PostConstruct
    public void init() {
        log.info("test");
        try {
            log.error("test2");
            regimenModel.setListRegimen(regimenService.todosLosRegimen());
        } catch (Exception e) {
            
            getLogger().error(e.getMessage());
        }
        log.debug("test3");
    }
    
    /**
     *
     */
    public void agrega() {
        Regimen regimen= new Regimen();
        
        regimen.setIdRegimen(regimenModel.getIdRegimen());
        regimen.setNombre(regimenModel.getNombre());
        regimen.setDescripcion(regimenModel.getDescripcion());
        regimen.setOrden(ConstantsCatalogos.ORDEN);
        Calendar cal = Calendar.getInstance();
        regimen.setFechaInicio(cal.getTime());
       
        Integer reg = regimenService.buscaRegimenPorIdYNom(regimen);
        if (reg == null) {
            AbstractBaseMB.msgError(ConstantsCatalogos.ERROR_CONSULTA_DB);
            return;
        }
        if (reg > 0) {
            AbstractBaseMB.msgError(ConstantsCatalogos.REGISTRO_DUPLICADO);
            return;
        }

        try {
            regimenService.agregaRegimen(regimen);
            regimenModel.setListRegimen(regimenService.todosLosRegimen());
            clean();
            cerrar();

        } catch (Exception e) {

            getLogger().error(e.getMessage());
        }
    }
    
    /**
     *
     */
    public void edita() {
        try {
            regimenService.editaRegimen(regimenModel.getRegimenEdit());
            regimenModel.setListRegimen(regimenService.todosLosRegimen());
            clean();
            cerrar();
        } catch (Exception e) {
            
            getLogger().error(e.getMessage());
        }
    }
    
    /**
     *
     */
    public void reactiva() {
        try {
            regimenService.reactivaRegimen(regimenModel.getRegimenEdit());
            regimenModel.setListRegimen(regimenService.todosLosRegimen());
            clean();
            cerrar();
        } catch (Exception e) {
            
            getLogger().error(e.getMessage());
        }
    }
    
    /**
     *
     */
    public void elimina() {
        Calendar cal = Calendar.getInstance();
        regimenModel.getRegimenEli().setFechaFin(cal.getTime());

        try {
            regimenService.eliminaRegimen(regimenModel.getRegimenEli());
            regimenModel.setListRegimen(regimenService.todosLosRegimen());
            this.clean();
            this.cerrar();
        } catch (Exception e) {

            getLogger().error(e.getMessage());
        }
    }
    
    /**
     *
     */
    public void generaExcel() {
       
        byte[] excel;
        
            if (regimenModel.getListRegimenTmp() != null) {
                excel = regimenService.generaExcel(regimenModel.getListRegimenTmp()).toByteArray();
            } else {
                excel = regimenService.generaExcel(regimenModel.getListRegimen()).toByteArray();
            }

        despachaArchivo(excel, ConstantsCatalogos.TYPE_EXCEL, "tabla.xls", ConstantsCatalogos.ERROR_GENERA_EXCEL);
    }
    
    /**
     *
     */
    public void generaPdf() {
    
        byte[] pdf;
       
            if (regimenModel.getListRegimenTmp() != null) {
                pdf = regimenService.generaPDF(regimenModel.getListRegimenTmp()).toByteArray();
            } else {
                pdf = regimenService.generaPDF(regimenModel.getListRegimen()).toByteArray();
            }
        despachaArchivo(pdf, ConstantsCatalogos.TYPE_PDF, "tabla.pdf", ConstantsCatalogos.ERROR_GENERA_PDF);

    }
    
    /**
     *
     */
    public void abrirNuevo() {
        this.getRegimenModel().setTblVisible(false);
        this.getRegimenModel().setNvoVisible(true);
    }
    
    /**
     *
     */
    public void abrirEditar() {

        regimenModel.setFechaFinStr(regimenModel.getRegimenEdit().getFechaFinStr());
        this.getRegimenModel().setTblVisible(false);
        this.getRegimenModel().setEdtVisible(true);
    }
    
    /**
     *
     */
    public void abrirEliminar() {
        this.getRegimenModel().setTblVisible(false);
        this.getRegimenModel().setElmVisible(true);
    }

    /**
     *
     */
    public void cerrar() {
        this.getRegimenModel().setTblVisible(true);
        this.getRegimenModel().setNvoVisible(false);
        this.getRegimenModel().setEdtVisible(false);
        this.getRegimenModel().setElmVisible(false);
        clean();
    }

    
    /**
     *
     */
    public void clean() {
        getRegimenModel().setIdRegimen(null);
        getRegimenModel().setNombre("");
        getRegimenModel().setDescripcion("");
        getRegimenModel().setOrden(null);
 
    }

    /**
     *
     * @param regimenService
     */
    public void setRegimenService(RegimenService regimenService) {
        this.regimenService = regimenService;
    }

    /**
     *
     * @return
     */
    public RegimenService getRegimenService() {
        return regimenService;
    }

    /**
     *
     * @param regimenModel
     */
    public void setRegimenModel(RegimenModel regimenModel) {
        this.regimenModel = regimenModel;
    }

    /**
     *
     * @return
     */
    public RegimenModel getRegimenModel() {
        return regimenModel;
    }
}
