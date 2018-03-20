/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.helper;

import java.io.Serializable;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.db2.Paginador;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaAdministracionLocal;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaAprobar;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaEntidadFederativa;

/**
 *
 * @author usuario
 */
public class RechazarVigilanciasHelper implements Serializable {

    private Paginador paginadorEntidadFederativa;
    private List<VigilanciaEntidadFederativa> vigilanciasEntidadFederativa;
    private VigilanciaEntidadFederativa vigilanciaRechazada;
    private Long paginaEntidadFederativa;

    private Paginador paginadorVigilanciasAdmonLocal;
    private List<VigilanciaAprobar> vigilanciasAdmonLocal;
    private VigilanciaAdministracionLocal vigilanciaLocal;
    private Long paginaAdmonLocal;

    public RechazarVigilanciasHelper() {
        vigilanciaLocal = new VigilanciaAdministracionLocal();
        vigilanciaRechazada = new VigilanciaEntidadFederativa();
    }

    public List<VigilanciaEntidadFederativa> getVigilanciasEntidadFederativa() {
        return vigilanciasEntidadFederativa;
    }

    public void setVigilanciasEntidadFederativa(List<VigilanciaEntidadFederativa> vigilanciasEntidadFederativa) {
        this.vigilanciasEntidadFederativa = vigilanciasEntidadFederativa;
    }

    public Paginador getPaginadorEntidadFederativa() {
        return paginadorEntidadFederativa;
    }

    public void setPaginadorEntidadFederativa(Paginador paginadorEntidadFederativa) {
        this.paginadorEntidadFederativa = paginadorEntidadFederativa;
    }

    public Long getPaginaEntidadFederativa() {
        return paginaEntidadFederativa;
    }

    public void setPaginaEntidadFederativa(Long paginaEntidadFederativa) {
        this.paginaEntidadFederativa = paginaEntidadFederativa;
    }

    public List<VigilanciaAprobar> getVigilanciasAdmonLocal() {
        return vigilanciasAdmonLocal;
    }

    public void setVigilanciasAdmonLocal(List<VigilanciaAprobar> vigilanciasAdmonLocal) {
        this.vigilanciasAdmonLocal = vigilanciasAdmonLocal;
    }

    public Paginador getPaginadorVigilanciasAdmonLocal() {
        return paginadorVigilanciasAdmonLocal;
    }

    public void setPaginadorVigilanciasAdmonLocal(Paginador paginadorVigilanciasAdmonLocal) {
        this.paginadorVigilanciasAdmonLocal = paginadorVigilanciasAdmonLocal;
    }

    public Long getPaginaAdmonLocal() {
        return paginaAdmonLocal;
    }

    public void setPaginaAdmonLocal(Long paginaAdmonLocal) {
        this.paginaAdmonLocal = paginaAdmonLocal;
    }

    public VigilanciaAdministracionLocal getVigilanciaLocal() {
        return vigilanciaLocal;
    }

    public void setVigilanciaLocal(VigilanciaAdministracionLocal vigilanciaLocal) {
        this.vigilanciaLocal = vigilanciaLocal;
    }

    public VigilanciaEntidadFederativa getVigilanciaRechazada() {
        return vigilanciaRechazada;
    }

    public void setVigilanciaRechazada(VigilanciaEntidadFederativa vigilanciaRechazada) {
        this.vigilanciaRechazada = vigilanciaRechazada;
    }
}
