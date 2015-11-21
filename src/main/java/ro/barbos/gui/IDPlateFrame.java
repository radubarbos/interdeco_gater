package ro.barbos.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumn;

import ro.barbos.gater.dao.IDPlateDAO;
import ro.barbos.gater.gui.grid.DefaultGrid;
import ro.barbos.gater.model.IDPlate;
import ro.barbos.gui.renderer.IDPlateRenderer;
import ro.barbos.gui.tablemodel.IDPlatesModel;

public class IDPlateFrame extends GeneralFrame implements ActionListener {

	
	private static final long serialVersionUID = 1L;
	
	IDPlatesModel platesModel;
	DefaultGrid grid;

	JTextField newPlateText = null;

	public IDPlateFrame() {
		setTitle("Placute de intentificare");
		setResizable(true);
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);

		JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEADING));

		JButton but = new JButton("Adauga", new ImageIcon(
				GUITools.getImage("resources/add24.png")));
		but.setVerticalTextPosition(SwingConstants.BOTTOM);
		 but.setHorizontalTextPosition(SwingConstants.CENTER);
		but.setFocusPainted(false);
		but.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		but.setToolTipText("Adauga placuta noua");
		but.setActionCommand("ADD_IDPLATE");
		but.addActionListener(this);
		toolbar.add(but);

		but = new JButton("Sterge", new ImageIcon(
				GUITools.getImage("resources/delete24.png")));
		but.setVerticalTextPosition(SwingConstants.BOTTOM);
		 but.setHorizontalTextPosition(SwingConstants.CENTER);
		but.setFocusPainted(false);
		but.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		but.setToolTipText("Sterge placuta");
		but.setActionCommand("DELETE_IDPLATE");
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

		platesModel = new IDPlatesModel();
		grid = new DefaultGrid(platesModel);

		TableColumn col1 = grid.getTable().getColumnModel().getColumn(0);
		col1.setMinWidth(10);
		col1.setPreferredWidth(20);
		col1.setWidth(20);
		IDPlateRenderer renderer = new IDPlateRenderer(platesModel);
		for (int i = 0; i < grid.getTable().getColumnModel().getColumnCount(); i++) {
			grid.getTable().getColumnModel().getColumn(i).setCellRenderer(renderer);
		}

		getContentPane().add(toolbar, BorderLayout.NORTH);
		getContentPane().add(grid, BorderLayout.CENTER);

		extractIdPlates();
	}

	private void extractIdPlates() {
		platesModel.setIdPlates(IDPlateDAO.getAllPlates());
	}

	public void actionPerformed(ActionEvent eve) {
		String command = eve.getActionCommand();
		if (command.equals("ADD_IDPLATE")) {
			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			JLabel label = new JLabel();
			label.setText("Placuta:");
			label.setPreferredSize(new Dimension(70,
					label.getPreferredSize().height));
			newPlateText = new JTextField(10);
			JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
			row.add(label);
			row.add(newPlateText);
			panel.add(row);
			JButton buttonOk = new JButton("Salveaza");
			buttonOk.setActionCommand("SAVE_ADD");
			buttonOk.addActionListener(this);
			JButton buttonCancel = new JButton("Anuleaza");
			buttonCancel.setActionCommand("CANCEL_ADD");
			buttonCancel.addActionListener(this);
			JOptionPane.showOptionDialog(GUIUtil.container, panel,
					"Adaugare placuta", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, new JButton[] {
							buttonOk, buttonCancel }, buttonCancel);
		} else if (command.equals("CANCEL_ADD")) {
			Window w = SwingUtilities.getWindowAncestor((JButton) eve
					.getSource());
			if (w != null)
				w.dispose();
		} else if (command.equals("SAVE_ADD")) {
			String newPlate = newPlateText.getText().trim();
			if (newPlate.length() == 0) {
				((JLabel) newPlateText.getParent().getComponent(0))
						.setForeground(Color.red);
				return;
			}
			IDPlate plate = IDPlateDAO.create(newPlate);
			if (plate == null) {
				JOptionPane.showMessageDialog(GUIUtil.container,
						"Placuta nu a putut fi creata", "Eroare",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			platesModel.addIdPlate(plate);
			Window w = SwingUtilities.getWindowAncestor((JButton) eve
					.getSource());
			if (w != null)
				w.dispose();
		} else if (command.equals("DELETE_IDPLATE")) {
			int row = grid.getTable().getSelectedRow();
			if (row == -1) {
				JOptionPane.showMessageDialog(GUIUtil.container,
						"Selectati o placuta");
			} else {
				IDPlate idPlate = platesModel.getIDPlate(row);
				if(idPlate == null) {
				JOptionPane.showMessageDialog(GUIUtil.container,
						"Selectati o placuta");
				return;
				}
				int ras = JOptionPane.showConfirmDialog(GUIUtil.container,
						"Doriti sa stergeti placuta: " + idPlate.getLabel(),
						"Confirmati stergere", JOptionPane.YES_NO_OPTION);
				if (ras == JOptionPane.YES_OPTION) {
					boolean deleted = IDPlateDAO.delete(idPlate);
					if (!deleted) {
						JOptionPane
								.showMessageDialog(GUIUtil.container,
										"Placuta nu a fost stearsa",
										"Nu se poate sterge",
										JOptionPane.ERROR_MESSAGE);
					} else {
						platesModel.removeIdPlate(row);
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
				  platesModel.toCsv(theFileToSave);
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

	@Override
	public String getFrameCode() {
		return GUIUtil.IDPLATE_KEY;
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
