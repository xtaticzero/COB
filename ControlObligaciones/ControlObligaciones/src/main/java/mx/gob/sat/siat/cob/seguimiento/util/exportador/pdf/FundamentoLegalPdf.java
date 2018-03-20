package mx.gob.sat.siat.cob.seguimiento.util.exportador.pdf;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.FundamentoLegal;


public class FundamentoLegalPdf extends GeneraPdf {
    /**
     *
     */
    public FundamentoLegalPdf() {
        super();
    }

    /**
     *
     * @param datos
     */
    @Override
    public void colocarDatos(List<Object> datos) {
        for (Object obj : datos) {
            FundamentoLegal fundamentoLegal = (FundamentoLegal) obj;
            super.getTb().addCell(fundamentoLegal.getDescripcionLarga());
            super.getTb().addCell(fundamentoLegal.getIdRegimenDescr());
            super.getTb().addCell(fundamentoLegal.getIdEjercicioFiscalDescr());
            super.getTb().addCell(fundamentoLegal.getIdPeriodoDescr());
            super.getTb().addCell(fundamentoLegal.getFechaVencimientoStr()==null?"":fundamentoLegal.getFechaVencimientoStr().toString());
            super.getTb().addCell(fundamentoLegal.getFundamentoLegal());
        }
    }
}
