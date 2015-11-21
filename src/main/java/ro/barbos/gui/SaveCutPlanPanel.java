package ro.barbos.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SaveCutPlanPanel extends JPanel {

	JTextField name = new JTextField(15);
	JComboBox<Integer> day = new JComboBox<Integer>();
	JComboBox<String> month = new JComboBox<String>(new String[]{"Ianuarie", "Februarie", "Martie", "Aprilie", "Mai", "Iunie", "Iulie", "August", "Septembrie", "Octombrie", "Noiembrie", "Decembrie"});
	JComboBox<Integer> year = new JComboBox<Integer>();
	JCheckBox active = new JCheckBox();
	
	public SaveCutPlanPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel row = new JPanel(new FlowLayout(FlowLayout.LEADING));
		row.add(GUIFactory.createLabel("Nume", 100));
		row.add(name);
		add(row);
		
		Date now = new Date();
		for(int i=1; i<32; i++) {
			((DefaultComboBoxModel<Integer>)day.getModel()).addElement(i);
		}
		int yearNo = now.getYear() + 1900;
		for(int i=yearNo; i<(yearNo+3); i++) {
			((DefaultComboBoxModel<Integer>)year.getModel()).addElement(i);
		}
		row = new JPanel(new FlowLayout(FlowLayout.LEADING));
		row.add(GUIFactory.createLabel("Data", 100));
		row.add(GUIFactory.createLabel("Zi", 20));
		day.setSelectedIndex(now.getDate()-1);
		row.add(day);
		row.add(GUIFactory.createLabel("Luna", 30));
		month.setSelectedIndex(now.getMonth());
		row.add(month);
		row.add(GUIFactory.createLabel("Year", 30));
		row.add(year);
		add(row);
		
		row = new JPanel(new FlowLayout(FlowLayout.LEADING));
		row.add(GUIFactory.createLabel("Active", 100));
		active.setSelected(true);
		row.add(active);
		add(row);
	}
	
	public boolean validateData() {
		boolean valid = true;
		JLabel label = (JLabel)((JPanel)getComponent(0)).getComponent(0);
		if(name.getText().trim().length()==0) {
			label.setForeground(Color.red);
			valid = false;
		}
		else {
			label.setForeground(Color.black);
		}
		return valid;
	}
	
	public String getPlanName() {
		return name.getText();
	}
	
	public Date getPlanDate() {
		Date date = new Date();
		date.setDate((Integer)day.getSelectedItem());
		date.setYear((Integer)year.getSelectedItem() - 1900);
		date.setMonth(month.getSelectedIndex());
		return date;
	}
	
	public Boolean isPlanActive() {
		return active.isSelected();
	} 
	
}
