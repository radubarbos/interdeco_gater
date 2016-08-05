package ro.barbos.gui.production;

import ro.barbos.gater.dao.ProductDAO;
import ro.barbos.gater.dto.production.ProductionProductPalletDTO;
import ro.barbos.gater.model.Product;
import ro.barbos.gui.ConfigLocalManager;
import ro.barbos.gui.GUIFactory;
import ro.barbos.gui.GUIUtil;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Date;

public class AddProductPalletPanel extends JPanel {

    NumberFormat formater = NumberFormat.getNumberInstance(ConfigLocalManager.locale);

    public AddProductPalletPanel() {
        formater.setMaximumFractionDigits(1);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        java.util.List<Product> products = ProductDAO.getProducts();
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(GUIFactory.createLabel("Produs:", 100));
        JComboBox<Product> produs = new JComboBox<Product>(products.toArray(new Product[0]));
        panel.add(produs);
        add(panel);

        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(GUIFactory.createLabel("Cantitate:", 100));
        JFormattedTextField widthInput = GUIFactory.createNumberInput(null, 0L, 1000000L, 80);
        panel.add(widthInput);
        add(panel);

        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(GUIFactory.createLabel("Packet:", 100));
        JFormattedTextField thickInput = GUIFactory.createNumberInput(null, 0L, 1000000L, 80);
        panel.add(thickInput);
        add(panel);

        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(GUIFactory.createLabel("Tip:", 100));
        JComboBox<String> tipCombo = new JComboBox<String>(GUIUtil.types);
        ((DefaultComboBoxModel<String>)tipCombo.getModel()).insertElementAt("", 0);
        tipCombo.setSelectedIndex(0);
        tipCombo.setPreferredSize(new Dimension(80, tipCombo.getPreferredSize().height));
        panel.add(tipCombo);
        add(panel);
    }

    public ProductionProductPalletDTO validateData() {
        boolean status = true;

        JLabel produsLabel = (JLabel)((JPanel)getComponent(0)).getComponent(0);
        JComboBox<Product> produse = (JComboBox<Product>)((JPanel)getComponent(0)).getComponent(1);
        produsLabel.setForeground(Color.black);
        if(produse.getSelectedItem() == null) {
            status = false;
            produsLabel.setForeground(Color.red);
        }

        JLabel quantityLabel = (JLabel)((JPanel)getComponent(1)).getComponent(0);
        JFormattedTextField quantity = (JFormattedTextField)((JPanel)getComponent(1)).getComponent(1);
        quantityLabel.setForeground(Color.black);
        if(quantity.getValue() == null) {
            status = false;
            quantityLabel.setForeground(Color.red);
        }

        JLabel packageLabel = (JLabel)((JPanel)getComponent(2)).getComponent(0);
        JFormattedTextField packageNo = (JFormattedTextField)((JPanel)getComponent(2)).getComponent(1);
        packageLabel.setForeground(Color.black);
        if(packageNo.getValue() == null) {
            status = false;
            packageLabel.setForeground(Color.red);
        }

        JLabel typeLabel = (JLabel)((JPanel)getComponent(3)).getComponent(0);
        JComboBox type = (JComboBox)((JPanel)getComponent(3)).getComponent(1);
        typeLabel.setForeground(Color.black);
        if(type.getSelectedItem() == null || type.getSelectedItem().toString().length()==0) {
            status = false;
            typeLabel.setForeground(Color.red);
        }

        if(!status) {
            return null;
        }

        ProductionProductPalletDTO productPalletDTO = new ProductionProductPalletDTO();
        productPalletDTO.setProduct((Product)produse.getSelectedItem());
        productPalletDTO.setEntryDate(new Date());
        productPalletDTO.setQuantity(((Long)quantity.getValue()).intValue());
        productPalletDTO.setPackageNo(((Long)packageNo.getValue()).intValue());
        productPalletDTO.setLumberType((long)type.getSelectedIndex());
        productPalletDTO.setUser(ConfigLocalManager.currentUser);

        return productPalletDTO;
    }


}
