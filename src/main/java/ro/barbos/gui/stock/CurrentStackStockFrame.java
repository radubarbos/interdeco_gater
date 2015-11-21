package ro.barbos.gui.stock;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ro.barbos.gater.dao.StockDAO;
import ro.barbos.gater.dto.LumberStackInfoDTO;
import ro.barbos.gui.ConfigLocalManager;
import ro.barbos.gui.GUITools;
import ro.barbos.gui.GUIUtil;
import ro.barbos.gui.GeneralFrame;
import ro.barbos.gui.renderer.GeneralTableRenderer;
import ro.barbos.gui.tablemodel.StackStockModel;

public class CurrentStackStockFrame extends GeneralFrame implements ActionListener, ListSelectionListener, MouseListener {

	
	private static final long serialVersionUID = 1L;
	
	JPanel north;
	StackStockModel stockModel;
	JTable stockTable;
	
	private NumberFormat numberFormatter = NumberFormat.getInstance(ConfigLocalManager.locale);
	
	public CurrentStackStockFrame() {
		super();
		
		setTitle("Stoc current per stiva");
		setResizable(true);
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		
		numberFormatter.setMaximumFractionDigits(2);
		numberFormatter.setMinimumFractionDigits(2);
		
		north = new JPanel();
		north.setLayout(null);
		 north.setPreferredSize(new Dimension(70, 50));
		
		 
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
		 JButton print  = new JButton("Print", new ImageIcon(GUITools.getImage("resources/printb24.png")));
		 print.setVerticalTextPosition(SwingConstants.BOTTOM);
		 print.setHorizontalTextPosition(SwingConstants.CENTER);
			print.setToolTipText("Printeaza tabel");
			print.setActionCommand("PRINT");
			print.addActionListener(this);
			print.setFocusPainted(false);
			print.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			toolbar.add(print);

		
			
			toolbar.setSize(toolbar.getPreferredSize());
			toolbar.setLocation(20, 70);
			north.add(toolbar);
		 
		 stockModel = new StackStockModel();
         stockTable = new JTable(stockModel);	
	     stockTable.getSelectionModel().addListSelectionListener(this);
	     stockTable.addMouseListener(this);
	    
			GeneralTableRenderer renderer = new GeneralTableRenderer();
			for(int i=0;i<stockTable.getColumnModel().getColumnCount();i++)
			{
				stockTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
			}
		 
		 getContentPane().setLayout(new BorderLayout());
		 getContentPane().add(toolbar, BorderLayout.NORTH);
		 getContentPane().add(new JScrollPane(stockTable), BorderLayout.CENTER);
		 
		 extractStock();
	}
	
	private void extractStock() {
		List<LumberStackInfoDTO> lumberStacksDTO = StockDAO.getCurrentStackInfo();
		if(lumberStacksDTO != null) {
			stockModel.setLumberStacksInfo(lumberStacksDTO);
		}
		
	}
	
	public void actionPerformed(ActionEvent eve) {
		String command = eve.getActionCommand();
		if(command.equals("MARK_PROCESSED")) {
			
			
		} 
		else if(command.equals("MARK_DELETED")) {
			
		}
		else if(command.equals("CANCEL_ADD_LOG")) {
			Window w = SwingUtilities.getWindowAncestor((JButton)eve.getSource());
			if(w!=null) w.dispose();
		}
		else if(command.equals("SAVE_ADD_LOG")) {
			
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
		else if(command.equals("PRINT"))
		{
			try
			{
				stockTable.print();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}

	}
	
	
	
	/* (non-Javadoc)
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(!e.getValueIsAdjusting()) {
			
		}
	}
	
	

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent eve) {
		if(eve.getClickCount() == 2) {
			int row = stockTable.getSelectedRow();
			if(row != -1) {
				LumberStackInfoDTO stack = stockModel.getLumberStackInfoDTO(row);
				new StackLumberLogFrame(stack);
			}
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getFrameCode() {
		return GUIUtil.STACKS_STOCKS_KEY;
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
