package mx.gob.sat.siat.cob.seguimiento.service.catalogos.impl;

import java.io.ByteArrayOutputStream;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.UsuarioEFDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.rfcampliado.RfccEntidadDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Combo;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.UsuarioEF;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.catalogos.UsuarioEFService;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.exportador.excel.UsuarioEFXLS;
import mx.gob.sat.siat.cob.seguimiento.util.exportador.pdf.ModeloPDFExcel;
import mx.gob.sat.siat.cob.seguimiento.util.exportador.pdf.UsuarioEFPdf;
import mx.gob.sat.siat.exception.DaoException;
import mx.gob.sat.siat.modelo.dao.SegbMovimientosDAO;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioEFServiceImpl implements UsuarioEFService {

    @Autowired
    private UsuarioEFDAO usuarioEFDAO;
    @Autowired
    @Qualifier("segbMovimientosDAOImpl")
    private SegbMovimientosDAO segbMovimientosDAO;
    @Autowired
    private RfccEntidadDAO rfccEntidadDAO;

    @Transactional(readOnly = true)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public List<UsuarioEF> todosLosUsuariosEF() {
        return usuarioEFDAO.todosLosUsuariosEF();
    }

    @Transactional(readOnly = true)
    @Override
    public List<UsuarioEF> obtenerUsuarioPorRfcCorto(String rfc) throws SGTServiceException {
        List<UsuarioEF> usuarios = usuarioEFDAO.obtenerUsuarioPorRfcCorto(rfc);
        if (usuarios == null || usuarios.size() == 0) {
            throw new SGTServiceException("Usuario no encontrado, verifique cat\u00e1logo");
        }
        return usuarios;
    }

    @Transactional(readOnly = false)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public void agregaUsuarioEF(UsuarioEF usuarioEF, SegbMovimientoDTO segMovDto) throws SeguimientoDAOException, DaoException {

        usuarioEFDAO.agregaUsuarioEF(usuarioEF);
        segbMovimientosDAO.insert(segMovDto);

    }

    @Transactional(readOnly = true)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public void editaUsuarioEF(UsuarioEF usuarioEF, SegbMovimientoDTO segMovDto) throws SeguimientoDAOException, DaoException {

        usuarioEFDAO.editaUsuarioEF(usuarioEF);
        segbMovimientosDAO.insert(segMovDto);

    }

    @Transactional(readOnly = true)
    @Override
    public void reactivaUsuarioEF(UsuarioEF usuarioEF) throws SeguimientoDAOException {
        usuarioEFDAO.reactivaUsuarioEF(usuarioEF);

    }

    @Transactional(readOnly = true)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public void eliminaUsuarioEF(UsuarioEF usuarioEF, SegbMovimientoDTO segMovDto) throws SeguimientoDAOException, DaoException {

        usuarioEFDAO.eliminaUsuarioEF(usuarioEF);
        segbMovimientosDAO.insert(segMovDto);

    }

    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public Integer buscaUsuarioEFPorIdYNom(UsuarioEF usuarioEF) {
        return usuarioEFDAO.buscarUsuarioEFPorIdYNom(usuarioEF);
    }

    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public ByteArrayOutputStream generaExcel(List<UsuarioEF> list) {
        UsuarioEFXLS usuarioEFXLS = new UsuarioEFXLS();
        return usuarioEFXLS.generarExcel(generaModelo(list));
    }

    public ModeloPDFExcel generaModelo(List<UsuarioEF> list) {
        ModeloPDFExcel modeloPDFExcel = new ModeloPDFExcel();
        modeloPDFExcel.setTitulo("Catalogo de Usuario Entidad Federativa");
        modeloPDFExcel.setDatos(list);
        modeloPDFExcel.getNombreColumnas().add("Usuario");
        modeloPDFExcel.getNombreColumnas().add("Entidad Federativa");
        modeloPDFExcel.getNombreColumnas().add("Nombre Usuario");
        modeloPDFExcel.getNombreColumnas().add("Correo Electronico");
        modeloPDFExcel.getNombreColumnas().add("Situaci\u00f3n");

        return modeloPDFExcel;
    }

    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public ByteArrayOutputStream generaPDF(List<UsuarioEF> list) {
        UsuarioEFPdf usuarioEFPdf = new UsuarioEFPdf();
        return usuarioEFPdf.generarPdf(generaModelo(list));
    }

    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public List<Combo> obtenerComboEntidad() {
        return rfccEntidadDAO.obtenerComboEntidad();
    }

    @Transactional(readOnly = true)
    @Override
    public List<UsuarioEF> obtenerUsuariosPorEntidad(Long entidad) throws SGTServiceException {
        List<UsuarioEF> usuarios = usuarioEFDAO.obtenerUsuariosPorEntidad(entidad);
        if (usuarios == null || usuarios.size() == 0) {
            throw new SGTServiceException("Usuarios no encontrados, verifique cat\u00e1logo");
        }
        return usuarios;
    }
}
