/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.BitacoraMulta;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoMultaEnum;

/**
 *
 * @author root
 */
public interface BitacoraMultaDAO {    
    Integer insert(BitacoraMulta bitacoraMulta);
    /**
     * Registra en bitácora el tipo de multa generado a documentos.
     * @param documentos Lista de documentos a los que se les ha generado una multa.
     * @param estadoMulta Estado de la multa que será registrado en bitácora.
     * @return El número de registros afectados. 
     */
    Integer registraBitacoraMultaMasivo(List<Documento> documentos, EstadoMultaEnum estadoMulta);
}
