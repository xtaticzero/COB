package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Obligacion;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.ValorBooleano;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.BuscaRenuentes;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DatosCargaRenuentes;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.PeriodicidadHelper;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoServiciosConcurrentesEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.catalogos.ObligacionService;
import mx.gob.sat.siat.cob.seguimiento.service.otros.ConcurrenceService;
import mx.gob.sat.siat.cob.seguimiento.service.otros.ServiceCatalogos;
import mx.gob.sat.siat.cob.seguimiento.service.renuente.BuscaRenuentesService;
import mx.gob.sat.siat.cob.seguimiento.service.renuente.DoctoRenuenteService;
import mx.gob.sat.siat.cob.seguimiento.service.renuente.PeriodoBusquedaService;
import mx.gob.sat.siat.cob.seguimiento.service.renuente.PeriodoXPeriodicidadService;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import mx.gob.sat.siat.cob.seguimiento.utils.AbstractBaseMB;
import mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.helper.BuscaRenuentesMBeanHelper;
import mx.gob.sat.siat.cob.seguimiento.web.util.ConstantesWeb;
import mx.gob.sat.siat.exception.AccesoDenegadoException;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;
import mx.gob.sat.siat.util.MovimientoUtil;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("buscaRenuentes")
@Scope(value = "view")
public class BuscaRenuentesManagedBean extends AbstractBaseMB {

    @Autowired
    private transient ObligacionService obligacion;
    @Autowired
    private transient ServiceCatalogos catalogos;
    @Autowired
    private transient BuscaRenuentesService buscaRenuentesService;
    @Autowired
    private transient PeriodoXPeriodicidadService periodoService;
    @Autowired
    private DoctoRenuenteService doctoRenuenteService;
    @Autowired
    private PeriodoBusquedaService periodoBusquedaService;
    @Autowired
    private ConcurrenceService concurrenceService;
    @Value("#{properties['filename.renuentes.carga']}")
    private String cargaFileName;
    //VARIABLES
    private List<String> selectedPeriodicidad;
    private List<String> selectedTipoDocumento;
    private List<String> selectedObligaciones;
    private List<String> listaCriteriosBusqueda;
    private String estadoDocumento;
    private List<PeriodicidadHelper> listaPeriodicidadHelper;
    private List<DatosCargaRenuentes> listaDatosCarga;
    private int progress;
    private boolean mostrarPanelArchivo;
    private boolean mostrarMensajeArchivo;
    private boolean mostrarBotonDescargaArchivo;
    private String muestraMensaje;
    private BuscaRenuentes buscaRenuentes;
    private BuscaRenuentesMBeanHelper beanHelper;

    /**
     * Metodo inicial que se ejecuta antes de desplegar la pantalla
     */
    @PostConstruct
    public void init() {
        try {
            beanHelper = new BuscaRenuentesMBeanHelper();
            obtenerAccesoUsrEmpleados();
            autorizar(ConstantsCatalogos.IDENTIFICADOR_BUSCARENUENTES);
            beanHelper.setCatalogoPeriodicidad(catalogos.consultar(ConstantsCatalogos.PERIODICIDAD));
            beanHelper.setCatalogoEjercicioFiscal(catalogos.consultar(ConstantsCatalogos.EJERCICIO_FISCAL));
            List<CatalogoBase> listaTipoDocumento = catalogos.consultar(ConstantsCatalogos.TIPO_DOCUMENTO);
            List<CatalogoBase> listaEstadoDocto = catalogos.consultar(ConstantsCatalogos.EDO_DOCUMENTO);
            List<Obligacion> listaObligaciones = obligacion.getDistinctObligacion();
            beanHelper.setListaMensual(periodoService.catalogoPeriodoXPeriodicidad("M"));
            beanHelper.setListaBimestral(periodoService.catalogoPeriodoXPeriodicidad("B"));
            beanHelper.setListaTrimestral(periodoService.catalogoPeriodoXPeriodicidad("T"));
            beanHelper.setListaCuatrimestral(periodoService.catalogoPeriodoXPeriodicidad("Q"));
            beanHelper.setListaSemestral(periodoService.catalogoPeriodoXPeriodicidad("S"));
            beanHelper.setListaAnual(periodoService.catalogoPeriodoXPeriodicidad("Y"));
            listaPeriodicidadHelper = new ArrayList<PeriodicidadHelper>();
            for (CatalogoBase cat : beanHelper.getCatalogoPeriodicidad()) {
                PeriodicidadHelper ph = new PeriodicidadHelper();

                ph.setCatalogoEjercicioFinal(beanHelper.getCatalogoEjercicioFiscal());
                ph.setCatalogoEjercicioInicial(beanHelper.getCatalogoEjercicioFiscal());
                ph.setCatalogoPeriodoFinal(beanHelper.getCatalogoPeriodo());
                ph.setCatalogoPeriodoInicial(beanHelper.getCatalogoPeriodo());
                ph.setPeriodicidad(cat);
                listaPeriodicidadHelper.add(ph);
            }
            this.parseObligaciones(listaObligaciones);
            this.filtraCatalogoTipoDocumento(listaTipoDocumento);
            this.filtraCatalogoEstadoDocto(listaEstadoDocto);
            ultimoArchivoCargado();
        } catch (SGTServiceException e) {
            getLogger().info(ConstantsCatalogos.MSG_ERROR_CRITERIO_RENUENTES);
        }
    }

