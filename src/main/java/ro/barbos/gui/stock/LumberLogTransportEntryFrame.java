package ro.barbos.gui.stock;

import com.google.gson.GsonBuilder;
import org.apache.shiro.SecurityUtils;
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
import ro.barbos.gui.GeneralTableDataFrame;
import ro.barbos.gui.tablemodel.stock.LumberLogTransportEntryModel;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by radu on 8/23/2016.
 */
public class LumberLogTransportEntryFrame extends GeneralTableDataFrame {

    private ConfigLumberTransportEntryFrame configFrame;

    public LumberLogTransportEntryFrame() {
        setTitle("Receptii");
        initGui();
        fetchData();

        TableColumn col1 = dataTable.getColumnModel().getColumn(0);
        col1.setMinWidth(10);
        col1.setPreferredWidth(20);
        col1.setWidth(20);
    }

    @Override
    public JPanel initToolbar() {
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEADING));

        JButton edit = new JButton("Busteni", new ImageIcon(
                GUITools.getImage("resources/edit24.png")));
        edit.setVerticalTextPosition(SwingConstants.BOTTOM);
        edit.setHorizontalTextPosition(SwingConstants.CENTER);
        edit.setToolTipText("Detali busteni din receptie");
        edit.setActionCommand("VIEW_DETAILS");
        edit.addActionListener(this);
        edit.setFocusPainted(false);
        edit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        toolbar.add(edit);

        JButton but = new JButton("Cost", new ImageIcon(
                GUITools.getImage("resources/currency-euro-icon24.png")));
        but.setVerticalTextPosition(SwingConstants.BOTTOM);
        but.setHorizontalTextPosition(SwingConstants.CENTER);
        but.setFocusPainted(false);
        but.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        but.setToolTipText("Seteaza matrice cost");
        but.setActionCommand("SET_COST_CONFIG");
        but.addActionListener(this);
        if (SecurityUtils.getSubject().hasRole("administrator")) {
            toolbar.add(but);
        }


        toolbar.add(createCSVExportButton());
        toolbar.add(createPrintButton());
        return toolbar;
    }

    @Override
    public void initDataModel() {
        dataModel = new LumberLogTransportEntryModel();
        List<Supplier> supplierList = SupplierDAO.getSuppliers();
        Map<Long, Supplier> suppliers = new HashMap();
        for (Supplier supplier : supplierList) {
            suppliers.put(supplier.getId(), supplier);
        }
        ((LumberLogTransportEntryModel) dataModel).setSuppliers(suppliers);
        List<LumberLogTransportCertificate> certificatesList = new LumberLogTransportCertificateDAO().findAll();
        Map<Long, LumberLogTransportCertificate> certificates = new HashMap();
        for (LumberLogTransportCertificate certificate : certificatesList) {
            certificates.put(certificate.getId(), certificate);
        }
        ((LumberLogTransportEntryModel) dataModel).setSuppliers(suppliers);
        ((LumberLogTransportEntryModel) dataModel).setCertificates(certificates);
    }

    @Override
    public void fetchData() {
        ((LumberLogTransportEntryModel) dataModel).setData(new LumberLogTransportEntryDAO().getEntries());
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
            LumberLogTransportEntry entry = ((LumberLogTransportEntryModel) dataModel).getRecord(row);
            if (entry.getLumberLogs().isEmpty()) {
                LumberLogFilterDTO filter = new LumberLogFilterDTO();
                filter.setTransportEntryId(entry.getId());
                entry.setLumberLogs(new LumberLogDAO().findAll(filter));
            }
            if (entry.getCostConfig() != null && entry.isStartedForProduction()) {
                JOptionPane.showMessageDialog(GUIUtil.container,
                        "Costul nu mai poate fi schimbat. Sa taiat deja din busteni.");
                return;
            }
            configFrame = new ConfigLumberTransportEntryFrame(this, entry);
            this.getLayeredPane().add(configFrame, JLayeredPane.PALETTE_LAYER, 0);
        } else if (command.equals("VIEW_DETAILS")) {
            int row = dataTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(GUIUtil.container,
                        "Selectati o receptie");
                return;
            }
            rowAction(row);
        } else super.actionPerformed(e);
    }

    public void setNewCost(LumberLogTransportEntryCostMatrix matrix, LumberLogTransportEntry entry) {
        GsonBuilder builder = new GsonBuilder();
        String matrixJson = builder.create().toJson(matrix);
        entry.setCost(matrix.getMediumCost());
        entry.setCostConfig(matrixJson);
        entry.setFinished(true);
        boolean status = new LumberLogTransportEntryDAO().update(entry, matrix);
        this.getLayeredPane().remove(configFrame);
        this.repaint(0);
        if (status) {
            JOptionPane.showMessageDialog(null, "Costul a fost salvat.");
        } else {
            JOptionPane.showMessageDialog(null, "A aparut o eroare. Reincercati.");
        }
    }

    @Override
    public void rowAction(int row) {
        LumberLogTransportEntry entry = ((LumberLogTransportEntryModel) dataModel).getRecord(row);
        new TransportEntryLumberLogFrame(entry);
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
