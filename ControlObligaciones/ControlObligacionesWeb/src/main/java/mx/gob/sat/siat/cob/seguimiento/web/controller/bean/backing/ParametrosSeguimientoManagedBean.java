package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.EmailReporteProcesoDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.EmailReporteProceso;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ParametrosSeguimiento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ParametrosSgtDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.TipoDocEtapa;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoDatoEnum;
import mx.gob.sat.siat.cob.seguimiento.service.otros.MailService;
import mx.gob.sat.siat.cob.seguimiento.service.otros.SGTService;
import mx.gob.sat.siat.cob.seguimiento.service.otros.ServiceCatalogos;
import mx.gob.sat.siat.cob.seguimiento.service.otros.TipoDocEtapaSGTAService;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import mx.gob.sat.siat.cob.seguimiento.utils.AbstractBaseMB;
import mx.gob.sat.siat.controlador.AccesoProceso;
import mx.gob.sat.siat.exception.AccesoDenegadoException;
import mx.gob.sat.siat.exception.AccesoDenegadoFielException;
import mx.gob.sat.siat.exception.SessionRolNullException;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;
import mx.gob.sat.siat.util.MovimientoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author root
 */
@Controller("parametrosSeguimiento")
@Scope(value = "view")
public class ParametrosSeguimientoManagedBean extends AbstractBaseMB {

    @Autowired
    private ServiceCatalogos catalogos;
    @Autowired
    private SGTService sgtService;
    @Autowired
    private TipoDocEtapaSGTAService tipoDocEtapaSGTAService;
    @Autowired
    private MailService mailService;
    @Autowired
    private EmailReporteProcesoDAO emailReporteProcesoDAO;
    private int tipoDocumento;
    private String estadoActual;
    private String booleanEstadoSeguimiento;
    private String etapaDocumento;
    private String diasVencimiento;
    private List<CatalogoBase> catalogoValorBoolean = new ArrayList<CatalogoBase>();
    private List<TipoDocEtapa> catalogoEtapaDocumento = new ArrayList<TipoDocEtapa>();
    private List<CatalogoBase> catalogoTipoDocumento = new ArrayList<CatalogoBase>();
    private List<CatalogoBase> catalogoLiquidacion;
    private List<ParametrosSeguimiento> listaParametrosVigentes;
    private List<ParametrosSgtDTO> parametrosSgt;
    private List<ParametrosSgtDTO> parametrosVigentesSgt;
    private ParametrosSgtDTO param = new ParametrosSgtDTO();
    private String numberMask;
    private String precision;
    private String valorCombo;
    private String inputParameter;
    private String fechaActual = Utilerias.formatearFechaDDMMYYYY(new Date());
    private int longitudMaxima;
    private boolean booleanValue;
    private boolean stringValue;
    private boolean numericValue;
    private boolean dateValue;
    private boolean disableCampoNumber;
    private Date paramFecha;
    private String actualizaEmail;

    /**
     *
     */
    @PostConstruct
    public void init() {
        Integer estado = 0;
        try {
            getLogger().info("Entrando a metodo init Activar Seguimiento");
            super.obtenerAccesoUsrEmpleados();
            if (AccesoProceso.validaAccesoProceso(getSession(), ConstantsCatalogos.IDENTIFICADOR_ACTIVARSEGUIMIENTO, ConstantsCatalogos.PARAMETRO_FIEL)) {
                String rfc = getAccesoUsr().getUsuario();
                getLogger().info("rfc getUsuario():" + rfc);
                estado = sgtService.buscarEstadoSeguimientoActualPorRfc("");
            }
        } catch (SGTServiceException ex) {
            getLogger().error("Error de Servicio,no se encontro estado Inicial se inicializa a cero:" + ex.getMessage());

            estado = 0;
        } catch (SessionRolNullException ex) {
            getLogger().error("Error de Sesion:" + ex.getMessage());

            manejaException(ex);
        } catch (AccesoDenegadoException ex) {
            getLogger().error("Error de Acceso denegado:" + ex.getMessage());

            manejaException(ex);
        } catch (AccesoDenegadoFielException ex) {
            getLogger().error("Error de Acceso Denegado Fiel:" + ex.getMessage());

            manejaException(ex);
        }

        try {
            estadoActual = estado == 1 ? "Si" : "No";
            parametrosSgt = sgtService.obtenerParametrosSgt();
            catalogoValorBoolean = catalogos.consultar("obligaciones diot");
            catalogoTipoDocumento = catalogos.consultar("tipo de documento");
            listaParametrosVigentes = sgtService.buscarParametrosVigentes();
            parametrosVigentesSgt = sgtService.obtenerParametrosVigentesSgt();
            setNumericValue(true);
            setDisableCampoNumber(true);
        } catch (SGTServiceException ex) {
            getLogger().error("Error de Servicio:" + ex.getMessage());

        }
    }

