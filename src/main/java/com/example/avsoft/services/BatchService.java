package com.example.avsoft.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.avsoft.dtos.BatchInstallmentDTO;
import com.example.avsoft.dtos.BatchInstallmentDetailsDTO;
import com.example.avsoft.dtos.BatchInstallmentRequestDTO;
import com.example.avsoft.entities.Batch;
import com.example.avsoft.entities.InstallmentStructure;
import com.example.avsoft.entities.UserBatchEnrollment;
import com.example.avsoft.repositories.BatchRepository;
import com.example.avsoft.repositories.CourseRepository;
import com.example.avsoft.repositories.InstallmentStructureRepository;
import com.example.avsoft.repositories.UserBatchEnrollmentRepo;

@Service
public class BatchService {

	private static final Logger logger = LoggerFactory.getLogger(BatchService.class);

	@Autowired
	private BatchRepository batchRepository;

	@Autowired
	private UserBatchEnrollmentRepo userBatchEnrollmentRepo;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private InstallmentStructureRepository installmentRepository;

	@Transactional
	public Batch createBatch(BatchInstallmentRequestDTO input) {
		logger.info("Creating batch with title: {}", input.getTitle());

		Batch newBatch = new Batch();
		newBatch.setTitle(input.getTitle());
		newBatch.setDescription(input.getDescription());
		newBatch.setDuration(input.getDuration());

		// newBatch.setCourseIds(input.getCourses().get);

		newBatch.setFee(input.getFee());
		newBatch.setSpecialfee(input.getSpecialfee());

		BatchInstallmentDTO installmentDTO = input.getInstallmentDTO();
		try {
			newBatch = batchRepository.save(newBatch);
			if(installmentDTO.getBatchInstallmentdto() == null )
			{
				createInstallmentStructureByDefault(input.getInstallmentDTO().getInsatllmentNo(),input.getInstallmentDTO().getNoOfDaysInbetweenInstallment(), newBatch.getId(), input.getFee());
			}else
			{
				createInstallmentStructure(installmentDTO, newBatch.getId(), input.getFee());
			}

			

			logger.info("Batch saved successfully with ID: {}", newBatch.getId());
		} catch (Exception e) {
			logger.error("Error saving batch", e);
		}

		return newBatch;
	}

	private void createInstallmentStructure(BatchInstallmentDTO installmentDTO, Long batch, int fees) {

		int insatllmentNo = installmentDTO.getInsatllmentNo();
		List<InstallmentStructure> strlist = new ArrayList<InstallmentStructure>();

		int installment = 1;
		List<BatchInstallmentDetailsDTO> batchInstallmentdto = installmentDTO.getBatchInstallmentdto();

		batchInstallmentdto.get(installment).getInstallmentDate();
		

		while (installment <= insatllmentNo) {
			InstallmentStructure structure = new InstallmentStructure();

			structure.setDate(LocalDate.parse(batchInstallmentdto.get(installment-1).getInstallmentDate()));

			if (installment == 1) {
				structure.setAmount(new BigDecimal(batchInstallmentdto.get(installment-1).getInstallmentFee()));
			} else {
				structure.setAmount(new BigDecimal(batchInstallmentdto.get(installment-1).getInstallmentFee()));
			}

			structure.setBatch(batch);

			structure.setInstallmentNo(installment);

			strlist.add(structure);
			installment++;
		}
		installmentRepository.saveAll(strlist);
	}

	private void createInstallmentStructureByDefault(int insatllmentNo,String noOfDaysInbetweenInstallment, Long batch, int fees) {

		
		int eachInstallment = 0;
		int lastInstallmet = fees % 5000;

		if (fees % 10000 != 0) {
			lastInstallmet = fees % 10000;
			eachInstallment = (fees - lastInstallmet) / (insatllmentNo - 1);
		} else {

			eachInstallment = (fees - lastInstallmet) / (insatllmentNo);
			lastInstallmet = eachInstallment;
		}

		LocalDate startingDate = LocalDate.now();

		int datecounter = 2;

		List<InstallmentStructure> strlist = new ArrayList<InstallmentStructure>();

		int installment = 1;

		while (installment <= insatllmentNo) {
			InstallmentStructure structure = new InstallmentStructure();

			structure.setDate(startingDate);

			if (installment == insatllmentNo) {
				structure.setAmount(new BigDecimal(lastInstallmet));
			} else {
				structure.setAmount(new BigDecimal(eachInstallment));
			}

			LocalDate plusDays = startingDate.plusDays(Long.parseLong(noOfDaysInbetweenInstallment));
			startingDate = plusDays;

			structure.setBatch(batch);

			structure.setInstallmentNo(installment);

			strlist.add(structure);
			installment++;
		}
		installmentRepository.saveAll(strlist);
	}

	public List<Batch> getAllBatches() {
		List<Batch> allBatches = batchRepository.findAll();
		return allBatches;
	}

	public List<Batch> getAllBatchesByUser(Integer userId) {
		List<UserBatchEnrollment> enrollments = userBatchEnrollmentRepo.findByUserId(userId);

		List<Batch> userBatches = new ArrayList<>();

		for (UserBatchEnrollment enrollment : enrollments) {

			Optional<Batch> batch = batchRepository.findById(enrollment.getBatch().getId());

			if (batch.isPresent()) {
				userBatches.add(batch.get());
			} else {
				logger.warn("Batch with ID {} not found for user {}", enrollment.getId(), userId);
			}
		}

		return userBatches;
	}

	public String applySpecial(Long batchId, int userId) {
		Optional<UserBatchEnrollment> enrollmentOpt = userBatchEnrollmentRepo.findByBatchIdAndUserId(batchId, userId);

		if (enrollmentOpt.isPresent()) {
			UserBatchEnrollment enrollment = enrollmentOpt.get();
			enrollment.setStatusSpecial("pending"); // Update the statusSpecial field
			userBatchEnrollmentRepo.save(enrollment); // Save the updated entity back to the database
			return enrollment.getStatusSpecial(); // Return the updated statusSpecial
		} else {
			throw new RuntimeException("Enrollment not found for batchId: " + batchId + " and userId: " + userId);
		}
	}

}
