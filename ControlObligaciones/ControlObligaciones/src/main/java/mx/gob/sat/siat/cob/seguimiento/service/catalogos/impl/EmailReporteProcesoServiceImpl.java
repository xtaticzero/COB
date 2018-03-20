package mx.gob.sat.siat.cob.seguimiento.service.catalogos.impl;

import java.io.ByteArrayOutputStream;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.EmailReporteProcesoDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.EmailReporteProceso;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.catalogos.EmailReporteProcesoService;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.exportador.excel.EmailReporteProcesoXLS;
import mx.gob.sat.siat.cob.seguimiento.util.exportador.pdf.EmailReporteProcesoPdf;
import mx.gob.sat.siat.cob.seguimiento.util.exportador.pdf.ModeloPDFExcel;
import mx.gob.sat.siat.exception.DaoException;
import mx.gob.sat.siat.modelo.dao.SegbMovimientosDAO;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmailReporteProcesoServiceImpl implements EmailReporteProcesoService {
    
    @Autowired
    private EmailReporteProcesoDAO emailReporteProcesoDAO;
    
    @Autowired
    @Qualifier("segbMovimientosDAOImpl")
    private SegbMovimientosDAO segbMovimientosDAO;
    
    @Transactional(readOnly = true)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public List<EmailReporteProceso> todosLosEmailReporteProceso(boolean isEmailEc) {
        return emailReporteProcesoDAO.todosLosEmailReporteProceso(isEmailEc);
    }
    
    @Transactional( readOnly = false)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public void agregaEmailReporteProceso(EmailReporteProceso emailReporteProceso, SegbMovimientoDTO segMovDto, boolean isEmailEc) throws SeguimientoDAOException, DaoException {

        emailReporteProcesoDAO.agregaEmailReporteProceso(emailReporteProceso, isEmailEc);
        segbMovimientosDAO.insert(segMovDto);

    }
    
    @Transactional( readOnly = true)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public void editaEmailReporteProceso(EmailReporteProceso emailReporteProceso, SegbMovimientoDTO segMovDto, boolean isEmailEc ) throws SeguimientoDAOException, DaoException {

        emailReporteProcesoDAO.editaEmailReporteProceso(emailReporteProceso, isEmailEc);
        segbMovimientosDAO.insert(segMovDto);

    }
    
    @Transactional( readOnly = true)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public void eliminaEmailReporteProceso(EmailReporteProceso emailReporteProceso, SegbMovimientoDTO segMovDto, boolean isEmailEc) throws SeguimientoDAOException, DaoException {

        emailReporteProcesoDAO.eliminaEmailReporteProceso(emailReporteProceso, isEmailEc);
        segbMovimientosDAO.insert(segMovDto);

    }
    
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public ByteArrayOutputStream generaExcel(List<EmailReporteProceso> list, boolean isEmailEc) {
        EmailReporteProcesoXLS emailReporteProcesoXLS = new EmailReporteProcesoXLS();
        return emailReporteProcesoXLS.generarExcel(generaModelo(list, isEmailEc));
    }
    
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public ByteArrayOutputStream generaPDF(List<EmailReporteProceso> list, boolean isEmailEc) {
        EmailReporteProcesoPdf emailReporteProcesoPdf = new EmailReporteProcesoPdf();
        return emailReporteProcesoPdf.generarPdf(generaModelo(list, isEmailEc));
    }
    
    public ModeloPDFExcel generaModelo(List<EmailReporteProceso> list, boolean isEmailEc) {
        ModeloPDFExcel modeloPDFExcel = new ModeloPDFExcel();
        modeloPDFExcel.setTitulo(isEmailEc?"Cat\u00e1logo de correos electr\u00f3nicos para reporte de datos nulos en EC":"Cat\u00e1logo de correos electr\u00f3nicos para aviso de procesos detenidos");
        modeloPDFExcel.setDatos(list);
        modeloPDFExcel.getNombreColumnas().add("Nombre completo");
        modeloPDFExcel.getNombreColumnas().add("Correo Electronico");
        modeloPDFExcel.getNombreColumnas().add("Correo Electronico Alterno");
        
        return modeloPDFExcel;
    }

}
