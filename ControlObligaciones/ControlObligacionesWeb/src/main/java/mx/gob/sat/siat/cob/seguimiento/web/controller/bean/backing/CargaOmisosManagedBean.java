package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import javax.faces.context.FacesContext;

import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.TareaDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.CargaVigilancia;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleCarga;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoMedioEnvioEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.otros.SGTService;
import mx.gob.sat.siat.cob.seguimiento.service.otros.ServiceCatalogos;
import mx.gob.sat.siat.cob.seguimiento.service.renuente.PeriodoXPeriodicidadService;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import mx.gob.sat.siat.cob.seguimiento.utils.AbstractBaseMB;
import mx.gob.sat.siat.cob.seguimiento.web.controller.bean.support.UploadHandler;
import mx.gob.sat.siat.cob.seguimiento.web.util.validador.ValidadorRegistro;
import mx.gob.sat.siat.exception.AccesoDenegadoException;
import mx.gob.sat.siat.util.MovimientoUtil;
import org.primefaces.context.RequestContext;

import org.primefaces.event.FileUploadEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 */
@Controller("cargaOmisos")
@Scope(value = "view")
public class CargaOmisosManagedBean extends AbstractBaseMB {

    @Value("#{properties['ruta.archivo.repobitacoras']}")
    private String rutaRepoBitacoraErrores;
    @Value("#{properties['ruta.archivo.repositorio']}")
    private String rutaRepositorio;
    @SuppressWarnings("compatibility:-1737564122481946159")
    private static final long serialVersionUID = 1L;
    @Autowired
    private transient ServiceCatalogos catalogos;
    @Autowired
    private transient PeriodoXPeriodicidadService periodoService;
    @Autowired
    private transient SGTService sgtService;
    private TareaDTO dto;
    private CargaVigilancia vigilancia;
    private List<BeanCargaOmisos> listadoCargaOmisos = new ArrayList<BeanCargaOmisos>();
    private List<CatalogoBase> catalogoTipoDocumento = Collections.emptyList();
    private List<CatalogoBase> catalogoMedioEnvio = Collections.emptyList();
    private List<CatalogoBase> catalogoTipoFirma = Collections.emptyList();
    private List<CatalogoBase> catalogoEtapaVigilancia = Collections.emptyList();
    private List<CatalogoBase> catalogoEstadoVigilancia = Collections.emptyList();
    private List<CatalogoBase> catalogoObliSancion = Collections.emptyList();
    private List<CatalogoBase> catalogoObligacion = Collections.emptyList();
    private List<CatalogoBase> catalogoPlantilla = Collections.emptyList();
    private List<CatalogoBase> catalogoTransicionEtapa = Collections.emptyList();
    private List<CatalogoBase> catalogoEstadoDocto = Collections.emptyList();
    private List<CatalogoBase> catalogoCausaCancelDoc = Collections.emptyList();
    private List<CatalogoBase> catalogoTransicionDoc = Collections.emptyList();
    private List<CatalogoBase> catalogoPeriodicidad = Collections.emptyList();
    private List<CatalogoBase> catalogoPeriodo = Collections.emptyList();
    private List<CatalogoBase> catalogoDescripcion = Collections.emptyList();
    private List<CatalogoBase> catalogoNivelEmision = Collections.emptyList();
    private List<CatalogoBase> catalogoCargoAdmtvo = Collections.emptyList();
    private List<CatalogoBase> catalogoEjercicioFiscal = Collections.emptyList();
    private List<CatalogoBase> catalogoRegimen = Collections.emptyList();
    private List<CatalogoBase> catalogoPeriodoXPeriodicidad = Collections.emptyList();
    private Map<Integer,List<CatalogoBase>> catalogoCargosAdmtvos = new HashMap<Integer,List<CatalogoBase>>();
    private int progress;
    /**
     *
     */
    public CargaOmisosManagedBean() {
        super();
    }
    
