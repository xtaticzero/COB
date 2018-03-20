package mx.gob.sat.siat.cob.background.service.carga;

/**
 *
 * @author christian.ventura
 */
public interface CargaInformacionService {

    /**
     *metodo proncipal donde se carga informacion de archivos para pasarlos a BD
     */
    void cargaInformacion();

    /**
     *borra los archivos una ves que se acabo de procesar toda la informacion
     */
    void borraArchivos();

}
