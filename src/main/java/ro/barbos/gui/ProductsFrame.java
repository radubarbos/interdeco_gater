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

import ro.barbos.gater.dao.ProductDAO;
import ro.barbos.gater.model.Product;
import ro.barbos.gui.renderer.GeneralTableRenderer;
import ro.barbos.gui.tablemodel.ProductsModel;

public class ProductsFrame extends GeneralFrame implements ActionListener {

	
	private static final long serialVersionUID = 1L;
	ProductsModel productsModel;
	JTable productsTable;

	private AddProductPanel addProductPanel;

	public ProductsFrame() {
		super();

		setTitle("Produse");
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
		but.setToolTipText("Adauga produs nou");
		but.setActionCommand("ADD_PRODUCT");
		but.addActionListener(this);
		toolbar.add(but);

		but = new JButton("Sterge", new ImageIcon(
				GUITools.getImage("resources/delete24.png")));
		but.setVerticalTextPosition(SwingConstants.BOTTOM);
		 but.setHorizontalTextPosition(SwingConstants.CENTER);
		but.setFocusPainted(false);
		but.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		but.setToolTipText("Sterge produs");
		but.setActionCommand("DELETE_PRODUS");
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

		productsModel = new ProductsModel();
		productsTable = new JTable(productsModel);

		TableColumn col1 = productsTable.getColumnModel().getColumn(0);
		col1.setMinWidth(10);
		col1.setPreferredWidth(20);
		col1.setWidth(20);
		GeneralTableRenderer renderer = new GeneralTableRenderer();
		for (int i = 0; i < productsTable.getColumnModel().getColumnCount(); i++) {
			productsTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
		}

		getContentPane().add(toolbar, BorderLayout.NORTH);
		getContentPane().add(new JScrollPane(productsTable), BorderLayout.CENTER);

		extractProducts();
	}

	private void extractProducts() {
		productsModel.setProducts(ProductDAO.getProducts());
	}

	public void actionPerformed(ActionEvent eve) {
		String command = eve.getActionCommand();
		if (command.equals("ADD_PRODUCT")) {
			addProductPanel = new AddProductPanel();
			JButton buttonOk = new JButton("Salveaza");
			buttonOk.setActionCommand("SAVE_ADD");
			buttonOk.addActionListener(this);
			JButton buttonCancel = new JButton("Anuleaza");
			buttonCancel.setActionCommand("CANCEL_ADD");
			buttonCancel.addActionListener(this);
			JOptionPane.showOptionDialog(GUIUtil.container, addProductPanel,
					"Adaugare produs", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, new JButton[] {
							buttonOk, buttonCancel }, buttonCancel);
		} else if (command.equals("CANCEL_ADD")) {
			Window w = SwingUtilities.getWindowAncestor((JButton) eve
					.getSource());
			if (w != null)
				w.dispose();
		} else if (command.equals("SAVE_ADD")) {
			Product product = addProductPanel.validateData();
			if(product != null) {
				product = ProductDAO.create(product);
				if (product == null) {
					JOptionPane.showMessageDialog(GUIUtil.container,
							"Produsul nu a putut fi salvat", "Eroare",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				productsModel.addProduct(product);
				Window w = SwingUtilities.getWindowAncestor((JButton) eve
						.getSource());
				if (w != null)
					w.dispose();
			} 
		} else if (command.equals("DELETE_PRODUS")) {
			int row = productsTable.getSelectedRow();
			if (row == -1) {
				JOptionPane.showMessageDialog(GUIUtil.container,
						"Selectati un produs");
			} else {
				Product product = productsModel.getProduct(row);
				int ras = JOptionPane.showConfirmDialog(GUIUtil.container,
						"Doriti sa stergeti produsul: " + product.getName(),
						"Confirmati stergere", JOptionPane.YES_NO_OPTION);
				if (ras == JOptionPane.YES_OPTION) {
					boolean deleted = ProductDAO.delete(product);
					if (!deleted) {
						JOptionPane
								.showMessageDialog(GUIUtil.container,
										"Produsul nu a fost stears",
										"Nu se poate sterge",
										JOptionPane.ERROR_MESSAGE);
					} else {
						productsModel.deleteProduct(row);
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
				  productsModel.toCsv(theFileToSave);
				}
			}
		}
		else if (command.equals("PRINT")) {
			try {
				productsTable.print();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	@Override
	public String getFrameCode() {
		return GUIUtil.PRODUCT_KEY;
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