    /**
     *
     */
    @PostConstruct
    public void init() {
        super.obtenerAccesoUsrEmpleados();
        autorizar(ConstantsCatalogos.IDENTIFICADOR_CARGAOMISOS);
        try {
            this.vigilancia = new CargaVigilancia();
            this.vigilancia.setUsuario(getAccesoUsr());
            this.vigilancia.setIdMovimiento(ConstantsCatalogos.GUARDA_CARGAOMISOS);
            catalogoTipoDocumento = catalogos.consultar("tipo de documento omiso");
            catalogoMedioEnvio = catalogos.consultar("medio de envio");
            catalogoTipoFirma = sgtService.getTipoFirma();
            setCatalogoNivelEmision(catalogos.consultar("nivel emision"));
            setCatalogoCargosAdmtvos(catalogos.consultar("cargo admtvo"));
            catalogoDescripcion = catalogos.consultar("descripcion");

            catalogoObligacion = catalogos.consultar("obligacion");
            catalogoRegimen = catalogos.consultar("regimen");
            catalogoEjercicioFiscal = catalogos.consultar("ejercicio fiscal");
            catalogoPeriodo = catalogos.consultar("periodo");
            catalogoPeriodicidad = catalogos.consultar("periodicidad");
            catalogoPeriodoXPeriodicidad = periodoService.catalogoTodosLosPeriodos();

            Collections.sort(catalogoObligacion);
            Collections.sort(catalogoRegimen);
            Collections.sort(catalogoEjercicioFiscal);
            Collections.sort(catalogoPeriodo);

            ValidadorRegistro.setCatalogoObligacion(catalogoObligacion);
            ValidadorRegistro.setCatalogoRegimen(catalogoRegimen);
            ValidadorRegistro.setCatalogoEjercicioFiscal(catalogoEjercicioFiscal);
            ValidadorRegistro.setCatalogoPeriodo(catalogoPeriodo);
            ValidadorRegistro.setCatalogoPeriodicidad(catalogoPeriodicidad);
            ValidadorRegistro.setCatalogoPeriodoXPeriodicidad(catalogoPeriodoXPeriodicidad);

            getLogger().debug("accesoUsr:" + getAccesoUsr());
            dto = new TareaDTO(vigilancia.getUsuario().getNombreCompleto().split(" ")[0]);
            getLogger().info("Se cargo correctamente MB...");
            getLogger().info(rutaRepositorio);
            getLogger().info(rutaRepoBitacoraErrores);
        } catch (SGTServiceException e) {
            getLogger().error(e);
            addErrorMessage("Error al tratar de inicializar m\u00F3dulo de carga omisos", null);
        }
    }


    /**
     *
     * @param event
     */
    public void handleFileUpload(FileUploadEvent event) {
        UploadHandler upHandler = new UploadHandler(this.dto, rutaRepositorio, rutaRepoBitacoraErrores);
        BeanCargaOmisos bean=null;
        DetalleCarga detalle=null;
        try {
            detalle = upHandler.handleFileUpload(
                    event.getFile().getFileName(), event.getFile().getInputstream(),"");
            getLogger().info("pasa set");
            bean = new BeanCargaOmisos(detalle);
        } catch (IOException ex) {
            getLogger().error(ex);
        }
        getLogger().info("agrega a listacargaomisos");
        this.listadoCargaOmisos.add(bean);
        RequestContext.getCurrentInstance().reset(":formaUploader:pnluploader");
        RequestContext.getCurrentInstance().reset(":formaGeneral:panelArchivosOmisos");
    }

    /**
     *
     * @return
     */
    public String guardar() {
        String identificador;
        try {
            this.vigilancia.setDetalleCarga(this.listadoCargaOmisos);
            if (camposObligatoriosCompletos()) {
                identificador = this.sgtService.guardaVigilancia(this.vigilancia,
                        MovimientoUtil.addMovimiento(getSession(),
                                ConstantsCatalogos.CVE_SISTEMA,
                                ConstantsCatalogos.IDENTIFICADOR_CARGAOMISOS, new Date(), new Date(),
                                ConstantsCatalogos.GUARDA_CARGAOMISOS, ConstantsCatalogos.CARGAOMISOS_OBS)
                );
                addMessage("Guardado exitoso, el ID de vigilancia es: " + identificador, null);
                renuevaProceso();
            }
        }
        catch (AccesoDenegadoException e) {
            getLogger().error(e);
            addErrorMessage("Error al tratar de guardar", null);
        } 
        catch (SGTServiceException e) {
            getLogger().error(e);
            addErrorMessage("Error al tratar de guardar", null);
        }
        return null;
    }

