package mx.gob.sat.siat.cob.seguimiento.service.otros;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EsMultaEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;

public interface PlantillaArcaService {

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
    int determinaPlantillaRequerimientoCarta(int idTipoDocto, int idEtapaVigilancia, int blnDiot, int idTipoMedio, int idTipoFirma, int idModalidadDoc) throws SGTServiceException;

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
    int determinaPlantillaLiquidacion(int idTipoDocto, int idEtapaVigilancia, int blnDiot, int idTipoMedio, int idTipoFirma, int idModalidadDoc, int dias) throws SGTServiceException;

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
    int determinaPlantillaMulta(int idEtapaVigilancia, int blnDiot, int idTipoMedio, int idTipoFirma, int idModalidadDoc, String constanteResolMotivo) throws SGTServiceException;

    /**
     *
     * @throws SGTServiceException
     */
    void guardaPlantillaArcaBatch(EsMultaEnum esMulta) throws SGTServiceException;

    /**
     *
     * @throws SGTServiceException
     */
    void eliminaPlantillaArcaBatch() throws SGTServiceException;
}
