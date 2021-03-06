package ro.barbos.gui.stock;

import ro.barbos.gater.dao.LumberLogDAO;
import ro.barbos.gater.dao.StockDAO;
import ro.barbos.gater.data.LumberLogUtil;
import ro.barbos.gater.data.METRIC;
import ro.barbos.gater.data.MetricTools;
import ro.barbos.gater.model.LumberLog;
import ro.barbos.gater.model.LumberLogStockEntry;
import ro.barbos.gui.*;
import ro.barbos.gui.renderer.GeneralTableRenderer;
import ro.barbos.gui.tablemodel.FullStockModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.NumberFormat;
import java.util.List;

public class CurrentStockFrame extends GeneralFrame implements ActionListener {


    private static final long serialVersionUID = 1L;
    JPanel north;
    FullStockModel stockModel;
    JTable stockTable;

    JLabel totalLabel;
    double totalVolume = 0;
    JLabel useableLabel;
    double useableVolume = 0;
    JLabel allTotalLabel;

    JLabel avgCostLabel;
    double avgCost = 0;
    JLabel avgMarginLabel;
    double avgMargin = 0;

    private NumberFormat numberFormatter = NumberFormat.getInstance(ConfigLocalManager.locale);

    MarkProcessedLumberPanel markPanel;
    AddLogLumberPanel editLogLumberPanel;
    LumberLog lumberLog;
    private int currentRow = -1;

