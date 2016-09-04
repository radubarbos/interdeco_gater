package ro.barbos.gater.cutprocessor.strategy;

import org.junit.Ignore;
import org.junit.Test;
import ro.barbos.TestUtils;
import ro.barbos.gater.cutprocessor.diagram.CutDiagram;
import ro.barbos.gater.model.LumberLog;
import ro.barbos.gater.model.Product;

import java.util.ArrayList;
import java.util.List;

public class SimpleVerticalCutStrategyTest {

    @Ignore
    @Test
	public void testOneProductCut() {
		SimpleVerticalCutStrategy cutStrategy = new SimpleVerticalCutStrategy();
        LumberLog lumberLog = TestUtils.createLumberLog(300d, 450d, 3200d);
        List<Product> products = new ArrayList<>();
		products.add(create105_17_3100());
		List<Integer> pieces = new ArrayList<>();
		pieces.add(100);
		
		CutDiagram diagram = cutStrategy.cutLumberLog(lumberLog, products, pieces);
	}


    public static Product createProduct(Long width, Long thick, Long length) {
		Product product = new Product();
		product.setWidth(width);
		product.setThick(thick);
		product.setLength(length);
		return product;
	}
	
	public static Product create105_17_3100() {
		return createProduct(105L, 17L, 3100L);
	}

}
