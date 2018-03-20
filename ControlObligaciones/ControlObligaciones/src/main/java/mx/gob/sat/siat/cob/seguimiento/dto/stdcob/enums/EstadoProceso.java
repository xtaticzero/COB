package mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums;

public enum EstadoProceso {
    DESHABILITADO(0),
    HABILITADO(1),
    EN_ESPERA(2),
    POR_EJECUTAR(3),
    EN_EJECUCION(4),
    COMPLETADO(5),
    FALLIDO(6),
    INTENTOS_AGOTADOS(7);
    
    private int idEdoProceso;
    private EstadoProceso(int value) {
            this.idEdoProceso = value;
    }
    
    public int getIdEdoDoc(){
        return idEdoProceso;
    }
}
