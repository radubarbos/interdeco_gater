package ro.barbos.gui;

import ro.barbos.gater.cutprocessor.DefaultCutOptionsCalculatorData;
import ro.barbos.gater.dao.IDPlateDAO;
import ro.barbos.gater.dao.ProductDAO;
import ro.barbos.gater.dao.StockDAO;
import ro.barbos.gater.dto.LumberLogFilterDTO;
import ro.barbos.gater.model.IDPlate;
import ro.barbos.gater.model.LumberLogStockEntry;
import ro.barbos.gater.model.Product;
import ro.barbos.gui.exswing.SuggestionJComboBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class CutOptionsTargetPanel extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	private SuggestionJComboBox<IDPlate> plates;
	
	private JButton see = new JButton("Calculeaza");
	private JButton cancel = new JButton("Anuleaza");
	private List<JCheckBox> selection = new ArrayList<>(); 
	private JLabel label1;
	private JLabel label2;
    private JCheckBox filtru2m = new JCheckBox("2m");
    private JCheckBox filtru3m = new JCheckBox("3m");
    private JCheckBox filtru4m = new JCheckBox("4m");

	List<Product> products;
	
	public CutOptionsTargetPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		label1 = GUIFactory.createLabel("Placuta:", 100);
		panel.add(label1);
		plates = new SuggestionJComboBox<IDPlate>(IDPlateDAO.getUsedPlates().toArray(new IDPlate[0]));
		plates.setPreferredSize(new Dimension(80, plates.getPreferredSize().height));
		panel.add(plates);
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
            chk.setName("length"+(long)product.getLength());
			row.add(new JLabel(product.getName()));
			productsPanel.add(row);
		}
		JScrollPane scrollPane = new JScrollPane(productsPanel);
		scrollPane.setPreferredSize(new Dimension(productsPanel.getPreferredSize().width+20, 215));
		panel.add(scrollPane);
		add(panel);

        JPanel filtru = new JPanel(new FlowLayout(FlowLayout.LEADING));
        filtru.add(filtru2m);
        filtru.add(filtru3m);
        filtru.add(filtru4m);
        filtru2m.setActionCommand("FILTRU2");
        filtru3m.setActionCommand("FILTRU3");
        filtru4m.setActionCommand("FILTRU4");
        filtru2m.addActionListener(this);
        filtru3m.addActionListener(this);
        filtru4m.addActionListener(this);
        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        label1 = GUIFactory.createLabel("Filtru", 100);
        panel.add(label1);
        panel.add(filtru);
        add(panel);

		cancel.setActionCommand("CANCEL_DIALOG");
		cancel.addActionListener(GUIUtil.main);
		see.addActionListener(this);
	}
	
	private JButton[] getButtons() {
		return new JButton[]{see, cancel};
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().startsWith("FILTRU")) {
            int length = Integer.parseInt(e.getActionCommand().substring(6));
            for(int index = 0; index < selection.size();  index++) {
                JCheckBox chk1 = (JCheckBox)selection.get(index);
                int productLength = Integer.parseInt(chk1.getName().substring(6));
                if(!filtru2m.isSelected() && !filtru3m.isSelected() && !filtru4m.isSelected()) {
                    chk1.getParent().setVisible(true);
                    continue;
                }
                if(productLength<3000 && productLength>=2000 && filtru2m.isSelected()) {
                    chk1.getParent().setVisible(true);
                } else if(productLength<4000 && productLength>=3000 && filtru3m.isSelected()) {
                    chk1.getParent().setVisible(true);
                } else if(productLength<5000 && productLength>=4000 && filtru4m.isSelected()) {
                    chk1.getParent().setVisible(true);
                } else {
                    chk1.getParent().setVisible(false);
                }
            }
            this.revalidate();
            return;
        }
		boolean valid = true;
		IDPlate plate = plates.getSelectedItem();
		if(plate == null) {
			label1.setForeground(Color.red);
			valid = false;
		}
		else {
			label1.setForeground(Color.black);
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
		targetData.put("ALL_PRODUCTS", products);
		targetData.put("IDPLATE", plate);
		targetData.put("IDPLATE_LABEL", plate.getLabel());
		targetData.put("SELECTED_PRODUCTS", selectedProducts);
        DefaultCutOptionsCalculatorData data = new DefaultCutOptionsCalculatorData();
        data.setSelectedProducts(selectedProducts);
        //one product
        List<List<Product>> additionalWithJustOne = new ArrayList<>();
        for(Product  product: products) {
            List<Product> entry = new ArrayList<>();
            entry.add(product);
            additionalWithJustOne.add(entry);
        }
        data.setProducts(additionalWithJustOne);
        LumberLogFilterDTO filter = new LumberLogFilterDTO();
        filter.setIdPlates(Arrays.asList(new Long[]{plate.getId().longValue()}));
        List<LumberLogStockEntry> lumbers = StockDAO.getCurrentLumbersLogs(filter);
        data.setLumberLog(lumbers.get(0).getLumberLog());
		new CutOptionsTargetFrame(targetData, data);
		GUITools.closeParentDialog(this);
	}

	public static void showDialog() {
		CutOptionsTargetPanel panel = new CutOptionsTargetPanel();
		JButton[] buttons = panel.getButtons();
		JOptionPane.showOptionDialog(GUIUtil.container, panel, "Optiuni taiere bustean",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, buttons[0]);
	}

}
