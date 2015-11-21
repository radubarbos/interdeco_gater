package ro.barbos.gater.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ro.barbos.gater.data.IDPlateManager;
import ro.barbos.gater.model.IDPlate;

public class IDPlateDAO {

	public static List<IDPlate> getAvailablePlates() {
		Logger logger = Logger.getLogger("dao");
		List<IDPlate> plates = null;
		
		StringBuilder sql = new StringBuilder("select ID, Label from idplate where id not in (select distinct idplate from lumberlog) order by Label ");
		
		Connection con =null;
	    Statement stm =null;
	    ResultSet rs = null;
	    try {
	    	con = DataAccess.getInstance().getDatabaseConnection();
	    	con.setAutoCommit(true);
	    	stm = con.createStatement();
	    	logger.info(sql.toString());
	    	rs = stm.executeQuery(sql.toString());
	    	plates = new ArrayList<IDPlate>();
	    	while(rs.next())
	    	{
	    		IDPlate idPlate = new IDPlate();
	    		idPlate.setId(rs.getLong("ID"));
	    		idPlate.setLabel(rs.getString("Label"));
	    		plates.add(idPlate);
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
	    return plates;
	}
	
	public static List<IDPlate> getAllPlates() {
		Logger logger = Logger.getLogger("dao");
		List<IDPlate> plates = null;
		
		StringBuilder sql = new StringBuilder("select i.id, i.label, l.idplate from idplate i left join lumberlog l on i.id = l.idplate order by i.label");
		
		Connection con =null;
	    Statement stm =null;
	    ResultSet rs = null;
	    try {
	    	con = DataAccess.getInstance().getDatabaseConnection();
	    	con.setAutoCommit(true);
	    	stm = con.createStatement();
	    	logger.info(sql.toString());
	    	rs = stm.executeQuery(sql.toString());
	    	plates = new ArrayList<IDPlate>();
	    	while(rs.next())
	    	{
	    		IDPlate idPlate = new IDPlate();
	    		idPlate.setId(rs.getLong("ID"));
	    		idPlate.setLabel(rs.getString("Label"));
	    		rs.getInt(3);
	    		if(!rs.wasNull()) {
	    			idPlate.setStatus(true);
	    		}
	    		plates.add(idPlate);
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
	    return plates;
	}
	
	public static IDPlate create(String plate) {
		Logger logger = Logger.getLogger("dao");
		IDPlate newPlate = null;
		
		StringBuilder checkSql = new StringBuilder("select id from idplate where label='");
		checkSql.append(DataAccess.escapeString(plate)).append("'");
		StringBuilder insertSql = new StringBuilder("insert into idplate(label) values('");
		insertSql.append(DataAccess.escapeString(plate)).append("')");
		
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
	    		  newPlate = new IDPlate();
	    		  newPlate.setId(rs.getLong(1));
	    		  newPlate.setLabel(plate);
	    		  IDPlateManager.addAvailablePlate(newPlate);
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
	    return newPlate;
	}
	
	public static boolean delete(IDPlate plate) {
		Logger logger = Logger.getLogger("dao");
		boolean status = false;
		
		StringBuilder checkSql = new StringBuilder("select id from lumberlog where idplate = ").append(plate.getId());		
		StringBuilder deleteSql = new StringBuilder("delete from idplate where id = ").append(plate.getId());
		
		
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
	        logger.info(deleteSql.toString());		
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
	
	public static List<IDPlate> getUsedPlates() {
		Logger logger = Logger.getLogger("dao");
		List<IDPlate> plates = null;
		
		Connection con =null;
	    Statement stm =null;
	    ResultSet rs = null;
	    try {
	    	con = DataAccess.getInstance().getDatabaseConnection();
	    	con.setAutoCommit(true);
	    	stm = con.createStatement();
	    	logger.info("select ID, Label from idplate where id in (select distinct idplate from lumberlog)");
	    	rs = stm.executeQuery("select ID, Label from idplate where id in (select distinct idplate from lumberlog) order by Label");
	    	plates = new ArrayList<IDPlate>();
	    	while(rs.next())
	    	{
	    		IDPlate idPlate = new IDPlate();
	    		idPlate.setId(rs.getLong("ID"));
	    		idPlate.setLabel(rs.getString("Label"));
	    		plates.add(idPlate);
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
	    return plates;
	}
	
}
