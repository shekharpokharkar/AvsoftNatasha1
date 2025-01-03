package com.example.avsoft.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.avsoft.entities.Placements;
import com.example.avsoft.services.AdminDashboardService;

@RequestMapping("/placements")
@RestController
public class PlacementsController {
	
	@Autowired
	private AdminDashboardService adminDashboardService;
	
	@GetMapping("/get-placement")
	public ResponseEntity<List<Placements>> getPlacement() {
	    List<Placements> placements = adminDashboardService.getPlacements();
	    return ResponseEntity.ok(placements);
	}

}
