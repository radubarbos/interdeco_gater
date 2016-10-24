package ro.barbos.gui;

import org.apache.shiro.SecurityUtils;
import ro.barbos.gater.dao.*;
import ro.barbos.gater.dto.LumberLogFilterDTO;
import ro.barbos.gater.model.IDPlate;
import ro.barbos.gater.model.LumberLogStockEntry;
import ro.barbos.gater.model.Machine;
import ro.barbos.gui.cut.DiameterCutOptionsTargetPanel;
import ro.barbos.gui.cut.LumberLogCutAnalysisPanel;
import ro.barbos.gui.exswing.SuggestionJComboBox;
import ro.barbos.gui.inventory.machines.InventoryMachineFrame;
import ro.barbos.gui.production.MachineProductionFrame;
import ro.barbos.gui.stock.*;
import ro.barbos.gui.supplier.SuppliersFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeftPanel extends JPanel implements ActionListener {

    private JTextArea messageArea;
    SuggestionJComboBox<IDPlate> optPlates;

    JPanel north = new JPanel();
    JPanel center = new JPanel();
    JPanel south = new JPanel();

    List<JButton> menu = new ArrayList<>();
    Map<Integer, List<JButton>> subMenu = new HashMap<>();

    public LeftPanel() {

        int buttonWidth = 200;
        int buttonHeight = 30;
        Dimension buttonDimension = new Dimension(buttonWidth, buttonHeight);

        setLayout(new BorderLayout());
        north.setLayout(new BoxLayout(north, BoxLayout.Y_AXIS));
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        south.setLayout(new BoxLayout(south, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        setMinimumSize(new Dimension(buttonWidth, buttonHeight));

        Integer rights = ConfigLocalManager.currentUser.getRights().getRightsLevel();

        JButton stock = new JButton("Stoc");
        stock.setActionCommand("MENU_GROUP_0");
        stock.addActionListener(this);
        stock.setAlignmentX(0);
        stock.setMaximumSize(buttonDimension);

        List<JButton> stockMenu = new ArrayList<>();
        subMenu.put(0, stockMenu);
        JButton receptie = new JButton("Receptie");
        receptie.setActionCommand("RECEPTIE");
        receptie.addActionListener(this);
        receptie.setAlignmentX(0);
        receptie.setMaximumSize(buttonDimension);
        Color subMenuColor = new Color(receptie.getBackground().getRed() - 20, receptie.getBackground().getGreen() - 20, receptie.getBackground().getBlue());
        receptie.setBackground(subMenuColor);
        stockMenu.add(receptie);

        JButton stockCurrent = new JButton("Stock current");
        stockCurrent.setActionCommand("STOCK");
        stockCurrent.addActionListener(this);
        stockCurrent.setAlignmentX(0);
        stockCurrent.setMaximumSize(buttonDimension);
        stockCurrent.setBackground(subMenuColor);
        stockMenu.add(stockCurrent);

        JButton stockCurrent2 = new JButton("Stock per stiva");
        stockCurrent2.setActionCommand("STACK_STOCK");
        stockCurrent2.addActionListener(this);
        stockCurrent2.setAlignmentX(0);
        stockCurrent2.setMaximumSize(buttonDimension);
        stockCurrent2.setBackground(subMenuColor);
        stockMenu.add(stockCurrent2);

        JButton stockCurrent3 = new JButton("Stock per tip");
        stockCurrent3.setActionCommand("TIP_STOCK");
        stockCurrent3.addActionListener(this);
        stockCurrent3.setAlignmentX(0);
        stockCurrent3.setMaximumSize(buttonDimension);
        stockCurrent3.setBackground(subMenuColor);
        stockMenu.add(stockCurrent3);

        JButton stockCurrent4 = new JButton("Stock per class");
        stockCurrent4.setActionCommand("CLASS_STOCK");
        stockCurrent4.addActionListener(this);
        stockCurrent4.setAlignmentX(0);
        stockCurrent4.setMaximumSize(buttonDimension);
        stockCurrent4.setBackground(subMenuColor);
        stockMenu.add(stockCurrent4);

        JButton stockCurrent9 = new JButton("Furnizori");
        stockCurrent9.setActionCommand("SUPPLIERS_KEY");
        stockCurrent9.addActionListener(this);
        stockCurrent9.setAlignmentX(0);
        stockCurrent9.setMaximumSize(buttonDimension);
        stockCurrent9.setBackground(subMenuColor);


        JButton stockCurrent10 = new JButton("Avize");
        stockCurrent10.setActionCommand("LUMBER_TRANSPORT_STOCK");
        stockCurrent10.addActionListener(this);
        stockCurrent10.setAlignmentX(0);
        stockCurrent10.setMaximumSize(buttonDimension);
        stockCurrent10.setBackground(subMenuColor);


        if (SecurityUtils.getSubject().hasRole("administrator")) {
            stockMenu.add(stockCurrent9);
            stockMenu.add(stockCurrent10);
        }

        JButton stockCurrent11 = new JButton("Situatii receptii");
        stockCurrent11.setActionCommand("TRANSPORT_STOCK_ENTRIES");
        stockCurrent11.addActionListener(this);
        stockCurrent11.setAlignmentX(0);
        stockCurrent11.setMaximumSize(buttonDimension);
        stockCurrent11.setBackground(subMenuColor);
        stockMenu.add(stockCurrent11);


        JButton idplates = new JButton("Placi");
        idplates.setActionCommand("IDPLATES");
        idplates.addActionListener(this);
        idplates.setAlignmentX(0);
        idplates.setMaximumSize(buttonDimension);

        JButton stacks = new JButton("Stive");
        stacks.setActionCommand("STACKS");
        stacks.addActionListener(this);
        stacks.setAlignmentX(0);
        stacks.setMaximumSize(buttonDimension);

        JButton stockCurrent5 = new JButton("Istoric procesat");
        stockCurrent5.setActionCommand("PROCESSED_HISTORY");
        stockCurrent5.addActionListener(this);
        stockCurrent5.setAlignmentX(0);
        stockCurrent5.setMaximumSize(buttonDimension);


        JButton settings = new JButton("Setari");
        settings.setActionCommand("SETTINGS");
        settings.addActionListener(this);
        settings.setAlignmentX(0);
        settings.setMaximumSize(buttonDimension);


        List<JButton> cutPlansMenu = new ArrayList<>();
        subMenu.put(2, cutPlansMenu);
        JButton cutPlans = new JButton("Plan taiere");
        cutPlans.setActionCommand("MENU_GROUP_2");
        cutPlans.addActionListener(this);
        cutPlans.setAlignmentX(0);
        cutPlans.setMaximumSize(buttonDimension);

        JButton cutPlan = new JButton("Crearea plan taiere");
        cutPlan.setActionCommand("CUT_PLAN");
        cutPlan.addActionListener(this);
        cutPlan.setAlignmentX(0);
        cutPlan.setMaximumSize(buttonDimension);
        cutPlan.setBackground(subMenuColor);
        cutPlansMenu.add(cutPlan);

        JButton cutPlanHistory = new JButton("Planuri");
        cutPlanHistory.setActionCommand("HISTORY_CUT_PLAN");
        cutPlanHistory.addActionListener(this);
        cutPlanHistory.setAlignmentX(0);
        cutPlanHistory.setMaximumSize(buttonDimension);
        cutPlanHistory.setBackground(subMenuColor);
        cutPlansMenu.add(cutPlanHistory);

        List<JButton> taiereMenu = new ArrayList<>();
        subMenu.put(3, taiereMenu);
        JButton taiere = new JButton("Taieri");
        taiere.setActionCommand("MENU_GROUP_3");
        taiere.addActionListener(this);
        taiere.setAlignmentX(0);
        taiere.setMaximumSize(buttonDimension);

        JButton cut = new JButton("Simulare taiere");
        cut.setActionCommand("CUT_SIMULATION");
        cut.addActionListener(this);
        cut.setAlignmentX(0);
        cut.setMaximumSize(buttonDimension);
        cut.setBackground(subMenuColor);
        taiereMenu.add(cut);

        JButton mprocessed = new JButton("Marcheaza procesat");
        mprocessed.setActionCommand("MARK_PROCESSED");
        mprocessed.addActionListener(this);
        mprocessed.setAlignmentX(0);
        mprocessed.setMaximumSize(buttonDimension);
        mprocessed.setBackground(subMenuColor);
        taiereMenu.add(mprocessed);

        JButton optionCutDiagram = new JButton("Optiuni taiere bustean");
        optionCutDiagram.setActionCommand("SEE_CUT_OPTION_DIAGRAM");
        optionCutDiagram.addActionListener(this);
        optionCutDiagram.setAlignmentX(0);
        optionCutDiagram.setMaximumSize(buttonDimension);
        optionCutDiagram.setBackground(subMenuColor);
        taiereMenu.add(optionCutDiagram);

        JButton logout = new JButton("Iesire");
        logout.setActionCommand("LOGOUT");
        logout.addActionListener(GUIUtil.main);
        logout.setAlignmentX(0);
        logout.setMaximumSize(buttonDimension);

        JButton products = new JButton("Produse");
        products.setActionCommand("PRODUCTS");
        products.addActionListener(this);
        products.setAlignmentX(0);
        products.setMaximumSize(buttonDimension);

        List<JButton> processedMenu = new ArrayList<>();
        subMenu.put(7, processedMenu);
        JButton processed = new JButton("Procesare");
        processed.setActionCommand("MENU_GROUP_7");
        processed.addActionListener(this);
        processed.setAlignmentX(0);
        processed.setMaximumSize(buttonDimension);
        List<Machine> machines = MachineDAO.getMachines();
        for (Machine machine : machines) {
            JButton machineMenu = new JButton(machine.getLabel());
            machineMenu.setActionCommand("INVENTORY_MACHINE_" + machine.getId());
            machineMenu.addActionListener(this);
            machineMenu.setAlignmentX(0);
            machineMenu.setMaximumSize(buttonDimension);
            machineMenu.setBackground(subMenuColor);
            processedMenu.add(machineMenu);
        }

        List<JButton> inventoryMenu = new ArrayList<>();
        subMenu.put(8, inventoryMenu);
        JButton inventory = new JButton("Inventar");
        inventory.setActionCommand("MENU_GROUP_8");
        inventory.addActionListener(this);
        inventory.setAlignmentX(0);
        inventory.setMaximumSize(buttonDimension);

        JButton inventoryMachines = new JButton("Masini");
        inventoryMachines.setActionCommand("INVENTORY_MACHINES");
        inventoryMachines.addActionListener(this);
        inventoryMachines.setAlignmentX(0);
        inventoryMachines.setMaximumSize(buttonDimension);
        inventoryMachines.setBackground(subMenuColor);
        inventoryMenu.add(inventoryMachines);

        JButton users = new JButton("Utilizatori");
        users.setActionCommand("USERS");
        users.addActionListener(this);
        users.setAlignmentX(0);
        users.setMaximumSize(buttonDimension);

        JButton db2 = new JButton("Fix database");
        db2.setActionCommand("FIX");
        db2.addActionListener(this);
        db2.setAlignmentX(0);
        db2.setMaximumSize(buttonDimension);

        JButton db = new JButton("Analiza taiere produse");
        db.setActionCommand("CUT_PRODUCTS_ANALYSIS");
        db.addActionListener(this);
        db.setAlignmentX(0);
        db.setMaximumSize(buttonDimension);
        db.setBackground(subMenuColor);
        taiereMenu.add(db);


        JButton diameterAnalisis = new JButton("Analiza taiere diametru");
        diameterAnalisis.setActionCommand("CUT_PRODUCTS_LUMBER_RADIUS_ANALYSIS");
        diameterAnalisis.addActionListener(this);
        diameterAnalisis.setAlignmentX(0);
        diameterAnalisis.setMaximumSize(buttonDimension);
        diameterAnalisis.setBackground(subMenuColor);
        taiereMenu.add(diameterAnalisis);

        menu.add(stock);
        if (SecurityUtils.getSubject().hasRole("administrator")) {
            menu.add(stockCurrent5);
            menu.add(cutPlans);
            menu.add(taiere);
            menu.add(idplates);
            menu.add(stacks);
            menu.add(products);
            menu.add(processed);
            menu.add(inventory);
            menu.add(users);
            menu.add(settings);
        }

        //menu.add(db2);
        menu.add(logout);
        add(north, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);
        refreshMenuLayout(menu.size());
    }

    private void refreshMenuLayout(int openIndex) {
        north.removeAll();
        center.removeAll();
        south.removeAll();
        boolean open = false;
        for (int index = 0; index < menu.size(); index++) {
            if (index <= openIndex) {
                north.add(menu.get(index));
                north.add(Box.createVerticalStrut(3));
            }
            if (index == openIndex && subMenu.containsKey(index)) {
                List<JButton> subMenuList = subMenu.get(index);
                for (JButton button : subMenuList) {
                    center.add(button);
                    center.add(Box.createVerticalStrut(3));
                }
                open = true;
            }
            if (index > openIndex) {
                if (open) {
                    south.add(menu.get(index));
                    south.add(Box.createVerticalStrut(3));
                } else {
                    north.add(menu.get(index));
                    north.add(Box.createVerticalStrut(3));
                }
            }
        }
        revalidate();
    }

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        MainContainer parent = GUIUtil.container;
        String command = e.getActionCommand();
        if (command == null) {
            return;
        } else if (command.startsWith("MENU_GROUP_")) {
            int index = Integer.valueOf(command.substring("MENU_GROUP_".length()));
            refreshMenuLayout(index);
        } else if (command.equals("RECEPTIE")) {
            if (!parent.isFrameSet(GUIUtil.RECEPTIE_KEY)) {
                ReceiveFrame frame = new ReceiveFrame();
                parent.addFrame(frame, GUIUtil.RECEPTIE_KEY);
            }
        } else if (command.equals("STOCK")) {
            if (!parent.isFrameSet(GUIUtil.STOCK_KEY)) {
                CurrentStockFrame frame = new CurrentStockFrame();
                parent.addFrame(frame, GUIUtil.STOCK_KEY);
            }
        } else if (command.equals("STACK_STOCK")) {
            if (!parent.isFrameSet(GUIUtil.STACKS_STOCKS_KEY)) {
                CurrentStackStockFrame frame = new CurrentStackStockFrame();
                parent.addFrame(frame, GUIUtil.STACKS_STOCKS_KEY);
            }
        } else if (command.equals("TIP_STOCK")) {
            if (!parent.isFrameSet(GUIUtil.TYPE_STOCKS_KEY)) {
                CurrentTypeStockFrame frame = new CurrentTypeStockFrame();
                parent.addFrame(frame, GUIUtil.TYPE_STOCKS_KEY);
            }
        } else if (command.equals("SUPPLIER_STOCK")) {
            if (!parent.isFrameSet(GUIUtil.SUPPLIER_STOCK_KEY)) {
                SupplierStockFrame frame = new SupplierStockFrame();
                parent.addFrame(frame, GUIUtil.SUPPLIER_STOCK_KEY);
            }
        } else if (command.equals("LENGTH_STOCK")) {
            if (!parent.isFrameSet(GUIUtil.LENGTH_STOCK_KEY)) {
                LengthStockFrame frame = new LengthStockFrame();
                parent.addFrame(frame, GUIUtil.LENGTH_STOCK_KEY);
            }
        } else if (command.equals("CLASS_STOCK")) {
            if (!parent.isFrameSet(GUIUtil.CLASS_STOCKS_KEY)) {
                CurrentClassStockFrame frame = new CurrentClassStockFrame();
                parent.addFrame(frame, GUIUtil.CLASS_STOCKS_KEY);
            }
        } else if (command.equals("PROCESSED_HISTORY")) {
            if (!parent.isFrameSet(GUIUtil.PROCESSED_HISTORY_KEY)) {
                ProcessedHistoryFrame frame = new ProcessedHistoryFrame();
                parent.addFrame(frame, GUIUtil.PROCESSED_HISTORY_KEY);
            }
        } else if (command.equals("IDPLATES")) {
            if (!parent.isFrameSet(GUIUtil.IDPLATE_KEY)) {
                IDPlateFrame frame = new IDPlateFrame();
                parent.addFrame(frame, GUIUtil.IDPLATE_KEY);
            }
        } else if (command.equals("STACKS")) {
            if (!parent.isFrameSet(GUIUtil.STACKS_KEY)) {
                LumberStackFrame frame = new LumberStackFrame();
                parent.addFrame(frame, GUIUtil.STACKS_KEY);
            }
        } else if (command.equals("SETTINGS")) {
            if (!parent.isFrameSet(GUIUtil.SETTINGS_KEY)) {
                SettingsFrame frame = new SettingsFrame();
                parent.addFrame(frame, GUIUtil.SETTINGS_KEY);
            }
        } else if (command.equals("CUT_SIMULATION")) {
            if (!parent.isFrameSet(GUIUtil.CUT_SIMULATION_KEY)) {
                CutSimulationFrame frame = new CutSimulationFrame();
                parent.addFrame(frame, GUIUtil.CUT_SIMULATION_KEY);
            }
        } else if (command.equals("MARK_PROCESSED")) {
            List<IDPlate> plates = IDPlateDAO.getUsedPlates();
            //plates.add(0, null);
            //optPlates = new JComboBox<IDPlate>(plates.toArray(new IDPlate[0]));
            optPlates = new SuggestionJComboBox<IDPlate>(plates.toArray(new IDPlate[0]));
            optPlates.setPreferredSize(new Dimension(100, optPlates.getPreferredSize().height));
            JPanel markPanel = new JPanel();
            markPanel.setLayout(new BoxLayout(markPanel, BoxLayout.Y_AXIS));
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            panel.add(createLabel("Placuta:"));
            panel.add(optPlates);
            markPanel.add(panel);
            panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            panel.add(createLabel("Mesaj:"));
            messageArea = new JTextArea(5, 20);
            messageArea.setWrapStyleWord(true);
            panel.add(new JScrollPane(messageArea));
            markPanel.add(panel);
            int rasp = JOptionPane.showOptionDialog(GUIUtil.container, markPanel, "Marcare bustean procesat",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (rasp == JOptionPane.YES_OPTION) {
                IDPlate plate = (IDPlate) optPlates.getSelectedItem();
                String message = messageArea.getText();
                if (plate == null) {
                    JOptionPane.showMessageDialog(GUIUtil.container, "Nu a fost selectat nici o placuta");
                    return;
                }
                LumberLogFilterDTO filter = new LumberLogFilterDTO();
                List<Long> platesId = new ArrayList<>();
                platesId.add(plate.getId());
                filter.setIdPlates(platesId);
                List<LumberLogStockEntry> logs = StockDAO.getCurrentLumbersLogs(filter);
                if (logs.size() != 1) {
                    //err
                    return;
                }
                boolean status = LumberLogDAO.markProcessedLumberLog(logs.get(0).getLumberLog(), message);
                if (!status) {
                    JOptionPane.showMessageDialog(GUIUtil.container, "Busteanul nu a fost marcat ca procesat", "Erroare", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(GUIUtil.container, "Busteanul a fost marcat.");
                }
            }
        } else if (command.equals("CUT_PLAN")) {
            if (!parent.isFrameSet(GUIUtil.CUT_PLAN_KEY)) {
                CutPlanFrame frame = new CutPlanFrame();
                parent.addFrame(frame, GUIUtil.CUT_PLAN_KEY);
            }
        } else if (command.equals("PRODUCTS")) {
            if (!parent.isFrameSet(GUIUtil.PRODUCT_KEY)) {
                ProductsFrame frame = new ProductsFrame();
                parent.addFrame(frame, GUIUtil.PRODUCT_KEY);
            }
        } else if (command.equals("HISTORY_CUT_PLAN")) {
            if (!parent.isFrameSet(GUIUtil.HISTORY_CUT_PLAN_KEY)) {
                CutPlanHistoryFrame frame = new CutPlanHistoryFrame();
                parent.addFrame(frame, GUIUtil.HISTORY_CUT_PLAN_KEY);
            }
        } else if (command.equals("USERS")) {
            if (!parent.isFrameSet(GUIUtil.USERS_KEY)) {
                UsersFrame frame = new UsersFrame();
                parent.addFrame(frame, GUIUtil.USERS_KEY);
            }
        } else if (command.equals("SEE_CUT_OPTION_DIAGRAM")) {
            CutOptionsTargetPanel.showDialog();
        } else if (command.equals("FIX")) {
            Connection con = null;
            Statement stm = null;
            try {
                con = DataAccess.getInstance().getDatabaseConnection();
                con.setAutoCommit(true);
                stm = con.createStatement();
                stm.executeUpdate("alter table cutplanproduct add ProductVolume decimal(20,2);");
            } catch (Exception eee) {
            } finally {
                if (stm != null) try {
                    stm.close();
                } catch (Exception er) {
                }
            }

            try {
                con = DataAccess.getInstance().getDatabaseConnection();
                con.setAutoCommit(true);
                stm = con.createStatement();
                stm.executeUpdate("alter table cutplanlumberlogdiagram add SmallDiameter decimal(10,2) NOT NULL,\n" +
                        "                                 add MediumDiameter decimal(10,2) NOT NULL,\n" +
                        "                                 add BigDiameter decimal(10,2) NOT NULL,\n" +
                        "                                 add Length decimal(10,2) NOT NULL,\n" +
                        "                                 add Volume decimal(20,2) NOT NULL,\n" +
                        "                                 add Reallength decimal(10,2) NOT NULL,\n" +
                        "                                 add Realvolume decimal(20,2) NOT NULL;");
            } catch (Exception eee) {
            } finally {
                if (stm != null) try {
                    stm.close();
                } catch (Exception er) {
                }
            }
            JOptionPane.showMessageDialog(GUIUtil.container, "Baza de date a fost modificata.");
        } else if (command.equals("CUT_PRODUCTS_ANALYSIS")) {
            LumberLogCutAnalysisPanel.showDialog();
        } else if (command.equals("CUT_PRODUCTS_LUMBER_RADIUS_ANALYSIS")) {
            DiameterCutOptionsTargetPanel.showDialog();
        } else if (command.equals("INVENTORY_MACHINES")) {
            if (!parent.isFrameSet(GUIUtil.INVENTORY_MACHINE_KEY)) {
                InventoryMachineFrame frame = new InventoryMachineFrame();
                parent.addFrame(frame, GUIUtil.INVENTORY_MACHINE_KEY);
            }
        } else if (command.startsWith("INVENTORY_MACHINE_")) {
            if (!parent.isFrameSet(GUIUtil.INVENTORY_MACHINE_SINGLE_KEY)) {
                MachineProductionFrame frame = new MachineProductionFrame(Long.valueOf(command.substring("INVENTORY_MACHINE_".length())));
                parent.addFrame(frame, GUIUtil.INVENTORY_MACHINE_SINGLE_KEY);
            }
        } else if (command.equals("SUPPLIERS_KEY")) {
            if (!parent.isFrameSet(GUIUtil.SUPPLIERS_KEY)) {
                SuppliersFrame frame = new SuppliersFrame();
                parent.addFrame(frame, GUIUtil.SUPPLIERS_KEY);
            }
        } else if (command.equals("LUMBER_TRANSPORT_STOCK")) {
            if (!parent.isFrameSet(GUIUtil.LUMBER_TRANSPORT_STOCK_EY)) {
                LumberLogTransportCertificateFrame frame = new LumberLogTransportCertificateFrame();
                parent.addFrame(frame, GUIUtil.LUMBER_TRANSPORT_STOCK_EY);
            }
        } else if (command.equals("TRANSPORT_STOCK_ENTRIES")) {
            if (!parent.isFrameSet(GUIUtil.TRANSPORT_STOCK_ENTRIES_KEY)) {
                LumberLogTransportEntryFrame frame = new LumberLogTransportEntryFrame();
                parent.addFrame(frame, GUIUtil.TRANSPORT_STOCK_ENTRIES_KEY);
            }
        }
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel();
        label.setText(text);
        label.setPreferredSize(new Dimension(100, label.getPreferredSize().height));
        return label;
    }


}
