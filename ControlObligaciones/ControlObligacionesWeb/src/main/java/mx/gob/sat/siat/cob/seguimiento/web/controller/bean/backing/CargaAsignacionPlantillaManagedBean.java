package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import mx.gob.sat.siat.cob.seguimiento.br.CargarAsignarPlantillaBR;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.ComboStr;

import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.PlantillaDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.TipoDocEtapa;
import mx.gob.sat.siat.cob.seguimiento.service.otros.AdministracionFuncionariosService;
import mx.gob.sat.siat.cob.seguimiento.service.otros.MovimientosService;
import mx.gob.sat.siat.cob.seguimiento.service.otros.SGTService;
import mx.gob.sat.siat.cob.seguimiento.service.otros.ServiceCatalogos;
import mx.gob.sat.siat.cob.seguimiento.service.otros.TipoDocEtapaSGTAService;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import mx.gob.sat.siat.cob.seguimiento.utils.AbstractBaseMB;

import mx.gob.sat.siat.cob.seguimiento.web.util.CreaArchivoSistema;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;
import org.primefaces.model.UploadedFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("cargarAsignarPlantilla")
@Scope(value = "view")
public class CargaAsignacionPlantillaManagedBean extends AbstractBaseMB {
    
    public static final Integer NO_SELECTED=-1;

    @Value("#{properties['ruta.archivo.plantillas']}")
    private String rutaArchivo;

    private PlantillaDocumento plantillaPanel;

    @Autowired
    private ServiceCatalogos catalogos;
    @Autowired
    private SGTService sgtService;
    @Autowired
    private TipoDocEtapaSGTAService tipoDocEtapaSGTAService;
    @Autowired
    private CargarAsignarPlantillaBR cargaAsignacionPlantillaRules;
    @Autowired
    private AdministracionFuncionariosService administracionFuncionariosService;
    @Autowired
    private MovimientosService movimientoService;
    
    private List<CatalogoBase> cargosAdministrativos;
    private List<CatalogoBase> catalogoTipoDocumento = Collections.emptyList();
    private List<TipoDocEtapa> catalogoEtapaVigilancia = Collections.emptyList();
    private List<CatalogoBase> catalogoValorBoolean = Collections.emptyList();
    private List<CatalogoBase> catalogoMedioEnvio = Collections.emptyList();
    private List<CatalogoBase> catalogoTipoFirma = Collections.emptyList();
    private List<CatalogoBase> catalogoPlantillas = Collections.emptyList();
    private List<ComboStr> catalogoTipoMotivo = Collections.emptyList();
    private List<CatalogoBase> catalogoDiasMonto = Collections.emptyList();
    private List<CatalogoBase> nivelesEmision;
    private List<PlantillaDocumento> listaPlantillaDocumento = new ArrayList<PlantillaDocumento>();
    private List<PlantillaDocumento> listaPlantillas = new ArrayList<PlantillaDocumento>();

    private int etVigilancia;
    private int tipDocumento;
    private int idEtapaVigilancia;
    private String descripcion;
    private String booleanObligacion;
    private boolean mostrarPanelDIOT;
    private boolean mostrarPanelEtapa;
    private boolean mostrarPanelAsignar;
    private boolean mostrarPanelEliminar;
    private boolean mostrarBotonGuardar;
    private boolean mostrarPanelContinuar;
    private boolean mostrarPanelDias;
    private boolean mostrarPanelTipoMotivo;
    private PlantillaDocumento plantillaDocumento;
    private PlantillaDocumento plantillaDocumentoDTO = new PlantillaDocumento();
    private UploadedFile file;

    /**
     *
     */
    public CargaAsignacionPlantillaManagedBean() {
        super();
    }

