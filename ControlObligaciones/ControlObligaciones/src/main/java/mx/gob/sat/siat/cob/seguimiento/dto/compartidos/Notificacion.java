package mx.gob.sat.siat.cob.seguimiento.dto.compartidos;

import java.io.Serializable;

public class Notificacion implements Serializable{
    
    public static final int ERROR = 0,INFO=1,WARNING=2;
    @SuppressWarnings("compatibility:172419896603046399")
    private static final long serialVersionUID = -8153664743236982959L;

    private String titulo;
    private String mensaje;
    private int tipo;
    
    public Notificacion() {
        super();
    }

    public Notificacion(String titulo, String mensaje, int tipo) {
        super();
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.tipo = tipo;
    }


    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getTipo() {
        return tipo;
    }
}
