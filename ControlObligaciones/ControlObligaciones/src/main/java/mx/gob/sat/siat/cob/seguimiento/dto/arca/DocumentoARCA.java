/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.arca;

import java.util.Date;

/**
 *
 * @author Juan
 */
public class DocumentoARCA {

    private String boId;
    private String numControlPadre;
    private String id;
    private int idTipoDocumento;
    private int idMedioEnvio;
    private int idEstadoDocumento;
    private int idDocumentoPlantilla;
    private String idAlsc;
    private String descripcionContribuyente;
    private String rfc;
    private String curp;
    private String descripcionDireccion;
    private int crh;
    private String resolucion;
    private Integer idResolucion;
    private String importe;
    private String codPostal;
    private String idTipoPersona;
    private String nombreFuncionario;
    private String descripcionFuncionario;
    private String infromacionQR;
    private int idFirmaDigitalAplicable;
    private Long idProceso;
    private Long idSubProceso;
    private Long idLote;
    private Direccion direccion;
    private Contribuyente contribuyente;
    private Date fechaDocumento;
    private Date fechaEnvioARCA;
    private int idEstatus;
    private String idMotivo;
    private String creditoSIR;
    private int idEntidadFederativa;
    private int idDelegacionMunicipio;
    private String mensajeErrorARCA;
    private Long idVigilancia;
    private String fechaRegistroARCA;
    private String firmaDigital;
    private String cadenaOriginal;

    /**
     *
     */
    public DocumentoARCA() {
    }

    /**
     *
     * @return
     */
    public String getBoId() {
        return boId;
    }

    /**
     *
     * @param boId
     */
    public void setBoId(String boId) {
        this.boId = boId;
    }

    /**
     *
     * @return
     */
    public String getNumControlPadre() {
        return numControlPadre;
    }

    /**
     *
     * @param numControlPadre
     */
    public void setNumControlPadre(String numControlPadre) {
        this.numControlPadre = numControlPadre;
    }

    /**
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public int getIdTipoDocumento() {
        return idTipoDocumento;
    }

    /**
     *
     * @param idTipoDocumento
     */
    public void setIdTipoDocumento(int idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    /**
     *
     * @return
     */
    public int getIdMedioEnvio() {
        return idMedioEnvio;
    }

    /**
     *
     * @param idMedioEnvio
     */
    public void setIdMedioEnvio(int idMedioEnvio) {
        this.idMedioEnvio = idMedioEnvio;
    }

    /**
     *
     * @return
     */
    public int getIdEstadoDocumento() {
        return idEstadoDocumento;
    }

    /**
     *
     * @param idEstadoDocumento
     */
    public void setIdEstadoDocumento(int idEstadoDocumento) {
        this.idEstadoDocumento = idEstadoDocumento;
    }

    /**
     *
     * @return
     */
    public int getIdDocumentoPlantilla() {
        return idDocumentoPlantilla;
    }

    /**
     *
     * @param idDocumentoPlantilla
     */
    public void setIdDocumentoPlantilla(int idDocumentoPlantilla) {
        this.idDocumentoPlantilla = idDocumentoPlantilla;
    }

    /**
     *
     * @return
     */
    public String getIdAlsc() {
        return idAlsc;
    }

    /**
     *
     * @param idAlsc
     */
    public void setIdAlsc(String idAlsc) {
        this.idAlsc = idAlsc;
    }

    /**
     *
     * @return
     */
    public String getDescripcionContribuyente() {
        return descripcionContribuyente;
    }

    /**
     *
     * @param descripcionContribuyente
     */
    public void setDescripcionContribuyente(String descripcionContribuyente) {
        this.descripcionContribuyente = descripcionContribuyente;
    }

    /**
     *
     * @return
     */
    public String getRfc() {
        return rfc;
    }

    /**
     *
     * @param rfc
     */
    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    /**
     *
     * @return
     */
    public String getCurp() {
        return curp;
    }

    /**
     *
     * @param curp
     */
    public void setCurp(String curp) {
        this.curp = curp;
    }

    /**
     *
     * @return
     */
    public String getDescripcionDireccion() {
        return descripcionDireccion;
    }

    /**
     *
     * @param curp
     */
    public void setDescripcionDireccion(String descripcionDireccion) {
        this.descripcionDireccion = descripcionDireccion;
    }

    /**
     *
     * @return
     */
    public int getCrh() {
        return crh;
    }

    /**
     *
     * @param crh
     */
    public void setCrh(int crh) {
        this.crh = crh;
    }

    /**
     *
     * @return
     */
    public String getResolucion() {
        return resolucion;
    }

    /**
     *
     * @param resolucion
     */
    public void setResolucion(String resolucion) {
        this.resolucion = resolucion;
    }

    /**
     *
     * @return
     */
    public Integer getIdResolucion() {
        return idResolucion;
    }

    /**
     *
     * @param idResolucion
     */
    public void setIdResolucion(Integer idResolucion) {
        this.idResolucion = idResolucion;
    }

    /**
     *
     * @return
     */
    public String getImporte() {
        return importe;
    }

    /**
     *
     * @param importe
     */
    public void setImporte(String importe) {
        this.importe = importe;
    }

    /**
     *
     * @return
     */
    public String getCodPostal() {
        return codPostal;
    }

    /**
     *
     * @param codPostal
     */
    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }

