package ro.barbos.gater.dao;

import ro.barbos.gater.cutprocessor.diagram.CutDiagram;
import ro.barbos.gater.data.IDPlateManager;
import ro.barbos.gater.dto.LumberLogFilterDTO;
import ro.barbos.gater.model.*;
import ro.barbos.gui.ConfigLocalManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LumberLogDAO {

	public static boolean saveLumberLog(LumberLog lumberLog, LumberStack stack, LumberLogEntry stockEntry) {
		
		Logger logger = Logger.getLogger("dao");
		boolean status = false;
		boolean saveNewEntry = false;
		StringBuilder insertStockEntry = null;
		if(stockEntry.getId() == null) {
			saveNewEntry = true;
			insertStockEntry = new StringBuilder("insert into lumberentry(user) values(");
			insertStockEntry.append(stockEntry.getUser().getID()).append(")");
		}
		
		StringBuilder insertLumberLog = new StringBuilder("insert into lumberlog( " +
				"small_diameter, medium_diameter, big_diameter, length, reallength, volume, realvolume, lumbertype, lumberclass," +
                " idplate, stack, Status, SupplierId, TransportCertificateId, Margin, MarginVolume, RealMarginVolume, TransportEntryId) values (");
        insertLumberLog.append(lumberLog.getSmallRadius()).append(",");
		insertLumberLog.append("0").append(",");
		insertLumberLog.append(lumberLog.getBigRadius()).append(",");
		insertLumberLog.append(lumberLog.getLength()).append(",");
		insertLumberLog.append(lumberLog.getRealLength()).append(",");
		insertLumberLog.append(lumberLog.getVolume()).append(",");
		insertLumberLog.append(lumberLog.getRealVolume()).append(",");
		insertLumberLog.append(lumberLog.getLumberType()).append(",");
		insertLumberLog.append(lumberLog.getLumberClass()).append(",");
		insertLumberLog.append(lumberLog.getPlate().getId()).append(",");
        insertLumberLog.append(stack.getId()).append(",").append(lumberLog.getStatus()).append(",");
        insertLumberLog.append(lumberLog.getSupplierId()).append(",");
        insertLumberLog.append(lumberLog.getTransportCertifiateId() != null ? lumberLog.getTransportCertifiateId() : "NULL").append(",");
        insertLumberLog.append(lumberLog.getMarginPercent()).append(",");
        insertLumberLog.append(lumberLog.getMarginVolume()).append(",");
        insertLumberLog.append(lumberLog.getMarginRealVolume()).append(",");
        insertLumberLog.append(lumberLog.getTransportEntryId()).append(")");

		StringBuilder insertMapEntrySql = new StringBuilder("insert into lumberentry_to_lumberlog(entryid, lumberlogid) values (");
		
		StringBuilder insertLumberLogDiameter = new StringBuilder("insert into lumberlog_diameter(lumberlog_id, diameter, metric) values(?, ?, 1)"); 
		
		Connection con =null;
	    Statement stm =null;
	    PreparedStatement pstm = null;
	    ResultSet rs = null;
	    try {
	    	con = DataAccess.getInstance().getDatabaseConnection();
	    	con.setAutoCommit(false);
	    	stm = con.createStatement();
	    	logger.info(insertLumberLog.toString()); 
	    	int rez = stm.executeUpdate(insertLumberLog.toString(), Statement.RETURN_GENERATED_KEYS);
	    	if(rez > 0) {
	    		rs = stm.getGeneratedKeys();
	    		if(rs.next()) {
	    			lumberLog.setId(rs.getLong(1));
	    		}
	    	}
	    	if(rs != null) {
	    		rs.close();
	    	}
	    	if(saveNewEntry) {
	    		logger.info(insertStockEntry.toString());
	    		rez = stm.executeUpdate(insertStockEntry.toString(), Statement.RETURN_GENERATED_KEYS);	
	    		if(rez>0) {
	    			rs = stm.getGeneratedKeys();
		    		if(rs.next()) {
		    		  stockEntry.setId(rs.getLong(1));
		    		}	
	    		}
	    	}
	    	if(lumberLog.getMediumRadius() != null && !lumberLog.getMediumRadius().isEmpty()) {
	    		pstm = con.prepareStatement(insertLumberLogDiameter.toString());
	    		for(Double radius: lumberLog.getMediumRadius()) {
	    			pstm.setLong(1, lumberLog.getId());
	    			pstm.setDouble(2, radius);
	    			pstm.executeUpdate();
	    		}
	    	}
	    	if(lumberLog.getId() != null && stockEntry.getId() != null) {
	    		insertMapEntrySql.append(stockEntry.getId()).append(",").append(lumberLog.getId()).append(")");
	    		logger.info(insertMapEntrySql.toString());
	    		rez = stm.executeUpdate(insertMapEntrySql.toString());
	    		if(rez>0) {
	    			status = true;
	    			IDPlateManager.removeAvailablePlate(lumberLog.getPlate());
	    		}
	    	}
	    }catch(Exception e){
	    	try{
		    	   con.rollback();
		       } catch(Exception ex) {}
	       logger.warning(e.getMessage());
	       logger.log(Level.INFO, "Error", e);
	       status = false;
	    }
	    finally{
	    	if(con != null) {
	    		try{
	    		con.setAutoCommit(true);
	    		}catch(Exception ex) {}
	    	}
			if(rs!=null) try{rs.close();}catch(Exception e){}
			if(stm!=null) try{stm.close();}catch(Exception e){}
		}
		
		
		
		return status;
	}
	
	public static boolean deleteLumberLog(LumberLog lumberLog) {
		Logger logger = Logger.getLogger("dao");
		boolean status = false;
		StringBuilder delete = new StringBuilder("delete from lumberlog where id = " + lumberLog.getId());
		
		Connection con =null;
	    Statement stm =null;
	    
	    try {
	    	con = DataAccess.getInstance().getDatabaseConnection();
	    	con.setAutoCommit(true);
	    	stm = con.createStatement();
	    	logger.info(delete.toString()); 
	    	int rez = stm.executeUpdate(delete.toString());
	    	if(rez > 0) {
	    		status = true;
	    		IDPlateManager.addAvailablePlate(lumberLog.getPlate());
	    	}
	    }catch(Exception e){
	       logger.warning(e.getMessage());
	       logger.log(Level.INFO, "Error", e);
	    }
	    finally{
			if(stm!=null) try{stm.close();}catch(Exception e){}
		}
	    
	    return status;
	}
	
	public static boolean markProcessedLumberLog(LumberLog lumberLog, String message) {
		Logger logger = Logger.getLogger("dao");
		boolean status = false;
		
		StringBuilder selectSql = new StringBuilder("select id, small_diameter, medium_diameter, big_diameter, length, volume, lumbertype, lumberclass, stack, idplate, planid from lumberlog where id = ").append(lumberLog.getId());
		StringBuilder deleteSql = new StringBuilder("delete from lumberlog where id = ").append(lumberLog.getId());
		StringBuilder insertSql = new StringBuilder("insert into lumberlog_processed(userid, small_diameter, medium_diameter, big_diameter, length, volume, idlumbertype, idlumberclass, idstack, idplate, message, planId) values (");
		insertSql.append(ConfigLocalManager.currentUser.getID());
		
		StringBuilder cutPlan = new StringBuilder(" select PlanId, Percentage from  CutPlanLumberLogDiagram cp inner join CutPlan plan on  cp.PlanId = plan.id where plan.status = 0 and cp.LumberLogIDPlate = '").append(DataAccess.escapeString(lumberLog.getPlate().getLabel())).append("'");
		StringBuilder checkLastFromPlan = new StringBuilder("select count(*) from lumberlog where planId = ");
		StringBuilder updateCutPlan = new StringBuilder(" update CutPlan set ");

        StringBuilder planPiecesUpdate = new StringBuilder("update cutplanproduct set ProcessedPieces = ProcessedPieces + ? where PlanId= ? and ProductName =?");
		
		Connection con =null;
	    Statement stm =null;
	    ResultSet rs = null;
	    try {
            GeneralResponse diagramResponse = null;
            if(lumberLog.getCutPlanId() != null) {
                diagramResponse = CutPlanDAO.getCutDiagram(lumberLog.getPlate(), lumberLog.getCutPlanId().intValue());
            }
	    	con = DataAccess.getInstance().getDatabaseConnection();
	    	con.setAutoCommit(false);
	    	stm = con.createStatement();
	    	logger.info(selectSql.toString()); 
	    	rs = stm.executeQuery(selectSql.toString());
	    	if(rs.next()) {
	    		insertSql.append(",").append(rs.getDouble(2));
	    		insertSql.append(",").append(rs.getDouble(3));
	    		insertSql.append(",").append(rs.getDouble(4));
	    		insertSql.append(",").append(rs.getDouble(5));
	    		insertSql.append(",").append(rs.getDouble(6));
	    		insertSql.append(",").append(rs.getInt(7));
	    		insertSql.append(",").append(rs.getInt(8));
	    		insertSql.append(",").append(rs.getInt(9));
	    		insertSql.append(",").append(rs.getInt(10));
	    		insertSql.append(",'").append(DataAccess.escapeString(message)).append("'");
	    		insertSql.append(",").append(rs.getInt(11)).append(")");
	    	}
	    	logger.info(insertSql.toString());
	    	int insertRez = stm.executeUpdate(insertSql.toString());
	    	logger.info(deleteSql.toString());
	    	int deleteRez = stm.executeUpdate(deleteSql.toString());
	    	if(insertRez > 0 && deleteRez > 0) {
	    		status = true;
	    		
	    		logger.info(cutPlan.toString());
	    		rs = stm.executeQuery(cutPlan.toString());
	    		Integer planId = null;
	    		if(rs.next()) {
	    			planId = rs.getInt(1);
	    			Double percent = rs.getDouble(2);
	    			updateCutPlan.append("complete = complete + ").append(percent).append(" where id=" + planId);
	    		}
	    		if(planId != null) {
	    			rs = stm.executeQuery(checkLastFromPlan.append(planId).toString());
	    			if(rs.next()) {
	    				int count = rs.getInt(1);
	    				if(count == 0) {
	    					updateCutPlan.append(",status=1 ").append(" where id = ").append(planId);
	    				}
	    			}
	    			logger.info(updateCutPlan.toString());
	    			stm.executeUpdate(updateCutPlan.toString());
	    		}
	    		IDPlateManager.addAvailablePlate(lumberLog.getPlate());
                if(diagramResponse != null) {
                    if(diagramResponse.getCode() == 200) {
                        CutDiagram diagram = (CutDiagram)diagramResponse.getData();
                        PreparedStatement pstm = con.prepareStatement(planPiecesUpdate.toString());
                        Iterator<Map.Entry<String, Integer>> cutInfo = diagram.cutInfo.cutPieces.entrySet().iterator();
                        while(cutInfo.hasNext()) {
                            Map.Entry<String, Integer> productInfo = cutInfo.next();
                            String productName = productInfo.getKey();
                            Integer pieces = productInfo.getValue();System.out.println(productName + " " + pieces);
                            pstm.setInt(1, pieces);
                            pstm.setInt(2, lumberLog.getCutPlanId().intValue());
                            pstm.setString(3, productName);
                            pstm.executeUpdate();
                        }
                        pstm.close();
                    }
                }
	    	}
	    	con.commit();
	    }catch(Exception e){
	    	try{
	    		con.rollback();
	    	}catch(SQLException ex) {}
	       logger.warning(e.getMessage());
	       logger.log(Level.INFO, "Error", e);
	       status = false;
	    }
	    finally{
	    	if(rs!=null) try{rs.close();}catch(Exception e){}
			if(stm!=null) try{stm.close();}catch(Exception e){}
			if(con != null) {
				try {con.setAutoCommit(true);} catch(Exception e){}
			}
		}
	    
	    return status;
	}
	
	public static void refreshLumberLog(LumberLog lumberLog) {
		Logger logger = Logger.getLogger("dao");
		StringBuilder lumberLogData = new StringBuilder("select diameter, metric from lumberlog_diameter where lumberlog_id = ").append(lumberLog.getId());
		
		Connection con =null;
	    Statement stm =null;
	    ResultSet rs = null;
	    try {
	    	con = DataAccess.getInstance().getDatabaseConnection();
	    	con.setAutoCommit(true);
	    	stm = con.createStatement();
	    	logger.info(lumberLogData.toString()); 
	    	rs = stm.executeQuery(lumberLogData.toString());
	    	lumberLog.setMediumRadius(new ArrayList<Double>());
	    	while(rs.next()) {
	    		lumberLog.getMediumRadius().add(rs.getDouble(1));
	    	}
	    }catch(Exception e){
	       logger.warning(e.getMessage());
	       logger.log(Level.INFO, "Error", e);
	    }
	    finally{
	    	if(rs!=null) try{rs.close();}catch(Exception e){}
			if(stm!=null) try{stm.close();}catch(Exception e){}
		}
	}
	
	public static boolean updateLumberLog(LumberLog lumberLog) {
		Logger logger = Logger.getLogger("dao");
		boolean status = false;
		StringBuilder updateLumberLog = new StringBuilder("update lumberlog set small_diameter=").append(lumberLog.getSmallRadius());
		updateLumberLog.append(",big_diameter=").append(lumberLog.getBigRadius());
		updateLumberLog.append(",length=").append(lumberLog.getLength());
		updateLumberLog.append(",reallength=").append(lumberLog.getRealLength());
		updateLumberLog.append(",volume=").append(lumberLog.getVolume());
		updateLumberLog.append(",realvolume=").append(lumberLog.getRealVolume());
		updateLumberLog.append(",lumbertype=").append(lumberLog.getLumberType());
		updateLumberLog.append(",lumberclass=").append(lumberLog.getLumberClass());
		updateLumberLog.append(",stack=").append(lumberLog.getStack().getId());
		updateLumberLog.append(" where id=").append(lumberLog.getId());
		
		StringBuilder insertLumberLogDiameter = new StringBuilder("insert into lumberlog_diameter(lumberlog_id, diameter, metric) values(?, ?, 1)");
		
		Connection con =null;
	    Statement stm =null;
	    PreparedStatement pstm;
	    try {
	    	con = DataAccess.getInstance().getDatabaseConnection();
	    	con.setAutoCommit(false);
	    	stm = con.createStatement();
	    	logger.info(updateLumberLog.toString()); 
	    	stm.executeUpdate(updateLumberLog.toString());
	    	stm.executeUpdate("delete from  lumberlog_diameter where lumberlog_id="+lumberLog.getId());
	    	if(lumberLog.getMediumRadius() != null && !lumberLog.getMediumRadius().isEmpty()) {
	    		pstm = con.prepareStatement(insertLumberLogDiameter.toString());
	    		for(Double radius: lumberLog.getMediumRadius()) {
	    			pstm.setLong(1, lumberLog.getId());
	    			pstm.setDouble(2, radius);
	    			pstm.executeUpdate();
	    		}
	    	}
	    	status = true;
	    }catch(Exception e){e.printStackTrace();
	       logger.warning(e.getMessage());
	       logger.log(Level.INFO, "Error", e);
	       status = false;
	       try{
	    	   con.rollback();
	       } catch(Exception ex) {}
	    }
	    finally{
			if(stm!=null) try{stm.close();}catch(Exception e){}
			if(con != null) {
				try {con.setAutoCommit(true);} catch(Exception e){}
			}
		}
	    
	    return status;
	}

    public List<LumberLog> getPendingEntryLumberLogs(Long userId) {
        Logger logger = Logger.getLogger("dao");
        StringBuilder lumberLogData = new StringBuilder(" select l.*,i.label as plateName,st.name as stackName from lumberlog l left join idplate i on l.idplate = i.id left join lumberstack st on l.stack = st.id join lumberentry_to_lumberlog le on l.id = le.lumberlogid join lumberentry stoe on le.entryid = stoe.id join user u on stoe.user = u.ID where l.Status=-1 and u.ID=").append(userId);

        List<LumberLog> result = new ArrayList<>();

        Connection con = null;
        Statement stm = null;
        ResultSet rs = null;
        try {
            con = DataAccess.getInstance().getDatabaseConnection();
            con.setAutoCommit(true);
            stm = con.createStatement();
            logger.info(lumberLogData.toString());
            rs = stm.executeQuery(lumberLogData.toString());
            while (rs.next()) {
                result.add(fromResultSet(rs));
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

        return result;
    }

    public boolean updateCertificate(Long certificateId, List<Long> lumberLogIds) {
        Logger logger = Logger.getLogger("dao");
        StringBuilder updateSql = new StringBuilder(" update lumberlog set TransportCertificateId = ").append(certificateId);
        updateSql.append(" where id in(");
        for (Long id : lumberLogIds) {
            updateSql.append(id).append(",");
        }
        updateSql.deleteCharAt(updateSql.length() - 1);
        updateSql.append(")");

        boolean status = true;

        Connection con = null;
        Statement stm = null;
        try {
            con = DataAccess.getInstance().getDatabaseConnection();
            con.setAutoCommit(true);
            stm = con.createStatement();
            logger.info(updateSql.toString());
            int count = stm.executeUpdate(updateSql.toString());

        } catch (Exception e) {
            status = false;
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

    public boolean updateSupplier(Long supplierId, List<Long> lumberLogIds) {
        Logger logger = Logger.getLogger("dao");
        StringBuilder updateSql = new StringBuilder(" update lumberlog set TransportCertificateId = NULL, SupplierId = ").append(supplierId);
        updateSql.append(" where id in(");
        for (Long id : lumberLogIds) {
            updateSql.append(id).append(",");
        }
        updateSql.deleteCharAt(updateSql.length() - 1);
        updateSql.append(")");

        boolean status = true;

        Connection con = null;
        Statement stm = null;
        try {
            con = DataAccess.getInstance().getDatabaseConnection();
            con.setAutoCommit(true);
            stm = con.createStatement();
            logger.info(updateSql.toString());
            int count = stm.executeUpdate(updateSql.toString());

        } catch (Exception e) {
            status = false;
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

    public boolean updateStatus(List<Long> lumberLogIds) {
        Logger logger = Logger.getLogger("dao");
        StringBuilder updateSql = new StringBuilder(" update lumberlog set Status = 0 ");
        updateSql.append(" where id in(");
        for (Long id : lumberLogIds) {
            updateSql.append(id).append(",");
        }
        updateSql.deleteCharAt(updateSql.length() - 1);
        updateSql.append(")");

        boolean status = true;

        Connection con = null;
        Statement stm = null;
        try {
            con = DataAccess.getInstance().getDatabaseConnection();
            con.setAutoCommit(true);
            stm = con.createStatement();
            logger.info(updateSql.toString());
            int count = stm.executeUpdate(updateSql.toString());

        } catch (Exception e) {
            status = false;
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


    protected LumberLog fromResultSet(ResultSet rs) throws SQLException {
        LumberLog lumberLog = new LumberLog();
        lumberLog.setId(rs.getLong("id"));
        lumberLog.setLength(rs.getDouble("length"));
        lumberLog.setRealLength(rs.getLong("reallength"));
        lumberLog.setVolume(rs.getDouble("volume"));
        lumberLog.setRealVolume(rs.getDouble("realvolume"));
        lumberLog.setSmallRadius(rs.getDouble("small_diameter"));
        lumberLog.setBigRadius(rs.getDouble("big_diameter"));
        lumberLog.setLumberType(rs.getLong("lumbertype"));
        lumberLog.setLumberClass(rs.getLong("lumberclass"));
        lumberLog.setCutPlanId(rs.getLong("planId"));
        LumberStack stack = new LumberStack();
        stack.setName(rs.getString("stackName"));
        stack.setId(rs.getLong("stack"));
        lumberLog.setStack(stack);
        IDPlate plate = new IDPlate();
        plate.setId(rs.getLong("idplate"));
        plate.setLabel(rs.getString("plateName"));
        lumberLog.setPlate(plate);
        lumberLog.setSupplierId(rs.getLong("SupplierId"));
        lumberLog.setTransportCertifiateId(rs.getLong("TransportCertificateId"));
        if (rs.wasNull()) {
            lumberLog.setTransportCertifiateId(null);
        }
        lumberLog.setMarginPercent(rs.getInt("Margin"));
        lumberLog.setMarginVolume(rs.getDouble("MarginVolume"));
        lumberLog.setMarginRealVolume(rs.getDouble("RealMarginVolume"));

        return lumberLog;
    }

    public List<LumberLog> findAll(LumberLogFilterDTO filter) {
        Logger logger = Logger.getLogger("dao");
        StringBuilder selectSql = new StringBuilder(" select l.*,i.label as plateName,st.name as stackName from lumberlog l left join idplate i on l.idplate = i.id left join lumberstack st on l.stack = st.id join lumberentry_to_lumberlog le on l.id = le.lumberlogid join lumberentry stoe on le.entryid = stoe.id join user u on stoe.user = u.ID");
        if (filter != null) {
            selectSql.append(" where ");
            String operator = "";
            if (filter.getStatus() != null) {
                selectSql.append(operator).append(" l.Status=").append(filter.getStatus());
                operator = " and ";
            }
            if (filter.getTransportEntryId() != null) {
                selectSql.append(operator).append(" l.TransportEntryId=").append(filter.getTransportEntryId());
                operator = " and ";
            }
        }
        List<LumberLog> result = new ArrayList<>();

        Connection con = null;
        Statement stm = null;
        ResultSet rs = null;
        try {
            con = DataAccess.getInstance().getDatabaseConnection();
            con.setAutoCommit(true);
            stm = con.createStatement();
            logger.info(selectSql.toString());
            rs = stm.executeQuery(selectSql.toString());
            while (rs.next()) {
                result.add(fromResultSet(rs));
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

        return result;
    }

}
