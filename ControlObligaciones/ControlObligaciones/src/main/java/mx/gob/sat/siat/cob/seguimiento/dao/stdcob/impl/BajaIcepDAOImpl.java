/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.util.Set;

import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.BajaIcepDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.BajaIcepSQL;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.BajaIcepSQL.DELETE_ALL;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.BajaIcepSQL.DELETE_POR_BOIDS_ICEPS;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import static mx.gob.sat.siat.cob.seguimiento.util.Utilerias.reeamplazarCorchetesPorParentesis;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 *
 * @author root
 */
@Repository
public class BajaIcepDAOImpl implements BajaIcepDAO, BajaIcepSQL {

    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    @Override
    @Propagable(catched = true)
    public Integer borrarPorBoidEIcep(Set<String> boids, Set<Long> iceps) {
        if (boids.isEmpty() && iceps.isEmpty()) {
            return template.update(DELETE_ALL);
        } else {
            String query = DELETE_POR_BOIDS_ICEPS.
                    replace("BOIDS", reeamplazarCorchetesPorParentesis(boids.toString()));
            query = query.replace("ICEPS", reeamplazarCorchetesPorParentesis(iceps.toString()));
            return template.update(query);
        }
    }

}
