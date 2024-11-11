package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.dao.UserRepository;
import com.example.demo.util.JwtTokenUtil;
import com.example.demo.vo.User;

public class AuthController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user) {
		log.info("Jasontest login user.account = " + user.getUsername());
		User existingUser = userRepository.findByUsername(user.getUsername());
		if(existingUser != null && new BCryptPasswordEncoder().matches(user.getPassword(), existingUser.getPassword())) {
			String token = jwtTokenUtil.generateToken(user.getUsername());
			Map<String, String> response = new HashMap<>();
			response.put("token", token);
			
			return ResponseEntity.ok(response);
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
	}
	
}
