package ro.barbos.gater.cutprocessor;

import ro.barbos.gater.cutprocessor.cutplan.CutPlanStackResult;
import ro.barbos.gater.cutprocessor.diagram.CutDiagram;
import ro.barbos.gater.dto.ProductCutTargetDTO;
import ro.barbos.gater.model.LumberLog;

import java.util.List;

/**
 * Created by radu on 5/9/2016.
 */
public interface CutPlanStatistics {

    public static final double OPTIMAL_FACTOR = 0.75;

    public abstract void lumberLogSelected(LumberLog log, CutDiagram cutDiagram);
    public abstract void printStatistics();
    public abstract void computeStats();
    public abstract List<CutPlanStackResult> getOptimalSelection(List<ProductCutTargetDTO> cutDataInfo);
}
