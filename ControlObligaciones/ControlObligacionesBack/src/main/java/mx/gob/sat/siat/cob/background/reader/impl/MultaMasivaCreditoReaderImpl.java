/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.reader.impl;

import javax.sql.DataSource;
import mx.gob.sat.siat.cob.background.reader.MultaMasivaCreditoReader;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.mapper.DocumentoCreditoMapper;
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
 * @author Daniel
 */
@Service("multaMasivaCreditoReader")
@Scope("step")
public class MultaMasivaCreditoReaderImpl extends JdbcCursorItemReader
        implements MultaMasivaCreditoReader {

    private Logger logger = Logger.getLogger(MultaMasivaCreditoReaderImpl.class);

    public MultaMasivaCreditoReaderImpl() {
        logger.info("### MultaMasivaCreditoReaderImpl ");
        setRowMapper(new DocumentoCreditoMapper());
    }

    @Autowired
    @Qualifier(DataSourceNames.COB)
    @Override
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    @Value(DoctoEnviaArcaSQL.SELECT_DOCUMENTOS_MULTAS_CREDITO)
    @Override
    public void setSql(String sql) {
        logger.info("### MultaMasivaCreditoReaderImpl -->" + sql);
        super.setSql(sql);
    }
}
