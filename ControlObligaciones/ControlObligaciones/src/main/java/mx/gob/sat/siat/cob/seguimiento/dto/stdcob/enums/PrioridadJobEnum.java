package mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums;

public enum PrioridadJobEnum {
    NULL("--Seleccionar--",0),
    ALTA("Alta",1),
    MEDIA("Media",2),
    BAJA("Baja",3);
    
    private Integer idValue;
    private String  etiqueta;
    
    private PrioridadJobEnum(String etiqueta,Integer value) {
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
