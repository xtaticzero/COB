/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.reader.impl;

import javax.sql.DataSource;
import mx.gob.sat.siat.cob.background.reader.BusquedaFinalizadaReader;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.BuscaRenuentesMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.BuscaRenuentesSQL;
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
 * @author juan
 */
@Service("busquedaFinalizadaReader")
@Scope("step")
public class BusquedaFinalizadaReaderImpl extends JdbcCursorItemReader
        implements BusquedaFinalizadaReader {

    private Logger log = Logger.getLogger(BusquedaFinalizadaReaderImpl.class);

    public BusquedaFinalizadaReaderImpl() {
        log.info("### BusquedaFinalizadaReaderImpl");
        setRowMapper(new BuscaRenuentesMapper());
    }

    @Autowired
    @Qualifier(DataSourceNames.COB)
    @Override
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    @Value(BuscaRenuentesSQL.REGISTROS_A_FINALIZAR)
    @Override
    public void setSql(String sql) {
        log.info("### BusquedaFinalizadaReaderImpl --> SQL " + sql);
        super.setSql(sql);
    }
}
