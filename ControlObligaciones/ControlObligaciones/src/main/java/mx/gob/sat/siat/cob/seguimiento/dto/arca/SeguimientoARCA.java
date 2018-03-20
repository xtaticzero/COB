/*	****************************************************************
 * Nombre de archivo: SeguimientoARCA.java
 * Autores: Emmanuel Estrada Gonzalez 		 	 		
 * Bitácora de modificaciones:
 * CR/Defecto Fecha Autor Descripción del cambio
 * ---------------------------------------------------------------------------
 * N/A 21/10/2013 <Nombre del desarrollador que está modificando el archivo>
 * ---------------------------------------------------------------------------
 */
package mx.gob.sat.siat.cob.seguimiento.dto.arca;

import java.util.Date;

public class SeguimientoARCA {

    private int id;
    private String idDocumento;
    private Integer idEstadoDocumento;
    private Date fechaResultado;
    private Date fechaRegistro;

    /**
     *
     */
    public SeguimientoARCA() {
        super();
    }

    /**
     *
     * @param idDocumento
     * @param idEstadoDocumento
     * @param fechaResultado
     * @param fechaRegistro
     */
    public SeguimientoARCA(String idDocumento, Integer idEstadoDocumento, Date fechaResultado, Date fechaRegistro) {
        super();
        this.idDocumento = idDocumento;
        this.idEstadoDocumento = idEstadoDocumento;
        if (fechaResultado != null) {
            this.fechaResultado = (Date) fechaResultado.clone();
        }
        if (fechaRegistro != null) {
            this.fechaRegistro = (Date) fechaRegistro.clone();
        }
    }

    /**
     *
     * @param idDocumento
     */
    public void setIdDocumento(String idDocumento) {
        this.idDocumento = idDocumento;
    }

    /**
     *
     * @return
     */
    public String getIdDocumento() {
        return idDocumento;
    }

    /**
     *
     * @param idEstadoDocumento
     */
    public void setIdEstadoDocumento(Integer idEstadoDocumento) {
        this.idEstadoDocumento = idEstadoDocumento;
    }

    /**
     *
     * @return
     */
    public Integer getIdEstadoDocumento() {
        return idEstadoDocumento;
    }

    /**
     *
     * @param fechaResultado
     */
    public void setFechaResultado(Date fechaResultado) {
        if (fechaResultado != null) {
            this.fechaResultado = (Date) fechaResultado.clone();
        }

    }

    /**
     *
     * @return
     */
    public Date getFechaResultado() {
        if (fechaResultado != null) {
            return (Date) fechaResultado.clone();
        }
        return null;
    }

    /**
     *
     * @param fechaRegistro
     */
    public void setFechaRegistro(Date fechaRegistro) {
        if (fechaRegistro != null) {
            this.fechaRegistro = (Date) fechaRegistro.clone();
        }

    }

    /**
     *
     * @return
     */
    public Date getFechaRegistro() {

        if (fechaRegistro != null) {
            return (Date) fechaRegistro.clone();
        }
        return null;
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
}
