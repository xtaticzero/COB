package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author root
 */
public class PlantillaDocumento implements Serializable {

    @SuppressWarnings("compatibility:-6444827574340174468")
    private static final long serialVersionUID = 1L;
    private String descripcion;
    private String tipoDocumento;
    private Integer idTipoDocumento;
    private String descripcionTipoDoc;
    private String etapaVigilancia;
    private Integer idEtapaVigilancia;
    private String descripcionEtapaVig;
    private Boolean esPlantilla;
    private Integer blnPlantillaDIOT;
    private String descDIOT;
    private Date fechaInicio;
    private Date fechaFin;
    private String rutaArchivo;
    private String nombreArchivo;
    private Integer idPlantilla;
    private String file;
    private String descMedioEnvio;
    private Integer idMedioEnvio;
    private String descTipoFirma;
    private Integer idTipoFirma;
    private String descModalidadDocumento;
    private Integer idModalidadDocumento;
    private Integer idPlantillaArca;
    private String idTipoMotivo;
    private Integer idTipoDias;
    private Integer nivelEmision;
    private String descNivelEmision;
    private Integer idCargoAdministrativo;
    private String descCargoAdmin;

    /**
     *
     */
    public PlantillaDocumento() {
        super();
    }

    /**
     *
     * @return
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     *
     * @param descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     *
     * @return
     */
    public Boolean isEsPlantilla() {
        return esPlantilla;
    }

    /**
     *
     * @param esPlantilla
     */
    public void setEsPlantilla(Boolean esPlantilla) {
        this.esPlantilla = esPlantilla;
    }

    /**
     *
     * @return
     */
    public Date getFechaInicio() {
        if(fechaInicio!=null){
        return (Date) fechaInicio.clone();
        }
        return null;
    }

    /**
     *
     * @param fechaInicio
     */
    public void setFechaInicio(Date fechaInicio) {
        if (fechaInicio != null) {
            this.fechaInicio = (Date) fechaInicio.clone();
        }
    }

    /**
     *
     * @return
     */
    public Date getFechaFin() {
        if(fechaFin!=null){
        return (Date) fechaFin.clone();
        }
        return null;
    }

    /**
     *
     * @param fechaFin
     */
    public void setFechaFin(Date fechaFin) {
        if (fechaFin != null) {
            this.fechaFin = (Date) fechaFin.clone();
        }
    }

    /**
     *
     * @return
     */
    public String getRutaArchivo() {
        return rutaArchivo;
    }

    /**
     *
     * @param rutaArchivo
     */
    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
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

    /**
     *
     * @param etapaVigilancia
     */
    public void setEtapaVigilancia(String etapaVigilancia) {
        this.etapaVigilancia = etapaVigilancia;
    }

    /**
     *
     * @return
     */
    public String getEtapaVigilancia() {
        return etapaVigilancia;
    }

    /**
     *
     * @param descripcionTipoDoc
     */
    public void setDescripcionTipoDoc(String descripcionTipoDoc) {
        this.descripcionTipoDoc = descripcionTipoDoc;
    }

    /**
     *
     * @return
     */
    public String getDescripcionTipoDoc() {
        return descripcionTipoDoc;
    }

    /**
     *
     * @param descripcionEtapaVig
     */
    public void setDescripcionEtapaVig(String descripcionEtapaVig) {
        this.descripcionEtapaVig = descripcionEtapaVig;
    }

    /**
     *
     * @return
     */
    public String getDescripcionEtapaVig() {
        return descripcionEtapaVig;
    }

    /**
     *
     * @param nombreArchivo
     */
    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    /**
     *
     * @return
     */
    public String getNombreArchivo() {
        return nombreArchivo;
    }

    /**
     *
     * @param idPlantilla
     */
    public void setIdPlantilla(Integer idPlantilla) {
        this.idPlantilla = idPlantilla;
    }

    /**
     *
     * @return
     */
    public Integer getIdPlantilla() {
        return idPlantilla;
    }

    /**
     *
     * @param file
     */
    public void setFile(String file) {
        this.file = file;
    }

    /**
     *
     * @return
     */
    public String getFile() {
        return file;
    }

    /**
     *
     * @param descMedioEnvio
     */
    public void setDescMedioEnvio(String descMedioEnvio) {
        this.descMedioEnvio = descMedioEnvio;
    }

    /**
     *
     * @return
     */
    public String getDescMedioEnvio() {
        return descMedioEnvio;
    }

