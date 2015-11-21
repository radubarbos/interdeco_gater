package ro.barbos.gui;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Window;
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
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumn;

import ro.barbos.gater.dao.LumberStackDAO;
import ro.barbos.gater.model.LumberStack;
import ro.barbos.gui.renderer.GeneralTableRenderer;
import ro.barbos.gui.tablemodel.LumberStackModel;

public class LumberStackFrame extends GeneralFrame implements ActionListener {
	
	
	private static final long serialVersionUID = 1L;
	private LumberStackModel stackModel;
	private JTable stackTable;
	
	private AddLumberstackPanel addPanel;
	
	public LumberStackFrame() {
		 super();
			
			setTitle("Stive de busteni");
			setResizable(true);
			setMaximizable(true);
			setIconifiable(true);
			setClosable(true);
			
			 
			 JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEADING));

				JButton but = new JButton("Adauga", new ImageIcon(
						GUITools.getImage("resources/add24.png")));
				but.setToolTipText("Printeaza tabel");
				but.setVerticalTextPosition(SwingConstants.BOTTOM);
				 but.setHorizontalTextPosition(SwingConstants.CENTER);
				but.setFocusPainted(false);
				but.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				but.setToolTipText("Adauga stiva");
				but.setActionCommand("ADD_STACK");
				but.addActionListener(this);
				toolbar.add(but);

				but = new JButton("Sterge", new ImageIcon(
						GUITools.getImage("resources/delete24.png")));
				but.setVerticalTextPosition(SwingConstants.BOTTOM);
				 but.setHorizontalTextPosition(SwingConstants.CENTER);
				but.setFocusPainted(false);
				but.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				but.setToolTipText("Sterge stiva");
				but.setActionCommand("DELETE_STACK");
				but.addActionListener(this);
				toolbar.add(but);
				
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
				
				
				stackModel = new LumberStackModel();
		        stackTable = new JTable(stackModel);	
			     
		         TableColumn col1 =stackTable.getColumnModel().getColumn(0);
					col1.setMinWidth(10);
					col1.setPreferredWidth(20);
					col1.setWidth(20);
					GeneralTableRenderer renderer = new GeneralTableRenderer();
					for(int i=0;i<stackTable.getColumnModel().getColumnCount();i++)
					{
						stackTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
					}
				
				getContentPane().add(toolbar, BorderLayout.NORTH);
				getContentPane().add(new JScrollPane(stackTable), BorderLayout.CENTER);
				
				extractLumberStacks();
	}
	
	private void extractLumberStacks() {
		stackModel.setLumberStacks(LumberStackDAO.getAllstack());
	}
	
	public void actionPerformed(ActionEvent eve) {
		String command = eve.getActionCommand();
		if(command.equals("ADD_STACK")) {
			JButton buttonOk = new JButton("Salveaza");
			buttonOk.setActionCommand("SAVE_ADD");
			buttonOk.addActionListener(this);
			JButton buttonCancel = new JButton("Anuleaza");
			buttonCancel.setActionCommand("CANCEL_ADD");
			buttonCancel.addActionListener(this);
			addPanel = new AddLumberstackPanel();
			JOptionPane.showOptionDialog(GUIUtil.container, addPanel, "Adaugare stiva", 
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new JButton[]{buttonOk, buttonCancel}, buttonCancel);
		}
		else if(command.equals("CANCEL_ADD")) {
			Window w = SwingUtilities.getWindowAncestor((JButton)eve.getSource());
			if(w!=null) w.dispose();
		}
		else if(command.equals("SAVE_ADD")) {
			if(addPanel != null) {
				LumberStack lumberStack = addPanel.validateData();
				if(lumberStack == null) {
					return;
				}
				boolean save = LumberStackDAO.create(lumberStack);
				if (!save) {
		    		JOptionPane.showMessageDialog(GUIUtil.container, "Stiva nu poate fi creata", "Nu se poate crea", JOptionPane.ERROR_MESSAGE);
		    	}
				else {
					Window w = SwingUtilities.getWindowAncestor((JButton)eve.getSource());
					if(w!=null) w.dispose();
					stackModel.addLumberStack(lumberStack);
				}
			}
		}
		else if(command.equals("DELETE_STACK")) {
			int row = stackTable.getSelectedRow();
			if(row == -1) {
				JOptionPane.showMessageDialog(GUIUtil.container, "Selectati o stiva");
			} 
			else {
				LumberStack lumberStack = stackModel.getLumberStack(row);
				int ras = JOptionPane.showConfirmDialog(GUIUtil.container, "Doriti sa stergeti stiva: "+lumberStack.getName(), "Confirmati stergere", JOptionPane.YES_NO_OPTION);
			    if(ras == JOptionPane.YES_OPTION) {
			    	boolean deleted = LumberStackDAO.delete(lumberStack);
			    	if(!deleted) {
			    		JOptionPane.showMessageDialog(GUIUtil.container, "Stiva nu se poate sterge daca nu-i goala", "Nu se poate sterge", JOptionPane.ERROR_MESSAGE);
			    	}
			    	else {
			    		stackModel.removeLumberStack(row);
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
				  stackModel.toCsv(theFileToSave);
				}
			}
		}
		else if (command.equals("PRINT")) {
			try {
				stackTable.print();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	
	@Override
	public String getFrameCode() {
		return GUIUtil.STACKS_KEY;
	}

	@Override
	public ImageIcon getFrameIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImageIcon getIconifiedIcon() {
		Image image = GUITools.getImage("/ro/barbos/gui/resources/chartb32.png");
		return new ImageIcon(image);
	}

}
