package com.example.avsoft.dtos;

import java.math.BigDecimal;

public class UserPaymentResponseDTO {
	private int userId;
	private Long batchId;
	private BigDecimal requestedAmount;
	private String status;
	private int totalPaidAmount;
	private int batchTotalFees;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Long getBatchId() {
		return batchId;
	}

	public void setBatchId(Long batchId) {
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

	@Override
	public String toString() {
		return "UserPaymentResponseDTO [userId=" + userId + ", batchId=" + batchId + ", requestedAmount="
				+ requestedAmount + ", status=" + status + ", totalPaidAmount=" + totalPaidAmount + ", batchTotalFees="
				+ batchTotalFees + "]";
	}

	public UserPaymentResponseDTO(int userId, Long batchId, BigDecimal requestedAmount, String status,
			int totalPaidAmount, int batchTotalFees) {

		this.userId = userId;
		this.batchId = batchId;
		this.requestedAmount = requestedAmount;
		this.status = status;
		this.totalPaidAmount = totalPaidAmount;
		this.batchTotalFees = batchTotalFees;
	}

	public UserPaymentResponseDTO() {

	}

}
