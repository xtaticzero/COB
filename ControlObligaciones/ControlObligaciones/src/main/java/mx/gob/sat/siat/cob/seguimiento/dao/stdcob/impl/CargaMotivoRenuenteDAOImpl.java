/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.sql.Types;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.CargaMotivoRenuenteDAO;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.CargaMotivoRenuenteSQL.AGREGA_CARGA_DOC_RENUENTE;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.CargaMotivoRenuente;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author juan
 */
@Repository
public class CargaMotivoRenuenteDAOImpl implements CargaMotivoRenuenteDAO {

    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    @Override
    @Propagable
    public void agregaMotivoRenuente(CargaMotivoRenuente renuente) {
        template.update(AGREGA_CARGA_DOC_RENUENTE,
                new Object[]{
                    renuente.getIdCargaRenuente(),
                    renuente.getIdMotivoNoRenuente(),
                    renuente.getLineaArchivo()
                }, new int[]{Types.NUMERIC, Types.NUMERIC, Types.VARCHAR});

    }

}
