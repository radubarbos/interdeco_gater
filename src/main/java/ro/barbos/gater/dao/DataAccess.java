package ro.barbos.gater.dao;

import java.sql.Connection;
import java.sql.DriverManager;

import com.mysql.jdbc.JDBC4Connection;



public class DataAccess 
{

	private static DataAccess instance;
	private String server="127.0.0.1";
	private String serverURL="jdbc:mysql://127.0.0.1:3306/gater?autoReconnect=true"; 
	private String serverUser="root";
	private String serverPass="root";
	private String driver="org.gjt.mm.mysql.Driver";
	private Connection con;
	
	private DataAccess()
	{
		
	}
	
	public void setDatabaseServer(String serverURL){
		this.serverURL = serverURL;
	}
	
	public void setServerUser(String user){
		this.serverUser=user;
	}
	
	public void setServerPassword(String password){
		this.serverPass=password;
	}
	
	public void setDataDriver(String driver){
		this.driver=driver;
		try{
			Class.forName(driver);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public  static DataAccess getInstance(){
		if(instance == null) instance = new DataAccess();
		return instance;
	}
	
	public Connection getDatabaseConnection(boolean comit){
		if(con !=null) {
			try{
			con.setAutoCommit(comit);
			}catch(Exception e){}
			return con;
		}
	    con =null;
		try{
		   con = DriverManager.getConnection(serverURL,serverUser,serverPass);
		   if(con!=null && !comit) con.setAutoCommit(false);
		}catch (Exception e) {
			con=null;
			e.printStackTrace();
		}
		return con;
	}
	
	public Connection getNewDatabaseConnection() {
		try{
			   return DriverManager.getConnection(serverURL,serverUser,serverPass);
			}catch (Exception e) {
				e.printStackTrace();
			}
		return null;
	}
	
	public void init(){
		 String customDBServer = System.getProperty("DBSERVER");
		 if(customDBServer!=null && customDBServer.trim().length()>0)
		 {
			 server = customDBServer;
			 serverURL="jdbc:mysql://"+server+":3306/gater?autoReconnect=true";
		 }
		 try
		 {
			 Class.forName(driver);
		 }catch(Exception e){}
		 con = getDatabaseConnection(true);
		
	}
	
	public Connection getDatabaseConnection()
	{
		/*if(con!=null && con instanceof JDBC4Connection)
		{
			JDBC4Connection con2 = (JDBC4Connection)con;
			try{
				if(con.isValid(0)==false) 
				{
					
						con = DriverManager.getConnection(serverURL,serverUser,serverPass);
					
				}
			}catch(Exception e){}
		}*/
		if(con != null) {
			try{
				if(con.isClosed()) {
					
				}
			}catch(Exception ex) {
				con = getDatabaseConnection(true);
			}
			
		}
		return con;
	}
	
	public boolean isConnected()
	{
		return con!=null;
	}
	
	public void closeConnection()
	{
		if(con!=null)
		{
			try{con.close();}catch(Exception e){}
		}
		con=null;
	}
	
	public static String escapeString(String text) {
		if(text == null) {
			return null;
		}
		return text.replace("'","''");
	}
	
}
