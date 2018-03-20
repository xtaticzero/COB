package mx.gob.sat.siat.cob.seguimiento.web.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


public class FiltroWeb implements Filter {
    
    private static Logger logger = Logger.getLogger(FiltroWeb.class);
    
    public FiltroWeb() {
        super();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String userAgent = request.getHeader("User-Agent");
        
        if(userAgent.contains("MSIE")){
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.addHeader("X-UA-Compatible", "IE=EmulateIE8");
            logger.info("Se detecto navegador internet explorer y se adhiere header de compatibilidad X-UA");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
