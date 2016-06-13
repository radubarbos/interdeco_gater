package ro.barbos.gui.cut.diagram;

import ro.barbos.gater.cutprocessor.CutterSettings;
import ro.barbos.gater.cutprocessor.diagram.*;
import ro.barbos.gater.data.MetricTools;
import ro.barbos.gater.model.LumberLog;
import ro.barbos.gui.ConfigLocalManager;
import ro.barbos.gui.MetricFormatter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CutDiagramPaintPanel extends JPanel implements ActionListener, Printable {

	
	private static final long serialVersionUID = 1L;

    public enum DIAGRAM_CONTENT {FINAL_POSITION, INITIAL_POSITION}

    public static final String ANIMATION_ACTION = "ANIMATION_ACTION";
    public static final String SHOW_INITIAL_POSITION_ACTION = "SHOW_INITIAL_POSITION_ACTION";
    public static final String SHOW_FINAL_POSITION_ACTION = "SHOW_FINAL_POSITION_ACTION";

	private LumberLog lumberLog;
	private CutDiagram diagram;
    private JButton finalPosition;
    private JButton initialPosition;
	private JButton animateCutProcess;
	
	private NumberFormat formatter = NumberFormat.getNumberInstance(ConfigLocalManager.locale);
	
	private boolean ignoreEmptySegments = true;
	
	private BufferedImage bufferedImage;
    private DIAGRAM_CONTENT diagramContent = DIAGRAM_CONTENT.FINAL_POSITION;

	private int printPages = 0;

    String lumberLogId;
    String lumberLogVolume;
    String smallDiameter;
    String bigDiameter;
	
	public CutDiagramPaintPanel(LumberLog lumberLog, CutDiagram diagram) {
		this.lumberLog = lumberLog;
		this.diagram = diagram;
		formatter.setMaximumFractionDigits(2);
		formatter.setMinimumFractionDigits(2);
		setLayout(null);
		animateCutProcess = new JButton("Animatie");
		animateCutProcess.addActionListener(this);
		animateCutProcess.setLocation(-1000, -1000);
        animateCutProcess.setSize(new Dimension(150, animateCutProcess.getPreferredSize().height));
        animateCutProcess.setActionCommand(ANIMATION_ACTION);
		add(animateCutProcess);
        finalPosition = new JButton("Pos. finala");
        finalPosition.addActionListener(this);
        finalPosition.setLocation(-1000, -1000);
        finalPosition.setSize(new Dimension(150, finalPosition.getPreferredSize().height));
        finalPosition.setActionCommand(SHOW_FINAL_POSITION_ACTION);
        add(finalPosition);
        initialPosition = new JButton("Pos. initiala");
        initialPosition.addActionListener(this);
        initialPosition.setLocation(-1000, -1000);
        initialPosition.setSize(new Dimension(150, initialPosition.getPreferredSize().height));
        initialPosition.setActionCommand(SHOW_INITIAL_POSITION_ACTION);
        add(initialPosition);
        lumberLogId = "Bustean: " + lumberLog.getPlate().getLabel();
        lumberLogVolume = MetricFormatter.formatVolume(MetricTools.toMeterCubs(lumberLog.getVolume()));
        smallDiameter = "D. mic: "+lumberLog.getSmallRadius() + " mm";
        bigDiameter = "D. mare: " + lumberLog.getBigRadius() + " mm";
		buildDisplay();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(lumberLog == null) {
			return;
		}
		g.drawImage(bufferedImage, 0, 0, null);
	}
	
	private void resetBufferedImage() {
		Dimension size = getPreferredSize();
		bufferedImage = new BufferedImage((int)size.getWidth(), (int)size.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
	}
	
	private void buildDisplay() {
		resetBufferedImage();
		Graphics2D g2 = (Graphics2D)bufferedImage.getGraphics();
		RenderingHints rh = new RenderingHints(
	             RenderingHints.KEY_TEXT_ANTIALIASING,
	             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	    g2.setRenderingHints(rh);
	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    paintDiagram(g2, 0, -1);
	}
	
	private void paintDiagram(Graphics2D g2, int printType, int widthLimit) {
		int centerOffset = 60;
		int centerOffsetX = 20;
		int firstTxtGap = 40;
		if(printType == 1) {
			centerOffset = 5;
			firstTxtGap = 10;
            centerOffsetX = 5;
		}

        paintLumberLogData(g2);

		if(diagram.cutInfo != null) {
			g2.setPaint(Color.black);
			int x = lumberLog.getSmallRadius().intValue() + firstTxtGap;
			int y = 20;
			if(printType == 0) {
			  initialPosition.setLocation(x, y);
			  finalPosition.setLocation(x, y+25);
			  animateCutProcess.setLocation(x, y+50);
			  this.paintChildren(g2);
			  y += 90;
			}
			g2.drawString("Eficenta taiere: " + formatter.format(CutterSettings.DO_LENGTH_OPTIMIZATION ? diagram.cutInfo.cutVolumeEfficency : diagram.cutInfo.cutLayoutEfficency) + " %", x, y);
			y+= 20;
			if(diagram.cutInfo.cutPieces != null) {
			Iterator<Map.Entry<String, Integer>> it = diagram.cutInfo.cutPieces.entrySet().iterator();
			g2.drawString("Piese taiate: ", x, y);
			y+= 20;
			while(it.hasNext()) {
				Map.Entry<String, Integer> entry = it.next();
				g2.setColor(diagram.cutInfo.colors.get(entry.getKey()));
				g2.drawString(entry.getKey() + " piese: "+entry.getValue(), x, y);
				y+=20;
			}
			}
		}
	    
		if(printType == 1) {
		g2.setClip(null);
		}
		else {
	    paintSteps(g2, printType, false);
	    }
		
		paintCutDiagram(g2, centerOffsetX, centerOffset, printType);

		
	}

    private void paintCutDiagram(Graphics2D g2, int centerOffsetX, int centerOffset, int printType) {
        g2.translate(lumberLog.getSmallRadius()/2 +centerOffsetX, lumberLog.getSmallRadius()/2 + centerOffset);
        g2.setPaint(Color.blue);
        double radius = lumberLog.getSmallRadius()/2;
        Ellipse2D.Double lumberLogCircle = new Ellipse2D.Double(-radius, -radius, lumberLog.getSmallRadius(), lumberLog.getSmallRadius());
        g2.draw(lumberLogCircle);
        if(printType == 0){
            g2.clip(lumberLogCircle);
        }

        if(diagramContent == DIAGRAM_CONTENT.FINAL_POSITION) {
            drawFinalPosition(g2);
        } else {
            drawInitialPosition(g2);
        }




        if(diagram.debugSquare != null) {
            g2.setPaint(Color.cyan);
            Rectangle2D.Double cutSlide = new Rectangle2D.Double(-diagram.debugSquare/2, -diagram.debugSquare/2, diagram.debugSquare, diagram.debugSquare);
            g2.draw(cutSlide);
        }
    }

    private void drawInitialPosition(Graphics2D g2) {
        for(GaterCutStep operation: diagram.steps.getStepSequence()) {
            if(operation.isRotate) {
                g2.rotate(Math.toRadians(operation.value));
            }
        }
        drawFinalPosition(g2);
    }

    private void drawFinalPosition(Graphics2D g2) {
        double radius = lumberLog.getSmallRadius()/2;
        for(GaterOperation operation: diagram.gaterCutFlow) {
            if(operation.rotate()) {
                g2.rotate(Math.PI/2);
            }
        }

        for(GaterOperation operation: diagram.gaterCutFlow) {
            if(operation.rotate()) {
                g2.rotate(-Math.PI/2);
            }
            else {
                GaterSlide slide = (GaterSlide)operation;
                double sliceX = -radius-30;
                double sliceWidth = lumberLog.getSmallRadius() + 60;
                if(sliceX + sliceWidth > slide.rightLimit) {
                    sliceWidth = slide.rightLimit - sliceX;
                }
                g2.setPaint(Color.blue);
                if(CutDiagramPaintManager.SHOW_GATER_CUT) {
                    Rectangle2D.Double cutSlide = new Rectangle2D.Double(sliceX, slide.y, sliceWidth, slide.height);
                    g2.draw(cutSlide);
                }
                if(slide.pieces > 0) {
                    g2.setPaint(slide.color);
                    for(int innerIndex = 0; innerIndex < slide.pieces; innerIndex++) {
                        double x = slide.x + innerIndex * slide.pieceWidth + innerIndex * CutterSettings.MULTIBLADE;
                        Rectangle2D.Double piece = new Rectangle2D.Double(x, slide.y, slide.pieceWidth, slide.height);
                        g2.draw(piece);
                        if(CutDiagramPaintManager.SHOW_PHASE && slide.phase != null) {
                            drawPhaseNumber(g2, piece, slide.phase.getCode());
                        }
                    }
                }
            }
        }

        for(MultibladeCutSlide slide : diagram.multiBladeSlides) {
            g2.setPaint(slide.color);
            for(int innerIndex = 0; innerIndex < slide.pieces; innerIndex++) {
                double x = slide.x + innerIndex * slide.pieceWidth + innerIndex * CutterSettings.MULTIBLADE;
                Rectangle2D.Double piece = new Rectangle2D.Double(x, slide.y, slide.pieceWidth, slide.height);
                g2.draw(piece);
                if(CutDiagramPaintManager.SHOW_PHASE && slide.phase != null) {
                    drawPhaseNumber(g2, piece, slide.phase.getCode());
                }
            }
        }
    }

    private void paintLumberLogData(Graphics2D g2) {

        g2.setPaint(Color.black);
        Font defaultFont = g2.getFont();
        g2.setFont(new Font("arial", Font.BOLD, 16));
        int labelWidth = g2.getFontMetrics().stringWidth(lumberLogId);
        g2.drawString(lumberLogId, 5, 20);

        g2.drawString(lumberLogVolume, 5 + labelWidth + 50, 20);

        g2.drawString(smallDiameter, 5, 50);
        g2.drawString(bigDiameter, 5 + labelWidth + 50, 50);

        g2.setFont(defaultFont);
    }
	
	public Dimension getPreferredSize() {
		if(lumberLog == null) {
			return new Dimension(400, 300);
		}
		return new Dimension(lumberLog.getSmallRadius().intValue() + 400, lumberLog.getSmallRadius().intValue() + 150);
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("ANIMATION_ACTION")) {
            new CutDiagramAnimationDisplay(diagram, lumberLog).setVisible(true);
        } else if(e.getActionCommand().equals("SHOW_FINAL_POSITION_ACTION")) {
            diagramContent = DIAGRAM_CONTENT.FINAL_POSITION;
            buildDisplay();
            revalidate();
            getParent().revalidate();
            repaint();
            getParent().repaint();
        } else if(e.getActionCommand().equals("SHOW_INITIAL_POSITION_ACTION")) {
            diagramContent = DIAGRAM_CONTENT.INITIAL_POSITION;
            buildDisplay();
            revalidate();
            getParent().revalidate();
            repaint();
            getParent().repaint();
        }

	}
	
	private void paintInfo(Graphics2D g2, int printType) {
		if(diagram.cutInfo != null) {
			g2.setPaint(Color.black);
			int x = 30;
			int y = 20;
			if(printType == 0) {
			  animateCutProcess.setLocation(x, y); 
			  this.paintChildren(g2);
			  y += 40;
			}
			g2.drawString("Eficenta taiere: " + formatter.format(diagram.cutInfo.cutLayoutEfficency) + " %", x, y);
			y+= 20;
			if(diagram.cutInfo.cutPieces != null) {
			Iterator<Map.Entry<String, Integer>> it = diagram.cutInfo.cutPieces.entrySet().iterator();
			g2.drawString("Piese taiate: ", x, y);
			y+= 20;
			while(it.hasNext()) {
				Map.Entry<String, Integer> entry = it.next();
				g2.setColor(diagram.cutInfo.colors.get(entry.getKey()));
				g2.drawString(entry.getKey() + " piese: "+entry.getValue(), x, y);
				y+=20;
			}
			}
		}
	}
	
	private void paintSteps(Graphics2D g2, int printType, boolean translated) {//TODO refactor to use steps compile
		int firstTxtGap = 40;
		g2.setColor(Color.black);
		int x = lumberLog.getSmallRadius().intValue() + 200;
		int y = 30;
		if(printType == 1) {
			firstTxtGap = 10;
			if(translated) {
				x = lumberLog.getSmallRadius().intValue()/2 + firstTxtGap;	
				y -= lumberLog.getSmallRadius().intValue()/2;
			}
			else {
				x = 30;
				y = 130;
			}
			
			y+= 100;
		}
		boolean left = diagram.steps.getLeft() != null && (ignoreEmptySegments && diagram.steps.getLeft().cuts.size()>1);
		boolean bottom = diagram.steps.getBottom() != null && (ignoreEmptySegments && diagram.steps.getBottom().cuts.size()>0);
		boolean right = diagram.steps.getRight() != null && (ignoreEmptySegments && diagram.steps.getRight().cuts.size()>1);
		boolean top = diagram.steps.getTop() != null && (ignoreEmptySegments && diagram.steps.getTop().cuts.size()>0);
		boolean multiBlade = diagram.steps.getMultiBlade() != null;
		
		int step = 1;
		if(left) {
			List<Double> segmentSteps = diagram.steps.getLeft().cuts;
			for(int index = 0; index < segmentSteps.size(); index++) {
				g2.drawString("Pasul "  + step +  " : Taiati la: " + formatter.format(segmentSteps.get(index)) + (index == 0 ? (" ( " + formatter.format(CutterSettings.TOP_START - segmentSteps.get(index)) + ")"): ""), x, y);
				step++;
				y+= 20;
			}
			
		}
		if(left && right) {
			g2.drawString("Pasul "  + step +  " : Rotiti 180 grade: ", x, y);
			step++;
			y+= 20;
		}
		else if(left && bottom) {
			g2.drawString("Pasul "  + step +  " : Rotiti dreapta 90 grade: ", x, y);
			step++;
			y+= 20;
		}
		
		if(right) {
			List<Double> segmentSteps = diagram.steps.getRight().cuts;
			for(int index = 0; index < segmentSteps.size(); index++) {
				g2.drawString("Pasul "  + step +  " : Taiati la: " + formatter.format(segmentSteps.get(index)) + (index == 0 ? (" ( " + formatter.format(CutterSettings.TOP_START - segmentSteps.get(index)) + ")"): ""), x, y);
				step++;
				y+= 20;
			}
		}
		if(right && (bottom || top)) {
			g2.drawString("Pasul "  + step +  " : Rotiti dreapta 90 grade: ",x ,y);
			step++;
			y+= 20;
		}
		if(bottom) {
			List<Double> segmentSteps = diagram.steps.getBottom().cuts;
			for(int index = 0; index < segmentSteps.size(); index++) {
				g2.drawString("Pasul "  + step +  " : Taiati la: " + formatter.format(segmentSteps.get(index)) + (index == 0 ? (" ( " + formatter.format(CutterSettings.TOP_START - segmentSteps.get(index)) + ")"): ""), x, y);
				step++;
				y+= 20;
			}
			g2.drawString("Pasul "  + step +  " : Rotiti 180 grade: ", x, y);
			step++;
			y+= 20;
		}
		//show top segment
		if(top) {
			List<Double> segmentSteps = diagram.steps.getTop().cuts;
			for(int index = 0; index < segmentSteps.size(); index++) {
				g2.drawString("Pasul "  + step +  " : Taiati la: " + formatter.format(segmentSteps.get(index)) + (index == 0 ? (" ( " + formatter.format(CutterSettings.TOP_START - segmentSteps.get(index)) + ")"): ""), x, y);
				step++;
				y+= 20;
			}
		}
		if(multiBlade) {
			List<Double> segmentSteps = diagram.steps.getMultiBlade().cuts;
			double endMultiBlade = segmentSteps.size();
			if(bottom) endMultiBlade--;
			for(int index =0; index < endMultiBlade; index++) {
				g2.drawString("Pasul "  + step +  " : Taiati la: " + formatter.format(segmentSteps.get(index)), x, y);
				step++;
				y+= 20;
			}
		}
	}
	
	private void drawPhaseNumber(Graphics2D g2, Rectangle2D.Double piece, int no)  {
		Color original = g2.getColor();
		g2.setPaint(Color.blue);
		double x = piece.getX() + piece.getWidth()/2 - 3;
		double y = piece.getY() + piece.getHeight()/2 + 4;
		g2.drawString(""+no, (int)x, (int)y);
		g2.setColor(original);
	}
	
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
			throws PrinterException {
		if(pageIndex==0) printPages = calculatePrintPages(pageFormat);
		if(pageIndex==printPages) return NO_SUCH_PAGE;
		
		Graphics2D g2 = (Graphics2D)graphics;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		
		g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
		
		if(pageIndex == 0) {
		  paintDiagram(g2, 1, (int)pageFormat.getWidth());
		}
		if(printPages == 1) {
		  paintSteps(g2, 1, true);
		}
		else if(printPages == 2 && pageIndex == 1) {
			paintInfo(g2, 1);
			paintSteps(g2, 1, false);
		}
		
		return PAGE_EXISTS;
	}
	
	private int calculatePrintPages(PageFormat pageFormat) {
		if(lumberLog != null && lumberLog.getSmallRadius() > 420) {
			return 2;
		}
		return 1;
	}
}
