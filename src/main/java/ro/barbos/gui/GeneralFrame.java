package ro.barbos.gui;

import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;

public abstract class GeneralFrame extends JInternalFrame{

	public abstract String getFrameCode();
	
	public abstract ImageIcon getIconifiedIcon();
	
	public abstract ImageIcon getFrameIcon();
	
}