    public CurrentStockFrame() {
        super();

        setTitle("Stoc current");
        setResizable(true);
        setMaximizable(true);
        setIconifiable(true);
        setClosable(true);

        numberFormatter.setMaximumFractionDigits(2);
        numberFormatter.setMinimumFractionDigits(2);

        north = new JPanel();
        north.setLayout(null);
        north.setPreferredSize(new Dimension(70, 140));
        totalLabel = new JLabel();
        totalLabel.setText("Total metric cubi: ");
        totalLabel.setLocation(20, 10);
        totalLabel.setFont(new Font("arial", Font.BOLD, 16));
        totalLabel.setSize(new Dimension(totalLabel.getPreferredSize().width + 400, totalLabel.getPreferredSize().height));
        north.add(totalLabel);

        useableLabel = new JLabel();
        useableLabel.setText("Total coaja cubi: ");
        useableLabel.setLocation(300, 10);
        useableLabel.setFont(new Font("arial", Font.BOLD, 16));
        useableLabel.setSize(new Dimension(useableLabel.getPreferredSize().width + 400, useableLabel.getPreferredSize().height));
        north.add(useableLabel);

        allTotalLabel = new JLabel();
        allTotalLabel.setText("Total: ");
        allTotalLabel.setLocation(600, 10);
        allTotalLabel.setFont(new Font("arial", Font.BOLD, 16));
        allTotalLabel.setSize(new Dimension(allTotalLabel.getPreferredSize().width + 400, allTotalLabel.getPreferredSize().height));
        north.add(allTotalLabel);

        avgCostLabel = new JLabel();
        avgCostLabel.setText("Cost mediu: ");
        avgCostLabel.setLocation(20, 40);
        avgCostLabel.setFont(new Font("arial", Font.BOLD, 16));
        avgCostLabel.setSize(new Dimension(avgCostLabel.getPreferredSize().width + 400, avgCostLabel.getPreferredSize().height));
        north.add(avgCostLabel);

        avgMarginLabel = new JLabel();
        avgMarginLabel.setText("Cost mediu coaja: ");
        avgMarginLabel.setLocation(300, 40);
        avgMarginLabel.setFont(new Font("arial", Font.BOLD, 16));
        avgMarginLabel.setSize(new Dimension(avgMarginLabel.getPreferredSize().width + 400, avgMarginLabel.getPreferredSize().height));
        north.add(avgMarginLabel);

        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEADING));
        JButton edit = new JButton("Modifica", new ImageIcon(
                GUITools.getImage("resources/edit24.png")));
        edit.setVerticalTextPosition(SwingConstants.BOTTOM);
        edit.setHorizontalTextPosition(SwingConstants.CENTER);
        edit.setToolTipText("Modifica bustean");
        edit.setActionCommand("EDIT");
        edit.addActionListener(this);
        edit.setFocusPainted(false);
        edit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        toolbar.add(edit);

        edit = new JButton("Fix stoc", new ImageIcon(
                GUITools.getImage("resources/new24.png")));
        edit.setVerticalTextPosition(SwingConstants.BOTTOM);
        edit.setHorizontalTextPosition(SwingConstants.CENTER);
        edit.setToolTipText("Adjusteaza lungime busteni");
        edit.setActionCommand("STOK");
        edit.addActionListener(this);
        edit.setFocusPainted(false);
        edit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        toolbar.add(edit);

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
        JButton print = new JButton("Print", new ImageIcon(
                GUITools.getImage("resources/printb24.png")));
        print.setVerticalTextPosition(SwingConstants.BOTTOM);
        print.setHorizontalTextPosition(SwingConstants.CENTER);
        print.setToolTipText("Printeaza tabel");
        print.setActionCommand("PRINT");
        print.addActionListener(this);
        print.setFocusPainted(false);
        print.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        toolbar.add(print);


        toolbar.setSize(toolbar.getPreferredSize());
        toolbar.setLocation(5, 70);
        north.add(toolbar);

        stockModel = new FullStockModel();
        stockTable = new JTable(stockModel);


        GeneralTableRenderer renderer = new GeneralTableRenderer();
        for (int i = 0; i < stockTable.getColumnModel().getColumnCount(); i++) {
            stockTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(north, BorderLayout.NORTH);
        getContentPane().add(new JScrollPane(stockTable), BorderLayout.CENTER);

        extractStock();
    }

    private void extractStock() {
        List<LumberLogStockEntry> lumberLogs = StockDAO.getCurrentLumbersLogs(null);
        if (lumberLogs != null) {
            List<Double> stockInfo = stockModel.setLumberLogsData(lumberLogs);
            totalVolume = stockInfo.get(0);
            useableVolume = stockInfo.get(1);
            totalLabel.setText("Total cubaj lemn: " + numberFormatter.format(totalVolume) + " metri cubi");
            useableLabel.setText("Total coaja cubi: " + numberFormatter.format(stockInfo.get(2)) + " metri cubi");
            avgCostLabel.setText("Cost mediu: " + numberFormatter.format(stockInfo.get(3)) + " RON");
            avgMarginLabel.setText("Cost mediu coaja: " + numberFormatter.format(stockInfo.get(4)) + " RON");
            allTotalLabel.setText("Total: " + numberFormatter.format(totalVolume + stockInfo.get(2)) + " metri cubi");
        }

    }

    public void actionPerformed(ActionEvent eve) {
        String command = eve.getActionCommand();
        if (command.equals("MARK_PROCESSED")) {

            currentRow = stockTable.getSelectedRow();
            if (currentRow == -1) {
                JOptionPane.showMessageDialog(GUIUtil.container, "Nici un bustean nu a fost selectat");
            } else {
                LumberLog lumberLog = stockModel.getLumberLog(currentRow);
                markPanel = new MarkProcessedLumberPanel(lumberLog);
                JButton buttonOk = new JButton("Marcheaza");
                buttonOk.setActionCommand("SAVE_ADD_LOG");
                buttonOk.addActionListener(this);
                JButton buttonCancel = new JButton("Anuleaza");
                buttonCancel.setActionCommand("CANCEL_ADD_LOG");
                buttonCancel.addActionListener(this);
                JOptionPane.showOptionDialog(GUIUtil.container, markPanel, "Marcare bustean procesat",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new JButton[]{buttonOk, buttonCancel}, buttonCancel);
            }

        } else if (command.equals("MARK_DELETED")) {
            int row = stockTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(GUIUtil.container, "Nici un bustean nu a fost selectat");
            } else {
                LumberLog lumberLog = stockModel.getLumberLog(row);
                int rasp = JOptionPane.showConfirmDialog(GUIUtil.container, "Confirmati stergerea", "Stergere bustean", JOptionPane.YES_NO_OPTION);
                if (rasp == JOptionPane.YES_OPTION) {
                    boolean status = LumberLogDAO.deleteLumberLog(lumberLog);
                    if (!status) {
                        JOptionPane.showMessageDialog(GUIUtil.container, "Busteanul nu a fost sters", "Erroare", JOptionPane.ERROR_MESSAGE);
                    } else {
                        totalVolume -= MetricTools.toMeterCubs(lumberLog.getVolume(), METRIC.MILIMETER);
                        totalLabel.setText("Total cubaj: " + numberFormatter.format(totalVolume) + " metri cubi");
                        stockModel.removeLumberLog(row);
                    }
                }
            }
        } else if (command.equals("CANCEL_ADD_LOG")) {
            Window w = SwingUtilities.getWindowAncestor((JButton) eve.getSource());
            if (w != null) w.dispose();
        } else if (command.equals("SAVE_ADD_LOG")) {
            String message = markPanel.getMessage();
            LumberLog lumberLog = markPanel.getLumberLog();
            boolean status = LumberLogDAO.markProcessedLumberLog(lumberLog, message);
            if (!status) {
                JOptionPane.showMessageDialog(GUIUtil.container, "Busteanul nu a fost marcat ca procesat", "Erroare", JOptionPane.ERROR_MESSAGE);
            } else {
                totalVolume -= (lumberLog.getVolume() / 1000000000L);
                totalLabel.setText("Total cubaj: " + numberFormatter.format(totalVolume) + " metri cubi");
                stockModel.removeLumberLog(currentRow);
                Window w = SwingUtilities.getWindowAncestor((JButton) eve.getSource());
                if (w != null) w.dispose();

            }

        } else if (command.equals("EDIT")) {
            currentRow = stockTable.getSelectedRow();
            if (currentRow == -1) {
                JOptionPane.showMessageDialog(GUIUtil.container, "Nici un bustean nu a fost selectat");
            } else {
                lumberLog = stockModel.getLumberLog(currentRow);
                LumberLogDAO.refreshLumberLog(lumberLog);
                JButton buttonOk = new JButton("Salveaza");
                buttonOk.setActionCommand("SAVE_EDIT_LOG");
                buttonOk.addActionListener(this);
                JButton buttonCancel = new JButton("Anuleaza");
                buttonCancel.setActionCommand("CANCEL_ADD_LOG");
                buttonCancel.addActionListener(this);
                editLogLumberPanel = new AddLogLumberPanel(lumberLog);
                JScrollPane scroll = new JScrollPane(editLogLumberPanel);
                scroll.setMaximumSize(new Dimension(1000, 300));
                scroll.setPreferredSize(new Dimension(editLogLumberPanel.getPreferredSize().width + 20, 250));
                JOptionPane.showOptionDialog(GUIUtil.container, scroll, "Modificare bustean",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new JButton[]{buttonOk, buttonCancel}, buttonCancel);
            }
        } else if (command.equals("SAVE_EDIT_LOG")) {
            if (editLogLumberPanel != null) {
                LumberLog lumberLog = editLogLumberPanel.validateData();
                if (lumberLog == null) {
                    return;
                }
                LumberLogUtil.calculateVolume(lumberLog);
                lumberLog.setRealLength(lumberLog.getLength().longValue());
                lumberLog.setRealVolume(lumberLog.getVolume());
                LumberLogUtil.trimAndSetLength(lumberLog);
                LumberLogUtil.calculateVolume(lumberLog);
                lumberLog.setMarginRealVolume(lumberLog.getRealVolume() * (lumberLog.getMarginPercent() / 100D));
                lumberLog.setMarginVolume(lumberLog.getVolume() * (lumberLog.getMarginPercent() / 100D));
                //adjust volume
                lumberLog.setVolume(lumberLog.getVolume() - lumberLog.getMarginVolume());
                lumberLog.setRealVolume(lumberLog.getRealVolume() - lumberLog.getMarginRealVolume());
                boolean update = LumberLogDAO.updateLumberLog(lumberLog);
                if (!update) {
                    JOptionPane.showMessageDialog(GUIUtil.container, "Error", "Busteanul nu a fost schimbat.", JOptionPane.ERROR_MESSAGE);
                } else {
                    Window w = SwingUtilities.getWindowAncestor((JButton) eve.getSource());
                    if (w != null) w.dispose();
                    totalVolume = stockModel.updateLumberLog(lumberLog, currentRow);
                    totalLabel.setText("Total cubaj: " + numberFormatter.format(totalVolume) + " metri cubi");
                }

            }
        } else if (command.equals("CSV")) {
            JFileChooser chooser = new JFileChooser();
            int option = chooser.showSaveDialog(GUIUtil.container);
            if (option == JFileChooser.APPROVE_OPTION) {
                if (chooser.getSelectedFile() != null) {
                    File theFileToSave = chooser.getSelectedFile();
                    stockModel.toCsv(theFileToSave);
                }
            }
        } else if (command.equals("PRINT")) {
            try {
                stockTable.print();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (command.equals("STOK")) {
            JDialog dialog = new JDialog(GUIUtil.main);
            dialog.getContentPane().add(new JLabel("Asteptati"));
            dialog.setSize(300, 50);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
            int count = StockDAO.adjustStock();
            System.out.println("lumbers updated: " + count);
            dialog.dispose();
        }
    }

    @Override
    public String getFrameCode() {
        return GUIUtil.STOCK_KEY;
    }

    @Override
    public ImageIcon getFrameIcon() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ImageIcon getIconifiedIcon() {
        Image image = GUITools.getImage("/ro/barbos/gui/resources/chart32.png");
        return new ImageIcon(image);
    }

}
