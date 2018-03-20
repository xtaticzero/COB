package mx.gob.sat.siat.cob.seguimiento.exception;

public class CombosException extends Exception{
   
    public CombosException(Throwable throwable) {
        super(throwable);
    }

    /**
     *
     * @param string
     * @param throwable
     */
    public CombosException(String string, Throwable throwable) {
        super(string, throwable);
    }

    /**
     *
     * @param string
     */
    public CombosException(String string) {
        super(string);
    }

    /**
     *
     */
    public CombosException() {
        super();
    }
}