    /**
     *
     * @return
     */
    private boolean camposObligatoriosCompletos() {
        int idTipoDoc = this.vigilancia.getTipoDoc().getId();
        int idMedio = this.vigilancia.getMedio().getId();
        int idFirma = this.vigilancia.getFirma().getId();
        int sumaValidaciones = 0;
        int registrosUploaded = this.vigilancia.getDetalleCarga().size();
        if (idTipoDoc == 0) {
            addErrorMessage("Se debe seleccionar tipo de documento", null);
            sumaValidaciones++;
        }
        if (idFirma == 0 && idMedio!=TipoMedioEnvioEnum.ENTIDAD_FEDERATIVA.getValor()) {
            addErrorMessage("Se debe seleccionar tipo de firma", null);
            sumaValidaciones++;
        }
        if (idMedio == 0) {
            addErrorMessage("Se debe seleccionar medio de env\u00EDo", null);
            sumaValidaciones++;
        }
        if (registrosUploaded <= 0) {
            addErrorMessage("Se debe cargar por lo menos un archivo de omisos", null);
            sumaValidaciones++;
        }
        sumaValidaciones = camposObligatoriosSiguientesCompletos(sumaValidaciones,idMedio);
        if (sumaValidaciones > 0) {
            return false;
        }
        return true;
    }

    /**
     *
     * @param sumaValidaciones
     * @return
     */
    private int camposObligatoriosSiguientesCompletos(int sumaValidaciones,int idMedio) {
        int suma = sumaValidaciones;
        int idDescripcionVigilancia = this.vigilancia.getIdDescripcionVigilancia();
        int idNivelEmision = this.vigilancia.getIdNivelEmision();
        int idCargoAdmtvo = this.vigilancia.getIdCargoAdmtvo();
        Date fechaCorte = this.vigilancia.getFechaCorte();
        if (idDescripcionVigilancia == 0) {
            addErrorMessage("Se debe seleccionar una descripci\u00f3n", null);
            suma++;
        }
        if (!this.listadoCargaOmisos.isEmpty() && sonTodosRegistrosErroneos()) {
            addErrorMessage("Algunos archivos cargados son inv\u00E1lidos, no hay nada que procesar", null);
            suma++;
        }
        if (!this.listadoCargaOmisos.isEmpty() && validarCodificacion()) {
            addErrorMessage("Algunos archivos cargados son inv\u00E1lidos, la codificaci\u00f3n debe ser UTF-8 o ANSI", null);
            suma++;
        }
        if (fechaCorte == null) {
            addErrorMessage("Se debe seleccionar una fecha de corte", null);
            suma++;
        }
        if (idNivelEmision == 0 && idMedio!=TipoMedioEnvioEnum.ENTIDAD_FEDERATIVA.getValor()) {
            addErrorMessage("Se debe seleccionar un nivel de emisi\u00f3n", null);
            suma++;
        }
        if (idCargoAdmtvo == 0 && idMedio!=TipoMedioEnvioEnum.ENTIDAD_FEDERATIVA.getValor()) {
            addErrorMessage("Se debe seleccionar un cargo administrativo", null);
            suma++;
        }
        return suma;
    }

    /**
     *
     * @return
     */
    private boolean sonTodosRegistrosErroneos() {
        int numRegConErrores = 0;
        for (BeanCargaOmisos b : this.listadoCargaOmisos) {
            if (b.isCargaInvalida()) {
                numRegConErrores++;
            }
        }
        return (numRegConErrores > 0);
    }
    
    /**
     *
     * @return
     */
    private boolean validarCodificacion() {
        int numRegConErrores = 0;
        for (BeanCargaOmisos b : this.listadoCargaOmisos) {
            if (!b.isCodificacionCorrecta()) {
                numRegConErrores++;
            }
        }
        return (numRegConErrores > 0);
    }

