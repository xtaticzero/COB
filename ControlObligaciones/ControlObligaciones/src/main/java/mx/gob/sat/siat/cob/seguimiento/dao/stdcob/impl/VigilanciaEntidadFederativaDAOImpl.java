package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.sql.Types;


import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.VigilanciaEntidadFederativaDAO;

import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.VigilanciaEFSingleMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.VigilanciaEntidadFederativaMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.VigilanciaEntidadFederativaSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.db2.Paginador;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaEntidadFederativa;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.SituacionArchivoEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.SituacionVigilanciaEnum;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class VigilanciaEntidadFederativaDAOImpl implements VigilanciaEntidadFederativaDAO, VigilanciaEntidadFederativaSQL {

    private final Logger logger = Logger.getLogger(getClass());
    @Autowired
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    @Override
    @Propagable(catched = true)
    public List<VigilanciaEntidadFederativa> obtenerVigilancias(Long claveEf) {

        StringBuilder sql = new StringBuilder();
        sql.append(VigilanciaEntidadFederativaSQL.CONSULTA_VIGILANCIAS_EF_X_ENTIDAD_FEDERATIVA);
        logger.debug(sql);
        return template.query(sql.toString(),
                new Object[]{claveEf},
                new int[]{Types.INTEGER},
                new VigilanciaEntidadFederativaMapper());


    }

    @Override
    @Propagable(catched = true)
    public List<VigilanciaEntidadFederativa> obtenerVigilancias() {

        StringBuilder sql = new StringBuilder();
        sql.append(VigilanciaEntidadFederativaSQL.CONSULTA_VIGILANCIAS_EF);
        logger.debug(sql);
        return template.query(sql.toString(), new VigilanciaEntidadFederativaMapper());
    }

    @Propagable
    @Override
    public Long obtenerCantidadVigilancias() {
        try {
            return template.queryForObject(CONSULTA_CANTIDAD_VIGILANCIAS_EF, Long.class);
        } catch (EmptyResultDataAccessException e) {
            logger.debug(e);
            return null;
        }
    }

    @Override
    @Propagable(catched = true)
    public Integer guardarRechazo(VigilanciaEntidadFederativa vef) {

        return template.update(VigilanciaEntidadFederativaSQL.RECHAZAR_VIGILANCIA_EF,
                new Object[]{vef.getIdMotivoRechazo(),
            SituacionVigilanciaEnum.RECHAZADA.getIdSituacion(),
            vef.getIdVigilancia(),
            vef.getIdEntidadFederativa()},
                new int[]{Types.INTEGER,
            Types.INTEGER,
            Types.INTEGER,
            Types.INTEGER});
    }

    @Override
    @Propagable(catched = true)
    public Integer aceptarVigilancia(VigilanciaEntidadFederativa vef) {

        return template.update(VigilanciaEntidadFederativaSQL.ACEPTAR_VIGILANCIA_EF,
                new Object[]{SituacionVigilanciaEnum.ACEPTADA.getIdSituacion(),
            SituacionArchivoEnum.DESCARGADO.getIdSituacionArchivo(),
            new Date(),
            vef.getIdVigilancia(),
            vef.getIdEntidadFederativa()},
                new int[]{Types.INTEGER,
            Types.INTEGER,
            Types.DATE,
            Types.INTEGER,
            Types.INTEGER});

    }

    @Override
    @Propagable(catched = true)
    public List<VigilanciaEntidadFederativa> obtenerVigilanciaPorIdVigClaveEF(Long idVigilancia, Long claveEf) {

        return template.query(VigilanciaEntidadFederativaSQL.CONSULTA_VIGILANCIAS_EF_POR_IDVIG_CLAVEEF,
                new Object[]{idVigilancia, claveEf},
                new int[]{Types.INTEGER,
            Types.INTEGER},
                new VigilanciaEFSingleMapper());

    }

    @Override
    @Propagable(catched = true)
    public Date consultarVigilanciaPorUsuarioEf(String usuario, Long idVigilancia) {

        StringBuilder sql = new StringBuilder();
        sql.append(VigilanciaEntidadFederativaSQL.CONSULTA_VIGILANCIAS_EF_X_USUARIO);
        logger.debug(sql);
        return template.queryForObject(sql.toString(),
                new Object[]{usuario, idVigilancia},
                new int[]{Types.VARCHAR, Types.INTEGER}, Date.class);



    }

    @Override
    @Propagable(catched = true)
    public List<VigilanciaEntidadFederativa> obtenerVigilanciasPaginadas(Paginador paginador) {

        return (List<VigilanciaEntidadFederativa>) template.query(VigilanciaEntidadFederativaSQL.CONSULTA_VIGILANCIA_PAGINADA,
                new Object[]{paginador.getRowInicial(), paginador.getRowFinal()},
                new int[]{Types.NUMERIC, Types.NUMERIC},
                new VigilanciaEntidadFederativaMapper());

    }
}
