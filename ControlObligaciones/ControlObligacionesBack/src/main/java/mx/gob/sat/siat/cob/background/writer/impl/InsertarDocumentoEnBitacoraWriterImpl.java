/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.writer.impl;

import javax.sql.DataSource;
import mx.gob.sat.siat.cob.background.writer.InsertarDocumentoEnBitacoraWriter;
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
@Service("insertarDocumentoEnBitacoraWriter")
@Scope("step")
public class InsertarDocumentoEnBitacoraWriterImpl extends JdbcBatchItemWriter
        implements InsertarDocumentoEnBitacoraWriter {

    public InsertarDocumentoEnBitacoraWriterImpl() {
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
    @Value(DocumentoARCASQL.INSERT_BITACORA_ARCHIVOS_MASIVAS)
    @Override
    public void setSql(String sql) {
        super.setSql(sql);
    }
}
