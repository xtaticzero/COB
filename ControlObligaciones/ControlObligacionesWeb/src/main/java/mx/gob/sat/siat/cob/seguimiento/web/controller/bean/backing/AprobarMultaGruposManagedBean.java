/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing;


import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AdministrativoNivelCargo;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaAprobarGrupo;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EventoCargaEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.aprobacion.AprobarMultasService;
import mx.gob.sat.siat.cob.seguimiento.service.aprobacion.impl.AprobarMultasCountThread;
import mx.gob.sat.siat.cob.seguimiento.service.otros.AdministracionFuncionariosService;
import mx.gob.sat.siat.cob.seguimiento.util.IntegerMutable;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import mx.gob.sat.siat.cob.seguimiento.utils.AbstractBaseMB;
import mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.helper.AprobarMultaGruposManagedBeanHelper;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author root
 */
@Controller("aprobarMultaGruposMB")
@Scope(value = "view")
public class AprobarMultaGruposManagedBean extends AbstractBaseMB  {
    @Autowired
    private AprobarMultasService aprobarMultasService;
    @Autowired
    private AdministracionFuncionariosService administracionFuncionariosService;
    @Autowired
    private ApplicationContext applicationContext;
    private AprobarMultaGruposManagedBeanHelper aprobarMultaGruposManagedBeanHelper;
    private boolean botonDescargaVisible = false;
    private Integer progress = 0;
    private AprobarMultasCountThread thread;
    private AdministrativoNivelCargo administrativo;

    public AprobarMultaGruposManagedBean() {
        aprobarMultaGruposManagedBeanHelper = new AprobarMultaGruposManagedBeanHelper();
        aprobarMultaGruposManagedBeanHelper.setNombreArchivoDescarga((String) ((HttpSession) FacesContext.
                getCurrentInstance().
                getExternalContext().
                getSession(true)).
                getAttribute("archivoResultadosEmisionMulta"));
        ((HttpSession) FacesContext.
                getCurrentInstance().
                getExternalContext().
                getSession(true)).removeAttribute("archivoResultadosEmisionMulta");
        if(aprobarMultaGruposManagedBeanHelper.getNombreArchivoDescarga()!=null){
            botonDescargaVisible = true;
        }
    }
    @PostConstruct
    public void init() {
        try {
            obtenerAccesoUsrEmpleados();
            autorizar(ConstantsCatalogos.IDENTIFICADOR_EMISION_MULTAS);
            administrativo = administracionFuncionariosService.buscarCargoAdministrativo(this.getAccesoUsr().getNumeroEmp(), EventoCargaEnum.CARGA_OMISOS);
            aprobarMultaGruposManagedBeanHelper.setListaMultasGrupo(aprobarMultasService.listarMultasAgrupadas(administrativo));
            aprobarMultaGruposManagedBeanHelper.setProgress(new IntegerMutable());
            aprobarMultaGruposManagedBeanHelper.getProgress().setValor(0);
        } catch (SGTServiceException ex) {
            this.getLogger().error(ex);
            addErrorMessage("Error", ex.getMessage());
        }
    }

    public AprobarMultaGruposManagedBeanHelper getAprobarMultaGruposManagedBeanHelper() {
        return aprobarMultaGruposManagedBeanHelper;
    }

    public void setAprobarMultaGruposManagedBeanHelper(AprobarMultaGruposManagedBeanHelper aprobarMultaGruposManagedBeanHelper) {
        this.aprobarMultaGruposManagedBeanHelper = aprobarMultaGruposManagedBeanHelper;
    }
    
    //Se agrega processo thread
    public void setMultaAprobarGrupo(MultaAprobarGrupo multaAprobarGrupo) {
        try {            
            thread = applicationContext.getBean(AprobarMultasCountThread.class);
            thread.establecerValoresEjecucion(multaAprobarGrupo,
                                            administrativo,
                                            aprobarMultaGruposManagedBeanHelper.getProgress());
            thread.start();
            
            getSession().setAttribute("multaAprobarGrupo", multaAprobarGrupo);
            
        }catch(Exception e){
            getLogger().error(e.getMessage());
        }
    }

    public boolean isBotonDescargaVisible() {
        return botonDescargaVisible;
    }
    
    public StreamedContent getDescargaEmision() {
        StreamedContent sc = null;
        try {
            sc = new DefaultStreamedContent(aprobarMultasService.generarArchivoEmision(aprobarMultaGruposManagedBeanHelper.getNombreArchivoDescarga()),
                                                 "application/pdf", aprobarMultaGruposManagedBeanHelper.getNombreArchivoDescarga());
        } catch (SGTServiceException e) {
            this.getLogger().error(e);
            addErrorMessage("Error", e.getMessage());
            aprobarMultaGruposManagedBeanHelper.setMultaAprobarDescarga(null);
            botonDescargaVisible=false;
        }
        return sc;
    }
    public void mensajeEmision(){
        if(aprobarMultaGruposManagedBeanHelper.getMultaAprobarDescarga()!=null){
            if(aprobarMultaGruposManagedBeanHelper.getMultaAprobarDescarga().getCantidadPosiblesEmitir().
                    equals(aprobarMultaGruposManagedBeanHelper.getMultaAprobarDescarga().getCantidadMultasEmitidas())){
                addMessage(null, "Los multas fueron emitidas exitosamente. Verifique el archivo de descarga");
            }else{
                addErrorMessage("Error", "No todas las multas pudieron ser emitidas. Verifique el archivo de descarga");
            }
            botonDescargaVisible = true;
        }
        
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }
    
    public void increment() {
        progress++;
        this.getLogger().debug("\nel número de llamadas del poll va en " + progress);
    }
    
    public void irMultasIndividuales() {
        MultaAprobarGrupo multaAprobarGrupo = (MultaAprobarGrupo) getSession().getAttribute("multaAprobarGrupo");

        if (thread.getNumeroElementos() != null) {
            multaAprobarGrupo.setConteoElementos(thread.getNumeroElementos());
        }

        if (thread.getException() != null) {
            getLogger().error(thread.getException().getMessage());
        }

        getSession().setAttribute("multaAprobarGrupo", multaAprobarGrupo);
        
        redirigir("/pages/cob/aprobarVigilancias/aprobarMulta.jsf");

    }
}
