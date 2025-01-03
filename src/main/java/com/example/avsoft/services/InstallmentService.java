package com.example.avsoft.services;

import java.time.LocalDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.avsoft.dtos.InstallmentrRequestDto;
import com.example.avsoft.entities.Batch;
import com.example.avsoft.entities.Installment;
import com.example.avsoft.entities.User;
import com.example.avsoft.enums.Status;
import com.example.avsoft.exceptions.ResourceNotFoundException;
import com.example.avsoft.repositories.BatchRepository;
import com.example.avsoft.repositories.InstallmentRepository;
import com.example.avsoft.repositories.UserRepository;
import com.example.avsoft.responses.InstallmentResponseDto;

@Service
public class InstallmentService {

	@Autowired
	private InstallmentRepository installmentDetailsRepository;

	@Autowired
	private BatchRepository batchRepository;

	@Autowired
	private UserRepository userRepository;

	public Installment saveInstallment(InstallmentrRequestDto detailDto) {

		User user = userRepository.findByEmail(detailDto.getEmail())
				.orElseThrow(() -> new RuntimeException("User not found for the given email"));

		Batch batch = batchRepository.findById(detailDto.getBatchId())
				.orElseThrow(() -> new ResourceNotFoundException("Batch not found for the given ID"));

		// it will Check if same installment number for same user and batch
		Optional<Installment> existingInstallment = installmentDetailsRepository
				.findByUserIdAndBatchIdAndInstallmentNo(user.getId(), batch.getId(), detailDto.getInstallmentNo());
		if (existingInstallment.isPresent()) {
			throw new ResourceNotFoundException("Installment number already exists for the given user and batch.");
		}

		int totalFee = batch.getFee();

		List<Installment> previousPayments = installmentDetailsRepository.findByUserIdAndBatchId(user.getId(),
				batch.getId());
		int totalPaidAmount = previousPayments.stream().mapToInt(Installment::getPayAmount).sum()
				+ detailDto.getPayAmount();

		// Calculate the remaining fee after the new payment
		int remainingFee = totalFee - totalPaidAmount;
		if (remainingFee < 0) {
			throw new ResourceNotFoundException("The user pay the full fees.");
		}
		Installment Details = new Installment();

		Details.setRemainingAmount(remainingFee);
		Details.setInstallmentNo(detailDto.getInstallmentNo());
		Details.setPayAmount(detailDto.getPayAmount());// enrollment.getAmount()
		Details.setPaymentDate(LocalDateTime.now());
		Details.setPaymentMode(detailDto.getPaymentMode());
		Details.setTransactionId(detailDto.getTransactionId());
		Details.setRemark(detailDto.getRemark());
		Details.setStatus(detailDto.getStatus());
		Details.setUser(user);
		Details.setBatch(batch);

		if (remainingFee > 0) {
			LocalDate today = LocalDate.now();

			// Check if the next installment date
			if (detailDto.getNextInstallmentDate().isBefore(today.plusDays(1))) {
				throw new ResourceNotFoundException("Next installment date must be after today.");
			}

			// Check next installment amount
			if (detailDto.getNextInstallmentAmount() > remainingFee) {
				throw new ResourceNotFoundException(
						"Next installment amount cannot exceed the remaining fee: " + remainingFee);
			}

			Details.setNextInstallmentAmount(detailDto.getNextInstallmentAmount());
			Details.setNextInstallmentDate(detailDto.getNextInstallmentDate());
		} else {
			Details.setNextInstallmentAmount(0);
			Details.setNextInstallmentDate(null);
		}

		return installmentDetailsRepository.save(Details);
	}

	public List<InstallmentResponseDto> getDetailsByEmail(String email, long batchId) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

		List<Installment> installmentDetailsList = installmentDetailsRepository.findByUser(user);
		List<InstallmentResponseDto> responseDtos = installmentDetailsList.stream()
				.filter(i -> i.getBatch().getId() == batchId).map(details -> {
					InstallmentResponseDto dto = new InstallmentResponseDto();
					dto.setId(details.getId());
					dto.setInstallmentNo(details.getInstallmentNo());
					dto.setPayAmount(details.getPayAmount());
					dto.setPaymentDate(details.getPaymentDate());
					dto.setPaymentMode(details.getPaymentMode());
					dto.setTransactionId(details.getTransactionId());
					dto.setRemainingAmount(details.getRemainingAmount());
					dto.setNextInstallmentAmount(details.getNextInstallmentAmount());
					dto.setStatus(details.getStatus());
					dto.setUserId(user.getId());
					dto.setUserName(user.getFullName());
					dto.setBatchId(details.getBatch().getId());
					dto.setRemark(details.getRemark());
					if (details.getRemainingAmount() > 0) {
						dto.setNextInstallmentDate(details.getNextInstallmentDate());
					} else {
						dto.setNextInstallmentDate(null);
					}
					return dto;
				}).collect(Collectors.toList());

		return responseDtos;
	}

	public Installment updateNextInstallmentDate(int installmentId, LocalDate nextInstalmentDate) {
		Installment installment = installmentDetailsRepository.findById(installmentId)
				.orElseThrow(() -> new ResourceNotFoundException("Installment not found with the given ID"));

		installment.setNextInstallmentDate(nextInstalmentDate);

		return installmentDetailsRepository.save(installment);
	}

	public boolean updateStatus(int instalmentId, Status status) {
		Optional<Installment> optionalInstallment = installmentDetailsRepository.findById(instalmentId);

		if (optionalInstallment.isPresent()) {
			Installment installment = optionalInstallment.get();
			installment.setStatus(status); // Set the new status
			installmentDetailsRepository.save(installment); // Save the updated installment
			return true; // Successfully updated
		}
		return false; // Installment not found
	}
}
