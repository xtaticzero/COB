/*	****************************************************************
 * Nombre de archivo: TipoProcesamientoDTO.java
 * Autores: Emmanuel Estrada Gonzalez 		 	 		   
 * Bitácora de modificaciones:
 * CR/Defecto Fecha Autor Descripción del cambio
 * ---------------------------------------------------------------------------
 * N/A 15/05/2014 <Nombre del desarrollador que está modificando el archivo>
 * ---------------------------------------------------------------------------
 */
package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

/**
 *
 * @author emmanuel
 */
public class TipoProcesamientoDTO {

    private Integer id;
    private String procesamiento;

    public String getProcesamiento() {
        return procesamiento;
    }

    public void setProcesamiento(String procesamiento) {
        this.procesamiento = procesamiento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
