package ro.barbos.gui.stock;

import ro.barbos.gater.dao.SupplierDAO;
import ro.barbos.gater.model.LumberLogTransportCertificate;
import ro.barbos.gater.model.Supplier;
import ro.barbos.gui.GUIFactory;
import ro.barbos.gui.exswing.JDateTimePicker;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 * Created by radu on 8/20/2016.
 */
public class AddLumberTransportCertificatePanel extends JPanel {

    private JComponent entryDate;
    private JTextField serialCode;
    private JTextField serialNo;
    private JComboBox<Supplier> suppliers;
    private JTextField uniqueCode;
    private JComponent codeCreationDate;
    private JTextField document;
    private JTextField loadPlace;
    private JComponent loadDate;
    private JTextField unloadPlace;
    private JComponent unloadDate;
    private JTextField docCreator;
    private JTextField docCreatorName;
    private JTextField transportName;
    private JTextField transportPlate;
    private JTextField driverName;
    private JTextField driverId;
    JPanel docTab;
    JPanel transportTab;


    public AddLumberTransportCertificatePanel() {
        initGui();
    }

    private void initGui() {

        JTabbedPane tab = new JTabbedPane();
        docTab = new JPanel();
        docTab.setLayout(new BoxLayout(docTab, BoxLayout.Y_AXIS));
        transportTab = new JPanel();
        transportTab.setLayout(new BoxLayout(transportTab, BoxLayout.Y_AXIS));

        int labelWidth = 180;
        entryDate = GUIFactory.createDateTimePicker();
        docTab.add(GUIFactory.createFieldPanel(GUIFactory.createLabel("Data emiterii:", labelWidth), entryDate));
        serialCode = new JTextField(18);
        docTab.add(GUIFactory.createFieldPanel(GUIFactory.createLabel("Seria:", labelWidth), serialCode));
        serialNo = new JTextField(18);
        docTab.add(GUIFactory.createFieldPanel(GUIFactory.createLabel("Nr:", labelWidth), serialNo));
        Vector<Supplier> supplierModel = new Vector<>(SupplierDAO.getSuppliers());
        suppliers = new JComboBox<>(supplierModel);
        suppliers.setPreferredSize(new Dimension(200, (int) suppliers.getPreferredSize().getHeight()));
        docTab.add(GUIFactory.createFieldPanel(GUIFactory.createLabel("Furnizor:", labelWidth), suppliers));
        uniqueCode = new JTextField(18);
        docTab.add(GUIFactory.createFieldPanel(GUIFactory.createLabel("Cod unic:", labelWidth), uniqueCode));
        codeCreationDate = GUIFactory.createDateTimePicker();
        docTab.add(GUIFactory.createFieldPanel(GUIFactory.createLabel("Data generarii codului:", labelWidth), codeCreationDate));
        document = new JTextField(18);
        docTab.add(GUIFactory.createFieldPanel(GUIFactory.createLabel("Do provenienta:", labelWidth), document));
        loadPlace = new JTextField(18);
        transportTab.add(GUIFactory.createFieldPanel(GUIFactory.createLabel("Punct incarcare:", labelWidth), loadPlace));
        loadDate = GUIFactory.createDateTimePicker();
        transportTab.add(GUIFactory.createFieldPanel(GUIFactory.createLabel("Data incarcari:", labelWidth), loadDate));
        unloadPlace = new JTextField(18);
        transportTab.add(GUIFactory.createFieldPanel(GUIFactory.createLabel("Punct descarcare:", labelWidth), unloadPlace));
        unloadDate = GUIFactory.createDateTimePicker();
        transportTab.add(GUIFactory.createFieldPanel(GUIFactory.createLabel("Data descarcari:", labelWidth), unloadDate));
        docCreator = new JTextField(18);
        docTab.add(GUIFactory.createFieldPanel(GUIFactory.createLabel("Emitent:", labelWidth), docCreator));
        docCreatorName = new JTextField(18);
        docTab.add(GUIFactory.createFieldPanel(GUIFactory.createLabel("Nume imputernicire:", labelWidth), docCreatorName));
        transportName = new JTextField(18);
        transportTab.add(GUIFactory.createFieldPanel(GUIFactory.createLabel("Mijloc transport:", labelWidth), transportName));
        transportPlate = new JTextField(18);
        transportTab.add(GUIFactory.createFieldPanel(GUIFactory.createLabel("Nr masina:", labelWidth), transportPlate));
        driverName = new JTextField(18);
        transportTab.add(GUIFactory.createFieldPanel(GUIFactory.createLabel("Sofer:", labelWidth), driverName));
        driverId = new JTextField(18);
        transportTab.add(GUIFactory.createFieldPanel(GUIFactory.createLabel("Sofer CNP:", labelWidth), driverId));

        tab.add("Document", docTab);
        tab.add("Transport", transportTab);
        add(tab);
    }

