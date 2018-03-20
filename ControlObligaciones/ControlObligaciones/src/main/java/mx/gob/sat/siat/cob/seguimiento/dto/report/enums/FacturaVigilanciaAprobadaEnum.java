/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.report.enums;

import java.io.Serializable;

/**
 *
 * @author root
 */
public enum FacturaVigilanciaAprobadaEnum implements Serializable {

    TIPO_ADMINISTRACION("tipoAdministracion"),
    DESCRIPCION_VIGILANCIA("descripcionVigilancia"),
    FECHA_EMISION("fechaEmision"),
    EXCLUIDOS_POR_RESPONSABLE("excluidosPorResponsable"),
    CANCELADOS("cancelados"),
    CUMPLIMIENTO("cumplimiento"),
    TOTAL("total"),
    TOTAL_DOCUMENTOS_PROCESADOS("totalDocumentosProcesados");

    private final String parametro;
    public  static final String RUTA_REPORTE="reports/FacturaVigilanciaAprobada.jasper";
    public  static final String NOMBRE_ARCHIVO="factura";
    public  static final String EXTENSION=".pdf";
    

    private FacturaVigilanciaAprobadaEnum(String parametro) {
        this.parametro = parametro;
    }

    public String getParametro() {
        return parametro;
    }
}
