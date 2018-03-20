/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.reader.impl;

import javax.sql.DataSource;
import mx.gob.sat.siat.cob.background.reader.ConsultarDatosCreditoReader;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.DatosCreditoFiscalMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.DocumentoSQL;
import mx.gob.sat.siat.cob.seguimiento.util.constante.DataSourceNames;
import org.apache.log4j.Logger;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author Daniel
 */
public class ConsultarDatosCreditoReaderImpl extends JdbcCursorItemReader
    implements ConsultarDatosCreditoReader{
 
    private Logger log  = Logger.getLogger(ConsultarDatosCreditoReaderImpl.class);

    public ConsultarDatosCreditoReaderImpl(){
        log.info("### ConsultarDatosCreditoReaderImpl");
        setRowMapper(new DatosCreditoFiscalMapper());
    }
    
    @Autowired
    @Qualifier(DataSourceNames.COB)
    @Override
    public void setDataSource(DataSource dataSource){
        super.setDataSource(dataSource);
    }
    
    @Value(DocumentoSQL.CONSULTA_MONTO_TOTAL_MULTAS_BATCH)
    @Override 
    public void setSql(String sql){
    log.info("###sql de consulta " + sql);
    super.setSql(sql);
    }
}
