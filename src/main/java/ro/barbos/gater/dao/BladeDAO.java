package ro.barbos.gater.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.PreparedStatement;

import ro.barbos.gater.data.METRIC;
import ro.barbos.gater.model.Blade;

public class BladeDAO {
	
	public static List<Blade> getBlades() {
		Logger logger = Logger.getLogger("dao");
		List<Blade> blades = null;
		
		StringBuilder sql = new StringBuilder("select ID, Name, Thick, Metric from Blades");
		
		Connection con =null;
	    Statement stm =null;
	    ResultSet rs = null;
	    try {
	    	con = DataAccess.getInstance().getDatabaseConnection();
	    	con.setAutoCommit(true);
	    	stm = con.createStatement();
	    	logger.info(sql.toString());
	    	rs = stm.executeQuery(sql.toString());
	    	blades = new ArrayList<>();
	    	while(rs.next())
	    	{
	    		Blade blade = new Blade();
	    		blade.setId(rs.getLong("ID"));
	    		blade.setName(rs.getString("Name"));
	    		blade.setThick(rs.getDouble("Thick"));
	    		blade.setMetric(METRIC.getById(rs.getInt("Metric")));
	    		blades.add(blade);
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
	    return blades;
	}
	
	public static List<Blade> updateBlades(List<Blade> newBlades) {
		Logger logger = Logger.getLogger("dao");
		boolean updateStatus = true;
		
		StringBuilder sql = new StringBuilder("update Blades set Thick = ?, Metric = ? where ID = ?");
		logger.info(sql.toString());
		logger.info("blades no: "+newBlades.size());
		Connection con =null;
	    PreparedStatement stm =null;
	    ResultSet rs = null;
	    try {
	    	con = DataAccess.getInstance().getDatabaseConnection();
	    	con.setAutoCommit(true);
	    	stm = con.prepareStatement(sql.toString());
	    	for(int index =0; index < newBlades.size(); index++) {
	    		Blade blade = newBlades.get(index);
	    		stm.setDouble(1, blade.getThick());
	    		stm.setInt(2, blade.getMetric().ordinal()+1);
	    		stm.setLong(3, blade.getId());
	    		int row = stm.executeUpdate();
	    		if(row == 0) {
	    			updateStatus = false;
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
	    return updateStatus ? BladeDAO.getBlades() : null;
	}

}
