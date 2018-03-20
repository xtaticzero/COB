/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.reader.impl;

import javax.sql.DataSource;
import mx.gob.sat.siat.cob.background.reader.DatosCobranzaReader;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.ConsultaDatosCobranzaMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.DocumentoSQL;
import mx.gob.sat.siat.cob.seguimiento.util.constante.DataSourceNames;
import org.apache.log4j.Logger;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author Juan
 */
@Service("datosCobranzaReader")
@Scope("step")
public class DatosCobranzaReaderImpl extends JdbcCursorItemReader
        implements DatosCobranzaReader {

    private final Logger logger = Logger.getLogger(DatosCobranzaReaderImpl.class);

    public DatosCobranzaReaderImpl() {
        logger.info("### DatosCobranzaReaderImpl");
        setRowMapper(new ConsultaDatosCobranzaMapper());
    }

    @Autowired
    @Qualifier(DataSourceNames.COB)
    @Override
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    @Value(DocumentoSQL.SELECT_INFO_COBRANZA)
    @Override
    public void setSql(String sql) {
        logger.info("### DatosCobranzaReaderImpl --> SQL " + sql);
        super.setSql(sql);
    }
}
