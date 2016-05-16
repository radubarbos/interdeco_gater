package ro.barbos.gater.cutprocessor;

import ro.barbos.gater.dto.ProductCutTargetDTO;

import java.util.List;

/**
 * Created by radu on 11/26/2015.
 */
public interface CutPlanCalculatorListener {

    public void showPlan(List<CutPlanSenquence> plan, List<ProductCutTargetDTO> cutDataInfo);

    public void showStatistics(CutPlanStatistics statistics);
}
