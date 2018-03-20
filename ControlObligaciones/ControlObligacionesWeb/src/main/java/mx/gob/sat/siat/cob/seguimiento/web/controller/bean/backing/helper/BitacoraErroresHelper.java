package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.BitacoraErrores;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

public class BitacoraErroresHelper extends BitacoraErrores{
    public BitacoraErroresHelper() {
        super();
    }
    
    public StreamedContent getArchivoBitacora() throws IOException {
        File arc = new File(super.getRutaBitacoraErrores());
        InputStream is = new FileInputStream(arc);
        return new DefaultStreamedContent(is, "text/plain", super.getRutaBitacoraErrores());
    }
    
    
    
}
