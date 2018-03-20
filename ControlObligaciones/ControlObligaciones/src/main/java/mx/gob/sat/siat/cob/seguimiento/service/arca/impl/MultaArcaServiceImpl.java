/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.arca.impl;

import java.util.ArrayList;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.DoctoEnvioArcaDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.DocumentoARCADAO;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.ObligacionArcaDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.PeriodoArcaDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.DocumentoARCA;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.Obligacion;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.ObligacionPeriodo;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.Periodo;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoMultaEnum;
import mx.gob.sat.siat.cob.seguimiento.service.arca.MultaARCAService;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cobranza.domain.Resolucion;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author rodrigo
 */
@Service
public class MultaArcaServiceImpl implements MultaARCAService {

    @Autowired
    private DocumentoARCADAO documentoARCADAO;
    @Autowired
    private PeriodoArcaDAO periodoDAO;
    @Autowired
    private ObligacionArcaDAO obligacionDAO;
    @Autowired
    private DoctoEnvioArcaDAO doctoEnvioArcaDAO;
    @Autowired
    private DocumentoDAO documentoDAO;
    private Logger log = Logger.getLogger(MultaArcaServiceImpl.class);

    @Propagable(catched = true)
    @Override
    public void generarMulta(Documento documento, Resolucion resolucion, TipoMultaEnum constanteResolMotivo) throws SGTServiceException {

        double importe = 0.0;
        DocumentoARCA doctoArca = doctoEnvioArcaDAO.multaDoctoArca(documento.getNumeroControl(), resolucion.getIdResolucion());
        for (DetalleDocumento detalle : documento.getDetalles()) {
            if (detalle.getImporteCargo() != null) {
                importe += detalle.getImporteCargo();
            }
        }
        doctoArca.setImporte(String.valueOf(importe));
        insertarMultaArca(doctoArca);
        List<ObligacionPeriodo> lstObligacionPeriodo = obtenerDetalleObligacionesPeriodos(documento);
        List<Obligacion> obligaciones = new ArrayList<Obligacion>();
        List<Periodo> periodos = new ArrayList<Periodo>();

        for (ObligacionPeriodo obligaPeriodo : lstObligacionPeriodo) {
            obligaciones.add(obligaPeriodo.getObligacion());
            periodos.add(obligaPeriodo.getPeriodo());
        }
        log.info("Comienza a insertar Obligaciones");
        insertObligaciones(obligaciones);
        log.info("Comienza a insertar Periodos");
        insertPeriodos(periodos);

    }

    @Override
    public void generarMultasMasiva(List<Documento> documentos, List<Resolucion> resoluciones,
            TipoMultaEnum constanteResolMotivo) throws SGTServiceException {
    }

    void insertarMultaArca(DocumentoARCA doctoArca) {
        List<DocumentoARCA> documentosArca = new ArrayList<DocumentoARCA>();
        documentosArca.add(doctoArca);

        insertarMultaArca(documentosArca);
    }

    void insertarMultaArca(List<DocumentoARCA> doctosArca) {
        documentoARCADAO.insert(doctosArca);
    }

    private List<ObligacionPeriodo> obtenerDetalleObligacionesPeriodos(Documento documento) {
        return doctoEnvioArcaDAO.multaObligacionPeriodoArca(documento.getNumeroControl(),
                documento.getDetalles());
    }

    private void insertObligaciones(List<Obligacion> obligaciones) {
        obligacionDAO.insert(obligaciones);
    }

    private void insertPeriodos(List<Periodo> periodos) {
        periodoDAO.insert(periodos);
    }

    void actualizaEstadoDocumento(List<DocumentoARCA> doctosARCA) {
        List<String> numerosControl = new ArrayList<String>();
        for (DocumentoARCA doctoARCA : doctosARCA) {
            numerosControl.add(doctoARCA.getId());
        }
        documentoDAO.actualizarEstadoDocumento(numerosControl, EstadoDocumentoEnum.PENDIENTE_DE_IMPRIMIR);
    }
}
