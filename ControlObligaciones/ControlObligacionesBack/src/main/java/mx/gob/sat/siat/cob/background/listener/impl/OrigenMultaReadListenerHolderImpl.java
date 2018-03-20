/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.listener.impl;

import java.util.List;
import mx.gob.sat.siat.cob.background.holder.CargaArchivosHolder;
import mx.gob.sat.siat.cob.background.holder.HolderCargaMasivaMap;
import mx.gob.sat.siat.cob.background.listener.OrigenMultaReadListenerHolder;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.MonitorArchivoArcaDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.MonitorArchivoArca;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.OrigenMulta;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaAdministracionLocal;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author root
 */
@Service("origenMultaReadListenerHolder")
@Scope("step")
public class OrigenMultaReadListenerHolderImpl implements OrigenMultaReadListenerHolder {

    private static Logger log = Logger.getLogger(OrigenMultaReadListenerHolderImpl.class);
    @Autowired
    private HolderCargaMasivaMap holderCargaMasiva;
    @Autowired
    private MonitorArchivoArcaDAO monitorDAO;
    private String idVigilanciaAdministracionLocal;
    private Long idVigilancia;
    private Long situacionVigilancia;

    public OrigenMultaReadListenerHolderImpl() {
        log.info("### OrigenMultaReadListenerHolderImpl");
    }

    @Override
    public void beforeWrite(List<? extends OrigenMulta> list) {
    }

    @Override
    public void afterWrite(List<? extends OrigenMulta> origenesMulta) {

        CargaArchivosHolder holder = (CargaArchivosHolder) holderCargaMasiva.getHolderCargaMasiva().get(idVigilancia + idVigilanciaAdministracionLocal);
        if (holder == null) {
            holder = new CargaArchivosHolder();
            VigilanciaAdministracionLocal vigilancia = new VigilanciaAdministracionLocal();
            vigilancia.setIdVigilancia(idVigilancia);
            vigilancia.setIdAdministracionLocal(idVigilanciaAdministracionLocal);
            vigilancia.setIdSituacionVigilancia(situacionVigilancia);

            holder.setVigilanciaAdministracionLocal(vigilancia);
            holderCargaMasiva.getHolderCargaMasiva().put(idVigilancia + idVigilanciaAdministracionLocal, holder);
        }
        holder.getOrigenesMulta().addAll(origenesMulta);
        log.info("### Tamanio Origen " + holder.getOrigenesMulta().size());
        actualizaMonitoreo(holder.getOrigenesMulta().size());
    }

    @Override
    public void onWriteError(Exception excptn, List<? extends OrigenMulta> list) {
    }

    public String getIdVigilanciaAdministracionLocal() {
        return idVigilanciaAdministracionLocal;
    }

    @Value("#{jobParameters['idAdmonLocal']}")
    public void setIdVigilanciaAdministracionLocal(String idVigilanciaAdministracionLocal) {
        this.idVigilanciaAdministracionLocal = idVigilanciaAdministracionLocal;
    }

    public Long getSituacionVigilancia() {
        return situacionVigilancia;
    }

    @Value("#{jobParameters['situacionVigilancia']}")
    public void setSituacionVigilancia(Long situacionVigilancia) {
        this.situacionVigilancia = situacionVigilancia;
    }

    public Long getIdVigilancia() {
        return idVigilancia;
    }

    @Value("#{jobParameters['idVigilancia']}")
    public void setIdVigilancia(Long idVigilancia) {
        this.idVigilancia = idVigilancia;
    }

    private void actualizaMonitoreo(Integer tamOrigen) {
        try {
            MonitorArchivoArca monitor = monitorDAO.
                    consultarMonitorArchivoArca(idVigilancia, idVigilanciaAdministracionLocal,
                    ConstantsCatalogos.UNO);
            if (monitor != null) {
                log.info("#### ACTUALIZAR CANTIDAD ORIGEN_MULTA");
                monitor.setCantidadOrigenMulta(tamOrigen);
                monitorDAO.actualizaMonitorArchivoArca(monitor);
            } else {
                log.error("#### NO SE INSERTO ANTERIORMENTE");
            }
        } catch (SeguimientoDAOException ex) {
            log.error(ex);
        }
    }
}