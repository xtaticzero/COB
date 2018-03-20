/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.db2.Paginador;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AdministrativoNivelCargo;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.CadenaOriginal;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaAprobar;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaAprobarDetalle;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaAprobarGrupo;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaAprobarReporteDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoMultaEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoMedioEnvioEnum;

/**
 * DAO que contiene los métodos para las funcionalidades de emisión de multas.
 *
 * @author Gabriel García
 */
public interface AprobarMultasDAO {

    /**
     * Obtiene el listado en resumen de las multas que pueden ser aprobadas,
     * estan agrupadas por la fecha de emisión, medio de envio, tipo de firma,
     * tipo de multa y el número de multas en ese grupo
     *
     * @param administrativoNivelCargo Datos del administrativo que está
     * haciendo la consulta
     * @param estadoMultaEnum Estado de la multa que será consultado
     * @param medioNoConsiderar Tipo de medio que no se va a considerar para la
     * consulta
     * @param tipoMultasConsiderar Tipos de multa que serán considerados para la
     * consulta
     * @return Listado en resumen de las multas que pueden ser aprobadas
     */
    List<MultaAprobarGrupo> listarMultasAgrupadas(AdministrativoNivelCargo administrativoNivelCargo, EstadoMultaEnum estadoMultaEnum, TipoMedioEnvioEnum medioNoConsiderar, String[] tipoMultasConsiderar);

    /**
     * Obtiene el listado de multas individuales que podrán ser aprobadas.
     *
     * @param multaAprobarGrupo Parámetros con los que serán buscadas las
     * multas.
     * @param paginador Parámetros de paginación.
     * @param administrativoNivelCargo Datos del administrativo que está
     * haciendo la consulta
     * @param estadoMultaEnum Estado de la multa que será consultado
     * @return Listado de multas a ser aprobadas.
     */
    List<MultaAprobar> listarMultasPorAprobar(MultaAprobarGrupo multaAprobarGrupo, Paginador paginador,
            AdministrativoNivelCargo administrativoNivelCargo, EstadoMultaEnum estadoMultaEnum);

    /**
     * Obtiene el listado de multas individuales que NO podrán ser aprobadas.
     *
     * @param multaAprobarGrupo Parámetros con los que serán buscadas las
     * multas.
     * @param administrativoNivelCargo Datos del administrativo que está
     * haciendo la consulta
     * @param estadoMultaEnum Estado de la multa que será consultado
     * @return Listado de multas que no podrán ser aprobadas.
     */
    List<MultaAprobar> listarMultasNoAprobar(MultaAprobarGrupo multaAprobarGrupo, AdministrativoNivelCargo administrativoNivelCargo,
            EstadoMultaEnum estadoMultaEnum);

    /**
     * Obtiene el detalle de una multa individual.
     *
     * @param multaAprobar Datos de la multa por la cual se buscará el detalle.
     * @return Listado de detalle de una multa individual.
     */
    List<MultaAprobarDetalle> listarDetallesMulta(MultaAprobar multaAprobar);

    /**
     * Genera la emisión de las multas
     *
     * @param multaAprobarGrupo Parámetros con los que serán buscadas las
     * multas.
     * @param estadoOrigen Estado origen de las multas que serán buscadas
     * @param estadoDestino Estado destino al que serán convertidas las multas
     * @param administrativoNivelCargo Datos del administrativo que está
     * haciendo la consulta
     * @return Número de multas emitidas
     */
    Integer emitirMultas(MultaAprobarGrupo multaAprobarGrupo,
            EstadoMultaEnum estadoOrigen, EstadoMultaEnum estadoDestino,
            AdministrativoNivelCargo administrativoNivelCargo);

