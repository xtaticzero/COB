/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.reader.impl;

import mx.gob.sat.siat.cob.background.reader.ArchivoMultaOrigenReader;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.OrigenMulta;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author Juan
 */
@Service("archivoMultaOrigenReader")
@Scope("step")
public class ArchivoMultaOrigenReaderImpl extends FlatFileItemReader
        implements ArchivoMultaOrigenReader {

    public ArchivoMultaOrigenReaderImpl() {
        DefaultLineMapper lineMapper = new DefaultLineMapper();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter("|");
        lineTokenizer.setNames(new String[]{"idDocumento",
            "numControlOrigen",
            "fechaNotificacionOrigen",
            "numControlPrimero",
            "fechaNotificacionPrimero",
            "numControlSegundo",
            "fechaNotificacionSegundo"});
        BeanWrapperFieldSetMapper fieldSetMapper = new BeanWrapperFieldSetMapper();
        fieldSetMapper.setTargetType(OrigenMulta.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        setLineMapper(lineMapper);
    }

    @Value(ConstantsCatalogos.RUTA_ARCHIVO_ORIGEN_MULTA)
    @Override
    public void setResource(Resource resource) {
        super.setResource(resource);
    }
}