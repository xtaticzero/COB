package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class BuscaRenuentes {

    private Long idBusquedaRenuente;
    private List<String> selectedTipoDocumento = Collections.emptyList();
    private Integer estadoDocumento;
    private Date fechaBusqueda;
    private Integer esFinalizada;
    private List<String> selectedObligaciones = Collections.emptyList();
    private String rutaArchivo;
    private List<String> selectedPeriodicidad = Collections.emptyList();
    private List<PeriodicidadHelper> selectedPeriodicidadHelper = Collections.emptyList();
    private String fechaBusquedaStr;
    private String esFinalizadaDesc;

    public BuscaRenuentes() {
        super();
    }

    public Long getIdBusquedaRenuente() {
        return idBusquedaRenuente;
    }

    public void setIdBusquedaRenuente(Long idBusquedaRenuente) {
        this.idBusquedaRenuente = idBusquedaRenuente;
    }

    public List<String> getSelectedTipoDocumento() {
        return selectedTipoDocumento;
    }

    public void setSelectedTipoDocumento(List<String> selectedTipoDocumento) {
        this.selectedTipoDocumento = selectedTipoDocumento;
    }

    public Integer getEstadoDocumento() {
        return estadoDocumento;
    }

    public void setEstadoDocumento(Integer estadoDocumento) {
        this.estadoDocumento = estadoDocumento;
    }

    public Date getFechaBusqueda() {
        if (fechaBusqueda != null) {
            return (Date) fechaBusqueda.clone();
        }
        return null;
    }

    public void setFechaBusqueda(Date fechaBusqueda) {
        if (fechaBusqueda != null) {
            this.fechaBusqueda = (Date) fechaBusqueda.clone();
        } else {
            this.fechaBusqueda = null;
        }
    }

    public Integer getEsFinalizada() {
        return esFinalizada;
    }

    public void setEsFinalizada(Integer esFinalizada) {
        this.esFinalizada = esFinalizada;
    }

    public List<String> getSelectedObligaciones() {
        return selectedObligaciones;
    }

    public void setSelectedObligaciones(List<String> selectedObligaciones) {
        this.selectedObligaciones = selectedObligaciones;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public List<String> getSelectedPeriodicidad() {
        return selectedPeriodicidad;
    }

    public void setSelectedPeriodicidad(List<String> selectedPeriodicidad) {
        this.selectedPeriodicidad = selectedPeriodicidad;
    }

    public List<PeriodicidadHelper> getSelectedPeriodicidadHelper() {
        return selectedPeriodicidadHelper;
    }

    public void setSelectedPeriodicidadHelper(List<PeriodicidadHelper> selectedPeriodicidadHelper) {
        this.selectedPeriodicidadHelper = selectedPeriodicidadHelper;
    }

    public String getFechaBusquedaStr() {
        return fechaBusquedaStr;
    }

    public void setFechaBusquedaStr(String fechaBusquedaStr) {
        this.fechaBusquedaStr = fechaBusquedaStr;
    }

    public String getEsFinalizadaDesc() {
        return esFinalizadaDesc;
    }

    public void setEsFinalizadaDesc(String esFinalizadaDesc) {
        this.esFinalizadaDesc = esFinalizadaDesc;
    }

}
