package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Date;

import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.OracleDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.sql.OracleSQL;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class OracleDAOImpl implements OracleDAO {
    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;
    
    private final Logger logger = Logger.getLogger(OracleDAOImpl.class);
    
    @Transactional(readOnly = true)
    @Override
    public Date consultarFechaActual() throws SeguimientoDAOException {
        logger.debug("Consultando fecha actual...");
        
        Date fechaActual = template.query(OracleSQL.CONSULTAR_FECHA_ACTUAL,new ResultSetExtractor<Date>(){
                @Override
                public Date extractData(ResultSet resultSet) {
                    Date resultado = null;
                    try {
                        if(resultSet.next()){
                            logger.debug("Obteniendo fecha..."+resultSet);
                            resultado =  new Date(resultSet.getTimestamp("FECHA_ACTUAL").getTime());
                        }
                       
                    } catch (SQLException e) {
                        logger.error(e.toString());
                    }
                    return resultado;
                }
            });
        logger.debug("Fecha Oracle:"+Utilerias.formatearFechaAAAAMMDDHHMMSS(fechaActual));
        return fechaActual;
    }
}
