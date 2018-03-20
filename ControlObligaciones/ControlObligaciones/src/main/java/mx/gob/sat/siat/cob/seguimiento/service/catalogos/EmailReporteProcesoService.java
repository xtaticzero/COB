package mx.gob.sat.siat.cob.seguimiento.service.catalogos;

import java.io.ByteArrayOutputStream;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.EmailReporteProceso;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.exception.DaoException;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;

public interface EmailReporteProcesoService {
    
    List<EmailReporteProceso> todosLosEmailReporteProceso(boolean isEmailEc);
    void agregaEmailReporteProceso(EmailReporteProceso emailReporteProceso, SegbMovimientoDTO segMovDto, boolean isEmailEc) throws SeguimientoDAOException, DaoException;
    void editaEmailReporteProceso(EmailReporteProceso emailReporteProceso, SegbMovimientoDTO segMovDto, boolean isEmailEc) throws SeguimientoDAOException, DaoException;
    void eliminaEmailReporteProceso(EmailReporteProceso emailReporteProceso, SegbMovimientoDTO segMovDto, boolean isEmailEc) throws SeguimientoDAOException, DaoException;
    ByteArrayOutputStream generaExcel(List<EmailReporteProceso> list, boolean isEmailEc);
    ByteArrayOutputStream generaPDF(List<EmailReporteProceso> list, boolean isEmailEc);
}
