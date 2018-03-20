/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import java.util.List;
import java.util.Set;
import mx.gob.sat.siat.cob.seguimiento.dto.db2.Paginador;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.CadenaOriginal;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DocumentoAprobar;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;

/**
 *
 * @author root
 */
public interface DocumentoAprobarDAO {

    List<DocumentoAprobar> listarDocumentos(Paginador paginador, String numeroCarga, String idLocalidad,String filtroRFC);

    List<CadenaOriginal> listarDocumentosFirmar(int rowInicial, int rowFinal, String vigilancia, String idLocalidad, String nombre);
            
    Long contarRegistros(String numeroCarga, String idLocalidad);
    
    Long contarRegistros(String numeroCarga, String idLocalidad,String filtroRFC);
    
    Long contarRegistrosFirma(String numeroCarga, String idLocalidad);

    List<DocumentoAprobar> listarDocumentosIcep(Paginador paginador, String numeroCarga, String idLocalidad);

    Long contarRegistrosIcep(String numeroCarga, String idLocalidad);

    Integer actualizarDocumentos(String numeroCarga, String idLocalidad,EstadoDocumentoEnum estado);

    Integer bitacoraDocumentos(String numeroCarga, String idLocalidad,EstadoDocumentoEnum estado);
    
    Integer rechazarDocumentosPorNumeroControl(Set<String> numerosControl);
    
    Integer bitacoraRechazarDocumentosPorNumeroControl(Set<String> numerosControl);
    
    Integer actualizarEstatusDocumento(String numeroControl,EstadoDocumentoEnum estadoNuevo);
    
}
