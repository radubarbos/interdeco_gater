package ro.barbos.gui;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;

import javax.swing.JPanel;

public class JBackgroundPanel extends JPanel{

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		Paint p = new GradientPaint(200, 0, Color.white, 200, getHeight(),new Color(119,237,244));
		g2.setPaint(p);
		g2.fill(new Rectangle(getWidth(), getHeight()));
	}
	
}
