package ro.barbos.gui.cut;

import ro.barbos.gater.cutprocessor.DefaultCutOptionsCalculatorData;
import ro.barbos.gater.dao.IDPlateDAO;
import ro.barbos.gater.dao.ProductDAO;
import ro.barbos.gater.dao.StockDAO;
import ro.barbos.gater.data.LumberLogUtil;
import ro.barbos.gater.data.METRIC;
import ro.barbos.gater.dto.LumberLogFilterDTO;
import ro.barbos.gater.model.IDPlate;
import ro.barbos.gater.model.LumberLog;
import ro.barbos.gater.model.Product;
import ro.barbos.gater.model.ProductCombinator;
import ro.barbos.gater.stock.StockSettings;
import ro.barbos.gui.CutOptionsTargetFrame;
import ro.barbos.gui.GUIFactory;
import ro.barbos.gui.GUITools;
import ro.barbos.gui.GUIUtil;
import ro.barbos.gui.exswing.SuggestionJComboBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.*;
import java.util.List;

public class LumberLogCutAnalysisPanel extends JPanel implements ActionListener, ItemListener {

    private JButton see = new JButton("Calculeaza");
    private JButton cancel = new JButton("Anuleaza");
    private LumberLog editLumberLog = null;

    public LumberLogCutAnalysisPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(GUIFactory.createLabel("Placuta:", 180));
        SuggestionJComboBox<IDPlate> plates = new SuggestionJComboBox<IDPlate>(IDPlateDAO.getUsedPlates().toArray(new IDPlate[0]));
        plates.addItemListener(this);
        plates.setPreferredSize(new Dimension(80, plates.getPreferredSize().height));
        panel.add(plates);
        add(panel);

        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(GUIFactory.createLabel("Lungime:", 180));
        JFormattedTextField lengthInput = GUIFactory.createNumberInput(null, 0L, 1000000L, 80);

        panel.add(lengthInput);
        JComboBox<String> mCombo = new JComboBox<String>(GUIUtil.metric);
        mCombo.addItemListener(this);
        mCombo.setPreferredSize(new Dimension(50, mCombo.getPreferredSize().height));
        panel.add(mCombo);
        add(panel);

        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(GUIFactory.createLabel("Diametru mic:", 180));
        JFormattedTextField smallRadius = GUIFactory.createNumberInput(null, 0L, 1000000L, 80);
        panel.add(smallRadius);
        mCombo = new JComboBox<String>(GUIUtil.metric);
        mCombo.setPreferredSize(new Dimension(50, mCombo.getPreferredSize().height));

        panel.add(mCombo);
        add(panel);


        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(GUIFactory.createLabel("Diametru mare:", 180));
        JFormattedTextField bigRadius = GUIFactory.createNumberInput(null, 0L, 1000000L, 80);
        panel.add(bigRadius);
        mCombo = new JComboBox<String>(GUIUtil.metric);
        mCombo.setPreferredSize(new Dimension(50, mCombo.getPreferredSize().height));

