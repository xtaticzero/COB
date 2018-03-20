/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.partitioner.impl;

import java.util.HashMap;
import java.util.Map;
import mx.gob.sat.siat.cob.background.holder.CargaArchivosHolder;
import mx.gob.sat.siat.cob.background.holder.HolderCargaMasivaMap;
import mx.gob.sat.siat.cob.background.partitioner.DatosMultaDocumentoResolucionPartitioner;
import mx.gob.sat.siat.cob.background.util.UtileriasBackground;
import org.apache.log4j.Logger;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author Juan
 */
@Service("datosMultaDocumentoResolucionPartitioner")
@Scope("step")
public class DatosMultaDocumentoResolucionPartitionerImpl
        implements DatosMultaDocumentoResolucionPartitioner {

    private static Logger log = Logger.getLogger(DatosMultaDocumentoResolucionPartitionerImpl.class);
    @Autowired
    private HolderCargaMasivaMap holderCargaMasiva;
    private Long idVigilancia;
    private String idVigilanciaAdministracionLocal;

    public DatosMultaDocumentoResolucionPartitionerImpl() {
        super();
        log.info("### DatosMultaDocumentoResolucionPartitionerImpl -->");
    }

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        if (holderCargaMasiva.getHolderCargaMasiva().get(idVigilancia + idVigilanciaAdministracionLocal) != null) {
            int totalSize = ((CargaArchivosHolder) holderCargaMasiva.getHolderCargaMasiva().get(idVigilancia + idVigilanciaAdministracionLocal)).getDocumentosARCA().size();
            log.info("### DatosMultaDocumentoResolucionPartitionerImpl --> Partition Hilos: " + gridSize + " to: " + totalSize);
            return UtileriasBackground.ejecucionPartition(gridSize, totalSize);
        }
        return new HashMap<String, ExecutionContext>();
    }

    public String getIdVigilanciaAdministracionLocal() {
        return idVigilanciaAdministracionLocal;
    }

    @Value("#{jobParameters['idAdmonLocal']}")
    public void setIdVigilanciaAdministracionLocal(String idVigilanciaAdministracionLocal) {
        this.idVigilanciaAdministracionLocal = idVigilanciaAdministracionLocal;
    }

    public Long getIdVigilancia() {
        return idVigilancia;
    }

    @Value("#{jobParameters['idVigilancia']}")
    public void setIdVigilancia(Long idVigilancia) {
        this.idVigilancia = idVigilancia;
    }
}
