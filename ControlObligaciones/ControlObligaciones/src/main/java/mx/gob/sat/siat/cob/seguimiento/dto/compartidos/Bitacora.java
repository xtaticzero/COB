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
public class Bitacora implements Serializable {

    private static final long serialVersionUID = 55L;
    private String rutaRepoBitacoraErrores;
    private static final String NOMBRE_PARC_ARCH = "bitacora_";
    private static final String EXT_ARCH = ".txt";
    private String rutaFinal;
    private transient Writer escritor;
    private boolean rollbacked = false;
    private boolean inicializada = false;
    private boolean estatus = false;
    private String nombreEnBitacora;

    /**
     *
     * @param rutaRepoBitacoraErrores
     */
    public Bitacora(String rutaRepoBitacoraErrores) {
        this.rutaRepoBitacoraErrores = rutaRepoBitacoraErrores;
    }

    /**
     *
     * @param linea
     * @throws IOException
     */
    public void escribe(String linea) throws IOException {
        this.escritor.write(linea + "\n");
    }

    /**
     *
     * @param dto
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    public void inicializa(TareaDTO dto,String codificacion) throws FileNotFoundException, UnsupportedEncodingException {
        OutputStream os;
        File dirTmp = new File(rutaRepoBitacoraErrores);
        estatus = dirTmp.mkdirs();
        String nombreArchivo = dto.toString();
        this.nombreEnBitacora = NOMBRE_PARC_ARCH + nombreArchivo + EXT_ARCH;
        this.rutaFinal = rutaRepoBitacoraErrores + NOMBRE_PARC_ARCH + nombreArchivo + EXT_ARCH;
        os = new FileOutputStream(this.rutaFinal);
        this.escritor = new OutputStreamWriter(os, codificacion);
        this.inicializada = true;
    }

    /**
     *
     * @throws IOException
     */
    public void cierra() throws IOException {
        if (!this.rollbacked && this.inicializada) {
            this.escritor.flush();
            this.escritor.close();
        }
    }

    /**
     *
     * @throws IOException
     */
    public void rollbackBitacora() throws IOException {
        this.escritor.close();
        File a = new File(this.rutaFinal);
        estatus = a.delete();
        this.rollbacked = true;
    }

    /**
     *
     * @return
     */
    public boolean estaInicializada() {
        return this.inicializada;
    }

    /**
     *
     * @return
     */
    public String getNombreEnBitacora() {
        return nombreEnBitacora;
    }

    /**
     *
     * @return
     */
    public String getRutaFinal() {
        return rutaFinal;
    }

    /**
     *
     * @return
     */
    public boolean isEstatus() {
        return estatus;
    }

    /**
     *
     * @param estatus
     */
    public void setEstatus(boolean estatus) {
        this.estatus = estatus;
    }

    /**
     *
     * @return
     */
    public String getRutaRepoBitacoraErrores() {
        return rutaRepoBitacoraErrores;
    }

    /**
     *
     * @param rutaRepoBitacoraErrores
     */
    public void setRutaRepoBitacoraErrores(String rutaRepoBitacoraErrores) {
        this.rutaRepoBitacoraErrores = rutaRepoBitacoraErrores;
    }
}
