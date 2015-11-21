package ro.barbos.gater.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


import ro.barbos.gater.data.IDPlateManager;
import ro.barbos.gater.model.LumberLog;
import ro.barbos.gater.model.LumberLogEntry;
import ro.barbos.gater.model.LumberStack;
import ro.barbos.gui.ConfigLocalManager;

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
				" idplate, stack) values (");
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
		insertLumberLog.append(stack.getId()).append(")");
		
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
		
		Connection con =null;
	    Statement stm =null;
	    ResultSet rs = null;
	    try {
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
	    			updateCutPlan.append("complete = complete + ").append(percent);
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
	
}
