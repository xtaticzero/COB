package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import java.util.Date;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.db2.Paginador;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaEntidadFederativa;

public interface VigilanciaEntidadFederativaDAO {
    
    List<VigilanciaEntidadFederativa> obtenerVigilancias(Long claveEf);
    List<VigilanciaEntidadFederativa> obtenerVigilancias();
    Long obtenerCantidadVigilancias();
    List<VigilanciaEntidadFederativa> obtenerVigilanciasPaginadas(Paginador paginador);
    Integer guardarRechazo(VigilanciaEntidadFederativa vef);
    Integer aceptarVigilancia(VigilanciaEntidadFederativa vef);
    List<VigilanciaEntidadFederativa> obtenerVigilanciaPorIdVigClaveEF(Long idVig,Long claveEf );
    Date consultarVigilanciaPorUsuarioEf(String usuario, Long idVigilancia);
}
