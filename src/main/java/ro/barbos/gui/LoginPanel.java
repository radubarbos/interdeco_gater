package ro.barbos.gui;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.imageio.*;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.*;
import java.util.logging.*;
import java.util.*;



public class LoginPanel extends JPanelBackground implements KeyListener {

	JLabel loginTitle;
	
	public Logger loger = Logger.getLogger("gui");
	

	private JTextField user =null;
	private JPasswordField pass =null;
	
	private ActionListener listener;
	
	public LoginPanel(Image img,ActionListener listener)
	{
		super(null);
		this.listener = listener;
		if(img==null)
		{
			InputStream inData = getClass().getResourceAsStream("/resources/background.gif");
			BufferedImage back=null;
			if(inData!=null) 
				try{
				back=ImageIO.read(inData);
				}catch(IOException e){
					loger.finest("LoginPanel class can't get the background image");
				}
			this.background = back;	
		}

        setLayout(null);
        JPanel logingPanel = new JPanel();
		loginTitle = new JLabel("Autentificare");
		logingPanel.setSize(250,150);
		logingPanel.setBorder(new javax.swing.border.LineBorder(Color.black,2));
		logingPanel.setLayout(null);
		loginTitle.setBounds(3, 0, logingPanel.getWidth(),20);
		logingPanel.add(loginTitle);
		JLabel loginUser = new JLabel("Utilizator");
		loginUser.setBounds(80-loginUser.getPreferredSize().width,40,loginUser.getPreferredSize().width,loginUser.getPreferredSize().height);
		logingPanel.add(loginUser);
		user = new JTextField(10);
		user.setBounds(85, 40, user.getPreferredSize().width,user.getPreferredSize().height);
		//user.setText("test");
		//loginUser.setLabelFor(user);
		logingPanel.add(user);
		JLabel loginPass = new JLabel("Parola");
		loginPass.setBounds(80-loginPass.getPreferredSize().width,80,loginPass.getPreferredSize().width,loginPass.getPreferredSize().height);
		logingPanel.add(loginPass);
		//JTextField pass = new JTextField(10);
		pass = new JPasswordField(10);
		pass.setBounds(85, 80, pass.getPreferredSize().width,pass.getPreferredSize().height);
		pass.addKeyListener(this);
		//pass.setText("test");
		//loginUser.setLabelFor(user);
		logingPanel.add(pass);
		JButton loginButton = new JButton("Start");
		loginButton.setActionCommand("LOGIN");
		loginButton.setBounds(60, 110, loginButton.getPreferredSize().width, loginButton.getPreferredSize().height);
		//loginButton.setOpaque(false);
		loginButton.setFocusPainted(false);
		//loginButton.setContentAreaFilled(false);
		//loginButton.setBorderPainted(false);
		loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		loginButton.addActionListener(listener);
		logingPanel.add(loginButton);
		
		add(logingPanel);
	}
	
	public String getUser()
	{
		return user.getText();
	}
	
	public String getPassword()
	{
		return new String(pass.getPassword());
	}
	
	public void showLoginFailed(){
		loginTitle.setForeground(Color.red);
		loginTitle.setText("Cont nerecunoscut");
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			ActionEvent eve = new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, "LOGIN");
			listener.actionPerformed(eve);
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
