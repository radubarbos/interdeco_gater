package ro.barbos.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;

import ro.barbos.gater.cutprocessor.CutterSettings;
import ro.barbos.gater.dao.BladeDAO;
import ro.barbos.gater.dao.CutPlanDAO;
import ro.barbos.gater.dao.DataAccess;
import ro.barbos.gater.dao.GaterSettingDAO;
import ro.barbos.gater.dao.IDPlateDAO;
import ro.barbos.gater.dao.LoginDAO;
import ro.barbos.gater.dao.StockDAO;
import ro.barbos.gater.data.IDPlateManager;
import ro.barbos.gater.model.Blade;
import ro.barbos.gater.model.GaterSetting;
import ro.barbos.gater.model.User;

public class MainFrame extends JFrame implements WindowListener, ActionListener {
	
	public static final long serialVersionUID = 1L;
	
	private LoginPanel pp =null;
	private ChangeUserPassPanel userPanel = null;
	private JInternalFrame loginFrame =null;
	private JInternalFrame changeUser = null;
	private JPanel background;
	
	private String appTitle = "Gater ";
	
	ScheduledExecutorService backgroundRefresher;

	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setSize((int)(screen.width*0.75),(int)(screen.height*0.80));
		
		//create obj
		MainContainer container = new MainContainer();
		GUIUtil.container = container;
		getContentPane().add(container,BorderLayout.CENTER);
		DataAccess.getInstance().init();
		InfoPanel info = new InfoPanel();
		GUIUtil.info = info;
		getContentPane().add(info,BorderLayout.SOUTH);
		
		background = new JBackgroundPanel();
		background.setOpaque(true);
		background.setSize(2000,2000);
		container.add(background);
		
		String user = System.getProperty("APPUSER");
		String pwd = System.getProperty("APPPWD");
		
		if(user==null || pwd==null)
		{
			displayLoginFrame();
		}
		else
		{
			
			User userobj = LoginDAO.login(user, pwd);
			if(userobj ==null) 
			{
				displayLoginFrame();
			}
			else
			{
			LoginDAO.loadRights(userobj);
			ConfigLocalManager.currentUser = userobj;
			ConfigLocalManager.locale = new Locale("ro");
			applicationStart();
			}
		}
		
		/*User test = new User();
		test.setID(1);
		test.setName("test");
		test.setUserName("");
		ConfigLocalManager.currentUser = test;
		ConfigLocalManager.locale = new Locale("ro");
		
		applicationStart();*/
		
