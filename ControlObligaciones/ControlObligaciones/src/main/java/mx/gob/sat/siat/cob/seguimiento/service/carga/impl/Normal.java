package mx.gob.sat.siat.cob.seguimiento.service.carga.impl;

import java.util.ArrayList;
import mx.gob.sat.siat.cob.seguimiento.service.carga.Agrupamiento;
import mx.gob.sat.siat.cob.seguimiento.exception.AgrupamientoException;
import org.apache.log4j.Logger;

/**
 *
 * @author root
 */
public class Normal extends Agrupamiento {
    
    private Logger log =Logger.getLogger(Normal.class);
       
    /**
     *
     */
    public Normal() {
        super();
    }

    /**
     *
     * @param rutaRepositorio
     * @throws AgrupamientoException
     */
    @Override
    public void agrupa(String rutaRepositorio, String nombreArchivo) throws AgrupamientoException {
        try{
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
                //la llave del mapa consistira en el rfc+ejercicio (ABC0000000002013)
                temp.setLength(0);
                temp.append(componentes[1]).append(componentes[5]);
                setMapKey(temp.toString());
                this.setListaDetalle(Agrupamiento.getMapaAgr().get(getMapKey()));
                if (getListaDetalle() == null) {
                    setListaDetalle(new ArrayList<String>());
                }
                temp.setLength(0);
                temp.append(linea).append("|").append(nombreArchivo);
                getListaDetalle().add(temp.toString());
                Agrupamiento.getMapaAgr().put(getMapKey(),getListaDetalle());
                linea = getBr().readLine();
            }
            cierraRecursos();
        }
        catch(Exception e){
            throw new AgrupamientoException(e);
        }      
    }   
}