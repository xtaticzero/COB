package mx.gob.sat.siat.cob.seguimiento.service.carga;

import mx.gob.sat.siat.cob.seguimiento.exception.AgrupamientoException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;

public abstract class Agrupamiento implements Serializable {

    private static Map<String, List<String>> mapaAgr = new HashMap<String, List<String>>();
    private static final int BUFFER_SIZE = 10240;

    public static void nuevoAgrupamiento() {
        Agrupamiento.mapaAgr.clear();
    }

    private InputStream is = null;
    private InputStreamReader isr = null;
    private BufferedReader br = null;
    private String mapKey = null;
    private List<String> listaDetalle = null;

    public Agrupamiento() {
        super();
    }

    protected void cierraRecursos() throws IOException {
        this.br.close();
        this.isr.close();
        this.is.close();
    }

    protected void abreRecursos(String ligaRecurso) throws IOException {
        String codificacion = Utilerias.detectaCodificacion(ligaRecurso);
        is = new FileInputStream(ligaRecurso);
        isr = new InputStreamReader(is, codificacion);
        br = new BufferedReader(isr, BUFFER_SIZE);
    }

    public abstract void agrupa(String rutaRepositorio, String nombreArchivo) throws AgrupamientoException;

    public List<String> getListaDetalle() {
        return listaDetalle;
    }

    public void setListaDetalle(List<String> listaDetalle) {
        this.listaDetalle = listaDetalle;
    }

    public InputStream getIs() {
        return is;
    }

    public void setIs(InputStream is) {
        this.is = is;
    }

    public BufferedReader getBr() {
        return br;
    }

    public void setBr(BufferedReader br) {
        this.br = br;
    }

    public InputStreamReader getIsr() {
        return isr;
    }

    public void setIsr(InputStreamReader isr) {
        this.isr = isr;
    }

    public String getMapKey() {
        return mapKey;
    }

    public void setMapKey(String mapKey) {
        this.mapKey = mapKey;
    }

    public static Map<String, List<String>> getMapaAgr() {
        return mapaAgr;
    }

    public static void setMapaAgr(Map<String, List<String>> mapaAgr) {
        Agrupamiento.mapaAgr = mapaAgr;
    }

}
