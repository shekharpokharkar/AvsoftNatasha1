package com.example.avsoft.controller;

import com.example.avsoft.entities.Enquiry;
import com.example.avsoft.entities.User;
import com.example.avsoft.dtos.EnquiryUserDto;
import com.example.avsoft.dtos.LoginUserDto;
import com.example.avsoft.dtos.RegisterUserDto;
import com.example.avsoft.dtos.ResetPasswordDto;
import com.example.avsoft.responses.LoginResponse;
import com.example.avsoft.services.AuthenticationService;
import com.example.avsoft.services.JwtService;
import com.example.avsoft.services.UserService;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
	
	@Autowired
    private  JwtService jwtService;
	@Autowired
    private  AuthenticationService authenticationService;
	@Autowired
	private UserService userService;
   
	@RequestMapping("/{path:[^\\.]+}/**")
	    public String forward() {
	        return "forward:/index.html";
	}

    @PostMapping("/signup")
    public ResponseEntity<User> register(@Validated @RequestBody RegisterUserDto registerUserDto) {
    	
    User registeredUser = authenticationService.signup(registerUserDto);
     String message = "Registration successful";
        
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);
        

        Collection<? extends GrantedAuthority> authorities = authenticatedUser.getAuthorities();
        List<String> roles = authorities.stream()
                                        .map(GrantedAuthority::getAuthority)
                                        .toList();
        LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setRole(roles).setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
    
    @PostMapping("/enquiry")
    public ResponseEntity saveEnquiry(@RequestBody EnquiryUserDto EnquiryUserDto ) {
    	
    	Enquiry enquiry = authenticationService.saveEnquiry(EnquiryUserDto);
        return ResponseEntity.ok(enquiry.getName());

    }
    @PostMapping("/reset-password/send-otp")
    public ResponseEntity<String> requestPasswordReset(@RequestBody ResetPasswordDto dto) {
    	//String mail = dto.getEmail();
    	authenticationService.sendOtpToEmail(dto);
        return ResponseEntity.ok("OTP sent to email.");
    }

    @PostMapping("/reset-password/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody ResetPasswordDto dto) {
        boolean isVerified = authenticationService.verifyOTP(dto);
 if (isVerified) {
            boolean isResetSuccessful = userService.resetPassword(dto.getEmail(), dto.getNewPassword());
            if (isResetSuccessful) {
                return ResponseEntity.ok("OTP verified and password reset successfully.");
            } else {
                return ResponseEntity.badRequest().body("User not found.");
            }
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired OTP.");
        }
    }
 

}