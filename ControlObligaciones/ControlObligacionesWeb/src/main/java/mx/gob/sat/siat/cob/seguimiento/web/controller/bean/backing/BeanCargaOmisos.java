package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleCarga;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

public class BeanCargaOmisos extends DetalleCarga{
    
    /**
     *
     */
    public BeanCargaOmisos() {
        super();
    }
    
    public BeanCargaOmisos(DetalleCarga detalleCarga) {
        this.setIdVigilancia(detalleCarga.getIdVigilancia());
        this.setNombreOriginalArchivo(detalleCarga.getNombreOriginalArchivo());
        this.setNombreEnRepositorio(detalleCarga.getNombreEnRepositorio());
        this.setNumeroRegistros(detalleCarga.getNumeroRegistros());
        this.setRutaEnRepositorio(detalleCarga.getRutaEnRepositorio());
        this.setRutaEnBitacora(detalleCarga.getRutaEnBitacora());
        this.setCargaInvalida(detalleCarga.isCargaInvalida());
        this.setTotalRegistrosConError(detalleCarga.getTotalRegistrosConError());
        this.setCodificacionCorrecta(detalleCarga.isCodificacionCorrecta());
    }
      
    /**
     *
     * @return
     * @throws IOException
     */
    public StreamedContent getArchivoBitacora() throws IOException{
        File arc = new File(getRutaEnBitacora());
        InputStream is = new FileInputStream(arc);
        StreamedContent sc = new DefaultStreamedContent(is,"text/plain",
                getNombreOriginalArchivo()+"_errores.txt");
        return sc;
    }

}
