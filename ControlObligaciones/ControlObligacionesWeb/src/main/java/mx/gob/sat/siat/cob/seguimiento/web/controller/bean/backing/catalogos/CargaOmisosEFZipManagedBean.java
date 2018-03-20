package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.catalogos;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;

import java.util.ArrayList;
import java.util.Date;
import java.util.zip.ZipOutputStream;

import java.util.List;

import java.util.zip.ZipEntry;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;



import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MotRechazoVig;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaEntidadFederativa;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.carga.CargaOmisosEntidadFederativaService;
import mx.gob.sat.siat.cob.seguimiento.service.catalogos.UsuarioEFService;
import mx.gob.sat.siat.cob.seguimiento.service.otros.MailService;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import mx.gob.sat.siat.cob.seguimiento.utils.AbstractBaseMB;


import mx.gob.sat.siat.exception.AccesoDenegadoException;

import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;
import mx.gob.sat.siat.util.MovimientoUtil;


import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


@Controller("cargaOmisosEfBis")
@Scope(value = "view")
public class CargaOmisosEFZipManagedBean extends AbstractBaseMB {
    public CargaOmisosEFZipManagedBean() {
        super();
    }
    
    @Autowired
    private CargaOmisosEntidadFederativaService cargaOmisosEntidadFederativaService;
    
    @Autowired
    private MailService mailService;
    
    
    
    @Value("#{properties['ruta.archivo.zip']}")
    private String rutaArchivoZip;
    
    
    private List<VigilanciaEntidadFederativa> vigilancias;
    private List<MotRechazoVig> motivosRechazo;
    
    @Autowired
    private UsuarioEFService usuarioEFService;
    
    private VigilanciaEntidadFederativa vigilanciaRechazada=new VigilanciaEntidadFederativa();
    private VigilanciaEntidadFederativa vigilanciaAceptada=new VigilanciaEntidadFederativa();
    
    private boolean mostrarPanelRechazar;
    private boolean mostrarPanelVigilancias;
    
    
   @PostConstruct
     public void init(){
         
         try{
             obtenerAccesoUsrEmpleados();
             autorizar(ConstantsCatalogos.IDENTIFICADOR_CARGA_OMISOS_EF);
             Long claveEF=usuarioEFService.obtenerUsuarioPorRfcCorto(getAccesoUsr().getUsuario()).get(0).getIdEntidadFederativa();
             vigilancias=cargaOmisosEntidadFederativaService.obtenerVigilancias(claveEF);
         }catch(SGTServiceException e){
                 getLogger().error(e.getMessage());
                 super.addErrorMessage(e.getMessage(), "");
         }
     }
    
    
    public void inicializarPaneles(){
        setMostrarPanelRechazar(false);
        setMostrarPanelVigilancias(true);
    }
    
   
 
    
   
    
    public StreamedContent getArchivoZip(){
        SegbMovimientoDTO dto=null; 
        StreamedContent sc = null;
        try{
            dto = MovimientoUtil.addMovimiento(getSession(),
                    ConstantsCatalogos.CVE_SISTEMA,
                    ConstantsCatalogos.IDENTIFICADOR_CARGA_OMISOS_EF_BIS,
                    new Date(),new Date(),
                    ConstantsCatalogos.DESCARGA_OMISOS_EF,
                    ConstantsCatalogos.DESCARGAR_ACEPTAR_VIGILANCIA_OMISOS_EF);
        } catch (AccesoDenegadoException e) {
            getLogger().error(e.getMessage(), e);
        }
        
        try{
            if(vigilanciaAceptada.getFechaDescarga().equals("")){
                cargaOmisosEntidadFederativaService.aceptarVigilancia(vigilanciaAceptada,dto);
            }
            generarArchivoZip(vigilanciaAceptada);
            Long claveEF=usuarioEFService.obtenerUsuarioPorRfcCorto(getAccesoUsr().getUsuario()).get(0).getIdEntidadFederativa();
            vigilancias=cargaOmisosEntidadFederativaService.obtenerVigilancias(claveEF);
            
            sc=new DefaultStreamedContent(cargaOmisosEntidadFederativaService.generarArchivoVigilanciaEF(rutaArchivoZip),
                                           "text/plain", rutaArchivoZip);
        }catch(SGTServiceException e){
            getLogger().error(e.getMessage());
            super.addErrorMessage(e.getMessage(), "");
        }
        return sc;
    }
    
    public void generarArchivoZip(VigilanciaEntidadFederativa vef){
                List<String> fileList = new ArrayList<String>();
                
                fileList.add(vef.getRutaArchivoFactura());
                fileList.add(vef.getRutaArchivoFundamentos());
                fileList.add(vef.getRutaArchivoOmisos());
                
                
                this.zipIt(rutaArchivoZip,fileList);
    }
    
