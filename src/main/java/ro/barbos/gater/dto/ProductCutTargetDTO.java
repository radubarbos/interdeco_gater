package ro.barbos.gater.dto;

public class ProductCutTargetDTO {

	private String product;
	private Long targetPieces = 0L;
	private Long cutPieces = 0L;
	private Double targetVolume =0D;
	private Double productVolume =0D;
	/**
	 * @return the product
	 */
	public String getProduct() {
		return product;
	}
	/**
	 * @param product the product to set
	 */
	public void setProduct(String product) {
		this.product = product;
	}
	/**
	 * @return the targetPieces
	 */
	public Long getTargetPieces() {
		return targetPieces;
	}
	/**
	 * @param targetPieces the targetPieces to set
	 */
	public void setTargetPieces(Long targetPieces) {
		this.targetPieces = targetPieces;
	}
	/**
	 * @return the cutPieces
	 */
	public Long getCutPieces() {
		return cutPieces;
	}
	/**
	 * @param cutPieces the cutPieces to set
	 */
	public void setCutPieces(Long cutPieces) {
		this.cutPieces = cutPieces;
	}
	
	public boolean isTargetReached() {
		return cutPieces >= targetPieces;
	}
	
	public Long getRemainingCount() {
		return targetPieces - cutPieces;
	}
	
	public void addCutPieces(Long pieces) {
		cutPieces += pieces;
	}
	/**
	 * @return the targetVolume
	 */
	public Double getTargetVolume() {
		return targetVolume;
	}
	/**
	 * @param targetVolume the targetVolume to set
	 */
	public void setTargetVolume(Double targetVolume) {
		this.targetVolume = targetVolume;
	}
	/**
	 * @return the productVolume
	 */
	public Double getProductVolume() {
		return productVolume;
	}
	/**
	 * @param productVolume the productVolume to set
	 */
	public void setProductVolume(Double productVolume) {
		this.productVolume = productVolume;
	}
	
	
}
