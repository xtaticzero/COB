package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.catalogos;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.EmailReporteProceso;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.catalogos.EmailReporteProcesoService;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import mx.gob.sat.siat.cob.seguimiento.utils.AbstractBaseMB;
import mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.catalogos.model.EmailReporteProcesoModel;
import mx.gob.sat.siat.exception.AccesoDenegadoException;
import mx.gob.sat.siat.exception.DaoException;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;
import mx.gob.sat.siat.util.MovimientoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author root
 */
@Controller("emailReportEcMB")
@Scope("view")
public class EmailReportEcManagedBean extends AbstractBaseMB {

    @Autowired
    private EmailReporteProcesoService emailReporteProcesoService;
    private EmailReporteProcesoModel emailReporteProcesoModel = new EmailReporteProcesoModel();
    private Long idEmailReporteProceso;
    private String nombreCompletoTmp;
    private String correoElectronicoTmp;
    private String correoElectronicoAlternoTmp;

    @PostConstruct
    public void init() {
        obtenerAccesoUsrEmpleados();
        autorizar(ConstantsCatalogos.IDENTIFICADOR_EMAIL_EC);
        emailReporteProcesoModel.setListEmail(emailReporteProcesoService.todosLosEmailReporteProceso(true));
    }

    public void agrega() {
        boolean resultado = false;
        String expresionRegular = "[\\w\\.-]*[a-zA-Z0-9_]@[\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]";
        if (emailReporteProcesoModel.getCorreoElectronicoAlterno() != null && !emailReporteProcesoModel.getCorreoElectronicoAlterno().equals("")) {
            resultado = Pattern.matches(expresionRegular, emailReporteProcesoModel.getCorreoElectronicoAlterno());
        } else {
            resultado = true;
        }

        if (resultado) {

            EmailReporteProceso emailReporteProceso = new EmailReporteProceso();
            emailReporteProceso.setNombreCompleto(emailReporteProcesoModel.getNombreCompleto());
            emailReporteProceso.setCorreoElectronico(emailReporteProcesoModel.getCorreoElectronico());
            emailReporteProceso.setCorreoElectronicoAlterno(emailReporteProcesoModel.getCorreoElectronicoAlterno());
            Calendar cal = Calendar.getInstance();
            emailReporteProceso.setFechaInicio(cal.getTime());

            try {
                SegbMovimientoDTO dto = MovimientoUtil.addMovimiento(getSession(),
                        ConstantsCatalogos.CVE_SISTEMA,
                        ConstantsCatalogos.IDENTIFICADOR_EMAIL_EC,
                        new Date(), new Date(),
                        ConstantsCatalogos.ALTA_MOV_EMAIL_REPORTEEC,
                        ConstantsCatalogos.ALTA_EMAILREPORTEEC_OBS);
                emailReporteProcesoService.agregaEmailReporteProceso(emailReporteProceso, dto, true);
                emailReporteProcesoModel.setListEmail(emailReporteProcesoService.todosLosEmailReporteProceso(true));
                emailReporteProcesoModel.setListEmailTmp(emailReporteProcesoService.todosLosEmailReporteProceso(true));

                cerrar();
                AbstractBaseMB.msgInfo(ConstantsCatalogos.REGISTROEXITOSO);
            } catch (AccesoDenegadoException e) {
                getLogger().error(e.getMessage());
            } catch (SeguimientoDAOException e) {
                getLogger().error(e.getMessage());
            } catch (DaoException e) {
                getLogger().error(e.getMessage());
            }
        } else {
            AbstractBaseMB.msgError(ConstantsCatalogos.ERROR_VALIDACION_CORREO);

        }

    }

