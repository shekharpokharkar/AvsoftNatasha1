package com.example.avsoft.entities;

import java.math.BigDecimal;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "User_Payment_Details")
@IdClass(UserPaymentID.class)
public class UserPayment {

	@Id
	private int userId;
	@Id
	private int batchId;

	private BigDecimal requestedAmount;
	private String status;
	private int totalPaidAmount;
	
	

	
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

	@Override
	public String toString() {
		return "UserPayment [userId=" + userId + ", batchId=" + batchId + ", requestedAmount=" + requestedAmount
				+ ", status=" + status + ", totalPaidAmount=" + totalPaidAmount + "]";
	}

	public UserPayment(int userId, int batchId) {

		this.userId = userId;
		this.batchId = batchId;
	}

	public UserPayment() {

	}

	

}
