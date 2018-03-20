package mx.gob.sat.siat.cob.seguimiento.service.carga;

import java.io.FileInputStream;
import java.io.InputStream;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MotRechazoVig;
import mx.gob.sat.siat.cob.seguimiento.dto.db2.Paginador;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaEntidadFederativa;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;

public interface CargaOmisosEntidadFederativaService {

    List<VigilanciaEntidadFederativa> obtenerVigilancias(Long claveEf) throws SGTServiceException;

    List<VigilanciaEntidadFederativa> obtenerVigilancias() throws SGTServiceException;

    List<VigilanciaEntidadFederativa> obtenerVigilanciasPaginadas(Paginador paginador) throws SGTServiceException;

    Long obtenerCantidadVigilancias() throws SGTServiceException;

    List<MotRechazoVig> obtenerMotivosRechazo() throws SGTServiceException;

    void guardarRechazo(VigilanciaEntidadFederativa vef, SegbMovimientoDTO dto, String numeroEmpleado) throws SGTServiceException;

    void aceptarVigilancia(VigilanciaEntidadFederativa vef, SegbMovimientoDTO dto) throws SGTServiceException;

    InputStream generarArchivoVigilanciaEF(String rutaArchivo) throws SGTServiceException;

    FileInputStream generarArchivoZip(String file) throws SGTServiceException;
}