    public void edita() {

        boolean resultado = false;
        String expresionRegular = "[\\w\\.-]*[a-zA-Z0-9_]@[\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]";
        if (emailReporteProcesoModel.getEmailEdit().getCorreoElectronicoAlterno() != null && !emailReporteProcesoModel.getEmailEdit().getCorreoElectronicoAlterno().equals("")) {
            resultado = Pattern.matches(expresionRegular, emailReporteProcesoModel.getEmailEdit().getCorreoElectronicoAlterno());
        } else {
            resultado = true;
        }
        if (resultado) {

            try {
                SegbMovimientoDTO dto = MovimientoUtil.addMovimiento(getSession(),
                        ConstantsCatalogos.CVE_SISTEMA,
                        ConstantsCatalogos.IDENTIFICADOR_EMAIL_EC,
                        new Date(), new Date(),
                        ConstantsCatalogos.MODIFICACION_MOV_EMAIL_REPORTEEC,
                        ConstantsCatalogos.MODIFICACION_EMAILREPORTEEC_OBS);

                emailReporteProcesoService.editaEmailReporteProceso(emailReporteProcesoModel.getEmailEdit(), dto, true);
                emailReporteProcesoModel.setListEmail(emailReporteProcesoService.todosLosEmailReporteProceso(true));
                emailReporteProcesoModel.setListEmailTmp(emailReporteProcesoService.todosLosEmailReporteProceso(true));

                cerrar();
                AbstractBaseMB.msgInfo(ConstantsCatalogos.REGISTROEDITADO);
            } catch (AccesoDenegadoException e) {
                getLogger().error(e.getMessage());
            } catch (SeguimientoDAOException e) {
                getLogger().error(e.getMessage());
            } catch (DaoException e) {
                getLogger().error(e.getMessage());
            }
        } else {
            AbstractBaseMB.msgError(ConstantsCatalogos.ERROR_VALIDACION_CORREO);
        }

    }

    public void elimina() {

        Calendar cal = Calendar.getInstance();
        emailReporteProcesoModel.getEmailEli().setFechaFin(cal.getTime());

        try {
            SegbMovimientoDTO dto = MovimientoUtil.addMovimiento(getSession(),
                    ConstantsCatalogos.CVE_SISTEMA,
                    ConstantsCatalogos.IDENTIFICADOR_EMAIL_EC,
                    new Date(), new Date(),
                    ConstantsCatalogos.BAJA_MOV_EMAIL_REPORTEEC,
                    ConstantsCatalogos.BAJA_EMAILREPORTEEC_OBS);
            emailReporteProcesoService.eliminaEmailReporteProceso(emailReporteProcesoModel.getEmailEli(), dto, true);
            emailReporteProcesoModel.setListEmail(emailReporteProcesoService.todosLosEmailReporteProceso(true));
            emailReporteProcesoModel.setListEmailTmp(emailReporteProcesoService.todosLosEmailReporteProceso(true));

            this.cerrar();
            AbstractBaseMB.msgInfo(ConstantsCatalogos.REGISTROELIMINADO);
        } catch (AccesoDenegadoException e) {
            getLogger().error(e.getMessage());
        } catch (SeguimientoDAOException e) {
            getLogger().error(e.getMessage());
        } catch (DaoException e) {
            getLogger().error(e.getMessage());
        }
    }

    /**
     *
     */
    public void generaExcel() {
        byte[] excel;
        if (emailReporteProcesoModel.getListEmailTmp() != null) {
            excel = emailReporteProcesoService.generaExcel(emailReporteProcesoModel.getListEmailTmp(), true).toByteArray();
        } else {
            excel = emailReporteProcesoService.generaExcel(emailReporteProcesoModel.getListEmail(), true).toByteArray();
        }
        despachaArchivo(excel, ConstantsCatalogos.TYPE_EXCEL, "tabla.xls", ConstantsCatalogos.ERROR_GENERA_EXCEL);
    }

    /**
     *
     */
    public void generaPdf() {
        byte[] pdf;
        if (emailReporteProcesoModel.getListEmailTmp() != null) {
            pdf = emailReporteProcesoService.generaPDF(emailReporteProcesoModel.getListEmailTmp(), true).toByteArray();
        } else {
            pdf = emailReporteProcesoService.generaPDF(emailReporteProcesoModel.getListEmail(), true).toByteArray();
        }
        despachaArchivo(pdf, ConstantsCatalogos.TYPE_PDF, "tabla.pdf", ConstantsCatalogos.ERROR_GENERA_PDF);
    }

