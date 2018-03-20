package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.ParametroSgtEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.extraccion.DocumentoService;
import mx.gob.sat.siat.cob.seguimiento.service.otros.SGTService;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import mx.gob.sat.siat.cob.seguimiento.utils.AbstractBaseMB;

import mx.gob.sat.siat.cob.seguimiento.web.util.CreaArchivoSistema;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("multasPendientes")
@Scope(value="view")
public class MultasPendientesManagedBean extends AbstractBaseMB{
    
    
    @Value("#{properties['filename.archivo.multas']}")
    private String nombreArchivoMultas;
    
    @Autowired
    private DocumentoService documentoService;
    
    @Autowired
    private SGTService sgtService;
    
    
    @PostConstruct
    public void init() {
        obtenerAccesoUsrEmpleados();
        autorizar(ConstantsCatalogos.IDENTIFICADOR_MULTAS_PENDIENTES);
    }
    
    
    public StreamedContent getArchivoMultasPendientes(){
        try{
            
            CreaArchivoSistema.crearArchivoMultasPendientes(documentoService.obtenerMultasPendientes(getAccesoUsr().getUsuario()), getFileName(nombreArchivoMultas),sgtService.obtenerParametroSgtPorIdParametro(ParametroSgtEnum.CANTIDAD_DIAS_REPORTE_EF.getValor()));
            File arc = new File(getFileName(nombreArchivoMultas));
            InputStream is = new FileInputStream(arc);
            return new DefaultStreamedContent(is,"text/plain",getFileName(nombreArchivoMultas));
        } catch (SGTServiceException e) {
            super.addErrorMessage("", e.getMessage());
            getLogger().debug("Error Metodo: getArchivo(), Causa:" + e + " Mensaje: " + e.getMessage() + " Clase error:" + e.getClass());
        } catch (FileNotFoundException e) {
            super.addErrorMessage("", "No existen registros con los datos de búsqueda");
            getLogger().debug("Error Metodo: getArchivo(), Causa:" + e+ " Mensaje: " + e.getMessage() + " Clase error:" + e.getClass());
        }
            return null;
    }


    public void setNombreArchivoMultas(String nombreArchivoMultas) {
        this.nombreArchivoMultas = nombreArchivoMultas;
    }

    public String getNombreArchivoMultas() {
        return nombreArchivoMultas;
    }

    
}
