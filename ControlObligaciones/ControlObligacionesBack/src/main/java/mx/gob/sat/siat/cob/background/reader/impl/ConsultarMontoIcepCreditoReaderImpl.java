/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.reader.impl;

import javax.sql.DataSource;
import mx.gob.sat.siat.cob.background.reader.ConsultarMontoIcepCreditoReader;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.ConsultarMontoIcepMapper;
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
 * @author Daniel
 */
@Service("consultarMontoIcepCreditoReader")
@Scope("step")
public class ConsultarMontoIcepCreditoReaderImpl extends JdbcCursorItemReader
        implements ConsultarMontoIcepCreditoReader {

    private final Logger logger = Logger.getLogger(ConsultarMontoIcepCreditoReaderImpl.class);

    public ConsultarMontoIcepCreditoReaderImpl() {
        logger.info("### ConsultarMontoIcepCreditoReaderImpl");
        setRowMapper(new ConsultarMontoIcepMapper());
    }

    @Autowired
    @Qualifier(DataSourceNames.COB)
    @Override
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    @Value(DocumentoSQL.CONSULTA_ICEPS_MULTA_CREDITO_BATCH)
    @Override
    public void setSql(String sql) {
        logger.info("### ConsultarMontoIcepCreditoReaderImpl --> SQL " + sql);
        super.setSql(sql);
    }
}
