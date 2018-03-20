/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.aprobacion;

import java.util.List;
import java.util.Map;
import mx.gob.sat.siat.cob.seguimiento.dto.db2.Paginador;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AdministrativoNivelCargo;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DocumentoAprobar;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ReporteAprobacionVigilancia;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaAdministracionLocal;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;

/**
 *
 * @author emmanuel
 */
public interface AprobarVigilanciaDelegateService {

    List<DocumentoAprobar> listarDocumentosIcepPorAprobar(String numeroCarga,
            Paginador paginador,
            String numeroEmpleado) throws SGTServiceException;

    AdministrativoNivelCargo buscarCargoAdministrativo(String numeroEmpleado) throws SGTServiceException;

    Paginador crearPaginadorIceps(String numeroCarga, String numeroEmpleado, int numeroFilas) throws SGTServiceException;

    ReporteAprobacionVigilancia buscarDatos(VigilanciaAdministracionLocal vigilancia,
            AdministrativoNivelCargo adminitrativo) throws SGTServiceException;

    Map<String, Object> llenarParametros(String numeroEmpleado,
            ReporteAprobacionVigilancia reporte,
            VigilanciaAdministracionLocal vigilancia) throws SGTServiceException;
}
