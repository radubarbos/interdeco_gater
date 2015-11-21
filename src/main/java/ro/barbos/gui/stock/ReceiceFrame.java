package ro.barbos.gui.stock;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumn;

import ro.barbos.gater.dao.LumberLogDAO;
import ro.barbos.gater.data.LumberLogUtil;
import ro.barbos.gater.model.LumberLog;
import ro.barbos.gater.model.LumberLogEntry;
import ro.barbos.gui.AddLogLumberPanel;
import ro.barbos.gui.ConfigLocalManager;
import ro.barbos.gui.GUITools;
import ro.barbos.gui.GUIUtil;
import ro.barbos.gui.GeneralFrame;
import ro.barbos.gui.renderer.ReceiveTableRenderer;
import ro.barbos.gui.tablemodel.ReceiveModel;

public class ReceiceFrame extends GeneralFrame implements ActionListener {
	
	public static final long serialVersionUID = 1L;
	
	private JPanel north = null;
	private Date now = new Date();
	private SimpleDateFormat format = new SimpleDateFormat("dd. MM yyyy");
	private NumberFormat numberFormatter = NumberFormat.getInstance(ConfigLocalManager.locale);
	
	private Double totalCubaj = 0D;
	
	private AddLogLumberPanel addLogLumberPanel = null;
	
	JTable receiveTable;
	ReceiveModel data;
	
	JLabel totalCubajTxt;
	
	LumberLogEntry stockEntry;
	
