package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper.ComboMapperStr;
import mx.gob.sat.siat.cob.seguimiento.dao.cobranza.sql.CobranzaSQL;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.MultaCobDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.ComboStr;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MultaCobDAOImpl implements MultaCobDAO,CobranzaSQL {
       
       @Autowired
       @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
       private JdbcTemplate template;
       
          
       @Override
       public List<ComboStr> buscarTiposMulta() {
           return  template.query(LISTA_COMBO_RESOL_MOTIVO, new ComboMapperStr());
       }
}
