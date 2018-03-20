/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.arca.mapper.DocsAsociadosMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper.ComboMapperStr;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.AfectacionXAutoridadDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.AfectacionXAutoridadMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.BusquedaDocumentosMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.CancelacionXAutoridadMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.ConsultaReporteMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.ConsultasRenuentesMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.MultaDocumentoAfectacionesMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.ResolucionesDocumentosMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.AfectacionXAutoridadSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.RequerimientosAnteriores;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.ComboStr;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AfectacionXAutoridad;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ConsultasRenuentes;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaDocumentoAfectaciones;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ReporteAfectacionXAutoridadDTO;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;


@Repository
public class AfectacionXAutoridadDAOImpl implements AfectacionXAutoridadDAO {


    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    @Override
    @Propagable(catched = true)
    public List<AfectacionXAutoridad> searchDocsAfectacionByNumeroControl(String numeroControl) {
        String query = AfectacionXAutoridadSQL.CONSULTA_AFECTACION_BY_NUMCONTROL + "'" + numeroControl + "'";
        List<AfectacionXAutoridad> listAfectacion = this.template.query(query, new AfectacionXAutoridadMapper());
        if(listAfectacion== null || listAfectacion.isEmpty()){
            Logger.getLogger(AfectacionXAutoridadDAOImpl.class.getName()).log(Level.INFO, query);
        }
        return listAfectacion;
    }


    @Override
    @Propagable(catched = true)
    public List<AfectacionXAutoridad> searchAfectacionXAutoridadByNumeroControl(String numeroControl) {
        String query = AfectacionXAutoridadSQL.AFECTACION_BY_NUMCONTROL + "'" + numeroControl + "'";
        List<AfectacionXAutoridad> listAfectacion = this.template.query(query, new CancelacionXAutoridadMapper());
        if(listAfectacion== null || listAfectacion.isEmpty()){
            Logger.getLogger(AfectacionXAutoridadDAOImpl.class.getName()).log(Level.INFO, query);
        }

        return listAfectacion;
    }

    @Override
    @Propagable(catched = true)
    public List<AfectacionXAutoridad> searchAfectacionXAutoridadByRFC(String rfc) {
        String query = AfectacionXAutoridadSQL.AFECTACION_BY_RFC + "'" + rfc + "'";
        List<AfectacionXAutoridad> listAfectacion = this.template.query(query, new AfectacionXAutoridadMapper());
        if(listAfectacion== null || listAfectacion.isEmpty()){
            Logger.getLogger(AfectacionXAutoridadDAOImpl.class.getName()).log(Level.INFO, query);
        }
        return listAfectacion;
    }


    @Override
    @Propagable(catched = true)
    public List<ConsultasRenuentes> searchAfectacionXAutoridadByBoId(String boId, String tipoPersona) {

        String query = "";
        if(tipoPersona != null){
            String nombre = tipoPersona.equals("F")? " CONCAT(CONCAT(per.nombre, CONCAT(' ',per.apellidopaterno)),CONCAT(' ', per.apellidomaterno)) AS nombre":" pm.compania as nombre ";
            String pesonaMorFis =  tipoPersona.equals("F")? " inner join RFCP_PERSONAFISICA per on per.idpersona = doc.boid": AfectacionXAutoridadSQL.CONSULTA_NOMBRE_PERSONA_MORAL;
            query = AfectacionXAutoridadSQL.OBTENER_NUMEROS_CONTROL +nombre+
                    " from sgtt_documento doc " +  AfectacionXAutoridadSQL.COMPLEMENT_BY_TIPO_PERSONA + pesonaMorFis +
                    " and doc.idtipopersona = '"+tipoPersona+"' and doc.BOID in ("+boId+")";
        }

       List<ConsultasRenuentes> listAfectacion = this.template.query(query, new ConsultasRenuentesMapper());
        if(listAfectacion== null || listAfectacion.isEmpty()){
            Logger.getLogger(AfectacionXAutoridadDAOImpl.class.getName()).log(Level.INFO, query);
        }
       return listAfectacion;
    }


