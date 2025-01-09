package com.example.avsoft.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.avsoft.dtos.BatchInstallmentRequestDTO;
import com.example.avsoft.dtos.ChangeStatusDto;
import com.example.avsoft.dtos.EnrollmentResponseStudent;
import com.example.avsoft.dtos.UserPaymentResponseDTO;
import com.example.avsoft.dtos.UserPaymentsRequestDTO;
import com.example.avsoft.entities.Batch;
import com.example.avsoft.entities.Blog;
import com.example.avsoft.entities.Courses;
import com.example.avsoft.entities.Enquiry;
import com.example.avsoft.services.AdminService;
import com.example.avsoft.services.BatchService;
import com.example.avsoft.services.BlogServices;
import com.example.avsoft.services.CoursesService;
import com.example.avsoft.services.EnrollmentService;
import com.example.avsoft.services.UserService;

@RequestMapping("/admin")
@RestController
public class AdminController {

	@Autowired
	private AdminService AdminService;

	@Autowired
	private BlogServices BlogServices;

	@Autowired
	private CoursesService coursesService;

	@Autowired
	private BatchService batchService;

	@Autowired
	private EnrollmentService enrollmentService;

	@Autowired
	private UserService userService;

	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping("/user-enquiries")
	public ResponseEntity<List<Enquiry>> getEnquiries() {
		List<Enquiry> Enquiries = AdminService.getEnquiries();
		return ResponseEntity.ok(Enquiries);

	}

	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping("/user-blogs")
	public ResponseEntity<List<Blog>> getBlogs() {
		List<Blog> blogs = BlogServices.getUsersBlogs();
		return ResponseEntity.ok(blogs);

	}

	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping("/user-blogs-status/{flag}/{id}")
	public ResponseEntity blogStatus(@PathVariable Boolean flag, @PathVariable int id) {

		Boolean flagStatus = BlogServices.blogStatus(flag, id);

		return ResponseEntity.ok(flagStatus);

	}

	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping("/user-blog/{id}")
	public ResponseEntity<Optional<Blog>> getBlog(@PathVariable int id) {
		Optional<Blog> blog = BlogServices.getBlogbyId(id);
		if (blog != null) {
			return ResponseEntity.ok(blog);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping("/save-course")
	public ResponseEntity<Courses> saveCourse(@RequestBody Courses courses) {

		try {

			Courses newcourse = coursesService.saveCourse(courses);
			return ResponseEntity.ok(newcourse);
		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping("/get-courses")
	public ResponseEntity<List<Courses>> getCourses() {
		try {
			System.out.println("Hi");
			List<Courses> courses = coursesService.getCourses();
			return ResponseEntity.ok(courses);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();

		}
	}

	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping("/create-batch")
	public ResponseEntity<Batch> createBatch(@RequestBody BatchInstallmentRequestDTO input) {
		try {
			Batch createdBatch = batchService.createBatch(input);
			return ResponseEntity.ok(createdBatch);
		} catch (DataIntegrityViolationException e) {
			// Handle specific exception
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping("/batches")
	public ResponseEntity<List<Batch>> getAllBatches() {
		List<Batch> batches = batchService.getAllBatches();
		return ResponseEntity.ok(batches);
	}

	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping("/get-enrollments/{batchId}")
	public ResponseEntity<List<EnrollmentResponseStudent>> getEnrollments(@PathVariable int batchId) {
		// Fetch the enrolled users for the batch
		List<EnrollmentResponseStudent> users = enrollmentService.getEnrollments(batchId);

		// Return the list of users in the response
		return ResponseEntity.ok(users);
	}

	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PutMapping("/change-enrollment-status/{id}")
	public ResponseEntity<String> changeEnrollmentStatus(@RequestBody ChangeStatusDto input, @PathVariable int id) {
		try {
			enrollmentService.changeEnrollmentStatus(input, id);
			return ResponseEntity.ok("Enrollment status updated successfully.");
		} catch (Exception ex) {
			throw new RuntimeException("An unexpected error occurred", ex);
		}
	}

	@GetMapping("/users/roles")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<List<String>> findUserRoles(@RequestParam Integer userId) {
		try {
			List<String> roles = userService.getUserRolesById(userId);
			return ResponseEntity.ok(roles);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(new ArrayList<>());
		}
	}

	@PutMapping("/users/roles")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<String> updateUserRoles(@RequestParam Integer userId, @RequestBody List<String> roles) {
		try {
			userService.updateUserRoles(userId, roles);
			return ResponseEntity.ok("SUCCESS");
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body("FAILED");
		}
	}

	@PutMapping("/users/processFee")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<UserPaymentResponseDTO> processFeeRequest(@RequestBody UserPaymentsRequestDTO dto) {
		UserPaymentResponseDTO payment = userService.processFee(dto);
		return new ResponseEntity<UserPaymentResponseDTO>(payment, HttpStatus.OK);
	}

	@GetMapping("/users/processFee")
	public ResponseEntity<List<UserPaymentResponseDTO>> getAllprocessFeeRequest() {
		List<UserPaymentResponseDTO> payment = userService.getAllprocessFee();
		return new ResponseEntity<List<UserPaymentResponseDTO>>(payment, HttpStatus.OK);
	}

	@GetMapping("/users/processFee/{batchId}/{paymentStatus}")
	public ResponseEntity<List<UserPaymentResponseDTO>> getAllprocessFeeRequest(@PathVariable int batchId,
			@PathVariable String paymentStatus) {
		List<UserPaymentResponseDTO> payment = userService.getAllprocessFeeByBatchID(batchId, paymentStatus);

		return new ResponseEntity<List<UserPaymentResponseDTO>>(payment, HttpStatus.OK);
	}

	@GetMapping("/users/processFeeBatchStatus/{paymentStatus}")
	public ResponseEntity<List<UserPaymentResponseDTO>> getAllprocessFeeRequestBasedOnPaymentStatus(
			@PathVariable String paymentStatus) {
		List<UserPaymentResponseDTO> payment = userService.getAllprocessFeeRequestBasedOnPaymentStatus(paymentStatus);

		return new ResponseEntity<List<UserPaymentResponseDTO>>(payment, HttpStatus.OK);
	}

	@GetMapping("/users/processFeeByBatchId/{batchId}")
	public ResponseEntity<List<UserPaymentResponseDTO>> getAllprocessFeeRequestBasedOnBatchId(
			@PathVariable int batchId) {
		List<UserPaymentResponseDTO> payment = userService.getAllprocessFeeRequestBasedOnBatchId(batchId);

		return new ResponseEntity<List<UserPaymentResponseDTO>>(payment, HttpStatus.OK);
	}
	
	@GetMapping("/users/processFeeByBatchId/{userId}")
	public ResponseEntity<UserPaymentResponseDTO> getAllprocessFeeRequestBasedOnUserId(
			@PathVariable int userId) {
	UserPaymentResponseDTO payment = userService.getAllprocessFeeRequestBasedOnUserId(userId);

		return new ResponseEntity<UserPaymentResponseDTO>(payment, HttpStatus.OK);
	}

}
