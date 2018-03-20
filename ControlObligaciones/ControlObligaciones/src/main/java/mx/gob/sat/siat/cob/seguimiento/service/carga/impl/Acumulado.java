package mx.gob.sat.siat.cob.seguimiento.service.carga.impl;

import java.io.IOException;
import java.util.ArrayList;
import mx.gob.sat.siat.cob.seguimiento.service.carga.Agrupamiento;
import mx.gob.sat.siat.cob.seguimiento.exception.AgrupamientoException;
import org.apache.log4j.Logger;


/**
 *
 * @author root
 */
public class Acumulado extends Agrupamiento {
    
    private Logger log =Logger.getLogger(Acumulado.class);
    
    /**
     *
     */
    public Acumulado() {
        super();
    }

    /**
     *
     * @param rutaRepositorio
     * @throws AgrupamientoException
     */
    @Override
    public void agrupa(String rutaRepositorio, String nombreArchivo) throws AgrupamientoException {
        try {
            abreRecursos(rutaRepositorio);
            String linea = getBr().readLine();
            String[] componentes=null;
            int contador=1;
            StringBuilder temp=new StringBuilder();
            while(linea != null){
                if((contador % 10000) == 0 ){
                    log.debug("contador:"+contador++);
                }
                componentes = linea.split("\\|");

                //la llave del mapa consistira en el rfc
                setMapKey(componentes[1]);
                setListaDetalle(Agrupamiento.getMapaAgr().get(getMapKey()));
                if (getListaDetalle() == null) {
                    setListaDetalle(new ArrayList<String>());
                }
                temp.setLength(0);
                temp.append(linea).append("|").append(nombreArchivo);
                getListaDetalle().add(temp.toString());
                
                Agrupamiento.getMapaAgr().put(getMapKey(), getListaDetalle());
                linea = getBr().readLine();
            }
            cierraRecursos();
        } catch (IOException e) {
            throw new AgrupamientoException(e);
        } 
    }
}
