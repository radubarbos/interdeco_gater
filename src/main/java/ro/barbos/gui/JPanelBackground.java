package ro.barbos.gui;


import javax.swing.*;
import java.awt.*;

public class JPanelBackground extends JPanel{

    protected Image background;
    private int width = -1;
    private int height =-1;
    
    JFrame parent =null;
	
	public JPanelBackground(Image background){
		this.background=background;
	}
	
	public JPanelBackground(Image background,int width,int height){
		this.background=background;
		this.width=width;
		this.height=height;
	}
	
	public JPanelBackground(Image background, JFrame parent){
		this.background=background;
		this.parent=parent;
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
	 if(background!=null && width==-1 && parent==null)	g.drawImage(background,0,0,this);
	 else if(background!=null && width>0 && parent==null) g.drawImage(background,0,0,width,height,this);
	 else if(background!=null && parent!=null) g.drawImage(background,0,0,parent.getWidth(),parent.getHeight(),this);
	}
	
}
