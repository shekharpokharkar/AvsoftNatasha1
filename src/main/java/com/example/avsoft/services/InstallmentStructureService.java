package com.example.avsoft.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.avsoft.entities.InstallmentStructure;
import com.example.avsoft.repositories.InstallmentStructureRepository;

@Service
public class InstallmentStructureService {

	@Autowired
	private InstallmentStructureRepository installmentRepository;

	public List<InstallmentStructure> getAllInstruction(String batchId) {

		
		List<InstallmentStructure> installStructureList = installmentRepository.findByBatch(Integer.parseInt(batchId));

		return installStructureList;
	}

}
