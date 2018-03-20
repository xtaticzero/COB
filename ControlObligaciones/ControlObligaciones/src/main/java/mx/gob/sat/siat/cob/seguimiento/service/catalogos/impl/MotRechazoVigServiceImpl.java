package mx.gob.sat.siat.cob.seguimiento.service.catalogos.impl;

import java.io.ByteArrayOutputStream;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.MotRechazoVigDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MotRechazoVig;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.catalogos.MotRechazoVigService;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.exportador.excel.MotRechazoVigXLS;
import mx.gob.sat.siat.cob.seguimiento.util.exportador.pdf.ModeloPDFExcel;
import mx.gob.sat.siat.cob.seguimiento.util.exportador.pdf.MotRechazoVigPdf;
import mx.gob.sat.siat.exception.DaoException;
import mx.gob.sat.siat.modelo.dao.SegbMovimientosDAO;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class MotRechazoVigServiceImpl implements MotRechazoVigService{
    
    @Autowired
    private MotRechazoVigDAO motRechazoVigDAO;

    @Autowired
    @Qualifier("segbMovimientosDAOImpl")
    private SegbMovimientosDAO segbMovimientosDAO;

    @Transactional(readOnly = true)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public List<MotRechazoVig> todosLosMotivos() {
        return motRechazoVigDAO.todosLosMotivos();
    }

    @Transactional( readOnly = false)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public void agregaMotivo(MotRechazoVig motRechazoVig, SegbMovimientoDTO segMovDto) throws SeguimientoDAOException, DaoException {

        motRechazoVigDAO.agregaMotivo(motRechazoVig);
        segbMovimientosDAO.insert(segMovDto);

    }

    @Transactional( readOnly = true)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public void editaMotivo(MotRechazoVig motRechazoVig, SegbMovimientoDTO segMovDto) throws SeguimientoDAOException, DaoException {

        motRechazoVigDAO.editaMotivo(motRechazoVig);
        segbMovimientosDAO.insert(segMovDto);

    }

    @Transactional( readOnly = true)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public void eliminaMotivo(MotRechazoVig motRechazoVig, SegbMovimientoDTO segMovDto) throws SeguimientoDAOException, DaoException {

        motRechazoVigDAO.eliminaMotivo(motRechazoVig);
        segbMovimientosDAO.insert(segMovDto);

    }

    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public Integer buscaMotivoPorIdYNom(MotRechazoVig motRechazoVig) {
        return motRechazoVigDAO.buscarMotivoPorIdYNom(motRechazoVig);
    }

    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public ByteArrayOutputStream generaExcel(List<MotRechazoVig> list) {
        MotRechazoVigXLS motRechazoVigXLS = new MotRechazoVigXLS();
        return motRechazoVigXLS.generarExcel(generaModelo(list));
    }

    public ModeloPDFExcel generaModelo(List<MotRechazoVig> list) {
        ModeloPDFExcel modeloPDFExcel = new ModeloPDFExcel();
        modeloPDFExcel.setTitulo("Catalogo de Motivo Rechazo Vigilancia");
        modeloPDFExcel.setDatos(list);
        modeloPDFExcel.getNombreColumnas().add("Nombre");
        modeloPDFExcel.getNombreColumnas().add("Descripcion");
        return modeloPDFExcel;
    }

    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public ByteArrayOutputStream generaPDF(List<MotRechazoVig> list) {
        MotRechazoVigPdf motRechazoVigPdf = new MotRechazoVigPdf();
        return motRechazoVigPdf.generarPdf(generaModelo(list));
    }
}
