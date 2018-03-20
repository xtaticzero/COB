/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.db2.Paginador;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaAprobar;

/**
 *
 * @author root
 */
public interface VigilanciaAprobarDAO {

    List<VigilanciaAprobar> listarVigilanciasAprobar(long idCargoAdministrativo);

    List<VigilanciaAprobar> listarVigilanciasAprobar(long idCargoAdministrativo, String idAdministracionLocal);

    List<VigilanciaAprobar> listarVigilanciasAprobar();

    Long obtenerCantidadVigilanciasAL();

    List<VigilanciaAprobar> obtenerVigilanciasPaginadas(Paginador paginador);

}
