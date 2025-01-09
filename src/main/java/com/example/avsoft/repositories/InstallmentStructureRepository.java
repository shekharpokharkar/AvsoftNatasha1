package com.example.avsoft.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.avsoft.entities.InstallmentStructure;

public interface InstallmentStructureRepository extends JpaRepository<InstallmentStructure, Integer>{

	
	@Query(name = "select * from InstallmentStructure where batch :batchId")
	public List<InstallmentStructure> findByBatch(int batchId);

}
