/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.otros.impl;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.CicloVidaSgtDao;
import mx.gob.sat.siat.cob.seguimiento.service.otros.CicloVidaSgtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CicloVidaSgtServiceImpl implements CicloVidaSgtService{

    @Autowired
    @Qualifier("daoCicloVidaSgt")
    private CicloVidaSgtDao dao;


    @Override
    public boolean validaCambioEtapa(Integer idEtapaActual,
            Integer idTipoDocumento,
            Integer idEtapaRequerida) {

        List<Integer> ids = dao.consultaEtapa(idEtapaActual, idTipoDocumento, idEtapaRequerida);
        return ids.contains(idEtapaActual);
    }

}
