package ro.barbos.gui;
/**
 * huzu
 */
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ChangeUserPassPanel extends JPanelBackground{
	
	public Logger loger = Logger.getLogger("gui");
	private JLabel curentUserLabel = null;
	private JTextField curentUser= null;
	private JLabel parolaVecheLabel = null;
	private JPasswordField parolaVeche = null;
	private JLabel parolaNouaLabel = null;
	private JPasswordField parolaNoua = null;
	private JButton salvare = null;
	

	public ChangeUserPassPanel(Image img,ActionListener listener) {
		super(null);
		if(img==null)
		{
			InputStream inData = getClass().getResourceAsStream("ro/barbos/gui/resources/background.gif");
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
        JPanel changePanel = new JPanel();
        changePanel.setSize(350, 260);
        changePanel.setBorder(new javax.swing.border.LineBorder(Color.black,2));
        changePanel.setLayout(null);
        
        curentUserLabel = new JLabel("User");
        curentUserLabel.setBounds(90-curentUserLabel.getPreferredSize().width,40,curentUserLabel.getPreferredSize().width,curentUserLabel.getPreferredSize().height);
        
        curentUser = new JTextField(15);
        curentUser.setBounds(95, 40, curentUser.getPreferredSize().width,curentUser.getPreferredSize().height);
        curentUser.setText(ConfigLocalManager.currentUser.getUserName());
        curentUser.setEditable(false);
        
        parolaVecheLabel = new JLabel("Parola veche");
        parolaVecheLabel.setBounds(90-parolaVecheLabel.getPreferredSize().width,80,parolaVecheLabel.getPreferredSize().width,parolaVecheLabel.getPreferredSize().height);
        
        parolaVeche = new JPasswordField(10);
        parolaVeche.setBounds(95, 80, parolaVeche.getPreferredSize().width,parolaVeche.getPreferredSize().height);
                
        parolaNouaLabel = new JLabel("Parola noua");
        parolaNouaLabel.setBounds(90-parolaNouaLabel.getPreferredSize().width,120,parolaNouaLabel.getPreferredSize().width,parolaNouaLabel.getPreferredSize().height);

        parolaNoua = new JPasswordField(10);
        parolaNoua.setSize(parolaNoua.getPreferredSize());
        parolaNoua.setLocation(parolaVeche.getX(), parolaNouaLabel.getY());
        
        salvare = new JButton("Salveaza");
        salvare.setSize(salvare.getPreferredSize());
        salvare.setLocation(parolaNoua.getX()+90, parolaNoua.getY()+parolaNoua.getHeight()+20);
        
        changePanel.add(curentUserLabel);
        changePanel.add(curentUser);
        changePanel.add(parolaVecheLabel);
        changePanel.add(parolaVeche);
        changePanel.add(parolaNouaLabel);
        changePanel.add(parolaNoua);
        changePanel.add(salvare);
        
        add(changePanel);
	}

}
