package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing;

import java.util.List;
import javax.annotation.PostConstruct;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.ComboStr;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.FuncionarioSat;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.otros.AdministracionFuncionariosService;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import mx.gob.sat.siat.cob.seguimiento.utils.AbstractBaseMB;
import mx.gob.sat.siat.controlador.AccesoProceso;
import mx.gob.sat.siat.exception.AccesoDenegadoException;
import mx.gob.sat.siat.exception.AccesoDenegadoFielException;
import mx.gob.sat.siat.exception.SessionRolNullException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("admonFuncionarios")
@Scope(value = "view")
public class AdministracionFuncionariosManagedBean extends AbstractBaseMB {

    @Autowired
    private AdministracionFuncionariosService administracionFuncionariosService;
    private List<FuncionarioSat> funcionarios;
    private List<FuncionarioSat> funcionariosTemp;
    private List<CatalogoBase> cargosAdministrativos;
    private List<CatalogoBase> nivelesEmision;
    private List<CatalogoBase> eventoCarga;
    private List<ComboStr> regionALR;
    private List<ComboStr> empleadosAdmtvo;
    private FuncionarioSat empleado;
    private FuncionarioSat empleadoEditar = new FuncionarioSat();
    private FuncionarioSat empleadoDTO = new FuncionarioSat();
    private FuncionarioSat empleadoAux = new FuncionarioSat();
    private boolean mostrarPanelEliminar;
    private boolean mostrarPanelTabla;
    private boolean mostrarPanelGuardar;
    private boolean mostrarPanelEditar;
    private boolean mostrarComboRegion;
    private boolean mostrarBotonGuardar;
    private boolean mostrarPanelContinuar;
    private boolean mostrarPanelHabilitarContinuar;

    public AdministracionFuncionariosManagedBean() {
        super();
    }

    @PostConstruct
    public void init() {

        try {
            obtenerAccesoUsrEmpleados();
            if (AccesoProceso.validaAccesoProceso(getSession(), "COB00028", ConstantsCatalogos.PARAMETRO_FIEL)) {
                empleadosAdmtvo = administracionFuncionariosService.obtenerEmpleadosAdministrativo();
                funcionarios = administracionFuncionariosService.buscarFuncionarios();
                nivelesEmision = administracionFuncionariosService.buscarNivelesEmision();
                regionALR = administracionFuncionariosService.buscarRegionALR();
                cerrar();
                setMostrarBotonGuardar(true);
                setMostrarPanelContinuar(false);
            }
        } catch (SGTServiceException e) {
            getLogger().error(e.getMessage());

        } catch (SessionRolNullException ex) {
            getLogger().error("Error de Sesion:" + ex.getMessage());
            manejaException(ex);
        } catch (AccesoDenegadoException ex) {
            getLogger().error("Error de acceso denegado:" + ex.getMessage());
            manejaException(ex);
        } catch (AccesoDenegadoFielException ex) {
            getLogger().error("Error de acceso denegado fiel:" + ex.getMessage());
            manejaException(ex);
        }
    }

    private void manejaException(Exception e) {
        getSession().setAttribute("mensaje", e.getMessage());
        redirigir(ConstantsCatalogos.URL_EN_CONSTRUCCION);
    }

    public void habilitarComboRegion() {
        setMostrarComboRegion((empleadoDTO.getNivelEmision() == 1));
        cargosAdministrativos =
                administracionFuncionariosService.buscarCargoPorNivelEmision(empleadoDTO.getNivelEmision());
        eventoCarga = administracionFuncionariosService.buscarEventoCarga(empleadoDTO.getNivelEmision());

    }

    public void habilitarComboRegionUpdate() {
        setMostrarComboRegion((empleadoEditar.getNivelEmision() == 1));
        cargosAdministrativos =
                administracionFuncionariosService.buscarCargoPorNivelEmision(empleadoEditar.getNivelEmision());
        eventoCarga = administracionFuncionariosService.buscarEventoCarga(empleadoEditar.getNivelEmision());
        if (empleadoEditar.getNivelEmision() != 1) {
            empleadoEditar.setIdRegionAlr(null);
        }
    }

    public void mostrarPaneles() {
        setMostrarBotonGuardar(false);
        if (mostrarPanelGuardar) {
            setMostrarPanelContinuar(true);
        }
        if (mostrarPanelEditar) {
            setMostrarPanelContinuar(false);
            setMostrarPanelHabilitarContinuar(true);
        }
        if (empleadoEditar.getFechaFin() != null) {
            setMostrarPanelContinuar(false);
            setMostrarPanelHabilitarContinuar(true);
       }
    }

    public boolean isEditar() {
        if ((mostrarPanelEditar && empleadoEditar != null && empleadoEditar.getIdEventoCarga() == 1)) {
            return true;
        }
        return false;
    }

    public boolean isGuardar() {
        if ((mostrarPanelGuardar && empleadoDTO != null && empleadoDTO.getIdEventoCarga() == 1)) {
            return true;
        }
        return false;
    }