    /**
     * Metodo encargado de recopilar la informacion, del ultimo archivo cargado.
     */
    private void ultimoArchivoCargado() {
        buscaRenuentes = buscaRenuentesService.obtenerArchivoBusquedaRenuente();
            if (buscaRenuentes != null) {
                mostrarPanelArchivo = true;
                if (buscaRenuentes.getEsFinalizada().equals(ConstantsCatalogos.CERO)) {
                    muestraMensaje = ConstantsCatalogos.BUSQUEDA_RENUENTES_EN_EJECUCION;
                    mostrarMensajeArchivo = true;
                    mostrarBotonDescargaArchivo = false;
                } else if (buscaRenuentes.getRutaArchivo() != null) {
                    File arc = new File(buscaRenuentes.getRutaArchivo());
                    if (arc.exists()) {
                        mostrarBotonDescargaArchivo = true;
                        mostrarMensajeArchivo = false;
                    } else {
                        muestraMensaje = ConstantsCatalogos.MSG_CARGA_DILIGENCIA;
                        mostrarMensajeArchivo = true;
                        mostrarBotonDescargaArchivo = false;
                    }
                } else {
                    mostrarBotonDescargaArchivo = false;
                    mostrarMensajeArchivo = false;
                }
            } else {
                mostrarPanelArchivo = false;
                mostrarBotonDescargaArchivo = false;
            }
    }

    /**
     * Metodo para despelgar los estados de documentos necesarios segun el caso
     * de uso
     *
     * @param listaEstadoDocto
     */
    private void filtraCatalogoEstadoDocto(List<CatalogoBase> listaEstadoDocto) {
        //Solo se necesita desplegar la opcion 8 y 9
        beanHelper.setCatalogoEstadoDocto(new ArrayList<CatalogoBase>());
        for (CatalogoBase item : listaEstadoDocto) {
            if (item.getId() == ConstantsCatalogos.OCHO || item.getId() == ConstantsCatalogos.NUEVE) {
                beanHelper.getCatalogoEstadoDocto().add(item);
            }
        }
    }

    /**
     * Metodo para desplegar los tipos de documentos necesarios segun el caso de
     * uso
     *
     * @param listaTipoDocumento
     */
    private void filtraCatalogoTipoDocumento(List<CatalogoBase> listaTipoDocumento) {
        //Solo se necesita desplegar la opcion 1 y 4
        beanHelper.setCatalogoTipoDocumento(new ArrayList<CatalogoBase>());
        for (CatalogoBase item : listaTipoDocumento) {
            if (item.getId() == ConstantsCatalogos.UNO || item.getId() == ConstantsCatalogos.CUATRO) {
                beanHelper.getCatalogoTipoDocumento().add(item);
            }
        }
    }

    /**
     * Metodo para formatear el id de obligaciones
     *
     * @param listaObligaciones
     */
    private void parseObligaciones(List<Obligacion> listaObligaciones) {
        if (listaObligaciones != null && !listaObligaciones.isEmpty()) {
            selectedObligaciones = new ArrayList<String>();
            beanHelper.setCatalogoObligacion(new ArrayList<CatalogoBase>());
            for (Obligacion o : listaObligaciones) {
                ValorBooleano valorBooleano = o.getValorBooleano();

                if (valorBooleano.getIdValorBooleano() == ConstantsCatalogos.UNO) {
                    selectedObligaciones.add(o.getIdObligacion().toString());
                }
                CatalogoBase catalogoBase = new CatalogoBase();
                catalogoBase.setIdString(o.getIdObligacion().toString());
                catalogoBase.setNombre(o.getConcepto());
                beanHelper.getCatalogoObligacion().add(catalogoBase);
            }
        }
    }

