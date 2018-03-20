package mx.gob.sat.siat.cob.background.service.cargaentidadfederativa.impl;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mx.gob.sat.siat.cob.background.service.cargaentidadfederativa.ArchivoOmisosEFService;
import mx.gob.sat.siat.cob.background.service.cargaentidadfederativa.LogArchivoEFService;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.UsuarioEFDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.UsuarioEF;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ConsultaVigilancia;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleReporteFacturaBean;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.RfcNombreDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.UbicacionEFDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EtapaVigilanciaEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.extraccion.DocumentoService;
import mx.gob.sat.siat.cob.seguimiento.service.otros.CicloDocEtapaService;
import mx.gob.sat.siat.cob.seguimiento.service.otros.MailService;
import mx.gob.sat.siat.cob.seguimiento.service.otros.OmisosEFService;
import mx.gob.sat.siat.cob.seguimiento.service.otros.ReporterService;
import mx.gob.sat.siat.cob.seguimiento.util.ProcesoEnvioEMail;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import mx.gob.sat.siat.cob.seguimiento.util.constante.TipoEnvioEnum;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

/**
 *
 * @author christian.ventura
 */
@Service
public class ArchivoOmisosEFServiceImpl implements ArchivoOmisosEFService {

    private Logger log =Logger.getLogger(ArchivoOmisosEFServiceImpl.class);

    private static final int CONJUNTO_ARCHIVOS_MAXIMO = 10;
    private static final int ARCHIVOS_MAXIMO = 10;
    private static final int TIEMPO_ESPERA_ARCHIVO_OMISOS=1000;
    private static final String MEDIO_ENVIO_EF = "06";
    private static final int NUM_MAX_OBLIGACIONES = 8;
    private static final String ENCABEZADO_FUNDAMENTO_LEGAL=
            "Vigilancia|Obligacion IDC|Ejercicio Fiscal|Periodo|Regimen|"
            + "Descripcion de la obligacion|Descripcion del fundamento legal|Fecha de Vencimiento";

    @Autowired
    private OmisosEFService omisosEFService;

    @Autowired
    private LogArchivoEFService logArchivoOmisos;

    @Autowired
    private LogArchivoEFService logArchivoFundamentoLeg;

    @Autowired
    private LogArchivoEFService logArchivoFactura;

    @Autowired
    private ReporterService reporterService;

    @Autowired
    private CicloDocEtapaService cicloDocEtapaService;

    @Autowired
    private DocumentoService documentoService;
    
    @Autowired
    private MailService mailService;
    
    @Autowired
    @Qualifier("taskExecutorEF")
    private ThreadPoolTaskExecutor taskExecutor;
    
    @Autowired
    private UsuarioEFDAO usuarioEFDAO;
    
    @Autowired
    @Qualifier("taskExecutorMail")
    private ThreadPoolTaskExecutor taskExecutorMail;

    /**
     * metodo principal
     */
    @Propagable(catched = true)
    @Override
    public void generaArchivoOmisos(){
        log.info("generaArchivoOmisosEF");
        List<Integer> listaIdentidadFed=null;
        List<ConsultaVigilancia> vigilancias=null;
        List<RfcNombreDTO> listaBoid=new ArrayList<RfcNombreDTO>();
        try {
            vigilancias = omisosEFService.consultaVigilanciasEF();
            for (ConsultaVigilancia vigilancia : vigilancias) {
                listaIdentidadFed=omisosEFService.consultarListaEntidadesFed(vigilancia.getIdVigilancia());
                if(!listaIdentidadFed.isEmpty()){
                    for(Integer identidadF:listaIdentidadFed){
                        log.debug(".........................."+identidadF+"..........................");

                        //generar archivos de omisos
                        List<RfcNombreDTO> listaTemp=generarArchivoOmisos(vigilancia.getIdVigilancia(), identidadF);
                        if(listaTemp!=null){
                            listaBoid.addAll(listaTemp);
                        }
                        //genera archivo de fundamentos legales
                        generaArchivoLegal(vigilancia.getIdVigilancia(),identidadF);
                        
                        //genera reporte de factura
                        generaArchivoFactura(vigilancia.getIdVigilancia(),identidadF);
                    }
                    //inserta los archivos, num doc totales y detalles de la vigilancia en vigilanciaEF
                    insertaDatosVigilanciaEF(vigilancia.getIdVigilancia(),listaIdentidadFed);
                    
                    //valida y cambia estado de documento
                    actualizaEstadoDocumentos(vigilancia);

                    //registra en bitacora el cambio de estado
                    actualizaEstadoBitacoraDocumento(listaBoid);
                }

                //actualiza el estado de vigilancia a cargado
                omisosEFService.actualizarVigilancia(vigilancia.getIdVigilancia());

                //revisar si hay mas de 10 archivos en los directorios y borrarlos
                verificarCantidadArchivosDirectorios();
                
                //envia correo a las entidades federativas que estan relacionadas
                envioCorreoEntidadFederativa(vigilancia);
                
                //enviua correo al que cargo la vigilancia
                enviaCorreoUsuarioXVigilancia(vigilancia);
            }
        }
        catch (SGTServiceException ex) {
            log.error(ex);
        }
        catch (SeguimientoDAOException ex) {
            log.error(ex);
        }
        catch (SQLException ex) {
            log.error(ex);
        }
        log.debug("termina generaArchivoOmisos");
    }

