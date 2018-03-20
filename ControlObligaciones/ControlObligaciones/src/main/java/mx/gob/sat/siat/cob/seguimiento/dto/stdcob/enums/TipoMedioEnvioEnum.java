package mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums;
/**
 *
 * @author root
 * tabla SGTC_TIPOMEDIO
 * 19 feb 2014: cambio los id de tipos de envio y se quitaron algunos
 */
public enum TipoMedioEnvioEnum {

    SEPOMEX(1),
//    MENSAJERIA_ESPECIALIZADA(2),
//    MENSAJERIA_A(3),
//    ENTREGA_EN_MAF(4),
    UNIDAD_DILIGENCIACION(3),
    ENTIDAD_FEDERATIVA(2);


    private final int valor;

    private TipoMedioEnvioEnum(int valor) {
        this.valor = valor;
    }
    public int getValor(){
        return valor;
    }

    public static TipoMedioEnvioEnum obtenerTipoMedio(int idTipoMedio) {
        TipoMedioEnvioEnum tipo=null;
        for (TipoMedioEnvioEnum t : TipoMedioEnvioEnum.values()) {
            if(t.getValor()==idTipoMedio){
                tipo=t;
            }
        }
        return tipo;
    }
}