    /**
     *
     */
   @PostConstruct
    public void init() {

        try {
            obtenerAccesoUsrEmpleados();
            autorizar(ConstantsCatalogos.IDENTIFICADOR_ASIGNARPLANTILLA);
            catalogoTipoDocumento = catalogos.consultar("tipo de documento");
            catalogoValorBoolean = catalogos.consultar("obligaciones diot");
            catalogoMedioEnvio = catalogos.consultar("medio de envio");
            catalogoTipoFirma = sgtService.getTipoFirma();
            catalogoPlantillas = sgtService.buscarPlantillasDBArca();
            catalogoTipoMotivo = sgtService.buscarTiposMotivo();
            catalogoDiasMonto = sgtService.buscarDiasMonto();
            nivelesEmision = administracionFuncionariosService.buscarNivelesEmision();
            listaPlantillas = sgtService.buscarPlantillasDocumentoArca();
            inicializarPanelesPlantilla();
        } catch (SGTServiceException ex) {
            redirectErrorPage(ex);
        } 

    }
    
    public void loadCargosAdministrativos() {
        if(getPlantillaDocumentoDTO().getNivelEmision().equals(NO_SELECTED)){
            getPlantillaDocumentoDTO().setIdCargoAdministrativo(NO_SELECTED);
        }
        cargosAdministrativos = administracionFuncionariosService.buscarCargoPorNivelEmision(getPlantillaDocumentoDTO().getNivelEmision());
    }
    /**
     *
     */
    public void inicializarPanelesPlantilla() {
        setBooleanObligacion("");
        getPlantillaDocumentoDTO().setBlnPlantillaDIOT(0);
        setMostrarPanelDIOT(false);
        setMostrarPanelEtapa(true);
        setMostrarPanelAsignar(true);
        setMostrarBotonGuardar(true);
        setMostrarPanelContinuar(false);
        setMostrarPanelDias(true);
        setMostrarPanelTipoMotivo(true);
    }

    /**
     *
     */
    public void abrirEliminar() {
        setMostrarPanelAsignar(false);
        setMostrarPanelEliminar(true);
    }

    /**
     *
     */
    public void cerrar() {
        setMostrarPanelAsignar(true);
        setMostrarPanelEliminar(false);
    }

    /**
     *
     */
    public void eliminarRegistroArca() {
        try {
            SegbMovimientoDTO dto = generarMovimiento(ConstantsCatalogos.IDENTIFICADOR_ASIGNARPLANTILLA, ConstantsCatalogos.BAJA_ASIGNARPLANTILLA, ConstantsCatalogos.BAJA_ASIGNAR_PLANTILLA_OBS);
            movimientoService.registrarMovimiento(dto);
            sgtService.actualizarEstadoPlantillaArca(plantillaPanel);
            listaPlantillas = sgtService.buscarPlantillasDocumentoArca();
            super.addMessage("Registro "+plantillaPanel.getDescripcion()+" eliminado correctamente","");
        } catch (SGTServiceException e) {
            addErrorMessage("Error al tratar de eliminar", "");
            getLogger().error(e.getMessage(), e);
        }
        setMostrarPanelAsignar(true);
        setMostrarPanelEliminar(false);
    }

    public void habilitarMultas() {
        if (getPlantillaDocumentoDTO().getBlnPlantillaDIOT() == 1) {
            setMostrarPanelTipoMotivo(false);
        } else {
            setMostrarPanelTipoMotivo(true);
        }
    }

    /**
     *
     */
    public void cancelar() {
        setMostrarBotonGuardar(true);
        setMostrarPanelContinuar(false);
    }

