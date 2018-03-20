package mx.gob.sat.siat.cob.seguimiento.dto.compartidos;

import java.io.Serializable;

public class ErrorCampo implements Serializable {

    @SuppressWarnings("compatibility:-8640413854105388830")
    private static final long serialVersionUID = -3507291531019085352L;
    private String nombreCampo;
    private String toolTip;
    
    public ErrorCampo() {
        super();
    }

    public ErrorCampo(String nombreCampo, String toolTip) {
        super();
        this.nombreCampo = nombreCampo;
        this.toolTip = toolTip;
    }

    public void setNombreCampo(String nombreCampo) {
        this.nombreCampo = nombreCampo;
    }

    public String getNombreCampo() {
        return nombreCampo;
    }

    public void setToolTip(String toolTip) {
        this.toolTip = toolTip;
    }

    public String getToolTip() {
        return toolTip;
    }
}
