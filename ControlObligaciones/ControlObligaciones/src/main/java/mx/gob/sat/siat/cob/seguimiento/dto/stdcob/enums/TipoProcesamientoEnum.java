/*	****************************************************************
 * Nombre de archivo: TipoProcesamientoEnum.java
 * Autores: Emmanuel Estrada Gonzalez 		 	 		   
 * Bitácora de modificaciones:
 * CR/Defecto Fecha Autor Descripción del cambio
 * ---------------------------------------------------------------------------
 * N/A 01/07/2014 <Nombre del desarrollador que está modificando el archivo>
 * ---------------------------------------------------------------------------
 */

package mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums;

/**
 *
 * @author emmanuel
 */
public enum TipoProcesamientoEnum {
    CADENA("Cadena Procesamiento",1),
    ORDEN("Orden Procesamiento",2);
    
    private Integer idValue;
    private String  etiqueta;
    
    private TipoProcesamientoEnum(String etiqueta,Integer value) {
        this.idValue = value;
        this.etiqueta = etiqueta;
    }
    
    public Integer getIdVlue(){
        return this.idValue;    
    }
    
    public String getEtiqueta(){
        return this.etiqueta;    
    }    
}
