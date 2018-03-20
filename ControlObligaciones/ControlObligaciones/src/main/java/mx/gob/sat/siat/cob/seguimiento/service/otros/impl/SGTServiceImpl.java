package mx.gob.sat.siat.cob.seguimiento.service.otros.impl;

import java.math.BigInteger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.arca.PlantillaArcaDAO;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.PlantillaSGTDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.TipoDocEtapaDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.VigilanciaDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.ComboStr;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.BitacoraErrores;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.CargaVigilancia;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleCarga;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ParametrosSeguimiento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.PlantillaDocumento;
import mx.gob.sat.siat.cob.seguimiento.service.otros.SGTService;
import mx.gob.sat.siat.exception.DaoException;
import mx.gob.sat.siat.modelo.dao.SegbMovimientosDAO;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;
import mx.gob.sat.siat.cob.seguimiento.dao.cobranza.CatalogosCobranzaDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.MultaCobDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.ParametroSgtDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ParametrosSgtDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoFirmaEnum;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author root
 */
@Service(value = "sgtservice")
@Transactional
public class SGTServiceImpl implements SGTService {

    @Autowired
    private transient VigilanciaDAO vigilanciaDAO;
    @Autowired
    private transient PlantillaSGTDAO plantillaSGTDAO;
    @Autowired
    private transient TipoDocEtapaDAO tipoDocEtapaSGTADAO;
    @Autowired
    private transient PlantillaArcaDAO plantillaArcaDAO;
    @Autowired
    @Qualifier("segbMovimientosDAOImpl")
    private transient SegbMovimientosDAO segbMovimientosDAO;
    @Autowired
    private transient CatalogosCobranzaDAO catalogosCobranzaDAO;
    @Autowired
    private ParametroSgtDAO parametroSgtDAO;
    @Autowired
    private MultaCobDAO multaCobDAO;
    private final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(getClass());

    /**
     *
     * @param vigilancia
     * @param segMovDto
     * @throws SGTServiceException
     */
    @Transactional(readOnly = false,
            isolation = Isolation.DEFAULT,
            propagation = Propagation.NESTED,
            rollbackFor = {SeguimientoDAOException.class})
    @Override
    public String guardaVigilancia(CargaVigilancia vigilancia, SegbMovimientoDTO segMovDto) throws SGTServiceException {
        BigInteger folio;
        String identificador = "";
        try {
            vigilancia.setFechaCarga(new Date());
            logger.error(vigilancia);
            vigilanciaDAO.guarda(vigilancia);
            for (DetalleCarga d : vigilancia.getDetalleCarga()) {
                if (!d.isCargaInvalida()) {
                    vigilanciaDAO.guardaDetalle(d);
                    identificador = vigilanciaDAO.consultaUltimoIdVigilancia();
                }
            }
            logger.error("paso vigilancia");
            folio = segbMovimientosDAO.insert(segMovDto);
            logger.info(folio);
        } catch (SeguimientoDAOException e) {
            logger.error(e.getMessage());
            throw new SGTServiceException(e);
        } catch (DaoException e) {
            logger.error(e.getMessage());
            throw new SGTServiceException(e);
        }
        return identificador;
    }

    /**
     *
     * @param vigilancia
     * @throws SGTServiceException
     */
    @Transactional(readOnly = false,
            isolation = Isolation.DEFAULT,
            propagation = Propagation.NESTED,
            rollbackFor = {SeguimientoDAOException.class})
    @Override
    public String guardaVigilancia(CargaVigilancia vigilancia) throws SGTServiceException {
        String identificador = "";
        try {
            vigilancia.setFechaCarga(new Date());
            vigilanciaDAO.guarda(vigilancia);
            for (DetalleCarga d : vigilancia.getDetalleCarga()) {
                if (!d.isCargaInvalida()) {
                    vigilanciaDAO.guardaDetalle(d);
                    identificador = vigilanciaDAO.consultaUltimoIdVigilancia();
                }
            }
        } catch (SeguimientoDAOException e) {
            logger.error(e.getMessage());
            throw new SGTServiceException(e);
        }
        return identificador;
    }

