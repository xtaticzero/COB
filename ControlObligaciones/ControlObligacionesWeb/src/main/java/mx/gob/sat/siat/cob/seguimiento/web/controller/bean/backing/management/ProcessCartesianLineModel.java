package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.management;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.LineChartSeries;

/**
 *
 * @author Marco Murakami
 */
public class ProcessCartesianLineModel implements Serializable {
    
    private CartesianChartModel processModel;
    private LineChartSeries processChartSeries;
    private Map processData;
    
    public ProcessCartesianLineModel(){
        processModel = new CartesianChartModel();
        processChartSeries = new LineChartSeries();
        processData = new TreeMap();
        processChartSeries.set(0, 0);
        processModel.addSeries(processChartSeries);
    }
    
    public ProcessCartesianLineModel(String label){
        processModel = new CartesianChartModel();
        processChartSeries = new LineChartSeries(label);
        processData = new TreeMap();
        processChartSeries.set(0, 0);
        processModel.addSeries(processChartSeries);
    }

    public CartesianChartModel getProcessModel() {
        return processModel;
    }

    public void setProcessModel(CartesianChartModel processModel) {
        this.processModel = processModel;
    }

    public LineChartSeries getProcessChartSeries() {
        return processChartSeries;
    }

    public void setProcessChartSeries(LineChartSeries processChartSeries) {
        this.processChartSeries = processChartSeries;
    }

    public Map getProcessData() {
        return processData;
    }

    public void setProcessData(Map processData) {
        this.processData = processData;
    }    
}
