package ro.barbos.gui.supplier;

import ro.barbos.gater.dao.SupplierDAO;
import ro.barbos.gater.model.Supplier;
import ro.barbos.gui.GUITools;
import ro.barbos.gui.GUIUtil;
import ro.barbos.gui.GeneralFrame;
import ro.barbos.gui.renderer.GeneralTableRenderer;
import ro.barbos.gui.tablemodel.supplier.SupplierModel;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by radu on 8/15/2016.
 */
public class SuppliersFrame extends GeneralFrame implements ActionListener {



    private static final long serialVersionUID = 1L;
    SupplierModel dataModel;
    JTable dataTable;

    private AddSupplierPanel addPanel;

    public SuppliersFrame() {
        super();

        setTitle("Furnizori");
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
        but.setToolTipText("Adauga furnizor");
        but.setActionCommand("ADD_SUPPLIER");
        but.addActionListener(this);
        toolbar.add(but);

        but = new JButton("Sterge", new ImageIcon(
                GUITools.getImage("resources/delete24.png")));
        but.setVerticalTextPosition(SwingConstants.BOTTOM);
        but.setHorizontalTextPosition(SwingConstants.CENTER);
        but.setFocusPainted(false);
        but.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        but.setToolTipText("Sterge furnizor");
        but.setActionCommand("DELETE_SUPPLIER");
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

        dataModel = new SupplierModel();
        dataTable = new JTable(dataModel);

        TableColumn col1 = dataTable.getColumnModel().getColumn(0);
        col1.setMinWidth(10);
        col1.setPreferredWidth(20);
        col1.setWidth(20);
        GeneralTableRenderer renderer = new GeneralTableRenderer();
        for (int i = 0; i < dataTable.getColumnModel().getColumnCount(); i++) {
            dataTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        getContentPane().add(toolbar, BorderLayout.NORTH);
        getContentPane().add(new JScrollPane(dataTable), BorderLayout.CENTER);

        extractSuppliers();
    }

    private void extractSuppliers() {
        dataModel.setData(SupplierDAO.getSuppliers());
    }

    public void actionPerformed(ActionEvent eve) {
        String command = eve.getActionCommand();
        if (command.equals("ADD_SUPPLIER")) {
            addPanel = new AddSupplierPanel();
            JButton buttonOk = new JButton("Salveaza");
            buttonOk.setActionCommand("SAVE_ADD");
            buttonOk.addActionListener(this);
            JButton buttonCancel = new JButton("Anuleaza");
            buttonCancel.setActionCommand("CANCEL_ADD");
            buttonCancel.addActionListener(this);
            JOptionPane.showOptionDialog(GUIUtil.container, addPanel,
                    "Adaugare furnizor", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, new JButton[] {
                            buttonOk, buttonCancel }, buttonCancel);
        } else if (command.equals("CANCEL_ADD")) {
            Window w = SwingUtilities.getWindowAncestor((JButton) eve
                    .getSource());
            if (w != null)
                w.dispose();
        } else if (command.equals("SAVE_ADD")) {
            Supplier supplier = addPanel.validateData();
            if(supplier != null) {
                supplier = SupplierDAO.create(supplier);
                if (supplier == null) {
                    JOptionPane.showMessageDialog(GUIUtil.container,
                            "Furnizorul nu a putut fi salvat", "Eroare",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                dataModel.addRecord(supplier);
                Window w = SwingUtilities.getWindowAncestor((JButton) eve
                        .getSource());
                if (w != null)
                    w.dispose();
            }
        } else if (command.equals("DELETE_SUPPLIER")) {
            int row = dataTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(GUIUtil.container,
                        "Selectati un furnizor");
            } else {
                Supplier supplier = dataModel.getRecord(row);
                int ras = JOptionPane.showConfirmDialog(GUIUtil.container,
                        "Doriti sa stergeti furnizorul: " + supplier.getTitle(),
                        "Confirmati stergere", JOptionPane.YES_NO_OPTION);
                if (ras == JOptionPane.YES_OPTION) {
                    boolean deleted = SupplierDAO.delete(supplier);
                    if (!deleted) {
                        JOptionPane
                                .showMessageDialog(GUIUtil.container,
                                        "Furnizorul nu a fost stears",
                                        "Nu se poate sterge",
                                        JOptionPane.ERROR_MESSAGE);
                    } else {
                        dataModel.deleteRecord(row);
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
                    dataModel.toCsv(theFileToSave);
                }
            }
        }
        else if (command.equals("PRINT")) {
            try {
                dataTable.print();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public String getFrameCode() {
        return GUIUtil.SUPPLIERS_KEY;
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
