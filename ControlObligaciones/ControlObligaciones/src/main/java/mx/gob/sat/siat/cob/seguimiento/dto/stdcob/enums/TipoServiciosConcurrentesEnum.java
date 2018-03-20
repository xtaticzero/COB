package mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums;

/**
 *
 * @author emmanuel
 */
public enum TipoServiciosConcurrentesEnum {

    IDENTIFICADOR_VIGILANCIA_DOCUMENTOS("COB00030"),
    IDENTIFICADOR_APROBAR_MULTAS("COB00031"),
    IDENTIFICADOR_RESULTADOS_DILIGENCIA("COB00034"),
    IDENTIFICADOR_CARGARENUENTES("COB00024"),
    IDENTIFICADOR_BUSCARENUENTES("COB00023"),
    IDENTIFICADOR_DOCUMENTOS_RENUENTES("COB00043");

    private final String valor;

    private TipoServiciosConcurrentesEnum(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}
