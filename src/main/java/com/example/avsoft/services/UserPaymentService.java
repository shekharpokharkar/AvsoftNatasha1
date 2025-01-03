package com.example.avsoft.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.avsoft.dtos.UserPaymentsRequestDTO;
import com.example.avsoft.entities.UserBatchEnrollment;
import com.example.avsoft.entities.UserPayment;
import com.example.avsoft.entities.UserPaymentID;
import com.example.avsoft.exceptions.UserPaymentException;
import com.example.avsoft.repositories.UserBatchEnrollmentRepo;
import com.example.avsoft.repositories.UserPaymentRepository;

@Service
public class UserPaymentService {
	
	@Autowired
	private UserPaymentRepository repository;

	@Autowired
	private UserBatchEnrollmentRepo userRepo;

	public UserPayment addUserPayment(UserPaymentsRequestDTO payment) {

		UserPayment user = new UserPayment();

		int batch = payment.getBatchId();
		int userId = payment.getUserId();

		Optional<UserBatchEnrollment> byBatchIdAndUserId = userRepo.findByBatchIdAndUserId(batch, userId);

		int batchfee = byBatchIdAndUserId.get().getBatch().getFee();

		if (byBatchIdAndUserId.isEmpty()) {
			throw new UserPaymentException("Student:" + userId + " is not enrolled in in the batch:" + batch
					+ " first enroll and the do payment");
		}
		Optional<UserPayment> byUserPayment = repository.findById(new UserPaymentID(userId, batch));

		if (byUserPayment.isPresent()) {
			if (byUserPayment.get().getTotalPaidAmount() == batchfee) {
				throw new UserPaymentException("Thank you are already paid the Batch fees!!!");
			}
			
			if ((batchfee - byUserPayment.get().getTotalPaidAmount()) < payment.getAmount()) {
				throw new UserPaymentException(
						"Please Enter the amount less than " + ((batchfee - byUserPayment.get().getTotalPaidAmount())));
			}

			if (byUserPayment.get().getStatus().equalsIgnoreCase("Pending")) {
				throw new UserPaymentException("please Contact to Admin your previous payment is not accepted");
			}

		}

		if (batchfee < payment.getAmount()) {

			throw new UserPaymentException("Invalid Amount!! Please Enter the Amount Less than the BatchFee");
		}

		user.setRequestedAmount(new BigDecimal(payment.getAmount()));
		user.setBatchId(batch);
		user.setStatus("Pending");
		user.setUserId(userId);
		user.setTotalPaidAmount(0);

		UserPayment userPayment = repository.save(user);
		return userPayment;
	}

	public UserPayment getPaymentDetails(int userId, int BatchId) {

		UserPaymentID ID = new UserPaymentID(userId, BatchId);
		Optional<UserPayment> byId = repository.findById(ID);
		return byId.get();
	}

	public List<UserPayment> getAllUserPayment() {
		List<UserPayment> all = repository.findAll();
		return all;
	}

}
