package com.stockExchange.dao;

import com.stockExchange.data.dto.LoginDTO;
import com.stockExchange.data.dto.ResponseDTO;
import com.stockExchange.data.dto.UserDTO;
import com.stockExchange.data.dto.UserUpdateDTO;

public interface UserService{

	public ResponseDTO login(LoginDTO login);
	public ResponseDTO register(UserDTO user);
	public ResponseDTO updateUser(UserUpdateDTO user);
}
