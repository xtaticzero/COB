package mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums;

public enum TipoDocumentoEnum {
    REQUERIMIENTO_NORMAL(1),
    CARTA_EXHORTO(2),
    REQUERIMIENTO_MULTA(4), 
    MULTA(5),
    REQUERIMIENTO_DIOT(6),
    REQUERIMIENTO_ACUMULADO(7);
    
    private int valor;

    private TipoDocumentoEnum(int valor) {
        this.valor = valor;
    }
    public int getValor(){
        return valor;
    }
}