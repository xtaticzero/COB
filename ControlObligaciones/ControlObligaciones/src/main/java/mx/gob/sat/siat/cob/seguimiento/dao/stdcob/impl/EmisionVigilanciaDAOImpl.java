package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.EmisionVigilanciaDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.EmisionVigilanciaMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.EmisionVigilanciaSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.EmisionVigilancia;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EmisionVigilanciaDAOImpl implements EmisionVigilanciaDAO {

    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    /**
     * @param emisionVigilancia
     */
    @Override
    @Propagable
    public void insert(EmisionVigilancia emisionVigilancia) {
        String query
                = EmisionVigilanciaSQL.INSERT_EMISION_VIGILANCIA + emisionVigilancia.getIdVigilancia() + ", "
                + emisionVigilancia.getIdTipoDocumento() + ", " + emisionVigilancia.getIdEtapaVigilancia() + ", "
                + emisionVigilancia.getEsPlantillaDIOT() + ", " + emisionVigilancia.getIdEstadoEmision() + ")";
        template.update(query);
    }

    /**
     */
    @Override
    @Propagable
    public List<EmisionVigilancia> consultaEmisionVigilancia() {
        String query = EmisionVigilanciaSQL.SELECT_EMISION_VIGILANCIA;
        return this.template.query(query, new EmisionVigilanciaMapper());
    }

    /**
     */
    @Override
    @Propagable
    public List<EmisionVigilancia> emisionVigXEdoEmision() {
        String query = EmisionVigilanciaSQL.SELECT_EMISION_VIG_X_EDO_EMISION;
        return this.template.query(query, new EmisionVigilanciaMapper());
    }

    /**
     * @param idestadoemision
     * @param idvigilancia
     * @param idetapavigilancia
     * @param idtipodocumento
     * @return
     */
    @Override
    @Propagable
    public Integer actualizaEstadoEmision(int idestadoemision, long idvigilancia, long idetapavigilancia,
            int idtipodocumento) {
        StringBuilder sql = new StringBuilder();
        sql.append("update sgtt_emisionvig set idestadoemision = ").
                append(idestadoemision).
                append(" where idvigilancia = ").
                append(idvigilancia).
                append(" and idetapavigilancia = ").
                append(idetapavigilancia).
                append(" and idtipodocumento = ").
                append(idtipodocumento);
        return template.update(sql.toString());
    }
}
