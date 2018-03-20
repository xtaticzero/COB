package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.management;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import mx.gob.sat.siat.cob.seguimiento.dto.management.ProcessInfo;
import mx.gob.sat.siat.cob.seguimiento.dto.management.ServerInfo;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.management.ManagementMonitorService;
import mx.gob.sat.siat.cob.seguimiento.utils.AbstractBaseMB;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Marco Murakami
 */
@Controller("monitor")
@Scope(value = "view")
public class ManagementMonitorManagedBean extends AbstractBaseMB{

    @Autowired
    private ManagementMonitorService managementService;
    
    private static final int POINTS = 100;
    private int count;
    
    private CartesianChartModel memoryModel;
    private List<ProcessCartesianLineModel> processModelList;
    
    private LineChartSeries physicalMemSeries;
    private LineChartSeries swapMemSeries;
    private List<LineChartSeries> processSeriesList;
    
    private Map physicalMemData;
    private Map swapData;
    private List<Map> processDataList;


    /**
     * Metodo para iniciar los objetos para la graficacion
     */
    @PostConstruct
    public void init() {
        this.initMemoryModel();
        this.initProcessModel();
        this.count = 0;
    }

    private void initMemoryModel() {
        memoryModel = new CartesianChartModel();
        physicalMemData = new TreeMap();
        physicalMemSeries = new LineChartSeries();
        physicalMemSeries.setLabel("Physical Memory");
        physicalMemSeries.set(0, 0);
        memoryModel.addSeries(physicalMemSeries);

        
        swapData = new TreeMap();
        swapMemSeries = new LineChartSeries();
        swapMemSeries.setLabel("Swap Space");
        swapMemSeries.set(0, 0);
        
        memoryModel.addSeries(swapMemSeries);
    }

    private void initProcessModel() {
        try {
            processModelList = new ArrayList<ProcessCartesianLineModel>();
            processSeriesList = new ArrayList<LineChartSeries>();
            processDataList = new ArrayList<Map>();

            List<ProcessInfo> piList = managementService.getProcessInfoData();
            for (int i = 0; i < piList.size(); i++) {
                ProcessCartesianLineModel processModel = new ProcessCartesianLineModel("Proceso - " + (i + 1));
                processDataList.add(processModel.getProcessData());
                processSeriesList.add(processModel.getProcessChartSeries());
                processModelList.add(processModel);
            }
        } catch (SGTServiceException ex) {
            getLogger().error(ex.getMessage());
        }
    }

    public void memoryUsage() {
        try {
            ServerInfo localSI = managementService.getServerInfoData();
            if (localSI != null) {
                
                long totalPhysicalMem = localSI.getTotalPhysicalMemorySize(); 
                long freePhyMem = localSI.getFreePhysicalMemorySize();
                long usedPhyMem = totalPhysicalMem - freePhyMem;
                long usedPhyMemP = usedPhyMem * 100 / totalPhysicalMem;
                
                long totalSwap = localSI.getTotalSwapSpaceSize();
                long freeSwap = localSI.getFreeSwapSpaceSize();
                long usedSwap = totalSwap - freeSwap;
                long usedSwapP = usedSwap * 100 / totalSwap;
                SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
                String time = formatter.format(localSI.getTimestamp());
                physicalMemData.put(time, usedPhyMemP);
                physicalMemSeries.setData(physicalMemData);
                memoryModel.clear();
                swapData.put(time, usedSwapP);
                swapMemSeries.setData(swapData);
                memoryModel.clear();
                memoryModel.addSeries(swapMemSeries);
                memoryModel.addSeries(physicalMemSeries);
                if (this.count >= POINTS) {
                    TreeMap auxSwapMap = (TreeMap) swapData;
                    auxSwapMap.pollFirstEntry();
                    TreeMap auxPhysMap = (TreeMap) physicalMemData;
                    auxPhysMap.pollFirstEntry();
                }
            }
        } catch (SGTServiceException ex) {
            getLogger().error(ex.getMessage());
        }
    }

    /**
     * Metodo para la consulta de valores del proceso
     */
    public void processUsage() {
        try {
            List<ProcessInfo> processInfoList = managementService.getProcessInfoData();

            for (int i = 0; i < processInfoList.size(); i++) {
                ProcessInfo localProcessInfo = processInfoList.get(i);
                SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
                String time = formatter.format(localProcessInfo.getTimestamp());
                CartesianChartModel cartesianChartModel = processModelList.get(i).getProcessModel();
                LineChartSeries processSeries = processSeriesList.get(i);
                Map processData = processDataList.get(i);
                processData.put(time, localProcessInfo.getMean());
                processSeries.setData(processData);
                cartesianChartModel.clear();
                cartesianChartModel.addSeries(processSeries);

                if (this.count >= POINTS) {
                    TreeMap auxProcessMap = (TreeMap) processData;
                    auxProcessMap.pollFirstEntry();
                }

            }
        } catch (SGTServiceException ex) {
                  getLogger().error(ex.getMessage());
        }

    }

    /**
     * Metodo que invoca el almacenamiento de las distintas series y que es llamado cada 5 segundos
     */
    public void populateData() {
        this.memoryUsage();
        this.processUsage();
        this.count++;
    }

    public CartesianChartModel getMemoryModel() {
        return memoryModel;
    }

    public void setMemoryModel(CartesianChartModel memoryModel) {
        this.memoryModel = memoryModel;
    }

    public List<ProcessCartesianLineModel> getProcessModelList() {
        return processModelList;
    }

    public void setProcessModelList(List<ProcessCartesianLineModel> processModelList) {
        this.processModelList = processModelList;
    }
}
