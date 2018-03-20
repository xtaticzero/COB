/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.afectaciones;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Vigilancia;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EtapaVigilanciaEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;


/**
 *
 * @author root
 */
public interface ProcesoAfectacionTiempoService {
    
    List<Vigilancia> consultarDocsVencidosVigilanciaPorEstado(EstadoDocumentoEnum estado, EtapaVigilanciaEnum etapa, TipoDocumentoEnum[] tipoDocumentoEnums) throws SGTServiceException;
 
    void procesarDocumentosVencidosTiempo(int limMenor, int limMayor, Documento tipoDocumento, EstadoDocumentoEnum estadoDestino) throws SGTServiceException;

    void procesarDocumentosVencidosRenuentes(int limMenor, int limMayor, Documento tipoDoc) throws SGTServiceException;
    
    void generaDocumentoLiquidacion(EtapaVigilanciaEnum etapaVigilancia, TipoDocumentoEnum tipoDocumento, List<Documento> documentosVencidos)throws SGTServiceException;
}
