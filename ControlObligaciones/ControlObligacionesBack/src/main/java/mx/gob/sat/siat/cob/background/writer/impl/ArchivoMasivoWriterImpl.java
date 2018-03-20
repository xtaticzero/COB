/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.writer.impl;

import mx.gob.sat.siat.cob.background.writer.ArchivoMasivoWriter;
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
@Service("archivoMasivoWriter")
@Scope("step")
public class ArchivoMasivoWriterImpl extends FlatFileItemWriter implements ArchivoMasivoWriter {

    public ArchivoMasivoWriterImpl() {
        DelimitedLineAggregator delimitedLineAggregator = new DelimitedLineAggregator();
        delimitedLineAggregator.setDelimiter("|");
        setLineAggregator(delimitedLineAggregator);
        BeanWrapperFieldExtractor fieldExtractor = new BeanWrapperFieldExtractor();
        fieldExtractor.setNames(
                new String[]{"boId",
            "numControlPadre",
            "id",
            "idTipoDocumento",
            "idMedioEnvio",
            "idEstadoDocumento",
            "idDocumentoPlantilla",
            "idAlsc",
            "descripcionContribuyente",
            "rfc",
            "curp",
            "descripcionDireccion",
            "crh",
            "resolucion",
            "idResolucion",
            "importe",
            "codPostal",
            "idTipoPersona",
            "infromacionQR",
            "idFirmaDigitalAplicable"});

        delimitedLineAggregator.setFieldExtractor(fieldExtractor);
    }

    @Value(ConstantsCatalogos.RUTA_ARCHIVO_COB_ARCHIVOS_MASIVOS)
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
