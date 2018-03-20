/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.aprobacion;

import java.io.InputStream;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.EstadoDocumentoDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.db2.Paginador;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DocumentoAprobar;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.IcepsAprobar;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ParametrosValidacionCumplimiento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaAdministracionLocal;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaAprobar;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciasLogDTO;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.util.IntegerMutable;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author root
 */
public interface AprobarVigilanciasService {

    int REGISTROS_A_PROCESAR = 50000;
    int REGISTROS_A_PROCESAR_CUMPLIMIENTO = 1500;
    int PORCENTAJE_TOTAL = 100;
    int AVANCE = 10;
    int VALOR_INICIAL = 0;
    int MITAD = 2;
    String PATH = "/siat/cob/tmp/";
    String PIPE = "|";
    String SALTO_DE_LINEA = "\n";

    List<VigilanciaAprobar> listarVigilanciasPorAprobar(String numeroEmpleado) throws SGTServiceException;

    List<DocumentoAprobar> listarDocumentosPorAprobar(String numeroCarga,
            Paginador paginador,
            String numeroEmpleado, String filtroRFC) throws SGTServiceException;

    Paginador crearPaginador(String numeroCarga, String numeroEmpleado, int numeroFilas) throws SGTServiceException;

    Paginador crearPaginador(String numeroCarga, String numeroEmpleado, int numeroFilas, String filtro) throws SGTServiceException;

    Paginador crearPaginadorIcep(String numeroCarga, String numeroEmpleado, int numeroFilas) throws SGTServiceException;

    void verificarCumplimientos(String numeroCarga, String numeroEmpleado, IntegerMutable progress) throws DataAccessException, SGTServiceException;

    List<IcepsAprobar> listarIcepsPagina(String numeroCarga, Paginador paginador, String numeroEmpleado) throws SGTServiceException;

    InputStream generarArchivoIceps(String numeroCarga, String numeroEmpleado, IntegerMutable progreso) throws SGTServiceException;

    void aprobarVigilancia(VigilanciaAdministracionLocal vigilancia,
            String numeroEmpleado,
            IntegerMutable progress) throws SGTServiceException;

    void removerProcesoAprobar(VigilanciaAdministracionLocal vigilancia, String numeroEmpleado);

    void remover(String numeroCarga, String numeroEmpleado);

    boolean validarEnProceso(String numeroCarga, String numeroEmpleado);

    List<ParametrosValidacionCumplimiento> getParametrosEnProcesoValidacion();

    List<ParametrosValidacionCumplimiento> getParametrosProcesoAprobacion();

    byte[] obtenerPlantilla(int idPlantilla) throws SGTServiceException;

    void guardaErrorAprobacion(String numeroEmpleado, VigilanciaAdministracionLocal vigilancia, String descripcionError);

    String obtenerError(VigilanciaAdministracionLocal vigilancia);

    List<VigilanciasLogDTO> getVigilanciaConError(String numeroEmpleado, List<VigilanciaAdministracionLocal> lstVigilanciasXaprobar);

    Integer cleanVigilanciaError(VigilanciaAdministracionLocal vigAdmLoc);

    List<EstadoDocumentoDTO> getListaActEstado();

    void setListaActEstado(List<EstadoDocumentoDTO> listaActEstado);

    Long obtenerCantidadVigilanciasAL() throws SGTServiceException;

    List<VigilanciaAprobar> obtenerVigilanciasPaginadas(Paginador paginador) throws SGTServiceException;
}
