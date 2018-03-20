package mx.gob.sat.siat.cob.seguimiento.service.otros;

import java.io.IOException;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.db2.HistoricoCumplimiento;


public interface ValidacionCumplimientoFailWriter {
    void reportarIncidencias(String processId,String vigilancia,List<HistoricoCumplimiento> historicosCumplimiento) throws IOException;
}
