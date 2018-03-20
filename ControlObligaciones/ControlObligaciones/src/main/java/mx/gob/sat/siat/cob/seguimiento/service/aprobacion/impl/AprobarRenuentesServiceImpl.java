/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.aprobacion.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.FuncionarioDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.AprobarRenuentesDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Funcionario;
import mx.gob.sat.siat.cob.seguimiento.dto.db2.Paginador;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AdministrativoNivelCargo;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.CadenaOriginal;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VisualizaDetalleRenuente;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VisualizaDocumentoRenuente;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VisualizaVigilanciaRenuentes;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.NivelEmisionEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.SituacionIcepEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoServiciosConcurrentesEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.aprobacion.AprobarRenuentesService;
import mx.gob.sat.siat.cob.seguimiento.service.otros.ConcurrenceService;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import mx.gob.sat.siat.exception.DaoException;
import mx.gob.sat.siat.modelo.dao.SegbMovimientosDAO;

import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author root
 */
@Service
public class AprobarRenuentesServiceImpl implements AprobarRenuentesService {

    @Autowired
    private AprobarRenuentesDAO aprobarRenuentesDAO;
    @Autowired
    @Qualifier("segbMovimientosDAOImpl")
    private SegbMovimientosDAO segbMovimientosDAO;
    @Autowired
    private FuncionarioDAO funcionarioDAO;
    @Autowired
    private ConcurrenceService concurrenceServiceImpl;
    public static final String PATH = "/siat/cob/tmp/";
    private final Logger log = Logger.getLogger(AprobarRenuentesServiceImpl.class);

    @Override
    public List<VisualizaVigilanciaRenuentes> listarVigilanciaRenuentes(AdministrativoNivelCargo administrativoNivelCargo) throws SGTServiceException {
        List<VisualizaVigilanciaRenuentes> lista;
        if (administrativoNivelCargo == null) {
            throw new SGTServiceException("El funcionario no tiene permisos para aprobar los documentos renuentes");
        }
        lista = aprobarRenuentesDAO.listarVigilanciaRenuentes(administrativoNivelCargo);

        if (lista == null) {
            throw new SGTServiceException("No se pudo listar las vigilancias, por favor intente mas tarde.");
        }
        return lista;
    }

    @Override
    public List<VisualizaDocumentoRenuente> listarDocumentosRenuentes(VisualizaVigilanciaRenuentes visualizaVigilanciaRenuentes, Paginador paginador,
            AdministrativoNivelCargo administrativoNivelCargo) throws SGTServiceException {
        List<VisualizaDocumentoRenuente> lista = aprobarRenuentesDAO.listarDocumentosRenuentes(visualizaVigilanciaRenuentes, paginador, administrativoNivelCargo);
        if (lista == null) {
            throw new SGTServiceException(
                    "No se pudo listar los documentos, por favor intente mas tarde.");
        }
        return lista;
    }

    @Override
    public Paginador crearPaginador(VisualizaVigilanciaRenuentes visualizaVigilanciaRenuentes, int numeroFilas, AdministrativoNivelCargo administrativoNivelCargo) throws SGTServiceException {
        Integer numeroRegistros = aprobarRenuentesDAO.contarDocumentosRenuentes(visualizaVigilanciaRenuentes, administrativoNivelCargo);
        if (numeroRegistros == null) {
            throw new SGTServiceException(
                    "No se pudo obtener el numero de registros, intente mas tarde");
        }
        return new Paginador(numeroRegistros, numeroFilas);
    }

    @Override
    public List<VisualizaDetalleRenuente> listarDetallesRenuentes(VisualizaDocumentoRenuente visualizaDocumentoRenuente) throws SGTServiceException {
        List<VisualizaDetalleRenuente> lista = aprobarRenuentesDAO.listarDetallesRenuentes(visualizaDocumentoRenuente, SituacionIcepEnum.INCUMPLIDO);
        if (lista == null) {
            throw new SGTServiceException(
                    "No se pudo listar los detalle de documento, por favor intente mas tarde.");
        }
        return lista;
    }

