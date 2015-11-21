package ro.barbos.gui.exswing;

import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;
//http://java-swing-tips.blogspot.ro/2009/01/create-auto-suggest-jcombobox.html
public class SuggestionJComboBox<E> extends JComboBox<E> implements KeyListener {

	private Vector<E> itemIndex;
	private boolean shouldHide = false;
	
	public SuggestionJComboBox(E items[]) {
		super(items);
		itemIndex = new Vector<E>();
		setEditable(true);
		this.getEditor().getEditorComponent().addKeyListener(this);
		addToIndex(items);
	}
	
	private void addToIndex(E items[]) {
		itemIndex.addAll(Arrays.asList(items));
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		JTextField textField = (JTextField)e.getSource();
	    String text = textField.getText();
	    shouldHide = false;
	    switch(e.getKeyCode()) {
	      case KeyEvent.VK_RIGHT:
	        for(E s: itemIndex) {
	          if(s.toString().startsWith(text)) {
	            textField.setText(s.toString());
	            return;
	          }
	        }
	        break;
	      case KeyEvent.VK_ENTER:
	        /*if(!list.contains(text)) {
	          list.addElement(text);
	          Collections.sort(itemIndex);
	          this.setSuggestionModel(getSuggestedModel(itemIndex, text), text);
	        }*/
	        shouldHide = true;
	        break;
	      case KeyEvent.VK_ESCAPE:
	        shouldHide = true;
	        break;
	    }
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(final KeyEvent e) {
		final SuggestionJComboBox comboBox = this;
		EventQueue.invokeLater(new Runnable() {
		      @Override public void run() {
		        String text = ((JTextField)e.getSource()).getText();
		        if(text.length()==0) {
		          comboBox.setSuggestionModel(new DefaultComboBoxModel(itemIndex), "");
		          comboBox.hidePopup();
		        }else{
		          ComboBoxModel m = getSuggestedModel(itemIndex, text);
		          if(m.getSize()==0 || shouldHide) {
		            comboBox.hidePopup();
		          }else{
		            comboBox.setSuggestionModel(m, text);
		            comboBox.showPopup();
		          }
		        }
		      }
		    });
	}
	
	private void setSuggestionModel( ComboBoxModel mdl, String str) {
		    this.setModel(mdl);
		    this.setSelectedIndex(-1);
		    ((JTextField)this.getEditor().getEditorComponent()).setText(str);
		  }
	
		  private  ComboBoxModel getSuggestedModel(
		      Vector<E> list, String text) {
		    DefaultComboBoxModel m = new DefaultComboBoxModel();
		    for(E s: list) {
		      if(s.toString().startsWith(text)) m.addElement(s);
		    }
		    return m;
		  }
		
		  @Override
		  public E getSelectedItem() {
		Object selection = super.getSelectedItem();
		if(selection != null && selection instanceof String) {
			return null;
		}
		return (E)selection;
	}	  
}