    /**
     * registra el cambio de estado de pendiente de notificar en la bitacora de sgtDocumento
     * @param documentos
     * @throws SeguimientoDAOException
     */
    private void actualizaEstadoBitacoraDocumento(List<RfcNombreDTO> documentos) throws SeguimientoDAOException{
        List<Documento> docs = new ArrayList<Documento>();
        for(RfcNombreDTO dato: documentos){
            Documento doc=new Documento();
            doc.setNumeroControl(dato.getNumeroControl());
            doc.setEsUltimoEstado(EstadoDocumentoEnum.PENDIENTE_DE_NOTIFICAR.getValor());
            docs.add(doc);
        }
        documentoService.guardaSgtDocumentoBatch(docs);
    }

    /**
     * 
     * @param idVigilancia
     * @param identidadFed
     * @return 
     */
    private List<RfcNombreDTO> generarArchivoOmisos(int idVigilancia, int identidadFed){
        StringBuilder nombreArchivo = new StringBuilder();
        List<RfcNombreDTO> listaBoid =
                omisosEFService.consultaBoidDatosGenerales(idVigilancia,identidadFed);
        nombreArchivo.setLength(0);
        log.debug("----------------------------------------------------------");
        agrupaObligaciones(listaBoid);
        log.debug("----------------------------------------------------------");
        for (RfcNombreDTO registro : listaBoid) {
            if(nombreArchivo.length()==0){
                nombreArchivo.append(idVigilancia).append("_").
                        append(Utilerias.formatearFechaDDMMAAAA(new Date())).append("_omisos_EF");
                logArchivoOmisos.setNombreArchivo(
                        String.valueOf(registro.getIdFederativa()),
                        nombreArchivo.toString());
                logArchivoOmisos.getListaArchivo().put(registro.getIdFederativa(),
                        logArchivoOmisos.getNombreArchivo());
            }
            taskExecutor.execute(new EscribirDatosUbicacion(omisosEFService, registro, logArchivoOmisos));
        }
        int count = -1;
        while(count != 0) {
            try {
                Thread.sleep(TIEMPO_ESPERA_ARCHIVO_OMISOS);
            } catch (InterruptedException e) {
                log.error(e);
            }
            count = taskExecutor.getActiveCount();
            log.debug("------Active Threads : " + count);
            if (count == 0) {
                log.debug("termina generarArchivoOmisos");
            }
        }
        Utilerias.convertirArchivoAZip(logArchivoOmisos.getNombreArchivoTmp(), logArchivoOmisos.getNombreArchivo());
        return listaBoid;
    }
    
