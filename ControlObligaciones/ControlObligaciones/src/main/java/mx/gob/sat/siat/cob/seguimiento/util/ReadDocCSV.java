package mx.gob.sat.siat.cob.seguimiento.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.db2.HistoricoCumplimiento;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.util.constante.FilesPath;
import org.apache.log4j.Logger;

/**
 *
 * @author emmanuel
 */
public class ReadDocCSV {

    public static final String CSV_SPLIT_BY = ",";
    public static final String FORMAT_DATE = "yyyy-mm-dd";
    public static final String FORMAT_DATE_ATRIBUTE = "HHmmss";
    public static final int KEY = 0;
    public static final int VALUE = 0;
    private static final Logger LOG = Logger.getLogger(ReadDocCSV.class);

    public List<HistoricoCumplimiento> run() {

        BufferedReader br = null;
        String line;

        List<HistoricoCumplimiento> lstHistoricoCumplimiento = new ArrayList<HistoricoCumplimiento>();

        try {
            //Aqui le dan el nombre y/o con la ruta del archivo salida
            File f = new File(FilesPath.RUTA_ARCHIVO_CSV.concat(FilesPath.NOMBRE_ARCHIVO_CSV));

            br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
            SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_DATE);
            while ((line = br.readLine()) != null) {
                HistoricoCumplimiento h = new HistoricoCumplimiento();
                // use comma as separator
                String[] country = line.split(CSV_SPLIT_BY);

                LOG.debug("Country [code= " + country[4]
                        + " , name=" + country[5] + "]");
                h.setbOID(country[0]);
                h.setClaveICEP(country[1]);
                h.setEstadoVigilable(1);
                h.setEstadoIcep(Integer.parseInt(country[3]));
                h.setIdentificadorCumplimiento(country[4].equals("(null)") ? null : country[4]);
                h.setImportePagar(null);
                try {
                    h.setFechaPresentacion(country[6].equals("(null)") ? null : formatter.parse(country[6]));
                } catch (ParseException e) {
                    h.setFechaPresentacion(null);
                }

                h.setTipoDeclaracion(country[7].equals("(null)") ? null : country[7]);
                lstHistoricoCumplimiento.add(h);
            }

        } catch (FileNotFoundException e) {
            LOG.error(e);
        } catch (IOException e) {
            LOG.error(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    LOG.error(e);
                }
            }
        }

        LOG.debug("Done");
        return lstHistoricoCumplimiento;
    }

    /**
     * La siguiente función crea un Archivo si no existe y escribe en el una
     * cadena que recibe como parámetro.
     *
     * @param fArchivo
     * @param newRow
     */
    public void ecribirArchivo(String csvFilename, String newRow) throws IOException {
        File fArchivo = null;
        BufferedWriter bw = null;

        try {
            fArchivo = new File(csvFilename);

            //Si no Existe el archivo lo crea  
            if (!fArchivo.exists()) {
                fArchivo.createNewFile();
                Runtime.getRuntime().exec("chmod 664 "+fArchivo.getAbsolutePath());
            }
            /*Abre un Flujo de escritura,sobre el archivo con codificacion utf-8.  
             *Además  en el pedazo de sentencia "FileOutputStream(fArchivo,true)", 
             *true es por si existe el archivo seguir añadiendo texto y no borrar lo que tenia*/
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fArchivo, true), "utf-8"));
            /*Escribe en el archivo la cadena que recibe la función.  
             *el string "\r\n" significa salto de linea*/
            bw.write(newRow + "\n");
        } catch (FileNotFoundException ex) {
            //Captura un posible error  
            LOG.error(ex);
            throw ex;
        } catch (IOException ex) {
            LOG.error(ex);
            throw ex;
        } finally {
            if (bw != null) {
                try {
                    //Cierra el flujo de escritura
                    bw.close();
                } catch (IOException ex) {
                    LOG.error(ex);
                }

            }
        }
    }

    /**
     * Función que lee el contenido de un archivo de texto fArchivo Objeto de la
     * clase file donde se va a leer
     *
     * @param fArchivo
     */
    public List<String> leerArchivo(String csvFilename) {
        File fArchivo = null;
        List<String> readlines = null;
        BufferedReader br = null;
        try {
            fArchivo = new File(csvFilename);

            if (!fArchivo.exists()) {
                crearArchivo(csvFilename);
            }

            fArchivo = null;

            fArchivo = new File(csvFilename);

            /*Si existe el archivo*/
            if (fArchivo.exists()) {
                readlines = new ArrayList<String>();

                /*Abre un flujo de lectura a el archivo*/
                br = new BufferedReader(new InputStreamReader(new FileInputStream(fArchivo), "UTF-8"));
                String readLine;
                /*Lee el archivo linea a linea hasta llegar a la ultima*/
                while ((readLine = br.readLine()) != null) {
                    /*Agrega a la lista cada linea leida*/
                    readlines.add(readLine);
                }
            }
        } catch (FileNotFoundException ex) {
            /*Captura un posible error y le imprime en pantalla*/
            LOG.error(ex.getMessage());
        } catch (IOException ex) {
            LOG.error(ex);
        } finally {
            try {
                if (br != null) {
                    /*Cierra el flujo*/
                    br.close();
                }
            } catch (IOException ex) {
                LOG.error(ex);
            }
        }

        return readlines;
    }

    /**
     * Método que borra un archivo si existe fArchivo Objeto de la clase file
     * donde se va a borrar
     *
     * @param fArchivo
     */
    public static void borrarFichero(File fArchivo) throws SGTServiceException {
        try {
            /*Si existe el archivo*/
            if (fArchivo.exists()) {
                /*Borra el archivo*/
                fArchivo.delete();
                LOG.debug("Archivo Borrado con exito...");
            }
        } catch (Exception ex) {
            /*Captura un posible error y le imprime en pantalla*/
            LOG.error(ex);
            throw new SGTServiceException(ex);
        }
    }

    /**
     * Modificar un archivo de texto, consiste en leer un archivo y escribir su
     * con tenido en uno nuevo llamado X, excepto la linea a modificar que se
     * remplaza con la linea nueva, Luego se borra el archivo inicial y se
     * renombra el nuevo archivo con el nombre del archivo inicial
     *
     * @param csvFilename
     * @param rowOriginal
     * @param rowNew
     */
    public void modificarArchivo(String csvFilename, String rowOriginal, String rowNew) {

        File archivoOld = null;
        BufferedReader br = null;
        File archivoNuevo = null;

        try {
            archivoOld = new File(csvFilename);
            /*Creo un nombre para el nuevo archivo apartir del 
             *numero aleatorio*/
            String nombFichNuev = archivoOld.getParent() + getNameFileTMP() + ".csv";
            /*Crea un objeto File para el archivo nuevo*/
            archivoNuevo = new File(nombFichNuev);
            Runtime.getRuntime().exec("chmod 664 "+archivoNuevo.getAbsolutePath());
            /*Si existe el archivo inical*/
            if (archivoOld.exists()) {
                /*Abro un flujo de lectura*/
                br = new BufferedReader(new FileReader(archivoOld));
                String linea;
                /*Recorro el archivo de texto linea a linea*/
                while ((linea = br.readLine()) != null) {
                    /*Si la lia obtenida es igual al la bucada 
                     *para modificar*/
                    if (linea.toUpperCase().trim().equals(rowOriginal.toUpperCase().trim())) {
                        /*Escribo la nueva linea en vez de la que tenia*/
                        ecribirArchivo(nombFichNuev, rowNew);
                    } else {
                        /*Escribo la linea antigua*/
                        ecribirArchivo(nombFichNuev, linea);
                    }
                }
                /*Borro el archivo inicial*/
                borrarFichero(archivoOld);
                /*renombro el nuevo archivo con el nombre del  
                 *archivo inicial*/
                archivoNuevo.renameTo(archivoOld);
            }
        } catch (FileNotFoundException ex) {
            /*Captura un posible error y le imprime en pantalla*/
            LOG.error("No se pudo modificar el Archivo " + csvFilename);
        } catch (IOException ex) {
            LOG.error("No se pudo modificar el Archivo " + csvFilename);
            LOG.error(ex);
        } catch (SGTServiceException ex) {
            LOG.error(ex);
        } finally {
            try {
                if (br != null) {
                    /*Cierro el flujo de lectura*/
                    br.close();
                }
            } catch (IOException ex) {
                LOG.error(ex);
            }
        }
    }

    /**
     * Eliminar un registro dentro de un fichero de texto consiste en leer un
     * archivo y escribir su contenido en uno nuevo llamado X, excepto la linea
     * a eliminar.Luego se borra el fichero inicial y se renombra el nuevo
     * fichero con el nombre del archivo inicial
     *
     * @param fArchivoAntiguo
     * @param rowOld
     */
    public boolean eliminarRegistro(String csvFilename, String rowOld) throws IOException {

        File archivoOld = null;
        File archivoNuevo = null;
        BufferedReader br = null;
        boolean flag = false;

        try {
            archivoOld = new File(csvFilename);
            /*Creo un nombre para el nuevo fichero apartir del 
             *numero aleatorio*/
            String nameFileNew = archivoOld.getParent() + getNameFileTMP() + ".csv";
            /*Crea un objeto File para el fichero nuevo*/
            archivoNuevo = new File(nameFileNew);
            Runtime.getRuntime().exec("chmod 664 "+archivoNuevo.getAbsolutePath());
            /*Si existe el fichero inical*/
            if (archivoOld.exists()) {
                /*Abro un flujo de lectura*/
                br = new BufferedReader(new FileReader(archivoOld));
                String linea;
                /*Recorro el fichero de texto linea a linea*/
                while ((linea = br.readLine()) != null) {
                    /*Si la linea obtenida es distinta al la buscada 
                     *para eliminar*/
                    if (!linea.toUpperCase().trim().equals(rowOld.toUpperCase().trim())) {
                        /*la escribo en el fichero nuevo*/
                        ecribirArchivo(nameFileNew, linea);
                    } else {
                        /*Si es igual simple mete no hago nada*/
                        flag = true;
                    }
                }

                /*Borro el fichero inicial*/
                borrarFichero(archivoOld);
                /*renombro el nuevo fichero con el nombre del fichero inicial*/
                archivoNuevo.renameTo(archivoOld);
                return flag;
            } else {
                return false;
            }
        } catch (FileNotFoundException ex) {
            LOG.error(ex);
            throw ex;
        } catch (IOException ex) {
            LOG.error("No se pudo borrar registro del archivo " + csvFilename);
            LOG.error(ex);
            throw ex;
        } catch (SGTServiceException ex) {
            LOG.error(ex);
            return false;
        } finally {
            if (br != null) {
                try {
                    /*Cierro el flujo de lectura*/
                    br.close();
                } catch (IOException ex) {
                    LOG.error(ex);
                }
            }

        }
    }

    private void crearArchivo(String fileName) {
        BufferedWriter bw = null;
        File fArchivoTMP = null;

        try {
            fArchivoTMP = new File(fileName);
            //Si no Existe el archivo lo crea  
            if (fArchivoTMP.createNewFile()) {
                fArchivoTMP.setReadable(true);
                Runtime.getRuntime().exec("chmod 664 "+fArchivoTMP.getAbsolutePath());
                bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fArchivoTMP, true), "utf-8"));
                /*Escribe en el archivo la cadena que recibe la función.  
                 *el string "\r\n" significa salto de linea*/
                bw.write("id,firma\n");
            }

        } catch (IOException ex1) {
            LOG.error(ex1);
            LOG.error("El Archivo " + fileName + " No Existe");
        } finally {
            try {
                if (bw != null) {
                    /*Cierra el flujo*/
                    bw.flush();
                    bw.close();
                }
            } catch (IOException ex) {
                LOG.error(ex);
            }
        }

    }

    private String getNameFileTMP() {
        /*Obtengo un nombre aleatorio*/
        return (String.valueOf(System.currentTimeMillis()));
    }
}
