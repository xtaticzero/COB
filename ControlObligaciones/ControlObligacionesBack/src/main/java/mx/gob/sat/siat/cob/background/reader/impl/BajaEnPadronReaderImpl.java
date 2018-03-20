/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.reader.impl;

import javax.sql.DataSource;
import mx.gob.sat.siat.cob.background.reader.BajaEnPadronReader;
import mx.gob.sat.siat.cob.seguimiento.dao.dbd2.mapper.PadronMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.dbd2.sql.PadronSQL;
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
@Service("bajaEnPadronReader")
@Scope("step")
public class BajaEnPadronReaderImpl extends JdbcCursorItemReader implements BajaEnPadronReader {

    public BajaEnPadronReaderImpl() {
        setRowMapper(new PadronMapper());
    }

    @Autowired
    @Qualifier(DataSourceNames.ESTRUCTURA_CUMPLIMIENTO)
    @Override
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    @Value(PadronSQL.CONSULTA_BAJA_PADRON)
    @Override
    public void setSql(String sql) {
        super.setSql(sql);
    }
}
