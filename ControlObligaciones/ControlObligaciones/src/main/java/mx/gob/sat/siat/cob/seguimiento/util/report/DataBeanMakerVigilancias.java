package mx.gob.sat.siat.cob.seguimiento.util.report;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mx.gob.sat.siat.cob.seguimiento.dto.report.ReporteVigilancias;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AlscDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ReporteVigilancia;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoMedioEnvioEnum;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class DataBeanMakerVigilancias {

    private static final String PATTERN_NUMER_FORMAT = "###,###";

    /**
     *
     */
    public DataBeanMakerVigilancias() {
        super();
    }

    /**
     *
     * @return @throws FileNotFoundException
     */
    public InputStream getRepotStream() throws FileNotFoundException {
        return this.getClass().getClassLoader().getResourceAsStream((ReporteVigilancias.MASTER_REPORT.getPathReport()));
    }

    /**
     *
     * @param reporteVigilancia
     * @return
     */
    public Map getParameters(ReporteVigilancia reporteVigilancia) {
        Map parameters = new HashMap();

        parameters.put("idVigilancia", reporteVigilancia.getIdVigilancia());
        parameters.put("tipoDoc", reporteVigilancia.getTipoDocumento());
        parameters.put("tipoVigilancia", reporteVigilancia.getTipoVigilancia());
        parameters.put("periodo", reporteVigilancia.getPeriodoRequerido());
        parameters.put("ejercicio", reporteVigilancia.getEjercicioRequerido());
        parameters.put("fechaLiberacion", reporteVigilancia.getFechaLiberacion());
        parameters.put("tipoEnvio", reporteVigilancia.getTipoMedio());
        if (reporteVigilancia.getIdTipoMedio() == TipoMedioEnvioEnum.ENTIDAD_FEDERATIVA.getValor()) {
            parameters.put("pTituloReporte2", "Relacion de documentos por entidad federativa:");
        } else {
            parameters.put("pTituloReporte2", "");
        }
        parameters.put("totalNumDoc", getTotalDoc(reporteVigilancia.getLstALSC()));
        parameters.put("totalNumImp", getTotalImp(reporteVigilancia.getLstALSC()));

        if (!reporteVigilancia.getLstEF().isEmpty()) {
            parameters.put("totalNumDocEF", getTotalDoc(reporteVigilancia.getLstEF()));
        }
        return parameters;
    }

    /**
     *
     * @param lstALSC
     * @return
     */
    public JRBeanCollectionDataSource getDsALSC(List<AlscDTO> lstALSC) {
        return new JRBeanCollectionDataSource(getLstAlscFormat(lstALSC));
    }

    /**
     *
     * @param lstALSC
     * @return
     */
    public List<AlscDTO> getLstAlscFormat(List<AlscDTO> lstALSC) {
        List<AlscDTO> lstALSCformat;
        lstALSCformat = null;

        if (lstALSC != null) {
            lstALSCformat = new ArrayList<AlscDTO>();
            for (AlscDTO item : lstALSC) {
                item.setCantidadDocumentosStr(getNumberFormat(item.getCantidadDocumentos()));
                item.setCantidadImpresosStr(getNumberFormat(item.getCantidadImpresos()));
                lstALSCformat.add(item);
            }
        }

        return lstALSCformat;
    }

    /**
     *
     * @param lstALSC
     * @return
     */
    public List<AlscDTO> getLstEfFormat(List<AlscDTO> lstALSC) {
        List<AlscDTO> lstALSCformat;
        lstALSCformat = null;
        if (lstALSC != null && !lstALSC.isEmpty()) {
            lstALSCformat = new ArrayList<AlscDTO>();
            for (AlscDTO item : lstALSC) {
                item.setCantidadDocumentosStr(getNumberFormat(item.getCantidadDocumentos()));
                lstALSCformat.add(item);
            }
        }
        return lstALSCformat;
    }

    /**
     *
     * @param number
     * @return
     */
    public String getNumberFormat(Integer number) {
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator('.');
        simbolos.setGroupingSeparator(',');
        DecimalFormat formateadorNumeric = new DecimalFormat(PATTERN_NUMER_FORMAT, simbolos);
        return formateadorNumeric.format(number);
    }

    /**
     *
     * @param lstAlsc
     * @return
     */
    public String getTotalDoc(List<AlscDTO> lstAlsc) {
        Integer sumaDoc = 0;

        for (AlscDTO dto : lstAlsc) {
            sumaDoc = sumaDoc + dto.getCantidadDocumentos();
        }
        return getNumberFormat(sumaDoc);
    }

    /**
     *
     * @param lstAlsc
     * @return
     */
    public String getTotalImp(List<AlscDTO> lstAlsc) {
        Integer sumaImp = 0;

        for (AlscDTO dto : lstAlsc) {
            sumaImp = sumaImp + dto.getCantidadImpresos();
        }
        return getNumberFormat(sumaImp);
    }
}
