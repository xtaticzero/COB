package mx.gob.sat.siat.cob.seguimiento.service.otros;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.ComboStr;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.BitacoraErrores;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.CargaVigilancia;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ParametrosSeguimiento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ParametrosSgtDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.PlantillaDocumento;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;

public interface SGTService {
    
    List<ParametrosSgtDTO> obtenerParametrosVigentesSgt();
    
    ParametrosSgtDTO obtenerParametroSgtPorIdParametro(int idParametro);
    
    List<ParametrosSgtDTO> obtenerParametrosSgt()  throws SGTServiceException;;

    String guardaVigilancia(CargaVigilancia vigilancia, SegbMovimientoDTO segMovDto) throws SGTServiceException;

    String guardaVigilancia(CargaVigilancia vigilancia) throws SGTServiceException;

    int guardaPlantilla(PlantillaDocumento plantilla) throws SGTServiceException;

    List<PlantillaDocumento> buscarPlantillasDocumento() throws SGTServiceException;

    Integer buscarEstadoSeguimientoActualPorRfc(String rfc) throws SGTServiceException;

    void actualizarEstadoSeguimiento(Integer enEjecucion, String rfc, SegbMovimientoDTO segMovDto) throws SGTServiceException;
    
    void guardarParametrosLiquidacion(String montoMinimo, String montoTotal)throws SGTServiceException;
    
    void guardarParametrosEF(ParametrosSgtDTO param)throws SGTServiceException;

    List<ParametrosSeguimiento> buscarParametrosVigentes() throws SGTServiceException;

    void guardarPlantillaARCA(PlantillaDocumento plantillaDocumento) throws SGTServiceException;
    
    Boolean existePlantillaARCA(PlantillaDocumento plantillaDocumento) throws SGTServiceException;

    /**
     *
     * @return
     * @throws SGTServiceException
     */
    List<PlantillaDocumento> buscarPlantillasDocumentoArca() throws SGTServiceException;

    /**
     *
     * @param plantillaDocumento
     * @param dto
     * @throws SGTServiceException
     */
    void actualizarEstadoPlantillaArca(PlantillaDocumento plantillaDocumento) throws SGTServiceException;

    /**
     *
     * @return
     * @throws SGTServiceException
     */
    List<CatalogoBase> buscarPlantillasDBArca() throws SGTServiceException;

    /**
     *
     * @return
     * @throws SGTServiceException
     */
    List<ComboStr> buscarTiposMotivo() throws SGTServiceException;

    /**
     *
     * @return
     * @throws SGTServiceException
     */
    List<CatalogoBase> getTipoFirma() throws SGTServiceException;

    /**
     *
     * @return
     * @throws SGTServiceException
     */
    List<CatalogoBase> buscarDiasMonto() throws SGTServiceException;

    /**
     *
     * @param idVigilancia
     * @param fechaInicio
     * @param fechaFin
     * @return
     * @throws SGTServiceException
     */
    List<BitacoraErrores> buscarBitacoraErroresPorIdVigilancia(Integer idVigilancia, String fechaInicio, String fechaFin) throws SGTServiceException;
    
    List<BitacoraErrores> buscarBitacoraErroresPorIdVigilanciaSinRutaBitacora(Integer idVigilancia, String fechaInicio, String fechaFin) throws SGTServiceException;

    List<ParametrosSgtDTO> obtenerParametrosSgt(String idParametro)throws SGTServiceException;
    
}
