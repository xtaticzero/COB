package mx.gob.sat.siat.cob.seguimiento.service.otros.impl;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.AdministrativoNivelCargoDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.ComboStr;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AdministrativoNivelCargo;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.FuncionarioSat;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EventoCargaEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.otros.AdministracionFuncionariosService;

import mx.gob.sat.siat.cob.seguimiento.util.Propagable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author root
 */
@Service
@Transactional
public class AdministracionFuncionariosServiceImpl implements AdministracionFuncionariosService {
    /**
     *
     */
    public AdministracionFuncionariosServiceImpl() {
        super();
    }

    @Autowired
    private AdministrativoNivelCargoDAO administrativoNivelCargoDAO;


    @Override
    public boolean validarFuncionarioExistente(FuncionarioSat empleado, List<FuncionarioSat> empleados) {
        boolean res = false;

        List<FuncionarioSat> empleadoExistente = administrativoNivelCargoDAO.buscarEmpleadosExistentes(empleado);

        if (empleadoExistente != null && empleadoExistente.size() > 0) {
            res = true;
        }

        return res;
    }
    
    @Override
    public boolean buscarEmpleadosNumEvento(FuncionarioSat empleado, List<FuncionarioSat> empleados) {
        boolean res = false;

        List<FuncionarioSat> empleadoExistente = administrativoNivelCargoDAO.buscarEmpleadosNumEvento(empleado);

        if (empleadoExistente != null && empleadoExistente.size() > 0) {
            res = true;
        }

        return res;
    }

    /**
     *
     * @return
     */
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.NESTED,
                   rollbackFor = { SeguimientoDAOException.class })
    @Override
    public List<FuncionarioSat> buscarFuncionarios() {

        return administrativoNivelCargoDAO.buscarFuncionarios();

    }

    /**
     *
     * @param numeroEmpleado
     */
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.NESTED,
                   rollbackFor = { SeguimientoDAOException.class })
    @Override
    public void eliminarRegistroFuncionario(String numeroEmpleado, Integer eventoCarga) {
        administrativoNivelCargoDAO.eliminarRegistroFuncionario(numeroEmpleado, eventoCarga);
    }

    /**
     *
     * @param funcionario
     * @throws SGTServiceException
     */
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.NESTED,
                   rollbackFor = { SeguimientoDAOException.class })
    @Override
    public void guardarFuncionario(FuncionarioSat funcionario) throws SGTServiceException {


        if (administrativoNivelCargoDAO.guardarFuncionario(funcionario) != null) {
            List<FuncionarioSat> empleadoExistente =
                administrativoNivelCargoDAO.buscarEmpleadosExistentes(funcionario);

            if (empleadoExistente != null && empleadoExistente.size() > 0) {
                administrativoNivelCargoDAO.bajaEmpleadosExistentes(funcionario);
            }
        } else {
            throw new SGTServiceException("Ya existe un registro en base de datos con el mismo n\u00famero de empleado y el mismo evento de carga");
        }

    }

    /**
     *
     * @return
     */
    @Override
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.NESTED,
                   rollbackFor = { SeguimientoDAOException.class })
    public List<CatalogoBase> buscarCargosAdministrativos() {
        return administrativoNivelCargoDAO.buscarCargosAdministrativos();
    }

    /**
     *
     * @return
     */
    @Override
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.NESTED,
                   rollbackFor = { SeguimientoDAOException.class })
    public List<CatalogoBase> buscarNivelesEmision() {
        return administrativoNivelCargoDAO.buscarNivelesEmision();
    }

    /**
     *
     * @return
     */
    @Override
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.NESTED,
                   rollbackFor = { SeguimientoDAOException.class })
    public List<CatalogoBase> buscarEventoCarga(Integer nivelEmision) {

        List<CatalogoBase> cat = administrativoNivelCargoDAO.buscarEventoCarga();

        if (nivelEmision == 1) {

            CatalogoBase catBorrar = null;
            for (CatalogoBase c : cat) {
                if (c.getId() == 2) {
                    catBorrar = c;
                }
            }
            cat.remove(catBorrar);

        }
        return cat;
    }

    /**
     *
     * @param funcionario
     * @throws SGTServiceException
     */
    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.NESTED,
                   rollbackFor = { SeguimientoDAOException.class })
    @Propagable(exceptionClass = SGTServiceException.class)
    public void actualizarRegistroFuncionario(FuncionarioSat funcionario) throws SGTServiceException {
        if (administrativoNivelCargoDAO.actualizarRegistroFuncionario(funcionario) != null) {
            List<FuncionarioSat> empleadoExistente =
                administrativoNivelCargoDAO.buscarEmpleadosExistentes(funcionario);

            if (empleadoExistente != null && empleadoExistente.size() > 0) {
                administrativoNivelCargoDAO.bajaEmpleadosExistentes(funcionario);
            }
        } else {
            throw new SGTServiceException("Ocurrio un error al guardar el registro");
        }
    }


    /**
     *
     * @param numeroEmpleado
     */
    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.NESTED,
                   rollbackFor = { SeguimientoDAOException.class })
    public void habilitarRegistroFuncionario(FuncionarioSat funcionario) throws SGTServiceException  {
        if (administrativoNivelCargoDAO.habilitarRegistroFuncionario(funcionario.getNumeroEmpleado(),funcionario.getIdEventoCarga()) != null) {
            
            List<FuncionarioSat> empleadoExistente =
                administrativoNivelCargoDAO.buscarEmpleadosExistentes(funcionario);

            if (empleadoExistente != null && empleadoExistente.size() > 0) {
                administrativoNivelCargoDAO.bajaEmpleadosExistentes(funcionario);
            } 
        }
    }

    /**
     *
     * @return
     */
    @Override
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.NESTED,
                   rollbackFor = { SeguimientoDAOException.class })
    public List<ComboStr> buscarRegionALR() {
        return administrativoNivelCargoDAO.buscarRegionALR();
    }

    /**
     *
     * @param idNivelEmision
     * @return
     */
    @Override
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.NESTED,
                   rollbackFor = { SeguimientoDAOException.class })
    public List<CatalogoBase> buscarCargoPorNivelEmision(Integer idNivelEmision) {
        return administrativoNivelCargoDAO.buscarCargoPorNivelEmision(idNivelEmision);
    }

    /**
     *
     * @return
     * @throws SGTServiceException
     */
    @Override
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.NESTED,
                   rollbackFor = { SeguimientoDAOException.class })
    @Propagable
    public List<ComboStr> obtenerEmpleadosAdministrativo() {
        return administrativoNivelCargoDAO.obtenerEmpleadosAdministrativo();
    }

    /**
     *
     * @param numeroEmpleado
     * @param eventoCargaEnum
     * @return
     * @throws SGTServiceException
     */
    @Override
    public AdministrativoNivelCargo buscarCargoAdministrativo(String numeroEmpleado,
                                                              EventoCargaEnum eventoCargaEnum) throws SGTServiceException {
        AdministrativoNivelCargo adminitrativo =
            administrativoNivelCargoDAO.buscarPorNumeroEmpleado(numeroEmpleado, eventoCargaEnum);
        if (adminitrativo == null) {
            throw new SGTServiceException("El empleado no esta registrado");
        }
        return adminitrativo;
    }
}
