package ro.barbos.gui.production;

/**
 * Created by radu on 8/5/2016.
 */


import ro.barbos.gater.dao.MachineDAO;
import ro.barbos.gater.dao.production.ProductionMachineDAO;
import ro.barbos.gater.dto.production.ProductionProductPalletDTO;
import ro.barbos.gater.model.Machine;
import ro.barbos.gui.GUITools;
import ro.barbos.gui.GUIUtil;
import ro.barbos.gui.GeneralFrame;
import ro.barbos.gui.renderer.GeneralTableRenderer;
import ro.barbos.gui.tablemodel.production.ProductPalletModel;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MachineProductionFrame extends GeneralFrame implements ActionListener {


    private static final long serialVersionUID = 1L;
    ProductPalletModel palletModel;
    JTable palletTable;

    private AddProductPalletPanel addProductPalletPanel;

    private Machine machine;

    public MachineProductionFrame(Long machineId) {
        super();

        machine = MachineDAO.getMachine(machineId);
        setTitle("Productie masina " + machine.getLabel());
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
        but.setToolTipText("Adauga produs");
        but.setActionCommand("ADD_PRODUCT_PALLET");
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

        palletModel = new ProductPalletModel();
        palletTable = new JTable(palletModel);

        TableColumn col1 = palletTable.getColumnModel().getColumn(0);
        col1.setMinWidth(10);
        col1.setPreferredWidth(20);
        col1.setWidth(20);
        GeneralTableRenderer renderer = new GeneralTableRenderer();
        for (int i = 0; i < palletTable.getColumnModel().getColumnCount(); i++) {
            palletTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        getContentPane().add(toolbar, BorderLayout.NORTH);
        getContentPane().add(new JScrollPane(palletTable), BorderLayout.CENTER);

        extractProducts();
    }

    private void extractProducts() {
        palletModel.setData(ProductionMachineDAO.getProductionProducts((long)machine.getId()));
    }

    public void actionPerformed(ActionEvent eve) {
        String command = eve.getActionCommand();
        if (command.equals("ADD_PRODUCT_PALLET")) {
            addProductPalletPanel = new AddProductPalletPanel();
            JButton buttonOk = new JButton("Salveaza");
            buttonOk.setActionCommand("SAVE_ADD");
            buttonOk.addActionListener(this);
            JButton buttonCancel = new JButton("Anuleaza");
            buttonCancel.setActionCommand("CANCEL_ADD");
            buttonCancel.addActionListener(this);
            JOptionPane.showOptionDialog(GUIUtil.container, addProductPalletPanel,
                    "Adaugare produs", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, new JButton[] {
                            buttonOk, buttonCancel }, buttonCancel);
        } else if (command.equals("CANCEL_ADD")) {
            Window w = SwingUtilities.getWindowAncestor((JButton) eve
                    .getSource());
            if (w != null)
                w.dispose();
        } else if (command.equals("SAVE_ADD")) {
            ProductionProductPalletDTO pallet = addProductPalletPanel.validateData();
            if(pallet != null) {
                pallet.setMachine(machine);
                pallet = ProductionMachineDAO.create(pallet);
                if (pallet == null) {
                    JOptionPane.showMessageDialog(GUIUtil.container,
                            "Produsul nu a putut fi salvat", "Eroare",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                palletModel.addRecord(pallet);
                Window w = SwingUtilities.getWindowAncestor((JButton) eve
                        .getSource());
                if (w != null)
                    w.dispose();
            }
        } else if (command.equals("DELETE_PRODUS")) {
            int row = palletTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(GUIUtil.container,
                        "Selectati o intrare");
            } else {
                ProductionProductPalletDTO palletDTO = palletModel.getRecord(row);
                int ras = JOptionPane.showConfirmDialog(GUIUtil.container,
                        "Doriti sa stergeti intrarea din : " + palletDTO.getEntryDate() + " cu cantitatea " + palletDTO.getQuantity(),
                        "Confirmati stergere", JOptionPane.YES_NO_OPTION);
                if (ras == JOptionPane.YES_OPTION) {
                    boolean deleted = ProductionMachineDAO.delete(palletDTO);
                    if (!deleted) {
                        JOptionPane
                                .showMessageDialog(GUIUtil.container,
                                        "Intrarea nu a fost stears",
                                        "Nu se poate sterge",
                                        JOptionPane.ERROR_MESSAGE);
                    } else {
                        palletModel.deleteRecord(row);
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
                    palletModel.toCsv(theFileToSave);
                }
            }
        }
        else if (command.equals("PRINT")) {
            try {
                palletTable.print();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public String getFrameCode() {
        return GUIUtil.INVENTORY_MACHINE_SINGLE_KEY;
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
