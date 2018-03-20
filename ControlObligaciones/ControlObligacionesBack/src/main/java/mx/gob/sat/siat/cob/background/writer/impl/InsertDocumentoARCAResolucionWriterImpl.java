/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.writer.impl;

import javax.sql.DataSource;
import mx.gob.sat.siat.cob.background.writer.InsertDocumentoARCAResolucionWriter;
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
@Service("insertDocumentoARCAResolucionWriter")
@Scope("step")
public class InsertDocumentoARCAResolucionWriterImpl extends JdbcBatchItemWriter
        implements InsertDocumentoARCAResolucionWriter {

    public InsertDocumentoARCAResolucionWriterImpl() {
        super.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider());
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
    @Value(DocumentoARCASQL.INSERT_T_DOCUMENTO)
    @Override
    public void setSql(String sql) {
        super.setSql(sql);
    }
}
