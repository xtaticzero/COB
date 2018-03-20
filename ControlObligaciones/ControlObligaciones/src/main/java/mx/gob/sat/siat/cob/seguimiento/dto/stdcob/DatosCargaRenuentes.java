package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;

public class DatosCargaRenuentes implements Serializable {

    @SuppressWarnings("compatibility:7085778745015324670")
    private static final long serialVersionUID = -8990069296505525563L;
    private String nombreArchivo;
    private int totRegistros;
    private int goodRegistros;
    private int wrongRegistros;

    public DatosCargaRenuentes() {
        super();
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setTotRegistros(int totRegistros) {
        this.totRegistros = totRegistros;
    }

    public int getTotRegistros() {
        return totRegistros;
    }

    public void setGoodRegistros(int goodRegistros) {
        this.goodRegistros = goodRegistros;
    }

    public int getGoodRegistros() {
        return goodRegistros;
    }

    public void setWrongRegistros(int wrongRegistros) {
        this.wrongRegistros = wrongRegistros;
    }

    public int getWrongRegistros() {
        return wrongRegistros;
    }
}
