package ro.barbos.gater.cutprocessor;

import ro.barbos.gater.cutprocessor.cutplan.CutPlanStackResult;
import ro.barbos.gater.cutprocessor.diagram.CutDiagram;
import ro.barbos.gater.dao.LumberStackDAO;
import ro.barbos.gater.data.LumberLogUtil;
import ro.barbos.gater.dto.ProductCutTargetDTO;
import ro.barbos.gater.model.LumberLog;
import ro.barbos.gater.model.LumberStack;

import java.util.*;

/**
 * Created by radu on 5/9/2016.
 */
public class StackCutPlanStatistics implements CutPlanStatistics {

    List<LumberStack> stacks = LumberStackDAO.getAllstack();
    Map<LumberStack, Map<String, Object>> statistics = new HashMap<>();


    public StackCutPlanStatistics() {
        for (LumberStack stack : stacks) {
            Map<String, Object> stats = new HashMap<String, Object>();
            stats.put("COUNT", 0);
            stats.put("AVG_EFFICENCY", 0D);
            stats.put("TOT_EFFICENCY", 0D);
            stats.put("DIAGRAMS", new ArrayList<CutDiagram>());
            statistics.put(stack, stats);
        }
    }

    @Override
    public void lumberLogSelected(LumberLog lumberLog, CutDiagram cutDiagram) {
        LumberStack stack = LumberLogUtil.findLumberStack(lumberLog, stacks);
        Map<String, Object> stats = statistics.get(stack);
        int count = (Integer) stats.get("COUNT");
        count++;
        double totEficency = (Double) stats.get("TOT_EFFICENCY");
        double volumeEfficency = cutDiagram.cutInfo.cutVolumeEfficency;
        totEficency += volumeEfficency;
        stats.put("COUNT", count);
        stats.put("TOT_EFFICENCY", totEficency);
        ((List)stats.get("DIAGRAMS")).add(cutDiagram);
    }

    @Override
    public void printStatistics() {
        for(Map.Entry<LumberStack, Map<String, Object>> entry: statistics.entrySet()) {
            LumberStack stack = entry.getKey();
            Map<String, Object> stats = statistics.get(stack);
            System.out.println("Stack: " + stack.getName());
            System.out.println("Count: " + stats.get("COUNT"));
            System.out.println("Average: " + stats.get("AVG_EFFICENCY"));
        }
    }

    @Override
    public List<CutPlanStackResult> getOptimalSelection(List<ProductCutTargetDTO> cutDataInfo) {
        List<CutPlanStackResult> optimalResult = new ArrayList<>();
        Map<String, CutPlanStackResult> mapData = new HashMap<>();
        Map.Entry<LumberStack, Map<String, Object>> entry = null;
        while((entry = getMostEfficentStack()) != null) {
            LumberStack stack = entry.getKey();
            Map<String, Object> stats = statistics.get(stack);
            String stackName = stack.getName();
            Integer stackCount = (Integer)stats.get("COUNT");
            Double totalAvg =  (Double)stats.get("TOT_EFFICENCY");
            if(!mapData.containsKey(stackName)) {
                CutPlanStackResult stackInfo = new CutPlanStackResult();
                stackInfo.setStack(stackName);
                mapData.put(stackName, stackInfo);
                optimalResult.add(stackInfo);
            }
            CutPlanStackResult stackInfo = mapData.get(stackName);
            List<CutDiagram> cutDiagrams = (List)entry.getValue().get("DIAGRAMS");
            double avg = getAvgAndRemoveDiagram(cutDiagrams);
            totalAvg -= avg;
            stats.put("COUNT", stackCount-1);
            stats.put("TOT_EFFICENCY", totalAvg);
            computeStats();
            stackInfo.setCount(stackInfo.getCount() + 1);
        }
        Collections.sort(optimalResult, new Comparator<CutPlanStackResult>() {
            @Override
            public int compare(CutPlanStackResult o1, CutPlanStackResult o2) {
                if(o2.getCount()>0) {
                    return 1;
                }
                return -1;
            }
        });
        return optimalResult;
    }

    @Override
    public void computeStats() {
        for(Map.Entry<LumberStack, Map<String, Object>> entry: statistics.entrySet()) {
            LumberStack stack = entry.getKey();
            Map<String, Object> stats = statistics.get(stack);
            Integer stackCount = (Integer)stats.get("COUNT");
            Double stackTot =  (Double)stats.get("TOT_EFFICENCY");
            if(stackCount > 0) {
                stats.put("AVG_EFFICENCY", stackTot/stackCount);
            } else {
                stats.put("AVG_EFFICENCY", 0D);
            }

        }
    }

    private Map.Entry<LumberStack, Map<String, Object>> getMostEfficentStack() {
        Map.Entry<LumberStack, Map<String, Object>> best = null;
        for(Map.Entry<LumberStack, Map<String, Object>> entry: statistics.entrySet()) {
            if(best == null) {
                best = entry;
            } else {
                List<CutDiagram> cutDiagrams = (List)entry.getValue().get("DIAGRAMS");
                if(cutDiagrams != null && !cutDiagrams.isEmpty()) {
                    LumberStack stack = entry.getKey();
                    Map<String, Object> stats = statistics.get(stack);
                    Double bestAgv = (Double)best.getValue().get("AVG_EFFICENCY");
                    Double currentAgv = (Double)entry.getValue().get("AVG_EFFICENCY");
                    if(currentAgv>bestAgv) {
                        best = entry;
                    }
                }
            }
        }
        if(best!=null) {
            List<CutDiagram> cutDiagrams = (List)best.getValue().get("DIAGRAMS");
            if(cutDiagrams.isEmpty()) {
                best = null;
            }
        }
        return best;
    }

    private double getAvgAndRemoveDiagram(List<CutDiagram> cutDiagrams) {
        int index = (int)(cutDiagrams.size() * (1-CutPlanStatistics.OPTIMAL_FACTOR));
        CutDiagram diagram = cutDiagrams.remove(index);
        return diagram.cutInfo.cutVolumeEfficency;
    }

    public Map<LumberStack, Map<String, Object>> getStatistics() {
        return statistics;
    }

    public void setStatistics(Map<LumberStack, Map<String, Object>> statistics) {
        this.statistics = statistics;
    }
}
