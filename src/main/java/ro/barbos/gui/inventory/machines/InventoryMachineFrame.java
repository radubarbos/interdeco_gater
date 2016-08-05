package ro.barbos.gui.inventory.machines;

import ro.barbos.gater.dao.MachineDAO;
import ro.barbos.gater.model.Machine;
import ro.barbos.gui.GUITools;
import ro.barbos.gui.GUIUtil;
import ro.barbos.gui.GeneralFrame;
import ro.barbos.gui.renderer.GeneralTableRenderer;
import ro.barbos.gui.tablemodel.inventory.MachineModel;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class InventoryMachineFrame extends GeneralFrame implements ActionListener {


    private static final long serialVersionUID = 1L;
    MachineModel machineModel;
    JTable machineTable;

    private AddMachinePanel addMachinePanel;

    public InventoryMachineFrame() {
        super();

        setTitle("Masini");
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
        but.setToolTipText("Adauga masina noua");
        but.setActionCommand("ADD_MACHINE");
        but.addActionListener(this);
        toolbar.add(but);

        but = new JButton("Sterge", new ImageIcon(
                GUITools.getImage("resources/delete24.png")));
        but.setVerticalTextPosition(SwingConstants.BOTTOM);
        but.setHorizontalTextPosition(SwingConstants.CENTER);
        but.setFocusPainted(false);
        but.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        but.setToolTipText("Sterge masina");
        but.setActionCommand("DELETE_MACHINE");
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

        machineModel = new MachineModel();
        machineTable = new JTable(machineModel);

        TableColumn col1 = machineTable.getColumnModel().getColumn(0);
        col1.setMinWidth(10);
        col1.setPreferredWidth(20);
        col1.setWidth(20);
        GeneralTableRenderer renderer = new GeneralTableRenderer();
        for (int i = 0; i < machineTable.getColumnModel().getColumnCount(); i++) {
            machineTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        getContentPane().add(toolbar, BorderLayout.NORTH);
        getContentPane().add(new JScrollPane(machineTable), BorderLayout.CENTER);

        extractProducts();
    }

    private void extractProducts() {
        machineModel.setMachines(MachineDAO.getMachines());
    }

    public void actionPerformed(ActionEvent eve) {
        String command = eve.getActionCommand();
        if (command.equals("ADD_MACHINE")) {
            addMachinePanel = new AddMachinePanel();
            JButton buttonOk = new JButton("Salveaza");
            buttonOk.setActionCommand("SAVE_ADD");
            buttonOk.addActionListener(this);
            JButton buttonCancel = new JButton("Anuleaza");
            buttonCancel.setActionCommand("CANCEL_ADD");
            buttonCancel.addActionListener(this);
            JOptionPane.showOptionDialog(GUIUtil.container, addMachinePanel,
                    "Adaugare masina", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, new JButton[] {
                            buttonOk, buttonCancel }, buttonCancel);
        } else if (command.equals("CANCEL_ADD")) {
            Window w = SwingUtilities.getWindowAncestor((JButton) eve
                    .getSource());
            if (w != null)
                w.dispose();
        } else if (command.equals("SAVE_ADD")) {
            Machine machine = addMachinePanel.validateData();
            if(machine != null) {
                machine = MachineDAO.create(machine);
                if (machine == null) {
                    JOptionPane.showMessageDialog(GUIUtil.container,
                            "Masina nu a putut fi salvat", "Eroare",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                machineModel.addMachine(machine);
                Window w = SwingUtilities.getWindowAncestor((JButton) eve
                        .getSource());
                if (w != null)
                    w.dispose();
            }
        } else if (command.equals("DELETE_MACHINE")) {
            int row = machineTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(GUIUtil.container,
                        "Selectati un produs");
            } else {
                Machine machine = machineModel.getMachine(row);
                int ras = JOptionPane.showConfirmDialog(GUIUtil.container,
                        "Doriti sa stergeti masina: " + machine.getLabel(),
                        "Confirmati stergere", JOptionPane.YES_NO_OPTION);
                if (ras == JOptionPane.YES_OPTION) {
                    boolean deleted = MachineDAO.delete(machine);
                    if (!deleted) {
                        JOptionPane
                                .showMessageDialog(GUIUtil.container,
                                        "Masina nu a fost stears",
                                        "Nu se poate sterge",
                                        JOptionPane.ERROR_MESSAGE);
                    } else {
                        machineModel.deleteMachine(row);
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
                    machineModel.toCsv(theFileToSave);
                }
            }
        }
        else if (command.equals("PRINT")) {
            try {
                machineTable.print();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public String getFrameCode() {
        return GUIUtil.INVENTORY_MACHINE_KEY;
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
