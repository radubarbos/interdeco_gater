package ro.barbos.gui;

import ro.barbos.gater.dao.LumberStackDAO;
import ro.barbos.gater.data.IDPlateManager;
import ro.barbos.gater.data.LumberLogUtil;
import ro.barbos.gater.data.METRIC;
import ro.barbos.gater.model.IDPlate;
import ro.barbos.gater.model.LumberLog;
import ro.barbos.gater.model.LumberStack;
import ro.barbos.gater.stock.StockSettings;
import ro.barbos.gui.exswing.SuggestionJComboBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class AddLogLumberPanel extends JPanel implements ActionListener, PropertyChangeListener, ItemListener {
	
	
	
	private LumberStack lastStack = null;
	private JPanel middleRadiusPanel = null;
	private LumberLog editLumberLog = null;
	
	public AddLogLumberPanel(LumberLog lumberLog) {
		editLumberLog = lumberLog;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.add(GUIFactory.createLabel("Placuta:", 180));
		SuggestionJComboBox<IDPlate> plates = new SuggestionJComboBox<IDPlate>(IDPlateManager.getAvailableIDPlates().toArray(new IDPlate[0]));
		if(lumberLog != null) {
			((DefaultComboBoxModel<IDPlate>)plates.getModel()).addElement(lumberLog.getPlate());
			plates.setSelectedIndex(plates.getModel().getSize()-1);
			plates.setEnabled(false);
		}
		plates.setPreferredSize(new Dimension(80, plates.getPreferredSize().height));
		panel.add(plates);
		add(panel);
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.add(GUIFactory.createLabel("Lungime:", 180));
		JFormattedTextField lengthInput = GUIFactory.createNumberInput(null, 0L, 1000000L, 80);
		if(lumberLog != null) {
			lengthInput.setValue(lumberLog.getRealLength());
		}
		lengthInput.addPropertyChangeListener("value", this);
		panel.add(lengthInput);
		JComboBox<String> mCombo = new JComboBox<String>(GUIUtil.metric);
		mCombo.addItemListener(this);
		mCombo.setPreferredSize(new Dimension(50, mCombo.getPreferredSize().height));
		panel.add(mCombo);
		add(panel);
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.add(GUIFactory.createLabel("Diametru mic:", 180));
		JFormattedTextField smallRadius = GUIFactory.createNumberInput(null, 0L, 1000000L, 80);
		panel.add(smallRadius);
		mCombo = new JComboBox<String>(GUIUtil.metric);
		mCombo.setPreferredSize(new Dimension(50, mCombo.getPreferredSize().height));
		if(lumberLog != null) {
			smallRadius.setValue(lumberLog.getSmallRadius().longValue());
		}
		panel.add(mCombo);
		add(panel);
		
		middleRadiusPanel = new JPanel();
		middleRadiusPanel.setLayout(new BoxLayout(middleRadiusPanel, BoxLayout.Y_AXIS));

        if(lumberLog == null && StockSettings.MEASURE_MIDDLE_ONCE) {
            panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            panel.add(GUIFactory.createLabel(createMiddleRadiusLabel(0, false), 180));
            panel.add(GUIFactory.createNumberInput(null, 0L, 1000000L, 80));
            mCombo = new JComboBox<String>(GUIUtil.metric);
            mCombo.setPreferredSize(new Dimension(50, mCombo.getPreferredSize().height));
            panel.add(mCombo);
            middleRadiusPanel.add(panel);
        }
        else if(lumberLog != null && lumberLog.getMediumRadius() != null) {
			for(int index =0; index < lumberLog.getMediumRadius().size(); index++) {
    			JPanel panelMiddle = new JPanel(new FlowLayout(FlowLayout.LEFT));
    			panelMiddle.add(GUIFactory.createLabel(createMiddleRadiusLabel(index, lumberLog.getMediumRadius().size()>1), 180));
    			JFormattedTextField middleVal = GUIFactory.createNumberInput(null, 0L, 1000000L, 80);
    			middleVal.setValue(lumberLog.getMediumRadius().get(index).longValue());
    			panelMiddle.add(middleVal);
    			JComboBox<String> mComboMiddle = new JComboBox<String>(GUIUtil.metric);
    			mComboMiddle.setPreferredSize(new Dimension(50, mComboMiddle.getPreferredSize().height));
    			panelMiddle.add(mComboMiddle);
    			middleRadiusPanel.add(panelMiddle);
    		}
		}
		add(middleRadiusPanel);


		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.add(GUIFactory.createLabel("Diametru mare:", 180));
		JFormattedTextField bigRadius = GUIFactory.createNumberInput(null, 0L, 1000000L, 80);
		panel.add(bigRadius);
		mCombo = new JComboBox<String>(GUIUtil.metric);
		mCombo.setPreferredSize(new Dimension(50, mCombo.getPreferredSize().height));
		if(lumberLog != null) {
			bigRadius.setValue(lumberLog.getBigRadius().longValue());
		}
		panel.add(mCombo);
		add(panel);
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.add(GUIFactory.createLabel("Tip:", 180));
		JComboBox<String> tipCombo = new JComboBox<String>(GUIUtil.types);
		((DefaultComboBoxModel<String>)tipCombo.getModel()).insertElementAt("", 0);
		tipCombo.setSelectedIndex(0);
		if(lumberLog != null) {
			tipCombo.setSelectedIndex(lumberLog.getLumberType().intValue());
		}
		tipCombo.setPreferredSize(new Dimension(80, tipCombo.getPreferredSize().height));
		panel.add(tipCombo);
		add(panel);
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.add(GUIFactory.createLabel("Clasa:", 180));
		JComboBox<String> classCombo = new JComboBox<String>(GUIUtil.lumberClass);
		((DefaultComboBoxModel<String>)classCombo.getModel()).insertElementAt("", 0);
		classCombo.setSelectedIndex(0);
		if(lumberLog != null) {
			classCombo.setSelectedIndex(lumberLog.getLumberClass().intValue());
		}
		classCombo.setPreferredSize(new Dimension(80, classCombo.getPreferredSize().height));
		panel.add(classCombo);
		add(panel);
		
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 20));
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
    public void propertyChange(PropertyChangeEvent evt) { 
        Long length = (Long)evt.getNewValue();
        JComboBox<String> lengthMetric = (JComboBox<String>)((JPanel)getComponent(1)).getComponent(2);
        lengthChanged(length, lengthMetric.getSelectedIndex());
    }
	
	

	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	@Override
	public void itemStateChanged(ItemEvent eve) {
		if(eve.getStateChange() == ItemEvent.SELECTED) {
			JFormattedTextField length = (JFormattedTextField)((JPanel)getComponent(1)).getComponent(1);
			lengthChanged((Long)length.getValue(), ((JComboBox<String>)eve.getSource()).getSelectedIndex());
		}
	}

    private void lengthChanged(Long length, int lengthMetricIndex) {
        if(StockSettings.MEASURE_MIDDLE_ONCE) {
            return;
        }
    	if(length != null) {
        	double lengthValue =  METRIC.toMilimeter(length, lengthMetricIndex);
        	int meters = (int)lengthValue/1000;
        	meters--;
        	middleRadiusPanel.removeAll();
        	if(meters>10) meters = 10;
        	if(meters>0) {
        		for(int index =0; index < meters; index++) {
        			JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        			panel.add(GUIFactory.createLabel(createMiddleRadiusLabel(index, true), 180));
        			panel.add(GUIFactory.createNumberInput(null, 0L, 1000000L, 80));
        			JComboBox<String> mCombo = new JComboBox<String>(GUIUtil.metric);
        			mCombo.setPreferredSize(new Dimension(50, mCombo.getPreferredSize().height));
        			panel.add(mCombo);
        			middleRadiusPanel.add(panel);
        		}
        	}
        	middleRadiusPanel.revalidate();
        	middleRadiusPanel.repaint();
        }
    }

    private String createMiddleRadiusLabel(int index, boolean force) {
      String label = "Diametru "+(index+1)+"m:";
      if(StockSettings.MEASURE_MIDDLE_ONCE && index == 0 && !force) {
          label = "Diametru mijlociu";
      }
      return label;
    }

	public LumberLog validateData() {
		boolean status = true;
		lastStack = null;
		
		JLabel plateLabel = (JLabel)((JPanel)getComponent(0)).getComponent(0);
		SuggestionJComboBox plates = (SuggestionJComboBox)((JPanel)getComponent(0)).getComponent(1);
		IDPlate plate = (IDPlate)plates.getSelectedItem();
		if(plate == null) {
			plateLabel.setForeground(Color.red);
			status = false;
		}
		else {
			plateLabel.setForeground(Color.black);
		}
		JLabel lenLabel = (JLabel)((JPanel)getComponent(1)).getComponent(0);
		JFormattedTextField lenRadius = (JFormattedTextField)((JPanel)getComponent(1)).getComponent(1);
		JComboBox<String> lenRadiusMetric = (JComboBox<String>)((JPanel)getComponent(1)).getComponent(2);
		lenLabel.setForeground(Color.black);
		if(lenRadius.getValue() == null) {
			status = false;
			lenLabel.setForeground(Color.red);
		}
		JLabel smallLabel = (JLabel)((JPanel)getComponent(2)).getComponent(0);
		JFormattedTextField smallRadius = (JFormattedTextField)((JPanel)getComponent(2)).getComponent(1);
		JComboBox<String> smallRadiusMetric = (JComboBox<String>)((JPanel)getComponent(2)).getComponent(2);
		smallLabel.setForeground(Color.black);
		if(smallRadius.getValue() == null) {
			status = false;
			smallLabel.setForeground(Color.red);
		}

        JLabel bigLabel = (JLabel)((JPanel)getComponent(4)).getComponent(0);
        JFormattedTextField bigRadius = (JFormattedTextField)((JPanel)getComponent(4)).getComponent(1);
        JComboBox<String> bigRadiusMetric = (JComboBox<String>)((JPanel)getComponent(4)).getComponent(2);
        bigLabel.setForeground(Color.black);
        if(bigRadius.getValue() == null) {
            status = false;
            bigLabel.setForeground(Color.red);
        }

        double minimumValue =  METRIC.toMilimeter((Long)smallRadius.getValue(), smallRadiusMetric.getSelectedIndex());
        double maxmimumValue =  METRIC.toMilimeter((Long)bigRadius.getValue(), bigRadiusMetric.getSelectedIndex());
		
		List<Double> middleRadius = new ArrayList<>();
		for(int index = 0; index < middleRadiusPanel.getComponentCount(); index++) {
			JLabel label = (JLabel)((JPanel)middleRadiusPanel.getComponent(index)).getComponent(0);
			JFormattedTextField radius = (JFormattedTextField)((JPanel)middleRadiusPanel.getComponent(index)).getComponent(1);
			JComboBox<String> radiusMetric = (JComboBox<String>)((JPanel)middleRadiusPanel.getComponent(index)).getComponent(2);
			label.setForeground(Color.black);
            Long radValue = (Long)radius.getValue();
			if(radValue == null) {
				status = false;
				label.setForeground(Color.red);
			}
			else {
				double mediumValue =  METRIC.toMilimeter((Long)radius.getValue(), radiusMetric.getSelectedIndex());
                if(mediumValue < minimumValue || mediumValue>maxmimumValue) {
                    status = false;
                    label.setForeground(Color.red);
                }
				middleRadius.add(mediumValue);
			}
		}
		

		
		JLabel typeLabel = (JLabel)((JPanel)getComponent(5)).getComponent(0);
		JComboBox<String> typeValue = (JComboBox<String>)((JPanel)getComponent(5)).getComponent(1);
		typeLabel.setForeground(Color.black);
		if(typeValue.getSelectedIndex() == 0) {
			status = false;
			typeLabel.setForeground(Color.red);
		}
		
		JLabel classLabel = (JLabel)((JPanel)getComponent(6)).getComponent(0);
		JComboBox<String> classValue = (JComboBox<String>)((JPanel)getComponent(6)).getComponent(1);
		classLabel.setForeground(Color.black);
		if(classValue.getSelectedIndex() == 0) {
			status = false;
			classLabel.setForeground(Color.red);
		}
		
		if(!status) {
			return null;
		}
		

		
		if(minimumValue > maxmimumValue) {
			smallLabel.setForeground(Color.red);
			bigLabel.setForeground(Color.red);
			status = false;
		}
	
		
		METRIC minimumMetric = null;
		switch (smallRadiusMetric.getSelectedIndex()) {
		case 0: minimumMetric = METRIC.MILIMETER;break;
		case 1: minimumMetric = METRIC.CENTIMETER;break;
		case 2: minimumMetric = METRIC.DECIMETER;break;
		case 3: minimumMetric = METRIC.METER;break;
		}
		
		lastStack = LumberStackDAO.getLumberStack(((Long)smallRadius.getValue()).doubleValue(), minimumMetric);
		if(lastStack == null) {
		   status = false;
		   JOptionPane.showMessageDialog(null, "Nu a fost gasita nici o stiva pentru diametru "+smallRadius.getValue());
		}
		
		if(!status) {
			return null;
		}
		
		
		LumberLog lumberLog = new LumberLog();
		JComboBox<String> plateCombo = (JComboBox<String>)((JPanel)getComponent(0)).getComponent(1);
		lumberLog.setPlate((IDPlate)plateCombo.getSelectedItem());
		lumberLog.setSmallRadius((double)minimumValue);
		lumberLog.setMediumRadius(middleRadius);
		lumberLog.setBigRadius((double)maxmimumValue);
		lumberLog.setMetric(METRIC.MILIMETER);
		
		double lengthValue =  METRIC.toMilimeter((Long)lenRadius.getValue(), lenRadiusMetric.getSelectedIndex());
		lumberLog.setLength(lengthValue);
		
		JComboBox<String> tipCombo = (JComboBox<String>)((JPanel)getComponent(5)).getComponent(1);
		lumberLog.setLumberType((long)tipCombo.getSelectedIndex());
		
		JComboBox<String> classCombo = (JComboBox<String>)((JPanel)getComponent(6)).getComponent(1);
		lumberLog.setLumberClass((long)classCombo.getSelectedIndex());
		
		//extra validation
		String warning = "";
		if(lengthValue < StockSettings.RECEIVE_MIN_LENGTH) {
			warning += "Lungime i mai mica ca limita admisa" + System.lineSeparator();
		}
		if(lengthValue > StockSettings.RECEIVE_MAX_LENGTH) {
			warning += "Lungime i mai mare ca limita admisa" + System.lineSeparator();
		}
		LumberLogUtil.calculateVolume(lumberLog);
		if(lumberLog.getVolume()/1000000000L > StockSettings.RECEIVE_MAX_VOLUME) {
			warning += "Volumul i mai mare ca limita admisa" + System.lineSeparator();
		}
		if(warning.length()>0) {
			int confirmation = JOptionPane.showConfirmDialog(null, warning);
			if(confirmation!=JOptionPane.YES_OPTION) {
				return null;
			}
		}
		
		lumberLog.setStack(lastStack);
		if(editLumberLog != null) {
			lumberLog.setId(editLumberLog.getId());
		}
		
		return lumberLog;
	}
	
	public LumberStack getLumberStack() {
		return lastStack;
	}
}
