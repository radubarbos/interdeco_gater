package ro.barbos.gui;

import java.awt.Component;
import java.util.HashMap;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

public class MainContainer extends JDesktopPane implements InternalFrameListener
{
	private HashMap<String,Boolean> framesState = new HashMap<String,Boolean>();

	public void addFrame(JInternalFrame frame,String code)
	{
		closeFrames();
		add(frame);
		frame.setLocation(0, 0);
		frame.setSize(getWidth(), getHeight());
		frame.setVisible(true);
		framesState.put(code,true);
		frame.addInternalFrameListener(this);
		if(frame instanceof GeneralFrame)
		{
			GUIUtil.info.addFrame((GeneralFrame)frame);
		}
	}
	
	public void addJustFrame(JInternalFrame frame,String code)
	{
		framesState.put(code,true);
		add(frame);
	}
	
	public boolean isFrameSet(String code)
	{
		Boolean state = framesState.get(code);
		if(state==null) return false;
		return state;
	}
	
	public void removeFrameState(String code)
	{
		framesState.remove(code);
		GUIUtil.info.removeFrame(code);
	}
	
	public void closeFrame(String code) {
		for(int index = 0; index < getComponentCount(); index++) {
			Component comp = getComponent(index);
			if(comp instanceof GeneralFrame) {
				if(code.equals(((GeneralFrame)comp).getFrameCode())) {
					((GeneralFrame)comp).dispose();
				}
			}
		}
	}

	public void closeFrames() {
		for(int index = 0; index < getComponentCount(); index++) {
			Component comp = getComponent(index);
			if(comp instanceof GeneralFrame) {
				((GeneralFrame)comp).dispose();
			}
		}
	}
	
	public void closeAllFrames() {
		for(int index = 0; index < getComponentCount(); index++) {
			Component comp = getComponent(index);
			if(comp instanceof JInternalFrame) {
				((JInternalFrame)comp).dispose();
			}
		}
	}
	
	public void closeFrames(int layer) {
		Component[] lowest = getComponentsInLayer(layer);
		for(Component comp: lowest) {
			remove(comp);
		}
	}
	
	public void internalFrameActivated(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	public void internalFrameClosed(InternalFrameEvent e) {
		/*if(e.getInternalFrame() instanceof StockFrame)
			removeFrameState(GUIUtil.STOCK_KEY);
		if(e.getInternalFrame() instanceof OrderQuantityCheckFrame)
			removeFrameState(GUIUtil.ORDER_QTYCHK_KEY);
		if(e.getInternalFrame() instanceof OrderFrame)
			removeFrameState(GUIUtil.ORDER_KEY);
		if(e.getInternalFrame() instanceof ReportMostUsedProductsFrame)
			removeFrameState(GUIUtil.REPORT_MUP_KEY);*/
		if(e.getInternalFrame() instanceof GeneralFrame)
			removeFrameState(((GeneralFrame)e.getInternalFrame()).getFrameCode());
	}

	
	public void internalFrameClosing(InternalFrameEvent e) {
		/*if(e.getInternalFrame() instanceof StockFrame)
			removeFrameState(GUIUtil.STOCK_KEY);
		if(e.getInternalFrame() instanceof OrderQuantityCheckFrame)
			removeFrameState(GUIUtil.ORDER_QTYCHK_KEY);
		if(e.getInternalFrame() instanceof OrderFrame)
			removeFrameState(GUIUtil.ORDER_KEY);
		if(e.getInternalFrame() instanceof ReportMostUsedProductsFrame)
			removeFrameState(GUIUtil.REPORT_MUP_KEY);*/
		if(e.getInternalFrame() instanceof GeneralFrame)
			removeFrameState(((GeneralFrame)e.getInternalFrame()).getFrameCode());
	}

	
	public void internalFrameDeactivated(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	public void internalFrameDeiconified(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	public void internalFrameIconified(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	public void internalFrameOpened(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
