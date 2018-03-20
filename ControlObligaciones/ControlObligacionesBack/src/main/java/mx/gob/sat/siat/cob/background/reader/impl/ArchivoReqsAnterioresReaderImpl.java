/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.reader.impl;

import mx.gob.sat.siat.cob.background.reader.ArchivoReqsAnterioresReader;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.RequerimientosAnteriores;
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
@Service("archivoReqsAnterioresReader")
@Scope("step")
public class ArchivoReqsAnterioresReaderImpl extends FlatFileItemReader
        implements ArchivoReqsAnterioresReader {

    public ArchivoReqsAnterioresReaderImpl() {
        DefaultLineMapper lineMapper = new DefaultLineMapper();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter("|");
        lineTokenizer.setNames(new String[]{
            "idDocumento",
            "idDocumentoPadre",
            "descrTipoRequ",
            "fechaNotificacion",
            "descrObligacion",
            "descrPeriodo",
            "ejercicio",
            "fechaPresentacionObligacion",
            "fechaVencimientoReq",
            "edoObligacionVencimiento",
            "tipoDocumento"});
        BeanWrapperFieldSetMapper fieldSetMapper = new BeanWrapperFieldSetMapper();
        fieldSetMapper.setTargetType(RequerimientosAnteriores.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        setLineMapper(lineMapper);
    }

    @Value(ConstantsCatalogos.RUTA_ARCHIVO_REQS_ANTERIORES_ARCHIVOS_MASIVOS)
    @Override
    public void setResource(Resource resource) {
        super.setResource(resource);
    }
}
