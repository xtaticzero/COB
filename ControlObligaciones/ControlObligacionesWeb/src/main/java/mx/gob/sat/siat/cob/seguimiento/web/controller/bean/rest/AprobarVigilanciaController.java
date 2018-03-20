package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.FuncionarioDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DocumentoAprobarDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Funcionario;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AdministrativoNivelCargo;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.CadenaOriginal;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.FirmaAprobarVigilanciaDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoServiciosConcurrentesEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.aprobacion.AprobarVigilanciaDelegateService;
import mx.gob.sat.siat.cob.seguimiento.service.otros.ConcurrenceService;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author christian.ventura
 */
@Controller
@RequestMapping("/aprobarVigilancia")
public class AprobarVigilanciaController {

    private final Logger logger = Logger.getLogger(AprobarVigilanciaController.class);
    private final static String STR_NUM_EMPL = "numEmp";
    private final static String STR_VIGILANCIA = "vigilancia";
    private final static String STR_ESTATUS = "estatus";
    private static final String RESULTADOS_APROBACION_EN_EJECUCION = "El proceso de carga de Aprobacion de vigilancias se está ejecutando.";
    @Autowired
    private AprobarVigilanciaDelegateService aprobarVigilanciaDelegateServiceImpl;
    @Autowired
    private DocumentoAprobarDAO documentoAprobarDAO;
    @Autowired
    private FuncionarioDAO funcionarioDAO;
    @Autowired
    private ConcurrenceService concurrenceService;
    @Autowired
    @Qualifier("mapaFirmas")
    private HashMap mapaFirmas;
    @Autowired
    @Qualifier("jobLauncher")
    private JobLauncher jobLauncher;
    @Autowired
    @Qualifier("aprobarVigilanciaJob")
    private Job job;

    private ObjectMapper objectMapper;