    public void addWarn() {
        if (isEditar() || isGuardar()) {
            if (administracionFuncionariosService.validarFuncionarioExistente(mostrarPanelEditar ? empleadoEditar
                    : empleadoDTO, this.getFuncionarios())) {
                addWarningMessage("Ya existe un funcionario con estos datos Al presionar el boton Continuar, ser\u00e1 reemplazado", "");
                mostrarPaneles();

            } else {
                mostrarPaneles();
                guardar();
            }
        } else {
            mostrarPaneles();
            guardar();
        }
    }

    public void cancelar() {
        setMostrarBotonGuardar(true);
        setMostrarPanelContinuar(false);
        setMostrarPanelHabilitarContinuar(false);
        cerrar();
    }

    public void guardar() {
        if (mostrarPanelGuardar) {
            guardarEmpleado();
        }
        if (mostrarPanelEditar) {
            actualizarRegistroEmpleado();
        }
        if (empleadoEditar.getFechaFin() != null) {
            habilitarRegistroEmpleado();
        }
    }

    public void guardarEmpleado() {
        try {
            if(!administracionFuncionariosService.buscarEmpleadosNumEvento(empleadoDTO, funcionarios)){
            administracionFuncionariosService.guardarFuncionario(empleadoDTO);
            funcionarios = administracionFuncionariosService.buscarFuncionarios();
            super.addMessage("Se guardo exitosamente el empleado", "");
            }else{
                super.addErrorMessage("Ya existe un registro en base de datos con el mismo n\u00famero de empleado y el mismo evento de carga", "");
                getLogger().error("Ya existe un registro en base de datos con el mismo n\u00famero de empleado y el mismo evento de carga");
            }
            cerrar();
        } catch (SGTServiceException e) {
            setMostrarBotonGuardar(true);
            setMostrarPanelContinuar(false);
            super.addErrorMessage(e.getMessage(), "");
        }
    }

    public void actualizarRegistroEmpleado() {
        try {
            if (empleadoEditar.getFechaFin() == null) {
                administracionFuncionariosService.actualizarRegistroFuncionario(empleadoEditar);
                funcionarios = administracionFuncionariosService.buscarFuncionarios();
                funcionariosTemp = administracionFuncionariosService.buscarFuncionarios();
                cerrar();
                super.addMessage("Se guardo exitosamente el empleado", "");
            }
        } catch (SGTServiceException e) {
            cerrar();
            super.addErrorMessage(e.getMessage(), "");
            getLogger().error(e.getMessage());
        }
    }

    public void habilitarRegistroEmpleado() {
        try {
            administracionFuncionariosService.habilitarRegistroFuncionario(empleadoEditar);
            funcionarios = administracionFuncionariosService.buscarFuncionarios();
            funcionariosTemp = administracionFuncionariosService.buscarFuncionarios();
            cerrar();
        } catch (SGTServiceException e) {
            cerrar();
            super.addErrorMessage(e.getMessage(), "");
            getLogger().error(e.getMessage());
        }
    }

    public void eliminarRegistroEmpleado() {

        administracionFuncionariosService.eliminarRegistroFuncionario(empleado.getNumeroEmpleado(), empleado.getIdEventoCarga());
        funcionarios = administracionFuncionariosService.buscarFuncionarios();
        funcionariosTemp = administracionFuncionariosService.buscarFuncionarios();
        cerrar();

    }

    public void abrirGuardar() {

        setMostrarPanelGuardar(true);
        setMostrarBotonGuardar(true);
        setMostrarPanelEliminar(false);
        setMostrarPanelTabla(false);
        setMostrarPanelEditar(false);

    }

    public void abrirEditar() {
        setMostrarComboRegion((empleadoEditar.getNivelEmision() == 1));
        cargosAdministrativos =
                administracionFuncionariosService.buscarCargoPorNivelEmision(empleadoEditar.getNivelEmision());
        eventoCarga = administracionFuncionariosService.buscarEventoCarga(empleadoEditar.getNivelEmision());
        getLogger().error("valor:" + mostrarComboRegion);
        setMostrarPanelEliminar(false);
        setMostrarPanelTabla(false);
        setMostrarPanelGuardar(false);
        setMostrarPanelEditar(true);
    }

    public void abrirEliminar() {
        setMostrarPanelEliminar(true);
        setMostrarPanelTabla(false);
        setMostrarPanelGuardar(false);
        setMostrarPanelEditar(false);
    }

    public void cerrar() {
        limpiarObjeto(empleadoDTO);
        setMostrarComboRegion(false);
        setMostrarPanelTabla(true);
        setMostrarPanelEliminar(false);
        setMostrarPanelGuardar(false);
        setMostrarPanelEditar(false);
        setMostrarPanelContinuar(false);
        setMostrarPanelHabilitarContinuar(false);
    }

