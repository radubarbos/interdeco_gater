package ro.barbos.gui;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import ro.barbos.gater.dao.StockDAO;
import ro.barbos.gater.data.DataSearchResult;
import ro.barbos.gater.dto.ProcessedLumberLogFilterDTO;
import ro.barbos.gater.gui.grid.DefaultGrid;
import ro.barbos.gater.model.ProcessedLumberLog;
import ro.barbos.gui.tablemodel.ProcessedHistoryModel;

public class ProcessedHistoryFilterFrame extends JInternalFrame implements ActionListener {

	ProcessedHistoryModel historyData;
    ProcessedHistoryGridInfoPanel gridInfoPanel;

    DefaultGrid grid;
	
	private Date startDate;
	private Date endDate;
	
	private double volume = 0;
    private int lumberCount = 0;
	
	public ProcessedHistoryFilterFrame(Date startDate, Date endDate) {
        super();
		this.startDate = startDate;
		this.endDate = endDate;
		setTitle("Istoric busteni procesati din data de " + ConfigLocalManager.format.format(startDate) + " pana in " + ConfigLocalManager.format.format(endDate));
		setResizable(true);
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		
		JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEADING));
		

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
		 
		 setLocation(0, 0);
		 setSize(GUIUtil.container.getSize());
		 
		 GUIUtil.container.add(this, 100000);
		 setVisible(true);
	}
	
	public void extractStock() {
		ProcessedLumberLogFilterDTO filter = new ProcessedLumberLogFilterDTO();
		filter.setStartDate(startDate);
		filter.setEndDate(endDate);
		DataSearchResult<ProcessedLumberLog> lumbersProcessed = StockDAO.getProcessedHistory(filter, null);
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
		if(command.equals("CSV")) {
			JFileChooser chooser = new JFileChooser();
			int option = chooser.showSaveDialog(GUIUtil.container);  
			if(option == JFileChooser.APPROVE_OPTION){  
				if(chooser.getSelectedFile()!=null){  
				  File theFileToSave = chooser.getSelectedFile(); 
				  historyData.toCsv(theFileToSave);
				}
			}
		}
		else if (command.equals("PRINT")) {
			try {
				grid.getTable().print();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
