package ro.barbos.gater.cutprocessor;

import ro.barbos.gater.cutprocessor.cutplan.CutPlanStackResult;
import ro.barbos.gater.cutprocessor.diagram.CutDiagram;
import ro.barbos.gater.dto.ProductCutTargetDTO;
import ro.barbos.gater.model.LumberLog;

import java.util.List;

/**
 * Created by radu on 5/9/2016.
 */
public class DefaultCutPlanStatistics implements CutPlanStatistics {

    @Override
    public void lumberLogSelected(LumberLog log, CutDiagram cutDiagram) {

    }

    @Override
    public void printStatistics() {

    }

    @Override
    public void computeStats() {

    }

    @Override
    public List<CutPlanStackResult> getOptimalSelection(List<ProductCutTargetDTO> cutDataInfo) {
        return null;
    }
}
