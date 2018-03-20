/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.aprobacion.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.FuncionarioDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.AprobarMultasDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.ParametroSgtDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Funcionario;
import mx.gob.sat.siat.cob.seguimiento.dto.db2.Paginador;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AdministrativoNivelCargo;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.CadenaOriginal;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaAprobar;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaAprobarDetalle;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaAprobarGrupo;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaAprobarReporteDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ParametrosSgtDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoMultaEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.NivelEmisionEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.ParametroSgtEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoFirmaEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoMedioEnvioEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoMultaEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.aprobacion.AprobarMultasService;
import static mx.gob.sat.siat.cob.seguimiento.service.aprobacion.AprobarMultasService.PORCENTAJE_TOTAL;
import static mx.gob.sat.siat.cob.seguimiento.service.aprobacion.AprobarVigilanciasService.AVANCE;
import static mx.gob.sat.siat.cob.seguimiento.service.aprobacion.AprobarVigilanciasService.MITAD;
import static mx.gob.sat.siat.cob.seguimiento.service.aprobacion.AprobarVigilanciasService.VALOR_INICIAL;
import mx.gob.sat.siat.cob.seguimiento.service.otros.MailService;
import mx.gob.sat.siat.cob.seguimiento.service.otros.ReporterService;
import mx.gob.sat.siat.cob.seguimiento.util.IntegerMutable;
import mx.gob.sat.siat.cob.seguimiento.util.ProcesoEnvioEMail;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import mx.gob.sat.siat.cob.seguimiento.util.constante.TipoEnvioEnum;
import mx.gob.sat.siat.exception.DaoException;
import mx.gob.sat.siat.modelo.dao.SegbMovimientosDAO;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author root
 */
@Service("aprobarMultasService")
public class AprobarMultasServiceImpl implements AprobarMultasService {

    @Autowired
    private AprobarMultasDAO aprobarMultasDAO;
    @Autowired
    @Qualifier("segbMovimientosDAOImpl")
    private SegbMovimientosDAO segbMovimientosDAO;
    @Autowired
    private ReporterService reporterService;
    @Autowired
    private FuncionarioDAO funcionarioDAO;
    @Autowired
    private MailService mailService;
    @Autowired
    private ParametroSgtDAO parametroSgtDAO;
    @Autowired
    @Qualifier("taskExecutorMail")
    private ThreadPoolTaskExecutor taskExecutorMail;
    private final Logger log = Logger.getLogger(AprobarMultasServiceImpl.class);
    public static final String PATH = "/siat/cob/tmp/";
    private static final int AVANCE_20 = 20;
    private static final int AVANCE_40 = 40;
    private static final int AVANCE_60 = 60;
    private static final int AVANCE_80 = 80;
    private static final String[] TIPO_MULTA_CONSIDERAR = {
        TipoMultaEnum.INCUMPLIMIENTO.toString(),
        TipoMultaEnum.EXTEMPORANEIDAD.toString(),
        TipoMultaEnum.INCUMPLIMIENTO_EXTEMPORANEIDAD.toString(),
        TipoMultaEnum.COMPLEMENTARIA.toString()
    };

    @Override
    public List<MultaAprobarGrupo> listarMultasAgrupadas(AdministrativoNivelCargo administrativoNivelCargo) throws SGTServiceException {
        List<MultaAprobarGrupo> gruposMultas;
        if (administrativoNivelCargo == null) {
            throw new SGTServiceException("El funcionario no tiene permisos para emitir multas");
        }
        gruposMultas = aprobarMultasDAO.listarMultasAgrupadas(administrativoNivelCargo, EstadoMultaEnum.PENDIENTE_DE_PROCESAR,
                TipoMedioEnvioEnum.ENTIDAD_FEDERATIVA, TIPO_MULTA_CONSIDERAR);
        if (gruposMultas == null) {
            throw new SGTServiceException("No se pudo listar los grupos de multas, por favor intente mas tarde.");
        }
        return gruposMultas;
    }

