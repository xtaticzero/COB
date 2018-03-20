package mx.gob.sat.siat.cob.seguimiento.service.catalogos.impl;

import java.util.ArrayList;
import mx.gob.sat.siat.cob.seguimiento.exception.AgrupamientoException;
import mx.gob.sat.siat.cob.seguimiento.service.carga.Agrupamiento;
import org.apache.log4j.Logger;

/**
 *
 * @author root
 */
public class FundamentoLegalArchivo extends Agrupamiento {

    private Logger log =Logger.getLogger(FundamentoLegalArchivo.class);

    /**
     *
     * @param rutaRepositorio
     * @param nombreArchivo
     * @throws AgrupamientoException
     */
    @Override
    public void agrupa(String rutaRepositorio, String nombreArchivo) throws AgrupamientoException {
        try{
            abreRecursos(rutaRepositorio);
            setMapKey(rutaRepositorio);
            String linea = getBr().readLine();
            int contador=1;
            StringBuilder temp=new StringBuilder();
            setListaDetalle(new ArrayList<String>());
            while(linea != null){
                if((contador % 1000) == 0 ){
                    log.debug("contador:"+contador++);
                }
                temp.setLength(0);
                temp.append(linea).append("|").append(nombreArchivo);
                getListaDetalle().add(temp.toString());
                linea = getBr().readLine();
            }
            Agrupamiento.getMapaAgr().put(getMapKey(),getListaDetalle());
            cierraRecursos();
        }
        catch(Exception e){
            throw new AgrupamientoException(e);
        }
    }

}
