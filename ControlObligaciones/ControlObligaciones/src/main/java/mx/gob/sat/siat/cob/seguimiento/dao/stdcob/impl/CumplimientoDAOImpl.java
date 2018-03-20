package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.math.BigInteger;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.CumplimientoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.DocumentoAprobarIcepMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.GrupoAfectacionCumpMapper;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.CumplimientoSQL.AFECTAR_DETALLES_CON_CUMPLIMIENTO;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.CumplimientoSQL.DETALLES_INCUMPLIDOS;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.CumplimientoSQL.INSERT;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.CumplimientoSQL.OBTENER_MAXIMO_IDENTIFICADOR_CUMPLIMIENTO;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.CumplimientoSQL.TRUNCATE_TABLA_CUMPLIMIENTOS;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.CumplimientoSQL.ELIMINAR_CUMPLIMIENTOS_FECHA_MTO;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.CumplimientoSQL.CONTEO_AGRUPADO_DETALLES_INCUMPLIDOS;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.CumplimientoSQL.ACTUALIZAR_DETALLES_CUMPLIDOS;
import mx.gob.sat.siat.cob.seguimiento.dto.db2.Paginador;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Cumplimiento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DocumentoAprobar;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.GrupoAfectacionCumpDTO;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CumplimientoDAOImpl implements CumplimientoDAO {
    
    private final Logger log = Logger.getLogger(CumplimientoDAOImpl.class);

    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;
    @Autowired(required = false)
    @Qualifier("namedTemplateSTDCOB")
    private NamedParameterJdbcTemplate namedJDBCTemplate;

    @Override
    @Propagable(catched = true)
    public Integer insert(Cumplimiento cumplimiento) {
        return template.update(INSERT, new Object[]{
            cumplimiento.getbOID(),
            cumplimiento.getClaveICEP(),
            cumplimiento.getIdentificadorCumplimiento(),
            cumplimiento.getImportePagar(),
            cumplimiento.getFechaPresentacion()},
                new int[]{Types.DECIMAL,
            Types.DECIMAL,
            Types.DECIMAL,
            Types.DECIMAL,
            Types.DATE});
    }

    /**
     * Actualiza los detalles de documento dependiendo de los cumplimientos
     * traidos de Estructura de Cumplimiento
     *
     * @param maximoIdentificador
     * @param estados
     * @return
     */
    @Override
    @Propagable
    public Integer afectarDetallesConCumplimiento(Integer[] estados) throws SeguimientoDAOException{
        List<Integer> estadosCollection = Arrays.asList(estados);
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("estados", estadosCollection);
        return this.namedJDBCTemplate.update(AFECTAR_DETALLES_CON_CUMPLIMIENTO, parameters);
    }

    @Override
    @Propagable(catched = true)
    public BigInteger obtenerMaximoIdentificadorCumplimiento() {
        return this.template.queryForObject(
                OBTENER_MAXIMO_IDENTIFICADOR_CUMPLIMIENTO,
                BigInteger.class);
    }

    @Override
    @Propagable(catched = true)
    public Integer eliminarCumplimientosProcesados() {
        SimpleJdbcCall caller = new SimpleJdbcCall(this.template)
                .withProcedureName(TRUNCATE_TABLA_CUMPLIMIENTOS)
                .withoutProcedureColumnMetaDataAccess();
        caller.execute();
        return 1;
    }

    @Override
    @Propagable(catched = true)
    public Integer eliminarCumplimientosPorFechaMto(Date fechaMantenimiento) {
        return template.update(ELIMINAR_CUMPLIMIENTOS_FECHA_MTO, new Object[]{fechaMantenimiento},
                new int[]{Types.DATE});
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentoAprobar> listarDocumentosIcep(GrupoAfectacionCumpDTO grupoAfectacionCumpDTO, Paginador paginador) {
        List<List<DocumentoAprobar>> lst;
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("vigilancia", grupoAfectacionCumpDTO.getVigilancia());
        mapSqlParameterSource.addValue("admonlocal", grupoAfectacionCumpDTO.getIdAdmonLocal());
        mapSqlParameterSource.addValue("inicio", paginador.getRowInicial());
        mapSqlParameterSource.addValue("fin", paginador.getRowFinal());

        lst = (List<List<DocumentoAprobar>>) this.namedJDBCTemplate.query(
                DETALLES_INCUMPLIDOS,
                mapSqlParameterSource,
                new DocumentoAprobarIcepMapper());
        if (lst != null && lst.size() > 0) {
            return (List<DocumentoAprobar>) lst.get(0);
        } else {
            return null;
        }
    }


    @Override
    @Propagable(catched = true)
    public Integer actualizarDetalleConCumplimiento(Documento documento, Integer idSituacionIcep) {
        int i = 0;
        for (DetalleDocumento det : documento.getDetalles()) {
                                log.info("Se actualizara detalle de documento con los siguientes datos \n" +
                                     "Fecha de cumplimiento "  + det.getFechaCumplimiento() + "\n" +
                                     "Situacion icep "  +   idSituacionIcep + "\n" +
                                     "Importe Cargo "  +   det.getImporteCargo() + "\n" +
                                     "Tipo de declaracion "  +   det.getIdTipoDeclaracion() + "\n" +
                                     "Id cumplimiento "  +   det.getIdCumplimiento() + "\n" +
                                     "Estado icep "  +   det.getEstadoIcepEC() + "\n" +
                                     "Numero de control "  +   documento.getNumeroControl() + "\n" +
                                     "Clave icep "  +   det.getClaveIcep() + "\n" );
                 
                 
             i += template.update(ACTUALIZAR_DETALLES_CUMPLIDOS, new Object[]{
            det.getFechaCumplimiento(),
            idSituacionIcep,
            det.getImporteCargo(),
            det.getIdTipoDeclaracion(),
            (det.getIdCumplimiento()!=null ? det.getIdCumplimiento().toString():null),
            det.getEstadoIcepEC(),
            documento.getNumeroControl(),
            det.getClaveIcep()},
                new int[]{Types.DATE,
            Types.DECIMAL,
            Types.DECIMAL,
            Types.DECIMAL,
            Types.VARCHAR,
            Types.DECIMAL,
            Types.VARCHAR,
            Types.DECIMAL});
        }
        return i;

    }

    @Override
    @Propagable(catched = true)
    @Transactional(readOnly = true)
    public List<GrupoAfectacionCumpDTO> obtenerDetallesOmisos() {
        return this.namedJDBCTemplate.query(
                CONTEO_AGRUPADO_DETALLES_INCUMPLIDOS,
                new MapSqlParameterSource(),
                new GrupoAfectacionCumpMapper());
    }

}
