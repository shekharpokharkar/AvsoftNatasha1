package com.example.avsoft.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.avsoft.dtos.UserPaymentDetailsDTO;
import com.example.avsoft.dtos.UserPaymentsRequestDTO;
import com.example.avsoft.entities.UserPayment;
import com.example.avsoft.exceptions.UserEnrollmentException;
import com.example.avsoft.services.UserPaymentService;

@RestController
public class UserPaymentController {

	@Autowired
	public UserPaymentService service;

	@PostMapping("/userPayments")
	public ResponseEntity<UserPayment> dopayment(@RequestBody UserPaymentsRequestDTO dto) throws UserEnrollmentException {
		UserPayment userPayment = service.addUserPayment(dto);
		return new ResponseEntity<UserPayment>(userPayment, HttpStatus.OK);
	}

	@GetMapping("/userPayments")
	public ResponseEntity<List<UserPayment>> getAllUserFeeDetails() {

		List<UserPayment> userPayment = service.getAllUserPayment();
		return new ResponseEntity<List<UserPayment>>(userPayment, HttpStatus.OK);
	}

	@GetMapping("/userPaymentByID")
	public ResponseEntity<UserPaymentDetailsDTO> getFeeDetails(@RequestParam int batchId, @RequestParam int userId) {

		UserPaymentDetailsDTO userPayment = service.getPaymentDetails(userId,batchId);
		return new ResponseEntity<UserPaymentDetailsDTO>(userPayment, HttpStatus.OK);
	}
}
