package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;

import java.util.Date;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoMedioEnvioEnum;


public class Vigilancia implements Serializable {

    private Long idVigilancia;
    private Long idMovimiento;
    private Date fechaCargaArchivos;
    private int idTipoDocumento;
    private String rfcUsuario;
    private String idAdmonGralUsuario;
    private String idAdmonLocalUsuario;
    private String nombreUsuario;
    private String apellidoPaternoUsuario;
    private String apellidoMaternoUsuario;
    private String numEmpleadoUsuario;
    private String ipUsuario;
    private int idTipoMedio;
    private int idTipoFirma;
    private int idEstadoVigilancia;
    private String descripcionVigilancia;

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
    public Long getIdVigilancia() {
        return idVigilancia;
    }

    /**
     *
     * @param idMovimiento
     */
    public void setIdMovimiento(Long idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    /**
     *
     * @return
     */
    public Long getIdMovimiento() {
        return idMovimiento;
    }

    /**
     *
     * @param fechaCargaArchivos
     */
    public void setFechaCargaArchivos(Date fechaCargaArchivos) {
        if (fechaCargaArchivos != null) {
            this.fechaCargaArchivos = new Date(fechaCargaArchivos.getTime());
        }
    }

    /**
     *
     * @return
     */
    public Date getFechaCargaArchivos() {
        if (fechaCargaArchivos != null) {
            return (Date) fechaCargaArchivos.clone();
        } else {
            return null;
        }
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
    public int getIdTipoDocumento() {
        return idTipoDocumento;
    }

    /**
     *
     * @param rfcUsuario
     */
    public void setRfcUsuario(String rfcUsuario) {
        this.rfcUsuario = rfcUsuario;
    }

    /**
     *
     * @return
     */
    public String getRfcUsuario() {
        return rfcUsuario;
    }

    /**
     *
     * @param idAdmonGralUsuario
     */
    public void setIdAdmonGralUsuario(String idAdmonGralUsuario) {
        this.idAdmonGralUsuario = idAdmonGralUsuario;
    }

    /**
     *
     * @return
     */
    public String getIdAdmonGralUsuario() {
        return idAdmonGralUsuario;
    }

    /**
     *
     * @param idAdmonLocalUsuario
     */
    public void setIdAdmonLocalUsuario(String idAdmonLocalUsuario) {
        this.idAdmonLocalUsuario = idAdmonLocalUsuario;
    }

    /**
     *
     * @return
     */
    public String getIdAdmonLocalUsuario() {
        return idAdmonLocalUsuario;
    }

    /**
     *
     * @param nombreUsuario
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     *
     * @return
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     *
     * @param apellidoPaternoUsuario
     */
    public void setApellidoPaternoUsuario(String apellidoPaternoUsuario) {
        this.apellidoPaternoUsuario = apellidoPaternoUsuario;
    }

    /**
     *
     * @return
     */
    public String getApellidoPaternoUsuario() {
        return apellidoPaternoUsuario;
    }

    /**
     *
     * @param apellidoMaternoUsuario
     */
    public void setApellidoMaternoUsuario(String apellidoMaternoUsuario) {
        this.apellidoMaternoUsuario = apellidoMaternoUsuario;
    }

    /**
     *
     * @return
     */
    public String getApellidoMaternoUsuario() {
        return apellidoMaternoUsuario;
    }

    /**
     *
     * @param numEmpleadoUsuario
     */
    public void setNumEmpleadoUsuario(String numEmpleadoUsuario) {
        this.numEmpleadoUsuario = numEmpleadoUsuario;
    }

    /**
     *
     * @return
     */
    public String getNumEmpleadoUsuario() {
        return numEmpleadoUsuario;
    }

    /**
     *
     * @param ipUsuario
     */
    public void setIpUsuario(String ipUsuario) {
        this.ipUsuario = ipUsuario;
    }

    /**
     *
     * @return
     */
    public String getIpUsuario() {
        return ipUsuario;
    }

    /**
     *
     * @param idTipoMedio
     */
    public void setIdTipoMedio(int idTipoMedio) {
        this.idTipoMedio = idTipoMedio;
    }

    /**
     *
     * @return
     */
    public int getIdTipoMedio() {
        return idTipoMedio;
    }

    /**
     *
     * @param idTipoFirma
     */
    public void setIdTipoFirma(int idTipoFirma) {
        this.idTipoFirma = idTipoFirma;
    }

    /**
     *
     * @return
     */
    public int getIdTipoFirma() {
        return idTipoFirma;
    }

    /**
     *
     * @param idEstadoVigilancia
     */
    public void setIdEstadoVigilancia(int idEstadoVigilancia) {
        this.idEstadoVigilancia = idEstadoVigilancia;
    }

    /**
     *
     * @return
     */
    public int getIdEstadoVigilancia() {
        return idEstadoVigilancia;
    }

    /**
     *
     * @return
     */
    public TipoDocumentoEnum getIdTipoDocumentoEnum() {
        TipoDocumentoEnum tipoDocumentoEnum = null;
        for (TipoDocumentoEnum t : TipoDocumentoEnum.values()) {
            if (idTipoDocumento == t.getValor()) {
                tipoDocumentoEnum = t;
            }
        }
        return tipoDocumentoEnum;
    }
    
    public TipoMedioEnvioEnum getIdTipoMedioEnum() {
        TipoMedioEnvioEnum tipoMedioEnvioEnum = null;
        for (TipoMedioEnvioEnum t : TipoMedioEnvioEnum.values()) {
            if (idTipoMedio == t.getValor()) {
                tipoMedioEnvioEnum = t;
            }
        }
        return tipoMedioEnvioEnum;
    }

    /**
     *
     * @return
     */
    public String getDescripcionVigilancia() {
        return descripcionVigilancia;
    }

    /**
     *
     * @param descripcionVigilancia
     */
    public void setDescripcionVigilancia(String descripcionVigilancia) {
        this.descripcionVigilancia = descripcionVigilancia;
    }
}
