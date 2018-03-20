package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import java.util.List;
import java.util.Map;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleReporteFacturaBean;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.RfcNombreDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.UbicacionEFDTO;

/**
 *
 * @author christian.ventura
 */
public interface OmisosEFDAO {

    /**
     * 
     * @param idVigilancia
     * @param identidadFederativa
     * @return
     */
    List<String> consultarFundamentoLegal(Integer idVigilancia, Integer identidadFederativa);

    /**
     * 
     * @param idVigilancia
     * @return
     */
    List<Integer> consultarListaEntidadesFed(Integer idVigilancia);

    /**
     *obtiene una lista de boid's junto con su nombre, rfc, fecha y su in federativa
     * a partir de un id vigilancia
     * @param idVigilancia
     * @return
     */
    List<RfcNombreDTO> consultarRfcNombre(Integer idVigilancia, int identidadFederativa);

    /**
     * obtiene un objeto con los datos de ubicacion de un boid que
     * se le manda como paretro
     * @param boid
     * @return
     */
    List<UbicacionEFDTO> consultarDatosUbicacion(String boid);

    /**
     * para insertar los archivos generados y mas datos en la tabla vigilanciaEF
     * @param idVigilancia
     * @param identidadFederativa 
     * @param archOmisos
     * @param archFundLegal
     * @param archFactura
     * @return
     */
    int insertaVigilanciaEF(int idVigilancia, int identidadFederativa,
            String archOmisos, String archFundLegal,String archFactura);

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
