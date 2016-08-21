package ro.barbos.gater.dao;

import ro.barbos.gater.model.LumberLogTransportCertificate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by radu on 8/17/2016.
 */
public class LumberLogTransportCertificateDAO extends EntityDAO<LumberLogTransportCertificate> {

    @Override
    public StringBuilder getFindAllQuery() {
        return new StringBuilder("select Id,EntryDate,Code,CodeCreationTime,SerialCode, SerialNo,Document,LoadPlace,TransportLeaveDate,UnloadPlace,TransportArrivalDate,DocCreator,DocCreatorName,TransportName,TransportPlate,DriverName,DriverId,SupplierId from LumberLogTransportCertificate order by Id");
    }

    @Override
    public StringBuilder getFindByIdQuery(Long id) {
        return new StringBuilder("select Id,EntryDate,Code,CodeCreationTime,SerialCode,SerialNo,Document,LoadPlace,TransportLeaveDate,UnloadPlace,TransportArrivalDate,DocCreator,DocCreatorName,TransportName,TransportPlate,DriverName,DriverId,SupplierId from LumberLogTransportCertificate where Id=").append(id);
    }

    @Override
    public LumberLogTransportCertificate store(LumberLogTransportCertificate entity) {
        Logger logger = Logger.getLogger("dao");

        SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        StringBuilder insertSql = new StringBuilder("insert into LumberLogTransportCertificate(EntryDate,SerialCode, SerialNo,SupplierId,Code,CodeCreationTime,Document,LoadPlace,TransportLeaveDate,UnloadPlace,TransportArrivalDate,DocCreator,DocCreatorName,TransportName,TransportPlate,DriverName,DriverId) values(");
        insertSql.append("'").append(dateTimeFormatter.format(entity.getEntryDate())).append("',");
        insertSql.append("'").append(DataAccess.escapeString(entity.getSerialCode())).append("',");
        insertSql.append("'").append(DataAccess.escapeString(entity.getSerialNo())).append("',");
        insertSql.append("").append(entity.getSupplierId()).append(",");
        insertSql.append("'").append(DataAccess.escapeString(entity.getCode())).append("',");
        insertSql.append("'").append(dateTimeFormatter.format(entity.getCodeCreationTime())).append("',");
        if (entity.getDocument() != null) {
            insertSql.append("'").append(DataAccess.escapeString(entity.getDocument())).append("',");
        } else {
            insertSql.append("NULL,");
        }
        insertSql.append("'").append(DataAccess.escapeString(entity.getLoadPlace())).append("',");
        insertSql.append("'").append(dateTimeFormatter.format(entity.getTransportLeaveDate())).append("',");
        insertSql.append("'").append(DataAccess.escapeString(entity.getUnloadPlace())).append("',");
        insertSql.append("'").append(dateTimeFormatter.format(entity.getTransportArrivalDate())).append("',");
        if (entity.getDocCreator() != null) {
            insertSql.append("'").append(DataAccess.escapeString(entity.getDocCreator())).append("',");
        } else {
            insertSql.append("NULL,");
        }
        if (entity.getDocCreatorName() != null) {
            insertSql.append("'").append(DataAccess.escapeString(entity.getDocCreatorName())).append("',");
        } else {
            insertSql.append("NULL,");
        }
        if (entity.getTransportName() != null) {
            insertSql.append("'").append(DataAccess.escapeString(entity.getTransportName())).append("',");
        } else {
            insertSql.append("NULL,");
        }
        insertSql.append("'").append(DataAccess.escapeString(entity.getTransportPlate())).append("',");
        insertSql.append("'").append(DataAccess.escapeString(entity.getDriverName())).append("',");
        if (entity.getDriverId() != null) {
            insertSql.append("'").append(DataAccess.escapeString(entity.getDriverId())).append("')");
        } else {
            insertSql.append("NULL)");
        }


        Connection con = null;
        Statement stm = null;
        ResultSet rs = null;
        try {
            con = DataAccess.getInstance().getDatabaseConnection();
            con.setAutoCommit(true);
            stm = con.createStatement();
            logger.fine(insertSql.toString());
            int rez = stm.executeUpdate(insertSql.toString(), Statement.RETURN_GENERATED_KEYS);
            if (rez > 0) {
                rs = stm.getGeneratedKeys();
                rs.next();
                entity.setId(rs.getLong(1));
            }
        } catch (Exception e) {
            logger.warning(e.getMessage());
            logger.log(Level.INFO, "Error", e);
            entity = null;
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (Exception e) {
            }
            if (stm != null) try {
                stm.close();
            } catch (Exception e) {
            }
        }
        return entity;
    }

    @Override
    public boolean delete(LumberLogTransportCertificate entity) {
        Logger logger = Logger.getLogger("dao");
        boolean status = false;

        StringBuilder deleteSql = new StringBuilder("delete from LumberLogTransportCertificate where Id = ").append(entity.getId());

        Connection con = null;
        Statement stm = null;

        try {
            con = DataAccess.getInstance().getDatabaseConnection();
            con.setAutoCommit(true);
            stm = con.createStatement();
            logger.fine(deleteSql.toString());
            int rez = stm.executeUpdate(deleteSql.toString());
            if (rez > 0) {
                status = true;
            }
        } catch (Exception e) {
            logger.warning(e.getMessage());
            logger.log(Level.INFO, "Error", e);
        } finally {
            if (stm != null) try {
                stm.close();
            } catch (Exception e) {
            }
        }
        return status;
    }

    @Override
    protected LumberLogTransportCertificate fromResultSet(ResultSet rs) throws SQLException {
        LumberLogTransportCertificate certificate = new LumberLogTransportCertificate();
        certificate.setId(rs.getLong("Id"));
        certificate.setEntryDate(rs.getDate("EntryDate"));
        certificate.setCode(rs.getString("Code"));
        certificate.setCodeCreationTime(rs.getDate("CodeCreationTime"));
        certificate.setSerialCode(rs.getString("SerialCode"));
        certificate.setSerialNo(rs.getString("SerialNo"));
        certificate.setDocument(rs.getString("Document"));
        certificate.setLoadPlace(rs.getString("LoadPlace"));
        certificate.setTransportLeaveDate(rs.getDate("TransportLeaveDate"));
        certificate.setUnloadPlace(rs.getString("UnloadPlace"));
        certificate.setTransportArrivalDate(rs.getDate("TransportArrivalDate"));
        certificate.setDocCreator(rs.getString("DocCreator"));
        certificate.setDocCreatorName(rs.getString("DocCreatorName"));
        certificate.setTransportName(rs.getString("TransportName"));
        certificate.setTransportPlate(rs.getString("TransportPlate"));
        certificate.setDriverName(rs.getString("DriverName"));
        certificate.setDriverId(rs.getString("DriverId"));
        certificate.setSupplierId(rs.getLong("SupplierId"));
        return certificate;
    }
}
