/*package mx.gob.sat.siat.cob.seguimiento.utils;

import java.io.Serializable;

import java.util.Date;

import javax.faces.context.FacesContext;

import javax.servlet.http.HttpSession;

import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.SIATSeguridad;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.UsuarioNovell;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.api.Usuario;
import mx.gob.sat.siat.controlador.BitacoraService;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

public class SIATSeguridadNovell implements Serializable, SIATSeguridad {
    
   
    private static String strSesionNovell = "";
    private static String auth = "";
    private static String mac = "";
    private UsuarioNovell usrNovell;
    private long idMovimiento = 0L;
    
    public SIATSeguridadNovell() {
        super();
    }

    @Override
    public void aplica() throws Exception {
        
        HttpSession sess = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        AccesoUsr usuariosiat = (AccesoUsr)sess.getAttribute("accesoEF");
        this.usrNovell = (UsuarioNovell)usuariosiat;
        
        BitacoraService bitacoraServices = new BitacoraService();
        this.idMovimiento =
               bitacoraServices.registraMovimiento(strSesionNovell, 
                                                   new Date(), 
                                                   new Date(), 
                                                   usuariosiat.getRfc(), 
                                                   89,
                                                   2,
                                                   "Ingreso al sistema por tipo de autenticacion = " + auth,
                                                   1,
                                                   "1",
                                                   1);
        bitacoraServices.registraMovSesion(idMovimiento, usuariosiat.getIp(), mac, strSesionNovell);
        
    }

    @Override
    public Usuario getUsuario() {
        return this.usrNovell;
    }

    @Override
    public long getIdMovimiento() {
        return this.idMovimiento;
    }
}
*/