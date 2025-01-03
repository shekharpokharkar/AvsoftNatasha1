package com.example.avsoft.exceptions;

import java.util.Date;

public class ExceptionResponseDTO {

	private String errorClass;
	private String errorMessage;
	private Date date;

	public String getErrorClass() {
		return errorClass;
	}

	public void setErrorClass(String errorClass) {
		this.errorClass = errorClass;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "VideoExceptionResponseDTO [errorClass=" + errorClass + ", errorMessage=" + errorMessage + ", date="
				+ date + "]";
	}

}
