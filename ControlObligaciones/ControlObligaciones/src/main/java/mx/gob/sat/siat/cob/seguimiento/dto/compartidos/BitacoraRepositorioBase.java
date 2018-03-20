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
 * @author christian.ventura
 */
public class BitacoraRepositorioBase implements Serializable {
    private static final long serialVersionUID = 567433L;
    private static final String NOMBRE_PARC_ARCH = "_";
    private static final String EXT_ARCH = ".txt";
    private String rutaFinal;
    private String rutaGuardado;
    private transient Writer escritor;
    private boolean rollbacked = false;
    private boolean inicializado = false;
    private boolean estatus = false;
    private String nombreIdentificador;
    
    public BitacoraRepositorioBase(String rutaGuardado){
        this.rutaGuardado=rutaGuardado;
    }
    
    /**
     *
     * @param dto
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    public void inicializa(TareaDTO dto,String codificacion) throws FileNotFoundException, UnsupportedEncodingException {
        OutputStream os;
        File dirTmp = new File(rutaGuardado);
        estatus = dirTmp.mkdirs();
        String nombreArchivo = dto.toString();
        this.nombreIdentificador = NOMBRE_PARC_ARCH + nombreArchivo + EXT_ARCH;
        this.rutaFinal = rutaGuardado + NOMBRE_PARC_ARCH + nombreArchivo + EXT_ARCH;
        os = new FileOutputStream(this.rutaFinal);
        this.escritor = new OutputStreamWriter(os, codificacion);
        this.inicializado = true;
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
     * @throws java.io.IOException
     */
    public void cierra() throws IOException{
        if(!this.isRollbacked() && this.isInicializado()){
            this.escritor.flush();
            this.escritor.close();
        }
    }
    /**
     *
     * @throws java.io.IOException
     */
    public void rollback() throws IOException{
        this.escritor.close();
        this.setRollbacked(true);
        File a = new File(this.getRutaFinal());
        setEstatus(a.delete());
    }

    /**
     * @return the rutaFinal
     */
    public String getRutaFinal() {
        return rutaFinal;
    }

    /**
     * @param rutaFinal the rutaFinal to set
     */
    public void setRutaFinal(String rutaFinal) {
        this.rutaFinal = rutaFinal;
    }

    /**
     * @return the rutaGuardado
     */
    public String getRutaGuardado() {
        return rutaGuardado;
    }

    /**
     * @param rutaGuardado the rutaGuardado to set
     */
    public void setRutaGuardado(String rutaGuardado) {
        this.rutaGuardado = rutaGuardado;
    }

    /**
     * @return the rollbacked
     */
    public boolean isRollbacked() {
        return rollbacked;
    }

    /**
     * @param rollbacked the rollbacked to set
     */
    public void setRollbacked(boolean rollbacked) {
        this.rollbacked = rollbacked;
    }

    /**
     * @return the inicializado
     */
    public boolean isInicializado() {
        return inicializado;
    }

    /**
     * @param inicializado the inicializado to set
     */
    public void setInicializado(boolean inicializado) {
        this.inicializado = inicializado;
    }

    /**
     * @return the estatus
     */
    public boolean isEstatus() {
        return estatus;
    }

    /**
     * @param estatus the estatus to set
     */
    public void setEstatus(boolean estatus) {
        this.estatus = estatus;
    }

    /**
     * @return the nombreIdentificador
     */
    public String getNombreIdentificador() {
        return nombreIdentificador;
    }

    /**
     * @param nombreIdentificador the nombreIdentificador to set
     */
    public void setNombreIdentificador(String nombreIdentificador) {
        this.nombreIdentificador = nombreIdentificador;
    }
    
    
}
