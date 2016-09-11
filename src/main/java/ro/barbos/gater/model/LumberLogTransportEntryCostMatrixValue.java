package ro.barbos.gater.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by radu on 8/24/2016.
 */
public class LumberLogTransportEntryCostMatrixValue implements Serializable {

    private long cost;
    private double volume;
    private List<Long> lumberLogsIds = new ArrayList();

    public LumberLogTransportEntryCostMatrixValue(long cost, double volume) {
        this.cost = cost;
        this.volume = volume;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public void addVolume(double volume) {
        this.volume += volume;
    }


    public long getCost() {
        return cost;
    }

    public double getRealCost() {
        return cost / 100D;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public double getVolumeCost() {
        return volume * cost;
    }

    public List<Long> getLumberLogsIds() {
        return lumberLogsIds;
    }

    public void setLumberLogsIds(List<Long> lumberLogsIds) {
        this.lumberLogsIds = lumberLogsIds;
    }

    public void addLumberId(Long id) {
        lumberLogsIds.add(id);
    }

}
