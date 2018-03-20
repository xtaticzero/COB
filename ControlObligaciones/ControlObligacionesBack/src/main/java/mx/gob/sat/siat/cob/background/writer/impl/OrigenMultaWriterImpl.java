/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.writer.impl;

import mx.gob.sat.siat.cob.background.writer.OrigenMultaWriter;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author Juan
 */
@Service("origenMultaWriter")
@Scope("step")
public class OrigenMultaWriterImpl extends FlatFileItemWriter
        implements OrigenMultaWriter {

    public OrigenMultaWriterImpl() {
        DelimitedLineAggregator delimitedLineAggregator = new DelimitedLineAggregator();
        delimitedLineAggregator.setDelimiter("|");
        setLineAggregator(delimitedLineAggregator);
        BeanWrapperFieldExtractor fieldExtractor = new BeanWrapperFieldExtractor();
        fieldExtractor.setNames(
                new String[]{"idDocumento",
            "numControlOrigen",
            "fechaNotificacionOrigen",
            "numControlPrimero",
            "fechaNotificacionPrimero",
            "numControlSegundo",
            "fechaNotificacionSegundo"});

        delimitedLineAggregator.setFieldExtractor(fieldExtractor);
    }

    @Value(ConstantsCatalogos.RUTA_ARCHIVO_ORIGEN_MULTA)
    @Override
    public void setResource(Resource resource) {
        super.setResource(resource);
    }

    @Value("true")
    @Override
    public void setAppendAllowed(boolean append) {
        super.setAppendAllowed(append);
    }

    @Value("true")
    @Override
    public void setShouldDeleteIfExists(boolean shouldDeleteIfExists) {
        super.setShouldDeleteIfExists(shouldDeleteIfExists);
    }
}
