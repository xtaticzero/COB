package mx.gob.sat.siat.cob.seguimiento.service.otros;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.ComboStr;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AdministrativoNivelCargo;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.FuncionarioSat;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EventoCargaEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;

public interface AdministracionFuncionariosService {
    
    boolean validarFuncionarioExistente(FuncionarioSat empleado,List<FuncionarioSat> empleados);
    boolean buscarEmpleadosNumEvento(FuncionarioSat empleado,List<FuncionarioSat> empleados);
    List<FuncionarioSat> buscarFuncionarios();
    void eliminarRegistroFuncionario(String numeroEmpleado, Integer eventoCarga);
    void actualizarRegistroFuncionario(FuncionarioSat funcionario) throws SGTServiceException;
    void habilitarRegistroFuncionario(FuncionarioSat numeroEmpleado) throws SGTServiceException ;
    void guardarFuncionario(FuncionarioSat funcionario) throws SGTServiceException;
    List<CatalogoBase> buscarCargosAdministrativos();
    List<CatalogoBase> buscarNivelesEmision();
    List<CatalogoBase> buscarEventoCarga(Integer nivelEmision);
    List<ComboStr> buscarRegionALR();
    List<CatalogoBase> buscarCargoPorNivelEmision(Integer idNivelEmision);
    List<ComboStr> obtenerEmpleadosAdministrativo() throws SGTServiceException;
    AdministrativoNivelCargo buscarCargoAdministrativo(String numeroEmpleado, EventoCargaEnum eventoCargaEnum) throws SGTServiceException;
}
