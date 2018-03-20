package mx.gob.sat.siat.cob.seguimiento.exception;

public class SGTServiceException extends Exception {
    /**
     *
     * @param throwable
     */
    public SGTServiceException(Throwable throwable) {
        super(throwable);
    }

    /**
     *
     * @param string
     * @param throwable
     */
    public SGTServiceException(String string, Throwable throwable) {
        super(string, throwable);
    }

    /**
     *
     * @param string
     */
    public SGTServiceException(String string) {
        super(string);
    }

    /**
     *
     */
    public SGTServiceException() {
        super();
    }
}
