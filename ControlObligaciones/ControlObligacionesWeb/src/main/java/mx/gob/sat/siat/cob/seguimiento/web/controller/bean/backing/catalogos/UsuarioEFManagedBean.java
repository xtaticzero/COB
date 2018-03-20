package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.catalogos;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Funcionario;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.UsuarioEF;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.catalogos.FuncionarioService;
import mx.gob.sat.siat.cob.seguimiento.service.catalogos.UsuarioEFService;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import mx.gob.sat.siat.cob.seguimiento.utils.AbstractBaseMB;
import mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.catalogos.model.FuncionarioModel;
import mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.catalogos.model.UsuarioEFModel;
import mx.gob.sat.siat.controlador.AccesoProceso;
import mx.gob.sat.siat.exception.AccesoDenegadoException;
import mx.gob.sat.siat.exception.AccesoDenegadoFielException;
import mx.gob.sat.siat.exception.DaoException;
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
@Controller("usuarioEFMB")
@Scope("view")
public class UsuarioEFManagedBean extends AbstractBaseMB {
    
    @Autowired
    private UsuarioEFService usuarioEFService;
    @Autowired
    private FuncionarioService funcionarioService;

    private UsuarioEFModel usuarioEFModel = new UsuarioEFModel();
    private FuncionarioModel funcionarioModel = new FuncionarioModel();

    private String rfcCortoTemp;
    private Long entidadFederativaTemp;
    private String nombreUsuarioTemp;
    private String correoElectronicoTemp;    
    private String correoElectronicoFuncionarioTemp;
    private String numeroEmpleadoTemp;
    private String nombreFuncionarioTemp;
    private String correoElectronicoAlternoTemp;
    private String areaDeAdscripcionTemp;
    private String cargoDescripcionTemp;
    
    /**
     *
     */
    public UsuarioEFManagedBean() {
        super();
    }
    /**
     *
     */
    @PostConstruct
    public void init() {
        try {
            obtenerAccesoUsrEmpleados();
            if (AccesoProceso.validaAccesoProceso(getSession(),
                    ConstantsCatalogos.IDENTIFICADOR_USUARIOEF,
                    ConstantsCatalogos.PARAMETRO_FIEL)) {
                usuarioEFModel.setListUsuarioEF(usuarioEFService.todosLosUsuariosEF());
                funcionarioModel.setListFuncionario(funcionarioService.todosLosFuncionarios());
    }
        } catch (SessionRolNullException e) {
            getSession().setAttribute("mensaje", e.getMessage());
            redirigir(ConstantsCatalogos.URL_EN_CONSTRUCCION);
            getLogger().error(e);
        } catch (AccesoDenegadoException e) {
            getSession().setAttribute("mensaje", e.getMessage());
            redirigir(ConstantsCatalogos.URL_EN_CONSTRUCCION);
            getLogger().error(e);
        } catch (AccesoDenegadoFielException e) {
            getSession().setAttribute("mensaje", e.getMessage());
            redirigir(ConstantsCatalogos.URL_EN_CONSTRUCCION);
            getLogger().error(e);
        }
    }

