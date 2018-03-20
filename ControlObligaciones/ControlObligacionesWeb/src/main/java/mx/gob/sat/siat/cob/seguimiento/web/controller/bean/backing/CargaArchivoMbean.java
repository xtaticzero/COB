package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author christianV
 */
@ManagedBean(name = "cargaArchivoMbean")
@ApplicationScoped
public class CargaArchivoMbean implements Serializable {

    Logger log = Logger.getLogger(CargaArchivoMbean.class);
    private StreamedContent inputImagen;
    private StreamedContent inputImagenPie;

    public void handleFileUpload(FileUploadEvent event) {
        log.debug("handleFileUpload--------------");
        UploadedFile file;
        file = event.getFile();
        inputImagen = new DefaultStreamedContent(new ByteArrayInputStream(file.getContents()), "image/png", "nombre.png");
    }

    public StreamedContent getInputImagen() {
        log.debug("getInputImagen--------------");
        if (inputImagen != null) {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
                return new DefaultStreamedContent();
            } else {
                return inputImagen;
            }
        } else {
            return null;
        }
    }
    
    public void handleFileUploadPie(FileUploadEvent event) {
        log.debug("handleFileUploadPie--------------");
        UploadedFile file;
        file = event.getFile();
        inputImagenPie = new DefaultStreamedContent(new ByteArrayInputStream(file.getContents()), "image/png", "nombre.png");
    }

    public StreamedContent getInputImagenPie() {
        log.debug("getInputImagenPie--------------");
        if (inputImagenPie != null) {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
                return new DefaultStreamedContent();
            } else {
                return inputImagenPie;
            }
        } else {
            return null;
        }
    }
}
