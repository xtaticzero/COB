/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.NivelEmisionEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoMedioEnvioEnum;

/**
 *
 * @author root
 */
public class VigilanciaAl extends VigilanciaAprobar implements Serializable {

    private NivelEmisionEnum nivelEmision;
    private String cargoAdministrativo;
    private TipoMedioEnvioEnum tipoMedioEnvio;
    private List<VigilanciaAdministracionLocal> detalle;
    private List<VigilanciaEntidadFederativa> detalleEF;

    public NivelEmisionEnum getNivelEmision() {
        return nivelEmision;
    }

    public void setNivelEmision(NivelEmisionEnum nivelEmision) {
        this.nivelEmision = nivelEmision;
    }

    public String getCargoAdministrativo() {
        return cargoAdministrativo;
    }

    public void setCargoAdministrativo(String cargoAdministrativo) {
        this.cargoAdministrativo = cargoAdministrativo;
    }

    public List<VigilanciaAdministracionLocal> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<VigilanciaAdministracionLocal> detalle) {
        this.detalle = detalle;
    }

    public List<VigilanciaEntidadFederativa> getDetalleEF() {
        return detalleEF;
    }

    public void setDetalleEF(List<VigilanciaEntidadFederativa> detalleEF) {
        this.detalleEF = detalleEF;
    }

    public TipoMedioEnvioEnum getTipoMedioEnvio() {
        return tipoMedioEnvio;
    }

    public void setTipoMedioEnvio(TipoMedioEnvioEnum tipoMedioEnvio) {
        this.tipoMedioEnvio = tipoMedioEnvio;
    }

}
