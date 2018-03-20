/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.writer.impl;

import javax.sql.DataSource;
import mx.gob.sat.siat.cob.background.writer.DatosPeriodoARCAWriter;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.sql.DocumentoARCASQL;
import mx.gob.sat.siat.cob.seguimiento.util.constante.DataSourceNames;
import org.apache.log4j.Logger;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author Juan
 */
@Service("datosPeriodoARCAWriter")
@Scope("step")
public class DatosPeriodoARCAWriterImpl extends JdbcBatchItemWriter
        implements DatosPeriodoARCAWriter {

    private Logger log = Logger.getLogger(DatosPeriodoARCAWriterImpl.class);

    public DatosPeriodoARCAWriterImpl() {
        log.info("### Esta en DatosPeriodoARCAWriterImpl");
        super.setItemSqlParameterSourceProvider(
                new BeanPropertyItemSqlParameterSourceProvider());
    }

    @Autowired
    @Qualifier(DataSourceNames.ARCA)
    @Override
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    @Value(DocumentoARCASQL.INSERT_PERIODO_ARCA)
    @Override
    public void setSql(String sql) {
        log.info("### Esta en DatosPeriodoARCAWriterImpl --> SQL: " + sql);
        super.setSql(sql);
    }
}
