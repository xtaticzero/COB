/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.listener.impl;

import java.util.List;
import mx.gob.sat.siat.cob.background.holder.CargaArchivosHolder;
import mx.gob.sat.siat.cob.background.holder.HolderCargaMasivaMap;
import mx.gob.sat.siat.cob.background.listener.DocumentoReadListenerHolder;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.MonitorArchivoArcaDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.DocumentoARCA;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.MonitorArchivoArca;
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
 * @author Juan
 */
@Service("documentoReadListenerHolder")
@Scope("step")
public class DocumentoReadListenerHolderImpl implements DocumentoReadListenerHolder {

    private static Logger log = Logger.getLogger(DocumentoReadListenerHolderImpl.class);
    @Autowired
    private HolderCargaMasivaMap holderCargaMasiva;
    @Autowired
    private MonitorArchivoArcaDAO monitorDAO;
    private String idVigilanciaAdministracionLocal;
    private Long situacionVigilancia;
    private Long idVigilancia;
    private String isEnvioMulta;

    public DocumentoReadListenerHolderImpl() {
        log.info("### DocumentoReadListenerHolderImpl");
    }

    @Override
    public void beforeWrite(List<? extends DocumentoARCA> list) {
    }

    @Override
    public void afterWrite(List<? extends DocumentoARCA> documentos) {
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
        holder.getDocumentosARCA().addAll(documentos);
        log.info("### Tamanio Documentos " + holder.getDocumentosARCA().size());
        actualizaMonitoreo(holder.getDocumentosARCA().size());

    }

    @Override
    public void onWriteError(Exception excptn, List<? extends DocumentoARCA> list) {
    }

    @Value("#{jobParameters['idAdmonLocal']}")
    public void setIdVigilanciaAdministracionLocal(String idVigilanciaAdministracionLocal) {
        this.idVigilanciaAdministracionLocal = idVigilanciaAdministracionLocal;
    }

    @Value("#{jobParameters['situacionVigilancia']}")
    public void setSituacionVigilancia(Long situacionVigilancia) {
        this.situacionVigilancia = situacionVigilancia;
    }

    @Value("#{jobParameters['idVigilancia']}")
    public void setIdVigilancia(Long idVigilancia) {
        this.idVigilancia = idVigilancia;
    }

    @Value("#{jobParameters['isEnvioMulta']}")
    public void setIsEnvioMulta(String isEnvioMulta) {
        this.isEnvioMulta = isEnvioMulta;
    }

    private void actualizaMonitoreo(Integer tamDocumentos) {
        try {
            MonitorArchivoArca monitor;
            if (isEnvioMulta.equals(String.valueOf(ConstantsCatalogos.UNO))) {
                monitor = monitorDAO.
                        consultarMonitorArchivoArca(idVigilancia, idVigilanciaAdministracionLocal,
                        ConstantsCatalogos.UNO);
            } else {
                monitor = monitorDAO.
                        consultarMonitorArchivoArca(idVigilancia, idVigilanciaAdministracionLocal,
                        ConstantsCatalogos.CERO);
            }
            if (monitor != null) {
                log.info("#### ACTUALIZAR CANTIDAD DOCUMENTOS");
                monitor.setCantidadDocumentos(tamDocumentos);
                monitorDAO.actualizaMonitorArchivoArca(monitor);
            } else {
                log.error("#### NO SE INSERTO ANTERIORMENTE");
            }
        } catch (SeguimientoDAOException ex) {
            log.error(ex);
        }
    }
}