    /**
     *
     * @param actionEvent
     */
    public void addWarn(ActionEvent actionEvent) {
        PlantillaDocumento plantillaArca = this.getPlantillaDocumentoDTO();
        
        String tipoDoc = tipDocumento + "";
        if(tipoDoc.equals(NO_SELECTED.toString())){
            addErrorMessage("El campo tipo documento es requerido","");
            return;
        }
        if(NO_SELECTED.equals(idEtapaVigilancia)){
            addErrorMessage("El campo etapa vigilancia es requerido","");
            return;
        }
        plantillaArca.setIdEtapaVigilancia(idEtapaVigilancia);
        plantillaArca.setIdTipoDocumento(tipDocumento);
        plantillaArca.setIdTipoDias(NO_SELECTED.equals(plantillaArca.getIdTipoDias())? null: plantillaArca.getIdTipoDias() );
        try {
            if (sgtService.existePlantillaARCA(plantillaArca)){
                addWarningMessage("Ya existe una plantilla con estos datos", "Al presionar el boton Continuar, ser\u00e1 reemplazada");
                setMostrarBotonGuardar(false);
                setMostrarPanelContinuar(true);
            } else {
                guardarPlantillaArca();
            }
        } catch (SGTServiceException e) {
            addErrorMessage("Error al guardar: ", "Ocurrio un error en base de datos");
            getLogger().error(e.getMessage(), e);
        }
    }

    /**
     *
     */
    public void guardarPlantillaArca() {
        getLogger().debug("entra a guardar");

        try {
            PlantillaDocumento plantillaArca = this.getPlantillaDocumentoDTO();
            plantillaArca.setIdTipoDias(NO_SELECTED.equals(plantillaArca.getIdTipoDias())? null: plantillaArca.getIdTipoDias() );
            plantillaArca.setIdEtapaVigilancia(idEtapaVigilancia);
            plantillaArca.setIdTipoDocumento(tipDocumento);
            String[] val = plantillaArca.getDescripcion().split("-");
            plantillaArca.setIdPlantillaArca(Integer.valueOf(val[0]));
            plantillaArca.setDescripcion(val[1]);
            
           SegbMovimientoDTO dto = generarMovimiento(ConstantsCatalogos.IDENTIFICADOR_ASIGNARPLANTILLA, ConstantsCatalogos.ALTA_ASIGNARPLANTILLA, ConstantsCatalogos.ALTA_ASIGNAR_PLANTILLA_OBS);
            movimientoService.registrarMovimiento(dto);
            
            sgtService.guardarPlantillaARCA(plantillaArca);

            listaPlantillas = sgtService.buscarPlantillasDocumentoArca();
            super.addMessage("Plantilla "+plantillaArca.getDescripcion()+" agregada correctamente", "");
            cancelar();
            limpiarObjeto();
        } catch (SGTServiceException e) {
            addErrorMessage("Error al guardar: ", "Ocurrio un error en base de datos");
            getLogger().error(e.getMessage(), e);

        }
    }
    
    public void limpiarObjeto(){
        getPlantillaDocumentoDTO().setBlnPlantillaDIOT(0);
        getPlantillaDocumentoDTO().setDescripcion(null);
        getPlantillaDocumentoDTO().setIdTipoMotivo(null);
        getPlantillaDocumentoDTO().setNivelEmision(null);
        setTipDocumento(0);
        setIdEtapaVigilancia(0);
        getPlantillaDocumentoDTO().setIdTipoDias(null);
        getPlantillaDocumentoDTO().setIdMedioEnvio(null);
        getPlantillaDocumentoDTO().setIdTipoFirma(null);
        getPlantillaDocumentoDTO().setIdCargoAdministrativo(null);
    }

