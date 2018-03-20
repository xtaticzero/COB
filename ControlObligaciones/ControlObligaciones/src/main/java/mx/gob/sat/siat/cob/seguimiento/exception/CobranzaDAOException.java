package mx.gob.sat.siat.cob.seguimiento.exception;

public class CobranzaDAOException extends Exception {
    private static final String DATASOURCE = ":Datasource Cobranza:";

    /**
     *
     * @param throwable
     */
    public CobranzaDAOException(Throwable throwable) {
        super(throwable);
    }

    /**
     *
     * @param string
     * @param throwable
     */
    public CobranzaDAOException(String string, Throwable throwable) {
        super(string, throwable);
    }

    /**
     *
     * @param string
     */
    public CobranzaDAOException(String string) {
        super(string);
    }

    /**
     *
     */
    public CobranzaDAOException() {
        super();
    }

    @Override
    public String getMessage() {
        return DATASOURCE + super.getMessage();
    } 
}
