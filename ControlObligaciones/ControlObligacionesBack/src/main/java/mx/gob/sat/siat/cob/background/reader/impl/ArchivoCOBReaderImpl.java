/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.reader.impl;

import mx.gob.sat.siat.cob.background.reader.ArchivoCOBReader;
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
@Service("archivoCOBReader")
@Scope("step")
public class ArchivoCOBReaderImpl extends FlatFileItemReader implements ArchivoCOBReader {

    public ArchivoCOBReaderImpl() {
        DefaultLineMapper lineMapper = new DefaultLineMapper();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter("|");
        lineTokenizer.setNames(new String[]{"boId",
            "numControlPadre",
            "id",
            "idTipoDocumento",
            "idMedioEnvio",
            "idEstadoDocumento",
            "idDocumentoPlantilla",
            "idAlsc",
            "contribuyente",
            "rfc",
            "curp",
            "descripcionDireccion",
            "crh",
            "resolucion",
            "idResolucion",
            "importe",
            "codPostal",
            "idTipoPersona",
            "infromacionQR"});
        BeanWrapperFieldSetMapper fieldSetMapper = new BeanWrapperFieldSetMapper();
        fieldSetMapper.setTargetType(DocumentoARCA.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        setLineMapper(lineMapper);
    }

    @Value(ConstantsCatalogos.RUTA_ARCHIVO_COB_ARCHIVOS_MASIVOS)
    @Override
    public void setResource(Resource resource) {
        super.setResource(resource);
    }
}
