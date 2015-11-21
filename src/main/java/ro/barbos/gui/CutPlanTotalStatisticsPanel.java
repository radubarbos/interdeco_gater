package ro.barbos.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.text.NumberFormat;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ro.barbos.gater.cutprocessor.CutPlanSenquence;
import ro.barbos.gater.dto.ProductCutTargetDTO;

public class CutPlanTotalStatisticsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private NumberFormat formatter = NumberFormat.getNumberInstance(ConfigLocalManager.locale);

public void showTotalStatistics(List<CutPlanSenquence> plan, List<ProductCutTargetDTO> cutDataInfo) {
	  this.removeAll();
	  this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	  
	  JPanel row = new JPanel(new FlowLayout(FlowLayout.LEADING));
	  row.add(GUIFactory.createLabel("Numar busteni", 150));
	  row.add(createValueLabel(plan.size()));
	  this.add(row);
	  
	  double volumeLumber = 0, volumeProduct = 0;
	  for(CutPlanSenquence cutSequence: plan) {
		 volumeLumber += cutSequence.getLumberLog().getVolume()/1000000000L; 
		 volumeProduct += cutSequence.getCutDiagram().cutInfo.cutVolume/1000000000L;
	  }
	  
	  row = new JPanel(new FlowLayout(FlowLayout.LEADING));
	  row.add(GUIFactory.createLabel("Volum lemn", 150));
	  row.add(createValueLabel(formatter.format(volumeLumber) + " m .cub"));
	  this.add(row);
	  
	  row = new JPanel(new FlowLayout(FlowLayout.LEADING));
	  row.add(GUIFactory.createLabel("Volum produs", 150));
	  row.add(createValueLabel(formatter.format(volumeProduct) + " m .cub"));
	  this.add(row);
	  
	  row = new JPanel(new FlowLayout(FlowLayout.LEADING));
	  row.add(GUIFactory.createLabel("Eficenta total", 150));
	  row.add(createValueLabel(formatter.format(100*volumeProduct/volumeLumber) + " %"));
	  this.add(row);
	  
	  for(ProductCutTargetDTO productInfo: cutDataInfo) {
		  row = new JPanel(new FlowLayout(FlowLayout.LEADING));
		  row.add(GUIFactory.createLabel("Volum "+productInfo.getProduct(), 150));
		  row.add(createValueLabel(formatter.format(productInfo.getCutPieces() * (productInfo.getProductVolume()/1000000000L)) + " m. cub"));
		  this.add(row);
	  }
  }

  private JLabel createValueLabel(Object txt) {
	  JLabel label = new JLabel(txt.toString());
	  label.setForeground(Color.red);
	  return label;
  }
}
