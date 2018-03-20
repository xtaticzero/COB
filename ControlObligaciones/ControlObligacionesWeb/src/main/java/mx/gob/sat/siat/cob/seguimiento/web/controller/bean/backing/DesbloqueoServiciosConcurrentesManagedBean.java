/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ElementoConcurrente;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.otros.ConcurrenceService;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import mx.gob.sat.siat.cob.seguimiento.utils.AbstractBaseMB;
import mx.gob.sat.siat.exception.AccesoDenegadoException;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;
import mx.gob.sat.siat.util.MovimientoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author root
 */
@Controller("desbloqueoServiciosConcurrentesMB")
@Scope(value = "view")
public class DesbloqueoServiciosConcurrentesManagedBean extends AbstractBaseMB {

    @Autowired
    private ConcurrenceService concurrenceService;
    private List<ElementoConcurrente> listaElementosConcurrentes;

    public DesbloqueoServiciosConcurrentesManagedBean() {
    }

    @PostConstruct
    public void init() {
        try {
            obtenerAccesoUsrEmpleados();
            autorizar(ConstantsCatalogos.IDENTIFICADOR_DESBLOQUEO_SERV_CONCURRENTES);
            cargarPagina();
        } catch (SGTServiceException ex) {
            this.getLogger().error(ex);
            addErrorMessage("Error", ex.getMessage());
        }
    }

    private void cargarPagina() throws SGTServiceException {
        listaElementosConcurrentes = concurrenceService.obtenerElementosBloqueados();
    }

    public List<ElementoConcurrente> getListaElementosConcurrentes() {
        return listaElementosConcurrentes;
    }

    public void desbloqueo() {
        try {
            SegbMovimientoDTO dto = MovimientoUtil.addMovimiento(getSession(), ConstantsCatalogos.CVE_SISTEMA, ConstantsCatalogos.IDENTIFICADOR_DESBLOQUEO_SERV_CONCURRENTES, new Date(), new Date(),
                    ConstantsCatalogos.MODIFICACION_MOV_DESBLOQUEO_FIRMA, ConstantsCatalogos.DESBLOQUEO_SERV_CONCURRENTES_OBS);
            List<ElementoConcurrente> listaAuxiliar = new ArrayList<ElementoConcurrente>();
            for (ElementoConcurrente elementoConcurrente : listaElementosConcurrentes) {
                if (elementoConcurrente.isChecado()) {
                    listaAuxiliar.add(elementoConcurrente);
                }
            }
            try {
                if (!listaAuxiliar.isEmpty()) {
                    concurrenceService.desbloqueoManual(listaAuxiliar, dto);
                    cargarPagina();
                    this.addMessage("Elementos desbloqueados", "Se lograron desbloquear los elementos con éxito.");
                }
            } catch (SGTServiceException ex) {
                this.getLogger().error(ex);
                addErrorMessage("Error al desbloquear los elementos", "Error al desbloquear los elementos");
            }
        } catch (AccesoDenegadoException e) {
            getLogger().error(e.getMessage());
            addErrorMessage("Error al desbloquear los elementos", "Error al desbloquear los elementos");
        }
    }

}
