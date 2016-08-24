package ro.barbos.gui.supplier;

import ro.barbos.gater.dao.SupplierDAO;
import ro.barbos.gater.model.Supplier;
import ro.barbos.gui.GUIFactory;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 * Created by radu on 8/22/2016.
 */
public class SuppliersPanel extends JPanel {

    private JComboBox<Supplier> suppliers;

    public SuppliersPanel() {
        initGui();
    }

    private void initGui() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        Vector<Supplier> supplierModel = new Vector<>(SupplierDAO.getSuppliers());
        suppliers = new JComboBox<>(supplierModel);
        suppliers.setPreferredSize(new Dimension(200, (int) suppliers.getPreferredSize().getHeight()));
        add(GUIFactory.createFieldPanel(GUIFactory.createLabel("Furnizor:", 100), suppliers));
    }

    public Supplier validateData() {
        return (Supplier) suppliers.getSelectedItem();
    }
}
