/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.reader.impl;

import javax.sql.DataSource;
import mx.gob.sat.siat.cob.background.reader.DatosReqsAnterioresReader;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.mapper.ReqAnterioresMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.sql.DoctoEnviaArcaSQL;
import mx.gob.sat.siat.cob.seguimiento.util.constante.DataSourceNames;
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
@Service("datosReqsAnterioresReader")
@Scope("step")
public class DatosReqsAnterioresReaderImpl extends JdbcCursorItemReader
        implements DatosReqsAnterioresReader {

    public DatosReqsAnterioresReaderImpl() {
        setRowMapper(new ReqAnterioresMapper());
    }

    @Autowired
    @Qualifier(DataSourceNames.COB)
    @Override
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    @Value(DoctoEnviaArcaSQL.SELECT_REQANTERIORES_COB)
    @Override
    public void setSql(String sql) {
        super.setSql(sql);
    }
}
