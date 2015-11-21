package ro.barbos.gater.model;

import java.io.Serializable;

public class UserRights implements Serializable {
	
	public static final long serialVersionUID = 1L;
	
	private boolean isAdmin = false;
	
	private Integer rightsLevel = -1;
	
	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	/**
	 * @return the rightsLevel
	 */
	public Integer getRightsLevel() {
		return rightsLevel;
	}

	/**
	 * @param rightsLevel the rightsLevel to set
	 */
	public void setRightsLevel(Integer rightsLevel) {
		this.rightsLevel = rightsLevel;
		if(rightsLevel != null && rightsLevel == 0) {
			isAdmin = true;
		} 
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	
	
}
