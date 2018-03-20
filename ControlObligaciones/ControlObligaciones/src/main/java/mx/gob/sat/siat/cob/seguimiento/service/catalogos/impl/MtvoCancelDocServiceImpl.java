package mx.gob.sat.siat.cob.seguimiento.service.catalogos.impl;

import java.io.ByteArrayOutputStream;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.MtvoCancelDocDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MtvoCancelDoc;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.catalogos.MtvoCancelDocService;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.exportador.excel.MtvoCancelDocXLS;
import mx.gob.sat.siat.cob.seguimiento.util.exportador.pdf.ModeloPDFExcel;
import mx.gob.sat.siat.cob.seguimiento.util.exportador.pdf.MtvoCancelDocPdf;
import mx.gob.sat.siat.exception.DaoException;
import mx.gob.sat.siat.modelo.dao.SegbMovimientosDAO;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MtvoCancelDocServiceImpl implements MtvoCancelDocService {

    @Autowired
    private MtvoCancelDocDAO mtvoCancelDocDAO;

    @Autowired
    @Qualifier("segbMovimientosDAOImpl")
    private SegbMovimientosDAO segbMovimientosDAO;

    @Transactional(readOnly = true)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public List<MtvoCancelDoc> todosLosMotivos() {
        return mtvoCancelDocDAO.todosLosMotivos();
    }

    @Transactional( readOnly = false)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public void agregaMotivo(MtvoCancelDoc mtvoCancelDoc, SegbMovimientoDTO segMovDto) throws SeguimientoDAOException, DaoException {

        mtvoCancelDocDAO.agregaMotivo(mtvoCancelDoc);
        segbMovimientosDAO.insert(segMovDto);

    }

    @Transactional( readOnly = true)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public void editaMotivo(MtvoCancelDoc mtvoCancelDoc, SegbMovimientoDTO segMovDto) throws SeguimientoDAOException, DaoException {

        mtvoCancelDocDAO.editaMotivo(mtvoCancelDoc);
        segbMovimientosDAO.insert(segMovDto);

    }

    @Transactional( readOnly = true)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public void eliminaMotivo(MtvoCancelDoc mtvoCancelDoc, SegbMovimientoDTO segMovDto) throws SeguimientoDAOException, DaoException {

        mtvoCancelDocDAO.eliminaMotivo(mtvoCancelDoc);
        segbMovimientosDAO.insert(segMovDto);

    }

    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public Integer buscaMotivoPorIdYNom(MtvoCancelDoc mtvoCancelDoc) {
        return mtvoCancelDocDAO.buscarMotivoPorIdYNom(mtvoCancelDoc);
    }

    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public ByteArrayOutputStream generaExcel(List<MtvoCancelDoc> list) {
        MtvoCancelDocXLS mtvoCancelDocXLS = new MtvoCancelDocXLS();
        return mtvoCancelDocXLS.generarExcel(generaModelo(list));
    }

    public ModeloPDFExcel generaModelo(List<MtvoCancelDoc> list) {
        ModeloPDFExcel modeloPDFExcel = new ModeloPDFExcel();
        modeloPDFExcel.setTitulo("Catalogo de Motivo Cancelacion Documento");
        modeloPDFExcel.setDatos(list);
        modeloPDFExcel.getNombreColumnas().add("Nombre");
        modeloPDFExcel.getNombreColumnas().add("Descripcion");
        return modeloPDFExcel;
    }

    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public ByteArrayOutputStream generaPDF(List<MtvoCancelDoc> list) {
        MtvoCancelDocPdf mtvoCancelDocPDF = new MtvoCancelDocPdf();
        return mtvoCancelDocPDF.generarPdf(generaModelo(list));
    }

}