    /**
     *
     */
    public void longitudListener() {
        String presicion = "";
        setInputParameter(null);
        setDisableCampoNumber(false);
        try {
            List<ParametrosSgtDTO> parametros = sgtService.obtenerParametrosSgt(valorCombo);
            if (parametros.get(0).getPrecision() != null) {
                presicion = parametros.get(0).getPrecision();
            }
            if (parametros.get(0).getTipoDato().equals(TipoDatoEnum.STRING.getValor())) {
                habilitarInputTextArea(presicion);
            } else if (parametros.get(0).getTipoDato().equals(TipoDatoEnum.DATE.getValor())) {
                habilitarFecha();
            } else if (parametros.get(0).getTipoDato().equals(TipoDatoEnum.BOOLEAN.getValor())) {
                habilitarBandera();
            } else {
                habilitarInput(presicion);
            }
        } catch (NumberFormatException e) {
            //en lugar de cachar la excepcion, preguntar por la clave del tipo de dato.
            try {
                String arr[] = presicion.split(",");
                if (arr[1].equals("0")) {
                    setNumberMask(maskGenerator(arr[0]));
                } else {
                    setNumberMask(maskGenerator(arr[0]) + "." + maskGenerator(arr[1]));
                }
            } catch (IndexOutOfBoundsException ex) {
                getLogger().error(ex.getMessage());
                super.addErrorMessage("Error al formatear precision de campo, verifique valor en base de datos", "");
            }
        } catch (SGTServiceException e) {
            getLogger().error(e.getMessage());
        }
    }

    private String maskGenerator(String precision) {
        StringBuilder enteros = new StringBuilder("9?");
        for (int i = 1; i < Integer.parseInt(precision); i++) {
            enteros.append("9");
        }
        return enteros.toString();
    }

    private void manejaException(Exception e) {
        try {
            getSession().setAttribute("mensaje", e.getMessage());
            FacesContext.getCurrentInstance().getExternalContext().
                    redirect(ConstantsCatalogos.URL_EN_CONSTRUCCION);
        } catch (IOException ioe) {
            getLogger().debug(ioe);
        }
    }

    private List<TipoDocEtapa> obtenerEtapaVigilanciaPorTipoDocumento(String tipDocumento) {
        List<TipoDocEtapa> catalogo = Collections.emptyList();
        getLogger().debug("obtenerEtapaVig:" + tipDocumento);
        try {
            catalogo = tipoDocEtapaSGTAService.consultarTipoDocEtapa(tipDocumento);

        } catch (SGTServiceException e) {
            getLogger().error(e.getMessage());
        }
        return catalogo;
    }

    /**
     *
     */
    public void handleTipoDocumento() {
        String tipoDoc = tipoDocumento + "";
        if (tipoDoc != null && !tipoDoc.isEmpty()) {
            catalogoEtapaDocumento = this.obtenerEtapaVigilanciaPorTipoDocumento(tipoDoc);
        }
    }