    /**
     *
     */
    public void guardar() {
        try {

            if (file != null) {

                if (!cargaAsignacionPlantillaRules.validarArchivo(file.getFileName())) {
                    addErrorMessage("Error al tratar de guardar, favor de cargar un archivo v\u00e1lido", "");
                    return;
                }
                this.plantillaDocumento = new PlantillaDocumento();
                getLogger().debug("nombreArchivo:" + rutaArchivo + file.getFileName());
                getLogger().debug("Desc:" + descripcion);
                getLogger().debug("Etapa:" + idEtapaVigilancia);
                getLogger().debug("TipoDoc:" + tipDocumento);
                getLogger().debug("booleanObligacion:" + booleanObligacion);
                plantillaDocumento.setDescripcion(descripcion);
                plantillaDocumento.setEtapaVigilancia(idEtapaVigilancia + "");
                plantillaDocumento.setFechaFin(new Date());
                plantillaDocumento.setFechaInicio(new Date());
                plantillaDocumento.setEsPlantilla(booleanObligacion.equals("1"));
                plantillaDocumento.setRutaArchivo(rutaArchivo + file.getFileName());
                plantillaDocumento.setTipoDocumento(tipDocumento + "");
                plantillaDocumento.setFile(file.getFileName());

                int idPlantillaMax = this.sgtService.guardaPlantilla(this.plantillaDocumento);
                CreaArchivoSistema.crearArchivoEnSistema(file, idPlantillaMax, rutaArchivo);

                FacesContext facesContext = FacesContext.getCurrentInstance();
                FacesMessage msg = new FacesMessage("Archivo: " + file.getFileName() + ", cargado correctamente.");
                facesContext.addMessage(null, msg);
                this.listaPlantillaDocumento = new ArrayList<PlantillaDocumento>();
                List<PlantillaDocumento> docs = sgtService.buscarPlantillasDocumento();

                for (PlantillaDocumento doc : docs) {
                    String nombre = doc.getRutaArchivo();
                    String nombreAux = nombre.substring(rutaArchivo.length(), nombre.length());
                    doc.setRutaArchivo(nombreAux);
                    this.listaPlantillaDocumento.add(doc);
                }

                setIdEtapaVigilancia(NO_SELECTED);
                setBooleanObligacion(NO_SELECTED.toString());
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al tratar de guardar, favor de cargar un archivo",
                                ""));

            }
        } catch (SGTServiceException e) {
            getLogger().error(e.getMessage());
        }
    }

    private List<TipoDocEtapa> obtenerEtapaVigilanciaPorTipoDocumento(String tipDocumento) {

        List<TipoDocEtapa> catalogo = Collections.emptyList();
        getLogger().debug("obtenerEtapaVig:" + tipDocumento);
        try {
            catalogo = tipoDocEtapaSGTAService.consultarTipoDocEtapa(tipDocumento);

            if (tipDocumento.equals("3")) {//req renuente

                setMostrarPanelEtapa(false);

                setMostrarPanelDias(true);
            } else if (tipDocumento.equals("1")) {//req normal

                setMostrarPanelEtapa(false);

                setMostrarPanelDias(true);

            } else if (tipDocumento.equals("5")) {//multa
                setMostrarPanelEtapa(false);

                setMostrarPanelDias(true);
            } else if (tipDocumento.equals("4")) {// req. multa

                setMostrarPanelEtapa(false);

                setMostrarPanelDias(true);
            } else {

                setMostrarPanelEtapa(true);

                setMostrarPanelDias(true);
            }
        } catch (SGTServiceException e) {
            getLogger().error(e.getMessage());
        }

        return catalogo;
    }

    /**
     *
     */
    public void handleTipoDocumento() {
        String tipoDoc = tipDocumento + "";

        setIdEtapaVigilancia(NO_SELECTED);
        getPlantillaDocumentoDTO().setIdTipoDias(NO_SELECTED);
        
        if (tipoDoc != null && !tipoDoc.isEmpty()) {
            catalogoEtapaVigilancia = this.obtenerEtapaVigilanciaPorTipoDocumento(tipoDoc);
        }
        if (!tipoDoc.equals("1") || !tipoDoc.equals("4")) {
            setMostrarPanelDias(true);
     
        }
    }

    /**
     *
     */
    public void handleEtapaVigilancia() {
        String etapaVigilancia = idEtapaVigilancia + "";
        String tipoDoc = tipDocumento + "";
        
        if (tipoDoc.equals("1") || tipoDoc.equals("4")) {
            if (etapaVigilancia.equals("4")) {
                setMostrarPanelDias(false);
            } else {
                setMostrarPanelDias(true);
            }
        } else {
            setMostrarPanelDias(true);
        }
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
     * @param catalogoEtapaVigilancia
     */
    public void setCatalogoEtapaVigilancia(List<TipoDocEtapa> catalogoEtapaVigilancia) {
        this.catalogoEtapaVigilancia = catalogoEtapaVigilancia;
    }

    /**
     *
     * @return
     */
    public List<TipoDocEtapa> getCatalogoEtapaVigilancia() {
        return catalogoEtapaVigilancia;
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
     * @param etVigilancia
     */
    public void setEtVigilancia(int etVigilancia) {
        this.etVigilancia = etVigilancia;
    }

    /**
     *
     * @return
     */
    public int getEtVigilancia() {
        return etVigilancia;
    }

    /**
     *
     * @param tipDocumento
     */
    public void setTipDocumento(int tipDocumento) {
        this.tipDocumento = tipDocumento;
    }

    /**
     *
     * @return
     */
    public int getTipDocumento() {
        return tipDocumento;
    }

    /**
     *
     * @param descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     *
     * @return
     */
    public String getDescripcion() {
        return descripcion;
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
     * @param plantillaDocumento
     */
    public void setPlantillaDocumento(PlantillaDocumento plantillaDocumento) {
        this.plantillaDocumento = plantillaDocumento;
    }

    /**
     *
     * @return
     */
    public PlantillaDocumento getPlantillaDocumento() {
        return plantillaDocumento;
    }

    /**
     *
     * @param listaPlantillaDocumento
     */
    public void setListaPlantillaDocumento(List<PlantillaDocumento> listaPlantillaDocumento) {
        this.listaPlantillaDocumento = listaPlantillaDocumento;
    }

    /**
     *
     * @return
     */
    public List<PlantillaDocumento> getListaPlantillaDocumento() {
        return listaPlantillaDocumento;
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
     * @param booleanObligacion
     */
    public void setBooleanObligacion(String booleanObligacion) {
        this.booleanObligacion = booleanObligacion;
    }

    /**
     *
     * @return
     */
    public String getBooleanObligacion() {
        return booleanObligacion;
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
     * @param mostrarPanelDIOT
     */
    public void setMostrarPanelDIOT(boolean mostrarPanelDIOT) {
        this.mostrarPanelDIOT = mostrarPanelDIOT;
    }

    /**
     *
     * @return
     */
    public boolean isMostrarPanelDIOT() {
        return mostrarPanelDIOT;
    }

    /**
     *
     * @param mostrarPanelEtapa
     */
    public void setMostrarPanelEtapa(boolean mostrarPanelEtapa) {
        this.mostrarPanelEtapa = mostrarPanelEtapa;
    }

    /**
     *
     * @return
     */
    public boolean isMostrarPanelEtapa() {
        return mostrarPanelEtapa;
    }

    /**
     *
     * @return
     */
    public UploadedFile getFile() {
        return file;
    }

    /**
     *
     * @param file
     */
    public void setFile(UploadedFile file) {
        this.file = file;
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
     * @param catalogoPlantillas
     */
    public void setCatalogoPlantillas(List<CatalogoBase> catalogoPlantillas) {
        this.catalogoPlantillas = catalogoPlantillas;
    }

    /**
     *
     * @return
     */
    public List<CatalogoBase> getCatalogoPlantillas() {
        return catalogoPlantillas;
    }

    /**
     *
     * @param listaPlantillas
     */
    public void setListaPlantillas(List<PlantillaDocumento> listaPlantillas) {
        this.listaPlantillas = listaPlantillas;
    }

    /**
     *
     * @return
     */
    public List<PlantillaDocumento> getListaPlantillas() {
        return listaPlantillas;
    }

    /**
     *
     * @param mostrarPanelAsignar
     */
    public void setMostrarPanelAsignar(boolean mostrarPanelAsignar) {
        this.mostrarPanelAsignar = mostrarPanelAsignar;
    }

    /**
     *
     * @return
     */
    public boolean isMostrarPanelAsignar() {
        return mostrarPanelAsignar;
    }

    /**
     *
     * @param mostrarPanelEliminar
     */
    public void setMostrarPanelEliminar(boolean mostrarPanelEliminar) {
        this.mostrarPanelEliminar = mostrarPanelEliminar;
    }

    /**
     *
     * @return
     */
    public boolean isMostrarPanelEliminar() {
        return mostrarPanelEliminar;
    }

    /**
     *
     * @param plantillaPanel
     */
    public void setPlantillaPanel(PlantillaDocumento plantillaPanel) {
        this.plantillaPanel = plantillaPanel;
    }

    /**
     *
     * @return
     */
    public PlantillaDocumento getPlantillaPanel() {
        return plantillaPanel;
    }

    /**
     *
     * @param plantillaDocumentoDTO
     */
    public void setPlantillaDocumentoDTO(PlantillaDocumento plantillaDocumentoDTO) {
        this.plantillaDocumentoDTO = plantillaDocumentoDTO;
    }

    /**
     *
     * @return
     */
    public PlantillaDocumento getPlantillaDocumentoDTO() {
        return plantillaDocumentoDTO;
    }

    /**
     *
     * @param mostrarBotonGuardar
     */
    public void setMostrarBotonGuardar(boolean mostrarBotonGuardar) {
        this.mostrarBotonGuardar = mostrarBotonGuardar;
    }

    /**
     *
     * @return
     */
    public boolean isMostrarBotonGuardar() {
        return mostrarBotonGuardar;
    }

    /**
     *
     * @param mostrarPanelContinuar
     */
    public void setMostrarPanelContinuar(boolean mostrarPanelContinuar) {
        this.mostrarPanelContinuar = mostrarPanelContinuar;
    }

    /**
     *
     * @return
     */
    public boolean isMostrarPanelContinuar() {
        return mostrarPanelContinuar;
    }

    /**
     *
     * @param catalogoTipoMotivo
     */
    public void setCatalogoTipoMotivo(List<ComboStr> catalogoTipoMotivo) {
        this.catalogoTipoMotivo = catalogoTipoMotivo;
    }

    /**
     *
     * @return
     */
    public List<ComboStr> getCatalogoTipoMotivo() {
        return catalogoTipoMotivo;
    }

    /**
     *
     * @param catalogoDiasMonto
     */
    public void setCatalogoDiasMonto(List<CatalogoBase> catalogoDiasMonto) {
        this.catalogoDiasMonto = catalogoDiasMonto;
    }

    /**
     *
     * @return
     */
    public List<CatalogoBase> getCatalogoDiasMonto() {
        return catalogoDiasMonto;
    }

    /**
     *
     * @param mostrarPanelDias
     */
    public void setMostrarPanelDias(boolean mostrarPanelDias) {
        this.mostrarPanelDias = mostrarPanelDias;
    }

    /**
     *
     * @return
     */
    public boolean isMostrarPanelDias() {
        return mostrarPanelDias;
    }

    /**
     *
     * @param mostrarPanelTipoMotivo
     */
    public void setMostrarPanelTipoMotivo(boolean mostrarPanelTipoMotivo) {
        this.mostrarPanelTipoMotivo = mostrarPanelTipoMotivo;
    }

    /**
     *
     * @return
     */
    public boolean isMostrarPanelTipoMotivo() {
        return mostrarPanelTipoMotivo;
    }

    public void setNivelesEmision(List<CatalogoBase> nivelesEmision) {
        this.nivelesEmision = nivelesEmision;
    }

    public List<CatalogoBase> getNivelesEmision() {
        return nivelesEmision;
    }

    public void setCargosAdministrativos(List<CatalogoBase> cargosAdministrativos) {
        this.cargosAdministrativos = cargosAdministrativos;
    }

    public List<CatalogoBase> getCargosAdministrativos() {
        return cargosAdministrativos;
    }
   public int getNotSelected(){
       return NO_SELECTED.intValue();
   }
}
