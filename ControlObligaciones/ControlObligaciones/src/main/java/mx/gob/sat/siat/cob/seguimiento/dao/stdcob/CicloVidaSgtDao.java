package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import java.util.List;

public interface CicloVidaSgtDao {
    
    List<Integer> consultaEtapa(Integer idEtapaActual,
                                         Integer idTipoDocumento,
                                         Integer idEtapaRequerida);
}
