package mx.gob.sat.siat.cob.background.writer.impl;

import javax.sql.DataSource;

import mx.gob.sat.siat.cob.background.writer.RegistroMultaCobranzaWriter;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.DocumentoSQL;
import mx.gob.sat.siat.cob.seguimiento.util.constante.DataSourceNames;

import org.apache.log4j.Logger;

import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("registroMultaCobranzaWriter")
@Scope("step")
public class RegistroMultaCobranzaWriterImpl extends JdbcBatchItemWriter
        implements RegistroMultaCobranzaWriter {

    private Logger log = Logger.getLogger(RegistroMultaCobranzaWriterImpl.class);

    public RegistroMultaCobranzaWriterImpl() {
        super.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider());
    }

    @Autowired
    @Qualifier(DataSourceNames.COB)
    @Override
    public void setDataSource(DataSource dataSource) {
        log.debug("******writer multa cobranza");
        super.setDataSource(dataSource);

    }

    @Value(DocumentoSQL.ACTUALIZAR_IDRESOLUCION_MULTA)
    @Override
    public void setSql(String sql) {
        log.info("### ACTUALIZA_ID_RESOLUCION -->" + sql);
        super.setSql(sql);
    }
}
