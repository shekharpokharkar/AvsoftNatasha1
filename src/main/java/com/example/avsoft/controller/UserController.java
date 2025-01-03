package com.example.avsoft.controller;

import com.example.avsoft.dtos.ProfileUpdateRequest;
import com.example.avsoft.dtos.UserDto;
import com.example.avsoft.entities.Batch;
import com.example.avsoft.entities.Profile;
import com.example.avsoft.entities.User;
import com.example.avsoft.entities.UserBatchEnrollment;
import com.example.avsoft.repositories.UserBatchEnrollmentRepo;
import com.example.avsoft.responses.UserProfileResponse;
import com.example.avsoft.services.BatchService;
import com.example.avsoft.services.EnrollmentService;
import com.example.avsoft.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RequestMapping("/users")
@RestController
public class UserController {
	
	@Autowired
    private  UserService userService;
    
	@Autowired
	private EnrollmentService enrollmentService;
	
	@Autowired
	private UserBatchEnrollmentRepo userbatchrepo;
	
	
	@Autowired
	private BatchService batchService;
    
    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }
    
    @GetMapping("/get-profile")
    public ResponseEntity<Optional<UserProfileResponse>> getProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        
        Optional<UserProfileResponse> user = userService.getUserProfile(currentUser.getId());
        return ResponseEntity.ok(user);
    }
    
    
    @PostMapping("update-profile")
    public ResponseEntity<Profile> saveProfile(
            @ModelAttribute ProfileUpdateRequest profileUpdateRequest,
            @RequestParam("aadhaarFile") MultipartFile aadhaarFile) {

        try {
            Profile userProfile = userService.updateProfile(profileUpdateRequest, aadhaarFile);
            return ResponseEntity.ok(userProfile);
        } catch (Exception e) {
        	
            return (ResponseEntity<Profile>) ResponseEntity.ok();
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> allUsers() {
        List <UserDto> users = userService.allUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/enroll/{id}")
    public ResponseEntity<String> enroll(@PathVariable Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        boolean alreadyEnrolled = userbatchrepo.existsByUserIdAndBatchId(currentUser.getId(), id);
        
        if (alreadyEnrolled) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                                 .body("User is already enrolled in this batch.");
        }
        
        enrollmentService.enrollUserInBatch(currentUser.getId(), id);
        
        return ResponseEntity.ok("User successfully enrolled in batch.");
    }
    
    @GetMapping("/batches")
    public ResponseEntity<List<Batch>> getAllBatches() {
    	  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
          User currentUser = (User) authentication.getPrincipal();
        List<Batch> batches = batchService.getAllBatchesByUser(currentUser.getId());
        return ResponseEntity.ok(batches);
    }
    
    
//    @GetMapping("/user-enrollments")
//    public ResponseEntity<List<UserBatchEnrollment>> getUserEnrollments() {
//    	  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//          User currentUser = (User) authentication.getPrincipal();
//          
//        List<UserBatchEnrollment> userEnrollments = EnrollmentService.getEnrollmentsByUser(currentUser.getId());
//        return ResponseEntity.ok(userEnrollments);
//    }
    
    @GetMapping("/user-enrollments")
    public ResponseEntity<List<UserBatchEnrollment>> getUserEnrollments() {
    	 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         User currentUser = (User) authentication.getPrincipal();
         
        List<UserBatchEnrollment> enrollments = enrollmentService.getEnrollmentsByUser(currentUser.getId());
        return ResponseEntity.ok(enrollments);
    }
    
    
    
    
    @GetMapping("/apply-special/{batchId}")
    public ResponseEntity<String> applySpecial(@PathVariable int batchId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        int userId = currentUser.getId();  // Or use whatever method gets the ID

        String status = batchService.applySpecial(batchId, userId);

        return ResponseEntity.ok(status);
    }

    
}
