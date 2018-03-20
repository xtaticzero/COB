package mx.gob.sat.siat.cob.background.service.carga.impl;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mx.gob.sat.siat.cob.background.service.carga.CargaInformacionHelper;
import mx.gob.sat.siat.cob.background.service.carga.CargaInformacionService;
import mx.gob.sat.siat.cob.background.service.carga.LogErrorCargaService;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.BaseDocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ConsultaVigilancia;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleConsultaVigilancia;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.service.carga.Agrupamiento;
import mx.gob.sat.siat.cob.seguimiento.exception.AgrupamientoException;
import mx.gob.sat.siat.cob.seguimiento.service.carga.impl.Acumulado;
import mx.gob.sat.siat.cob.seguimiento.service.carga.impl.CargaInformacionDocumentosBuilder;
import mx.gob.sat.siat.cob.seguimiento.service.carga.impl.Normal;
import mx.gob.sat.siat.cob.seguimiento.service.otros.OmisosEFService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author christian.ventura
 */
@Service
public class CargaInformacionServiceImpl implements CargaInformacionService {

    private List<ConsultaVigilancia> vigilancias;
    private ConsultaVigilancia currentVigilancia;
    private List<Documento> currentlistaDocs;
    private Agrupamiento agrupador;
    private static final int NUMERO_HILOS = 20;
    //bloque de datos a procesar y generar para los docuentos
    private static final int INTERVALO_BLOQUE = 100000;
    private int inicioBloque=0;
    private int finBloque=0;
    private Logger log =Logger.getLogger(CargaInformacionServiceImpl.class);

    @Autowired
    private CargaInformacionHelper cargaInformacionHelper;

    @Autowired
    private LogErrorCargaService logErrorCarga;

    @Autowired
    private BaseDocumentoDAO baseDocumentoDAO;
    
    @Autowired
    private OmisosEFService omisosEFService;

    /**
     * principal
     */
    @Override
    public void cargaInformacion() {
        try{
            //tenia un validaejecucion pero ya se quito: cargaInformacionHelper.validaEjecucion()
            log.debug("LA EJECUCION DEL PROCESO DE CARGA ESTA ACTIVADA, TERMINANDO CON EL PROCESO...");
            this.vigilancias = cargaInformacionHelper.cargaListadoVigilancias();
            if(this.vigilancias == null){
                log.debug("NO HAY VIGILANCIAS CON ESTADO PROCEDENTE...TERMINA EJECUCION DE PROCESO");
                return;
            }
            log.debug(" EXISTEN "+ this.vigilancias.size() +
                    " DISPONIBLES PARA PROCESAR, CONTINUANDO CON EL PROCESO...");

            for(ConsultaVigilancia v: this.vigilancias){
                if(existeVigilancia(v)){
                    continue;
                }
                agruparCrearDocumento(v);
            }
            borraArchivos();
        }
        catch(SeguimientoDAOException e){
            log.error("ERROR EN LA EJECUCION DEL PROCESO DE CARGA DE INFORMACION DEBIDO A :", e);
        }
        catch (AgrupamientoException e) {
            log.error("ERROR EN LA EJECUCION DEL PROCESO DE CARGA DE INFORMACION DEBIDO A :", e);
        }
        catch (Exception e) {
            log.error("ERROR EN LA EJECUCION DEL PROCESO DE CARGA DE INFORMACION DEBIDO A :", e);
        }
        finally{
            log.debug("FIN DE LA EJECUCION DEL PROCESO...");
            baseDocumentoDAO.cleanCallerDocs();
            logErrorCarga.guardarLogVigilancia();
        }
    }

