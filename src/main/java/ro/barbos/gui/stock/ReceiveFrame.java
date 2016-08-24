package ro.barbos.gui.stock;

import ro.barbos.gater.dao.LumberLogDAO;
import ro.barbos.gater.dao.LumberLogTransportCertificateDAO;
import ro.barbos.gater.dao.LumberLogTransportEntryDAO;
import ro.barbos.gater.dao.SupplierDAO;
import ro.barbos.gater.data.LumberLogUtil;
import ro.barbos.gater.model.*;
import ro.barbos.gater.model.validation.LumberLogTransportEntryValidator;
import ro.barbos.gater.stock.StockSettings;
import ro.barbos.gui.*;
import ro.barbos.gui.renderer.ReceiveTableRenderer;
import ro.barbos.gui.supplier.SuppliersPanel;
import ro.barbos.gui.tablemodel.ReceiveModel;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReceiveFrame extends GeneralFrame implements ActionListener {

    public static final long serialVersionUID = 1L;

    private JPanel north = null;
    private JPanel info = null;
    private Date now = new Date();
    private SimpleDateFormat format = new SimpleDateFormat("dd. MM yyyy");
    private NumberFormat numberFormatter = NumberFormat.getInstance(ConfigLocalManager.locale);

    private AddLogLumberPanel addLogLumberPanel = null;
    private SuppliersPanel suppliersPanel = null;
    private LumberTransportCertificatesPanel certificatePanel = null;
    private AddLumberTransportCertificatePanel addCertificatePanel = null;

    JTable receiveTable;
    ReceiveModel data;

    private JLabel totalCubajLabel;
    private JLabel marginCubajLabel;

    LumberLogEntry stockEntry;

    JLabel supplierName;
    JLabel transportCertificateName;
    JLabel supplierNameLabel;
    JLabel transportCertificateNameLabel;

    JLabel message;

    LumberLogTransportEntry entry;
    boolean isNew = false;

    private java.util.List<LumberLog> pendingLogs = new ArrayList();

    public ReceiveFrame() {
        super();

        initData();

        stockEntry = new LumberLogEntry();
        stockEntry.setUser(ConfigLocalManager.currentUser);

        Font h1Font = new Font("arial", Font.BOLD, 16);

        setTitle("Receptie");
        setResizable(true);
        setMaximizable(true);
        setIconifiable(true);
        setClosable(true);

        numberFormatter.setMaximumFractionDigits(2);
        numberFormatter.setMinimumFractionDigits(2);

        north = new JPanel();
        north.setLayout(new BorderLayout());
        info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.X_AXIS));
        JPanel info1 = new JPanel();
        info1.setLayout(new BoxLayout(info1, BoxLayout.Y_AXIS));
        JPanel info2 = new JPanel();
        info2.setLayout(new BoxLayout(info2, BoxLayout.Y_AXIS));
        info.add(Box.createHorizontalStrut(5));
        info.add(info1);
        info.add(Box.createHorizontalStrut(30));
        info.add(info2);

        message = new JLabel();
        message.setFont(new Font("Arial", Font.BOLD, 18));
        message.setForeground(Color.red);
        JPanel messagePanel = new JPanel(new BorderLayout());
        messagePanel.add(message, BorderLayout.CENTER);

        JLabel lnow = new JLabel();
        lnow.setText("Data: " + format.format(now));
        lnow.setLocation(20, 10);
        lnow.setFont(new Font("arial", Font.BOLD, 16));
        lnow.setSize(new Dimension(150, lnow.getPreferredSize().height));
        info1.add(lnow);

        JLabel lope = new JLabel();
        lope.setText("Operator:" + ConfigLocalManager.currentUser.getName());
        lope.setLocation(500, 10);
        lope.setFont(new Font("arial", Font.BOLD, 16));
        lope.setSize(new Dimension(150, lope.getPreferredSize().height));
        info1.add(lope);

        totalCubajLabel = new JLabel();
        totalCubajLabel.setText("Total cubaj: " + numberFormatter.format(entry.getVolume()) + " metri cubi");
        totalCubajLabel.setFont(h1Font);
        info1.add(totalCubajLabel);
        marginCubajLabel = new JLabel();
        marginCubajLabel.setText("Coaja cubaj: " + numberFormatter.format(entry.getMarginVolume()) + " metri cubi");
        marginCubajLabel.setFont(h1Font);
        info1.add(marginCubajLabel);

        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEADING));

        JButton but = new JButton("Adauga", new ImageIcon(
                GUITools.getImage("resources/add24.png")));
        but.setVerticalTextPosition(SwingConstants.BOTTOM);
        but.setHorizontalTextPosition(SwingConstants.CENTER);
        but.setFocusPainted(false);
        but.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        but.setToolTipText("Adauga bustean");
        but.setActionCommand("ADD");
        but.addActionListener(this);
        toolbar.add(but);

        but = new JButton("Sterge", new ImageIcon(
                GUITools.getImage("resources/delete24.png")));
        but.setVerticalTextPosition(SwingConstants.BOTTOM);
        but.setHorizontalTextPosition(SwingConstants.CENTER);
        but.setFocusPainted(false);
        but.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        but.setToolTipText("Sterge bustean");
        but.setActionCommand("REMOVE");
        but.addActionListener(this);
        toolbar.add(but);

        but = new JButton("Furnizor", new ImageIcon(
                GUITools.getImage("resources/add24.png")));
        but.setVerticalTextPosition(SwingConstants.BOTTOM);
        but.setHorizontalTextPosition(SwingConstants.CENTER);
        but.setFocusPainted(false);
        but.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        but.setToolTipText("Adauga furnizor");
        but.setActionCommand("ADD_SUPPLIER");
        but.addActionListener(this);
        toolbar.add(but);

        but = new JButton("Aviz", new ImageIcon(
                GUITools.getImage("resources/add24.png")));
        but.setVerticalTextPosition(SwingConstants.BOTTOM);
        but.setHorizontalTextPosition(SwingConstants.CENTER);
        but.setFocusPainted(false);
        but.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        but.setToolTipText("Adauga aviz");
        but.setActionCommand("ADD_CERTIFICATE");
        but.addActionListener(this);
        toolbar.add(but);

        but = new JButton("Finalizeaza", new ImageIcon(
                GUITools.getImage("resources/edit-validated-icon24.png")));
        but.setVerticalTextPosition(SwingConstants.BOTTOM);
        but.setHorizontalTextPosition(SwingConstants.CENTER);
        but.setFocusPainted(false);
        but.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        but.setToolTipText("Finalizeaza");
        but.setActionCommand("FINALIZE");
        but.addActionListener(this);
        toolbar.add(but);

        JButton csvExport = new JButton("Exporta", new ImageIcon(
                GUITools.getImage("resources/csv24.png")));
        csvExport.setVerticalTextPosition(SwingConstants.BOTTOM);
        csvExport.setHorizontalTextPosition(SwingConstants.CENTER);
        csvExport.setToolTipText("Exporta tabelul in fisier csv");
        csvExport.setActionCommand("CSV");
        csvExport.addActionListener(this);
        csvExport.setFocusPainted(false);
        csvExport.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        toolbar.add(csvExport);

        toolbar.setSize(toolbar.getPreferredSize());
        toolbar.setLocation(20, 70);
        north.add(toolbar, BorderLayout.NORTH);
        north.add(info, BorderLayout.SOUTH);

        //
        supplierNameLabel = new JLabel("Furnizor:");
        supplierNameLabel.setFont(h1Font);
        JPanel tempRow = new JPanel(new FlowLayout(FlowLayout.LEADING));
        supplierNameLabel.setPreferredSize(new Dimension(120, supplierNameLabel.getPreferredSize().height));
        supplierName = new JLabel();
        tempRow.add(supplierNameLabel);
        tempRow.add(supplierName);
        info2.add(tempRow);
        if (entry.getSupplierId() != null) {
            Supplier supplier = SupplierDAO.findById(entry.getSupplierId());
            supplierName.setText(supplier.getTitle());
        }
        transportCertificateNameLabel = new JLabel("Aviz:");
        transportCertificateNameLabel.setFont(h1Font);
        tempRow = new JPanel(new FlowLayout(FlowLayout.LEADING));
        transportCertificateNameLabel.setPreferredSize(new Dimension(120, transportCertificateNameLabel.getPreferredSize().height));
        transportCertificateName = new JLabel();
        tempRow.add(transportCertificateNameLabel);
        tempRow.add(transportCertificateName);
        info2.add(tempRow);
        if (entry.getCertificateId() != null) {
            LumberLogTransportCertificate certificate = new LumberLogTransportCertificateDAO().findById(entry.getCertificateId());
            transportCertificateName.setText(certificate.getCode());
        }

        info1.setPreferredSize(new Dimension(info1.getPreferredSize().width + 20, info1.getPreferredSize().height));

        data = new ReceiveModel();
        receiveTable = new JTable(data);
        data.addLumberLogs(pendingLogs);

        TableColumn col1 = receiveTable.getColumnModel().getColumn(0);
        col1.setMinWidth(10);
        col1.setPreferredWidth(20);
        col1.setWidth(20);
        ReceiveTableRenderer renderer = new ReceiveTableRenderer(data);
        for (int i = 0; i < receiveTable.getColumnModel().getColumnCount(); i++) {
            receiveTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(north, BorderLayout.NORTH);
        JPanel temp = new JPanel(new BorderLayout());
        temp.add(messagePanel, BorderLayout.NORTH);
        temp.add(new JScrollPane(receiveTable), BorderLayout.CENTER);
        getContentPane().add(temp, BorderLayout.CENTER);
    }

    private void showMessage(String text) {
        message.setText(text);
    }


    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent eve) {
        showMessage(null);
        String command = eve.getActionCommand();
        if (command.equals("ADD")) {
            if (entry.getSupplierId() == null) {
                showMessage("Setati furnizorul inainte de adauga busteni.");
                return;
            }
            JButton buttonOk = new JButton("Salveaza");
            buttonOk.setActionCommand("SAVE_ADD_LOG");
            buttonOk.addActionListener(this);
            JButton buttonCancel = new JButton("Anuleaza");
            buttonCancel.setActionCommand("CANCEL_DIALOG");
            buttonCancel.addActionListener(this);
            addLogLumberPanel = new AddLogLumberPanel(null);
            JScrollPane scrollPane = new JScrollPane(addLogLumberPanel);
            scrollPane.setPreferredSize(new Dimension(addLogLumberPanel.getPreferredSize().width + 20, 250));
            JOptionPane.showOptionDialog(GUIUtil.container, scrollPane, "Adaugare bustean",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new JButton[]{buttonOk, buttonCancel}, buttonCancel);
        } else if (command.equals("CANCEL_ADD_LOG")) {
            Window w = SwingUtilities.getWindowAncestor((JButton) eve.getSource());
            if (w != null) w.dispose();
        } else if (command.equals("SAVE_ADD_LOG")) {
            boolean status = addLumberLogEntry();
            if (status) {
                closeDialog((JComponent) eve.getSource());
            }
        } else if (command.equals("REMOVE")) {
            int row = receiveTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(GUIUtil.container, "Nici un bustean nu a fost selectat");
            } else {
                LumberLog lumberLog = data.getLumberLog(row);
                int ras = JOptionPane.showConfirmDialog(GUIUtil.container, "Doriti sa stergeti busteanul cu placuta: " + lumberLog.getPlate().getLabel(), "Confirmati stergere", JOptionPane.YES_NO_OPTION);
                if (ras == JOptionPane.YES_OPTION) {
                    boolean deleted = LumberLogDAO.deleteLumberLog(lumberLog);
                    if (!deleted) {
                        JOptionPane.showMessageDialog(GUIUtil.container, "Busteanul nu a fost sters", "Eroare", JOptionPane.ERROR_MESSAGE);
                    } else {
                        removeLumberLog(lumberLog);
                        data.removeLumberLog(row);
                    }
                }
            }
        } else if (command.equals("CSV")) {
            JFileChooser chooser = new JFileChooser();
            int option = chooser.showSaveDialog(GUIUtil.container);
            if (option == JFileChooser.APPROVE_OPTION) {
                if (chooser.getSelectedFile() != null) {
                    File theFileToSave = chooser.getSelectedFile();
                    data.toCsv(theFileToSave);
                }
            }
        } else if (command.equals("ADD_SUPPLIER")) {
            JButton buttonOk = new JButton("Seteaza");
            buttonOk.setActionCommand("SAVE_ADD_SUPPLIER_LOG");
            buttonOk.addActionListener(this);
            JButton buttonCancel = new JButton("Anuleaza");
            buttonCancel.setActionCommand("CANCEL_DIALOG");
            buttonCancel.addActionListener(this);
            suppliersPanel = new SuppliersPanel();
            JOptionPane.showOptionDialog(GUIUtil.container, suppliersPanel, "Furnizor",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new JButton[]{buttonOk, buttonCancel}, buttonCancel);
        } else if (command.equals("CANCEL_DIALOG")) {
            closeDialog((JComponent) eve.getSource());
        } else if (command.equals("SAVE_ADD_SUPPLIER_LOG")) {
            boolean dbUpdated = true;
            Supplier supplier = suppliersPanel.validateData();
            if (supplier == null) {
                return;
            }
            if (!data.getLumberLogs().isEmpty()) {
                dbUpdated = new LumberLogDAO().updateSupplier(supplier.getId(), entry.getLumberLogIds());
            }
            if (dbUpdated) {
                entry.setSupplierId(supplier.getId());
                supplierName.setText(supplier.getTitle());
                entry.setCertificateId(null);
                transportCertificateName.setText(null);
            } else {
                showMessage("A aparut o eroare la salvarea datelor. Reincerca.");
            }
            closeDialog((JComponent) eve.getSource());

        } else if (command.equals("ADD_CERTIFICATE")) {
            if (entry.getSupplierId() == null) {
                showMessage("Setati furnizorul inainte de adauga busteni.");
                return;
            }
            JButton buttonNew = new JButton("Creaza aviz nou");
            buttonNew.setActionCommand("NEW_CERTIFICATE_LOG");
            buttonNew.addActionListener(this);
            JButton buttonOk = new JButton("Seteaza");
            buttonOk.setActionCommand("SAVE_ADD_CERTIFICATE_LOG");
            buttonOk.addActionListener(this);
            JButton buttonCancel = new JButton("Anuleaza");
            buttonCancel.setActionCommand("CANCEL_DIALOG");
            buttonCancel.addActionListener(this);
            certificatePanel = new LumberTransportCertificatesPanel(entry.getSupplierId());
            JOptionPane.showOptionDialog(GUIUtil.container, certificatePanel, "Aviz",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new JButton[]{buttonNew, buttonOk, buttonCancel}, buttonCancel);
        } else if (command.equals("SAVE_ADD_CERTIFICATE_LOG")) {
            LumberLogTransportCertificate certificate = certificatePanel.validateData();
            if (certificate == null) {
                showMessage("Nu sa selectat nici un aviz");
                return;
            }
            boolean dbUpdated = updateLumberCertificate(certificate);
            closeDialog((JComponent) eve.getSource());
            if (!dbUpdated) {
                showMessage("A aparut o eroare la salvarea datelor. Reincercati.");
                return;
            }
            entry.setCertificateId(certificate.getId());
            transportCertificateName.setText(certificate.getCode());
        } else if (command.equals("NEW_CERTIFICATE_LOG")) {
            JButton buttonOk = new JButton("Seteaza");
            buttonOk.setActionCommand("SAVE_ADD_NEW_CERTIFICATE_LOG");
            buttonOk.addActionListener(this);
            JButton buttonCancel = new JButton("Anuleaza");
            buttonCancel.setActionCommand("CANCEL_DIALOG");
            buttonCancel.addActionListener(this);
            addCertificatePanel = new AddLumberTransportCertificatePanel();
            addCertificatePanel.setSupplierId(entry.getSupplierId());
            Supplier supplier = SupplierDAO.findById(entry.getSupplierId());
            JOptionPane.showOptionDialog(GUIUtil.container, addCertificatePanel, "Aviz nou pt furnizorul " + supplier.getTitle(),
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new JButton[]{buttonOk, buttonCancel}, buttonCancel);
        } else if (command.equals("SAVE_ADD_NEW_CERTIFICATE_LOG")) {
            LumberLogTransportCertificate certificate = addCertificatePanel.validateData();
            if (certificate == null) {
                return;
            }
            certificate = new LumberLogTransportCertificateDAO().store(certificate);
            if (certificate == null) {
                showMessage("A aparut o eroare la salvarea datelor noului aviz");
                return;
            }
            boolean dbUpdated = updateLumberCertificate(certificate);
            entry.setCertificateId(certificate.getId());
            closeDialog((JComponent) eve.getSource());
            closeDialog(certificatePanel);
            if (!dbUpdated) {
                showMessage("A aparut o eroare la salvarea datelor. Reincercati.");
                return;
            }
            entry.setCertificateId(certificate.getId());
            transportCertificateName.setText(certificate.getCode());
        } else if (command.equals("FINALIZE")) {
            String errMsg = new LumberLogTransportEntryValidator().validate(entry);
            if (errMsg != null) {
                showMessage(errMsg);
                return;
            }
            int confirmation = JOptionPane.showConfirmDialog(null, "Receptia e valida si urmeaza sa fie inchisa.\n Odata inchisa nu mai poate fi schimbata\n Continuati?");
            if (confirmation != JOptionPane.YES_OPTION) {
                return;
            }
            entry.setFinished(true);
            boolean status = new LumberLogTransportEntryDAO().update(entry);
            if (!status) {
                showMessage("A aparut o eroare. Reincercati.");
            } else {
                status = new LumberLogDAO().updateStatus(entry.getLumberLogIds());
                if (status) {
                    JOptionPane.showMessageDialog(null, "Receptia a fost inchisa.");
                    dispose();
                } else {
                    showMessage("A aparut o eroare. Reincercati.");
                }
            }
        }
    }

    private boolean addLumberLogEntry() {
        if (addLogLumberPanel != null) {
            LumberLog lumberLog = addLogLumberPanel.validateData();
            if (lumberLog == null) {
                return false;
            }

            if (isNew) {
                LumberLogTransportEntry savedEntry = new LumberLogTransportEntryDAO().store(entry);
                if (savedEntry == null) {
                    showMessage("A aparut o eroare la salvare");
                    return true;
                } else {
                    isNew = false;
                }
            }

            lumberLog.setStatus(-1);
            lumberLog.setMarginPercent(StockSettings.LUMBER_LOG_MARGIN);
            LumberLogUtil.calculateVolume(lumberLog);
            lumberLog.setRealLength(lumberLog.getLength().longValue());
            lumberLog.setRealVolume(lumberLog.getVolume());
            LumberLogUtil.trimAndSetLength(lumberLog);
            LumberLogUtil.calculateVolume(lumberLog);
            lumberLog.setMarginRealVolume(lumberLog.getRealVolume() * (lumberLog.getMarginPercent() / 100D));
            lumberLog.setMarginVolume(lumberLog.getVolume() * (lumberLog.getMarginPercent() / 100D));
            lumberLog.setSupplierId(entry.getSupplierId());
            lumberLog.setTransportEntryId(entry.getId());
            if (entry.getCertificateId() != null) {
                lumberLog.setTransportCertifiateId(entry.getCertificateId());
            }


            boolean status = LumberLogDAO.saveLumberLog(lumberLog, addLogLumberPanel.getLumberStack(), stockEntry);
            if (!status) {
                showMessage("A aparut o eroare la salvarea busteanului. Reintrodu.");
                return true;
            }
            data.addLumberLog(lumberLog, addLogLumberPanel.getLumberStack());
            addLumberLog(lumberLog);
        }
        return true;
    }

    private void addLumberLog(LumberLog lumberLog) {
        entry.addLumberLog(lumberLog);
        totalCubajLabel.setText("Total cubaj: " + numberFormatter.format(entry.getVolume()) + " metri cubi");
        marginCubajLabel.setText("Total cubaj: " + numberFormatter.format(entry.getMarginVolume()) + " metri cubi");
    }

    private void removeLumberLog(LumberLog lumberLog) {
        entry.removeLumberLog(lumberLog);
        totalCubajLabel.setText("Total cubaj: " + numberFormatter.format(entry.getVolume()) + " metri cubi");
        marginCubajLabel.setText("Total cubaj: " + numberFormatter.format(entry.getMarginVolume()) + " metri cubi");
    }

    private void initData() {
        entry = new LumberLogTransportEntryDAO().getOpenEntry((long) ConfigLocalManager.currentUser.getID());
        if (entry == null) {
            entry = new LumberLogTransportEntry();
            entry.setUserId((long) ConfigLocalManager.currentUser.getID());
            entry.setEntryDate(new Date());
            entry.setFinished(false);
            isNew = true;
        } else {
            pendingLogs = new LumberLogDAO().getPendingEntryLumberLogs((long) ConfigLocalManager.currentUser.getID());
            if (!pendingLogs.isEmpty()) {
                LumberLog lumberLog = pendingLogs.get(0);
                entry.setSupplierId(lumberLog.getSupplierId());
                if (lumberLog.getTransportCertifiateId() != null) {
                    entry.setCertificateId(lumberLog.getTransportCertifiateId());
                }
                entry.setFinished(false);
            }
            entry.setLumberLogs(pendingLogs);
            isNew = false;
        }
    }

    private void closeDialog(JComponent component) {
        Window w = SwingUtilities.getWindowAncestor(component);
        if (w != null) w.dispose();
    }

    private boolean updateLumberCertificate(LumberLogTransportCertificate certificate) {
        boolean dbUpdated = true;
        if (entry.getLumberLogCount() > 0) {
            dbUpdated = new LumberLogDAO().updateCertificate(certificate.getId(), entry.getLumberLogIds());
        }
        return dbUpdated;
    }

    @Override
    public String getFrameCode() {
        return GUIUtil.RECEPTIE_KEY;
    }

    @Override
    public ImageIcon getFrameIcon() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ImageIcon getIconifiedIcon() {
        Image image = GUITools.getImage("/ro/barbos/gui/resources/product32.png");
        return new ImageIcon(image);
    }

}
