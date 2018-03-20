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
@Controller("emailProcsDetMB")
@Scope("view")
public class EmailProcsDetManagedBean extends AbstractBaseMB {

    @Autowired
    private EmailReporteProcesoService emailReporteProcesoService;
    private EmailReporteProcesoModel emailReporteProcesoModel = new EmailReporteProcesoModel();
    private Long idEmailReporteProceso;
    private String nombreCompletoTemp;
    private String correoElectronicoTemp;
    private String correoElectronicoAlternoTemp;

    @PostConstruct
    public void init() {
        obtenerAccesoUsrEmpleados();
        autorizar(ConstantsCatalogos.IDENTIFICADOR_EMAIL_PROCESO_DETENIDO);
        emailReporteProcesoModel.setListEmail(emailReporteProcesoService.todosLosEmailReporteProceso(false));
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
                        ConstantsCatalogos.IDENTIFICADOR_EMAIL_PROCESO_DETENIDO,
                        new Date(), new Date(),
                        ConstantsCatalogos.ALTA_MOV_EMAIL_PROCESO_DETENIDO,
                        ConstantsCatalogos.ALTA_EMAILPROCESODETENIDO_OBS);
                emailReporteProcesoService.agregaEmailReporteProceso(emailReporteProceso, dto, false);
                emailReporteProcesoModel.setListEmail(emailReporteProcesoService.todosLosEmailReporteProceso(false));
                emailReporteProcesoModel.setListEmailTmp(emailReporteProcesoService.todosLosEmailReporteProceso(false));

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
                        ConstantsCatalogos.IDENTIFICADOR_EMAIL_PROCESO_DETENIDO,
                        new Date(), new Date(),
                        ConstantsCatalogos.MODIFICACION_MOV_EMAIL_PROCESO_DETENIDO,
                        ConstantsCatalogos.MODIFICACION_EMAILPROCESODETENIDO_OBS);

                emailReporteProcesoService.editaEmailReporteProceso(emailReporteProcesoModel.getEmailEdit(), dto, false);
                emailReporteProcesoModel.setListEmail(emailReporteProcesoService.todosLosEmailReporteProceso(false));
                emailReporteProcesoModel.setListEmailTmp(emailReporteProcesoService.todosLosEmailReporteProceso(false));

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
                    ConstantsCatalogos.IDENTIFICADOR_EMAIL_PROCESO_DETENIDO,
                    new Date(), new Date(),
                    ConstantsCatalogos.BAJA_MOV_EMAIL_PROCESO_DETENIDO,
                    ConstantsCatalogos.BAJA_EMAILPROCESODETENIDO_OBS);
            emailReporteProcesoService.eliminaEmailReporteProceso(emailReporteProcesoModel.getEmailEli(), dto, false);
            emailReporteProcesoModel.setListEmail(emailReporteProcesoService.todosLosEmailReporteProceso(false));
            emailReporteProcesoModel.setListEmailTmp(emailReporteProcesoService.todosLosEmailReporteProceso(false));

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
            excel = emailReporteProcesoService.generaExcel(emailReporteProcesoModel.getListEmailTmp(), false).toByteArray();
        } else {
            excel = emailReporteProcesoService.generaExcel(emailReporteProcesoModel.getListEmail(), false).toByteArray();
        }
        despachaArchivo(excel, ConstantsCatalogos.TYPE_EXCEL, "tabla.xls", ConstantsCatalogos.ERROR_GENERA_EXCEL);
    }

    /**
     *
     */
    public void generaPdf() {
        byte[] pdf;
        if (emailReporteProcesoModel.getListEmailTmp() != null) {
            pdf = emailReporteProcesoService.generaPDF(emailReporteProcesoModel.getListEmailTmp(), false).toByteArray();
        } else {
            pdf = emailReporteProcesoService.generaPDF(emailReporteProcesoModel.getListEmail(), false).toByteArray();
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
        setNombreCompletoTemp(emailReporteProcesoModel.getEmailEdit().getNombreCompleto());
        setCorreoElectronicoTemp(emailReporteProcesoModel.getEmailEdit().getCorreoElectronico());
        setCorreoElectronicoAlternoTemp(emailReporteProcesoModel.getEmailEdit().getCorreoElectronicoAlterno());

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

        if (getNombreCompletoTemp() != null) {
            emailReporteProcesoModel.getEmailEdit().setNombreCompleto(getNombreCompletoTemp());
        }
        if (getCorreoElectronicoTemp() != null) {
            emailReporteProcesoModel.getEmailEdit().setCorreoElectronico(getCorreoElectronicoTemp());
        }
        if (getCorreoElectronicoAlternoTemp() != null) {
            emailReporteProcesoModel.getEmailEdit().setCorreoElectronicoAlterno(getCorreoElectronicoAlternoTemp());
        }

        emailReporteProcesoModel.setIdEmailReporteProceso(null);
        emailReporteProcesoModel.setNombreCompleto("");
        emailReporteProcesoModel.setCorreoElectronico("");
        emailReporteProcesoModel.setCorreoElectronicoAlterno("");

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

    public void setIdEmailReporteProceso(Long idEmailReporteProceso) {
        this.idEmailReporteProceso = idEmailReporteProceso;
    }

    public Long getIdEmailReporteProceso() {
        return idEmailReporteProceso;
    }

    public void setNombreCompletoTemp(String nombreCompletoTemp) {
        this.nombreCompletoTemp = nombreCompletoTemp;
    }

    public String getNombreCompletoTemp() {
        return nombreCompletoTemp;
    }

    public void setCorreoElectronicoTemp(String correoElectronicoTemp) {
        this.correoElectronicoTemp = correoElectronicoTemp;
    }

    public String getCorreoElectronicoTemp() {
        return correoElectronicoTemp;
    }

    public void setCorreoElectronicoAlternoTemp(String correoElectronicoAlternoTemp) {
        this.correoElectronicoAlternoTemp = correoElectronicoAlternoTemp;
    }

    public String getCorreoElectronicoAlternoTemp() {
        return correoElectronicoAlternoTemp;
    }
}
