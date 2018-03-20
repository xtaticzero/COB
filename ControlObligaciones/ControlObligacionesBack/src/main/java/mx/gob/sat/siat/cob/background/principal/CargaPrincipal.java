package mx.gob.sat.siat.cob.background.principal;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author christian.ventura
 */
public final class CargaPrincipal {

    private static Logger logger = Logger.getLogger(CargaPrincipal.class);
    
    private CargaPrincipal(){
        
    }

    /**
     *
     * @param arg
     */
    public static void main(String[] arg) {
        System.setProperty("mail.mime.charset", "utf8");
        ClassPathXmlApplicationContext classPathXmlApplicationContext =
                new ClassPathXmlApplicationContext("JobManagerContext.xml");
        logger.debug("isRunning:"+classPathXmlApplicationContext.isRunning());
    }
    
}