    @Override
    @Propagable(catched = true)
    public List<ComboStr> obtenerBoId(String rfc, String tipoPersona){
        String query = "";
        if(tipoPersona.equals("F")){
            query =  "select A.idPersona as ID, B.RFC as DESCRIPCION from (select IDPERSONA from RFCH_IDENTIFICADOR where identificador like '" +rfc+ "%'"+
                        " union select IDPERSONA from RFCP_PERSONAFISICA where rfc like '" +rfc+"%') A"+
                        " inner join (select IDPERSONA, RFC from RFCP_PERSONAFISICA where rfc like '" +rfc+"%') B "+ "on (A.idPersona = B.idPersona)";
        }else{
            query =  "select A.idPersona  as ID, B.RFC as DESCRIPCION from(select IDPERSONA from RFCH_IDENTIFICADOR where identificador like '" +rfc+ "%'"+
                        " union select IDPERSONA from RFCP_PERSONAMORAL where rfc like '" +rfc+"%') A"+
                        " inner join (select IDPERSONA, RFC from RFCP_PERSONAMORAL where rfc like '" +rfc+"%') B "+ "on (A.idPersona = B.idPersona)";
        }

       List<ComboStr> boIds = this.template.query(query, new ComboMapperStr());
        if(boIds== null || boIds.isEmpty()){
            Logger.getLogger(AfectacionXAutoridadDAOImpl.class.getName()).log(Level.INFO, query);
        }
        return boIds;
    }

    /**
     * 
     * @param numeroControl
     * @param tipoPersona
     * @return 
     */
    @Override
    @Propagable(catched = true)
    public List<AfectacionXAutoridad> searchDocsAfectacionByNumeroControl(String numeroControl, String tipoPersona) {
        String query = "";
        List<AfectacionXAutoridad> listAfectacion = null;
        if(tipoPersona != null){
            String nombre = tipoPersona.equals("F")? ", CONCAT(CONCAT(per.nombre, CONCAT(' ',per.apellidopaterno)),CONCAT(' ', per.apellidomaterno)) AS nombre":", pm.compania as nombre ";
            String pesonaMorFis =  tipoPersona.equals("F")? " inner join RFCP_PERSONAFISICA per on per.idpersona = doc.boid": AfectacionXAutoridadSQL.CONSULTA_NOMBRE_PERSONA_MORAL;
            query = AfectacionXAutoridadSQL.CONSULTA_AFECTACION_BY_NUMCONTROL_INI +nombre+
                    " from SGTT_DetalleDoc detalle inner join SGTC_Obligacion obl on (numeroControl = '"+numeroControl+"' and detalle.idObligacion = obl.idObligacion and obl.fechaFin is null )" +
                    AfectacionXAutoridadSQL.CONSULTA_AFECTACION_BY_NUMCONTROL_MED+ pesonaMorFis+ "  order by obl.idObligacion";
             listAfectacion = this.template.query(query, new BusquedaDocumentosMapper());
        }

        if(listAfectacion== null || listAfectacion.isEmpty()){
            Logger.getLogger(AfectacionXAutoridadDAOImpl.class.getName()).log(Level.INFO, query);
        }

        return listAfectacion;
    }

    /**
     * 
     * @param numControl
     * @return 
     */
    @Propagable(catched = true)
    @Override
    public List<AfectacionXAutoridad>  searchDocsAfectacionNumControl(String numControl) {
        String tipoPersona = null;
        List<AfectacionXAutoridad> listaAfectaciones = null;
        SqlRowSet srs = template.queryForRowSet(AfectacionXAutoridadSQL.OBTENER_TIPO_PERSONA ,
                numControl);

        while (srs.next()) {
            tipoPersona = srs.getString("IDTIPOPERSONA");
            listaAfectaciones = searchDocsAfectacionByNumeroControl(numControl, tipoPersona);
        }

        return listaAfectaciones;
    }

    private String searchTipoPersonaByControl(String numControl) {
        String tipoPersona = null;
        SqlRowSet srs = template.queryForRowSet(AfectacionXAutoridadSQL.OBTENER_TIPO_PERSONA ,numControl);

        while (srs.next()) {
            tipoPersona = srs.getString("IDTIPOPERSONA");
        }
        return tipoPersona;
    }


    @Override
    @Propagable(catched = true)
    public List<AfectacionXAutoridad> searchDocsAfectacionByNumeroControlCancelacion(String numeroControl) {
        String tipoPersona= searchTipoPersonaByControl(numeroControl);
        String query = "";
        List<AfectacionXAutoridad> listAfectacion = null;
        if(tipoPersona != null){
            String nombre = tipoPersona.equals("F")? ", CONCAT(CONCAT(per.nombre, CONCAT(' ',per.apellidopaterno)),CONCAT(' ', per.apellidomaterno)) AS nombre":", pm.compania as nombre ";
            String pesonaMorFis =  tipoPersona.equals("F")? " inner join RFCP_PERSONAFISICA per on per.idpersona = doc.boid": AfectacionXAutoridadSQL.CONSULTA_NOMBRE_PERSONA_MORAL;
            query = AfectacionXAutoridadSQL.CONSULTA_AFECTACION_BY_NUMCONTROL_INI +nombre+
                    " from SGTT_DetalleDoc detalle inner join SGTC_Obligacion obl on (numeroControl = '"+numeroControl+"' and detalle.idObligacion = obl.idObligacion and obl.fechaFin is null )" +
                    AfectacionXAutoridadSQL.CONSULTA_AFECTACION_BY_NUMCONTROL_MED+pesonaMorFis + " and estado.idestadodocto IN (3,2,4,8,9,10,0) order by obl.idObligacion";
         listAfectacion = this.template.query(query, new BusquedaDocumentosMapper());
        }

        if(listAfectacion== null || listAfectacion.isEmpty()){
            Logger.getLogger(AfectacionXAutoridadDAOImpl.class.getName()).log(Level.INFO, query);
        }
        return listAfectacion;
    }


