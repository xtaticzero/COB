package mx.gob.sat.siat.cob.background.manager.impl;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;

import mx.gob.sat.siat.cob.background.manager.JobManager;
import mx.gob.sat.siat.cob.background.service.job.impl.JobServiceImpl;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.EmailReporteProcesoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.OracleDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.EjecucionDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.IntentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.ParametroSgtDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.ProcesoDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.EmailReporteProceso;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ConsultarProcesosFiltroDto;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ParametrosSgtDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Proceso;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoProceso;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.ParametroSgtEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.otros.MailService;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;

import org.apache.log4j.Logger;

import org.quartz.CronExpression;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 *
 * @author root
 */
@Service(value = "jobManager")
public class JobManagerImpl implements JobManager, InitializingBean {

    @Autowired
    private ProcesoDAO procesoDAO;
    @Autowired
    private EjecucionDAO ejecucionDAO;
    @Autowired
    private IntentoDAO intentoDAO;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    @Qualifier("mailServiceImpl")
    private MailService mailService;
    @Autowired
    @Qualifier("jobManagerMailProperties")
    private Properties mailProperties;
    @Autowired
    private EmailReporteProcesoDAO emailReporteProcesoDAO;
    @Autowired
    private ParametroSgtDAO parametroSgtDAO;
    @Autowired
    private OracleDAO oracleDAO;
    @Autowired
    private ArrayList<Proceso> procesosRestraso;

    private static Logger logger = Logger.getLogger(JobManagerImpl.class);

    /**
     *
     */
    public JobManagerImpl() {
        super();

    }

    /**
     *
     * @throws SeguimientoDAOException
     * @throws SGTServiceException
     */
    @Override
    public void ejecutarProcesos() throws SeguimientoDAOException, SGTServiceException {
        Double idManager = new Double(Math.random());
        String managerName = idManager.toString().substring(2, 10);
        logger.debug("Inicia Job Exec..." + managerName);
        ExecutorService executor = Executors.newFixedThreadPool(5);
        procesoDAO.actualizarManager(managerName);

        ConsultarProcesosFiltroDto filtro = new ConsultarProcesosFiltroDto();
        filtro.getExcluirEstados().add(EstadoProceso.DESHABILITADO);
        filtro.getIncluirEstados().add(EstadoProceso.POR_EJECUTAR);
        filtro.setIdManager(managerName);
        List<Proceso> porEjecutar = null;

        porEjecutar = procesoDAO.consultarProcesos(filtro);

        for (Proceso p : porEjecutar) {
            JobServiceImpl thread = new JobServiceImpl();
            thread.setProcesoDAO(procesoDAO);
            thread.setEjecucionDAO(ejecucionDAO);
            thread.setIntentoDAO(intentoDAO);
            thread.setOracleDAO(oracleDAO);
            thread.setContext(applicationContext);
            thread.setProceso(p);
            thread.setManagerName(managerName);
            executor.execute(thread);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                logger.error(ex);
            }
        }

