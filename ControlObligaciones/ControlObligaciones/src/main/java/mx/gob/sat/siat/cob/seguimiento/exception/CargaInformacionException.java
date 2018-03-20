package mx.gob.sat.siat.cob.seguimiento.exception;

public class CargaInformacionException extends Exception {
    
    /**
     *
     * @param throwable
     */
    public CargaInformacionException(Throwable throwable) {
        super(throwable);
    }

    /**
     *
     * @param string
     * @param throwable
     */
    public CargaInformacionException(String string, Throwable throwable) {
        super(string, throwable);
    }

    /**
     *
     * @param string
     */
    public CargaInformacionException(String string) {
        super(string);
    }

    /**
     *
     */
    public CargaInformacionException() {
        super();
    }
}