    @Override
    @Propagable(catched = true)
    public List<MultaDocumentoAfectaciones> obtenerMultasPorNumControl(String numControl)  {
        String query =  "  SELECT " +
                        "RES.NUMERORESOLUCION, " +
                        "MUL.NOMBREMULTACOB as TIPOMULTA, " +
                        "MUL.CONSTANTERESOLMOTIVO as IDTIPOMULTA, " +
                        "EDO.NOMBRE AS NOMBREESTADO " +
                        "FROM SGTT_RESOLUCIONDOC RES " +
                        "INNER JOIN SGTC_ESTADORESOL EDO ON RES.ULTIMOESTADO = EDO.IDESTADORESOLUCION " +
                        "INNER JOIN SGTC_MULTACOB MUL ON ( " +
                        "RES.NUMEROCONTROL = '"+numControl+"' AND mul.constanteresolmotivo IN ( " +
                        "'RESOLMOTIVO_INCUMPLIMIENTO', " +
                        "'RESOLMOTIVO_EXTEMPORANEIDAD', " +
                        "'RESOLMOTIVO_AMBOS', " +
                        "'RESOLMOTIVO_COMPLEMENTARIA') AND RES.CONSTANTERESOLMOTIVO = MUL.CONSTANTERESOLMOTIVO AND MUL.FECHAFIN IS NULL)";

        List<MultaDocumentoAfectaciones> multas = this.template.query(query, new ResolucionesDocumentosMapper());
        if(multas== null || multas.isEmpty()){
            Logger.getLogger(AfectacionXAutoridadDAOImpl.class.getName()).log(Level.INFO, query);
        }
        return multas;
    }

    /**
     * 
     * @param numRes
     * @param tipoMulta
     * @return 
     */
    @Override
    @Propagable(catched = true)
    public List<MultaDocumentoAfectaciones> obtenerMultasPorNumControlTipoMulta(String numRes, String tipoMulta) {
        StringBuilder query = new StringBuilder();
        query.append("select  mul.nombremultacob as TIPOMULTA, ric.numeroResolucion as NUMRESOLUCION, det.idobligacion as IDOBLIGACION,\n");
        query.append("per.descripcionperiodo as PERIODO,det.ejercicio as EJERCICIO,");
        query.append(" obl.descripcion as DESCOBLIGACION, mmo.monto as MONTO from  sgtt_ResolucionDoc mdo");
        query.append(" inner join SGTA_ResolICEP ric on (mdo.numeroResolucion = ric.numeroResolucion and ric.numeroresolucion = '").append(numRes).append("')\n");
        query.append(AfectacionXAutoridadSQL.OBTENER_MULTAS_POR_NUMCONTROL_TIPOMULTA);
       if (tipoMulta.equals("RESOLMOTIVO_INCUMPLIMIENTO") || tipoMulta.equals("RESOLMOTIVO_AMBOS")) {
            query.append(" (mmo.constanteResolMotivo in ('RESOLMOTIVO_INCUMPLIMIENTO','RESOLMOTIVO_AMBOS') and ((doc.fechaNotificacion) >= (mmo.fechaInicio))");
            query.append(" and ((doc.fechaNotificacion) <= (mmo.fechaFin) or mmo.fechaFin is null))");
        } else if (tipoMulta.equals("RESOLMOTIVO_EXTEMPORANEIDAD")) {
            query.append(" (mmo.constanteResolMotivo = 'RESOLMOTIVO_EXTEMPORANEIDAD' and ((det.fechaCumplimiento) >= (mmo.fechaInicio))");
            query.append(" and ((det.fechaCumplimiento) <= (mmo.fechaFin) or mmo.fechaFin is null))");
        } else {
            query.append(" (mmo.constanteResolMotivo = 'RESOLMOTIVO_COMPLEMENTARIA' and ((det.fechaPresentacionCompl) >= (mmo.fechaInicio))");
            query.append(" and ((det.fechaPresentacionCompl) <= (mmo.fechaFin) or mmo.fechaFin is null))");
        }
        List<MultaDocumentoAfectaciones> multas = this.template.query(query.toString(), new MultaDocumentoAfectacionesMapper());
        if (multas == null || multas.isEmpty()) {
            Logger.getLogger(AfectacionXAutoridadDAOImpl.class.getName()).log(Level.INFO, query);
        }
        return multas;
    }

