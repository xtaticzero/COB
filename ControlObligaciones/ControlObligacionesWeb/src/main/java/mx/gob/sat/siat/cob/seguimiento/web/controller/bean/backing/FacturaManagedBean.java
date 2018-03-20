/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import mx.gob.sat.siat.cob.seguimiento.dto.report.enums.FacturaVigilanciaAprobadaEnum;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import mx.gob.sat.siat.cob.seguimiento.utils.AbstractBaseMB;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author emmanuel
 */
@Controller("facturaVigilancias")
@Scope("session")
public class FacturaManagedBean extends AbstractBaseMB {
    private String identificador;
    private StreamedContent streamedContent;
    private HttpSession session;
    private String namefullFile;
    private transient FileInputStream fis;
    private static final long serialVersionUID = -7361L;
    
    @PostConstruct
    public void init() {
        super.obtenerAccesoUsrEmpleados();
        identificador = ConstantsCatalogos.IDENTIFICADOR_VIGILANCIA;
        autorizar(identificador);
    }
    
    public void cargarArchivo() {
        session = ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true));
        fis = null;
        namefullFile = (String) (session).getAttribute("nameFactura");
        if (namefullFile == null) {
            namefullFile = "/siat/cob/tmp/factura51000000143674.pdf";
        }
        try {
            fis = new FileInputStream(new File(namefullFile));
            streamedContent = new DefaultStreamedContent(fis, "application/pdf");
        } catch (FileNotFoundException ex) {
            streamedContent = null;
            getLogger().error(ex);
        }
        
    }
    
    public StreamedContent getFactura() {
        try {
            return new DefaultStreamedContent(new FileInputStream(namefullFile),
                "application/pdf",
                FacturaVigilanciaAprobadaEnum.NOMBRE_ARCHIVO
                + FacturaVigilanciaAprobadaEnum.EXTENSION);
        } catch (FileNotFoundException ex) {
            getLogger().error(ex);
            return null;
        } 
    }
    
    public void regresar() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("aprobarVigilancias.jsf");
        } catch (IOException ex) {
            getLogger().error(ex);
        }

    }
    
    public StreamedContent getStreamedContent() {
        if (FacesContext.getCurrentInstance().getRenderResponse()) {
            return streamedContent;
        } else {
            return streamedContent;
        }
    }

    public void setStreamedContent(StreamedContent streamedContent) {
        this.streamedContent = streamedContent;
    }
    
}
