package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EtapaVigilanciaEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.IndicadorCumplimientoEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.SituacionIcepEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoDocumentoEnum;

public class Documento implements Serializable, Cloneable {

    private String numeroControl = "";
    private String numeroControlPadre = "";
    private Date fechaNotificacion;
    private Date fechaImpresion;
    private Date dateNoTrabajado;
    private String rfc = "";
    private BigInteger boid;
    private Date fechaVencimiento;
    private int idEtapaVigilancia;
    private Date fechaNoLocalizadoContribuyente;
    private int esUltimoGenerado;
    private int ultimoEstado;
    private Date fechaRegistro;
    private List<DetalleDocumento> detalles;
    private int esUltimoEstado = EstadoDocumentoEnum.PENDIENTE_DE_PROCESAR.getValor();
    private int consideraRenuencia;
    private Vigilancia vigilancia;
    private IndicadorCumplimientoEnum indicadorCumplimiento;
    private String fechaNoTrabajado;
    private String idAdmonLocal;
    private String codigoPostal;
    private int idcrh;
    private int identidadFederativa;
    private String idTipoPersona;
    private boolean vigilable;

    /**
     *
     */
    public Documento() {
        super();
        vigilancia = new Vigilancia();
        detalles = new ArrayList<DetalleDocumento>(0);
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
     * @param numeroControlPadre
     */
    public void setNumeroControlPadre(String numeroControlPadre) {
        this.numeroControlPadre = numeroControlPadre;
    }

    /**
     *
     * @return
     */
    public String getNumeroControlPadre() {
        return numeroControlPadre;
    }

    /**
     *
     * @param fechaNotificacion
     */
    public void setFechaNotificacion(Date fechaNotificacion) {
        if (fechaNotificacion != null) {
            this.fechaNotificacion = new Date(fechaNotificacion.getTime());
        }
    }

    /**
     *
     * @return
     */
    public Date getFechaNotificacion() {
        if (fechaNotificacion != null) {
            return (Date) fechaNotificacion.clone();
        } else {
            return null;
        }
    }

    /**
     *
     * @param fechaImpresion
     */
    public void setFechaImpresion(Date fechaImpresion) {
        if (fechaImpresion != null) {
            this.fechaImpresion = new Date(fechaImpresion.getTime());
        }
    }

    /**
     *
     * @return
     */
    public Date getFechaImpresion() {
        if (fechaImpresion != null) {
            return (Date) fechaImpresion.clone();
        } else {
            return null;
        }
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
    public String getRfc() {
        return rfc;
    }

    /**
     *
     * @param boid
     */
    public void setBoid(String boid) {
        this.boid = new BigInteger(boid);
    }

    /**
     *
     * @return
     */
    public String getBoid() {
        return boid.toString();
    }

    /**
     *
     * @param fechaVencimiento
     */
    public void setFechaVencimiento(Date fechaVencimiento) {
        if (fechaVencimiento != null) {
            this.fechaVencimiento = new Date(fechaVencimiento.getTime());
        }
    }

    /**
     *
     * @return
     */
    public Date getFechaVencimiento() {
        if (fechaVencimiento != null) {
            return (Date) fechaVencimiento.clone();
        } else {
            return null;
        }
    }

    /**
     *
     * @param idEtapaVigilancia
     */
    public void setIdEtapaVigilancia(int idEtapaVigilancia) {
        this.idEtapaVigilancia = idEtapaVigilancia;
    }

    /**
     *
     * @return
     */
    public int getIdEtapaVigilancia() {
        return idEtapaVigilancia;
    }

    /**
     *
     * @param fechaNoLocalizadoContribuyente
     */
    public void setFechaNoLocalizadoContribuyente(Date fechaNoLocalizadoContribuyente) {
        if (fechaNoLocalizadoContribuyente != null) {
            this.fechaNoLocalizadoContribuyente = new Date(fechaNoLocalizadoContribuyente.getTime());
        }

    }

    /**
     *
     * @return
     */
    public Date getFechaNoLocalizadoContribuyente() {
        if (fechaNoLocalizadoContribuyente != null) {
            return (Date) fechaNoLocalizadoContribuyente.clone();
        } else {
            return null;
        }
    }

    /**
     *
     * @param esUltimoGenerado
     */
    public void setEsUltimoGenerado(int esUltimoGenerado) {
        this.esUltimoGenerado = esUltimoGenerado;
    }

    /**
     *
     * @return
     */
    public int getEsUltimoGenerado() {
        return esUltimoGenerado;
    }

    /**
     *
     * @param ultimoEstado
     */
    public void setUltimoEstado(int ultimoEstado) {
        this.ultimoEstado = ultimoEstado;
    }

    /**
     *
     * @return
     */
    public int getUltimoEstado() {
        return ultimoEstado;
    }

    /**
     *
     * @param fechaRegistro
     */
    public void setFechaRegistro(Date fechaRegistro) {
        if (fechaRegistro != null) {
            this.fechaRegistro = new Date(fechaRegistro.getTime());
        }
    }

    /**
     *
     * @return
     */
    public Date getFechaRegistro() {
        if (fechaRegistro != null) {
            return (Date) fechaRegistro.clone();
        } else {
            return null;
        }
    }

    /**
     *
     * @param detalles
     */
    public void setDetalles(List<DetalleDocumento> detalles) {
        this.detalles = detalles;
    }

    /**
     *
     * @return
     */
    public List<DetalleDocumento> getDetalles() {
        return detalles;
    }

    /**
     *
     * @param esUltimoEstado
     */
    public void setEsUltimoEstado(int esUltimoEstado) {
        this.esUltimoEstado = esUltimoEstado;
    }

    /**
     *
     * @return
     */
    public int getEsUltimoEstado() {
        return esUltimoEstado;
    }

    /**
     *
     * @param consideraRenuencia
     */
    public void setConsideraRenuencia(int consideraRenuencia) {
        this.consideraRenuencia = consideraRenuencia;
    }

    /**
     *
     * @return
     */
    public int getConsideraRenuencia() {
        return consideraRenuencia;
    }

    /**
     *
     * @return
     */
    public Vigilancia getVigilancia() {
        return vigilancia;
    }

    /**
     *
     * @param vigilancia
     */
    public void setVigilancia(Vigilancia vigilancia) {
        this.vigilancia = vigilancia;
    }

    /**
     *
     * @return
     */
    public IndicadorCumplimientoEnum getIndicadorCumplimiento() {
        return indicadorCumplimiento;
    }

    /**
     *
     * @param indicadorCumplimiento
     */
    public void setIndicadorCumplimiento(IndicadorCumplimientoEnum indicadorCumplimiento) {
        this.indicadorCumplimiento = indicadorCumplimiento;
    }

    /**
     *
     * @return
     */
    public EstadoDocumentoEnum getEstadoDocumento() {
        for (EstadoDocumentoEnum ed : EstadoDocumentoEnum.values()) {
            if (ed.getValor() == ultimoEstado) {
                return ed;
            }
        }
        return null;
    }

    /**
     *
     * @return
     */
    public String getFechaNoTrabajado() {
        return fechaNoTrabajado;
    }

    /**
     *
     * @param fechaNoTrabajado
     */
    public void setFechaNoTrabajado(String fechaNoTrabajado) {
        this.fechaNoTrabajado = fechaNoTrabajado;
    }

    /**
     *
     * @return
     */
    public TipoDocumentoEnum getTipoDocumento() {
        for (TipoDocumentoEnum tipo : TipoDocumentoEnum.values()) {
            if (tipo.getValor() == this.getVigilancia().getIdTipoDocumento()) {
                return tipo;
            }
        }
        return null;
    }

    /**
     *
     * @return
     */
    public EtapaVigilanciaEnum getEtapaVigilancia() {
        for (EtapaVigilanciaEnum etapa : EtapaVigilanciaEnum.values()) {
            if (etapa.getValor() == idEtapaVigilancia) {
                return etapa;
            }
        }
        return null;
    }

    /**
     *
     * @param dateNoTrabajado
     */
    public void setDateNoTrabajado(Date dateNoTrabajado) {
        if (dateNoTrabajado != null) {
            this.dateNoTrabajado = new Date(dateNoTrabajado.getTime());
        }
    }

    /**
     *
     * @return
     */
    public Date getDateNoTrabajado() {
        if (dateNoTrabajado != null) {
            return (Date) dateNoTrabajado.clone();
        } else {
            return null;
        }
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getIdAdmonLocal() {
        return idAdmonLocal;
    }

    public void setIdAdmonLocal(String idAdmonLocal) {
        this.idAdmonLocal = idAdmonLocal;
    }

    public int getIdcrh() {
        return idcrh;
    }

    public void setIdcrh(int idcrh) {
        this.idcrh = idcrh;
    }

    public int getIdentidadFederativa() {
        return identidadFederativa;
    }

    public void setIdentidadFederativa(int identidadFederativa) {
        this.identidadFederativa = identidadFederativa;
    }

    public String getIdTipoPersona() {
        return idTipoPersona;
    }

    public void setIdTipoPersona(String idTipoPersona) {
        this.idTipoPersona = idTipoPersona;
    }

    public boolean isVigilable() {
        return vigilable;
    }

    public void setVigilable(boolean vigilable) {
        this.vigilable = vigilable;
    }

    @Override
    public Documento clone() throws CloneNotSupportedException {
        return (Documento) super.clone();
    }
    
    /**
     * Metodo que determina si un Documento tiene algun icep cancelado
     * @return 
     */
    public boolean tieneICEPScancelados(){
        for(DetalleDocumento det: this.detalles){
            if(det.getSituacionIcep()==SituacionIcepEnum.CANCELADO_POR_MOVIMIENTOS_PADRON){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return (numeroControlPadre.length() == 0 ? "null" : numeroControlPadre)
                + "," + fechaNotificacion + "," + fechaImpresion
                + "," + rfc + "," + boid + "," + fechaVencimiento
                + "," + idEtapaVigilancia + "," + fechaNoLocalizadoContribuyente
                + "," + vigilancia.getIdVigilancia() + "," + esUltimoGenerado
                + "," + ultimoEstado + "," + consideraRenuencia;
    }
}
