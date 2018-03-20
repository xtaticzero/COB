package mx.gob.sat.siat.cob.background.reader.impl;

import javax.sql.DataSource;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.ConsultarMontoIcepMapper;

import mx.gob.sat.siat.cob.background.reader.ConsultarMontoIcepReader;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.DocumentoSQL;
import mx.gob.sat.siat.cob.seguimiento.util.constante.DataSourceNames;

import org.apache.log4j.Logger;

import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("consultarMontoIcepReader")
@Scope("step")
public class ConsultarMontoIcepReaderImpl extends JdbcCursorItemReader
        implements ConsultarMontoIcepReader {

    private Logger log = Logger.getLogger(ConsultarMontoIcepReaderImpl.class);

    public ConsultarMontoIcepReaderImpl() {
        log.info("### ConsultarMontoIcepReaderImpl");
        setRowMapper(new ConsultarMontoIcepMapper());
    }

    @Autowired
    @Qualifier(DataSourceNames.COB)
    @Override
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    @Value(DocumentoSQL.CONSULTA_ICEPS_MULTA_BATCH)
    @Override
    public void setSql(String sql) {
        log.info("### ActualizarMontoIcepReaderImpl --> SQL " + sql);
        super.setSql(sql);
    }
}
