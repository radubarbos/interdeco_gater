package ro.barbos.gater.dao;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import ro.barbos.gater.cutprocessor.CutPlanSenquence;
import ro.barbos.gater.cutprocessor.diagram.CutDiagram;
import ro.barbos.gater.dto.ProductCutTargetDTO;
import ro.barbos.gater.model.CutPlan;
import ro.barbos.gater.model.GeneralResponse;
import ro.barbos.gater.model.IDPlate;

public class CutPlanDAO {

	public static GeneralResponse create(CutPlan cutPlan) {
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
		boolean doSave = true;
		Logger logger = Logger.getLogger("dao");
		GeneralResponse response = new GeneralResponse();
		response.setCode(1);
		
		String planDate = dateFormater.format(cutPlan.getDate());
		StringBuilder checkSql = new StringBuilder("select * from CutPlan where entryDate>='").append(planDate).append("' and entryDate<='").append(planDate).append("'");
		StringBuilder checkSql2 = new StringBuilder("select * from CutPlan where status = 0");
		
		StringBuilder insertPlanSql = new StringBuilder("insert into CutPlan(name, description, entrydate, status) values (");
		insertPlanSql.append("'").append(DataAccess.escapeString(cutPlan.getName())).append("',");
		insertPlanSql.append("'").append(DataAccess.escapeString(cutPlan.getDescription())).append("',");
		insertPlanSql.append("'").append(planDate).append("',");
		insertPlanSql.append(cutPlan.getStatus()).append(")");
		StringBuilder insertProduct = new StringBuilder("insert into CutPlanProduct(PlanId, ProductName, TargetVolume, TargetPieces, CutPieces) values(?, ?, ? ,?, ?)");
		StringBuilder insertDiagram = new StringBuilder("insert into CutPlanLumberLogDiagram(PlanId, LumberLogIDPlate, CutDiagramResult, CutDiagram, Percentage) values(?, ?, ? ,?, ?)");
		
		Connection con =null;
	    Statement stm =null;
	    PreparedStatement pstm = null;
	    ResultSet rs = null;
	    try {
	    	con = DataAccess.getInstance().getDatabaseConnection();
	    	con.setAutoCommit(false);
	    	stm = con.createStatement();
	    	logger.info(checkSql.toString());
	    	rs = stm.executeQuery(checkSql.toString());
	    	if(rs.next()) {
	    		response.setMessage("Planul nu poate fi salvat.\n Mai exista un plan pe aceasta zi. ");
	    		doSave = false;
	    	}
	    	if(cutPlan.getStatus() == 0) {
	    		logger.info(checkSql2.toString());
		    	rs = stm.executeQuery(checkSql2.toString());	
		    	if(rs.next()) {
		    		response.setMessage("Planul nu poate fi salvat.\n Mai exista un plan activ. ");
		    		doSave = false;
		    	}
	    	}
	    	if(doSave) {
	    		//start saving
	    		logger.info(insertPlanSql.toString());
	    		int rez = 	stm.executeUpdate(insertPlanSql.toString(), Statement.RETURN_GENERATED_KEYS);
		    	  if(rez>0) {
		    		  rs = stm.getGeneratedKeys();
		    		  rs.next();
		    		  Integer planId = rs.getInt(1);
		    		  cutPlan.setId(planId);
		    		  if(cutPlan.getCutDataInfo() != null) {
		    			  pstm = con.prepareStatement(insertProduct.toString()); 
		    			  for(ProductCutTargetDTO productInfo: cutPlan.getCutDataInfo()) {
		    				  logger.info("Save cut plan product: " + productInfo.getProduct() + " planid:" + planId + " targetVolum: "+productInfo.getTargetVolume() + " targetPieces: " + productInfo.getTargetPieces() + " cut pieces " + productInfo.getCutPieces());
		    				  pstm.setInt(1, planId);
		    				  pstm.setString(2, productInfo.getProduct());
		    				  pstm.setDouble(3, productInfo.getTargetVolume());
		    				  pstm.setLong(4, productInfo.getTargetPieces());
		    				  pstm.setLong(5, productInfo.getCutPieces());
		    				  pstm.executeUpdate();
		    			  }
		    		  }
		    		  if(cutPlan.getCutDiagrams() != null) {
		    			  if(pstm != null) {
		    				  pstm.close();
		    			  }
		    			  pstm = con.prepareStatement(insertDiagram.toString());
		    			  StringBuilder updateLumbers = new StringBuilder("update lumberlog set planId = "+planId+" where id in(");
		    			  int lumberIndex =0;
		    			  for(CutPlanSenquence seq: cutPlan.getCutDiagrams()) {
		    				  logger.info("Save cut diagram for planid:" + planId + " lumber: "+seq.getLumberLog().getPlate().getLabel());
		    				  pstm.setInt(1, planId);
		    				  pstm.setString(2, seq.getLumberLog().getPlate().getLabel());
		    				  pstm.setString(3, "");
		    				  pstm.setObject(4, seq.getCutDiagram());
		    				  pstm.setDouble(5, seq.getPercentage());
		    				  pstm.executeUpdate();
		    				  if(lumberIndex > 0) updateLumbers.append(",");
		    				  updateLumbers.append(seq.getLumberLog().getId());
		    				  lumberIndex++;
		    			  }
		    			  if(lumberIndex>0) {
		    				  updateLumbers.append(")");
		    				  logger.info(updateLumbers.toString());
		    				  stm.executeUpdate(updateLumbers.toString());
		    			  }
		    		  }
		    		  
		    		  con.commit();
		    		  response.setCode(200);
		    	  }
		    	  //end saving
	    	}
	    }catch(Exception e)
	    {
	      try {
	    	  con.rollback();
	      }	catch(SQLException ee){}
	       logger.warning(e.getMessage());	
	       logger.log(Level.INFO, "Error", e);
	       response.setCode(1);
	       response.setMessage("A aparut o eroare la salvare.");
	    }
	    finally
		{
	    	try{con.setAutoCommit(true);}catch(Exception e){}
			if(rs!=null) try{rs.close();}catch(Exception e){}
			if(stm!=null) try{stm.close();}catch(Exception e){}
			if(pstm!=null) try{pstm.close();}catch(Exception e){}
		}
		
		
		return response;
	}
	
