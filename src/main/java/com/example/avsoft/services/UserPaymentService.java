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
import com.example.avsoft.entities.Batch;
import com.example.avsoft.entities.InstallmentStructure;
import com.example.avsoft.entities.UserBatchEnrollment;
import com.example.avsoft.entities.UserPayment;
import com.example.avsoft.entities.UserPaymentID;
import com.example.avsoft.exceptions.UserEnrollmentException;
import com.example.avsoft.exceptions.UserPaymentException;
import com.example.avsoft.repositories.BatchRepository;
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
	@Autowired
	private BatchRepository batchRepo;

	public UserPayment addUserPayment(UserPaymentsRequestDTO payment) throws UserEnrollmentException {

		UserPayment user = new UserPayment();

		Long batchId = payment.getBatchId();
		int userId = payment.getUserId();

		Optional<UserBatchEnrollment> byBatchIdAndUserId = userRepo.findByBatchIdAndUserId(batchId, userId);

		if (byBatchIdAndUserId.isEmpty()) {
			throw new UserPaymentException("Student:" + userId + " is not enrolled in in the batch:" + batchId
					+ " first enroll and the do payment");
		}
		int batchfee = byBatchIdAndUserId.get().getBatch().getFee();

		Optional<UserPayment> byUserPayment = repository.findById(new UserPaymentID(userId, batchId));

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
		user.setBatchId(batchId);
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

	public UserPaymentDetailsDTO getPaymentDetails(int userId, Long BatchId) {

		// Finding the userId+BatchId userPament
		UserPaymentID ID = new UserPaymentID(userId, BatchId);
		
		//find UserPayment Details b using Id
		Optional<UserPayment> byId = repository.findById(ID);

		//find Batch Details by Batch Id
		Optional<Batch> byId2 = batchRepo.findById(BatchId);

		
		//chekck Batch is created and userIs Enroll in batch
		if (byId.isEmpty() && byId2.isEmpty()) {
			throw new RuntimeException("Sorry Your Record Not found plz check once OR contact to admin");
		}
		
		
		
		UserPayment userPayment = byId.get();

		//find All Installment deatils of given batch
		List<InstallmentStructure> byBatch = installmentRepo.findByBatch(BatchId);

		//UserPayment Details
		UserPaymentDetailsDTO detailsDTO = mapper.map(userPayment, UserPaymentDetailsDTO.class);

		InstallmentDTO[] map = mapper.map(byBatch, InstallmentDTO[].class);

		List<InstallmentDTO> asList = Arrays.asList(map);

		detailsDTO.setDto(asList);

		detailsDTO.setBatchTotalFees(byId2.get().getFee());

		return detailsDTO;
	}

	public List<UserPayment> getAllUserPayment() {
		
		return null;
	}

}
