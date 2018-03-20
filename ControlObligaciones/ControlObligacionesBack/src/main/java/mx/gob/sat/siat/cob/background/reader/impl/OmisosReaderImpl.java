package mx.gob.sat.siat.cob.background.reader.impl;

import java.util.ArrayList;
import java.util.List;
import mx.gob.sat.siat.cob.background.reader.OmisosReader;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleDocumento;
import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Service;

@Service("omisosReader")
public class OmisosReaderImpl implements OmisosReader, ItemReader<DetalleDocumento>{

    private Logger log =Logger.getLogger(OmisosReaderImpl.class);
    private List<DetalleDocumento> currentListaDetalle = new ArrayList<DetalleDocumento>();
    private int index=-1;

    /**
     *
     * @return
     * @throws UnexpectedInputException
     */
    @Override
    public DetalleDocumento read() throws UnexpectedInputException {
        index++;
        if(index>=currentListaDetalle.size()){
            index=-1;
            return null;
        }
        log.debug("index:"+index+",size()"+currentListaDetalle.size());
        return currentListaDetalle.get(index);
    }

    /**
     *
     * @return
     */
    @Override
    public List<DetalleDocumento> getCurrentListaDetalle() {
        return currentListaDetalle;
    }

    /**
     *
     * @param currentListaDetalle
     */
    @Override
    public void setCurrentListaDetalle(List<DetalleDocumento> currentListaDetalle) {
        this.currentListaDetalle = currentListaDetalle;
    }

    /**
     *
     * @return
     */
    @Override
    public int getIndex() {
        return index;
    }

    /**
     * 
     * @param index
     */
    @Override
    public void setIndex(int index) {
        this.index = index;
    }

}