    @Propagable(catched = true)
    @Override
    public List<RequerimientosAnteriores> origenMultaArcaPosteriores(String numControl) {

        String query = " SELECT doc.numerocontrol as NumeroControl, doc.fechanotificacion as FechaNotificacion, tipo.nombre as Nombre, \n" +
        "            doc.numerocontrolpadre as NumeroControlPadre, level\n" +
        "    from sgtt_documento doc, sgtt_vigilancia vig, sgtc_tipodocumento tipo  where vig.idvigilancia = doc.idvigilancia \n" +
        "    and tipo.idtipodocumento = vig.idtipodocumento and level > 1\n" +
        "    start with doc.numerocontrol = '"+numControl+"'  connect by doc.numerocontrolpadre = prior doc.numerocontrol order by level";
        List<RequerimientosAnteriores> reqsAnteriores = this.template.query(query,  new DocsAsociadosMapper());
        if(reqsAnteriores== null || reqsAnteriores.isEmpty()){
            Logger.getLogger(AfectacionXAutoridadDAOImpl.class.getName()).log(Level.INFO, query);
        }
        return reqsAnteriores;
    }

    @Propagable(catched = true)
    @Override
    public List<RequerimientosAnteriores> origenMultaArcaAnteriores(String numControl) {

        String query = "  SELECT\n" +
        "     doc.numerocontrol as NumeroControl, doc.fechanotificacion as FechaNotificacion, tipo.nombre as Nombre, \n" +
        "     doc.numerocontrolpadre as NumeroControlPadre, level\n" +
        "     from sgtt_documento doc, sgtt_vigilancia vig, sgtc_tipodocumento tipo \n" +
        "     where vig.idvigilancia = doc.idvigilancia \n" +
        "     and tipo.idtipodocumento = vig.idtipodocumento\n" +
        "     and level > 1\n" +
        "     start with doc.numerocontrol = '"+numControl+"'  \n" +
        "     connect by doc.numerocontrol = prior doc.numerocontrolpadre\n" +
        "     order by level";
        List<RequerimientosAnteriores> reqsAnteriores = this.template.query(query,  new DocsAsociadosMapper());
        if(reqsAnteriores== null || reqsAnteriores.isEmpty()){
            Logger.getLogger(AfectacionXAutoridadDAOImpl.class.getName()).log(Level.INFO, query);
        }
        return reqsAnteriores;
    }

    @Propagable(catched = true)
    @Override
    public List<ReporteAfectacionXAutoridadDTO> obtenerReporte(String nc) {
        List<ReporteAfectacionXAutoridadDTO> listReporteAfectacion;

        String query =  "SELECT doc.numerocontrol, estado.nombre AS estadoRequerimiento, to_char(doc.fecharegistro, 'DD/MM/YYYY') as fecharegistro, " +
                        " to_char(doc.FECHANOTRABAJADO, 'DD/MM/YYYY') AS FECHANOTRABAJADO,\n" + 
                        " to_char(doc.FECHANOLOCALIZADOCONTRIBUYENTE, 'DD/MM/YYYY') AS FECHANOLOCALIZADOCONTRIBUYENTE,\n" + 
                        " to_char(doc.fechanotificacion, 'DD/MM/YYYY') as fechanotificacion, to_char(doc.fechavencimientodocto, 'DD/MM/YYYY') AS fechavencimiento, " +
                        " obl.idobligacion   AS claveobligacion, " +
                        " obl.descripcion, " +
                        " detalle.ejercicio, " +
                        " per.descripcionperiodo, " +
                        " tipo.nombre AS tipodocumento, " +
                        " icep.nombre AS situacionicep "+
                        " from SGTT_DetalleDoc detalle " +
                        " inner join SGTC_Obligacion obl on (numeroControl = '"+nc+"' and detalle.idObligacion = obl.idObligacion and obl.fechaFin is null) " +
                        AfectacionXAutoridadSQL.OBTENER_REPORTE;
        listReporteAfectacion = template.query(query, new ConsultaReporteMapper());
        if(listReporteAfectacion== null || listReporteAfectacion.isEmpty()){
            Logger.getLogger(AfectacionXAutoridadDAOImpl.class.getName()).log(Level.INFO, query);
        }

        return listReporteAfectacion;
    }

}
