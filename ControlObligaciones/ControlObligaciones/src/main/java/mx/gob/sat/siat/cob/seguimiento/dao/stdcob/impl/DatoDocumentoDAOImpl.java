package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DatoDocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.DatoDocumentoMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.DatoDocumentoSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DatoDocumento;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author root
 */
@Repository
public class DatoDocumentoDAOImpl implements DatoDocumentoDAO {

    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    /**
     * @param datoDocumento
     */
    @Override
    @Propagable
    public void insert(DatoDocumento datoDocumento)  {
            String query =
                    DatoDocumentoSQL.INSERT_DATO_DOCUMENTO + datoDocumento.getNumeroControl() + ", " + datoDocumento.getNombreALSC()
                    + ", " + datoDocumento.getRfc() + ", " + datoDocumento.getCurp() + ", "
                    + datoDocumento.getNombreContribuyente() + ", " + datoDocumento.getDomicilioContribuyente() + ", "
                    + datoDocumento.getIdVigilancia() + ", " + datoDocumento.getFraccionALSC() + ", "
                    + datoDocumento.getLocalidadALSC() + ", " + datoDocumento.getNombreAdminALSC() + ", "
                    + datoDocumento.getIdEstadoGeneracion() + ", " + datoDocumento.getDomicilioALSC() + ")";
            template.update(query);
    }

    /**
     */
    @Override
    @Propagable
    public List<DatoDocumento> consultaDatoDocumento()  {
            String query = DatoDocumentoSQL.SELECT_DATO_DOCUMENTO;
            return  this.template.query(query, new DatoDocumentoMapper());
    }

    /**
     */
    @Override
    @Propagable
    public List<DatoDocumento> consultaDatoDocumento(int idVigilancia, int idEtapaVigilancia) {
            String query = DatoDocumentoSQL.SELECT_DATO_DOCUMENTO;
            query += "WHERE IDVIGILANCIA = \'" + idVigilancia + "\' ";
            query += "AND IDETAPAVIGILANCIA = \'" + idEtapaVigilancia + "\'";
            return this.template.query(query, new DatoDocumentoMapper());
    }

    /**
     * int actualizaEstadoDocumento Actualiza el estado del documento mediante
     * el numero de control
     *
     * @param idEstadoGeneracion
     * @param numeroControl
     * @return
     */
    @Override
    @Propagable
    public Integer actualizaEstadoDocumento(int idEstadoGeneracion, long numeroControl) {
        StringBuilder sql = new StringBuilder();
        sql.append("update sgtt_datodocumento set idestadogeneracion = ");
        sql.append(idEstadoGeneracion);
        sql.append("where numerocontrol = ");
        sql.append(numeroControl);
    
        return template.update(sql.toString());
    }
}