    @Override
    public List<MultaAprobar> listarMultasPorAprobar(MultaAprobarGrupo multaAprobarGrupo, Paginador paginador, AdministrativoNivelCargo administrativoNivelCargo) throws SGTServiceException {
        List<MultaAprobar> listaMultas = aprobarMultasDAO.listarMultasPorAprobar(multaAprobarGrupo, paginador, administrativoNivelCargo, EstadoMultaEnum.PENDIENTE_DE_PROCESAR);
        if (listaMultas == null) {
            throw new SGTServiceException(
                    "No se pudieron listar las multas, por favor intente mas tarde.");
        }
        return listaMultas;
    }

    @Override
    public List<MultaAprobar> listarMultasNoAprobar(MultaAprobarGrupo multaAprobarGrupo, AdministrativoNivelCargo administrativoNivelCargo) throws SGTServiceException {
        List<MultaAprobar> listaMultas = aprobarMultasDAO.listarMultasNoAprobar(multaAprobarGrupo, administrativoNivelCargo, EstadoMultaEnum.PENDIENTE_DE_PROCESAR);
        if (listaMultas == null) {
            throw new SGTServiceException(
                    "No se pudieron listar las multas que no se pueden aprobar, por favor intente mas tarde.");
        }
        return listaMultas;
    }

    @Override
    public Paginador crearPaginador(MultaAprobarGrupo multaAprobarGrupo, int numeroFilas, AdministrativoNivelCargo administrativoNivelCargo) throws SGTServiceException {
        Integer numeroRegistros = aprobarMultasDAO.contarResolucionesAprobar(multaAprobarGrupo, EstadoMultaEnum.PENDIENTE_DE_PROCESAR, administrativoNivelCargo);
        if (numeroRegistros == null) {
            throw new SGTServiceException(
                    "No se pudo obtener el numero de registros, intente mas tarde");
        }
        return new Paginador(numeroRegistros, numeroFilas);
    }

    @Override
    public List<MultaAprobarDetalle> listarDetallesMulta(MultaAprobar multaAprobar) throws SGTServiceException {
        List<MultaAprobarDetalle> listaDetalle = aprobarMultasDAO.listarDetallesMulta(multaAprobar);
        if (listaDetalle == null) {
            throw new SGTServiceException(
                    "No se pudo listar el detalle de la multa, intente mas tarde");
        }
        return listaDetalle;
    }

