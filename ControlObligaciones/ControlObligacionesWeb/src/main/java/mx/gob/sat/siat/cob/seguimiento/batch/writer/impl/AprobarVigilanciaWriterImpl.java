package mx.gob.sat.siat.cob.seguimiento.batch.writer.impl;

import javax.sql.DataSource;
import mx.gob.sat.siat.cob.seguimiento.batch.writer.AprobarVigilanciaWriter;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.DocumentoAprobarSQL;
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
 * @author christian.ventura
 */
@Service("aprobarVigilanciaWriter")
@Scope("step")
public class AprobarVigilanciaWriterImpl extends JdbcBatchItemWriter implements AprobarVigilanciaWriter {

    /**
     *
     * @param dataSource
     */
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
    @Value(DocumentoAprobarSQL.INSERTA_FIRMAS_DOCUMENTOS)
    @Override
    public void setSql(String sql) {
        super.setSql(sql);
    }

    /**
     *
     */
    public AprobarVigilanciaWriterImpl() {
        super.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider());
    }
    
}
