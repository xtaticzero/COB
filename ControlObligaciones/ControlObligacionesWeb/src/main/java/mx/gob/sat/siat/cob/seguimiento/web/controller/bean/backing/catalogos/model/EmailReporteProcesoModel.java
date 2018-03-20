package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.catalogos.model;

import java.io.Serializable;

import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.EmailReporteProceso;

public class EmailReporteProcesoModel implements Serializable {

    private static final long serialVersionUID = -5197691180506547942L;

    private List<EmailReporteProceso> listEmail;
    private List<EmailReporteProceso> listEmailTmp;

    private Long idEmailReporteProceso;
    private String nombreCompleto;
    private String correoElectronico;
    private String correoElectronicoAlterno;
    private Date fechaFin;
    private Date fechaInicio;

    private EmailReporteProceso emailEdit;
    private EmailReporteProceso emailEli;

    private boolean tblVisible = true;
    private boolean nvoVisible = false;
    private boolean edtVisible = false;
    private boolean elmVisible = false;

    public void setListEmail(List<EmailReporteProceso> listEmail) {
        this.listEmail = listEmail;
    }

    public List<EmailReporteProceso> getListEmail() {
        return listEmail;
    }

    public void setListEmailTmp(List<EmailReporteProceso> listEmailTmp) {
        this.listEmailTmp = listEmailTmp;
    }

    public List<EmailReporteProceso> getListEmailTmp() {
        return listEmailTmp;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronicoAlterno(String correoElectronicoAlterno) {
        this.correoElectronicoAlterno = correoElectronicoAlterno;
    }

    public String getCorreoElectronicoAlterno() {
        return correoElectronicoAlterno;
    }

    public void setFechaFin(Date fechaFin) {
        if (fechaFin != null) {
            this.fechaFin = (Date) fechaFin.clone();
        } else {
            this.fechaFin = null;
        }
    }

    public Date getFechaFin() {
        if (fechaFin != null) {
            return (Date) fechaFin.clone();
        }
        return null;
    }

    public Date getFechaInicio() {
        if (fechaInicio != null) {
            return (Date) fechaInicio.clone();
        }
        return null;
    }

    public void setFechaInicio(Date fechaInicio) {
        if (fechaInicio != null) {
            this.fechaInicio = (Date) fechaInicio.clone();
        } else {
            this.fechaInicio = null;
        }
    }

    public void setEmailEdit(EmailReporteProceso emailEdit) {
        this.emailEdit = emailEdit;
    }

    public EmailReporteProceso getEmailEdit() {
        return emailEdit;
    }

    public void setEmailEli(EmailReporteProceso emailEli) {
        this.emailEli = emailEli;
    }

    public EmailReporteProceso getEmailEli() {
        return emailEli;
    }

    public void setTblVisible(boolean tblVisible) {
        this.tblVisible = tblVisible;
    }

    public boolean isTblVisible() {
        return tblVisible;
    }

    public void setNvoVisible(boolean nvoVisible) {
        this.nvoVisible = nvoVisible;
    }

    public boolean isNvoVisible() {
        return nvoVisible;
    }

    public void setEdtVisible(boolean edtVisible) {
        this.edtVisible = edtVisible;
    }

    public boolean isEdtVisible() {
        return edtVisible;
    }

    public void setElmVisible(boolean elmVisible) {
        this.elmVisible = elmVisible;
    }

    public boolean isElmVisible() {
        return elmVisible;
    }

    public void setIdEmailReporteProceso(Long idEmailReporteProceso) {
        this.idEmailReporteProceso = idEmailReporteProceso;
    }

    public Long getIdEmailReporteProceso() {
        return idEmailReporteProceso;
    }
}
