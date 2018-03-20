package mx.gob.sat.siat.cob.seguimiento.util.exportador.pdf;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.EjercicioFiscal;

public class EjercicioFiscalPdf extends GeneraPdf {

    /**
     *
     */
    public EjercicioFiscalPdf() {
        super();
    }

    /**
     *
     * @param datos
     */
    @Override
    public void colocarDatos(List<Object> datos) {
        for (Object obj : datos) {
            EjercicioFiscal ejercicioFiscal = (EjercicioFiscal) obj;
            super.getTb().addCell(ejercicioFiscal.getIdEjercicioFiscal().toString());
            super.getTb().addCell(ejercicioFiscal.getNombre());
            super.getTb().addCell(ejercicioFiscal.getDescripcion());

        }
    }
}
