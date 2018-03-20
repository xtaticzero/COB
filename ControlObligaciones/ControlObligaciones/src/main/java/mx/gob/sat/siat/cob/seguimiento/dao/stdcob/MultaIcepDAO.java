/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaIcep;


/**
 *
 * @author root
 */
public interface MultaIcepDAO {
    
    Integer insert(MultaIcep multaIcep);
    
    List<MultaIcep> selectPorNumeroResolucion(String numeroResolucion);
    
    Integer insertarIcepsPorMultasIncumplimiento(List <Documento> documentosVencidos, int esIcepRenuente);
    /**
     * Método para insertar las multas complementarias que se generan por cada detalle de documento.
     * @param documentos Lista de documentos a los que sus detalles (los que apliquen)
     *                  tendrán multa complementaria.
     * @return El número de registros afectados. 
     */
    Integer insertarIcepsPorMultasComplementarias(List <Documento> documentos);
    
    void actualizarMontoResolucionIcep (Long monto, String numeroResolucion, String claveIcep);
    /**
    * Metodo que sirve como rollback del proceso generar multas
    * @param numeroResolucion
    * */
    void borrarMontosResolucionIcep(String numResolucionDet);

}
