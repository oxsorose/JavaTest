package com.interview.template.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.interview.template.constants.UserConstants;
import com.interview.template.dao.UserDao;
import com.interview.template.exceptions.BlacklistedUserNameException;
import com.interview.template.exceptions.UserEmailExistsException;
import com.interview.template.exceptions.UserNameExistsException;
import com.interview.template.exceptions.UserNotFoundException;
import com.interview.template.model.UserEntity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class UserService {

	private final UserDao userDao;

	@Value("${blacklist.names}")
	private List<String> blackListedNames;

	public List<UserEntity> getAllUsers() {
		return userDao.findAll();
	}

	public UserEntity getUser(long id) throws UserNotFoundException {
		return userDao.findOrDie(id);
	}

	public void checkUserExists(long id) throws UserNotFoundException {
		userDao.checkExists(id);
	}

	public UserEntity createUser(UserEntity user)
			throws UserNameExistsException, UserEmailExistsException, BlacklistedUserNameException {
		checkBlackListedUser(user.getUsername());
		checkUserNameExists(user.getUsername());
		checkUserEmailExists(user.getEmail());
		return userDao.createUser(user);
	}

	private void checkBlackListedUser(String userName) throws BlacklistedUserNameException {
		if (!blackListedNames.isEmpty() && blackListedNames.contains(userName)) {
			throw new BlacklistedUserNameException(String.format(UserConstants.BLACKLISTED_USER_NAME, userName));
		}
	}

	private void checkUserNameExists(String userName) throws UserNameExistsException {
		List<UserEntity> userDetails = userDao.findByUserName(userName);
		if (!userDetails.isEmpty()) {
			throw new UserNameExistsException(String.format(UserConstants.USER_NAME_EXISTS, userName));
		}
	}

	private void checkUserEmailExists(String email) throws UserEmailExistsException {
		List<UserEntity> userDetails = userDao.findByEmail(email);
		if (!userDetails.isEmpty()) {
			throw new UserEmailExistsException(String.format(UserConstants.USER_EMAIL_EXISTS, email));
		}
	}

	public void deleteUserById(Long id) throws UserNotFoundException {
		userDao.deleteUserById(id);
	}

	public List<UserEntity> fetchUsers(String userName) {
		return userDao.fetchUsers(userName);
	}
}
