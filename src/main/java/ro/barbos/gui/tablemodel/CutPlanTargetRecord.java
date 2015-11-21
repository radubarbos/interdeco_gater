package ro.barbos.gui.tablemodel;

import ro.barbos.gater.model.Product;

public class CutPlanTargetRecord {

	private Product product;
	private Double targetMCub;
	private Long pieces;
	/**
	 * @return the product
	 */
	public Product getProduct() {
		return product;
	}
	/**
	 * @param product the product to set
	 */
	public void setProduct(Product product) {
		this.product = product;
	}
	/**
	 * @return the targetMCub
	 */
	public Double getTargetMCub() {
		return targetMCub;
	}
	/**
	 * @param targetMCub the targetMCub to set
	 */
	public void setTargetMCub(Double targetMCub) {
		this.targetMCub = targetMCub;
	}
	/**
	 * @return the pieces
	 */
	public Long getPieces() {
		return pieces;
	}
	/**
	 * @param pieces the pieces to set
	 */
	public void setPieces(Long pieces) {
		this.pieces = pieces;
	}
	
	
	
}
