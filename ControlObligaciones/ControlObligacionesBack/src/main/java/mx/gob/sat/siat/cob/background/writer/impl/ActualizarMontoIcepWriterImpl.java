/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.writer.impl;

import javax.sql.DataSource;
import mx.gob.sat.siat.cob.background.writer.ActualizarMontoIcepWriter;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.MultaDocumentoSQL;
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
 * @author root
 */
@Service("actualizarMontoIcepWriter")
@Scope("step")
public class ActualizarMontoIcepWriterImpl extends JdbcBatchItemWriter
        implements ActualizarMontoIcepWriter{
    
    
    private Logger log = Logger.getLogger(ActualizarMontoIcepWriterImpl.class);
    
    public ActualizarMontoIcepWriterImpl() {
        log.info("## ActualizarMontoIcepWriterImpl --> ");
        super.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider());
    }

    @Autowired
    @Qualifier(DataSourceNames.COB)
    @Override
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    /**
     *
     * @param sql
     */
    @Value(MultaDocumentoSQL.ACTUALIZAR_MONTO_MULTA_ICEP)
    @Override
    public void setSql(String sql) {
        log.info("### ActualizarMontoIcepWriterImpl  SQL ..." + sql);
        super.setSql(sql);
    }
    
}
