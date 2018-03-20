/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.sql.Types;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.VigilanciaAprobarDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.VigilanciaAprobarMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.VigilanciaAprobarSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.db2.Paginador;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaAprobar;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author root
 */
@Repository
public class VigilanciaAprobarDAOImpl implements VigilanciaAprobarDAO, VigilanciaAprobarSQL {

    @Autowired
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    @Override
    @Propagable(catched = true)
    public List<VigilanciaAprobar> listarVigilanciasAprobar(long idCargoAdministrativo) {
        return template.query(CONSULTAR_TODAS,
                new Object[]{idCargoAdministrativo},
                new int[]{Types.NUMERIC},
                new VigilanciaAprobarMapper());
    }

    @Override
    @Propagable(catched = true)
    public List<VigilanciaAprobar> listarVigilanciasAprobar(long idCargoAdministrativo, String idAdministracionLocal) {
        return template.query(CONSULTAR_POR_LOCALIDAD,
                new Object[]{idCargoAdministrativo,
                    idAdministracionLocal,
                    idAdministracionLocal},
                new int[]{Types.NUMERIC,
                    Types.VARCHAR,
                    Types.VARCHAR},
                new VigilanciaAprobarMapper());
    }

    @Override
    @Propagable(catched = true)
    public List<VigilanciaAprobar> listarVigilanciasAprobar() {
        VigilanciaAprobarMapper mapper = new VigilanciaAprobarMapper();
        mapper.setValor(Integer.SIZE);
        return template.query(CONSULTAR_VIGILANCIAS,
                mapper);
    }

    @Propagable
    @Override
    public Long obtenerCantidadVigilanciasAL() {
        try {
            return template.queryForObject(CONSULTA_CANTIDAD_VIGILANCIAS_AL, Long.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    @Propagable(catched = true)
    public List<VigilanciaAprobar> obtenerVigilanciasPaginadas(Paginador paginador) {
        VigilanciaAprobarMapper mapper = new VigilanciaAprobarMapper();
        mapper.setValor(Integer.SIZE);
        return (List<VigilanciaAprobar>) template.query(CONSULTA_VIGILANCIA_PAGINADA,
                new Object[]{paginador.getRowInicial(), paginador.getRowFinal()},
                new int[]{Types.NUMERIC, Types.NUMERIC},
                mapper);

    }

}
