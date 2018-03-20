package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.BaseDocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.NumeroResolucionMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.BaseDocumentoSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.idc.Ubicacion;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.NumeroControlDTO;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlTypeValue;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.nativejdbc.C3P0NativeJdbcExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author christian.ventura
 */
@Repository
public class BaseDocumentoDAOImpl implements BaseDocumentoDAO {

    private final Logger log = Logger.getLogger(BaseDocumentoDAOImpl.class.getName());

    private static final String PARAMETRO_RETORNO = "return";
    private static final String PARAMETRO_TERMINA = "termina:";
    private static final String PARAMETRO_V_RETORNO = "v_retorno";
    private static final String PARAMETRO_BOIDS = "boids";
    private static final String PARAMETRO_ETAPA_VIGILANCIA = "etapavigilancia";
    private static final String TIPO_DOCUMENTO = "TIPODOCUMENTO";
    private static final String TIPO_LISTA_PARAM = "TIPO_LISTA_PARAM";
    private static final String TIPO_LISTA_DATOS = "TIPO_LISTA_DATOS";
    private static final int TIEMPO_ESPERA_GENERA_NUMCTROL = 20000;

    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;
    
    @Autowired(required = false)
    private C3P0NativeJdbcExtractor cp30NativeExtractor;

    private SimpleJdbcCall callerDocs;
    
    /**
     * Obtiene un nuevo identificador(numerocontrol) para el insert de documento
     * y su detalle
     *
     * @param boid
     * @param tipoDocumento
     * @param etapaVigilancia
     * @return numero de control
     */
    @Override
    public NumeroControlDTO getNumeroControl(String boid, Integer tipoDocumento, Integer etapaVigilancia) {
        NumeroControlDTO numeroControlDto = new NumeroControlDTO();
        log.debug("inicia getNumeroControl");
        SimpleJdbcCall caller = new SimpleJdbcCall(this.template)
                .withFunctionName(BaseDocumentoSQL.GENERAR_NUMCONTROL)
                .withCatalogName("PKG_COB")
                .withoutProcedureColumnMetaDataAccess()
                .withReturnValue()
                .declareParameters(new SqlOutParameter(PARAMETRO_RETORNO, Types.VARCHAR))
                .declareParameters(new SqlParameter("P_BOID", Types.VARCHAR))
                .declareParameters(new SqlParameter("P_TIPODOCUMENTO", Types.INTEGER))
                .declareParameters(new SqlParameter("P_ETAPAVIGILANCIA", Types.INTEGER));
        MapSqlParameterSource inParams = new MapSqlParameterSource();
        inParams.addValue("P_BOID", boid);
        inParams.addValue("P_TIPODOCUMENTO", tipoDocumento);
        inParams.addValue("P_ETAPAVIGILANCIA", etapaVigilancia);
        Map out = caller.execute(inParams);
        log.debug(PARAMETRO_TERMINA + out.get(PARAMETRO_RETORNO));
        //111301141796271C12005-12-06500-005-09
        String[] strArray = out.get(PARAMETRO_RETORNO).toString().split("-");
        numeroControlDto.setNumeroControl(strArray[0]);
        Ubicacion ubicacion = new Ubicacion();
        if (strArray.length > 1) {
            ubicacion.setClvALR(strArray[1]);
            ubicacion.setClvCRH(strArray[3]);
            ubicacion.setCodPostal(strArray[2]);
            ubicacion.setClvEntidadFederativa(strArray[4]);
            //tipo de persona
            ubicacion.setReferenciaAdicional(strArray[5]);
        }
        numeroControlDto.setUbicacion(ubicacion);
        return numeroControlDto;
    }

