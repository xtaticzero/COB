package mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums;

public enum ParametroSgtEnum {
    CANTIDAD_DIAS_REPORTE_EF(1),
    MONTO_MINIMO(2),
    MONTO_PLANTILLA(3),
    LIMITE_DOCUMENTOS_ENVIO_ARCA_POR_DIA(4),
    REGISTRO_CREDITOS_SIAT_COBRANZA(5),
    AMBIENTE(6),
    FECHA_ULTIMA_EJECUCION_CUMPLIMIENTO(7),
    FECHA_ULTIMA_EJECUCION_BAJA_POR_PADRON(8),
    JOBMANAGER_TIEMPO_LIMITE_PROCESO_EN_EJECUCION(9),
    JOBMANAGER_TIEMPO_LIMITE_PROCESO_POR_EJECUTAR(10),
    JOBMANAGER_INTERVALO_TIEMPO_AVISOS(11),
    TAMANO_BLOQUE_EXTRACCION_CUMPLIMIENTO_PAGINADO(12),
    CAMBIO_DOMICILIO_EMISION_MULTAS(13),
    IMPRESION_CONSULTAS_BUSQUEDA_CUMPLIMIENTOS_BKG(14),
    IMPRESION_CONSULTAS_BUSQUEDA_CUMPLIMIENTOS_WEB(15);
    
    private final int valor;
    
    private ParametroSgtEnum(int valor){
        this.valor=valor;
    }
    
    public int getValor(){
        return valor;
    }
    
}