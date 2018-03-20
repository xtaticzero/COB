package mx.gob.sat.siat.cob.seguimiento.dto.compartidos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

/**
 *
 * @author root
 */
public class Repositorio implements Serializable {

    private static final long serialVersionUID = 45L;
    private String rutaRepositorio;
    private String rutaFinal;
    private static final String NOMBRE_PARC_ARCH = "repositorio_";
    private static final String EXT_ARCH = ".txt";
    private transient Writer escritor;
    private boolean rollbacked = false;
    private boolean inicializado = false;
    private boolean estatus = false;
    private String nombreDeRepositorio;

    /**
     *
     */
    public Repositorio(String rutaRepositorio) {
        this.rutaRepositorio = rutaRepositorio;
    }

    /**
     *
     * @param linea
     * @throws java.io.IOException
     */
    public void escribe(String linea) throws IOException {
        this.escritor.write(linea + "\n");
    }

    /**
     *
     * @param dto
     * @throws java.io.FileNotFoundException
     * @throws java.io.UnsupportedEncodingException
     */
    public void inicializa(TareaDTO dto, String codificacion) throws FileNotFoundException, UnsupportedEncodingException {
        OutputStream os;
        File dirTmp = new File(rutaRepositorio);
        estatus = dirTmp.mkdirs();
        String nombreArchivo = dto.toString();
        this.nombreDeRepositorio = NOMBRE_PARC_ARCH + nombreArchivo + EXT_ARCH;
        this.rutaFinal = rutaRepositorio + NOMBRE_PARC_ARCH + nombreArchivo + EXT_ARCH;
        os = new FileOutputStream(this.rutaFinal);
        this.escritor = new OutputStreamWriter(os, codificacion);
        this.inicializado = true;
    }

    /**
     *
     * @throws java.io.IOException
     */
    public void cierra() throws IOException {
        if (!this.rollbacked && this.inicializado) {
            this.escritor.flush();
            this.escritor.close();
        }
    }

    /**
     *
     * @throws java.io.IOException
     */
    public void rollbackRepositorio() throws IOException {
        this.escritor.close();
        this.rollbacked = true;
        File a = new File(this.rutaFinal);
        estatus = a.delete();
    }

    /**
     *
     * @return
     */
    public boolean repoRollbacked() {
        return this.rollbacked;
    }

    /**
     *
     * @return
     */
    public boolean estaInicializado() {
        return this.inicializado;
    }

    /**
     *
     * @return
     */
    public String getNombreDeRepositorio() {
        return nombreDeRepositorio;
    }

    /**
     *
     * @return
     */
    public String getRutaFinal() {
        return rutaFinal;
    }

    public boolean isEstatus() {
        return estatus;
    }

    public void setEstatus(boolean estatus) {
        this.estatus = estatus;
    }

    public String getRutaRepositorio() {
        return rutaRepositorio;
    }

    public void setRutaRepositorio(String rutaRepositorio) {
        this.rutaRepositorio = rutaRepositorio;
    }

}
