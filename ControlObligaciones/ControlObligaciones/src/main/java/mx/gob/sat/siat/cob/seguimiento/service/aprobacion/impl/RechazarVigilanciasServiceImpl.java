/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.aprobacion.impl;

import java.net.SocketException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.mail.MessagingException;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.FuncionarioDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.AdministrativoNivelCargoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DocumentoAprobarDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.VigilanciaAdministracionLocalDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Funcionario;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AdministrativoNivelCargo;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DocumentoAprobar;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaAdministracionLocal;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EventoCargaEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.SituacionVigilanciaEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.aprobacion.RechazarVigilanciasService;
import mx.gob.sat.siat.cob.seguimiento.service.otros.MailService;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author root
 */
@Service
public class RechazarVigilanciasServiceImpl implements RechazarVigilanciasService {

    @Autowired
    private DocumentoAprobarDAO documentoAprobarDAO;
    @Autowired
    private AdministrativoNivelCargoDAO administrativoNivelCargoDAO;
    @Autowired
    private VigilanciaAdministracionLocalDAO vigilanciaAdministracionLocalDAO;
    @Autowired
    private MailService mailService;
    @Autowired
    private FuncionarioDAO funcionarioDAO;
    private final Logger log = Logger.getLogger(RechazarVigilanciasServiceImpl.class);

    @Override
    @Transactional(rollbackFor = SGTServiceException.class)
    public void rechazar(VigilanciaAdministracionLocal vigilancia, String numeroEmpleado) throws SGTServiceException {
        vigilancia.setSituacionVigilanciaEnum(SituacionVigilanciaEnum.RECHAZADA);
        vigilancia.setNumeroEmpleado(numeroEmpleado);
        if (vigilanciaAdministracionLocalDAO.actualizarVigilancias(vigilancia) == null) {
            throw new SGTServiceException("No se pudo actualizar el estado de la vigilancia");
        }
        if (documentoAprobarDAO.bitacoraDocumentos(vigilancia.getNumeroCarga() + "",
                vigilancia.getIdAdministracionLocal(),
                EstadoDocumentoEnum.NO_PROCESADO) == null) {
            throw new SGTServiceException("No se pudo actualizar el estado de los documentos de la vigilancia");
        }
        if (documentoAprobarDAO.actualizarDocumentos(vigilancia.getNumeroCarga() + "",
                vigilancia.getIdAdministracionLocal(), EstadoDocumentoEnum.NO_PROCESADO) == null) {
            throw new SGTServiceException("No se pudo registrar los cambios de estado en la bitacora de documentos");
        }
        String cuerpoCorreo = obtenerCorreoRechazo(vigilancia, numeroEmpleado);
        try {
            List<AdministrativoNivelCargo> empleados
                    = administrativoNivelCargoDAO.buscarPorEventoCarga(
                            EventoCargaEnum.RECHAZO_VIGILANCIA);
            for (AdministrativoNivelCargo anc : empleados) {
                mailService.enviarCorreoIdEmpleado(
                        anc.getNumeroEmpleado(),
                        "MAT CO - Rechazo de Vigilancia",
                        cuerpoCorreo);
            }
        } catch (SQLException e) {
            log.error("No se pudo enviar correro -->" + e.getMessage(), e);
            log.error("Correo No Enviado -->" + cuerpoCorreo);
        } catch (MessagingException e) {
            log.error("No se pudo enviar correro -->" + e.getMessage(), e);
            log.error("Correo No Enviado -->" + cuerpoCorreo);
        } catch (SocketException ex) {
            log.error("No se pudo enviar correro -->" + ex.getMessage(), ex);
            log.error("Correo No Enviado -->" + cuerpoCorreo);
        }
    }

    @Override
    @Transactional(rollbackFor = SGTServiceException.class)
    public void rechazarPorNumeroDocumento(Set<DocumentoAprobar> documentos) throws SGTServiceException {
        if (!documentos.isEmpty()) {
            Set<String> numerosControl = new HashSet<String>();
            for (DocumentoAprobar documentoAprobar : documentos) {
                numerosControl.add(documentoAprobar.getNumeroControl());
            }
            if (documentoAprobarDAO.
                    rechazarDocumentosPorNumeroControl(numerosControl) == null) {
                throw new SGTServiceException(
                        "No se pudieron rechazar los documentos");
            }
            if (documentoAprobarDAO.
                    bitacoraRechazarDocumentosPorNumeroControl(numerosControl) == null) {
                throw new SGTServiceException(
                        "No se pudo insertar en bitacora el rechazo de documentos");
            }
        }
    }

    private String obtenerCorreoRechazo(VigilanciaAdministracionLocal vigilancia, String numeroEmpleado) throws SGTServiceException {
        Funcionario funcionario = new Funcionario();
        funcionario.setNumeroEmpleado(numeroEmpleado);
        funcionario = funcionarioDAO.buscaFuncionarioPorID(funcionario);
        if (funcionario == null) {
            throw new SGTServiceException("Error, El funcionario no esta registrado");
        }
        Map map = new HashMap<String, String>();
        map.put("numeroCarga", vigilancia.getNumeroCarga());
        map.put("descripcionVigilancia", vigilancia.getDescripcionVigilancia());
        map.put("fechaCarga", Utilerias.formatearFechaDDMMAAAA(vigilancia.getFechaEnvioArca()));
        map.put("nombreFuncionario", funcionario.getNombreFuncionario());
        map.put("nombreAdministracion", funcionario.getDescAreaAdscripcion());
        map.put("motivoRechazo", vigilancia.getMotivoRechazoVigilancia().getNombre());
        return Utilerias.obtenerCorreo("rechazoVigilancia", map);
    }
}
