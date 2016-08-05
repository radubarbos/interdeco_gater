package ro.barbos.gater.model;

/**
 * Created by radu on 8/2/2016.
 */
public class Machine {

    private int id;
    private String label;
    private int type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
