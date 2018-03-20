package mx.gob.sat.siat.cob.seguimiento.dao.catalogos;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.EmailReporteProceso;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;

public interface EmailReporteProcesoDAO {
    
    List<EmailReporteProceso> todosLosEmailReporteProceso(boolean isEmailEc);
    void agregaEmailReporteProceso(EmailReporteProceso emailReporteProceso, boolean isEmailEc) throws SeguimientoDAOException;
    void editaEmailReporteProceso(EmailReporteProceso emailReporteProceso, boolean isEmailEc) throws SeguimientoDAOException;
    Integer eliminaEmailReporteProceso(EmailReporteProceso emailReporteProceso, boolean isEmailEc) throws SeguimientoDAOException;
}
