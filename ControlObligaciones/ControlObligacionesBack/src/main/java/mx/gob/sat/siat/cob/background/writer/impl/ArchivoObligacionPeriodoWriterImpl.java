/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.writer.impl;

import mx.gob.sat.siat.cob.background.writer.ArchivoObligacionPeriodoWriter;
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
@Service("archivoObligacionPeriodoWriter")
@Scope("step")
public class ArchivoObligacionPeriodoWriterImpl extends FlatFileItemWriter
        implements ArchivoObligacionPeriodoWriter {

    public ArchivoObligacionPeriodoWriterImpl() {
        DelimitedLineAggregator delimitedLineAggregator = new DelimitedLineAggregator();
        delimitedLineAggregator.setDelimiter("|");
        setLineAggregator(delimitedLineAggregator);
        BeanWrapperFieldExtractor fieldExtractor = new BeanWrapperFieldExtractor();
        fieldExtractor.setNames(
                new String[]{
            "periodo.idDocumento",
            "periodo.descripcionPeriodo",
            "periodo.ejercicio",
            "periodo.conceptoImpuesto",
            "obligacion.idDocumento",
            "obligacion.descripcionDeObligacion",
            "obligacion.descripcionDePeriodo",
            "obligacion.ejercicio",
            "obligacion.fundamentoLegal",
            "obligacion.fechaDeVencimiento",
            "obligacion.sancion",
            "obligacion.infraccion",
            "obligacion.importe"
            });

        delimitedLineAggregator.setFieldExtractor(fieldExtractor);
    }

    @Value(ConstantsCatalogos.RUTA_ARCHIVO_OBLIGACION_PERIODO)
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