    @Override
    @Transactional
    public Integer emitirDocumentos(VisualizaVigilanciaRenuentes visualizaVigilanciaRenuentes, SegbMovimientoDTO segMovDto, AdministrativoNivelCargo administrativoNivelCargo) throws SGTServiceException {
        String firma = generaFirma(visualizaVigilanciaRenuentes, administrativoNivelCargo);
        if (!concurrenceServiceImpl.lockServices(TipoServiciosConcurrentesEnum.IDENTIFICADOR_DOCUMENTOS_RENUENTES, firma)) {
            throw new SGTServiceException("Ya existe un proceso para emitir este grupo de documentos.");
        }
        try {
            Integer numeroVigilanciasActualizadas = aprobarRenuentesDAO.cambiaEstadoVigilancias(visualizaVigilanciaRenuentes, EstadoDocumentoEnum.PENDIENTE_DE_PROCESAR, EstadoDocumentoEnum.NO_GENERADO, administrativoNivelCargo);
            if (numeroVigilanciasActualizadas == null) {
                throw new SGTServiceException(
                        "No se pudieron volver las vigilancias enviadas a arca a situacion erronea.");
            }
            Integer numeroBitacoraEmisionFuncionario = aprobarRenuentesDAO.bitacoraEmisionFuncionario(visualizaVigilanciaRenuentes, EstadoDocumentoEnum.PENDIENTE_DE_PROCESAR, EstadoDocumentoEnum.NO_GENERADO, administrativoNivelCargo);
            if (numeroBitacoraEmisionFuncionario == null) {
                throw new SGTServiceException(
                        "No se pudo crear la bitacora para el funcionario que hizo la emisión.");
            }
            Integer numeroRegistrosBitacora = aprobarRenuentesDAO.bitacoraCambioDocumentos(visualizaVigilanciaRenuentes, EstadoDocumentoEnum.PENDIENTE_DE_PROCESAR, EstadoDocumentoEnum.NO_GENERADO, administrativoNivelCargo);
            Integer numeroRegistrosModificados = aprobarRenuentesDAO.emitirDocumentos(visualizaVigilanciaRenuentes, EstadoDocumentoEnum.PENDIENTE_DE_PROCESAR, EstadoDocumentoEnum.NO_GENERADO, administrativoNivelCargo);
            //el numero de registros modificados debe ser el mismo que se insertaron en bitacora
            if (numeroRegistrosModificados == null || numeroRegistrosModificados == 0
                    || !numeroRegistrosModificados.equals(numeroRegistrosBitacora)) {

                throw new SGTServiceException(
                        "No se pudo hacer la emisión de los documentos.");
            }
            try {
                segbMovimientosDAO.insert(segMovDto);
            } catch (DaoException e) {
                throw new SGTServiceException(
                        "No se pudo hacer la emisión de los documentos" + e);
            }
            return numeroRegistrosModificados;
        } finally {
            if (!concurrenceServiceImpl.unlockServices(TipoServiciosConcurrentesEnum.IDENTIFICADOR_DOCUMENTOS_RENUENTES, firma)) {
                throw new SGTServiceException("No se pudo hacer el desbloqueo del proceso de emision de documentos renuentes.");
            }
        }

    }