    /**
     *
     * @return
     */
    public String getIdTipoPersona() {
        return idTipoPersona;
    }

    /**
     *
     * @param idTipoPersona
     */
    public void setIdTipoPersona(String idTipoPersona) {
        this.idTipoPersona = idTipoPersona;
    }

    /**
     *
     * @return
     */
    public String getInfromacionQR() {
        return infromacionQR;
    }

    /**
     *
     * @param infromacionQR
     */
    public void setInfromacionQR(String infromacionQR) {
        this.infromacionQR = infromacionQR;
    }

    /**
     *
     * @return
     */
    public int getIdFirmaDigitalAplicable() {
        return idFirmaDigitalAplicable;
    }

    /**
     *
     * @param idFirmaDigitalAplicable
     */
    public void setIdFirmaDigitalAplicable(int idFirmaDigitalAplicable) {
        this.idFirmaDigitalAplicable = idFirmaDigitalAplicable;
    }

    /**
     *
     * @return
     */
    public Long getIdLote() {
        return idLote;
    }

    /**
     *
     * @param idLote
     */
    public void setIdLote(Long idLote) {
        this.idLote = idLote;
    }

    /**
     *
     * @return
     */
    public Long getIdProceso() {
        return idProceso;
    }

    /**
     *
     * @param idProceso
     */
    public void setIdProceso(Long idProceso) {
        this.idProceso = idProceso;
    }

    /**
     *
     * @return
     */
    public Long getIdSubProceso() {
        return idSubProceso;
    }

    /**
     *
     * @param idSubProceso
     */
    public void setIdSubProceso(Long idSubProceso) {
        this.idSubProceso = idSubProceso;
    }

    /**
     *
     * @return
     */
    public String getDescripcionFuncionario() {
        return descripcionFuncionario;
    }

    /**
     *
     * @param descripcionFuncionario
     */
    public void setDescripcionFuncionario(String descripcionFuncionario) {
        this.descripcionFuncionario = descripcionFuncionario;
    }

    /**
     *
     * @return
     */
    public String getNombreFuncionario() {
        return nombreFuncionario;
    }

    /**
     *
     * @param nombreFuncionario
     */
    public void setNombreFuncionario(String nombreFuncionario) {
        this.nombreFuncionario = nombreFuncionario;
    }

    /**
     *
     * @return
     */
    public Direccion getDireccion() {
        return direccion;
    }

    /**
     *
     * @param direccion
     */
    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    /**
     *
     * @return
     */
    public Contribuyente getContribuyente() {
        return contribuyente;
    }

