package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;

import java.util.Date;


public class ConsultarBitacoraFiltroDTO implements Serializable{
   @SuppressWarnings("compatibility:-7248266520910555642")
   private static final long serialVersionUID = 3995462063655855867L;
   private Integer idProceso;
   private Date fechaInicioDe;
   private Date fechaInicioA;

    public void setIdProceso(Integer idProceso) {
        this.idProceso = idProceso;
    }

    public Integer getIdProceso() {
        return idProceso;
    }

    public void setFechaInicioDe(Date fechaInicioDe) {
        if(fechaInicioDe!=null){
            this.fechaInicioDe = (Date)fechaInicioDe.clone();
        }
    }

    public Date getFechaInicioDe() {
        if(fechaInicioDe==null){
            return null;
        }else{
            return (Date)fechaInicioDe.clone();
        }
    }

    public void setFechaInicioA(Date fechaInicioA) {
        if(fechaInicioA!=null){
            this.fechaInicioA = (Date)fechaInicioA.clone();
        }
    }

    public Date getFechaInicioA() {
        if(fechaInicioA==null){
            return null;
        }else{
            return (Date)fechaInicioA.clone();
        }
    }
}
