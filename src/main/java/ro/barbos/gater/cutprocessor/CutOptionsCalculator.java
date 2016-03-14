package ro.barbos.gater.cutprocessor;

import ro.barbos.gater.cutprocessor.diagram.CutDiagram;
import ro.barbos.gater.cutprocessor.strategy.CutStrategyType;
import ro.barbos.gater.model.LumberLog;
import ro.barbos.gater.model.Product;
import ro.barbos.gui.CutOptionsTargetFrame;

import javax.swing.*;
import java.util.*;

public class CutOptionsCalculator extends SwingWorker<List<CutPlanSenquence>, Object>{
	
	private LumberLog lumberLog;
	//private List<Product> products;
	//private Map<Long, Boolean> selectedMap;
	//private List<Product> allProducts;
	
	//private List<Integer> targetPieces;
	private List<CutPlanSenquence> result;
	
	private CutOptionsTargetFrame frame;

    DefaultCutOptionsCalculatorData data;
	
	private int combinations = 0;
	private int combinationIndex = 0;

    private List<Integer> targetPieces = null;//Arrays.asList(1111111111);

    public CutOptionsCalculator(LumberLog lumberLog, DefaultCutOptionsCalculatorData data) {
		this.lumberLog = lumberLog;
		/*this.products = products;
		this.allProducts = allProducts;*/
		targetPieces = new ArrayList<>();
        targetPieces.add(111111111);
		result = new ArrayList<CutPlanSenquence>();
		/*selectedMap = new HashMap<>();
		for(Product product: products) {
			selectedMap.put(product.getId(), true);
		}*/
        this.data = data;
        combinations = data.getSelectedProducts().size() + data.getProducts().size();
	}

	/* (non-Javadoc)
	 * @see javax.swing.SwingWorker#doInBackground()
	 */
	@Override
	protected List<CutPlanSenquence> doInBackground() throws Exception {
		Map<String, Boolean> cutStrategies = CutStrategyType.getActiveStrategies(); //new HashMap<>();
		/*cutStrategies.put(CutStrategyType.SIMPLE_VERTICAL.name(), true);
		cutStrategies.put(CutStrategyType.BEST_MATCH_VERTICAL.name(), true);
		cutStrategies.put(CutStrategyType.ROTATE_ONE.name(), true);
		cutStrategies.put(CutStrategyType.BEST_MULTIBLADE_MATCH.name(), true);*/
		
		//int diff = allProducts.size() - products.size();
		//combinations = diff;
		//combinations += allProducts.size();

        double efficence = 0;
		CuterProcessor processor = new CuterProcessor();

        CutDiagram selectedDiagram = null;
        if(!data.getSelectedProducts().isEmpty()) {
            selectedDiagram = processor.getBestCut(lumberLog, data.getSelectedProducts(), getTargetPieces(data.getSelectedProducts().size()), cutStrategies);
            efficence = selectedDiagram.cutInfo.cutVolumeEfficency;
        }

		
		//adding one to selected products
		/*for(int index = 0; index < allProducts.size(); index++) {
			if(selectedMap.containsKey(index)) {
				continue;
			}
			CutDiagram alternativeDiagram = processor.getBestCut(lumberLog, getProductVariationOne(index), getTargetPieces(products.size()), cutStrategies);
			if(alternativeDiagram != null && alternativeDiagram.cutInfo.cutVolumeEfficency > efficence) {//TODO check fara efience limit
				result.add(createCutStep(alternativeDiagram));
			}
			combinationIndex++;
			updateProgress();
		}*/
		
		//use just one
		/*for(int index = 0; index < allProducts.size(); index++) {
			CutDiagram alternativeDiagram = processor.getBestCut(lumberLog, getSingleProduct(index), getTargetPieces(1), cutStrategies);
			if(alternativeDiagram != null && alternativeDiagram.cutInfo.cutVolumeEfficency > efficence) {
				result.add(createCutStep(alternativeDiagram));
			}
			combinationIndex++;
			updateProgress();
		}*/

        for(List<Product> entry: data.getProducts()) {
            CutDiagram alternativeDiagram = processor.getBestCut(lumberLog, entry, getTargetPieces(entry.size()), cutStrategies);
            if(alternativeDiagram != null && alternativeDiagram.cutInfo.cutVolumeEfficency > efficence) {
                result.add(createCutStep(alternativeDiagram));
            }
            combinationIndex++;
            updateProgress();
        }
		
		
		sortAlternativeCuts();
        if(!data.getSelectedProducts().isEmpty()) {
            result.add(0, createCutStep(selectedDiagram));
        }

        if(result.size()>130) {
            for(int h=result.size()-1;h>130;h--) {
                result.remove(h);
            }
        }
		
		return null;
	}
	
	private List<Integer> getTargetPieces(int productsCount) {
		if(productsCount > targetPieces.size()) {
			int limit = productsCount - targetPieces.size();
			for(int i=0;i<limit;i++) {
				targetPieces.add(1111111);
			}
		}
		return targetPieces;
	}
	
	/*private List<Product> getProductVariationOne(int index) {
		List<Product> products = new ArrayList<>();
		*//*for(Product product: products) {
			products.add(product);
		}*//*
		products.add(allProducts.get(index));
		return products;
	}*/
	
	/*private List<Product> getSingleProduct(int index) {
		List<Product> products = new ArrayList<>();
		products.add(allProducts.get(index));
		return products;
	}*/
	
	private CutPlanSenquence createCutStep(CutDiagram diagram){
		CutPlanSenquence seq = new CutPlanSenquence();
		seq.setCutDiagram(diagram);
		seq.setLumberLog(lumberLog);
		return seq;
	}
	
	private void updateProgress() { 
		int value = (combinationIndex * 100) / combinations;
		frame.getProgressBar().setValue(value);
	}
	
	private void sortAlternativeCuts() {
		Collections.sort(result, new Comparator<CutPlanSenquence>() {

			/* (non-Javadoc)
			 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
			 */
			@Override
			public int compare(CutPlanSenquence o1, CutPlanSenquence o2) {
				double efficence1 = o1.getCutDiagram().cutInfo.cutVolumeEfficency;
				double efficence2 = o2.getCutDiagram().cutInfo.cutVolumeEfficency;
				if(efficence2 > efficence1) {
					return 1;
				}
				return -1;
			}
			
		});
	}

	/* (non-Javadoc)
	 * @see javax.swing.SwingWorker#done()
	 */
	@Override
	protected void done() {
		frame.showDiagrams(result);
		super.done();
	}

	/**
	 * @return the frame
	 */
	public CutOptionsTargetFrame getFrame() {
		return frame;
	}

	/**
	 * @param frame the frame to set
	 */
	public void setFrame(CutOptionsTargetFrame frame) {
		this.frame = frame;
	}

	
	
}
