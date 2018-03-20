package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

/**
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
public class EjecucionSeguimiento {

    private int id = 0;
    private String nombre = "";
    private int enEjecucion = 0;

    /**
     *
     */
    public EjecucionSeguimiento() {
        super();
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @param enEjecucion
     */
    public void setEnEjecucion(int enEjecucion) {
        this.enEjecucion = enEjecucion;
    }

    /**
     *
     * @return
     */
    public int getEnEjecucion() {
        return enEjecucion;
    }
}