    /**
     * 
     * @param v
     * @throws AgrupamientoException
     * @throws SeguimientoDAOException
     * @throws SQLException
     */
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED,
                   rollbackFor = {Exception.class})
    public void agruparCrearDocumento(ConsultaVigilancia v) throws AgrupamientoException,
            SeguimientoDAOException, SQLException  {
        this.currentVigilancia = v;
        int contadorDocs=0;
        int contadorDocsError=0;
        log.debug("\tPROCESANDO LA VIGILANCIA CON ID:" + v.getIdVigilancia());
        //lee informacion de archivo y lo deja en memoria
        agrupar();
        if(Agrupamiento.getMapaAgr().isEmpty()){
            log.debug("\tEL AGRUPADOR DEL PROCESO NO GENERO NINGUN AGRUPAMIENTO..."
                    + "TERMINA EJECUCION DE PROCESO DE ESTA VIGILANCIA");
        }else{
            inicioBloque=1;
            finBloque=INTERVALO_BLOQUE;
            crearDocumentos();
            baseDocumentoDAO.initCallerDocs();
            while(!currentlistaDocs.isEmpty()){
                List<Documento> documentosEnviar = new ArrayList<Documento>();
                for(Documento d: currentlistaDocs){
                   documentosEnviar.add(d);
                   contadorDocs++;
                   if(documentosEnviar.size()==NUMERO_HILOS || contadorDocs == currentlistaDocs.size()){
                       log.debug("\t\t----contadorDocs:"+contadorDocs);
                       contadorDocsError += cargaInformacionHelper.guardarDocumentosHilos(documentosEnviar);
                       documentosEnviar = new ArrayList<Documento>();
                   }
                }
                inicioBloque=finBloque+1;
                finBloque=finBloque+INTERVALO_BLOQUE;
                //crea estructura de documento, con detalles (objeto pojo) y lo deja en memoria
                crearDocumentos();
            }
            
            if(contadorDocs!=0){
                //se quito una linea porque el estatus se cambia ahora en ArchivoOmisosEFServiceImpl.java
                cargaInformacionHelper.guardaVigAdminLocal(this.currentVigilancia.getIdVigilancia());
                cargaInformacionHelper.envioCorreoPorNivelEmision(this.currentVigilancia);
            }else{
                //cuando no se haya guardado nada en las tablas de documentos
                omisosEFService.actualizarVigilancia(this.currentVigilancia.getIdVigilancia());
                cargaInformacionHelper.enviaCorreoErrorXVigilancia(currentVigilancia);
            }
        }
    }
    
    /**
     *
     * @param v
     * @return
     * @throws SeguimientoDAOException
     */
    public boolean existeVigilancia(ConsultaVigilancia v) throws SeguimientoDAOException{
        boolean noExisteArchivo=false;
        boolean retorno=false;
        int existeDocs=cargaInformacionHelper.consultaVigilanciasExistente(v.getIdVigilancia());
        List<Integer> listaIdentidadFed=omisosEFService.consultarListaEntidadesFed(v.getIdVigilancia());
        
        for(DetalleConsultaVigilancia det:v.getDetalle()){
            File existeArchivo=new File(det.getRutaLocalArchivo());
            if(!existeArchivo.exists()){
                log.error("el archivo "+det.getRutaLocalArchivo()+" no existe");
                log.error("se desactivo la vigilancia "+v.getIdVigilancia());
                noExisteArchivo=true;
            }
        }
        //estan pendientes por procesar entidades federativas
        if(existeDocs>=1 && !listaIdentidadFed.isEmpty() ){
            retorno=true;
        }else
        if(noExisteArchivo || existeDocs>=1){
            //ya se procesaron los docs y se cambia su estatus
            omisosEFService.actualizarVigilancia(v.getIdVigilancia());
            retorno=true;
        }
        return retorno;
    }

    /**
     * borra archivos una ves acabo todo el proceso de carga
     */
    @Override
    public void borraArchivos(){
        if(this.vigilancias!=null){
            for(ConsultaVigilancia v: this.vigilancias){
                for(DetalleConsultaVigilancia det:v.getDetalle()){
                    File f=new File(det.getRutaLocalArchivo());
                    boolean isBorrado=f.delete();
                    log.debug("f.delete();"+isBorrado);
                }
            }
        }
    }

    /**
     * agrupamiento normal,acumulado
     * @throws AgrupamientoException
     */
    private void agrupar() throws AgrupamientoException {
        if(this.currentVigilancia.getIdTipoDocumento() !=
                TipoDocumentoEnum.REQUERIMIENTO_ACUMULADO.getValor()){
            this.agrupador = new Normal();
            log.debug("\t\tPROCESANDO LA VIGILANCIA CON AGRUPAMIENTO NORMAL...");
        }
        else{
            this.agrupador = new Acumulado();
            log.debug("\t\tPROCESANDO LA VIGILANCIA CON AGRUPAMIENTO ACUMULADO...");
        }

        Agrupamiento.nuevoAgrupamiento();

        for(DetalleConsultaVigilancia det:this.currentVigilancia.getDetalle()){
            log.debug("\t\t\tPROCESANDO AGRUPAMIENTO DEL DETALLE DE LA VIGILANCIA CON RUTA: "+
                    det.getRutaLocalArchivo());
            this.agrupador.agrupa(det.getRutaLocalArchivo(),det.getNombreArchivo());
        }
    }

    /**
     * crea objeto de documentos
     */
    private void crearDocumentos() {
        log.debug("agrupamiento:"+Agrupamiento.getMapaAgr().size()+
                ",inicioBloque:"+inicioBloque+",finBloque:"+finBloque);
        this.currentlistaDocs = new ArrayList<Documento>();
        CargaInformacionDocumentosBuilder builder = new CargaInformacionDocumentosBuilder();
        currentlistaDocs = builder.creaDocumentos(Agrupamiento.getMapaAgr(),
                Integer.valueOf(this.currentVigilancia.getIdVigilancia()),
                inicioBloque,finBloque,this.currentVigilancia.getIdTipoDocumento());
        log.debug("\t\tCREADOS: "+this.currentlistaDocs.size() + " DOCUMENTOS...");
    }
    
}
