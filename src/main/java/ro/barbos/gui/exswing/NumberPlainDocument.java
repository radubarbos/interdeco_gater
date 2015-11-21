package ro.barbos.gui.exswing;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class NumberPlainDocument extends PlainDocument {
	
	@Override
    public void insertString(int offs, String str, AttributeSet a)
            throws BadLocationException
    {

      try {
            Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return;
        }

        super.insertString(offs, str, a);
    }  

}
