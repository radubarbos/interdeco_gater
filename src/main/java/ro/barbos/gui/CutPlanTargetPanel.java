package ro.barbos.gui;

import ro.barbos.gater.dao.ProductDAO;
import ro.barbos.gater.model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CutPlanTargetPanel extends JPanel implements ActionListener {

	private List<Product> products = ProductDAO.getProducts();
	private int productIndex = 0;
	
	public CutPlanTargetPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(createNewEntry());
	}
	
	private JPanel createNewEntry() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.add(GUIFactory.createLabel("Product"+(++productIndex)+":", 80));
		JComboBox<Product> product1 = new JComboBox<Product>(products.toArray(new Product[0]));
		panel.add(product1);
		panel.add(GUIFactory.createLabel("Bucati", 50));
		panel.add(GUIFactory.createNumberInput(0L, 0L, 1000000L, 50));
		panel.add(createRemove());
		return panel;
	}
	
	private JButton createRemove() {
		JButton but = new JButton(new ImageIcon(
				GUITools.getImage("resources/delete24.png")));
		but.setFocusPainted(false);
		but.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		but.setToolTipText("Sterge placuta");
		but.setActionCommand("DELETE_ROW");
		but.setContentAreaFilled(false);
		but.setBorder(null);
		but.addActionListener(this);
		return but;
	}
	
	public void addNewProduct() {
		add(createNewEntry());
		revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("DELETE_ROW")) {
			JButton but = (JButton)e.getSource();
			Container parent = but.getParent();
			remove(parent);
			revalidate();
			relabel();
			repaint();
		}
	}
	
	private void relabel() {
		productIndex = getComponentCount();
		for(int index =0; index< productIndex; index++) {
			JLabel label = (JLabel)((JPanel)getComponent(index)).getComponent(0);
			label.setText("Product"+(index+1)+":");
		}
		
	}
	
	public boolean validateData() {
		boolean valid = true;
		for(int index =0; index< this.getComponentCount(); index++) {
			JLabel label = (JLabel)((JPanel)getComponent(index)).getComponent(0);
			JFormattedTextField quantity = (JFormattedTextField)((JPanel)getComponent(index)).getComponent(3);
			if(quantity.getValue() == null || (Long)quantity.getValue() == 0) {
				label.setForeground(Color.red);
				valid = false;
			}
			else {
				label.setForeground(Color.black);
			}
		}
		if(this.getComponentCount() == 0) valid = false;
		return valid;
	}
	
	public List<List<Object>> getData() {
		List<List<Object>> target = new ArrayList<>();
		for(int index =0; index< productIndex; index++) {
			JComboBox<Product> product = (JComboBox<Product>)((JPanel)getComponent(index)).getComponent(1);
			JFormattedTextField quantity = (JFormattedTextField)((JPanel)getComponent(index)).getComponent(3);
			List<Object> data = new ArrayList<>();
			data.add(product.getSelectedItem());
			data.add(quantity.getValue());
			target.add(data);
		}
		
		return target;
	}
}
