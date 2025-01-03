package com.example.avsoft.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.avsoft.dtos.AdminDashboardDto;
import com.example.avsoft.entities.Placements;
import com.example.avsoft.responses.PlacementProfile;
import com.example.avsoft.services.AdminDashboardService;


@RequestMapping("/admin/dashboard")
@RestController
public class AdminDashboardContoller {
	
	@Autowired 
	private AdminDashboardService  adminDashboardService;
	
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping("/getdata")
	public ResponseEntity<AdminDashboardDto> getDashboardData(){
          AdminDashboardDto data = 	adminDashboardService.getData();
          return ResponseEntity.ok(data);
		
	}
	
	
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping("/create-placement")
	public ResponseEntity<PlacementProfile> createPlacement(@ModelAttribute PlacementProfile profile,@RequestParam("photo") MultipartFile photo)
	{
		PlacementProfile newprofile = adminDashboardService.createPlacements(profile, photo);
		return ResponseEntity.ok(newprofile);
		
		
	}
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@DeleteMapping("/delete-placement/{id}")
	public ResponseEntity<String> deletePlacement(@PathVariable int id) {
	    String result = adminDashboardService.deletePlacements(id);
	    
	    if ("Placement deleted successfully.".equals(result)) {
	        return ResponseEntity.ok(result);  // Return HTTP 200 with success message
	    } else if ("Placement not found.".equals(result)) {
	        return ResponseEntity.status(404).body(result);  // Return HTTP 404 for not found
	    } else {
	        return ResponseEntity.status(500).body("An error occurred while deleting the placement.");  // Return HTTP 500 for internal server error
	    }
	}

	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping("/get-placement")
	public ResponseEntity<List<Placements>> getPlacement() {
	    List<Placements> placements = adminDashboardService.getPlacements();
	    return ResponseEntity.ok(placements);
	}



}