    @Override
    @Transactional
    public String emitirMultas(MultaAprobarGrupo multaAprobarGrupo, SegbMovimientoDTO segMovDto,
            AdministrativoNivelCargo administrativoNivelCargo, IntegerMutable progress) throws SGTServiceException {
        List<MultaAprobarReporteDTO> multasTrasladadas = null;
        List<MultaAprobarReporteDTO> multasAemitir;
        List<MultaAprobarReporteDTO> multasNoGeneradas;
        String nombreArchivo;
        ParametrosSgtDTO parametrosSgtDTO;
        boolean trasladoMultas;
        Integer actualizacionLocales=0;


        //se actualizan las locales actuales
        parametrosSgtDTO = parametroSgtDAO.obtenerParametroSgtPorIdParametro(ParametroSgtEnum.CAMBIO_DOMICILIO_EMISION_MULTAS.getValor());
        if (parametrosSgtDTO == null) {
            throw new SGTServiceException("No se pudo obtener la bandera para cambio de domicilio.");
        }
        //si el tipo de firma es electrónica y es un usuario central, entonces ya se hizo previamente el traslado de locales
        trasladoMultas = parametrosSgtDTO.getValor().equals("1") 
                && !(multaAprobarGrupo.getIdTipoFirma().equals(TipoFirmaEnum.ELECTRONICA.getValor())
                && administrativoNivelCargo.getNivelEmision() == NivelEmisionEnum.CENTRAL);
        if (trasladoMultas) {
            log.info("Actualizando locales actuales");            
            
            //si el usuario es central, se actualiza la local actual y original
            if(administrativoNivelCargo.getNivelEmision() == NivelEmisionEnum.CENTRAL){
                actualizacionLocales = aprobarMultasDAO.actualizarLocalesActualesOrigen(multaAprobarGrupo, EstadoMultaEnum.PENDIENTE_DE_PROCESAR, administrativoNivelCargo);                            
            }else{
                actualizacionLocales = aprobarMultasDAO.actualizarLocalesActuales(multaAprobarGrupo, EstadoMultaEnum.PENDIENTE_DE_PROCESAR, administrativoNivelCargo);
            }
            if (actualizacionLocales == null) {
                throw new SGTServiceException("Error al actualizar las locales");
            }
            progress.setValor(AVANCE_20);            
        }
        if ((administrativoNivelCargo.getNivelEmision() == NivelEmisionEnum.LOCAL) && trasladoMultas) {
            log.debug("Obteniendo multas trasladadas");
            multasTrasladadas = aprobarMultasDAO.listaMultasTrasladadas(multaAprobarGrupo, EstadoMultaEnum.PENDIENTE_DE_PROCESAR, administrativoNivelCargo);
            if (multasTrasladadas == null) {
                throw new SGTServiceException(
                        "Error, no se pudieron obtener las multas trasladadas.");
            }
            //las firmas de las multas trasladadas no las vamos a necesitar en el proceso
            if (multaAprobarGrupo.getIdTipoFirma().equals(TipoFirmaEnum.ELECTRONICA.getValor())
                    && multasTrasladadas.size() > 0
                    && !(multasTrasladadas.size() == (aprobarMultasDAO.eliminaFirmasTrasladadas(multaAprobarGrupo, EstadoMultaEnum.PENDIENTE_DE_PROCESAR, administrativoNivelCargo)))) {
                throw new SGTServiceException(
                        "Error, no se pudieron eliminar las firmas de las multas trasladadas.");
            }
        }
        progress.setValor(AVANCE_40);
        //Se genera una lista de las resoluciones sin modificacion en su administracion local (son las que se van a emitir)
        //y otra de las que cambiaron
        //se determina el tipo de administracion
        Funcionario funcionario = new Funcionario();
        funcionario.setNumeroEmpleado(administrativoNivelCargo.getNumeroEmpleado());
        funcionario = funcionarioDAO.buscaFuncionarioPorID(funcionario);
        if (funcionario == null) {
            throw new SGTServiceException("Error, El funcionario no esta registrado");
        }
        multaAprobarGrupo.setTipoAdministracion(funcionario.getDescAreaAdscripcion());
        log.info("Obteniendo multas a emitir");
        multasAemitir = aprobarMultasDAO.listaMultasAemitir(multaAprobarGrupo, EstadoMultaEnum.PENDIENTE_DE_PROCESAR, administrativoNivelCargo);
        log.info("Obteniendo multas no generadas");
        multasNoGeneradas = aprobarMultasDAO.listaMultasNoGeneradas(multaAprobarGrupo, EstadoMultaEnum.PENDIENTE_DE_PROCESAR, administrativoNivelCargo);
        if (multasAemitir == null || multasNoGeneradas == null) {
            throw new SGTServiceException(
                    "No se pudieron obtener las multas a emitir");
        }
        //se hace la emisión de las multas
        log.info("Realizando la emisión de multas");
        Integer numeroRegistrosBitacora = aprobarMultasDAO.bitacoraCambioMultas(multaAprobarGrupo, EstadoMultaEnum.PENDIENTE_DE_PROCESAR, EstadoMultaEnum.AUTORIZADA, administrativoNivelCargo);
        Integer numeroRegistrosModificados = aprobarMultasDAO.emitirMultas(multaAprobarGrupo, EstadoMultaEnum.PENDIENTE_DE_PROCESAR, EstadoMultaEnum.AUTORIZADA, administrativoNivelCargo);
        //el numero de registros modificados debe ser el mismo que se insertaron en bitacora
        if (numeroRegistrosModificados == null
                || !numeroRegistrosModificados.equals(numeroRegistrosBitacora)) {
            throw new SGTServiceException(
                    "Error al emitir las multas.");
        }
        progress.setValor(AVANCE_60);
        if (trasladoMultas && actualizacionLocales>0
                && administrativoNivelCargo.getNivelEmision() == NivelEmisionEnum.LOCAL) {
            //se actualizan las locales originales para que puedan ser encontradas por otro funcionario en su local
            log.info("Actualizando las locales originales");
            Integer actualizacionOriginales = aprobarMultasDAO.actualizarLocalesOrigen(multaAprobarGrupo, EstadoMultaEnum.PENDIENTE_DE_PROCESAR, administrativoNivelCargo);
            if (actualizacionOriginales == null) {
                throw new SGTServiceException("Error al actualizar las locales");
            }
        }
        try {
            segbMovimientosDAO.insert(segMovDto);
        } catch (DaoException e) {
            throw new SGTServiceException(
                    "No se pudo hacer la emisión de las multas" + e);
        }
        progress.setValor(AVANCE_80);
        nombreArchivo = "emisionMultas_"
                + Utilerias.formatearFechaAAAAMMDD(multaAprobarGrupo.getFechaEmision()) + "_"
                + (administrativoNivelCargo.getNivelEmision() == NivelEmisionEnum.LOCAL ? administrativoNivelCargo.getIdAdministacionLocal() + "_" : "")
                + Utilerias.formatearFechaAAAAMMDDHHMMSS(new Date()) + ".pdf";
        log.info("Generando archivo de resultados");
        reporterService.makeReport(multaAprobarGrupo,
                multasAemitir,
                multasNoGeneradas,
                multasTrasladadas,
                PATH + nombreArchivo);
        envioCorreo(multaAprobarGrupo, administrativoNivelCargo, new File(PATH + nombreArchivo));
        log.debug("Termina proceso de emisión de multas");
        return nombreArchivo;
    }

