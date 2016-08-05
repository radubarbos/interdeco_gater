package ro.barbos.gater.dao;

/**
 * Created by radu on 8/4/2016.
 */
public enum MachineType {
    PRODUCTION(4001);

    private int code;

    MachineType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