    /**
     * procedimiento para agrupar las obligaciones hasta un maximo de 8 y eliminar duplicados
     * 
     * @param listaBoid 
     */
    private void agrupaObligaciones(List<RfcNombreDTO> listaBoid){
        log.debug("inicia agrupaObligaciones:"+listaBoid.size());
        Collections.sort(listaBoid);
        StringBuilder obligaciones=new StringBuilder();
        RfcNombreDTO regAnterior=new RfcNombreDTO();
        List<RfcNombreDTO> lstBorrar=new ArrayList<RfcNombreDTO>();
        int contadorObligaciones=1;
        for (int i=0;i<listaBoid.size();i++) {
            RfcNombreDTO registro = listaBoid.get(i);
            if(registro.getRfc().equals(regAnterior.getRfc()) && ++contadorObligaciones <= NUM_MAX_OBLIGACIONES ){
                obligaciones.setLength(0);
                obligaciones.append(regAnterior.getObligaciones()).append("|").
                        append(registro.getObligaciones());
                registro.setObligaciones(obligaciones.toString());
                lstBorrar.add(regAnterior);
                regAnterior=registro;
                if(i==(listaBoid.size()-1)) {
                    agregaObligDefault(contadorObligaciones,obligaciones,registro);
                }
            }else{
                if(obligaciones.length()>0){
                    agregaObligDefault(contadorObligaciones,obligaciones,regAnterior);
                    obligaciones.setLength(0);
                    contadorObligaciones=1;
                }else{
                    obligaciones.append(regAnterior.getObligaciones());
                    agregaObligDefault(contadorObligaciones,obligaciones,regAnterior);
                    obligaciones.setLength(0);
                }
                if(i==(listaBoid.size()-1)) {
                    obligaciones.setLength(0);
                    obligaciones.append(registro.getObligaciones());
                    agregaObligDefault(contadorObligaciones,obligaciones,registro);
                }
                regAnterior=registro;
            }
        }
        for (RfcNombreDTO borrar : lstBorrar) {
            listaBoid.remove(borrar);
        }
    }
    
    /**
     * 
     * @param contadorObligaciones
     * @param obligaciones
     * @param registro 
     */
    private void agregaObligDefault(int contadorObligaciones, StringBuilder obligaciones, RfcNombreDTO registro){
        for(int cont=contadorObligaciones;cont<NUM_MAX_OBLIGACIONES;cont++){
            obligaciones.append("||||");
        }
        registro.setObligaciones(obligaciones.toString());
    }

    /**
     * 
     * @param idVigilancia
     * @param listaIdentidadFed
     */
    public void insertaDatosVigilanciaEF(int idVigilancia,List<Integer> listaIdentidadFed){
        for(Integer identidadF:listaIdentidadFed){
            omisosEFService.insertaVigilanciaEF(idVigilancia,
                identidadF,
                logArchivoOmisos.getListaArchivo().get(identidadF),
                logArchivoFundamentoLeg.getListaArchivo().get(identidadF),
                logArchivoFactura.getListaArchivo().get(identidadF));
        }
    }

    /**
     *verificar Cantidad de Archivos en Directorios de entidades
     */
    private void verificarCantidadArchivosDirectorios(){
        File dirPrincipal=new File(LogArchivoEFServiceImpl.getRUTAENTIDADFED());
        File[] directoriosEntidades = dirPrincipal.listFiles();
        log.debug(directoriosEntidades.length);
        for(File dirEntidad:directoriosEntidades){
            if(dirEntidad.isDirectory()){
                File[] archivosGenerados=dirEntidad.listFiles();
                log.debug("archivosGenerados.length"+archivosGenerados.length);
                if(archivosGenerados.length>ARCHIVOS_MAXIMO){
                    //ordenar por ultima modificacion descendentemente
                    Arrays.sort(archivosGenerados, new Comparator<File>() {
                        @Override
                        public int compare(File o1, File o2) {
                            if(o1.lastModified() < o2.lastModified()){
                                return 1;
                            }else if(o1.lastModified() > o2.lastModified()){
                                return -1;
                            }else{
                                return 0;
                            }
                        }
                    });
                    borrarArchivos(archivosGenerados);
                }
            }
        }
        log.debug("termina verificarCantidadArchivosDirectorios");
    }
    
    private void borrarArchivos(File[] archivosGenerados){
        int contador=0;
        String nombreVigilancia="";
        String fileSubStr="";
        for(File archivo:archivosGenerados){
            log.debug(archivo.getAbsolutePath());
            fileSubStr = archivo.getName().substring(0, 3);
            if(!nombreVigilancia.equals(fileSubStr)){
                nombreVigilancia=fileSubStr;
                contador++;
            }
            log.debug(fileSubStr+","+contador);
            if(contador>=CONJUNTO_ARCHIVOS_MAXIMO && archivo.isFile()){
                log.debug("archivo.delete:"+archivo.getAbsolutePath());
                if(archivo.delete()){
                    log.error("borrar el archivo:"+archivo.getAbsolutePath());
                }
            }
        }
    }

