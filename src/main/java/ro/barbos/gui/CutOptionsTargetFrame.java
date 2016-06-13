package ro.barbos.gui;

import ro.barbos.gater.cutprocessor.CutOptionsCalculator;
import ro.barbos.gater.cutprocessor.CutPlanSenquence;
import ro.barbos.gater.cutprocessor.DefaultCutOptionsCalculatorData;
import ro.barbos.gui.cut.diagram.CutDiagramPaintPanel;

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
