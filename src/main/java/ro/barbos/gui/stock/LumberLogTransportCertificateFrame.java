package ro.barbos.gui.stock;

import ro.barbos.gater.dao.LumberLogTransportCertificateDAO;
import ro.barbos.gater.dao.SupplierDAO;
import ro.barbos.gater.model.LumberLogTransportCertificate;
import ro.barbos.gater.model.Supplier;
import ro.barbos.gui.GUITools;
import ro.barbos.gui.GUIUtil;
import ro.barbos.gui.GeneralFrame;
import ro.barbos.gui.renderer.GeneralTableRenderer;
import ro.barbos.gui.tablemodel.stock.LumberLogTransportCertificateModel;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by radu on 8/17/2016.
 */
public class LumberLogTransportCertificateFrame extends GeneralFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    LumberLogTransportCertificateModel dataModel;
    JTable dataTable;

    private AddLumberTransportCertificatePanel addPanel;

    private Map<Long, Supplier> suppliers = new HashMap();

    public LumberLogTransportCertificateFrame() {
        super();

        setTitle("Avize");
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
        but.setToolTipText("Adauga aviz");
        but.setActionCommand("ADD_ENTITY");
        but.addActionListener(this);
        toolbar.add(but);

        but = new JButton("Sterge", new ImageIcon(
                GUITools.getImage("resources/delete24.png")));
        but.setVerticalTextPosition(SwingConstants.BOTTOM);
        but.setHorizontalTextPosition(SwingConstants.CENTER);
        but.setFocusPainted(false);
        but.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        but.setToolTipText("Sterge aviz");
        but.setActionCommand("DELETE_ENTITY");
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

        dataModel = new LumberLogTransportCertificateModel();
        dataModel.setSuppliers(suppliers);
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

        List<Supplier> supplierList = SupplierDAO.getSuppliers();
        for (Supplier supplier : supplierList) {
            suppliers.put(supplier.getId(), supplier);
        }

        extractData();
    }

    private void extractData() {
        dataModel.setData(new LumberLogTransportCertificateDAO().findAll());
    }

    public void actionPerformed(ActionEvent eve) {
        String command = eve.getActionCommand();
        if (command.equals("ADD_ENTITY")) {
            addPanel = new AddLumberTransportCertificatePanel();
            JButton buttonOk = new JButton("Salveaza");
            buttonOk.setActionCommand("SAVE_ADD");
            buttonOk.addActionListener(this);
            JButton buttonCancel = new JButton("Anuleaza");
            buttonCancel.setActionCommand("CANCEL_ADD");
            buttonCancel.addActionListener(this);
            JOptionPane.showOptionDialog(GUIUtil.container, addPanel,
                    "Adaugare aviz", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, new JButton[]{
                            buttonOk, buttonCancel}, buttonCancel);
        } else if (command.equals("CANCEL_ADD")) {
            Window w = SwingUtilities.getWindowAncestor((JButton) eve
                    .getSource());
            if (w != null)
                w.dispose();
        } else if (command.equals("SAVE_ADD")) {
            LumberLogTransportCertificate certificate = addPanel.validateData();
            if (certificate != null) {
                certificate = new LumberLogTransportCertificateDAO().store(certificate);
                if (certificate == null) {
                    JOptionPane.showMessageDialog(GUIUtil.container,
                            "Avizul nu a putut fi salvat", "Eroare",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                dataModel.addRecord(certificate);
                Window w = SwingUtilities.getWindowAncestor((JButton) eve
                        .getSource());
                if (w != null)
                    w.dispose();
            }
        } else if (command.equals("DELETE_ENTITY")) {
            int row = dataTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(GUIUtil.container,
                        "Selectati un aviz");
            } else {
                LumberLogTransportCertificate certificate = dataModel.getRecord(row);
                int ras = JOptionPane.showConfirmDialog(GUIUtil.container,
                        "Doriti sa stergeti avizul: " + certificate.getCode(),
                        "Confirmati stergere", JOptionPane.YES_NO_OPTION);
                if (ras == JOptionPane.YES_OPTION) {
                    boolean deleted = new LumberLogTransportCertificateDAO().delete(certificate);
                    if (!deleted) {
                        JOptionPane
                                .showMessageDialog(GUIUtil.container,
                                        "Avizul nu a fost stears",
                                        "Nu se poate sterge",
                                        JOptionPane.ERROR_MESSAGE);
                    } else {
                        dataModel.deleteRecord(row);
                    }
                }
            }

        } else if (command.equals("CSV")) {
            JFileChooser chooser = new JFileChooser();
            int option = chooser.showSaveDialog(GUIUtil.container);
            if (option == JFileChooser.APPROVE_OPTION) {
                if (chooser.getSelectedFile() != null) {
                    File theFileToSave = chooser.getSelectedFile();
                    dataModel.toCsv(theFileToSave);
                }
            }
        } else if (command.equals("PRINT")) {
            try {
                dataTable.print();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public String getFrameCode() {
        return GUIUtil.LUMBER_TRANSPORT_STOCK_EY;
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
