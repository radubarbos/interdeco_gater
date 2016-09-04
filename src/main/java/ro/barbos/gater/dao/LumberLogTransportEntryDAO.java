package ro.barbos.gater.dao;

import ro.barbos.gater.model.LumberLogTransportEntry;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by radu on 8/22/2016.
 */
public class LumberLogTransportEntryDAO extends EntityDAO<LumberLogTransportEntry> {

    public LumberLogTransportEntry getOpenEntry(Long userId) {
        Logger logger = Logger.getLogger("dao");
        LumberLogTransportEntry entity = null;

        StringBuilder sql = new StringBuilder("select Id, EntryDate, UserId, Status, 0 as Volume, 0 as LumberCount, 0 as MarginVolume, 0 as Cost, NULL as CostConfig, NULL as SupplierId, NULL as TransportCertificateId from LumberLogTransportEntry where UserId=").append(userId).append(" and Status = -1 order by EntryDate");

        Connection con = null;
        Statement stm = null;
        ResultSet rs = null;
        try {
            con = DataAccess.getInstance().getDatabaseConnection();
            con.setAutoCommit(true);
            stm = con.createStatement();
            logger.fine(sql.toString());
            rs = stm.executeQuery(sql.toString());
            if (rs.next()) {
                entity = fromResultSet(rs);
            }
        } catch (Exception e) {
            logger.warning(e.getMessage());
            logger.log(Level.INFO, "Error", e);
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

    public List<LumberLogTransportEntry> getEntries() {
        Logger logger = Logger.getLogger("dao");
        List<LumberLogTransportEntry> results = new ArrayList<>();

        StringBuilder sql = new StringBuilder("select e.Id, e.EntryDate, e.UserId, e.Status, e.Cost,e.CostConfig, count(l.id) as LumberCount, sum((l.volume/1000000000)) as Volume, sum((l.MarginVolume/1000000000)) as MarginVolume,l.SupplierId,l.TransportCertificateId from LumberLogTransportEntry e left join lumberlog l on l.TransportEntryId = e.Id where e.Status = 0 group by e.Id order by e.EntryDate desc");

        Connection con = null;
        Statement stm = null;
        ResultSet rs = null;
        try {
            con = DataAccess.getInstance().getDatabaseConnection();
            con.setAutoCommit(true);
            stm = con.createStatement();
            logger.fine(sql.toString());
            rs = stm.executeQuery(sql.toString());
            while (rs.next()) {
                results.add(fromResultSet(rs));
            }
        } catch (Exception e) {
            logger.warning(e.getMessage());
            logger.log(Level.INFO, "Error", e);
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
        return results;
    }

    @Override
    protected LumberLogTransportEntry fromResultSet(ResultSet rs) throws SQLException {
        LumberLogTransportEntry transportEntry = new LumberLogTransportEntry();
        transportEntry.setId(rs.getLong("Id"));
        transportEntry.setEntryDate(rs.getDate("EntryDate"));
        transportEntry.setUserId(rs.getLong("UserId"));
        transportEntry.setFinished(rs.getInt("Status") == 0);
        transportEntry.setVolume(rs.getDouble("Volume"));
        transportEntry.setLumberLogCount(rs.getInt("LumberCount"));
        transportEntry.setMarginVolume(rs.getDouble("MarginVolume"));
        transportEntry.setCost(rs.getDouble("Cost"));
        transportEntry.setCostConfig(rs.getString("CostConfig"));
        if (rs.wasNull()) {
            transportEntry.setCostConfig(null);
        }
        transportEntry.setSupplierId(rs.getLong("SupplierId"));
        if (rs.wasNull()) {
            transportEntry.setSupplierId(null);
        }
        transportEntry.setCertificateId(rs.getLong("TransportCertificateId"));
        if (rs.wasNull()) {
            transportEntry.setCertificateId(null);
        }
        return transportEntry;
    }

    @Override
    public StringBuilder getFindByIdQuery(Long id) {
        return null;
    }

    @Override
    public StringBuilder getFindAllQuery() {
        return null;
    }

    @Override
    public LumberLogTransportEntry store(LumberLogTransportEntry entity) {
        Logger logger = Logger.getLogger("dao");

        SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        StringBuilder insertSql = new StringBuilder("insert into LumberLogTransportEntry(EntryDate,UserId,Status) values(");
        insertSql.append("'").append(dateTimeFormatter.format(entity.getEntryDate())).append("',");
        insertSql.append("").append(entity.getUserId()).append(",");
        insertSql.append("").append(entity.getFinished() == true ? 0 : -1).append(")");

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

    public boolean update(LumberLogTransportEntry entity) {
        Logger logger = Logger.getLogger("dao");

        StringBuilder updateSql = new StringBuilder("update LumberLogTransportEntry set Status=");
        updateSql.append("").append(entity.getFinished() == true ? 0 : -1);
        updateSql.append(",Cost=").append(entity.getCost());
        if (entity.getCostConfig() != null) {
            updateSql.append(",CostConfig='").append(entity.getCostConfig()).append("'");
        } else {
            updateSql.append(",CostConfig=NULL");
        }
        updateSql.append(" where Id = ").append(entity.getId());

        boolean status = true;

        Connection con = null;
        Statement stm = null;
        try {
            con = DataAccess.getInstance().getDatabaseConnection();
            con.setAutoCommit(true);
            stm = con.createStatement();
            logger.fine(updateSql.toString());
            int rez = stm.executeUpdate(updateSql.toString());
            if (rez > 0) {
                status = true;
            }
        } catch (Exception e) {
            logger.warning(e.getMessage());
            logger.log(Level.INFO, "Error", e);
            status = false;
        } finally {
            if (stm != null) try {
                stm.close();
            } catch (Exception e) {
            }
        }
        return status;
    }

    @Override
    public boolean delete(LumberLogTransportEntry entity) {
        return false;
    }
}
