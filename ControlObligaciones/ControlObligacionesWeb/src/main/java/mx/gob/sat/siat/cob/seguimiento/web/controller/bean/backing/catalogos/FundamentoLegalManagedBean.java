package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.catalogos;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;


import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.FundamentoLegal;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.TareaDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.CargaVigilancia;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleCarga;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.carga.Agrupamiento;
import mx.gob.sat.siat.cob.seguimiento.service.catalogos.FundamentoLegalService;
import mx.gob.sat.siat.cob.seguimiento.service.otros.ServiceCatalogos;
import mx.gob.sat.siat.cob.seguimiento.service.renuente.PeriodoXPeriodicidadService;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import mx.gob.sat.siat.cob.seguimiento.utils.AbstractBaseMB;
import mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.BeanCargaOmisos;
import mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.catalogos.model.FundamentoLegalModel;
import mx.gob.sat.siat.cob.seguimiento.web.controller.bean.support.UploadHandler;
import mx.gob.sat.siat.cob.seguimiento.web.util.validador.ValidadorRegistro;
import mx.gob.sat.siat.controlador.AccesoProceso;
import mx.gob.sat.siat.exception.AccesoDenegadoException;
import mx.gob.sat.siat.exception.AccesoDenegadoFielException;
import mx.gob.sat.siat.exception.DaoException;
import mx.gob.sat.siat.exception.SessionRolNullException;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;
import mx.gob.sat.siat.util.MovimientoUtil;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("catalogoFundamentoLegalMB")
@Scope("view")
public class FundamentoLegalManagedBean extends AbstractBaseMB {

    @Value("#{properties['ruta.archivo.repobitacoras']}")
    private String rutaRepoBitacoraErrores;
    @Value("#{properties['ruta.carpeta.temporal']}")
    private String rutaRepositorio;
    @Autowired
    private FundamentoLegalService fundamentoLegalService;
    @Autowired
    private transient ServiceCatalogos catalogos;
    @Autowired
    private PeriodoXPeriodicidadService periodoService;
    private static final long serialVersionUID = 21L;
    private FundamentoLegalModel fundamentoLegalModel = new FundamentoLegalModel();
    private String fundamentoTemp;
    private int progress;
    private TareaDTO dto;
    private CargaVigilancia vigilancia;
    private List<BeanCargaOmisos> listadoCargaOmisos = new ArrayList<BeanCargaOmisos>();
    private StreamedContent file;
    private boolean mostrarBotonCargaArchivo;
    private boolean archivosConErrores;
    private List<CatalogoBase> catalogoObligacion = Collections.emptyList();
    private List<CatalogoBase> catalogoRegimen = Collections.emptyList();
    private List<CatalogoBase> catalogoEjercicioFiscal = Collections.emptyList();
    private List<CatalogoBase> catalogoPeriodo = Collections.emptyList();
    private List<CatalogoBase> catalogoPeriodicidad = Collections.emptyList();
    private List<CatalogoBase> catalogoPeriodoXPeriodicidad = Collections.emptyList();