        panel.add(mCombo);
        add(panel);


        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 20));

        cancel.setActionCommand("CANCEL_DIALOG");
        cancel.addActionListener(GUIUtil.main);
        see.addActionListener(this);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == see) {
            LumberLog lumberLog = validateData();
            if(lumberLog == null) {
                return;
            }
            DefaultCutOptionsCalculatorData data = new DefaultCutOptionsCalculatorData();

            ProductCombinator combinator = new ProductCombinator();
            List<List<Product>> productsCombined = new ArrayList<>();
            productsCombined.addAll(combinator.combineProducts(ProductDAO.getProducts(),2,true));
            data.setProducts(productsCombined);
            data.setLumberLog(lumberLog);

            Map<String, Object> targetData = new HashMap<String, Object>();
            targetData.put("IDPLATE_LABEL", "test");
            new CutOptionsTargetFrame(targetData, data);
            GUITools.closeParentDialog(this);
        }
    }




    /* (non-Javadoc)
     * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
     */
    @Override
    public void itemStateChanged(ItemEvent eve) {
        if(eve.getStateChange() == ItemEvent.SELECTED) {
            SuggestionJComboBox plates = (SuggestionJComboBox)((JPanel)getComponent(0)).getComponent(1);
            IDPlate plate = (IDPlate)plates.getSelectedItem();
            if(plate != null) {
                LumberLogFilterDTO filter = new LumberLogFilterDTO();
                filter.setIdPlates(Arrays.asList(plate.getId()));
                List<LumberLog> lumberLogs = StockDAO.getJustCurrentLumbersLogs(filter);
                if(lumberLogs != null && !lumberLogs.isEmpty()) {
                    LumberLog lumberLog = lumberLogs.get(0);
                    JFormattedTextField lenRadius = (JFormattedTextField)((JPanel)getComponent(1)).getComponent(1);
                    lenRadius.setValue(lumberLog.getLength().longValue());
                    JFormattedTextField smallRadius = (JFormattedTextField)((JPanel)getComponent(2)).getComponent(1);
                    smallRadius.setValue(lumberLog.getSmallRadius().longValue());
                    JFormattedTextField bigRadius = (JFormattedTextField)((JPanel)getComponent(3)).getComponent(1);
                    bigRadius.setValue(lumberLog.getBigRadius().longValue());
                }
            }
        }
    }

    public LumberLog validateData() {
        boolean status = true;


        JLabel lenLabel = (JLabel)((JPanel)getComponent(1)).getComponent(0);
        JFormattedTextField lenRadius = (JFormattedTextField)((JPanel)getComponent(1)).getComponent(1);
        JComboBox<String> lenRadiusMetric = (JComboBox<String>)((JPanel)getComponent(1)).getComponent(2);
        lenLabel.setForeground(Color.black);
        if(lenRadius.getValue() == null) {
            status = false;
            lenLabel.setForeground(Color.red);
        }
        JLabel smallLabel = (JLabel)((JPanel)getComponent(2)).getComponent(0);
        JFormattedTextField smallRadius = (JFormattedTextField)((JPanel)getComponent(2)).getComponent(1);
        JComboBox<String> smallRadiusMetric = (JComboBox<String>)((JPanel)getComponent(2)).getComponent(2);
        smallLabel.setForeground(Color.black);
        if(smallRadius.getValue() == null) {
            status = false;
            smallLabel.setForeground(Color.red);
        }

        JLabel bigLabel = (JLabel)((JPanel)getComponent(3)).getComponent(0);
        JFormattedTextField bigRadius = (JFormattedTextField)((JPanel)getComponent(3)).getComponent(1);
        JComboBox<String> bigRadiusMetric = (JComboBox<String>)((JPanel)getComponent(3)).getComponent(2);
        bigLabel.setForeground(Color.black);
        if(bigRadius.getValue() == null) {
            status = false;
            bigLabel.setForeground(Color.red);
        }

        double minimumValue =  METRIC.toMilimeter((Long)smallRadius.getValue(), smallRadiusMetric.getSelectedIndex());
        double maxmimumValue =  METRIC.toMilimeter((Long)bigRadius.getValue(), bigRadiusMetric.getSelectedIndex());

        if(!status) {
            return null;
        }



        if(minimumValue > maxmimumValue) {
            smallLabel.setForeground(Color.red);
            bigLabel.setForeground(Color.red);
            status = false;
        }


        if(!status) {
            return null;
        }


        LumberLog lumberLog = new LumberLog();
        lumberLog.setSmallRadius(minimumValue);
        List<Double> middleRadius = new ArrayList<>();
        middleRadius.add(minimumValue);
        lumberLog.setMediumRadius(middleRadius);
        lumberLog.setBigRadius((double) maxmimumValue);
        lumberLog.setMetric(METRIC.MILIMETER);

        double lengthValue =  METRIC.toMilimeter((Long)lenRadius.getValue(), lenRadiusMetric.getSelectedIndex());
        lumberLog.setLength(lengthValue);
        lumberLog.setRealLength((long)lengthValue);
        IDPlate plate = new IDPlate();
        plate.setId(-1L);
        plate.setLabel("Test");
        lumberLog.setPlate(plate);


        //extra validation
        String warning = "";
        if(lengthValue < StockSettings.RECEIVE_MIN_LENGTH) {
            warning += "Lungime i mai mica ca limita admisa" + System.lineSeparator();
        }
        if(lengthValue > StockSettings.RECEIVE_MAX_LENGTH) {
            warning += "Lungime i mai mare ca limita admisa" + System.lineSeparator();
        }
        LumberLogUtil.calculateVolume(lumberLog);
        if(lumberLog.getVolume()/1000000000L > StockSettings.RECEIVE_MAX_VOLUME) {
            warning += "Volumul i mai mare ca limita admisa" + System.lineSeparator();
        }
        if(warning.length()>0) {
            int confirmation = JOptionPane.showConfirmDialog(null, warning);
            if(confirmation!=JOptionPane.YES_OPTION) {
                return null;
            }
        }

        if(editLumberLog != null) {
            lumberLog.setId(editLumberLog.getId());
        }

        return lumberLog;
    }



    private JButton[] getButtons() {
        return new JButton[]{see, cancel};
    }

    public static void showDialog() {
        LumberLogCutAnalysisPanel panel = new LumberLogCutAnalysisPanel();
        JButton[] buttons = panel.getButtons();
        JOptionPane.showOptionDialog(GUIUtil.container, panel, "Analiza produse pe bustean",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, buttons[0]);
    }

}
