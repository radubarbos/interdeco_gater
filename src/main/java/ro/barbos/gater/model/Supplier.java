package ro.barbos.gater.model;

import java.util.Date;

/**
 * Created by radu on 8/11/2016.
 */
public class Supplier {
    private long id;
    private Date entryDate;
    private String registerNo;
    private String title;
    private String address;
    private boolean useStatus;
    private String areaCode;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegisterNo() {
        return registerNo;
    }

    public void setRegisterNo(String registerNo) {
        this.registerNo = registerNo;
    }

    public boolean isUseStatus() {
        return useStatus;
    }

    public void setUseStatus(boolean useStatus) {
        this.useStatus = useStatus;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
}
