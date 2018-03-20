/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.NivelEmisionEnum;

/**
 *
 * @author root
 */
public class AdministrativoNivelCargo implements Serializable {

    private Long idCargoAdministrativo;
    private String numeroEmpleado;
    private String idAdministacionLocal;
    private NivelEmisionEnum nivelEmision;
    private EventoCarga eventoCarga;
    private String nombreFuncionario;
    private String correoElectronico;
    private String correoElectronicoAlterno;

    public AdministrativoNivelCargo() {
        eventoCarga = new EventoCarga();
    }

    public Long getIdCargoAdministrativo() {
        return idCargoAdministrativo;
    }

    public void setIdCargoAdministrativo(Long idCargoAdministrativo) {
        this.idCargoAdministrativo = idCargoAdministrativo;
    }

    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }

    public void setNumeroEmpleado(String numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }

    public String getIdAdministacionLocal() {
        return idAdministacionLocal;
    }

    public void setIdAdministacionLocal(String idAdministacionLocal) {
        this.idAdministacionLocal = idAdministacionLocal;
    }

    public NivelEmisionEnum getNivelEmision() {
        return nivelEmision;
    }

    public void setNivelEmision(NivelEmisionEnum nivelEmision) {
        this.nivelEmision = nivelEmision;
    }

    public EventoCarga getEventoCarga() {
        return eventoCarga;
    }

    public void setEventoCarga(EventoCarga eventoCarga) {
        this.eventoCarga = eventoCarga;
    }

    @Override
    public String toString() {
        return "AdministrativoNivelCargo{" + "idCargoAdministrativo=" + idCargoAdministrativo + ", numeroEmpleado=" + numeroEmpleado + ", idAdministacionLocal=" + idAdministacionLocal + ", nivelEmision=" + nivelEmision + ", eventoCarga=" + eventoCarga + '}';
    }

    public String getNombreFuncionario() {
        return nombreFuncionario;
    }

    public void setNombreFuncionario(String nombreFuncionario) {
        this.nombreFuncionario = nombreFuncionario;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getCorreoElectronicoAlterno() {
        return correoElectronicoAlterno;
    }

    public void setCorreoElectronicoAlterno(String correoElectronicoAlterno) {
        this.correoElectronicoAlterno = correoElectronicoAlterno;
    }
}
