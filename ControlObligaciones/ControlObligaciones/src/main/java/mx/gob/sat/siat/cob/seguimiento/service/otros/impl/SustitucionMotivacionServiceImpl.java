package mx.gob.sat.siat.cob.seguimiento.service.otros.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.cob.seguimiento.dto.arca.Obligacion;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.catalogos.MultaMontoService;
import mx.gob.sat.siat.cob.seguimiento.service.otros.SustitucionMotivacionService;
import mx.gob.sat.siat.cob.seguimiento.service.renuente.PeriodoXPeriodicidadService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author root
 */
@Service
public class SustitucionMotivacionServiceImpl implements SustitucionMotivacionService {

    private List<CatalogoBase> catalogoPeriodoXPeriodicidad;
    private List<CatalogoBase> catalogoOblisancion;
    private boolean hasCatalogos;
    private final Logger log = Logger.getLogger(SustitucionMotivacionServiceImpl.class);
    @Autowired
    private PeriodoXPeriodicidadService periodoService;
    @Autowired
    private MultaMontoService oblisancionService;

    /**
     *
     * @param obligacion
     */
    @Override
    public void sustituirMotivacion(Obligacion obligacion) throws SGTServiceException {
        Map<String, String> map = new HashMap<String, String>();
        String cadenaPeriodo = "";
        String cadenaMotivacion = "";
        cargarCatalogos();
        String periodoPeriodicidad = obligacion.getIdPeriodo() + "-" + obligacion.getIdPeriodicidad();
        for (CatalogoBase datoPeriodo : catalogoPeriodoXPeriodicidad) {
            if (periodoPeriodicidad.equals(datoPeriodo.getIdString())) {
                cadenaPeriodo = datoPeriodo.getNombre();
                break;
            }
        }
        map.put(":periodo:", cadenaPeriodo);
        map.put(":ejercicio:", obligacion.getEjercicio() + "");
        map.put(":numcontrol:", obligacion.getNumControl());
        map.put(":fechanotificacion:", obligacion.getFechaNotificacion());

        for (CatalogoBase datoMotivacion : catalogoOblisancion) {
            if (Integer.parseInt(obligacion.getIdObligacion()) == datoMotivacion.getId()
                    && datoMotivacion.getIdString().equals(obligacion.getConstanteResolMotivo())) {
                cadenaMotivacion = datoMotivacion.getNombre();
                break;
            }
        }
        if (cadenaMotivacion.isEmpty()) {
            throw new SGTServiceException("no se genero la sustitucion de la cadena motivacion");
        }
        for (Map.Entry<String, String> e : map.entrySet()) {
            cadenaMotivacion = cadenaMotivacion.replace(e.getKey(), e.getValue());
        }
        obligacion.setMotivacion(cadenaMotivacion);
    }

    /**
     *
     */
    public void cargarCatalogos() {
        if (!isHasCatalogos()) {
            try {
                catalogoPeriodoXPeriodicidad = periodoService.catalogoTodosLosPeriodos();
                catalogoOblisancion = oblisancionService.getOblisancionMotivaciones();
                setHasCatalogos(true);
            } catch (SGTServiceException ex) {
                log.error(ex);
            }
        }

    }

    /**
     *
     * @return
     */
    public boolean isHasCatalogos() {
        return hasCatalogos;
    }

    /**
     *
     * @param hasCatalogos
     */
    public void setHasCatalogos(boolean hasCatalogos) {
        this.hasCatalogos = hasCatalogos;
    }

    /**
     *
     * @return
     */
    public List<CatalogoBase> getCatalogoPeriodoXPeriodicidad() {
        return catalogoPeriodoXPeriodicidad;
    }

    /**
     *
     * @param catalogoPeriodoXPeriodicidad
     */
    public void setCatalogoPeriodoXPeriodicidad(List<CatalogoBase> catalogoPeriodoXPeriodicidad) {
        this.catalogoPeriodoXPeriodicidad = catalogoPeriodoXPeriodicidad;
    }

    /**
     *
     * @return
     */
    public List<CatalogoBase> getCatalogoOblisancion() {
        return catalogoOblisancion;
    }

    /**
     *
     * @param catalogoOblisancion
     */
    public void setCatalogoOblisancion(List<CatalogoBase> catalogoOblisancion) {
        this.catalogoOblisancion = catalogoOblisancion;
    }
}
