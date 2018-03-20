/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums;
import static mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.CampoEnum.*;

/**
 *
 * @author root
 */
public enum CatalogoEnum {
    TIPO_DOCUMENTO(ID_TIPO_DOCUMENTO.getNombre(),
                   ID_TIPO_DOCUMENTO_FIELD.getNombre(),
                   ID_NOMBRE_FIELD.getNombre(),
                   ID_DATASOURCE_SGT.getNombre(),
                   "",
                   "tipo de documento"),
     TIPO_DOCUMENTO_OMISO(ID_TIPO_DOCUMENTO.getNombre(),
                          ID_TIPO_DOCUMENTO_FIELD.getNombre(),
                          ID_NOMBRE_FIELD.getNombre(),
                          ID_DATASOURCE_SGT.getNombre(),
                          " and consideracargaomisos = 1",
                          "tipo de documento omiso"),
     TIPO_FIRMA(ID_TIPO_FIRMA.getNombre(),
             ID_TIPO_FIRMA_FIELD.getNombre(),
             ID_NOMBRE_FIELD.getNombre(),
             ID_DATASOURCE_SGT.getNombre(),
             "",
             "tipo de firma"),
     OBLIGACIONES_DIOT(ID_VALOR_BOOLEAN.getNombre(),
             ID_VALOR_BOOLEAN_FIELD.getNombre(),
             ID_NOMBRE_FIELD.getNombre(),
             ID_DATASOURCE_SGT.getNombre(),
             "",
             "obligaciones diot"),
     MEDIO_ENVIO(ID_TIPO_MEDIO.getNombre(),
             ID_TIPO_MEDIO_FIELD.getNombre(),
             ID_NOMBRE_FIELD.getNombre(),
             ID_DATASOURCE_SGT.getNombre(),
             "",
             "medio de envio"),
     MODALIDAD_DOCUMENTO(ID_MODALIDAD_DOCUMENTO.getNombre(),
                ID_MODALIDAD_DOCUMENTO_FIELD.getNombre(),
             ID_NOMBRE_FIELD.getNombre(),
             ID_DATASOURCE_SGT.getNombre(),
             "",
             "modalidad de documento"),
     ETAPA_VIGILANCIA(ID_ETAPA_VIGILANCIA.getNombre(),
                      ID_ETAPA_VIGILANCIA_FIELD.getNombre(),
                      ID_NOMBRE_FIELD.getNombre(),
                      ID_DATASOURCE_SGT.getNombre(),
                      "",
                      "etapa vigilancia"),
     ESTADO_VIGILANCIA(ID_ESTADO_VIGILANCIA.getNombre(),
             ID_ESTADO_VIGILANCIA_FIELD.getNombre(),
             ID_NOMBRE_FIELD.getNombre(),
             ID_DATASOURCE_SGT.getNombre(),
             "",
             "estado vigilancia"),
       OBLIGACION_SANCION(ID_OBLISANCION.getNombre(),
               ID_OBLIGACION_SANCION_FIELD.getNombre(),
               ID_SANCION_FIELD.getNombre(),
               ID_DATASOURCE_SGT.getNombre(),
               "",
               "obligacion sancion"),
        OBLIGACION(ID_OBLIGACION.getNombre(),
                ID_OBLIGACION_FIELD.getNombre(),
                ID_DESCRIPCION_FIELD.getNombre(),
                ID_DATASOURCE_SGT.getNombre(),
                "",
                "obligacion"),
        PLANTILLA(ID_PLANTILLA.getNombre(),
                ID_PLANTILLA_FIELD.getNombre(),
                ID_NOMBRE_FIELD.getNombre(),
                ID_DATASOURCE_SGT.getNombre(),
                "",
                "plantilla"),
        TRANSICION(ID_TRANSICION_SGT.getNombre(),
                ID_TRANSICION.getNombre(),
                ID_NOMBRE_FIELD.getNombre(),
                ID_DATASOURCE_SGT.getNombre(),
                "",
                "transicionSgt"),
        ESTADO_DOCUMENTO(ID_ESTADO_DOCUMENTO.getNombre(),
                ID_ESTADO_DOCTO_FIELD.getNombre(),
                ID_NOMBRE_FIELD.getNombre(),
                ID_DATASOURCE_SGT.getNombre(),
                "",
                "estado documento"),
        CAUSA_CANCELAR_DOCUMENTO(ID_CAUSA_CANCEL_DOCUMENTO.getNombre(),
                ID_CAUSA_CANCELA_DOCTO_FIELD.getNombre(),
                ID_NOMBRE_FIELD.getNombre(),
                ID_DATASOURCE_SGT.getNombre(),"","causa cancelar documento"),
        TRANSICION_DOCUMENTO(ID_TRANSICION_DOCUMENTO.getNombre(),
                ID_TRANSICION_DOCTO_FIELD.getNombre(),
                ID_NOMBRE_FIELD.getNombre(),
                ID_DATASOURCE_SGT.getNombre(),"","transicion documento"),
        PERIODICIDAD(ID_PERIODICIDAD.getNombre(),
                ID_PERIODICIDAD_FIELD.getNombre(),
                ID_NOMBRE_FIELD.getNombre(),
                ID_DATASOURCE_SGT.getNombre(),
                "",
                "periodicidad"),
        PERIODO(ID_PERIODO.getNombre(),
                ID_PERIODO_FIELD.getNombre(),
                ID_NOMBRE_FIELD.getNombre(),
                ID_DATASOURCE_SGT.getNombre(),
                "",
                "periodo"),
        EJERCICIO_FISCAL(ID_EJERCICIO_FISCAL.getNombre(),
               ID_EJERCICIO_FISCAL_FIELD.getNombre(),
                ID_NOMBRE_FIELD.getNombre(),
                ID_DATASOURCE_SGT.getNombre(),
                "",
                "ejercicio fiscal"),
        DESCRIPCION(DESCRIPCION_TABLE.getNombre(),
                CAMPO_DESCRIPCION_FIELD.getNombre(),
                ID_DESCRIPCION_FIELD.getNombre(),
                ID_DATASOURCE_SGT.getNombre(),
                "",
                "descripcion"),
        NIVELEMISION(NIVELEMI_TABLE.getNombre(),
                ID_NIVELEMI_FIELD.getNombre(),
                ID_NOMBRE_FIELD.getNombre(),
                ID_DATASOURCE_SGT.getNombre(),
                "",
                "nivel emision"),
        CARGOSADMTVOS(CARGOADMN_TABLE.getNombre(),
                ID_CARGOADMN_FIELD.getNombre(),
                ID_NOMBRE_FIELD.getNombre(),
                ID_DATASOURCE_SGT.getNombre(),
                "",
                "cargo admtvo",
                ID_NIVELEMI_FIELD.getNombre()),
        REGIMEN(REGIMEN_TABLE.getNombre(),
                ID_REGIMEN_FIELD.getNombre(),
                REGIMEN_DESC_FIELD.getNombre(),
                ID_DATASOURCE_SGT.getNombre(),
                "",
                "regimen"),
        MOTIVORENUENTE(MOTIVORENUENTE_TABLE.getNombre(),
                ID_MOTIVORENUENTE_FIELD.getNombre(),
                ID_DESCRIPCION_FIELD.getNombre(),
                ID_DATASOURCE_SGT.getNombre(),
                "",
                "motivo_renuente");

