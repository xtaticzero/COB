/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.writer.impl;

import mx.gob.sat.siat.cob.background.writer.ArchivoMultaIDCWriter;
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
@Service("archivoMultaIDCWriter")
@Scope("step")
public class ArchivoMultaIDCWriterImpl extends FlatFileItemWriter
        implements ArchivoMultaIDCWriter {

    public ArchivoMultaIDCWriterImpl() {
        DelimitedLineAggregator delimitedLineAggregator = new DelimitedLineAggregator();
        delimitedLineAggregator.setDelimiter("|");
        setLineAggregator(delimitedLineAggregator);
        BeanWrapperFieldExtractor fieldExtractor = new BeanWrapperFieldExtractor();
        fieldExtractor.setNames(
                new String[]{
            "boId",
            "calle",
            "numExterior",
            "numInterior",
            "descrColonia",
            "descrLocalidad",
            "entreCalle1",
            "entreCalle2",
            "descrMunicipio",
            "descrEntidadFederativa",
            "curp",
            "nombre",
            "apPaterno",
            "apMaterno",
            "compania"});

        delimitedLineAggregator.setFieldExtractor(fieldExtractor);
    }

    @Value(ConstantsCatalogos.RUTA_ARCHIVO_IDC_MULTA)
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
