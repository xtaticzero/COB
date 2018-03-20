package mx.gob.sat.siat.cob.seguimiento.service.jobmanager.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import mx.gob.sat.siat.cob.seguimiento.br.ProcesoDetalleBR;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.ProcesoDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ConsultarProcesosFiltroDto;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Proceso;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ProcesoDetalle;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoJobEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoProceso;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.PrioridadJobEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.BusinessException;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.jobmanager.ProcesoDetalleService;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcesoDetalleServiceImpl implements ProcesoDetalleService {

    @Autowired
    private ProcesoDAO procesoDao;
    @Autowired
    private ProcesoDetalleBR procesoDetalleBR;

    public ProcesoDetalleServiceImpl() {
        super();
    }

    @Override
    public ProcesoDetalle consultarProcesoDetalle(Integer idProceso) throws SGTServiceException {
        try {
            ProcesoDetalle resultado = null;
            List<Integer> ids = new ArrayList<Integer>();
            ids.add(idProceso);
            List<Proceso> procesos = procesoDao.consultarPorId(ids);
            if (procesos.size() > 0) {
                resultado = new ProcesoDetalle();
                Proceso proceso = procesos.get(0);
                resultado.setProceso(proceso);

                List<Proceso> lanzadores = procesoDao.consultarPorId(Utilerias.charSeparatedToIntegerList(proceso.getLanzador(), ",", Integer.class));
                List<Proceso> excluyentes = procesoDao.consultarPorId(Utilerias.charSeparatedToIntegerList(proceso.getExcluir(), ",", Integer.class));

                resultado.setLanzadores(lanzadores);
                resultado.setExcluyentes(excluyentes);
            }
            return resultado;
        } catch (SeguimientoDAOException ex) {
            throw new SGTServiceException(ex);
        }
    }

    @Override
    public void guardar(ProcesoDetalle procesoDetalle) throws BusinessException, SGTServiceException {
        try {
            Proceso proceso = procesoDetalle.getProceso();
            StringBuilder sLanzadores = new StringBuilder("");
            StringBuilder sExcluyentes = new StringBuilder("");
            if (procesoDetalle.getLanzadores() != null) {
                for (Proceso lanzador : procesoDetalle.getLanzadores()) {
                    sLanzadores.append(lanzador.getIdProceso()).append(",");
                }
                if (procesoDetalle.getLanzadores().size() > 0) {
                    sLanzadores.deleteCharAt(sLanzadores.length() - 1);
                }
                proceso.setLanzador(sLanzadores.toString());

                if (procesoDetalle.getLanzadores().size() > 0) {
                    proceso.setPrioridad(PrioridadJobEnum.NULL.getIdVlue());
                    proceso.setTipoCadena(0);
                }
            } else {
                proceso.setLanzador("");
            }
            if (procesoDetalle.getExcluyentes() != null) {
                for (Proceso exclusion : procesoDetalle.getExcluyentes()) {
                    sExcluyentes.append(exclusion.getIdProceso()).append(",");
                }
                if (procesoDetalle.getExcluyentes().size() > 0) {
                    sExcluyentes.deleteCharAt(sExcluyentes.length() - 1);
                }
                proceso.setExcluir(sExcluyentes.toString());
            } else {
                proceso.setExcluir("");
            }

            if (proceso.getIdProceso() != null) {
                procesoDetalleBR.validarEdicion(procesoDetalle);
                if (isActivacion(proceso)) {
                    proceso.setIntentos(0);
                }
                proceso.setIdManager("");
                procesoDao.actualizar(proceso);
            } else {
                procesoDetalleBR.validarCreacion(procesoDetalle);
                proceso.setIntentos(0);
                procesoDao.crear(proceso);
            }
        } catch (SeguimientoDAOException ex) {
            throw new SGTServiceException(ex);
        }
    }

    @Override
    public List<Proceso> consultarPorAgregar(List<Proceso> actuales) throws SGTServiceException {
        try {
            ConsultarProcesosFiltroDto filtro = new ConsultarProcesosFiltroDto();
            for (Proceso p : actuales) {
                filtro.getExcluirProcesos().add(p);
            }
            return procesoDao.consultarProcesos(filtro);
        } catch (SeguimientoDAOException ex) {
            throw new SGTServiceException(ex);
        }
    }

    private boolean isActivacion(Proceso p) {
        return p.getEstado().equals(EstadoProceso.HABILITADO.getIdEdoDoc());
    }

    @Override
    public List<Proceso> cadenaLanzadores(Proceso proceso) throws SGTServiceException {
        List<Proceso> cadenaRetorno = new ArrayList<Proceso>();
        cadenaRetorno.addAll(procesosHijos(proceso));
        cadenaRetorno.addAll(procesosPadres(proceso));
        Collections.sort(cadenaRetorno);
        return cadenaRetorno;
    }

    @Override
    public List<Proceso> procesosPadres(Proceso proceso) throws SGTServiceException {
        try {
            List<Proceso> consulta = procesoDao.consultarPorId(Utilerias.charSeparatedToIntegerList(proceso.getIdProceso().toString(), ",", Integer.class));
            List<Proceso> aux;
            Set<Proceso> setCadena = new HashSet<Proceso>();
            for (Proceso lanzador : consulta) {
                while (lanzador.getLanzador() != null) {
                    aux = procesoDao.consultarPorId(Utilerias.charSeparatedToIntegerList(lanzador.getLanzador(), ",", Integer.class));
                    setCadena.addAll(aux);
                    lanzador = validaProceso(aux);
                }
            }
            List<Proceso> cadenaRetorno = new ArrayList<Proceso>(setCadena);
            Collections.sort(cadenaRetorno);
            return cadenaRetorno;
        } catch (SeguimientoDAOException ex) {
            throw new SGTServiceException(ex);
        }
    }

    @Override
    public List<Proceso> procesosHijos(Proceso proceso) throws SGTServiceException {
        try {
            List<Proceso> aux;
            Set<Proceso> setCadena = new HashSet<Proceso>();
            Proceso procesoConsulta = proceso;
            while (procesoConsulta != null) {
                aux = procesoDao.consultarPorLanzadores(procesoConsulta.getIdProceso().toString());
                setCadena.addAll(aux);
                procesoConsulta = validaProceso(aux);
            }
            List<Proceso> cadenaRetorno = new ArrayList<Proceso>(setCadena);
            Collections.sort(cadenaRetorno);
            return cadenaRetorno;
        } catch (SeguimientoDAOException ex) {
            throw new SGTServiceException(ex);
        }
    }

    private Proceso validaProceso(List<Proceso> aux) throws SeguimientoDAOException {
        if (aux != null && aux.size() > 0) {
            if (aux.size() > 1) {
                Proceso aunLanzadores = null;
                for (Proceso lanza : aux) {
                    if (!procesoDao.consultarPorLanzadores(lanza.getIdProceso().toString()).isEmpty()) {
                        aunLanzadores = lanza;
                        break;
                    } else {
                        aunLanzadores = lanza;
                    }
                }
                return aunLanzadores;
            } else {
                return (Proceso) aux.get(0);
            }
        }
        return null;
    }

    @Override
    public boolean isUltimoEnCadena(Proceso proceso) throws SGTServiceException {
        try {
            List<Proceso> lanzadores = procesoDao.consultarPorLanzadores(proceso.getIdProceso().toString());
            if (lanzadores == null || estanDesactivos(lanzadores)) {
                return true;
            }
        } catch (SeguimientoDAOException ex) {
            throw new SGTServiceException(ex);
        }
        return false;
    }

    @Override
    public boolean isPrimeroEnCadena(Proceso proceso) throws SGTServiceException {
        try {
            return procesoDao.isPrimeroEnCadena(proceso.getIdProceso());
        } catch (SeguimientoDAOException ex) {
            throw new SGTServiceException(ex);
        }
    }

    @Override
    public List<Proceso> consultarPorId(String lanzador) throws SGTServiceException {
        List<Proceso> procesos = null;
        try {
            procesos = procesoDao.consultarPorId(Utilerias.charSeparatedToIntegerList(lanzador, ",", Integer.class));
            Collections.sort(procesos);
        } catch (SeguimientoDAOException ex) {
            throw new SGTServiceException(ex);
        }
        return procesos;
    }

    @Override
    public List<Proceso> consultarPorLanzadores(String lanzador) throws SGTServiceException {
        List<Proceso> procesos = null;
        try {
            procesos = procesoDao.consultarPorLanzadores(lanzador);
            Collections.sort(procesos);
        } catch (SeguimientoDAOException ex) {
            throw new SGTServiceException(ex);
        }
        return procesos;
    }

    private boolean estanDesactivos(List<Proceso> lanzadores) {
        int contador = ConstantsCatalogos.CERO;
        for (Proceso p : lanzadores) {
            if (p.getEstado().equals(EstadoJobEnum.INACTIVO.getIdVlue())) {
                contador++;
            }
        }
        return lanzadores.size() == contador;
    }

}
