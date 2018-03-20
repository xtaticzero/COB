package mx.gob.sat.siat.cob.seguimiento.dao.arca.sql;

public interface OrigenMultaSQL {
    
    String INSERT = "INSERT INTO SGTT_REQSANTERIORES (\n"
            + "    IDDOCUMENTO,\n" 
            + "    IDNUMERODECONTROLORIGEN,\n"             
            + "    FDFECHANOTIFICACION,\n"            
            + "    IDNUMERODECONTROLPRIMERO,\n"             
            + "    FDFECHANOTIFICACIONPRIMERO,\n"
            + "    IDNUMERODECONTROLSEGUNDO,\n"             
            + "    FDFECHANOTIFICACIONSEGUNDO) VALUES(";    

}