    /**
     *
     */
    public void guardarParametrosEF() {
        try {
            param.setIdParametro(Integer.parseInt(valorCombo));
            actualizaEmail = "Actualización de parámetro";
            StringBuilder mensaje = new StringBuilder();
            mensaje.append("El usuario ").
                    append(getAccesoUsr().getUsuario()).append(" - ").append(getAccesoUsr().getNombreCompleto()).
                    append(" realizó cambios en los parámetro del sistema de Seguimiento. <br/>A continuación el detalle del cambio realizado:<br/>");
            List<ParametrosSgtDTO> parametros = sgtService.obtenerParametrosSgt(valorCombo);
            mensaje.append("<br/> Parametro: ").append(parametros.get(0).getNombre());
            if (parametros.get(0).getTipoDato().equals(TipoDatoEnum.BOOLEAN.getValor())) {
                mensaje.append("<br/> Valor anterior: ").append("0".equals(parametros.get(0).getValor()) ? "Falso" : "Verdadero");
            } else {
                mensaje.append("<br/> Valor anterior: ").append(parametros.get(0).getValor());
            }
            if (parametros.get(0).getTipoDato().equals(TipoDatoEnum.DATE.getValor())) {
                param.setValor(Utilerias.formatearFechaDDMMYYYY(paramFecha));
            } else {
                param.setValor(inputParameter);
            }
            if (parametros.get(0).getTipoDato().equals(TipoDatoEnum.BOOLEAN.getValor())) {
                mensaje.append("<br/> Valor actual: ").append(
                        "0".equals(param.getValor()) ? "Falso" : "Verdadero");
            } else {
                mensaje.append("<br/> Valor actual: ").append(param.getValor());
            }
            sgtService.guardarParametrosEF(param);

            parametrosVigentesSgt = sgtService.obtenerParametrosVigentesSgt();
            super.addMessage("Parametro: " + sgtService.obtenerParametrosSgt(valorCombo).get(0).getNombre() + " actualizado correctamente", "");
            setInputParameter("");
            setValorCombo("-1");

            enviarCorreo(mensaje);
        } catch (SGTServiceException e) {
            super.addErrorMessage("Error al actualizar el Parametro: " + param.getValor(), "");
            getLogger().error(e.getMessage());
        }
    }

    /**
     *
     */
    public void guardar() {

        try {
            getLogger().info("estadoActual:" + estadoActual);
            Integer valorUpdate = estadoActual.equals("Si") ? 0 : 1;
            getLogger().info("cambio a valorUpdate:" + valorUpdate);
            String rfc = "";
            getLogger().info("Inicializando valor de RFC");
            if (getAccesoUsr() != null) {
                rfc = getAccesoUsr().getUsuario();
                getLogger().info("accesoUsr toString():" + getAccesoUsr().toString());
                getLogger().info("rfc getUsuario():" + rfc);
            }
            actualizaEmail = "Actualización de estado de seguimiento";
            StringBuilder mensaje = new StringBuilder();
            mensaje.append("El usuario ").
                    append(getAccesoUsr().getUsuario()).append(" - ").append(getAccesoUsr().getNombreCompleto()).
                    append(" realizó cambios en el estado del Seguimiento. \nA continuación el detalle del cambio realizado:<br/>");
            mensaje.append("<br/> Valor anterior: ").append("Si".equals(estadoActual) ? "Activo" : "Inactivo");
            mensaje.append("<br/> Valor actual: ").append(!"Si".equals(estadoActual) ? "Activo" : "Inactivo");
            SegbMovimientoDTO dto = MovimientoUtil.addMovimiento(getSession(),
                    ConstantsCatalogos.CVE_SISTEMA,
                    ConstantsCatalogos.IDENTIFICADOR_ACTIVARSEGUIMIENTO,
                    new Date(), new Date(),
                    ConstantsCatalogos.ACTIVAR_DESACTIVAR_SEGUIMIENTO,
                    ConstantsCatalogos.ACTIVAR_DESACTIVAR_SEGUIMIENTO_OBS);
            sgtService.actualizarEstadoSeguimiento(valorUpdate, rfc, dto);
            Integer estado = sgtService.buscarEstadoSeguimientoActualPorRfc(rfc);
            estadoActual = (estado == 1 ? "Si" : "No");
            super.addMessage(ConstantsCatalogos.REGISTROEDITADO, "");
            enviarCorreo(mensaje);
        } catch (SGTServiceException e) {
            super.addErrorMessage("Error al actualizar estado", "");
            getLogger().error(e.getMessage(), e);
        } catch (AccesoDenegadoException e) {
            getLogger().error(e.getMessage(), e);
        }

    }

