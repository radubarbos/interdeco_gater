package ro.barbos.gater.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ro.barbos.gater.data.IDPlateManager;
import ro.barbos.gater.data.METRIC;
import ro.barbos.gater.model.IDPlate;
import ro.barbos.gater.model.LumberStack;

public class LumberStackDAO {
	
	public static boolean create(LumberStack stack) {
		Logger logger = Logger.getLogger("dao");
		boolean status = false;
		
		StringBuilder checkSql = new StringBuilder(" select id from lumberstack where minimum>=");
		checkSql.append(stack.getMinimum()).append(" and minimum<=").append(stack.getMaximum());
		
		StringBuilder insertSql = new StringBuilder("insert into lumberstack(name, minimum, maximum, metric) values('");
		insertSql.append(DataAccess.escapeString(stack.getName())).append("',");
		insertSql.append(stack.getMinimum()).append(",").append(stack.getMaximum()).append(", 1)");
		
		Connection con =null;
	    Statement stm =null;
	    ResultSet rs = null;
	    try {
	    	con = DataAccess.getInstance().getDatabaseConnection();
	    	con.setAutoCommit(true);
	    	stm = con.createStatement();
	    	logger.info(checkSql.toString());
	    	rs = stm.executeQuery(checkSql.toString());
	    	if(!rs.next()) {
	    	int rez = 	stm.executeUpdate(insertSql.toString(), Statement.RETURN_GENERATED_KEYS);
	    	  if(rez>0) {
	    		  rs = stm.getGeneratedKeys();
	    		  rs.next();
	    		  stack.setId(rs.getLong(1));
	    		  status = true;
	    	  }
	    	}
	    }catch(Exception e)
	    {
	       logger.warning(e.getMessage());	
	       logger.log(Level.INFO, "Error", e);
	    }
	    finally
		{
			if(rs!=null) try{rs.close();}catch(Exception e){}
			if(stm!=null) try{stm.close();}catch(Exception e){}
		}
	    return status;
	}

	public static LumberStack getLumberStack(Double radius, METRIC metric) {
		Logger logger = Logger.getLogger("dao");
		LumberStack lumberStack = null;
		
		switch (metric) {
		case MILIMETER: radius = radius/1;break;
		case CENTIMETER: radius = radius/10;break;
		case DECIMETER: radius = radius/100;break;
		case METER: radius = radius/1000;break;
		}
		
		StringBuilder sql = new StringBuilder("select id, name from lumberstack where ");
		sql.append(radius).append(">=minimum and maximum>=").append(radius);;
		
		Connection con =null;
	    Statement stm =null;
	    ResultSet rs = null;
	    try {
	    	con = DataAccess.getInstance().getDatabaseConnection();
	    	con.setAutoCommit(true);
	    	stm = con.createStatement();
	    	logger.info(sql.toString());
	    	rs = stm.executeQuery(sql.toString());
	    	if(rs.next()) {
	    		lumberStack = new LumberStack();
	    		lumberStack.setId(rs.getLong("id"));
	    		lumberStack.setName(rs.getString("name"));
	    	}
	    }catch(Exception e){
	       logger.warning(e.getMessage());	
	       logger.log(Level.INFO, "Error", e);
	    }
	    finally{
			if(rs!=null) try{rs.close();}catch(Exception e){}
			if(stm!=null) try{stm.close();}catch(Exception e){}
		}
		
		return lumberStack;
	}
	
	public static List<LumberStack> getAllstack() {
		Logger logger = Logger.getLogger("dao");
		List<LumberStack> stacks = null;
		
		StringBuilder sql = new StringBuilder("select id, name, minimum, maximum from lumberstack");
		
		Connection con =null;
	    Statement stm =null;
	    ResultSet rs = null;
	    try {
	    	con = DataAccess.getInstance().getDatabaseConnection();
	    	con.setAutoCommit(true);
	    	stm = con.createStatement();
	    	logger.info(sql.toString());
	    	rs = stm.executeQuery(sql.toString());
	    	stacks = new ArrayList<LumberStack>();
	    	while(rs.next())
	    	{
	    		LumberStack lumberStack = new LumberStack();
	    		lumberStack.setId(rs.getLong("id"));
	    		lumberStack.setName(rs.getString("name"));
	    		lumberStack.setMinimum(rs.getDouble(3));
	    		lumberStack.setMaximum(rs.getDouble(4));
	    		stacks.add(lumberStack);
	    	}
	    }catch(Exception e)
	    {
	       logger.warning(e.getMessage());	
	       logger.log(Level.INFO, "Error", e);
	    }
	    finally
		{
			if(rs!=null) try{rs.close();}catch(Exception e){}
			if(stm!=null) try{stm.close();}catch(Exception e){}
		}
	    return stacks;
	}
	
	public static boolean delete(LumberStack  stack) {
		Logger logger = Logger.getLogger("dao");
		boolean status = false;
		
		StringBuilder checkSql = new StringBuilder("select id from lumberlog where stack = ").append(stack.getId()).append(" limit 1");		
		StringBuilder deleteSql = new StringBuilder("delete from lumberstack where id = ").append(stack.getId());
		
		
		Connection con =null;
	    Statement stm =null;
	    ResultSet rs = null;
	    try {
	    	con = DataAccess.getInstance().getDatabaseConnection();
	    	con.setAutoCommit(true);
	    	stm = con.createStatement();
	    	logger.fine(checkSql.toString());
	    	rs = stm.executeQuery(checkSql.toString());
	    	if(!rs.next()) {
	        logger.fine(deleteSql.toString());		
	    	int rez = stm.executeUpdate(deleteSql.toString());
	    	  if(rez>0) {
	    		  status = true;
	    	  }
	    	}
	    }catch(Exception e)
	    {
	       logger.warning(e.getMessage());	
	       logger.log(Level.INFO, "Error", e);
	    }
	    finally
		{
			if(rs!=null) try{rs.close();}catch(Exception e){}
			if(stm!=null) try{stm.close();}catch(Exception e){}
		}
	    return status;
	}
	
}
