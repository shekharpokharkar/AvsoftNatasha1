package com.example.avsoft.dtos;

import java.time.LocalDateTime;

public class OtpData {
	private String otp;
	private LocalDateTime genratedTime;
	private String newPassword;

	private static final int EXPIRATION_MINUTES = 5;

	public OtpData(String otp, LocalDateTime genratedTime) {
		this.otp = otp;
		this.genratedTime = genratedTime;
	}

	public String getOtp() {
		return otp;
	}

	public LocalDateTime getgenratedTime() {
		return genratedTime;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public boolean isExpired() {
		return LocalDateTime.now().isAfter(genratedTime.plusMinutes(EXPIRATION_MINUTES));
	}
}
