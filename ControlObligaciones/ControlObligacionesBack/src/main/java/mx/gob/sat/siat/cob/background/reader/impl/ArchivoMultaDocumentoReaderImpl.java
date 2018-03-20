/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.reader.impl;

import mx.gob.sat.siat.cob.background.reader.ArchivoMultaDocumentoReader;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.DocumentoARCA;
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
@Service("archivoMultaDocumentoReader")
@Scope("step")
public class ArchivoMultaDocumentoReaderImpl extends FlatFileItemReader
        implements ArchivoMultaDocumentoReader {

    public ArchivoMultaDocumentoReaderImpl() {
        DefaultLineMapper lineMapper = new DefaultLineMapper();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter("|");
        lineTokenizer.setNames(new String[]{"boId",
            "numControlPadre",
            "id",
            "idTipoDocumento",
            "idMedioEnvio",
            "idDocumentoPlantilla",
            "idEstadoDocumento",
            "rfc",
            "codPostal",
            "crh",
            "idAlsc",
            "resolucion",
            "idResolucion",
            "importe",
            "contribuyente",
            "curp",
            "descripcionDireccion"});
        BeanWrapperFieldSetMapper fieldSetMapper = new BeanWrapperFieldSetMapper();
        fieldSetMapper.setTargetType(DocumentoARCA.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        setLineMapper(lineMapper);
    }

    @Value(ConstantsCatalogos.RUTA_ARCHIVO_DOCUMENTO_MULTA)
    @Override
    public void setResource(Resource resource) {
        super.setResource(resource);
    }
}
