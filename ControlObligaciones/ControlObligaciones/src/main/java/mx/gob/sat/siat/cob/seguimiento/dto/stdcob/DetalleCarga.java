package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;

import java.util.Date;

public class DetalleCarga implements Serializable {

    static final long serialVersionUID = 9284946L;
    private int idVigilancia;
    private String nombreOriginalArchivo;
    private String nombreEnRepositorio;
    private int numeroRegistros;
    private String rutaEnRepositorio;
    private String rutaEnBitacora;
    private boolean cargaInvalida;
    private int totalRegistrosConError = 0;
    private Date fechaCargaArchivo;
    private String fechaCargaArchivoStr;
    private boolean codificacionCorrecta;

    /**
     *
     */
    public DetalleCarga() {
        super();
    }

    /**
     *
     * @param idVigilancia
     */
    public void setIdVigilancia(int idVigilancia) {
        this.idVigilancia = idVigilancia;
    }

    /**
     *
     * @return
     */
    public int getIdVigilancia() {
        return idVigilancia;
    }

    /**
     *
     * @param nombreOriginalArchivo
     */
    public void setNombreOriginalArchivo(String nombreOriginalArchivo) {
        this.nombreOriginalArchivo = nombreOriginalArchivo;
    }

    /**
     *
     * @return
     */
    public String getNombreOriginalArchivo() {
        return nombreOriginalArchivo;
    }

    /**
     *
     * @param nombreEnRepositorio
     */
    public void setNombreEnRepositorio(String nombreEnRepositorio) {
        this.nombreEnRepositorio = nombreEnRepositorio;
    }

    /**
     *
     * @return
     */
    public String getNombreEnRepositorio() {
        return nombreEnRepositorio;
    }

    /**
     *
     * @param numeroRegistros
     */
    public void setNumeroRegistros(int numeroRegistros) {
        this.numeroRegistros = numeroRegistros;
    }

    /**
     *
     * @return
     */
    public int getNumeroRegistros() {
        return numeroRegistros;
    }

    /**
     *
     * @param rutaEnRepositorio
     */
    public void setRutaEnRepositorio(String rutaEnRepositorio) {
        this.rutaEnRepositorio = rutaEnRepositorio;
    }

    /**
     *
     * @return
     */
    public String getRutaEnRepositorio() {
        return rutaEnRepositorio;
    }

    /**
     *
     * @param rutaEnBitacora
     */
    public void setRutaEnBitacora(String rutaEnBitacora) {
        this.rutaEnBitacora = rutaEnBitacora;
    }

    /**
     *
     * @return
     */
    public String getRutaEnBitacora() {
        return rutaEnBitacora;
    }

    /**
     *
     * @param cargaInvalida
     */
    public void setCargaInvalida(boolean cargaInvalida) {
        this.cargaInvalida = cargaInvalida;
    }

    /**
     *
     * @return
     */
    public boolean isCargaInvalida() {
        return cargaInvalida;
    }

    /**
     *
     * @param totalRegistrosConError
     */
    public void setTotalRegistrosConError(int totalRegistrosConError) {
        this.totalRegistrosConError = totalRegistrosConError;
    }

    /**
     *
     * @return
     */
    public int getTotalRegistrosConError() {
        return totalRegistrosConError;
    }

    /**
     *
     * @return
     */
    public int getTotalRegistrosCorrectos() {
        return getNumeroRegistros()-totalRegistrosConError;
    }

    @Override
    public String toString() {
        return "DetalleCarga{" + "idVigilancia=" + idVigilancia + 
                ", nombreOriginalArchivo=" + nombreOriginalArchivo + 
                ", nombreEnRepositorio=" + nombreEnRepositorio + 
                ", numeroRegistros=" + numeroRegistros + 
                ", rutaEnRepositorio=" + rutaEnRepositorio + 
                ", rutaEnBitacora=" + rutaEnBitacora + 
                ", cargaInvalida=" + cargaInvalida + 
                ", codificacionCorrecta=" + codificacionCorrecta + 
                ", totalRegistrosConError=" + totalRegistrosConError + '}';
    }
    
    public void setFechaCargaArchivo(Date fechaCargaArchivo) {
        this.fechaCargaArchivo = fechaCargaArchivo != null ? new Date(fechaCargaArchivo.getTime()): null;
    }

    public Date getFechaCargaArchivo() {
        if(fechaCargaArchivo!=null){
        return (Date) fechaCargaArchivo.clone();
        }
        return null;
    }

    public void setFechaCargaArchivoStr(String fechaCargaArchivoStr) {
        this.fechaCargaArchivoStr = fechaCargaArchivoStr;
    }

    public String getFechaCargaArchivoStr() {
        return fechaCargaArchivoStr;
    }

    public void setCodificacionCorrecta(boolean codificacionCorrecta) {
        this.codificacionCorrecta = codificacionCorrecta;
    }

    public boolean isCodificacionCorrecta() {
        return codificacionCorrecta;
    }
    
}
