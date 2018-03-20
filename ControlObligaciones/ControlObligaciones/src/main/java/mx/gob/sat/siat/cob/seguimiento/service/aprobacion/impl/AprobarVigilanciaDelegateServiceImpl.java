/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.aprobacion.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.FuncionarioDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.AdministrativoNivelCargoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DocumentoAprobarDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.IcepsAprobarDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.ReporteAprobacionVigilanciaDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Funcionario;
import mx.gob.sat.siat.cob.seguimiento.dto.db2.Paginador;
import static mx.gob.sat.siat.cob.seguimiento.dto.report.enums.FacturaVigilanciaAprobadaEnum.CANCELADOS;
import static mx.gob.sat.siat.cob.seguimiento.dto.report.enums.FacturaVigilanciaAprobadaEnum.CUMPLIMIENTO;
import static mx.gob.sat.siat.cob.seguimiento.dto.report.enums.FacturaVigilanciaAprobadaEnum.DESCRIPCION_VIGILANCIA;
import static mx.gob.sat.siat.cob.seguimiento.dto.report.enums.FacturaVigilanciaAprobadaEnum.EXCLUIDOS_POR_RESPONSABLE;
import static mx.gob.sat.siat.cob.seguimiento.dto.report.enums.FacturaVigilanciaAprobadaEnum.FECHA_EMISION;
import static mx.gob.sat.siat.cob.seguimiento.dto.report.enums.FacturaVigilanciaAprobadaEnum.TIPO_ADMINISTRACION;
import static mx.gob.sat.siat.cob.seguimiento.dto.report.enums.FacturaVigilanciaAprobadaEnum.TOTAL;
import static mx.gob.sat.siat.cob.seguimiento.dto.report.enums.FacturaVigilanciaAprobadaEnum.TOTAL_DOCUMENTOS_PROCESADOS;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AdministrativoNivelCargo;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DocumentoAprobar;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ReporteAprobacionVigilancia;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaAdministracionLocal;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EventoCargaEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.aprobacion.AprobarVigilanciaDelegateService;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author emmanuel
 */
@Service
public class AprobarVigilanciaDelegateServiceImpl implements AprobarVigilanciaDelegateService {

    @Autowired
    private DocumentoAprobarDAO documentoAprobarDAO;
    @Autowired
    private AdministrativoNivelCargoDAO administrativoNivelCargoDAO;
    @Autowired
    private IcepsAprobarDAO icepsAprobarDAO;
    @Autowired
    private FuncionarioDAO funcionarioDAO;
    @Autowired
    private ReporteAprobacionVigilanciaDAO reporteAprobacionVigilanciaDAO;

    private final Logger log = Logger.getLogger(AprobarVigilanciaDelegateServiceImpl.class);

    /**
     * Este metodo recibe un objeto paginador con los datos de la pagina que se
     * desea obtener con respecto al numero de carga de una vigilancia y con los
     * privilegios de un empleado especifico
     *
     * @param numeroCarga Es el idVigilancia de la vigilancia a la que
     * pertenecen los documentos a buscar
     * @param paginador objeto que tiene el control de que pagina se require
     * @param numeroEmpleado Es el numero del empleado para el cual se
     * regresaran los documentos segun sus privilegios
     * @return
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException
     */
    @Override
    public List<DocumentoAprobar> listarDocumentosIcepPorAprobar(String numeroCarga,
            Paginador paginador,
            String numeroEmpleado) throws SGTServiceException {
        AdministrativoNivelCargo adminitrativo = buscarCargoAdministrativo(numeroEmpleado);
        List<DocumentoAprobar> lista = documentoAprobarDAO.
                listarDocumentosIcep(paginador,
                        numeroCarga,
                        adminitrativo.getIdAdministacionLocal());
        log.error("numero de local : [" + adminitrativo.getIdAdministacionLocal() + "]");
        return lista;
    }

    @Override
    public AdministrativoNivelCargo buscarCargoAdministrativo(String numeroEmpleado) throws SGTServiceException {
        AdministrativoNivelCargo adminitrativo
                = administrativoNivelCargoDAO.buscarPorNumeroEmpleado(
                        numeroEmpleado, EventoCargaEnum.CARGA_OMISOS);
        if (adminitrativo == null) {
            log.debug("El empleado no esta registrado");
            throw new SGTServiceException("El empleado no esta registrado");
        }
        return adminitrativo;
    }