    /**
     *
     * @param plantilla
     * @return
     * @throws SGTServiceException
     */
    @Transactional(readOnly = false,
            isolation = Isolation.DEFAULT,
            propagation = Propagation.NESTED,
            rollbackFor = {SeguimientoDAOException.class})
    @Override
    public int guardaPlantilla(PlantillaDocumento plantilla) throws SGTServiceException {
        int idPlantillaMax = 0;
        try {

            idPlantillaMax = plantillaSGTDAO.guardarPlantilla(plantilla);

        } catch (SeguimientoDAOException e) {
            logger.error(e.getMessage());
            throw new SGTServiceException(e);
        }
        return idPlantillaMax;
    }

    /**
     *
     * @return @throws SGTServiceException
     */
    @Transactional(readOnly = false,
            isolation = Isolation.DEFAULT,
            propagation = Propagation.NESTED,
            rollbackFor = {SeguimientoDAOException.class})
    @Override
    public List<PlantillaDocumento> buscarPlantillasDocumento() throws SGTServiceException {

        List<PlantillaDocumento> plantillas = Collections.emptyList();
        try {

            plantillas = plantillaSGTDAO.buscarPlantillasDocumento();

        } catch (SeguimientoDAOException e) {
            logger.error(e.getMessage());
            throw new SGTServiceException(e);
        }

        return plantillas;
    }

    /**
     *
     * @param rfc
     * @return
     * @throws SGTServiceException
     */
    @Transactional(readOnly = false,
            isolation = Isolation.DEFAULT,
            propagation = Propagation.NESTED,
            rollbackFor = {SeguimientoDAOException.class})
    @Override
    public Integer buscarEstadoSeguimientoActualPorRfc(String rfc) throws SGTServiceException {
        Integer enEjecucion = 0;

        try {

            enEjecucion = tipoDocEtapaSGTADAO.buscarEstadoSeguimientoActualPorRfc(rfc);

        } catch (SeguimientoDAOException e) {
            logger.error(e.getMessage());
            throw new SGTServiceException(e);
        }
        return enEjecucion;
    }

    @Transactional(readOnly = false,
            isolation = Isolation.DEFAULT,
            propagation = Propagation.NESTED,
            rollbackFor = {SeguimientoDAOException.class, Exception.class})
    @Propagable
    public void guardarParametrosLiquidacion(String montoMinimo, String montoTotal) throws SGTServiceException {

        parametroSgtDAO.actualizarVigenciaLiquidacion();
        parametroSgtDAO.guardarParametrosLiquidacion(montoMinimo, montoTotal);

    }

    @Transactional(readOnly = true)
    @Propagable
    public List<ParametrosSgtDTO> obtenerParametrosSgt() throws SGTServiceException {
        try {
            return parametroSgtDAO.obtenerParametrosSgt();
        } catch (SeguimientoDAOException e) {
            logger.error(e.getMessage());
            throw new SGTServiceException(e);
        }

    }

    @Transactional(readOnly = false,
            isolation = Isolation.DEFAULT,
            propagation = Propagation.NESTED,
            rollbackFor = {SeguimientoDAOException.class, Exception.class})
    public void guardarParametrosEF(ParametrosSgtDTO param) throws SGTServiceException {
        try {
            parametroSgtDAO.actualizarVigenciaParametroSgt(param.getIdParametro());
            parametroSgtDAO.guardarParametroSgt(param);
        } catch (SeguimientoDAOException e) {
            logger.error("Error al actualizar o guardar parametro Sgt" + e.getMessage());
            throw new SGTServiceException("Error al actualizar o guardar parametro Sgt" + e);
        }

    }

    /**
     *
     * @param enEjecucion
     * @param rfc
     * @param segMovDto
     * @throws SGTServiceException
     */
    @Transactional(readOnly = false,
            isolation = Isolation.DEFAULT,
            propagation = Propagation.NESTED,
            rollbackFor = {SeguimientoDAOException.class, Exception.class})
    @Override
    public void actualizarEstadoSeguimiento(Integer enEjecucion, String rfc, SegbMovimientoDTO segMovDto) throws SGTServiceException {
        BigInteger folio;
        try {
            logger.info("valorUpdate-Guardar:" + enEjecucion);
            logger.info("rfc-Guardar:" + rfc);
            tipoDocEtapaSGTADAO.actualizarRegistroEjecucionSeguimiento(enEjecucion, rfc);
            folio = segbMovimientosDAO.insert(segMovDto);
            logger.info(folio);
        } catch (SeguimientoDAOException e) {
            logger.error(e.getMessage());
            throw new SGTServiceException(e);
        } catch (DaoException e) {
            logger.error(e);
        }

    }

