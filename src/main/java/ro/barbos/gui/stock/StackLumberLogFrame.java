package ro.barbos.gui.stock;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import ro.barbos.gater.dao.StockDAO;
import ro.barbos.gater.dto.LumberLogFilterDTO;
import ro.barbos.gater.dto.LumberStackInfoDTO;
import ro.barbos.gater.model.LumberLogStockEntry;
import ro.barbos.gui.GUITools;
import ro.barbos.gui.GUIUtil;
import ro.barbos.gui.GeneralFrame;
import ro.barbos.gui.renderer.GeneralTableRenderer;
import ro.barbos.gui.tablemodel.StockModel;

public class StackLumberLogFrame extends GeneralFrame implements ActionListener {

	StockModel stockModel;
	JTable stockTable;

	public StackLumberLogFrame(LumberStackInfoDTO stackDTO) {
		super();
		if (GUIUtil.container.isFrameSet(GUIUtil.STACKS_LUMBERS_KEY)) {
			GUIUtil.container.closeFrame(GUIUtil.STACKS_LUMBERS_KEY);
		}

		setTitle("Busteni din stiva " + stackDTO.getStack().getName());
		setResizable(true);
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);

		JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEADING));

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
		
		 stockModel = new StockModel();
         stockTable = new JTable(stockModel);	
	     
	    
			GeneralTableRenderer renderer = new GeneralTableRenderer();
			for(int i=0;i<stockTable.getColumnModel().getColumnCount();i++)
			{
				stockTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
			}

		getContentPane().add(toolbar, BorderLayout.NORTH);
		getContentPane().add(new JScrollPane(stockTable), BorderLayout.CENTER);

		GUIUtil.container.addFrame(this, GUIUtil.STACKS_LUMBERS_KEY);
		extractStock(stackDTO.getStack().getId());
	}
	
	private void extractStock(Long stackId) {
		LumberLogFilterDTO filter = new LumberLogFilterDTO();
		List<Long> stacks = new ArrayList<>();
		stacks.add(stackId);
		filter.setStacks(stacks);
		List<LumberLogStockEntry> lumberLogs = StockDAO.getCurrentLumbersLogs(filter);
		stockModel.setLumberLogs(lumberLogs);
	}

	public void actionPerformed(ActionEvent eve) {
		String command = eve.getActionCommand();
		if (command.equals("MARK_PROCESSED")) {

		} else if (command.equals("MARK_DELETED")) {

		} else if (command.equals("CANCEL_ADD_LOG")) {
			Window w = SwingUtilities.getWindowAncestor((JButton) eve
					.getSource());
			if (w != null)
				w.dispose();
		} else if (command.equals("SAVE_ADD_LOG")) {

		} 
		else if(command.equals("CSV")) {
			JFileChooser chooser = new JFileChooser();
			int option = chooser.showSaveDialog(GUIUtil.container);  
			if(option == JFileChooser.APPROVE_OPTION){  
				if(chooser.getSelectedFile()!=null){  
				  File theFileToSave = chooser.getSelectedFile(); 
				  stockModel.toCsv(theFileToSave);
				}
			}
		}
		else if (command.equals("PRINT")) {
			try {
				stockTable.print();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

	}

	@Override
	public String getFrameCode() {
		return GUIUtil.STACKS_LUMBERS_KEY;
	}

	@Override
	public ImageIcon getFrameIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImageIcon getIconifiedIcon() {
		Image image = GUITools.getImage("/ro/barbos/gui/resources/chart32.png");
		return new ImageIcon(image);
	}

}