    /**
     *
     */
    public void actualizarDiasVencimiento() {
        actualizaEmail = "Actualización de días de vencimiento";
        StringBuilder mensaje = new StringBuilder();
        mensaje.append("El usuario ").
                append(getAccesoUsr().getUsuario()).append(" - ").
                append(getAccesoUsr().getNombreCompleto()).
                append(" realizó cambios en los días de vencimiento. <br/>A continuación el detalle del cambio realizado:<br/>");
        String tipo = obtenerTipoDocto();
        String etapa = obtenerEtapaDocto();
        mensaje.append("<br/> Tipo Documento: ").append(tipo);
        mensaje.append("<br/> Etapa Documento: ").append(etapa);
        mensaje.append("<br/> Valor Anterior: ").append(obtenerDiasVencimiento(tipo, etapa));

        TipoDocEtapa tde = new TipoDocEtapa();
        tde.setDiasVencimiento(Integer.parseInt(diasVencimiento));
        tde.setIdEtapaVigilancia(Integer.parseInt(etapaDocumento));
        tde.setId(tipoDocumento);
        mensaje.append("<br/> Valor Actual: ").append(tde.getDiasVencimiento());
        try {
            SegbMovimientoDTO dto = MovimientoUtil.addMovimiento(getSession(),
                    ConstantsCatalogos.CVE_SISTEMA,
                    ConstantsCatalogos.IDENTIFICADOR_ACTIVARSEGUIMIENTO,
                    new Date(), new Date(),
                    ConstantsCatalogos.ACTUALIZAR_DIAS_SEGUIMIENTO,
                    ConstantsCatalogos.ACTUALIZAR_DIAS_SEGUIMIENTO_OBS);
            tipoDocEtapaSGTAService.actualizarParametrosPorTipoDocumento(tde, dto);
            listaParametrosVigentes = sgtService.buscarParametrosVigentes();
            setTipoDocumento(-1);
            setEtapaDocumento("-1");
            setDiasVencimiento("");
            super.addMessage(ConstantsCatalogos.REGISTROEDITADO, "");
            enviarCorreo(mensaje);
        } catch (SGTServiceException ex) {
            getLogger().error(ex.getMessage(), ex);
        } catch (AccesoDenegadoException ex) {
            getLogger().error(ex.getMessage(), ex);
        } catch (Exception e) {
            super.addErrorMessage("Error al actualizar dias de vencimiento", "");
            getLogger().error(e.getMessage(), e);
        }

    }

    private String obtenerTipoDocto() {
        for (CatalogoBase tipoDocto : catalogoTipoDocumento) {
            if (tipoDocto.getId() == Integer.valueOf(tipoDocumento)) {
                return tipoDocto.getNombre();
            }
        }
        return "";
    }

    private String obtenerEtapaDocto() {
        for (TipoDocEtapa etapaDocto : catalogoEtapaDocumento) {
            if (etapaDocto.getIdEtapaVigilancia() == Integer.valueOf(etapaDocumento)) {
                return etapaDocto.getNombreDocumentoEtapa();
            }
        }
        return "";
    }

    /**
     *
     * @param tipo
     * @param etapa
     */
    private String obtenerDiasVencimiento(String tipo, String etapa) {
        String tipo2 = tipo.toUpperCase();
        String etapa2 = etapa.toUpperCase();

        for (ParametrosSeguimiento parametro : listaParametrosVigentes) {

            String tipo3 = parametro.getTipoDocumento().toUpperCase();
            String etapa3 = parametro.getEtapa().toUpperCase();
            if (tipo3.equals(tipo2)
                    && etapa3.equals(etapa2)) {
                return parametro.getDiasVencimiento();
            }
        }
        return "";
    }

    private void habilitarInputTextArea(String presicion) {
        setStringValue(true);
        setNumericValue(false);
        setDateValue(false);
        setBooleanValue(false);
        setLongitudMaxima(Integer.parseInt(presicion));
    }

    private void habilitarFecha() {
        setStringValue(false);
        setNumericValue(false);
        setDateValue(true);
        setBooleanValue(false);
    }

    private void habilitarBandera() {
        setStringValue(false);
        setNumericValue(false);
        setDateValue(false);
        setBooleanValue(true);
    }

    private void habilitarInput(String presicion) {
        setStringValue(false);
        setNumericValue(true);
        setDateValue(false);
        setBooleanValue(false);
        setNumberMask(maskGenerator(presicion));
        setPrecision(presicion);
    }

