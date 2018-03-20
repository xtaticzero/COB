package mx.gob.sat.siat.cob.seguimiento.dao.compartidos.impl;

import java.util.Collections;
import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.compartidos.CatalogosDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.compartidos.mapper.CatMapper;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.CampoEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.CatalogoEnum;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CatalogosDAOImpl implements CatalogosDAO {

    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    @Override
    @Propagable
    public List consultar(String aliasTabla) {
        CatalogoEnum catalogo = Utilerias.obtenerPorAlias(aliasTabla);
        StringBuilder query = new StringBuilder();
        query.append("SELECT ");
        if (catalogo.getCurrentTablaID().equals(CampoEnum.ID_OBLIGACION.getNombre())) {
            query.append("/*+index_ss(SGTC_OBLIGACION) */ ");
        }
        query.append(catalogo.getCurrentCampoID());
        if (aliasTabla.equals(CatalogoEnum.MOTIVORENUENTE.getAlias())) {
            query.append(" AS ID,").append(CampoEnum.ID_NOMBRE_FIELD.getNombre());
        } else {
            query.append(" AS ID,").append(catalogo.getCurrentDescripcionID()).
                    append(" AS NOMBRE ");
        }
        if (catalogo.getCurrentTablaID().equals(CampoEnum.CARGOADMN_TABLE.getNombre())) {
            query.append(", ").append(catalogo.getCurrentCampoAuxID()).append(" as IDAUX ");
        }
        query.append(" FROM ").append(catalogo.getCurrentTablaID());
        if (!catalogo.getCurrentTablaID().equals(CampoEnum.REGIMEN_TABLE.getNombre())) {
            query.append(" WHERE FECHAFIN IS NULL").append(catalogo.getCurrentWhereData());
        }
        if (catalogo.getCurrentDataSourceID().equals(CampoEnum.ID_DATASOURCE_SGT.getNombre())) {
            if (catalogo.getCurrentTablaID().equals(CampoEnum.ID_EJERCICIO_FISCAL.getNombre())) {
                query.append(" ORDER BY IDEJERCICIOFISCAL");
            } else if (catalogo.getCurrentTablaID().equals(CampoEnum.DESCRIPCION_TABLE.getNombre())) {
                query.append(" ORDER BY IDDESCRIPCION desc");
            } else if (!(catalogo.getCurrentTablaID().equals(CampoEnum.ID_TIPO_FIRMA.getNombre())
                    || catalogo.getCurrentTablaID().equals(CampoEnum.REGIMEN_TABLE.getNombre()))) {
                query.append(" ORDER BY ORDEN");
            }
            return this.template.query(query.toString(), new CatMapper(catalogo));
        }
        return Collections.emptyList();
    }
}
