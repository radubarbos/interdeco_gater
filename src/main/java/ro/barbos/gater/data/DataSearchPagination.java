package ro.barbos.gater.data;

public class DataSearchPagination {

	private int pageIndex = -1;
	private int pageSize = 50;
	
	public DataSearchPagination(){
		
	}
	
	public DataSearchPagination(int pageIndex, int pageSize){
	this.pageIndex = pageIndex;
	this.pageSize = pageSize;
	}
	
	/**
	 * @return the pageIndex
	 */
	public int getPageIndex() {
		return pageIndex;
	}
	/**
	 * @param pageIndex the pageIndex to set
	 */
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}
	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	
}
