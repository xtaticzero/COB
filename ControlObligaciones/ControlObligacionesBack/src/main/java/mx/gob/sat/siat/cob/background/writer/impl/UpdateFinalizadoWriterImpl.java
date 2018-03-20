/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.writer.impl;

import javax.sql.DataSource;
import mx.gob.sat.siat.cob.background.writer.UpdateFinalizadoWriter;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.BuscaRenuentesSQL;
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
 * @author juan
 */
@Service("updateFinalizadoWriter")
@Scope("step")
public class UpdateFinalizadoWriterImpl extends JdbcBatchItemWriter
        implements UpdateFinalizadoWriter {

    private Logger log = Logger.getLogger(UpdateFinalizadoWriterImpl.class);

    public UpdateFinalizadoWriterImpl() {
        super.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider());
    }

    @Autowired
    @Qualifier(DataSourceNames.COB)
    @Override
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);

    }

    @Value(BuscaRenuentesSQL.UPDATE_FINALIZADA)
    @Override
    public void setSql(String sql) {
        log.info("### UpdateFinalizadoWriterImpl -->" + sql);
        super.setSql(sql);
    }

}
