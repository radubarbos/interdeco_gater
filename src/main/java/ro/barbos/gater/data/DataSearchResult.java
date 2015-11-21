package ro.barbos.gater.data;

import java.util.List;

public class DataSearchResult<E> {

	private Long total;
	private List<E> data;
	/**
	 * @return the total
	 */
	public Long getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(Long total) {
		this.total = total;
	}
	/**
	 * @return the data
	 */
	public List<E> getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(List<E> data) {
		this.data = data;
	}
	
	
}
