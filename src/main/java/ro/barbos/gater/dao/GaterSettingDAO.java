package ro.barbos.gater.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ro.barbos.gater.cutprocessor.CutterSettings;
import ro.barbos.gater.data.METRIC;
import ro.barbos.gater.model.Blade;
import ro.barbos.gater.model.GaterSetting;

public class GaterSettingDAO {

	public static List<GaterSetting> getSettings() {
		Logger logger = Logger.getLogger("dao");
		List<GaterSetting> settings = null;
		
		StringBuilder sql = new StringBuilder("select ID, Name, Val, Metric from GaterSetting");
		
		Connection con =null;
	    Statement stm =null;
	    ResultSet rs = null;
	    try {
	    	con = DataAccess.getInstance().getDatabaseConnection();
	    	con.setAutoCommit(true);
	    	stm = con.createStatement();
	    	logger.info(sql.toString());
	    	rs = stm.executeQuery(sql.toString());
	    	settings = new ArrayList<>();
	    	boolean toleranceEdge = false;
	    	while(rs.next())
	    	{
	    		GaterSetting setting = new GaterSetting();
	    		setting.setId(rs.getLong("ID"));
	    		setting.setName(rs.getString("Name"));
	    		setting.setValue(rs.getDouble("Val"));
	    		setting.setMetric(METRIC.getById(rs.getInt("Metric")));
	    		settings.add(setting);
	    		if(setting.getName().startsWith("Tol")) {
	    			toleranceEdge = true;
	    		}
	    	}/*
	    	if(!toleranceEdge) {
	    		GaterSetting setting = new GaterSetting();
	    		setting.setName("Toleranta margine");
	    		setting.setValue(CutterSettings.EDGE_TOLERANCE);
	    		setting.setMetric(METRIC.getById(1));
	    		settings.add(setting);
	    	}*/
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
	    return settings;
	}
	
	public static List<GaterSetting> updateSettings(List<GaterSetting> newSettings) {
		Logger logger = Logger.getLogger("dao");
		boolean updateStatus = true;
		
		StringBuilder sql = new StringBuilder("update GaterSetting set Val = ?, Metric = ? where ID = ?");
		logger.info(sql.toString());
		logger.info("settings no: "+newSettings.size());
		Connection con =null;
	    PreparedStatement stm =null;
	    ResultSet rs = null;
	   // GaterSetting insert = null;
	    try {
	    	con = DataAccess.getInstance().getDatabaseConnection();
	    	con.setAutoCommit(true);
	    	stm = con.prepareStatement(sql.toString());
	    	for(int index =0; index < newSettings.size(); index++) {
	    		GaterSetting setting = newSettings.get(index);
	    		/*if(setting.getId() == null) {
	    			insert = setting;
	    			continue;
	    		}*/
	    		stm.setDouble(1, setting.getValue());
	    		stm.setInt(2, setting.getMetric().ordinal()+1);
	    		stm.setLong(3, setting.getId());
	    		int row = stm.executeUpdate();
	    		if(row == 0) {
	    			updateStatus = false;
	    		}
	    	}
	    	/*if(insert != null) {
	    		Statement stm2 = con.createStatement();
	    		stm2.executeUpdate("insert into GaterSetting(Name, Val, Metric) values('"+DataAccess.escapeString(insert.getName()) + "',"+insert.getValue()+","+(insert.getMetric().ordinal()+1)+")");
	    		stm2.close();
	    	}*/
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
	    //return updateStatus ? GaterSettingDAO.getSettings() : null;
	    return GaterSettingDAO.getSettings();
	}
	
}
