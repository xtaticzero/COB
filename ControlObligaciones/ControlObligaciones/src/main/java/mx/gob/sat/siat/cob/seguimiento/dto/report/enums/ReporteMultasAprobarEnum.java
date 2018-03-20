/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.sat.siat.cob.seguimiento.dto.report.enums;

/**
 *
 * @author emmanuel
 */
public enum ReporteMultasAprobarEnum {
    PATH_RELATIVO("reports/"),
    MASTER_REPORT("reports/FacturaMultaAprobar.jasper");
    
    private final String pathReport;
    public  static final String NOMBRE_ARCHIVO="reporteMultas";
    public  static final String EXTENSION=".pdf";
    public  static final String RUTA_REPORTE="reports/FacturaVigilanciaAprobada.jasper";
    public  static final String PATH = "/siat/cob/tmp/";
    
    private ReporteMultasAprobarEnum(String pathReport) {
        this.pathReport = pathReport;
    }

    public String getPathReport() {
        return pathReport;
    }
}