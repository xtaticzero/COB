/*	****************************************************************
 * Nombre de archivo: PeriodoValidezServiceImpl.java
 * Autores: Emmanuel Estrada Gonzalez 		 	 		   
 * Bitácora de modificaciones:
 * CR/Defecto Fecha Autor Descripción del cambio
 * ---------------------------------------------------------------------------
 * N/A 10/10/2013 <Nombre del desarrollador que está modificando el archivo>
 * ---------------------------------------------------------------------------
 */
package mx.gob.sat.siat.cob.seguimiento.dao.cobranza.impl;

import java.sql.Types;

import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.cobranza.DiaInhabilDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.cobranza.mapper.DiaInhabilMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.cobranza.sql.DiaInhabilSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.DiaInhabil;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author Emmanuel Estrada Gonzalez
 */
@Repository("diaInhabildaoImpl")
public class DiaInhabilDAOImpl implements DiaInhabilDAO {

    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_ADM_SGT)
    private JdbcTemplate template;

    /**
     * Metodo que regresa una lista con todos los dias inhabiles contenidos en
     * la tabla "ADMC_DIAINHABIL"
     *
     * @return List<DiaInhabil>
     */
    @Override
    @Propagable
    public List<DiaInhabil> todosDiaInhabil(Date fechaNotificacion) {
        List<DiaInhabil> listDiaInhabil = null;
        listDiaInhabil = template.query(DiaInhabilSQL.OBTEN_TODOS_DIA_INHABIL,
                new Object[]{fechaNotificacion},
                new int[]{Types.DATE},
                new DiaInhabilMapper());
        return listDiaInhabil;
    }
}
