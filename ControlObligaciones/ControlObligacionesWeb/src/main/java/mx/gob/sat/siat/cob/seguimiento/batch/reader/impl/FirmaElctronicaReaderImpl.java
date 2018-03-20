/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.batch.reader.impl;

import java.util.HashMap;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.batch.reader.FirmaElectronicaReader;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.FirmaDigital;
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
 * @author root
 */
@Service("firmaElectronicaReader")
@Scope("step")
public class FirmaElctronicaReaderImpl implements FirmaElectronicaReader, ItemReader<FirmaDigital> {
    
    private Logger log =Logger.getLogger(FirmaElctronicaReaderImpl.class);
    private List<FirmaDigital> currentListaDetalle;
    private int index=-1;

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
        log.debug("==============================="+this.nombreLista);
    }
    
    /**
     * 
     * @param se 
     */
    @BeforeStep
    public void inicializaLista(StepExecution se){
        log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<<<<inicializaLista<<<<<<<<<<<<");
        currentListaDetalle=(List<FirmaDigital>)mapaFirmas.remove(nombreLista);
        log.debug(currentListaDetalle.size());
    }
    
    /**
     *
     * @return
     * @throws UnexpectedInputException
     */
    @Override
    public FirmaDigital read() throws UnexpectedInputException {
        index++;
        if(currentListaDetalle==null || index>=currentListaDetalle.size()){
            index=-1;
            return null;
        }
        log.debug("index:"+index+",size()"+currentListaDetalle.size());
        return currentListaDetalle.get(index);
    }
}
