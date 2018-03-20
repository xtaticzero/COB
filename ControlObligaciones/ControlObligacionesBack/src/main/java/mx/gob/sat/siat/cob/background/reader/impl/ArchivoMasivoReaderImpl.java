/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.reader.impl;

import javax.sql.DataSource;
import mx.gob.sat.siat.cob.background.reader.ArchivoMasivoReader;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.mapper.DocumentoMultaMasivaMapper;
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
@Service("archivoMasivoReader")
@Scope("step")
public class ArchivoMasivoReaderImpl extends JdbcCursorItemReader
        implements ArchivoMasivoReader {

    private Logger log = Logger.getLogger(ArchivoMasivoReaderImpl.class);

    public ArchivoMasivoReaderImpl() {
        setRowMapper(new DocumentoMultaMasivaMapper());
        log.info("### --> ArchivoMasivoReaderImpl");
    }

    @Autowired
    @Qualifier(DataSourceNames.COB)
    @Override
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    @Value(DoctoEnviaArcaSQL.SELECT_DOCUMENTO_IDC_CARGA_ARCHIVOS)
    @Override
    public void setSql(String sql) {
        super.setSql(sql);
        log.info("### SQL -->" + sql);
    }
}
