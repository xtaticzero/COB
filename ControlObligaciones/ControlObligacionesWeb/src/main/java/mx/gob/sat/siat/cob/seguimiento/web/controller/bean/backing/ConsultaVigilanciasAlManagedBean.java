/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.BitacoraErrores;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaAl;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.aprobacion.ConsultarVigilanciaAlService;
import mx.gob.sat.siat.cob.seguimiento.service.otros.SGTService;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import mx.gob.sat.siat.cob.seguimiento.utils.AbstractBaseMB;
import mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.helper.BitacoraErroresHelper;
import mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.helper.ConsultaVigilanciasAlHelper;
import org.primefaces.event.SelectEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author root
 */
@Controller
@Scope("view")
public class ConsultaVigilanciasAlManagedBean extends AbstractBaseMB {

    @Autowired
    private ConsultarVigilanciaAlService consultarVigilanciaAlService;
    @Autowired
    private SGTService sgtService;
    private ConsultaVigilanciasAlHelper consultaVigilanciasAlHelper;
    private List<BitacoraErroresHelper> bitacoraErrores;
    private Integer selected;
    private Integer paramIdVigilancia;
    private Date paramFechaInicio;
    private Date paramFechaFin;
    private Date paramFechaFinMin;
    private Date paramFechaFinMax;
    private boolean mostrarOpcionIdVig;
    private boolean mostrarOpcionFechas;
    private boolean mostrarPanelTabla;
    private boolean mostrarPanelResultados;

    @PostConstruct
    public void init() {
        consultaVigilanciasAlHelper = new ConsultaVigilanciasAlHelper();
        obtenerAccesoUsrEmpleados();
        autorizar(ConstantsCatalogos.IDENTIFICADOR_CIFRAS_VIGILANCIA);
        setMostrarOpcionIdVig(false);
    }

    public void consultarVigilancia() {
        try {
            Map<String, String> params =
                    FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            String idVigilanciaParam = params.get(ConstantsCatalogos.ID_VIGILANCIAPARAM);
            if (idVigilanciaParam != null) {
                VigilanciaAl vig = new VigilanciaAl();
                vig.setNumeroCarga(idVigilanciaParam);
                consultaVigilanciasAlHelper.setVigilancia(vig);
            }
            consultaVigilanciasAlHelper.setVigilancia(consultarVigilanciaAlService.consultarVigilancia(consultaVigilanciasAlHelper.getVigilancia()));
        } catch (SGTServiceException e) {
            consultaVigilanciasAlHelper.setVigilancia(new VigilanciaAl());
            getLogger().error(e);
            addErrorMessage("Error", e.getMessage());
        }
    }

    public void busquedaBitacoraErrores() {
        if (mostrarOpcionIdVig) {
            consultarVigilancia();
            setMostrarPanelTabla(false);
            setMostrarPanelResultados(true);
            setParamFechaFin(null);
            setParamFechaInicio(null);
            setParamIdVigilancia(null);
        } else {
            String fechaInicio = paramFechaInicio != null ? Utilerias.formatearFechaAAAAMMDD(paramFechaInicio) : "";
            String fechaFin = paramFechaFin != null ? Utilerias.formatearFechaAAAAMMDD(paramFechaFin) : "";
            try {
                Map<String, String> params =
                        FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
                String idVigilanciaParam = params.get(ConstantsCatalogos.ID_VIGILANCIAPARAM);
                if (idVigilanciaParam == null) {
                    bitacoraErrores = getBitacoraErrores(sgtService.buscarBitacoraErroresPorIdVigilanciaSinRutaBitacora(paramIdVigilancia, fechaInicio, fechaFin));
                    setMostrarPanelTabla(true);
                    setMostrarPanelResultados(false);
                    setParamIdVigilancia(null);
                    consultaVigilanciasAlHelper.setVigilancia(new VigilanciaAl());
                } else {
                    consultarVigilancia();
                    setMostrarPanelTabla(false);
                    setMostrarPanelResultados(true);
                    setParamFechaFin(null);
                    setParamFechaInicio(null);
                    setParamIdVigilancia(null);
                }
            } catch (Exception e) {
                getLogger().error(e.getMessage());
            }


        }
    }

