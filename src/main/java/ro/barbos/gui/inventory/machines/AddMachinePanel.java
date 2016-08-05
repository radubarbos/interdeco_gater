package ro.barbos.gui.inventory.machines;

import ro.barbos.gater.model.Machine;
import ro.barbos.gui.GUIFactory;

import javax.swing.*;
import java.awt.*;

public class AddMachinePanel extends JPanel {


    public AddMachinePanel() {

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(GUIFactory.createLabel("Nume:", 60));
        panel.add(new JTextField(10));
        add(panel);

    }

    public Machine validateData() {
        boolean status = true;

        JLabel label = (JLabel)((JPanel)getComponent(0)).getComponent(0);
        JTextField name = (JTextField)((JPanel)getComponent(0)).getComponent(1);
        label.setForeground(Color.black);
        if(name.getText().trim().length() == 0) {
            status = false;
            label.setForeground(Color.red);
        }

        if(!status) {
            return null;
        }

        Machine machine = new Machine();
        machine.setLabel(name.getText().trim());

        return machine;
    }


}

