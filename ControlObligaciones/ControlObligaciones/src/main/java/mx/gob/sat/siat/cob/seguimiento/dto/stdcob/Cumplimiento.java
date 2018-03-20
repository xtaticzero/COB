package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;
import java.util.Date;

public class Cumplimiento implements Serializable {

    @SuppressWarnings("compatibility:4936464945693471001")
    private static final long serialVersionUID = 1L;
    private String bOID;
    private String claveICEP;
    private String identificadorCumplimiento;
    private Double importePagar;
    private Date fechaPresentacion;
    private Integer estadoVigilable;
    private String tipoDeclaracion;
    private Date fechaMantenimiento;

    /**
     *
     */
    public Cumplimiento() {
    }

    /**
     * @return the bOID
     */
    public String getbOID() {
        return bOID;
    }

    /**
     * @param bOID the bOID to set
     */
    public void setbOID(String bOID) {
        this.bOID = bOID;
    }

    /**
     * @return the claveICEP
     */
    public String getClaveICEP() {
        return claveICEP;
    }

    /**
     * @param claveICEP the claveICEP to set
     */
    public void setClaveICEP(String claveICEP) {
        this.claveICEP = claveICEP;
    }

    /**
     * @return the identificadorCumplimiento
     */
    public String getIdentificadorCumplimiento() {
        return identificadorCumplimiento;
    }

    /**
     * @param identificadorCumplimiento the identificadorCumplimiento to set
     */
    public void setIdentificadorCumplimiento(String identificadorCumplimiento) {
        this.identificadorCumplimiento = identificadorCumplimiento;
    }

    /**
     * @return the importeAPagar
     */
    public Double getImportePagar() {
        return importePagar;
    }

    /**
     * @param importeAPagar the importeAPagar to set
     */
    public void setImportePagar(Double importePagar) {
        this.importePagar = importePagar;
    }

    /**
     * @return the fechaPresentacion
     */
    public Date getFechaPresentacion() {
        if (fechaPresentacion != null) {
            return (Date) fechaPresentacion.clone();
        } else {
            return null;
        }

    }

    /**
     * @param fechaPresentacion the fechaPresentacion to set
     */
    public void setFechaPresentacion(Date fechaPresentacion) {
        if (fechaPresentacion != null) {
            this.fechaPresentacion = (Date) fechaPresentacion.clone();
        }
    }

    public Integer getEstadoVigilable() {
        return estadoVigilable;
    }

    public void setEstadoVigilable(Integer estadoVigilable) {
        this.estadoVigilable = estadoVigilable;
    }

    /**
     * @return the tipoDeclaracion
     */
    public String getTipoDeclaracion() {
        return tipoDeclaracion;
    }

    /**
     * @param tipoDeclaracion the tipoDeclaracion to set
     */
    public void setTipoDeclaracion(String tipoDeclaracion) {
        this.tipoDeclaracion = tipoDeclaracion;
    }

    public Date getFechaMantenimiento() {
        if (fechaMantenimiento != null) {
            return (Date) fechaMantenimiento.clone();
        } else {
            return null;
        }
    }

    public void setFechaMantenimiento(Date fechaMantenimiento) {
        if (fechaMantenimiento != null) {
            this.fechaMantenimiento = (Date) fechaMantenimiento.clone();
        }
    }
}
