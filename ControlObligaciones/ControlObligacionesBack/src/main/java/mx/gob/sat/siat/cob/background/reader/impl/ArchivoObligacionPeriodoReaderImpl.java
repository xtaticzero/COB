/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.reader.impl;

import mx.gob.sat.siat.cob.background.reader.ArchivoObligacionPeriodoReader;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.ObligacionPeriodo;
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
@Service("archivoObligacionPeriodoReader")
@Scope("step")
public class ArchivoObligacionPeriodoReaderImpl extends FlatFileItemReader
        implements ArchivoObligacionPeriodoReader {

    public ArchivoObligacionPeriodoReaderImpl() {
        DefaultLineMapper lineMapper = new DefaultLineMapper();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter("|");
        lineTokenizer.setNames(new String[]{
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
        BeanWrapperFieldSetMapper fieldSetMapper = new BeanWrapperFieldSetMapper();
        fieldSetMapper.setTargetType(ObligacionPeriodo.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        setLineMapper(lineMapper);
    }

    @Value(ConstantsCatalogos.RUTA_ARCHIVO_OBLIGACION_PERIODO)
    @Override
    public void setResource(Resource resource) {
        super.setResource(resource);
    }
}
