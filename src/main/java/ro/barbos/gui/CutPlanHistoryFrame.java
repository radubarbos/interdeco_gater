package ro.barbos.gui;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import ro.barbos.gater.dao.CutPlanDAO;
import ro.barbos.gater.model.CutPlan;
import ro.barbos.gater.model.GeneralResponse;
import ro.barbos.gui.renderer.GeneralTableRenderer;
import ro.barbos.gui.tablemodel.CutPlanHistoryModel;

public class CutPlanHistoryFrame extends GeneralFrame implements ActionListener {

	private CutPlanHistoryModel cutPlansModel;
	private JTable cutPlansTable;
	
	public CutPlanHistoryFrame() {
		super();

		setTitle("Istoric plan taiere");
		setResizable(true);
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		
		JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEADING));
		JButton activatePlan = new JButton(new ImageIcon(
				GUITools.getImage("resources/gridadd24.png")));
		activatePlan.setToolTipText("Activeaza plan de taiere");
		activatePlan.setActionCommand("ACTIVATE");
		activatePlan.addActionListener(this);
		activatePlan.setFocusPainted(false);
		activatePlan.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		toolbar.add(activatePlan);
		JButton deactivatePlan = new JButton(new ImageIcon(
				GUITools.getImage("resources/deleteDoc24.png")));
		deactivatePlan.setToolTipText("Deactiveaza plan de taiere");
		deactivatePlan.setActionCommand("DEACTIVATE");
		deactivatePlan.addActionListener(this);
		deactivatePlan.setFocusPainted(false);
		deactivatePlan.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		toolbar.add(deactivatePlan);
		JButton deletePlan = new JButton(new ImageIcon(
				GUITools.getImage("resources/delete24.png")));
		deletePlan.setFocusPainted(false);
		deletePlan.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		deletePlan.setToolTipText("Sterge plan");
		deletePlan.setActionCommand("DELETE_PLAN");
		deletePlan.addActionListener(this);
		toolbar.add(deletePlan);
		JButton csvExport = new JButton(new ImageIcon(
				GUITools.getImage("resources/csv24.png")));
		csvExport.setToolTipText("Exporta tabelul in fisier csv");
		csvExport.setActionCommand("CSV");
		csvExport.addActionListener(this);
		csvExport.setFocusPainted(false);
		csvExport.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		toolbar.add(csvExport);
	 JButton print = new JButton(new ImageIcon(
				GUITools.getImage("resources/printb24.png")));
		print.setToolTipText("Printeaza tabel");
		print.setActionCommand("PRINT");
		print.addActionListener(this);
		print.setFocusPainted(false);
		print.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		toolbar.add(print);
		
		cutPlansModel = new CutPlanHistoryModel();
		cutPlansTable = new JTable(cutPlansModel);
		TableColumn col1 = cutPlansTable.getColumnModel().getColumn(0);
		col1.setMinWidth(10);
		col1.setPreferredWidth(20);
		col1.setWidth(20);
		GeneralTableRenderer renderer = new GeneralTableRenderer();
		for (int i = 0; i < cutPlansTable.getColumnModel().getColumnCount(); i++) {
			cutPlansTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
		}

		
		add(toolbar, BorderLayout.NORTH);
		add(new JScrollPane(cutPlansTable), BorderLayout.CENTER);
		extractData();
	}
	
	private void extractData() {
		cutPlansModel.setRecords(CutPlanDAO.getCutPlans());
	}
	
	public void actionPerformed(ActionEvent eve) {
		String command = eve.getActionCommand();
		if(command.equals("ACTIVATE")) {
			int row = cutPlansTable.getSelectedRow();
			if(row == -1) {
				JOptionPane.showMessageDialog(GUIUtil.container, "Nu s-a selectat nici un plan");
				return;
			}
			CutPlan plan = cutPlansModel.getRecord(row);
			if(plan.getStatus() == 0) {
				JOptionPane.showMessageDialog(GUIUtil.container, "Planul este activ");
			}
			else {
				GeneralResponse response = CutPlanDAO.activatePlan(plan);
				if(response.getCode() != 200) {
					JOptionPane.showMessageDialog(GUIUtil.container, response.getMessage());
				}
				else {
					extractData();
				}
			}
		}
		else if(command.equals("DEACTIVATE")) {
			int row = cutPlansTable.getSelectedRow();
			if(row == -1) {
				JOptionPane.showMessageDialog(GUIUtil.container, "Nu s-a selectat nici un plan");
				return;
			}
			CutPlan plan = cutPlansModel.getRecord(row);
			if(plan.getStatus() == 1) {
				JOptionPane.showMessageDialog(GUIUtil.container, "Planul nu este activ");
			}
			else {
				GeneralResponse response = CutPlanDAO.deactivatePlan(plan);
				if(response.getCode() != 200) {
					JOptionPane.showMessageDialog(GUIUtil.container, response.getMessage());
				}
				else {
					extractData();
				}
			}
		}
		else if(command.equals("DELETE_PLAN")) {
			int row = cutPlansTable.getSelectedRow();
			if(row == -1) {
				JOptionPane.showMessageDialog(GUIUtil.container, "Nu s-a selectat nici un plan");
				return;
			}
			CutPlan plan = cutPlansModel.getRecord(row);
			int rasp = JOptionPane.showConfirmDialog(GUIUtil.container, "Confirmare stergere plan : " + plan.getName(), "Confirmare", JOptionPane.YES_NO_OPTION);
			if(rasp == JOptionPane.YES_OPTION) {
				GeneralResponse response = CutPlanDAO.deletePlan(plan);
				if(response.getCode() != 200) {
					JOptionPane.showMessageDialog(GUIUtil.container, response.getMessage());
				}
				else {
					extractData();
				}
			}
		}
		else if(command.equals("CSV")) {
			JFileChooser chooser = new JFileChooser();
			int option = chooser.showSaveDialog(GUIUtil.container);  
			if(option == JFileChooser.APPROVE_OPTION){  
				if(chooser.getSelectedFile()!=null){  
				  File theFileToSave = chooser.getSelectedFile(); 
				  cutPlansModel.toCsv(theFileToSave);
				}
			}
		}
		else if (command.equals("PRINT")) {
			try {
				cutPlansTable.print();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	@Override
	public String getFrameCode() {
		return GUIUtil.HISTORY_CUT_PLAN_KEY;
	}

	@Override
	public ImageIcon getFrameIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImageIcon getIconifiedIcon() {
		Image image = GUITools
				.getImage("/ro/barbos/gui/resources/chartb32.png");
		return new ImageIcon(image);
	}
}