    public LumberLogTransportCertificate validateData() {
        boolean status = true;
        status &= checkDateField((JPanel) docTab.getComponent(0));
        status &= checkTextField((JPanel) docTab.getComponent(1));
        status &= checkTextField((JPanel) docTab.getComponent(2));
        status &= checkComboboxField((JPanel) docTab.getComponent(3));
        status &= checkTextField((JPanel) docTab.getComponent(4));
        status &= checkDateField((JPanel) docTab.getComponent(5));
        status &= checkTextField((JPanel) transportTab.getComponent(0));
        status &= checkDateField((JPanel) transportTab.getComponent(1));
        status &= checkTextField((JPanel) transportTab.getComponent(2));
        status &= checkDateField((JPanel) transportTab.getComponent(3));
        status &= checkTextField((JPanel) transportTab.getComponent(6));
        status &= checkTextField((JPanel) transportTab.getComponent(7));

        if (!status) {
            return null;
        }

        LumberLogTransportCertificate certificate = new LumberLogTransportCertificate();
        certificate.setEntryDate(((JDateTimePicker) entryDate).getDateTime());
        certificate.setSerialCode(serialCode.getText());
        certificate.setSerialNo(serialNo.getText());
        certificate.setSupplierId(((Supplier) suppliers.getSelectedItem()).getId());
        certificate.setCode(uniqueCode.getText());
        certificate.setCodeCreationTime(((JDateTimePicker) codeCreationDate).getDateTime());
        if (document.getText().trim().length() > 0) {
            certificate.setDocument(document.getText());
        }
        certificate.setLoadPlace(loadPlace.getText());
        certificate.setTransportLeaveDate(((JDateTimePicker) loadDate).getDateTime());
        certificate.setUnloadPlace(unloadPlace.getText());
        certificate.setTransportArrivalDate(((JDateTimePicker) unloadDate).getDateTime());
        if (docCreator.getText().trim().length() > 0) {
            certificate.setDocCreator(docCreator.getText());
        }
        if (docCreatorName.getText().trim().length() > 0) {
            certificate.setDocCreatorName(docCreatorName.getText());
        }
        if (transportName.getText().trim().length() > 0) {
            certificate.setTransportName(transportName.getText());
        }
        certificate.setDriverName(driverName.getText());
        certificate.setTransportPlate(transportPlate.getText());
        if (driverId.getText().trim().length() > 0) {
            certificate.setDriverId(driverId.getText());
        }

        return certificate;
    }

    public void setSupplierId(Long supplierId) {
        int size = suppliers.getModel().getSize();
        for (int i = 0; i < size; i++) {
            Supplier supp = suppliers.getItemAt(i);
            if (supp.getId() == supplierId.longValue()) {
                suppliers.setSelectedIndex(i);
                break;
            }
        }
        suppliers.setEditable(false);
        suppliers.setEnabled(false);
    }

    private boolean checkDateField(JPanel panel) {
        boolean valid = true;
        JLabel label = (JLabel) (panel).getComponent(0);
        JDateTimePicker start = (JDateTimePicker) (panel).getComponent(1);
        if (start.getDateTime() == null) {
            label.setForeground(Color.red);
            valid = false;
        } else {
            label.setForeground(Color.black);
        }
        return valid;
    }

    private boolean checkTextField(JPanel panel) {
        boolean valid = true;
        JLabel label = (JLabel) (panel).getComponent(0);
        JTextField value = (JTextField) (panel).getComponent(1);
        if (value.getText().trim().length() == 0) {
            label.setForeground(Color.red);
            valid = false;
        } else {
            label.setForeground(Color.black);
        }
        return valid;
    }

    private boolean checkComboboxField(JPanel panel) {
        boolean valid = true;
        JLabel label = (JLabel) (panel).getComponent(0);
        JComboBox value = (JComboBox) (panel).getComponent(1);
        if (value.getSelectedItem() == null) {
            label.setForeground(Color.red);
            valid = false;
        } else {
            label.setForeground(Color.black);
        }
        return valid;
    }
}