    /**
     *
     * @param descTipoFirma
     */
    public void setDescTipoFirma(String descTipoFirma) {
        this.descTipoFirma = descTipoFirma;
    }

    /**
     *
     * @return
     */
    public String getDescTipoFirma() {
        return descTipoFirma;
    }

    /**
     *
     * @param descModalidadDocumento
     */
    public void setDescModalidadDocumento(String descModalidadDocumento) {
        this.descModalidadDocumento = descModalidadDocumento;
    }

    /**
     *
     * @return
     */
    public String getDescModalidadDocumento() {
        return descModalidadDocumento;
    }

    /**
     *
     * @param idMedioEnvio
     */
    public void setIdMedioEnvio(Integer idMedioEnvio) {
        this.idMedioEnvio = idMedioEnvio;
    }

    /**
     *
     * @return
     */
    public Integer getIdMedioEnvio() {
        return idMedioEnvio;
    }

    /**
     *
     * @param idTipoFirma
     */
    public void setIdTipoFirma(Integer idTipoFirma) {
        this.idTipoFirma = idTipoFirma;
    }

    /**
     *
     * @return
     */
    public Integer getIdTipoFirma() {
        return idTipoFirma;
    }

    /**
     *
     * @param idModalidadDocumento
     */
    public void setIdModalidadDocumento(Integer idModalidadDocumento) {
        this.idModalidadDocumento = idModalidadDocumento;
    }

    /**
     *
     * @return
     */
    public Integer getIdModalidadDocumento() {
        return idModalidadDocumento;
    }

    /**
     *
     * @param idTipoDocumento
     */
    public void setIdTipoDocumento(Integer idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    /**
     *
     * @return
     */
    public Integer getIdTipoDocumento() {
        return idTipoDocumento;
    }

    /**
     *
     * @param idEtapaVigilancia
     */
    public void setIdEtapaVigilancia(Integer idEtapaVigilancia) {
        this.idEtapaVigilancia = idEtapaVigilancia;
    }

    /**
     *
     * @return
     */
    public Integer getIdEtapaVigilancia() {
        return idEtapaVigilancia;
    }

    /**
     *
     * @param blnPlantillaDIOT
     */
    public void setBlnPlantillaDIOT(Integer blnPlantillaDIOT) {
        this.blnPlantillaDIOT = blnPlantillaDIOT;
    }

    /**
     *
     * @return
     */
    public Integer getBlnPlantillaDIOT() {
        return blnPlantillaDIOT;
    }

    /**
     *
     * @param idPlantillaArca
     */
    public void setIdPlantillaArca(Integer idPlantillaArca) {
        this.idPlantillaArca = idPlantillaArca;
    }

    /**
     *
     * @return
     */
    public Integer getIdPlantillaArca() {
        return idPlantillaArca;
    }

    /**
     *
     * @param idTipoMotivo
     */
    public void setIdTipoMotivo(String idTipoMotivo) {
        this.idTipoMotivo = idTipoMotivo;
    }

    /**
     *
     * @return
     */
    public String getIdTipoMotivo() {
        return idTipoMotivo;
    }

    /**
     *
     * @param idTipoDias
     */
    public void setIdTipoDias(Integer idTipoDias) {
        this.idTipoDias = idTipoDias;
    }

    /**
     *
     * @return
     */
    public Integer getIdTipoDias() {
        return idTipoDias;
    }

    /**
     *
     * @param descDIOT
     */
    public void setDescDIOT(String descDIOT) {
        this.descDIOT = descDIOT;
    }

    /**
     *
     * @return
     */
    public String getDescDIOT() {
        return descDIOT;
    }

    public void setNivelEmision(Integer nivelEmision) {
        this.nivelEmision = nivelEmision;
    }

    public Integer getNivelEmision() {
        return nivelEmision;
    }

    public void setDescNivelEmision(String descNivelEmision) {
        this.descNivelEmision = descNivelEmision;
    }

    public String getDescNivelEmision() {
        return descNivelEmision;
    }

    public void setIdCargoAdministrativo(Integer idCargoAdministrativo) {
        this.idCargoAdministrativo = idCargoAdministrativo;
    }

    public Integer getIdCargoAdministrativo() {
        return idCargoAdministrativo;
    }

    public void setDescCargoAdmin(String descCargoAdmin) {
        this.descCargoAdmin = descCargoAdmin;
    }

    public String getDescCargoAdmin() {
        return descCargoAdmin;
    }
}
