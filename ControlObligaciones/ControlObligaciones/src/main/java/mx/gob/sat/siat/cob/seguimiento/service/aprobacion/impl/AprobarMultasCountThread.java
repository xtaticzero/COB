/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.aprobacion.impl;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AdministrativoNivelCargo;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaAprobarGrupo;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.aprobacion.AprobarMultasService;
import mx.gob.sat.siat.cob.seguimiento.service.aprobacion.AprobarVigilanciasService;
import mx.gob.sat.siat.cob.seguimiento.util.IntegerMutable;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author emmanuel
 */
@Component
@Scope("prototype")
public class AprobarMultasCountThread extends Thread {

    @Autowired
    private AprobarMultasService aprobarMultasService;
    private IntegerMutable progress;
    private MultaAprobarGrupo multaAprobarGrupo;
    private AdministrativoNivelCargo administrativoNivelCargo;
    private Integer numeroElementos;
    private final Logger logger = Logger.getLogger(AprobarMultasCountThread.class);
    private SGTServiceException exception;

    public void establecerValoresEjecucion(MultaAprobarGrupo multaAprobarGrupo,
                                            AdministrativoNivelCargo administrativoNivelCargo,
                                            IntegerMutable progress) {
        this.multaAprobarGrupo = multaAprobarGrupo;
        this.administrativoNivelCargo = administrativoNivelCargo;
        this.progress = progress;
        this.numeroElementos = 0;
    }

    @Override
    public void run() {
        try {
            this.numeroElementos=aprobarMultasService.countElementosVisualisar(multaAprobarGrupo, administrativoNivelCargo, progress);
        } catch (SGTServiceException e) {
            exception = e;
            logger.error(e, e);
        } finally {
            synchronized (progress) {
                progress.setValor(AprobarVigilanciasService.PORCENTAJE_TOTAL);
                progress.notifyAll();
            }
        }
    }

    public SGTServiceException getException() {
        return exception;
    }

    public Integer getNumeroElementos() {
        return numeroElementos;
    }

    public void setNumeroElementos(Integer numeroElementos) {
        this.numeroElementos = numeroElementos;
    }

}