    @Override
    public InputStream generarArchivoEmision(VisualizaVigilanciaRenuentes visualizaVigilanciaRenuentes) throws SGTServiceException {
        String archivo = PATH + "emisionDocumentos" + Utilerias.formatearFechaAAAAMMDDHHMMSS(new Date()) + ".txt";
        StringBuilder contenido = new StringBuilder();
        try {
            FileOutputStream fos = new FileOutputStream(archivo, true);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
            BufferedWriter bf = new BufferedWriter(osw);
            contenido.append("Fecha de emision").append("|").append(Utilerias.formatearFechaAAAAMMDD(visualizaVigilanciaRenuentes.getFechaEmision())).append("\n");
            contenido.append("Medio de envio").append("|").append(visualizaVigilanciaRenuentes.getMedioEnvio()).append("\n");
            contenido.append("Tipo de firma").append("|").append(visualizaVigilanciaRenuentes.getTipoFirma()).append("\n");
            contenido.append("Tipo de documento").append("|").append(visualizaVigilanciaRenuentes.getTipoDocumento()).append("\n");
            contenido.append("Total de documentos").append("|").append(visualizaVigilanciaRenuentes.getCantidadDocumentos()).append("\n");
            contenido.append("Documentos emitidos").append("|").append(visualizaVigilanciaRenuentes.getCantidadDocumentosEmitidos());
            bf.write(contenido.toString());
            bf.flush();
            bf.close();
            osw.close();
            fos.close();

            File f = new File(archivo);
            return new FileInputStream(f);
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
            throw new SGTServiceException("No se pudo crear el archivo, por favor intente mas tarde", ex);
        }
    }

    @Override
    public List<CadenaOriginal> listarCadenasOriginales(VisualizaVigilanciaRenuentes visualizaVigilanciaRenuentes,
            AdministrativoNivelCargo administrativoNivelCargo,
            Integer rowInicial, Integer rowFinal) throws SGTServiceException {
        Funcionario funcionario = new Funcionario();
        funcionario.setNumeroEmpleado(administrativoNivelCargo.getNumeroEmpleado());
        funcionario = funcionarioDAO.buscaFuncionarioPorID(funcionario);
        if (funcionario == null) {
            throw new SGTServiceException("Error, El funcionario no esta registrado");
        }
        List<CadenaOriginal> cadenasOriginales = aprobarRenuentesDAO.listarCadenasOriginales(visualizaVigilanciaRenuentes, administrativoNivelCargo,
                funcionario.getNombreFuncionario(), rowInicial, rowFinal);
        if (cadenasOriginales == null) {
            throw new SGTServiceException(
                    "No se pudieron listar las cadenas originales, por favor intente mas tarde.");
        }
        return cadenasOriginales;
    }

    @Override
    public Integer contarDocumentosAprobar(VisualizaVigilanciaRenuentes visualizaVigilanciaRenuentes, AdministrativoNivelCargo administrativoNivelCargo) throws SGTServiceException {
        Integer numeroRegistros = aprobarRenuentesDAO.contarDocumentosRenuentes(visualizaVigilanciaRenuentes, administrativoNivelCargo);
        if (numeroRegistros == null) {
            throw new SGTServiceException(
                    "No se pudo obtener el numero de registros, intente mas tarde");
        }
        return numeroRegistros;
    }

    @Override
    public void eliminaFirmasSinAprobar(VisualizaVigilanciaRenuentes visualizaVigilanciaRenuentes, AdministrativoNivelCargo administrativoNivelCargo) throws SGTServiceException {
        Integer numeroElementos;
        numeroElementos = aprobarRenuentesDAO.eliminaFirmasSinAprobar(visualizaVigilanciaRenuentes, administrativoNivelCargo);
        if (numeroElementos == null) {
            throw new SGTServiceException(
                    "No se pudieron eliminar las  firmas de documentos renuentes pendientes de aprobar");
        }
    }

    @Override
    public String generaFirma(VisualizaVigilanciaRenuentes visualizaVigilanciaRenuentes, AdministrativoNivelCargo administrativo) {
        return Utilerias.formatearFechaAAAAMMDD(visualizaVigilanciaRenuentes.getFechaEmision()) + "|"
                + visualizaVigilanciaRenuentes.getIdTipoDocumento() + "|"
                + visualizaVigilanciaRenuentes.getIdMedioEnvio() + "|"
                + visualizaVigilanciaRenuentes.getIdTipoFirma() + "|"
                + (administrativo.getNivelEmision() == NivelEmisionEnum.LOCAL
                ? administrativo.getIdAdministacionLocal() : "") + "|"
                + administrativo.getIdCargoAdministrativo();
    }
}