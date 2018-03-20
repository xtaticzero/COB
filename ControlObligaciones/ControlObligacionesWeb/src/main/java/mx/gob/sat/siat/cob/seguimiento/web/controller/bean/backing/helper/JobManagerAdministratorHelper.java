/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.helper;

import java.util.ArrayList;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Proceso;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ProcesoDetalle;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoJobEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.PrioridadJobEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoProcesamientoEnum;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;

/**
 *
 * @author juan
 */
public class JobManagerAdministratorHelper {

    private String msgErrorPrioridad;
    private String msgErrorProgramacion;
    private String msgErrorTipoCadena;
    private String msgErrNombre;
    private boolean msgErrDescReq;
    private boolean msgErrDescLength;
    private boolean msgErrNomReq;
    private boolean msgErrNomLength;

    private boolean btnRegresar;
    private boolean tipoProcesamiento;
    private int minimumLength = ConstantsCatalogos.DIEZ;
    private String projectId;
    private int numeroMensajes = ConstantsCatalogos.UNO;
    private String mensajeInfo;
    private String mensajeAccion;
    private boolean activadoGuardadoCadena = false;
    private boolean confirmarDialogo = false;
    private boolean mostrarBotones = true;

    private Integer estado;

    public JobManagerAdministratorHelper() {
    }

    public void clear() {
        setMsgErrorProgramacion("");
        setMsgErrorTipoCadena("");
        setMsgErrorPrioridad("");
        setMsgErrDescLength(false);
        setMsgErrDescReq(false);
        setMsgErrNomLength(false);
        setMsgErrNomReq(false);
    }

    /**
     * Regresa una lista con solo procesos con estado inactivo.
     *
     * @param procesos
     * @return
     */
    public List<Proceso> lanzadoresInactivos(List<Proceso> procesos) {
        List<Proceso> lanzadoresInactivos = new ArrayList<Proceso>();
        for (Proceso p : procesos) {
            if (p.getEstado().equals((EstadoJobEnum.INACTIVO.getIdVlue()))) {
                lanzadoresInactivos.add(p);
            }
        }
        return lanzadoresInactivos;
    }

    /**
     * Regresa una lista con solo procesos con estado diferente de inactivo.
     *
     * @param procesos
     * @return
     */
    public List<Proceso> lanzadoresDiferentesDeInactivos(List<Proceso> procesos) {
        List<Proceso> lanzadoresDInactivosctivos = new ArrayList<Proceso>();
        for (Proceso p : procesos) {
            if (p.getEstado().intValue() != EstadoJobEnum.INACTIVO.getIdVlue()) {
                lanzadoresDInactivosctivos.add(p);
            }
        }
        return lanzadoresDInactivosctivos;
    }

    /**
     * Regresa una lista con solo procesos con estado por ejecutar y en
     * ejecucion.
     *
     * @param procesos
     * @return
     */
    public List<Proceso> lanzadoresPorEjecutarEnEjecucion(List<Proceso> procesos) {
        List<Proceso> lanzadoresPorEjecutarEnEjecucion = new ArrayList<Proceso>();
        for (Proceso p : procesos) {
            if (p.getEstado().equals(EstadoJobEnum.POR_EJECUTAR.getIdVlue())
                    || p.getEstado().equals(EstadoJobEnum.EJECUCION.getIdVlue())) {
                lanzadoresPorEjecutarEnEjecucion.add(p);
            }
        }
        return lanzadoresPorEjecutarEnEjecucion;
    }

    /**
     * Regresa numero de datos pertenecidos a la lista procesos con estado
     * diferente de inactivo.
     *
     * @param procesos
     * @return
     */
    public int numeroDiferenteDeInactivos(List<Proceso> procesos) {
        int inactivos = ConstantsCatalogos.CERO;
        for (Proceso p : procesos) {
            if (p.getEstado().intValue() != EstadoJobEnum.INACTIVO.getIdVlue()) {
                inactivos++;
            }
        }
        return inactivos;
    }

    /**
     * Regresa true si todos los datos pertenecientes a la lista estan como
     * inactivos.
     *
     * @param procesos
     * @return
     */
    public boolean estanInactivos(List<Proceso> procesos) {
        int contador = ConstantsCatalogos.CERO;
        for (Proceso p : procesos) {
            if (p.getEstado().equals(EstadoJobEnum.INACTIVO.getIdVlue())) {
                contador++;
            }
        }
        return procesos.size() == contador;
    }

    /**
     * Regresa true si todos los datos pertenecientes a la lista estan como
     * diferentes de inactivos.
     *
     * @param procesos
     * @return
     */
    public boolean estanDiferenteInactivo(List<Proceso> procesos) {
        return procesos.size() == numeroDiferenteDeInactivos(procesos);
    }

