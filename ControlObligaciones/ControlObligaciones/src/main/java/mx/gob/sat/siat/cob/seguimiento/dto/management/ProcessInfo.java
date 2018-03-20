package mx.gob.sat.siat.cob.seguimiento.dto.management;

import java.util.Date;

/**
 *
 * @author Marco Murakami
 */
public class ProcessInfo {

    private int processId;
    private short active;
    private long counter;
    private long max;
    private long maxActive;
    private long maxActiveTimestamp;
    private double mean;
    private long min;
    private long minTimestamp;
    private double standardDeviation;
    private long total;
    private double variance;
    private double varianceN;
    private Date timestamp;

    /**
     *
     * @return
     */
    public int getProcessId() {
        return processId;
    }

    /**
     *
     * @param processId
     */
    public void setProcessId(int processId) {
        this.processId = processId;
    }

    /**
     *
     * @return
     */
    public short getActive() {
        return active;
    }

    /**
     *
     * @param active
     */
    public void setActive(short active) {
        this.active = active;
    }

    /**
     *
     * @return
     */
    public long getCounter() {
        return counter;
    }

    /**
     *
     * @param counter
     */
    public void setCounter(long counter) {
        this.counter = counter;
    }

    /**
     *
     * @return
     */
    public long getMax() {
        return max;
    }

    /**
     *
     * @param max
     */
    public void setMax(long max) {
        this.max = max;
    }

    /**
     *
     * @return
     */
    public long getMaxActive() {
        return maxActive;
    }

    /**
     *
     * @param maxActive
     */
    public void setMaxActive(long maxActive) {
        this.maxActive = maxActive;
    }

    /**
     *
     * @return
     */
    public long getMaxActiveTimestamp() {
        return maxActiveTimestamp;
    }

    /**
     *
     * @param maxActiveTimestamp
     */
    public void setMaxActiveTimestamp(long maxActiveTimestamp) {
        this.maxActiveTimestamp = maxActiveTimestamp;
    }

    /**
     *
     * @return
     */
    public double getMean() {
        return mean;
    }

    /**
     *
     * @param mean
     */
    public void setMean(double mean) {
        this.mean = mean;
    }

    /**
     *
     * @return
     */
    public long getMin() {
        return min;
    }

    /**
     *
     * @param min
     */
    public void setMin(long min) {
        this.min = min;
    }

    /**
     *
     * @return
     */
    public long getMinTimestamp() {
        return minTimestamp;
    }

    /**
     *
     * @param minTimestamp
     */
    public void setMinTimestamp(long minTimestamp) {
        this.minTimestamp = minTimestamp;
    }

    /**
     *
     * @return
     */
    public double getStandardDeviation() {
        return standardDeviation;
    }

    /**
     *
     * @param standardDeviation
     */
    public void setStandardDeviation(double standardDeviation) {
        this.standardDeviation = standardDeviation;
    }

    /**
     *
     * @return
     */
    public long getTotal() {
        return total;
    }

    /**
     *
     * @param total
     */
    public void setTotal(long total) {
        this.total = total;
    }

    /**
     *
     * @return
     */
    public double getVariance() {
        return variance;
    }

    /**
     *
     * @param variance
     */
    public void setVariance(double variance) {
        this.variance = variance;
    }

    /**
     *
     * @return
     */
    public double getVarianceN() {
        return varianceN;
    }

    /**
     *
     * @param varianceN
     */
    public void setVarianceN(double varianceN) {
        this.varianceN = varianceN;
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
