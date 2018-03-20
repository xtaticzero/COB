package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.BitacoraErrores;
import mx.gob.sat.siat.cob.seguimiento.service.otros.SGTService;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import mx.gob.sat.siat.cob.seguimiento.utils.AbstractBaseMB;
import mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.helper.BitacoraErroresHelper;
import mx.gob.sat.siat.controlador.AccesoProceso;
import mx.gob.sat.siat.exception.AccesoDenegadoException;
import mx.gob.sat.siat.exception.AccesoDenegadoFielException;
import mx.gob.sat.siat.exception.SessionRolNullException;

import org.primefaces.event.SelectEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("bitacoraErroresVigilancia")
@Scope(value = "view")
public class BitacoraErroresVigilanciaManagedBean extends AbstractBaseMB {

    private List<BitacoraErroresHelper> bitacoraErrores;
    private List<CatalogoBase> opcionesBusqueda;
    private Integer paramIdVigilancia;
    private Integer selected;
    private Date paramFechaInicio;
    private Date paramFechaFin;
    private Date paramFechaFinMin;
    private Date paramFechaFinMax;
    private boolean mostrarPanelTabla;
    private boolean mostrarPanelFiltros;
    private boolean mostrarOpcionIdVig;
    private boolean mostrarOpcionFechas;

    @Autowired
    private SGTService sgtService;
    

    public BitacoraErroresVigilanciaManagedBean() {
        super();
    }

    @PostConstruct
    public void init() {
        try {
            super.obtenerAccesoUsrEmpleados();
            if (AccesoProceso.validaAccesoProceso(getSession(), ConstantsCatalogos.IDENTIFICADOR_BITACORA_ERRORES_VIG, ConstantsCatalogos.PARAMETRO_FIEL)) {

                setMostrarPanelFiltros(true);
                setMostrarPanelTabla(false);
                setMostrarOpcionFechas(false);
                setMostrarOpcionIdVig(false);
            }
        } catch (SessionRolNullException ex) {
            getLogger().debug("Error de Sesion:" + ex.getMessage());
            manejaException(ex);
        } catch (AccesoDenegadoException ex) {
            getLogger().debug("Error de acceso denegado:" + ex.getMessage());
            manejaException(ex);
        } catch (AccesoDenegadoFielException ex) {
            getLogger().debug("Error de acceso denegado fiel:" + ex.getMessage());
            manejaException(ex);
        }
    }

    private void manejaException(Exception e) {
        getSession().setAttribute("mensaje", e.getMessage());
        redirigir(ConstantsCatalogos.URL_EN_CONSTRUCCION);
    }

    public void busquedaBitacoraErrores() {
        String fechaInicio = paramFechaInicio != null ? Utilerias.formatearFechaAAAAMMDD(paramFechaInicio) : "";
        String fechaFin = paramFechaFin != null ? Utilerias.formatearFechaAAAAMMDD(paramFechaFin) : "";
        try {
            bitacoraErrores =getBitacoraErrores(sgtService.buscarBitacoraErroresPorIdVigilancia(paramIdVigilancia, fechaInicio, fechaFin));
        } catch (Exception e) {
            getLogger().error(e.getMessage());
        }
        setMostrarPanelFiltros(true);
        setMostrarPanelTabla(true);
        setParamFechaFin(null);
        setParamFechaInicio(null);
        setParamIdVigilancia(null);

    }
    
    public List<BitacoraErroresHelper> getBitacoraErrores(List<BitacoraErrores> lst){
        List<BitacoraErroresHelper> bit=new ArrayList<BitacoraErroresHelper>();
        for(BitacoraErrores b:lst){
            BitacoraErroresHelper beh=new BitacoraErroresHelper();
            beh.setIdVigilancia(b.getIdVigilancia());
            beh.setDescripcionVigilancia(b.getDescripcionVigilancia());
            beh.setNombreOriginalArchivo(b.getNombreOriginalArchivo());
            beh.setRutaBitacoraErrores(b.getRutaBitacoraErrores());
            beh.setFechaCargaArchivo(b.getFechaCargaArchivo());
            beh.setFechaCargaArchivoStr(b.getFechaCargaArchivoStr());
            bit.add(beh);
        }
        return bit;
    }
   

