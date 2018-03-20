/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.sql.Types;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.IcepsAprobarDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.IcepsAprobarMapper;
import mx.gob.sat.siat.cob.seguimiento.dto.db2.Paginador;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.IcepsAprobar;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.SituacionIcepEnum;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
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
public class IcepsAprobarDAOImpl implements IcepsAprobarDAO {

    @Autowired
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    @Override
    @Propagable(catched = true)
    public List<IcepsAprobar> listarIcepsPorPagina(String idLocalidad, String numeroCarga, Paginador paginador) {
        if (idLocalidad == null) {
            return template.query(
                    ICEPS_POR_PAGINA.replace(FRAGMENTO_LOCAL, ""),
                    new Object[]{numeroCarga,
                        paginador.getRowInicial(),
                        paginador.getRowFinal()},
                    new int[]{Types.NUMERIC,
                        Types.NUMERIC,
                        Types.NUMERIC},
                    new IcepsAprobarMapper());
        } else {
            return template.query(
                    ICEPS_POR_PAGINA.replace(FRAGMENTO_LOCAL, LOCAL),
                    new Object[]{numeroCarga,
                        idLocalidad,
                        paginador.getRowInicial(),
                        paginador.getRowFinal()},
                    new int[]{Types.NUMERIC,
                        Types.VARCHAR,
                        Types.NUMERIC,
                        Types.NUMERIC},
                    new IcepsAprobarMapper());
        }
    }

    @Override
    @Propagable(catched = true)
    public Long contarIceps(String idLocalidad, String numeroCarga) {
        if (idLocalidad == null) {
            return template.queryForObject(
                    CONTAR_ICEPS.replace(FRAGMENTO_LOCAL, ""),
                    new Object[]{numeroCarga},
                    new int[]{Types.NUMERIC},
                    Long.class);
        } else {
            return template.queryForObject(
                    CONTAR_ICEPS.replace(FRAGMENTO_LOCAL, LOCAL),
                    new Object[]{numeroCarga, idLocalidad},
                    new int[]{Types.NUMERIC, Types.VARCHAR},
                    Long.class);
        }
    }

    /**
     * aqui se hara camnio en la actualizacion del detalle
     */
    @Override
    @Propagable(catched = true)
    public Integer realizarCumplimientoIcep(Documento documento) {
        int i = 0;
        for (DetalleDocumento det : documento.getDetalles()) {
            i += template.update(ACTUALIZAR_ICEPS,
                    new Object[]{det.getFechaCumplimiento(),
                        det.getSituacionIcep().getValor(),
                        det.getImporteCargo(),
                        det.getIdTipoDeclaracion(),
                        (det.getIdCumplimiento()==null?null:det.getIdCumplimiento().toString()),
                        det.getEstadoIcepEC(),
                        documento.getNumeroControl(),
                        det.getClaveIcep()}
                    );
        }
        return i;
    }

    @Override
    @Propagable(catched = true)
    public Integer actualizarEstadoIcep(Documento documento,
            SituacionIcepEnum situacion) {
        int i = 0;
        switch (documento.getIndicadorCumplimiento()) {
            case TOTAL:
                String query = ACTUALIZAR_ICEPS_PADRON.replace(PARAMETRO_ICEPS, "");
                i += template.update(query,
                        new Object[]{situacion.getValor(),
                            documento.getNumeroControl()},
                        new int[]{Types.NUMERIC,
                            Types.VARCHAR});
                break;
            case PARCIAL:
                Set<String> claves = new HashSet<String>();
                for (DetalleDocumento det : documento.getDetalles()) {
                    claves.add(det.getClaveIcep() + "");
                }
                String update = ACTUALIZAR_ICEPS_PADRON.replace(
                        PARAMETRO_ICEPS, " and "
                        + Utilerias.formatearParaSQLInNumeric(claves, "claveicep"));
                i += template.update(update,
                        new Object[]{situacion.getValor(),
                            documento.getNumeroControl()},
                        new int[]{Types.NUMERIC,
                            Types.VARCHAR});
        }
        return i;
    }

}
