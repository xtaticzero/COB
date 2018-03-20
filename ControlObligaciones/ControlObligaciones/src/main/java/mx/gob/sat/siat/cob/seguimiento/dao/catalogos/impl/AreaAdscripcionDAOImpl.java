package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.impl;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.AreaAdscripcionDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper.AreaAdscripcionMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.sql.AreaAdscripcionSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.AreaAdscripcion;
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
public class AreaAdscripcionDAOImpl implements AreaAdscripcionDAO{
    
    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    /**
     *
     * @return
     */
    @Override
    public List<AreaAdscripcion> todasLasAreas() {
        List<AreaAdscripcion> listAreaAdscripcion;
        listAreaAdscripcion = template.query(AreaAdscripcionSQL.OBTEN_TODAS_AREAS, new AreaAdscripcionMapper());
        if(listAreaAdscripcion == null || listAreaAdscripcion.isEmpty()){
        Logger.getLogger(AreaAdscripcionDAOImpl.class.getName()).log(Level.INFO, AreaAdscripcionSQL.OBTEN_TODAS_AREAS);   
        }
        return listAreaAdscripcion;
    }

    /**
     *
     * @param areaAdscripcion
     *
     */
    @Override
    @Propagable
    public void agregaArea(AreaAdscripcion areaAdscripcion) {
        int resultado = template.update(AreaAdscripcionSQL.AGREGA_AREA, areaAdscripcion.getNombre(),
                areaAdscripcion.getDescripcion(), areaAdscripcion.getFechaInicio(), areaAdscripcion.getFechaFin(),
                areaAdscripcion.getOrden());
        if(resultado == -1){
            Logger.getLogger(AreaAdscripcionDAOImpl.class.getName()).log(Level.INFO, AreaAdscripcionSQL.AGREGA_AREA);    
        }
    }

    /**
     *
     * @param areaAdscripcion
     *
     */
    @Override
    @Propagable
    public void editaArea(AreaAdscripcion areaAdscripcion) {
        int resultado = template.update(AreaAdscripcionSQL.EDITA_AREA, areaAdscripcion.getNombre(),
                areaAdscripcion.getDescripcion(), areaAdscripcion.getIdAreaAdscripcion());
        if(resultado == -1){
            Logger.getLogger(AreaAdscripcionDAOImpl.class.getName()).log(Level.INFO, AreaAdscripcionSQL.EDITA_AREA);    
        }
    }

    /**
     *
     * @param areaAdscripcion
     *
     */
    @Override
    @Propagable
    public void reactivaArea(AreaAdscripcion areaAdscripcion) {
        int resultado = template.update(AreaAdscripcionSQL.REACTIVA_AREA,
                areaAdscripcion.getIdAreaAdscripcion());
        if(resultado == -1){
            Logger.getLogger(AreaAdscripcionDAOImpl.class.getName()).log(Level.INFO, AreaAdscripcionSQL.REACTIVA_AREA);    
        }
    }

    /**
     *
     * @param areaAdscripcion
     * @return
     *
     */
    @Override
    @Propagable
    public Integer eliminaArea(AreaAdscripcion areaAdscripcion) {
        Integer reg;
        reg = template.update(AreaAdscripcionSQL.ELIMINA_AREA, areaAdscripcion.getFechaFin(),
                areaAdscripcion.getIdAreaAdscripcion());
        if(reg == -1){
            Logger.getLogger(AreaAdscripcionDAOImpl.class.getName()).log(Level.INFO, AreaAdscripcionSQL.ELIMINA_AREA);    
        }
        return reg;
    }

    /**
     *
     * @param areaAdscripcion
     * @return
     */
    @Override
    public Integer buscarAreaPorId(AreaAdscripcion areaAdscripcion) {
        Integer reg = null;
        SqlRowSet srs;

        srs = template.queryForRowSet(AreaAdscripcionSQL.BUSCA_AREA_POR_ID,
                areaAdscripcion.getIdAreaAdscripcion());

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
