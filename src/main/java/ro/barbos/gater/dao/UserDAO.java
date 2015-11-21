package ro.barbos.gater.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ro.barbos.gater.model.User;
import ro.barbos.gater.model.UserRights;

public class UserDAO {

	public static List<User> getUsers() {
		Logger logger = Logger.getLogger("dao");
		List<User> users = null;
		
		StringBuilder sql = new StringBuilder("select ID, UserName, FullName, RightsLevel from User order by ID ");
		
		Connection con =null;
	    Statement stm =null;
	    ResultSet rs = null;
	    try {
	    	con = DataAccess.getInstance().getDatabaseConnection();
	    	con.setAutoCommit(true);
	    	stm = con.createStatement();
	    	logger.info(sql.toString());
	    	rs = stm.executeQuery(sql.toString());
	    	users = new ArrayList<User>();
	    	while(rs.next())
	    	{
	    		User user = new User();
	    		user.setID(rs.getInt("ID"));
	    		user.setName(rs.getString("FullName"));
	    		user.setUserName(rs.getString("UserName"));
	    		UserRights rights = new UserRights();
	    		rights.setRightsLevel(rs.getInt("RightsLevel"));
	    		user.setRights(rights);
	    		users.add(user);
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
	    return users;
	}
}
