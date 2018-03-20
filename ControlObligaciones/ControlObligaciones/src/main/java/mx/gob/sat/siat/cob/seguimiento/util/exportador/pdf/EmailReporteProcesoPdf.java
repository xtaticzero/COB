package mx.gob.sat.siat.cob.seguimiento.util.exportador.pdf;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.EmailReporteProceso;

public class EmailReporteProcesoPdf extends GeneraPdf{
    
    /**
     *
     * @param datos
     */
    @Override
    public void colocarDatos(List<Object> datos) {
        for(Object obj : datos){
            EmailReporteProceso emailReporteProceso = (EmailReporteProceso)obj;
            super.getTb().addCell(emailReporteProceso.getNombreCompleto());
            super.getTb().addCell(emailReporteProceso.getCorreoElectronico());
            super.getTb().addCell(emailReporteProceso.getCorreoElectronicoAlterno());
        }
    }
    
}
