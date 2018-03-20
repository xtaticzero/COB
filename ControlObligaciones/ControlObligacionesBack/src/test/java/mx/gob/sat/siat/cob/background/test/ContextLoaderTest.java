package mx.gob.sat.siat.cob.background.test; 

import org.apache.log4j.Logger;
import org.springframework.context.support.GenericXmlApplicationContext;

public class ContextLoaderTest {
    public Logger log=Logger.getLogger(ContextLoaderTest.class);
    private  GenericXmlApplicationContext context;
    
    public ContextLoaderTest() {
        iniciarContexto();
    }
    
    private void iniciarContexto(){
        try {
            context = new GenericXmlApplicationContext();
            context.getEnvironment().setActiveProfiles("development");
            context.load("/applicationContext-main.xml");
            context.refresh();
        }catch(Exception ex) {
            ex.getMessage();
        }
    }

    public GenericXmlApplicationContext getContext() {
        return context;
    }

    public void setContext(GenericXmlApplicationContext context) {
        this.context=context;
    }
}