    /**
     * Genera la bitácora de las multas emitidas
     *
     * @param multaAprobarGrupo Parámetros con los que serán buscadas las
     * multas.
     * @param estadoOrigen Estado origen de las multas que serán buscadas
     * @param estadoDestino Estado destino al que serán convertidas las multas
     * @param administrativoNivelCargo Datos del administrativo que está
     * haciendo la consulta
     * @return Número de registros dados de alta.
     */
    Integer bitacoraCambioMultas(MultaAprobarGrupo multaAprobarGrupo, EstadoMultaEnum estadoOrigen,
            EstadoMultaEnum estadoDestino, AdministrativoNivelCargo administrativoNivelCargo);

    /**
     * Devuelve el número de resoluciones que podrán ser aprobadas
     *
     * @param multaAprobarGrupo Parámetros con los que serán buscadas las
     * multas.
     * @param estadoMultaEnum Estado de la multa que será consultado
     * @param administrativoNivelCargo Datos del administrativo que está
     * haciendo la consulta
     * @return Número de resoluciones que podrán ser aprobadas
     */
    Integer contarResolucionesAprobar(MultaAprobarGrupo multaAprobarGrupo, EstadoMultaEnum estadoMultaEnum, AdministrativoNivelCargo administrativoNivelCargo);
    /*
     List<MultaAprobarReporteDTO> multasFirmadas,
     List<MultaAprobarReporteDTO> multasNoGeneradas,
     List<MultaAprobarReporteDTO> multasTrasladadas*/

    List<MultaAprobarReporteDTO> listaMultasAemitir(MultaAprobarGrupo multaAprobarGrupo, EstadoMultaEnum estadoMultaEnum, AdministrativoNivelCargo administrativoNivelCargo);

    List<MultaAprobarReporteDTO> listaMultasNoGeneradas(MultaAprobarGrupo multaAprobarGrupo, EstadoMultaEnum estadoMultaEnum, AdministrativoNivelCargo administrativoNivelCargo);

    List<MultaAprobarReporteDTO> listaMultasTrasladadas(MultaAprobarGrupo multaAprobarGrupo, EstadoMultaEnum estadoMultaEnum, AdministrativoNivelCargo administrativoNivelCargo);

    Integer actualizarLocalesOrigen(MultaAprobarGrupo multaAprobarGrupo, EstadoMultaEnum estadoMultaEnum, AdministrativoNivelCargo administrativoNivelCargo);

    Integer actualizarLocalesActuales(MultaAprobarGrupo multaAprobarGrupo, EstadoMultaEnum estadoMultaEnum, AdministrativoNivelCargo administrativoNivelCargo);
    
    Integer actualizarLocalesActualesOrigen(MultaAprobarGrupo multaAprobarGrupo, EstadoMultaEnum estadoMultaEnum, 
            AdministrativoNivelCargo administrativoNivelCargo);
    
    Integer actualizarLocalesActualesOrigen(MultaAprobarGrupo multaAprobarGrupo, EstadoMultaEnum estadoMultaEnum, 
            AdministrativoNivelCargo administrativoNivelCargo,
            Integer rowInicial, Integer rowFinal);

    List<CadenaOriginal> listarCadenasOriginales(MultaAprobarGrupo multaAprobarGrupo, EstadoMultaEnum estadoMultaEnum,
            AdministrativoNivelCargo administrativoNivelCargo, String nombreFuncionario,
            Integer rowInicial, Integer rowFinal);

    /**
     * Elimina las firmas generadas de las multas que fueron trasladadas
     *
     * @param multaAprobarGrupo
     * @param estadoMultaEnum
     * @param administrativoNivelCargo
     * @return
     */
    Integer eliminaFirmasTrasladadas(MultaAprobarGrupo multaAprobarGrupo, EstadoMultaEnum estadoMultaEnum, AdministrativoNivelCargo administrativoNivelCargo);

    Integer eliminaFirmasSinAprobar(MultaAprobarGrupo multaAprobarGrupo, EstadoMultaEnum estadoMultaEnum, AdministrativoNivelCargo administrativoNivelCargo);
}
