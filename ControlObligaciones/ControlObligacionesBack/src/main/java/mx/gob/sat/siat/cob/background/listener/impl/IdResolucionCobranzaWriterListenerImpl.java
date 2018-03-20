package mx.gob.sat.siat.cob.background.listener.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mx.gob.sat.siat.cob.background.holder.CargaArchivosHolder;
import mx.gob.sat.siat.cob.background.listener.IdResolucionCobranzaWriterListener;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaDocumento;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import mx.gob.sat.siat.cobranza.domain.Resolucion;
import mx.gob.sat.siat.cobranza.registromasivo.api.RegistroMasivoFacade;
import mx.gob.sat.siat.cobranza.registromasivo.util.excepcion.RegistroMasivoException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author Daniel
 */
@Service("idResolucionCobranzaWriterListener")
@Scope("step")
public class IdResolucionCobranzaWriterListenerImpl implements IdResolucionCobranzaWriterListener {

    private Logger log = Logger.getLogger(IdResolucionCobranzaWriterListenerImpl.class);
    @Autowired(required = false)
    private RegistroMasivoFacade registroMasivoFacade;
    @Autowired
    private CargaArchivosHolder cargaArchivosHolder;
    private String esOperacionMat;

    /**
     *
     * @param resoluciones
     */
    @Override
    public void beforeWrite(List<? extends Resolucion> resoluciones) {
        if (esOperacionMat.equals("1")) {
            if (!resoluciones.isEmpty()) {
                log.info("Insertando en Cobranza tamanio de Resoluciones: " + resoluciones.size());
                try {

                    List<Resolucion> resolucionesClone = (List<Resolucion>) Utilerias.copyList(resoluciones);
                    Map loteResoluciones = registroMasivoFacade.insertarRegistroTemporalCM(new ArrayList(resoluciones));
                    if (loteResoluciones != null) {
                        List<Resolucion> resolucionesFail =
                                (List<Resolucion>) loteResoluciones.get(RegistroMasivoFacade.RESOLUCIONES);
                        Long idLote = (Long) loteResoluciones.get(RegistroMasivoFacade.IDLOTE);

                        log.debug("### NumerosResolucion no registrados: " + resolucionesFail.size());
                        log.info("### Numero de lote guardado: " + idLote);

                        if (resolucionesFail.size() == resolucionesClone.size()) {
                            resolucionesFail = new ArrayList<Resolucion>();
                        }

                        if (!resolucionesFail.isEmpty()) {
                            for (Resolucion resol : resolucionesFail) {
                                resolucionesClone.remove(resol);
                            }
                        }
                    }
                    List<MultaDocumento> multas = new ArrayList<MultaDocumento>();
                    for (Resolucion resolucion : resolucionesClone) {
                        MultaDocumento multaDocumento = new MultaDocumento();
                        multaDocumento.setNumeroResolucion(resolucion.getNumResolucionDet());
                        multaDocumento.setMonto(Double.parseDouble(resolucion.getImporteDet().toString()));
                        multas.add(multaDocumento);
                    }
                    cargaArchivosHolder.setMultasDocumentos(multas);
                } catch (RegistroMasivoException ex) {
                    log.error(ex);
                }
            }
        }
    }

    /**
     *
     * @param multasDocumento
     */
    @Override
    public void afterWrite(List<? extends Resolucion> resoluciones) {
    }

    /**
     *
     * @param excptn
     * @param list
     */
    @Override
    public void onWriteError(Exception excptn, List<? extends Resolucion> list) {
    }

    @Value("#{jobParameters['esOperacionMat']}")
    public void setEsOperacionMat(String esOperacionMat) {
        this.esOperacionMat = esOperacionMat;
    }
}
