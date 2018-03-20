package mx.gob.sat.siat.cob.seguimiento.exception;

public class ExportarException extends Exception {
    
    public ExportarException(Throwable throwable) {
        super(throwable);
    }

    /**
     *
     * @param string
     * @param throwable
     */
    public ExportarException(String string, Throwable throwable) {
        super(string, throwable);
    }

    /**
     *
     * @param string
     */
    public ExportarException(String string) {
        super(string);
    }

    /**
     *
     */
    public ExportarException() {
        super();
    }
}
