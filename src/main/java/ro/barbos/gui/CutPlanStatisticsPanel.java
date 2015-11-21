package ro.barbos.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CutPlanStatisticsPanel extends StatisticsPanel {

	Map<String, Object> data = new  HashMap<String, Object>();
	NumberFormat formatter = NumberFormat.getNumberInstance(ConfigLocalManager.locale);
	SimpleDateFormat dateFormater = new SimpleDateFormat("dd, MM yyyy");
	
	public CutPlanStatisticsPanel() {
		formatter.setMaximumFractionDigits(0);
		formatter.setMinimumFractionDigits(0);
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
	    
	    Font defaultfont = g2.getFont();
	    if(data != null && data.containsKey("PLAN_NAME") && data.containsKey("PLAN_DATE")) {
	    	g2.setPaint(Color.black);
	    	g2.setFont(new Font("arial", Font.BOLD, 24));
	    	g2.drawString("Plan activ: " + (String)data.get("PLAN_NAME") +"              Data: " + dateFormater.format((Date)data.get("PLAN_DATE")), 100, 50);
	    	
	    	
	    	Double complete = (Double)data.get("PLAN_COMPLETE");
	    	if(complete > 1) {
	    		complete = 1D;
	    	}
	    	Rectangle2D.Double completeRect = new Rectangle2D.Double(150, 100, 200, 300);
	    	double height = complete * completeRect.getHeight();
	    	Rectangle2D.Double completeFillRect = new Rectangle2D.Double(150, 100 + completeRect.getHeight() - height, 200, height);
	    	g2.setPaint(new Color(255, 0, 0, 80));
	    	g2.fill(completeFillRect);
	    	
	    	g2.setColor(Color.black);
	    	g2.setStroke(new BasicStroke(2f));
	    	g2.draw(completeRect);
	    	g2.setFont(new Font("arial", Font.BOLD, 18));
	    	int strWidth = g2.getFontMetrics().stringWidth("Complect");
	    	g2.drawString("Complect", 250 - strWidth/2, 90);
	    	g2.drawString(formatter.format(complete * 100) + " %", 230, 240);
	    	
	    	Rectangle2D.Double lumberLogRect = new Rectangle2D.Double(450, 100, 200, 300);
	    	Integer toBeCut = (Integer)data.get("PLAN_TOBECUT"); 
	    	Integer cutted = (Integer)data.get("PLAN_CUTTED"); 
	    	Integer total = (Integer)data.get("PLAN_CUTTARGET"); 
	    	double cutPercent = 0;
	    	if(total.intValue() != 0) {
	    		cutPercent = cutted / (double)total;
	    		if(cutPercent > 1) {
	    			cutPercent = 1;
	    		}
	    		height = cutPercent * lumberLogRect.getHeight();
	    		Rectangle2D.Double lumberLogFillRect = new Rectangle2D.Double(450, 100 + lumberLogRect.getHeight() - height, 200, height);
		    	g2.setPaint(new Color(255, 0, 0, 80));
		    	g2.fill(lumberLogFillRect);
	    	}
	    	g2.setColor(Color.black);
	    	g2.setStroke(new BasicStroke(2f));
	    	g2.draw(lumberLogRect);
	    	g2.setFont(new Font("arial", Font.BOLD, 18));
	    	strWidth = g2.getFontMetrics().stringWidth("Busteni");
	    	g2.drawString("Busteni", 550 - strWidth/2, 90);
	    	g2.drawString(formatter.format(cutPercent * 100) + " %", 530, 240);
	    	g2.drawString("Total: " + total, 670, 120);
	    	g2.drawString("Taiati: " + cutted, 670, 150);
	    	g2.drawString("Ramasi: " + toBeCut, 670, 180);
	    }
	}
	
	public void showData(Map<String, Object> data) {
	  	this.data = data;
	  	this.repaint();
	}
}
