package ro.barbos.gater.model;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.GsonBuilder;
import ro.barbos.gater.data.MetricTools;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by radu on 8/22/2016.
 */
public class LumberLogTransportEntry {
    private Long id;
    private Date entryDate;
    private int lumberLogCount = 0;
    private Long userId;
    private Boolean finished;
    private Double volume = 0D;
    private Double marginVolume = 0D;
    private Long supplierId;
    private Long certificateId;
    private List<LumberLog> lumberLogs = new ArrayList<>();
    private Double cost = 0D;
    private String costConfig;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Integer getLumberLogCount() {
        return lumberLogCount;
    }

    public void setLumberLogCount(Integer lumberLogCount) {
        this.lumberLogCount = lumberLogCount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Double getMarginVolume() {
        return marginVolume;
    }

    public void setMarginVolume(Double marginVolume) {
        this.marginVolume = marginVolume;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(Long certificateId) {
        this.certificateId = certificateId;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public List<Long> getLumberLogIds() {
        List<Long> result = new ArrayList<>();
        for (LumberLog lumberLog : lumberLogs) {
            result.add(lumberLog.getId());
        }
        return result;
    }

    public void setLumberLogs(List<LumberLog> lumberLogs) {
        this.lumberLogs = lumberLogs;
    }

    public List<LumberLog> getLumberLogs() {
        return lumberLogs;
    }

    public String getCostConfig() {
        return costConfig;
    }

    public void setCostConfig(String costConfig) {
        this.costConfig = costConfig;
    }

    public void addLumberLogs(List<LumberLog> lumberLogs) {
        for (int i = 0; i < lumberLogs.size(); i++) {
            addLumberLog(lumberLogs.get(i));
        }
    }

    public void addLumberLog(LumberLog lumberLog) {
        volume += MetricTools.toMeterCubs(lumberLog.getVolume());
        marginVolume += MetricTools.toMeterCubs(lumberLog.getMarginVolume());
        lumberLogCount++;
        lumberLogs.add(lumberLog);
    }

    public void removeLumberLog(LumberLog lumberLog) {
        volume -= MetricTools.toMeterCubs(lumberLog.getVolume());
        marginVolume -= MetricTools.toMeterCubs(lumberLog.getMarginVolume());
        lumberLogCount--;
        lumberLogs.remove(lumberLog);
    }

    public boolean isStartedForProduction() {
        LumberLogTransportEntryCostMatrix matrix = new GsonBuilder().setExclusionStrategies(new CostExclStrat()).create().fromJson(getCostConfig(), LumberLogTransportEntryCostMatrix.class);
        if (matrix.getLumberLogsCount() > lumberLogCount) {
            return true;
        }
        return false;
    }

}

class CostExclStrat implements ExclusionStrategy {

    public boolean shouldSkipClass(Class<?> arg0) {
        return false;
    }

    public boolean shouldSkipField(FieldAttributes f) {

        return (f.getName().equals("cellData"));
    }

}
