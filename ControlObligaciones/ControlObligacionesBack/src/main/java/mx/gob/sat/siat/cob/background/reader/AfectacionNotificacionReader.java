package mx.gob.sat.siat.cob.background.reader;

import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.SGTBRetroARCADAO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.SGTBRetroARCA;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;

public class AfectacionNotificacionReader extends JdbcCursorItemReader implements ItemReader{
    private static Logger log = Logger.getLogger(AfectacionNotificacionReader.class);
    private SGTBRetroARCADAO sGTBRetroARCADAO;

    @Override
    public void afterPropertiesSet() throws SeguimientoDAOException, SGTServiceException {
        SGTBRetroARCA sGTBRetroARCA = sGTBRetroARCADAO.getUltimoActualizado();
        this.setSql("SELECT * FROM SIATControlDeObligaciones.T_SeguimientoARCA Seguimiento WHERE Id>"+sGTBRetroARCA.getIdProcesado() + " order by id " );
        log.debug("===>Query Setted!");
        log.debug("el ultimo Id procesado es::::::"+sGTBRetroARCA.getIdProcesado());
        log.debug("Paquete Actual 13 jun 2014::::::");
        try{
            super.afterPropertiesSet();
        }catch(Exception ex){
            throw new SGTServiceException(ex);
        }        
    }

    @Autowired
    public void setSGTBRetroARCADAO(SGTBRetroARCADAO sGTBRetroARCADAO) {
        this.sGTBRetroARCADAO = sGTBRetroARCADAO;
    }
}