    @Override
    public InputStream generarArchivoEmision(String nombreArchivo) throws SGTServiceException {
        try {
            File f = new File(PATH + nombreArchivo);
            return new FileInputStream(f);
        } catch (IOException ex) {
            throw new SGTServiceException("No se pudo crear el archivo de emisión de multas, por favor intente mas tarde", ex);
        }
    }

    private void envioCorreo(MultaAprobarGrupo multaAprobarGrupo,
            AdministrativoNivelCargo administrativoNivelCargo,
            File archivo) throws SGTServiceException {

        StringBuilder contenidoCorreo = new StringBuilder();
        contenidoCorreo.append("Usted ha emitido multas con fecha de registro ")
                .append(Utilerias.formatearFechaAAAAMMDD(multaAprobarGrupo.getFechaEmision())).append(", ")
                .append("conforme a los siguientes datos: </br>")
                .append("Medio de envio: ")
                .append(multaAprobarGrupo.getMedioEnvio()).append("</br>")
                .append("Tipo de firma: ")
                .append(multaAprobarGrupo.getTipoFirma()).append("</br>")
                .append("Tipo de multa: ")
                .append(multaAprobarGrupo.getTipoMulta()).append("</br></br>")
                .append("Se anexa la factura generada con el detalle de la emisión de multas del ")
                .append(Utilerias.formatearFechaAAAAMMDD(multaAprobarGrupo.getFechaEmision()))
                .append("</br>")
                .append("<p>Por favor no responda a este mensaje, fue enviado desde una cuenta de correo electr&oacute;nico no monitoreada.</p>");

        taskExecutorMail.execute(new ProcesoEnvioEMail(administrativoNivelCargo.getNumeroEmpleado(),
                "MAT CO - Emisión de multas",
                contenidoCorreo.toString(), mailService, TipoEnvioEnum.ID, archivo));

    }

    @Override
    @Transactional(readOnly = true)
    public Integer countElementosVisualisar(MultaAprobarGrupo multaAprobarGrupo, AdministrativoNivelCargo administrativoNivelCargo, IntegerMutable progress) throws SGTServiceException {
        Integer numeroElementos;
        progress.setValor(VALOR_INICIAL);
        try {
            progress.setValor(PORCENTAJE_TOTAL / MITAD);
            numeroElementos = aprobarMultasDAO.contarResolucionesAprobar(multaAprobarGrupo, EstadoMultaEnum.PENDIENTE_DE_PROCESAR, administrativoNivelCargo);
            if (numeroElementos <= 0) {
                progress.setValor(PORCENTAJE_TOTAL + AVANCE);
                throw new SGTServiceException("No se localizaron ningun elemento para la consulta");
            }

        } catch (Exception ex) {
            throw new SGTServiceException(ex);
        }
        progress.setValor(PORCENTAJE_TOTAL + AVANCE);
        return numeroElementos;
    }

