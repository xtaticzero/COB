/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.reader.impl;

import javax.sql.DataSource;
import mx.gob.sat.siat.cob.background.reader.DatosObligacionPeriodoMultaReader;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.mapper.ObligacionPeriodoMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.sql.DoctoEnviaArcaSQL;
import mx.gob.sat.siat.cob.seguimiento.util.constante.DataSourceNames;
import org.apache.log4j.Logger;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author Juan
 */
@Service("datosObligacionPeriodoMultaReader")
@Scope("step")
public class DatosObligacionPeriodoMultaReaderImpl extends JdbcCursorItemReader
        implements DatosObligacionPeriodoMultaReader {
    
    private Logger log = Logger.getLogger(DatosObligacionPeriodoMultaReaderImpl.class);
    
    public DatosObligacionPeriodoMultaReaderImpl() {
        setRowMapper(new ObligacionPeriodoMapper());
    }
    
    @Autowired
    @Qualifier(DataSourceNames.COB)
    @Override
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }
    
    @Value(DoctoEnviaArcaSQL.SELECT_OBLIGACION_PERIODO_MULTA)
    @Override
    public void setSql(String sql) {
        log.info("### Obligacion Periodo --> ");
        log.info(sql);
        super.setSql(sql);
    }
}
