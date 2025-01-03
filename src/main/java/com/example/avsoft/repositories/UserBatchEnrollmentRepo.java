package com.example.avsoft.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.avsoft.entities.UserBatchEnrollment;

public interface UserBatchEnrollmentRepo  extends JpaRepository<UserBatchEnrollment, Integer>{

	boolean existsByUserIdAndBatchId(Integer userId, Integer batchId);
	
	List<UserBatchEnrollment> findByUserId(int userId);
	List<UserBatchEnrollment> findByBatchId(int batchId);

    Optional<UserBatchEnrollment> findByBatchIdAndUserId(int batchId, int userId);
    
    
}
