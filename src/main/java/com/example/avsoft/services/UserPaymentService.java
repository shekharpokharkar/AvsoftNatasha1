package com.example.avsoft.services;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.avsoft.dtos.InstallmentDTO;
import com.example.avsoft.dtos.UserPaymentDetailsDTO;
import com.example.avsoft.dtos.UserPaymentsRequestDTO;
import com.example.avsoft.entities.InstallmentStructure;
import com.example.avsoft.entities.UserBatchEnrollment;
import com.example.avsoft.entities.UserPayment;
import com.example.avsoft.entities.UserPaymentID;
import com.example.avsoft.exceptions.UserEnrollmentException;
import com.example.avsoft.exceptions.UserPaymentException;
import com.example.avsoft.repositories.InstallmentStructureRepository;
import com.example.avsoft.repositories.UserBatchEnrollmentRepo;
import com.example.avsoft.repositories.UserPaymentRepository;

@Service
public class UserPaymentService {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private UserPaymentRepository repository;

	@Autowired
	private UserBatchEnrollmentRepo userRepo;

	@Autowired
	private InstallmentStructureRepository installmentRepo;

	public UserPayment addUserPayment(UserPaymentsRequestDTO payment) throws UserEnrollmentException {

		UserPayment user = new UserPayment();

		int batch = payment.getBatchId();
		int userId = payment.getUserId();

		Optional<UserBatchEnrollment> byBatchIdAndUserId = userRepo.findByBatchIdAndUserId(batch, userId);

		if (byBatchIdAndUserId.isEmpty()) {
			throw new UserPaymentException("Student:" + userId + " is not enrolled in in the batch:" + batch
					+ " first enroll and the do payment");
		}
		int batchfee = byBatchIdAndUserId.get().getBatch().getFee();

		Optional<UserPayment> byUserPayment = repository.findById(new UserPaymentID(userId, batch));

		if (byUserPayment.isPresent()) {
			if (byUserPayment.get().getTotalPaidAmount() == batchfee) {
				throw new UserPaymentException("Thank you are already paid the Batch fees!!!");
			}

			if ((batchfee - byUserPayment.get().getTotalPaidAmount()) < payment.getAmount()) {
				throw new UserPaymentException(
						"Please Enter the amount less than " + ((batchfee - byUserPayment.get().getTotalPaidAmount())));
			}

			if (byUserPayment.get().getStatus() != null) {

				if (byUserPayment.get().getStatus().equalsIgnoreCase("Pending")) {
					throw new UserPaymentException("please Contact to Admin your previous payment is not accepted");
				}
			}
		}

		if (batchfee < payment.getAmount()) {

			throw new UserPaymentException("Invalid Amount!! Please Enter the Amount Less than the BatchFee");
		}

		user.setRequestedAmount(new BigDecimal(payment.getAmount()));
		user.setBatchId(batch);
		user.setStatus("Pending");
		user.setUserId(userId);
		if (byUserPayment.isEmpty()) {
			user.setTotalPaidAmount(0);

		} else {
			user.setTotalPaidAmount(byUserPayment.get().getTotalPaidAmount());
		}
		UserPayment userPayment = repository.save(user);
		return userPayment;
	}

	public UserPaymentDetailsDTO getPaymentDetails(int userId, int BatchId) {

		UserPaymentID ID = new UserPaymentID(userId, BatchId);
		Optional<UserPayment> byId = repository.findById(ID);

		if (byId.isEmpty()) {
			throw new RuntimeException("Sorry Your Record Not found plz check once OR contact to admin");
		}
		UserPayment userPayment = byId.get();

		List<InstallmentStructure> byBatch = installmentRepo.findByBatch(BatchId);

		UserPaymentDetailsDTO dto = new UserPaymentDetailsDTO();

		UserPaymentDetailsDTO detailsDTO = mapper.map(userPayment, UserPaymentDetailsDTO.class);

		InstallmentDTO[] map = mapper.map(byBatch, InstallmentDTO[].class);

		detailsDTO.setDto(Arrays.asList(map));

		return detailsDTO;
	}

	public List<UserPayment> getAllUserPayment() {
		List<UserPayment> all = repository.findAll();
		return all;
	}

}
