/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class ParametrosValidacionCumplimiento implements Serializable {

    private String numeroEmpleado;
    private String numeroCarga;

    public ParametrosValidacionCumplimiento(String numeroEmpleado, String numeroCarga) {
        this.numeroEmpleado = numeroEmpleado;
        this.numeroCarga = numeroCarga;
    }

    public ParametrosValidacionCumplimiento() {
    }

    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }

    public void setNumeroEmpleado(String numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }

    public String getNumeroCarga() {
        return numeroCarga;
    }

    public void setNumeroCarga(String numeroCarga) {
        this.numeroCarga = numeroCarga;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + (this.numeroEmpleado != null ? this.numeroEmpleado.hashCode() : 0);
        hash = 47 * hash + (this.numeroCarga != null ? this.numeroCarga.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ParametrosValidacionCumplimiento other = (ParametrosValidacionCumplimiento) obj;
        boolean buffer = validarNumeroEmpleado(other.numeroEmpleado);
        buffer &= validarNumeroCarga(other.numeroCarga);
        return buffer;
    }

    private boolean validarNumeroEmpleado(String numeroEmpleado) {
        return !((this.numeroEmpleado == null) ? (numeroEmpleado != null) : !this.numeroEmpleado.equals(numeroEmpleado));
    }

    private boolean validarNumeroCarga(String numeroCarga) {
        return !((this.numeroCarga == null) ? (numeroCarga != null) : !this.numeroCarga.equals(numeroCarga));
    }

}
