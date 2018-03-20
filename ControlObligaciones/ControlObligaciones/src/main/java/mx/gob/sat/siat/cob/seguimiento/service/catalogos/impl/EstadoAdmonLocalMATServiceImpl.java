package mx.gob.sat.siat.cob.seguimiento.service.catalogos.impl;

import java.io.ByteArrayOutputStream;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.EstadoAdmonLocalMATDAO;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Combo;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.EstadoAdmonLocalMAT;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.catalogos.EstadoAdmonLocalMATService;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.exportador.excel.EstadoAdmonLocalMATXLS;
import mx.gob.sat.siat.cob.seguimiento.util.exportador.pdf.EstadoAdmonLocalMATPdf;
import mx.gob.sat.siat.cob.seguimiento.util.exportador.pdf.ModeloPDFExcel;
import mx.gob.sat.siat.exception.DaoException;
import mx.gob.sat.siat.modelo.dao.SegbMovimientosDAO;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EstadoAdmonLocalMATServiceImpl implements EstadoAdmonLocalMATService {

    @Autowired
    private EstadoAdmonLocalMATDAO estadoAdmonLocalMATDAO;
    @Autowired
    @Qualifier("segbMovimientosDAOImpl")
    private SegbMovimientosDAO segbMovimientosDAO;

    @Transactional(readOnly = true)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public List<EstadoAdmonLocalMAT> todosLosEstadosAdmonLocalMAT() {
        return estadoAdmonLocalMATDAO.todosLosEstadosAdmonLocalMAT();
    }

    @Transactional(readOnly = true)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public void editaEstadoAdmonLocalMAT(EstadoAdmonLocalMAT estadoAdmonLocalMAT, SegbMovimientoDTO segMovDto) throws SeguimientoDAOException, DaoException {

        estadoAdmonLocalMATDAO.editaEstadoAdmonLocalMAT(estadoAdmonLocalMAT);
        segbMovimientosDAO.insert(segMovDto);

    }

    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public ByteArrayOutputStream generaExcel(List<EstadoAdmonLocalMAT> list) {
        EstadoAdmonLocalMATXLS estadoAdmonLocalMATXLS = new EstadoAdmonLocalMATXLS();
        return estadoAdmonLocalMATXLS.generarExcel(generaModelo(list));
    }

    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public ByteArrayOutputStream generaPDF(List<EstadoAdmonLocalMAT> list) {
        EstadoAdmonLocalMATPdf estadoAdmonLocalMATPdf = new EstadoAdmonLocalMATPdf();
        return estadoAdmonLocalMATPdf.generarPdf(generaModelo(list));
    }

    public ModeloPDFExcel generaModelo(List<EstadoAdmonLocalMAT> list) {
        ModeloPDFExcel modeloPDFExcel = new ModeloPDFExcel();
        modeloPDFExcel.setTitulo("Relación de Administraciones Locales con Operación en MAT Cobranza");
        modeloPDFExcel.setDatos(list);
        modeloPDFExcel.getNombreColumnas().add("Clave Administración Local");
        modeloPDFExcel.getNombreColumnas().add("Nombre");
        modeloPDFExcel.getNombreColumnas().add("¿Opera con MAT Cobranza?");

        return modeloPDFExcel;
    }

    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public List<Combo> obtenerComboEstado() {
        return estadoAdmonLocalMATDAO.obtenerComboEstado();
    }
}