    @Override
    @Transactional(readOnly = true)
    public Paginador crearPaginadorIceps(String numeroCarga, String numeroEmpleado, int numeroFilas) throws SGTServiceException {
        AdministrativoNivelCargo adminitrativo
                = buscarCargoAdministrativo(numeroEmpleado);
        Long numeroRegistros = icepsAprobarDAO.
                contarIceps(adminitrativo.getIdAdministacionLocal(),
                        numeroCarga);
        if (numeroRegistros == null) {
            log.debug("No se pudo obtener el numero de registros, intente mas tarde");
            throw new SGTServiceException(
                    "No se pudo obtener el numero de registros, intente mas tarde");
        }
        return new Paginador(numeroRegistros, numeroFilas);
    }

    @Override
    public ReporteAprobacionVigilancia buscarDatos(VigilanciaAdministracionLocal vigilancia,
            AdministrativoNivelCargo adminitrativo) throws SGTServiceException {
        ReporteAprobacionVigilancia reporte = reporteAprobacionVigilanciaDAO.consultarCifrasDeVigilancia(
                Long.parseLong(vigilancia.getNumeroCarga()), adminitrativo.getIdAdministacionLocal());
        if (reporte == null) {
            throw new SGTServiceException("No se pudo obtener los datos para generar la factura");
        }
        reporte.setDetalles(reporteAprobacionVigilanciaDAO.detallesReporte(
                Long.parseLong(vigilancia.getNumeroCarga()), adminitrativo.getIdAdministacionLocal()));
        //Se agregaron detalles a la descripcion del reporte
        reporte.setDetallesExcluidos(reporteAprobacionVigilanciaDAO.detallesExcluidosReporte(
                Long.parseLong(vigilancia.getNumeroCarga()), adminitrativo.getIdAdministacionLocal()));
        reporte.setDetallesCancelados(reporteAprobacionVigilanciaDAO.detallesCanceladosReporte(
                Long.parseLong(vigilancia.getNumeroCarga()), adminitrativo.getIdAdministacionLocal()));
        reporte.setDetallesCumplidos(reporteAprobacionVigilanciaDAO.detallesCumplidosReporte(
                Long.parseLong(vigilancia.getNumeroCarga()), adminitrativo.getIdAdministacionLocal()));

        if (reporte.getDetalles() == null) {
            log.debug("No se pudo obtener el detalle para generar la factura");
            throw new SGTServiceException("No se pudo obtener el detalle para generar la factura");
        }
        return reporte;
    }

    @Override

    public Map<String, Object> llenarParametros(String numeroEmpleado,
            ReporteAprobacionVigilancia reporte,
            VigilanciaAdministracionLocal vigilancia) throws SGTServiceException {
        Funcionario funcionario = new Funcionario();
        funcionario.setNumeroEmpleado(numeroEmpleado);
        funcionario = funcionarioDAO.buscaFuncionarioPorID(funcionario);
        if (funcionario == null) {
            log.debug("Error, El funcionario no esta registrado");
            throw new SGTServiceException("Error, El funcionario no esta registrado");
        }
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put(TIPO_ADMINISTRACION.getParametro(), funcionario.getDescAreaAdscripcion());
        parametros.put(DESCRIPCION_VIGILANCIA.getParametro(), vigilancia.getDescripcionVigilancia());
        parametros.put(FECHA_EMISION.getParametro(), Utilerias.formatearFechaDDMMYYYY(new Date()));
        parametros.put(EXCLUIDOS_POR_RESPONSABLE.getParametro(), reporte.getExcluidosPorResponsable());
        parametros.put(CANCELADOS.getParametro(), reporte.getCancelados());
        parametros.put(CUMPLIMIENTO.getParametro(), reporte.getCumplidos());
        parametros.put(TOTAL.getParametro(), reporte.getTotal());
        parametros.put(TOTAL_DOCUMENTOS_PROCESADOS.getParametro(), reporte.getDetalles().size());
        return parametros;
    }
}
