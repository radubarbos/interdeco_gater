package ro.barbos.gui;

import ro.barbos.gui.GeneralFrame;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class InfoPanel extends JPanel implements ActionListener{

	private int height = 32;
	
	private HashMap<String,JInternalFrame> cache = new HashMap<String, JInternalFrame>();
	private HashMap<String,JButton> buttonL = new HashMap<String,JButton>();
	
	public InfoPanel()
	{
		super();
		setLayout(new FlowLayout(FlowLayout.LEADING,2,0));
		setBorder(BorderFactory.createLineBorder(Color.gray));
	}
	
	public Dimension getPreferredSize()
	{
		return new Dimension(super.getPreferredSize().width,height);
	}
	
	public void addFrame(GeneralFrame frame)
	{
		cache.put(frame.getFrameCode(), frame);
		JButton button = new JButton();
		button.setIcon(frame.getIconifiedIcon());
		button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		button.setActionCommand(frame.getFrameCode());
		button.addActionListener(this);
		button.setBorder(null);
		add(button);
		buttonL.put(frame.getFrameCode(), button);
		revalidate();
		repaint();
	}
	
	public void removeFrame(String code)
	{
		JInternalFrame inf = cache.remove(code);
		if(inf==null) return;
		JButton but = buttonL.remove(code);
		if(but!=null) 
		{
			remove(but);
			revalidate();
			repaint();
		}
	}

	public void actionPerformed(ActionEvent e)
	{
		String command = e.getActionCommand();
		if(command==null) return;
		JInternalFrame frame= cache.get(command);
		if(frame!=null) 
		{
			if(frame.isIcon())
			{
				try
				{
				frame.setIcon(false);
				}catch(Exception ee){}
			}
			frame.toFront();
		}
	}
	
	
}
