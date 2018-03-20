/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.service.afectaciones.impl;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import mx.gob.sat.siat.cob.background.service.afectaciones.CargaCumplimientoService;
import mx.gob.sat.siat.cob.background.service.afectaciones.ExtraccionCumplimientoService;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.EjecucionSeguimientoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.ParametroSgtDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ParametrosSgtDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EjecucionSeguimientoEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.ParametroSgtEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.CumplimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;

/**
 *
 * @author root
 */
@Service("extraccionCumplimientoService")
public class ExtraccionCumplimientoServiceImpl implements ExtraccionCumplimientoService {

    private Logger log = Logger.getLogger(ExtraccionCumplimientoServiceImpl.class);
    private Date fechaExtraccion;
    private ParametrosSgtDTO parametrosSgtDTO;
    @Autowired
    private ParametroSgtDAO parametroSgtDAO;
    @Autowired
    @Qualifier("cargaCumplimientoPaginadoService")
    private CargaCumplimientoService cargaCumplimientoPaginadoService;
    @Autowired
    @Qualifier("cargaCumplimientoService")
    private CargaCumplimientoService cargaCumplimientoService;
    @Autowired
    private CumplimientoService cumplimientoService;
    @Autowired
    private EjecucionSeguimientoDAO ejecucionSeguimientoDAO;

    @Override
    public void extraerCumplimientos() throws SGTServiceException {
        try {
            if (!preparacion()) {
                return;
            }
            log.info("Realizando carga de cumplimientos sin paginar");
            cargaCumplimientoService.cargarCumplimientos(fechaExtraccion);
            actualizarFechaExtraccion();
        } catch (SGTServiceException ex) {
            log.error(ex);
            Utilerias.getDetalleExcepcion(ex);
        }
    }

    @Override
    public void extraerCumplimientosPaginados() throws SGTServiceException {
        try {
            if (!preparacion()) {
                return;
            }
            log.info("Realizando carga de cumplimientos de manera paginada");
            cargaCumplimientoPaginadoService.cargarCumplimientos(fechaExtraccion);
            actualizarFechaExtraccion();
        } catch (SGTServiceException ex) {
            log.error(ex);
            Utilerias.getDetalleExcepcion(ex);
        }
    }

    private boolean obtenerFechaExtraccion() throws SGTServiceException {
        parametrosSgtDTO = parametroSgtDAO.obtenerParametroSgtPorIdParametro(ParametroSgtEnum.FECHA_ULTIMA_EJECUCION_CUMPLIMIENTO.getValor());

        Date fechaUltimoProcesamiento;
        fechaExtraccion = Utilerias.setFechaTrunk(Utilerias.getYesterday());

        if (parametrosSgtDTO.getValor() != null) {
            try {
                fechaUltimoProcesamiento = Utilerias.formatearFechaDDMMAAAAHHMM(parametrosSgtDTO.getValor());
                if (fechaUltimoProcesamiento.compareTo(fechaExtraccion) >= 0) {
                    log.error("La última fecha de extracción es de ayer o posterior.");
                    return false;
                } else {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(fechaUltimoProcesamiento);
                    calendar.add(Calendar.DAY_OF_YEAR, 1);
                    fechaExtraccion = calendar.getTime();
                }
            } catch (ParseException ex) {
                throw new SGTServiceException("Error de parseo de fecha\n" + ex);
            }
        }
        log.info("La fecha de extracción a trabajar es " + fechaExtraccion);
        return true;

    }

    private void actualizarFechaExtraccion() throws SGTServiceException {
        parametrosSgtDTO.setValor(Utilerias.formatearFechaDDMMYYYY(fechaExtraccion));
        try {
            log.info("Actualizando la fecha de extracción en el catálogo de procesos la cual es  " + fechaExtraccion);
            parametroSgtDAO.actualizarParametroSgt(parametrosSgtDTO);
            log.info("Se ha actualizado con éxito la fecha de extracción la cual es  " + fechaExtraccion);
        } catch (SeguimientoDAOException ex) {
            throw new SGTServiceException("Error al actualizar la fecha de extracción\n" + ex);
        }
    }
    //siempre se hace un borrado previo del día en que vamos a extraer los cumplimientos
    //por si hubo un fallo previamente

    private void eliminarCumplimientosActuales() throws SGTServiceException {
        log.info("la fecha con la que se va a borrar es " + fechaExtraccion);
        cumplimientoService.eliminarCumplimientosPorFechaMto(fechaExtraccion);
    }
    //determina si el proceso se debe ejecutar y determina la fecha de extraccion

    private boolean preparacion() throws SGTServiceException {
        EjecucionSeguimientoEnum ejecucionSeguimientoEnum;
        try {
            ejecucionSeguimientoEnum = ejecucionSeguimientoDAO.enEjecucion();
            if (ejecucionSeguimientoEnum == null || !ejecucionSeguimientoEnum.equals(EjecucionSeguimientoEnum.PROCESANDO)) {
                log.error("<--extraccionCumplimiento La bandera de procesamiento se encuentra inactiva");
                return false;
            }
        } catch (SeguimientoDAOException ex) {
            log.error(ex.toString());
            return false;
        }

        if(!obtenerFechaExtraccion()){
            return false;
        }
        log.info("Eliminando cumplimientos de la fecha a trabajar");
        eliminarCumplimientosActuales();
        return true;
    }
}
