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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import ro.barbos.gater.dao.UserDAO;
import ro.barbos.gui.renderer.GeneralTableRenderer;
import ro.barbos.gui.renderer.IDPlateRenderer;
import ro.barbos.gui.tablemodel.IDPlatesModel;
import ro.barbos.gui.tablemodel.UsersModel;

public class UsersFrame extends GeneralFrame implements ActionListener {

	UsersModel usersModel;
	JTable users;
	
	public UsersFrame() {
		setTitle("Utilizatori");
		setResizable(true);
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		
		JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEADING));

		JButton but = new JButton(new ImageIcon(
				GUITools.getImage("resources/add24.png")));
		but.setFocusPainted(false);
		but.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		but.setToolTipText("Adauga utilizator noua");
		but.setActionCommand("ADD_USER");
		but.addActionListener(this);
		toolbar.add(but);

		but = new JButton(new ImageIcon(
				GUITools.getImage("resources/delete24.png")));
		but.setFocusPainted(false);
		but.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		but.setToolTipText("Sterge utilizator");
		but.setActionCommand("DELETE_USER");
		but.addActionListener(this);
		toolbar.add(but);
		
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
		
		usersModel = new UsersModel();
		users = new JTable(usersModel);

		TableColumn col1 = users.getColumnModel().getColumn(0);
		col1.setMinWidth(10);
		col1.setPreferredWidth(20);
		col1.setWidth(20);
		GeneralTableRenderer renderer = new GeneralTableRenderer();
		for (int i = 0; i < users.getColumnModel().getColumnCount(); i++) {
			users.getColumnModel().getColumn(i).setCellRenderer(renderer);
		}

		getContentPane().add(toolbar, BorderLayout.NORTH);
		getContentPane().add(new JScrollPane(users), BorderLayout.CENTER);
		
		extractUsers();
	}
	
	private void extractUsers() {
		usersModel.setRecords(UserDAO.getUsers());
	}
	
	public void actionPerformed(ActionEvent eve) {
		String command = eve.getActionCommand();
		
	   if(command.equals("CSV")) {
			JFileChooser chooser = new JFileChooser();
			int option = chooser.showSaveDialog(GUIUtil.container);  
			if(option == JFileChooser.APPROVE_OPTION){  
				if(chooser.getSelectedFile()!=null){  
				  File theFileToSave = chooser.getSelectedFile(); 
				  usersModel.toCsv(theFileToSave);
				}
			}
		}
		else if (command.equals("PRINT")) {
			try {
				users.print();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	@Override
	public String getFrameCode() {
		return GUIUtil.USERS_KEY;
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
