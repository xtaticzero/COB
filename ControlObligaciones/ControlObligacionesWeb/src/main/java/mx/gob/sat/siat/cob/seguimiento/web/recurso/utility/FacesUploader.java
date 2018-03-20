package mx.gob.sat.siat.cob.seguimiento.web.recurso.utility;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mx.gob.sat.siat.cob.seguimiento.web.recurso.AbstractFacesServlet;

public class FacesUploader extends AbstractFacesServlet implements Serializable{
    /**
     *
     */
    public FacesUploader() {
        super();
    }

    /**
     *
     * @param request
     * @param response
     */
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        FacesMessage msg = new FacesMessage("succesful esta arriba...");
        FacesContext context = this.getFacesContext(request, response);
        context.addMessage(null, msg);
    }
}
