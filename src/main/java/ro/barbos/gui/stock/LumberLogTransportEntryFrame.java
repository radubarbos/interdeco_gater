package ro.barbos.gui.stock;

import com.google.gson.GsonBuilder;
import ro.barbos.gater.dao.LumberLogDAO;
import ro.barbos.gater.dao.LumberLogTransportCertificateDAO;
import ro.barbos.gater.dao.LumberLogTransportEntryDAO;
import ro.barbos.gater.dao.SupplierDAO;
import ro.barbos.gater.dto.LumberLogFilterDTO;
import ro.barbos.gater.model.LumberLogTransportCertificate;
import ro.barbos.gater.model.LumberLogTransportEntry;
import ro.barbos.gater.model.LumberLogTransportEntryCostMatrix;
import ro.barbos.gater.model.Supplier;
import ro.barbos.gui.GUITools;
import ro.barbos.gui.GUIUtil;
import ro.barbos.gui.GeneralFrame;
import ro.barbos.gui.renderer.GeneralTableRenderer;
import ro.barbos.gui.tablemodel.stock.LumberLogTransportEntryModel;

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
 * Created by radu on 8/23/2016.
 */
public class LumberLogTransportEntryFrame extends GeneralFrame implements ActionListener {

    private LumberLogTransportEntryModel dataModel;
    private JTable dataTable;
    private ConfigLumberTransportEntryFrame configFrame;

    public LumberLogTransportEntryFrame() {
        super();

        setTitle("Receptii");
        setResizable(true);
        setMaximizable(true);
        setIconifiable(true);
        setClosable(true);

        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEADING));

        JButton but = new JButton("Cost", new ImageIcon(
                GUITools.getImage("resources/currency-euro-icon24.png")));
        but.setVerticalTextPosition(SwingConstants.BOTTOM);
        but.setHorizontalTextPosition(SwingConstants.CENTER);
        but.setFocusPainted(false);
        but.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        but.setToolTipText("Seteaza matrice cost");
        but.setActionCommand("SET_COST_CONFIG");
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

        dataModel = new LumberLogTransportEntryModel();
        List<Supplier> supplierList = SupplierDAO.getSuppliers();
        Map<Long, Supplier> suppliers = new HashMap();
        for (Supplier supplier : supplierList) {
            suppliers.put(supplier.getId(), supplier);
        }
        dataModel.setSuppliers(suppliers);
        List<LumberLogTransportCertificate> certificatesList = new LumberLogTransportCertificateDAO().findAll();
        Map<Long, LumberLogTransportCertificate> certificates = new HashMap();
        for (LumberLogTransportCertificate certificate : certificatesList) {
            certificates.put(certificate.getId(), certificate);
        }
        dataModel.setSuppliers(suppliers);
        dataModel.setCertificates(certificates);
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

        extractData();
    }

    private void extractData() {
        dataModel.setData(new LumberLogTransportEntryDAO().getEntries());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("SET_COST_CONFIG")) {
            int row = dataTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(GUIUtil.container,
                        "Selectati o receptie");
                return;
            }
            LumberLogTransportEntry entry = dataModel.getRecord(row);
            if (entry.getLumberLogs().isEmpty()) {
                LumberLogFilterDTO filter = new LumberLogFilterDTO();
                filter.setTransportEntryId(entry.getId());
                entry.setLumberLogs(new LumberLogDAO().findAll(filter));
            }
            configFrame = new ConfigLumberTransportEntryFrame(this, entry);
            this.getLayeredPane().add(configFrame, JLayeredPane.PALETTE_LAYER, 0);
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

    public void setNewCost(LumberLogTransportEntryCostMatrix matrix, LumberLogTransportEntry entry) {
        GsonBuilder builder = new GsonBuilder();
        String matrixJson = builder.create().toJson(matrix);
        entry.setCost(matrix.getTotalCost());
        entry.setCostConfig(matrixJson);
        entry.setFinished(true);
        boolean status = new LumberLogTransportEntryDAO().update(entry);
        this.getLayeredPane().remove(configFrame);
        this.repaint(0);
        if (status) {
            JOptionPane.showMessageDialog(null, "Costul a fost salvat.");
        } else {
            JOptionPane.showMessageDialog(null, "A aparut o eroare. Reincercati.");
        }
    }


    @Override
    public String getFrameCode() {
        return GUIUtil.TRANSPORT_STOCK_ENTRIES_KEY;
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
