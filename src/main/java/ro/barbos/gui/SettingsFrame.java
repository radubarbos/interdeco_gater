package ro.barbos.gui;

import ro.barbos.gater.dao.BladeDAO;
import ro.barbos.gater.dao.GaterSettingDAO;
import ro.barbos.gater.model.GaterSetting;
import ro.barbos.gui.settings.*;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.List;

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
        List<GaterSetting> gaterSettings = GaterSettingDAO.getSettings();
        filterGaterSettings(gaterSettings);
		GaterSettingsPanel gaterSettingsPanel = new GaterSettingsPanel(gaterSettings);
		tab.add("Setari gater", gaterSettingsPanel);
		AlgorithmSettingsPanel algorithmSettings = new AlgorithmSettingsPanel();
		tab.add("Setari calculare", algorithmSettings);
		tab.add("Algoritmi taieri", new CutStrategySettingsPanel());
		tab.add("Stoc", new StockSettingsPanel());

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

    private void filterGaterSettings(List<GaterSetting> settings) {
        Iterator<GaterSetting> iterator = settings.iterator();
        while(iterator.hasNext()) {
            GaterSetting setting = iterator.next();
            if(setting.getName().equals(Settings.MEASURE_MIDDLE_ONCE)) {
                iterator.remove();
            }
        }
    }

}
