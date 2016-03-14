package ro.barbos.gui;

import ro.barbos.gater.cutprocessor.CutOptionsCalculator;
import ro.barbos.gater.cutprocessor.CutPlanSenquence;
import ro.barbos.gater.cutprocessor.DefaultCutOptionsCalculatorData;
import ro.barbos.gater.model.IDPlate;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class CutOptionsTargetFrame extends GeneralFrame {
	
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> cutInfo;
    private DefaultCutOptionsCalculatorData data;
	
	private JProgressBar progressBar;
	
	public CutOptionsTargetFrame(Map<String, Object> cutInfo, DefaultCutOptionsCalculatorData data) {
		this.cutInfo = cutInfo;
        this.data = data;
		IDPlate plate = (IDPlate)cutInfo.get("IDPLATE");
		setTitle("Optiuni taiere taiere pentru busteanul " + cutInfo.get("IDPLATE_LABEL"));
		setResizable(true);
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		
		
		GUIUtil.container.addFrame(this, getFrameCode());
		doCalculations();
	}
	
	private void doCalculations() {
		getContentPane().removeAll();
		progressBar= new JProgressBar();
		progressBar.setStringPainted(true);
		getContentPane().add(progressBar, BorderLayout.NORTH);
		//IDPlate plate = (IDPlate)cutInfo.get("IDPLATE");
		//LumberLogFilterDTO filter = new LumberLogFilterDTO();
		//filter.setIdPlates(Arrays.asList(new Long[]{plate.getId().longValue()}));
		//List<LumberLogStockEntry> lumbers = StockDAO.getCurrentLumbersLogs(filter);
		//List<Product> selectedProducts = (List<Product>)(cutInfo.get("SELECTED_PRODUCTS"));
		//List<Product> allProducts = (List<Product>)(cutInfo.get("ALL_PRODUCTS"));
		//LumberLog lumberLog = lumbers.get(0).getLumberLog();

		CutOptionsCalculator calculator = new CutOptionsCalculator(data.getLumberLog(), data);
		calculator.setFrame(this);
		calculator.execute();
		progressBar.setValue(1);
	}
	
	public void showDiagrams(List<CutPlanSenquence> diagrams) {
		getContentPane().removeAll();
		JPanel center = new JPanel();
		center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
		for(CutPlanSenquence diagram: diagrams) {
			CutDiagramPaintPanel painter = new CutDiagramPaintPanel(diagram.getLumberLog(), diagram.getCutDiagram());
			center.add(painter);
		}
		getContentPane().add(new JScrollPane(center));
	}
	
	
	
	/**
	 * @return the progressBar
	 */
	public JProgressBar getProgressBar() {
		return progressBar;
	}

	/**
	 * @param progressBar the progressBar to set
	 */
	public void setProgressBar(JProgressBar progressBar) {
		this.progressBar = progressBar;
	}

	@Override
	public String getFrameCode() {
		return GUIUtil.CUT_OPTIONS_KEY;
	}

	@Override
	public ImageIcon getFrameIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImageIcon getIconifiedIcon() {
		Image image = GUITools.getImage("/ro/barbos/gui/resources/product32.png");
		return new ImageIcon(image);
	}

}
