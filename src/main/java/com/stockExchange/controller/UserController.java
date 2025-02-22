package com.stockExchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stockExchange.dao.UserService;
import com.stockExchange.data.dto.LoginDTO;
import com.stockExchange.data.dto.ResponseDTO;
import com.stockExchange.data.dto.UserDTO;
import com.stockExchange.data.dto.UserUpdateDTO;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userServics;
	
	@PostMapping("/login")
	private ResponseEntity<ResponseDTO> login(@RequestBody LoginDTO login) {
		
		ResponseDTO response = userServics.login(login);
		return new ResponseEntity<ResponseDTO>(response, HttpStatus.valueOf(response.getStatusCode()));
	}
	
	@PostMapping("/register")
	private ResponseEntity<ResponseDTO> register(@RequestBody UserDTO user) {
		
		ResponseDTO response = userServics.register(user);
		return new ResponseEntity<ResponseDTO>(response, HttpStatus.valueOf(response.getStatusCode()));
	}
	
	@PostMapping("/update")
	private ResponseEntity<ResponseDTO> update(@RequestBody UserUpdateDTO user) {
		
		ResponseDTO response = userServics.updateUser(user);
		return new ResponseEntity<ResponseDTO>(response, HttpStatus.valueOf(response.getStatusCode()));
	}
}
