package mx.gob.sat.siat.cob.background.reader;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleDocumento;

public interface OmisosReader {

    List<DetalleDocumento> getCurrentListaDetalle();

    void setCurrentListaDetalle(List<DetalleDocumento> currentListaDetalle);

    int getIndex();

    void setIndex(int index);

}
