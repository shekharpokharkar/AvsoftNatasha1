package com.example.avsoft.services;

import com.example.avsoft.dtos.EnquiryUserDto;
import com.example.avsoft.dtos.LoginUserDto;
import com.example.avsoft.dtos.OtpData;
import com.example.avsoft.dtos.RegisterUserDto;
import com.example.avsoft.dtos.ResetPasswordDto;
import com.example.avsoft.entities.Enquiry;
import com.example.avsoft.entities.User;
import com.example.avsoft.exceptions.UserAlreadyExistsException;
import com.example.avsoft.repositories.EnquiryRepository;
import com.example.avsoft.repositories.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthenticationService {
	
	@Autowired
    private  UserRepository userRepository;
	@Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private  AuthenticationManager authenticationManager;
    
    @Autowired
    private EnquiryRepository  EnquiryRepository;
    
    
    @Autowired
    private JavaMailSender mailSender;
    
    private final Map<String, OtpData> otpStore = new HashMap<>();
   
    @Transactional
    public User signup(RegisterUserDto input) {
    	if(userRepository.findByEmail(input.getEmail()).isPresent()) {
    		throw new UserAlreadyExistsException("Email is already registered.");
    	}
    		
        User user = new User()
            .setFullName(input.getFullName())
            .setEmail(input.getEmail())
            .setPassword(passwordEncoder.encode(input.getPassword()))
            .setRole("user")
            .setImage(input.getImage());
            

        return userRepository.save(user);
    }
    
    

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                input.getEmail(),
                input.getPassword()
            )
        );

        return userRepository.findByEmail(input.getEmail()).orElseThrow();
    }
    
    public Enquiry saveEnquiry(EnquiryUserDto dto) {
    	 var enquiry = new Enquiry();
    	    enquiry.setName(dto.getName());
    	    enquiry.setEmail(dto.getEmail());
    	    enquiry.setContact(dto.getContact());
    	    enquiry.setCourse(dto.getCourse());
    	    enquiry.setExperience(dto.getExperience());
    	    enquiry.setStatus("New");

    	    return EnquiryRepository.save(enquiry);
    }
    
    

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }
    // Generating the  OTP and send email to user
    public void sendOtpToEmail(ResetPasswordDto dto) {
        Optional<User> optionalUser = userRepository.findByEmail(dto.getEmail());

        if (optionalUser.isEmpty()) {
            throw new NoSuchElementException("User with email " + dto.getEmail() + " not found");
        }

        String otp = String.format("%06d", new Random().nextInt(999999)); // Generate 6-digit OTP
        otpStore.put(dto.getEmail(), new OtpData(otp,LocalDateTime.now()));
     
       
        // Send email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(dto.getEmail());
        message.setSubject("Password Reset OTP");
        message.setText("Your OTP is: " + otp);
        mailSender.send(message);
    }
    
    

    public boolean verifyOTP(ResetPasswordDto dto) {
        OtpData data = otpStore.get(dto.getEmail());
        if (data == null || data.isExpired()) {
            otpStore.remove(dto.getEmail()); // Remove expired OTP
            return false; // Invalid OTP due to expiration
        }
        boolean isValid = data.getOtp().equals(dto.getOtp());
        if (isValid) {
            otpStore.remove(dto.getEmail()); 
        }
        
        return isValid;
    }
}
