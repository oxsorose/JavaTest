package com.interview.template.dao;

import com.interview.template.model.UserEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface UserRepository extends JpaRepository<UserEntity, Long> {
	
	List<UserEntity> findByUsernameStartingWith(String userName);
	
	List<UserEntity> findByUsername(String userName);
	
	List<UserEntity> findByEmail(String email);
}
