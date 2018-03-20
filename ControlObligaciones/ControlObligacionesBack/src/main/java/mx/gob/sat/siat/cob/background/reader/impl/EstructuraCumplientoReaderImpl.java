/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.reader.impl;

import javax.sql.DataSource;
import mx.gob.sat.siat.cob.background.reader.EstructuraCumplimientoReader;
import mx.gob.sat.siat.cob.seguimiento.dao.dbd2.mapper.HistoricoCumplimientoMapper;
import static mx.gob.sat.siat.cob.seguimiento.dao.dbd2.sql.HistoricoCumplimientoSQL.CONSULTA_CUMPLIMIENTO;
import mx.gob.sat.siat.cob.seguimiento.util.constante.DataSourceNames;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author root
 */
@Service("estructuraCumplimientoReader")
@Scope("step")
public class EstructuraCumplientoReaderImpl extends JdbcCursorItemReader
        implements EstructuraCumplimientoReader {
    public EstructuraCumplientoReaderImpl() {
        setRowMapper(new HistoricoCumplimientoMapper());
    }

    @Autowired
    @Qualifier(DataSourceNames.ESTRUCTURA_CUMPLIMIENTO)
    @Override
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }  
    
    
    @Value(CONSULTA_CUMPLIMIENTO)
    @Override
    public void setSql(String sql) {
        super.setSql(sql);      
    }
}
