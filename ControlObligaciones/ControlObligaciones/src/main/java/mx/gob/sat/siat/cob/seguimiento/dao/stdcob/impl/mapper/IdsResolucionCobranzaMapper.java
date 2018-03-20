package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mx.gob.sat.siat.cobranza.domain.ICEP;
import mx.gob.sat.siat.cobranza.domain.Persona;
import mx.gob.sat.siat.cobranza.domain.Resolucion;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;


public class IdsResolucionCobranzaMapper implements RowMapper<Resolucion> {

    /**
     *
     * @param rs
     * @param i
     * @return
     * @throws SQLException
     */
    private Logger log = Logger.getLogger(IdsResolucionCobranzaMapper.class);
    @Override
    public Resolucion mapRow(ResultSet rs, int i) throws SQLException {

        Resolucion resolucion = new Resolucion();
        resolucion.setNumResolucionDet(rs.getString("NUMERORESOLUCION"));
        resolucion.setFecResolucionDet(new Date());
        resolucion.setIdClaveSir(rs.getLong("IDCLAVESIR"));
        resolucion.setIdResolMotivoConstante("RESOLMOTIVO_INCUMPLIMIENTO");
        try{
            resolucion.setImporteDet(rs.getBigDecimal("idmultamonto"));
        }catch(Exception ex){
            log.error("La multa " +resolucion.getNumResolucionDet() + " tiene un monto nullo");
            log.error(ex);
        }
        resolucion.setIdFirmaTipo(rs.getLong("IDTIPOFIRMA"));
        resolucion.setFecReqCob(new Date());
        if (rs.getString("FECHANOTIFICACION") != null){
            resolucion.setFecReqCob(rs.getDate("FECHANOTIFICACION"));
        }
        
        try{
        resolucion.setClaveICEP(rs.getLong("CLAVEICEP"));
        }catch(Exception ex){
            log.debug("no se pudo obtener claveicep debido a que no existe al obtener el monto total y eso es correcto");
        }
 
        ICEP icep = new ICEP();
        icep.setIdMultaDesc(rs.getLong("IDMULTADESCUENTO"));
        List<ICEP> iceps = new ArrayList<ICEP>();
        iceps.add(icep);
        Persona per = new Persona();
        per.setBoId("1018716446448457062980458107561");
        per.setRfc("AARE8011132M8");
        resolucion.setPersona(per);
        resolucion.setListaICEPs(iceps);
        return resolucion;
    }
}