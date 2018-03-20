package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.util.List;

/**
 *
 * @author root
 */
public class ReporteJasperAfectacionDTO {
    
    private String numeroControl;
    private String fechaRegistro;
    private String fechaNotificacion;
    private String fechaVencimiento;
    private String estado;
    private String tipoDocumento;
    private String admonLocal;
    private String rfc;
    private String nombre;
    private String tipoMedio;
    private String fechaNoTrabajado;
    private String fechaNoLocalizado;
    private String fechaCitatorio;
    
    private List<ReporteAfectacionXAutoridadDTO> listaObligaciones;
    private List<MultaDocumentoAfectaciones> listaMultas;
    
    
    
    /**
     *
     */
    public ReporteJasperAfectacionDTO() {
        super();
    }

    /**
     *
     * @param numeroControl
     */
    public void setNumeroControl(String numeroControl) {
        this.numeroControl = numeroControl;
    }

    /**
     *
     * @return
     */
    public String getNumeroControl() {
        return numeroControl;
    }

    /**
     *
     * @param fechaRegistro
     */
    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /**
     *
     * @return
     */
    public String getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     *
     * @param fechaNotificacion
     */
    public void setFechaNotificacion(String fechaNotificacion) {
        this.fechaNotificacion = fechaNotificacion;
    }

    /**
     *
     * @return
     */
    public String getFechaNotificacion() {
        return fechaNotificacion;
    }

    /**
     *
     * @param fechaVencimiento
     */
    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    /**
     *
     * @return
     */
    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    /**
     *
     * @param estado
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     *
     * @return
     */
    public String getEstado() {
        return estado;
    }

    /**
     *
     * @param listaObligaciones
     */
    public void setListaObligaciones(List<ReporteAfectacionXAutoridadDTO> listaObligaciones) {
        this.listaObligaciones = listaObligaciones;
    }

    /**
     *
     * @return
     */
    public List<ReporteAfectacionXAutoridadDTO> getListaObligaciones() {
        return listaObligaciones;
    }

    /**
     *
     * @param listaMultas
     */
    public void setListaMultas(List<MultaDocumentoAfectaciones> listaMultas) {
        this.listaMultas = listaMultas;
    }

    /**
     *
     * @return
     */
    public List<MultaDocumentoAfectaciones> getListaMultas() {
        return listaMultas;
    }

    /**
     *
     * @param tipoDocumento
     */
    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    /**
     *
     * @return
     */
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setAdmonLocal(String admonLocal) {
        this.admonLocal = admonLocal;
    }

    public String getAdmonLocal() {
        return admonLocal;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getRfc() {
        return rfc;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipoMedio() {
        return tipoMedio;
    }

    public void setTipoMedio(String tipoMedio) {
        this.tipoMedio = tipoMedio;
    }
    
    /**
     * @return the FechaNoTrabajado
     */
    public String getFechaNoTrabajado() {
        return fechaNoTrabajado;
    }

    /**
     * @param FechaNoTrabajado the FechaNoTrabajado to set
     */
    public void setFechaNoTrabajado(String fechaNoTrabajado) {
        this.fechaNoTrabajado = fechaNoTrabajado;
    }

    /**
     * @return the fechaNoLocalizado
     */
    public String getFechaNoLocalizado() {
        return fechaNoLocalizado;
    }

    /**
     * @param fechaNoLocalizado the fechaNoLocalizado to set
     */
    public void setFechaNoLocalizado(String fechaNoLocalizado) {
        this.fechaNoLocalizado = fechaNoLocalizado;
    }
    
    /**
     * @return the fechaCitatorio
     */
    public String getFechaCitatorio() {
        return fechaCitatorio;
    }
    
    /**
     * @param fechaCitatorio the fechaCitatorio to set
     */
    public void setFechaCitatorio(String fechaCitatorio) {
        this.fechaCitatorio = fechaCitatorio;
    }
    
}
