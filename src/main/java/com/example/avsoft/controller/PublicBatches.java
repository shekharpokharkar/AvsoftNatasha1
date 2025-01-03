package com.example.avsoft.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.avsoft.entities.Batch;
import com.example.avsoft.entities.Placements;
import com.example.avsoft.services.AdminDashboardService;
import com.example.avsoft.services.BatchService;

@RequestMapping("/public")
@RestController
public class PublicBatches {
	
	@Autowired
	private BatchService batchService;
	
	@Autowired
	private AdminDashboardService adminDashboardService;
	
	 @GetMapping("/batches")
	    public ResponseEntity<List<Batch>> getAllBatches() {
	        List<Batch> batches = batchService.getAllBatches();
	        return ResponseEntity.ok(batches);
	 }
	 
	 @GetMapping("/get-placement")
		public ResponseEntity<List<Placements>> getPlacement() {
		 System.out.println("Requesst");
		    List<Placements> placements = adminDashboardService.getPlacements();
		    return ResponseEntity.ok(placements);
		}

}
