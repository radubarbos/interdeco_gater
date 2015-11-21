package ro.barbos.gui;

import javax.swing.SwingUtilities;

public class StartUI {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
       SwingUtilities.invokeLater(new Runnable() {
			
			
			public void run() {
				new MainFrame();
			}
		});

	}

}