    /**
     *
     * @param mensaje
     */
    private void enviarCorreo(StringBuilder mensaje) {
        try {
            String ambiente = "";
            for (ParametrosSgtDTO parametro : parametrosVigentesSgt) {
                if (parametro.getIdParametro() == 6) {
                    ambiente = parametro.getValor();
                }
            }
            List<EmailReporteProceso> emails = emailReporteProcesoDAO.todosLosEmailReporteProceso(false);
            StringBuilder sEmails = new StringBuilder("");
            for (EmailReporteProceso emailReporteProceso : emails) {
                sEmails.append(emailReporteProceso.getCorreoElectronico()).append(",");
                if (emailReporteProceso.getCorreoElectronicoAlterno() != null) {
                    sEmails.append(emailReporteProceso.getCorreoElectronicoAlterno()).append(",");
                }
            }
            String[] destinatarios = sEmails.toString().split(",");
            mensaje.append("<br/> Fecha y hora del cambio: ").append(Utilerias.formatearFechaDDMMAAAAHHMMSS(new Date())).
                    append("<br/> Por favor no responda a este mensaje, fue enviado desde una cuenta de correo electrónico no monitoreada.");

            mailService.enviarCorreoPara(destinatarios, "MAT CO " + ambiente + " - " + actualizaEmail, mensaje.toString());
        } catch (MessagingException ex) {
            getLogger().error(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param estadoActual
     */
    public void setEstadoActual(String estadoActual) {
        this.estadoActual = estadoActual;
    }

    /**
     *
     * @return
     */
    public String getEstadoActual() {
        return estadoActual;
    }

    /**
     *
     * @param booleanEstadoSeguimiento
     */
    public void setBooleanEstadoSeguimiento(String booleanEstadoSeguimiento) {
        this.booleanEstadoSeguimiento = booleanEstadoSeguimiento;
    }

    /**
     *
     * @return
     */
    public String getBooleanEstadoSeguimiento() {
        return booleanEstadoSeguimiento;
    }

    /**
     *
     * @param tipoDocumento
     */
    public void setTipoDocumento(int tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    /**
     *
     * @return
     */
    public int getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     *
     * @param catalogoValorBoolean
     */
    public void setCatalogoValorBoolean(List<CatalogoBase> catalogoValorBoolean) {
        this.catalogoValorBoolean = catalogoValorBoolean;
    }

    /**
     *
     * @return
     */
    public List<CatalogoBase> getCatalogoValorBoolean() {
        return catalogoValorBoolean;
    }

    /**
     *
     * @param catalogoEtapaDocumento
     */
    public void setCatalogoEtapaDocumento(List<TipoDocEtapa> catalogoEtapaDocumento) {
        this.catalogoEtapaDocumento = catalogoEtapaDocumento;
    }

    /**
     *
     * @return
     */
    public List<TipoDocEtapa> getCatalogoEtapaDocumento() {
        return catalogoEtapaDocumento;
    }

    /**
     *
     * @param catalogos
     */
    public void setCatalogos(ServiceCatalogos catalogos) {
        this.catalogos = catalogos;
    }

    /**
     *
     * @return
     */
    public ServiceCatalogos getCatalogos() {
        return catalogos;
    }

    /**
     *
     * @param sgtService
     */
    public void setSgtService(SGTService sgtService) {
        this.sgtService = sgtService;
    }

    /**
     *
     * @return
     */
    public SGTService getSgtService() {
        return sgtService;
    }

    /**
     *
     * @param etapaDocumento
     */
    public void setEtapaDocumento(String etapaDocumento) {
        this.etapaDocumento = etapaDocumento;
    }

    /**
     *
     * @return
     */
    public String getEtapaDocumento() {
        return etapaDocumento;
    }

    /**
     *
     * @param catalogoTipoDocumento
     */
    public void setCatalogoTipoDocumento(List<CatalogoBase> catalogoTipoDocumento) {
        this.catalogoTipoDocumento = catalogoTipoDocumento;
    }

    /**
     *
     * @return
     */
    public List<CatalogoBase> getCatalogoTipoDocumento() {
        return catalogoTipoDocumento;
    }

    /**
     *
     * @param listaParametrosVigentes
     */
    public void setListaParametrosVigentes(List<ParametrosSeguimiento> listaParametrosVigentes) {
        this.listaParametrosVigentes = listaParametrosVigentes;
    }

    /**
     *
     * @return
     */
    public List<ParametrosSeguimiento> getListaParametrosVigentes() {
        return listaParametrosVigentes;
    }

    /**
     *
     * @param diasVencimiento
     */
    public void setDiasVencimiento(String diasVencimiento) {
        this.diasVencimiento = diasVencimiento;
    }

    /**
     *
     * @return
     */
    public String getDiasVencimiento() {
        return diasVencimiento;
    }

    /**
     *
     * @param catalogoLiquidacion
     */
    public void setCatalogoLiquidacion(List<CatalogoBase> catalogoLiquidacion) {
        this.catalogoLiquidacion = catalogoLiquidacion;
    }

    /**
     *
     * @return
     */
    public List<CatalogoBase> getCatalogoLiquidacion() {
        return catalogoLiquidacion;
    }

    /**
     *
     * @param parametrosSgt
     */
    public void setParametrosSgt(List<ParametrosSgtDTO> parametrosSgt) {
        this.parametrosSgt = parametrosSgt;
    }

    /**
     *
     * @return
     */
    public List<ParametrosSgtDTO> getParametrosSgt() {
        return parametrosSgt;
    }

    /**
     *
     * @param param
     */
    public void setParam(ParametrosSgtDTO param) {
        this.param = param;
    }

    /**
     *
     * @return
     */
    public ParametrosSgtDTO getParam() {
        return param;
    }

    /**
     *
     * @param valorCombo
     */
    public void setValorCombo(String valorCombo) {
        this.valorCombo = valorCombo;
    }

    /**
     *
     * @return
     */
    public String getValorCombo() {
        return valorCombo;
    }

    /**
     *
     * @param fechaActual
     */
    public void setFechaActual(String fechaActual) {
        this.fechaActual = fechaActual;
    }

    /**
     *
     * @return
     */
    public String getFechaActual() {
        return fechaActual;
    }

    /**
     *
     * @param inputParameter
     */
    public void setInputParameter(String inputParameter) {
        this.inputParameter = inputParameter;
    }

    /**
     *
     * @return
     */
    public String getInputParameter() {
        return inputParameter;
    }

    /**
     *
     * @return
     */
    public int getLongitudMaxima() {
        return longitudMaxima;
    }

    /**
     *
     * @param longitudMaxima
     */
    public void setLongitudMaxima(int longitudMaxima) {
        this.longitudMaxima = longitudMaxima;
    }

    /**
     *
     * @param numberMask
     */
    public void setNumberMask(String numberMask) {
        this.numberMask = numberMask;
    }

    /**
     *
     * @return
     */
    public String getNumberMask() {
        return numberMask;
    }

    /**
     *
     * @param disableCampoNumber
     */
    public void setDisableCampoNumber(boolean disableCampoNumber) {
        this.disableCampoNumber = disableCampoNumber;
    }

    /**
     *
     * @return
     */
    public boolean isDisableCampoNumber() {
        return disableCampoNumber;
    }

    /**
     *
     * @param stringValue
     */
    public void setStringValue(boolean stringValue) {
        this.stringValue = stringValue;
    }

    /**
     *
     * @return
     */
    public boolean isStringValue() {
        return stringValue;
    }

    /**
     *
     * @param numericValue
     */
    public void setNumericValue(boolean numericValue) {
        this.numericValue = numericValue;
    }

    /**
     *
     * @return
     */
    public boolean isNumericValue() {
        return numericValue;
    }

    /**
     *
     * @param dateValue
     */
    public void setDateValue(boolean dateValue) {
        this.dateValue = dateValue;
    }

    /**
     *
     * @return
     */
    public boolean isDateValue() {
        return dateValue;
    }

    /**
     *
     * @param paramFecha
     */
    public void setParamFecha(Date paramFecha) {
        if (paramFecha != null) {
            this.paramFecha = (Date) paramFecha.clone();
        }
    }

    /**
     *
     * @return
     */
    public Date getParamFecha() {
        if (paramFecha != null) {
            return (Date) paramFecha.clone();
        } else {
            return null;
        }
    }

    /**
     *
     * @param booleanValue
     */
    public void setBooleanValue(boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    /**
     *
     * @return
     */
    public boolean isBooleanValue() {
        return booleanValue;
    }

    /**
     *
     * @param parametrosVigentesSgt
     */
    public void setParametrosVigentesSgt(List<ParametrosSgtDTO> parametrosVigentesSgt) {
        this.parametrosVigentesSgt = parametrosVigentesSgt;
    }

    /**
     *
     * @return
     */
    public List<ParametrosSgtDTO> getParametrosVigentesSgt() {
        return parametrosVigentesSgt;
    }

    /**
     *
     * @return
     */
    public String getPrecision() {
        return precision;
    }

    /**
     *
     * @param precision
     */
    public void setPrecision(String precision) {
        this.precision = precision;
    }
}
