package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoProceso;


public class ConsultarProcesosFiltroDto {

    private String listaIdProcesos;
    private Integer idProceso;
    private String idManager;
    private List<EstadoProceso> excluirEstados = new ArrayList<EstadoProceso>();
    private List<EstadoProceso> incluirEstados = new ArrayList<EstadoProceso>();
    private List<Proceso> excluirProcesos = new ArrayList<Proceso>();

    public ConsultarProcesosFiltroDto() {
        super();
    }

    public void setListaIdProcesos(String listaIdProcesos) {
        this.listaIdProcesos = listaIdProcesos;
    }

    public String getListaIdProcesos() {
        return listaIdProcesos;
    }

    public void setIdProceso(Integer idProceso) {
        this.idProceso = idProceso;
    }

    public Integer getIdProceso() {
        return idProceso;
    }

    public void setExcluirEstados(List<EstadoProceso> excluirEstados) {
        this.excluirEstados = excluirEstados;
    }

    public List<EstadoProceso> getExcluirEstados() {
        return excluirEstados;
    }

    public void setIncluirEstados(List<EstadoProceso> incluirEstados) {
        this.incluirEstados = incluirEstados;
    }

    public List<EstadoProceso> getIncluirEstados() {
        return incluirEstados;
    }

    public void setIdManager(String idManager) {
        this.idManager = idManager;
    }

    public String getIdManager() {
        return idManager;
    }
    public void setExcluirProcesos(List<Proceso> excluirProcesos) {
        this.excluirProcesos = excluirProcesos;
    }

    public List<Proceso> getExcluirProcesos() {
        return excluirProcesos;
    }
}