	public static List<CutPlan> getCutPlans() {
		List<CutPlan> plans = new ArrayList<>();
		
		Logger logger = Logger.getLogger("dao");
		StringBuilder sql = new StringBuilder("select id, name, description, entrydate, status, complete from CutPlan order by id");
		
		Connection con =null;
	    Statement stm =null;
	    ResultSet rs = null;
	    try {
	    	con = DataAccess.getInstance().getDatabaseConnection();
	    	con.setAutoCommit(true);
	    	stm = con.createStatement();
	    	logger.info(sql.toString());
	    	rs = stm.executeQuery(sql.toString());
	    	while(rs.next()) {
	    		CutPlan plan = new CutPlan();
	    		plan.setId(rs.getInt(1));
	    		plan.setName(rs.getString(2));
	    		plan.setDescription(rs.getString(3));
	    		plan.setDate(new Date(rs.getTimestamp(4).getTime()));
	    		plan.setStatus(rs.getInt(5));
	    		plan.setCompleted(rs.getDouble(6));
	    		plans.add(plan);
	    	}
	    }
	    	catch(Exception e)
		    {
		       logger.warning(e.getMessage());	
		       logger.log(Level.INFO, "Error", e);
		    }
		    finally
			{
				if(rs!=null) try{rs.close();}catch(Exception e){}
				if(stm!=null) try{stm.close();}catch(Exception e){}
			}
	    
	    return plans;
	}
	
