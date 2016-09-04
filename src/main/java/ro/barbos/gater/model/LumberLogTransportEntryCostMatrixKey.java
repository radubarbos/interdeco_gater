package ro.barbos.gater.model;

import java.io.Serializable;

/**
 * Created by radu on 8/24/2016.
 */
public class LumberLogTransportEntryCostMatrixKey implements Serializable {

    private Long length;
    private Integer qualityClass;
    private Integer type;

    public LumberLogTransportEntryCostMatrixKey(Long length, Integer qualityClass, Integer type) {
        this.length = length;
        this.qualityClass = qualityClass;
        this.type = type;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LumberLogTransportEntryCostMatrixKey that = (LumberLogTransportEntryCostMatrixKey) o;

        if (!length.equals(that.length)) return false;
        if (!qualityClass.equals(that.qualityClass)) return false;
        if (!type.equals(that.type)) return false;

        return true;
    }

    public Integer getQualityClass() {
        return qualityClass;
    }

    public Integer getType() {
        return type;
    }

    public Long getLength() {
        return length;
    }

    @Override
    public int hashCode() {
        int result = length.hashCode();
        result = 31 * result + qualityClass.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "" + length +
                "," + type +
                "," + qualityClass;
    }
}
