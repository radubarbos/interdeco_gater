package ro.barbos.gater.model;

public class Product {

	private Long id;
	private String name;
	private Long length;
	private Long width;
	private Long thick;
	
	
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the length
	 */
	public Long getLength() {
		return length;
	}
	/**
	 * @param length the length to set
	 */
	public void setLength(Long length) {
		this.length = length;
	}
	/**
	 * @return the width
	 */
	public Long getWidth() {
		return width;
	}
	/**
	 * @param width the width to set
	 */
	public void setWidth(Long width) {
		this.width = width;
	}
	/**
	 * @return the thick
	 */
	public Long getThick() {
		return thick;
	}
	/**
	 * @param thick the thick to set
	 */
	public void setThick(Long thick) {
		this.thick = thick;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name;
	}
	
	
	
}
