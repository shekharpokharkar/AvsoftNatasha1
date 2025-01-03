package com.example.avsoft.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.avsoft.dtos.AdminDashboardDto;
import com.example.avsoft.entities.Enquiry;
import com.example.avsoft.entities.Placements;
import com.example.avsoft.repositories.EnquiryRepository;
import com.example.avsoft.repositories.PlacementsRepository;
import com.example.avsoft.repositories.UserRepository;
import com.example.avsoft.responses.PlacementProfile;

import ch.qos.logback.classic.Logger;
import io.jsonwebtoken.io.IOException;

@Service
public class AdminDashboardService {
	
	@Autowired
	private EnquiryRepository enquiryRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PlacementsRepository placementsRepository;
	
	
	public AdminDashboardDto getData() {
		
        //first get the count of enquires
		
		AdminDashboardDto dashboardData = new AdminDashboardDto();
		
		dashboardData.setEnquiries(enquiryRepository.count());
		dashboardData.setStudent(userRepository.count());
		
		HashMap<String, Integer> enquiryOvertime = new HashMap<>();
		
//		calculate the last sevend Days
		LocalDateTime last7Days  = LocalDateTime.now().minus(7, ChronoUnit.DAYS);
		
		List<Enquiry> enquirys = enquiryRepository.findAllByLast7Days(last7Days);
		
	   
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
		enquirys.forEach(enquiry -> {
			 LocalDateTime createdAt = Instant.ofEpochMilli(enquiry.getCreatedAt().getTime())
		                .atZone(ZoneId.systemDefault())
		                .toLocalDateTime();
		String day = createdAt.format(formatter);
	            enquiryOvertime.put(day, enquiryOvertime.getOrDefault(day, 0) + 1);
	        });
		dashboardData.setEnquiryOverTime(enquiryOvertime);
		
		return dashboardData;
		
		

		
	}
	
	 @Value("${file.upload-dir-static}")
	    private String fileUploadDir;
	
	 public PlacementProfile createPlacements(PlacementProfile profile, MultipartFile photo) throws IOException {
		    Placements placement = new Placements(null, null, null, null);

		    if (photo != null && !photo.isEmpty()) {
		        String originalFileName = photo.getOriginalFilename();
	            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
	            String newFileName = profile.getName() + profile.getCompany().replaceAll("\\s+", "")  + "_placements" + fileExtension;

	            Path uploadPath = Paths.get(fileUploadDir);
	            if (!Files.exists(uploadPath)) {
	                try {
						Files.createDirectories(uploadPath);
					} catch (java.io.IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }

	            Path targetLocation = uploadPath.resolve(newFileName);
	            try {
					Files.copy(photo.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
				} catch (java.io.IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	            String fileUrl = newFileName;
	            placement.setImage(fileUrl);
		    }

		    // Set other placement properties from the profile if needed
		    placement.setCompany(profile.getCompany());
		    placement.setName(profile.getName());
		    placement.setSalary(profile.getSalary());
		    
		    
		    placementsRepository.save(placement);

		    return profile;
		}
	 
	 
     public List<Placements> getPlacements(){
    	    List<Placements> placements = placementsRepository.findAll();
    	    return placements;

     }
     
     public String deletePlacements(int id) {
    	    // Check if placement exists by ID
    	    Placements placement = placementsRepository.findById(id).orElse(null);
    	    
    	    if (placement == null) {
    	        return "Placement not found.";
    	    }
    	    
    	    // Delete the placement's associated image if it exists
    	    if (placement.getImage() != null) {
    	        Path imagePath = Paths.get(fileUploadDir, placement.getImage());
    	        try {
    	            Files.deleteIfExists(imagePath);
    	        } catch (java.io.IOException e) {
    	            e.printStackTrace();
    	            return "Error deleting placement image.";
    	        }
    	    }
    	    
    	    // Delete the placement from the repository
    	    placementsRepository.deleteById(id);
    	    
    	    return "Placement deleted successfully.";
    	}

	
	
	

}
