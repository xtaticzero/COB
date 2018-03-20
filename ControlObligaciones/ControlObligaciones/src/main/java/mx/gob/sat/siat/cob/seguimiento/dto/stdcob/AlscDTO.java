package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;

import java.util.Date;


/**
 *
 * @author root
 */
public class AlscDTO implements Serializable {

    private static final long serialVersionUID = -5197691180506547942L;
    private Integer cvAlsc;
    private String alsc;
    private Integer cantidadDocumentos;
    private String cantidadDocumentosStr;
    private Integer cantidadImpresos;
    private String cantidadImpresosStr;
    private Date fechaDeterminacion;
    private String situacionVigilancia;
    private Integer indentidadFederativa;
    private String descripcion;
    private String tipoEnvio;
    private Date fechaDescarga;

    /**
     *
     * @return
     */
    public Integer getCvAlsc() {
        return cvAlsc;
    }

    /**
     *
     * @param cvAlsc
     */
    public void setCvAlsc(Integer cvAlsc) {
        this.cvAlsc = cvAlsc;
    }

    /**
     *
     * @return
     */
    public String getAlsc() {
        return alsc;
    }

    /**
     *
     * @param alsc
     */
    public void setAlsc(String alsc) {
        this.alsc = alsc;
    }

    /**
     *
     * @return
     */
    public Integer getCantidadDocumentos() {
        return cantidadDocumentos;
    }

    /**
     *
     * @param cantidadDocumentos
     */
    public void setCantidadDocumentos(Integer cantidadDocumentos) {
        this.cantidadDocumentos = cantidadDocumentos;
    }

    /**
     *
     * @return
     */
    public Integer getCantidadImpresos() {
        return cantidadImpresos;
    }

    /**
     *
     * @param cantidadImpresos
     */
    public void setCantidadImpresos(Integer cantidadImpresos) {
        this.cantidadImpresos = cantidadImpresos;
    }

    /**
     *
     * @return
     */
    public Date getFechaDeterminacion() {
        return fechaDeterminacion == null ? null : (Date) fechaDeterminacion.clone();
    }

    /**
     *
     * @param fechaDeterminacion
     */
    public void setFechaDeterminacion(Date fechaDeterminacion) {
        if (fechaDeterminacion != null) {
            this.fechaDeterminacion = (Date) fechaDeterminacion.clone();
        }
    }

    /**
     *
     * @return
     */
    public String getSituacionVigilancia() {
        return situacionVigilancia;
    }

    /**
     *
     * @param situacionVigilancia
     */
    public void setSituacionVigilancia(String situacionVigilancia) {
        this.situacionVigilancia = situacionVigilancia;
    }

    /**
     *
     * @return
     */
    public String getCantidadDocumentosStr() {
        return cantidadDocumentosStr;
    }

    /**
     *
     * @param cantidadDocumentosStr
     */
    public void setCantidadDocumentosStr(String cantidadDocumentosStr) {
        this.cantidadDocumentosStr = cantidadDocumentosStr;
    }

    /**
     *
     * @return
     */
    public String getCantidadImpresosStr() {
        return cantidadImpresosStr;
    }

    /**
     *
     * @param cantidadImpresosStr
     */
    public void setCantidadImpresosStr(String cantidadImpresosStr) {
        this.cantidadImpresosStr = cantidadImpresosStr;
    }

    /**
     *
     * @return
     */
    public Integer getIndentidadFederativa() {
        return indentidadFederativa;
    }

    /**
     *
     * @param indentidadFederativa
     */
    public void setIndentidadFederativa(Integer indentidadFederativa) {
        this.indentidadFederativa = indentidadFederativa;
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
    public String getTipoEnvio() {
        return tipoEnvio;
    }

    /**
     *
     * @param tipoEnvio
     */
    public void setTipoEnvio(String tipoEnvio) {
        this.tipoEnvio = tipoEnvio;
    }

    /**
     *
     * @return
     */
    public Date getFechaDescarga() {
        return fechaDescarga == null ? null : (Date) fechaDescarga.clone();
    }

    /**
     *
     * @param fechaDescarga
     */
    public void setFechaDescarga(Date fechaDescarga) {
        if (fechaDescarga != null) {
            this.fechaDescarga = (Date) fechaDescarga.clone();
        }
    }

}
