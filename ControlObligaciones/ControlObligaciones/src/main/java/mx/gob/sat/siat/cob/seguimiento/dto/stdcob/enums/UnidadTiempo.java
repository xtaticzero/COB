package mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums;

public enum UnidadTiempo {
   MILISEGUNDO(1),
   SEGUNDO(1000),
   MINUTO(60000),
   HORA(3600000),
   DIA(86400000);
   
   private int milisegundos;
    private UnidadTiempo(int value) {
            this.milisegundos = value;
    }
    public int getMilis(){
        return milisegundos;
    }
}
