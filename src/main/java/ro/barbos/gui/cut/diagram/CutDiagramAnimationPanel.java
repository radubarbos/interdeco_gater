package ro.barbos.gui.cut.diagram;

import ro.barbos.gater.cutprocessor.CuterManager;
import ro.barbos.gater.cutprocessor.diagram.CutDiagram;
import ro.barbos.gater.cutprocessor.diagram.GaterCutStep;
import ro.barbos.gater.model.LumberLog;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class CutDiagramAnimationPanel extends JPanel implements Runnable {
	
	private static final long serialVersionUID = 1L;
	
	private CutDiagram diagram;
	private LumberLog lumberLog;
	private CutDiagramAnimationDisplay parentFrame;
	private Rectangle2D.Double clipRect;
	private GeneralPath clipPath;
	private double radius = 0;
	private double diameter = 0;
	private boolean running = true;
	private int currentStep = -1;
	private boolean rotating = false;
	private double rotateAngle = 0D;
	private double rotateDelta = 0D;
	private BufferedImage bufferedImage;
	private RadialGradientPaint radialPaint;
	
	private static int frameDelay = 2000;
	
	public CutDiagramAnimationPanel(LumberLog lumberLog, CutDiagram diagram, CutDiagramAnimationDisplay parentFrame) {
		this.diagram = diagram;
		this.lumberLog = lumberLog;
		this.parentFrame = parentFrame;
		diameter = lumberLog.getSmallRadius();
		radius = lumberLog.getSmallRadius()/2;
		clipRect = new Rectangle2D.Double(-radius - 1, -radius -1, 2 * radius + 2, 2 * radius + 2);
		resetBufferedImage();
		radialPaint = new RadialGradientPaint(new Point2D.Double(0, 0), (float)radius, new float[] {0.3f, 0.9f}, new Color[]{new Color(255, 178, 40), new Color(191, 75, 30)});
	}
	
	private void resetBufferedImage() {
		Dimension size = getPreferredSize();
		bufferedImage = new BufferedImage((int)size.getWidth(), (int)size.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(lumberLog == null) {
			return;
		}
		g.drawImage(bufferedImage, 0, 0, null);
	}
	
	private void buildClipRect() {
		if(currentStep == -1 || currentStep == diagram.steps.getStepSequence().size()) {
			clipRect = new Rectangle2D.Double(-radius - 1, -radius -1, 2 * radius + 2, 2 * radius + 2);
			return;
		}
		GaterCutStep step = diagram.steps.getStepSequence().get(currentStep);
		if(step.isStart) {
			double sliceSize = CuterManager.computeGapFromTop(diameter, step.value);  
			clipRect.y += sliceSize;
			clipRect.height -= sliceSize;
		}
		else if(step.isRotate) {
			rotating = true;
			rotateAngle = step.value;
			rotateDelta = 0D;
		}
		else {
			clipRect.y += step.value;
			clipRect.height -= step.value;
			if(clipRect.height < 0) {
				clipRect.height = 0;
			}
		}
	}
	
	private void buildFrameDisplay() {
		resetBufferedImage();
		Graphics2D g2 = (Graphics2D)bufferedImage.getGraphics();
		RenderingHints rh = new RenderingHints(
	             RenderingHints.KEY_TEXT_ANTIALIASING,
	             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	    g2.setRenderingHints(rh);
	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    
	    g2.translate(radius +30, radius + 30);
	    g2.setPaint(Color.blue);
	    
	    Shape clipViewShape = null;
	    if(!this.rotating) {
	    	buildClipRect();
	    	clipViewShape = clipRect;
	    }
	    if(rotating) {
		    clipPath = rotateRectangle(clipRect, rotateDelta); 
			rotateDelta++;
			if(rotateDelta>=rotateAngle) {
				this.rotating = false;
				updateClipRectangle(clipPath);
			}
			clipViewShape = clipPath;
		}
	    g2.setClip(clipViewShape);
	    Ellipse2D.Double lumberLogCircle = new Ellipse2D.Double(-radius, -radius, lumberLog.getSmallRadius(), lumberLog.getSmallRadius());
	    g2.setPaint(radialPaint);
	    g2.fill(lumberLogCircle);
	}
	
	private GeneralPath rotateRectangle(Rectangle2D.Double rectangle, double angle) {
		GeneralPath path = new GeneralPath();
		Point2D.Double leftTop = new Point2D.Double(rectangle.getX(), rectangle.getY());
		leftTop = roatePoint(leftTop, angle);
		path.moveTo(leftTop.x, leftTop.y);
		Point2D.Double rightTop = new Point2D.Double(rectangle.getX() + rectangle.getWidth(), rectangle.getY());
		rightTop = roatePoint(rightTop, angle);
		path.lineTo(rightTop.x, rightTop.y);
		Point2D.Double rightBottom = new Point2D.Double(rectangle.getX() + rectangle.getWidth(), rectangle.getY() + rectangle.getHeight());
		rightBottom = roatePoint(rightBottom, angle);
		path.lineTo(rightBottom.x, rightBottom.y);
		Point2D.Double leftBottom = new Point2D.Double(rectangle.getX(), rectangle.getY() + rectangle.getHeight());
		leftBottom = roatePoint(leftBottom, angle);
		path.lineTo(leftBottom.x, leftBottom.y);
		path.closePath();
		return path;
	}
	
	private Point2D.Double roatePoint(Point2D.Double point, double angle) {
		double radians = Math.toRadians(angle);
		double x1 = point.x * Math.cos(radians) - point.y * Math.sin(radians);
		double y1 = point.x * Math.sin(radians) + point.y * Math.cos(radians);
		point.x = x1;
		point.y = y1;
		return point;
	}
	
	private void updateClipRectangle(GeneralPath clipPath) {
		Rectangle2D rect = clipPath.getBounds2D();
		clipRect.x = rect.getX();
		clipRect.y = rect.getY();
		clipRect.width = rect.getWidth();
		clipRect.height = rect.getHeight();
	}
	
	public Dimension getPreferredSize() {
		if(lumberLog == null) {
			return new Dimension(400, 300);
		}
		return new Dimension(lumberLog.getSmallRadius().intValue() + 300, lumberLog.getSmallRadius().intValue() + 50);
	}
	
	public void play() {
		this.running = true;
		new Thread(this).start();
	}
	
	public void pause() {
		this.running = false;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while(running) {
			GaterCutStep gaterStep = null;
			 if(currentStep >= 0 && !this.rotating) {
				 gaterStep = diagram.steps.getStepSequence().get(currentStep);
				 parentFrame.displayGaterStep(gaterStep, currentStep);
			 }
			 else if(currentStep == -1) {
				 parentFrame.displayGaterStep(gaterStep, currentStep);
			 }
			 
			 this.buildFrameDisplay();
			 this.repaint();
			 int sleep = frameDelay;
			 if(this.rotating) {
				 sleep = 100;
			 }
			 else {
				 this.currentStep++;
				 if(this.currentStep == this.diagram.steps.getStepSequence().size()) {
					 this.currentStep = -1;
				 } 
			 }
			 
			try {
			   Thread.sleep(sleep);
			} catch(InterruptedException ex) {
				
			}
			
		}
	}
}
