package mx.gob.sat.siat.cob.background.service.carga.impl;

import java.net.SocketException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.mail.MessagingException;

import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.EjecucionSeguimientoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.VigilanciaDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ConsultaVigilancia;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EjecucionSeguimientoEnum;
import mx.gob.sat.siat.cob.background.service.carga.CargaInformacionHelper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.BaseDocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.VigilanciaAdministracionLocalDAO;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.background.service.carga.LogErrorCargaService;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.AdministrativoNivelCargoDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AdministrativoNivelCargo;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.NivelEmisionEnum;
import mx.gob.sat.siat.cob.seguimiento.service.otros.MailService;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author christian.ventura
 */
@Service
public class CargaInformacionHelperImpl implements CargaInformacionHelper{

    private Logger log =Logger.getLogger(CargaInformacionHelperImpl.class);

    private StringBuilder mensaje=new StringBuilder();

    @Autowired
    private EjecucionSeguimientoDAO ejecucionSeguimientoDAO;
    @Autowired
    private VigilanciaDAO vigilanciaDAO;
    @Autowired
    private BaseDocumentoDAO baseDocumentoDAO;
    @Autowired
    private VigilanciaAdministracionLocalDAO vigilanciaAdminLocalDAO;
    @Autowired
    private LogErrorCargaService logErrorCarga;
    @Autowired
    private MailService mailService;
    @Autowired
    private AdministrativoNivelCargoDAO administrativoNivelCargoDAO;
    
    /**
     * valida la ejecucion. procesando=1, detenido=0
     * @return
     * @throws SeguimientoDAOException
     */
    @Transactional(readOnly = true,
                   rollbackFor = {SeguimientoDAOException.class })
    @Override
    public boolean validaEjecucion() throws SeguimientoDAOException{
        EjecucionSeguimientoEnum ejec = ejecucionSeguimientoDAO.enEjecucion();
        return ejec.compareTo(EjecucionSeguimientoEnum.PROCESANDO)==0;
    }

    /**
     * cargando instancias
     * @return
     * @throws SeguimientoDAOException
     */
    @Transactional(readOnly = true,
                   rollbackFor = { SeguimientoDAOException.class })
    @Override
    public List<ConsultaVigilancia> cargaListadoVigilancias() throws SeguimientoDAOException {
        return vigilanciaDAO.consultaVigilancia();
    }

    /**
     * guarda documentos
     * @param documento
     * @param tipoDocumento
     * @throws SeguimientoDAOException
     */
    @Transactional(readOnly = true)
    @Override
    public boolean guardarDocumentos(Documento documento) {
        boolean estatus=false;
        Long idVigilancia=documento.getVigilancia().getIdVigilancia();
        logErrorCarga.getVigilancia().add(idVigilancia.intValue());
        mensaje.setLength(0);
        log.debug("\t\tGUARDANDO CABECERA DE DOCUMENTO ...");
        String resultado="";
        try{
            resultado = baseDocumentoDAO.guardaDocumentosDetalles(
                documento,documento.getTipoDocumento().getValor(),documento.getIdEtapaVigilancia());
        }catch(SQLException ex){
            log.error(ex);
            resultado="Error:"+ex.getErrorCode()+", "+ex.getSQLState()+", "+ ex.getLocalizedMessage();
        }catch(UncategorizedSQLException ex){
            log.error(ex);
            resultado="Error:"+", "+ ex.getCause().getMessage();
        }
        if(resultado.startsWith("errorUbicacion")){
            log.debug("datos no guardados");
            for(DetalleDocumento det:documento.getDetalles()){
                escribirErrorUbicacion(det,idVigilancia.toString());
            }
            logErrorCarga.escribirLog("",idVigilancia.toString());
            estatus=true;
        }
        else if(resultado.startsWith("Error:")){
            log.debug("datos no guardados");
            mensaje.append("boid:").append(documento.getBoid()).
                    append(", exception: documento no guardado por error al guardar su detalle");
            logErrorCarga.escribirLog(mensaje.toString(),idVigilancia.toString());
            for(DetalleDocumento det:documento.getDetalles()){
                escribirErrorDetalle(det,resultado,idVigilancia.toString());
            }
            logErrorCarga.escribirLog("",idVigilancia.toString());
            estatus=true;
        }
        return estatus;
    }

