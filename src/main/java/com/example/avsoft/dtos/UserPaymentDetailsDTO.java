package com.example.avsoft.dtos;

import java.math.BigDecimal;
import java.util.List;

public class UserPaymentDetailsDTO {

	private int userId;
	private int batchId;
	private BigDecimal requestedAmount;
	private String status;
	private int totalPaidAmount;
	private int batchTotalFees;

	private List<InstallmentDTO> dto;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getBatchId() {
		return batchId;
	}

	public void setBatchId(int batchId) {
		this.batchId = batchId;
	}

	public BigDecimal getRequestedAmount() {
		return requestedAmount;
	}

	public void setRequestedAmount(BigDecimal requestedAmount) {
		this.requestedAmount = requestedAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getTotalPaidAmount() {
		return totalPaidAmount;
	}

	public void setTotalPaidAmount(int totalPaidAmount) {
		this.totalPaidAmount = totalPaidAmount;
	}

	public int getBatchTotalFees() {
		return batchTotalFees;
	}

	public void setBatchTotalFees(int batchTotalFees) {
		this.batchTotalFees = batchTotalFees;
	}

	public UserPaymentDetailsDTO() {

	}

	public List<InstallmentDTO> getDto() {
		return dto;
	}

	public void setDto(List<InstallmentDTO> dto) {
		this.dto = dto;
	}

}