    /**
     * Identifica si el proceso esta con estado inactivo.
     *
     * @param estado
     * @return
     */
    public boolean isInactivo(Integer estado) {
        if (estado.equals(ConstantsCatalogos.CERO)
                || estado.equals(ConstantsCatalogos.SIETE)) {
            return true;
        }
        return false;
    }

    /**
     * Metodo que verifica si ocurrio un cambio en contra del estado que tiene
     * actualmente en la base de datos contra el estado que se pudiera ver
     * modificado en la pantalla.
     *
     * @param estadoActual
     * @param estadoUpdate
     * @return
     *
     */
    public boolean cambioEstado(Integer estadoActual, Integer estadoUpdate) {
        Integer actual = 0;
        if (isInactivo(estadoActual)) {
            actual = EstadoJobEnum.INACTIVO.getIdVlue();
        } else {
            actual = EstadoJobEnum.ACTIVO.getIdVlue();
        }
        if (!actual.equals(estadoUpdate)) {
            return true;
        }
        return false;
    }

    /**
     * Metodo que verifica si ocurrio un cambio en contra de la prioridad que
     * tiene actualmente en la base de datos contra la prioridad que se pudiera
     * ver modificado en la pantalla.
     *
     * @param prioridadUpdate
     * @return
     *
     */
    public boolean cambioPrioridad(Integer prioridadUpdate) {
        if (!prioridadUpdate.equals(PrioridadJobEnum.NULL.getIdVlue())) {
            return true;
        }
        return false;
    }

    /**
     * Retorna una table que sera presentanda en el envio de correo.
     *
     * @param antes
     * @param despues
     * @return
     */
    public String crearTabla(ProcesoDetalle antes, ProcesoDetalle despues) {
        StringBuilder tabla = new StringBuilder();
        tabla.append("<table cellpadding='7' border='1' style='text-align:left;'><tr><th></th><th>Antes</th><th>Después</th></tr>\n").
                append("<tr><th>Nombre</th><th>").append(antes.getProceso().getNombre()).append("</th><th>").append(despues.getProceso().getNombre()).append("</th></tr>\n").
                append("<tr><th>Descripción</th><th>").append(antes.getProceso().getDescripcion()).append("</th><th>").append(despues.getProceso().getDescripcion()).append("</th></tr>\n").
                append("<tr><th>Prioridad</th><th>").append(getPrioridad(antes.getProceso().getPrioridad())).append("</th><th>").append(getPrioridad(despues.getProceso().getPrioridad())).append("</th></tr>\n").
                append("<tr><th>Programación</th><th>").append(getString(antes.getProceso().getProgramacion())).append("</th><th>").append(getString(despues.getProceso().getProgramacion())).append("</th></tr>\n").
                append("<tr><th>Estado</th><th>").append(getEstado(antes.getProceso().getEstado())).append("</th><th>").append(getEstado(despues.getProceso().getEstado())).append("</th></tr>\n").
                append("<tr><th>Intentos Máximos</th><th>").append(antes.getProceso().getIntentosMax()).append("</th><th>").append(despues.getProceso().getIntentosMax()).append("</th></tr>\n").
                append("<tr><th>Tipo Procesamiento</th><th>").append(getTipoProcesamiento(antes.getProceso().getTipoCadena())).append("</th><th>").append(getTipoProcesamiento(despues.getProceso().getTipoCadena())).append("</th></tr>\n").
                append("<tr><th>Lanzadores</th><th><table>\n").
                append(crearListadoTabla(antes.getLanzadores(), despues.getLanzadores())).
                append("<tr><th>Excluyentes</th><th><table>\n").
                append(crearListadoTabla(antes.getExcluyentes(), despues.getExcluyentes())).
                append("</table>");
        return tabla.toString();
    }

    /**
     * Crea el listado de los lanzadores y excluyentes.
     */
    private String crearListadoTabla(List<Proceso> anteriores, List<Proceso> modificados) {
        StringBuilder tabla = new StringBuilder();
        for (Proceso anterior : anteriores) {
            tabla.append("<tr><th>").append(anterior.getDescripcion()).append("</th></tr>");
        }
        tabla.append("</table></th><th><table>\n");
        for (Proceso modificado : modificados) {
            tabla.append("<tr><th>").append(modificado.getDescripcion()).append("</th></tr>");
        }
        tabla.append("</table></th></tr>");
        return tabla.toString();
    }

