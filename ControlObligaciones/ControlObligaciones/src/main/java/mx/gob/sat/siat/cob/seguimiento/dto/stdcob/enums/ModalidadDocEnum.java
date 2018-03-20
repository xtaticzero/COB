package mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums;

public enum ModalidadDocEnum {

    NORMAL(1),
    ACUMULADO(2);

    private int valor;

    private ModalidadDocEnum(int valor) {
        this.valor = valor;
    }
    public int getValor(){
        return valor;
    }

}
