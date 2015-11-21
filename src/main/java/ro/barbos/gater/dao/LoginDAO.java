package ro.barbos.gater.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import ro.barbos.gater.model.User;
import ro.barbos.gater.model.UserRights;

public class LoginDAO {

	public static User login(String user,String pwd)
	{
		Logger logger = Logger.getLogger("dao");
		User userobj =null;
		Connection con =null;
	    Statement stm =null;
	    ResultSet rs = null;
	    try
	    {
	    	con = DataAccess.getInstance().getDatabaseConnection();
	    	con.setAutoCommit(true);
	    	stm = con.createStatement();
	    	logger.info("select ID,UserName,FullName,RightsLevel from User where UserName='"+user+"' and Password='"+pwd+"'");
	    	rs = stm.executeQuery("select ID,UserName,FullName,RightsLevel from User where UserName='"+user+"' and Password='"+pwd+"'");
	    	if(rs.next())
	    	{
	    		userobj = new User();
	    		userobj.setID(rs.getInt("ID"));
	    		userobj.setName(rs.getString("FullName"));
	    		userobj.setUserName(rs.getString("UserName"));
	    		UserRights rights = new UserRights();
	    		rights.setRightsLevel(rs.getInt("RightsLevel"));
	    		userobj.setRights(rights);
	    	}
	    }catch(Exception e)
	    {
	       logger.warning(e.getMessage());	
	       logger.log(Level.INFO, "Error", e);
	       userobj =null;
	    }
	    finally
		{
			if(rs!=null) try{rs.close();}catch(Exception e){}
			if(stm!=null) try{stm.close();}catch(Exception e){}
		}
	    return userobj;
	}
	
	public static void loadRights(User user)
	{
		Logger logger = Logger.getLogger("dao");
		if(user.getUserName().equals("Administrator") || 1>0)
		{
			UserRights rights = new UserRights();
			rights.setAdmin(true);
			user.setRights(rights);
			return;
		}
		
		Connection con =null;
	    Statement stm =null;
	    ResultSet rs = null;
	    try
	    {
	    	con = DataAccess.getInstance().getDatabaseConnection();
	    	stm = con.createStatement();
	    	logger.fine("select StockRights,OrderRights,ReportRights,ProductRights from UserRights where UserID="+user.getID());
	    	rs = stm.executeQuery("select StockRights,OrderRights,ReportRights,ProductRights,ClientRights from UserRights where UserID="+user.getID());
	    	if(rs.next())
	    	{
	    		UserRights rights = new UserRights();
	    			user.setRights(rights);
	    	}
	    }catch(Exception e)
	    {
	    	logger.warning(e.getMessage());
	    	e.printStackTrace();
	    }
	    finally
		{
			if(rs!=null) try{rs.close();}catch(Exception e){}
			if(stm!=null) try{stm.close();}catch(Exception e){}
		}
	   
	}
	
	
}
