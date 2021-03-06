/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.reader.impl;

import mx.gob.sat.siat.cob.background.holder.CargaArchivosHolder;
import mx.gob.sat.siat.cob.background.holder.HolderCargaMasivaMap;
import mx.gob.sat.siat.cob.background.reader.OrigenSlaveItemReader;
import mx.gob.sat.siat.cob.background.util.UtileriasBackground;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author Juan
 */
@Service("origenSlaveItemReader")
@Scope("step")
public class OrigenSlaveItemReaderImpl implements OrigenSlaveItemReader {

    private static Logger logger = Logger.getLogger(OrigenSlaveItemReaderImpl.class);
    @Autowired
    private HolderCargaMasivaMap holderCargaMasiva;
    private Long idVigilancia;
    private String idVigilanciaAdministracionLocal;
    private int fromId;
    private int toId;
    private int index;

    public OrigenSlaveItemReaderImpl() {
        super();
        logger.info("### OrigenSlaveItemReaderImpl --> ");
    }

    @Override
    public Object read() {
        if (holderCargaMasiva.getHolderCargaMasiva().get(idVigilancia + idVigilanciaAdministracionLocal) != null) {
            if (UtileriasBackground.puedeRegresarValores(index, toId,
                    ((CargaArchivosHolder) holderCargaMasiva.getHolderCargaMasiva().get(idVigilancia + idVigilanciaAdministracionLocal))
                    .getOrigenesMulta().size())) {
                return ((CargaArchivosHolder) holderCargaMasiva.getHolderCargaMasiva().get(idVigilancia + idVigilanciaAdministracionLocal))
                        .getOrigenesMulta().get(index++);
            }
        }
        return null;
    }

    @Value("#{stepExecutionContext[fromId]}")
    public void setFromId(int fromId) {
        this.fromId = fromId;
        index = fromId;
    }

    public int getFromId() {
        return fromId;
    }

    @Value("#{stepExecutionContext[toId]}")
    public void setToId(int toId) {
        this.toId = toId;
    }

    public int getToId() {
        return toId;
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
