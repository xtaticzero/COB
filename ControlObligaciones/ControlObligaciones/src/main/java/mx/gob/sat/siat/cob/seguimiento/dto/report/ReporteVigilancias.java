/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.report;

/**
 *
 * @author root
 */
public enum ReporteVigilancias {

    PATH_RELATIVO("reports/"),
    MASTER_REPORT("reports/ReporteDeVigilanciasV2.jasper");
    
    private final String pathReport;

    private ReporteVigilancias(String pathReport) {
        this.pathReport = pathReport;
    }

    public String getPathReport() {
        return pathReport;
    }
}
