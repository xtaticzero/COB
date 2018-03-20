package mx.gob.sat.siat.cob.seguimiento.util.exportador.pdf;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Obligacion;


/**
 *
 * @author root
 */
public class ObligacionPdf extends GeneraPdf {

    /**
     *
     */
    public ObligacionPdf() {
        super();
    }

    /**
     *
     * @param datos
     */
    @Override
    public void colocarDatos(List<Object> datos) {
        for (Object obj : datos) {
            Obligacion obligacion = (Obligacion) obj;
            super.getTb().addCell(obligacion.getDescrObliCompleta());
            super.getTb().addCell(obligacion.getConcepto());
            super.getTb().addCell(obligacion.getDescripcion());
            super.getTb().addCell(obligacion.getValorBooleano().getNombre());

        }
    }
}