		addWindowListener(this);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		GUIUtil.main = this;
	}
	
	public void displayLoginFrame()
	{
		loginFrame = new JInternalFrame();
		loginFrame.setTitle("Autentificare");
		loginFrame.setSize(260,180);
		loginFrame.setLocation(getSize().width/2-loginFrame.getWidth()/2,getSize().height/2-loginFrame.getHeight()/2);
		pp = new LoginPanel(null,this);
		loginFrame.setContentPane(pp);
		GUIUtil.container .add(loginFrame);
		loginFrame.setVisible(true);
		try{
		loginFrame.setSelected(true);
		}catch(Exception ee){}
		loginFrame.getGlassPane().setVisible(false);
	}
	
	private void applicationStart()
	{
	   setTitle(appTitle+" "+ConfigLocalManager.currentUser.getName());	
       //setJMenuBar(MenuBarCreator.createMainMenuBar());
	   Logger logger = Logger.getLogger("gui");
		
		((JPanel)getContentPane()).revalidate();
		
		//lunch init
		Timer tt = new Timer(2000,new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				
				boolean connected = DataAccess.getInstance().isConnected();
				if(!connected)
				{
					JOptionPane.showMessageDialog(MainFrame.this,"Nu sa putut face conexiunea la baza de date\nRestartati applicatia");
				}
			}
		});
		tt.setRepeats(false);
		tt.start();
		
		setBackgroundData();
		
		if(ConfigLocalManager.currentUser.getRights().getRightsLevel() == 0) {
			//load settings
			List<Blade> blades = BladeDAO.getBlades(); 
			for(Blade blade: blades) {
				if(blade.getName().startsWith("Lam")) {
					CutterSettings.GATER_THICK = blade.getThick().doubleValue();
				}
				else {
					CutterSettings.MULTIBLADE = blade.getThick().doubleValue();
				}
			}
			List<GaterSetting> gaterSettings = GaterSettingDAO.getSettings();
			for(GaterSetting setting: gaterSettings) {
				if(setting.getName().startsWith("Su")) {
					CutterSettings.TOP_START = setting.getValue().doubleValue();
				}
				else if(setting.getName().startsWith("Jo")) {
					CutterSettings.BOTTOM_END = setting.getValue().doubleValue();
				}
				else if(setting.getName().startsWith("Li")) {
					CutterSettings.BOTTOM_OFFSET = setting.getValue().doubleValue();
				}
				else if(setting.getName().startsWith("Mul")) {
					CutterSettings.MULTIBLADE_TOLERANCE = setting.getValue().doubleValue();
				}
				else if(setting.getName().startsWith("Tol")) {
					CutterSettings.EDGE_TOLERANCE = setting.getValue().doubleValue();
				}
			}
		}
		
		IDPlateManager.setAvailableIDPlate(IDPlateDAO.getAvailablePlates());
		try{
		   getContentPane().add(new LeftPanel(), BorderLayout.WEST);
		}catch(Exception e) {
			logger.warning(e.getMessage());
			logger.log(Level.WARNING, "Error", e);
		}
		UIManager.put("OptionPane.cancelButtonText", "Anuleaza");
	    UIManager.put("OptionPane.noButtonText", "Nu");
	    UIManager.put("OptionPane.okButtonText", "Ok");
	    UIManager.put("OptionPane.yesButtonText", "Da");
	}

	
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	public void windowClosed(WindowEvent e) {
		
		DataAccess.getInstance().closeConnection();
	}

	
	public void windowClosing(WindowEvent e) 
	{
		DataAccess.getInstance().closeConnection();
	}

	
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	//Huzu
		private void displayChangeUser()
		{
		GUIUtil.container.removeFrameState(GUIUtil.PASSWORD_CHANGE_KEY);	
		if(changeUser!=null) changeUser.dispose();
		changeUser = new JInternalFrame();
		changeUser.setTitle("Schimba Parola");
		changeUser.setClosable(true);
		changeUser.setSize(350, 260);
		changeUser.setLocation(getSize().width/2-changeUser.getWidth()/2,getSize().height/2-changeUser.getHeight()/2);
		userPanel = new ChangeUserPassPanel(null,this);
		changeUser.setContentPane(userPanel);
		GUIUtil.container.addJustFrame(changeUser,GUIUtil.PASSWORD_CHANGE_KEY);
		changeUser.setVisible(true);
		try{
			changeUser.setSelected(true);
		}catch(Exception e){}
		changeUser.getGlassPane().setVisible(false);	
			
		}//ends Huzu

	
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if(command == null) return;
		if(command.equals("LOGIN"))
		{
			String user = pp.getUser();
			String pwd = pp.getPassword();
			if(user.trim().length()==0 || pwd.trim().length()==0) pp.showLoginFailed();
			User userobj = LoginDAO.login(user, pwd);
			if(userobj ==null) 
			{
				pp.showLoginFailed();
				return;
			}
			//LoginDAO.loadRights(userobj);
			ConfigLocalManager.currentUser = userobj;
			ConfigLocalManager.locale = new Locale("ro");
			loginFrame.dispose();
			applicationStart();
		}
		else if(command.equals("LOGOUT"))
		{
			GUIUtil.container.closeAllFrames();
			GUIUtil.container.remove(background);
			background = new JBackgroundPanel();
			background.setOpaque(true);
			background.setSize(2000,2000);
			this.getContentPane().remove(((BorderLayout)this.getContentPane().getLayout()).getLayoutComponent(BorderLayout.WEST));
			setTitle("Gater");
			setJMenuBar(null);
			GUIUtil.container.add(background,Integer.MIN_VALUE);
			displayLoginFrame();
			repaint();
			if(backgroundRefresher != null) {
				backgroundRefresher.shutdown();
			}
		}
		//Huzu
		else if(command.equals("CHANGE"))
		{
			/*JInternalFrame[] frames = GUIUtil.container.getAllFrames();
			if(frames!=null)
			{
				for(JInternalFrame frame:frames)
				{
					frame.dispose();
				}
			}
			setJMenuBar(null);*/
			displayChangeUser();
		}//ends
		else if(command.equals("CANCEL_DIALOG")) {
			GUITools.closeParentDialog((JComponent)e.getSource());
		}
	}
	
	private void setBackgroundData() {
		GUIUtil.container.remove(background);
		if(ConfigLocalManager.currentUser.getRights().getRightsLevel() == 1) {
		background = new StockStatisticsPanel();
		background.setOpaque(true);
		background.setSize(2000,2000);
		GUIUtil.container.add(background,Integer.MIN_VALUE);
		((StatisticsPanel)background).showData(StockDAO.getStockStatictics());
		backgroundRefresher =  Executors.newSingleThreadScheduledExecutor();
		backgroundRefresher.scheduleAtFixedRate(new StockStatisticRefresher(), 1, 1, TimeUnit.MINUTES);
		}
		else if(ConfigLocalManager.currentUser.getRights().getRightsLevel() == 0 || ConfigLocalManager.currentUser.getRights().getRightsLevel() == 2) {
			background = new CutPlanStatisticsPanel();
			background.setOpaque(true);
			background.setSize(2000,2000);
			GUIUtil.container.add(background,Integer.MIN_VALUE);
			((CutPlanStatisticsPanel)background).showData(CutPlanDAO.getCutPlanStatistics());
			backgroundRefresher =  Executors.newSingleThreadScheduledExecutor();
			backgroundRefresher.scheduleAtFixedRate(new CutPlanStatisticRefresher(), 10, 10, TimeUnit.SECONDS);
		}
		else {
			background = new JBackgroundPanel();
		}
		GUIUtil.container.revalidate();
	}
	
	class StockStatisticRefresher implements Runnable {
		public void run() {
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					((StatisticsPanel)background).showData(StockDAO.getStockStatictics());
					((StatisticsPanel)background).repaint();
					GUIUtil.container.revalidate();
				}
			});
		}
	}
	
	class CutPlanStatisticRefresher implements Runnable {
		public void run() {
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					((CutPlanStatisticsPanel)background).showData(CutPlanDAO.getCutPlanStatistics());
					((CutPlanStatisticsPanel)background).repaint();
					GUIUtil.container.revalidate();
				}
			});
		}
	}
}
