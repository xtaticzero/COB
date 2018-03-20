package mx.gob.sat.siat.cob.seguimiento.service.catalogos.impl;

import java.io.ByteArrayOutputStream;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.MultaDescuentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.MultaCobDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Combo;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.ComboStr;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MultaDescuento;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.catalogos.MultaDescuentoService;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.exportador.excel.MultaDescuentoXLS;
import mx.gob.sat.siat.cob.seguimiento.util.exportador.pdf.ModeloPDFExcel;
import mx.gob.sat.siat.cob.seguimiento.util.exportador.pdf.MultaDescuentoPdf;
import mx.gob.sat.siat.exception.DaoException;
import mx.gob.sat.siat.modelo.dao.SegbMovimientosDAO;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class MultaDescuentoServiceImpl implements MultaDescuentoService {

    @Autowired
    private MultaDescuentoDAO multaMontoDAO;
    @Autowired
    @Qualifier("segbMovimientosDAOImpl")
    private SegbMovimientosDAO segbMovimientosDAO;
    @Autowired
    private MultaCobDAO multaCobDAO;

    public MultaDescuentoServiceImpl() {
        super();
    }

    @Transactional(readOnly = true)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public List<MultaDescuento> todasLasMultaMonto() {
        return (List<MultaDescuento>) multaMontoDAO.todasLasMultaMonto();
    }

    @Transactional(readOnly = false)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public void agregarMultaMonto(MultaDescuento multaMonto, SegbMovimientoDTO segMovDto, Boolean existe) throws SeguimientoDAOException, DaoException {
        multaMontoDAO.agregarMultaMonto(multaMonto, existe);
        segbMovimientosDAO.insert(segMovDto);
    }

    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public Integer buscarMultaMontoRepetida(MultaDescuento multaMonto) {
        return multaMontoDAO.buscarMultaMontoRepetida(multaMonto);
    }

    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public List<ComboStr> obtenerComboResolMotivo() {
        return multaCobDAO.buscarTiposMulta();
    }

    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public List<Combo> obtenerComboMultaDescuento() {
        return multaMontoDAO.obtenerComboMultaDescuento();
    }

    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public ByteArrayOutputStream generaExcel(List<MultaDescuento> list) {
        MultaDescuentoXLS multaXLS = new MultaDescuentoXLS();
        return multaXLS.generarExcel(generaModelo(list));
    }

    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public ByteArrayOutputStream generaPDF(List<MultaDescuento> list) {
        MultaDescuentoPdf multa = new MultaDescuentoPdf();
        return multa.generarPdf(generaModelo(list));
    }

    public ModeloPDFExcel generaModelo(List<MultaDescuento> list) {
        ModeloPDFExcel modeloPDFExcel = new ModeloPDFExcel();
        modeloPDFExcel.setTitulo("Catalogo de Multa Descuento");
        modeloPDFExcel.setDatos(list);
        modeloPDFExcel.getNombreColumnas().add("Tipo de multa");
        modeloPDFExcel.getNombreColumnas().add("Descuento");
      
        return modeloPDFExcel;
    }
}
