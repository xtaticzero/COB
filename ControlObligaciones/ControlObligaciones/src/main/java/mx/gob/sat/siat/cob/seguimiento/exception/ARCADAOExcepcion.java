package mx.gob.sat.siat.cob.seguimiento.exception;

public class ARCADAOExcepcion extends Exception {

    private static final String DATASOURCE = ":Datasource ARCA:";

    /**
     *
     * @param throwable
     */
    public ARCADAOExcepcion(Throwable throwable) {
        super(throwable);
    }

    /**
     *
     * @param string
     * @param throwable
     */
    public ARCADAOExcepcion(String string, Throwable throwable) {
        super(string, throwable);
    }

    /**
     *
     * @param string
     */
    public ARCADAOExcepcion(String string) {
        super(string);
    }

    /**
     *
     */
    public ARCADAOExcepcion() {
        super();
    }

    @Override
    public String getMessage() {
        return DATASOURCE + super.getMessage();
    }

}
