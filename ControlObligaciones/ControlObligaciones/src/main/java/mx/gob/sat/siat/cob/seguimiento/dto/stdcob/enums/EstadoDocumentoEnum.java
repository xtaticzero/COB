package mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums;

public enum EstadoDocumentoEnum {
    PENDIENTE_DE_PROCESAR(0),
    NO_GENERADO(1),
    PENDIENTE_DE_IMPRIMIR(2),
    PENDIENTE_DE_NOTIFICAR(3),
    NOTIFICADO(4),
    NO_LOCALIZADO(5),
    NO_TRABAJADO(6),
    CUMPLIDO_SIN_NOTIFICAR(7),
    VENCIDO(8),
    VENCIDO_PARCIAL(9),
    SOLVENTADO_PARCIAL(10),
    CANCELADO(11),
    CUMPLIDO_DENTRO_DEL_PLAZO(12),
    CUMPLIDO_ANTES_DE_LA_NOTIFICACION(13),
    CUMPLIDO_FUERA_DEL_PLAZO(14),
    EMITIDO(15),
    CUMPLIDO(16),
    ATENDIDO_PARCIAL(17),
    ANULADO(18),
    CANCELADO_POR_AUTORIDAD(19),
    NO_PROCESADO(20);

    private final int valor;

    private EstadoDocumentoEnum(int valor) {
        this.valor = valor;
    }
    public int getValor(){
        return valor;
    }

}
