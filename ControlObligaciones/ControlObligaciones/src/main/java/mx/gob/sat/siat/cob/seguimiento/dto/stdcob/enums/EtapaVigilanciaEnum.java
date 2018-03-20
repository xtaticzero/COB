package mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums;

public enum EtapaVigilanciaEnum {
    
    ETAPA_1(1),
    ETAPA_2(2),
    ETAPA_3(3),
    ETAPA_4(4);
    
    private int valor;

    private EtapaVigilanciaEnum(int valor) {
        this.valor = valor;
    }
    public int getValor(){
        return valor;
    }
    public EtapaVigilanciaEnum next(EtapaVigilanciaEnum etapa){
            if (etapa.ordinal() == EtapaVigilanciaEnum.values().length - 1) {
                return ETAPA_4;
            }
            else{
                            return EtapaVigilanciaEnum.values()[etapa.ordinal() + 1];
            }
        }
    
}
