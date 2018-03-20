package mx.gob.sat.siat.cob.seguimiento.service.catalogos.impl;

import java.io.ByteArrayOutputStream;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.FuncionarioDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Combo;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Funcionario;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.catalogos.FuncionarioService;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.exportador.excel.FuncionarioXLS;
import mx.gob.sat.siat.cob.seguimiento.util.exportador.pdf.FuncionarioPdf;
import mx.gob.sat.siat.cob.seguimiento.util.exportador.pdf.ModeloPDFExcel;
import mx.gob.sat.siat.exception.DaoException;
import mx.gob.sat.siat.modelo.dao.SegbMovimientosDAO;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {

    @Autowired
    private FuncionarioDAO funcionarioDAO;
    @Autowired
    @Qualifier("segbMovimientosDAOImpl")
    private SegbMovimientosDAO segbMovimientosDAO;

    @Transactional(readOnly = true)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public List<Funcionario> todosLosFuncionarios() {
        return funcionarioDAO.todosLosFuncionarios();
    }

    @Transactional(readOnly = false)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public void agregaFuncionario(Funcionario funcionario, SegbMovimientoDTO segMovDto) throws SeguimientoDAOException, DaoException {

        funcionarioDAO.agregaFuncionario(funcionario);
        segbMovimientosDAO.insert(segMovDto);

    }

    @Transactional(readOnly = true)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public void editaFuncionario(Funcionario funcionario, SegbMovimientoDTO segMovDto) throws SeguimientoDAOException, DaoException {

        funcionarioDAO.editaFuncionario(funcionario);
        segbMovimientosDAO.insert(segMovDto);

    }

    @Transactional(readOnly = true)
    @Override
    public void reactivaFuncionario(Funcionario funcionario) throws SeguimientoDAOException {
        funcionarioDAO.reactivaFuncionario(funcionario);

    }

    @Transactional(readOnly = true)
    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public void eliminaFuncionario(Funcionario funcionario, SegbMovimientoDTO segMovDto) throws SeguimientoDAOException, DaoException {

        funcionarioDAO.eliminaFuncionario(funcionario);
        segbMovimientosDAO.insert(segMovDto);

    }

    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public Integer buscaFuncionarioPorIdYNom(Funcionario funcionario) {
        return funcionarioDAO.buscarFuncionarioPorIdYNom(funcionario);
    }

    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public ByteArrayOutputStream generaExcel(List<Funcionario> list) {
        FuncionarioXLS funcionarioXLS = new FuncionarioXLS();
        return funcionarioXLS.generarExcel(generaModelo(list));
    }

    public ModeloPDFExcel generaModelo(List<Funcionario> list) {
        ModeloPDFExcel modeloPDFExcel = new ModeloPDFExcel();
        modeloPDFExcel.setTitulo("Catalogo de Usuarios Internos");
        modeloPDFExcel.setDatos(list);
        modeloPDFExcel.getNombreColumnas().add("Numero de Empleado");
        modeloPDFExcel.getNombreColumnas().add("Nombre Funcionario");
        modeloPDFExcel.getNombreColumnas().add("Cargo");
        modeloPDFExcel.getNombreColumnas().add("Area de Adiscripci\u00f3n");
        modeloPDFExcel.getNombreColumnas().add("Correo Electronico");
        modeloPDFExcel.getNombreColumnas().add("Correo Electronico Alterno");
        modeloPDFExcel.getNombreColumnas().add("Situaci\u00f3n");

        return modeloPDFExcel;
    }

    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public ByteArrayOutputStream generaPDF(List<Funcionario> list) {
        FuncionarioPdf funcionarioPdf = new FuncionarioPdf();
        return funcionarioPdf.generarPdf(generaModelo(list));
    }

    @Override
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public List<Combo> obtenerComboArea() {
        return funcionarioDAO.obtenerComboArea();
    }

    @Override
    public void guardaNivelEmision() throws SGTServiceException {
        try {
            funcionarioDAO.guardaNivelEmision();
        } catch (SeguimientoDAOException ex) {
            throw new SGTServiceException(ex);
        }
    }

    @Override
    public void eliminaNivelEmision() throws SGTServiceException {
        try {
            funcionarioDAO.eliminaNivelEmision();
        } catch (SeguimientoDAOException ex) {
            throw new SGTServiceException(ex);
        }
    }
}
