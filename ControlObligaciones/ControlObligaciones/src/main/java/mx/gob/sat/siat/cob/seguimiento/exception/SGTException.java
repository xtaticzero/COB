package mx.gob.sat.siat.cob.seguimiento.exception;

public class SGTException extends Exception {
    
    /**
     *
     * @param throwable
     */
    public SGTException(Throwable throwable) {
        super(throwable);
    }

    /**
     *
     * @param string
     * @param throwable
     */
    public SGTException(String string, Throwable throwable) {
        super(string, throwable);
    }

    /**
     *
     * @param string
     */
    public SGTException(String string) {
        super(string);
    }

    /**
     *
     */
    public SGTException() {
        super();
    }
}