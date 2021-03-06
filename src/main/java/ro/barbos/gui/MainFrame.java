package ro.barbos.gui;

import org.apache.shiro.SecurityUtils;
import ro.barbos.auth.LocalSecurityManager;
import ro.barbos.gater.cutprocessor.CutterSettings;
import ro.barbos.gater.cutprocessor.strategy.CutStrategyType;
import ro.barbos.gater.dao.*;
import ro.barbos.gater.data.IDPlateManager;
import ro.barbos.gater.model.Blade;
import ro.barbos.gater.model.GaterSetting;
import ro.barbos.gater.model.User;
import ro.barbos.gater.stock.StockSettings;
import ro.barbos.gui.settings.Settings;

import javax.swing.*;
import java.awt.*;
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
        setSize((int) (screen.width * 0.75), (int) (screen.height * 0.90));

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

            User userobj = null;
            LocalSecurityManager.loginUser(user, pwd);
            if (SecurityUtils.getSubject().isAuthenticated()) {
                userobj = LoginDAO.login(user, pwd);
            }
            if (!SecurityUtils.getSubject().isAuthenticated()) {
				displayLoginFrame();
			}
			else
			{
                //LoginDAO.loadRights(userobj);
                ConfigLocalManager.currentUser = userobj;
			ConfigLocalManager.locale = new Locale("ro");
			applicationStart();
			}
		}


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
        setTitle(appTitle + " " + ConfigLocalManager.currentUser.getName());
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
            CutStrategyType.SIMPLE_VERTICAL.setEnabled(true);
            CutStrategyType.BEST_MATCH_VERTICAL.setEnabled(true);
            CutStrategyType.ROTATE_ONE.setEnabled(true);
            CutStrategyType.ROTATE_ONE_180.setEnabled(true);
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
                else if(setting.getName().equals(Settings.MEASURE_MIDDLE_ONCE)) {
                    StockSettings.MEASURE_MIDDLE_ONCE = setting.getValue() == 1;
                }
                else if(setting.getName().equals(CutStrategyType.SIMPLE_VERTICAL.name()) && setting.getValue() == 0) {
                    CutStrategyType.SIMPLE_VERTICAL.setEnabled(false);
                }
                else if(setting.getName().equals(CutStrategyType.BEST_MATCH_VERTICAL.name()) && setting.getValue() == 0) {
                    CutStrategyType.BEST_MATCH_VERTICAL.setEnabled(false);
                }
                else if(setting.getName().equals(CutStrategyType.ROTATE_ONE.name()) && setting.getValue() == 0) {
                    CutStrategyType.ROTATE_ONE.setEnabled(false);
                }
                else if(setting.getName().equals(CutStrategyType.ROTATE_ONE_180.name()) && setting.getValue() == 0) {
                    CutStrategyType.ROTATE_ONE_180.setEnabled(false);
                }
                else if(setting.getName().equals(CutStrategyType.BEST_MULTIBLADE_MATCH.name())) {
                    CutStrategyType.BEST_MULTIBLADE_MATCH.setEnabled(setting.getValue() == 1);
                }
                else if(setting.getName().equals(CutStrategyType.NO_MULTI_BLADE.name())) {
                    CutStrategyType.NO_MULTI_BLADE.setEnabled(setting.getValue() == 1);
                }
                else if(setting.getName().equals("LUMBER_LOG_MARGIN")) {
                    StockSettings.LUMBER_LOG_MARGIN = setting.getValue().intValue();
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

        MetricFormatter.init();
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
            User userobj = null;
            LocalSecurityManager.loginUser(user, pwd);
            if (SecurityUtils.getSubject().isAuthenticated()) {
                userobj = LoginDAO.login(user, pwd);
            }
            if(userobj ==null)
			{
				pp.showLoginFailed();
				return;
			}
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
            SecurityUtils.getSubject().logout();
            repaint();
			if(backgroundRefresher != null) {
				backgroundRefresher.shutdown();
			}
		}
		//Huzu
		else if(command.equals("CHANGE"))
		{
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
