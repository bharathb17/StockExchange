package com.stockExchange.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stockExchange.data.entity.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity, String>{

	public Optional<UserEntity> findByUsernameAndRole(String userName, String role);
	public Optional<UserEntity> findByUsername(String userName);
}
