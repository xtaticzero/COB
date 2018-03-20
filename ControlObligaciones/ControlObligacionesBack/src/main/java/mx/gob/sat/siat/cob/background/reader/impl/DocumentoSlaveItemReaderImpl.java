package mx.gob.sat.siat.cob.background.reader.impl;

import mx.gob.sat.siat.cob.background.holder.CargaArchivosHolder;
import mx.gob.sat.siat.cob.background.holder.HolderCargaMasivaMap;
import mx.gob.sat.siat.cob.background.reader.DocumentoSlaveItemReader;
import mx.gob.sat.siat.cob.background.util.UtileriasBackground;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.DocumentoARCA;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("documentoSlaveItemReader")
@Scope("step")
public class DocumentoSlaveItemReaderImpl implements DocumentoSlaveItemReader {

    private int fromId;
    private int toId;
    private int index;
    @Autowired
    private HolderCargaMasivaMap holderCargaMasiva;
    private String idVigilanciaAdministracionLocal;
    private Long idVigilancia;

    @Override
    public DocumentoARCA read() {
        if (holderCargaMasiva.getHolderCargaMasiva().get(idVigilancia + idVigilanciaAdministracionLocal) != null) {
            if (UtileriasBackground.puedeRegresarValores(index, toId,
                    ((CargaArchivosHolder) holderCargaMasiva.getHolderCargaMasiva().get(idVigilancia + idVigilanciaAdministracionLocal))
                    .getDocumentosARCA().size())) {
                return ((CargaArchivosHolder) holderCargaMasiva.getHolderCargaMasiva().get(idVigilancia + idVigilanciaAdministracionLocal))
                        .getDocumentosARCA().get(index++);
            }
        }
        return null;
    }

    public int getFromId() {
        return fromId;
    }

    @Value("#{stepExecutionContext[fromId]}")
    public void setFromId(int fromId) {
        this.fromId = fromId;
        index = fromId;
    }

    @Value("#{stepExecutionContext[toId]}")
    public void setToId(int toId) {
        this.toId = toId;
    }

    @Value("#{jobParameters['idAdmonLocal']}")
    public void setIdVigilanciaAdministracionLocal(String idVigilanciaAdministracionLocal) {
        this.idVigilanciaAdministracionLocal = idVigilanciaAdministracionLocal;
    }

    @Value("#{jobParameters['idVigilancia']}")
    public void setIdVigilancia(Long idVigilancia) {
        this.idVigilancia = idVigilancia;
    }
}
