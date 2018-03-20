package mx.gob.sat.siat.cob.seguimiento.service.otros.impl;

import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.PlantillaSGTDAO;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.PlantillaDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EsMultaEnum;
import mx.gob.sat.siat.cob.seguimiento.service.otros.PlantillaArcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PlantillaArcaServiceImpl implements PlantillaArcaService {

    @Autowired
    private PlantillaSGTDAO plantillaSGTDAO;

    /**
     *
     * @param idTipoDocto
     * @param idEtapaVigilancia
     * @param blnDiot
     * @param idTipoMedio
     * @param idTipoFirma
     * @param idModalidadDoc
     * @return
     * @throws SGTServiceException
     */
    @Override
    public int determinaPlantillaRequerimientoCarta(int idTipoDocto, int idEtapaVigilancia, int blnDiot, int idTipoMedio, int idTipoFirma, int idModalidadDoc) throws SGTServiceException {
        PlantillaDocumento plantilla = null;
        PlantillaDocumento pl = new PlantillaDocumento();
        pl.setIdTipoDocumento(idTipoDocto);
        pl.setIdEtapaVigilancia(idEtapaVigilancia);
        pl.setBlnPlantillaDIOT(blnDiot);
        pl.setIdMedioEnvio(idTipoMedio);
        pl.setIdTipoFirma(idTipoFirma);
        pl.setIdModalidadDocumento(idModalidadDoc);

        try {
            plantilla = plantillaSGTDAO.buscarPlantillaArcaPorParametros(pl, "requerimiento");
        } catch (SeguimientoDAOException e) {
            throw new SGTServiceException(e);
        }

        return plantilla.getIdPlantilla();
    }

    /**
     *
     * @param idTipoDocto
     * @param idEtapaVigilancia
     * @param blnDiot
     * @param idTipoMedio
     * @param idTipoFirma
     * @param idModalidadDoc
     * @param dias
     * @return
     * @throws SGTServiceException
     */
    @Override
    public int determinaPlantillaLiquidacion(int idTipoDocto, int idEtapaVigilancia, int blnDiot, int idTipoMedio, int idTipoFirma, int idModalidadDoc, int dias) throws SGTServiceException {
        int contEtapaVig = idEtapaVigilancia + 1;

        PlantillaDocumento plantilla = null;
        PlantillaDocumento pl = new PlantillaDocumento();
        pl.setIdTipoDocumento(idTipoDocto);
        pl.setIdEtapaVigilancia(contEtapaVig);
        pl.setBlnPlantillaDIOT(blnDiot);
        pl.setIdMedioEnvio(idTipoMedio);
        pl.setIdTipoFirma(idTipoFirma);
        pl.setIdModalidadDocumento(idModalidadDoc);
        pl.setIdTipoDias(dias);

        try {
            plantilla = plantillaSGTDAO.buscarPlantillaArcaPorParametros(pl, "liquidacion");
        } catch (SeguimientoDAOException e) {
            throw new SGTServiceException(e);
        }
        return plantilla.getIdPlantilla();
    }

    /**
     *
     * @param idEtapaVigilancia
     * @param blnDiot
     * @param idTipoMedio
     * @param idTipoFirma
     * @param idModalidadDoc
     * @param constanteResolMotivo
     * @return
     * @throws SGTServiceException
     */
    @Override
    public int determinaPlantillaMulta(int idEtapaVigilancia, int blnDiot, int idTipoMedio, int idTipoFirma, int idModalidadDoc, String constanteResolMotivo) throws SGTServiceException {
        int idTipoDocto = 5;//tipo de documento Multa

        PlantillaDocumento plantilla = null;
        PlantillaDocumento pl = new PlantillaDocumento();
        pl.setIdTipoDocumento(idTipoDocto);
        pl.setIdEtapaVigilancia(idEtapaVigilancia);
        pl.setBlnPlantillaDIOT(blnDiot);
        pl.setIdMedioEnvio(idTipoMedio);
        pl.setIdTipoFirma(idTipoFirma);
        pl.setIdModalidadDocumento(idModalidadDoc);
        pl.setIdTipoMotivo(constanteResolMotivo);

        try {
            plantilla = plantillaSGTDAO.buscarPlantillaArcaPorParametros(pl, "multa");
        } catch (SeguimientoDAOException e) {
            throw new SGTServiceException(e);
        }

        return plantilla.getIdPlantilla();
    }

    /**
     * @throws SGTServiceException
     */
    @Override
    public void guardaPlantillaArcaBatch(EsMultaEnum esMulta) throws SGTServiceException {
        try {
            plantillaSGTDAO.guardaPlantillaArcaBatch(esMulta);
        } catch (SeguimientoDAOException ex) {
            throw new SGTServiceException(ex);
        }
    }

    /**
     * @throws SGTServiceException
     */
    @Override
    public void eliminaPlantillaArcaBatch() throws SGTServiceException {
        try {
            plantillaSGTDAO.eliminaPlantillaArcaBatch();
        } catch (SeguimientoDAOException ex) {
            throw new SGTServiceException(ex);
        }
    }
}
