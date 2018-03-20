/*	****************************************************************
 * Nombre de archivo: FileUtility.java
 * Autores: Emmanuel Estrada Gonzalez 		 	 		   
 * Bitácora de modificaciones:
 * CR/Defecto Fecha Autor Descripción del cambio
 * ---------------------------------------------------------------------------
 * N/A 20/08/2014 <Nombre del desarrollador que está modificando el archivo>
 * ---------------------------------------------------------------------------
 */
package mx.gob.sat.siat.cob.seguimiento.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.SgtcProcesoDTO;
import static mx.gob.sat.siat.cob.seguimiento.util.Utilerias.formatearFechaAAAAMMDDHHMMSS;
import mx.gob.sat.siat.cob.seguimiento.util.constante.FilesPath;

/**
 *
 * @author Emmanuel Estrada Gonzalez 
 */
public class FileUtility<T> {
    private String fileFullName;
        
    public String crearArchivoTXT(String nameFile, List<T> lstData) throws IOException {
        escribirArchivo(crearArchivo(nameFile), lstData);
        return fileFullName;
    }

    public PrintWriter crearArchivo(String nameFile) throws IOException {
        String fileNamePart = formatearFechaAAAAMMDDHHMMSS(new Date());
        //Las siguientes 3 líneas nos permite crear un archivo y escribir en el
        fileFullName=nameFile+"_"+fileNamePart+FilesPath.TYPE_TXT;
        
        File archivo = new File(FilesPath.RUTA_ARCHIVO_TMP_TXT+fileFullName);
        FileWriter writer = new FileWriter(archivo);
        return (new PrintWriter(writer));
    }

    public PrintWriter escribirArchivo(PrintWriter salida, List<T> lstCadena){
        if (lstCadena != null && lstCadena.size() > 0) {            
            if (lstCadena.get(0) instanceof SgtcProcesoDTO) {
                //Este método permite escribir en el archivo
                salida.write("IDPROCESO       |"
                            +"NOMBRE                                                                |"
                            +"DESCRIPCION                                                           |"
                            +"LANZADOR        |"
                            +"PROGRAMACION    |"
                            +"EXCLUIR         |"
                            +"PRIORIDAD       |"
                            +"ESTADO          |"
                            +"INTENTOS        |"
                            +"INTENTOSMAX     |"
                            +"IDMANAGER       |"
                            +"INICIOEJECUCION |"
                            +"FINEJECUCION    |"
                            +"TIPOPROCESAMIENTO");                
                //Este método sirve para dar un salto de línea
                salida.println();
            }
            
            for (T line : lstCadena) {
                salida.write(line.toString());
                salida.println();
            }
        }
        
        
        //Es importante no olvidar cerrar el archivo
        salida.close();
        return salida;
    }
}
