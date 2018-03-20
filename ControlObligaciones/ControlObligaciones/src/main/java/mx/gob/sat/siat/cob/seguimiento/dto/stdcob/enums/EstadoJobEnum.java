package mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums;

public enum EstadoJobEnum {

    INACTIVO("Inactivo", 0),
    ACTIVO("Activo", 1),
    EN_ESPERA("En espera", 2),
    POR_EJECUTAR("Por Ejecutar", 3),
    EJECUCION("En ejecucion", 4),
    COMPLETADO("Completado", 5),
    FALLIDO("Fallido", 6),
    INTENTOS_TERMINADOS("Intentos Terminados", 7);

    private Integer idValue;
    private String etiqueta;

    private EstadoJobEnum(String etiqueta, Integer value) {
        this.idValue = value;
        this.etiqueta = etiqueta;
    }

    public Integer getIdVlue() {
        return this.idValue;
    }

    public String getEtiqueta() {
        return this.etiqueta;
    }
}
