package mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums;

public enum IcepRenuenteEnum {
        NO(0),
        SI(1);
        
        private int valor;

        private IcepRenuenteEnum(int valor) {
            this.valor = valor;
        }
        public int getValor(){
            return valor;
        }
}

