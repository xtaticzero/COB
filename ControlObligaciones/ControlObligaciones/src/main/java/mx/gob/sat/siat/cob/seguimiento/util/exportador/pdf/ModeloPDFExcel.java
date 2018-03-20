package mx.gob.sat.siat.cob.seguimiento.util.exportador.pdf;

import java.util.ArrayList;
import java.util.List;

public class ModeloPDFExcel {

    private String titulo;
    private List datos;
    private List<String> nombreColumnas = new ArrayList<String>();

    /**
     *
     */
    public ModeloPDFExcel() {
        super();
    }

    /**
     *
     * @return
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     *
     * @param titulo
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     *
     * @return
     */
    public List getDatos() {
        return datos;
    }

    /**
     *
     * @param datos
     */
    public void setDatos(List datos) {
        this.datos = datos;
    }

    /**
     *
     * @return
     */
    public List<String> getNombreColumnas() {
        return nombreColumnas;
    }

    /**
     *
     * @param nombreColumnas
     */
    public void setNombreColumnas(List<String> nombreColumnas) {
        this.nombreColumnas = nombreColumnas;
    }
}
