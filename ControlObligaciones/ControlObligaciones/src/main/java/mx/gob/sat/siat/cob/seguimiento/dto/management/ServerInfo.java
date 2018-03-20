package mx.gob.sat.siat.cob.seguimiento.dto.management;

import java.util.Date;

/**
 *
 * @author Marco Murakami
 */
public class ServerInfo {
    
    private int serverId;
    private long commitedVirtualMemorySize;
    private long freePhysicalMemorySize;
    private long freeSwapSpaceSize;
    private double processCPULoad;
    private long processCPUTime;
    private double systemCPULoad;
    private long totalPhysicalMemorySize;
    private long totalSwapSpaceSize;
    private Date timestamp;

    /**
     *
     * @return
     */
    public int getServerId() {
        return serverId;
    }

    /**
     *
     * @param serverId
     */
    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    /**
     *
     * @return
     */
    public long getCommitedVirtualMemorySize() {
        return commitedVirtualMemorySize;
    }

    /**
     *
     * @param commitedVirtualMemorySize
     */
    public void setCommitedVirtualMemorySize(long commitedVirtualMemorySize) {
        this.commitedVirtualMemorySize = commitedVirtualMemorySize;
    }

    /**
     *
     * @return
     */
    public long getFreePhysicalMemorySize() {
        return freePhysicalMemorySize;
    }

    /**
     *
     * @param freePhysicalMemorySize
     */
    public void setFreePhysicalMemorySize(long freePhysicalMemorySize) {
        this.freePhysicalMemorySize = freePhysicalMemorySize;
    }

    /**
     *
     * @return
     */
    public long getFreeSwapSpaceSize() {
        return freeSwapSpaceSize;
    }

    /**
     *
     * @param freeSwapSpaceSize
     */
    public void setFreeSwapSpaceSize(long freeSwapSpaceSize) {
        this.freeSwapSpaceSize = freeSwapSpaceSize;
    }

    /**
     *
     * @return
     */
    public double getProcessCPULoad() {
        return processCPULoad;
    }

    /**
     *
     * @param processCPULoad
     */
    public void setProcessCPULoad(double processCPULoad) {
        this.processCPULoad = processCPULoad;
    }

    /**
     *
     * @return
     */
    public long getProcessCPUTime() {
        return processCPUTime;
    }

    /**
     *
     * @param processCPUTime
     */
    public void setProcessCPUTime(long processCPUTime) {
        this.processCPUTime = processCPUTime;
    }

    /**
     *
     * @return
     */
    public double getSystemCPULoad() {
        return systemCPULoad;
    }

    /**
     *
     * @param systemCPULoad
     */
    public void setSystemCPULoad(double systemCPULoad) {
        this.systemCPULoad = systemCPULoad;
    }

    /**
     *
     * @return
     */
    public long getTotalPhysicalMemorySize() {
        return totalPhysicalMemorySize;
    }

    /**
     *
     * @param totalPhysicalMemorySize
     */
    public void setTotalPhysicalMemorySize(long totalPhysicalMemorySize) {
        this.totalPhysicalMemorySize = totalPhysicalMemorySize;
    }

    /**
     *
     * @return
     */
    public long getTotalSwapSpaceSize() {
        return totalSwapSpaceSize;
    }

    /**
     *
     * @param totalSwapSpaceSize
     */
    public void setTotalSwapSpaceSize(long totalSwapSpaceSize) {
        this.totalSwapSpaceSize = totalSwapSpaceSize;
    }

    /**
     *
     * @return
     */
    public Date getTimestamp() {
        if(timestamp!=null){
            return (Date) timestamp.clone();
        }else{
            return null;
        }
    }

    /**
     *
     * @param timestamp
     */
    public void setTimestamp(Date timestamp) {
        if(timestamp!=null){
            this.timestamp = (Date)timestamp.clone();
        }
    }
}
