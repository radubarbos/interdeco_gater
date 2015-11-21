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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import ro.barbos.gater.dao.StockDAO;
import ro.barbos.gater.data.DataSearchResult;
import ro.barbos.gater.dto.ProcessedLumberLogFilterDTO;
import ro.barbos.gater.model.ProcessedLumberLog;
import ro.barbos.gui.renderer.GeneralTableRenderer;
import ro.barbos.gui.tablemodel.ProcessedHistoryModel;

public class ProcessedHistoryFilterFrame extends JInternalFrame implements ActionListener {

	private SimpleDateFormat format = new SimpleDateFormat("dd, MM yyyy");
	private NumberFormat numberFormatter = NumberFormat.getInstance(new Locale("ro"));
	
	JTable historyTable;
	ProcessedHistoryModel historyData;
	
	private Date startDate;
	private Date endDate;
	
	private double volume = 0;
	private JLabel volumeLabel = new JLabel();
	
	public ProcessedHistoryFilterFrame(Date startDate, Date endDate) {
super();
		this.startDate = startDate;
		this.endDate = endDate;
		setTitle("Istoric busteni procesati din data de " + format.format(startDate) + " pana in " + format.format(endDate));
		setResizable(true);
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		
		numberFormatter.setMaximumFractionDigits(2);
		numberFormatter.setMinimumFractionDigits(2);
		
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
		
		toolbar.add(volumeLabel);
			
		 historyData = new ProcessedHistoryModel();
	     historyTable = new JTable(historyData);	
	     
	 
			GeneralTableRenderer renderer = new GeneralTableRenderer();
			for(int i=0;i<historyTable.getColumnModel().getColumnCount();i++)
			{
				historyTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
			}
	     
		 getContentPane().add(toolbar, BorderLayout.NORTH);
		 getContentPane().add(new JScrollPane(historyTable), BorderLayout.CENTER);
		 
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
		}
		volumeLabel.setText("Total cubaj: " + numberFormatter.format(volume) + " metri cubi");
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
				historyTable.print();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
