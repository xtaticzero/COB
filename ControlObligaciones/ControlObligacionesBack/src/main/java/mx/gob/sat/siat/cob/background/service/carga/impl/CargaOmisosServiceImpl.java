package mx.gob.sat.siat.cob.background.service.carga.impl;

import mx.gob.sat.siat.cob.background.service.carga.CargaOmisosService;
import mx.gob.sat.siat.cob.background.service.carga.CargaInformacionService;
import mx.gob.sat.siat.cob.background.service.carga.LogErrorCargaService;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author christian.ventura
 */
@Service
public class CargaOmisosServiceImpl implements CargaOmisosService{

    private Logger log =Logger.getLogger(CargaOmisosServiceImpl.class);

    @Autowired
    private CargaInformacionService cargaInformacionService;

    @Autowired
    private LogErrorCargaService logErrorCarga;

    /**
     *metodo principal para la carga de archivos de omisos
     */
    @Override
    public void ejecutarCargaOmisos() {
        log.info("version 8-noviembre-2014");
        logErrorCarga.setNombreArchivo("errores");
        cargaInformacionService.cargaInformacion();
        log.info("Se ejecuto la carga de omisos");
    }
    
}
