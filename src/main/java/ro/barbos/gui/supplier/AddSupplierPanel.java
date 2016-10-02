package ro.barbos.gui.supplier;

import ro.barbos.gater.model.Person;
import ro.barbos.gater.model.Supplier;
import ro.barbos.gui.GUIFactory;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;

public class AddSupplierPanel extends JPanel {

    JTextArea address;
    String[] areaCodes = new String[]{"Alba","Arad","Arges","Bacau","Bihor","Bistrita-Nasaud",
            "Botosani","Braila","Brasov","Bucuresti","Buzau","Calarasi",
            "Caras-Severin","Cluj","Constanta","Covasna","Dambovita","Dolj",
            "Galati","Giurgiu","Gorj","Harghita","Hunedoara","Ialomita",
            "Iasi","Ilfov","Maramures","Mehedinti","Mures","Neamt",
            "Olt","Prahova","Salaj","Satu Mare","Sibiu","Suceava",
            "Teleorman","Timis","Tulcea","Valcea","Vaslui","Vrancea"};

    JPanel contactPanel = new JPanel();

    public AddSupplierPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(GUIFactory.createLabel("Titlu:", 90));
        panel.add(new JTextField(30));
        add(panel);

        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(GUIFactory.createLabel("Nr. registru:", 90));
        panel.add(new JTextField(30));
        add(panel);

        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(GUIFactory.createLabel("CUI:", 90));
        panel.add(new JTextField(30));
        add(panel);

        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(GUIFactory.createLabel("Adresa:", 90));
        address = new JTextArea(7,30);
        address.setWrapStyleWord(true);
        panel.add(new JScrollPane(address));
        add(panel);

        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(GUIFactory.createLabel("Judet:", 90));
        panel.add(new JComboBox<>(new Vector<String>(Arrays.asList(areaCodes))));
        add(panel);


        contactPanel.setLayout(new BoxLayout(contactPanel, BoxLayout.Y_AXIS));
        contactPanel.setBorder(new TitledBorder("Persoana contact"));
        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(GUIFactory.createLabel("Nume:", 90));
        panel.add(new JTextField(30));
        contactPanel.add(panel);
        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(GUIFactory.createLabel("Prenume:", 90));
        panel.add(new JTextField(30));
        contactPanel.add(panel);
        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(GUIFactory.createLabel("Telefon:", 90));
        panel.add(new JTextField(30));
        contactPanel.add(panel);
        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(GUIFactory.createLabel("Email:", 90));
        panel.add(new JTextField(30));
        contactPanel.add(panel);
        add(contactPanel);
    }

    public Supplier validateData() {
        boolean status = true;

        JLabel labelTitle = (JLabel)((JPanel)getComponent(0)).getComponent(0);
        JTextField titleField = (JTextField)((JPanel)getComponent(0)).getComponent(1);
        labelTitle.setForeground(Color.black);
        if(titleField.getText().trim().length() == 0) {
            status = false;
            labelTitle.setForeground(Color.red);
        }

        JLabel labelRegistry = (JLabel) ((JPanel) getComponent(1)).getComponent(0);
        JTextField registryField = (JTextField) ((JPanel) getComponent(1)).getComponent(1);
        labelRegistry.setForeground(Color.black);
        if (registryField.getText().trim().length() == 0) {
            status = false;
            labelRegistry.setForeground(Color.red);
        }

        JLabel labelCui = (JLabel) ((JPanel) getComponent(2)).getComponent(0);
        JTextField cuiField = (JTextField) ((JPanel) getComponent(2)).getComponent(1);
        labelCui.setForeground(Color.black);
        if(cuiField.getText().trim().length() == 0) {
            status = false;
            labelCui.setForeground(Color.red);
        }

        JLabel labelAddress = (JLabel) ((JPanel) getComponent(3)).getComponent(0);
        labelAddress.setForeground(Color.black);
        if(address.getText().trim().length() == 0) {
            status = false;
            labelAddress.setForeground(Color.red);
        }

        JLabel labelAreaCode = (JLabel) ((JPanel) getComponent(4)).getComponent(0);
        JComboBox<String> areaCodeField = (JComboBox<String>) ((JPanel) getComponent(4)).getComponent(1);
        labelAreaCode.setForeground(Color.black);
        if(((String)areaCodeField.getSelectedItem()).trim().length() == 0) {
            status = false;
            labelAreaCode.setForeground(Color.red);
        }

        Person person = new Person();
        JLabel labelName = (JLabel) ((JPanel) contactPanel.getComponent(0)).getComponent(0);
        JTextField nameField = (JTextField) ((JPanel) contactPanel.getComponent(0)).getComponent(1);
        labelName.setForeground(Color.black);
        if (nameField.getText().trim().length() == 0) {
            status = false;
            labelName.setForeground(Color.red);
        }
        person.setLastName(nameField.getText());

        JLabel labelFirstName = (JLabel) ((JPanel) contactPanel.getComponent(1)).getComponent(0);
        JTextField firstNameField = (JTextField) ((JPanel) contactPanel.getComponent(1)).getComponent(1);
        labelFirstName.setForeground(Color.black);
        if (firstNameField.getText().trim().length() == 0) {
            status = false;
            labelFirstName.setForeground(Color.red);
        }
        person.setFirstName(firstNameField.getText());

        JLabel labelPhone = (JLabel) ((JPanel) contactPanel.getComponent(2)).getComponent(0);
        JTextField phoneField = (JTextField) ((JPanel) contactPanel.getComponent(2)).getComponent(1);
        labelPhone.setForeground(Color.black);
        if (phoneField.getText().trim().length() == 0) {
            status = false;
            labelPhone.setForeground(Color.red);
        }
        person.setPhone(phoneField.getText());

        JLabel labelEmail = (JLabel) ((JPanel) contactPanel.getComponent(3)).getComponent(0);
        JTextField emailField = (JTextField) ((JPanel) contactPanel.getComponent(3)).getComponent(1);
        labelEmail.setForeground(Color.black);
        if (emailField.getText().trim().length() == 0) {
            status = false;
            labelEmail.setForeground(Color.red);
        }
        person.setEmail(emailField.getText());

        if(!status) {
            return null;
        }

        Supplier supplier = new Supplier();
        supplier.setTitle(titleField.getText().trim());
        supplier.setRegisterNo(registryField.getText().trim());
        supplier.setRegisterId(cuiField.getText().trim());
        supplier.setAddress(address.getText().trim());
        supplier.setEntryDate(new Date());
        supplier.setAreaCode((String)areaCodeField.getSelectedItem());
        supplier.setContactPerson(person);

        return supplier;
    }


}

