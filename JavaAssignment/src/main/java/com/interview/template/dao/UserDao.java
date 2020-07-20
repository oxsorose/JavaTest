package com.interview.template.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.interview.template.constants.UserConstants;
import com.interview.template.exceptions.UserNotFoundException;
import com.interview.template.model.UserEntity;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class UserDao {
	private final UserRepository userRepository;

	public List<UserEntity> findAll() {
		return userRepository.findAll();
	}

	public Optional<UserEntity> find(long id) {
		return userRepository.findById(id);
	}

	public UserEntity findOrDie(long id) throws UserNotFoundException {
		return find(id).orElseThrow(() -> new UserNotFoundException(String.format(UserConstants.USERID_NOT_FOUND, id)));
	}

	public void checkExists(long id) throws UserNotFoundException {
		if (!userRepository.existsById(id)) {
			throw new UserNotFoundException(String.format(UserConstants.USERID_NOT_FOUND, id));
		}
	}

	public List<UserEntity> findByUserName(String userName) {
		return userRepository.findByUsername(userName);
	}

	public List<UserEntity> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public UserEntity createUser(UserEntity user) {
		if (user.getId() != null) {
			throw new IllegalArgumentException("User already exists.");
		}
		return userRepository.save(user);
	}

	public void deleteUserById(long id) throws UserNotFoundException {
		checkExists(id);
		userRepository.deleteById(id);
	}
	
	public List<UserEntity> fetchUsers(String userName) {
		return userRepository.findByUsernameStartingWith(userName);
	}
}
