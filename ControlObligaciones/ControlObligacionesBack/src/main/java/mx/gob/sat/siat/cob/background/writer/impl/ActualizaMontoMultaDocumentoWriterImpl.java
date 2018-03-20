/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.writer.impl;

import javax.sql.DataSource;
import mx.gob.sat.siat.cob.background.writer.ActualizaMontoMultaDocumentoWriter;
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
 * @author Juan
 */
@Service("actualizaMontoMultaDocumentoWriter")
@Scope("step")
public class ActualizaMontoMultaDocumentoWriterImpl
        extends JdbcBatchItemWriter
        implements ActualizaMontoMultaDocumentoWriter {

    private Logger log = Logger.getLogger(ActualizaMontoMultaDocumentoWriterImpl.class);
    
    public ActualizaMontoMultaDocumentoWriterImpl() {
        log.info("## ActualizaMontoMultaDocumentoWriterImpl --> ");
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
    @Value(MultaDocumentoSQL.ACTUALIZAR_MONTO_RESOLUCIONDOC_BATCH)
    @Override
    public void setSql(String sql) {
        log.info("### ActualizaMontoMultaDocumentoWriterImpl  SQL ..." + sql);
        super.setSql(sql);
    }
}
