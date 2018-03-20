/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.aprobacion;

import java.io.InputStream;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.db2.Paginador;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AdministrativoNivelCargo;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.CadenaOriginal;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaAprobar;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaAprobarDetalle;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaAprobarGrupo;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.util.IntegerMutable;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;

/**
 * Servicio que contiene los métodos para las funcionalidades de emisión de multas.
 * @author Gabriel García
 */
public interface AprobarMultasService {
    
    String MULTAS_EN_EJECUCION="Ya existe un proceso para el grupo de multas a emitir, por favor espere el correo de confirmación.";
    int PORCENTAJE_TOTAL = 100;
    /**
     * Obtiene el listado en resumen de las multas que pueden ser aprobadas, estan agrupadas por la fecha de emisión, medio de envio,
     * tipo de firma, tipo de multa y el número de multas en ese grupo
     * @param administrativoNivelCargo Datos del administrativo que está haciendo la consulta
     * @return Listado en resumen de las multas que pueden ser aprobadas
     * @throws SGTServiceException En caso de que exista error al hacer la consulta.
     */
    List<MultaAprobarGrupo> listarMultasAgrupadas(AdministrativoNivelCargo administrativoNivelCargo)throws SGTServiceException;
    /**
     * Obtiene el listado de multas individuales que podrán ser aprobadas.
     * @param multaAprobarGrupo Parámetros con los que serán buscadas las multas.
     * @param paginador Parámetros de paginación.
     * @param administrativoNivelCargo Datos del administrativo que está haciendo la consulta
     * @return Listado de multas a ser aprobadas.
     * @throws SGTServiceException En caso de que exista error al hacer la consulta.
     */
    List<MultaAprobar> listarMultasPorAprobar(MultaAprobarGrupo multaAprobarGrupo, Paginador paginador, AdministrativoNivelCargo administrativoNivelCargo) throws SGTServiceException;
    /**
     * Obtiene el listado de multas individuales que NO podrán ser aprobadas.
     * @param multaAprobarGrupo Parámetros con los que serán buscadas las multas.
     * @param administrativoNivelCargo Datos del administrativo que está haciendo la consulta
     * @return Listado de multas que no podrán ser aprobadas.
     * @throws SGTServiceException En caso de que exista error al hacer la consulta.
     */
    List<MultaAprobar> listarMultasNoAprobar(MultaAprobarGrupo multaAprobarGrupo, AdministrativoNivelCargo administrativoNivelCargo) throws SGTServiceException;
    /**
     * Genera un paginador para la consulta de multas
     * @param multaAprobarGrupo Parámetros con los que serán buscadas las multas.
     * @param numeroFilas Número de filas en cada página
     * @param administrativoNivelCargo Datos del administrativo que está haciendo la consulta
     * @return Paginador generado a partir de los datos a consultar.
     * @throws SGTServiceException Si no se puede obtener el total de registros.
     */
    Paginador crearPaginador(MultaAprobarGrupo multaAprobarGrupo, int numeroFilas, AdministrativoNivelCargo administrativoNivelCargo) throws SGTServiceException;
    /**
     * Obtiene el detalle de una multa individual.
     * @param multaAprobar Datos de la multa por la cual se buscará el detalle.
     * @return Listado de detalle de una multa individual.
     * @throws SGTServiceException En caso de que exista error al hacer la consulta.
     */
    List<MultaAprobarDetalle> listarDetallesMulta(MultaAprobar multaAprobar) throws SGTServiceException;
    /**
     * Genera la emisión de las multas
     * @param multaAprobarGrupo Parámetros con los que serán buscadas las multas.
     * @param segMovDto Contiene propiedades para poder generar la bitácora del movimiento.
     * @param administrativoNivelCargo Datos del administrativo que está haciendo la consulta
     * @return Ruta de archivo de resultados.
     * @throws SGTServiceException En caso de que haya habido un problema al emitir las multas o generar la bitácora.
     */
    String emitirMultas(MultaAprobarGrupo multaAprobarGrupo, SegbMovimientoDTO segMovDto, AdministrativoNivelCargo administrativoNivelCargo, IntegerMutable progress) throws SGTServiceException;
    /**
     * Genera un archivo con los datos del grupo de multas que fueron emitidas
     * @param multaAprobarGrupo Datos del grupo de multas que fueron emitidas.
     * @return Stream del archivo generado
     * @throws SGTServiceException En caso de que no se pueda generar el archivo.
     */
    InputStream generarArchivoEmision(String rutaArchivo) throws SGTServiceException;
    
    /**
     * Metodo para contar el numero de multas que seran cargadas para ser procesadas
     * @param multaAprobarGrupo
     * @param estadoMultaEnum
     * @param administrativoNivelCargo
     * @param progress
     * @return
     * @throws SGTServiceException 
     */
    Integer countElementosVisualisar(MultaAprobarGrupo multaAprobarGrupo,
                                    AdministrativoNivelCargo administrativoNivelCargo,
                                    IntegerMutable progress) throws SGTServiceException ;
    
    List<CadenaOriginal> listarCadenasOriginales(MultaAprobarGrupo multaAprobarGrupo, AdministrativoNivelCargo administrativoNivelCargo, 
                                                    Integer rowInicial, Integer rowFinal) throws SGTServiceException;
    
    Integer contarResolucionesAprobar(MultaAprobarGrupo multaAprobarGrupo, AdministrativoNivelCargo administrativoNivelCargo) throws SGTServiceException;
    /**
     * Elimina las firmas de multas que no estén aprobadas
     * @param multaAprobarGrupo
     * @param administrativoNivelCargo
     * @throws SGTServiceException 
     */
    void eliminaFirmasSinAprobar(MultaAprobarGrupo multaAprobarGrupo, AdministrativoNivelCargo administrativoNivelCargo) throws SGTServiceException;
    /**
     * Genera una cadena que servirá de firma para el bloqueo del proceso de ese grupo de multas
     * @return 
     */
    String generaFirma(MultaAprobarGrupo multaAprobarGrupo, AdministrativoNivelCargo administrativo);
}
