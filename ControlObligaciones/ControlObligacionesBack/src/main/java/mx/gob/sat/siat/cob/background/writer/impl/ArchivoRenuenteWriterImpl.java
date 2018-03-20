/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.writer.impl;

import java.io.IOException;
import java.io.Writer;
import mx.gob.sat.siat.cob.background.writer.ArchivoMasivoWriter;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author juan
 */
@Service("archivoRenuenteWriter")
@Scope("step")
public class ArchivoRenuenteWriterImpl extends FlatFileItemWriter implements ArchivoMasivoWriter {

    public ArchivoRenuenteWriterImpl() {

        super.setHeaderCallback(new FlatFileHeaderCallback() {
            @Override
            public void writeHeader(Writer writer) throws IOException {
                writer.write("NUMEROCONTROL|RFC|ESTADO|CLAVE OBLIGACION|"
                        + "CONCEPTO OBLIGACION|CLAVE REGIMEN|CONCEPTO REGIMEN|EJERCICIO|"
                        + "PERIODO|PERIODICIDAD|FECHACUMPLIMIENTO");
            }
        }
        );
        DelimitedLineAggregator delimitedLineAggregator = new DelimitedLineAggregator();
        delimitedLineAggregator.setDelimiter("|");
        setLineAggregator(delimitedLineAggregator);

        BeanWrapperFieldExtractor fieldExtractor = new BeanWrapperFieldExtractor();

        fieldExtractor.setNames(
                new String[]{"numeroControl", "rfc", "estado", "clave", "concepto",
                    "regimen", "regimenDesc", "ejercicio", "periodo", "periodicidad",
                    "fechaCumplimiento"});

        delimitedLineAggregator.setFieldExtractor(fieldExtractor);
    }

    @Value("file:/#{jobParameters['rutaArchivo']}")
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
