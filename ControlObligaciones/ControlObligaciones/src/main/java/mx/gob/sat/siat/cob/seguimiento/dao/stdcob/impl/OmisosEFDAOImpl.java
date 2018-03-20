package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.sql.Types;
import java.util.List;
import java.util.Map;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.OmisosEFDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.FundamentoLegalArchivoMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.ReporteFacturaMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.RfcNombreMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.UbicacionEFMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.OmisosEFSQL;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.OmisosEFSQL.OBTENER_ENCABEZADO_FACTURA;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleReporteFacturaBean;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.RfcNombreDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.UbicacionEFDTO;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author christian.ventura
 */
@Repository
public class OmisosEFDAOImpl implements OmisosEFDAO, OmisosEFSQL{

    private Logger logger = Logger.getLogger(getClass());

    @Autowired
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    @Autowired
    @Qualifier(ConstantsCatalogos.DATASOURCE_AMPLIADO)
    private JdbcTemplate templateRFCAmpl;

    @Propagable(catched = true)
    @Override
    public List<String> consultarFundamentoLegal(Integer idVigilancia, Integer identidadFederativa) {
        logger.debug("consultarDatosDetalleDoc");
        List<String> datos=this.template.query(
                OBTENER_FUNDAMENTOS_LEGALES,
                new Object[]{idVigilancia,identidadFederativa},
                new int[]{Types.INTEGER,Types.INTEGER}, new FundamentoLegalArchivoMapper());
        logger.debug(datos.size());
        return datos;
    }

    @Propagable(catched = true)
    @Override
    public List<Integer> consultarListaEntidadesFed(Integer idVigilancia) {
        logger.debug("consultarListaEntidadesFed");
        List<Integer> datos=this.template.queryForList(
                OBTENER_ENTIDADES_FEDERATIVAS,
                new Object[]{idVigilancia},
                new int[]{Types.INTEGER}, Integer.class);
        logger.debug(datos.size());
        return datos;
    }

    @Propagable(catched = true)
    @Override
    public List<RfcNombreDTO> consultarRfcNombre(Integer idVigilancia, int identidadFederativa) {
        logger.debug("consultarRfcNombre"+idVigilancia+","+identidadFederativa);
        return this.template.query(
                OBTENER_RFC_NOMBRE,
                new Object[]{idVigilancia,identidadFederativa},
                new int[]{Types.INTEGER,Types.INTEGER}, new RfcNombreMapper());
    }

    @Propagable(catched = true)
    @Override
    public List<UbicacionEFDTO> consultarDatosUbicacion(String boid) {
        return this.templateRFCAmpl.query(
                OBTENER_DATOS_UBICACION_PERSONA,
                new Object[]{boid},
                new int[]{Types.VARCHAR}, new UbicacionEFMapper());
    }

    @Propagable(catched = true)
    @Override
    public int insertaVigilanciaEF(int idVigilancia, int identidadFederativa,
            String archOmisos, String archFundLegal, String archFactura){
        logger.debug(""+idVigilancia);
        logger.debug(""+identidadFederativa);
        logger.debug(""+archOmisos);
        logger.debug(""+archFundLegal);
        logger.debug(""+archFactura);
        return template.update(
                    INSERT_VIGILANCIA_EF,
                    new Object[]{archOmisos,archFactura,archFundLegal,
                    idVigilancia,identidadFederativa,archOmisos,archFactura,archFundLegal},
                    new int[]{
                        Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.INTEGER,
                        Types.INTEGER,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR
                });
    }

    @Propagable(catched = true)
    @Override
    public Map<String, Object> consultarEncabezadoFactura(Integer idVigilancia, Integer identidadFederativa){
        logger.debug("consultarEncabezadoFactura");
        return this.template.queryForMap(
                OBTENER_ENCABEZADO_FACTURA,
                new Object[]{idVigilancia,identidadFederativa},
                new int[]{Types.NUMERIC,Types.NUMERIC});
    }
    
    @Propagable(catched = true)
    @Override
    public List<DetalleReporteFacturaBean> consultarDetalleFactura(Integer idVigilancia, Integer identidadFederativa){
        logger.debug("consultarDetalleFactura");
        return this.template.query(
                OBTENER_DETALLE_FACTURA,
                new Object[]{idVigilancia,identidadFederativa},
                new int[]{Types.NUMERIC,Types.NUMERIC}, new ReporteFacturaMapper());
    }

}