	public ReceiceFrame()
	{
		super();
		
		stockEntry = new LumberLogEntry();
		stockEntry.setUser(ConfigLocalManager.currentUser);
		
		setTitle("Receptie");
		setResizable(true);
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		
		numberFormatter.setMaximumFractionDigits(2);
		numberFormatter.setMinimumFractionDigits(2);
		
		 north = new JPanel(null);
		 north.setLayout(null);
		 north.setPreferredSize(new Dimension(90, 140));
		 JLabel lnow = new JLabel();
		 lnow.setText("Data: " + format.format(now));
		 lnow.setLocation(20, 10);
		 lnow.setFont(new Font("arial", Font.BOLD, 16));
		 lnow.setSize(lnow.getPreferredSize());
		 north.add(lnow);
		 
		 JLabel lope = new JLabel();
		 lope.setText("Operator:"  + ConfigLocalManager.currentUser.getName());
		 lope.setLocation(500, 10);
		 lope.setFont(new Font("arial", Font.BOLD, 16));
		 lope.setSize(lope.getPreferredSize());
		 north.add(lope);
		 
		 totalCubajTxt = new JLabel();
		 totalCubajTxt.setText("Total cubaj: " + numberFormatter.format(totalCubaj) + " metri cubi");
		 totalCubajTxt.setLocation(20, 40);
		 totalCubajTxt.setFont(new Font("arial", Font.BOLD, 16));
		 totalCubajTxt.setSize(totalCubajTxt.getPreferredSize());
		 north.add(totalCubajTxt);
		 
		 JPanel toolbar = new JPanel();
			
				JButton but = new JButton("Adauga", new ImageIcon(
						GUITools.getImage("resources/add24.png")));
				but.setVerticalTextPosition(SwingConstants.BOTTOM);
				but.setHorizontalTextPosition(SwingConstants.CENTER);
				but.setFocusPainted(false);
				but.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				but.setToolTipText("Adauga bustean");
				but.setActionCommand("ADD");
				but.addActionListener(this);
				toolbar.add(but);

				but = new JButton("Sterge", new ImageIcon(
						GUITools.getImage("resources/delete24.png")));
				but.setVerticalTextPosition(SwingConstants.BOTTOM);
				but.setHorizontalTextPosition(SwingConstants.CENTER);
				but.setFocusPainted(false);
				but.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				but.setToolTipText("Sterge bustean");
				but.setActionCommand("REMOVE");
				but.addActionListener(this);
				toolbar.add(but);
				
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
			
			toolbar.setSize(toolbar.getPreferredSize());
			toolbar.setLocation(20, 70);
			north.add(toolbar);
			
			data = new ReceiveModel();
	     receiveTable = new JTable(data);	
	     
	     TableColumn col1 =receiveTable.getColumnModel().getColumn(0);
			col1.setMinWidth(10);
			col1.setPreferredWidth(20);
			col1.setWidth(20);
			ReceiveTableRenderer renderer = new ReceiveTableRenderer(data);
			for(int i=0;i<receiveTable.getColumnModel().getColumnCount();i++)
			{
				receiveTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
			}
	     
		 getContentPane().setLayout(new BorderLayout());
		 getContentPane().add(north, BorderLayout.NORTH);
		 getContentPane().add(new JScrollPane(receiveTable), BorderLayout.CENTER);
	}
	
	
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent eve) {
		String command = eve.getActionCommand();
		if(command.equals("ADD")) {
			JButton buttonOk = new JButton("Salveaza");
			buttonOk.setActionCommand("SAVE_ADD_LOG");
			buttonOk.addActionListener(this);
			JButton buttonCancel = new JButton("Anuleaza");
			buttonCancel.setActionCommand("CANCEL_ADD_LOG");
			buttonCancel.addActionListener(this);
			addLogLumberPanel = new AddLogLumberPanel(null);
			JScrollPane scrollPane = new JScrollPane(addLogLumberPanel);
			scrollPane.setPreferredSize(new Dimension(addLogLumberPanel.getPreferredSize().width+20, 250));
			JOptionPane.showOptionDialog(GUIUtil.container, scrollPane, "Adaugare bustean", 
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new JButton[]{buttonOk, buttonCancel}, buttonCancel);
		} 
		else if(command.equals("CANCEL_ADD_LOG")) {
			Window w = SwingUtilities.getWindowAncestor((JButton)eve.getSource());
			if(w!=null) w.dispose();
		}
		else if(command.equals("SAVE_ADD_LOG")) {
			if(addLogLumberPanel != null) {
				LumberLog lumberLog = addLogLumberPanel.validateData();
				if(lumberLog == null) {
					return;
				}
				LumberLogUtil.calculateVolume(lumberLog);
				lumberLog.setRealLength(lumberLog.getLength().longValue());
				lumberLog.setRealVolume(lumberLog.getVolume());
				LumberLogUtil.trimAndSetLength(lumberLog);
				LumberLogUtil.calculateVolume(lumberLog);
				LumberLogDAO.saveLumberLog(lumberLog, addLogLumberPanel.getLumberStack(), stockEntry);
				Window w = SwingUtilities.getWindowAncestor((JButton)eve.getSource());
				if(w!=null) w.dispose();
				data.addLumberLog(lumberLog, addLogLumberPanel.getLumberStack());
				addLumberLog(lumberLog);
			}
		}
		else if(command.equals("REMOVE")) {
			int row = receiveTable.getSelectedRow();
			if(row == -1) {
				JOptionPane.showMessageDialog(GUIUtil.container, "Nici un bustean nu a fost selectat");
			} 
			else {
				LumberLog lumberLog = data.getLumberLog(row);
				int ras = JOptionPane.showConfirmDialog(GUIUtil.container, "Doriti sa stergeti busteanul cu placuta: "+lumberLog.getPlate().getLabel(), "Confirmati stergere", JOptionPane.YES_NO_OPTION);
			    if(ras == JOptionPane.YES_OPTION) {
			    	boolean deleted = LumberLogDAO.deleteLumberLog(lumberLog);
			    	if(!deleted) {
			    		JOptionPane.showMessageDialog(GUIUtil.container, "Busteanul nu a fost sters", "Eroare", JOptionPane.ERROR_MESSAGE);
			    	}
			    	else {
			    		removeLumberLog(lumberLog);
			    		data.removeLumberLog(row);
			    	}
			    }
			}
		}
		else if(command.equals("CSV")) {
			JFileChooser chooser = new JFileChooser();
			int option = chooser.showSaveDialog(GUIUtil.container);  
			if(option == JFileChooser.APPROVE_OPTION){  
				if(chooser.getSelectedFile()!=null){  
				  File theFileToSave = chooser.getSelectedFile(); 
				  data.toCsv(theFileToSave);
				}
			}
		}
	}

    private void addLumberLog(LumberLog lumberLog) {
    	double lumberLogM3 = lumberLog.getVolume()/1000000000;
    	totalCubaj += lumberLogM3;
    	totalCubajTxt.setText("Total cubaj: " + numberFormatter.format(totalCubaj) + " metri cubi");
    }
    
    private void removeLumberLog(LumberLog lumberLog) {
    	double lumberLogM3 = lumberLog.getVolume()/1000000000;
    	totalCubaj -= lumberLogM3;
    	totalCubajTxt.setText("Total cubaj: " + numberFormatter.format(totalCubaj) + " metri cubi");
    }

	@Override
	public String getFrameCode() {
		return GUIUtil.RECEPTIE_KEY;
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
