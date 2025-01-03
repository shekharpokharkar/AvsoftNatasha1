package com.example.avsoft.services;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.avsoft.dtos.ProfileUpdateRequest;
import com.example.avsoft.dtos.UserDto;
import com.example.avsoft.dtos.UserPaymentResponseDTO;
import com.example.avsoft.dtos.UserPaymentsRequestDTO;
import com.example.avsoft.entities.Batch;
import com.example.avsoft.entities.Profile;
import com.example.avsoft.entities.User;
import com.example.avsoft.entities.UserBatchEnrollment;
import com.example.avsoft.entities.UserPayment;
import com.example.avsoft.entities.UserPaymentID;
import com.example.avsoft.repositories.BatchRepository;
import com.example.avsoft.repositories.ProfileRepository;
import com.example.avsoft.repositories.UserBatchEnrollmentRepo;
import com.example.avsoft.repositories.UserPaymentRepository;
import com.example.avsoft.repositories.UserRepository;
import com.example.avsoft.responses.UserProfileResponse;

import jakarta.transaction.Transactional;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserPaymentRepository paymentRepository;

	@Autowired
	private BatchRepository batchRepository;

	@Autowired
	private UserBatchEnrollmentRepo userBatchrepo;

	public List<UserDto> allUsers() {
		List<User> users = (List<User>) userRepository.findAll();
		return users.stream().map((user -> {
			UserDto userDto = new UserDto();
			userDto.setId(user.getId());
			userDto.setFullName(user.getFullName());
			userDto.setEmail(user.getEmail());
			userDto.setImage(user.getImage());
			userDto.setRole(user.getRole());
			userDto.setUpdatedAt(user.getUpdatedAt());
			userDto.setCreatedAt(user.getCreatedAt());
			return userDto;
		})).toList();
	}

	// This method returns the current login user and profile
	public Optional<UserProfileResponse> getUserProfile(int id) {
		try {
			Optional<User> userOptional = userRepository.findById(id);
			if (userOptional.isPresent()) {
				User user = userOptional.get();
				Profile profile = profileRepository.findByUserId(id);

				UserProfileResponse userProfileResponse = new UserProfileResponse();
				userProfileResponse.setFullName(user.getFullName());
				userProfileResponse.setEmail(user.getEmail());
				userProfileResponse.setImage(user.getImage());
				userProfileResponse.setCreatedAt(user.getCreatedAt().toString());

				if (profile != null) {
					userProfileResponse.setAddress(profile.getAddress());
					userProfileResponse.setAadhaar(profile.getAadhaar());
					userProfileResponse.setStatus(profile.getStatus());
					userProfileResponse.setContact(profile.getContact());
				}

				return Optional.of(userProfileResponse);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return Optional.empty();
	}

	@Value("${file.upload-dir}")
	private String fileUploadDir;

	@Transactional
	public Profile updateProfile(ProfileUpdateRequest profileUpdateRequest, MultipartFile aadhaarFile)
			throws IOException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = (User) authentication.getPrincipal();
		Profile profile = new Profile();

		profile.setUserId(currentUser.getId());
		profile.setAddress(profileUpdateRequest.getAddress());
		profile.setContact(profileUpdateRequest.getContact());

		if (aadhaarFile != null && !aadhaarFile.isEmpty()) {
			String originalFileName = aadhaarFile.getOriginalFilename();
			String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
			String newFileName = currentUser.getId() + currentUser.getFullName().replaceAll("\\s+", "") + "_aadhaar"
					+ fileExtension;

			Path uploadPath = Paths.get(fileUploadDir);
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}

			Path targetLocation = uploadPath.resolve(newFileName);
			Files.copy(aadhaarFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			String fileUrl = newFileName;
			profile.setAadhaar(fileUrl);
		}

		return profileRepository.save(profile);
	}

	public boolean resetPassword(String email, String newPassword) {
		Optional<User> userOptional = userRepository.findByEmail(email);

		if (userOptional.isPresent()) {
			User user = userOptional.get();
			String encodedPassword = passwordEncoder.encode(newPassword);
			user.setPassword(encodedPassword);
			userRepository.save(user); // Save the user with the new password
			return true;
		} else {
			throw new RuntimeException("User not found with email: " + email);
		}
	}

	public List<String> getUserRolesById(Integer userId) {
		String roles = userRepository.findRoleByUserId(userId);
		if (roles == null)
			throw new RuntimeException("User roles are null with id");
		return Arrays.stream(roles.split(",")).toList();
	}

	public void updateUserRoles(Integer userId, List<String> rolesList) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User with Id not found"));
		StringJoiner newRoles = new StringJoiner(",");
		for (String role : rolesList) {
			newRoles.add(role.toLowerCase());
		}
		user.setRole(newRoles.toString());
		userRepository.save(user);
	}

	public UserPaymentResponseDTO processFee(UserPaymentsRequestDTO dto) {
		int batchId = dto.getBatchId();

		Optional<Batch> byBatchId = batchRepository.findById(batchId);
		Optional<UserBatchEnrollment> byBatchIdAndUserId = userBatchrepo.findByBatchIdAndUserId(batchId,
				dto.getUserId());

		if (byBatchId.isEmpty() || byBatchIdAndUserId.isEmpty()) {
			throw new RuntimeException("Given " + batchId
					+ " id is Incorrect OR please enter correct Batch Id OR Enroll In Batch:" + batchId);
		}

		UserPaymentID id = new UserPaymentID(dto.getUserId(), batchId);

		Optional<UserPayment> byId = paymentRepository.findById(id);

		UserPaymentResponseDTO payment = new UserPaymentResponseDTO();

		if (byId.isPresent()) {

			UserPayment userPayment = byId.get();
			BigDecimal requestedAmount = userPayment.getRequestedAmount();

			userPayment.setStatus("Approved");
			userPayment.setTotalPaidAmount(requestedAmount.intValue());
			userPayment.setRequestedAmount(new BigDecimal(0));
			UserPayment save = paymentRepository.save(userPayment);
			payment.setBatchId(userPayment.getBatchId());

			Batch batch2 = byBatchId.get();

			payment.setBatchTotalFees(batch2.getFee());
			payment.setRequestedAmount(userPayment.getRequestedAmount());
			payment.setStatus("Approved");
			payment.setTotalPaidAmount(requestedAmount.intValue());
			payment.setUserId(userPayment.getUserId());

		}

		return payment;
	}

	public List<UserPaymentResponseDTO> getAllprocessFee() {

		List<UserPayment> allPayment = paymentRepository.findAll();

		List<UserPaymentResponseDTO> pmt = new ArrayList<UserPaymentResponseDTO>();

		for (UserPayment dto : allPayment) {
			UserPaymentResponseDTO payment = new UserPaymentResponseDTO();

			Optional<Batch> byId = batchRepository.findById(dto.getBatchId());
			payment.setBatchTotalFees(byId.get().getFee());
			payment.setRequestedAmount(dto.getRequestedAmount());
			payment.setStatus("Approved");
			payment.setTotalPaidAmount(dto.getTotalPaidAmount());
			payment.setUserId(dto.getUserId());
			pmt.add(payment);
		}

		return pmt;
	}

	public List<UserPaymentResponseDTO> getAllprocessFeeByBatchID(int batchId, String status) {

		List<UserPayment> allPayment = paymentRepository.findAllUserPaymentByBatchIdAndStatus(batchId, status);
		allPayment.forEach(s -> System.out.println(s));
		List<UserPaymentResponseDTO> pmt = new ArrayList<UserPaymentResponseDTO>();

		for (UserPayment dto : allPayment) {

			UserPaymentResponseDTO payment = new UserPaymentResponseDTO();

			Optional<Batch> byId = batchRepository.findById(dto.getBatchId());

			payment.setBatchTotalFees(byId.get().getFee());

			payment.setRequestedAmount(dto.getRequestedAmount());
			payment.setStatus(dto.getStatus());
			payment.setTotalPaidAmount(dto.getTotalPaidAmount());
			payment.setUserId(dto.getUserId());
			pmt.add(payment);
		}

		return pmt;

	}

	public List<UserPaymentResponseDTO> getAllprocessFeeRequestBasedOnPaymentStatus(String paymentStatus) {

		List<UserPayment> allPayment = paymentRepository.findAllUserPaymentByStatus(paymentStatus);
		
		List<UserPaymentResponseDTO> pmt = new ArrayList<UserPaymentResponseDTO>();

		for (UserPayment dto : allPayment) {

			UserPaymentResponseDTO payment = new UserPaymentResponseDTO();

			Optional<Batch> byId = batchRepository.findById(dto.getBatchId());

			payment.setBatchTotalFees(byId.get().getFee());

			payment.setRequestedAmount(dto.getRequestedAmount());
			payment.setStatus(dto.getStatus());
			payment.setTotalPaidAmount(dto.getTotalPaidAmount());
			payment.setUserId(dto.getUserId());
			pmt.add(payment);
		}

		return pmt;
	}

	public List<UserPaymentResponseDTO> getAllprocessFeeRequestBasedOnBatchId(int batchId) {
		
		List<UserPayment> allPayment = paymentRepository.findAllByBatchId(batchId);
		
		List<UserPaymentResponseDTO> pmt = new ArrayList<UserPaymentResponseDTO>();

		for (UserPayment dto : allPayment) {

			UserPaymentResponseDTO payment = new UserPaymentResponseDTO();

			Optional<Batch> byId = batchRepository.findById(batchId);

			payment.setBatchTotalFees(byId.get().getFee());

			payment.setRequestedAmount(dto.getRequestedAmount());
			payment.setStatus(dto.getStatus());
			payment.setTotalPaidAmount(dto.getTotalPaidAmount());
			payment.setUserId(dto.getUserId());
			pmt.add(payment);
		}

		return pmt;
	}

}
