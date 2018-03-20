/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.exception;

import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;

/**
 *
 * @author root
 */
public class RFCAmpliadoException extends Exception {

    /**
     *
     * @param throwable
     */
    public RFCAmpliadoException(Throwable throwable) {
        super(throwable);
    }

    /**
     *
     * @param string
     * @param throwable
     */
    public RFCAmpliadoException(String string, Throwable throwable) {
        super(string, throwable);
    }

    /**
     *
     * @param string
     */
    public RFCAmpliadoException(String string) {
        super(string);
    }

    /**
     *
     */
    public RFCAmpliadoException() {
        super();
    }

    @Override
    public String getMessage() {
        return "No se puede conectar con datasource " + ConstantsCatalogos.DATASOURCE_AMPLIADO;
    }
}
