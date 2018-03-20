package mx.gob.sat.siat.cob.seguimiento.service.extraccion.impl;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DetalleObligaPeriodosDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleObligaPeriodo;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.extraccion.DetalleObligaPeriodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DetalleObligaPeriodoServiceImpl implements DetalleObligaPeriodoService {

    @Autowired
    private DetalleObligaPeriodosDAO detalleObligaPeriodoDAO;

    /**
     *
     */
    public DetalleObligaPeriodoServiceImpl() {
        super();
    }

    @Override
    public List<DetalleObligaPeriodo> buscaDetalleOblicaPeriodos() throws SGTServiceException {
        List<DetalleObligaPeriodo> detalleObligaPeriodos;
        try {
            detalleObligaPeriodos = detalleObligaPeriodoDAO.buscaDetalleOblicaPeriodos();
        } catch (SeguimientoDAOException e) {
            throw new SGTServiceException("No se pudo consultar", e);
        }
        return detalleObligaPeriodos;
    }
}
