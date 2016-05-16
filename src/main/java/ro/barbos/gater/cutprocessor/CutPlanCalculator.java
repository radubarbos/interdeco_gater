package ro.barbos.gater.cutprocessor;

import ro.barbos.gater.cutprocessor.diagram.CutDiagram;
import ro.barbos.gater.dao.StockDAO;
import ro.barbos.gater.dto.LumberLogFilterDTO;
import ro.barbos.gater.dto.ProductCutTargetDTO;
import ro.barbos.gater.model.LumberLog;
import ro.barbos.gater.model.Product;
import ro.barbos.gui.tablemodel.CutPlanTargetRecord;

import javax.swing.*;
import java.util.*;
import java.util.logging.Logger;

public class CutPlanCalculator extends SwingWorker {
	
	Logger logger = Logger.getLogger("cutprocessor");
	
	private List<CutPlanTargetRecord> records;
	private List<ProductCutTargetDTO> cutDataInfo;
	private Map<String, Boolean> cutStrategies;
	Map<Long, Boolean> processedLumberLogs = new HashMap<>();
	Map<String, Integer> productIndex = new LinkedHashMap<>();
	Map<Integer, Product> productMap = new HashMap<>();
	
	List<CutPlanSenquence> planSteps = new ArrayList<>();
	
	private CutPlanCalculatorListener frame;
	
	private long shortestProductLength = 1111111110;

    private CutPlanStatistics statistics = new DefaultCutPlanStatistics();
	
	public CutPlanCalculator(List<CutPlanTargetRecord> records, CutPlanCalculatorListener frame, Map<String, Boolean> cutStrategies) {
		this.records = records;
		this.cutStrategies = cutStrategies;
		cutDataInfo = new ArrayList<>(records.size() * 2);
		for(CutPlanTargetRecord record: records) {
			ProductCutTargetDTO targetDTO = new ProductCutTargetDTO();
			Product prod = record.getProduct();
			targetDTO.setProduct(prod.getName());
			targetDTO.setTargetPieces(record.getPieces());
			targetDTO.setCutPieces(0L);
			targetDTO.setTargetVolume(record.getTargetMCub().doubleValue());
			targetDTO.setProductVolume(prod.getLength() * prod.getWidth() * prod.getThick().doubleValue());
			cutDataInfo.add(targetDTO);
			if(record.getProduct().getLength() < shortestProductLength) {
				shortestProductLength = record.getProduct().getLength();
			}
		}
		this.frame = frame;
	}

