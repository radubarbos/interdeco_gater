package ro.barbos.gater.model;

import com.google.gson.*;
import ro.barbos.gater.data.MetricTools;

import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by radu on 8/24/2016.
 */
public class LumberLogTransportEntryCostMatrix {

    private Map<LumberLogTransportEntryCostMatrixKey, LumberLogTransportEntryCostMatrixValue> cellData = new HashMap<>();
    private int lumberLogsCount = 0;

    private Double marginVolume = 0D;
    private Long marginCost = 0L;

    //
    private transient Set<Integer> lengths = new TreeSet<>();
    private transient Set<Integer> qualityClasses = new TreeSet<>();
    private transient Set<Integer> types = new TreeSet<>();

    private transient Double totalCost = 0D;
    private transient Double mediumCost = 0D;


    public void init(LumberLogTransportEntry entry) {
        analizeLumber(entry.getLumberLogs());
        setPreviousConfig(entry);
    }

    private void setPreviousConfig(LumberLogTransportEntry entry) {
        mediumCost = entry.getCost();
        if (entry.getCostConfig() == null || entry.getCostConfig().length() == 0) {
            return;
        }
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LumberLogTransportEntryCostMatrixKey.class, new MatrixKeyDeserializer());
        LumberLogTransportEntryCostMatrix previousMatrix = builder.create().fromJson(entry.getCostConfig(), LumberLogTransportEntryCostMatrix.class);
        if (previousMatrix.getCellData().isEmpty()) {
            return;
        }
        for (Map.Entry<LumberLogTransportEntryCostMatrixKey, LumberLogTransportEntryCostMatrixValue> cell : previousMatrix.getCellData().entrySet()) {
            LumberLogTransportEntryCostMatrixKey key = cell.getKey();
            LumberLogTransportEntryCostMatrixValue currentCell = cellData.get(key);
            if (cell.getValue().getLumberLogsIds() == null || cell.getValue().getLumberLogsIds().isEmpty()) {
                cell.getValue().setLumberLogsIds(currentCell.getLumberLogsIds());
            }
            cellData.put(key, cell.getValue());
            lengths.add(key.getLength().intValue());
            qualityClasses.add(key.getQualityClass().intValue());
            types.add(key.getType().intValue());
        }
        if (lumberLogsCount < previousMatrix.getLumberLogsCount()) {
            lumberLogsCount = previousMatrix.getLumberLogsCount();
        }
        if (marginVolume < previousMatrix.getMarginVolume()) {
            marginVolume = previousMatrix.getMarginVolume();
        }
        if (marginCost < previousMatrix.getMarginCost()) {
            marginCost = previousMatrix.getMarginCost();
        }
    }

    private void analizeLumber(List<LumberLog> lumberLogs) {
        for (LumberLog lumberLog : lumberLogs) {
            LumberLogTransportEntryCostMatrixKey key = createKey(lumberLog);
            LumberLogTransportEntryCostMatrixValue value = cellData.get(key);
            if (value == null) {
                value = new LumberLogTransportEntryCostMatrixValue(0, 0);
                cellData.put(key, value);
            }
            value.addVolume(MetricTools.toMeterCubs(lumberLog.getVolume()));
            value.addLumberId(lumberLog.getId());
            marginVolume += MetricTools.toMeterCubs(lumberLog.getMarginVolume());
            lengths.add(lumberLog.getLength().intValue());
            qualityClasses.add(lumberLog.getLumberClass().intValue());
            types.add(lumberLog.getLumberType().intValue());

        }
        lumberLogsCount = lumberLogs.size();
    }

    private LumberLogTransportEntryCostMatrixKey createKey(LumberLog lumberLog) {
        LumberLogTransportEntryCostMatrixKey key = new LumberLogTransportEntryCostMatrixKey(lumberLog.getLength().longValue(), lumberLog.getLumberClass().intValue(),
                lumberLog.getLumberType().intValue());
        return key;
    }

    public Set<Integer> getLengths() {
        return lengths;
    }

    public Set<Integer> getQualityClasses() {
        return qualityClasses;
    }

    public Set<Integer> getTypes() {
        return types;
    }

    public Map<LumberLogTransportEntryCostMatrixKey, LumberLogTransportEntryCostMatrixValue> getCellData() {
        return cellData;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public double calculateTotalCost() {
        totalCost = 0D;
        mediumCost = 0D;
        double totalVolume = 0;
        for (Map.Entry<LumberLogTransportEntryCostMatrixKey, LumberLogTransportEntryCostMatrixValue> cell : cellData.entrySet()) {
            totalCost += cell.getValue().getVolumeCost();
            totalVolume += cell.getValue().getVolume();
        }
        //margin
        totalCost += (marginVolume * marginCost);
        totalVolume += marginVolume;

        totalCost /= 100;
        mediumCost = (totalCost / totalVolume);// * totalVolume;
        return totalCost;
    }

    public Double getMediumCost() {
        return mediumCost;
    }

    public void setMediumCost(Double mediumCost) {
        this.mediumCost = mediumCost;
    }

    public int getLumberLogsCount() {
        return lumberLogsCount;
    }

    public void setLumberLogsCount(int lumberLogsCount) {
        this.lumberLogsCount = lumberLogsCount;
    }

    public Long getMarginCost() {
        return marginCost;
    }

    public void setMarginCost(Long marginCost) {
        this.marginCost = marginCost;
    }

    public Double getMarginVolume() {
        return marginVolume;
    }

    public void setMarginVolume(Double marginVolume) {
        this.marginVolume = marginVolume;
    }
}

class MatrixKeyDeserializer implements JsonDeserializer<LumberLogTransportEntryCostMatrixKey> {
    public LumberLogTransportEntryCostMatrixKey deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        String txt = json.getAsJsonPrimitive().getAsString();
        StringTokenizer tokenier = new StringTokenizer(txt, ",");
        Long length = Long.valueOf(tokenier.nextToken());
        Integer type = Integer.valueOf(tokenier.nextToken());
        Integer qclass = Integer.valueOf(tokenier.nextToken());
        return new LumberLogTransportEntryCostMatrixKey(length, qclass, type);
    }
}
