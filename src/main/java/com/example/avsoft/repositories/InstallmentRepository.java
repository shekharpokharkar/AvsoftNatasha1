package com.example.avsoft.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.avsoft.entities.Installment;
import com.example.avsoft.entities.User;

public interface InstallmentRepository extends JpaRepository<Installment, Integer> {
	
	List<Installment> findByUser(User user);

	Optional<Installment> findByUserIdAndBatchIdAndInstallmentNo(Integer id, int i, int installmentNo);

	List<Installment> findByUserIdAndBatchId(int integer, int i);

}
