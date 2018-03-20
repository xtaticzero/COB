/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.reader.impl;

import mx.gob.sat.siat.cob.background.holder.CargaArchivosHolder;
import mx.gob.sat.siat.cob.background.reader.MultaDocumentoReader;
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
@Service("multaDocumentoReader")
@Scope("step")
public class MultaDocumentoReaderImpl implements MultaDocumentoReader {

    private static Logger logger = Logger.getLogger(MultaDocumentoReaderImpl.class);
    private int fromId;
    private int toId;
    private int index;
    @Autowired
    private CargaArchivosHolder holder;

    @Override
    public Object read() {

        logger.info("### READ MULTA DOCUMENTO");

        logger.debug("@@@ From MD :" + fromId);
        logger.debug("@@@ To MD :" + toId);
        logger.debug("@@@ Index MD :" + index);
        logger.debug("@@@ Size Multa Documento Cobranza :" + holder.getMultasDocumentos().size());
        if (UtileriasBackground.puedeRegresarValores(index, toId, holder.getMultasDocumentos().size())) {
            return holder.getMultasDocumentos().get(index++);
        } else {
            logger.debug("@@@ From MD: " + fromId + " Exit Index: " + index);
            return null;
        }
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
}
