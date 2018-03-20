/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.batch.writer.impl;

import javax.sql.DataSource;
import mx.gob.sat.siat.cob.seguimiento.batch.writer.FirmaMultasWriter;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.AprobarMultasSQL;
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
 * @author root
 */
@Service("firmaMultasWriter")
@Scope("step")
public class FirmaMultasWriterImpl extends JdbcBatchItemWriter implements FirmaMultasWriter{
    @Autowired
    @Qualifier(DataSourceNames.COB)
    @Override
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    @Value(AprobarMultasSQL.INSERTA_FIRMA_INDIVIDUAL)
    @Override
    public void setSql(String sql) {
        super.setSql(sql);
    }
    public FirmaMultasWriterImpl() {
        super.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider());
    }
}
