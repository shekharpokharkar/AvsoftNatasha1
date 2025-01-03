package com.example.avsoft.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

import com.example.avsoft.enums.PaymentMode;
import com.example.avsoft.enums.Status;

import java.time.LocalDate;

@Entity
@Table(name = "userPayments")
public class Installment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private int installmentNo;

	@Column(nullable = false)
	private int payAmount;

	@Column(nullable = false)
	private LocalDateTime paymentDate;

	@Column(nullable = false)
	private int remainingAmount;

	
	private LocalDate nextInstallmentDate;
    
	@Enumerated(EnumType.STRING)
	private PaymentMode paymentMode;
	
	 @Column(columnDefinition = "TEXT")
	private String remark;
	 
	 @Column(nullable = false)
	private String transactionId;
	 
	@Column(nullable = false)
	private int nextInstallmentAmount;

	@Column
	@Enumerated(EnumType.STRING)
	private Status status = Status.PENDING;

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

	public LocalDate getNextInstallmentDate() {
		return nextInstallmentDate;
	}

	public void setNextInstallmentDate(LocalDate nextInstallmentDate) {
		this.nextInstallmentDate = nextInstallmentDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", nullable = false)
	private User user;
//
//	@ManyToOne
//	@JoinColumn(name = "enrolmentId", nullable = false)
//	private UserBatchEnrollment batchEnrollment;

	@ManyToOne(fetch = FetchType.LAZY) // Fetch batch lazily to avoid unnecessary joins
	@JoinColumn(name = "batch_id", nullable = false)
	private Batch batch;

	public Batch getBatch() {
		return batch;
	}

	public void setBatch(Batch batch) {
		this.batch = batch;
	}

	public void setInstallmentNo(int installmentNo) {
		this.installmentNo = installmentNo;
	}

	public int getInstallmentNo() {
		return installmentNo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(int payAmount) {
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
