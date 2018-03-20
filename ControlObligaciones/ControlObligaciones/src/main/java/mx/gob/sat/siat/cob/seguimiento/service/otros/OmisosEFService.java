package mx.gob.sat.siat.cob.seguimiento.service.otros;

import java.util.List;
import java.util.Map;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ConsultaVigilancia;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleReporteFacturaBean;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.RfcNombreDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.UbicacionEFDTO;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;

/**
 *
 * @author christian.ventura
 */
public interface OmisosEFService {

    /**
     * consulta las vigilancias que se han cargado y con estatus 1; Procedente
     * @return
     * @throws SeguimientoDAOException
     */
    List<ConsultaVigilancia> consultaVigilancias() throws SeguimientoDAOException;
    /**
     * consulta las vigilancias que se han cargado y con estatus 1; Procedente
     * @return
     * @throws SeguimientoDAOException
     */
    List<ConsultaVigilancia> consultaVigilanciasEF() throws SeguimientoDAOException;
    /**
     * consulta lista de boid relacionados a una vigilancia junto con mas datos
     * como rfc, nombre, etc
     * @param idVigilancia
     * @param identidadFederativa 
     * @return
     */
    List<RfcNombreDTO> consultaBoidDatosGenerales(int idVigilancia, int identidadFederativa);

    /**
     * consulta datos de ubicacion del boid que se le envia, estos datos son
     * de su direccion, calle, colonia, etc
     * @param pBoid
     * @return
     */
    List<UbicacionEFDTO> consultarDatosUbicacion(String pBoid);

    /**
     * consulta la lista de fundamentos legales relacionados a un id vigilancia
     * @param idVigilancia
     * @param identidadFederativa 
     * @return
     */
    List<String> consultarFundamentoLegal(int idVigilancia, Integer identidadFederativa);

    /**
     * 
     * @param idVigilancia
     * @return
     */
    List<Integer> consultarListaEntidadesFed(int idVigilancia);

    /**
     * inserta la informacion las rutas de los archivos generados asi como un conteo
     * de documentos por el id vigilancia que se le envia
     * @param idVigilancia
     * @param identidadFederativa 
     * @param archOmisos
     * @param archFundLegal
     * @return
     */
    int insertaVigilanciaEF(int idVigilancia, int identidadFederativa,
            String archOmisos, String archFundLegal, String archFactura);

    /**
     * actualiza el estatus de la vigilancia que se han cargado cambia a estatus 2; Procesado
     * @param idVigilancia
     * @throws SeguimientoDAOException
     */
    void actualizarVigilancia(int idVigilancia) throws SeguimientoDAOException;

    /**
     * consulta datos para encabezado del reporte de factura
     * @param idVigilancia
     * @param identidadFederativa
     * @return
     */
    Map<String, Object> consultarEncabezadoFactura(Integer idVigilancia, Integer identidadFederativa);
    
    /**
     * consulta datos para detalle del reporte de factura
     * @param idVigilancia
     * @param identidadFederativa
     * @return
     */
    List<DetalleReporteFacturaBean> consultarDetalleFactura(Integer idVigilancia, Integer identidadFederativa);
    
}
