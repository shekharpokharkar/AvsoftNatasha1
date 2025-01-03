package com.example.avsoft.entities;

import java.io.Serializable;
import java.util.Objects;

public class UserPaymentID implements Serializable {

	private static final long serialVersionUID = 1L;

	private int userId;

	private int batchId;

	public UserPaymentID(int userId, int batchId) {

		this.userId = userId;
		this.batchId = batchId;
	}

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

	@Override
	public int hashCode() {
		return Objects.hash(batchId, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserPaymentID other = (UserPaymentID) obj;
		return batchId == other.batchId && userId == other.userId;
	}

	@Override
	public String toString() {
		return "UserPaymentID [userId=" + userId + ", batchId=" + batchId + "]";
	}

	public UserPaymentID() {

	}

}
