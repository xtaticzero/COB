package mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums;

/**
 *
 * @author root
 */
public enum TipoFirmaEnum {
    AUTOGRAFA(1),
    ELECTRONICA(2),
    FACSIMILAR(3);
    
    private int valor;

    private TipoFirmaEnum(int valor) {
        this.valor = valor;
    }
    public int getValor(){
        return valor;
    }
    
}
