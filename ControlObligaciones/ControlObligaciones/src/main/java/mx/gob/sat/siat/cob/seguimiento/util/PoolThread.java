/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.util;

/**
 *
 * @author root
 */
public class PoolThread {

    public static final int RUNNING = 1, FINISHED = 2;
    private int estado;

    public PoolThread() {
        estado = RUNNING;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
