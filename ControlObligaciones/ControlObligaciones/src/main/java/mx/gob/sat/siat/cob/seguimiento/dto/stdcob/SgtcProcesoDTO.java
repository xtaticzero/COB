/*	****************************************************************
 * Nombre de archivo: SgtcProcesoDTO.java
 * Autores: Emmanuel Estrada Gonzalez 		 	 		   
 * Bitácora de modificaciones:
 * CR/Defecto Fecha Autor Descripción del cambio
 * ---------------------------------------------------------------------------
 * N/A 20/08/2014 <Nombre del desarrollador que está modificando el archivo>
 * ---------------------------------------------------------------------------
 */
package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author emmanuel
 */
public class SgtcProcesoDTO implements Serializable {

    private Integer idProceso;
    private String nombre;
    private String descripcion;
    private String lanzador;
    private String programacion;
    private String excluir;
    private Integer prioridad;
    private Integer estado;
    private Integer intentos;
    private Integer intentosMax;
    private String idManager;
    private Date intentoEjecucion;
    private Date finEjecucion;
    private Integer tipoProcesamiento;

    public Integer getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(Integer idProceso) {
        this.idProceso = idProceso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLanzador() {
        return lanzador;
    }

    public void setLanzador(String lanzador) {
        this.lanzador = lanzador;
    }

    public String getProgramacion() {
        return programacion;
    }

    public void setProgramacion(String programacion) {
        this.programacion = programacion;
    }

    public String getExcluir() {
        return excluir;
    }

    public void setExcluir(String excluir) {
        this.excluir = excluir;
    }

    public Integer getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Integer getIntentos() {
        return intentos;
    }

    public void setIntentos(Integer intentos) {
        this.intentos = intentos;
    }

    public Integer getIntentosMax() {
        return intentosMax;
    }

    public void setIntentosMax(Integer intentosMax) {
        this.intentosMax = intentosMax;
    }

    public String getIdManager() {
        return idManager;
    }

    public void setIdManager(String idManager) {
        this.idManager = idManager;
    }

    public Date getIntentoEjecucion() {
        if (intentoEjecucion != null) {
            return (Date) intentoEjecucion.clone();
        } else {
            return null;
        }
    }

    public void setIntentoEjecucion(Date intentoEjecucion) {
        if (intentoEjecucion != null) {
            this.intentoEjecucion = new Date(intentoEjecucion.getTime());
        }
    }

    public Date getFinEjecucion() {
        if (finEjecucion != null) {
            return (Date) finEjecucion.clone();
        } else {
            return null;
        }
    }

    public void setFinEjecucion(Date finEjecucion) {
        if (finEjecucion != null) {
            this.finEjecucion = (Date) finEjecucion.clone();
        }
    }

    public Integer getTipoProcesamiento() {
        return tipoProcesamiento;
    }

    public void setTipoProcesamiento(Integer tipoProcesamiento) {
        this.tipoProcesamiento = tipoProcesamiento;
    }

    @Override
    public String toString() {
        try {
            return formatLine();
        } catch (NullPointerException ex) {
            return idProceso + "|" + nombre + "|" + descripcion + "|" + lanzador + "|" + programacion + "|" + excluir + "|" + prioridad + "|" + estado + "|" + intentos + "|" + intentosMax + "|" + idManager + "|" + intentoEjecucion + "|" + finEjecucion + "|" + tipoProcesamiento;
        }
    }

    private String formatLine() {
        StringBuffer cadena = new StringBuffer();
        int cellStandar = 16;
        int cellText = 70;

        int value = 0;

        cadena.append(idProceso.toString());
        value = cellStandar - idProceso.toString().length();
        cadena = addCellSpaces(cellStandar, cadena, value);

        cadena.append("|");
        cadena.append(nombre);

        if (nombre == null) {
            nombre = "null";
        }
        value = cellText - nombre.length();

        cadena = addCellSpaces(cellText, cadena, value);

        cadena.append("|");
        cadena.append(descripcion);
        if (descripcion == null) {
            descripcion = "null";
        }

        value = cellText - descripcion.length();
        cadena = addCellSpaces(cellText, cadena, value);

        cadena.append("|");
        cadena.append(lanzador);
        if (lanzador == null) {
            lanzador = "null";
        }

        value = cellStandar - lanzador.length();
        cadena = addCellSpaces(cellStandar, cadena, value);

        cadena.append("|");
        cadena.append(programacion);
        if (programacion == null) {
            programacion = "null";
        }

        value = cellStandar - programacion.length();
        cadena = addCellSpaces(cellStandar, cadena, value);

        cadena.append("|");
        cadena.append(excluir);
        if (excluir == null) {
            excluir = "null";
        }

        value = cellStandar - excluir.length();
        cadena = addCellSpaces(cellStandar, cadena, value);

        cadena.append("|");
        cadena.append(prioridad);
        value = cellStandar - prioridad.toString().length();
        cadena = addCellSpaces(cellStandar, cadena, value);

        cadena.append("|");
        cadena.append(estado.toString());

        value = cellStandar - estado.toString().length();
        cadena = addCellSpaces(cellStandar, cadena, value);

        cadena.append("|");
        cadena.append(intentos.toString());

        value = cellStandar - intentos.toString().length();
        cadena = addCellSpaces(cellStandar, cadena, value);

        cadena.append("|");
        cadena.append(intentosMax.toString());

        value = cellStandar - intentosMax.toString().length();
        cadena = addCellSpaces(cellStandar, cadena, value);

        cadena.append("|");
        cadena.append(idManager);
        if (idManager == null) {
            idManager = "null";
        }

        value = cellStandar - idManager.length();
        cadena = addCellSpaces(cellStandar, cadena, value);

        cadena.append("|");
        cadena.append(intentoEjecucion.toString());

        value = cellStandar - intentoEjecucion.toString().length();
        cadena = addCellSpaces(cellStandar, cadena, value);

        cadena.append("|");
        cadena.append(finEjecucion.toString());

        value = cellStandar - finEjecucion.toString().length();
        cadena = addCellSpaces(cellStandar, cadena, value);

        cadena.append("|");
        cadena.append(tipoProcesamiento.toString());

        value = cellStandar - tipoProcesamiento.toString().length();
        cadena = addCellSpaces(cellStandar, cadena, value);

        return cadena.toString();
    }

    private StringBuffer addCellSpaces(int alineSpaces, StringBuffer cellString, int numSpaces) {
        if (alineSpaces - numSpaces > 0) {
            for (int i = 0; i < numSpaces; i++) {
                cellString.append(" ");
            }
        }
        return cellString;
    }
}
