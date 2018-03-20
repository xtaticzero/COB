/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.report;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AlscDTO;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class BeanReportVigilancias {
    private List<AlscDTO> lstALSC;
    private List<AlscDTO> lstEF;
    
    public BeanReportVigilancias(List<AlscDTO> lstALSC,List<AlscDTO> lstEF) {
        this.lstALSC = lstALSC;
        this.lstEF = lstEF;
    }

    public BeanReportVigilancias(List<AlscDTO> lstALSC) {
        this.lstALSC = lstALSC;
    }

    /**
     * Get the value of lstEF
     *
     * @return the value of lstEF
     */
    public List<AlscDTO> getLstEF() {
        return lstEF;
    }

    /**
     * Set the value of lstEF
     *
     * @param lstEF new value of lstEF
     */
    public void setLstEF(List<AlscDTO> lstEF) {
        this.lstEF = lstEF;
    }

    /**
     * Get the value of lstALSC
     *
     * @return the value of lstALSC
     */
    public List<AlscDTO> getLstALSC() {
        return lstALSC;
    }

    /**
     * Set the value of lstALSC
     *
     * @param lstALSC new value of lstALSC
     */
    public void setLstALSC(List<AlscDTO> lstALSC) {
        this.lstALSC = lstALSC;
    }

    /**
     * Get the value of dsALSC
     *
     * @return the value of dsALSC
     */
    public JRDataSource getDsALSC() {
        return new JRBeanCollectionDataSource(lstALSC);
    }
    
    public JRDataSource getDsEF() {
        return new JRBeanCollectionDataSource(lstEF);
    }
}
