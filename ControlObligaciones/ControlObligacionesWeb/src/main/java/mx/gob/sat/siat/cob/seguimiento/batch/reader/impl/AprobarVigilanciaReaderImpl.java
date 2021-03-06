package mx.gob.sat.siat.cob.seguimiento.batch.reader.impl;

import java.util.HashMap;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.batch.reader.AprobarVigilanciaReader;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.FirmaDigitalDTO;
import org.apache.log4j.Logger;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author christian.ventura
 */
@Service("aprobarVigilanciaReader")
@Scope("step")
public class AprobarVigilanciaReaderImpl implements AprobarVigilanciaReader, ItemReader<FirmaDigitalDTO> {

    private Logger log = Logger.getLogger(AprobarVigilanciaReaderImpl.class);
    private List<FirmaDigitalDTO> currentListaDetalle;
    private int index = -1;

    private String nombreLista;

    @Autowired
    @Qualifier("mapaFirmas")
    private HashMap mapaFirmas;

    /**
     *
     * @param nombreLista
     */
    @Value("#{jobParameters[time]}")
    public void setNombreLista(String nombreLista) {
        this.nombreLista = nombreLista;
        log.debug("===============================" + this.nombreLista);
    }

    /**
     *
     * @param se
     */
    @BeforeStep
    public void inicializaLista(StepExecution se) {
        log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<<<<inicializaLista<<<<<<<<<<<<");
        currentListaDetalle = (List<FirmaDigitalDTO>) mapaFirmas.remove(nombreLista);
        log.debug(currentListaDetalle.size());
    }

    /**
     *
     * @return
     */
    @Override
    public FirmaDigitalDTO read() throws UnexpectedInputException, ParseException, NonTransientResourceException {
        index++;
        if (currentListaDetalle == null || index >= currentListaDetalle.size()) {
            index = -1;
            return null;
        }
        log.debug("index:" + index + ",size()" + currentListaDetalle.size());
        return currentListaDetalle.get(index);
    }

}
