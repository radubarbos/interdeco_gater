package ro.barbos.gater.cutprocessor.cutplan;

import java.io.Serializable;

/**
 * Created by radu on 5/16/2016.
 */
public class CutPlanStackResult implements Serializable {

    private String stack;
    private int count;
    private double avgEfficency;

    public String getStack() {
        return stack;
    }

    public void setStack(String stack) {
        this.stack = stack;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getAvgEfficency() {
        return avgEfficency;
    }

    public void setAvgEfficency(double avgEfficency) {
        this.avgEfficency = avgEfficency;
    }
}
