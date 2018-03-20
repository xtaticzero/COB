package mx.gob.sat.siat.cob.background.partitioner.impl;

import java.util.Map;

import mx.gob.sat.siat.cob.background.holder.CargaArchivosHolder;
import mx.gob.sat.siat.cob.background.holder.HolderCargaMasivaMap;
import mx.gob.sat.siat.cob.background.partitioner.CargaDocumentoPartitioner;
import mx.gob.sat.siat.cob.background.util.UtileriasBackground;

import org.apache.log4j.Logger;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author Jonathan
 */
@Service("cargaDocumentoPartitioner")
@Scope("step")
public class CargaDocumentoPartitionerImpl implements CargaDocumentoPartitioner {

    private static Logger log = Logger.getLogger(CargaDocumentoPartitionerImpl.class);
    @Autowired
    private HolderCargaMasivaMap holderCargaMasiva;
    private String idVigilanciaAdministracionLocal;
    private Long idVigilancia;

    public CargaDocumentoPartitionerImpl() {
        super();
        log.info("### CargaDocumentoPartitionerImpl -->");
    }

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        if (holderCargaMasiva.getHolderCargaMasiva().get(idVigilancia + idVigilanciaAdministracionLocal) != null) {
            int totalSize = ((CargaArchivosHolder) holderCargaMasiva.getHolderCargaMasiva().get(idVigilancia + idVigilanciaAdministracionLocal)).getDocumentosARCA().size();
            return UtileriasBackground.ejecucionPartition(gridSize, totalSize);
        }
        return null;
    }

    @Value("#{jobParameters['idAdmonLocal']}")
    public void setIdVigilanciaAdministracionLocal(String idVigilanciaAdministracionLocal) {
        this.idVigilanciaAdministracionLocal = idVigilanciaAdministracionLocal;
    }

    @Value("#{jobParameters['idVigilancia']}")
    public void setIdVigilancia(Long idVigilancia) {
        this.idVigilancia = idVigilancia;
    }
}
