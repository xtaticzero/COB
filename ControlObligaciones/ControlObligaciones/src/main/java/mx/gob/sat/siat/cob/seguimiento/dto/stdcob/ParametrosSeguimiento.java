package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;

public class ParametrosSeguimiento implements Serializable {

    private String tipoDocumento;
    private String etapa;
    private String diasVencimiento;

    public ParametrosSeguimiento() {
        super();
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setEtapa(String etapa) {
        this.etapa = etapa;
    }

    public String getEtapa() {
        return etapa;
    }

    public void setDiasVencimiento(String diasVencimiento) {
        this.diasVencimiento = diasVencimiento;
    }

    public String getDiasVencimiento() {
        return diasVencimiento;
    }
}