    /**
     *
     * @param det
     */
    private void escribirErrorDetalle(DetalleDocumento det, String exception, String idVigilancia){
        mensaje.setLength(0);
        mensaje.append("archivo:").append(det.getRutaArchivo());
        mensaje.append(", boid:").append(det.getBoid());
        mensaje.append(", icep:").append(det.getClaveIcep());
        mensaje.append(", obligacion:").append(det.getIdObligacion());
        mensaje.append(", exception: error al guardar el detalle: ");
        mensaje.append(exception.trim());
        logErrorCarga.getListaArchivos().add(det.getRutaArchivo());
        logErrorCarga.escribirLog(mensaje.toString(),idVigilancia);
    }

    /**
     * 
     * @param det
     */
    private void escribirErrorUbicacion(DetalleDocumento det, String idVigilancia){
        mensaje.setLength(0);
        mensaje.append("archivo:").append(det.getRutaArchivo());
        mensaje.append(", boid:").append(det.getBoid());
        mensaje.append(", icep:").append(det.getClaveIcep());
        mensaje.append(", obligacion:").append(det.getIdObligacion());
        mensaje.append(", exception: dato no guardado por no encontrar datos de ubicacion");
        logErrorCarga.getListaArchivos().add(det.getRutaArchivo());
        logErrorCarga.escribirLog(mensaje.toString(),idVigilancia);
    }

