package mx.gob.sat.siat.cob.seguimiento.service.renuente.impl;

import java.math.BigInteger;

import java.util.List;
import javax.mail.MessagingException;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.AdministrativoNivelCargoDAO;

import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.BuscaRenuentesDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AdministrativoNivelCargo;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.BuscaRenuentes;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Renuentes;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EventoCargaEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.otros.MailService;
import mx.gob.sat.siat.cob.seguimiento.service.renuente.BuscaRenuentesService;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.exception.DaoException;
import mx.gob.sat.siat.modelo.dao.SegbMovimientosDAO;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "buscaRenuentesService")
@Transactional
public class BuscaRenuentesServiceImpl implements BuscaRenuentesService {

    private Logger log = Logger.getLogger(BuscaRenuentesServiceImpl.class);
    @Autowired
    private BuscaRenuentesDAO buscaRenuentesDAO;
    @Autowired
    @Qualifier("segbMovimientosDAOImpl")
    private SegbMovimientosDAO segbMovimientosDAO;
    @Autowired
    private AdministrativoNivelCargoDAO administrativoNivelCargoDAO;
    @Autowired
    private MailService mailService;

    /**
     *
     */
    public BuscaRenuentesServiceImpl() {
        super();
    }

    @Override
    @Propagable(exceptionClass = SGTServiceException.class)
    public String buscaRenuentes(BuscaRenuentes buscaRenuentes) throws SGTServiceException {

        String info = "NUMEROCONTROL|RFC|ESTADO|CLAVE OBLIGACION|CONCEPTO OBLIGACION|CLAVE REGIMEN|CONCEPTO REGIMEN|EJERCICIO|PERIODO|PERIODICIDAD|FECHACUMPLIMIENTO\n";

        List<Renuentes> listaRenuentes = buscaRenuentesDAO.buscaRenuentes(buscaRenuentes);

        if (listaRenuentes != null) {
            StringBuilder sbInfo = new StringBuilder();

            for (Renuentes renuente : listaRenuentes) {
                sbInfo.append(renuente.getNumeroControl());
                sbInfo.append("|");
                sbInfo.append(renuente.getRfc());
                sbInfo.append("|");
                sbInfo.append(renuente.getEstado());
                sbInfo.append("|");
                sbInfo.append(renuente.getClave());
                sbInfo.append("|");
                sbInfo.append(renuente.getConcepto());
                sbInfo.append("|");
                sbInfo.append(renuente.getRegimen());
                sbInfo.append("|");
                sbInfo.append(renuente.getRegimenDesc());
                sbInfo.append("|");
                sbInfo.append(renuente.getEjercicio());
                sbInfo.append("|");
                sbInfo.append(renuente.getPeriodo());
                sbInfo.append("|");
                sbInfo.append(renuente.getPeriodicidad());
                sbInfo.append("|");
                sbInfo.append(renuente.getFechaCumplimiento());
                sbInfo.append("|");
                sbInfo.append("\n");
            }
            info += sbInfo;

        } else {
            throw new SGTServiceException("Ocurrió un error al consultar la información.");
        }

        return info;
    }

    @Override
    public BigInteger registraBitacora(SegbMovimientoDTO segMovDto) throws SGTServiceException {
        try {
            return segbMovimientosDAO.insert(segMovDto);
        } catch (DaoException e) {
            log.error(e.getMessage());

        }
        return null;
    }

    @Override
    public Long obtenerIdBusquedaRenuentes() throws SGTServiceException {
        try {
            return buscaRenuentesDAO.obtenerIdBusquedaRenuentes();
        } catch (SeguimientoDAOException ex) {
            throw new SGTServiceException(ex);
        }
    }

    @Override
    public Integer insertBuscaRenuentes(BuscaRenuentes buscaRenuentes) throws SGTServiceException {
        Integer resultado = null;
        resultado = buscaRenuentesDAO.insertBuscaRenuentes(buscaRenuentes);
        if (resultado == null) {
            throw new SGTServiceException("No se concluyo la inserccion satisfactoriamnete");
        }
        return resultado;

    }

    @Transactional(readOnly = true)
    @Override
    public BuscaRenuentes obtenerArchivoBusquedaRenuente() {
        return buscaRenuentesDAO.obtenerArchivoBusquedaRenuente();
    }

    @Override
    public void enviaCorreo(BuscaRenuentes buscaRenuentes) {
        try {
            List<AdministrativoNivelCargo> emails = administrativoNivelCargoDAO.buscarPorEventoCargaYNivelEmision(EventoCargaEnum.CARGA_OMISOS);
            StringBuilder sEmails = new StringBuilder("");
            for (AdministrativoNivelCargo administrativoNivelCargo : emails) {
                sEmails.append(administrativoNivelCargo.getCorreoElectronico()).append(",");
                if (administrativoNivelCargo.getCorreoElectronicoAlterno() != null) {
                    sEmails.append(administrativoNivelCargo.getCorreoElectronicoAlterno()).append(",");
                }
            }
            String[] destinatarios = sEmails.toString().split(",");
            StringBuilder mensaje = new StringBuilder();
            mensaje.append("<br/> El proceso de búsqueda de renuentes realizado el ").
                    append(buscaRenuentes.getFechaBusquedaStr()).
                    append(" concluyo exitosamente. Ingrese al aplicativo para ver el resultado.").
                    append("<br/> Por favor no responda a este mensaje, fue enviado desde una cuenta de correo electrónico no monitoreada.");
            log.info(mensaje);
            mailService.enviarCorreoPara(destinatarios, "MAT CO - " + "Búsqueda de renuentes", mensaje.toString());
        } catch (MessagingException ex) {
            log.error(ex.getMessage(), ex);
        }
    }
}
