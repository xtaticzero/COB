/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql;


/**
 *
 * @author root
 */
public interface ResolucionIcepSQL {
    String INSERT="insert into SGTA_ResolICEP (numeroresolucion,numerocontrol,claveicep,idmultamonto) values (?,?,?,null)";
    
    String SELECT_POR_NUMERO_RESOLUCION="select numeroresolucion,numerocontrol,claveicep from SGTA_ResolICEP where numeroresolucion=?";
    
    String INSERTA_ICEP_POR_MULTA_COMPLEMENTARIA = "insert into SGTA_ResolICEP (numerocontrol, claveicep, numeroresolucion,idmultamonto)\n" +
                                                        "SELECT det.numeroControl, det.claveIcep, numres.numeroresolucion,null\n" + 
                                                        "from SGTT_DetalleDoc det \n" + 
                                                            "inner join SGTT_NUMRESOLUCION numres\n" + 
                                                            "on( numres.numerocontrol = det.numerocontrol)\n" + 
                                                        "where det.numeroControl in(:documentos)\n" + 
                                                            "and det.TIENEMULTACOMPLEMENTARIA = 0\n"+ 
                                                            "and det.FECHAPRESENTACIONCOMPL is not null";
    
    String INSERTAR_ICEPS_POR_MULTAS_INCUMPLIMIENTO ="insert into SGTA_ResolICEP (numerocontrol, claveicep, numeroresolucion,idmultamonto)\n" +
                "select det.numeroControl, det.claveIcep, numres.numeroresolucion,null\n" + 
                "  from SGTT_Documento doc\n" + 
                "  inner join SGTT_DetalleDoc det \n" + 
                "    on (doc.numeroControl = det.numeroControl)\n" + 
                "    inner join SGTC_OBLIGACION obli\n" + 
                "      on ( det.idobligacion = obli.idobligacion )\n" + 
                "    inner join SGTT_NUMRESOLUCION numres\n" + 
                "      on( numres.numerocontrol = doc.numerocontrol)\n" +
                "  where doc.numeroControl in(:documentos)\n" + 
                "  and det.idsituacionicep = :idSituacionIcep\n"+ 
                "  and obli.aplicarenuentes in( :aplicaRenuentes)";
    
    String ACTUALIZAR_MONTO_RESOLICEP = "update SGTA_ResolICEP set idmultamonto = ?\n" +
                                            "where numeroresolucion = ? and claveicep = ?;";
    
    String BORRAR_MONTOS_RESOLICEP = "update SGTA_ResolICEP set idMultaMonto = null\n"
                                    + "where numeroresolucion = ?";
}
