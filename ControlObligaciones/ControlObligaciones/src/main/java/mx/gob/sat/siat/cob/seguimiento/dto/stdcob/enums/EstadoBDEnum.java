package mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums;

public enum EstadoBDEnum {
    INCONSISTENCIA(98),
    ERROR(99);

    private final int valor;

    private EstadoBDEnum(int valor) {
        this.valor = valor;
    }
    public int getValor(){
        return valor;
    }

}