    private final String currentTablaID;
    private final String currentCampoID;
    private final String currentDescripcionID;
    private final String currentDataSourceID;
    private final String currentWhereData;
    private final String alias;
    private final String currentCampoAuxID;

    private CatalogoEnum(String currentTablaID,
                         String currentCampoID,
                         String currentDescripcionID,
                         String currentDataSourceID,
                         String currentWhereData,
                         String alias) {
        this.currentTablaID = currentTablaID;
        this.currentCampoID = currentCampoID;
        this.currentDescripcionID = currentDescripcionID;
        this.currentDataSourceID = currentDataSourceID;
        this.currentWhereData = currentWhereData;
        this.alias=alias;
        this.currentCampoAuxID="";
    }

    private CatalogoEnum(String currentTablaID,
                         String currentCampoID,
                         String currentDescripcionID,
                         String currentDataSourceID,
                         String currentWhereData,
                         String alias,
                         String currentCampoAuxID) {
        this.currentTablaID = currentTablaID;
        this.currentCampoID = currentCampoID;
        this.currentDescripcionID = currentDescripcionID;
        this.currentDataSourceID = currentDataSourceID;
        this.currentWhereData = currentWhereData;
        this.alias=alias;
        this.currentCampoAuxID=currentCampoAuxID;
    }


    public String getCurrentTablaID() {
        return currentTablaID;
    }

    public String getCurrentCampoID() {
        return currentCampoID;
    }

    public String getCurrentDescripcionID() {
        return currentDescripcionID;
    }

    public String getCurrentDataSourceID() {
        return currentDataSourceID;
    }

    public String getCurrentWhereData() {
        return currentWhereData;
    }

    public String getAlias() {
        return alias;
    }

    public String getCurrentCampoAuxID(){
        return currentCampoAuxID;
    }

}
