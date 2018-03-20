/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.writer.impl;

import javax.sql.DataSource;
import mx.gob.sat.siat.cob.background.writer.BajaIcepWriter;
import mx.gob.sat.siat.cob.seguimiento.dao.dbd2.sql.PadronSQL;
import mx.gob.sat.siat.cob.seguimiento.util.constante.DataSourceNames;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author root
 */
@Service("bajaIcepWriter")
public class BajaIcepWriterImpl extends JdbcBatchItemWriter implements BajaIcepWriter {

    @Autowired
    @Qualifier(DataSourceNames.COB)
    @Override
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    @Value(PadronSQL.INSERT_BAJA_ICEP)
    @Override
    public void setSql(String sql) {
        super.setSql(sql);
    }

    public BajaIcepWriterImpl() {
        super.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider());
    }
}