    /**
     * obtiene el numero de control generado consultando por boid y lo
     * transforma en objeto numerocontroldto junto con informacion de ubicacion
     *
     * @param boid
     * @return
     */
    @Override
    @Propagable(catched = true)
    public NumeroControlDTO getTransformacionNumeroControl(String boid) {
        log.debug("getTransformacionNumeroControl" + boid);
        NumeroControlDTO numeroControlDto = new NumeroControlDTO();
        String numControl = template.queryForObject(
                BaseDocumentoSQL.OBTENER_NUMERO_CONTROL_POR_BOID, new Object[]{
                    boid
                }, new int[]{
                    Types.VARCHAR
                }, String.class);
        String[] strArray = numControl.split("-");
        numeroControlDto.setNumeroControl(strArray[0]);
        Ubicacion ubicacion = new Ubicacion();
        if (strArray.length > 1) {
            ubicacion.setClvALR(strArray[1]);
            ubicacion.setCodPostal(strArray[2]);
            ubicacion.setClvCRH(strArray[3]);
            ubicacion.setClvEntidadFederativa(strArray[4]);
            ubicacion.setReferenciaAdicional(strArray[5]);//tipo de persona
        }
        numeroControlDto.setUbicacion(ubicacion);
        return numeroControlDto;
    }

    /**
     * Obtiene un nuevo identificador(numeroresolucion) para el insert de
     * documento y su detalle
     *
     * @param boid
     * @param resolMotivo
     * @return numero de resolucion
     */
    @Override
    public NumeroControlDTO getNumeroResolucion(String boid, String resolMotivo) {
        NumeroControlDTO numeroControlDto = new NumeroControlDTO();
        log.debug("inicia getNumeroResolucion");
        SimpleJdbcCall caller = new SimpleJdbcCall(this.template)
                .withFunctionName(BaseDocumentoSQL.GENERAR_NUMRESOLUCION)
                .withCatalogName("PKG_COB")
                .withoutProcedureColumnMetaDataAccess()
                .withReturnValue()
                .declareParameters(new SqlOutParameter(PARAMETRO_RETORNO, Types.VARCHAR))
                .declareParameters(new SqlParameter("p_boid", Types.VARCHAR))
                .declareParameters(new SqlParameter("p_resolmotivo", Types.VARCHAR));
        MapSqlParameterSource inParams = new MapSqlParameterSource();
        inParams.addValue("p_boid", boid);
        inParams.addValue("p_resolmotivo", resolMotivo);
        Map out = caller.execute(inParams);
        log.debug(PARAMETRO_TERMINA + out.get(PARAMETRO_RETORNO));
        //111301141796271C12005-RFSD43534354
        String[] retorno=out.get(PARAMETRO_RETORNO).toString().split("-");
        String[] datos=retorno[0].split("C");
        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setClvALR(datos[1].substring(0, 2));
        ubicacion.setClvCRH(datos[1].substring(2));
        numeroControlDto.setUbicacion(ubicacion);
        numeroControlDto.setNumeroControl(retorno[0]);
        if(datos[1].equals("00000")){
            log.error("para el boid "+boid+" y numero de control: "+
                    out.get(PARAMETRO_RETORNO).toString()+" no se pudo obtener sus datos de hubicacion ni rfc");
        }else if(retorno.length>1){
            numeroControlDto.setRfc(retorno[1]);
        }
        return numeroControlDto;
    }

    /**
     * genera conjunto de numero de control
     *
     * @param tipoDocumento
     * @param etapaVigilancia
     * @return
     */
    @Transactional (propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE )
    @Override
    public int generaNumerosControl(List<String> boids,
            Integer tipoDocumento, Integer etapaVigilancia, int contador) {
        log.debug("----------------------------------------------------------");
        log.debug("inicia generaNumerosControl-" + boids.size());
        SimpleJdbcCall caller = new SimpleJdbcCall(this.template)
                .withProcedureName(BaseDocumentoSQL.GENERAR_CONTROLES)
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(new SqlOutParameter(PARAMETRO_V_RETORNO, Types.VARCHAR))
                .declareParameters(new SqlParameter(PARAMETRO_BOIDS, Types.ARRAY, TIPO_LISTA_DATOS))
                .declareParameters(new SqlParameter(TIPO_DOCUMENTO, Types.INTEGER))
                .declareParameters(new SqlParameter(PARAMETRO_ETAPA_VIGILANCIA, Types.INTEGER));

        MapSqlParameterSource inParams = new MapSqlParameterSource();
        inParams.addValue(TIPO_DOCUMENTO, tipoDocumento);
        inParams.addValue(PARAMETRO_ETAPA_VIGILANCIA, etapaVigilancia);
        inParams.addValue(PARAMETRO_BOIDS, new Tipo(TIPO_LISTA_DATOS, boids,cp30NativeExtractor));
        Map out = caller.execute(inParams);
        log.debug(PARAMETRO_TERMINA + out.get(PARAMETRO_V_RETORNO));
        
        Integer numControl=0;
        while(numControl.intValue() < contador){
            try {
                Thread.sleep(TIEMPO_ESPERA_GENERA_NUMCTROL);
                numControl = template.queryForObject(
                        BaseDocumentoSQL.OBTENER_TOTAL_NUM_CONTROL, Integer.class);
                log.debug("contador:"+numControl);
            } catch (InterruptedException ex) {
                log.error(ex);
            }
        }
        log.debug("termina generaNumerosControl");
        return 1;
    }

