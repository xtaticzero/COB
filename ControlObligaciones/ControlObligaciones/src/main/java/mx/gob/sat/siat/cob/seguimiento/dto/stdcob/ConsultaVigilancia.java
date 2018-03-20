package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.util.List;

public class ConsultaVigilancia implements java.io.Serializable {

    private int idVigilancia;
    private int idTipoDocumento;
    private int nivelEmision;
    private int idTipoMedio;
    private String descripcion;
    private String fechaCarga;
    private List<DetalleConsultaVigilancia> detalle;
    private String numEmpleadoUsuario;

    /**
     *
     */
    public ConsultaVigilancia() {
        super();
    }

    /**
     *
     * @param idVigilancia
     */
    public void setIdVigilancia(int idVigilancia) {
        this.idVigilancia = idVigilancia;
    }

    /**
     *
     * @return
     */
    public int getIdVigilancia() {
        return idVigilancia;
    }

    /**
     *
     * @param idTipoDocumento
     */
    public void setIdTipoDocumento(int idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    /**
     *
     * @return
     */
    public int getIdTipoDocumento() {
        return idTipoDocumento;
    }

    /**
     *
     * @param detalle
     */
    public void setDetalle(List<DetalleConsultaVigilancia> detalle) {
        this.detalle = detalle;
    }

    /**
     *
     * @return
     */
    public List<DetalleConsultaVigilancia> getDetalle() {
        return detalle;
    }
    /**
     *
     * @return
     */
    public int getNivelEmision() {
        return nivelEmision;
    }
    /**
     * 
     * @param nivelEmision
     */
    public void setNivelEmision(int nivelEmision) {
        this.nivelEmision = nivelEmision;
    }
    /**
     * 
     * @return
     */
    public int getIdTipoMedio() {
        return idTipoMedio;
    }
    /**
     * 
     * @param idTipoMedio
     */
    public void setIdTipoMedio(int idTipoMedio) {
        this.idTipoMedio = idTipoMedio;
    }
    /**
     *
     * @return
     */
    public String getDescripcion() {
        return descripcion;
    }
    /**
     *
     * @param descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    /**
     *
     * @return
     */
    public String getFechaCarga() {
        return fechaCarga;
    }
    /**
     * 
     * @param fechaCarga
     */
    public void setFechaCarga(String fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    /**
     * @return the numEmpleadoUsuario
     */
    public String getNumEmpleadoUsuario() {
        return numEmpleadoUsuario;
    }

    /**
     * @param numEmpleadoUsuario the numEmpleadoUsuario to set
     */
    public void setNumEmpleadoUsuario(String numEmpleadoUsuario) {
        this.numEmpleadoUsuario = numEmpleadoUsuario;
    }
    
}
