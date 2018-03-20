/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.batch.writer.impl;

import javax.sql.DataSource;
import mx.gob.sat.siat.cob.seguimiento.batch.writer.FirmaRenuentesWriter;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.AprobarRenuentesSQL;
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
@Service("firmaRenuentesWriter")
@Scope("step")
public  class FirmaRenuentesWriterImpl extends JdbcBatchItemWriter implements FirmaRenuentesWriter {
    @Autowired
    @Qualifier(DataSourceNames.COB)
    @Override
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    @Value(AprobarRenuentesSQL.INSERTA_FIRMA_INDIVIDUAL)
    @Override
    public void setSql(String sql) {
        super.setSql(sql);
    }
    public FirmaRenuentesWriterImpl() {
        super.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider());
    }
}
