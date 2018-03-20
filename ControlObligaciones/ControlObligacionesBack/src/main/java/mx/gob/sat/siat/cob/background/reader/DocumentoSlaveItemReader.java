/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.reader;

import mx.gob.sat.siat.cob.seguimiento.dto.arca.DocumentoARCA;
import org.springframework.batch.item.ItemReader;

/**
 *
 * @author Juan
 */
public interface DocumentoSlaveItemReader extends ItemReader<DocumentoARCA> {
}
