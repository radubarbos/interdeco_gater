package ro.barbos.gater.cutprocessor;

import ro.barbos.gater.model.LumberLog;
import ro.barbos.gater.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by radu on 3/7/2016.
 */
public class DefaultCutOptionsCalculatorData {

    private List<Product> selectedProducts = new ArrayList<>();

    private List<List<Product>> products = new ArrayList<>();

    private LumberLog lumberLog;

    public List<Product> getSelectedProducts() {
        return selectedProducts;
    }

    public void setSelectedProducts(List<Product> selectedProducts) {
        this.selectedProducts = selectedProducts;
    }

    public List<List<Product>> getProducts() {
        return products;
    }

    public void setProducts(List<List<Product>> products) {
        this.products = products;
    }

    public LumberLog getLumberLog() {
        return lumberLog;
    }

    public void setLumberLog(LumberLog lumberLog) {
        this.lumberLog = lumberLog;
    }
}