    public ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        return objectMapper;
    }

    /**
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getTotalRegistros", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getTotalRegistros(HttpServletRequest request) {
        logger.debug("getTotalRegistros");
        Map<String, Object> retorno = new HashMap<String, Object>();
        Long numeroRegistros = 0L;
        try {
            String numeroEmpleado = "";
            String vigilancia = "";
            if (request.getParameter(STR_NUM_EMPL) != null && request.getParameter(STR_VIGILANCIA) != null) {
                numeroEmpleado = request.getParameter(STR_NUM_EMPL);
                vigilancia = request.getParameter(STR_VIGILANCIA);
                AdministrativoNivelCargo adminitrativo
                        = aprobarVigilanciaDelegateServiceImpl.buscarCargoAdministrativo(numeroEmpleado);
                numeroRegistros = documentoAprobarDAO.contarRegistrosFirma(vigilancia,
                        adminitrativo.getIdAdministacionLocal());
            }
        } catch (SGTServiceException ex) {
            logger.error(ex);
        }
        retorno.put("numeroRegistros", numeroRegistros);
        return retorno;
    }

    /**
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getDatos", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getDatos(HttpServletRequest request) {
        logger.debug("get datos");
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
            List<CadenaOriginal> documentosFirmar = new ArrayList<CadenaOriginal>();
            String numeroEmpleado = "";
            String vigilancia = "";
            int rowInicial = 0;
            int rowFinal = 0;
            if (request.getParameter(STR_NUM_EMPL) != null && request.getParameter(STR_VIGILANCIA) != null
                    && request.getParameter("rowInicial") != null && request.getParameter("rowFinal") != null) {
                numeroEmpleado = request.getParameter(STR_NUM_EMPL);
                vigilancia = request.getParameter(STR_VIGILANCIA);
                rowInicial = Integer.parseInt(request.getParameter("rowInicial"));
                rowFinal = Integer.parseInt(request.getParameter("rowFinal"));
                AdministrativoNivelCargo adminitrativo
                        = aprobarVigilanciaDelegateServiceImpl.buscarCargoAdministrativo(numeroEmpleado);

                Funcionario funcionario = new Funcionario();
                funcionario.setNumeroEmpleado(numeroEmpleado);
                funcionario = funcionarioDAO.buscaFuncionarioPorID(funcionario);
                if (funcionario != null) {
                    logger.debug(funcionario.getNombreFuncionario());
                    documentosFirmar = documentoAprobarDAO.listarDocumentosFirmar(rowInicial, rowFinal,
                            vigilancia, adminitrativo.getIdAdministacionLocal(), funcionario.getNombreFuncionario());
                }
            }
            retorno.put("cadenasOriginales", documentosFirmar);
            retorno.put(STR_ESTATUS, 1);
        } catch (SGTServiceException ex) {
            logger.error(ex);
            retorno.put(STR_ESTATUS, 0);
        }
        return retorno;
    }

    /**
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/guardarDatos", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> guardarDatos(HttpServletRequest request) {
        logger.debug("guardar datos");
        Map<String, Object> retorno = new HashMap<String, Object>();
        retorno.put("mensaje", "guardado");
        retorno.put(STR_ESTATUS, 1);

        StringBuilder cadenaOrigen = null;
        try {
            cadenaOrigen = new StringBuilder(5000);
            Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
            while (scanner.hasNextLine()) {
                cadenaOrigen.append(scanner.nextLine());
            }
        } catch (IOException ex) {
            logger.error(ex);
            retorno.put(STR_ESTATUS, 0);
        }
        if (cadenaOrigen != null && cadenaOrigen.length() > 0) {
            logger.debug(cadenaOrigen.toString());
            try {
                FirmaAprobarVigilanciaDTO datosFirmas = getObjectMapper().
                        readValue(cadenaOrigen.toString(), FirmaAprobarVigilanciaDTO.class);
                ejecutaBatch(datosFirmas, retorno);
            } catch (JsonParseException ex) {
                logger.error(ex);
                retorno.put(STR_ESTATUS, 0);
            } catch (JsonMappingException ex) {
                logger.error(ex);
                retorno.put(STR_ESTATUS, 0);
            } catch (IOException ex) {
                logger.error(ex);
                retorno.put(STR_ESTATUS, 0);
            }
        }

        return retorno;
    }

    /**
     *
     * @param datosFirmas
     */
    final void ejecutaBatch(FirmaAprobarVigilanciaDTO datosFirmas, Map<String, Object> retorno) {
        logger.info("--------------------- inicia job batch");
        JobExecution execution = null;
        Long time = System.currentTimeMillis() + datosFirmas.getLista().hashCode();
        mapaFirmas.put(String.valueOf(time), datosFirmas.getLista());
        try {
            if (!datosFirmas.getLista().isEmpty()) {
                JobParameters jobParameters = new JobParametersBuilder()
                        .addLong("time", time)
                        .addString("numEmpleado", datosFirmas.getEmpleadoFirma()).toJobParameters();
                execution = jobLauncher.run(job, jobParameters);
            }
        } catch (JobExecutionAlreadyRunningException ex) {
            logger.error(ex);
            retorno.put(STR_ESTATUS, 0);
        } catch (JobRestartException ex) {
            logger.error(ex);
            retorno.put(STR_ESTATUS, 0);
        } catch (JobInstanceAlreadyCompleteException ex) {
            logger.error(ex);
            retorno.put(STR_ESTATUS, 0);
        } catch (JobParametersInvalidException ex) {
            logger.error(ex);
            retorno.put(STR_ESTATUS, 0);
        } finally {
            if (execution != null && execution.getStatus().equals(BatchStatus.COMPLETED)) {
                logger.debug("-----------termina batch-----------");
                logger.error(execution.getStatus());
            } else {
                logger.debug("-----------termina batch-----------");
                retorno.put(STR_ESTATUS, 0);
            }
        }
    }

    /**
     *
     * @param request
     * @param sesion
     * @return
     */
    @RequestMapping(value = "/procesoTomado", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getProcesoTomado(HttpServletRequest request, HttpSession sesion) {
        Map<String, Object> retorno = new HashMap<String, Object>();
        retorno.put("estatus", 1);
        retorno.put("bloqueado", 1);
        try {
            logger.info("Verificando si se puede generar el firmado de multas");
            String vigilancia = "";
            String numEmp = "";
            if (request.getParameter("idFirma") != null && request.getParameter(STR_VIGILANCIA) != null
                    && request.getParameter(STR_NUM_EMPL) != null) {
                vigilancia = request.getParameter(STR_VIGILANCIA);
                numEmp = request.getParameter(STR_NUM_EMPL);
                AdministrativoNivelCargo administrativo
                        = aprobarVigilanciaDelegateServiceImpl.buscarCargoAdministrativo(numEmp);
                String firma = "";
                switch (administrativo.getNivelEmision()) {
                    case CENTRAL:
                        firma = vigilancia;
                        break;
                    case LOCAL:
                        firma = vigilancia + "|" + administrativo.getIdAdministacionLocal();
                        break;
                }
                if (!concurrenceService.isLockedServices(
                        TipoServiciosConcurrentesEnum.IDENTIFICADOR_VIGILANCIA_DOCUMENTOS, firma)) {
                    logger.info("Si se puede hacer el firmado de multas");
                    sesion.setAttribute("datosFirma", firma);
                    retorno.put("bloqueado", 0);
                }
            }
        } catch (Exception ex) {
            retorno.put("estatus", 0);
            retorno.put("error", ex.getMessage());
            logger.error("Error en verificar si el proceso esta tomado \n" + ex);
        }
        return retorno;
    }

    /**
     *
     * @param request
     * @param sesion
     * @return
     */
    @RequestMapping(value = "/tomarProceso", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> tomarProceso(HttpServletRequest request, HttpSession sesion) {
        Map<String, Object> retorno = new HashMap<String, Object>();
        retorno.put("estatus", 1);
        retorno.put("bloqueado", 1);
        try {
            String firma = "";
            firma = sesion.getAttribute("datosFirma").toString();
            logger.info("Intentando bloquear el grupo de multas " + firma);
            if (!concurrenceService.isLockedServices(
                    TipoServiciosConcurrentesEnum.IDENTIFICADOR_VIGILANCIA_DOCUMENTOS, firma)) {
                if (!concurrenceService.lockServices(
                        TipoServiciosConcurrentesEnum.IDENTIFICADOR_VIGILANCIA_DOCUMENTOS, firma)) {
                    throw new SGTServiceException(RESULTADOS_APROBACION_EN_EJECUCION);
                }
                logger.info("Se logro hacer el bloqueo " + firma);
            }
        } catch (Exception ex) {
            retorno.put("bloqueado", 0);
            retorno.put("estatus", 0);
            retorno.put("error", ex.getMessage());
            logger.error("Error en verificar si el proceso esta tomado \n" + ex);
        }
        return retorno;
    }

}
