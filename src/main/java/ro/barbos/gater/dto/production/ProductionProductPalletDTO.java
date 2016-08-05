package ro.barbos.gater.dto.production;

import ro.barbos.gater.model.Machine;
import ro.barbos.gater.model.Product;
import ro.barbos.gater.model.User;

import java.util.Date;

/**
 * Created by radu on 8/5/2016.
 */
public class ProductionProductPalletDTO {

    private Long id;
    private Product product;
    private Date entryDate;
    private int quantity;
    private int packageNo;
    private Long lumberType;
    private Machine machine;
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPackageNo() {
        return packageNo;
    }

    public void setPackageNo(int packageNo) {
        this.packageNo = packageNo;
    }

    public Long getLumberType() {
        return lumberType;
    }

    public void setLumberType(Long lumberType) {
        this.lumberType = lumberType;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