    /**
     *
     * @return @throws SGTServiceException
     */
    @Transactional(readOnly = false,
            isolation = Isolation.DEFAULT,
            propagation = Propagation.NESTED,
            rollbackFor = {SeguimientoDAOException.class})
    @Override
    public List<ParametrosSeguimiento> buscarParametrosVigentes() throws SGTServiceException {
        List<ParametrosSeguimiento> paramVigentes = null;
        try {
            paramVigentes = tipoDocEtapaSGTADAO.buscarParametrosVigentes();
        } catch (SeguimientoDAOException e) {
            logger.error(e.getMessage());
            throw new SGTServiceException(e);
        }
        return paramVigentes;
    }

    /**
     *
     * @param plantillaDocumentoArca
     * @param idRepetido
     * @param dto
     * @throws SGTServiceException
     */
    @Transactional(readOnly = false,
            isolation = Isolation.DEFAULT,
            propagation = Propagation.NESTED,
            rollbackFor = {SeguimientoDAOException.class})
    @Override
    public void guardarPlantillaARCA(PlantillaDocumento plantillaDocumentoArca) throws SGTServiceException {
        try {
            plantillaSGTDAO.eliminarPlantillasRepetidas(plantillaDocumentoArca);
            plantillaSGTDAO.guardarPlantillaArca(plantillaDocumentoArca);

        } catch (SeguimientoDAOException e) {
            logger.error(e.getMessage());
            throw new SGTServiceException(e);
        }
    }

    @Override
    public Boolean existePlantillaARCA(PlantillaDocumento plantillaDocumento) throws SGTServiceException {
        try {
            return plantillaSGTDAO.buscarPlantillaArcaPorParametros(plantillaDocumento) != null;
        } catch (SeguimientoDAOException e) {
            logger.error(e.getMessage());
            throw new SGTServiceException(e);
        }
    }

    /**
     *
     * @return @throws SGTServiceException
     */
    @Transactional(readOnly = false,
            isolation = Isolation.DEFAULT,
            propagation = Propagation.NESTED,
            rollbackFor = {SeguimientoDAOException.class})
    @Override
    public List<PlantillaDocumento> buscarPlantillasDocumentoArca() throws SGTServiceException {

        List<PlantillaDocumento> plantillas = Collections.emptyList();
        try {

            plantillas = plantillaSGTDAO.buscarPlantillasArca();

        } catch (SeguimientoDAOException e) {
            logger.error(e.getMessage());
            throw new SGTServiceException(e);
        }

        return plantillas;
    }

    /**
     *
     * @param plantillaDocumento
     * @param dto
     * @throws SGTServiceException
     */
    @Transactional(readOnly = false,
            isolation = Isolation.DEFAULT,
            propagation = Propagation.NESTED,
            rollbackFor = {SeguimientoDAOException.class})
    @Override
    public void actualizarEstadoPlantillaArca(PlantillaDocumento plantillaDocumento) throws SGTServiceException {
        try {
            plantillaSGTDAO.actualizarEstadoPlantillaArca(plantillaDocumento.getIdPlantilla());
        } catch (SeguimientoDAOException e) {
            logger.error(e.getMessage());
            throw new SGTServiceException(e);
        }
    }

    /**
     *
     * @return @throws SGTServiceException
     */
    @Transactional(readOnly = false,
            isolation = Isolation.DEFAULT,
            propagation = Propagation.NESTED,
            rollbackFor = {SeguimientoDAOException.class})
    @Override
    public List<CatalogoBase> buscarPlantillasDBArca() throws SGTServiceException {
        List<CatalogoBase> catPlantillasArca = Collections.emptyList();
        try {
            catPlantillasArca = plantillaArcaDAO.buscarPlantillasDBArca();

        } catch (SeguimientoDAOException e) {
            logger.error(e.getMessage());
            throw new SGTServiceException(e);
        }

        return catPlantillasArca;
    }

