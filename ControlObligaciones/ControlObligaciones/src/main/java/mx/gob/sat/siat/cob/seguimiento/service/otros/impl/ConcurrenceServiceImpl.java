/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.otros.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ElementoConcurrente;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.CSColumnsOrderEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoServiciosConcurrentesEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.otros.ConcurrenceService;
import mx.gob.sat.siat.cob.seguimiento.util.ReadDocCSV;
import mx.gob.sat.siat.cob.seguimiento.util.constante.FilesPath;
import mx.gob.sat.siat.exception.DaoException;
import mx.gob.sat.siat.modelo.dao.SegbMovimientosDAO;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author emmanuel
 */
@Service
public class ConcurrenceServiceImpl implements ConcurrenceService {

    private ReadDocCSV csv=new ReadDocCSV();
    private String nameBaseFile;
    private final static int NUMERO_MAX_INTENTOS = 50;
    private final Logger log = Logger.getLogger(ConcurrenceServiceImpl.class);
    @Autowired
    @Qualifier("segbMovimientosDAOImpl")
    private SegbMovimientosDAO segbMovimientosDAO;

    public ConcurrenceServiceImpl() {
        nameBaseFile = FilesPath.SERVICIOS_CONCURRENTES;
    }

    @Override
    public boolean lockServices(TipoServiciosConcurrentesEnum idServicio, String firmaProceso) throws SGTServiceException {
        String newRow;

        List<String> lstRegistroProcesos = csv.leerArchivo(nameBaseFile);

        if (lstRegistroProcesos != null && lstRegistroProcesos.size() > 0) {
            for (String serviceRecord : lstRegistroProcesos) {
                String[] columns = serviceRecord.split(ReadDocCSV.CSV_SPLIT_BY);
                if (columns[CSColumnsOrderEnum.IDENTIFICADOR_PROCESO.getValor()].equals(idServicio.getValor())) {
                    if (columns[CSColumnsOrderEnum.IDENTIFICADOR_FIRMA_PROCESO.getValor()].equals(firmaProceso)) {
                        return false;
                    }
                }
            }
        }
        try {
            newRow = idServicio.getValor() + ReadDocCSV.CSV_SPLIT_BY + firmaProceso;
            csv.ecribirArchivo(nameBaseFile, newRow);
            return true;
        } catch (FileNotFoundException ex) {
            log.error(ex);
            throw new SGTServiceException(ex);
        } catch (IOException ex) {
            log.error(ex);
            throw new SGTServiceException(ex);
        }

    }

    @Override
    public boolean unlockServices(TipoServiciosConcurrentesEnum idServicio, String firmaProceso) {
        String newRow;
        newRow = idServicio.getValor() + ReadDocCSV.CSV_SPLIT_BY + firmaProceso;
        return eliminarEnProceso(newRow);
    }

    private boolean eliminarEnProceso(String processName) {
        int attemptsNumber = NUMERO_MAX_INTENTOS;
        boolean flgEliminacion = false;

        while (!flgEliminacion) {
            try {
                if (attemptsNumber <= 0) {
                    break;
                }
                csv.eliminarRegistro(nameBaseFile, processName);

                flgEliminacion = true;
            } catch (FileNotFoundException ex) {
                attemptsNumber--;
            } catch (IOException ex) {
                attemptsNumber--;
            }
        }
        return flgEliminacion;
    }

    @Override
    public boolean isLockedServices(TipoServiciosConcurrentesEnum idServicio, String firmaProceso) {
        List<String> lstRegistroProcesos = csv.leerArchivo(nameBaseFile);
        if (lstRegistroProcesos != null && lstRegistroProcesos.size() > 0) {
            for (String serviceRecord : lstRegistroProcesos) {
                String[] columns = serviceRecord.split(ReadDocCSV.CSV_SPLIT_BY);
                if (columns[CSColumnsOrderEnum.IDENTIFICADOR_PROCESO.getValor()].equals(idServicio.getValor())) {
                    if (columns[CSColumnsOrderEnum.IDENTIFICADOR_FIRMA_PROCESO.getValor()].equals(firmaProceso)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    @Override
    public void desbloqueoManual(List<ElementoConcurrente> elementosConcurrentes, SegbMovimientoDTO segMovDto) throws SGTServiceException {
        for (ElementoConcurrente elementoConcurrente : elementosConcurrentes) {
            unlockServices(elementoConcurrente.getIdServicio(), elementoConcurrente.getFirmaProceso());
            try {
                segbMovimientosDAO.insert(segMovDto);
            } catch (DaoException e) {
                throw new SGTServiceException(
                        "No se pudo hacer la emisión de las multas" + e);
            }catch(Exception e){
                log.error("Excepcion general " + e);
                throw new SGTServiceException(
                        "No se pudo hacer la emisión de las multas" + e);
            }
        }
    }

    @Override
    public List<ElementoConcurrente> obtenerElementosBloqueados() throws SGTServiceException {
        csv = new ReadDocCSV();
        boolean pasaLineaEncabezado = false;

        List<ElementoConcurrente> elementosConcurrentes = new ArrayList<ElementoConcurrente>();
        List<String> lstRegistroProcesos = csv.leerArchivo(nameBaseFile);

        if (lstRegistroProcesos != null && lstRegistroProcesos.size() > 0) {
            for (String serviceRecord : lstRegistroProcesos) {
                if (pasaLineaEncabezado) {
                    String[] columns = serviceRecord.split(ReadDocCSV.CSV_SPLIT_BY);
                    elementosConcurrentes.add(new ElementoConcurrente(getTipoServiciosConcurrentesEnum(columns[CSColumnsOrderEnum.IDENTIFICADOR_PROCESO.getValor()]),
                            columns[CSColumnsOrderEnum.IDENTIFICADOR_FIRMA_PROCESO.getValor()]));
                }
                pasaLineaEncabezado = true;
            }
        }
        return elementosConcurrentes;
    }

    private TipoServiciosConcurrentesEnum getTipoServiciosConcurrentesEnum(String valor) {
        for (TipoServiciosConcurrentesEnum tipo : TipoServiciosConcurrentesEnum.values()) {
            if (tipo.getValor().equals(valor)) {
                return tipo;
            }
        }
        return null;
    }

}