    /**
     *
     */
    private void renuevaProceso() {
        this.vigilancia = new CargaVigilancia();
        this.vigilancia.setUsuario(getAccesoUsr());
        this.vigilancia.setIdMovimiento(ConstantsCatalogos.GUARDA_CARGAOMISOS);
        this.listadoCargaOmisos = new ArrayList<BeanCargaOmisos>();
    }

    /*
     * borra archivos de la lista carga omisos
     */
    private void borraArchivos() {
        boolean isBorrado;
        for (BeanCargaOmisos dato : this.listadoCargaOmisos) {
            String dirBitacora = dato.getRutaEnBitacora();
            String dirRepositorio = dato.getRutaEnRepositorio();
            if (dirBitacora != null && !dirBitacora.isEmpty()) {
                File archivoBitacora = new File(dirBitacora);
                if (archivoBitacora.exists()) {
                    isBorrado = archivoBitacora.delete();
                    getLogger().debug(isBorrado);
                }
            }
            if (dirRepositorio != null && !dirRepositorio.isEmpty()) {
                File archivoRepositorio = new File(dirRepositorio);
                if (archivoRepositorio.exists()) {
                    isBorrado = archivoRepositorio.delete();
                    getLogger().debug(isBorrado);
                }
            }
        }
    }

    /**
     *
     * @return
     */
    public String cancelar() {
        borraArchivos();
        renuevaProceso();
        addMessage("Carga Cancelada", null);
        return null;
    }
    /**
     * para restear los campos de la pagina al cancelar
     */
    public void resetFail() {
        borraArchivos();
        renuevaProceso();
        addMessage("Carga Cancelada", null);
        RequestContext.getCurrentInstance().reset(":frmSup:panelCatalogos");
    }

    public void eliminarArchivoSeleccionado() {
        getLogger().debug("--------------------------------------------------------");
        boolean isBorrado;
        Map<String, String> params = FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap();
        String archivo = params.get("archivo");
        getLogger().debug(archivo);
        BeanCargaOmisos datoEliminar = null;
        for (BeanCargaOmisos dato : this.listadoCargaOmisos) {
            if (dato.getNombreOriginalArchivo().equals(archivo)) {
                datoEliminar = dato;
                break;
            }
        }
        if (datoEliminar != null) {
            this.listadoCargaOmisos.remove(datoEliminar);
            getLogger().debug(datoEliminar.getRutaEnBitacora());
            getLogger().debug(datoEliminar.getRutaEnRepositorio());
            File archivoBitacora = new File(
                    datoEliminar.getRutaEnBitacora() == null ? "" : datoEliminar.getRutaEnBitacora());
            if (archivoBitacora.exists()) {
                isBorrado = archivoBitacora.delete();
                getLogger().debug(isBorrado);
            }
            File archivoRepositorio = new File(
                    datoEliminar.getRutaEnRepositorio() == null ? "" : datoEliminar.getRutaEnRepositorio());
            if (archivoRepositorio.exists()) {
                isBorrado = archivoRepositorio.delete();
                getLogger().debug(isBorrado);
            }
        }
    }
    
    public void handleLevelChange() {
        if(vigilancia.getIdNivelEmision() != 0){
            catalogoCargoAdmtvo = catalogoCargosAdmtvos.get( vigilancia.getIdNivelEmision() );
        }else{
            catalogoCargoAdmtvo = Collections.emptyList();
        }
    }

    /**
     *
     * @param vigilancia
     */
    public void setVigilancia(CargaVigilancia vigilancia) {
        this.vigilancia = vigilancia;
    }

