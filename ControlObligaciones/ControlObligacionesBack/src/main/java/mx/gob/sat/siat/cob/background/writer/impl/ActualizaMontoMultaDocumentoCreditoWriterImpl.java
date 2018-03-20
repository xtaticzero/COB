/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.writer.impl;

import javax.sql.DataSource;
import mx.gob.sat.siat.cob.background.writer.ActualizaMontoMultaDocumentoCreditoWriter;
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
 * @author Daniel
 */
@Service("actualizaMontoMultaDocumentoCreditoWriter")
@Scope("step")
public class ActualizaMontoMultaDocumentoCreditoWriterImpl 
        extends JdbcBatchItemWriter
        implements ActualizaMontoMultaDocumentoCreditoWriter {
    private Logger log = Logger.getLogger(ActualizaMontoMultaDocumentoCreditoWriterImpl.class);
    
    
    public ActualizaMontoMultaDocumentoCreditoWriterImpl(){
    log.info("## ActualizaMontoMultaDocumentoCreditoWriterImpl --> ");
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
    @Value(MultaDocumentoSQL.ACTUALIZAR_MONTO_CREDITO_BATCH)
    @Override
    public void setSql(String sql) {
        log.info("### ActualizaMontoMultaDocumentoCreditoWriterImpl  SQL ..." + sql);
        super.setSql(sql);
    }
}
