package mx.gob.sat.siat.cob.seguimiento.dao.arca.impl;


import java.sql.Types;
import java.util.List;


import mx.gob.sat.siat.cob.seguimiento.dao.arca.PlantillaArcaDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.mapper.DocumentoPlantillaMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.sql.PlantillaArcaSQL;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.ArchivoPlantillaArcaMapper;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PlantillaArcaDAOImpl implements PlantillaArcaDAO {

    private final Logger log = Logger.getLogger(PlantillaArcaDAOImpl.class);
    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_ARCA)
    private JdbcTemplate template;

    @Propagable
    @Override
    public List<CatalogoBase> buscarPlantillasDBArca() {
        StringBuilder sql = new StringBuilder();
        sql.append(PlantillaArcaSQL.BUSCAR_PLANTILLAS_DB_ARCA);
        log.debug(sql.toString());

        return template.query(sql.toString(), new DocumentoPlantillaMapper());
    }

    @Override
    @Propagable(catched = true)
    public List<byte[]> buscarArchivoPlantilla(int idPlantilla) {
        return template.query(PlantillaArcaSQL.OBTENER_DOCUMENTO,
                new Object[]{idPlantilla},
                new int[]{Types.NUMERIC},
                new ArchivoPlantillaArcaMapper());
    }

}
