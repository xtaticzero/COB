/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.extraccion.impl;

import java.util.ArrayList;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.VigilanciaAdministracionLocalDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.DocumentoARCA;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaAdministracionLocal;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.SituacionVigilanciaEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.extraccion.VigilanciaAdministracionLocalService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Juan
 */
@Service
public class VigilanciaAdministracionLocalServiceImpl
        implements VigilanciaAdministracionLocalService {

    private Logger log = Logger.getLogger(VigilanciaAdministracionLocalServiceImpl.class);
    @Autowired
    private VigilanciaAdministracionLocalDAO vigilanciaAdministracionLocalDAO;

    @Override
    public List<VigilanciaAdministracionLocal> obtenerAdministracionLocalXIdVigilanica(Long idVigilancia) throws SGTServiceException {
        try {
            return vigilanciaAdministracionLocalDAO.obtenerAdministracionLocalXIdVigilanica(idVigilancia);
        } catch (SeguimientoDAOException ex) {
            log.error(ex);
            throw new SGTServiceException(ex);
        }
    }

    @Override
    public void actualizaIdSituacionVigilancia(VigilanciaAdministracionLocal vigilanciaAdministracionLocal, int idSituacionVigilancia) throws SGTServiceException {
        try {
            vigilanciaAdministracionLocalDAO.actualizaIdSituacionVigilancia(vigilanciaAdministracionLocal, idSituacionVigilancia);
        } catch (SeguimientoDAOException ex) {
            log.error(ex);
            throw new SGTServiceException(ex);
        }
    }

    @Override
    public List<VigilanciaAdministracionLocal> obtenerIdAdministracionLocal(List<DocumentoARCA> doctos) throws SGTServiceException {
        List<String> numerosCtrl = new ArrayList<String>();
        for (DocumentoARCA docto : doctos) {
            numerosCtrl.add(docto.getId());
        }

        return vigilanciaAdministracionLocalDAO.obtenerIdAdministracionLocal(numerosCtrl);
    }

    @Override
    public void updateSituacionVigilancia(List<VigilanciaAdministracionLocal> valValida, SituacionVigilanciaEnum situacionEnum) throws SGTServiceException {
        if (vigilanciaAdministracionLocalDAO.updateSituacionVigilancia(valValida, situacionEnum) == null) {
            throw new SGTServiceException("Fallo al actualizar la VigilanciaAL");
        }
    }

    @Override
    public boolean hayRegistrosPorVigilancia(VigilanciaAdministracionLocal vigilancia, String joinNivelEmision) throws SGTServiceException {
        Integer resultado = vigilanciaAdministracionLocalDAO.cuentaRegistrosPorVigilancia(vigilancia, joinNivelEmision);
        if (resultado == null) {
            throw new SGTServiceException("Ocurrio un error al contar registros a procesar para la vigilancia: " + vigilancia.getIdVigilancia());
        }
        boolean hayRegistros = false;
        if (resultado > 0) {
            hayRegistros = true;
        }
        return hayRegistros;

    }

    @Override
    public List<VigilanciaAdministracionLocal> obtenerVigilanciasConDocumentos(VigilanciaAdministracionLocal vigilanciaAdminLocal) throws SGTServiceException {
        List<VigilanciaAdministracionLocal> vigilanciasAdmonLocal;
        try {
            vigilanciasAdmonLocal = vigilanciaAdministracionLocalDAO.obtenerVigilanciasConDocumentos(vigilanciaAdminLocal);
        } catch (SeguimientoDAOException e) {
            log.error(e);
            throw new SGTServiceException("No se pudieron consultar las vigilancias por etapa", e);
        }
        return vigilanciasAdmonLocal;

    }

    @Override
    public VigilanciaAdministracionLocal obtenerVigilanciaMultaBatch() throws SGTServiceException {
        try {
            return vigilanciaAdministracionLocalDAO.obtenerVigilanciaMultaBatch();
        } catch (SeguimientoDAOException ex) {
            log.error(ex);
            throw new SGTServiceException(ex);
        }
    }

    @Override
    public void actualizaFechaVigilancia(Long idVigilancia, String idAdministracionLocal) throws SGTServiceException {
        try {
            vigilanciaAdministracionLocalDAO.actualizaFechaVigilancia(idVigilancia, idAdministracionLocal);
        } catch (SeguimientoDAOException ex) {
            throw new SGTServiceException(ex);
        }

    }

    @Override
    public VigilanciaAdministracionLocal obtenerVigilancia(Long idVigilancia, String idAdmonLocal) throws SGTServiceException {
        try {
            return vigilanciaAdministracionLocalDAO.obtenerVigilancia(idVigilancia, idAdmonLocal);
        } catch (SeguimientoDAOException ex) {
            log.error(ex);
            throw new SGTServiceException(ex);
        }

    }

    @Override
    public Long obtenetNumeroVigilancia() throws SGTServiceException {
        try {
            return vigilanciaAdministracionLocalDAO.obtenetNumeroVigilancia();
        } catch (SeguimientoDAOException ex) {
            throw new SGTServiceException(ex);
        }
    }

    @Override
    public List<VigilanciaAdministracionLocal> obtenerAdministracionLocalMultaXIdVigilanica(Long idVigilancia) throws SGTServiceException {
        try {
            return vigilanciaAdministracionLocalDAO.obtenerAdministracionLocalMultaXIdVigilanica(idVigilancia);
        } catch (SeguimientoDAOException ex) {
            log.error(ex);
            throw new SGTServiceException(ex);
        }
    }

    @Override
    public Integer guardaVigAdminLocal(Long idVigilancia, String idAdmonLocal) throws SGTServiceException {
        try {
            return vigilanciaAdministracionLocalDAO.guardaVigAdminLocal(idVigilancia, idAdmonLocal);
        } catch (SeguimientoDAOException ex) {
            log.error("Fallo al insertar en la vigilancia administracion local: " + ex);
            throw new SGTServiceException(ex);
        }
    }
}
