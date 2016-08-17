package ro.barbos.gui.settings;

import ro.barbos.gater.dao.SettingsDAO;
import ro.barbos.gater.stock.StockSettings;
import ro.barbos.gui.GUIFactory;
import ro.barbos.gui.GUITools;
import ro.barbos.gui.GUIUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Vector;

/**
 * Created by radu on 8/15/2016.
 */
public class StockSettingsPanel extends JPanel implements ActionListener, ItemListener {

    private BufferedImage bufferedImage;
    JComboBox<Integer> lumberLogMargin;
    JPanel previewPanel;

    public StockSettingsPanel() {
        initLayout();
    }

    private void initLayout() {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        JPanel input = new JPanel();
        input.setLayout(new BoxLayout(input, BoxLayout.Y_AXIS));

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(GUIFactory.createLabel("Coaja:", 100));
        Vector<Integer> coajaPercent = new Vector<>();
        for(int p=0; p<101; p++) {
            coajaPercent.add(p);
        }
        lumberLogMargin = new JComboBox<Integer>(coajaPercent);
        lumberLogMargin.setSelectedItem(StockSettings.LUMBER_LOG_MARGIN);
        lumberLogMargin.addItemListener(this);
        panel.add(lumberLogMargin);
        panel.add(new JLabel(" %"));
        input.add(panel);
        lumberLogMargin.setSelectedItem(StockSettings.LUMBER_LOG_MARGIN);
        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton save = new JButton("Save");
        save.setActionCommand("SAVE");
        save.addActionListener(this);
        panel.add(save);
        input.add(panel);

        input.setPreferredSize(new Dimension(250, 100));

        add(input);
        previewPanel = createPreviewPanel();
        add(previewPanel);
    }

    private JPanel createPreviewPanel() {
        JPanel preview = new JPanel(){
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                if(bufferedImage == null) {
                    createPreviewImage(this);
                }
                g.drawImage(bufferedImage, 0, 0, null);
            }
        };
        preview.setPreferredSize(new Dimension(350, 350));
        preview.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray, 2), "Preview"));
        return preview;
    }

    private void createPreviewImage(JPanel panel) {

        int margin = (Integer)lumberLogMargin.getSelectedItem();

        Dimension size = panel.getPreferredSize();
        bufferedImage = new BufferedImage((int)size.getWidth(), (int)size.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);

        BufferedImage texture = (BufferedImage)GUITools.getImage("resources/textures/lumberLogSection.jpg");

        Graphics2D g2 = (Graphics2D)bufferedImage.getGraphics();
        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHints(rh);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.translate(75, 75);
        Ellipse2D.Double lumberLogCircle = new Ellipse2D.Double(0, 0, 200, 200);
        g2.setPaint(new Color(191, 75, 30));
        g2.fill(lumberLogCircle);
        Ellipse2D.Double innerLumberLogCircle = new Ellipse2D.Double(0 + (1*margin), 0 + (1*margin), 200 - 2*margin, 200-2*margin);
        if(texture != null) {
            BufferedImage textureResized = new BufferedImage(200, 200, texture.getType());
            Graphics2D g = textureResized.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(texture, 0, 0, 200, 200, 0, 0, texture.getWidth(),
                    texture.getHeight(), null);
            g.dispose();
            Rectangle2D tr = new Rectangle2D.Double(0, 0, textureResized.getWidth(), textureResized
                    .getHeight());
            TexturePaint tp = new TexturePaint(textureResized, tr);
            g2.setPaint(tp);
        } else {
            RadialGradientPaint radialPaint = new RadialGradientPaint(new Point2D.Double(0, 0), (float)100, new float[] {0.3f, 0.9f}, new Color[]{new Color(255, 178, 40), new Color(191, 75, 30)});
            g2.setPaint(radialPaint);
        }

        g2.fill(innerLumberLogCircle);
    }

    @Override
    public void actionPerformed(ActionEvent eve) {
        String command = eve.getActionCommand();
        if (command.equals("SAVE")) {
            int ras = JOptionPane.showConfirmDialog(GUIUtil.container, "Doriti sa salvati", "Confirmati salvare", JOptionPane.YES_NO_OPTION);
            if (ras == JOptionPane.YES_OPTION) {
                boolean updated = SettingsDAO.saveSetting("LUMBER_LOG_MARGIN", (Integer)lumberLogMargin.getSelectedItem());
                if (!updated) {
                    JOptionPane.showMessageDialog(GUIUtil.container, "Salvarea a esuat", "Nu se poate salva", JOptionPane.ERROR_MESSAGE);
                } else {
                    StockSettings.LUMBER_LOG_MARGIN = (Integer)lumberLogMargin.getSelectedItem();
                }
            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        bufferedImage = null;
        if(previewPanel != null) {
            previewPanel.repaint();
        }
    }
}