    /**
     * Metodo que valida los datos de busqueda.
     */
    public void validaBusqueda() {
        if (isProcesoBloqueado()) {
            super.addErrorMessage(ConstantsCatalogos.BUSQUEDA_RENUENTES_EN_EJECUCION, "");
        } else {
            try {
                saveBusqueda();
            } catch (SGTServiceException ex) {
                addErrorMessage(ConstantesWeb.ERROR, ex.getMessage());
            }
        }
    }

    /**
     * Metodo que inserta los datos de busqueda.
     *
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException
     */
    public void saveBusqueda() throws SGTServiceException {
        List<PeriodicidadHelper> helpers = retornaListaSelected();
        if (helpers.isEmpty()) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("validacionTabla", new FacesMessage(FacesMessage.SEVERITY_ERROR, ConstantesWeb.ERROR, ConstantsCatalogos.MSG_SEL_PERIODO));
        } else {
            bloquearProceso();
            BuscaRenuentes buscaRenuentesSave = new BuscaRenuentes();

            buscaRenuentesSave.setSelectedPeriodicidadHelper(helpers);
            buscaRenuentesSave.setEstadoDocumento(Integer.parseInt(estadoDocumento));
            buscaRenuentesSave.setSelectedObligaciones(selectedObligaciones);
            buscaRenuentesSave.setSelectedPeriodicidad(selectedPeriodicidad);
            buscaRenuentesSave.setSelectedTipoDocumento(selectedTipoDocumento);

            Long idBusquedaRenuente = buscaRenuentesService.obtenerIdBusquedaRenuentes();

            buscaRenuentesSave.setIdBusquedaRenuente(idBusquedaRenuente);
            buscaRenuentesSave.setEsFinalizada(ConstantsCatalogos.CERO);
            /**
             * Nombre del archivo.
             */
            String nomFile = getFileNameRFC(cargaFileName);
            buscaRenuentesSave.setRutaArchivo(nomFile);
            /**
             * Fecha con formato.
             */
            buscaRenuentesSave.setFechaBusquedaStr(Utilerias.formatearFechaDDMMAAAAHHMMSS(new Date()));
            /**
             * Insert sgtt_busqRenuentes.
             */
            buscaRenuentesService.insertBuscaRenuentes(buscaRenuentesSave);

            for (PeriodicidadHelper periodicidad : helpers) {
                periodicidad.setIdBusquedaRenuentes(idBusquedaRenuente);
                periodoBusquedaService.insertPeriodoBusqueda(periodicidad);
            }

            guardarBitacora(ConstantsCatalogos.BUSCA_MOV_RENUENTES, ConstantsCatalogos.BUSCA_RENUENTES_OBS);
            super.addMessage(ConstantsCatalogos.BUSQUEDA_RENUENTES_GUARDADO_EXITOSO, "");
        }

    }

    /**
     * Retorna lista de objetos seleccionados.
     */
    private List<PeriodicidadHelper> retornaListaSelected() {
        List<PeriodicidadHelper> listAux = new ArrayList<PeriodicidadHelper>();
        if (!listaPeriodicidadHelper.isEmpty()) {
            for (PeriodicidadHelper pHelper : listaPeriodicidadHelper) {
                if (pHelper.isChecado()) {
                    listAux.add(pHelper);
                }
            }
        }
        return listAux;
    }

    /**
     * Metodo para descargar la bitacora de errores
     *
     * @return
     */
    public StreamedContent getBitacoraFile() {
        try {
            return this.getFile(buscaRenuentes.getRutaArchivo());
        } catch (IOException ex) {
            getLogger().error(ex.getMessage());
            super.addErrorMessage("Error al descargar el archivo", "");
        }
        return null;
    }


    /**
     * Metodo para la descarga del archivo de renuentes
     *
     * @param nameFile
     * @param ext
     * @return
     * @throws IOException
     */
    private StreamedContent getFile(String fileFullName) throws IOException {

        File arc = new File(fileFullName);
        InputStream is = new FileInputStream(arc);
        StreamedContent sc = new DefaultStreamedContent(is, "text/plain", arc.getName());

        return sc;
    }

    private void guardarBitacora(int movimiento, String observacion) {
        try {
            SegbMovimientoDTO dto = MovimientoUtil.addMovimiento(getSession(), ConstantsCatalogos.CVE_SISTEMA, ConstantsCatalogos.IDENTIFICADOR_BUSCARENUENTES, new Date(), new Date(), movimiento, observacion);
            buscaRenuentesService.registraBitacora(dto);
        } catch (SGTServiceException ex) {
            getLogger().error(ex.getMessage());
        } catch (AccesoDenegadoException ex) {
            getLogger().error(ex.getMessage());
        }
    }

    private boolean isProcesoBloqueado() {
        String proceso = "buscaRenuentes";
        return concurrenceService.isLockedServices(
                TipoServiciosConcurrentesEnum.IDENTIFICADOR_BUSCARENUENTES, proceso);
    }

    private void bloquearProceso() throws SGTServiceException {
        String proceso = "buscaRenuentes";
        getLogger().info("Intentando bloquear el grupo de multas " + proceso);
        if (!concurrenceService.lockServices(
                TipoServiciosConcurrentesEnum.IDENTIFICADOR_BUSCARENUENTES, proceso)) {
            throw new SGTServiceException("No se pudo hacer el bloqueo de este proceso.");
        }
        getLogger().info("Se logro hacer el bloqueo " + proceso);
    }

    public void reloadPage() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("buscaRenuentes.jsf");
    }

    /**
     * @return the listaPeriodicidadHelper
     */
    public List<PeriodicidadHelper> getListaPeriodicidadHelper() {
        return listaPeriodicidadHelper;
    }

    /**
     * @param listaPeriodicidadHelper the listaPeriodicidadHelper to set
     */
    public void setListaPeriodicidadHelper(List<PeriodicidadHelper> listaPeriodicidadHelper) {
        this.listaPeriodicidadHelper = listaPeriodicidadHelper;
    }

    public void setSelectedPeriodicidad(List<String> selectedPeriodicidad) {
        this.selectedPeriodicidad = selectedPeriodicidad;
    }

    public List<String> getSelectedPeriodicidad() {
        return selectedPeriodicidad;
    }

    public void setSelectedTipoDocumento(List<String> selectedTipoDocumento) {
        this.selectedTipoDocumento = selectedTipoDocumento;
    }

    public List<String> getSelectedTipoDocumento() {
        return selectedTipoDocumento;
    }

    public void setSelectedObligaciones(List<String> selectedObligaciones) {
        this.selectedObligaciones = selectedObligaciones;
    }

    public List<String> getSelectedObligaciones() {
        return selectedObligaciones;
    }

    public void setEstadoDocumento(String estadoDocumento) {
        this.estadoDocumento = estadoDocumento;
    }

    public String getEstadoDocumento() {
        return estadoDocumento;
    }

    public void setDoctoRenuenteService(DoctoRenuenteService doctoRenuenteService) {
        this.doctoRenuenteService = doctoRenuenteService;
    }

    public DoctoRenuenteService getDoctoRenuenteService() {
        return doctoRenuenteService;
    }

    /**
     * @return the listaCriteriosBusqueda
     */
    public List<String> getListaCriteriosBusqueda() {
        return listaCriteriosBusqueda;
    }

    /**
     * @param listaCriteriosBusqueda the listaCriteriosBusqueda to set
     */
    public void setListaCriteriosBusqueda(List<String> listaCriteriosBusqueda) {
        this.listaCriteriosBusqueda = listaCriteriosBusqueda;
    }

    public void setListaDatosCarga(List<DatosCargaRenuentes> listaDatosCarga) {
        this.listaDatosCarga = listaDatosCarga;
    }

    public List<DatosCargaRenuentes> getListaDatosCarga() {
        return listaDatosCarga;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getProgress() {
        return progress;
    }

    public void increment() {
        progress++;
    }

    public BuscaRenuentesMBeanHelper getBeanHelper() {
        return beanHelper;
    }

    public boolean isMostrarPanelArchivo() {
        return mostrarPanelArchivo;
    }

    public boolean isMostrarMensajeArchivo() {
        return mostrarMensajeArchivo;
    }

    public boolean isMostrarBotonDescargaArchivo() {
        return mostrarBotonDescargaArchivo;
    }

    public String getMuestraMensaje() {
        return muestraMensaje;
    }

    public BuscaRenuentes getBuscaRenuentes() {
        return buscaRenuentes;
    }

    public void setBuscaRenuentes(BuscaRenuentes buscaRenuentes) {
        this.buscaRenuentes = buscaRenuentes;
    }

}
