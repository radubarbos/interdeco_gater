package ro.barbos.gater.dto;

/**
 * Created by radu on 9/5/2016.
 */
public class LengthStockDTO {

    private Double length;
    private Double totalVolume;
    private Long totalLumberLogs;

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Long getTotalLumberLogs() {
        return totalLumberLogs;
    }

    public void setTotalLumberLogs(Long totalLumberLogs) {
        this.totalLumberLogs = totalLumberLogs;
    }

    public Double getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(Double totalVolume) {
        this.totalVolume = totalVolume;
    }
}
