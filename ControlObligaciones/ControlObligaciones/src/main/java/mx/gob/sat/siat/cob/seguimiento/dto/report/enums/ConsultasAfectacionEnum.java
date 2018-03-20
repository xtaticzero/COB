/*	****************************************************************
 * Nombre de archivo: ConsultasAfectacionEnum.java
 * Autores: Emmanuel Estrada Gonzalez 		 	 		   
 * Bitácora de modificaciones:
 * CR/Defecto Fecha Autor Descripción del cambio
 * ---------------------------------------------------------------------------
 * N/A 24/02/2014 <Nombre del desarrollador que está modificando el archivo>
 * ---------------------------------------------------------------------------
 */
package mx.gob.sat.siat.cob.seguimiento.dto.report.enums;

/**
 *
 * @author emmanuel.estrada
 */
public enum ConsultasAfectacionEnum {
    
    PATH_RELATIVO("reports/"),
    MASTER_REPORT("reports/ConsultaDocumentosReport.jasper"),
    SUB_REPORT_AFECTACIONES("reports/Afectaciones_subreport1.jasper"),
    SUB_REPORT_MULTAS("reports/Multas_subreport1.jasper"),
    SUB_REPORT_MULTAS_DETALLE("reports/Multas_subreporte2.jasper");
    

    private final String parametro;
    
    private ConsultasAfectacionEnum(String parametro) {
        this.parametro = parametro;
    }

    public String getParametro() {
        return parametro;
    }
    
}
