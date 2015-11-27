package ro.barbos.gui;

import ro.barbos.gater.cutprocessor.CutPlanCalculator;
import ro.barbos.gater.cutprocessor.CutPlanCalculatorListener;
import ro.barbos.gater.cutprocessor.CutPlanSenquence;
import ro.barbos.gater.dao.CutPlanDAO;
import ro.barbos.gater.dto.ProductCutTargetDTO;
import ro.barbos.gater.model.CutPlan;
import ro.barbos.gater.model.GeneralResponse;
import ro.barbos.gater.model.Product;
import ro.barbos.gui.renderer.GeneralTableRenderer;
import ro.barbos.gui.tablemodel.CutPlanFoundInfoModel;
import ro.barbos.gui.tablemodel.CutPlanFoundModel;
import ro.barbos.gui.tablemodel.CutPlanTargetModel;
import ro.barbos.gui.tablemodel.CutPlanTargetRecord;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class CutPlanFrame extends GeneralFrame implements ActionListener, CutPlanCalculatorListener {

	
	private static final long serialVersionUID = 1L;
	
	private CutPlanTargetPanel cutPlan;
	private CutPlanTargetModel cutPlanTargetModel;
	private JTable cutPlanTarget;
	private JTable cutCalculatedTable;
	private JTable cutInfoTable;
	private JSplitPane displayPanel;
	private JPanel cutCalculatedPanel;

	private SaveCutPlanPanel savePanel;
	private CutPlanSettingsPanel settingsPanel = new CutPlanSettingsPanel();

	private List<ProductCutTargetDTO> cutDataInfo;
	private List<CutPlanSenquence> cutDiagrams;
	private Map<Product, Long> pieces;

	public CutPlanFrame() {
		super();

		setTitle("Plan taiere");
		setResizable(true);
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);

		JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEADING));
		JButton save = new JButton(new ImageIcon(GUITools.getImage("resources/save24.png")));
		save.setToolTipText("Save current plan");
		save.setActionCommand("SAVE_PLAN");
		save.addActionListener(this);
		save.setFocusPainted(false);
		save.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		toolbar.add(save);
		JButton edit = new JButton(new ImageIcon(GUITools.getImage("resources/edit24.png")));
		edit.setToolTipText("Target taiere");
		edit.setActionCommand("CUT_TARGET");
		edit.addActionListener(this);
		edit.setFocusPainted(false);
		edit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		toolbar.add(edit);
		JButton print = new JButton(new ImageIcon(GUITools.getImage("resources/printb24.png")));
		print.setToolTipText("Printeaza tabele");
		print.setActionCommand("PRINT");
		print.addActionListener(this);
		print.setFocusPainted(false);
		print.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		toolbar.add(print);
		JButton settings = new JButton(new ImageIcon(GUITools.getImage("resources/settings_a24.png")));
		settings.setToolTipText("Setari");
		settings.setActionCommand("SETTINGS");
		settings.addActionListener(this);
		settings.setFocusPainted(false);
		settings.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		toolbar.add(settings);

		cutPlanTargetModel = new CutPlanTargetModel();
		cutPlanTarget = new JTable(cutPlanTargetModel);

		GeneralTableRenderer renderer = new GeneralTableRenderer();
		for (int i = 0; i < cutPlanTarget.getColumnModel().getColumnCount(); i++) {
			cutPlanTarget.getColumnModel().getColumn(i).setCellRenderer(renderer);
		}

		displayPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		displayPanel.add(new JScrollPane(cutPlanTarget), JSplitPane.TOP);
		cutCalculatedPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		displayPanel.add(new JScrollPane(cutCalculatedPanel), JSplitPane.BOTTOM);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(toolbar, BorderLayout.NORTH);
		getContentPane().add(displayPanel, BorderLayout.CENTER);

	}

	public void actionPerformed(ActionEvent eve) {
		String command = eve.getActionCommand();
		if (command.equals("CUT_TARGET")) {
			JButton buttonOk = new JButton("Calculeaza");
			buttonOk.setActionCommand("SAVE_CUT_TARGET");
			buttonOk.addActionListener(this);
			JButton buttonCancel = new JButton("Anuleaza");
			buttonCancel.setActionCommand("CANCEL_CUT_TARGET");
			buttonCancel.addActionListener(this);
			JButton buttonNew = new JButton("Adauga");
			buttonNew.setActionCommand("NEW_CUT_TARGET");
			buttonNew.addActionListener(this);
			cutPlan = new CutPlanTargetPanel();
			JScrollPane scroll = new JScrollPane(cutPlan);
			scroll.setPreferredSize(new Dimension(cutPlan.getPreferredSize().width + 20, 150));
			JOptionPane.showOptionDialog(GUIUtil.container, scroll, "Tinta plan taiere",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new JButton[] {
							buttonOk, buttonCancel, buttonNew }, buttonCancel);
		} else if (command.equals("CANCEL_CUT_TARGET")) {
			Window w = SwingUtilities.getWindowAncestor((JButton) eve.getSource());
			if (w != null)
				w.dispose();
		} else if (command.equals("NEW_CUT_TARGET")) {
			cutPlan.addNewProduct();
		} else if (command.equals("SAVE_CUT_TARGET")) {
			if (cutPlan.validateData()) {
				List<List<Object>> data = cutPlan.getData();
				Window w = SwingUtilities.getWindowAncestor((JButton) eve.getSource());
				if (w != null)
					w.dispose();
				calculateCutPlan(data);
			}
		} else if (command.equals("PRINT")) {
			JComboBox<String> tables = new JComboBox<String>();
			((DefaultComboBoxModel<String>) tables.getModel())
					.addElement("Tabel tinta plan de taiere");
			((DefaultComboBoxModel<String>) tables.getModel()).addElement("Plan taiere");
			((DefaultComboBoxModel<String>) tables.getModel()).addElement("Informati per produs");
			int rasp = JOptionPane.showConfirmDialog(GUIUtil.container, tables,
					"Alege tabelul de printat", JOptionPane.YES_NO_OPTION);
			if (rasp == JOptionPane.YES_OPTION) {
				String tabel = (String) tables.getSelectedItem();
				JTable tabelToPrint = null;
				if (tabel.equals("Tabel tinta plan de taiere")) {
					tabelToPrint = cutPlanTarget;
				} else if (tabel.equals("Plan taiere")) {
					tabelToPrint = cutCalculatedTable;
				} else if (tabel.equals("Informati per produs")) {
					tabelToPrint = cutInfoTable;
				}
				if (tabelToPrint == null) {
					return;
				}
				try {
					tabelToPrint.print();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		} else if (command.equals("SAVE_PLAN")) {
			if (cutPlanTargetModel == null || cutPlanTargetModel.getRowCount() == 0) {
				JOptionPane.showMessageDialog(GUIUtil.container,
						"Nu exista nici un plan curent de salvat.");
				return;
			}
			JButton buttonOk = new JButton("Salveaza");
			buttonOk.setActionCommand("SAVE_CUT_PLAN_DB");
			buttonOk.addActionListener(this);
			JButton buttonCancel = new JButton("Anuleaza");
			buttonCancel.setActionCommand("CANCEL_CUT_PLAN_DB");
			buttonCancel.addActionListener(this);
			savePanel = new SaveCutPlanPanel();
			JOptionPane.showOptionDialog(GUIUtil.container, savePanel, "Salvare plan",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new JButton[] {
							buttonOk, buttonCancel }, buttonCancel);
		} else if (command.equals("CANCEL_CUT_PLAN_DB")) {
			Window w = SwingUtilities.getWindowAncestor((JButton) eve.getSource());
			if (w != null)
				w.dispose();
		} else if (command.equals("SAVE_CUT_PLAN_DB")) {
			if (!savePanel.validateData()) {
				return;
			}
			CutPlan plan = new CutPlan();
			plan.setName(savePanel.getPlanName());
			plan.setStatus(savePanel.isPlanActive() ? 0 : 1);
			plan.setDate(savePanel.getPlanDate());
			plan.setDescription("");
			plan.setCutDataInfo(cutDataInfo);
			plan.setCutDiagrams(cutDiagrams);
			GeneralResponse response = CutPlanDAO.create(plan);
			if (response.getCode() != 200) {
				JOptionPane.showMessageDialog(GUIUtil.container, response.getMessage());
			} else {
				Window w = SwingUtilities.getWindowAncestor((JButton) eve.getSource());
				if (w != null)
					w.dispose();
				JOptionPane.showMessageDialog(GUIUtil.container, "Planul a fost salvat");
			}
		}
		else if(command.equals("SETTINGS")) {
			JOptionPane.showConfirmDialog(GUIUtil.container, settingsPanel, "Setari", -1);
			
		}
	}

	private void calculateCutPlan(List<List<Object>> data) {
		pieces = new LinkedHashMap<>();
		for (List<Object> productData : data) {
			Product product = (Product) productData.get(0);
			Long piecesEntry = (Long) productData.get(1);
			Long currentPieces = pieces.get(product);
			if (currentPieces == null)
                currentPieces = 0L;
            currentPieces += piecesEntry;
			pieces.put(product, currentPieces);
		}
		List<CutPlanTargetRecord> records = new ArrayList<>();
		Iterator<Map.Entry<Product, Long>> ite = pieces.entrySet().iterator();
		while (ite.hasNext()) {
			Map.Entry<Product, Long> prod = ite.next();
			Product product = prod.getKey();
			CutPlanTargetRecord record = new CutPlanTargetRecord();
			record.setProduct(product);
			long productVolume = product.getLength() * product.getWidth() * product.getThick();
            record.setTargetMCub((productVolume / 1000000000D) * prod.getValue());
			record.setPieces(prod.getValue());
			records.add(record);
		}
		cutPlanTargetModel.setRecords(records);
		cutCalculatedPanel.removeAll();
		cutCalculatedTable = null;
		cutInfoTable = null;
		cutCalculatedPanel.add(new JLabel("Calculare"));
		JProgressBar progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		cutCalculatedPanel.add(progressBar);
		cutCalculatedPanel.revalidate();
		cutCalculatedPanel.repaint();
		new CutPlanCalculator(records, this, settingsPanel.getCutStrategySettings()).execute();
	}

	public void showPlan(List<CutPlanSenquence> plan, List<ProductCutTargetDTO> cutDataInfo) {
		cutCalculatedPanel.removeAll();
		this.cutDataInfo = cutDataInfo;
		this.cutDiagrams = plan;
		JTabbedPane tabPanel = new JTabbedPane();
		JPanel tab1 = new JPanel(new BorderLayout());
		CutPlanFoundModel model = new CutPlanFoundModel();
		final JTable tabel = new JTable(model);
		cutCalculatedTable = tabel;
		tabel.setRowHeight(25);
		GeneralTableRenderer renderer = new GeneralTableRenderer();
		for (int i = 0; i < tabel.getColumnModel().getColumnCount(); i++) {
			tabel.getColumnModel().getColumn(i).setCellRenderer(renderer);
		}
		tab1.add(new JScrollPane(tabel), BorderLayout.CENTER);
		tabPanel.add("Plan", tab1);

		JPanel tab2 = new JPanel();
		tab2.setLayout(new BoxLayout(tab2, BoxLayout.Y_AXIS));
		CutPlanFoundInfoModel model2 = new CutPlanFoundInfoModel();
		JTable tabel2 = new JTable(model2);
		cutInfoTable = tabel2;
		tabel2.setRowHeight(25);
		renderer = new GeneralTableRenderer();
		for (int i = 0; i < tabel2.getColumnModel().getColumnCount(); i++) {
			tabel2.getColumnModel().getColumn(i).setCellRenderer(renderer);
		}
		tab2.add(new JScrollPane(tabel2), BorderLayout.CENTER);
		tabPanel.add("Informatii produs", tab2);
		
		CutPlanTotalStatisticsPanel totalStatistics = new CutPlanTotalStatisticsPanel();
		totalStatistics.showTotalStatistics(plan, cutDataInfo);
		tabPanel.add("Statistici total", totalStatistics);

		cutCalculatedPanel.setLayout(new BorderLayout());
		cutCalculatedPanel.add(new JScrollPane(tabPanel), BorderLayout.CENTER);
		cutCalculatedPanel.revalidate();
		cutCalculatedPanel.repaint();
		model.setRecords(plan);
		model2.setRecords(cutDataInfo);
		final List<Integer> heights = new ArrayList<>();
		for (int row = 0; row < plan.size(); row++) {
	    //tabel.setRowHeight((plan.get(row).getCutDiagram().cutInfo.cutPieces.size() * 20));
		heights.add(plan.get(row).getCutDiagram().cutInfo.cutPieces.size() * 20);
       }
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				for (int row = 0; row < heights.size(); row++) {
			    tabel.setRowHeight(row, heights.get(row).intValue());
		       }
			}
		});
	}

	@Override
	public String getFrameCode() {
		return GUIUtil.CUT_PLAN_KEY;
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
