package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

public class VigilanciaEntidadFederativa extends Vigilancia {

    public VigilanciaEntidadFederativa() {
        super();
    }

    private String nombreEntidadFederativa;
    private Long idEntidadFederativa;
    private Long cantidadDocumentos;
    private String rutaArchivoOmisos;
    private String rutaArchivoFactura;
    private String rutaArchivoFundamentos;
    private Long idSituacionArchivo;
    private String fechaDescarga;
    private String fechaCorte;
    private String descFechaCargaArchivos;
    private Long idMotivoRechazo;
    private String descTipoDocumento;
    private String descSituacionArchivo;
    private int estadoDocumento;
    private boolean mostrarBtnDescarga;
    private boolean mostrarBtnRechazo;
    private boolean descargarFundamento;
    private boolean descargarFactura;

    public void setIdEntidadFederativa(Long idEntidadFederativa) {
        this.idEntidadFederativa = idEntidadFederativa;
    }

    public Long getIdEntidadFederativa() {
        return idEntidadFederativa;
    }

    public void setCantidadDocumentos(Long cantidadDocumentos) {
        this.cantidadDocumentos = cantidadDocumentos;
    }

    public Long getCantidadDocumentos() {
        return cantidadDocumentos;
    }

    public void setRutaArchivoOmisos(String rutaArchivoOmisos) {
        this.rutaArchivoOmisos = rutaArchivoOmisos;
    }

    public String getRutaArchivoOmisos() {
        return rutaArchivoOmisos;
    }

    public void setRutaArchivoFactura(String rutaArchivoFactura) {
        this.rutaArchivoFactura = rutaArchivoFactura;
    }

    public String getRutaArchivoFactura() {
        return rutaArchivoFactura;
    }

    public void setRutaArchivoFundamentos(String rutaArchivoFundamentos) {
        this.rutaArchivoFundamentos = rutaArchivoFundamentos;
    }

    public String getRutaArchivoFundamentos() {
        return rutaArchivoFundamentos;
    }

    public void setIdSituacionArchivo(Long idSituacionArchivo) {
        this.idSituacionArchivo = idSituacionArchivo;
    }

    public Long getIdSituacionArchivo() {
        return idSituacionArchivo;
    }

    public void setFechaDescarga(String fechaDescarga) {
        this.fechaDescarga = fechaDescarga;
    }

    public String getFechaDescarga() {
        return fechaDescarga;
    }

    public void setFechaCorte(String fechaCorte) {
        this.fechaCorte = fechaCorte;
    }

    public String getFechaCorte() {
        return fechaCorte;
    }

    public void setIdMotivoRechazo(Long idMotivoRechazo) {
        this.idMotivoRechazo = idMotivoRechazo;
    }

    public Long getIdMotivoRechazo() {
        return idMotivoRechazo;
    }

    public void setDescTipoDocumento(String descTipoDocumento) {
        this.descTipoDocumento = descTipoDocumento;
    }

    public String getDescTipoDocumento() {
        return descTipoDocumento;
    }

    public void setDescSituacionArchivo(String descSituacionArchivo) {
        this.descSituacionArchivo = descSituacionArchivo;
    }

    public String getDescSituacionArchivo() {
        return descSituacionArchivo;
    }

    public void setDescFechaCargaArchivos(String descFechaCargaArchivos) {
        this.descFechaCargaArchivos = descFechaCargaArchivos;
    }

    public String getDescFechaCargaArchivos() {
        return descFechaCargaArchivos;
    }

    public void setMostrarBtnDescarga(boolean mostrarBtnDescarga) {
        this.mostrarBtnDescarga = mostrarBtnDescarga;
    }

    public boolean isMostrarBtnDescarga() {
        return mostrarBtnDescarga;
    }

    public void setMostrarBtnRechazo(boolean mostrarBtnRechazo) {
        this.mostrarBtnRechazo = mostrarBtnRechazo;
    }

    public boolean isMostrarBtnRechazo() {
        return mostrarBtnRechazo;
    }

    public void setDescargarFundamento(boolean descargarFundamento) {
        this.descargarFundamento = descargarFundamento;
    }

    public boolean isDescargarFundamento() {
        return descargarFundamento;
    }

    public void setDescargarFactura(boolean descargarFactura) {
        this.descargarFactura = descargarFactura;
    }

    public boolean isDescargarFactura() {
        return descargarFactura;
    }

    public void setEstadoDocumento(int estadoDocumento) {
        this.estadoDocumento = estadoDocumento;
    }

    public int getEstadoDocumento() {
        return estadoDocumento;
    }

    public void setNombreEntidadFederativa(String nombreEntidadFederativa) {
        this.nombreEntidadFederativa = nombreEntidadFederativa;
    }

    public String getNombreEntidadFederativa() {
        return nombreEntidadFederativa;
    }
}
