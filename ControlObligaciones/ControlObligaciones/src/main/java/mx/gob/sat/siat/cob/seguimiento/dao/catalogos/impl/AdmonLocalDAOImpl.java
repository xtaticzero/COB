package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.impl;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.AdmonLocalDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper.AdmonLocalMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.sql.AdmonLocalSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.AdmonLocal;
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
public class AdmonLocalDAOImpl implements AdmonLocalDAO{
    
    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    /**
     *
     * @return
     */
    @Override
    public List<AdmonLocal> todasLasAdmon() {
        List<AdmonLocal> listAdmonLocal;
        listAdmonLocal = template.query(AdmonLocalSQL.OBTEN_TODAS_ADMON, new AdmonLocalMapper());
        if(listAdmonLocal == null || listAdmonLocal.isEmpty()){
        Logger.getLogger(AdmonLocalDAOImpl.class.getName()).log(Level.INFO, AdmonLocalSQL.OBTEN_TODAS_ADMON);   
        }
        return listAdmonLocal;
    }

    /**
     *
     * @param admonLocal
     *
     */
    @Override
    @Propagable
    public void agregaAdmon(AdmonLocal admonLocal) {
        int resultado = template.update(AdmonLocalSQL.AGREGA_ADMON, admonLocal.getIdAdmonLocal(), admonLocal.getNombre(),
                admonLocal.getDescripcion(), admonLocal.getFechaInicio(), admonLocal.getFechaFin(),
                admonLocal.getOrden());
        if(resultado == -1){
            Logger.getLogger(AdmonLocalDAOImpl.class.getName()).log(Level.INFO, AdmonLocalSQL.AGREGA_ADMON);    
        }
    }

    /**
     *
     * @param admonLocal
     *
     */
    @Override
    @Propagable
    public void editaAdmon(AdmonLocal admonLocal) {
        int resultado = template.update(AdmonLocalSQL.EDITA_ADMON, admonLocal.getNombre(),
                admonLocal.getDescripcion(), admonLocal.getIdAdmonLocal());
        if(resultado == -1){
            Logger.getLogger(AdmonLocalDAOImpl.class.getName()).log(Level.INFO, AdmonLocalSQL.EDITA_ADMON);    
        }
    }

    /**
     *
     * @param admonLocal
     *
     */
    @Override
    @Propagable
    public void reactivaAdmon(AdmonLocal admonLocal) {
        int resultado = template.update(AdmonLocalSQL.REACTIVA_ADMON,
                admonLocal.getIdAdmonLocal());
        if(resultado == -1){
            Logger.getLogger(AdmonLocalDAOImpl.class.getName()).log(Level.INFO, AdmonLocalSQL.REACTIVA_ADMON);    
        }
    }

    /**
     *
     * @param admonLocal
     * @return
     *
     */
    @Override
    @Propagable
    public Integer eliminaAdmon(AdmonLocal admonLocal) {
        Integer reg;
        reg = template.update(AdmonLocalSQL.ELIMINA_ADMON, admonLocal.getFechaFin(),
                admonLocal.getIdAdmonLocal());
        if(reg == -1){
            Logger.getLogger(AdmonLocalDAOImpl.class.getName()).log(Level.INFO, AdmonLocalSQL.ELIMINA_ADMON);    
        }
        return reg;
    }

    /**
     *
     * @param admonLocal
     * @return
     */
    @Override
    public Integer buscarAdmonPorId(AdmonLocal admonLocal) {
        Integer reg = null;
        SqlRowSet srs;

        srs = template.queryForRowSet(AdmonLocalSQL.BUSCA_ADMON_POR_ID,
                admonLocal.getIdAdmonLocal());

        while (srs.next()) {
            reg = srs.getInt("REGISTROS");
        }
     
        return reg;
    }

    /**
     *
     * @param template
     */
    public void setJdbcTemplate(JdbcTemplate template) {
        this.template = template;
    }

    /**
     *
     * @return
     */
    public JdbcTemplate getJdbcTemplate() {
        return template;
    }
    
}