	/* (non-Javadoc)
	 * @see javax.swing.SwingWorker#doInBackground()
	 */
	@Override
	protected Object doInBackground() throws Exception {
		logger.info("Starting calculating cut plan");
		LumberLogFilterDTO filter = new LumberLogFilterDTO();
		filter.setMinLength(shortestProductLength);
		filter.setAvailable(false);
		List<LumberLog> lumberLogs = StockDAO.getJustCurrentLumbersLogs(filter);
		logger.info("Cut plan processor: Number of lumber logs = " + lumberLogs.size());
		CuterProcessor processor = new CuterProcessor();
		for(int index =0; index < records.size(); index++) {
			productIndex.put(records.get(index).getProduct().getName(), index);
			productMap.put(index, records.get(index).getProduct());
		}
		
		boolean finish = false;
		boolean allLumbersProcessed = false;
        List[] firstTargetCutInfo = null;
		while(!finish && !allLumbersProcessed) {
			if(processedLumberLogs.size() == lumberLogs.size()) {
				allLumbersProcessed = true;
				break;
			}
			double bestEfficency = 0;
			double smallestVolumeLost = 1000000000;
			LumberLog bestLumberLog = null;
			CutDiagram bestDiagram = null;
			List[] targetCutInfo = getCurrentCutTarget();
            if(firstTargetCutInfo == null) {
                firstTargetCutInfo = targetCutInfo;
            }
			
			for(LumberLog lumberLog: lumberLogs) {
				if(!processedLumberLogs.containsKey(lumberLog.getId())) {
				 CutDiagram diagram =  processor.getBestCut(lumberLog, targetCutInfo[0], targetCutInfo[1], cutStrategies);	
				 if(CutterSettings.DO_LENGTH_OPTIMIZATION) {
					 if(lumberLog.getVolume() - diagram.cutInfo.cutVolume < smallestVolumeLost) {
						 smallestVolumeLost = lumberLog.getVolume() - diagram.cutInfo.cutVolume;
						 bestLumberLog = lumberLog;
						 bestDiagram = diagram;
					 } 
				 }
				 else if(diagram.cutInfo.cutLayoutEfficency > bestEfficency) {
					 bestEfficency = diagram.cutInfo.cutLayoutEfficency;
					 bestLumberLog = lumberLog;
					 bestDiagram = diagram;
				 }
				}
			}
			if(bestDiagram == null || bestDiagram.cutInfo.cutVolume == 0) {
				finish = true;
			}
			else {
				CutPlanSenquence senquence = new CutPlanSenquence();
				senquence.setCutDiagram(bestDiagram);
				senquence.setLumberLog(bestLumberLog);//System.out.println(bestLumberLog.getPlate().getLabel() + "" + bestDiagram.getClass().getName() + " " + (bestDiagram.cutInfo.cutVolume/1000D) + " " + (smallestVolumeLost/1000D));
				planSteps.add(senquence);
				processedLumberLogs.put(bestLumberLog.getId(), true);
				//update qunatities
				Map<String, Integer> lumberLogPieces = bestDiagram.cutInfo.cutPieces;
				Iterator<Map.Entry<String, Integer>> ite = lumberLogPieces.entrySet().iterator();
				double senquencePercent = 0;
				while(ite.hasNext()) {
					Map.Entry<String, Integer> entry = ite.next();
					String productName = entry.getKey();
					Integer piecesFromLumber = entry.getValue();
					Integer index = productIndex.get(productName);
					Long targetPieces = records.get(index).getPieces();
					senquencePercent += piecesFromLumber.doubleValue()/ targetPieces;
					senquence.setPercentage(senquencePercent);
					cutDataInfo.get(index).addCutPieces(piecesFromLumber.longValue());
				}
                statistics.lumberLogSelected(bestLumberLog, bestDiagram);
			}
		}
        if(processedLumberLogs.size() != lumberLogs.size()) {
            for(LumberLog lumberLog: lumberLogs) {
                if(!processedLumberLogs.containsKey(lumberLog.getId())) {
                    CutDiagram diagram =  processor.getBestCut(lumberLog, firstTargetCutInfo[0], firstTargetCutInfo[1], cutStrategies);
                    if(diagram != null && diagram.cutInfo.cutVolume > 0) {
                        statistics.lumberLogSelected(lumberLog, diagram);
                    }
                }
            }
        }
        statistics.computeStats();
		return null;
	}

    public CutPlanStatistics getStatistics() {
        return statistics;
    }

    public void setStatistics(CutPlanStatistics statistics) {
        this.statistics = statistics;
    }

    private List<Object>[] getCurrentCutTarget() {
		List<Product> products = new ArrayList<>(records.size());
		List<Integer> targetPieces = new ArrayList<>(records.size());
		for(int index = 0; index < records.size(); index++) {
			if(!cutDataInfo.get(index).isTargetReached()) {
				products.add(productMap.get(index));
				targetPieces.add(cutDataInfo.get(index).getRemainingCount().intValue());
			}
		}
		return new List[] {products, targetPieces};
	}

	/* (non-Javadoc)
	 * @see javax.swing.SwingWorker#done()
	 */
	@Override
	protected void done() {
		if(frame!=null){
            frame.showPlan(planSteps, cutDataInfo);
            frame.showStatistics(statistics);
        }
       // statistics.printStatistics();
		super.done();
	}

}