    public void zipIt(String zipFile,List<String> fileList){
     
         byte[] buffer = new byte[1024];
        FileOutputStream fos;
        ZipOutputStream zos=null;
         try{
            fos = new FileOutputStream(zipFile);
            zos = new ZipOutputStream(fos);
                for(String file : fileList){
         
                        
                        ZipEntry ze= new ZipEntry(file);
                        zos.putNextEntry(ze);
         
                        
                        FileInputStream in = cargaOmisosEntidadFederativaService.generarArchivoZip(file);
         
                        int len;
                        while ((len = in.read(buffer)) > 0) {
                                zos.write(buffer, 0, len);
                        }
         
                        in.close();
                }
            zos.closeEntry();
            zos.close();
        }catch(IOException ex){
           getLogger().error(ex.getMessage());
        }catch(SGTServiceException ex1) {
            getLogger().error(ex1.getMessage());
        }
         
    }
     
    public void abrirRechazar(){
        
        setMostrarPanelRechazar(true);
        setMostrarPanelVigilancias(false);
        try {
            motivosRechazo=cargaOmisosEntidadFederativaService.obtenerMotivosRechazo();
        } catch (SGTServiceException e) {
            getLogger().error(e.getMessage());
        }
    }
    
    public void aceptarRechazo(){
        try{
            mailService.enviarCorreoPara("victor.francisco@sat.gob.mx", "Prueba CargaOmisosEf", "mensaje1");
        } catch (SocketException ex) {
            getLogger().error("Error al enviar el correo CargaOmisosEf:"+ex.getMessage());
        }catch(MessagingException e){
            getLogger().error("Error al enviar el correo CargaOmisosEf:"+e.getMessage());
        }
        SegbMovimientoDTO dto=null;
        try{
            dto = MovimientoUtil.addMovimiento(getSession(),
                    ConstantsCatalogos.CVE_SISTEMA,
                    ConstantsCatalogos.IDENTIFICADOR_CARGA_OMISOS_EF_BIS,
                    new Date(),new Date(),
                    ConstantsCatalogos.RECHAZO_OMISOS_EF,
                    ConstantsCatalogos.RECHAZO_VIGILANCIA_OMISOS_EF);
        } catch (AccesoDenegadoException e) {
            getLogger().error(e.getMessage(), e);
        }
        
        try{
            vigilanciaRechazada.setRfcUsuario(getAccesoUsr().getUsuario());
            cargaOmisosEntidadFederativaService.guardarRechazo(vigilanciaRechazada,dto, getAccesoUsr().getNumeroEmp());
            vigilancias=cargaOmisosEntidadFederativaService.obtenerVigilancias(null);
            inicializarPaneles();
       }catch(SGTServiceException e){
            super.addErrorMessage(e.getMessage(), ""); 
       
       }
    }
    
    public void setMostrarPanelRechazar(boolean mostrarPanelRechazar) {
        this.mostrarPanelRechazar = mostrarPanelRechazar;
    }

    public boolean isMostrarPanelRechazar() {
        return mostrarPanelRechazar;
    }

    public void setMostrarPanelVigilancias(boolean mostrarPanelVigilancias) {
        this.mostrarPanelVigilancias = mostrarPanelVigilancias;
    }

    public boolean isMostrarPanelVigilancias() {
        return mostrarPanelVigilancias;
    }

    public void setMotivosRechazo(List<MotRechazoVig> motivosRechazo) {
        this.motivosRechazo = motivosRechazo;
    }

    public List<MotRechazoVig> getMotivosRechazo() {
        return motivosRechazo;
    }

    public void setVigilanciaRechazada(VigilanciaEntidadFederativa vigilanciaRechazada) {
        this.vigilanciaRechazada = vigilanciaRechazada;
    }

    public VigilanciaEntidadFederativa getVigilanciaRechazada() {
        return vigilanciaRechazada;
    }


    public void setVigilancias(List<VigilanciaEntidadFederativa> vigilancias) {
        this.vigilancias = vigilancias;
    }

    public List<VigilanciaEntidadFederativa> getVigilancias() {
        return vigilancias;
    }

    

    public void setVigilanciaAceptada(VigilanciaEntidadFederativa vigilanciaAceptada) {
        this.vigilanciaAceptada = vigilanciaAceptada;
    }

    public VigilanciaEntidadFederativa getVigilanciaAceptada() {
        return vigilanciaAceptada;
    }
}