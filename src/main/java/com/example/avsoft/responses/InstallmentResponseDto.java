package com.example.avsoft.responses;

import java.time.LocalDate;

import java.time.LocalDateTime;

import com.example.avsoft.enums.PaymentMode;
import com.example.avsoft.enums.Status;

public class InstallmentResponseDto {
	private int id;
	private int installmentNo;
	private long payAmount;
	private LocalDateTime paymentDate;
	private int batchId;
	private int remainingAmount;
	private LocalDate nextInstallmentDate;
	private PaymentMode paymentMode;
	private String remark;
	private String transactionId;
	private int nextInstallmentAmount;
	private Status status;

	// User details
	private int userId;
	private String userName;

	// private String mobileNo;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getInstallmentNo() {
		return installmentNo;
	}

	public void setInstallmentNo(int installmentNo) {
		this.installmentNo = installmentNo;
	}

	public long getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(long payAmount) {
		this.payAmount = payAmount;
	}

	public LocalDateTime getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDateTime paymentDate) {
		this.paymentDate = paymentDate;
	}

	public int getRemainingAmount() {
		return remainingAmount;
	}

	public void setRemainingAmount(int remainingAmount) {
		this.remainingAmount = remainingAmount;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getBatchId() {
		return batchId;
	}

	public void setBatchId(int i) {
		this.batchId = i;
	}

	public LocalDate getNextInstallmentDate() {
		return nextInstallmentDate;
	}

	public void setNextInstallmentDate(LocalDate nextInstallmentDate) {
		this.nextInstallmentDate = nextInstallmentDate;
	}

	public PaymentMode getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public int getNextInstallmentAmount() {
		return nextInstallmentAmount;
	}

	public void setNextInstallmentAmount(int nextInstallmentAmount) {
		this.nextInstallmentAmount = nextInstallmentAmount;
	}
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
