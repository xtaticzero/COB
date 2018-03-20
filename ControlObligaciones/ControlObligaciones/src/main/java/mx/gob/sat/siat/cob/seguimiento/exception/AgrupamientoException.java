package mx.gob.sat.siat.cob.seguimiento.exception;

public class AgrupamientoException extends Exception{
    @SuppressWarnings("compatibility:2658468177455429935")
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public AgrupamientoException() {
        super();
    }

    /**
     *
     * @param causa
     */
    public AgrupamientoException(Throwable causa) {
        super(causa);
    }
}