    /**
     * actualiza vigilancia
     * @param idVigilancia
     * @throws SeguimientoDAOException
     */
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED,
                   rollbackFor = { SeguimientoDAOException.class })
    @Override
    public void actualizarVigilancia(int idVigilancia) throws SeguimientoDAOException{
        vigilanciaDAO.actualizaVigilancia(idVigilancia);
    }

    /**
     * guarda datos vigilancia admin local
     * @param idVigilancia
     * @throws SeguimientoDAOException
     */
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED,
                   rollbackFor = { SeguimientoDAOException.class })
    @Override
    public void guardaVigAdminLocal(int idVigilancia) throws SeguimientoDAOException{
        vigilanciaAdminLocalDAO.guardaVigAdminLocal(idVigilancia);
    }

    /**
     * 
     * @param vigilancia
     * @throws MessagingException
     * @throws SQLException
     */
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED,
                   rollbackFor = { SeguimientoDAOException.class })
    @Override
    @Propagable(catched = true)
    public void envioCorreoPorNivelEmision(ConsultaVigilancia vigilancia)
                                        throws SQLException{
        log.debug("envioCorreoPorNivelEmision");
        mensaje.setLength(0);
        mensaje.append("Se le informa que la vigilancia <b>");
        mensaje.append(vigilancia.getIdVigilancia()).append("</b> "); 
        mensaje.append(vigilancia.getDescripcion()).append(" ");
        mensaje.append("fue cargada el ");
        mensaje.append(Utilerias.formatearFechaDDMMYYYY(new Date()));
        mensaje.append(" y para la cual existen documentos pendientes");
        mensaje.append(" de procesar que requieren de su autorizaci&oacute;n.");
        mensaje.append("<br>");

        mensaje.append("Por favor no responda a este mensaje, fue enviado desde ");
        mensaje.append("una cuenta de correo electr&oacute;nico no monitoreada.");
        if(NivelEmisionEnum.CENTRAL.getIdNivelEmision()==vigilancia.getNivelEmision()){
            log.debug("envioCorreo central");
            mensaje.append("<br>Central.");
            List<AdministrativoNivelCargo> empCentrales=
                administrativoNivelCargoDAO.getEmpleadosCentrales(vigilancia.getIdVigilancia());
            if(empCentrales!=null){
                for(AdministrativoNivelCargo empCentral:empCentrales){
                    try {
                        mailService.enviarCorreoIdEmpleado(empCentral.getNumeroEmpleado(),
                                "MAT CO - Carga de Vigilancia",
                                mensaje.toString());
                    } catch (MessagingException ex) {
                        log.error(ex);
                    } catch (SocketException ex) {
                        log.error(ex);
                    }
                }
            }
        }
        if(NivelEmisionEnum.LOCAL.getIdNivelEmision()==vigilancia.getNivelEmision()){
            log.debug("envioCorreo local");
            mensaje.append("<br>Local.");
            List<AdministrativoNivelCargo> empLocales=
                administrativoNivelCargoDAO.getEmpleadosLocales(vigilancia.getIdVigilancia());
            if(empLocales!=null){
                for(AdministrativoNivelCargo empLocal:empLocales){
                    try {
                        mailService.enviarCorreoIdEmpleado(empLocal.getNumeroEmpleado(),
                                "MAT CO - Carga de Vigilancia",
                                mensaje.toString());
                    } catch (MessagingException ex) {
                        log.error(ex);
                    } catch (SocketException ex) {
                        log.error(ex);
                    }
                }
            }
        }
        log.debug("termina envioCorreoPorNivelEmision");
    }

    /**
     * envia correo al usuario que cargo la vigilancia
     * @param vigilancia
     * @throws MessagingException
     * @throws SQLException 
     */
    @Override
    public void enviaCorreoErrorXVigilancia(ConsultaVigilancia vigilancia) throws SQLException {
        log.debug("envioCorreoEntidadFederativa");

        mensaje.setLength(0);
        mensaje.append("Se le informa que la vigilancia <b>");
        mensaje.append(vigilancia.getIdVigilancia()).append("</b> ");
        mensaje.append(vigilancia.getDescripcion()).append(" ");
        mensaje.append("fue cargada el ");
        mensaje.append(Utilerias.formatearFechaDDMMYYYY(new Date()));
        mensaje.append(".<br>");
        mensaje.append("Por favor no responda a este mensaje, fue enviado desde ");
        mensaje.append("una cuenta de correo electr&oacute;nico no monitoreada.");
        mensaje.append("<br>Entidad Federativa.");
        try {
            mailService.enviarCorreoIdEmpleado(
                vigilancia.getNumEmpleadoUsuario(),
                "MAT CO - Finaliza Carga de Vigilancia",
                mensaje.toString());
        } catch (MessagingException ex) {
            log.error(ex);
        } catch (SocketException ex) {
            log.error(ex);
        }
    }

    @Override
    public int guardarDocumentosHilos(List<Documento> documentos) throws SeguimientoDAOException {
        List<GuardaDoctoHilo> hilos= new ArrayList<GuardaDoctoHilo>();
        int contadorDocsError=0;
        for(Documento documento: documentos){
            hilos.add(new GuardaDoctoHilo(documento, this));
        }
        for(GuardaDoctoHilo hilo:hilos){
            log.debug("mandando guardar documento " + hilo.getDocumento().getNumeroControl());
            hilo.start();            
        }
        for(GuardaDoctoHilo hilo:hilos){
            try {
                hilo.join();
                log.debug("se ha terminado de guardar el documento " + hilo.getDocumento().getNumeroControl());
            } catch (InterruptedException ex) { log.error(ex);}
        }
        for(GuardaDoctoHilo hilo:hilos){
            if(hilo.isError()){
                contadorDocsError++;
            }
        }
        return contadorDocsError;
    }
    
    /**
     * 
     * @param idVigilancia
     * @return
     * @throws SeguimientoDAOException 
     */
    @Transactional(readOnly = true, rollbackFor = {SeguimientoDAOException.class})
    @Override
    public Integer consultaVigilanciasExistente(int idVigilancia) throws SeguimientoDAOException{
       return vigilanciaDAO.consultaVigilanciasExistente(idVigilancia);
    }
    
    /**
     * 
     * @param idVigilancia
     * @return
     * @throws SeguimientoDAOException 
     */
    @Transactional(readOnly = true, rollbackFor = {SeguimientoDAOException.class})
    @Override
    public Integer consultaVigilanciasExistenteEF(int idVigilancia) throws SeguimientoDAOException{
       return vigilanciaDAO.consultaVigilanciasExistenteEF(idVigilancia);
    }
    
}

class GuardaDoctoHilo extends Thread{
    private Documento documento;
    private CargaInformacionHelper cargaInformacionHelper;
    private boolean error =false;
    private Logger log =Logger.getLogger(CargaInformacionHelperImpl.class);
    public GuardaDoctoHilo( Documento documento, CargaInformacionHelper cargaInformacionHelper){
        this.documento = documento;
        this.cargaInformacionHelper = cargaInformacionHelper;
    }
    @Override
    public void run() {
        error=cargaInformacionHelper.guardarDocumentos(documento);
        if(error){
            log.error("no se guarda el documento");
        }
    }
    public boolean isError() {
        return error;
    }

    public Documento getDocumento() {
        return documento;
    }
}
