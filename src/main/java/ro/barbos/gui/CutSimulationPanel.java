package ro.barbos.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.NumberFormatter;

import ro.barbos.gater.dao.IDPlateDAO;
import ro.barbos.gater.dao.ProductDAO;
import ro.barbos.gater.dao.StockDAO;
import ro.barbos.gater.dto.LumberLogFilterDTO;
import ro.barbos.gater.model.IDPlate;
import ro.barbos.gater.model.LumberLog;
import ro.barbos.gater.model.Product;
import ro.barbos.gui.exswing.SuggestionJComboBox;

public class CutSimulationPanel extends JPanel {
	
	public CutSimulationPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.add(GUIFactory.createLabel("Placuta:", 100));
		SuggestionJComboBox<IDPlate> plates = new SuggestionJComboBox<IDPlate>(IDPlateDAO.getUsedPlates().toArray(new IDPlate[0]));
		plates.setPreferredSize(new Dimension(80, plates.getPreferredSize().height));
		panel.add(plates);
		add(panel);
		
		List<Product> products = ProductDAO.getProducts();
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.add(GUIFactory.createLabel("Product1:", 100));
		JComboBox<Product> product1 = new JComboBox<Product>(products.toArray(new Product[0]));
		panel.add(product1);
		panel.add(createNumberInput());
		add(panel);
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.add(GUIFactory.createLabel("Product2:", 100));
		JComboBox<Product> product2 = new JComboBox<Product>(products.toArray(new Product[0]));
		panel.add(product2);
		panel.add(createNumberInput());
		add(panel);
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.add(GUIFactory.createLabel("Product3:", 100));
		JComboBox<Product> product3 = new JComboBox<Product>(products.toArray(new Product[0]));
		panel.add(product3);
		panel.add(createNumberInput());
		add(panel);
	}
	
	public LumberLog getLumberLog() {
		JComboBox<String> plateCombo = (JComboBox<String>)((JPanel)getComponent(0)).getComponent(1);
		LumberLogFilterDTO filter = new LumberLogFilterDTO();
		List<Long> idplates = new ArrayList<>();
		idplates.add(((IDPlate)plateCombo.getSelectedItem()).getId());
		filter.setIdPlates(idplates);
		return StockDAO.getCurrentLumbersLogs(filter).get(0).getLumberLog();
	}
	
	public List<Product> getProducts() {
		List<Product> products = new ArrayList<>();
		JComboBox<Product> product1 = (JComboBox<Product>)((JPanel)getComponent(1)).getComponent(1);
		products.add((Product)product1.getSelectedItem());
		JComboBox<Product> product2 = (JComboBox<Product>)((JPanel)getComponent(2)).getComponent(1);
		products.add((Product)product2.getSelectedItem());
		JComboBox<Product> product3 = (JComboBox<Product>)((JPanel)getComponent(3)).getComponent(1);
		products.add((Product)product3.getSelectedItem());
		return products;
	}
	
	public List<Integer> getPieces() {
		List<Integer> pieces = new ArrayList<>();
		JFormattedTextField product1 = (JFormattedTextField)((JPanel)getComponent(1)).getComponent(2);
		pieces.add((Integer)product1.getValue());
		JFormattedTextField product2 = (JFormattedTextField)((JPanel)getComponent(2)).getComponent(2);
		pieces.add((Integer)product2.getValue());
		JFormattedTextField product3 = (JFormattedTextField)((JPanel)getComponent(3)).getComponent(2);
		pieces.add((Integer)product3.getValue());
		return pieces;
	}
	
	private JFormattedTextField createNumberInput() {
		NumberFormat nrFormat = NumberFormat.getIntegerInstance(ConfigLocalManager.locale); 
		//nrFormat.setGroupingUsed(false);
		NumberFormatter formater = new NumberFormatter(nrFormat);
		formater.setValueClass(Integer.class);
		//formater.setAllowsInvalid(false);
		formater.setMinimum(0);
		formater.setMaximum(1000000);
		JFormattedTextField field = new JFormattedTextField(formater);
		field.setPreferredSize(new Dimension(40, field.getPreferredSize().height));
		field.setValue(0);
		return field;
	}
	
	public boolean validateData() {
		boolean valid = true;
		JLabel label = (JLabel)((JPanel)getComponent(0)).getComponent(0);
		SuggestionJComboBox plates = (SuggestionJComboBox)((JPanel)getComponent(0)).getComponent(1);
		IDPlate plate = (IDPlate)plates.getSelectedItem();
		if(plate == null) {
			label.setForeground(Color.red);
			valid = false;
		}
		else {
			label.setForeground(Color.black);
		}
		/*for(int index =0; index< this.getComponentCount(); index++) {
			JLabel label = (JLabel)((JPanel)getComponent(index)).getComponent(0);
			JFormattedTextField quantity = (JFormattedTextField)((JPanel)getComponent(index)).getComponent(3);
			if(quantity.getValue() == null || (Double)quantity.getValue() == 0) {
				label.setForeground(Color.red);
				valid = false;
			}
			else {
				label.setForeground(Color.black);
			}
		}
		if(this.getComponentCount() == 0) valid = false;*/
		return valid;
	}

}
