/*	****************************************************************
 * Nombre de archivo: SGTBRetroARCA.java
 * Autores: Emmanuel Estrada Gonzalez 		 	 		
 * Bitácora de modificaciones:
 * CR/Defecto Fecha Autor Descripción del cambio
 * ---------------------------------------------------------------------------
 * N/A 30/10/2013 <Nombre del desarrollador que está modificando el archivo>
 * ---------------------------------------------------------------------------
 */
package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.util.Date;

public class SGTBRetroARCA {

    private int idProcesado;
    private Date fechaProcesado;

    /**
     *
     */
    public SGTBRetroARCA() {
        super();
    }

    /**
     *
     * @param idProcesado
     */
    public void setIdProcesado(int idProcesado) {
        this.idProcesado = idProcesado;
    }

    /**
     *
     * @return
     */
    public int getIdProcesado() {
        return idProcesado;
    }

    /**
     *
     * @param fechaProcesado
     */
    public void setFechaProcesado(Date fechaProcesado) {
        if (fechaProcesado != null) {
            this.fechaProcesado = (Date) fechaProcesado.clone();
        }
    }

    /**
     *
     * @return
     */
    public Date getFechaProcesado() {
        if (fechaProcesado != null) {
            return (Date) fechaProcesado.clone();
        } else {
            return null;
        }
    }
}
