package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.sql.Types;

import java.util.List;


import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.PlantillaSGTDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.BitacoraErroresMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.PlantillaArcaMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.PlantillaMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.PlantillaSingleMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.PlantillaArcaSQL;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.SQLOracleSeguimiento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.BitacoraErrores;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.PlantillaDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EsMultaEnum;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PlantillaDAOImpl implements PlantillaSGTDAO, SQLOracleSeguimiento {

    private static final String RUTA_ARCHIVO = "/siat/cob/plantillas/";
    private static Logger logger = Logger.getLogger(PlantillaDAOImpl.class.getName());
    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    @Override
    @Propagable
    public int guardarPlantilla(PlantillaDocumento plantillaDocumento) {
        int idPlantillaMax;
        int esPlantillaDIOT = plantillaDocumento.isEsPlantilla() ? 1 : 0;
        StringBuilder sql = new StringBuilder();
        StringBuilder sqlUpdate = new StringBuilder();
        String date = "sysdate";
        String nullValue = "null";
        List<PlantillaDocumento> plantillasExistentes = this.buscarPlantillasDocumentoExistente(plantillaDocumento);

        if (plantillasExistentes != null && plantillasExistentes.size() > 0) {
            sqlUpdate.append("update sgtt_plantilla set fechafin=sysdate where " + "idplantilla='").
                    append(plantillasExistentes.get(0).getIdPlantilla()).append("'");
            logger.debug(sqlUpdate.toString());
            template.update(sqlUpdate.toString());
        }
        idPlantillaMax = this.buscarMaximoIdPlantilla(plantillaDocumento);
        String nombreArchivo = FilenameUtils.removeExtension(plantillaDocumento.getFile());
        String extensionArchivo = FilenameUtils.getExtension(plantillaDocumento.getFile());
        String rutaArchivo = RUTA_ARCHIVO + nombreArchivo + "-" + idPlantillaMax + "." + extensionArchivo;
        logger.debug("idPlantillaMax:" + idPlantillaMax);

        sql.append("INSERT INTO SGTT_PLANTILLA "
                + "(IDPLANTILLA,"
                + "FECHAINICIO,"
                + "FECHAFIN,"
                + "DESCRIPCION,"
                + "RUTAARCHIVO,"
                + "ESPLANTILLADIOT,"
                + "IDTIPODOCUMENTO,"
                + "IDETAPAVIGILANCIA)"
                + " VALUES (  SGTQ_PLANTILLAS.NEXTVAL,").append(date).
                append(",").append(nullValue).
                append(",'").append(plantillaDocumento.getDescripcion()).
                append("','").append(rutaArchivo).
                append("',").append(esPlantillaDIOT).
                append(",'").append(plantillaDocumento.getTipoDocumento()).
                append("','").
                append(plantillaDocumento.getEtapaVigilancia()).
                append("'" + ")");
        logger.debug(sql.toString());
        template.update(sql.toString());

        return idPlantillaMax;
    }

    /**
     * String buscarPlantilla Busca una plantilla mediante idVigilancia,
     * idTipoDocumento y que tenga la fechafinal en nulo
     *
     * @param idVigilancia
     * @param idTipoDocumento
     * @return
     */
    @Override
    @Propagable
    public String buscarPlantilla(long idVigilancia, int idTipoDocumento) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from sgtt_plantilla where idetapavigilancia = ").
                append(idVigilancia).
                append(" and idtipodocumento = ").
                append(idTipoDocumento).
                append(" and fechafin is null");

        logger.info(sql.toString());
        String resultado = (String) template.queryForObject(sql.toString(), String.class);

        return resultado;
    }

    @Propagable
    public int buscarMaximoIdPlantilla(PlantillaDocumento plantilla) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM sgtt_plantilla WHERE idplantilla=(SELECT max(idplantilla) FROM sgtt_plantilla)");
        List<PlantillaDocumento> pl = template.query(sql.toString(), new PlantillaSingleMapper());
        int maxId = pl.get(0).getIdPlantilla();

        return maxId + 1;
    }

    //@Override
    @Propagable
    public List<PlantillaDocumento> buscarPlantillasDocumentoExistente(PlantillaDocumento plantilla) {
        StringBuilder sql = new StringBuilder();
        int esPlantillaDIOT = plantilla.isEsPlantilla() ? 1 : 0;
        sql.append("select * from sgtt_plantilla " + "where idtipodocumento='").
                append(plantilla.getTipoDocumento()).
                append("' " + "and idetapavigilancia='").
                append(plantilla.getEtapaVigilancia()).
                append("' " + "and esplantilladiot='").
                append(esPlantillaDIOT).
                append("' order by idplantilla desc");

        return template.query(sql.toString(), new PlantillaSingleMapper());
    }

    @Override
    @Propagable
    public List<PlantillaDocumento> buscarPlantillasDocumento() {

        StringBuilder sql = new StringBuilder();
        sql.append(SQLOracleSeguimiento.BUSCAR_REGISTROS_PLANTILLA);
        return template.query(sql.toString(), new PlantillaMapper());

    }

    @Override
    @Propagable
    public List<PlantillaDocumento> buscarPlantillasArca() {

        StringBuilder sql = new StringBuilder();
        sql.append(SQLOracleSeguimiento.BUSCAR_REGISTROS_PLANTILLA_ARCA_COB);
        logger.debug(sql.toString());
        return template.query(sql.toString(), new PlantillaArcaMapper());
    }

    @Override
    @Propagable
    public void guardarPlantillaArca(PlantillaDocumento plantillaDocumentoArca) {

        template.update(SQLOracleSeguimiento.INSERTAR_REGISTRO_PLANTILLA_ARCA,
                new Object[]{plantillaDocumentoArca.getIdPlantillaArca(),
            plantillaDocumentoArca.getDescripcion(),
            plantillaDocumentoArca.getBlnPlantillaDIOT() != null ? plantillaDocumentoArca.getBlnPlantillaDIOT() : 0,
            plantillaDocumentoArca.getIdTipoDocumento(),
            plantillaDocumentoArca.getIdEtapaVigilancia(),
            plantillaDocumentoArca.getIdMedioEnvio(),
            plantillaDocumentoArca.getIdTipoFirma(),
            plantillaDocumentoArca.getIdTipoMotivo(),
            plantillaDocumentoArca.getIdTipoDias(),
            plantillaDocumentoArca.getNivelEmision(),
            plantillaDocumentoArca.getIdCargoAdministrativo()},
                new int[]{Types.INTEGER,
            Types.VARCHAR,
            Types.INTEGER,
            Types.INTEGER,
            Types.INTEGER,
            Types.INTEGER,
            Types.INTEGER,
            Types.VARCHAR,
            Types.INTEGER,
            Types.INTEGER,
            Types.INTEGER});

    }

    @Override
    @Propagable
    public PlantillaDocumento buscarPlantillaArcaPorParametros(PlantillaDocumento p, String tipo) {
        StringBuilder sql = new StringBuilder();

        if (tipo.equals("requerimiento")) {
            sql.append(SQLOracleSeguimiento.BUSCAR_PLANTILLA_REQARCA_POR_PARAMETROS
                    .replace("{0}", p.getIdTipoDocumento() + "")
                    .replace("{1}", p.getIdEtapaVigilancia() + "")
                    .replace("{2}", p.getBlnPlantillaDIOT() + "")
                    .replace("{3}", p.getIdMedioEnvio() + "")
                    .replace("{4}", p.getIdTipoFirma() + "")
                    .replace("{5}", p.getIdModalidadDocumento() + ""));
        }
        if (tipo.equals("liquidacion")) {
            sql.append(SQLOracleSeguimiento.BUSCAR_PLANTILLA_LIQ_ARCA_POR_PARAMETROS
                    .replace("{0}", p.getIdTipoDocumento() + "")
                    .replace("{1}", p.getIdEtapaVigilancia() + "")
                    .replace("{2}", p.getBlnPlantillaDIOT() + "")
                    .replace("{3}", p.getIdMedioEnvio() + "")
                    .replace("{4}", p.getIdTipoFirma() + "")
                    .replace("{5}", p.getIdModalidadDocumento() + "")
                    .replace("{6}", p.getIdTipoDias() + ""));
        }
        if (tipo.equals("multa")) {
            sql.append(SQLOracleSeguimiento.BUSCAR_PLANTILLA_MULTA_ARCA_POR_PARAMETROS
                    .replace("{0}", p.getIdTipoDocumento() + "")
                    .replace("{1}", p.getIdEtapaVigilancia() + "")
                    .replace("{2}", p.getBlnPlantillaDIOT() + "")
                    .replace("{3}", p.getIdMedioEnvio() + "")
                    .replace("{4}", p.getIdTipoFirma() + "")
                    .replace("{5}", p.getIdModalidadDocumento() + "")
                    .replace("{6}", p.getIdTipoMotivo()));
        }
        PlantillaDocumento plantilla = (PlantillaDocumento) template.query(sql.toString(), new PlantillaArcaMapper());

        return plantilla;
    }

    @Override
    @Propagable
    public PlantillaDocumento buscarPlantillaArcaPorParametros(PlantillaDocumento plantilla) {
        List<PlantillaDocumento> plantillaDocumento;
        plantillaDocumento = template.query(SQLOracleSeguimiento.BUSCAR_REGISTRO_PLANTILLA_ARCA_INDIVIDUAL,
                new Object[]{plantilla.getIdTipoDocumento(),
            plantilla.getNivelEmision(),
            plantilla.getIdCargoAdministrativo(),
            plantilla.getIdEtapaVigilancia(),
            plantilla.getIdTipoFirma(),
            plantilla.getIdMedioEnvio(),
            plantilla.getBlnPlantillaDIOT() != null ? plantilla.getBlnPlantillaDIOT() : 0,
            plantilla.getIdTipoMotivo(),
            plantilla.getIdTipoDias()},
                new int[]{Types.INTEGER,
            Types.INTEGER,
            Types.INTEGER,
            Types.INTEGER,
            Types.INTEGER,
            Types.INTEGER,
            Types.INTEGER,
            Types.VARCHAR,
            Types.INTEGER},
                new PlantillaArcaMapper());
        if (plantillaDocumento.size() > 0) {
            return plantillaDocumento.get(0);
        }
        return null;
    }

    @Override
    @Propagable
    public void eliminarPlantillasRepetidas(PlantillaDocumento plantilla) {
        template.update(SQLOracleSeguimiento.ELIMINAR_PLANTILLAS_REPETIDAS,
                new Object[]{plantilla.getIdTipoDocumento(),
            plantilla.getNivelEmision(),
            plantilla.getIdCargoAdministrativo(),
            plantilla.getIdEtapaVigilancia(),
            plantilla.getIdTipoFirma(),
            plantilla.getIdMedioEnvio(),
            plantilla.getBlnPlantillaDIOT() != null ? plantilla.getBlnPlantillaDIOT() : 0,
            plantilla.getIdTipoMotivo(),
            plantilla.getIdTipoDias()},
                new int[]{Types.INTEGER,
            Types.INTEGER,
            Types.INTEGER,
            Types.INTEGER,
            Types.INTEGER,
            Types.INTEGER,
            Types.INTEGER,
            Types.VARCHAR,
            Types.INTEGER});
    }

    @Override
    @Propagable
    public void actualizarEstadoPlantillaArca(int idPlantilla) {
        StringBuilder sql = new StringBuilder();
        sql.append(SQLOracleSeguimiento.ACTUALIZAR_ESTADO_PLANTILLA_ARCA.replace("{0}", idPlantilla + ""));
        logger.debug(sql);
        template.update(sql.toString());

    }

    @Override
    @Propagable
    public List<BitacoraErrores> buscarBitacoraErroresPorIdVigilancia(Integer idVigilancia, String fechaInicio, String fechaFin) {
        StringBuilder sql = new StringBuilder();

        sql.append("select dt.idvigilancia,dsc.descripcion,dt.nombrearchivo,dt.rutabitacoraerrores,vig.fechacargaarchivos from sgtt_detallecarga dt inner join sgtt_vigilancia "
                + "vig on dt.idvigilancia=vig.idvigilancia"
                + " inner join SGTC_DscripcionVig dsc on dsc.iddescripcion=vig.iddescripcion ");
        if (idVigilancia != null) {
            sql.append(" where dt.idvigilancia=").append(idVigilancia);
        }

        if (!fechaInicio.isEmpty() && !fechaFin.isEmpty()) {
            sql.append(" where vig.fechacargaarchivos >= to_date('").append(fechaInicio).
                    append(" 00:00:00','YYYY/MM/DD HH24:MI:SS.SSSSS') and vig.fechacargaarchivos < to_date('").append(fechaFin).append(" 23:59:59','YYYY/MM/DD HH24:MI:SS.SSSSS')");
        }

        sql.append(" and dt.rutabitacoraerrores is not null");
        sql.append(" order by  dt.idvigilancia desc ");
        logger.debug("sql Buscar Bitacora:" + sql.toString());
        return template.query(sql.toString(), new BitacoraErroresMapper());

    }
    
    @Override
    @Propagable
    public List<BitacoraErrores> buscarBitacoraErroresPorIdVigilanciaSinRutaBitacora(Integer idVigilancia, String fechaInicio, String fechaFin) {
        StringBuilder sql = new StringBuilder();

        sql.append("select dt.idvigilancia,dsc.descripcion,dt.nombrearchivo,dt.rutabitacoraerrores,vig.fechacargaarchivos from sgtt_detallecarga dt inner join sgtt_vigilancia "
                + "vig on dt.idvigilancia=vig.idvigilancia"
                + " inner join SGTC_DscripcionVig dsc on dsc.iddescripcion=vig.iddescripcion ");
        if (idVigilancia != null) {
            sql.append(" where dt.idvigilancia=").append(idVigilancia);
        }

        if (!fechaInicio.isEmpty() && !fechaFin.isEmpty()) {
            sql.append(" where vig.fechacargaarchivos >= to_date('").append(fechaInicio).
                    append(" 00:00:00','YYYY/MM/DD HH24:MI:SS.SSSSS') and vig.fechacargaarchivos < to_date('").append(fechaFin).append(" 23:59:59','YYYY/MM/DD HH24:MI:SS.SSSSS')");
        }

        sql.append(" order by  dt.idvigilancia desc ");
        logger.debug("sql Buscar Bitacora:" + sql.toString());
        return template.query(sql.toString(), new BitacoraErroresMapper());

    }

    @Override
    @Propagable
    public void guardaPlantillaArcaBatch(EsMultaEnum esMulta) {
        if (esMulta.getValor().equals(0)) {
            template.update(PlantillaArcaSQL.INSERT_PLANTILLA_ARCA_BATCH, esMulta.getValor());
        } else if (esMulta.getValor().equals(1)) {
            template.update(PlantillaArcaSQL.INSERT_PLANTILLA_MULTA_CREDITO, esMulta.getValor());
        }
    }

    @Override
    @Propagable
    public void eliminaPlantillaArcaBatch() {
        template.execute(PlantillaArcaSQL.DELETE_PLANTILLA_ARCA_BATCH);
    }
}
