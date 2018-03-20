/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.writer.impl;

import javax.sql.DataSource;
import mx.gob.sat.siat.cob.background.writer.CumplimientoWriter;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.CumplimientoSQL;
import mx.gob.sat.siat.cob.seguimiento.util.constante.DataSourceNames;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author Juan
 */
@Service("cumplimientoWriter")
public class CumplimientoWriterImpl extends JdbcBatchItemWriter
        implements CumplimientoWriter {

    @Autowired
    @Qualifier(DataSourceNames.COB)
    @Override
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    @Value(CumplimientoSQL.INSERT_BATCH)
    @Override
    public void setSql(String sql) {
        super.setSql(sql);
    }

    public CumplimientoWriterImpl() {
        super.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider());
    }
}
