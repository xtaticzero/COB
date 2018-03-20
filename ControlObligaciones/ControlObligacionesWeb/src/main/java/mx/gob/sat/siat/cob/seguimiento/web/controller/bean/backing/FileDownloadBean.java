/*	****************************************************************
 * Nombre de archivo: FileDownloadBean.java
 * Autores: Emmanuel Estrada Gonzalez 		 	 		
 * Bitácora de modificaciones:
 * CR/Defecto Fecha Autor Descripción del cambio
 * ---------------------------------------------------------------------------
 * N/A 25/11/2013 <Nombre del desarrollador que está modificando el archivo>
 * ---------------------------------------------------------------------------
 */
package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.SocketException;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.arca.ftp.impl.FtpConnectionServiceImpl;
import mx.gob.sat.siat.cob.seguimiento.utils.AbstractBaseMB;


import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


/**
 *
 * @author root
 */
@Controller("fileDownloadBean")
@Scope(value = "view")
public class FileDownloadBean extends AbstractBaseMB {
    @Value("#{ftpConfig['ftp.host']}")
    private String host;
    @Value("#{ftpConfig['ftp.user']}")
    private String user;
    @Value("#{ftpConfig['ftp.pasword']}")
    private String password;
    @Value("#{ftpConfig['ftp.source.dir']}")
    private String sDir;    
    
    private StreamedContent file;

    private String fileSelected;
    
    private List<CatalogoBase> filesLst = new ArrayList<CatalogoBase>(); 

    private FtpConnectionServiceImpl ftpConnectionServiceImpl;
    
    /**
     *
     */
    @PostConstruct
    public void init() {
        try {
            ftpConnectionServiceImpl = new FtpConnectionServiceImpl();
            ftpConnectionServiceImpl.connect(host,user,password,sDir);
            getLogger().info("::::::::::: Se conecto clientFTP :::::::::::::::::::");
            
            String[] names = ftpConnectionServiceImpl.listFiles();
            for (String name : names) {
                CatalogoBase item = new CatalogoBase();
                item.setIdString(name);
                item.setNombre(name);
                filesLst.add(item);                
            }                   
           
        } catch (SocketException e) {
            getLogger().error(e.getMessage());
        } catch (IOException e) {
            getLogger().error(e.getMessage());
        } catch (SGTServiceException e) {
            getLogger().error(e.getMessage());
        }
    }    

    /**
     *
     */
    public FileDownloadBean() {
        super();
    }

    /**
     *
     * @return
     */
    public StreamedContent getFile() {
        return file;
    }

    /**
     *
     * @return
     */
    public String getFileSelected() {
        return fileSelected;
    }

    /**
     *
     * @param city
     */
    public void setFileSelected(String city) {
        this.fileSelected = city;
    }

  
    
    /**
     *
     */
    public void startDowload() {

        try {
            InputStream stream = null;

            byte[] bytesFile;
            bytesFile = ftpConnectionServiceImpl.dowloadFile(getFileSelected());
            stream = new ByteArrayInputStream(bytesFile);
            getLogger().error(fileSelected);
            file = new DefaultStreamedContent(stream, "image/jpg", getFileSelected());
            addOkToCallback();
        } catch (SGTServiceException e) {
            getLogger().error(e.getMessage());
        } catch (IOException e) {
            getLogger().error(e.getMessage());
        }
    }
    
    private void addOkToCallback() {
            RequestContext.getCurrentInstance().addCallbackParam("ok", true);
        }

    /**
     *
     * @param filesLst
     */
    public void setFilesLst(List<CatalogoBase> filesLst) {
        this.filesLst = filesLst;
    }

    /**
     *
     * @return
     */
    public List<CatalogoBase> getFilesLst() {
        return filesLst;
    }
}
