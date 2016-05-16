package ro.barbos.gui;

import ro.barbos.gater.cutprocessor.CutPlanCalculator;
import ro.barbos.gater.cutprocessor.CutPlanCalculatorListener;
import ro.barbos.gater.cutprocessor.CutPlanSenquence;
import ro.barbos.gater.cutprocessor.CutPlanStatistics;
import ro.barbos.gater.cutprocessor.diagram.CutDiagram;
import ro.barbos.gater.cutprocessor.strategy.CutStrategies;
import ro.barbos.gater.dao.CutPlanDAO;
import ro.barbos.gater.dao.IDPlateDAO;
import ro.barbos.gater.dao.StockDAO;
import ro.barbos.gater.dto.LumberLogFilterDTO;
import ro.barbos.gater.dto.ProductCutTargetDTO;
import ro.barbos.gater.model.*;
import ro.barbos.gui.renderer.GeneralTableRenderer;
import ro.barbos.gui.tablemodel.CutPlanHistoryModel;
import ro.barbos.gui.tablemodel.CutPlanTargetRecord;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterJob;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CutPlanHistoryFrame extends GeneralFrame implements ActionListener, CutPlanCalculatorListener {

	private CutPlanHistoryModel cutPlansModel;
	private JTable cutPlansTable;

    private SeeCutDiagramFrame view;
    private CutPlanSenquence addition;

	
	public CutPlanHistoryFrame() {
		super();

		setTitle("Plan taieri");
		setResizable(true);
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		
		JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEADING));
		JButton activatePlan = new JButton("Activare",new ImageIcon(
				GUITools.getImage("resources/gridadd24.png")));
		activatePlan.setToolTipText("Activeaza plan de taiere");
		activatePlan.setActionCommand("ACTIVATE");
        activatePlan.setVerticalTextPosition(SwingConstants.BOTTOM);
        activatePlan.setHorizontalTextPosition(SwingConstants.CENTER);
		activatePlan.addActionListener(this);
		activatePlan.setFocusPainted(false);
		activatePlan.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		toolbar.add(activatePlan);
		JButton deactivatePlan = new JButton("Dezactivare",new ImageIcon(
				GUITools.getImage("resources/deleteDoc24.png")));
		deactivatePlan.setToolTipText("Deactiveaza plan de taiere");
		deactivatePlan.setActionCommand("DEACTIVATE");
        deactivatePlan.setVerticalTextPosition(SwingConstants.BOTTOM);
        deactivatePlan.setHorizontalTextPosition(SwingConstants.CENTER);
		deactivatePlan.addActionListener(this);
		deactivatePlan.setFocusPainted(false);
		deactivatePlan.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		toolbar.add(deactivatePlan);
		JButton deletePlan = new JButton("Stergere",new ImageIcon(
				GUITools.getImage("resources/delete24.png")));
		deletePlan.setFocusPainted(false);
		deletePlan.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		deletePlan.setToolTipText("Sterge plan");
		deletePlan.setActionCommand("DELETE_PLAN");
        deletePlan.setVerticalTextPosition(SwingConstants.BOTTOM);
        deletePlan.setHorizontalTextPosition(SwingConstants.CENTER);
		deletePlan.addActionListener(this);
		toolbar.add(deletePlan);
        JButton seeCutDiagram = new JButton("Diagrama",new ImageIcon(
                GUITools.getImage("resources/rep24.png")));
        seeCutDiagram.setFocusPainted(false);
        seeCutDiagram.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        seeCutDiagram.setToolTipText("Diagrama setata pt busteni");
        seeCutDiagram.setActionCommand("SEE_CUT_DIAGRAM");
        seeCutDiagram.setVerticalTextPosition(SwingConstants.BOTTOM);
        seeCutDiagram.setHorizontalTextPosition(SwingConstants.CENTER);
        seeCutDiagram.addActionListener(this);
        toolbar.add(seeCutDiagram);
        JButton detailsCutDiagram = new JButton("Detalii",new ImageIcon(
                GUITools.getImage("resources/edit24.png")));
        detailsCutDiagram.setFocusPainted(false);
        detailsCutDiagram.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        detailsCutDiagram.setToolTipText("Detalii plan taiere");
        detailsCutDiagram.setActionCommand("SEE_CUT_PLAN_DETAILS");
        detailsCutDiagram.setVerticalTextPosition(SwingConstants.BOTTOM);
        detailsCutDiagram.setHorizontalTextPosition(SwingConstants.CENTER);
        detailsCutDiagram.addActionListener(this);
        toolbar.add(detailsCutDiagram);
        JButton addLumberLog = new JButton("Adauda bustean",new ImageIcon(
                GUITools.getImage("resources/add24.png")));
        addLumberLog.setFocusPainted(false);
        addLumberLog.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addLumberLog.setToolTipText("Adauga bustean");
        addLumberLog.setActionCommand("ADD_LUMBERLOG");
        addLumberLog.setVerticalTextPosition(SwingConstants.BOTTOM);
        addLumberLog.setHorizontalTextPosition(SwingConstants.CENTER);
        addLumberLog.addActionListener(this);
        toolbar.add(addLumberLog);
		JButton csvExport = new JButton("Export",new ImageIcon(
				GUITools.getImage("resources/csv24.png")));
		csvExport.setToolTipText("Exporta tabelul in fisier csv");
		csvExport.setActionCommand("CSV");
        csvExport.setVerticalTextPosition(SwingConstants.BOTTOM);
        csvExport.setHorizontalTextPosition(SwingConstants.CENTER);
		csvExport.addActionListener(this);
		csvExport.setFocusPainted(false);
		csvExport.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		toolbar.add(csvExport);
	   JButton print = new JButton("Printeaza",new ImageIcon(
				GUITools.getImage("resources/printb24.png")));
		print.setToolTipText("Printeaza tabel");
		print.setActionCommand("PRINT");
        print.setVerticalTextPosition(SwingConstants.BOTTOM);
        print.setHorizontalTextPosition(SwingConstants.CENTER);
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
		} else if(command.equals("SEE_CUT_PLAN_DETAILS")) {
            int row = cutPlansTable.getSelectedRow();
            if(row == -1) {
                JOptionPane.showMessageDialog(GUIUtil.container, "Nu s-a selectat nici un plan");
                return;
            }
            CutPlan plan = cutPlansModel.getRecord(row);
            plan = CutPlanDAO.getCutPlan(plan.getId());
            CutPlanFrame detailsFrame = new CutPlanFrame();
            List<CutPlanTargetRecord> records = new ArrayList<>();
            for(ProductCutTargetDTO productCutTargetDTO : plan.getCutDataInfo()){
                CutPlanTargetRecord targetProductRecord = new CutPlanTargetRecord();
                targetProductRecord.setTargetMCub(productCutTargetDTO.getTargetVolume());
                targetProductRecord.setPieces(productCutTargetDTO.getTargetPieces());
                Product product = new Product();
                product.setName(productCutTargetDTO.getProduct());
                targetProductRecord.setProduct(product);
                records.add(targetProductRecord);
            }
            detailsFrame.setTargetPlanRecords(records);
            GUIUtil.container.addFrame(detailsFrame, GUIUtil.CUT_PLAN_KEY);
            detailsFrame.showPlan(plan.getCutDiagrams(), plan.getCutDataInfo());
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

        } else if(command.equals("ADD_LUMBERLOG")) {
           final CutPlan plan = CutPlanDAO.getActiveCutPlan();
           if(plan == null) {
               JOptionPane.showMessageDialog(GUIUtil.container, "Nu exista nici un plan activ la care sa se adauge busteni.");
               return;
           }
            if(plan.getCompleted()>=1) {
                JOptionPane.showMessageDialog(GUIUtil.container, "Planul este complect.");
                return;
            }
            List<IDPlate> plates = IDPlateDAO.getUsedPlates();
            plates.add(0, null);
            JButton see = new JButton("Adauga");
            JButton cancel = new JButton("Anuleaza");
            cancel.setActionCommand("CANCEL_DIALOG");
            cancel.addActionListener(GUIUtil.main);
            JButton[] buttons = new JButton[] {see, cancel};
            final JComboBox<IDPlate> platesCombo = new JComboBox<IDPlate>(plates.toArray(new IDPlate[0]));

            see.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent arg0) {
                    GUITools.closeParentDialog((JComponent)arg0.getSource());
                    cutLumberLogForTheCurrentActivePlan((IDPlate)platesCombo.getSelectedItem(), plan);
                }
            });

            JOptionPane.showOptionDialog(GUIUtil.container, platesCombo, "Selecteaza bustean",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, buttons[1]);
        } else if(command.equals("ADDTOPLAN")) {
            if(view!=null) {
                view.dispose();
            }
            boolean status = CutPlanDAO.addLumberLog(addition, CutPlanDAO.getActiveCutPlan().getId());
            if(status) {
                JOptionPane.showMessageDialog(GUIUtil.container, "Sa atasat busteanul.");
                return;
            }
            else {
                JOptionPane.showMessageDialog(GUIUtil.container, "Eroare la atasarea buseanului.");
                return;
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
                new SeeCutDiagramFrame(lumberLogs.get(0), diagram, null);
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

    private void cutLumberLogForTheCurrentActivePlan(IDPlate plate, CutPlan cutPlan) {
        if(plate == null) {
            return;
        }
        LumberLogFilterDTO filter = new LumberLogFilterDTO();
        List<Long> platesFilter = new ArrayList<>();
        platesFilter.add(plate.getId());
        filter.setIdPlates(platesFilter);
        List<LumberLog> lumberLogs = StockDAO.getJustCurrentLumbersLogs(filter);
        if(lumberLogs.isEmpty()) {
            JOptionPane.showMessageDialog(GUIUtil.container, "Nu exista bustean cu placuta selectata.");
            return;
        }
        LumberLog lumberLog = lumberLogs.get(0);
        if(lumberLog.getCutPlanId() != null && (lumberLog.getCutPlanId() == cutPlan.getId().intValue())) {
            JOptionPane.showMessageDialog(GUIUtil.container, "Busteanul apartine planului deja.");
            return;
        }
        List<CutPlanTargetRecord> remaining = CutPlanDAO.getRemaining(cutPlan.getId());
        if(remaining.isEmpty()) {
            JOptionPane.showMessageDialog(GUIUtil.container, "Planul nu mai are produse neacoperite.");
            return;
        }
        new CutPlanCalculator(remaining, this, CutStrategies.getActiveStrategies()).execute();
    }

    public void showPlan(List<CutPlanSenquence> plan, List<ProductCutTargetDTO> cutDataInfo) {
        addition = plan.get(0);
        view = new SeeCutDiagramFrame(addition.getLumberLog(), addition.getCutDiagram(), this);
    }

    @Override
    public void showStatistics(CutPlanStatistics statistics) {

    }
}