	public static GeneralResponse getCutDiagram(IDPlate plate) {
		GeneralResponse response = new GeneralResponse();
		response.setCode(1);
		Logger logger = Logger.getLogger("dao");
		
		StringBuilder activePlan = new StringBuilder("select id from CutPlan where status = 0");
		StringBuilder sql = new StringBuilder(" select PlanId,CutDiagram from  CutPlanLumberLogDiagram where LumberLogIDPlate = '").append(DataAccess.escapeString(plate.getLabel())).append("' and PlanId = ");
		
		Connection con =null;
	    Statement stm =null;
	    ResultSet rs = null;
	    try {
	    	con = DataAccess.getInstance().getDatabaseConnection();
	    	con.setAutoCommit(true);
	    	stm = con.createStatement();
	    	Integer planId  = null;
	    	logger.info(activePlan.toString());
	    	rs = stm.executeQuery(activePlan.toString());
	    	if(rs.next()) {
	    		planId = rs.getInt(1);
	    	}
	    	if(planId != null) {
	    		sql.append(planId);
	    		logger.info(sql.toString());
		    	rs = stm.executeQuery(sql.toString());
		    	if(rs.next()) {
		    		//CutDiagram diagram = (CutDiagram)rs.getObject(2);
		    		byte[] buf = rs.getBytes(2);
		    	    ObjectInputStream objectIn = null;
		    	    if (buf != null)
		    	        objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));
		    	   
		    	      CutDiagram diagram = (CutDiagram)objectIn.readObject();
		    		response.setData(diagram);
		    		response.setCode(200);
		    	}
		    	else {
		    		response.setMessage("Busteanul nu face parte din planul activ.");
		    	}
	    	}
	    	else {
	    		response.setMessage("Busteanul nu face parte din nici un plan.");
	    	}
	    }
	    	catch(Exception e)
		    {
		       logger.warning(e.getMessage());	
		       logger.log(Level.INFO, "Error", e);
		       response.setMessage("A aparut o accesul bazei de date.");
		    }
		    finally
			{
				if(rs!=null) try{rs.close();}catch(Exception e){}
				if(stm!=null) try{stm.close();}catch(Exception e){}
			}
	    
		
		return response;
	}
	
	public static GeneralResponse activatePlan(CutPlan plan) {
		GeneralResponse response = new GeneralResponse();
		response.setCode(1);
		
		Logger logger = Logger.getLogger("dao");
		StringBuilder deactivateSql = new StringBuilder("update CutPlan set status = 1 where status = 0");
		StringBuilder activateSql = new StringBuilder("update CutPlan set status = 0 where id = ").append(plan.getId());
		
		Connection con =null;
	    Statement stm =null;
	    try {
	    	con = DataAccess.getInstance().getDatabaseConnection();
	    	con.setAutoCommit(false);
	    	stm = con.createStatement();
	    	logger.info(deactivateSql.toString());
	    	stm.executeUpdate(deactivateSql.toString());
	    	logger.info(activateSql.toString());
	    	int rows = stm.executeUpdate(activateSql.toString());
	    	if(rows>0) {
	    		response.setCode(200);
	    	}
	    	con.commit();
	    }
	    	catch(Exception e)
		    {
	    		try{
	    			con.rollback();
	    		}catch(Exception ex){}
		       logger.warning(e.getMessage());	
		       logger.log(Level.INFO, "Error", e);
		       response.setMessage("A aparut o eroare la salvare.");
		    }
		    finally
			{
				if(stm!=null) try{stm.close();}catch(Exception e){}
				if(con!=null) try{con.setAutoCommit(true);}catch(Exception e){}
			}
	    
	    return response;
	}
	
	public static GeneralResponse deactivatePlan(CutPlan plan) {
		GeneralResponse response = new GeneralResponse();
		response.setCode(1);
		
		Logger logger = Logger.getLogger("dao");
		StringBuilder deactivateSql = new StringBuilder("update CutPlan set status = 1 where id = ").append(plan.getId());
		
		Connection con =null;
	    Statement stm =null;
	    try {
	    	con = DataAccess.getInstance().getDatabaseConnection();
	    	stm = con.createStatement();
	    	logger.info(deactivateSql.toString());
	    	int rows = stm.executeUpdate(deactivateSql.toString());
	    	if(rows>0) {
	    		response.setCode(200);
	    	}
	    }
	    	catch(Exception e)
		    {
		       logger.warning(e.getMessage());	
		       logger.log(Level.INFO, "Error", e);
		       response.setMessage("A aparut o eroare la salvare.");
		    }
		    finally {
				if(stm!=null) try{stm.close();}catch(Exception e){}
			}
	    
	    return response;
	}
	
	public static GeneralResponse deletePlan(CutPlan plan) {
		GeneralResponse response = new GeneralResponse();
		response.setCode(1);
		
		Logger logger = Logger.getLogger("dao");
		StringBuilder updateLumberLogSql = new StringBuilder("update lumberlog set planId = NULL where planId = ").append(plan.getId());
		StringBuilder deleteDiagramsSql = new StringBuilder("delete from CutPlanLumberLogDiagram where PlanId = ").append(plan.getId());
		StringBuilder deleteProductSql = new StringBuilder("delete from CutPlanProduct where PlanId = ").append(plan.getId());
		StringBuilder deleteSql = new StringBuilder("delete from CutPlan where id = ").append(plan.getId());
		
		Connection con =null;
	    Statement stm =null;
	    try {
	    	con = DataAccess.getInstance().getDatabaseConnection();
	    	con.setAutoCommit(false);
	    	stm = con.createStatement();
	    	logger.info(updateLumberLogSql.toString());
	    	stm.executeUpdate(updateLumberLogSql.toString());
	    	logger.info(deleteDiagramsSql.toString());
	    	stm.executeUpdate(deleteDiagramsSql.toString());
	    	logger.info(deleteProductSql.toString());
	    	stm.executeUpdate(deleteProductSql.toString());
	    	logger.info(deleteSql.toString());
	    	int rows = stm.executeUpdate(deleteSql.toString());
	    	if(rows>0) {
	    		response.setCode(200);
	    	}
	    }
	    	catch(Exception e)
		    {
		       logger.warning(e.getMessage());	
		       logger.log(Level.INFO, "Error", e);
		       response.setMessage("A aparut o eroare la stergere.");
		    }
		    finally {
				if(stm!=null) try{stm.close();}catch(Exception e){}
				if(con != null) {
					if(con!=null) try{con.setAutoCommit(true);}catch(Exception e){}
				}
			}
	    
	    return response;
	}
	
	public static Map getCutPlanStatistics() {
        Logger logger = Logger.getLogger("dao");
		
		Map<String, Object> statistics = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder("select id, name, entrydate, complete from CutPlan where status = 0");
		
		Integer planId = null;
		
		Connection con =null;
	    Statement stm =null;
	    ResultSet rs = null;
	    try {
	    	con = DataAccess.getInstance().getNewDatabaseConnection();
	    	stm = con.createStatement();
	    	logger.fine(sql.toString()); 
	    	rs = stm.executeQuery(sql.toString());
	    	int toBecut = 0;
	    	int cutted = 0;
	    	if(rs.next()) {
	    		planId = rs.getInt(1);
	    		String name = rs.getString(2);
	    		Date date = new Date(rs.getTimestamp(3).getTime());
	    		Double complete = rs.getDouble(4);
	    		statistics.put("PLAN_NAME", name);
	    		statistics.put("PLAN_DATE", date);
	    		statistics.put("PLAN_COMPLETE", complete);
	    	}
	    	if(planId != null) {
	    		rs = stm.executeQuery("select count(*) from lumberlog where planId = " + planId);
	    		if(rs.next()) {
	    			toBecut = rs.getInt(1);
	    		}
	    		rs = stm.executeQuery("select count(*) from lumberlog_processed where planId = " + planId);
	    		if(rs.next()) {
	    			cutted = rs.getInt(1);
	    		}
	    	}
	    	statistics.put("PLAN_TOBECUT", toBecut);
	    	statistics.put("PLAN_CUTTED", cutted);
	    	statistics.put("PLAN_CUTTARGET", toBecut + cutted);
	    	
	    }catch(Exception e){
	       logger.warning(e.getMessage());
	       logger.log(Level.INFO, "Error", e);
	    }
	    finally{
	    	if(rs!=null) try{rs.close();}catch(Exception e){}
			if(stm!=null) try{stm.close();}catch(Exception e){}
			if(con!=null) try{con.close();}catch(Exception e){}
		}
	    
	    return statistics;
	}
}
