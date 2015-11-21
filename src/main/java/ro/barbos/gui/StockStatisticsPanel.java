package ro.barbos.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

public class StockStatisticsPanel extends StatisticsPanel {
	
	Image statisticBlock = null;
	Map<String, Object> data = new  HashMap<String, Object>();
	NumberFormat formatter = NumberFormat.getNumberInstance(ConfigLocalManager.locale);
	
	public StockStatisticsPanel() {
		statisticBlock = GUITools.getImage("resources/catinfo.gif");
		formatter.setMaximumFractionDigits(2);
		formatter.setMinimumFractionDigits(2);
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		RenderingHints rh = new RenderingHints(
	             RenderingHints.KEY_TEXT_ANTIALIASING,
	             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	    g2.setRenderingHints(rh);
	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		//draw total logs
		int x = GUIUtil.main.getWidth()-statisticBlock.getWidth(null)-20-200;
		int y = 20;
		g2.drawImage(statisticBlock, x, y, null);
		g2.setPaint(Color.gray);
		g2.setFont(new Font("arial",Font.BOLD, 18));
		String txt = "Busteni";
		int txtWidth = g2.getFontMetrics().stringWidth(txt);
		int txtX = statisticBlock.getWidth(null)/2 - txtWidth/2;
		g2.drawString(txt, x + txtX, y+20);
		if(data.containsKey("LUMBER_LOG_COUNT")) {
			g2.setPaint(Color.white);
			g2.setFont(new Font("arial", Font.BOLD, 18));
			txtWidth = g2.getFontMetrics().stringWidth(data.get("LUMBER_LOG_COUNT").toString());
			txtX = statisticBlock.getWidth(null)/2 - txtWidth/2;
			g2.drawString(data.get("LUMBER_LOG_COUNT").toString(), x + txtX, y+60);
		}
		
		//draw total volume
				x = GUIUtil.main.getWidth()-statisticBlock.getWidth(null)-20-400;
				y = 20;
				g2.drawImage(statisticBlock, x, y, null);
				g2.setPaint(Color.gray);
				g2.setFont(new Font("arial",Font.BOLD, 18));
				txt = "Total m. cub";
				txtWidth = g2.getFontMetrics().stringWidth(txt);
				txtX = statisticBlock.getWidth(null)/2 - txtWidth/2;
				g2.drawString(txt, x + txtX, y+20);
				if(data.containsKey("LUMBER_LOG_VOLUME")) {
					String valTxt = formatter.format(data.get("LUMBER_LOG_VOLUME"));
					g2.setPaint(Color.white);
					g2.setFont(new Font("arial", Font.BOLD, 18));
					txtWidth = g2.getFontMetrics().stringWidth(valTxt);
					txtX = statisticBlock.getWidth(null)/2 - txtWidth/2;
					g2.drawString(valTxt, x + txtX, y+60);
				}
				
				//draw brad
				x = GUIUtil.main.getWidth()-statisticBlock.getWidth(null)-20-200;
				y = 120;
				g2.drawImage(statisticBlock, x, y, null);
				g2.setPaint(Color.gray);
				g2.setFont(new Font("arial",Font.BOLD, 18));
				txt = "Brad";
				txtWidth = g2.getFontMetrics().stringWidth(txt);
				txtX = statisticBlock.getWidth(null)/2 - txtWidth/2;
				g2.drawString(txt, x + txtX, y+20);
				if(data.containsKey("1")) {
					String valTxt = data.get("1").toString();
					g2.setPaint(Color.white);
					g2.setFont(new Font("arial", Font.BOLD, 18));
					txtWidth = g2.getFontMetrics().stringWidth(valTxt);
					txtX = statisticBlock.getWidth(null)/2 - txtWidth/2;
					g2.drawString(valTxt, x + txtX, y+60);
				}	
				
				//draw molid
				x = GUIUtil.main.getWidth()-statisticBlock.getWidth(null)-20-200;
				y = 220;
				g2.drawImage(statisticBlock, x, y, null);
				g2.setPaint(Color.gray);
				g2.setFont(new Font("arial",Font.BOLD, 18));
				txt = "Molid";
				txtWidth = g2.getFontMetrics().stringWidth(txt);
				txtX = statisticBlock.getWidth(null)/2 - txtWidth/2;
				g2.drawString(txt, x + txtX, y+20);
				if(data.containsKey("1")) {
					String valTxt = data.get("2").toString();
					g2.setPaint(Color.white);
					g2.setFont(new Font("arial", Font.BOLD, 18));
					txtWidth = g2.getFontMetrics().stringWidth(valTxt);
					txtX = statisticBlock.getWidth(null)/2 - txtWidth/2;
					g2.drawString(valTxt, x + txtX, y+60);
				}	
				
				//draw fag
				x = GUIUtil.main.getWidth()-statisticBlock.getWidth(null)-20-200;
				y = 320;
				g2.drawImage(statisticBlock, x, y, null);
				g2.setPaint(Color.gray);
				g2.setFont(new Font("arial",Font.BOLD, 18));
				txt = "Fag";
				txtWidth = g2.getFontMetrics().stringWidth(txt);
				txtX = statisticBlock.getWidth(null)/2 - txtWidth/2;
				g2.drawString(txt, x + txtX, y+20);
				if(data.containsKey("1")) {
					String valTxt = data.get("3").toString();
					g2.setPaint(Color.white);
					g2.setFont(new Font("arial", Font.BOLD, 18));
					txtWidth = g2.getFontMetrics().stringWidth(valTxt);
					txtX = statisticBlock.getWidth(null)/2 - txtWidth/2;
					g2.drawString(valTxt, x + txtX, y+60);
				}
				
				//draw stejar
				x = GUIUtil.main.getWidth()-statisticBlock.getWidth(null)-20-200;
				y = 420;
				g2.drawImage(statisticBlock, x, y, null);
				g2.setPaint(Color.gray);
				g2.setFont(new Font("arial",Font.BOLD, 18));
				txt = "Stejar";
				txtWidth = g2.getFontMetrics().stringWidth(txt);
				txtX = statisticBlock.getWidth(null)/2 - txtWidth/2;
				g2.drawString(txt, x + txtX, y+20);
				if(data.containsKey("1")) {
					String valTxt = data.get("4").toString();
					g2.setPaint(Color.white);
					g2.setFont(new Font("arial", Font.BOLD, 18));
					txtWidth = g2.getFontMetrics().stringWidth(valTxt);
					txtX = statisticBlock.getWidth(null)/2 - txtWidth/2;
					g2.drawString(valTxt, x + txtX, y+60);
				}
	}
	
	public void showData(Map<String, Object> data) {
	  	this.data = data;
	}
	
}
