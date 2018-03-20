/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.partitioner.impl;

import java.util.Map;
import mx.gob.sat.siat.cob.background.holder.CargaArchivosHolder;
import mx.gob.sat.siat.cob.background.partitioner.CargaMultaDocumentoPartitioner;
import mx.gob.sat.siat.cob.background.util.UtileriasBackground;
import org.apache.log4j.Logger;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author Juan
 */
@Service("cargaMultaDocumentoPartitioner")
@Scope("step")
public class CargaMultaDocumentoPartitionerImpl implements CargaMultaDocumentoPartitioner {

    private static Logger log = Logger.getLogger(CargaMultaDocumentoPartitionerImpl.class);
    @Autowired
    private CargaArchivosHolder holder;

    public CargaMultaDocumentoPartitionerImpl() {
        super();
        log.info("### CargaMultaDocumentoPartitionerImpl -->");
    }

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        int totalSize = holder.getMultasDocumentos().size();
        log.info("### CargaMultaDocumentoPartitionerImpl --> Partition Hilos: " + gridSize + " to: " + totalSize);
        return UtileriasBackground.ejecucionPartition(gridSize, totalSize);

    }
}
