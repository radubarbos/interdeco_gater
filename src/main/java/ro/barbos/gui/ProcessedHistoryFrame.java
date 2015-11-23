package ro.barbos.gui;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import ro.barbos.gater.dao.StockDAO;
import ro.barbos.gater.data.DataSearchPagination;
import ro.barbos.gater.data.DataSearchResult;
import ro.barbos.gater.gui.grid.DefaultGrid;
import ro.barbos.gater.model.ProcessedLumberLog;
import ro.barbos.gui.renderer.GeneralTableRenderer;
import ro.barbos.gui.tablemodel.ProcessedHistoryModel;

public class ProcessedHistoryFrame extends GeneralFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	ProcessedHistoryModel historyData;
	DefaultGrid grid;
	
	private ProcessedHistoryFilterPanel filterPanel;

    private double volume = 0;
    private int lumberCount = 0;
    ProcessedHistoryGridInfoPanel gridInfoPanel;
	
	public ProcessedHistoryFrame()
	{
		super();
		
		setTitle("Istoric busteni procesati");
		setResizable(true);
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		
		JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEADING));
		
		JButton gridFilter = new JButton("Filtreaza", new ImageIcon(
				GUITools.getImage("resources/gridfilter24.png")));
		gridFilter.setVerticalTextPosition(SwingConstants.BOTTOM);
		gridFilter.setHorizontalTextPosition(SwingConstants.CENTER);
		gridFilter.setToolTipText("Filtrare rezultate");
		gridFilter.setActionCommand("FILTER");
		gridFilter.addActionListener(this);
		gridFilter.setFocusPainted(false);
		gridFilter.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		toolbar.add(gridFilter);

		JButton csvExport = new JButton("Exporta", new ImageIcon(
				GUITools.getImage("resources/csv24.png")));
		csvExport.setVerticalTextPosition(SwingConstants.BOTTOM);
		csvExport.setHorizontalTextPosition(SwingConstants.CENTER);
		csvExport.setToolTipText("Exporta tabelul in fisier csv");
		csvExport.setActionCommand("CSV");
		csvExport.addActionListener(this);
		csvExport.setFocusPainted(false);
		csvExport.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		toolbar.add(csvExport);
		
		JButton print = new JButton("Printeaza", new ImageIcon(
				GUITools.getImage("resources/printb24.png")));
		print.setVerticalTextPosition(SwingConstants.BOTTOM);
		print.setHorizontalTextPosition(SwingConstants.CENTER);
		print.setToolTipText("Printeaza tabel");
		print.setActionCommand("PRINT");
		print.addActionListener(this);
		print.setFocusPainted(false);
		print.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		toolbar.add(print);
			
		 historyData = new ProcessedHistoryModel();
		 grid = new DefaultGrid(historyData);
         JPanel infoPanel = (JPanel)((BorderLayout)grid.getLayout()).getLayoutComponent(BorderLayout.SOUTH);
         infoPanel.removeAll();
         gridInfoPanel = new ProcessedHistoryGridInfoPanel(grid);
         infoPanel.add(gridInfoPanel);
	     
		 getContentPane().add(toolbar, BorderLayout.NORTH);
		 getContentPane().add(grid, BorderLayout.CENTER);
		 
		 extractStock();
	}
	
	private void extractStock() {
		DataSearchPagination pagination = new DataSearchPagination(0, historyData.pageCount);
		DataSearchResult<ProcessedLumberLog> lumbersProcessed = StockDAO.getProcessedHistory(null, pagination);
		if(lumbersProcessed != null) {
			historyData.setHistoryData(lumbersProcessed);
		}
        for(ProcessedLumberLog processedLog: lumbersProcessed.getData()) {
            double lumberVolume = (processedLog.getLumberLog().getVolume()/1000000000L);
            volume += lumberVolume;
            lumberCount++;
        }
        gridInfoPanel.setInfo(volume, lumberCount);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent eve) {
		String command = eve.getActionCommand();
		if(command.equals("ADD")) {
		
		}
		else if(command.equals("FILTER")) {
			JButton buttonOk = new JButton("Filtreaza");
			buttonOk.setActionCommand("DO_FILTER");
			buttonOk.addActionListener(this);
			JButton buttonCancel = new JButton("Anuleaza");
			buttonCancel.setActionCommand("DO_CANCEL_FILTER");
			buttonCancel.addActionListener(this);
			filterPanel = new ProcessedHistoryFilterPanel();
			JOptionPane.showOptionDialog(GUIUtil.container, filterPanel, "Filtru rezultate", 
					JOptionPane.YES_NO_OPTION, JOptionPane.NO_OPTION, null, new JButton[]{buttonOk, buttonCancel}, buttonCancel);
		}
		else if(command.equals("CSV")) {
			JFileChooser chooser = new JFileChooser();
			int option = chooser.showSaveDialog(GUIUtil.container);  
			if(option == JFileChooser.APPROVE_OPTION){  
				if(chooser.getSelectedFile()!=null){  
				  File theFileToSave = chooser.getSelectedFile(); 
				  historyData.toCsv(theFileToSave);
				}
			}
		}
		else if (command.equals("DO_FILTER")) {
			boolean isValid = filterPanel.isFilterValid();
			if(!isValid) {
				return;
			}
			List<Date> dateRange = filterPanel.getDateRange();
			Window w = SwingUtilities.getWindowAncestor((JButton)eve.getSource());
			if(w!=null) w.dispose();
			ProcessedHistoryFilterFrame results = new ProcessedHistoryFilterFrame(dateRange.get(0), dateRange.get(1));
			results.extractStock();
		}
		else if (command.equals("DO_CANCEL_FILTER")) {
			Window w = SwingUtilities.getWindowAncestor((JButton)eve.getSource());
			if(w!=null) w.dispose();
		}
		else if (command.equals("PRINT")) {
			try {
				grid.getTable().print();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	@Override
	public String getFrameCode() {
		return GUIUtil.PROCESSED_HISTORY_KEY;
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