    public void handleOpcionesBusqueda() {
        if (selected == 2) {
            setMostrarOpcionFechas(true);
            setMostrarOpcionIdVig(false);
            setParamIdVigilancia(null);
            setParamFechaFin(null);
            setParamFechaInicio(null);
        }

        if (selected == 1) {
            setMostrarOpcionFechas(false);
            setMostrarOpcionIdVig(true);
            setParamFechaFin(null);
            setParamFechaInicio(null);
        }

    }
    
    public void habilitaFechaFin(SelectEvent ae){
        if(paramFechaInicio!=null){
            setParamFechaFinMin(paramFechaInicio);
            setParamFechaFinMax(Utilerias.getDosMesesDesdeHoy(paramFechaInicio));
         }
    }

    public void setBitacoraErrores(List<BitacoraErroresHelper> bitacoraErrores) {
        this.bitacoraErrores = bitacoraErrores;
    }

    public List<BitacoraErroresHelper> getBitacoraErrores() {
        return bitacoraErrores;
    }

    public void setParamIdVigilancia(Integer paramIdVigilancia) {
        this.paramIdVigilancia = paramIdVigilancia;
    }

    public Integer getParamIdVigilancia() {
        return paramIdVigilancia;
    }

    public void setParamFechaInicio(Date paramFechaInicio) {
        if (paramFechaInicio != null) {
            this.paramFechaInicio = (Date) paramFechaInicio.clone();
        }
    }

    public Date getParamFechaInicio() {
        if (paramFechaInicio != null) {
            return (Date) paramFechaInicio.clone();
        }
        return null;
    }

    public void setParamFechaFin(Date paramFechaFin) {
        if (paramFechaFin != null) {
            this.paramFechaFin = (Date) paramFechaFin.clone();
        }
    }

    public Date getParamFechaFin() {
        if (paramFechaFin != null) {
            return (Date) paramFechaFin.clone();
        }
        return null;
    }

    public void setMostrarPanelTabla(boolean mostrarPanelTabla) {
        this.mostrarPanelTabla = mostrarPanelTabla;
    }

    public boolean isMostrarPanelTabla() {
        return mostrarPanelTabla;
    }

    public void setMostrarPanelFiltros(boolean mostrarPanelFiltros) {
        this.mostrarPanelFiltros = mostrarPanelFiltros;
    }

    public boolean isMostrarPanelFiltros() {
        return mostrarPanelFiltros;
    }

    public void setOpcionesBusqueda(List<CatalogoBase> opcionesBusqueda) {
        this.opcionesBusqueda = opcionesBusqueda;
    }

    public List<CatalogoBase> getOpcionesBusqueda() {
        return opcionesBusqueda;
    }

    public void setSelected(Integer selected) {
        this.selected = selected;
    }

    public Integer getSelected() {
        return selected;
    }

    public void setMostrarOpcionIdVig(boolean mostrarOpcionIdVig) {
        this.mostrarOpcionIdVig = mostrarOpcionIdVig;
    }

    public boolean isMostrarOpcionIdVig() {
        return mostrarOpcionIdVig;
    }

    public void setMostrarOpcionFechas(boolean mostrarOpcionFechas) {
        this.mostrarOpcionFechas = mostrarOpcionFechas;
    }

    public boolean isMostrarOpcionFechas() {
        return mostrarOpcionFechas;
    }

    public void setParamFechaFinMin(Date paramFechaFinMin) {
        this.paramFechaFinMin = paramFechaFinMin != null ? new Date(paramFechaFinMin.getTime()): null;
    }

    public Date getParamFechaFinMin() {
        if(paramFechaFinMin!=null){
        return (Date) paramFechaFinMin.clone();
        }
        return null;
    }

    public void setParamFechaFinMax(Date paramFechaFinMax) {
        this.paramFechaFinMax = paramFechaFinMax != null ? new Date(paramFechaFinMax.getTime()): null;
    }

    public Date getParamFechaFinMax() {
        if(paramFechaFinMax!=null){
        return (Date) paramFechaFinMax.clone();
        }
        return null;
    }
}