    /**
     * genera archivo de fundamentos legales
     * @param idVigilancia
     * @param identidadFed
     */
    private void generaArchivoLegal(int idVigilancia, int identidadFed){
        List<String> listaFundamentos = omisosEFService.
                consultarFundamentoLegal(idVigilancia, identidadFed);
        logArchivoFundamentoLeg.setNombreArchivo(String.valueOf(identidadFed),
                idVigilancia +"_"+
                Utilerias.formatearFechaDDMMAAAA(new Date())+"_FundamentosLegales_EF");
        logArchivoFundamentoLeg.getListaArchivo().put(identidadFed,logArchivoFundamentoLeg.getNombreArchivo());
        if(!listaFundamentos.isEmpty()){
            logArchivoFundamentoLeg.escribirLog(ENCABEZADO_FUNDAMENTO_LEGAL);
        }
        for(String registro:listaFundamentos){
            logArchivoFundamentoLeg.escribirLog(registro);
        }
        Utilerias.convertirArchivoAZip(logArchivoFundamentoLeg.getNombreArchivoTmp(), logArchivoFundamentoLeg.getNombreArchivo());
    }

    /**
     *genera archivo pdf de factura
     * @param idVigilancia
     * @param identidadFed
     */
    private void generaArchivoFactura(int idVigilancia, int identidadFed){
        log.debug("generaArchivoFactura:"+idVigilancia+","+identidadFed);
        Map<String, Object> encabezadoFactura=omisosEFService.
                consultarEncabezadoFactura(idVigilancia,identidadFed);
        
        List<DetalleReporteFacturaBean> listaDetalleFactura=omisosEFService.
                consultarDetalleFactura(idVigilancia, identidadFed);
        log.debug("listaDetalleFactura.size:"+listaDetalleFactura.size());
        
        logArchivoFactura.setNombreArchivo(String.valueOf(identidadFed),
                idVigilancia +"_"+
                Utilerias.formatearFechaDDMMAAAA(new Date())+"_Factura_EF",".pdf");
        logArchivoFactura.getListaArchivo().put(identidadFed,logArchivoFactura.getNombreArchivo());
        
        JRBeanCollectionDataSource datosDetalle = new JRBeanCollectionDataSource(listaDetalleFactura);
        reporterService.makeReportFacturaEF(logArchivoFactura.getNombreArchivoTmp(),encabezadoFactura,datosDetalle);
        Utilerias.convertirArchivoAZip(logArchivoFactura.getNombreArchivoTmp(), logArchivoFactura.getNombreArchivo());
    }

    /**
     * 
     * @param vigilancia
     * @throws SGTServiceException
     */
    private void actualizaEstadoDocumentos(ConsultaVigilancia vigilancia) throws SGTServiceException{
        log.debug("actualizaEstadoDocumentos");
        TipoDocumentoEnum tipoDoc=null;
        for(TipoDocumentoEnum c : TipoDocumentoEnum.values()){
            if(c.getValor()==vigilancia.getIdTipoDocumento()){
                tipoDoc=c;
            }
        }
        boolean testCambio=cicloDocEtapaService.validaCambioEstado(
                EstadoDocumentoEnum.PENDIENTE_DE_PROCESAR,
                tipoDoc,
                EtapaVigilanciaEnum.ETAPA_1,
                EstadoDocumentoEnum.PENDIENTE_DE_NOTIFICAR);
       if(testCambio){
           documentoService.actualizarEstadoDocumento(vigilancia.getIdVigilancia(),
                   EstadoDocumentoEnum.PENDIENTE_DE_NOTIFICAR);
       }
    }

