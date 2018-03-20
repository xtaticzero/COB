/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.renuente.impl;

import java.util.List;
import javax.mail.MessagingException;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.FuncionarioDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.AdministrativoNivelCargoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.CargaMotivoRenuenteDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Funcionario;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AdministrativoNivelCargo;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.CargaMotivoRenuente;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EventoCargaEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.aprobacion.impl.RechazarVigilanciasServiceImpl;
import mx.gob.sat.siat.cob.seguimiento.service.otros.MailService;
import mx.gob.sat.siat.cob.seguimiento.service.renuente.CargaMotivoRenuenteService;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author juan
 */
@Service
public class CargaMotivoRenuenteServiceImpl implements CargaMotivoRenuenteService {

    @Autowired
    private CargaMotivoRenuenteDAO cargaMotivoRenuenteDAO;
    @Autowired
    private MailService mailService;
    @Autowired
    private FuncionarioDAO funcionarioDAO;
    @Autowired
    private AdministrativoNivelCargoDAO administrativoNivelCargoDAO;
    private final Logger log = Logger.getLogger(RechazarVigilanciasServiceImpl.class);

    @Override
    @Transactional
    @Propagable(catched = true, exceptionClass = SGTServiceException.class)
    public void agregaMotivoRenuente(CargaMotivoRenuente renuente) {
        cargaMotivoRenuenteDAO.agregaMotivoRenuente(renuente);
    }

    @Override
    public void enviaCorreo(String numeroEmpleado) {
        try {
            List<AdministrativoNivelCargo> emails = administrativoNivelCargoDAO.buscarPorEventoCargaYNivelEmision(EventoCargaEnum.CARGA_OMISOS);
            StringBuilder sEmails = new StringBuilder("");
            for (AdministrativoNivelCargo administrativoNivelCargo : emails) {
                sEmails.append(administrativoNivelCargo.getCorreoElectronico()).append(",");
                if (administrativoNivelCargo.getCorreoElectronicoAlterno() != null) {
                    sEmails.append(administrativoNivelCargo.getCorreoElectronicoAlterno()).append(",");
                }
            }
            Funcionario funcionario = new Funcionario();
            funcionario.setNumeroEmpleado(numeroEmpleado);
            funcionario = funcionarioDAO.buscaFuncionarioPorID(funcionario);
            if (funcionario == null) {
                throw new SGTServiceException("Error, El funcionario no esta registrado");
            }
            String[] destinatarios = sEmails.toString().split(",");
            StringBuilder mensaje = new StringBuilder();
            mensaje.append("<br/> El proceso de carga de renuentes realizada por ").
                    append(funcionario.getNombreFuncionario()).
                    append(" concluyo exitosamente. Ingrese al aplicativo para ver el resultado.").
                    append("<br/> Por favor no responda a este mensaje, fue enviado desde una cuenta de correo electrónico no monitoreada.");
            log.info(mensaje);
            mailService.enviarCorreoPara(destinatarios, "MAT CO - " + "Carga de renuentes", mensaje.toString());
        } catch (MessagingException ex) {
            log.error(ex.getMessage(), ex);
        } catch (SGTServiceException ex) {
            log.error(ex.getMessage(), ex);
        }

    }
}
