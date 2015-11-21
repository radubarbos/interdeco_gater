package ro.barbos.gater.model;

public class GeneralResponse {

	private int code;
	private String message;
	private GeneralEntity data;
	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the data
	 */
	public GeneralEntity getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(GeneralEntity data) {
		this.data = data;
	}
	
	
}