        logger.debug("Fin Job Exec.");
    }

    /**
     *
     * @throws SeguimientoDAOException
     * @throws SGTServiceException
     */
    @Override
    public void censar() throws SeguimientoDAOException, SGTServiceException {
        logger.debug("Job Censar...");
        reportarTiempoExcesivo();
        ConsultarProcesosFiltroDto filtro = new ConsultarProcesosFiltroDto();
        filtro.getExcluirEstados().add(EstadoProceso.DESHABILITADO);
        filtro.getExcluirEstados().add(EstadoProceso.POR_EJECUTAR);
        filtro.getExcluirEstados().add(EstadoProceso.EN_EJECUCION);
        List<Proceso> procesos = null;
        procesos = procesoDAO.consultarProcesos(filtro);
        for (Proceso p : procesos) {
            if (p.getEstado().equals(EstadoProceso.COMPLETADO.getIdEdoDoc())
                    && p.getLanzador() == null && procesosSubsecuentesCompletados(p)) {
                logger.debug("Habilitando cadena...");
                habilitarCadenaEjecucion(p);
                logger.debug("Fin");
            }
        }
        for (Proceso p : procesos) {
            establecerSiguienteEstado(p);
        }
        //excluye processo que estan listados en la columnda de excluir
        excluirProcesos(procesos);
        //valida si el tipo de proceso es cadena y si hay algun proceso como intentos_agotados
        //si es asi entonces no permite que ningun proceso se ejecute
        validaEjecucionTipoProceso(procesos);

        for (Proceso p : procesos) {
            if (p.getEstado().equals(EstadoProceso.POR_EJECUTAR.getIdEdoDoc())
                    || p.getEstado().equals(EstadoProceso.EN_ESPERA.getIdEdoDoc())
                    || p.getEstado().equals(EstadoProceso.FALLIDO.getIdEdoDoc())
                    || p.getEstado().equals(EstadoProceso.INTENTOS_AGOTADOS.getIdEdoDoc())) {

                logger.debug(p.getIdProceso() + "->" + p.getEstado());
                if (p.getEstado().equals(EstadoProceso.POR_EJECUTAR.getIdEdoDoc())) {
                    p.setFechaInicioEjecucion(oracleDAO.consultarFechaActual());
                }
                procesoDAO.actualizar(p);
            }
        }

        logger.debug("Fin del Censo.");
    }

    private void validaEjecucionTipoProceso(List<Proceso> procesos) throws SeguimientoDAOException, SGTServiceException {
        logger.debug("validaEjecucionTipoProceso");
        List<Proceso> lanzadores = new ArrayList<Proceso>();
        if (procesos.size() > 0) {
            lanzadores = procesoDAO.consultarPrimerLanzador(procesos.get(0));
            logger.debug("Lanzadores --> " + lanzadores.size());
        }
        boolean isIntentoAgotadoPorTipoCadena = false;
        for (Proceso p : procesos) {
            if (lanzadores.size() > 0) {
                isIntentoAgotadoPorTipoCadena
                        = lanzadores.get(0).getTipoCadena().intValue() == Proceso.TIPO_CADENA_PROCESAMIENTO
                        && p.getEstado().equals(EstadoProceso.INTENTOS_AGOTADOS.getIdEdoDoc());
                logger.debug("isIntentoAgotadoPorTipoCadena:" + isIntentoAgotadoPorTipoCadena);
                if (isIntentoAgotadoPorTipoCadena) {
                    break;
                }
            }
        }
        if (isIntentoAgotadoPorTipoCadena) {
            logger.debug("se ponen como estado habilitado los procesos, sin posibilidad de ejecucion");
            for (Proceso p : procesos) {
                if (p.getEstado().equals(EstadoProceso.POR_EJECUTAR.getIdEdoDoc())) {
                    p.setEstado(EstadoProceso.HABILITADO.getIdEdoDoc());
                }
                procesoDAO.actualizar(p);
            }
        }
    }

    private void establecerSiguienteEstado(Proceso p) throws SeguimientoDAOException, SGTServiceException {
        /*Caso 1. Proceso Fallido
         * Poner en estatus Por Ejecutar un proceso terminado en FALLO siempre que el numero de intentos sea menor al maximo
         * permitido (INTENTOSMAX)
         * */
        if (isCasoProcesoFallido(p)) {
            if (p.getIntentos() >= p.getIntentosMax()) {
                p.setEstado(EstadoProceso.INTENTOS_AGOTADOS.getIdEdoDoc());
                List<EmailReporteProceso> emails = emailReporteProcesoDAO.todosLosEmailReporteProceso(false);
                ParametrosSgtDTO parametro = parametroSgtDAO.obtenerParametroSgtPorIdParametro(ParametroSgtEnum.AMBIENTE.getValor());
                StringBuilder sEmails = new StringBuilder("");
                for (EmailReporteProceso emailReporteProceso : emails) {
                    sEmails.append(emailReporteProceso.getCorreoElectronico()).append(",");
                    if (emailReporteProceso.getCorreoElectronicoAlterno() != null) {
                        sEmails.append(emailReporteProceso.getCorreoElectronicoAlterno()).append(",");
                    }
                }
                String[] destinatarios = sEmails.toString().split(",");
                String asunto = mailProperties.getProperty("mail.asunto");
                String template = mailProperties.getProperty("mail.template");
                Map<String, String> sustituir = new HashMap<String, String>();
                sustituir.put("proceso.descripcion", p.getDescripcion());
                String mensaje = Utilerias.obtenerCorreo(template, sustituir);
                try {
                    logger.info("Enviando correo...");
                    mailService.enviarCorreoPara(destinatarios, "Ambiente:" + parametro.getValor() + " - " + asunto, mensaje);
                    logger.info("Correo enviado correctamente");
                } catch (MessagingException e) {
                    logger.error("No fue posible enviar correo " + e.toString());
                }
            } else {
                if (!procesosExcluyentesEnEjecucion(p)) {
                    logger.debug(p.getIdProceso() + "Habilitado por Proceso Fallido!");
                    p.setEstado(EstadoProceso.POR_EJECUTAR.getIdEdoDoc());
                }
            }
        }

        /* Caso 2. Expresion CRON
         * Cuando la expresión CRON se cumple para ejecución partiendo de estatus HABILITADO
         *
         * */
        if (isExpresionCron(p)) {
            try {
                if (p.getFechaInicioEjecucion() == null) {
                    if (!procesosExcluyentesEnEjecucion(p)) {
                        logger.debug(p.getIdProceso() + "Habilitado por primera vez!");
                        p.setEstado(EstadoProceso.POR_EJECUTAR.getIdEdoDoc());
                    }
                } else {
                    CronExpression cronExpression = new CronExpression(p.getProgramacion());
                    Date dSiguiente = cronExpression.getNextValidTimeAfter(p.getFechaInicioEjecucion() == null ? new Date(0) : p.getFechaInicioEjecucion());
                    logger.debug("--->" + Utilerias.formatearFechaAAAAMMDDHHMMSS(dSiguiente));
                    if (dSiguiente.before(oracleDAO.consultarFechaActual())) {
                        if (!procesosExcluyentesEnEjecucion(p)) {
                            logger.debug(p.getIdProceso() + "Habilitado por cron!");
                            p.setEstado(EstadoProceso.POR_EJECUTAR.getIdEdoDoc());
                        }
                    }
                }
            } catch (ParseException e) {
                logger.error(e);
            }
        }

        /* Caso 3. Procesos Cíclicos
         * Cuando el campo Programacion es nulo y el lanzador es nulo, el proceso se ejecuta ciclicamente siempre que no haya procesos en ejecución
         * */
        if (isProcesoCiclico(p) && !procesosExcluyentesEnEjecucion(p)) {
            logger.debug(p.getIdProceso() + "Habilitado por Procesos No programados!");
            p.setEstado(EstadoProceso.POR_EJECUTAR.getIdEdoDoc());
        }

        /* Caso 4. Procesos lanzadores completados
         * Cuando todos los procesos lanzadores están en estado COMPLETADOS poner en estado POR EJECUTAR
         * */
        if (isProcesosLanzadoresCompletados(p) && p.getLanzador() != null && !procesosExcluyentesEnEjecucion(p)) {
            logger.debug(p.getIdProceso() + "Habilitado por Procesos lanzadores completados!");
            p.setEstado(EstadoProceso.POR_EJECUTAR.getIdEdoDoc());
        }
    }

    private boolean isCasoProcesoFallido(Proceso p) {
        return p.getEstado().equals(EstadoProceso.FALLIDO.getIdEdoDoc());
    }

    private boolean isExpresionCron(Proceso p) {
        return p.getEstado().equals(EstadoProceso.HABILITADO.getIdEdoDoc()) && p.getLanzador() == null && p.getProgramacion() != null;
    }

    private boolean isProcesoCiclico(Proceso p) {
        return p.getEstado().equals(EstadoProceso.HABILITADO.getIdEdoDoc()) && p.getProgramacion() == null && p.getLanzador() == null;
    }

    private boolean isProcesosLanzadoresCompletados(Proceso p) throws SeguimientoDAOException, SGTServiceException {
        return p.getEstado().equals(EstadoProceso.HABILITADO.getIdEdoDoc()) && procesosLanzadoresCompletados(p);
    }

    /**
     *
     * @param p
     * @return
     * @throws SeguimientoDAOException
     * @throws SGTServiceException
     */

    private boolean procesosLanzadoresCompletados(Proceso p) throws SeguimientoDAOException, SGTServiceException {
        boolean ready = false;
        List<Integer> ids = new ArrayList<Integer>();

        if (p.getLanzador() != null) {
            String[] splitted = p.getLanzador().split(",");
            if (splitted.length > 0) {
                try {
                    for (int i = 0; i < splitted.length; i++) {
                        ids.add(Integer.valueOf(splitted[i]));
                    }
                } catch (NumberFormatException nfe) {
                    throw new SGTServiceException("Formato de parametros Trigger incorrecto para " + p.getDescripcion(), nfe);
                }
            } else {
                throw new SGTServiceException("Formato de parmetros Trigger incorrecto para " + p.getDescripcion());
            }
            List<Proceso> triggers = procesoDAO.consultarPorId(ids);

            int completed = 0;
            for (Proceso trigger : triggers) {

                if (isCompletado(trigger)) {
                    completed++;
                }
            }
            if (completed > 0 && completed == triggers.size()) {
                ready = true;
            } else {
                ready = false;
            }
        } else {
            ready = true;
        }
        return ready;
    }

    private boolean isCompletado(Proceso p) throws SeguimientoDAOException {
        logger.debug("-> isCompletado (" + p.getIdProceso() + ")");
        List<Proceso> lanzadores = procesoDAO.consultarPrimerLanzador(p);
        boolean isHabilitablePorTipoProcesamiento = false;
        logger.debug("Lanzadores --> " + lanzadores.size());
        if (lanzadores.size() > 0) {
            isHabilitablePorTipoProcesamiento
                    = (lanzadores.get(0).getTipoCadena().intValue() == Proceso.TIPO_ORDEN_PROCESAMIENTO
                    && p.getEstado().equals(EstadoProceso.INTENTOS_AGOTADOS.getIdEdoDoc()));
            logger.debug("Habilitable:" + isHabilitablePorTipoProcesamiento);
        }
        logger.debug("<- isCompletado (" + p.getIdProceso() + ") = " + (p.getEstado().equals(EstadoProceso.COMPLETADO.getIdEdoDoc())
                || p.getEstado().equals(EstadoProceso.DESHABILITADO.getIdEdoDoc())
                || isHabilitablePorTipoProcesamiento));
        return (p.getEstado().equals(EstadoProceso.COMPLETADO.getIdEdoDoc())
                || p.getEstado().equals(EstadoProceso.DESHABILITADO.getIdEdoDoc())
                || isHabilitablePorTipoProcesamiento);
    }

    /**
     *
     * @param p
     * @return
     * @throws SeguimientoDAOException
     * @throws SGTServiceException
     */
    private boolean procesosExcluyentesEnEjecucion(Proceso p) throws SeguimientoDAOException, SGTServiceException {
        logger.debug("Checking " + p.getIdProceso() + " Excluyentes");
        ConsultarProcesosFiltroDto filtroEnEjecucion = new ConsultarProcesosFiltroDto();
        List<EstadoProceso> enEjecucion = new ArrayList<EstadoProceso>();
        enEjecucion.add(EstadoProceso.EN_EJECUCION);
        enEjecucion.add(EstadoProceso.POR_EJECUTAR);
        enEjecucion.add(EstadoProceso.EN_ESPERA);

        filtroEnEjecucion.setListaIdProcesos(p.getExcluir());
        filtroEnEjecucion.setIncluirEstados(enEjecucion);

        List<Proceso> procesosEnEjecucion = procesoDAO.consultarProcesos(filtroEnEjecucion);
        logger.debug(p.getIdProceso() + "->" + procesosEnEjecucion.size() + " Found!");

        return procesosEnEjecucion.size() > 0;
    }

    /**
     *
     * @param procesos
     * @throws SeguimientoDAOException
     */
    private void excluirProcesos(List<Proceso> procesos) throws SeguimientoDAOException {
        // Se excluyen procesos por prioridad dentro de la misma lista de candidatos.
        List<Proceso> procesosPorEjecutar = new ArrayList<Proceso>();
        for (Proceso a : procesos) {
            if (a.getEstado() == EstadoProceso.POR_EJECUTAR.getIdEdoDoc()) {
                a.setPrioridad(consultarPrioridad(a));
                procesosPorEjecutar.add(a);
            }
        }

        Collections.sort(procesosPorEjecutar, new Comparator<Proceso>() {
            @Override
            public int compare(Proceso o1, Proceso o2) {
                int result = 0;
                if (o1.getPrioridad() > o2.getPrioridad()) {
                    result = 1;
                }
                if (o1.getPrioridad() < o2.getPrioridad()) {
                    result = -1;
                }
                return result;
            }

        });

        for (Proceso a : procesosPorEjecutar) {
            for (Proceso b : procesos) {
                if (a != b
                        && a.getEstado() == EstadoProceso.POR_EJECUTAR.getIdEdoDoc()
                        && b.getEstado() == EstadoProceso.POR_EJECUTAR.getIdEdoDoc()) {
                    String[] excluir = a.getExcluir() != null ? a.getExcluir().split(",") : new String[0];
                    boolean excluyente = false;
                    for (String excluir1 : excluir) {
                        if (b.getIdProceso().equals(Integer.valueOf(excluir1))) {
                            excluyente = true;
                            break;
                        }
                    }
                    if (excluyente) {
                        List<Proceso> headersa = procesoDAO.consultarPrimerLanzador(a);
                        List<Proceso> headersb = procesoDAO.consultarPrimerLanzador(b);

                        Proceso headera = headersa.size() > 0 ? headersa.get(0) : a;
                        Proceso headerb = headersb.size() > 0 ? headersb.get(0) : b;

                        if (headera.getPrioridad() < headerb.getPrioridad()) {
                            a.setEstado(EstadoProceso.POR_EJECUTAR.getIdEdoDoc());
                            b.setEstado(EstadoProceso.HABILITADO.getIdEdoDoc());
                        } else {
                            b.setEstado(EstadoProceso.POR_EJECUTAR.getIdEdoDoc());
                            a.setEstado(EstadoProceso.HABILITADO.getIdEdoDoc());
                        }
                    }
                }
            }
        }
    }

    private Integer consultarPrioridad(Proceso p) throws SeguimientoDAOException {
        List<Proceso> headersa = procesoDAO.consultarPrimerLanzador(p);
        Proceso headera = headersa.size() > 0 ? headersa.get(0) : p;
        return headera.getPrioridad();
    }

    private boolean procesosSubsecuentesCompletados(Proceso p) throws SeguimientoDAOException {
        boolean resultado = false;
        logger.debug(p.getNombre() + "--->procesosSubsecuentesCompletados ...");
        List<Proceso> subsecuentes = procesoDAO.consultarProcesosSubsecuentes(p);
        List<Proceso> lanzadores = procesoDAO.consultarPrimerLanzador(p);
        boolean isHabilitablePorTipoProcesamiento = false;
        if (lanzadores.size() > 0) {
            isHabilitablePorTipoProcesamiento
                    = lanzadores.get(0).getTipoCadena().intValue() == Proceso.TIPO_ORDEN_PROCESAMIENTO
                    && p.getEstado().equals(EstadoProceso.INTENTOS_AGOTADOS.getIdEdoDoc());
            logger.debug("Habilitable:" + isHabilitablePorTipoProcesamiento);
        }
        if (subsecuentes.size() > 0) {
            int completado = 0;
            boolean comparaEstados;
            for (Proceso subsecuente : subsecuentes) {
                comparaEstados = subsecuente.getEstado().equals(EstadoProceso.COMPLETADO.getIdEdoDoc())
                        || subsecuente.getEstado().equals(EstadoProceso.INTENTOS_AGOTADOS.getIdEdoDoc())
                        || subsecuente.getEstado().equals(EstadoProceso.DESHABILITADO.getIdEdoDoc());
                completado += ((comparaEstados
                        || isHabilitablePorTipoProcesamiento)
                        && procesosSubsecuentesCompletados(subsecuente)) ? 1 : 0;
            }
            if (completado == subsecuentes.size()) {
                resultado = true;
            } else {
                resultado = false;
            }
        } else {
            //Proceso sin subsecuentes
            if (p.getEstado() == EstadoProceso.COMPLETADO.getIdEdoDoc()
                    || p.getEstado() == EstadoProceso.DESHABILITADO.getIdEdoDoc()
                    || p.getEstado() == EstadoProceso.INTENTOS_AGOTADOS.getIdEdoDoc()) {
                resultado = true;
            } else {
                resultado = false;
            }
        }
        logger.debug(p.getNombre() + "--->procesosSubsecuentesCompletados:" + resultado);
        return resultado;
    }

    /**
     *
     * @param p
     * @throws SeguimientoDAOException
     */
    private void habilitarCadenaEjecucion(Proceso p) throws SeguimientoDAOException {
        if (p.getEstado().equals(EstadoProceso.COMPLETADO.getIdEdoDoc())) {
            procesoDAO.actualizarEstado(p, EstadoProceso.HABILITADO);
        }
        List<Proceso> subsecuentes = procesoDAO.consultarProcesosSubsecuentes(p);
        for (Proceso subsecuente : subsecuentes) {
            if (p.getEstado().equals(EstadoProceso.COMPLETADO.getIdEdoDoc())) {
                procesoDAO.actualizarEstado(subsecuente, EstadoProceso.HABILITADO);
            }
            habilitarCadenaEjecucion(subsecuente);
        }
    }

    private void reportarTiempoExcesivo() throws SeguimientoDAOException {
        logger.debug("-> Buscando procesos con retraso...");
        ConsultarProcesosFiltroDto filtro = new ConsultarProcesosFiltroDto();
        filtro.getIncluirEstados().add(EstadoProceso.POR_EJECUTAR);
        filtro.getIncluirEstados().add(EstadoProceso.EN_EJECUCION);
        List<Proceso> procesos = null;
        procesos = procesoDAO.consultarProcesos(filtro);
        for (Proceso proceso : procesos) {
            if (isRetrasoPorEjecutar(proceso)) {
                logger.debug("-> Retraso en estado Proceso por ejecutar:" + proceso.getNombre());
                logger.debug("Recuperando..." + proceso.getNombre());
                proceso.setIdManager(null);
                procesoDAO.actualizar(proceso);
                logger.debug("Listo para ser procesado por otro thread..." + proceso.getNombre());
            }
            if (isRetrasoEjecucion(proceso)) {
                logger.debug("-> Retraso en estado Proceso en Ejecucion:" + proceso.getNombre() + " Tiempo:" + Utilerias.getDateDiff(proceso.getFechaInicioEjecucion(), oracleDAO.consultarFechaActual(), TimeUnit.HOURS) + " Horas");
                procesosRestraso.add(proceso);
                proceso.setFechaFinEjecucion(oracleDAO.consultarFechaActual());
                procesoDAO.actualizar(proceso);
                ParametrosSgtDTO parametro = parametroSgtDAO.obtenerParametroSgtPorIdParametro(ParametroSgtEnum.AMBIENTE.getValor());
                List<EmailReporteProceso> emails = emailReporteProcesoDAO.todosLosEmailReporteProceso(false);
                StringBuilder sEmails = new StringBuilder("");
                for (EmailReporteProceso emailReporteProceso : emails) {
                    sEmails.append(emailReporteProceso.getCorreoElectronico()).append(",");
                    if (emailReporteProceso.getCorreoElectronicoAlterno() != null) {
                        sEmails.append(emailReporteProceso.getCorreoElectronicoAlterno()).append(",");
                    }
                }
                String[] destinatarios = sEmails.toString().split(",");
                String asunto = mailProperties.getProperty("mail.retraso.asunto");
                String template = mailProperties.getProperty("mail.retraso.template");
                Map<String, String> sustituir = new HashMap<String, String>();
                sustituir.put("proceso.descripcion", proceso.getDescripcion());
                sustituir.put("proceso.tiempo", Utilerias.getDateDiff(proceso.getFechaInicioEjecucion(), oracleDAO.consultarFechaActual(), TimeUnit.HOURS) + " Horas");
                String mensaje = Utilerias.obtenerCorreo(template, sustituir);
                try {
                    logger.info("Enviando correo...");
                    mailService.enviarCorreoPara(destinatarios, "Ambiente:" + parametro.getValor() + " - " + asunto, mensaje);
                    logger.info("Correo enviado correctamente");
                } catch (MessagingException e) {
                    logger.error("No fue posible enviar correo " + e.toString());
                }
            }
        }

    }

    private boolean isRetrasoEjecucion(Proceso proceso) throws SeguimientoDAOException {
        ParametrosSgtDTO parametrosSgtDTO = parametroSgtDAO.obtenerParametroSgtPorIdParametro(ParametroSgtEnum.JOBMANAGER_TIEMPO_LIMITE_PROCESO_EN_EJECUCION.getValor());
        Integer minsLimite = Integer.valueOf(parametrosSgtDTO.getValor());
        return proceso.getEstado().intValue() == EstadoProceso.EN_EJECUCION.getIdEdoDoc()
                && Utilerias.getDateDiff(proceso.getFechaInicioEjecucion(), oracleDAO.consultarFechaActual(), TimeUnit.MINUTES) >= minsLimite
                && isNotificable(proceso);
    }

    private boolean isRetrasoPorEjecutar(Proceso proceso) throws SeguimientoDAOException {
        ParametrosSgtDTO parametrosSgtDTO = parametroSgtDAO.obtenerParametroSgtPorIdParametro(ParametroSgtEnum.JOBMANAGER_TIEMPO_LIMITE_PROCESO_POR_EJECUTAR.getValor());
        Integer minsLimite = Integer.valueOf(parametrosSgtDTO.getValor());
        return proceso.getEstado().intValue() == EstadoProceso.POR_EJECUTAR.getIdEdoDoc()
                && proceso.getIdManager() != null && Utilerias.getDateDiff(proceso.getFechaInicioEjecucion(), oracleDAO.consultarFechaActual(), TimeUnit.MINUTES) >= minsLimite;
    }

    private boolean isNotificable(Proceso proceso) throws SeguimientoDAOException {
        ParametrosSgtDTO parametrosSgtDTO = parametroSgtDAO.obtenerParametroSgtPorIdParametro(ParametroSgtEnum.JOBMANAGER_INTERVALO_TIEMPO_AVISOS.getValor());
        Integer minsToReport = Integer.valueOf(parametrosSgtDTO.getValor());
        return proceso.getFechaFinEjecucion() == null
                || Utilerias.getDateDiff(proceso.getFechaFinEjecucion(), oracleDAO.consultarFechaActual(), TimeUnit.MINUTES) >= minsToReport;
    }

    @Override
    public void afterPropertiesSet() {
        try {
            logger.debug("Iniciando estados con el bean..." + procesoDAO);
            procesoDAO.iniciarEstados();
            logger.debug("Iniciando estados - Hecho!");
        } catch (SeguimientoDAOException ex) {
            logger.error(ex.toString());
        }
    }
}
