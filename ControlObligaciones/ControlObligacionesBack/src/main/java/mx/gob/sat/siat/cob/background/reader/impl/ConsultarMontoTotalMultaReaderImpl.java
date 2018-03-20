/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.reader.impl;

import javax.sql.DataSource;
import mx.gob.sat.siat.cob.background.reader.ConsultarMontoTotalMultaReader;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.ConsultarMontoTotalMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.DocumentoSQL;
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
 * @author root
 */
@Service("consultarMontoTotalMultaReader")
@Scope("step")
public class ConsultarMontoTotalMultaReaderImpl extends JdbcCursorItemReader
        implements ConsultarMontoTotalMultaReader {

    private final Logger logger = Logger.getLogger(ConsultarMontoTotalMultaReaderImpl.class);

    public ConsultarMontoTotalMultaReaderImpl() {
        logger.info("### ConsultarMontoTotalMultaReaderImpl");
        setRowMapper(new ConsultarMontoTotalMapper());
    }

    @Autowired
    @Qualifier(DataSourceNames.COB)
    @Override
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    @Value(DocumentoSQL.CONSULTA_MONTO_TOTAL_MULTAS_BATCH)
    @Override
    public void setSql(String sql) {
        logger.info("### ConsultarMontoTotalMultaReaderImpl --> SQL " + sql);
        super.setSql(sql);
    }
}