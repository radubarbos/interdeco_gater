package ro.barbos.gater.model;

import java.util.Date;

/**
 * Created by radu on 8/17/2016.
 */
public class LumberLogTransportCertificate {

    private Long id;
    private Date entryDate;
    private String code;
    private Date codeCreationTime;
    private String document;

    private String serialCode;
    private String serialNo;

    private String loadPlace;
    private Date transportLeaveDate;

    private String unloadPlace;
    private Date transportArrivalDate;

    private String docCreator;
    private String docCreatorName;

    private String transportName;
    private String transportPlate;
    private String driverName;
    private String driverId;

    private Long supplierId;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getCodeCreationTime() {
        return codeCreationTime;
    }

    public void setCodeCreationTime(Date codeCreationTime) {
        this.codeCreationTime = codeCreationTime;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getLoadPlace() {
        return loadPlace;
    }

    public void setLoadPlace(String loadPlace) {
        this.loadPlace = loadPlace;
    }

    public Date getTransportLeaveDate() {
        return transportLeaveDate;
    }

    public void setTransportLeaveDate(Date transportLeaveDate) {
        this.transportLeaveDate = transportLeaveDate;
    }

    public String getUnloadPlace() {
        return unloadPlace;
    }

    public void setUnloadPlace(String unloadPlace) {
        this.unloadPlace = unloadPlace;
    }

    public Date getTransportArrivalDate() {
        return transportArrivalDate;
    }

    public void setTransportArrivalDate(Date transportArrivalDate) {
        this.transportArrivalDate = transportArrivalDate;
    }

    public String getDocCreator() {
        return docCreator;
    }

    public void setDocCreator(String docCreator) {
        this.docCreator = docCreator;
    }

    public String getDocCreatorName() {
        return docCreatorName;
    }

    public void setDocCreatorName(String docCreatorName) {
        this.docCreatorName = docCreatorName;
    }

    public String getTransportName() {
        return transportName;
    }

    public void setTransportName(String transportName) {
        this.transportName = transportName;
    }

    public String getTransportPlate() {
        return transportPlate;
    }

    public void setTransportPlate(String transportPlate) {
        this.transportPlate = transportPlate;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getSerialCode() {
        return serialCode;
    }

    public void setSerialCode(String serialCode) {
        this.serialCode = serialCode;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }


    @Override
    public String toString() {
        return code;
    }
}
