package com.stockExchange.daoImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.stockExchange.dao.UserService;
import com.stockExchange.data.dto.LoginDTO;
import com.stockExchange.data.dto.ResponseDTO;
import com.stockExchange.data.dto.UserDTO;
import com.stockExchange.data.dto.UserUpdateDTO;
import com.stockExchange.data.entity.UserEntity;
import com.stockExchange.repository.UserRepo;

@Service
public class UserServiceImplementation implements UserService {

	@Autowired
	UserRepo userRepo;

	@Override
	public ResponseDTO login(LoginDTO login) {

		if (login.getUsername().isBlank()) {
			return new ResponseDTO(HttpStatus.BAD_REQUEST, "UserName should not be empty");
		}

		if (login.getPassword().isBlank()) {
			return new ResponseDTO(HttpStatus.BAD_REQUEST, "Password should not be empty");
		}

		if (login.getRole().isBlank()) {
			return new ResponseDTO(HttpStatus.BAD_REQUEST, "Role should not be empty");
		}

		Optional<UserEntity> data = userRepo.findByUsernameAndRole(login.getUsername(), login.getRole());

		if (!data.isPresent()) {
			return new ResponseDTO(HttpStatus.NOT_FOUND, "User not found");
		}

		if (!data.get().getPassword().equals(login.getPassword())) {
			return new ResponseDTO(HttpStatus.UNAUTHORIZED, "Password not matched", data);
		}

		return new ResponseDTO(HttpStatus.OK, "Login successful", data);
	}

	@Override
	public ResponseDTO register(UserDTO user) {

		Optional<UserEntity> data = userRepo.findByUsernameAndRole(user.getUsername(), "Investor");

		if (data.isPresent()) {
			return new ResponseDTO(HttpStatus.BAD_REQUEST, "UserName is Already taken");
		}
		
		UserEntity newUser = new UserEntity(user);
		userRepo.save(newUser);

		return new ResponseDTO(HttpStatus.OK, "Registration successful", newUser);
	}

	@Override
	public ResponseDTO updateUser(UserUpdateDTO user) {

		if (user.getUsername().isBlank()) {
			return new ResponseDTO(HttpStatus.BAD_REQUEST, "UserName should not be empty");
		}

		Optional<UserEntity> oldUserDetails = userRepo.findByUsername(user.getUsername());

		if (!oldUserDetails.isPresent()) {
			return new ResponseDTO(HttpStatus.NOT_FOUND, "User not found");
		}

		oldUserDetails.get().setFirstName(user.getFirstName());
		oldUserDetails.get().setLastName(user.getLastName());
		oldUserDetails.get().setEmail(user.getEmail());
		oldUserDetails.get().setNumber(user.getNumber());
		oldUserDetails.get().setPassword(user.getPassword());

		userRepo.save(oldUserDetails.get());
		return new ResponseDTO(HttpStatus.OK, "User details updated successfully", oldUserDetails);
	}

}
