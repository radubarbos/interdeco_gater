package ro.barbos.gui;

import ro.barbos.gater.dao.BladeDAO;
import ro.barbos.gater.dao.GaterSettingDAO;
import ro.barbos.gui.settings.AlgorithmSettingsPanel;
import ro.barbos.gui.settings.BladeSettingsPanel;
import ro.barbos.gui.settings.GaterSettingsPanel;
import ro.barbos.gui.settings.GeneralSettingsPanel;

import javax.swing.*;
import java.awt.*;

public class SettingsFrame extends GeneralFrame {
	
	public SettingsFrame() {
		
        super();	
		setTitle("Setari");
		setResizable(true);
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		
		JTabbedPane tab = new JTabbedPane();
        GeneralSettingsPanel generalSettingsPanel = new GeneralSettingsPanel();
        tab.add("General", generalSettingsPanel);
		BladeSettingsPanel bladeSettings = new BladeSettingsPanel(BladeDAO.getBlades());
		tab.add("Lame", bladeSettings);
		GaterSettingsPanel gaterSettings = new GaterSettingsPanel(GaterSettingDAO.getSettings());
		tab.add("Setari gater", gaterSettings);
		AlgorithmSettingsPanel algorithmSettings = new AlgorithmSettingsPanel();
		tab.add("Setari calculare", algorithmSettings);
		
		setContentPane(tab);
	}
	
	@Override
	public String getFrameCode() {
		return GUIUtil.SETTINGS_KEY;
	}

	@Override
	public ImageIcon getFrameIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImageIcon getIconifiedIcon() {
		Image image = GUITools.getImage("/ro/barbos/gui/resources/product32.png");
		return new ImageIcon(image);
	}

}
