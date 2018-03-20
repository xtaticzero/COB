package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaAdministracionLocal;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaAprobar;
import mx.gob.sat.siat.cob.seguimiento.util.IntegerMutable;

/**
 *
 * @author root
 */
public class AprobarVigilanciasHelper implements Serializable{
    
    private List<VigilanciaAprobar> vigilancias;
    private VigilanciaAdministracionLocal vigilancia;
    private IntegerMutable progress;

    public AprobarVigilanciasHelper() {
        vigilancias=new ArrayList<VigilanciaAprobar>(0);
        vigilancia=new VigilanciaAdministracionLocal();
        progress=new IntegerMutable();
    }

    public List<VigilanciaAprobar> getVigilancias() {
        return vigilancias;
    }

    public void setVigilancias(List<VigilanciaAprobar> vigilancias) {
        this.vigilancias = vigilancias;
    }

    public VigilanciaAdministracionLocal getVigilancia() {
        return vigilancia;
    }

    public void setVigilancia(VigilanciaAdministracionLocal vigilancia) {
        this.vigilancia = vigilancia;
    }

    public IntegerMutable getProgress() {
        return progress;
    }

    public void setProgress(IntegerMutable progress) {
        this.progress = progress;
    }
    
    
    
}
