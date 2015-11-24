package ro.barbos.gui;

import ro.barbos.gater.cutprocessor.diagram.CutDiagram;
import ro.barbos.gater.dao.CutPlanDAO;
import ro.barbos.gater.dao.IDPlateDAO;
import ro.barbos.gater.dao.LumberLogDAO;
import ro.barbos.gater.dao.StockDAO;
import ro.barbos.gater.dto.LumberLogFilterDTO;
import ro.barbos.gater.model.GeneralResponse;
import ro.barbos.gater.model.IDPlate;
import ro.barbos.gater.model.LumberLog;
import ro.barbos.gater.model.LumberLogStockEntry;
import ro.barbos.gui.exswing.SuggestionJComboBox;
import ro.barbos.gui.stock.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.List;

public class LeftPanel extends JPanel implements ActionListener {
	
	private JTextArea messageArea;
	SuggestionJComboBox<IDPlate> optPlates;
	
	public LeftPanel() {
		
		int buttonWidth = 200;
		int buttonHeight = 30;
		Dimension buttonDimension = new Dimension(buttonWidth, buttonHeight);
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		Integer rights = ConfigLocalManager.currentUser.getRights().getRightsLevel();
		
		JButton receptie = new JButton("Receptie");
		receptie.setActionCommand("RECEPTIE");
		receptie.addActionListener(this);
		receptie.setAlignmentX(0);
		receptie.setMaximumSize(buttonDimension);
				
		JButton stockCurrent = new JButton("Stock current");
		stockCurrent.setActionCommand("STOCK");
		stockCurrent.addActionListener(this);
		stockCurrent.setAlignmentX(0);
		stockCurrent.setMaximumSize(buttonDimension);
		
		JButton stockCurrent2 = new JButton("Stock per stiva");
		stockCurrent2.setActionCommand("STACK_STOCK");
		stockCurrent2.addActionListener(this);
		stockCurrent2.setAlignmentX(0);
		stockCurrent2.setMaximumSize(buttonDimension);
		
		JButton stockCurrent3 = new JButton("Stock per tip");
		stockCurrent3.setActionCommand("TIP_STOCK");
		stockCurrent3.addActionListener(this);
		stockCurrent3.setAlignmentX(0);
		stockCurrent3.setMaximumSize(buttonDimension);
		
		JButton stockCurrent4 = new JButton("Stock per class");
		stockCurrent4.setActionCommand("CLASS_STOCK");
		stockCurrent4.addActionListener(this);
		stockCurrent4.setAlignmentX(0);
		stockCurrent4.setMaximumSize(buttonDimension);
		
		JButton idplates = new JButton("Placi");
		idplates.setActionCommand("IDPLATES");
		idplates.addActionListener(this);
		idplates.setAlignmentX(0);
		idplates.setMaximumSize(buttonDimension);
		
		JButton stacks = new JButton("Stive");
		stacks.setActionCommand("STACKS");
		stacks.addActionListener(this);
		stacks.setAlignmentX(0);
		stacks.setMaximumSize(buttonDimension);
		
		JButton stockCurrent5 = new JButton("Istoric procesat");
		stockCurrent5.setActionCommand("PROCESSED_HISTORY");
		stockCurrent5.addActionListener(this);
		stockCurrent5.setAlignmentX(0);
		stockCurrent5.setMaximumSize(buttonDimension);
		
		JButton mprocessed = new JButton("Marcheaza procesat");
		mprocessed.setActionCommand("MARK_PROCESSED");
		mprocessed.addActionListener(this);
		mprocessed.setAlignmentX(0);
		mprocessed.setMaximumSize(buttonDimension);
		
		JButton cutDiagram = new JButton("Diagrama taiere");
		cutDiagram.setActionCommand("SEE_CUT_DIAGRAM");
		cutDiagram.addActionListener(this);
		cutDiagram.setAlignmentX(0);
		cutDiagram.setMaximumSize(buttonDimension);
		
		JButton optionCutDiagram = new JButton("Optiuni taiere bustean");
		optionCutDiagram.setActionCommand("SEE_CUT_OPTION_DIAGRAM");
		optionCutDiagram.addActionListener(this);
		optionCutDiagram.setAlignmentX(0);
		optionCutDiagram.setMaximumSize(buttonDimension);
		
		JButton settings = new JButton("Setari");
		settings.setActionCommand("SETTINGS");
		settings.addActionListener(this);
		settings.setAlignmentX(0);
		settings.setMaximumSize(buttonDimension);
		
		JButton cut = new JButton("Simulare taiere");
		cut.setActionCommand("CUT_SIMULATION");
		cut.addActionListener(this);
		cut.setAlignmentX(0);
		cut.setMaximumSize(buttonDimension);
		
		JButton cutPlan = new JButton("Plan taiere");
		cutPlan.setActionCommand("CUT_PLAN");
		cutPlan.addActionListener(this);
		cutPlan.setAlignmentX(0);
		cutPlan.setMaximumSize(buttonDimension);
		
		JButton cutPlanHistory = new JButton("Istoric plan taieri");
		cutPlanHistory.setActionCommand("HISTORY_CUT_PLAN");
		cutPlanHistory.addActionListener(this);
		cutPlanHistory.setAlignmentX(0);
		cutPlanHistory.setMaximumSize(buttonDimension);
		
		JButton logout = new JButton("Iesire");
		logout.setActionCommand("LOGOUT");
		logout.addActionListener(GUIUtil.main);
		logout.setAlignmentX(0);
		logout.setMaximumSize(buttonDimension);
		
		JButton products = new JButton("Produse");
		products.setActionCommand("PRODUCTS");
		products.addActionListener(this);
		products.setAlignmentX(0);
		products.setMaximumSize(buttonDimension);
		
		JButton users = new JButton("Utilizatori");
		users.setActionCommand("USERS");
		users.addActionListener(this);
		users.setAlignmentX(0);
		users.setMaximumSize(buttonDimension);

		
		if(rights == 0 || rights == 1) {
			add(receptie);
			add(Box.createVerticalStrut(3));
			add(stockCurrent);
			add(Box.createVerticalStrut(3));
			add(stockCurrent2);
			add(Box.createVerticalStrut(3));
			add(stockCurrent3);
			add(Box.createVerticalStrut(3));
			add(stockCurrent4);
			add(Box.createVerticalStrut(3));
			if(rights == 0) {
				
				add(stockCurrent5);
				add(Box.createVerticalStrut(3));
			}
			
			add(idplates);
			add(Box.createVerticalStrut(3));
			add(stacks);
			add(Box.createVerticalStrut(3));
		}
if(rights == 0) {
	add(settings);
	add(Box.createVerticalStrut(3));
	add(products);
	add(Box.createVerticalStrut(3));
	add(cutPlan);
	add(Box.createVerticalStrut(3));
	add(cut);
	add(Box.createVerticalStrut(3));
	add(cutPlanHistory);
	add(Box.createVerticalStrut(3));
	add(users);
	add(Box.createVerticalStrut(3));
		}
		if(rights == 0 || rights == 2) {
			add(mprocessed);
			add(Box.createVerticalStrut(3));
			add(cutDiagram);
			add(Box.createVerticalStrut(3));
			add(optionCutDiagram);
			add(Box.createVerticalStrut(3));
			
		}

		add(logout);
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		MainContainer parent = GUIUtil.container;
		String command = e.getActionCommand();
		if(command == null) {
			return;
		}
		else if(command.equals("RECEPTIE")) {
			if(!parent.isFrameSet(GUIUtil.RECEPTIE_KEY))
			{
				ReceiceFrame frame = new ReceiceFrame();
				parent.addFrame(frame, GUIUtil.RECEPTIE_KEY);
			} 
		}
		else if(command.equals("STOCK")) {
			if(!parent.isFrameSet(GUIUtil.STOCK_KEY))
			{
				CurrentStockFrame frame = new CurrentStockFrame();
				parent.addFrame(frame, GUIUtil.STOCK_KEY);
			} 
		}
		else if(command.equals("STACK_STOCK")) {
			if(!parent.isFrameSet(GUIUtil.STACKS_STOCKS_KEY))
			{
				CurrentStackStockFrame frame = new CurrentStackStockFrame();
				parent.addFrame(frame, GUIUtil.STACKS_STOCKS_KEY);
			} 
		}
		else if(command.equals("TIP_STOCK")) {
			if(!parent.isFrameSet(GUIUtil.TYPE_STOCKS_KEY))
			{
				CurrentTypeStockFrame frame = new CurrentTypeStockFrame();
				parent.addFrame(frame, GUIUtil.TYPE_STOCKS_KEY);
			} 
		}
		else if(command.equals("CLASS_STOCK")) {
			if(!parent.isFrameSet(GUIUtil.CLASS_STOCKS_KEY))
			{
				CurrentClassStockFrame frame = new CurrentClassStockFrame();
				parent.addFrame(frame, GUIUtil.CLASS_STOCKS_KEY);
			} 
		}
		else if(command.equals("PROCESSED_HISTORY")) {
			if(!parent.isFrameSet(GUIUtil.PROCESSED_HISTORY_KEY))
			{
				ProcessedHistoryFrame frame = new ProcessedHistoryFrame();
				parent.addFrame(frame, GUIUtil.PROCESSED_HISTORY_KEY);
			} 
		}
		else if(command.equals("IDPLATES")) {
			if(!parent.isFrameSet(GUIUtil.IDPLATE_KEY))
			{
				IDPlateFrame frame = new IDPlateFrame();
				parent.addFrame(frame, GUIUtil.IDPLATE_KEY);
			} 
		}
		else if(command.equals("STACKS")) {
			if(!parent.isFrameSet(GUIUtil.STACKS_KEY))
			{
				LumberStackFrame frame = new LumberStackFrame();
				parent.addFrame(frame, GUIUtil.STACKS_KEY);
			} 
		}
		else if(command.equals("SETTINGS")) {
			if(!parent.isFrameSet(GUIUtil.SETTINGS_KEY))
			{
				SettingsFrame frame = new SettingsFrame();
				parent.addFrame(frame, GUIUtil.SETTINGS_KEY);
			} 
		}
		else if(command.equals("CUT_SIMULATION")) {
			if(!parent.isFrameSet(GUIUtil.CUT_SIMULATION_KEY))
			{
				CutSimulationFrame frame = new CutSimulationFrame();
				parent.addFrame(frame, GUIUtil.CUT_SIMULATION_KEY);
			} 
		}
		else if(command.equals("MARK_PROCESSED")) {
			List<IDPlate> plates = IDPlateDAO.getUsedPlates();
			//plates.add(0, null);
			//optPlates = new JComboBox<IDPlate>(plates.toArray(new IDPlate[0]));
			optPlates = new SuggestionJComboBox<IDPlate>(plates.toArray(new IDPlate[0]));
			optPlates.setPreferredSize(new Dimension(100, optPlates.getPreferredSize().height));
			JPanel markPanel = new JPanel();
			markPanel.setLayout(new BoxLayout(markPanel, BoxLayout.Y_AXIS));
			JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			panel.add(createLabel("Placuta:"));
			panel.add(optPlates);
			markPanel.add(panel);
			panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			panel.add(createLabel("Mesaj:"));
			messageArea = new JTextArea(5, 20);
			messageArea.setWrapStyleWord(true);
			panel.add(new JScrollPane(messageArea));
			markPanel.add(panel);
			int rasp = JOptionPane.showOptionDialog(GUIUtil.container, markPanel, "Marcare bustean procesat", 
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
			if(rasp == JOptionPane.YES_OPTION) {
				IDPlate plate = (IDPlate)optPlates.getSelectedItem();
				String message = messageArea.getText();
				if(plate == null) {
					JOptionPane.showMessageDialog(GUIUtil.container, "Nu a fost selectat nici o placuta");
					return;
				}
				LumberLogFilterDTO filter = new LumberLogFilterDTO();
				List<Long> platesId = new ArrayList<>();
				platesId.add(plate.getId());
				filter.setIdPlates(platesId);
				List<LumberLogStockEntry> logs = StockDAO.getCurrentLumbersLogs(filter);
				if(logs.size()!=1) {
					//err
					return;
				}
				boolean status = LumberLogDAO.markProcessedLumberLog(logs.get(0).getLumberLog(), message);
				if(!status) {
					JOptionPane.showMessageDialog(GUIUtil.container, "Busteanul nu a fost marcat ca procesat", "Erroare", JOptionPane.ERROR_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(GUIUtil.container, "Busteanul a fost marcat.");	
				}
			}
		}
		else if(command.equals("SEE_CUT_DIAGRAM")) {
			List<IDPlate> plates = IDPlateDAO.getUsedPlates();
			plates.add(0, null);
			JButton see = new JButton("Afisare");
			JButton cancel = new JButton("Anuleaza");
			cancel.setActionCommand("CANCEL_DIALOG");
			cancel.addActionListener(GUIUtil.main);
			JButton print = new JButton("Printeaza");
			JButton[] buttons = new JButton[] {see, print, cancel}; 
			final JComboBox<IDPlate> platesCombo = new JComboBox<IDPlate>(plates.toArray(new IDPlate[0]));
			
see.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					GUITools.closeParentDialog((JComponent)arg0.getSource());
					doDiagramAction(platesCombo, 0);
				}
			});

print.addActionListener(new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		GUITools.closeParentDialog((JComponent)arg0.getSource());
		doDiagramAction(platesCombo, 1);
	}
});
			
			int rasp = JOptionPane.showOptionDialog(GUIUtil.container, platesCombo, "Diagrama taiere bustean", 
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, buttons[2]);
			
		}
		else if(command.equals("CUT_PLAN")) {
			if(!parent.isFrameSet(GUIUtil.CUT_PLAN_KEY))
			{
				CutPlanFrame frame = new CutPlanFrame();
				parent.addFrame(frame, GUIUtil.CUT_PLAN_KEY);
			} 
		}
		else if(command.equals("PRODUCTS")) {
			if(!parent.isFrameSet(GUIUtil.PRODUCT_KEY))
			{
				ProductsFrame frame = new ProductsFrame();
				parent.addFrame(frame, GUIUtil.PRODUCT_KEY);
			} 
		}
		else if(command.equals("HISTORY_CUT_PLAN")) {
			if(!parent.isFrameSet(GUIUtil.HISTORY_CUT_PLAN_KEY))
			{
				CutPlanHistoryFrame frame = new CutPlanHistoryFrame();
				parent.addFrame(frame, GUIUtil.HISTORY_CUT_PLAN_KEY);
			} 
		}
		else if(command.equals("USERS")) {
			if(!parent.isFrameSet(GUIUtil.USERS_KEY))
			{
				UsersFrame frame = new UsersFrame();
				parent.addFrame(frame, GUIUtil.USERS_KEY);
			} 
		}
		else if(command.equals("SEE_CUT_OPTION_DIAGRAM")) {
			CutOptionsTargetPanel.showDialog();
		}
	}
	
	private JLabel createLabel(String text) {
		JLabel label = new JLabel();
		label.setText(text);
		label.setPreferredSize(new Dimension(100, label.getPreferredSize().height));
		return label;
	}
	
	private void doDiagramAction(JComboBox<IDPlate> platesCombo, int type) {
		IDPlate plate = (IDPlate)platesCombo.getSelectedItem();
		if(plate == null) {
			JOptionPane.showMessageDialog(GUIUtil.container, "Nu a fost selectat nici o placuta");
			return;
		}
		GeneralResponse response = CutPlanDAO.getCutDiagram(plate);
		if(response.getCode() == 200) {
			CutDiagram diagram = (CutDiagram)response.getData();
			LumberLogFilterDTO filter = new LumberLogFilterDTO();
			List<Long> platesFilter = new ArrayList<>();
			platesFilter.add(plate.getId());
			filter.setIdPlates(platesFilter);
			List<LumberLog> lumberLogs = StockDAO.getJustCurrentLumbersLogs(filter);
			if(type == 0) {
			new SeeCutDiagramFrame(lumberLogs.get(0), diagram);
			}
			else if(type == 1) {
				CutDiagramPaintPanel painter = new CutDiagramPaintPanel(lumberLogs.get(0), diagram);
				PrinterJob job = PrinterJob.getPrinterJob();
				job.setPrintable(painter);
				if(job.printDialog())
				{
					try
					{
						job.print();
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}

			}
		}
		else {
			JOptionPane.showMessageDialog(GUIUtil.container, response.getMessage());
		}
	}
	
}
