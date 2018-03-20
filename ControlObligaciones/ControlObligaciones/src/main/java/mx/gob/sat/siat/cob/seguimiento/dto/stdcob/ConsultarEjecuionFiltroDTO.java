package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;

import java.util.Date;


public class ConsultarEjecuionFiltroDTO implements Serializable{

    @SuppressWarnings("compatibility:-1948682531702944869")
    private static final long serialVersionUID = 6632820216928482863L;
    private Integer id;
    private Integer idProceso;
    private Date inicioDe;
    private Date inicioA;
    private Integer estado;
    private Proceso proceso;
    
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setIdProceso(Integer idProceso) {
        this.idProceso = idProceso;
    }

    public Integer getIdProceso() {
        return idProceso;
    }

    public void setInicioDe(Date inicioDe) {
        this.inicioDe = inicioDe != null ? new Date(inicioDe.getTime()) : null;
    }

    public Date getInicioDe() {
        return  this.inicioDe != null ? new Date(this.inicioDe.getTime()) : null;
    }

    public void setInicioA(Date inicioA) {
        this.inicioA = inicioA != null ? new Date(inicioA.getTime()) : null;
    }

    public Date getInicioA() {
        return this.inicioA != null ? new Date(this.inicioA.getTime()) : null;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setProceso(Proceso proceso) {
        this.proceso = proceso;
    }

    public Proceso getProceso() {
        return proceso;
    }
}
