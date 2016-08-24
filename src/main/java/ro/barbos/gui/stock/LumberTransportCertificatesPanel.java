package ro.barbos.gui.stock;

import ro.barbos.gater.dao.LumberLogTransportCertificateDAO;
import ro.barbos.gater.model.LumberLogTransportCertificate;
import ro.barbos.gui.GUIFactory;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 * Created by radu on 8/22/2016.
 */
public class LumberTransportCertificatesPanel extends JPanel {

    private JComboBox<LumberLogTransportCertificate> certificates;
    private Long supplierId;

    public LumberTransportCertificatesPanel(Long supplierId) {
        this.supplierId = supplierId;
        initGui();
    }

    private void initGui() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        Vector<LumberLogTransportCertificate> supplierModel = new Vector<>(new LumberLogTransportCertificateDAO().findAll(supplierId));
        certificates = new JComboBox<>(supplierModel);
        certificates.setPreferredSize(new Dimension(200, (int) certificates.getPreferredSize().getHeight()));
        add(GUIFactory.createFieldPanel(GUIFactory.createLabel("Avize:", 100), certificates));
    }

    public LumberLogTransportCertificate validateData() {
        return (LumberLogTransportCertificate) certificates.getSelectedItem();
    }
}
