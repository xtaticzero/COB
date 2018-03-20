/*

* Todos los Derechos Reservados 2013 SAT.

* Servicio de Administracion Tributaria (SAT).

*

* Este software contiene informacion propiedad exclusiva del SAT considerada

* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma

* parcial o total.

*/
package mx.gob.sat.siat.cob.seguimiento.web.recurso;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


public class PetNoValRedirectServlet extends AbstractFacesServlet implements Serializable{
    
    @SuppressWarnings("compatibility:3345518045641363054")
    private static final long serialVersionUID = 1L;
    
    private static final String SERVLET_EXCEPTION_KEY = "javax.servlet.error.exception";
    private static Logger logger=Logger.getLogger(PetNoValRedirectServlet.class.getName());
    /**
     *
     */
    public PetNoValRedirectServlet() {
        super();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        try {
            Throwable throwable = (Throwable) request.getAttribute(SERVLET_EXCEPTION_KEY);
            logger.error(throwable.getMessage());
            response.sendRedirect(getServletContext().getContextPath() + "/petnoval404.jsf");
            
        } catch (Exception ex) {
            response.sendRedirect(getServletContext().getContextPath() + "/petnoval404.jsf");
        }
    }

    /**
     *
     * @param request
     * @param response
     */
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
    }
}
