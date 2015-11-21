package ro.barbos.gui.stock;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.NumberFormat;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import ro.barbos.gater.dao.StockDAO;
import ro.barbos.gater.dto.TypeStockDTO;
import ro.barbos.gui.ConfigLocalManager;
import ro.barbos.gui.GUITools;
import ro.barbos.gui.GUIUtil;
import ro.barbos.gui.GeneralFrame;
import ro.barbos.gui.renderer.GeneralTableRenderer;
import ro.barbos.gui.tablemodel.TypeStockModel;

public class CurrentTypeStockFrame extends GeneralFrame implements
		ActionListener {
	
	private static final long serialVersionUID = 1L;
	TypeStockModel stockModel;
	JTable stockTable;

	private NumberFormat numberFormatter = NumberFormat.getInstance(ConfigLocalManager.locale);

	public CurrentTypeStockFrame() {
		super();

		setTitle("Stoc current per tip de bustean");
		setResizable(true);
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);

		numberFormatter.setMaximumFractionDigits(2);
		numberFormatter.setMinimumFractionDigits(2);

		JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEADING));

		JButton csvExport = new JButton("Exporta", new ImageIcon(
				GUITools.getImage("resources/csv24.png")));
		csvExport.setToolTipText("Exporta tabelul in fisier csv");
		csvExport.setVerticalTextPosition(SwingConstants.BOTTOM);
		csvExport.setHorizontalTextPosition(SwingConstants.CENTER);
		csvExport.setActionCommand("CSV");
		csvExport.addActionListener(this);
		csvExport.setFocusPainted(false);
		csvExport.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		toolbar.add(csvExport);
		JButton print = new JButton("Print", new ImageIcon(
				GUITools.getImage("resources/printb24.png")));
		 print.setVerticalTextPosition(SwingConstants.BOTTOM);
		 print.setHorizontalTextPosition(SwingConstants.CENTER);
		print.setToolTipText("Printeaza tabel");
		print.setActionCommand("PRINT");
		print.addActionListener(this);
		print.setFocusPainted(false);
		print.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		toolbar.add(print);

		stockModel = new TypeStockModel();
		stockTable = new JTable(stockModel);

		GeneralTableRenderer renderer = new GeneralTableRenderer();
		for (int i = 0; i < stockTable.getColumnModel().getColumnCount(); i++) {
			stockTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
		}

		getContentPane().add(toolbar, BorderLayout.NORTH);
		getContentPane().add(new JScrollPane(stockTable), BorderLayout.CENTER);

		extractStock();
	}

	private void extractStock() {
		List<TypeStockDTO> lumberStockTypeDTO = StockDAO
				.getCurrentStockTypeInfo();
		if (lumberStockTypeDTO != null) {
			stockModel.setLumberTypeInfo(lumberStockTypeDTO);
		}

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

		} else if (command.equals("PRINT")) {
			try {
				stockTable.print();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
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
	}

	@Override
	public String getFrameCode() {
		return GUIUtil.TYPE_STOCKS_KEY;
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
