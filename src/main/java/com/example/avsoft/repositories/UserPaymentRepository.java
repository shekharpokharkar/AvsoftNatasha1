package com.example.avsoft.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.avsoft.entities.UserPayment;
import com.example.avsoft.entities.UserPaymentID;

public interface UserPaymentRepository extends JpaRepository<UserPayment, UserPaymentID>{

	
	@Query(name = "SELECT U FROM UserPayment U WHERE U.batchId = :BatchID AND U.status = :paymentStatus")
	public List<UserPayment> findAllUserPaymentByBatchIdAndStatus(Long BatchID,String paymentStatus);

	@Query(name = "SELECT U FROM UserPayment U WHERE U.status = :paymentStatus")
	public List<UserPayment> findAllUserPaymentByStatus(String paymentStatus);

	@Query("SELECT U FROM UserPayment U WHERE U.batchId = :batchId")
	public List<UserPayment> findAllByBatchId(Long batchId);

	@Query("SELECT U FROM UserPayment U WHERE U.userId = :userId")
	public UserPayment findByUserId(int userId);


	

}
