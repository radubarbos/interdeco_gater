package ro.barbos.gui.stock;

import ro.barbos.gater.model.LumberLogTransportEntry;
import ro.barbos.gater.model.LumberLogTransportEntryCostMatrix;
import ro.barbos.gui.ConfigLocalManager;
import ro.barbos.gui.GUIFactory;
import ro.barbos.gui.GUITools;
import ro.barbos.gui.tablemodel.stock.LumberLogTransportCostModel;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

/**
 * Created by radu on 8/24/2016.
 */
public class ConfigLumberTransportEntryFrame extends JPanel implements ComponentListener, ActionListener {

    private LumberLogTransportEntryFrame parentFrame;
    private LumberLogTransportCostModel costModel;
    private JTable costTabel;
    private LumberLogTransportEntryCostMatrix matrix;
    private LumberLogTransportEntry entry;

    private Double totalCost = 0D;
    private JLabel totalCostLabel;

    int dataWidth = 500;
    int dataHeight = 400;
    JPanel dataPanel;

    private NumberFormat numberFormatter = NumberFormat.getInstance(ConfigLocalManager.locale);

    public ConfigLumberTransportEntryFrame(LumberLogTransportEntryFrame parentFrame, LumberLogTransportEntry entry) {
        super();
        this.parentFrame = parentFrame;
        this.entry = entry;
        numberFormatter.setMaximumFractionDigits(2);

//setBackground(new Color(200,200,200,200));

        dataPanel = new JPanel();
        dataPanel.setLayout(new BorderLayout());
        dataPanel.setOpaque(true);

        matrix = new LumberLogTransportEntryCostMatrix();
        matrix.init(entry);

        costModel = new LumberLogTransportCostModel(matrix, this);
        costTabel = new JTable(costModel);
        costTabel.setRowHeight(25);

        Vector<String> rowNames = new Vector<>(costModel.getRows());
        JList rowHeader = new JList(rowNames);
        rowHeader.setFixedCellWidth(80);

        rowHeader.setFixedCellHeight(costTabel.getRowHeight()
                + costTabel.getRowMargin()
                + costTabel.getIntercellSpacing().height);
        rowHeader.setCellRenderer(new RowHeaderRenderer(costTabel));
        rowHeader.setOpaque(false);
        costTabel.setDefaultEditor(Object.class, new MyEditor());
        JScrollPane scroll = new JScrollPane(costTabel);
        scroll.setRowHeaderView(rowHeader);
        dataPanel.add(scroll, BorderLayout.CENTER);
        JPanel north = new JPanel(new BorderLayout());
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        SimpleDateFormat format = new SimpleDateFormat("dd, MM yyyy");
        JLabel tempLabel = new JLabel("Receptia din data de " + format.format(entry.getEntryDate()));
        tempLabel.setFont(new Font("arial", Font.BOLD, 16));
        infoPanel.add(tempLabel);
        totalCostLabel = new JLabel("Cost total : " + numberFormatter.format(matrix.getTotalCost()) + " RON");
        totalCostLabel.setFont(new Font("arial", Font.BOLD, 16));
        infoPanel.add(totalCostLabel);
        north.add(infoPanel, BorderLayout.SOUTH);
        dataPanel.add(north, BorderLayout.NORTH);

        //button bar
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEADING));

        JButton but = new JButton("Salveaza cost", new ImageIcon(
                GUITools.getImage("resources/stamp24.png")));
        but.setVerticalTextPosition(SwingConstants.BOTTOM);
        but.setHorizontalTextPosition(SwingConstants.CENTER);
        but.setFocusPainted(false);
        but.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        but.setToolTipText("Salveaza cost");
        but.setActionCommand("SET_COST_CONFIG");
        but.addActionListener(this);
        toolbar.add(but);

        but = new JButton("Anuleaza", new ImageIcon(
                GUITools.getImage("resources/delete24.png")));
        but.setVerticalTextPosition(SwingConstants.BOTTOM);
        but.setHorizontalTextPosition(SwingConstants.CENTER);
        but.setFocusPainted(false);
        but.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        but.setToolTipText("Iesire fara salvare");
        but.setActionCommand("EXIT");
        but.addActionListener(this);
        toolbar.add(but);
        dataPanel.add(toolbar, BorderLayout.SOUTH);


        setLayout(null);
        add(dataPanel);
        setInnerBounds();
        setBounds(0, 0, parentFrame.getWidth(), parentFrame.getHeight());
        parentFrame.addComponentListener(this);
    }

    private void setInnerBounds() {
        int startX = (parentFrame.getWidth() - dataWidth) / 2;
        int startY = (parentFrame.getHeight() - dataHeight) / 2;
        if (startX < 0) {
            startX = 0;
        }
        if (startY < 0) {
            startY = 0;
        }
        dataPanel.setBounds(startX, startY, dataWidth, dataHeight);
    }

    public void costChanged(double cost) {
        totalCostLabel.setText("Cost total : " + numberFormatter.format(cost) + " RON");
    }


    @Override
    public void componentResized(ComponentEvent e) {
        setBounds(0, 0, parentFrame.getWidth(), parentFrame.getHeight());
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("EXIT")) {
            parentFrame.getLayeredPane().remove(this);
            parentFrame.repaint(0);
        } else if (command.equals("SET_COST_CONFIG")) {
            parentFrame.setNewCost(this.matrix, this.entry);
        }
    }
}

class RowHeaderRenderer extends JLabel implements ListCellRenderer {

    RowHeaderRenderer(JTable table) {
        JTableHeader header = table.getTableHeader();
        setOpaque(true);
        setBorder(UIManager.getBorder("TableHeader.cellBorder"));
        setHorizontalAlignment(CENTER);
        setForeground(header.getForeground());
        setBackground(header.getBackground());
        setFont(header.getFont());
    }

    public Component getListCellRendererComponent(JList list, Object value,
                                                  int index, boolean isSelected, boolean cellHasFocus) {
        setText((value == null) ? "" : value.toString());
        return this;
    }
}

class MyEditor extends DefaultCellEditor {
    public MyEditor() {
        super(GUIFactory.createDecimalNumberInput(null, 0D, 1000000D, 80));

    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,
                                                 int row, int column) {
        JFormattedTextField editor = (JFormattedTextField) super.getTableCellEditorComponent(table, value, isSelected,
                row, column);

        if (value != null)
            editor.setText(value.toString());
        if (column == 3240) {
            editor.setHorizontalAlignment(SwingConstants.CENTER);
            editor.setFont(new Font("Serif", Font.BOLD, 14));
        } else {
            editor.setHorizontalAlignment(SwingConstants.RIGHT);
            editor.setFont(new Font("Serif", Font.ITALIC, 12));
        }
        return editor;
    }

    public Object getCellEditorValue() {
        return ((JFormattedTextField) editorComponent).getValue();
    }
}

