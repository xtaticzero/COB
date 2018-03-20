package mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums;

import java.io.Serializable;

public enum SituacionArchivoEnum implements Serializable{
    PENDIENTE_DESCARGAR(1),DESCARGADO(2);
                           
    private final int idSituacionArchivo;
    
    private SituacionArchivoEnum(int idSituacionArchivo){
        this.idSituacionArchivo=idSituacionArchivo;
        
    }
    
    public int getIdSituacionArchivo(){
        return idSituacionArchivo;
    }
    
}
