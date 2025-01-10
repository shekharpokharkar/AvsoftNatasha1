package com.example.avsoft.controller;

import java.time.LocalDate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.avsoft.dtos.InstallmentrRequestDto;
import com.example.avsoft.enums.Status;
import com.example.avsoft.responses.InstallmentResponseDto;
import com.example.avsoft.services.InstallmentService;

@RestController
public class InstallmentController {

	@Autowired
	private InstallmentService InstallmentService;

	// Create a new installment
	@PostMapping("/installmentDetails/create")
	public ResponseEntity saveInstallmentDetails(@RequestBody InstallmentrRequestDto requestDto) {
		InstallmentService.saveInstallment(requestDto);
		return new ResponseEntity("installment details created succesfully", HttpStatus.CREATED);
	}

	@GetMapping("/installmentDetails/{batchId}")
	public ResponseEntity<List<InstallmentResponseDto>> getPaymentDetailsByEmail(@RequestParam String email,
			@PathVariable long batchId) {
		List<InstallmentResponseDto> responseDto = InstallmentService.getDetailsByEmail(email, batchId);
		return ResponseEntity.ok(responseDto);
	}

	@PutMapping("/admin/NextinstallmeneDate/{instalmentId}")
	public ResponseEntity updateCommittedDate(@RequestParam LocalDate nextInstallmentDate,
			@PathVariable int instalmentId) {
		InstallmentService.updateNextInstallmentDate(instalmentId, nextInstallmentDate);
		return new ResponseEntity<>("Next Installlment date is updated", HttpStatus.CREATED);

	}

	@PutMapping("/updateStatus/{instalmentId}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<String> updateStatus(@RequestParam Status status, @PathVariable int instalmentId) {
		boolean isUpdated = InstallmentService.updateStatus(instalmentId, status);

		if (isUpdated) {
			return new ResponseEntity<>("Status updated successfully", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Installment not found", HttpStatus.NOT_FOUND);
		}
	}

}