    /**
     *
     * @return
     */
    public CargaVigilancia getVigilancia() {
        return vigilancia;
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
     * @param catalogoMedioEnvio
     */
    public void setCatalogoMedioEnvio(List<CatalogoBase> catalogoMedioEnvio) {
        this.catalogoMedioEnvio = catalogoMedioEnvio;
    }

    /**
     *
     * @return
     */
    public List<CatalogoBase> getCatalogoMedioEnvio() {
        return catalogoMedioEnvio;
    }

    /**
     *
     * @param catalogoTipoFirma
     */
    public void setCatalogoTipoFirma(List<CatalogoBase> catalogoTipoFirma) {
        this.catalogoTipoFirma = catalogoTipoFirma;
    }

    /**
     *
     * @return
     */
    public List<CatalogoBase> getCatalogoTipoFirma() {
        return catalogoTipoFirma;
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
     * @param listadoCargaOmisos
     */
    public void setListadoCargaOmisos(List<BeanCargaOmisos> listadoCargaOmisos) {
        this.listadoCargaOmisos = listadoCargaOmisos;
    }

    /**
     *
     * @return
     */
    public List<BeanCargaOmisos> getListadoCargaOmisos() {
        return listadoCargaOmisos;
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
     * @param catalogoEtapaVigilancia
     */
    public void setCatalogoEtapaVigilancia(List<CatalogoBase> catalogoEtapaVigilancia) {
        this.catalogoEtapaVigilancia = catalogoEtapaVigilancia;
    }

    /**
     *
     * @return
     */
    public List<CatalogoBase> getCatalogoEtapaVigilancia() {
        return catalogoEtapaVigilancia;
    }

    /**
     *
     * @param catalogoEstadoVigilancia
     */
    public void setCatalogoEstadoVigilancia(List<CatalogoBase> catalogoEstadoVigilancia) {
        this.catalogoEstadoVigilancia = catalogoEstadoVigilancia;
    }

    /**
     *
     * @return
     */
    public List<CatalogoBase> getCatalogoEstadoVigilancia() {
        return catalogoEstadoVigilancia;
    }

    /**
     *
     * @param catalogoObliSancion
     */
    public void setCatalogoObliSancion(List<CatalogoBase> catalogoObliSancion) {
        this.catalogoObliSancion = catalogoObliSancion;
    }

    /**
     *
     * @return
     */
    public List<CatalogoBase> getCatalogoObliSancion() {
        return catalogoObliSancion;
    }

    /**
     *
     * @param catalogoObligacion
     */
    public void setCatalogoObligacion(List<CatalogoBase> catalogoObligacion) {
        this.catalogoObligacion = catalogoObligacion;
    }

    /**
     *
     * @return
     */
    public List<CatalogoBase> getCatalogoObligacion() {
        return catalogoObligacion;
    }

    /**
     *
     * @param catalogoPlantilla
     */
    public void setCatalogoPlantilla(List<CatalogoBase> catalogoPlantilla) {
        this.catalogoPlantilla = catalogoPlantilla;
    }

    /**
     *
     * @return
     */
    public List<CatalogoBase> getCatalogoPlantilla() {
        return catalogoPlantilla;
    }

    /**
     *
     * @param catalogoTransicionEtapa
     */
    public void setCatalogoTransicionEtapa(List<CatalogoBase> catalogoTransicionEtapa) {
        this.catalogoTransicionEtapa = catalogoTransicionEtapa;
    }

    /**
     *
     * @return
     */
    public List<CatalogoBase> getCatalogoTransicionEtapa() {
        return catalogoTransicionEtapa;
    }

    /**
     *
     * @param catalogoEstadoDocto
     */
    public void setCatalogoEstadoDocto(List<CatalogoBase> catalogoEstadoDocto) {
        this.catalogoEstadoDocto = catalogoEstadoDocto;
    }

    /**
     *
     * @return
     */
    public List<CatalogoBase> getCatalogoEstadoDocto() {
        return catalogoEstadoDocto;
    }

    /**
     *
     * @param catalogoCausaCancelDoc
     */
    public void setCatalogoCausaCancelDoc(List<CatalogoBase> catalogoCausaCancelDoc) {
        this.catalogoCausaCancelDoc = catalogoCausaCancelDoc;
    }

    /**
     *
     * @return
     */
    public List<CatalogoBase> getCatalogoCausaCancelDoc() {
        return catalogoCausaCancelDoc;
    }

    /**
     *
     * @param catalogoTransicionDoc
     */
    public void setCatalogoTransicionDoc(List<CatalogoBase> catalogoTransicionDoc) {
        this.catalogoTransicionDoc = catalogoTransicionDoc;
    }

    /**
     *
     * @return
     */
    public List<CatalogoBase> getCatalogoTransicionDoc() {
        return catalogoTransicionDoc;
    }

    /**
     *
     * @param catalogoPeriodicidad
     */
    public void setCatalogoPeriodicidad(List<CatalogoBase> catalogoPeriodicidad) {
        this.catalogoPeriodicidad = catalogoPeriodicidad;
    }

    /**
     *
     * @return
     */
    public List<CatalogoBase> getCatalogoPeriodicidad() {
        return catalogoPeriodicidad;
    }

    /**
     *
     * @param catalogoPeriodo
     */
    public void setCatalogoPeriodo(List<CatalogoBase> catalogoPeriodo) {
        this.catalogoPeriodo = catalogoPeriodo;
    }

    /**
     *
     * @return
     */
    public List<CatalogoBase> getCatalogoPeriodo() {
        return catalogoPeriodo;
    }

    /**
     *
     * @return
     */
    public List<CatalogoBase> getCatalogoDescripcion() {
        return catalogoDescripcion;
    }
    /**
     *
     * @param catalogoDescripcion
     */
    public void setCatalogoDescripcion(List<CatalogoBase> catalogoDescripcion) {
        this.catalogoDescripcion = catalogoDescripcion;
    }
    /**
     *
     * @return
     */
    public List<CatalogoBase> getCatalogoNivelEmision() {
        return catalogoNivelEmision;
    }
    /**
     *
     * @param catalogoNivelEmision
     */
    public void setCatalogoNivelEmision(List<CatalogoBase> catalogoNivelEmision) {
        this.catalogoNivelEmision = catalogoNivelEmision;
    }
    /**
     *
     * @return
     */
    public List<CatalogoBase> getCatalogoCargoAdmtvo() {
        return catalogoCargoAdmtvo;
    }
    /**
     *
     * @param catalogoCargoAdmtvo
     */
    public void setCatalogoCargoAdmtvo(List<CatalogoBase> catalogoCargoAdmtvo) {
        this.catalogoCargoAdmtvo = catalogoCargoAdmtvo;
    }
    /**
     *
     * @return
     */
    public Map<Integer, List<CatalogoBase>> getCatalogoCargosAdmtvos() {
        return catalogoCargosAdmtvos;
    }
    /**
     *
     * @return
     */
    public List<CatalogoBase> getCatalogoEjercicioFiscal() {
        return catalogoEjercicioFiscal;
    }
    /**
     *
     * @param catalogoEjercicioFiscal
     */
    public void setCatalogoEjercicioFiscal(List<CatalogoBase> catalogoEjercicioFiscal) {
        this.catalogoEjercicioFiscal = catalogoEjercicioFiscal;
    }
    /**
     *
     * @return
     */
    public List<CatalogoBase> getCatalogoRegimen() {
        return catalogoRegimen;
    }
    /**
     * 
     * @param catalogoRegimen
     */
    public void setCatalogoRegimen(List<CatalogoBase> catalogoRegimen) {
        this.catalogoRegimen = catalogoRegimen;
    }
    /**
     *
     * @param catalogoCargosAdmtvos
     */
    public void setCatalogoCargosAdmtvos(List<CatalogoBase> catalogoCargosAdmtvos) {
        Map<Integer,List<CatalogoBase>> catalogo = new HashMap<Integer,List<CatalogoBase>>();
        for(CatalogoBase nivel: getCatalogoNivelEmision() ){
            List<CatalogoBase> lista= new ArrayList<CatalogoBase>();
            for(CatalogoBase cat:catalogoCargosAdmtvos){
                if( nivel.getId() == Integer.parseInt(cat.getIdAux()) ){
                    CatalogoBase tmp=new CatalogoBase();
                    tmp.setId(cat.getId());
                    tmp.setNombre(cat.getNombre());
                    lista.add(tmp);
                }
            }
            catalogo.put(nivel.getId(), lista);
        }
        this.catalogoCargosAdmtvos = catalogo;
    }
    /**
     * 
     * @return 
     */
    public int getProgress() {
        return progress;
    }
    /**
     * 
     * @param progress 
     */
    public void setProgress(int progress) {
        this.progress = progress;
    }
    /**
     * incrementa el progress
     */
    public void increment() {  
        progress++;
    }
    
}
