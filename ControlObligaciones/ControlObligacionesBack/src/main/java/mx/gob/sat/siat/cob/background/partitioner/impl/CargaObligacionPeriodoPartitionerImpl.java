/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.partitioner.impl;

import java.util.HashMap;
import java.util.Map;
import mx.gob.sat.siat.cob.background.holder.CargaArchivosHolder;
import mx.gob.sat.siat.cob.background.holder.HolderCargaMasivaMap;
import mx.gob.sat.siat.cob.background.partitioner.CargaObligacionPeriodoPartitioner;
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
@Service("cargaObligacionPeriodoPartitioner")
@Scope("step")
public class CargaObligacionPeriodoPartitionerImpl implements CargaObligacionPeriodoPartitioner {

    private static Logger log = Logger.getLogger(CargaObligacionPeriodoPartitionerImpl.class);
    @Autowired
    private HolderCargaMasivaMap holderCargaMasiva;
    private String idVigilanciaAdministracionLocal;
    private Long idVigilancia;

    public CargaObligacionPeriodoPartitionerImpl() {
        super();
        log.info("### CargaObligacionPeriodoPartitionerImpl -->");
    }

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        if (holderCargaMasiva.getHolderCargaMasiva().get(idVigilancia + idVigilanciaAdministracionLocal) != null) {
            int totalSize = ((CargaArchivosHolder) holderCargaMasiva.getHolderCargaMasiva().get(idVigilancia + idVigilanciaAdministracionLocal)).getObligacionPeriodos().size();
            return UtileriasBackground.ejecucionPartition(gridSize, totalSize);
        }
        return new HashMap<String, ExecutionContext>();
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
