package ro.barbos.gater.dto;

/**
 * Created by radu on 9/5/2016.
 */
public class SupplierStockDTO {

    private Long id;
    private String name;
    private Double totalVolume;
    private Long totalLumberLogs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(Double totalVolume) {
        this.totalVolume = totalVolume;
    }

    public Long getTotalLumberLogs() {
        return totalLumberLogs;
    }

    public void setTotalLumberLogs(Long totalLumberLogs) {
        this.totalLumberLogs = totalLumberLogs;
    }
}
