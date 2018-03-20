/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.writer.impl;

import javax.sql.DataSource;
import mx.gob.sat.siat.cob.background.writer.InsertaBitacoraResolucionWriter;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.sql.DoctoEnviaArcaSQL;
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
@Service("insertaBitacoraResolucionWriter")
@Scope("step")
public class InsertaBitacoraResolucionWriterImpl extends JdbcBatchItemWriter
        implements InsertaBitacoraResolucionWriter {

    private Logger log = Logger.getLogger(InsertaBitacoraResolucionWriterImpl.class);

    public InsertaBitacoraResolucionWriterImpl() {
        super.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider());
    }

    @Autowired
    @Qualifier(DataSourceNames.COB)
    @Override
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);

    }

    @Value(DoctoEnviaArcaSQL.INSERT_BITACORA_RESOLUCION)
    @Override
    public void setSql(String sql) {
        log.info("### ACTUALIZA_BITACORA -->" + sql);
        super.setSql(sql);
    }
}
