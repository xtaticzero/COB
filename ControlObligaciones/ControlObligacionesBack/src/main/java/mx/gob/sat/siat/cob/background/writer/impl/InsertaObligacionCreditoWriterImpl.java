/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.writer.impl;

import javax.sql.DataSource;
import mx.gob.sat.siat.cob.background.writer.InsertaObligacionCreditoWriter;
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
@Service("insertaObligacionCreditoWriter")
@Scope("step")
public class InsertaObligacionCreditoWriterImpl extends JdbcBatchItemWriter
        implements InsertaObligacionCreditoWriter {

    private Logger log = Logger.getLogger(InsertaObligacionCreditoWriterImpl.class);

    public InsertaObligacionCreditoWriterImpl() {
        log.info("### Esta en InsertaObligacionCreditoWriter");
        super.setItemSqlParameterSourceProvider(
                new BeanPropertyItemSqlParameterSourceProvider());
    }

    @Autowired
    @Qualifier(DataSourceNames.ARCA)
    @Override
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    @Value(DocumentoARCASQL.INSERT_OBLIGACION_CREDITO_ARCA)
    @Override
    public void setSql(String sql) {
        log.info("### Esta en InsertaObligacionCreditoWriter --> SQL: " + sql);
        super.setSql(sql);
    }
}