    /**
     *
     * @return @throws SGTServiceException
     */
    @Override
    public List<ComboStr> buscarTiposMotivo() throws SGTServiceException {
        List<ComboStr> cat = multaCobDAO.buscarTiposMulta();
        if (cat == null) {
            throw new SGTServiceException("No se pudo obtener combo resol motivo");
        }
        return cat;
    }

    /**
     *
     * @return @throws SGTServiceException
     */
    @Override
    public List<CatalogoBase> getTipoFirma() throws SGTServiceException {
        List<CatalogoBase> cat = catalogosCobranzaDAO.getTipoFirma();
        if (cat == null) {
            throw new SGTServiceException("No se pudo obtener combo tipo firma");
        } else {
            CatalogoBase catBorrar = null;
            for (CatalogoBase c : cat) {
                if (c.getId() == TipoFirmaEnum.FACSIMILAR.getValor()) {
                    catBorrar = c;
                }
            }
            cat.remove(catBorrar);
        }
        return cat;
    }

    /**
     *
     * @return
     */
    @Override
    public List<CatalogoBase> buscarDiasMonto() {
        List<CatalogoBase> cat = new ArrayList<CatalogoBase>();

        CatalogoBase c1 = new CatalogoBase();
        CatalogoBase c2 = new CatalogoBase();

        c1.setId(15);
        c1.setNombre("15 d\u00EDas");
        c2.setId(45);
        c2.setNombre("45 d\u00EDas");

        cat.add(c1);
        cat.add(c2);

        return cat;
    }

    /**
     *
     * @param idVigilancia
     * @param fechaInicio
     * @param fechaFin
     * @return
     * @throws SGTServiceException
     */
    @Override
    public List<BitacoraErrores> buscarBitacoraErroresPorIdVigilancia(Integer idVigilancia, String fechaInicio, String fechaFin) throws SGTServiceException {
        List<BitacoraErrores> lst = null;
        try {
            if (idVigilancia != null) {
                lst = plantillaSGTDAO.buscarBitacoraErroresPorIdVigilancia(idVigilancia, "", "");
            } else {
                lst = plantillaSGTDAO.buscarBitacoraErroresPorIdVigilancia(null, fechaInicio, fechaFin);
            }
        } catch (SeguimientoDAOException e) {
            logger.error(e.getMessage());
            throw new SGTServiceException(e);
        }
        return lst;
    }
    
    /**
     *
     * @param idVigilancia
     * @param fechaInicio
     * @param fechaFin
     * @return
     * @throws SGTServiceException
     */
    @Override
    public List<BitacoraErrores> buscarBitacoraErroresPorIdVigilanciaSinRutaBitacora(Integer idVigilancia, String fechaInicio, String fechaFin) throws SGTServiceException {
        List<BitacoraErrores> lst = null;
        try {
            if (idVigilancia != null) {
                lst = plantillaSGTDAO.buscarBitacoraErroresPorIdVigilancia(idVigilancia, "", "");
            } else {
                lst = plantillaSGTDAO.buscarBitacoraErroresPorIdVigilanciaSinRutaBitacora(null, fechaInicio, fechaFin);
            }
        } catch (SeguimientoDAOException e) {
            logger.error(e.getMessage());
            throw new SGTServiceException(e);
        }
        return lst;
    }

    @Override
    @Propagable
    public List<ParametrosSgtDTO> obtenerParametrosSgt(String idParametro) {

        return parametroSgtDAO.obtenerParametrosSgt(idParametro);

    }

    @Override
    public ParametrosSgtDTO obtenerParametroSgtPorIdParametro(int idParametro) {
        return parametroSgtDAO.obtenerParametroSgtPorIdParametro(idParametro);
    }

    @Override
    public List<ParametrosSgtDTO> obtenerParametrosVigentesSgt() {
        return parametroSgtDAO.obtenerParametrosVigentesSgt();
    }
}
