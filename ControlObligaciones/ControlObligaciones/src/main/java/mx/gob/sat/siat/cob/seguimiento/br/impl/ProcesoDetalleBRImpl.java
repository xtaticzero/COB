package mx.gob.sat.siat.cob.seguimiento.br.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.br.ProcesoDetalleBR;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.ProcesoDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.ErrorCampo;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.Notificacion;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ConsultarProcesosFiltroDto;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Proceso;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ProcesoDetalle;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoProceso;
import mx.gob.sat.siat.cob.seguimiento.exception.BusinessException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author root
 */
@Service
public class ProcesoDetalleBRImpl implements ProcesoDetalleBR {

    @Autowired
    private ProcesoDAO procesoDao;

    /**
     *
     */
    public ProcesoDetalleBRImpl() {
        super();
    }

    /**
     *
     * @param procesoDetalle
     * @throws BusinessException
     * @throws SeguimientoDAOException
     */
    @Override
    public void validarCreacion(ProcesoDetalle procesoDetalle) throws BusinessException, SeguimientoDAOException {
        List<ErrorCampo> errores = new ArrayList<ErrorCampo>();
        if (entradaNoNula(procesoDetalle)) {
            Proceso proceso = procesoDetalle.getProceso();

            if (isHeader(proceso) && proceso.getPrioridad() == null) {
                errores.add(new ErrorCampo("proceso.prioridad", "La prioridad no debe ser nula cuando se trata de un proceso sin Lanzadores"));
            }

            if (proceso.getNombre() == null || procesoDetalle.getProceso().getNombre().length() == 0) {
                errores.add(new ErrorCampo("proceso.nombre", "Campo no debe ser nulo"));
            }

            if (proceso.getProgramacion() != null && procesoDetalle.getProceso().getProgramacion().length() > 0) {
                if (!Utilerias.isExpresionCronValida(procesoDetalle.getProceso().getProgramacion())) {
                    errores.add(new ErrorCampo("proceso.programacion", "Expresión cron incorrecta"));
                }
            }

            if (proceso.getIntentosMax() < 1 || proceso.getIntentosMax() > 9) {
                errores.add(new ErrorCampo("proceso.intentosmax", "El campo permite valores entre 1 y 9"));
            }

            if (isHeader(proceso) && proceso.getTipoCadena() == null) {
                errores.add(new ErrorCampo("proceso.tipoProcesamiento", "El tipo de procesamiento no debe ser nulo cuando se trata de un proceso sin Lanzadores"));
            }

            if (errores.size() > 0) {
                throw new BusinessException(new Notificacion("Solicitud Incorrecta", "La información proporcionada no es correcta", Notificacion.ERROR), errores);
            }

        } else {
            throw new BusinessException("Solicitud Incorrecta");
        }
    }

    /**
     *
     * @param procesoDetalle
     * @throws BusinessException
     * @throws SeguimientoDAOException
     */
    @Override
    public void validarEdicion(ProcesoDetalle procesoDetalle) throws BusinessException, SeguimientoDAOException {
        List<ErrorCampo> errores = new ArrayList<ErrorCampo>();
        if (entradaNoNula(procesoDetalle)) {
            Proceso proceso = procesoDetalle.getProceso();
            if (isHeader(proceso) && proceso.getPrioridad().equals(ConstantsCatalogos.CERO)) {
                errores.add(new ErrorCampo("proceso.prioridad", "La prioridad no debe ser nula cuando se trata de un proceso sin Lanzadores"));
            }

            if (proceso.getNombre() == null || procesoDetalle.getProceso().getNombre().length() == 0) {
                errores.add(new ErrorCampo("proceso.nombre", "Campo no debe ser nulo"));
            }

            if (proceso.getProgramacion() != null && procesoDetalle.getProceso().getProgramacion().length() > 0) {
                if (!Utilerias.isExpresionCronValida(procesoDetalle.getProceso().getProgramacion())) {
                    errores.add(new ErrorCampo("proceso.programacion", "Expresión cron incorrecta"));
                }
            }

            if (isHeader(proceso) && proceso.getTipoCadena() == null) {
                errores.add(new ErrorCampo("proceso.tipoProcesamiento", "El tipo de procesamiento no debe ser nulo cuando se trata de un proceso sin Lanzadores"));
            }
            ConsultarProcesosFiltroDto filtro = new ConsultarProcesosFiltroDto();
            filtro.setIdProceso(proceso.getIdProceso());
            Proceso actual = procesoDao.consultarProcesos(filtro).get(0);

            if (!actual.getEstado().equals(proceso.getEstado())) {
                //Cambio de estado
                StringBuilder tooltip = new StringBuilder("");
                if (!isEstadoActualizable(proceso)) {
                    tooltip.append(" El proceso está siendo utilizado en este momento, no es posible cambiar su estado ");
                }
                if (tooltip.toString().length() > 0) {
                    errores.add(new ErrorCampo("proceso.estado", tooltip.toString()));
                }
            }

            if (errores.size() > 0) {
                throw new BusinessException(new Notificacion("Solicitud Incorrecta", "La información proporcionada no es correcta", Notificacion.ERROR), errores);
            }

        } else {
            throw new BusinessException("Solicitud Incorrecta");
        }
    }

    private boolean entradaNoNula(ProcesoDetalle procesoDetalle) {
        return (procesoDetalle != null && procesoDetalle.getProceso() != null);
    }

    private boolean isEstadoActualizable(Proceso proceso) {
        return proceso.getEstado() == EstadoProceso.HABILITADO.getIdEdoDoc() || proceso.getEstado() == EstadoProceso.INTENTOS_AGOTADOS.getIdEdoDoc() || proceso.getEstado() == EstadoProceso.DESHABILITADO.getIdEdoDoc() || proceso.getEstado() == EstadoProceso.POR_EJECUTAR.getIdEdoDoc();
    }

    private boolean isHeader(Proceso proceso) {
        return (proceso.getLanzador() == null || proceso.getLanzador().length() == 0);
    }
}
