package mx.gob.sat.siat.cob.seguimiento.exception;

public class ARCAServiceException extends Exception {
    /**
     *
     * @param throwable
     */
    public ARCAServiceException(Throwable throwable) {
        super(throwable);
    }

    /**
     *
     * @param string
     * @param throwable
     */
    public ARCAServiceException(String string, Throwable throwable) {
        super(string, throwable);
    }

    /**
     *
     * @param string
     */
    public ARCAServiceException(String string) {
        super(string);
    }

    /**
     *
     */
    public ARCAServiceException() {
        super();
    }
}
