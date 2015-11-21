package ro.barbos.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

import ro.barbos.gater.dao.LumberStackDAO;
import ro.barbos.gater.data.METRIC;
import ro.barbos.gater.model.IDPlate;
import ro.barbos.gater.model.LumberLog;
import ro.barbos.gater.model.LumberStack;

public class AddLumberstackPanel extends JPanel implements ActionListener {
	
	
	public AddLumberstackPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.add(GUIFactory.createLabel("Nume:", 150));
		JTextField name = new JTextField();
		name.setPreferredSize(new Dimension(80, name.getPreferredSize().height));
		panel.add(name);
		add(panel);
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.add(GUIFactory.createLabel("Minimum:", 150));
		panel.add(GUIFactory.createNumberInput(null, 0L, 99999L, 80));
		JComboBox<String> mCombo = new JComboBox<String>(GUIUtil.metric);
		mCombo.setPreferredSize(new Dimension(50, mCombo.getPreferredSize().height));
		panel.add(mCombo);
		add(panel);
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.add(GUIFactory.createLabel("Maximum:", 150));
		panel.add(GUIFactory.createNumberInput(null, 0L, 99999L, 80));
		mCombo = new JComboBox<String>(GUIUtil.metric);
		mCombo.setPreferredSize(new Dimension(50, mCombo.getPreferredSize().height));
		panel.add(mCombo);
		add(panel);
		
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public LumberStack validateData() {
		boolean status = true;
		JLabel nameLabel = (JLabel)((JPanel)getComponent(0)).getComponent(0);
		JTextField name = (JTextField)((JPanel)getComponent(0)).getComponent(1);
		nameLabel.setForeground(Color.black);
		if(name.getText().trim().length() ==0){
		  status = false;
		  nameLabel.setForeground(Color.red);
		}
		
		JLabel minimumLabel = (JLabel)((JPanel)getComponent(1)).getComponent(0);
		JFormattedTextField minimumRadius = (JFormattedTextField)((JPanel)getComponent(1)).getComponent(1);
		JComboBox<String> minimumRadiusMetric = (JComboBox<String>)((JPanel)getComponent(1)).getComponent(2);
		minimumLabel.setForeground(Color.black);
		if(minimumRadius.getValue() == null) {
			status = false;
			minimumLabel.setForeground(Color.red);
		}
		JLabel maximumLabel = (JLabel)((JPanel)getComponent(2)).getComponent(0);
		JFormattedTextField maximumRadius = (JFormattedTextField)((JPanel)getComponent(2)).getComponent(1);
		JComboBox<String> maximumRadiusMetric = (JComboBox<String>)((JPanel)getComponent(2)).getComponent(2);
		maximumLabel.setForeground(Color.black);
		if(maximumRadius.getValue() == null) {
			status = false;
			maximumLabel.setForeground(Color.red);
		}
		
		if(!status) {
			return null;
		}
		
		long minimumValue = getMilimetricNumericValue((Long)minimumRadius.getValue(), minimumRadiusMetric.getSelectedIndex());
		long maximumValue = getMilimetricNumericValue((Long)maximumRadius.getValue(), maximumRadiusMetric.getSelectedIndex());
		
		if(minimumValue > maximumValue) {
			minimumLabel.setForeground(Color.red);
			maximumLabel.setForeground(Color.red);
			status = false;
		}
		
		
		if(!status) {
			return null;
		}
		
		LumberStack stack = new LumberStack();
		stack.setName(name.getText().trim());
		stack.setMinimum((double)minimumValue);
		stack.setMaximum((double)maximumValue);
		
		return stack;
	} 

	private long getMilimetricNumericValue(long value, int metricIndex) {
		switch(metricIndex) {
		case 0: return value;
		case 1: return value * 10;
		case 2: return value * 100;
		case 3: return value * 1000;
		}
		return value;
	}
}
