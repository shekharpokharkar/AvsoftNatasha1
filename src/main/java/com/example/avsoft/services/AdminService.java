package com.example.avsoft.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.avsoft.entities.Enquiry;
import com.example.avsoft.repositories.EnquiryRepository;

@Service
public class AdminService {

    @Autowired
    private EnquiryRepository EnquiryRepository;
	
	public List<Enquiry> getEnquiries(){
		
		List <Enquiry> Enquiry = EnquiryRepository.findAll();
		Enquiry.sort((e1,e2)->e2.getCreatedAt().compareTo(e1.getCreatedAt()));
		return Enquiry;
		
	}
	
}
