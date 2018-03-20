/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author Juan
 */
public class ConsultaDatosCobranzaMapper implements RowMapper<Resolucion> {

    private Logger log = Logger.getLogger(ConsultaDatosCobranzaMapper.class);

    @Override
    public Resolucion mapRow(ResultSet rs, int i) throws SQLException {

        Resolucion resolucion = new Resolucion();
        resolucion.setNumResolucionDet(rs.getString("NUMERORESOLUCION"));

        Persona persona = new Persona();
        persona.setBoId(rs.getString("BOID"));
        persona.setRfc(rs.getString("RFC"));
        persona.setIdPersonaTipo(rs.getLong("idTipoPersona"));

        resolucion.setPersona(persona);

        resolucion.setFecResolucionDet(new Date());
        resolucion.setIdClaveSir(rs.getLong("IDCLAVESIR"));
        resolucion.setIdFirmaTipo(rs.getLong("IDTIPOFIRMA"));
        resolucion.setFecReqCob(new Date());

        if (rs.getString("FECHANOTIFICACION") != null) {
            resolucion.setFecReqCob(rs.getDate("FECHANOTIFICACION"));
        }
        resolucion.setIdResolMotivoConstante(rs.getString("CONSTANTERESOLMOTIVO"));

        ICEP icep = new ICEP();
        icep.setIdMultaDesc(rs.getLong("IDMULTADESCUENTO"));
        List<ICEP> iceps = new ArrayList<ICEP>();
        iceps.add(icep);
        resolucion.setListaICEPs(iceps);

        try {
            resolucion.setImporteDet(rs.getBigDecimal("MONTO"));
        } catch (Exception ex) {
            log.error("La multa " + resolucion.getNumResolucionDet() + " tiene un monto nullo");
            log.error(ex);
        }
        resolucion.setCmIdCobro(rs.getBigDecimal("IDDOMICILIO"));

        return resolucion;
    }
}
