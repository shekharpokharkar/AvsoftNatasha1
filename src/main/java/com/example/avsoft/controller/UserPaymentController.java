package com.example.avsoft.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.avsoft.dtos.UserPaymentsRequestDTO;
import com.example.avsoft.entities.UserPayment;
import com.example.avsoft.services.UserPaymentService;

@RestController
public class UserPaymentController {

	@Autowired
	public UserPaymentService service;

	@PostMapping("/userPayments")
	public ResponseEntity<UserPayment> dopayment(@RequestBody UserPaymentsRequestDTO dto) {
		UserPayment userPayment = service.addUserPayment(dto);
		return new ResponseEntity<UserPayment>(userPayment, HttpStatus.OK);
	}

	@GetMapping("/userPayments")
	public ResponseEntity<List<UserPayment>> getAllUserFeeDetails() {

		List<UserPayment> userPayment = service.getAllUserPayment();
		return new ResponseEntity<List<UserPayment>>(userPayment, HttpStatus.OK);
	}

	@GetMapping("/userPaymentByID")
	public ResponseEntity<UserPayment> getFeeDetails(@RequestParam String batchId, @RequestParam String userId) {

		UserPayment userPayment = service.getPaymentDetails(Integer.parseInt(userId), Integer.parseInt(batchId));
		return new ResponseEntity<UserPayment>(userPayment, HttpStatus.OK);
	}
}