    @Override
    @Transactional
    public List<CadenaOriginal> listarCadenasOriginales(MultaAprobarGrupo multaAprobarGrupo, AdministrativoNivelCargo administrativoNivelCargo,
            Integer rowInicial, Integer rowFinal) throws SGTServiceException {
        Funcionario funcionario = new Funcionario();
        
        //si el usuario es central, actualizamos las locales de una vez
        if (administrativoNivelCargo.getNivelEmision() == NivelEmisionEnum.CENTRAL){
            Integer localesActualesActualizadas = aprobarMultasDAO.actualizarLocalesActualesOrigen(multaAprobarGrupo, EstadoMultaEnum.PENDIENTE_DE_PROCESAR, 
                                                                                    administrativoNivelCargo, rowInicial, rowFinal);
            if(localesActualesActualizadas==null){
                throw new SGTServiceException(
                    "No se pudieron actualizar las locales de las multas");
            }
        }
        funcionario.setNumeroEmpleado(administrativoNivelCargo.getNumeroEmpleado());
        funcionario = funcionarioDAO.buscaFuncionarioPorID(funcionario);
        if (funcionario == null) {
            throw new SGTServiceException("Error, El funcionario no esta registrado");
        }
        List<CadenaOriginal> cadenasOriginales = aprobarMultasDAO.listarCadenasOriginales(multaAprobarGrupo,
                EstadoMultaEnum.PENDIENTE_DE_PROCESAR, administrativoNivelCargo,
                funcionario.getNombreFuncionario(), rowInicial, rowFinal);
        if (cadenasOriginales == null) {
            throw new SGTServiceException(
                    "No se pudieron listar las cadenas originales, por favor intente mas tarde.");
        }
        return cadenasOriginales;
    }

    @Override
    public Integer contarResolucionesAprobar(MultaAprobarGrupo multaAprobarGrupo, AdministrativoNivelCargo administrativoNivelCargo) throws SGTServiceException {
        Integer numeroElementos;
        numeroElementos = aprobarMultasDAO.contarResolucionesAprobar(multaAprobarGrupo, EstadoMultaEnum.PENDIENTE_DE_PROCESAR, administrativoNivelCargo);
        if (numeroElementos == null) {
            throw new SGTServiceException(
                    "No se pudo hacer el conteo de las multas por aprobar");
        }
        return numeroElementos;
    }

    @Override
    public void eliminaFirmasSinAprobar(MultaAprobarGrupo multaAprobarGrupo, AdministrativoNivelCargo administrativoNivelCargo) throws SGTServiceException {
        Integer numeroElementos;
        numeroElementos = aprobarMultasDAO.eliminaFirmasSinAprobar(multaAprobarGrupo, EstadoMultaEnum.PENDIENTE_DE_PROCESAR, administrativoNivelCargo);
        if (numeroElementos == null) {
            throw new SGTServiceException(
                    "No se pudieron eliminar las  firmas de multas pendientes de aprobar");
        }
    }

    @Override
    public String generaFirma(MultaAprobarGrupo multaAprobarGrupo, AdministrativoNivelCargo administrativo) {
        return Utilerias.formatearFechaAAAAMMDD(multaAprobarGrupo.getFechaEmision()) + "|"
                + multaAprobarGrupo.getIdTipoMulta() + "|"
                + multaAprobarGrupo.getIdMedioEnvio() + "|"
                + multaAprobarGrupo.getIdTipoFirma() + "|"
                + (administrativo.getNivelEmision() == NivelEmisionEnum.LOCAL
                ? administrativo.getIdAdministacionLocal() : "") + "|"
                + administrativo.getIdCargoAdministrativo();
    }
}
