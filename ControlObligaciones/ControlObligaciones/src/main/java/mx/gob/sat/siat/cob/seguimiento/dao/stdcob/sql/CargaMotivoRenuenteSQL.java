/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql;

/**
 *
 * @author juan
 */
public interface CargaMotivoRenuenteSQL {

    /**
     *
     */
    String AGREGA_CARGA_DOC_RENUENTE = "insert into sgta_cargaDocRente\n"
            + "(idCargaRenuentes, idMotivoNoRenuente, lineaArchivo)\n"
            + "values(?,?,?)";

}
