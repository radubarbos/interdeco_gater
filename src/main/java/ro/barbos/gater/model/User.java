package ro.barbos.gater.model;

public class User {

	private int ID =-1;
	private String userName=null;
	private String name =null;
	private UserRights rights =null;
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public UserRights getRights() {
		return rights;
	}
	public void setRights(UserRights rights) {
		this.rights = rights;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Override
	public String toString() {
		return name;
	}
	
	
	
}
