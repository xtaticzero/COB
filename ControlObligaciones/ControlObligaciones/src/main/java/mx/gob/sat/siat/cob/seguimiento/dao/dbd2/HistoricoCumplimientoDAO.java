/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.dbd2;

import java.util.Date;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.dbd2.sql.HistoricoCumplimientoSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.db2.HistoricoCumplimiento;
import mx.gob.sat.siat.cob.seguimiento.dto.db2.Paginador;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DocumentoAprobar;

/**
 *
 * @author rodrigo
 */
public interface HistoricoCumplimientoDAO  extends HistoricoCumplimientoSQL{

    List<HistoricoCumplimiento> consultarCumplimientos(Paginador paginador, Date fechaHistorico);

    long consultarTamanio(Date fechaHistorico);

    List<HistoricoCumplimiento> buscarCumplimientos(List<DocumentoAprobar> documentos);
    
    List<HistoricoCumplimiento> buscarCumplimientos(List<DocumentoAprobar> documentos,List<Integer> tiposDeclaracion);
    
}