    public List<BitacoraErroresHelper> getBitacoraErrores(List<BitacoraErrores> lst) {
        List<BitacoraErroresHelper> bit = new ArrayList<BitacoraErroresHelper>();
        for (BitacoraErrores b : lst) {
            BitacoraErroresHelper beh = new BitacoraErroresHelper();
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
            setParamFechaFinMin(null);
            setParamFechaFinMax(null);
        }

    }

    public void habilitaFechaFin(SelectEvent ae) {
        if (paramFechaInicio != null) {
            setParamFechaFinMin(paramFechaInicio);
            setParamFechaFinMax(Utilerias.getDosMesesDesdeHoy(paramFechaInicio));
        }
    }

    public ConsultaVigilanciasAlHelper getConsultaVigilanciasAlHelper() {
        return consultaVigilanciasAlHelper;
    }

    public void setConsultaVigilanciasAlHelper(ConsultaVigilanciasAlHelper consultaVigilanciasAlHelper) {
        this.consultaVigilanciasAlHelper = consultaVigilanciasAlHelper;
    }

    public void setSelected(Integer selected) {
        this.selected = selected;
    }

    public Integer getSelected() {
        return selected;
    }

    public void setParamIdVigilancia(Integer paramIdVigilancia) {
        this.paramIdVigilancia = paramIdVigilancia;
    }

    public Integer getParamIdVigilancia() {
        return paramIdVigilancia;
    }

    public void setParamFechaInicio(Date paramFechaInicio) {
        this.paramFechaInicio = paramFechaInicio != null ? new Date(paramFechaInicio.getTime()) : null;
    }

    public Date getParamFechaInicio() {
        if (paramFechaInicio != null) {
            return (Date) paramFechaInicio.clone();
        }
        return null;
    }

    public void setParamFechaFin(Date paramFechaFin) {
        this.paramFechaFin = paramFechaFin != null ? new Date(paramFechaFin.getTime()) : null;
    }

    public Date getParamFechaFin() {
        if (paramFechaFin != null) {
            return (Date) paramFechaFin.clone();
        }
        return null;
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

    public void setMostrarPanelTabla(boolean mostrarPanelTabla) {
        this.mostrarPanelTabla = mostrarPanelTabla;
    }

    public boolean isMostrarPanelTabla() {
        return mostrarPanelTabla;
    }

    public void setBitacoraErrores(List<BitacoraErroresHelper> bitacoraErrores) {
        this.bitacoraErrores = bitacoraErrores;
    }

    public List<BitacoraErroresHelper> getBitacoraErrores() {
        return bitacoraErrores;
    }

    public void setMostrarPanelResultados(boolean mostrarPanelResultados) {
        this.mostrarPanelResultados = mostrarPanelResultados;
    }

    public boolean isMostrarPanelResultados() {
        return mostrarPanelResultados;
    }

    public void setParamFechaFinMin(Date paramFechaFinMin) {
        this.paramFechaFinMin = paramFechaFinMin != null ? new Date(paramFechaFinMin.getTime()) : null;
    }

    public Date getParamFechaFinMin() {
        if (paramFechaFinMin != null) {
            return (Date) paramFechaFinMin.clone();
        }
        return null;
    }

    public void setParamFechaFinMax(Date paramFechaFinMax) {
        this.paramFechaFinMax = paramFechaFinMax != null ? new Date(paramFechaFinMax.getTime()) : null;
    }

    public Date getParamFechaFinMax() {
        if (paramFechaFinMax != null) {
            return (Date) paramFechaFinMax.clone();
        }
        return null;
    }
}
