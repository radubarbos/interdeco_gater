package ro.barbos.gater.dto;

import java.util.List;

public class LumberLogFilterDTO {
	
	private List<Long> stacks;
	private List<Long> idPlates;
	private long minLength = -1;
	private boolean available;
    private Integer status;
    private Long transportEntryId;

	/**
	 * @return the stacks
	 */
	public List<Long> getStacks() {
		return stacks;
	}

	/**
	 * @param stacks the stacks to set
	 */
	public void setStacks(List<Long> stacks) {
		this.stacks = stacks;
	}

	/**
	 * @return the idPlates
	 */
	public List<Long> getIdPlates() {
		return idPlates;
	}

	/**
	 * @param idPlates the idPlates to set
	 */
	public void setIdPlates(List<Long> idPlates) {
		this.idPlates = idPlates;
	}

	/**
	 * @return the available
	 */
	public boolean isAvailable() {
		return available;
	}

	/**
	 * @param available the available to set
	 */
	public void setAvailable(boolean available) {
		this.available = available;
	}

	/**
	 * @return the minLength
	 */
	public long getMinLength() {
		return minLength;
	}

	/**
	 * @param minLength the minLength to set
	 */
	public void setMinLength(long minLength) {
		this.minLength = minLength;
	}

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getTransportEntryId() {
        return transportEntryId;
    }

    public void setTransportEntryId(Long transportEntryId) {
        this.transportEntryId = transportEntryId;
    }
}