    private void limpiarObjeto(FuncionarioSat empleadoDTO) {
        empleadoDTO.setNumeroEmpleado(null);
        empleadoDTO.setNumeroEmpleadoSuperior(null);
        empleadoDTO.setNivelEmision(null);
        empleadoDTO.setIdCargoAdministrativo(null);
        empleadoDTO.setIdEventoCarga(null);
        empleadoDTO.setIdRegionAlr(null);
        
    }

    public void setFuncionarios(List<FuncionarioSat> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public List<FuncionarioSat> getFuncionarios() {
        return funcionarios;
    }

    public void setMostrarPanelEliminar(boolean mostrarPanelEliminar) {
        this.mostrarPanelEliminar = mostrarPanelEliminar;
    }

    public boolean isMostrarPanelEliminar() {
        return mostrarPanelEliminar;
    }

    public void setMostrarPanelTabla(boolean mostrarPanelTabla) {
        this.mostrarPanelTabla = mostrarPanelTabla;
    }

    public boolean isMostrarPanelTabla() {
        return mostrarPanelTabla;
    }

    public void setEmpleado(FuncionarioSat empleado) {
        this.empleado = empleado;
    }

    public FuncionarioSat getEmpleado() {
        return empleado;
    }

    public void setMostrarPanelGuardar(boolean mostrarPanelGuardar) {
        this.mostrarPanelGuardar = mostrarPanelGuardar;
    }

    public boolean isMostrarPanelGuardar() {
        return mostrarPanelGuardar;
    }

    public void setCargosAdministrativos(List<CatalogoBase> cargosAdministrativos) {
        this.cargosAdministrativos = cargosAdministrativos;
    }

    public List<CatalogoBase> getCargosAdministrativos() {
        return cargosAdministrativos;
    }

    public void setNivelesEmision(List<CatalogoBase> nivelesEmision) {
        this.nivelesEmision = nivelesEmision;
    }

    public List<CatalogoBase> getNivelesEmision() {
        return nivelesEmision;
    }

    public void setEventoCarga(List<CatalogoBase> eventoCarga) {
        this.eventoCarga = eventoCarga;
    }

    public List<CatalogoBase> getEventoCarga() {
        return eventoCarga;
    }

    public void setRegionALR(List<ComboStr> regionALR) {
        this.regionALR = regionALR;
    }

    public List<ComboStr> getRegionALR() {
        return regionALR;
    }

    public void setEmpleadoDTO(FuncionarioSat empleadoDTO) {
        this.empleadoDTO = empleadoDTO;
    }

    public FuncionarioSat getEmpleadoDTO() {
        return empleadoDTO;
    }

    public void setEmpleadoEditar(FuncionarioSat empleadoEditar) {
        this.empleadoEditar = empleadoEditar;
    }

    public FuncionarioSat getEmpleadoEditar() {
        return empleadoEditar;
    }

    public void setMostrarPanelEditar(boolean mostrarPanelEditar) {
        this.mostrarPanelEditar = mostrarPanelEditar;
    }

    public boolean isMostrarPanelEditar() {
        return mostrarPanelEditar;
    }

    public void setMostrarComboRegion(boolean mostrarComboRegion) {
        this.mostrarComboRegion = mostrarComboRegion;
    }

    public boolean isMostrarComboRegion() {
        return mostrarComboRegion;
    }

    public void setEmpleadosAdmtvo(List<ComboStr> empleadosAdmtvo) {
        this.empleadosAdmtvo = empleadosAdmtvo;
    }

    public List<ComboStr> getEmpleadosAdmtvo() {
        return empleadosAdmtvo;
    }

    public void setFuncionariosTemp(List<FuncionarioSat> funcionariosTemp) {
        this.funcionariosTemp = funcionariosTemp;
    }

    public List<FuncionarioSat> getFuncionariosTemp() {
        return funcionariosTemp;
    }

    public void setMostrarBotonGuardar(boolean mostrarBotonGuardar) {
        this.mostrarBotonGuardar = mostrarBotonGuardar;
    }

    public boolean isMostrarBotonGuardar() {
        return mostrarBotonGuardar;
    }

    public void setMostrarPanelContinuar(boolean mostrarPanelContinuar) {
        this.mostrarPanelContinuar = mostrarPanelContinuar;
    }

    public boolean isMostrarPanelContinuar() {
        return mostrarPanelContinuar;
    }

    public void setEmpleadoAux(FuncionarioSat empleadoAux) {
        this.empleadoAux = empleadoAux;
    }

    public FuncionarioSat getEmpleadoAux() {
        return empleadoAux;
    }

    public void setMostrarPanelHabilitarContinuar(boolean mostrarPanelHabilitarContinuar) {
        this.mostrarPanelHabilitarContinuar = mostrarPanelHabilitarContinuar;
    }

    public boolean isMostrarPanelHabilitarContinuar() {
        return mostrarPanelHabilitarContinuar;
    }
}
