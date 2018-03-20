/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.writer.impl;

import javax.sql.DataSource;
import mx.gob.sat.siat.cob.background.writer.InsertaObligacionXResolucionARCAWriter;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.sql.DocumentoARCASQL;
import mx.gob.sat.siat.cob.seguimiento.util.constante.DataSourceNames;
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
@Service("insertaObligacionXResolucionARCAWriter")
@Scope("step")
public class InsertaObligacionXResolucionARCAWriterImpl extends JdbcBatchItemWriter
        implements InsertaObligacionXResolucionARCAWriter {

    public InsertaObligacionXResolucionARCAWriterImpl() {
        super.setItemSqlParameterSourceProvider(
                new BeanPropertyItemSqlParameterSourceProvider());
    }

    @Autowired
    @Qualifier(DataSourceNames.ARCA)
    @Override
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    /**
     *
     * @param sql
     */
    @Value(DocumentoARCASQL.INSERT_OBLIGACION_ARCA)
    @Override
    public void setSql(String sql) {
        super.setSql(sql);
    }
}
