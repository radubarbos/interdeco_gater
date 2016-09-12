package ro.barbos.gui;

import ro.barbos.gui.renderer.GeneralTableRenderer;
import ro.barbos.gui.tablemodel.GeneralAbstractTableModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

/**
 * Created by radu on 9/4/2016.
 */
public abstract class GeneralTableDataFrame extends GeneralFrame implements ActionListener, ListSelectionListener, MouseListener {

    private static final long serialVersionUID = 1L;

    protected GeneralAbstractTableModel dataModel;
    protected JTable dataTable;

    public GeneralTableDataFrame() {
        setResizable(true);
        setMaximizable(true);
        setIconifiable(true);
        setClosable(true);
    }

    public void initGui() {
        JPanel toolbar = initToolbar();
        initDataModel();
        dataTable = new JTable(dataModel);

        dataTable.getSelectionModel().addListSelectionListener(this);
        dataTable.addMouseListener(this);

        GeneralTableRenderer renderer = new GeneralTableRenderer();
        for (int i = 0; i < dataTable.getColumnModel().getColumnCount(); i++) {
            dataTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        getContentPane().add(toolbar, BorderLayout.NORTH);
        getContentPane().add(new JScrollPane(dataTable), BorderLayout.CENTER);
    }

    public JButton createCSVExportButton() {
        JButton csvExport = new JButton("Exporta", new ImageIcon(
                GUITools.getImage("resources/csv24.png")));
        csvExport.setToolTipText("Exporta tabelul in fisier csv");
        csvExport.setVerticalTextPosition(SwingConstants.BOTTOM);
        csvExport.setHorizontalTextPosition(SwingConstants.CENTER);
        csvExport.setActionCommand("CSV");
        csvExport.addActionListener(this);
        csvExport.setFocusPainted(false);
        csvExport.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return csvExport;
    }

    public JButton createPrintButton() {
        JButton print = new JButton("Print", new ImageIcon(
                GUITools.getImage("resources/printb24.png")));
        print.setVerticalTextPosition(SwingConstants.BOTTOM);
        print.setHorizontalTextPosition(SwingConstants.CENTER);
        print.setToolTipText("Printeaza tabel");
        print.setActionCommand("PRINT");
        print.addActionListener(this);
        print.setFocusPainted(false);
        print.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return print;
    }

    public void actionPerformed(ActionEvent eve) {
        String command = eve.getActionCommand();
        if (command.equals("PRINT")) {
            try {
                dataTable.print();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (command.equals("CSV")) {
            JFileChooser chooser = new JFileChooser();
            int option = chooser.showSaveDialog(GUIUtil.container);
            if (option == JFileChooser.APPROVE_OPTION) {
                if (chooser.getSelectedFile() != null) {
                    File theFileToSave = chooser.getSelectedFile();
                    dataModel.toCsv(theFileToSave);
                }
            }
        }
    }

    public JPanel initToolbar() {
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEADING));
        toolbar.add(createCSVExportButton());
        toolbar.add(createPrintButton());
        return toolbar;
    }


    @Override
    public void valueChanged(ListSelectionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent eve) {
        if (eve.getClickCount() == 2) {
            int row = dataTable.getSelectedRow();
            if (row != -1) {
                //T data = dataModel.get(row);
                // new StackLumberLogFrame(stack);
                rowAction(row);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void rowAction(int row) {

    }

    public abstract void initDataModel();

    public abstract void fetchData();
}
