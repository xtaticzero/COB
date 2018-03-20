/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.holder;

import java.util.ArrayList;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.DocumentoARCA;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.ObligacionPeriodo;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.OrigenMulta;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.RequerimientosAnteriores;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaAdministracionLocal;
import org.springframework.stereotype.Component;

/**
 *
 * @author Juan
 */
@Component
public class CargaArchivosHolder {

    private List<DocumentoARCA> documentosARCA;
    private List<ObligacionPeriodo> obligacionPeriodos;
    private List<OrigenMulta> origenesMulta;
    private List<MultaDocumento> multasDocumentos;
    private List<RequerimientosAnteriores> requerimientos;
    private VigilanciaAdministracionLocal vigilanciaAdministracionLocal;

    public CargaArchivosHolder() {
        reset();
    }

    public List<DocumentoARCA> getDocumentosARCA() {
        return documentosARCA;
    }

    public void setDocumentosARCA(List<DocumentoARCA> documentosARCA) {
        this.documentosARCA = documentosARCA;
    }

    public List<ObligacionPeriodo> getObligacionPeriodos() {
        return obligacionPeriodos;
    }

    public void setObligacionPeriodos(List<ObligacionPeriodo> obligacionPeriodos) {
        this.obligacionPeriodos = obligacionPeriodos;
    }

    public List<OrigenMulta> getOrigenesMulta() {
        return origenesMulta;
    }

    public void setOrigenesMulta(List<OrigenMulta> origenesMulta) {
        this.origenesMulta = origenesMulta;
    }

    public List<MultaDocumento> getMultasDocumentos() {
        return multasDocumentos;
    }

    public void setMultasDocumentos(List<MultaDocumento> multasDocumentos) {
        this.multasDocumentos = multasDocumentos;
    }

    public VigilanciaAdministracionLocal getVigilanciaAdministracionLocal() {
        return vigilanciaAdministracionLocal;
    }

    public void setVigilanciaAdministracionLocal(VigilanciaAdministracionLocal vigilanciaAdministracionLocal) {
        this.vigilanciaAdministracionLocal = vigilanciaAdministracionLocal;
    }

    public List<RequerimientosAnteriores> getRequerimientos() {
        return requerimientos;
    }

    public void setRequerimientos(List<RequerimientosAnteriores> requerimientos) {
        this.requerimientos = requerimientos;
    }

    public void reset() {
        if (documentosARCA == null) {
            documentosARCA = new ArrayList<DocumentoARCA>();
        } else {
            documentosARCA.clear();
        }

        if (obligacionPeriodos == null) {
            obligacionPeriodos = new ArrayList<ObligacionPeriodo>();
        } else {
            obligacionPeriodos.clear();
        }

        if (origenesMulta == null) {
            origenesMulta = new ArrayList<OrigenMulta>();
        } else {
            origenesMulta.clear();
        }

        if (multasDocumentos == null) {
            multasDocumentos = new ArrayList<MultaDocumento>();
        } else {
            multasDocumentos.clear();
        }

        if (requerimientos == null) {
            requerimientos = new ArrayList<RequerimientosAnteriores>();
        } else {
            requerimientos.clear();
        }

        vigilanciaAdministracionLocal = new VigilanciaAdministracionLocal();
    }
}