    public void abrirNuevo() {
        emailReporteProcesoModel.setTblVisible(false);
        emailReporteProcesoModel.setNvoVisible(true);

    }

    /**
     *
     */
    public void abrirEditar() {

        setIdEmailReporteProceso(emailReporteProcesoModel.getEmailEdit().getIdEmailReporteProceso());
        setNombreCompletoTmp(emailReporteProcesoModel.getEmailEdit().getNombreCompleto());
        setCorreoElectronicoTmp(emailReporteProcesoModel.getEmailEdit().getCorreoElectronico());
        setCorreoElectronicoAlternoTmp(emailReporteProcesoModel.getEmailEdit().getCorreoElectronicoAlterno());

        emailReporteProcesoModel.setTblVisible(false);
        emailReporteProcesoModel.setEdtVisible(true);
    }

    /**
     *
     */
    public void abrirEliminar() {

        emailReporteProcesoModel.setTblVisible(false);
        emailReporteProcesoModel.setElmVisible(true);
    }

    public void cerrar() {
        emailReporteProcesoModel.setTblVisible(true);
        emailReporteProcesoModel.setNvoVisible(false);
        emailReporteProcesoModel.setEdtVisible(false);
        emailReporteProcesoModel.setElmVisible(false);
        clean();
    }

    public void clean() {

        if (getNombreCompletoTmp() != null) {
            emailReporteProcesoModel.getEmailEdit().setNombreCompleto(getNombreCompletoTmp());
        }
        if (getCorreoElectronicoTmp() != null) {
            emailReporteProcesoModel.getEmailEdit().setCorreoElectronico(getCorreoElectronicoTmp());
        }
        if (getCorreoElectronicoAlternoTmp() != null) {
            emailReporteProcesoModel.getEmailEdit().setCorreoElectronicoAlterno(getCorreoElectronicoAlternoTmp());
        }

        emailReporteProcesoModel.setIdEmailReporteProceso(null);
        emailReporteProcesoModel.setNombreCompleto(null);
        emailReporteProcesoModel.setCorreoElectronico(null);
        emailReporteProcesoModel.setCorreoElectronicoAlterno(null);

    }

    public Long getIdEmailReporteProceso() {
        return idEmailReporteProceso;
    }

    public void setIdEmailReporteProceso(Long idEmailReporteProceso) {
        this.idEmailReporteProceso = idEmailReporteProceso;
    }

    public EmailReporteProcesoService getEmailReporteProcesoService() {
        return emailReporteProcesoService;
    }

    public void setEmailReporteProcesoService(EmailReporteProcesoService emailReporteProcesoService) {
        this.emailReporteProcesoService = emailReporteProcesoService;
    }

    public EmailReporteProcesoModel getEmailReporteProcesoModel() {
        return emailReporteProcesoModel;
    }

    public void setEmailReporteProcesoModel(EmailReporteProcesoModel emailReporteProcesoModel) {
        this.emailReporteProcesoModel = emailReporteProcesoModel;
    }

    public void setNombreCompletoTmp(String nombreCompletoTmp) {
        this.nombreCompletoTmp = nombreCompletoTmp;
    }

    public String getNombreCompletoTmp() {
        return nombreCompletoTmp;
    }

    public void setCorreoElectronicoTmp(String correoElectronicoTmp) {
        this.correoElectronicoTmp = correoElectronicoTmp;
    }

    public String getCorreoElectronicoTmp() {
        return correoElectronicoTmp;
    }

    public void setCorreoElectronicoAlternoTmp(String correoElectronicoAlternoTmp) {
        this.correoElectronicoAlternoTmp = correoElectronicoAlternoTmp;
    }

    public String getCorreoElectronicoAlternoTmp() {
        return correoElectronicoAlternoTmp;
    }
}
