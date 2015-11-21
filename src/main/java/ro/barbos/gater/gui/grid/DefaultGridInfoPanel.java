package ro.barbos.gater.gui.grid;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DefaultGridInfoPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JLabel info = null;
	private JPanel dbInfoPanel = null;
	private JLabel dbInfo = null;
	private JLabel dbPageInfo = null;
	private JButton nextPage = null;
	private JButton prevPage = null;

	private DefaultGrid grid;

	public DefaultGridInfoPanel(DefaultGrid grid) {
		this.grid = grid;
		setBorder(BorderFactory.createLineBorder(Color.black, 1));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		info = new JLabel(" ");
		add(info);
		dbInfo = new JLabel("Total : 0");
		dbInfoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 2));
		dbInfoPanel.setMinimumSize(new Dimension(250, 30));
		dbInfoPanel.setMaximumSize(new Dimension(250, 30));
		dbInfoPanel.setPreferredSize(new Dimension(250, 30));
		nextPage = new JButton(">>");
		nextPage.setMargin(null);
		nextPage.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		nextPage.addActionListener(this);
		nextPage.setActionCommand("NEXT_PAGE");
		nextPage.setContentAreaFilled(false);
		nextPage.setEnabled(false);
		dbPageInfo = new JLabel("1/1");
		dbPageInfo.setPreferredSize(new Dimension(30, dbPageInfo.getPreferredSize().height));
		prevPage = new JButton("<<");
		prevPage.setMargin(null);
		prevPage.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		prevPage.addActionListener(this);
		prevPage.setActionCommand("PREV_PAGE");
		prevPage.setContentAreaFilled(false);
		prevPage.setEnabled(false);
		dbInfoPanel.add(dbInfo);
		dbInfoPanel.add(prevPage);
		dbInfoPanel.add(dbPageInfo);
		dbInfoPanel.add(nextPage);
		add(info);
		add(Box.createHorizontalGlue());
		add(dbInfoPanel);
		add(Box.createHorizontalStrut(2));
	}
	
	public void displayDbInfo(int page, int nopages, int total) {
		int acpage = page+1;
		dbPageInfo.setText(acpage+"/"+nopages);
		if(page>0) prevPage.setEnabled(true);
		else prevPage.setEnabled(false);
		if(acpage<nopages) nextPage.setEnabled(true);
		else nextPage.setEnabled(false);
		dbInfo.setText("Total : "+total);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals("NEXT_PAGE")) {
			grid.showNextPage();
		}
		if (command.equals("PREV_PAGE")) {
			grid.showPreviousPage();
		}
	}

}
