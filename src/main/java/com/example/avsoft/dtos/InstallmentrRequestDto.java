package com.example.avsoft.dtos;

import java.time.LocalDate;

import com.example.avsoft.enums.PaymentMode;
import com.example.avsoft.enums.Status;

import jakarta.validation.constraints.NotBlank;

public class InstallmentrRequestDto {
	
	private int batchId;
	private int installmentNo;
	private int payAmount;
	private String email;
	private LocalDate nextInstallmentDate;
	private PaymentMode paymentMode;
	private String remark;
	private String transactionId;
	private int nextInstallmentAmount;
	private Status status;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public int getNextInstallmentAmount() {
		return nextInstallmentAmount;
	}

	public void setNextInstallmentAmount(int nextInstallmentAmount) {
		this.nextInstallmentAmount = nextInstallmentAmount;
	}

	public int getInstallmentNo() {
		return installmentNo;
	}

	public void setInstallmentNo(int installmentNo) {
		this.installmentNo = installmentNo;
	}

	public int getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(int payAmount) {
		this.payAmount = payAmount;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getBatchId() {
		return batchId;
	}

	public void setBatchId(int batchId) {
		this.batchId = batchId;
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

}
