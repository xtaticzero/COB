package mx.gob.sat.siat.cob.seguimiento.service.otros;


import java.util.List;
import java.util.Map;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaAprobarGrupo;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaAprobarReporteDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ReporteJasperAfectacionDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ReporteVigilancia;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public interface ReporterService {
    byte[] makeReport(ReporteJasperAfectacionDTO jasperReportAfectacion) throws SGTServiceException;
    
    void makeLocalReportJASPER(String pathToFile, Map parametersReport);
    
    void makeReportFacturaEF(String pathToFile, Map<String, Object> parametersReport,JRBeanCollectionDataSource lstBeanDataSource);
    
    byte[] makeReport(ReporteVigilancia reporteVigilancia) throws SGTServiceException;
    
    byte[] makeReport(MultaAprobarGrupo multaAprobarGrupo,
                        List<MultaAprobarReporteDTO> multasFirmadas,
                        List<MultaAprobarReporteDTO> multasNoGeneradas,
                        List<MultaAprobarReporteDTO> multasTrasladadas) throws SGTServiceException;
    
    void makeReport(MultaAprobarGrupo multaAprobarGrupo,
                        List<MultaAprobarReporteDTO> multasFirmadas,
                        List<MultaAprobarReporteDTO> multasNoGeneradas,
                        List<MultaAprobarReporteDTO> multasTrasladadas,String fileFullName) throws SGTServiceException;
}
