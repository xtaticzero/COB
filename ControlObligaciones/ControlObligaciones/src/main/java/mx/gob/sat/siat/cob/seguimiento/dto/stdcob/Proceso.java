package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;
import java.util.Date;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;

public class Proceso implements Serializable, Comparable<Proceso>, Cloneable {

    public static final int TIPO_CADENA_PROCESAMIENTO = 1, TIPO_ORDEN_PROCESAMIENTO = 2;
    //@SuppressWarnings("compatibility:811956357787857930")
    private static final long serialVersionUID = 12321L;

    private Integer idProceso;
    private String nombre;
    private String descripcion;
    private String lanzador;
    private String programacion;
    private String excluir;
    private Integer estado;
    private Integer prioridad;
    private Integer intentos;
    private Integer intentosMax;
    private String idManager;
    private Date fechaInicioEjecucion;
    private Date fechaFinEjecucion;
    private Integer tipoCadena;

    public Proceso() {
        super();
    }

    public void setIdProceso(Integer idProceso) {
        this.idProceso = idProceso;
    }

    public Integer getIdProceso() {
        return idProceso;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setLanzador(String lanzador) {
        this.lanzador = lanzador;
    }

    public String getLanzador() {
        return lanzador;
    }

    public void setProgramacion(String programacion) {
        this.programacion = programacion;
    }

    public String getProgramacion() {
        return programacion;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setIntentos(Integer intentos) {
        this.intentos = intentos;
    }

    public Integer getIntentos() {
        return intentos;
    }

    public void setFechaInicioEjecucion(Date fechaInicioEjecucion) {
        if (fechaInicioEjecucion != null) {
            this.fechaInicioEjecucion = (Date) fechaInicioEjecucion.clone();
        }
    }

    public Date getFechaInicioEjecucion() {
        if (fechaInicioEjecucion != null) {
            return (Date) fechaInicioEjecucion.clone();
        } else {
            return null;
        }
    }

    public void setFechaFinEjecucion(Date fechaFinEjecucion) {
        if (fechaFinEjecucion != null) {
            this.fechaFinEjecucion = (Date) fechaFinEjecucion.clone();
        }
    }

    public Date getFechaFinEjecucion() {
        if (fechaFinEjecucion != null) {
            return (Date) fechaFinEjecucion.clone();
        } else {
            return null;
        }
    }

    public void setIntentosMax(Integer intentosMax) {
        this.intentosMax = intentosMax;
    }

    public Integer getIntentosMax() {
        return intentosMax;
    }

    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
    }

    public Integer getPrioridad() {
        return prioridad;
    }

    public void setIdManager(String idManager) {
        this.idManager = idManager;
    }

    public String getIdManager() {
        return idManager;
    }

    public void setExcluir(String excluir) {
        this.excluir = excluir;
    }

    public String getExcluir() {
        return excluir;
    }

    public void setTipoCadena(Integer tipoCadena) {
        this.tipoCadena = tipoCadena;
    }

    public Integer getTipoCadena() {
        return tipoCadena;
    }

    @Override
    public boolean equals(Object o) {
        if (o != null) {
            if (o instanceof Proceso) {
                final Proceso other = (Proceso) o;
                if (other.getIdProceso().equals(this.getIdProceso())
                        && other.getNombre().equals(this.getNombre())
                        && other.getDescripcion().equals(this.getDescripcion())
                        && other.getEstado().equals(this.getEstado())
                        && other.getIntentos().equals(this.getIntentos())
                        && igualdadIntentosMaximos(other)
                        && igualdadPrioridad(other)
                        && igualdadTipoCadena(other)
                        && igualdadProgramacion(other)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.idProceso != null ? this.idProceso.hashCode() : 0);
        return hash;
    }

    @Override
    public int compareTo(Proceso o) {
        if (o.getIdProceso().equals(this.getIdProceso())
                || igualdadEntreLanzadores(o.getLanzador())) {
            return 0;
        }
        if (o.getLanzador() != null) {
            for (String p : lanzadoresSeparados(o.getLanzador())) {
                if (igualdadEntreLanzadorProceso(p, this.getIdProceso().toString())) {
                    return -1;
                }
            }
        } else if (o.getLanzador() == null) {
            return 1;
        }
        if (this.getLanzador() != null) {
            for (String p : lanzadoresSeparados(this.getLanzador())) {
                if (igualdadEntreLanzadorProceso(p, o.getIdProceso().toString())) {
                    return 1;
                }
            }
        } else if (this.getLanzador() == null) {
            return -1;
        }
        return 1;
    }

    private String[] lanzadoresSeparados(String lanzadores) {
        String[] lanzadoresSerparados = new String[0];
        if (lanzadores != null) {
            lanzadoresSerparados = lanzadores.split(",");
        }
        return lanzadoresSerparados;
    }

    private boolean igualdadEntreLanzadorProceso(String lanzador, String idProceso) {
        if (lanzador.equals(idProceso)) {
            return true;
        }
        return false;
    }

    private boolean igualdadEntreLanzadores(String lanzadoresObject) {
        for (String lanzadorThis : lanzadoresSeparados(this.getLanzador())) {
            for (String lanzadorObject : lanzadoresSeparados(lanzadoresObject)) {
                if (igualdadEntreLanzadorProceso(lanzadorThis, lanzadorObject)) {
                    return true;
                }

            }
        }
        return false;
    }

    @Override
    public Proceso clone() throws CloneNotSupportedException {
        return (Proceso) super.clone();
    }

    private boolean igualdadProgramacion(Proceso other) {
        if (this.getProgramacion() == null && other.getProgramacion() == null) {
            return true;
        }
        if (this.getProgramacion() != null && other.getProgramacion() != null) {
            if (this.getProgramacion().trim().equals(other.getProgramacion().trim())) {
                return true;
            }
        } else if (this.getProgramacion() == null && other.getProgramacion() != null) {
            if (other.getProgramacion().trim().length() > ConstantsCatalogos.CERO) {
                return false;
            } else if (other.getProgramacion().trim().length() == ConstantsCatalogos.CERO) {
                return true;
            }
        }
        return false;
    }

    private boolean igualdadTipoCadena(Proceso other) {
        if (this.getTipoCadena() == null && other.getTipoCadena() == null) {
            return true;
        }
        if (this.getTipoCadena() != null && other.getTipoCadena() != null) {
            if (this.getTipoCadena().equals(other.getTipoCadena())) {
                return true;
            }
        } else if (this.getTipoCadena() == null
                && other.getTipoCadena() != null) {
            return false;
        }
        return false;
    }

    private boolean igualdadPrioridad(Proceso other) {
        if (this.getPrioridad() == null && other.getPrioridad() == null) {
            return true;
        }
        if (this.getPrioridad() != null && other.getPrioridad() != null) {
            if (this.getPrioridad().equals(other.getPrioridad())) {
                return true;
            }
        } else if (this.getPrioridad() == null
                && other.getPrioridad() != null) {
            return false;
        }
        return false;
    }

    private boolean igualdadIntentosMaximos(Proceso other) {
        if (this.getIntentosMax() == null && other.getIntentosMax() == null) {
            return true;
        }
        if (this.getIntentosMax() != null && other.getIntentosMax() != null) {
            if (this.getIntentosMax().equals(other.getIntentosMax())) {
                return true;
            }
        } else if (this.getIntentosMax() == null
                && other.getIntentosMax() != null) {
            return false;
        }
        return false;
    }
}