    /**
     *
     * @param contribuyente
     */
    public void setContribuyente(Contribuyente contribuyente) {
        this.contribuyente = contribuyente;
    }

    /**
     *
     * @return
     */
    public Date getFechaDocumento() {
        if (fechaDocumento != null) {
            return (Date) fechaDocumento.clone();
        }
        return null;
    }

    /**
     *
     * @param fechaDocumento
     */
    public void setFechaDocumento(Date fechaDocumento) {
        if (fechaDocumento != null) {
            this.fechaDocumento = (Date) fechaDocumento.clone();
        }
    }

    /**
     *
     * @return
     */
    public Date getFechaEnvioARCA() {
        if (fechaEnvioARCA != null) {
            return (Date) fechaEnvioARCA.clone();
        }
        return null;
    }

    /**
     *
     * @param fechaEnvioARCA
     */
    public void setFechaEnvioARCA(Date fechaEnvioARCA) {
        if (fechaEnvioARCA != null) {
            this.fechaEnvioARCA = (Date) fechaEnvioARCA.clone();
        }
    }

    /**
     *
     * @return
     */
    public int getIdEstatus() {
        return idEstatus;
    }

    /**
     *
     * @param idEstatus
     */
    public void setIdEstatus(int idEstatus) {
        this.idEstatus = idEstatus;
    }

    /**
     *
     * @return
     */
    public String getIdMotivo() {
        return idMotivo;
    }

    /**
     *
     * @param idMotivo
     */
    public void setIdMotivo(String idMotivo) {
        this.idMotivo = idMotivo;
    }

    /**
     *
     * @return
     */
    public String getCreditoSIR() {
        return creditoSIR;
    }

    /**
     *
     * @param creditoSIR
     */
    public void setCreditoSIR(String creditoSIR) {
        this.creditoSIR = creditoSIR;
    }

    /**
     *
     * @return
     */
    public int getIdEntidadFederativa() {
        return idEntidadFederativa;
    }

    /**
     *
     * @param idEntidadFederativa
     */
    public void setIdEntidadFederativa(int idEntidadFederativa) {
        this.idEntidadFederativa = idEntidadFederativa;
    }

    /**
     *
     * @return
     */
    public int getIdDelegacionMunicipio() {
        return idDelegacionMunicipio;
    }

    /**
     *
     * @param idDelegacionMunicipio
     */
    public void setIdDelegacionMunicipio(int idDelegacionMunicipio) {
        this.idDelegacionMunicipio = idDelegacionMunicipio;
    }

    /**
     *
     * @return
     */
    public String getMensajeErrorARCA() {
        return mensajeErrorARCA;
    }

    /**
     *
     * @param mensajeErrorARCA
     */
    public void setMensajeErrorARCA(String mensajeErrorARCA) {
        this.mensajeErrorARCA = mensajeErrorARCA;
    }

    /**
     *
     * @return
     */
    public Long getIdVigilancia() {
        return idVigilancia;
    }

    /**
     *
     * @param idVigilancia
     */
    public void setIdVigilancia(Long idVigilancia) {
        this.idVigilancia = idVigilancia;
    }

    /**
     *
     * @return
     */
    public String getFechaRegistroARCA() {
        return fechaRegistroARCA;
    }

    /**
     *
     * @param fechaRegistroARCA
     */
    public void setFechaRegistroARCA(String fechaRegistroARCA) {
        this.fechaRegistroARCA = fechaRegistroARCA;
    }

    /**
     *
     * @return
     */
    public String getCadenaOriginal() {
        return cadenaOriginal;
    }

    /**
     *
     * @param cadenaOriginal
     */
    public void setCadenaOriginal(String cadenaOriginal) {
        this.cadenaOriginal = cadenaOriginal;
    }

    /**
     *
     * @return
     */
    public String getFirmaDigital() {
        return firmaDigital;
    }

    /**
     *
     * @param firmaDigital
     */
    public void setFirmaDigital(String firmaDigital) {
        this.firmaDigital = firmaDigital;
    }
}