    /**
     *
     */
    public void agrega() {
        UsuarioEF usuarioEF = new UsuarioEF();
       
        usuarioEF.setRfcCorto(usuarioEFModel.getRfcCorto().toUpperCase());
        usuarioEF.setIdEntidadFederativa(usuarioEFModel.getIdTipoEntidad());
        usuarioEF.setNombreUsuario(usuarioEFModel.getNombreUsuario());
        usuarioEF.setCorreoElectronico(usuarioEFModel.getCorreoElectronico());
        Calendar cal = Calendar.getInstance();
        usuarioEF.setFechaInicio(cal.getTime());

        Integer reg = usuarioEFService.buscaUsuarioEFPorIdYNom(usuarioEF);
        if (reg == null) {
            AbstractBaseMB.msgError(ConstantsCatalogos.ERROR_CONSULTA_DB);
            return;
        }
        if (reg > 0) {
            AbstractBaseMB.msgError(ConstantsCatalogos.REGISTRO_DUPLICADO);
            return;
        }

        try {
            SegbMovimientoDTO dto = MovimientoUtil.addMovimiento(getSession(), 
                    ConstantsCatalogos.CVE_SISTEMA, 
                    ConstantsCatalogos.IDENTIFICADOR_USUARIOEF, 
                    new Date(), new Date(), 
                    ConstantsCatalogos.ALTA_MOV_USUARIOEF, 
                    ConstantsCatalogos.ALTA_USUARIOEF_OBS);
            usuarioEFService.agregaUsuarioEF(usuarioEF, dto);
            usuarioEFModel.setListUsuarioEF(usuarioEFService.todosLosUsuariosEF());
            
            cerrar();
            AbstractBaseMB.msgInfo(ConstantsCatalogos.REGISTROEXITOSO);
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
    public void edita() {
        try {
            SegbMovimientoDTO dto = MovimientoUtil.addMovimiento(getSession(),
                    ConstantsCatalogos.CVE_SISTEMA, 
                    ConstantsCatalogos.IDENTIFICADOR_USUARIOEF, 
                    new Date(), new Date(), 
                    ConstantsCatalogos.MODIFICACION_MOV_USUARIOEF, 
                    ConstantsCatalogos.MODIFICACION_USUARIOEF_OBS);
            
            usuarioEFModel.getUsuarioEFEdit().setIdEntidadFederativa(usuarioEFModel.getIdTipoEntidad());
            usuarioEFService.editaUsuarioEF(usuarioEFModel.getUsuarioEFEdit(), dto);
            usuarioEFModel.setListUsuarioEF(usuarioEFService.todosLosUsuariosEF());
           
            cerrar();
            AbstractBaseMB.msgInfo(ConstantsCatalogos.REGISTROEDITADO);
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
    public void reactiva() {

        try {
            usuarioEFService.reactivaUsuarioEF(usuarioEFModel.getUsuarioEFEdit());
            usuarioEFModel.setListUsuarioEF(usuarioEFService.todosLosUsuariosEF());
           
            cerrar();
            AbstractBaseMB.msgInfo(ConstantsCatalogos.REGISTROHABILITADO);
        } catch (SeguimientoDAOException e) {
            getLogger().error(e.getMessage());
        }
    }

    /**
     *
     */
    public void elimina() {

        Calendar cal = Calendar.getInstance();
        usuarioEFModel.getUsuarioEFEli().setFechaFin(cal.getTime());

        try {
            SegbMovimientoDTO dto = MovimientoUtil.addMovimiento(getSession(), 
                    ConstantsCatalogos.CVE_SISTEMA, 
                    ConstantsCatalogos.IDENTIFICADOR_USUARIOEF, 
                    new Date(), new Date(), 
                    ConstantsCatalogos.BAJA_MOV_USUARIOEF, 
                    ConstantsCatalogos.BAJA_USUARIOEF_OBS);
            usuarioEFService.eliminaUsuarioEF(usuarioEFModel.getUsuarioEFEli(), dto);
            usuarioEFModel.setListUsuarioEF(usuarioEFService.todosLosUsuariosEF());
           
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
        if (usuarioEFModel.getListUsuarioEFTmp() != null) {
                excel = usuarioEFService.generaExcel(usuarioEFModel.getListUsuarioEFTmp()).toByteArray();
            } else {
                excel = usuarioEFService.generaExcel(usuarioEFModel.getListUsuarioEF()).toByteArray();
            }
                despachaArchivo(excel, ConstantsCatalogos.TYPE_EXCEL, "tabla.xls", ConstantsCatalogos.ERROR_GENERA_EXCEL);
        }

    /**
     *
     */
    public void generaPdf() {
        byte[] pdf;
        if (usuarioEFModel.getListUsuarioEFTmp() != null) {
                pdf = usuarioEFService.generaPDF(usuarioEFModel.getListUsuarioEFTmp()).toByteArray();
            } else {
                pdf = usuarioEFService.generaPDF(usuarioEFModel.getListUsuarioEF()).toByteArray();
            }
            despachaArchivo(pdf, ConstantsCatalogos.TYPE_PDF, "tabla.pdf", ConstantsCatalogos.ERROR_GENERA_PDF);
        }

    /**
     *
     */
    public void abrirNuevo() {
        this.getUsuarioEFModel().setListaComboEntidad(usuarioEFService.obtenerComboEntidad());
        this.getFuncionarioModel().setTblVisibleFuncionario(false);
        this.getUsuarioEFModel().setTblVisible(false);
        this.getUsuarioEFModel().setNvoVisible(true);
        
    }

    /**
     *
     */
    public void abrirEditar() {
        
        this.getUsuarioEFModel().setListaComboEntidad(usuarioEFService.obtenerComboEntidad());
        this.getUsuarioEFModel().setIdTipoEntidad(usuarioEFModel.getUsuarioEFEdit().getIdEntidadFederativa());
        setRfcCortoTemp(usuarioEFModel.getUsuarioEFEdit().getRfcCorto());
        setEntidadFederativaTemp(usuarioEFModel.getUsuarioEFEdit().getIdEntidadFederativa());
        setNombreUsuarioTemp(usuarioEFModel.getUsuarioEFEdit().getNombreUsuario());
        setCorreoElectronicoTemp(usuarioEFModel.getUsuarioEFEdit().getCorreoElectronico());
        usuarioEFModel.setFechaFinStr(usuarioEFModel.getUsuarioEFEdit().getFechaFinStr());
        this.getUsuarioEFModel().setTblVisible(false);
        this.getFuncionarioModel().setTblVisibleFuncionario(false);
        this.getUsuarioEFModel().setEdtVisible(true);
    }

    /**
     *
     */
    public void abrirEliminar() {
        this.getUsuarioEFModel().setListaComboEntidad(usuarioEFService.obtenerComboEntidad());
        this.getUsuarioEFModel().setIdTipoEntidad(usuarioEFModel.getUsuarioEFEli().getIdEntidadFederativa());
        this.getUsuarioEFModel().setTblVisible(false);
        this.getFuncionarioModel().setTblVisibleFuncionario(false);
        this.getUsuarioEFModel().setElmVisible(true);
    }

    /**
     *
     */
    public void cerrar() {
        this.getUsuarioEFModel().setTblVisible(true);
        this.getFuncionarioModel().setTblVisibleFuncionario(true);
        this.getUsuarioEFModel().setNvoVisible(false);
        this.getUsuarioEFModel().setEdtVisible(false);
        this.getUsuarioEFModel().setElmVisible(false);
        clean();
    }

    /**
     *
     */
    public void clean() {
        if(rfcCortoTemp!=null){
             usuarioEFModel.getUsuarioEFEdit().setRfcCorto(getRfcCortoTemp());
        }
        if(nombreUsuarioTemp!=null){
           usuarioEFModel.getUsuarioEFEdit().setNombreUsuario(getNombreUsuarioTemp()); 
        }
        if(correoElectronicoTemp!=null){
           usuarioEFModel.getUsuarioEFEdit().setCorreoElectronico(getCorreoElectronicoTemp()); 
        }
        
        getUsuarioEFModel().setRfcCorto("");
        getUsuarioEFModel().setIdTipoEntidad(null);
        getUsuarioEFModel().setNombreUsuario("");
        getUsuarioEFModel().setCorreoElectronico("");
        
    }
    
    //Metodos para Funcionarios
    
    /**
     *
     */
    public void agregaFuncionario() {
        Funcionario funcionario = new Funcionario();
        boolean resultado = false;
        String expresionRegular = "[\\w\\.-]*[a-zA-Z0-9_]@[\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]";
        if(funcionarioModel.getCorreoElectronicoAlterno() !=null && !funcionarioModel.getCorreoElectronicoAlterno().equals("")){
          resultado = Pattern.matches(expresionRegular, funcionarioModel.getCorreoElectronicoAlterno());
        }else{
            resultado = true;
        }
            if (resultado) {
                funcionario.setNumeroEmpleado(funcionarioModel.getNumeroEmpleado());
                funcionario.setNombreFuncionario(funcionarioModel.getNombreFuncionario());
                funcionario.setCorreoElectronicoFuncionario(funcionarioModel.getCorreoElectronicoFuncionario());
                funcionario.setCorreoElectronicoAlterno(funcionarioModel.getCorreoElectronicoAlterno());
                funcionario.setAreaDeAdscripcion(funcionarioModel.getIdTipoArea());
                funcionario.setDescripcionCargo(funcionarioModel.getDescripcionCargo());

                Calendar cal = Calendar.getInstance();
                funcionario.setFechaInicio(cal.getTime());

                Integer reg = funcionarioService.buscaFuncionarioPorIdYNom(funcionario);
                if (reg == null) {
                    AbstractBaseMB.msgError(ConstantsCatalogos.ERROR_CONSULTA_DB);
                    return;
                }
                if (reg > 0) {
                    AbstractBaseMB.msgError(ConstantsCatalogos.REGISTRO_DUPLICADO);
                    return;
                }

                try {
                    SegbMovimientoDTO dto = MovimientoUtil.addMovimiento(getSession(),
                            ConstantsCatalogos.CVE_SISTEMA,
                            ConstantsCatalogos.IDENTIFICADOR_USUARIOEF,
                            new Date(), new Date(),
                            ConstantsCatalogos.ALTA_MOV_FUNCIONARIO,
                            ConstantsCatalogos.ALTA_FUNCIONARIO_OBS);
                    funcionarioService.agregaFuncionario(funcionario, dto);
                    funcionarioModel.setListFuncionario(funcionarioService.todosLosFuncionarios());
                    
                    cerrarFuncionario();
                    AbstractBaseMB.msgInfo(ConstantsCatalogos.REGISTROEXITOSO);
                        } catch (AccesoDenegadoException e) {
                            getLogger().error(e.getMessage());
                        } catch (SeguimientoDAOException e) {
                            getLogger().error(e.getMessage());
                        } catch (DaoException e) {
                            getLogger().error(e.getMessage());
            }
        }else{
                 AbstractBaseMB.msgError(ConstantsCatalogos.ERROR_VALIDACION_CORREO);
            
        }
    }

    /**
     *
     */
    public void editaFuncionario() {
        boolean resultado = false;
        String expresionRegular = "[\\w\\.-]*[a-zA-Z0-9_]@[\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]";
        if(funcionarioModel.getFuncionarioEdit().getCorreoElectronicoAlterno() !=null && !funcionarioModel.getFuncionarioEdit().getCorreoElectronicoAlterno().equals("")){
            resultado = Pattern.matches(expresionRegular, funcionarioModel.getFuncionarioEdit().getCorreoElectronicoAlterno());                        
        }else{
            resultado = true;
            }
            if (resultado) {
                try {
                    SegbMovimientoDTO dto = MovimientoUtil.addMovimiento(getSession(),
                                ConstantsCatalogos.CVE_SISTEMA,
                                ConstantsCatalogos.IDENTIFICADOR_USUARIOEF,
                                new Date(), new Date(),
                                ConstantsCatalogos.MODIFICACION_MOV_FUNCIONARIO,
                                ConstantsCatalogos.MODIFICACION_FUNCIONARIO_OBS);
                    funcionarioModel.getFuncionarioEdit().setAreaDeAdscripcion(funcionarioModel.getIdTipoArea());
                    funcionarioService.editaFuncionario(funcionarioModel.getFuncionarioEdit(), dto);
                    funcionarioModel.setListFuncionario(funcionarioService.todosLosFuncionarios());
                   
                    cerrarFuncionario();
                    AbstractBaseMB.msgInfo(ConstantsCatalogos.REGISTROEXITOSO);
                    } catch (AccesoDenegadoException e) {
                        getLogger().error(e.getMessage());
                    } catch (SeguimientoDAOException e) {
                        getLogger().error(e.getMessage());
                    } catch (DaoException e) {
                        getLogger().error(e.getMessage());
            }
        }else{
                AbstractBaseMB.msgError(ConstantsCatalogos.ERROR_VALIDACION_CORREO);
            }
       
    }

    /**
     *
     */
    public void reactivaFuncionario() {

        try {
            funcionarioService.reactivaFuncionario(funcionarioModel.getFuncionarioEdit());
            funcionarioModel.setListFuncionario(funcionarioService.todosLosFuncionarios());
           
            cerrarFuncionario();
            AbstractBaseMB.msgInfo(ConstantsCatalogos.REGISTROEXITOSO);
        } catch (SeguimientoDAOException e) {
            getLogger().error(e.getMessage());
        }
    }

    /**
     *
     */
    public void eliminaFuncionario() {

        Calendar cal = Calendar.getInstance();
        funcionarioModel.getFuncionarioEli().setFechaFin(cal.getTime());

        try {
            SegbMovimientoDTO dto = MovimientoUtil.addMovimiento(getSession(),
                    ConstantsCatalogos.CVE_SISTEMA,
                    ConstantsCatalogos.IDENTIFICADOR_USUARIOEF,
                    new Date(), new Date(),
                    ConstantsCatalogos.BAJA_MOV_FUNCIONARIO,
                    ConstantsCatalogos.BAJA_FUNCIONARIO_OBS);
            funcionarioService.eliminaFuncionario(funcionarioModel.getFuncionarioEli(), dto);
            funcionarioModel.setListFuncionario(funcionarioService.todosLosFuncionarios());
           
            this.cerrarFuncionario();
            AbstractBaseMB.msgInfo(ConstantsCatalogos.REGISTROEXITOSO);
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
    public void generaExcelFuncionario() {
        byte[] excel;
        if (funcionarioModel.getListFuncionarioTmp() != null) {
                excel = funcionarioService.generaExcel(funcionarioModel.getListFuncionarioTmp()).toByteArray();
            } else {
                excel = funcionarioService.generaExcel(funcionarioModel.getListFuncionario()).toByteArray();
            }
                despachaArchivo(excel, ConstantsCatalogos.TYPE_EXCEL, "tabla.xls", ConstantsCatalogos.ERROR_GENERA_EXCEL);
        }

    /**
     *
     */
    public void generaPdfFuncionario() {
        byte[] pdf;
        if (funcionarioModel.getListFuncionarioTmp() != null) {
                pdf = funcionarioService.generaPDF(funcionarioModel.getListFuncionarioTmp()).toByteArray();
            } else {
                pdf = funcionarioService.generaPDF(funcionarioModel.getListFuncionario()).toByteArray();
            }
            despachaArchivo(pdf, ConstantsCatalogos.TYPE_PDF, "tabla.pdf", ConstantsCatalogos.ERROR_GENERA_PDF);
        }

    /**
     *
     */
    public void abrirNuevoFuncionario() {
        this.getFuncionarioModel().setListaComboArea(funcionarioService.obtenerComboArea());
        this.getFuncionarioModel().setTblVisibleFuncionario(false);
        this.getUsuarioEFModel().setTblVisible(false);
        this.getFuncionarioModel().setNvoVisible(true);
    }

    /**
     *
     */
    public void abrirEditarFuncionario() {
        
        this.getFuncionarioModel().setListaComboArea(funcionarioService.obtenerComboArea());
        this.getFuncionarioModel().setIdTipoArea(funcionarioModel.getFuncionarioEdit().getAreaDeAdscripcion());
        setNumeroEmpleadoTemp(funcionarioModel.getFuncionarioEdit().getNumeroEmpleado());
        setNombreFuncionarioTemp(funcionarioModel.getFuncionarioEdit().getNombreFuncionario());
        setCorreoElectronicoTemp(funcionarioModel.getFuncionarioEdit().getCorreoElectronicoFuncionario());
        setCorreoElectronicoAlternoTemp(funcionarioModel.getFuncionarioEdit().getCorreoElectronicoAlterno());
        setAreaDeAdscripcionTemp(funcionarioModel.getFuncionarioEdit().getAreaDeAdscripcion().toString());
        setCargoDescripcionTemp(funcionarioModel.getFuncionarioEdit().getDescripcionCargo());
        funcionarioModel.setFechaFinStr(funcionarioModel.getFuncionarioEdit().getFechaFinStr());
        this.getFuncionarioModel().setTblVisibleFuncionario(false);
        this.getUsuarioEFModel().setTblVisible(false);
        this.getFuncionarioModel().setEdtVisible(true);
    }

    /**
     *
     */
    public void abrirEliminarFuncionario() {
        this.getFuncionarioModel().setListaComboArea(funcionarioService.obtenerComboArea());
        this.getFuncionarioModel().setIdTipoArea(funcionarioModel.getFuncionarioEli().getAreaDeAdscripcion());
        this.getFuncionarioModel().setTblVisibleFuncionario(false);
        this.getUsuarioEFModel().setTblVisible(false);
        this.getFuncionarioModel().setElmVisible(true);
    }

    /**
     *
     */
    public void cerrarFuncionario() {
        this.getFuncionarioModel().setTblVisibleFuncionario(true);
        this.getUsuarioEFModel().setTblVisible(true);
        this.getFuncionarioModel().setNvoVisible(false);
        this.getFuncionarioModel().setEdtVisible(false);
        this.getFuncionarioModel().setElmVisible(false);
        cleanFuncionario();
    }

    /**
     *
     */
    public void cleanFuncionario() {
        if(numeroEmpleadoTemp!=null){
             funcionarioModel.getFuncionarioEdit().setNumeroEmpleado(getNumeroEmpleadoTemp());
        }
        if(nombreFuncionarioTemp!=null){
           funcionarioModel.getFuncionarioEdit().setNombreFuncionario(getNombreFuncionarioTemp()); 
        }
        if(correoElectronicoFuncionarioTemp!=null){
           funcionarioModel.getFuncionarioEdit().setCorreoElectronicoFuncionario(getCorreoElectronicoFuncionarioTemp()); 
        }
        if(correoElectronicoAlternoTemp!=null){
           funcionarioModel.getFuncionarioEdit().setCorreoElectronicoAlterno(getCorreoElectronicoAlternoTemp()); 
        }     
        if(cargoDescripcionTemp!=null){
           funcionarioModel.getFuncionarioEdit().setDescripcionCargo(getCargoDescripcionTemp()); 
        }   

        getFuncionarioModel().setNumeroEmpleado("");
        getFuncionarioModel().setNombreFuncionario("");
        getFuncionarioModel().setCorreoElectronicoFuncionario("");
        getFuncionarioModel().setCorreoElectronicoAlterno("");
        getFuncionarioModel().setIdTipoArea(null);
        getFuncionarioModel().setDescripcionCargo("");
        
    }

    /**
     *
     * @param rfcCortoTemp
     */
    public void setRfcCortoTemp(String rfcCortoTemp) {
        this.rfcCortoTemp = rfcCortoTemp;
    }

    /**
     *
     * @return
     */
    public String getRfcCortoTemp() {
        return rfcCortoTemp;
    }

    /**
     *
     * @param entidadFederativaTemp
     */
    public void setEntidadFederativaTemp(Long entidadFederativaTemp) {
        this.entidadFederativaTemp = entidadFederativaTemp;
    }

    /**
     *
     * @return
     */
    public Long getEntidadFederativaTemp() {
        return entidadFederativaTemp;
    }

    /**
     *
     * @param nombreUsuarioTemp
     */
    public void setNombreUsuarioTemp(String nombreUsuarioTemp) {
        this.nombreUsuarioTemp = nombreUsuarioTemp;
    }

    /**
     *
     * @return
     */
    public String getNombreUsuarioTemp() {
        return nombreUsuarioTemp;
    }

    /**
     *
     * @param correoElectronicoTemp
     */
    public void setCorreoElectronicoTemp(String correoElectronicoTemp) {
        this.correoElectronicoTemp = correoElectronicoTemp;
    }

    /**
     *
     * @return
     */
    public String getCorreoElectronicoTemp() {
        return correoElectronicoTemp;
    }

    /**
     *
     * @param usuarioEFService
     */
    public void setUsuarioEFService(UsuarioEFService usuarioEFService) {
        this.usuarioEFService = usuarioEFService;
    }

    /**
     *
     * @return
     */
    public UsuarioEFService getUsuarioEFService() {
        return usuarioEFService;
    }

    /**
     *
     * @param usuarioEFModel
     */
    public void setUsuarioEFModel(UsuarioEFModel usuarioEFModel) {
        this.usuarioEFModel = usuarioEFModel;
    }

    /**
     *
     * @return
     */
    public UsuarioEFModel getUsuarioEFModel() {
        return usuarioEFModel;
    }

    /**
     *
     * @param funcionarioService
     */
    public void setFuncionarioService(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    /**
     *
     * @return
     */
    public FuncionarioService getFuncionarioService() {
        return funcionarioService;
    }

    /**
     *
     * @param funcionarioModel
     */
    public void setFuncionarioModel(FuncionarioModel funcionarioModel) {
        this.funcionarioModel = funcionarioModel;
    }

    /**
     *
     * @return
     */
    public FuncionarioModel getFuncionarioModel() {
        return funcionarioModel;
    }

    /**
     *
     * @param numeroEmpleadoTemp
     */
    public void setNumeroEmpleadoTemp(String numeroEmpleadoTemp) {
        this.numeroEmpleadoTemp = numeroEmpleadoTemp;
    }

    /**
     *
     * @return
     */
    public String getNumeroEmpleadoTemp() {
        return numeroEmpleadoTemp;
    }

    /**
     *
     * @param nombreFuncionarioTemp
     */
    public void setNombreFuncionarioTemp(String nombreFuncionarioTemp) {
        this.nombreFuncionarioTemp = nombreFuncionarioTemp;
    }

    /**
     *
     * @return
     */
    public String getNombreFuncionarioTemp() {
        return nombreFuncionarioTemp;
    }

    /**
     *
     * @param correoElectronicoAlternoTemp
     */
    public void setCorreoElectronicoAlternoTemp(String correoElectronicoAlternoTemp) {
        this.correoElectronicoAlternoTemp = correoElectronicoAlternoTemp;
    }

    /**
     *
     * @return
     */
    public String getCorreoElectronicoAlternoTemp() {
        return correoElectronicoAlternoTemp;
    }

    /**
     *
     * @param correoElectronicoFuncionarioTemp
     */
    public void setCorreoElectronicoFuncionarioTemp(String correoElectronicoFuncionarioTemp) {
        this.correoElectronicoFuncionarioTemp = correoElectronicoFuncionarioTemp;
    }

    /**
     *
     * @return
     */
    public String getCorreoElectronicoFuncionarioTemp() {
        return correoElectronicoFuncionarioTemp;
    }

    /**
     *
     * @param areaDeAdscripcionTemp
     */
    public void setAreaDeAdscripcionTemp(String areaDeAdscripcionTemp) {
        this.areaDeAdscripcionTemp = areaDeAdscripcionTemp;
    }

    /**
     *
     * @return
     */
    public String getAreaDeAdscripcionTemp() {
        return areaDeAdscripcionTemp;
    }

    /**
     *
     * @param cargoDescripcionTemp
     */
    public void setCargoDescripcionTemp(String cargoDescripcionTemp) {
        this.cargoDescripcionTemp = cargoDescripcionTemp;
    }
    
    /**
     *
     * @return
     */
    public String getCargoDescripcionTemp() {
        return cargoDescripcionTemp;
    }
}
