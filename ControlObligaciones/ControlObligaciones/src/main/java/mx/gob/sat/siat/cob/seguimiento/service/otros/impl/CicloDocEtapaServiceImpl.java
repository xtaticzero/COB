package mx.gob.sat.siat.cob.seguimiento.service.otros.impl;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.CicloDocEtapaDao;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EtapaVigilanciaEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.service.otros.CicloDocEtapaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CicloDocEtapaServiceImpl implements CicloDocEtapaService {

    @Autowired
    @Qualifier("daoCicloDocEtapa")
    private CicloDocEtapaDao dao;

    /**
     *
     */
    public CicloDocEtapaServiceImpl() {
    }

    /**
     *
     * @param idEstadoOrigen
     * @param idTipoDocumento
     * @param idEtapaVigilancia
     * @param idEstadoDestino
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public boolean validaCambioEstado(EstadoDocumentoEnum idEstadoOrigen,
            TipoDocumentoEnum idTipoDocumento,
            EtapaVigilanciaEnum idEtapaVigilancia,
            EstadoDocumentoEnum idEstadoDestino) {

        List<Integer> ids = dao.consultaEstados(
                idEstadoOrigen, idTipoDocumento,
                idEtapaVigilancia, idEstadoDestino);
        return ids.contains(Integer.valueOf(idEstadoOrigen.getValor()));
    }
}
