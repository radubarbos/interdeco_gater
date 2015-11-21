package ro.barbos.gater.cutprocessor;

import java.util.Comparator;

import ro.barbos.gater.model.Product;

public class WidthComparator implements Comparator<Product> {

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Product o1, Product o2) {
		if(o1.getWidth() < o2.getWidth()) {
			return -1;
		}  
		else if(o1.getWidth() > o2.getWidth()) {
			return 1;
		}
		return 0;
	}
	
	

}
