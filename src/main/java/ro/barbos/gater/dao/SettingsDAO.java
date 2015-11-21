package ro.barbos.gater.dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SettingsDAO {

	public static void loadOrderGroupSettings()
	{
		Logger logger = Logger.getLogger("dao");
		StringBuilder sql = new StringBuilder("select Parameter,Value from GSettings where PGroup='ORDER'");
		
		Statement stm =null;
		ResultSet rs =null;
		
		try
		{
			stm = DataAccess.getInstance().getDatabaseConnection().createStatement();
			logger.info(sql.toString());
			rs = stm.executeQuery(sql.toString());
			while(rs.next())
			{
				String param = rs.getString("Parameter");
				String val = rs.getString("Value");
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
	}
	
	public static boolean saveOrderGroupSettings(int procent,boolean extraplus)
	{
		Logger logger = Logger.getLogger("dao");
		StringBuilder up1 = new StringBuilder("update GSettings set Value='").append(procent).append("' where Parameter='EXTRAPROCENT' and PGroup='ORDER'");
		StringBuilder in1 = new StringBuilder("insert into GSettings(Parameter,Value,PGroup) values('EXTRAPROCENT','").append(procent).append("','ORDER')");
	
		StringBuilder up2 = new StringBuilder("update GSettings set Value='").append(extraplus).append("' where Parameter='GENERATE_ON_PLUS' and PGroup='ORDER'");
		StringBuilder in2 = new StringBuilder("insert into GSettings(Parameter,Value,PGroup) values('GENERATE_ON_PLUS','").append(extraplus).append("','ORDER')");
	
		boolean saveStatus = true;
		Statement stm =null;
        try
        {
        	stm = DataAccess.getInstance().getDatabaseConnection().createStatement();
        	logger.info(up1.toString());
        	int up = stm.executeUpdate(up1.toString());
        	logger.info(in1.toString());
        	if(up==0) stm.execute(in1.toString());
        	logger.info(up2.toString());
        	up = stm.executeUpdate(up2.toString());
        	logger.info(in2.toString());
        	if(up==0) stm.execute(in2.toString());
        }
        catch(Exception e)
		{
        	saveStatus = false;
        	logger.warning(e.getMessage());	
		    logger.log(Level.INFO, "Error", e);
		}
		finally
		{
			//if(rs!=null) try{rs.close();}catch(Exception e){}
			if(stm!=null) try{stm.close();}catch(Exception e){}
		}
		
		return saveStatus;
	}
}