    String getPrioridad(Integer idPrioridad) {
        if (idPrioridad.equals(0)) {
            return "";
        }
        for (PrioridadJobEnum prioridad : PrioridadJobEnum.values()) {
            if (prioridad.getIdVlue().equals(idPrioridad)) {
                return prioridad.getEtiqueta();
            }
        }
        return "";
    }

    String getTipoProcesamiento(Integer idProcesamiento) {
        for (TipoProcesamientoEnum procesamiento : TipoProcesamientoEnum.values()) {
            if (procesamiento.getIdVlue().equals(idProcesamiento)) {
                return procesamiento.getEtiqueta();
            }
        }
        return "";
    }

    String getEstado(Integer idEstado) {
        for (EstadoJobEnum estadoEnum : EstadoJobEnum.values()) {
            if (estadoEnum.getIdVlue().equals(idEstado)) {
                return estadoEnum.getEtiqueta();
            }
        }
        return "";
    }

    String getString(String cadena) {
        if (cadena == null) {
            return "";
        } else {
            return cadena;
        }
    }

    /**
     * Getters and setters.
     */
    public String getMsgErrorPrioridad() {
        return msgErrorPrioridad;
    }

    public void setMsgErrorPrioridad(String msgErrorPrioridad) {
        this.msgErrorPrioridad = msgErrorPrioridad;
    }

    public String getMsgErrorProgramacion() {
        return msgErrorProgramacion;
    }

    public void setMsgErrorProgramacion(String msgErrorProgramacion) {
        this.msgErrorProgramacion = msgErrorProgramacion;
    }

    public String getMsgErrorTipoCadena() {
        return msgErrorTipoCadena;
    }

    public void setMsgErrorTipoCadena(String msgErrorTipoCadena) {
        this.msgErrorTipoCadena = msgErrorTipoCadena;
    }

    public String getMsgErrNombre() {
        return msgErrNombre;
    }

    public void setMsgErrNombre(String msgErrNombre) {
        this.msgErrNombre = msgErrNombre;
    }

    public boolean isMsgErrDescReq() {
        return msgErrDescReq;
    }

    public void setMsgErrDescReq(boolean msgErrDescReq) {
        this.msgErrDescReq = msgErrDescReq;
    }

    public boolean isMsgErrDescLength() {
        return msgErrDescLength;
    }

    public void setMsgErrDescLength(boolean msgErrDescLength) {
        this.msgErrDescLength = msgErrDescLength;
    }

    public boolean isMsgErrNomReq() {
        return msgErrNomReq;
    }

    public void setMsgErrNomReq(boolean msgErrNomReq) {
        this.msgErrNomReq = msgErrNomReq;
    }

    public boolean isMsgErrNomLength() {
        return msgErrNomLength;
    }

    public void setMsgErrNomLength(boolean msgErrNomLength) {
        this.msgErrNomLength = msgErrNomLength;
    }

    public boolean isBtnRegresar() {
        return btnRegresar;
    }

    public void setBtnRegresar(boolean btnRegresar) {
        this.btnRegresar = btnRegresar;
    }

    public boolean isTipoProcesamiento() {
        return tipoProcesamiento;
    }

    public void setTipoProcesamiento(boolean tipoProcesamiento) {
        this.tipoProcesamiento = tipoProcesamiento;
    }

    public int getMinimumLength() {
        return minimumLength;
    }

    public void setMinimumLength(int minimumLength) {
        this.minimumLength = minimumLength;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public int getNumeroMensajes() {
        return numeroMensajes;
    }

    public void setNumeroMensajes(int numeroMensajes) {
        this.numeroMensajes = numeroMensajes;
    }

    public String getMensajeInfo() {
        return mensajeInfo;
    }

    public void setMensajeInfo(String mensajeInfo) {
        this.mensajeInfo = mensajeInfo;
    }

    public String getMensajeAccion() {
        return mensajeAccion;
    }

    public void setMensajeAccion(String mensajeAccion) {
        this.mensajeAccion = mensajeAccion;
    }

    public boolean isActivadoGuardadoCadena() {
        return activadoGuardadoCadena;
    }

    public void setActivadoGuardadoCadena(boolean activadoGuardadoCadena) {
        this.activadoGuardadoCadena = activadoGuardadoCadena;
    }

    public boolean isConfirmarDialogo() {
        return confirmarDialogo;
    }

    public void setConfirmarDialogo(boolean confirmarDialogo) {
        this.confirmarDialogo = confirmarDialogo;
    }

    public boolean isMostrarBotones() {
        return mostrarBotones;
    }

    public void setMostrarBotones(boolean mostrarBotones) {
        this.mostrarBotones = mostrarBotones;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

}