    /**
     *
     */
    public FundamentoLegalManagedBean() {
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
                    ConstantsCatalogos.IDENTIFICADOR_FUNDAMENTOLEGAL,
                    ConstantsCatalogos.PARAMETRO_FIEL)) {
                fundamentoLegalModel.setListaComboEjercicioFiscal(fundamentoLegalService.obtenerComboEjercicioFiscal());
                fundamentoLegalModel.setListFundamentoLegal(fundamentoLegalService.todosLosFundamentos());
                
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
                    
            }
        }   catch (SGTServiceException ex) {
            getSession().setAttribute("mensaje", ex.getMessage());
            redirigir(ConstantsCatalogos.URL_EN_CONSTRUCCION);
            getLogger().error(ex);
        }
        catch (SessionRolNullException e) {
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
    
    public void buscarFundamentosPorEjercicioFiscal() {
        fundamentoLegalModel.setListFundamentoLegal(fundamentoLegalService.todosLosFundamentosPorEjercicioFiscal(fundamentoLegalModel.getIdEjercicioFiscalFun()));
    }

    /**
     *
     */
    public void agrega() {
        FundamentoLegal fundamentoLegal = new FundamentoLegal();
        fundamentoLegal.setIdObligacion(fundamentoLegalModel.getIdObligacionFun());
        fundamentoLegal.setIdRegimen(fundamentoLegalModel.getIdRegimenFun());
        fundamentoLegal.setIdEjercicioFiscal(fundamentoLegalModel.getIdEjercicioFiscalFun());
        fundamentoLegal.setIdPeriodo(String.valueOf(fundamentoLegalModel.getIdPeriodoFun().charAt(0)));
        fundamentoLegal.setIdPeriodicidad(String.valueOf(fundamentoLegalModel.getIdPeriodoFun().charAt(2)));
        fundamentoLegal.setFundamentoLegal(fundamentoLegalModel.getFundamentoLegal());
        fundamentoLegal.setFechaVencimiento(fundamentoLegalModel.getFechaVencimiento());
        if (fundamentoLegalModel.getFechaVencimiento() != null) {
            fundamentoLegal.setFechaVencimientoStr(Utilerias.formatearFechaDDMMYYYY(fundamentoLegalModel.getFechaVencimiento()));
        }
        Calendar cal = Calendar.getInstance();
        fundamentoLegal.setFechaInicio(cal.getTime());

        Integer reg = fundamentoLegalService.buscarFundamentoLegalPorIdObligYIdReg(fundamentoLegal);
        if (reg == null) {
            AbstractBaseMB.msgError(ConstantsCatalogos.ERROR_CONSULTA_DB);
            return;
        }
        if (reg > 0) {
            AbstractBaseMB.msgError(ConstantsCatalogos.REGISTRO_DUPLICADO);
            return;
        }

        try {
            SegbMovimientoDTO dtoSeg = MovimientoUtil.addMovimiento(getSession(), ConstantsCatalogos.CVE_SISTEMA , ConstantsCatalogos.IDENTIFICADOR_FUNDAMENTOLEGAL,new Date(),new Date(),ConstantsCatalogos.ALTA_MOV_FUNDAMENTOLEGAL,ConstantsCatalogos.ALTA_FUNDAMENTOLEGAL_OBS);
            fundamentoLegalService.agregaFundamentoLegal(fundamentoLegal, dtoSeg);
            fundamentoLegalModel.setListFundamentoLegal(fundamentoLegalService.todosLosFundamentos());

            cerrar();
            try{
            reloadPage();
            }catch(IOException e){
                getLogger().error("No se pudo cargar la pagina correctamente",e);
            }
            AbstractBaseMB.msgInfo(ConstantsCatalogos.REGISTROEXITOSO);

        } catch (AccesoDenegadoException e) {
            getLogger().error(e.getMessage());
        } catch (SeguimientoDAOException e) {
            getLogger().error(e.getMessage());
        } catch (DaoException e) {
            getLogger().error(e.getMessage());
        }
    }

    public void reloadPage() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("fundamentoLegal.jsf");
    }

    public void reloadCombo() {
        fundamentoLegalModel.setIdEjercicioFiscalFun(fundamentoLegalModel.getListaComboEjercicioFiscal().get(0).getIdCombo());
        RequestContext.getCurrentInstance().update("formGral:EjercicioFiscalCombo");
    }
    
    public void reloadPanel() {
        fundamentoLegalModel.setListFundamentoLegal(fundamentoLegalService.todosLosFundamentos());
        RequestContext.getCurrentInstance().update("formGral:pnlTbl");
    }

    /**
     *
     */
    public void edita() {

        try {
            SegbMovimientoDTO dtoSeg = MovimientoUtil.addMovimiento(getSession(), 
                    ConstantsCatalogos.CVE_SISTEMA, 
                    ConstantsCatalogos.IDENTIFICADOR_FUNDAMENTOLEGAL,
                    new Date(),new Date(),
                    ConstantsCatalogos.MODIFICACION_MOV_FUNDAMENTOLEGAL,
                    ConstantsCatalogos.MODIFICACION_FUNDAMENTOLEGAL_OBS);
            fundamentoLegalService.editaFundamentoLegal(fundamentoLegalModel.getFundamentoLegalEdit(), dtoSeg);
            fundamentoLegalModel.setListFundamentoLegal(fundamentoLegalService.todosLosFundamentos());
            fundamentoLegalModel.setListaComboEjercicioFiscal(fundamentoLegalService.obtenerComboEjercicioFiscal());
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
    public void elimina() {

        Calendar cal = Calendar.getInstance();
        fundamentoLegalModel.getFundamentoLegalEli().setFechaFin(cal.getTime());

        try {
            SegbMovimientoDTO dtoSeg = MovimientoUtil.addMovimiento(getSession(), 
                    ConstantsCatalogos.CVE_SISTEMA ,
                    ConstantsCatalogos.IDENTIFICADOR_FUNDAMENTOLEGAL,
                    new Date(),new Date(),
                    ConstantsCatalogos.BAJA_MOV_FUNDAMENTOLEGAL,
                    ConstantsCatalogos.BAJA_FUNDAMENTOLEGAL_OBS);
            fundamentoLegalService.eliminaFundamentoLegal(fundamentoLegalModel.getFundamentoLegalEli(), dtoSeg);
            fundamentoLegalModel.setListFundamentoLegal(fundamentoLegalService.todosLosFundamentos());
            fundamentoLegalModel.setListaComboEjercicioFiscal(fundamentoLegalService.obtenerComboEjercicioFiscal());
            cerrar();
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
        if (fundamentoLegalModel.getListFundamentoLegalTmp() != null) {
            excel = fundamentoLegalService.generaExcel(fundamentoLegalModel.getListFundamentoLegalTmp()).toByteArray();
        } else {
            excel = fundamentoLegalService.generaExcel(fundamentoLegalModel.getListFundamentoLegal()).toByteArray();
        }
        despachaArchivo(excel, ConstantsCatalogos.TYPE_EXCEL, "tabla.xls", ConstantsCatalogos.ERROR_GENERA_EXCEL);

    }

    /**
     *
     */
    public void generaPdf() {
        byte[] pdf;
        if (fundamentoLegalModel.getListFundamentoLegalTmp() != null) {
            pdf = fundamentoLegalService.generaPDF(fundamentoLegalModel.getListFundamentoLegalTmp()).toByteArray();
        } else {
            pdf = fundamentoLegalService.generaPDF(fundamentoLegalModel.getListFundamentoLegal()).toByteArray();
        }
        despachaArchivo(pdf, ConstantsCatalogos.TYPE_PDF, "tabla.pdf", ConstantsCatalogos.ERROR_GENERA_PDF);
    }

    /**
     *
     */
    public void cargaListaRegimen() {
        try {
            fundamentoLegalModel.setListaComboRegimen(fundamentoLegalService.obtenerComboRegimenPorIdObligacion());
        } catch (Exception e) {

            getLogger().error(e.getMessage());
        }
    }

    /**
     *
     */
    public void abrirNuevo() {
        fundamentoLegalModel.setListaComboObligacion(fundamentoLegalService.obtenerComboObligacion());
        cargaListaRegimen();
        fundamentoLegalModel.setIdRegimenFun(null);
        fundamentoLegalModel.setListaComboEjercicioFiscal(fundamentoLegalService.obtenerComboEjercicioFiscal());
        fundamentoLegalModel.setListaComboPeriodicidad(fundamentoLegalService.obtenerComboPeriodicidad());
        getFundamentoLegalModel().setIdObligacionFun(null);
        getFundamentoLegalModel().setIdRegimenFun(null);
        getFundamentoLegalModel().setIdEjercicioFiscalFun(null);
        getFundamentoLegalModel().setIdPeriodoFun(null);
        getFundamentoLegalModel().setFechaVencimiento(null);
        this.getFundamentoLegalModel().setTblVisible(false);
        this.getFundamentoLegalModel().setNvoVisible(true);
    }

    /**
     *
     */
    public void abrirEditar() {
        setFundamentoTemp(fundamentoLegalModel.getFundamentoLegalEdit().getFundamentoLegal());
        fundamentoLegalModel.setListaComboObligacion(fundamentoLegalService.obtenerComboObligacion());
        fundamentoLegalModel.setIdObligacionFun(fundamentoLegalModel.getFundamentoLegalEdit().getIdObligacion());
        cargaListaRegimen();
        fundamentoLegalModel.setIdRegimenFun(fundamentoLegalModel.getFundamentoLegalEdit().getIdRegimen());
        fundamentoLegalModel.setListaComboEjercicioFiscal(fundamentoLegalService.obtenerComboEjercicioFiscal());
        fundamentoLegalModel.setIdEjercicioFiscalFun(fundamentoLegalModel.getFundamentoLegalEdit().getIdEjercicioFiscal());
        fundamentoLegalModel.setListaComboPeriodicidad(fundamentoLegalService.obtenerComboPeriodicidad());
        fundamentoLegalModel.setIdPeriodoFun(fundamentoLegalModel.getFundamentoLegalEdit().getIdPeriodo());
        this.getFundamentoLegalModel().setTblVisible(false);
        this.getFundamentoLegalModel().setEdtVisible(true);
    }

    /**
     *
     */
    public void abrirEliminar() {
        fundamentoLegalModel.setListaComboObligacion(fundamentoLegalService.obtenerComboObligacion());
        fundamentoLegalModel.setIdObligacionFun(fundamentoLegalModel.getFundamentoLegalEli().getIdObligacion());
        cargaListaRegimen();
        fundamentoLegalModel.setIdRegimenFun(fundamentoLegalModel.getFundamentoLegalEli().getIdRegimen());
        fundamentoLegalModel.setListaComboEjercicioFiscal(fundamentoLegalService.obtenerComboEjercicioFiscal());
        fundamentoLegalModel.setIdEjercicioFiscalFun(fundamentoLegalModel.getFundamentoLegalEli().getIdEjercicioFiscal());
        fundamentoLegalModel.setListaComboPeriodicidad(fundamentoLegalService.obtenerComboPeriodicidad());
        fundamentoLegalModel.setIdPeriodoFun(fundamentoLegalModel.getFundamentoLegalEli().getIdPeriodo());
        this.getFundamentoLegalModel().setTblVisible(false);
        this.getFundamentoLegalModel().setElmVisible(true);
    }

    /**
     *
     */
    public void cerrar() {
        this.getFundamentoLegalModel().setTblVisible(true);
        this.getFundamentoLegalModel().setNvoVisible(false);
        this.getFundamentoLegalModel().setEdtVisible(false);
        this.getFundamentoLegalModel().setElmVisible(false);
        this.getFundamentoLegalModel().setCargaPorArchivoVisible(false);
        reloadCombo();
        reloadPanel();
        clean();
    }

    /**
     *
     */
    public void clean() {
        if (fundamentoTemp != null) {
            fundamentoLegalModel.getFundamentoLegalEdit().setFundamentoLegal(getFundamentoTemp());
        }
        getFundamentoLegalModel().setFundamentoLegal(null);
        getFundamentoLegalModel().setIdObligacionFun(null);
        getFundamentoLegalModel().setIdRegimenFun(null);
        getFundamentoLegalModel().setFechaVencimiento(null);
        this.listadoCargaOmisos = new ArrayList<BeanCargaOmisos>();
    }

//    ##############################################################################################
    /**
     * incrementa el progress
     */
    public void increment() {
        progress++;
    }

    /**
     *
     */
    public void agregarPorArchivo() {
        try {
            AccesoUsr accesoUsr = obtenerAccesoUsrEmpleados();
            this.vigilancia = new CargaVigilancia();
            this.vigilancia.setUsuario(getAccesoUsr());
            this.vigilancia.setIdMovimiento(ConstantsCatalogos.MODIFICACION_MOV_FUNDAMENTOLEGAL);
            setMostrarBotonCargaArchivo(false);
            setArchivosConErrores(false);
            if (ValidadorRegistro.getCatalogoObligacion() == null || ValidadorRegistro.getCatalogoObligacion().isEmpty()) {
                catalogoObligacion = catalogos.consultar("obligacion");
                Collections.sort(catalogoObligacion);
                ValidadorRegistro.setCatalogoObligacion(catalogoObligacion);
            }
            if (ValidadorRegistro.getCatalogoRegimen() == null || ValidadorRegistro.getCatalogoRegimen().isEmpty()) {
                catalogoRegimen = catalogos.consultar("regimen");
                Collections.sort(catalogoRegimen);
                ValidadorRegistro.setCatalogoRegimen(catalogoRegimen);
            }
            if (ValidadorRegistro.getCatalogoEjercicioFiscal() == null || ValidadorRegistro.getCatalogoEjercicioFiscal().isEmpty()) {
                catalogoEjercicioFiscal = catalogos.consultar("ejercicio fiscal");
                Collections.sort(catalogoEjercicioFiscal);
                ValidadorRegistro.setCatalogoEjercicioFiscal(catalogoEjercicioFiscal);
            }
            if (ValidadorRegistro.getCatalogoPeriodo() == null || ValidadorRegistro.getCatalogoPeriodo().isEmpty()) {
                catalogoPeriodo = catalogos.consultar("periodo");
                Collections.sort(catalogoPeriodo);
                ValidadorRegistro.setCatalogoPeriodo(catalogoPeriodo);
            }
            if (ValidadorRegistro.getCatalogoPeriodicidad() == null || ValidadorRegistro.getCatalogoPeriodicidad().isEmpty()) {
                catalogoPeriodicidad = catalogos.consultar("periodicidad");
                ValidadorRegistro.setCatalogoPeriodicidad(catalogoPeriodicidad);
            }
            if (ValidadorRegistro.getCatalogoPeriodoXPeriodicidad() == null || ValidadorRegistro.getCatalogoPeriodoXPeriodicidad().isEmpty()) {
                catalogoPeriodoXPeriodicidad = periodoService.catalogoTodosLosPeriodos();
                ValidadorRegistro.setCatalogoPeriodoXPeriodicidad(catalogoPeriodoXPeriodicidad);
            }
            getLogger().debug("---------------------------------------------------------------");
            ValidadorRegistro.setCatalogoFundamLegalesExistentes(fundamentoLegalService.getFundamentosExistentes());
            getLogger().debug("---------------------------------------------------------------");
            dto = new TareaDTO(accesoUsr.getUsuario());
            this.fundamentoLegalModel.setCargaPorArchivoVisible(true);
            this.getFundamentoLegalModel().setTblVisible(false);

        } catch (SGTServiceException ex) {
            getLogger().error(ex);
        }
    }

    /**
     *
     */
    public void cargaEnBase() {
        getLogger().debug(">>>>cargaEnBase");
        for (BeanCargaOmisos dato : this.listadoCargaOmisos) {
            try {
                getLogger().debug("errores:" + dato.getTotalRegistrosConError());
                getLogger().debug("correctos:" + dato.getTotalRegistrosCorrectos());
                getLogger().debug(dato.getRutaEnRepositorio());
                getLogger().debug(dato.getNombreOriginalArchivo());

                Map<String, String> resultados = fundamentoLegalService.guardaFundamentoLegalBatch(
                        dato.getRutaEnRepositorio(), dato.getNombreOriginalArchivo());
                if (resultados.get("estado").equals("ok")) {
                    cerrar();
                    fundamentoLegalModel.setListFundamentoLegal(fundamentoLegalService.todosLosFundamentos());
                    getLogger().debug(">>se guardaron los datos");
                    AbstractBaseMB.msgInfo(ConstantsCatalogos.REGISTROEXITOSO);
                } else if (resultados.get("estado").equals("error")) {
                    getLogger().debug(">>" + resultados.get("exception"));
                    AbstractBaseMB.msgWarn(ConstantsCatalogos.REGISTROERROR);
                } else if (resultados.get("estado").equals("sin datos")) {
                    getLogger().debug(">>no hubo datos para guardar");
                    AbstractBaseMB.msgWarn(ConstantsCatalogos.REGISTROSINDATOS);
                }
            } catch (SeguimientoDAOException ex) {
                getLogger().error(ex);
            } finally {
                Agrupamiento.nuevoAgrupamiento();
            }
        }
        getLogger().debug(">>>termina cargaEnBase");
    }

    /**
     *
     * @param event
     */
    public void handleFileUpload(FileUploadEvent event) {
        UploadHandler upHandler = new UploadHandler(this.dto, rutaRepositorio, rutaRepoBitacoraErrores);
        BeanCargaOmisos bean = null;
        DetalleCarga detalle = null;
        try {
            detalle = upHandler.handleFileUpload(
                    event.getFile().getFileName(), event.getFile().getInputstream(), ConstantsCatalogos.CAT_EJERCICIO);
            getLogger().info("pasa set");
            bean = new BeanCargaOmisos(detalle);
        } catch (IOException ex) {
            getLogger().error(ex);
        }
        getLogger().info("agrega a listacargaomisos");
        this.listadoCargaOmisos.add(bean);
        for (BeanCargaOmisos beanCarga : this.listadoCargaOmisos) {
            if (!beanCarga.isCargaInvalida()) {
                setMostrarBotonCargaArchivo(true);
                setArchivosConErrores(false);
            } else {
                setMostrarBotonCargaArchivo(false);
                setArchivosConErrores(true);
                break;
            }
        }

        RequestContext.getCurrentInstance().reset(":formaUploader:pnluploader");
        RequestContext.getCurrentInstance().reset(":formaGeneral:panelArchivosOmisos");
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
        if(this.listadoCargaOmisos.isEmpty()){
            setMostrarBotonCargaArchivo(false);
            setArchivosConErrores(false);
        }else{
            for (BeanCargaOmisos beanCarga : this.listadoCargaOmisos) {
                if (!beanCarga.isCargaInvalida()) {
                    setMostrarBotonCargaArchivo(true);
                    setArchivosConErrores(false);
                } else {
                    setMostrarBotonCargaArchivo(false);
                    setArchivosConErrores(true);
                    break;
                }
            }
        }
        RequestContext.getCurrentInstance().reset(":formaGeneral:panelArchivosOmisos");
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
        addMessage("Carga Cancelada", null);
        return null;
    }

    /**
     * para restear los campos de la pagina al cancelar
     */
    public void resetFail() {
        borraArchivos();
        addMessage("Carga Cancelada", null);
    }
//    ##############################################################################################

    public StreamedContent getFile() {
        return file;
    }

    public void genereReport() {
        InputStream stream = ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream("/siat/cob/tmp/avion.jpeg");
        this.file = new DefaultStreamedContent(stream, "image/jpeg", "downloaded_avion.jpeg");
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    /**
     *
     * @param fundamentoLegalService
     */
    public void setFundamentoLegalService(FundamentoLegalService fundamentoLegalService) {
        this.fundamentoLegalService = fundamentoLegalService;
    }

    /**
     *
     * @return
     */
    public FundamentoLegalService getFundamentoLegalService() {
        return fundamentoLegalService;
    }

    /**
     *
     * @param fundamentoLegalModel
     */
    public void setFundamentoLegalModel(FundamentoLegalModel fundamentoLegalModel) {
        this.fundamentoLegalModel = fundamentoLegalModel;
    }

    /**
     *
     * @return
     */
    public FundamentoLegalModel getFundamentoLegalModel() {
        return fundamentoLegalModel;
    }

    public void setFundamentoTemp(String fundamentoTemp) {
        this.fundamentoTemp = fundamentoTemp;
    }

    public String getFundamentoTemp() {
        return fundamentoTemp;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getRutaRepoBitacoraErrores() {
        return rutaRepoBitacoraErrores;
    }

    public void setRutaRepoBitacoraErrores(String rutaRepoBitacoraErrores) {
        this.rutaRepoBitacoraErrores = rutaRepoBitacoraErrores;
    }

    public String getRutaRepositorio() {
        return rutaRepositorio;
    }

    public void setRutaRepositorio(String rutaRepositorio) {
        this.rutaRepositorio = rutaRepositorio;
    }

    public TareaDTO getDto() {
        return dto;
    }

    public void setDto(TareaDTO dto) {
        this.dto = dto;
    }

    public ServiceCatalogos getCatalogos() {
        return catalogos;
    }

    public void setCatalogos(ServiceCatalogos catalogos) {
        this.catalogos = catalogos;
    }

    public CargaVigilancia getVigilancia() {
        return vigilancia;
    }

    public void setVigilancia(CargaVigilancia vigilancia) {
        this.vigilancia = vigilancia;
    }

    public List<BeanCargaOmisos> getListadoCargaOmisos() {
        return listadoCargaOmisos;
    }

    public void setListadoCargaOmisos(List<BeanCargaOmisos> listadoCargaOmisos) {
        this.listadoCargaOmisos = listadoCargaOmisos;
    }

    public void setMostrarBotonCargaArchivo(boolean mostrarBotonCargaArchivo) {
        this.mostrarBotonCargaArchivo = mostrarBotonCargaArchivo;
    }

    public boolean isMostrarBotonCargaArchivo() {
        return mostrarBotonCargaArchivo;
    }

    public boolean isArchivosConErrores() {
        return archivosConErrores;
    }

    public void setArchivosConErrores(boolean archivosConErrores) {
        this.archivosConErrores = archivosConErrores;
    }
    
}