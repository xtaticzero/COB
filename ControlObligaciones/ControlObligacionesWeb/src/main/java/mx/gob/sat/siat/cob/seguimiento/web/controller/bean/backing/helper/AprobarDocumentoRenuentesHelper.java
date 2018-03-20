package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.helper;

import java.io.Serializable;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.db2.Paginador;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VisualizaDetalleRenuente;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VisualizaDocumentoRenuente;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VisualizaVigilanciaRenuentes;

/**
 *
 * @author root
 */
public class AprobarDocumentoRenuentesHelper implements Serializable {
    static final long serialVersionUID = 4632352179284946L;
    private List<VisualizaDocumentoRenuente> documentos;    
    private Paginador paginador;
    private long pagina;
    private VisualizaVigilanciaRenuentes visualizaVigilanciaRenuentes;
    private List<VisualizaDetalleRenuente> listaDetalle;
    private VisualizaDocumentoRenuente documentoSeleccionado;
    private String documentosGSon;

    public List<VisualizaDocumentoRenuente> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<VisualizaDocumentoRenuente> documentos) {
        this.documentos = documentos;
    }

    public Paginador getPaginador() {
        return paginador;
    }

    public void setPaginador(Paginador paginador) {
        this.paginador = paginador;
    }

    public long getPagina() {
        return pagina;
    }
    public void setPagina(long pagina) {
        this.pagina = pagina;
    }

    public VisualizaVigilanciaRenuentes getVisualizaVigilanciaRenuentes() {
        return visualizaVigilanciaRenuentes;
    }

    public void setVisualizaVigilanciaRenuentes(VisualizaVigilanciaRenuentes visualizaVigilanciaRenuentes) {
        this.visualizaVigilanciaRenuentes = visualizaVigilanciaRenuentes;
    }

    public List<VisualizaDetalleRenuente> getListaDetalle() {
        return listaDetalle;
    }

    public void setListaDetalle(List<VisualizaDetalleRenuente> listaDetalle) {
        this.listaDetalle = listaDetalle;
    } 

    public VisualizaDocumentoRenuente getDocumentoSeleccionado() {
        return documentoSeleccionado;
    }

    public void setDocumentoSeleccionado(VisualizaDocumentoRenuente documentoSeleccionado) {
        this.documentoSeleccionado = documentoSeleccionado;
    }

    public String getDocumentosGSon() {
        return documentosGSon;
    }

    public void setDocumentosGSon(String documentosGSon) {
        this.documentosGSon = documentosGSon;
    }
    
}
