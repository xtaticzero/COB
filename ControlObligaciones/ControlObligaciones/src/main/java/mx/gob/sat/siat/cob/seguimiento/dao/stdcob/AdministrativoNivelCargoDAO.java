package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.AdministrativoNivelCargoSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.ComboStr;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AdministrativoNivelCargo;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.FuncionarioSat;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EventoCargaEnum;

/**
 *
 * @author root
 */
public interface AdministrativoNivelCargoDAO extends AdministrativoNivelCargoSQL{
    
    /**
     *
     * @return
     */
    List<ComboStr> buscarRegionALR();
    
    /**
     *
     * @param numeroEmpleado
     * @param evento
     * @return
     */
    AdministrativoNivelCargo buscarPorNumeroEmpleado(String numeroEmpleado,
            EventoCargaEnum evento);

    /**
     *
     * @param evento
     * @return
     */
    List<AdministrativoNivelCargo> buscarPorEventoCarga(EventoCargaEnum evento);
    
    /**
     *
     * @param eventoYEmision
     * @return
     */
    List<AdministrativoNivelCargo> buscarPorEventoCargaYNivelEmision(EventoCargaEnum evento);

     /**
     *
     * @param idVigilancia
     * @return
     */
    List<AdministrativoNivelCargo> getEmpleadosCentrales(int idVigilancia);

     /**
     *
     * @param idVigilancia
     * @return
     */
    List<AdministrativoNivelCargo> getEmpleadosLocales(int idVigilancia);
    
    /**
     *
     * @return
     */
    List<FuncionarioSat> buscarFuncionarios();

    /**
     *
     * @param numeroEmpleado
     */
    void eliminarRegistroFuncionario(String numeroEmpleado, Integer eventoCarga);

    /**
     *
     * @param numeroEmpleado
     */
    Integer habilitarRegistroFuncionario(String numeroEmpleado, Integer eventoCarga);

    /**
     *
     * @param funcionarioSat
     * @return
     */
    Integer guardarFuncionario(FuncionarioSat funcionarioSat);

    /**
     *
     * @param funcionarioSat
     * @return
     */
    Integer actualizarRegistroFuncionario(FuncionarioSat funcionarioSat);

    /**
     *
     * @return
     */
    List<CatalogoBase> buscarCargosAdministrativos();

    /**
     *
     * @return
     */
    List<CatalogoBase> buscarNivelesEmision();

    /**
     *
     * @return
     */
    List<CatalogoBase> buscarEventoCarga();

    /**
     *
     * @param idNivelEmision
     * @return
     */
    List<CatalogoBase> buscarCargoPorNivelEmision(Integer idNivelEmision);
    
    
    /**
     *
     * @return
     */
    List<ComboStr> obtenerEmpleadosAdministrativo();

    List<FuncionarioSat> buscarEmpleadosExistentes(FuncionarioSat funcionarioSat);

    void bajaEmpleadosExistentes(FuncionarioSat fn);
    
    List<FuncionarioSat> buscarEmpleadosNumEvento(FuncionarioSat funcionarioSat);
}
