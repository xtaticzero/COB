package mx.gob.sat.siat.cob.seguimiento.service.carga.impl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EtapaVigilanciaEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.SituacionIcepEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.UltimoDocGeneradoEnum;

/**
 *
 * @author root
 */
public class CargaInformacionDocumentosBuilder implements Serializable {

    /**
     *
     */
    public CargaInformacionDocumentosBuilder() {
        super();
    }

    /**
     *
     * @param mapa
     * @param idVigilancia
     * @param inicio
     * @param termino
     * @return
     */
    public List<Documento> creaDocumentos(Map<String, List<String>> mapa,
            long idVigilancia, int inicio, int termino, int tipoDocumento) {
        List<String> detalles;
        Documento currentDocumento;
        List<DetalleDocumento> currentListaDetalle;
        List<Documento> listaFinal = new ArrayList<Documento>();
        int contador = 0;
        
        for (Entry<String, List<String>> k : mapa.entrySet()) {
            contador++;
            if (contador < inicio) {
                continue;
            }
            if (contador > termino) {
                break;
            }
            detalles = k.getValue();
            currentDocumento = new Documento();
            currentListaDetalle = new ArrayList<DetalleDocumento>();

            currentDocumento.getVigilancia().setIdVigilancia(Long.valueOf(idVigilancia));
            currentDocumento.setIdEtapaVigilancia(EtapaVigilanciaEnum.ETAPA_1.getValor());
            currentDocumento.setFechaRegistro(new Date());
            currentDocumento.setConsideraRenuencia(0);
            currentDocumento.setEsUltimoEstado(EstadoDocumentoEnum.PENDIENTE_DE_PROCESAR.getValor());
            currentDocumento.setEsUltimoGenerado(UltimoDocGeneradoEnum.SI.getValor());
            currentDocumento.getVigilancia().setIdTipoDocumento(tipoDocumento);

            generaDetalle(detalles, currentDocumento, currentListaDetalle);

            currentDocumento.setDetalles(currentListaDetalle);
            listaFinal.add(currentDocumento);
        }
        return listaFinal;
    }

    private void generaDetalle(List<String> detalles, Documento currentDocumento,
            List<DetalleDocumento> currentListaDetalle) {
        DetalleDocumento currentDetalleDocumento = null;
        for (String d : detalles) {
            currentDetalleDocumento = new DetalleDocumento();
            String[] componentes = d.split("\\|");

            String boid = componentes[0];
            String rfc = componentes[1];
            String icep = componentes[2];
            String obl = componentes[3];
            String regimen = componentes[4];
            String ejercicio = componentes[5];
            String periodo = componentes[6];
            String periodicidad = componentes[7];
            String fechaVenc = componentes[8];
            String rutaArchivo = componentes[9];

            currentDocumento.setBoid(boid);
            currentDocumento.setRfc(rfc);

            currentDetalleDocumento.setClaveIcep(Long.valueOf(icep));
            currentDetalleDocumento.setIdObligacion(Integer.valueOf(obl));
            currentDetalleDocumento.setIdEjercicio(ejercicio);
            currentDetalleDocumento.setIdPeriodo(periodo);
            currentDetalleDocumento.setIdPeriodicidad(periodicidad);
            currentDetalleDocumento.setFechaVencimiento(fechaVenc);
            currentDetalleDocumento.setIdRegimen(regimen);

            currentDetalleDocumento.setTieneMultaExtemporaneidad(0);
            currentDetalleDocumento.setTieneMultaComplementaria(0);
            currentDetalleDocumento.setRutaArchivo(rutaArchivo);
            currentDetalleDocumento.setBoid(boid);
            currentDetalleDocumento.setIdSituacionIcep(SituacionIcepEnum.INCUMPLIDO.getValor());

            currentListaDetalle.add(currentDetalleDocumento);
        }
    }

}