    /**
     * contruye un registro
     * @param registro
     * @param ubicacion
     * @param registroStr
     */
    private void construyeRegistro(RfcNombreDTO registro,
            UbicacionEFDTO ubicacion, StringBuffer registroStr){
        registroStr.setLength(0);
        registroStr.append(registro.getIdAdmonLocal()).append("|");
        registroStr.append(registro.getRfc()).append("|");
        registroStr.append(registro.getNumeroControl()).append("|");
        registroStr.append(registro.getNombre()).append("|");
        registroStr.append(ubicacion.getCalle()).append("|");
        registroStr.append(ubicacion.getNumeroExterior()).append("|");
        registroStr.append(ubicacion.getNumeroInterior()).append("|");
        registroStr.append(ubicacion.getDescripcionColonia()).append("|");
        registroStr.append(ubicacion.getDescripcionLocalidad()).append("|");
        registroStr.append(
                Utilerias.obtenerReferenciaUbicacion(
                    ubicacion.getEntreCalle1(), 
                    ubicacion.getEntreCalle2(), 
                    ubicacion.getReferenciaAdicionales())).append("|");
        //idobligacion,ejercicio,idperiodo,idregimen
        registroStr.append(registro.getObligaciones()).append("|");
        registroStr.append(MEDIO_ENVIO_EF).append("|");
        registroStr.append(registro.getFecha()).append("|");
        registroStr.append(ubicacion.getCodigoPostal()).append("|");
        registroStr.append(ubicacion.getClaveMunicipio()).append("|");
    }

    /**
     * 
     * @param vigilancia
     * @throws SQLException
     */
    @Propagable(catched = true)
    private void envioCorreoEntidadFederativa(ConsultaVigilancia vigilancia) throws SQLException{
        log.debug("------envioCorreoEntidadFederativa");
        StringBuilder mensaje=new StringBuilder();
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
        mensaje.append("<br>Entidad Federativa.");

        List<UsuarioEF> lista=usuarioEFDAO.todosLosUsuariosEFXVigilancia(vigilancia.getIdVigilancia());

        for(UsuarioEF entidadFed:lista){
            log.info(entidadFed.getCorreoElectronico());
            taskExecutorMail.execute(new ProcesoEnvioEMail(entidadFed.getCorreoElectronico(),
                    "MAT CO - Carga de Vigilancia",
                    mensaje.toString(),mailService, TipoEnvioEnum.MAIL));
        }
    }
    
    /**
     * envia correo al usuario que cargo la vigilancia
     * @param vigilancia
     * @throws SQLException 
     */
    private void enviaCorreoUsuarioXVigilancia(ConsultaVigilancia vigilancia) throws SQLException {
        log.debug("------enviaCorreoUsuarioXVigilancia");
        Map<String, String> map = new HashMap<String, String>();
        map.put("numeroVigilancia", vigilancia.getIdVigilancia()+"");
        map.put("descVigilancia", vigilancia.getDescripcion());
        map.put("fechaFigilancia", Utilerias.formatearFechaDDMMYYYY(new Date()) );
        log.info(vigilancia.getNumEmpleadoUsuario());
        taskExecutorMail.execute(new ProcesoEnvioEMail(vigilancia.getNumEmpleadoUsuario(),
                "MAT CO - Finaliza Carga de Vigilancia",
                Utilerias.obtenerCorreo("correoFinProcesoOmisos", map),mailService,TipoEnvioEnum.ID));
    }
    
    private class EscribirDatosUbicacion implements Runnable {

        private OmisosEFService omisosEFService;
        private RfcNombreDTO registro;
        private LogArchivoEFService logArchivoOmisos;

        public EscribirDatosUbicacion(OmisosEFService omisosEFService,
                RfcNombreDTO registro,LogArchivoEFService logArchivoOmisos) {
            this.omisosEFService=omisosEFService;
            this.registro=registro;
            this.logArchivoOmisos=logArchivoOmisos;
        }

        @Override
        public void run() {
            StringBuffer registroStr = new StringBuffer();
            List<UbicacionEFDTO> listaUbicacion = null;
            listaUbicacion = omisosEFService.consultarDatosUbicacion(registro.getBoid());
            if(listaUbicacion.isEmpty()){
                log.debug("no hubo dato de ubicacion para :"+registro.getBoid());
            }
            for (UbicacionEFDTO ubicacion : listaUbicacion) {
                construyeRegistro(registro, ubicacion, registroStr);
                logArchivoOmisos.escribirLog(Utilerias.quitarSaltosDeLinea(registroStr.toString()));
            }
        }
    }
    
}
