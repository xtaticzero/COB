package mx.gob.sat.siat.cob.background.reader.impl;

import javax.sql.DataSource;
import mx.gob.sat.siat.cob.background.reader.DatosObligacionPeriodoCOBReader;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.mapper.ObligacionPeriodoMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.sql.DoctoEnviaArcaSQL;
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
@Service("datosObligacionPeriodoCOBReader")
@Scope("step")
public class DatosObligacionPeriodoCOBReaderImpl extends JdbcCursorItemReader
        implements DatosObligacionPeriodoCOBReader {

    private Logger logger = Logger.getLogger(DatosObligacionPeriodoCOBReaderImpl.class);

    public DatosObligacionPeriodoCOBReaderImpl() {
        logger.info("### DatosObligacionPeriodoCOBReaderImpl");
        setRowMapper(new ObligacionPeriodoMapper());
    }

    @Autowired
    @Qualifier(DataSourceNames.COB)
    @Override
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    @Value(DoctoEnviaArcaSQL.SELECT_OBLIGACION_PERIODO_CARGA_ARCHIVOS)
    @Override
    public void setSql(String sql) {
        logger.info("### DatosObligacionPeriodoCOBReaderImpl --> SQL " + sql);
        super.setSql(sql);
    }
}