    /**
     * genera conjunto de numero de resoluciones
     *
     * @param documentos
     * @param resolMotivo
     * @param flgCommit
     * @return numero de resolucion
     */
//    @Transactional (propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED )
    @Override
    public int generaNumeroResoluciones(List<Documento> documentos, String resolMotivo,
            Integer flgCommit) {
        log.debug("----------------------------------------------------------");
        List<String> boids = new ArrayList<String>();
        List<String> numerosControl = new ArrayList<String>();
        for (Documento doc : documentos) {
            boids.add(doc.getBoid());
            numerosControl.add(doc.getNumeroControl());
        }
        log.debug("inicia generaNumeroResoluciones-");
        SimpleJdbcCall caller = new SimpleJdbcCall(this.template)
                .withProcedureName(BaseDocumentoSQL.GENERAR_RESOLUCIONES)
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(new SqlOutParameter(PARAMETRO_V_RETORNO, Types.VARCHAR))
                .declareParameters(new SqlParameter("resolmotivo", Types.VARCHAR))
                .declareParameters(new SqlParameter("flgCommit", Types.INTEGER))
                .declareParameters(new SqlParameter(PARAMETRO_BOIDS, Types.ARRAY, TIPO_LISTA_DATOS))
                .declareParameters(new SqlParameter("numerosControl", Types.ARRAY, TIPO_LISTA_DATOS));

        MapSqlParameterSource inParams = new MapSqlParameterSource();
        inParams.addValue("resolmotivo", resolMotivo);
        inParams.addValue("flgCommit", flgCommit);
        inParams.addValue(PARAMETRO_BOIDS, new Tipo(TIPO_LISTA_DATOS, boids,cp30NativeExtractor));
        inParams.addValue("numerosControl", new Tipo(TIPO_LISTA_DATOS, numerosControl,cp30NativeExtractor));
        Map out = caller.execute(inParams);
        log.debug(PARAMETRO_TERMINA + out.get(PARAMETRO_V_RETORNO));
        List<NumeroControlDTO> lista=consultaResoluciones();
        validaExistenciaUbicacion(lista, documentos);
        return 1;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    @Propagable(catched = true)
    public List<NumeroControlDTO> consultaResoluciones() {
        log.debug("-->consultaResoluciones");
        return (List<NumeroControlDTO>) this.template.query(
                BaseDocumentoSQL.OBTENER_DETALLE_NUM_RESOLUCION,
                new NumeroResolucionMapper());
    }
    
    /**
     * 
     * @param lista
     * @param documentos 
     */
    private void validaExistenciaUbicacion(List<NumeroControlDTO> lista,List<Documento> documentos){
        for(NumeroControlDTO resol:lista){
            for(Documento dato:documentos){
                if(dato.getNumeroControl().equals(resol.getNumeroControl()) &&
                        resol.getNumeroResolucion().endsWith("00000") ){
                    log.error("para el boid: "+dato.getBoid()+" y numero de control: "+
                            dato.getNumeroControl()+" no se pudo obtener sus datos de hubicacion");
                    break;
                }
            }
        }
    }
    
    /**
     * guarda el documento y sus detalles
     * @param documentos
     * @param tipoDocumento
     * @param etapaVigilancia
     * @return
     * @throws SQLException 
     */
    @Override
    public String guardaDocumentosDetalles(Documento documentos,Integer tipoDocumento, Integer etapaVigilancia)
    throws SQLException{
        log.debug("---------------------------------------------"+documentos.getBoid());
        List<String> listaDocumentos = new ArrayList<String>();
        List<String> listaDetalles = new ArrayList<String>();
        listaDocumentos.add(documentos.toString());
        for (DetalleDocumento det : documentos.getDetalles()) {
            listaDetalles.add(det.toString());
        }
        log.debug("inicia guardaDocumentosDetalles-");

        MapSqlParameterSource inParams = new MapSqlParameterSource();
        inParams.addValue("P_DOC", new Tipo(TIPO_LISTA_PARAM, listaDocumentos,cp30NativeExtractor));
        inParams.addValue("P_DOCDET", new Tipo(TIPO_LISTA_PARAM, listaDetalles,cp30NativeExtractor));
        inParams.addValue(TIPO_DOCUMENTO, tipoDocumento);
        inParams.addValue(PARAMETRO_ETAPA_VIGILANCIA, etapaVigilancia);
        Map out = getCaller().execute(inParams);
        log.debug(documentos.getBoid()+" "+PARAMETRO_TERMINA + out.get(PARAMETRO_V_RETORNO).toString());
        return out.get(PARAMETRO_V_RETORNO).toString();
    }

    /**
     * borra los datos de la tabla temporal
     *
     * @return int
     */
    @Override
    public Integer borrarTablaNumeroResolucion() {
        log.debug("inicia borrarTablaNumeroResolucion");
        SimpleJdbcCall caller = new SimpleJdbcCall(this.template)
                .withProcedureName(BaseDocumentoSQL.TRUNCATE_TABLA_NUMRESOLUCION)
                .withoutProcedureColumnMetaDataAccess();
        caller.execute();
        log.debug("termina borrarTablaNumeroResolucion");
        return 1;
    }

    /**
     *
     * @return
     */
    public SimpleJdbcCall getCaller() {
        return callerDocs;
    }

    @Override
    public void initCallerDocs() {
        this.callerDocs = new SimpleJdbcCall(this.template)
            .withProcedureName(BaseDocumentoSQL.GENERAR_DOCUMENTOS_DETALLES)
            .withoutProcedureColumnMetaDataAccess()
            .declareParameters(new SqlOutParameter(PARAMETRO_V_RETORNO, Types.VARCHAR))
            .declareParameters(new SqlParameter("P_DOC", Types.ARRAY, TIPO_LISTA_PARAM))
            .declareParameters(new SqlParameter("P_DOCDET", Types.ARRAY, TIPO_LISTA_PARAM))
            .declareParameters(new SqlParameter(TIPO_DOCUMENTO, Types.INTEGER))
            .declareParameters(new SqlParameter(PARAMETRO_ETAPA_VIGILANCIA, Types.INTEGER));
    }
    
    /**
     *
     */
    @Override
    public void cleanCallerDocs() {
        this.callerDocs = null;
    }
    
    static class Tipo implements SqlTypeValue {

        private final String listaTipos;
        private final List<String> valores;
        private C3P0NativeJdbcExtractor cp30NativeExtractor;

        public Tipo(String listaTipos, List<String> valores,C3P0NativeJdbcExtractor cp30NativeExtractor) {
            this.listaTipos = listaTipos;
            this.valores = valores;
            this.cp30NativeExtractor=cp30NativeExtractor;
        }

        @Override
        public void setTypeValue(PreparedStatement ps, int i, int i1, String string)
                throws SQLException {
            ArrayDescriptor des = ArrayDescriptor.createDescriptor(listaTipos, 
                    cp30NativeExtractor.getNativeConnection(ps.getConnection()) );
            ARRAY a = new ARRAY(des, cp30NativeExtractor.getNativeConnection(ps.getConnection())
                    , valores.toArray());
            ps.setObject(i, (Object) a);
        }
    }
}
