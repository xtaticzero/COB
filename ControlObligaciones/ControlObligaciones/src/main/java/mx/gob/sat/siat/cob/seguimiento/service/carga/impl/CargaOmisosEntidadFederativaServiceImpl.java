package mx.gob.sat.siat.cob.seguimiento.service.carga.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import javax.mail.MessagingException;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.FuncionarioDAO;

import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.MotRechazoVigDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.AdministrativoNivelCargoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.VigilanciaEntidadFederativaDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Funcionario;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MotRechazoVig;
import mx.gob.sat.siat.cob.seguimiento.dto.db2.Paginador;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AdministrativoNivelCargo;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaEntidadFederativa;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EventoCargaEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.carga.CargaOmisosEntidadFederativaService;

import mx.gob.sat.siat.cob.seguimiento.service.otros.MailService;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;

import mx.gob.sat.siat.modelo.dao.SegbMovimientosDAO;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class CargaOmisosEntidadFederativaServiceImpl implements CargaOmisosEntidadFederativaService {

    private final Logger logger = Logger.getLogger(getClass());
    @Autowired
    private VigilanciaEntidadFederativaDAO vigilanciaEntidadFederativaDAO;
    @Autowired
    private MotRechazoVigDAO motRechazoVigDAO;
    @Autowired
    @Qualifier("segbMovimientosDAOImpl")
    private SegbMovimientosDAO segbMovimientosDAO;
    @Autowired
    private MailService mailService;
    @Autowired
    private DocumentoDAO documentoDao;
    @Autowired
    private AdministrativoNivelCargoDAO administrativoNivelCargoDAO;
    @Autowired
    private FuncionarioDAO funcionarioDAO;

    /**
     *
     * @param claveEf
     * @return
     * @throws SGTServiceException
     */
    @Override
    public List<VigilanciaEntidadFederativa> obtenerVigilancias(Long claveEf) throws SGTServiceException {

        List<VigilanciaEntidadFederativa> vef = vigilanciaEntidadFederativaDAO.obtenerVigilancias(claveEf);

        if (vef == null) {
            throw new SGTServiceException("Error al obtener las vigilancias por entidad Federativa, verifique el usuario");
        }

        return vef;
    }

    /**
     *
     * @return @throws SGTServiceException
     */
    @Override
    public List<VigilanciaEntidadFederativa> obtenerVigilancias() throws SGTServiceException {

        List<VigilanciaEntidadFederativa> vef = vigilanciaEntidadFederativaDAO.obtenerVigilancias();

        if (vef == null) {
            throw new SGTServiceException("Error al obtener las vigilancias por entidad Federativa, verifique el usuario");
        }

        return vef;
    }

    /**
     *
     * @return @throws SGTServiceException
     */
    @Override
    public Long obtenerCantidadVigilancias() throws SGTServiceException {
        return vigilanciaEntidadFederativaDAO.obtenerCantidadVigilancias();
    }

    /**
     *
     * @return @throws SGTServiceException
     */
    @Override
    public List<MotRechazoVig> obtenerMotivosRechazo() throws SGTServiceException {

        return motRechazoVigDAO.todosLosMotivos();
    }

    /**
     *
     * @param vef
     * @param dto
     * @throws SGTServiceException
     */
    @Override
    @Transactional(readOnly = false,
            isolation = Isolation.DEFAULT,
            propagation = Propagation.NESTED,
            rollbackFor = {SeguimientoDAOException.class})
    public void guardarRechazo(VigilanciaEntidadFederativa vef, SegbMovimientoDTO dto, String numeroEmpleado) throws SGTServiceException {
        String cuerpoCorreo = "";
        try {
            logger.debug("Insert segbmovimiento: " + segbMovimientosDAO.insert(dto));
        } catch (Exception e) {
            logger.error("Error al insertar en SegbMovimiento para CargaOmisosEF", e);
        }
        try {
            cuerpoCorreo = obtenerCorreoRechazo(vef, numeroEmpleado);
            List<AdministrativoNivelCargo> empleados = administrativoNivelCargoDAO.buscarPorEventoCarga(
                    EventoCargaEnum.RECHAZO_VIGILANCIA);
            for (AdministrativoNivelCargo anc : empleados) {
                mailService.enviarCorreoIdEmpleado(
                        anc.getNumeroEmpleado(),
                        "MAT CO - Rechazo de Vigilancia Entidad Federativa",
                        cuerpoCorreo);
            }
            vef.setEstadoDocumento(EstadoDocumentoEnum.NO_PROCESADO.getValor());
            if (vigilanciaEntidadFederativaDAO.guardarRechazo(vef) == null) {
                throw new SGTServiceException("Error al guardar el motivo de rechazo");
            }

            if (documentoDao.actualizarEstatusDocumento(vef) == null) {
                throw new SGTServiceException("Error al actualizar estatus documento");
            }

            if (documentoDao.insertarBitacoraDocumentos(vef) == null) {
                throw new SGTServiceException("Error al insertar bitacora documento");
            }
        } catch (SQLException e) {
            logger.error("No se pudo enviar correro -->" + e.getMessage(), e);
            logger.error("Correo No Enviado -->" + cuerpoCorreo);
        } catch (MessagingException e) {
            logger.error("No se pudo enviar correro -->" + e.getMessage(), e);
            logger.error("Correo No Enviado -->" + cuerpoCorreo);
        } catch (SocketException ex) {
            logger.error("No se pudo enviar correro -->" + ex.getMessage(), ex);
            logger.error("Correo No Enviado -->" + cuerpoCorreo);
        }
    }

    /**
     *
     * @param vef
     * @param dto
     * @throws SGTServiceException
     */
    @Override
    @Transactional(readOnly = false,
            isolation = Isolation.DEFAULT,
            propagation = Propagation.NESTED,
            rollbackFor = {SeguimientoDAOException.class})
    public void aceptarVigilancia(VigilanciaEntidadFederativa vef, SegbMovimientoDTO dto) throws SGTServiceException {

        try {
            logger.debug("Insert segbmovimiento: " + segbMovimientosDAO.insert(dto));
        } catch (Exception e) {
            logger.error("Error al insertar en SegbMovimiento para CargaOmisosEF", e);
        }
        if (vigilanciaEntidadFederativaDAO.aceptarVigilancia(vef) == null) {
            throw new SGTServiceException("Error al aceptar la vigilancia");
        }
    }

    /**
     *
     * @param rutaArchivo
     * @return
     * @throws SGTServiceException
     */
    @Override
    public InputStream generarArchivoVigilanciaEF(String rutaArchivo) throws SGTServiceException {

        try {
            return new FileInputStream(new File(rutaArchivo));
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new SGTServiceException("No se pudo crear el archivo, por favor intente mas tarde", e);
        }
    }

    /**
     *
     * @param file
     * @return
     * @throws SGTServiceException
     */
    @Override
    public FileInputStream generarArchivoZip(String file) throws SGTServiceException {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new SGTServiceException("No se pudo crear el archivo, por favor intente mas tarde", e);
        }

    }

    @Override
    public List<VigilanciaEntidadFederativa> obtenerVigilanciasPaginadas(Paginador paginador) throws SGTServiceException {
        return vigilanciaEntidadFederativaDAO.obtenerVigilanciasPaginadas(paginador);
    }

    private String obtenerCorreoRechazo(VigilanciaEntidadFederativa vef, String numeroEmpleado) throws SGTServiceException {
        Funcionario funcionario = new Funcionario();
        funcionario.setNumeroEmpleado(numeroEmpleado);
        funcionario = funcionarioDAO.buscaFuncionarioPorID(funcionario);
        MotRechazoVig aux = new MotRechazoVig();
        aux.setIdMotivoRechazoVig(vef.getIdMotivoRechazo());
        MotRechazoVig motivo = motRechazoVigDAO.buscaMotivoPorID(aux);
        if (funcionario == null) {
            throw new SGTServiceException("Error, El funcionario no esta registrado");
        }
        Map map = new HashMap<String, String>();
        map.put("numeroCarga", vef.getIdVigilancia().toString());
        map.put("descripcionVigilancia", vef.getDescripcionVigilancia());
        map.put("fechaCarga", vef.getDescFechaCargaArchivos());
        map.put("nombreFuncionario", funcionario.getNombreFuncionario());
        map.put("nombreAdministracion", funcionario.getDescAreaAdscripcion());
        map.put("motivoRechazo", motivo.getNombre());
        return Utilerias.obtenerCorreo("rechazoVigilancia", map);
    }
}
