package ro.barbos.gui.cut;

import ro.barbos.gater.cutprocessor.DefaultCutOptionsCalculatorData;
import ro.barbos.gater.dao.LumberStackDAO;
import ro.barbos.gater.dao.ProductDAO;
import ro.barbos.gater.data.LumberLogUtil;
import ro.barbos.gater.data.METRIC;
import ro.barbos.gater.model.IDPlate;
import ro.barbos.gater.model.LumberLog;
import ro.barbos.gater.model.LumberStack;
import ro.barbos.gater.model.Product;
import ro.barbos.gui.CutOptionsTargetFrame;
import ro.barbos.gui.GUIFactory;
import ro.barbos.gui.GUITools;
import ro.barbos.gui.GUIUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiameterCutOptionsTargetPanel extends JPanel implements ActionListener, ItemListener {

    private static final long serialVersionUID = 1L;

    private JButton see = new JButton("Calculeaza");
    private JButton cancel = new JButton("Anuleaza");
    private List<JCheckBox> selection = new ArrayList<>();
    private JLabel label2;

    List<Product> products;

    public DiameterCutOptionsTargetPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(GUIFactory.createLabel("Lungime:", 100));
        JFormattedTextField length = GUIFactory.createNumberInput(4100L, 0L, 1000000L, 80);
        panel.add(length);
        JComboBox<String> mCombo = new JComboBox<String>(GUIUtil.metric);
        mCombo.addItemListener(this);
        mCombo = new JComboBox<String>(GUIUtil.metric);
        mCombo.setPreferredSize(new Dimension(50, mCombo.getPreferredSize().height));
        panel.add(mCombo);
        add(panel);

        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(GUIFactory.createLabel("Diametru 1:", 100));
        JFormattedTextField smallRadius = GUIFactory.createNumberInput(200L, 0L, 1000000L, 80);
        panel.add(smallRadius);
        mCombo = new JComboBox<String>(GUIUtil.metric);
        mCombo.addItemListener(this);
        mCombo = new JComboBox<String>(GUIUtil.metric);
        mCombo.setPreferredSize(new Dimension(50, mCombo.getPreferredSize().height));
        panel.add(mCombo);
        add(panel);

        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(GUIFactory.createLabel("Diametru 2:", 100));
        JFormattedTextField bigRadius = GUIFactory.createNumberInput(600L, 0L, 1000000L, 80);
        panel.add(bigRadius);
        mCombo = new JComboBox<String>(GUIUtil.metric);
        mCombo.addItemListener(this);
        mCombo = new JComboBox<String>(GUIUtil.metric);
        mCombo.setPreferredSize(new Dimension(50, mCombo.getPreferredSize().height));
        panel.add(mCombo);
        add(panel);



        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        label2 = GUIFactory.createLabel("Produse:", 100);
        panel.add(label2);
        products = ProductDAO.getProducts();
        JPanel productsPanel = new JPanel();
        productsPanel.setLayout(new BoxLayout(productsPanel, BoxLayout.Y_AXIS));
        for(Product product: products) {
            JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JCheckBox chk = new JCheckBox();
            row.add(chk);
            selection.add(chk);
            row.add(new JLabel(product.getName()));
            productsPanel.add(row);
        }
        JScrollPane scrollPane = new JScrollPane(productsPanel);
        scrollPane.setPreferredSize(new Dimension(productsPanel.getPreferredSize().width+20, 215));
        panel.add(scrollPane);
        add(panel);

        cancel.setActionCommand("CANCEL_DIALOG");
        cancel.addActionListener(GUIUtil.main);
        see.addActionListener(this);
    }

    private JButton[] getButtons() {
        return new JButton[]{see, cancel};
    }

    @Override
    public void itemStateChanged(ItemEvent eve) {
        if(eve.getStateChange() == ItemEvent.SELECTED) {

        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        boolean valid = true;

        JLabel lenLabel = (JLabel)((JPanel)getComponent(0)).getComponent(0);
        JFormattedTextField lenRadius = (JFormattedTextField)((JPanel)getComponent(0)).getComponent(1);
        JComboBox<String> lenRadiusMetric = (JComboBox<String>)((JPanel)getComponent(0)).getComponent(2);
        lenLabel.setForeground(Color.black);
        if(lenRadius.getValue() == null) {
            valid = false;
            lenLabel.setForeground(Color.red);
        }
        JLabel smallLabel = (JLabel)((JPanel)getComponent(1)).getComponent(0);
        JFormattedTextField smallRadius = (JFormattedTextField)((JPanel)getComponent(1)).getComponent(1);
        JComboBox<String> smallRadiusMetric = (JComboBox<String>)((JPanel)getComponent(1)).getComponent(2);
        smallLabel.setForeground(Color.black);
        if(smallRadius.getValue() == null) {
            valid = false;
            smallLabel.setForeground(Color.red);
        }

        JLabel bigLabel = (JLabel)((JPanel)getComponent(2)).getComponent(0);
        JFormattedTextField bigRadius = (JFormattedTextField)((JPanel)getComponent(2)).getComponent(1);
        JComboBox<String> bigRadiusMetric = (JComboBox<String>)((JPanel)getComponent(2)).getComponent(2);
        bigLabel.setForeground(Color.black);
        if(bigRadius.getValue() == null) {
            valid = false;
            bigLabel.setForeground(Color.red);
        }

        double minimumValue =  METRIC.toMilimeter((Long)smallRadius.getValue(), smallRadiusMetric.getSelectedIndex());
        double maxmimumValue =  METRIC.toMilimeter((Long)bigRadius.getValue(), bigRadiusMetric.getSelectedIndex());

        if(minimumValue > maxmimumValue) {
            smallLabel.setForeground(Color.red);
            bigLabel.setForeground(Color.red);
            valid = false;
        }

        boolean one = false;
        for(JCheckBox chk: selection) {
            if(chk.isSelected()) {
                one = true;
                break;
            }
        }
        if(!one) {
            label2.setForeground(Color.red);
            valid = false;
        }
        else {
            label2.setForeground(Color.black);
        }

        if(!valid) {
            return;
        }

        List<Product> selectedProducts = new ArrayList<Product>();
        for(int index = 0; index < selection.size();  index++) {
            if(selection.get(index).isSelected()) {
                selectedProducts.add(products.get(index));
            }
        }

        Map<String, Object> targetData = new HashMap<String, Object>();
        targetData.put("IDPLATE_LABEL", "Test");
        targetData.put("SELECTED_PRODUCTS", selectedProducts);
        DefaultCutOptionsCalculatorData data = new DefaultCutOptionsCalculatorData();
        data.setSelectedProducts(selectedProducts);

        List<LumberStack> stacks = LumberStackDAO.getAllstack();
        List<LumberLog> lumberLogs = new ArrayList<>();
        for(long start = (Long)smallRadius.getValue();start<(Long)bigRadius.getValue();start++) {
            LumberLog lumberLog = new LumberLog();
            lumberLog.setSmallRadius((double)start);
            List<Double> middleRadius = new ArrayList<>();
            middleRadius.add((double)start);
            lumberLog.setMediumRadius(middleRadius);
            lumberLog.setBigRadius((double)start);
            double lengthValue = METRIC.toMilimeter((Long) lenRadius.getValue(), lenRadiusMetric.getSelectedIndex());
            lumberLog.setLength(lengthValue);
            lumberLog.setRealLength((long)lengthValue);
            IDPlate plate = new IDPlate();
            plate.setId(-1L);
            LumberStack stack = LumberLogUtil.findLumberStack(lumberLog, stacks);
            String label = "Diametru " + start;
            if(stack != null) {
                label += " " + stack.getName();
            }
            plate.setLabel(label);
            lumberLog.setPlate(plate);
            LumberLogUtil.calculateVolume(lumberLog);
            lumberLogs.add(lumberLog);
        }
        data.setLumberLogs(lumberLogs);
        new CutOptionsTargetFrame(targetData, data);
        GUITools.closeParentDialog(this);
    }



    public static void showDialog() {
        DiameterCutOptionsTargetPanel panel = new DiameterCutOptionsTargetPanel();
        JButton[] buttons = panel.getButtons();
        JOptionPane.showOptionDialog(GUIUtil.container, panel, "Produse de taiat",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, buttons[0]);
    }

}
