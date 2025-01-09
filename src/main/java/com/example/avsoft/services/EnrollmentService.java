package com.example.avsoft.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.avsoft.dtos.ChangeStatusDto;
import com.example.avsoft.dtos.EnrollmentResponseStudent;
import com.example.avsoft.entities.Batch;
import com.example.avsoft.entities.User;
import com.example.avsoft.entities.UserBatchEnrollment;
import com.example.avsoft.repositories.BatchRepository;
import com.example.avsoft.repositories.UserBatchEnrollmentRepo;
import com.example.avsoft.repositories.UserRepository;

@Service
public class EnrollmentService {
	

	
	@Autowired
	private UserRepository userRepository;
	
	
	@Autowired
	private BatchRepository batchRepository;
	
	@Autowired
	private UserBatchEnrollmentRepo userbatchrepo;
	
	
//	public void enrollUserInBatch(Integer userId, Integer batchId) {
//		
//		UserBatchEnrollment newEnroll = new UserBatchEnrollment();
//		System.out.println(userId);
//		newEnroll.setUserId(userId);
//		newEnroll.setBatchId(batchId);
//		
//		newEnroll.setStatus("Pending");
//		
//		userbatchrepo.save(newEnroll);
//	   
//	 }
	
	public void enrollUserInBatch(Integer userId, Integer batchId) {
	    // Fetch the batch entity from the database
	    Optional<Batch> batchOptional = batchRepository.findById(batchId);

	    // Check if batch exists, throw an exception if not
	    if (!batchOptional.isPresent()) {
	        throw new IllegalArgumentException("Batch with ID " + batchId + " does not exist.");
	    }

	    // Create a new enrollment
	    UserBatchEnrollment newEnroll = new UserBatchEnrollment();
	    
	    // Set the user ID (assuming the user ID is coming as a parameter)
	    newEnroll.setUserId(userId);

	    // Set the Batch entity, which was fetched
	    newEnroll.setBatch(batchOptional.get());
	    
	    // Set default status
	    newEnroll.setStatus("Pending");

	    // Save the new enrollment
	    userbatchrepo.save(newEnroll);
	}
	
	
	
	
	public List<EnrollmentResponseStudent> getEnrollments(int batchId) 
	{
		
		//get Details of UserBatchEnrollment
	    List<UserBatchEnrollment> enrolledUsers = userbatchrepo.findByBatchId(batchId);
	    
	    List<EnrollmentResponseStudent> students = new ArrayList<>();

	    for (UserBatchEnrollment enrolledUser : enrolledUsers) {
	        
	    	Optional<User> userOptional = userRepository.findById(enrolledUser.getUserId());

	        if (userOptional.isPresent()) {
	        	
	            User user = userOptional.get();
	            
	            //Create a new EnrollmentResponseStudent DTO
	            EnrollmentResponseStudent enrollmentStudent = new EnrollmentResponseStudent();
	            
	            // Set values from User and UserBatchEnrollment
	            enrollmentStudent.setEnrollmentId(enrolledUser.getId());
	            enrollmentStudent.setName(user.getFullName());
	            enrollmentStudent.setEmail(user.getEmail());
	            enrollmentStudent.setImage(user.getImage()); // Assuming user has profile image
	            enrollmentStudent.setEnrollmentDate(enrolledUser.getCreatedAt()); // From UserBatchEnrollment
	            enrollmentStudent.setStatus(enrolledUser.getStatus()); // From UserBatchEnrollment
	            enrollmentStudent.setAmount(enrolledUser.getAmount()); // From UserBatchEnrollment
	            enrollmentStudent.setStatusSpecial(enrolledUser.getStatusSpecial());
	            enrollmentStudent.setUserId(user.getId());
	            students.add(enrollmentStudent);
	        }
	    }

	   
	    return students;
	}
	
	
	public void changeEnrollmentStatus(ChangeStatusDto input, int id) {
	    Optional<UserBatchEnrollment> enrolledUserOptional = userbatchrepo.findById(id);

	    enrolledUserOptional.ifPresent(user -> {
	        user.setStatus(input.getStatus());
	        user.setStatusSpecial(input.getStatusSpecial());
	        user.setAmount(input.getAmount());// Assuming the DTO has this field
	        userbatchrepo.save(user);
	    });
	}
	
	
	public List<UserBatchEnrollment> getEnrollmentsByUser(int userId) {
        return userbatchrepo.findByUserId(userId);
    }
	
	
	
	



	
	

}
