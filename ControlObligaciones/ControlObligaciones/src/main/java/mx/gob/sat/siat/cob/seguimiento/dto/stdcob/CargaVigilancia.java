package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

public class CargaVigilancia implements Serializable {

    private TipoDocumento tipoDoc = new TipoDocumento();
    private MedioEnvio medio = new MedioEnvio();
    private TipoFirma firma = new TipoFirma();
    private long idMovimiento;
    private AccesoUsr usuario;
    private EstadoVigilancia edoVigilancia;
    private List<? extends DetalleCarga> detalleCarga;
    private Date fechaCarga;
    private int idDescripcionVigilancia;
    private Date fechaCorte;
    private int idNivelEmision;
    private int idCargoAdmtvo;

    /**
     *
     * @param tipoDoc
     */
    public void setTipoDoc(TipoDocumento tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    /**
     *
     * @return
     */
    public TipoDocumento getTipoDoc() {
        return tipoDoc;
    }

    /**
     *
     * @param medio
     */
    public void setMedio(MedioEnvio medio) {
        this.medio = medio;
    }

    /**
     *
     * @return
     */
    public MedioEnvio getMedio() {
        return medio;
    }

    /**
     *
     * @param firma
     */
    public void setFirma(TipoFirma firma) {
        this.firma = firma;
    }

    /**
     *
     * @return
     */
    public TipoFirma getFirma() {
        return firma;
    }

    /**
     *
     * @param usuario
     */
    public void setUsuario(AccesoUsr usuario) {
        this.usuario = usuario;
    }

    /**
     *
     * @return
     */
    public AccesoUsr getUsuario() {
        return usuario;
    }

    /**
     *
     * @param edoVigilancia
     */
    public void setEdoVigilancia(EstadoVigilancia edoVigilancia) {
        this.edoVigilancia = edoVigilancia;
    }

    /**
     *
     * @return
     */
    public EstadoVigilancia getEdoVigilancia() {
        return edoVigilancia;
    }

    /**
     *
     * @param detalleCarga
     */
    public void setDetalleCarga(List<? extends DetalleCarga> detalleCarga) {
        this.detalleCarga = detalleCarga;
    }

    /**
     *
     * @return
     */
    public List<? extends DetalleCarga> getDetalleCarga() {
        return detalleCarga;
    }

    /**
     *
     * @param fechaCarga
     */
    public void setFechaCarga(Date fechaCarga) {
        if (fechaCarga != null) {
            this.fechaCarga = new Date(fechaCarga.getTime());
        }
    }

    /**
     *
     * @return
     */
    public Date getFechaCarga() {
        if (fechaCarga != null) {
            return (Date) fechaCarga.clone();
        } else {
            return null;
        }
    }

    /**
     *
     * @param idMovimiento
     */
    public void setIdMovimiento(long idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    /**
     *
     * @return
     */
    public long getIdMovimiento() {
        return idMovimiento;
    }

    /**
     *
     * @return
     */
    public Date getFechaCorte() {
        if (fechaCorte != null) {
            return (Date) fechaCorte.clone();
        } else {
            return null;
        }
    }

    /**
     *
     * @param fechaCorte
     */
    public void setFechaCorte(Date fechaCorte) {
        if (fechaCorte != null) {
            this.fechaCorte = new Date(fechaCorte.getTime());
        }
    }

    /**
     *
     * @return
     */
    public int getIdDescripcionVigilancia() {
        return idDescripcionVigilancia;
    }

    /**
     *
     * @param idDescripcionVigilancia
     */
    public void setIdDescripcionVigilancia(int idDescripcionVigilancia) {
        this.idDescripcionVigilancia = idDescripcionVigilancia;
    }

    /**
     *
     * @return
     */
    public int getIdCargoAdmtvo() {
        return idCargoAdmtvo;
    }

    /**
     *
     * @param idCargoAdmtvo
     */
    public void setIdCargoAdmtvo(int idCargoAdmtvo) {
        this.idCargoAdmtvo = idCargoAdmtvo;
    }

    /**
     *
     * @return
     */
    public int getIdNivelEmision() {
        return idNivelEmision;
    }

    /**
     *
     * @param idNivelEmision
     */
    public void setIdNivelEmision(int idNivelEmision) {
        this.idNivelEmision = idNivelEmision;
    }
}
